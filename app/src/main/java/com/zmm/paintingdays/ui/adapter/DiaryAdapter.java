package com.zmm.paintingdays.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
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

    private OnRightMenuClickListener mOnRightMenuClickListener;

    public void setOnRightMenuClickListener(OnRightMenuClickListener onRightMenuClickListener) {
        mOnRightMenuClickListener = onRightMenuClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DiaryBean item) {
        String createTime = item.getCreateTime();
        String textHistory = TimeUtils.getTimeFormatTextHistory(DateUtils.stringToLong(createTime,"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_item_time,textHistory);
        helper.setText(R.id.tv_item_title,item.getTitle());
        helper.setText(R.id.tv_item_content,item.getContent());

        final EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);

        helper.getView(R.id.tv_right_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easySwipeMenuLayout.resetStatus();
                if(mOnRightMenuClickListener != null){
                    mOnRightMenuClickListener.onRightMenuDelete(item.getId(),helper.getLayoutPosition());
                }
            }
        });

        helper.getView(R.id.tv_right_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easySwipeMenuLayout.resetStatus();
                if(mOnRightMenuClickListener != null){
                    mOnRightMenuClickListener.onRightMenuUpdate(item.getId());
                }
            }
        });
    }

    public interface OnRightMenuClickListener{

        void onRightMenuDelete(String id,int position);

        void onRightMenuUpdate(String id);
    }
}
