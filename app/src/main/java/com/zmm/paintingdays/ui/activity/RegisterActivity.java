package com.zmm.paintingdays.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.dagger.component.DaggerRegisterComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.RegisterModule;
import com.zmm.paintingdays.mvp.presenter.RegisterPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.RegisterContract;
import com.zmm.paintingdays.ui.widget.TitleBar;
import com.zmm.paintingdays.utils.SharedPreferencesUtil;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.utils.UIUtils;
import com.zmm.paintingdays.utils.VerificationUtils;
import com.zmm.paintingdays.config.CommonConfig;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.RegisterView {


    @BindView(R.id.sub_image)
    SubsamplingScaleImageView mSubImage;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.tv_getYzm)
    TextView mTvGetYzm;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private int mParam;

    private String mTitle = "注册";
    private Disposable mDisposable;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerRegisterComponent.builder()
                .httpComponent(httpComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mParam = getIntent().getIntExtra("param", 1);

        initToolBar();

    }


    private void initToolBar() {

        if (mParam == 1) {
            mTitle = "注册";
        } else if (mParam == 2) {
            mEtPassword.setHint("请输入新密码");
            mTitle = "重置密码";
        } else {
            mEtPassword.setHint("请输入新密码");
            mTitle = "修改密码";
        }
        System.out.println("mTitle = " + mTitle);

        mBtnConfirm.setText(mTitle);

        mTitleBar.setTitle(mTitle);
//        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class,true);
            }
        });
    }


    @OnClick({R.id.tv_getYzm, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getYzm:
                getYzm();
                break;
            case R.id.btn_confirm:

                buttonConfirm();

                break;
        }
    }


    private void buttonConfirm() {


        String username = mEtPhone.getText().toString();
        String password = mEtPassword.getText().toString();
        String verifyCode = mEtYzm.getText().toString();
        boolean flag1 = checkPhone(username);
        boolean flag2 = checkPassword(password);
        boolean flag3 = checkVerifyCode(verifyCode);

        if(flag1 && flag2 && flag3){
            if(mParam == 1){
                register(username,password,verifyCode);
            }else if(mParam == 2){
                ToastUtils.SimpleToast("重置密码");
//                resetPassword(username,password,verifyCode);
            }else {
                modifyPassword(username,password,verifyCode);
            }
        }
    }

    /**
     * 注册
     */
    private void register(String username, String password, String verifyCode) {

        mPresenter.register(username,password,verifyCode);

    }

    /**
     * 重置密码
     */
    private void resetPassword(String username, String password, String verifyCode) {
        mPresenter.resetPassword(username,password,verifyCode);

    }

    /**
     * 修改密码
     */
    private void modifyPassword(String username, String password, String verifyCode) {
        mPresenter.modifyPassword(username,password,verifyCode);

    }

    /**
     * 获取验证码
     */
    private void getYzm() {

        String phoneStr = mEtPhone.getText().toString();
        boolean flag = checkPhone(phoneStr);

        if(flag){
            mPresenter.getVerifyCode(phoneStr);
        }


    }

    private boolean checkPhone(String phone){
        if(TextUtils.isEmpty(phone)){
            ToastUtils.SimpleToast("手机号不能为空");
            return false;
        }

        boolean phoneValid = VerificationUtils.matcherPhoneNum(phone);

        if(!phoneValid){
            ToastUtils.SimpleToast("手机号格式不正确");
            return false;
        }

        return true;
    }


    private boolean checkPassword(String password){
        if(TextUtils.isEmpty(password)){
            ToastUtils.SimpleToast("密码不能为空");
            return false;
        }

        boolean passwordValid = VerificationUtils.matcherPassword(password);

        if(!passwordValid){
            ToastUtils.SimpleToast("密码格式不正确");
            return false;
        }

        return true;
    }

    private boolean checkVerifyCode(String code){

        if(TextUtils.isEmpty(code)){
            ToastUtils.SimpleToast("验证码不能为空");
            return false;
        }

        return true;
    }


    @Override
    public void getVerifyCode(String msg) {
        ToastUtils.SimpleToast(msg);


        System.out.println("开始计时：：");
        final int count = 60;

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                        mTvGetYzm.setClickable(false);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("value = " + value);
                        mTvGetYzm.setText(value + " 秒");

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.SimpleToast(getResources().getString(R.string.exception));
                    }

                    @Override
                    public void onComplete() {
                        mTvGetYzm.setClickable(true);
                        mTvGetYzm.setText("发送验证码");
                    }
                });
    }

    @Override
    public void registerSuccess(UserBean userBean) {

        SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER,SharedPreferencesUtil.toJson(userBean));

        startActivity(MainActivity.class,true);
    }

    @Override
    public void modifyPasswordSuccess(String msg) {
        ToastUtils.SimpleToast(msg);
        startActivity(LoginActivity.class,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mDisposable != null){
            mDisposable.dispose();
        }
    }
}
