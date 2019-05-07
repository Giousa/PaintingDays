package com.zmm.paintingdays.mvp.model;

import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.presenter.contract.DiaryContract;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/5/7
 * Email:65489469@qq.com
 */
public class DiaryModel implements DiaryContract.IDiaryModel{

    private ApiService mApiService;

    public DiaryModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<DiaryBean>> addDiary(String uId, String title, String content, Date createTime) {
        return mApiService.addDiary(uId,title,content,createTime);
    }

    @Override
    public Observable<BaseBean<List<DiaryBean>>> findAllDiaryByUid(String uId, int page, int size) {
        return mApiService.findAllDiaryByUid(uId,page,size);
    }

    @Override
    public Observable<BaseBean<String>> deleteDiary(String id) {
        return mApiService.deleteDiary(id);
    }
}
