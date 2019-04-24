package com.zmm.paintingdays.mvp.model;

import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.presenter.contract.LoginContract;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public class LoginModel implements LoginContract.ILoginModel {

    private ApiService mApiService;

    public LoginModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<UserBean>> login(String username, String password) {
        return mApiService.login(username,password);
    }
}
