package com.juntai.wisdom.policeAir.home_page.business.my_business;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.business.MyBusinessDetailBean;

/**
 * @Author: tobato
 * @Description: 作用描述  业务办理条目适配器
 * @CreateDate: 2020/5/20 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 9:47
 */
public class MyBusinessDetailAdapter extends BaseQuickAdapter<MyBusinessDetailBean.DataBean.PictureBean, BaseViewHolder> {
    public MyBusinessDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBusinessDetailBean.DataBean.PictureBean item) {
        helper.setText(R.id.info_name_tv,item.getMaterialsName());
        helper.setGone(R.id.info_img_old_iv,false);
        helper.addOnClickListener(R.id.info_img_new_iv);
        ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getPictureUrl(),helper.getView(R.id.info_img_new_iv));}
}
