package com.zmm.paintingdays.mvp.model;


import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.presenter.contract.RegisterContract;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class RegisterModel implements RegisterContract.IRegisterModel{

    private ApiService mApiService;

    public RegisterModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<String>> getVerifyCode(String username) {
        return mApiService.getVerifyCode(username);
    }

    @Override
    public Observable<BaseBean<UserBean>> register(String username, String password, String code) {
        return mApiService.register(username,password,code);
    }

    @Override
    public Observable<BaseBean<String>> modifyPassword(String username, String oldPassword,String newPassword) {
        return mApiService.modifyPassword(username,oldPassword,newPassword);
    }

    @Override
    public Observable<BaseBean<String>> resetPassword(String username, String newPassword, String code) {
        return mApiService.resetPassword(username,newPassword,code);
    }
}
