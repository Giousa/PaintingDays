package com.zmm.paintingdays.mvp.presenter;

import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.rx.RxHttpResponseCompat;
import com.zmm.paintingdays.rx.subscriber.ErrorHandlerSubscriber;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public class PaintingsPresenter extends BasePresenter<PaintingsContract.IPaintingsModel,PaintingsContract.PaintingsView>{


    @Inject
    public PaintingsPresenter(PaintingsContract.IPaintingsModel model, PaintingsContract.PaintingsView view) {
        super(model, view);
    }

    /**
     * 根据用户id，查询所有画作
     * @param userId
     * @param page
     * @param size
     * @param isRefresh
     */
    public void findAllPaintingsByUid(String userId, int page, int size, final boolean isRefresh) {

        mModel.findAllPaintingsByUid(userId,page,size)
                .compose(RxHttpResponseCompat.<List<PaintingsBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<PaintingsBean>>() {
                    @Override
                    public void onNext(List<PaintingsBean> paintingsBeanList) {
                        if(isRefresh){
                            mView.findAllPaintingsByUidOnRefresh(paintingsBeanList);
                        }else {
                            mView.findAllPaintingsByUidOnLoadMore(paintingsBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.findAllPaintingsByUidFailure();
                    }
                });
    }

    /**
     * 添加画作
     * @param id
     * @param username
     * @param title
     * @param content
     * @param tags
     * @param jurisdiction
     * @param path
     */
    public void addPaintings(String id, String username, String title, String content, String tags, int jurisdiction, String path) {
        File file= new File(path);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);

        mModel.addPaintings(id,username,title,content,tags,jurisdiction,part)
                .compose(RxHttpResponseCompat.<PaintingsBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<PaintingsBean>() {
                    @Override
                    public void onNext(PaintingsBean paintingsBean) {
                        mView.addPaintingsSuccess(paintingsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.addPaintingsFailure();
                    }
                });

    }

    /**
     * 删除画作
     * @param id
     * @param position
     */
    public void deletePaintingsById(String id, final int position) {
        mModel.deletePaintingsById(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        mView.deletePaintingsByIdSuccess(position);
                    }
                });
    }

    /**
     * 根据日期，查询画作
     * @param userId
     * @param date
     */
    public void findPaintingsByCreateTime(String userId, String date) {

    }
}
