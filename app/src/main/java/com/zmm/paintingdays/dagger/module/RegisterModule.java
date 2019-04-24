package com.zmm.paintingdays.dagger.module;



import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.model.RegisterModel;
import com.zmm.paintingdays.mvp.presenter.contract.RegisterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
@Module
public class RegisterModule {

    private RegisterContract.RegisterView mRegisterView;

    public RegisterModule(RegisterContract.RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Provides
    public RegisterContract.RegisterView provideRegisterView(){

        return mRegisterView;
    }

    @Provides
    public RegisterContract.IRegisterModel provideRegisterModel(ApiService apiService){

        return new RegisterModel(apiService);
    }


}
