package com.zmm.paintingdays.ui.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.config.CommonConfig;
import com.zmm.paintingdays.dagger.component.DaggerUserComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.UserModule;
import com.zmm.paintingdays.mvp.presenter.UserPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.UserContract;
import com.zmm.paintingdays.ui.activity.SettingActivity;
import com.zmm.paintingdays.ui.activity.UserInfoActivity;
import com.zmm.paintingdays.ui.widget.CustomItemView;
import com.zmm.paintingdays.utils.GlideUtils;
import com.zmm.paintingdays.utils.SharedPreferencesUtil;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.utils.UIUtils;
import com.zmm.paintingdays.utils.VerificationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class MyFragment extends BaseFragment<UserPresenter> implements CustomItemView.OnItemClickListener, UserContract.UserView, OnRefreshListener {

    @BindView(R.id.iv_my_icon)
    ImageView mIvMyIcon;
    @BindView(R.id.tv_my_name)
    TextView mTvMyName;
    @BindView(R.id.tv_my_sign)
    TextView mTvMySign;
    @BindView(R.id.iv_my_info)
    ImageView mIvMyInfo;
    @BindView(R.id.custom_item_setting)
    CustomItemView mCustomItemSetting;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private ArrayList<ImageItem> mImages;
    private UserBean mUserBean;

    @Override
    protected int setLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerUserComponent.builder()
                .httpComponent(httpComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void init() {

        mUserBean = UIUtils.getUserBean();

        initEvent();

        initView();

        initRefreshLayout();
    }


    private void initEvent() {
        mCustomItemSetting.setOnItemClickListener(this);

    }

    private void initView() {

        if (mUserBean != null) {
            String icon = mUserBean.getIcon();

            if (!TextUtils.isEmpty(icon)) {

                GlideUtils.loadCircleImage(mContext, icon, mIvMyIcon);
            }

            mPresenter.findUserById(mUserBean.getId());

        } else {
            mTvMyName.setText("登录/注册");
            mTvMySign.setVisibility(View.INVISIBLE);
        }

    }

    private void initRefreshLayout() {

        mRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void OnItemClick(String title) {
        startActivity(SettingActivity.class);
    }


    @OnClick({R.id.iv_my_icon, R.id.iv_my_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_my_icon:

                ImagePicker.getInstance().setMultiMode(false);
                ImagePicker.getInstance().setCrop(true);
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, 100);

                break;
            case R.id.iv_my_info:

                if (UIUtils.getUserBean() != null) {
//                    startActivity(UserInfoActivity.class);
                    startActivityForResult(new Intent(mContext, UserInfoActivity.class),1);

                } else {
                    ToastUtils.SimpleToast("请登录");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {

                mImages = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (mImages != null && mImages.size() > 0) {

                    System.out.println("选择图片：" + mImages.get(0).path);

                    if (!TextUtils.isEmpty(mUserBean.getId())) {
                        mPresenter.uploadPic(mUserBean.getId(), mUserBean.getUsername(), mImages.get(0).path);
                    } else {
                        ToastUtils.SimpleToast("请登录");
                    }
                }

            } else {
                System.out.println("没有数据");
            }
        }else if(requestCode == 1  && resultCode == 2){
            mPresenter.findUserById(mUserBean.getId());
        }


    }

    @Override
    public void updateSuccess(UserBean userBean) {

        if(mRefreshLayout != null){
            mRefreshLayout.finishRefresh();
        }
        if (userBean != null) {

            SharedPreferencesUtil.saveString(CommonConfig.LOGIN_USER, SharedPreferencesUtil.toJson(userBean));

            //头像
            String icon = userBean.getIcon();

            if (!TextUtils.isEmpty(icon)) {

                GlideUtils.loadCircleImage(mContext, icon, mIvMyIcon);
            }


            //名称
            if (TextUtils.isEmpty(userBean.getNickname())) {
                mTvMyName.setText(VerificationUtils.hidePhoneNumber(userBean.getUsername()));
            } else {
                mTvMyName.setText(userBean.getNickname());
            }

            //签名
            mTvMySign.setVisibility(View.VISIBLE);
            mTvMySign.setText(userBean.getSign());


        }
    }

    @Override
    public void updateFailure() {
        if(mRefreshLayout != null){
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        initView();
    }
}
