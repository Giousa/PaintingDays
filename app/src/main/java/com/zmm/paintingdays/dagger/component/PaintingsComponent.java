package com.zmm.paintingdays.dagger.component;

import com.zmm.paintingdays.dagger.ActivityScope;
import com.zmm.paintingdays.dagger.module.PaintingsModule;
import com.zmm.paintingdays.ui.activity.PaintingsInfoActivity;
import com.zmm.paintingdays.ui.fragment.HistoryFragment;
import com.zmm.paintingdays.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = PaintingsModule.class,dependencies = HttpComponent.class)
public interface PaintingsComponent {

    void inject(HomeFragment homeFragment);

    void inject(HistoryFragment historyFragment);

    void inject(PaintingsInfoActivity paintingsInfoActivity);
}
