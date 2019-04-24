package com.zmm.paintingdays.dagger.module;


import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.model.LoginModel;
import com.zmm.paintingdays.mvp.presenter.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
@Module
public class LoginModule {

    private LoginContract.LoginView mLoginView;

    public LoginModule(LoginContract.LoginView loginView) {
        mLoginView = loginView;
    }

    @Provides
    public LoginContract.LoginView provideLoginView(){

        return mLoginView;
    }

    @Provides
    public LoginContract.ILoginModel provideLoginModel(ApiService apiService){

        return new LoginModel(apiService);
    }


}
