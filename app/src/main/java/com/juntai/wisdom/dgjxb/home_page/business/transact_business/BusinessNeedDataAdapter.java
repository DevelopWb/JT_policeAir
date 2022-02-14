package com.juntai.wisdom.dgjxb.home_page.business.transact_business;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.business.BusinessNeedInfoBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 作用描述  业务办理条目适配器
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class BusinessNeedDataAdapter extends BaseQuickAdapter<BusinessNeedInfoBean.DataBean, BaseViewHolder> {
    public BusinessNeedDataAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessNeedInfoBean.DataBean item) {
        helper.setText(R.id.info_name_tv,item.getName());
        helper.addOnClickListener(R.id.info_img_new_iv);
        helper.addOnClickListener(R.id.info_img_old_iv);
        ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getExamplePicture(),helper.getView(R.id.info_img_old_iv));}
}
