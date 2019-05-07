package com.zmm.paintingdays.dagger.component;


import com.zmm.paintingdays.dagger.ActivityScope;
import com.zmm.paintingdays.dagger.module.UserModule;
import com.zmm.paintingdays.ui.fragment.MyFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/12
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = UserModule.class,dependencies = HttpComponent.class)
public interface UserComponent {

    void inject(MyFragment fragment);

//    void inject(UserInfoActivity activity);

//    void inject(SettingActivity activity);
}
