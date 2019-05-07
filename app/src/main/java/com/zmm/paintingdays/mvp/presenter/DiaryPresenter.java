package com.zmm.paintingdays.mvp.presenter;

import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.mvp.presenter.contract.DiaryContract;
import com.zmm.paintingdays.rx.RxHttpResponseCompat;
import com.zmm.paintingdays.rx.subscriber.ErrorHandlerSubscriber;
import com.zmm.paintingdays.utils.DateUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public class DiaryPresenter extends BasePresenter<DiaryContract.IDiaryModel,DiaryContract.DiaryView>{


    @Inject
    public DiaryPresenter(DiaryContract.IDiaryModel model, DiaryContract.DiaryView view) {
        super(model, view);
    }

    /**
     * 查询所有日迹
     * @param userId
     * @param page
     * @param size
     * @param isRefresh
     */
    public void findAllDiaryByUid(String userId, int page, int size, final boolean isRefresh) {
        mModel.findAllDiaryByUid(userId,page,size)
                .compose(RxHttpResponseCompat.<List<DiaryBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<DiaryBean>>() {
                    @Override
                    public void onNext(List<DiaryBean> diaryBeanList) {
                        if(isRefresh){
                            mView.findAllDiaryByUidOnRefresh(diaryBeanList);
                        }else {
                            mView.findAllDiaryByUidOnLoadMore(diaryBeanList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.findAllDiaryByUidFailure();
                    }
                });
    }

    /**
     * 添加日记
     * @param userId
     * @param title
     * @param contnet
     * @param createTime
     */
    public void addDiary(String userId, String title, String contnet, String createTime) {

        mModel.addDiary(userId,title,contnet, createTime)
                .compose(RxHttpResponseCompat.<DiaryBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<DiaryBean>() {
                    @Override
                    public void onNext(DiaryBean diaryBean) {
                        mView.addDiarySuccess(diaryBean);
                    }
                });
    }
}
