package com.example.appbase.base.customview.selectPics;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * Author:wang_sir
 * Time:2018/7/19 10:52
 * Description:This is ShowSelectedPicsAdapter
 */
public class SelectedPicVideoAdapter extends BaseQuickAdapter<SelectPicVideoBean, BaseViewHolder> {


    private boolean isDetail;


    public void setDetail(boolean detail) {
        this.isDetail = detail;
    }

    public SelectedPicVideoAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, SelectPicVideoBean item) {
        if (SelectPicVideoBean.TYPE_NULL==item.getType()) {
            ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, (ImageView) helper.getView(R.id.select_pic_icon_iv));
            helper.setGone(R.id.delete_item_iv, false);
        } else {
            ImageLoadUtil.loadImageNoCache(mContext, item.getPath(), (ImageView) helper.getView(R.id.select_pic_icon_iv));
            if (!isDetail) {
                helper.setGone(R.id.delete_item_iv, true);
            } else {
                helper.setGone(R.id.delete_item_iv, false);
            }

            if (SelectPicVideoBean.TYPE_VIDEO==item.getType()) {
                helper.setGone(R.id.item_video_tag, true);
            } else {
                helper.setGone(R.id.item_video_tag, false);
            }
        }
        helper.addOnClickListener(R.id.select_pic_icon_iv);
        helper.addOnClickListener(R.id.delete_item_iv);
    }
}