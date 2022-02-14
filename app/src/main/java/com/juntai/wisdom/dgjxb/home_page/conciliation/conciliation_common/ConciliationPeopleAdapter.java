package com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation_common;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationPeopleBean;

import java.util.List;

/**
 * Describe:调解人员
 * Create by zhangzhenlong
 * 2020-5-23
 * email:954101549@qq.com
 */
public class ConciliationPeopleAdapter extends BaseQuickAdapter<ConciliationPeopleBean, BaseViewHolder> {

    public ConciliationPeopleAdapter(int layoutResId, @Nullable List<ConciliationPeopleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConciliationPeopleBean item) {
        helper.setText(R.id.item_type, item.getType());
        helper.setText(R.id.item_name, item.getName());
        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), item.getPicUrl(), R.mipmap.my_hint_head, R.mipmap.my_hint_head, helper.getView(R.id.item_iv));
    }
}
