package com.zmm.paintingdays.dagger.component;



import com.zmm.paintingdays.dagger.ActivityScope;
import com.zmm.paintingdays.dagger.module.RegisterModule;
import com.zmm.paintingdays.ui.activity.RegisterActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = RegisterModule.class,dependencies = HttpComponent.class)
public interface RegisterComponent {

    void inject(RegisterActivity activity);

}
