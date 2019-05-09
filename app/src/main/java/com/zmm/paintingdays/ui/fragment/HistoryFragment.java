package com.zmm.paintingdays.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.IThumbViewInfo;
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
import com.zmm.paintingdays.ui.activity.CalendarActivity;
import com.zmm.paintingdays.ui.adapter.HistoryAdapter;
import com.zmm.paintingdays.ui.dialog.SimpleConfirmDialog;
import com.zmm.paintingdays.ui.widget.MyThumbViewInfo;
import com.zmm.paintingdays.ui.widget.TitleBar;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/8
 * Email:65489469@qq.com
 */
public class HistoryFragment extends BaseFragment<PaintingsPresenter> implements OnRefreshLoadMoreListener, PaintingsContract.PaintingsView, HistoryAdapter.OnPaintingsItemClickListener {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_Layout)
    SmartRefreshLayout mRefreshLayout;

    @Inject
    HistoryAdapter mHistoryAdapter;

    private int page = 0;
    private int size = 10;
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

        mTitleBar.setTitle("作品集");
        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.icon_calendar) {
            @Override
            public void performAction(View view) {

//                mContext.startActivity(new Intent(mContext,CalendarActivity.class));
                startActivityForResult(new Intent(mContext, CalendarActivity.class),1);

            }
        });

    }

    //判断，Only添加成功后返回，则刷新界面，否则皆不刷新
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 2){
            page = 0;
            mPresenter.findAllPaintingsByUid(mUserId,page,size,true);

        }
    }


    private void initRefreshLayout() {

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }


    private void initRecyclerView() {

        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new GridLayoutManager(mContext, 2));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHistoryAdapter);

        //适配器，设置空布局
        mHistoryAdapter.setEmptyView(R.layout.empty_content_home, mRvList);

        mHistoryAdapter.setOnPaintingsItemClickListener(this);

        mPresenter.findAllPaintingsByUid(mUserId,page,size,true);
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        System.out.println("加载更多");
//        page++;
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
        mHistoryAdapter.setNewData(paintingsBeanList);
        if(paintingsBeanList != null && paintingsBeanList.size() > 0){
            page = 1;
        }
    }

    @Override
    public void findAllPaintingsByUidOnLoadMore(List<PaintingsBean> paintingsBeanList) {
        mRefreshLayout.finishLoadMore();

        if(paintingsBeanList != null && paintingsBeanList.size() > 0){
            page++;
            mHistoryAdapter.addData(paintingsBeanList);
        }
    }

    @Override
    public void findAllPaintingsByUidFailure() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();

    }

    @Override
    public void addPaintingsSuccess(PaintingsBean paintingsBean) {

    }

    @Override
    public void addPaintingsFailure() {

    }

    @Override
    public void deletePaintingsByIdSuccess(int position) {
        ToastUtils.SimpleToast("画作删除成功");
        mHistoryAdapter.remove(position);
    }


    @Override
    public void OnPaintingsItemClick(String pic,int position) {
        List<IThumbViewInfo> iThumbViewInfoList = new ArrayList<>();
        List<PaintingsBean> data = mHistoryAdapter.getData();
        for (PaintingsBean paintingsBean:data) {
            iThumbViewInfoList.add(new MyThumbViewInfo(paintingsBean.getPics()));
        }

        GPreviewBuilder.from(this)
                .setData(iThumbViewInfoList)
                .setCurrentIndex(position)
                .setDrag(true,0.6f)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .setFullscreen(false)
                .start();

    }

    @Override
    public void OnPaintingsItemLongClick(final String id, final int position) {
        final SimpleConfirmDialog simpleConfirmDialog = new SimpleConfirmDialog(mContext,"是否删除此图片？");

        simpleConfirmDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleConfirmDialog.dismiss();
            }

            @Override
            public void onConfirm() {
                simpleConfirmDialog.dismiss();
                mPresenter.deletePaintingsById(id,position);
            }
        });

        simpleConfirmDialog.show();

    }

}
