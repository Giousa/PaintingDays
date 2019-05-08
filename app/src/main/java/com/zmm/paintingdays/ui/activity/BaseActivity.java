package com.zmm.paintingdays.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zmm.paintingdays.MyApplication;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/3
 * Email:65489469@qq.com
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder mUnbinder;
    protected MyApplication mMyApplication;

    private BaseActivity mBaseActivity;

    public int mScreenWidth;

    public Context mContext;


    @Inject
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

        super.onCreate(savedInstanceState);

        setContentView(setLayout());

        mContext = this;

        mUnbinder = ButterKnife.bind(this);

        mScreenWidth = getScreenWidth();

        mBaseActivity = this;

        mMyApplication = (MyApplication) getApplication();

        addActivity();

        setupActivityComponent(mMyApplication.getHttpComponent());

        init();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    protected abstract int setLayout();

    protected abstract void setupActivityComponent(HttpComponent httpComponent);

    protected abstract void init();


    protected void startActivity(Class activity) {

        startActivity(activity, false);
    }

    protected void startActivity(Class activity, boolean flag) {

        Intent intent = new Intent(this, activity);
        startActivity(intent);

        if (flag) {
            finish();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }


    public void addActivity() {
        mMyApplication.addActivity_(mBaseActivity);
    }

    public void removeActivity() {
        mMyApplication.removeActivity_(mBaseActivity);
    }

    public void removeAllActivity() {
        mMyApplication.removeAllActivity_();
    }

    public void removeAllOtherActivity(BaseActivity baseActivity) {
        mMyApplication.removeAllOtherActivity_(baseActivity);
    }


    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    /**
     * 让屏幕变暗
     */
    protected void makeWindowDark() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }

    /**
     * 让屏幕变亮
     */
    protected void makeWindowLight() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }


    protected void hideKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive(editText)) {
            editText.requestFocus();
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void showKeyboard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            editText.requestFocus();
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

        }
    }

    /**
     * 解决全屏切换非全屏闪烁问题
     */
    private void smoothSwitchScreen() {
        // 5.0以上修复了此bug
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup rootView = ((ViewGroup) this.findViewById(android.R.id.content));
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            rootView.setPadding(0, statusBarHeight, 0, 0);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }
}