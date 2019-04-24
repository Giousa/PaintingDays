package com.zmm.paintingdays.mvp.presenter;


import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.mvp.presenter.contract.RegisterContract;
import com.zmm.paintingdays.rx.RxHttpResponseCompat;
import com.zmm.paintingdays.rx.subscriber.ErrorHandlerSubscriber;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterModel,RegisterContract.RegisterView>{

    @Inject
    public RegisterPresenter(RegisterContract.IRegisterModel model, RegisterContract.RegisterView view) {
        super(model, view);
    }

    public void getVerifyCode(String username) {

        mModel.getVerifyCode(username)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.getVerifyCode(s);
                    }
                });
    }

    public void register(String username, String password, String verifyCode) {

        mModel.register(username,password,verifyCode)
                .compose(RxHttpResponseCompat.<UserBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mView.registerSuccess(userBean);
                    }
                });
    }

    public void modifyPassword(String username, String oldPassword, String newPassword) {

        mModel.modifyPassword(username,oldPassword,newPassword)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.modifyPasswordSuccess(s);
                    }
                });
    }

    public void resetPassword(String username, String password, String verifyCode) {

        mModel.resetPassword(username,password,verifyCode)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.modifyPasswordSuccess(s);
                    }
                });
    }
}
