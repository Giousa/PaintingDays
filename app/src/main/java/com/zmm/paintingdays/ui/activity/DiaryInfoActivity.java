package com.zmm.paintingdays.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.dagger.component.DaggerDiaryComponent;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.dagger.module.DiaryModule;
import com.zmm.paintingdays.mvp.presenter.DiaryPresenter;
import com.zmm.paintingdays.mvp.presenter.contract.DiaryContract;
import com.zmm.paintingdays.ui.widget.TitleBar;
import com.zmm.paintingdays.utils.DateUtils;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/4/26
 * Email:65489469@qq.com
 */
public class DiaryInfoActivity extends BaseActivity<DiaryPresenter> implements DiaryContract.DiaryView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_diary_title)
    EditText mEtDiaryTitle;
    @BindView(R.id.et_diary_content)
    EditText mEtDiaryContent;
    @BindView(R.id.tv_diary_time)
    TextView mTvDiaryTime;
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;


    private String mUserId;
    private String mId;
    private DiaryBean mDiaryBean;

    @Override
    protected int setLayout() {
        return R.layout.activity_diary_info;
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

        mTvDiaryTime.setText(DateUtils.longToString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));

        mUserId = UIUtils.getUserBean().getId();

        mId = getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(mId)){
            mPresenter.findDiaryById(mId);
        }

        initToolBar();
    }


    private void initToolBar() {
        mTitleBar.setTitle("写日记");
        mTitleBar.setLeftImageResource(R.drawable.icon_back);
        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @OnClick({R.id.iv_diary_time_select, R.id.btn_diary_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_diary_time_select:
                timeSelect();
                break;
            case R.id.btn_diary_submit:
                submit();
                break;
        }
    }

    private void timeSelect() {

    }

    private void submit() {

        String title = mEtDiaryTitle.getText().toString();
        if(!TextUtils.isEmpty(title)){
            title = title.trim();
        }
        String contnet = mEtDiaryContent.getText().toString();
        if(!TextUtils.isEmpty(contnet)){
            contnet = contnet.trim();
        }

        if(TextUtils.isEmpty(mId)){
            mPresenter.addDiary(mUserId,title,contnet,mTvDiaryTime.getText().toString());
        }else {
            if(mDiaryBean != null){
                mDiaryBean.setTitle(title);
                mDiaryBean.setContent(contnet);
                mDiaryBean.setCreateTime(mTvDiaryTime.getText().toString());
                mPresenter.updateDiary(mDiaryBean);
            }
        }

    }

    @Override
    public void findAllDiaryByUidOnRefresh(List<DiaryBean> diaryBeanList) {

    }

    @Override
    public void findAllDiaryByUidOnLoadMore(List<DiaryBean> diaryBeanList) {

    }

    @Override
    public void findAllDiaryByUidFailure() {

    }

    @Override
    public void addDiarySuccess(DiaryBean diaryBean) {

        if(TextUtils.isEmpty(mId)){
            ToastUtils.SimpleToast("日记添加成功");
        }else {
            ToastUtils.SimpleToast("日记修改成功");

        }
        setResult(2);
        finish();
    }

    @Override
    public void deleteSuccess(int position) {

    }

    @Override
    public void findDiaryByIdSuccess(DiaryBean diaryBean) {
        mDiaryBean = diaryBean;
        mEtDiaryTitle.setText(diaryBean.getTitle());
        mEtDiaryContent.setText(diaryBean.getContent());
        mTvDiaryTime.setText(DateUtils.dateToString(DateUtils.stringToDate(diaryBean.getCreateTime(),null),null));

    }

}
