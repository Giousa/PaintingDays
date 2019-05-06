package com.zmm.paintingdays.ui.widget;

import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zmm.paintingdays.R;
import com.zmm.paintingdays.utils.ToastUtils;
import com.zmm.paintingdays.utils.UIUtils;
import com.zyyoona7.popup.BasePopup;

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

    private String mSelectVals;

    public void setOnPopupClickListener(OnPopupClickListener onPopupClickListener) {
        mOnPopupClickListener = onPopupClickListener;
    }

    public static CustomePopup create(List<String> stringList){

        mStrings = stringList;
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

        mFlowLayout.setAdapter(new TagAdapter<String>(mStrings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });


        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                ToastUtils.SimpleToast(mStrings.get(position));
                return true;
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
        {
            @Override
            public void onSelected(Set<Integer> selectPosSet)
            {
                mSelectVals = selectPosSet.toString();
                ToastUtils.SimpleToast(selectPosSet.toString());
            }
        });
    }

    @Override
    protected void onPopupWindowDismiss() {
        super.onPopupWindowDismiss();
        System.out.println("隐藏后，显示选中的标签："+mSelectVals);
    }

    public interface OnPopupClickListener{
        void OnPopupClick(String title);
    }
}
