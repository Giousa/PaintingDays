package com.zmm.paintingdays.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.dagger.component.DaggerPaintingsComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.PaintingsModule;
import com.zmm.paintingdays.mvp.presenter.PaintingsPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.PaintingsContract;
import com.zmm.paintingdays.ui.widget.CustomePopup;
import com.zmm.paintingdays.ui.widget.TitleBar;
import com.zmm.paintingdays.utils.GlideUtils;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.radio_group_jurisdiction)
    RadioGroup mRadioGroupJurisdiction;
    @BindView(R.id.rb_jurisdiction_all)
    RadioButton mRbJurisdictionAll;
    @BindView(R.id.rb_jurisdiction_friend)
    RadioButton mRbJurisdictionFriend;
    @BindView(R.id.rb_jurisdiction_me)
    RadioButton mRbJurisdictionMe;
    @BindView(R.id.et_paintings_title)
    EditText mEtPaintingsTitle;
    @BindView(R.id.et_paintings_content)
    EditText mEtPaintingsContent;
    @BindView(R.id.btn_paintings_submit)
    Button mBtnPaintingsSubmit;
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;


    private ArrayList<ImageItem> mImages;

    //(0:自己可见 1：好友可见 2：全部可见)
    private int mJurisdiction = 0;

    private List<String> mPersonalList = Arrays.asList("天气", "早餐", "午餐", "晚餐", "零食", "打发时间", "回家", "游戏", "聚餐", "购物", "逛街", "锻炼", "程序", "宠物", "旅游", "拍摄", "保密");


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

        initJurisdiction();
    }


    private void initToolBar() {
        mTitleBar.setTitle("上传画作");
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initJurisdiction() {
        mRadioGroupJurisdiction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.rb_jurisdiction_all:
                        mJurisdiction = 2;
                        break;

                    case R.id.rb_jurisdiction_friend:
                        mJurisdiction = 1;
                        break;

                    case R.id.rb_jurisdiction_me:
                        mJurisdiction = 0;
                        break;
                }

            }
        });

    }

    @OnClick({R.id.iv_paintings_pic_select, R.id.btn_paintings_submit, R.id.iv_jurisdiction_popup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_paintings_pic_select:
                ImagePicker.getInstance().setMultiMode(false);
                ImagePicker.getInstance().setCrop(true);
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_paintings_submit:

                UserBean userBean = UIUtils.getUserBean();
                if (userBean != null) {

                    if (mImages != null && mImages.size() > 0) {


                    } else {
                        ToastUtils.SimpleToast("请选择图片");
                    }

                } else {
                    ToastUtils.SimpleToast("请登录账户");
                }

                break;

            case R.id.iv_jurisdiction_popup:
                CustomePopup diaryTitlePopup = CustomePopup.create(mPersonalList)
                        .setContext(this)
                        .apply();

                diaryTitlePopup.showAtLocation(mLlRoot, Gravity.BOTTOM, 0, 0);
                diaryTitlePopup.setOnPopupClickListener(new CustomePopup.OnPopupClickListener() {
                    @Override
                    public void OnPopupClick(String title) {
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {

                mImages = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (mImages != null && mImages.size() > 0) {

                    System.out.println("选择图片：" + mImages.get(0).path);
                    GlideUtils.loadImage(mContext, mImages.get(0).path, mIvPaintingsPicSelect);
                }

            } else {
                System.out.println("没有数据");
            }
        }
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
