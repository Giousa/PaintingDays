package com.zmm.paintingdays.mvp.presenter.contract;

import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public interface DiaryContract {


    interface IDiaryModel{

        Observable<BaseBean<DiaryBean>> addDiary(String uId, String title, String content, String createTime);

        Observable<BaseBean<DiaryBean>> findDiaryById(String id);

        Observable<BaseBean<DiaryBean>> updateDiary(DiaryBean diaryBean);

        Observable<BaseBean<List<DiaryBean>>> findAllDiaryByUid(String uId, int page, int size);

        Observable<BaseBean<String>> deleteDiary(String id);



    }

    interface DiaryView extends BaseView{

        void findAllDiaryByUidOnRefresh(List<DiaryBean> diaryBeanList);

        void findAllDiaryByUidOnLoadMore(List<DiaryBean> diaryBeanList);

        void findAllDiaryByUidFailure();

        void addDiarySuccess(DiaryBean diaryBean);

        void deleteSuccess(int position);

        void findDiaryByIdSuccess(DiaryBean diaryBean);

    }
}
