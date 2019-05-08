package com.zmm.paintingdays.ui.activity;

import android.content.Intent;

import com.previewlibrary.GPreviewBuilder;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.config.CommonConfig;
import com.zmm.paintingdays.dagger.component.HttpComponent;
import com.zmm.paintingdays.ui.widget.MyThumbViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 图片预览界面
 * Author:zhangmengmeng
 * Date:2019/1/7
 * Email:65489469@qq.com
 */
public class PreviewActivity extends BaseActivity{
    @Override
    protected int setLayout() {
        return R.layout.activity_preview;
    }

    @Override
    protected void setupActivityComponent(HttpComponent httpComponent) {

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        String[] pics = intent.getStringArrayExtra("pics");

        showPreviewPics(position,pics);
    }

    /**
     * 展示预览图片
     * @param position
     * @param pics
     */
    private void showPreviewPics(int position, String[] pics) {
        List<MyThumbViewInfo> myThumbViewInfos = new ArrayList<>();
        for (String pic : pics) {
            myThumbViewInfos.add(new MyThumbViewInfo(pic));
        }

        GPreviewBuilder.from(this)
                .setData(myThumbViewInfos)
                .setCurrentIndex(position)
                .setDrag(true,0.6f)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .start();
    }
}
