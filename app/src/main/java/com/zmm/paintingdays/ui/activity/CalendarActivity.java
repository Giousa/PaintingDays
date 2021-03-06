package com.zmm.paintingdays.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.necer.ncalendar.calendar.NCalendar;
import com.necer.ncalendar.listener.OnCalendarChangedListener;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.IThumbViewInfo;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.dagger.component.DaggerPaintingsComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.PaintingsModule;
import com.zmm.paintingdays.mvp.presenter.PaintingsPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.ui.adapter.HomeAdapter;
import com.zmm.paintingdays.ui.widget.MyThumbViewInfo;
import com.zmm.paintingdays.utils.UIUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/5/8
 * Email:65489469@qq.com
 */
public class CalendarActivity extends BaseActivity<PaintingsPresenter> implements PaintingsContract.PaintingsView, OnCalendarChangedListener, HomeAdapter.OnPaintingsItemClickListener {

    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.n_calendar)
    NCalendar mNCalendar;

    @Inject
    HomeAdapter mHomeAdapter;


    private String mUserId;
    private DateTime mDateTime;

    private boolean isUpdate;

    @Override
    protected int setLayout() {
        return R.layout.activity_calendar;
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

        initRecyclerView();

        mNCalendar.setOnCalendarChangedListener(this);


    }

    private void initRecyclerView() {
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));

        //添加分割线
        mRvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRvList.setAdapter(mHomeAdapter);

        //适配器，设置空布局
        mHomeAdapter.setEmptyView(R.layout.empty_content, mRvList);

        mHomeAdapter.setOnPaintingsItemClickListener(this);


    }

    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if(isUpdate){
                    setResult(2);
                }
                finish();
                break;
            case R.id.tv_add:
                startActivityForResult(new Intent(mContext, PaintingsInfoActivity.class),1);
                break;
        }
    }

    //判断，Only添加成功后返回，则刷新界面，否则皆不刷新
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == 2){
            isUpdate = true;
            requestCheckedData();
        }
    }

    @Override
    public void findAllPaintingsByUidOnRefresh(List<PaintingsBean> paintingsBeanList) {
        mHomeAdapter.setNewData(paintingsBeanList);
    }

    @Override
    public void findAllPaintingsByUidOnLoadMore(List<PaintingsBean> paintingsBeanList) {

    }

    @Override
    public void findAllPaintingsByUidFailure() {

    }

    @Override
    public void addPaintingsSuccess(PaintingsBean paintingsBean) {

    }

    @Override
    public void addPaintingsFailure() {

    }

    @Override
    public void deletePaintingsByIdSuccess(int position) {

    }

    @Override
    public void onCalendarChanged(DateTime dateTime) {
        mDateTime = dateTime;

        mTvMonth.setText(dateTime.getMonthOfYear() + "月");
        mTvDate.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月" + dateTime.getDayOfMonth() + "日");


        requestCheckedData();
    }

    /**
     * 更新数据
     */
    private void requestCheckedData(){

        if (mUserId != null) {

            mPresenter.findPaintingsByCreateTime(mUserId, mDateTime.getYear() + "-" + mDateTime.getMonthOfYear() + "-" + mDateTime.getDayOfMonth());

        }
    }

    @Override
    public void OnPaintingsItemClick(String pic, int position) {
        List<IThumbViewInfo> iThumbViewInfoList = new ArrayList<>();
        List<PaintingsBean> data = mHomeAdapter.getData();
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
    public void OnPaintingsItemLongClick(String id, int position) {

    }
}
