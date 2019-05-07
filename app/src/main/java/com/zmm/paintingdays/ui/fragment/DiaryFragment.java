package com.zmm.paintingdays.ui.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.dagger.component.DaggerDiaryComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.DiaryModule;
import com.zmm.paintingdays.mvp.presenter.DiaryPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.DiaryContract;
import com.zmm.paintingdays.ui.activity.DiaryInfoActivity;
import com.zmm.paintingdays.ui.activity.PaintingsInfoActivity;
import com.zmm.paintingdays.ui.adapter.DiaryAdapter;
import com.zmm.paintingdays.ui.widget.TitleBar;
import com.zmm.paintingdays.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class DiaryFragment extends BaseFragment<DiaryPresenter> implements DiaryContract.DiaryView, OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    DiaryAdapter mDiaryAdapter;


    private int page = 0;
    private int size = 4;
    private String mUserId;


    @Override
    protected int setLayout() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerDiaryComponent.builder()
                .httpComponent(httpComponent)
                .diaryModule(new DiaryModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void init() {
        UserBean userBean = UIUtils.getUserBean();
        if(userBean != null){

            mUserId = userBean.getId();
        }

        initToolBar();

        initRefreshLayout();

        initRecyclerView();
    }


    private void initToolBar() {

        mTitleBar.setTitle("日记");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
            @Override
            public void performAction(View view) {

                startActivityForResult(new Intent(mContext, DiaryInfoActivity.class),1);

            }
        });
    }

    private void initRefreshLayout() {

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        page++;
        mPresenter.findAllDiaryByUid(mUserId,page,size,false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mPresenter.findAllDiaryByUid(mUserId,page,size,true);
    }


    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mDiaryAdapter);

        //适配器，设置空布局
        mDiaryAdapter.setEmptyView(R.layout.empty_content, mRvList);

        mPresenter.findAllDiaryByUid(mUserId,page,size,true);
    }

    //判断，Only添加成功后返回，则刷新界面，否则皆不刷新
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 2){
            page = 0;
            mPresenter.findAllDiaryByUid(mUserId,page,size,true);

        }
    }

    @Override
    public void findAllDiaryByUidOnRefresh(List<DiaryBean> diaryBeanList) {
        mRefreshLayout.finishRefresh();
        mDiaryAdapter.setNewData(diaryBeanList);
        if(diaryBeanList != null && diaryBeanList.size() > 0){
            page = 1;
        }
    }

    @Override
    public void findAllDiaryByUidOnLoadMore(List<DiaryBean> diaryBeanList) {
        mRefreshLayout.finishLoadMore();

        if(diaryBeanList != null && diaryBeanList.size() > 0){
            page++;
            mDiaryAdapter.addData(diaryBeanList);
        }
    }

    @Override
    public void findAllDiaryByUidFailure() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void addDiarySuccess(DiaryBean diaryBean) {

    }


}
