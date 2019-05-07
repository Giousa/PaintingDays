package com.zmm.paintingdays.dagger.module;

import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.model.DiaryModel;
import com.zmm.paintingdays.mvp.presenter.contract.DiaryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */

@Module
public class DiaryModule {

    private DiaryContract.DiaryView mDiaryView;

    public DiaryModule(DiaryContract.DiaryView diaryView) {
        mDiaryView = diaryView;
    }

    @Provides
    public DiaryContract.DiaryView provideDiaryView(){

        return mDiaryView;
    }

    @Provides
    public DiaryContract.IDiaryModel provideDiaryModel(ApiService apiService){

        return new DiaryModel(apiService);
    }

}
