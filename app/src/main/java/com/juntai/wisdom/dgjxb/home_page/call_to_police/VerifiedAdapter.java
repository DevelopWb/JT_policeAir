package com.juntai.wisdom.dgjxb.home_page.call_to_police;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.VerifiedInfoBean;
import com.juntai.wisdom.dgjxb.bean.business.BusinessNeedInfoBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 认证
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class VerifiedAdapter extends BaseQuickAdapter<VerifiedInfoBean, BaseViewHolder> {
    public VerifiedAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VerifiedInfoBean item) {
        helper.setText(R.id.info_name_tv, item.getTitle());
        helper.addOnClickListener(R.id.info_img_new_iv);
        helper.addOnClickListener(R.id.info_img_old_iv);
        ImageLoadUtil.loadImage(mContext.getApplicationContext(),item.getSampleRes(), helper.getView(R.id.info_img_old_iv));
        ImageLoadUtil.loadImage(mContext.getApplicationContext(),item.getRealPicPath(),
                R.mipmap.add_icons,R.mipmap.add_icons, helper.getView(R.id.info_img_new_iv));
    }
}
