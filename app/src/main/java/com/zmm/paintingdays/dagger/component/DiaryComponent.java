package com.zmm.paintingdays.dagger.component;

import com.zmm.paintingdays.dagger.ActivityScope;
import com.zmm.paintingdays.dagger.module.DiaryModule;
import com.zmm.paintingdays.ui.activity.DiaryInfoActivity;
import com.zmm.paintingdays.ui.fragment.DiaryFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = DiaryModule.class,dependencies = HttpComponent.class)
public interface DiaryComponent {

    void inject(DiaryFragment diaryFragment);

    void inject(DiaryInfoActivity diaryInfoActivity);

}
