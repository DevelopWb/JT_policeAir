package com.juntai.wisdom.dgjxb.home_page.site_manager.site_info;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.site.EmployeeListBean;
import com.juntai.wisdom.dgjxb.utils.StringTools;

import java.util.List;

/**
 * Describe:单位检查记录适配器
 * Create by zhangzhenlong
 * 2020-7-4
 * email:954101549@qq.com
 */
public class ListDataAdapter extends BaseQuickAdapter<EmployeeListBean.DataBean.DatasBean, BaseViewHolder> {
    public ListDataAdapter(int layoutResId, @Nullable List<EmployeeListBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployeeListBean.DataBean.DatasBean item) {
        helper.setGone(R.id.item_image,false);
        helper.setText(R.id.item_title,item.getName());
        if (StringTools.isStringValueOk(item.getPhone())){
            helper.setText(R.id.item_time,item.getPhone());
        }else {
            helper.setText(R.id.item_time,item.getGmtCreate());
        }
    }
}
