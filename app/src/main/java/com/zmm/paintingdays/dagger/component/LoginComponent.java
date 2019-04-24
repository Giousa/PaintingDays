package com.zmm.paintingdays.dagger.component;


import com.zmm.paintingdays.dagger.ActivityScope;
import com.zmm.paintingdays.dagger.module.LoginModule;
import com.zmm.paintingdays.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = LoginModule.class,dependencies = HttpComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

}
