package com.zmm.paintingdays.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.dagger.component.DaggerPaintingsComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.PaintingsModule;
import com.zmm.paintingdays.mvp.presenter.PaintingsPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.ui.adapter.HomeAdapter;
import com.zmm.paintingdays.ui.widget.TitleBar;
import com.zmm.paintingdays.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HistoryFragment extends BaseFragment<PaintingsPresenter> implements OnRefreshLoadMoreListener, PaintingsContract.PaintingsView {


    @Inject
    HomeAdapter mHomeAdapter;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private int page = 0;
    private int size = 4;
    private String mUserId;


    @Override
    protected int setLayout() {
        return R.layout.fragment_history;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {
        DaggerPaintingsComponent.builder()
                .httpComponent(httpComponent)
                .paintingsModule(new PaintingsModule(this))
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

        mTitleBar.setTitle("今日画作");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.shangchuan) {
            @Override
            public void performAction(View view) {

//                mContext.startActivity(new Intent(mContext,DiaryInfoActivity.class));
            }
        });

    }


    private void initRefreshLayout() {

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }


    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHomeAdapter);

        //适配器，设置空布局
        mHomeAdapter.setEmptyView(R.layout.empty_content_home, mRvList);

        mPresenter.findAllPaintingsByUid(mUserId,page,size,true);
    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("HomeFragment onResume");

    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        System.out.println("HomeFragment onRefresh");


    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        System.out.println("加载更多");
        page++;
        mPresenter.findAllPaintingsByUid(mUserId,page,size,false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        System.out.println("上拉刷新");
        page = 0;
        mPresenter.findAllPaintingsByUid(mUserId,page,size,true);
    }

    @Override
    public void findAllPaintingsByUidOnRefresh(List<PaintingsBean> paintingsBeanList) {
        mRefreshLayout.finishRefresh();
        mHomeAdapter.setNewData(paintingsBeanList);
    }

    @Override
    public void findAllPaintingsByUidOnLoadMore(List<PaintingsBean> paintingsBeanList) {
        mRefreshLayout.finishLoadMore();

        if(paintingsBeanList == null){}
        mHomeAdapter.addData(paintingsBeanList);
    }

    @Override
    public void findAllPaintingsByUidFailure() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();

    }
}
