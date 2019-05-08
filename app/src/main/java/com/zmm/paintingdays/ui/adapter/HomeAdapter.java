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

    private OnPaintingsItemClickListener mOnPaintingsItemClickListener;

    public void setOnPaintingsItemClickListener(OnPaintingsItemClickListener onPaintingsItemClickListener) {
        mOnPaintingsItemClickListener = onPaintingsItemClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PaintingsBean item) {

        String createTime = item.getCreateTime();

        String textHistory = TimeUtils.getTimeFormatTextHistory(DateUtils.stringToLong(createTime,"yyyy-MM-dd HH:mm:ss"));

        helper.setText(R.id.tv_item_time,textHistory);

        helper.setText(R.id.tv_item_title,item.getTitle());


        ImageView pic = helper.getView(R.id.iv_item_pic);

        GlideUtils.loadImage(mContext,item.getPics(),pic);

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnPaintingsItemClickListener != null){
                    mOnPaintingsItemClickListener.OnPaintingsUpdateClick(item.getPics(),helper.getLayoutPosition());
                }
            }
        });

        pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(mOnPaintingsItemClickListener != null){
                    mOnPaintingsItemClickListener.OnPaintingsDeleteClick(item.getId(),helper.getLayoutPosition());
                }
                return true;
            }
        });

    }

    public interface OnPaintingsItemClickListener{

        void OnPaintingsUpdateClick(String pic,int position);

        void OnPaintingsDeleteClick(String id,int position);
    }
}
