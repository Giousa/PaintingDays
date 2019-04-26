package com.zmm.paintingdays.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.PaintingsBean;
import com.zmm.paintingdays.utils.DateUtils;
import com.zmm.paintingdays.utils.GlideUtils;
import com.zmm.paintingdays.utils.TimeUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class HistoryAdapter extends BaseQuickAdapter<PaintingsBean,BaseViewHolder>{


    public HistoryAdapter(){
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PaintingsBean item) {

        String createTime = item.getCreateTime();

        String textHistory = TimeUtils.getTimeFormatTextHistory(DateUtils.stringToLong(createTime,"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_item_history_time,textHistory);


        helper.setText(R.id.tv_item_history_title,item.getTitle());


        ImageView pic = helper.getView(R.id.iv_item_history_pic);

        GlideUtils.loadImage(mContext,item.getPics(),pic);

    }
}
