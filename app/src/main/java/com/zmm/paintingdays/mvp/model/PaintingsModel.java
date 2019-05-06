package com.zmm.paintingdays.mvp.model;

import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public class PaintingsModel implements PaintingsContract.IPaintingsModel{

    private ApiService mApiService;

    public PaintingsModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<PaintingsBean>>> findAllPaintingsByUid(String uId, int page, int size) {
        return mApiService.findAllPaintingsByUid(uId,page,size);
    }

    @Override
    public Observable<BaseBean<PaintingsBean>> addPaintings(String uId, String username, String title, String content, String tags, int jurisdiction, MultipartBody.Part file) {
        return mApiService.addPaintings(uId,username,title,content,tags,jurisdiction,file);
    }
}
