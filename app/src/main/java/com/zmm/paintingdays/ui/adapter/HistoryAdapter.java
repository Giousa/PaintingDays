package com.zmm.paintingdays.ui.adapter;

import android.view.View;
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

    private HomeAdapter.OnPaintingsItemClickListener mOnPaintingsItemClickListener;

    public void setOnPaintingsItemClickListener(HomeAdapter.OnPaintingsItemClickListener onPaintingsItemClickListener) {
        mOnPaintingsItemClickListener = onPaintingsItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final PaintingsBean item) {

        String createTime = item.getCreateTime();

        String textHistory = TimeUtils.getTimeFormatTextHistory(DateUtils.stringToLong(createTime,"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_item_history_time,textHistory);


        helper.setText(R.id.tv_item_history_title,item.getTitle());


        ImageView pic = helper.getView(R.id.iv_item_history_pic);

        GlideUtils.loadImage(mContext,item.getPics(),pic);

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnPaintingsItemClickListener != null){
                    mOnPaintingsItemClickListener.OnPaintingsPicClick(item.getPics());
                }
            }
        });

        pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(mOnPaintingsItemClickListener != null){
                    mOnPaintingsItemClickListener.OnPaintingsDelete(item.getId());
                }
                return true;
            }
        });

    }

    public interface OnPaintingsItemClickListener{

        void OnPaintingsPicClick(String pic);

        void OnPaintingsDelete(String id);
    }
}
