package com.juntai.wisdom.policeAir.home_page.business.business_common;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.MyMenuBean;

/**
 * @Author: tobato
 * @Description: 作用描述  业务办理条目适配器
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class BusinessItemAdapter extends BaseQuickAdapter<MyMenuBean, BaseViewHolder> {
    public BusinessItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMenuBean item) {
        helper.setText(R.id.business_tv, item.getName());
        helper.setImageResource(R.id.business_iv,item.getImageId());
    }
}
