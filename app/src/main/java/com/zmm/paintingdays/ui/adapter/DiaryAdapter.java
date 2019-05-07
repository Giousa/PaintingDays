package com.zmm.paintingdays.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.bean.DiaryBean;
import com.zmm.paintingdays.utils.DateUtils;
import com.zmm.paintingdays.utils.TimeUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/11/9
 * Email:65489469@qq.com
 */
public class DiaryAdapter extends BaseQuickAdapter<DiaryBean,BaseViewHolder>{


    public DiaryAdapter(){
        super(R.layout.item_diary);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiaryBean item) {
        String createTime = item.getCreateTime();
        String textHistory = TimeUtils.getTimeFormatTextHistory(DateUtils.stringToLong(createTime,"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_item_time,textHistory);
        helper.setText(R.id.tv_item_title,item.getTitle());
        helper.setText(R.id.tv_item_content,item.getContent());
    }
}
