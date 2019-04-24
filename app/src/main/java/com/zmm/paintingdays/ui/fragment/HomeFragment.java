package com.zmm.paintingdays.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.ui.adapter.HomeAdapter;
import com.zmm.paintingdays.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HomeFragment extends BaseFragment implements OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    private HomeAdapter mHomeAdapter;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }


    @Override
    protected void init() {

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

        mHomeAdapter = new HomeAdapter();

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHomeAdapter);

        //适配器，设置空布局
        mHomeAdapter.setEmptyView(R.layout.empty_content_home, mRvList);


        List<PaintingsBean> paintingsBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PaintingsBean paintingsBean = new PaintingsBean();
            paintingsBeanList.add(paintingsBean);
        }

        mHomeAdapter.setNewData(paintingsBeanList);
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
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        System.out.println("上拉刷新");
    }
}
