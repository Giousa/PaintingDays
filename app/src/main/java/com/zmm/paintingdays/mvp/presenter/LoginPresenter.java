package com.zmm.paintingdays.mvp.presenter;


import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.mvp.presenter.contract.LoginContract;
import com.zmm.paintingdays.rx.RxHttpResponseCompat;
import com.zmm.paintingdays.rx.subscriber.ErrorHandlerSubscriber;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public class LoginPresenter extends BasePresenter<LoginContract.ILoginModel,LoginContract.LoginView> {

    @Inject
    public LoginPresenter(LoginContract.ILoginModel model, LoginContract.LoginView view) {
        super(model, view);
    }


    /**
     * 登录
     * @param username
     * @param password
     */
    public void login(String username, String password) {

        mModel.login(username,password)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.loginSuccess(userBean);
                    }
                });
    }
}
