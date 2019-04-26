package com.zmm.paintingdays.mvp.presenter;

import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.rx.RxHttpResponseCompat;
import com.zmm.paintingdays.rx.subscriber.ErrorHandlerSubscriber;

import java.util.List;

import javax.inject.Inject;

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
                });
    }
}
