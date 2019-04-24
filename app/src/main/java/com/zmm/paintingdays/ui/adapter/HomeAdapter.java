package com.zmm.paintingdays.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.config.CommonConfig;
import com.zmm.paintingdays.utils.UIUtils;
import com.zmm.paintingdays.utils.VerificationUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class HomeAdapter extends BaseQuickAdapter<PaintingsBean,BaseViewHolder>{


    public HomeAdapter(){
        super(R.layout.item_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PaintingsBean item) {


    }
}
