package com.zmm.paintingdays.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.dagger.component.DaggerPaintingsComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.PaintingsModule;
import com.zmm.paintingdays.mvp.presenter.PaintingsPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public class PaintingsInfoActivity extends BaseActivity<PaintingsPresenter> implements PaintingsContract.PaintingsView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.iv_paintings_pic_select)
    ImageView mIvPaintingsPicSelect;
    @BindView(R.id.rb_jurisdiction_all)
    RadioButton mRbJurisdictionAll;
    @BindView(R.id.rb_jurisdiction_friend)
    RadioButton mRbJurisdictionFriend;
    @BindView(R.id.rb_jurisdiction_me)
    RadioButton mRbJurisdictionMe;
    @BindView(R.id.radio_group_spend)
    RadioGroup mRadioGroupSpend;
    @BindView(R.id.et_paintings_title)
    EditText mEtPaintingsTitle;
    @BindView(R.id.et_paintings_content)
    EditText mEtPaintingsContent;

    @Override
    protected int setLayout() {
        return R.layout.activity_paintings_info;
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

        initToolBar();

    }

    private void initToolBar() {
        mTitleBar.setTitle("上传画作");

    }

    @Override
    public void findAllPaintingsByUidOnRefresh(List<PaintingsBean> paintingsBeanList) {

    }

    @Override
    public void findAllPaintingsByUidOnLoadMore(List<PaintingsBean> paintingsBeanList) {

    }

    @Override
    public void findAllPaintingsByUidFailure() {

    }

    @Override
    public void addPaintings(PaintingsBean paintingsBean) {

    }

}
