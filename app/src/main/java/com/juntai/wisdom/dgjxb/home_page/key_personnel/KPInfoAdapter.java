package com.juntai.wisdom.dgjxb.home_page.key_personnel;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.TextListBean;

import java.util.List;

/**
 * Describe:重点人员信息列表
 * Create by zhangzhenlong
 * 2020-7-3
 * email:954101549@qq.com
 */
public class KPInfoAdapter extends BaseQuickAdapter<TextListBean, BaseViewHolder> {

    public KPInfoAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextListBean item) {
        helper.setText(R.id.item_text_list_left,item.getLeft());
        helper.setText(R.id.item_text_list_right,item.getRight());
    }
}
