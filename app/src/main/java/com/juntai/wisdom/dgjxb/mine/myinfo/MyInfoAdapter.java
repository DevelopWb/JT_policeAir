package com.juntai.wisdom.dgjxb.mine.myinfo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.TextListBean;

import java.util.List;

/**
 * 我的信息适配器
 * @aouther Ma
 * @date 2019/3/17
 */
public class MyInfoAdapter extends BaseQuickAdapter<TextListBean, BaseViewHolder> {

    public MyInfoAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextListBean item) {
        helper.setText(R.id.item_myinfo_name, item.getLeft());
        helper.setText(R.id.item_myinfo_value, item.getRight());

    }
}