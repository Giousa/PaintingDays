package com.zmm.paintingdays.ui.activity;

import android.graphics.PointF;
import android.text.TextUtils;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.utils.SharedPreferencesUtil;
import com.zmm.paintingdays.config.CommonConfig;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/12
 * Time:下午4:15
 */

public class SplashActivity extends BaseActivity {



    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {


        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                String userJson = SharedPreferencesUtil.getString(CommonConfig.LOGIN_USER, null);

                if (TextUtils.isEmpty(userJson)) {
                    startActivity(LoginActivity.class,true);
                } else {
                    startActivity(MainActivity.class,true);
                }

            }
        });


    }
}
