package com.zmm.paintingdays.ui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.utils.UIUtils;
import com.zyyoona7.popup.BasePopup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2019/5/6
 * Email:65489469@qq.com
 */
public class CustomePopup extends BasePopup<CustomePopup> {

    private static List<String> mStrings;
    private OnPopupClickListener mOnPopupClickListener;
    private TagFlowLayout mFlowLayout;

    private static Set<Integer> mSelectPosSet = new HashSet<>();

    public void setOnPopupClickListener(OnPopupClickListener onPopupClickListener) {
        mOnPopupClickListener = onPopupClickListener;
    }

    public static CustomePopup create(List<String> stringList,Set<Integer> selectPosSet){

        mStrings = stringList;
        mSelectPosSet = selectPosSet;
        return new CustomePopup();
    }


    @Override
    protected void initAttributes() {
        setContentView(R.layout.item_custome_popup_view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view, CustomePopup customePopup) {

        final LayoutInflater mInflater = LayoutInflater.from(UIUtils.getContext());

        mFlowLayout = findViewById(R.id.id_flowlayout);

        TagAdapter<String> tagAdapter = new TagAdapter<String>(mStrings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };

        if(mSelectPosSet != null && mSelectPosSet.size() > 0){
            tagAdapter.setSelectedList(mSelectPosSet);
        }
        mFlowLayout.setAdapter(tagAdapter);

    }

    @Override
    protected void onPopupWindowDismiss() {
        super.onPopupWindowDismiss();

        mSelectPosSet = mFlowLayout.getSelectedList();

        if(mSelectPosSet != null && mSelectPosSet.size() > 0){

            StringBuffer sb = new StringBuffer();
            for (Integer pot:mSelectPosSet) {
                sb.append(mStrings.get(pot)+",");
            }
            sb.deleteCharAt(sb.length() - 1);

            if(mOnPopupClickListener != null){
                mOnPopupClickListener.OnPopupClick(sb.toString(),mSelectPosSet);
            }
        }


    }

    public interface OnPopupClickListener{
        void OnPopupClick(String tags,Set<Integer> selectPosSet);
    }
}
