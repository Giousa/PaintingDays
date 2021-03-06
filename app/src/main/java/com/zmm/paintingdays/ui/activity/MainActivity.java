package com.zmm.paintingdays.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.ui.fragment.BaseFragment;
import com.zmm.paintingdays.ui.fragment.FragmentFactory;
import com.zmm.paintingdays.utils.ToastUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.bbl)
    BottomBarLayout mBbl;


    private BaseFragment current_fragment;
    private long time = 0;


    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {

        startFragmentAdd(FragmentFactory.createFragment(0));


        mBbl.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int prePosition, int currentPosition) {

                startFragmentAdd(FragmentFactory.createFragment(currentPosition));

            }
        });
    }


    // fragment的切换
    private void startFragmentAdd(BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.frame_layout, fragment).commit();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment)
                        .add(R.id.frame_layout, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment)
                        .commit();
            }
            current_fragment = fragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                ToastUtils.SimpleToast( "再次点击，退出应用");
            } else {
                removeAllActivity();
            }
        }

        return true;
    }
}
