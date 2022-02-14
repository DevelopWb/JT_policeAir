package com.juntai.wisdom.policeAir.home_page.baseInfo;

import android.view.Gravity;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.TextListBean;

import java.util.List;

/**
 * 文字列表 （左右）
 * @aouther Ma
 * @date 2019/3/17
 */
public class TextListAdapter extends BaseQuickAdapter<TextListBean, BaseViewHolder> {

    public TextListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextListBean item) {
        helper.setText(R.id.item_text_list_left,item.getLeft());
        helper.setText(R.id.item_text_list_right,item.getRight());
        TextView keyTv = helper.getView(R.id.item_text_list_left);
        TextView keyValue = helper.getView(R.id.item_text_list_right);
        if ("巡检描述".equals(item.getLeft())) {
            keyValue.setGravity(Gravity.LEFT);
            keyTv.setGravity(Gravity.TOP);
        }else {
            keyValue.setGravity(Gravity.RIGHT);
            keyTv.setGravity(Gravity.CENTER_VERTICAL);
        }

    }
}