package com.juntai.wisdom.dgjxb.home_page.site_manager.site_add;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.TextListBean;

import java.util.List;

/**
 * @description 添加场所图片列表适配器
 * @aouther ZhangZhenlong
 * @date 2020-7-11
 */
public class AddPicsAdapter extends BaseQuickAdapter<TextListBean, BaseViewHolder> {

    public AddPicsAdapter(int layoutResId, @Nullable List<TextListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextListBean item) {
        if ("".equals(item.getRight())) {
            helper.setGone(R.id.delete_pushed_news_iv,false);
            helper.setImageResource(R.id.select_pic_icon_iv, R.mipmap.add_icons);
        } else {
            helper.setGone(R.id.delete_pushed_news_iv,true);
            ImageLoadUtil.loadImage(mContext, item.getRight(), helper.getView(R.id.select_pic_icon_iv));
        }
        helper.addOnClickListener(R.id.select_pic_icon_iv);
        helper.addOnClickListener(R.id.delete_pushed_news_iv);
        helper.setText(R.id.select_pic_des_tv, item.getLeft());
    }
}
