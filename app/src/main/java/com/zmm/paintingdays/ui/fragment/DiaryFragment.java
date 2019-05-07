package com.zmm.paintingdays.ui.fragment;


import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.ui.widget.TitleBar;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class DiaryFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    @Override
    protected int setLayout() {
        return R.layout.fragment_diary;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }


    @Override
    protected void init() {
        initToolBar();
    }


    private void initToolBar() {

        mTitleBar.setTitle("日记");
    }
}
