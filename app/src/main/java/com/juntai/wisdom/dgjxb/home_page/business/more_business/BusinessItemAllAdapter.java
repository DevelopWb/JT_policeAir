package com.juntai.wisdom.dgjxb.home_page.business.more_business;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.business.BusinessListBean;

/**
 * @Author: tobato
 * @Description: 作用描述  业务办理条目适配器
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class BusinessItemAllAdapter extends BaseQuickAdapter<BusinessListBean.DataBean.DatasBean, BaseViewHolder> {
    public BusinessItemAllAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessListBean.DataBean.DatasBean item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.business_index_tv, String.valueOf(position+1));
        helper.setText(R.id.business_tv, item.getName());
    }
}
