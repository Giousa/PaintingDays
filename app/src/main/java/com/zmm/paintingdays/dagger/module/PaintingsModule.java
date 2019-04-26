package com.zmm.paintingdays.dagger.module;

import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.model.PaintingsModel;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.ui.adapter.HistoryAdapter;
import com.zmm.paintingdays.ui.adapter.HomeAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */

@Module
public class PaintingsModule {

    private PaintingsContract.PaintingsView mPaintingsView;

    public PaintingsModule(PaintingsContract.PaintingsView paintingsView) {
        mPaintingsView = paintingsView;
    }

    @Provides
    public PaintingsContract.PaintingsView providePaintingsView(){

        return mPaintingsView;
    }

    @Provides
    public PaintingsContract.IPaintingsModel providePaintingsModel(ApiService apiService){

        return new PaintingsModel(apiService);
    }

    @Provides
    public HomeAdapter provideHomeAdapter(){

        return new HomeAdapter();
    }

    @Provides
    public HistoryAdapter provideHistoryAdapter(){

        return new HistoryAdapter();
    }

}
