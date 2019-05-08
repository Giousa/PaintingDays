package com.zmm.paintingdays.mvp.presenter.contract;

import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public interface PaintingsContract {


    interface IPaintingsModel{
        Observable<BaseBean<List<PaintingsBean>>> findAllPaintingsByUid(String uId,int page,int size);

        Observable<BaseBean<List<PaintingsBean>>> findTodayPaintingsByUid(String uId,int page,int size);

        Observable<BaseBean<List<PaintingsBean>>> findPaintingsByCreateTime(String uId,String createTime);

        Observable<BaseBean<PaintingsBean>> addPaintings(String uId,String username,String title,String content, String tags,int jurisdiction, MultipartBody.Part file);

        Observable<BaseBean<String>> deletePaintingsById(String id);
    }

    interface PaintingsView extends BaseView{

        void findAllPaintingsByUidOnRefresh(List<PaintingsBean> paintingsBeanList);

        void findAllPaintingsByUidOnLoadMore(List<PaintingsBean> paintingsBeanList);

        void findAllPaintingsByUidFailure();

        void addPaintingsSuccess(PaintingsBean paintingsBean);

        void addPaintingsFailure();

        void deletePaintingsByIdSuccess(int position);
    }
}
