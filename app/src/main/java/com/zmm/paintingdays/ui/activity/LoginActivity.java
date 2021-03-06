package com.zmm.paintingdays.ui.activity;

import android.content.Intent;
import android.graphics.PointF;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.dagger.component.DaggerLoginComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.LoginModule;
import com.zmm.paintingdays.mvp.presenter.LoginPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.LoginContract;
import com.zmm.paintingdays.utils.SharedPreferencesUtil;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.config.CommonConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/3
 * Email:65489469@qq.com
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {


    @BindView(R.id.et_login_phone)
    EditText mEtLoginPhone;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;
    @BindView(R.id.sub_image)
    SubsamplingScaleImageView mSubImage;


    private String mUsernameStr;
    private String mPasswordStr;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerLoginComponent.builder()
                .httpComponent(httpComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void init() {

        mSubImage.setImage(ImageSource.resource(R.drawable.login_bg),new ImageViewState(1.2f, new PointF(0, 0), 0));
        mSubImage.setZoomEnabled(false);
        mSubImage.setPanEnabled(false);
    }


    @Override
    public void loginSuccess(UserBean userBean) {
        SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER,SharedPreferencesUtil.toJson(userBean));
        startActivity(MainActivity.class,true);
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 16;
    }

    @OnClick({R.id.btn_login, R.id.tv_login_register, R.id.tv_login_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_login_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                intent.putExtra("param",1);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_login_forget:
                Intent intent2 = new Intent(this,RegisterActivity.class);
                intent2.putExtra("param",2);
                startActivity(intent2);
                finish();
                break;
        }
    }

    private void login() {
        mUsernameStr = mEtLoginPhone.getText().toString();
        mPasswordStr = mEtLoginPassword.getText().toString();

        if (TextUtils.isEmpty(mUsernameStr) || !isPhoneValid(mUsernameStr.trim())) {
            ToastUtils.SimpleToast("用户名格式不正确");
            return;
        }

        if (TextUtils.isEmpty(mPasswordStr) || !isPasswordValid(mPasswordStr.trim())) {
            ToastUtils.SimpleToast("密码格式不正确");
            return;
        }


        mPresenter.login(mUsernameStr.trim(), mPasswordStr.trim());
    }
}
