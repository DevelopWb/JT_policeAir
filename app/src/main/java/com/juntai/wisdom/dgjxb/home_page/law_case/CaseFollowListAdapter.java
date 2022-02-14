package com.juntai.wisdom.dgjxb.home_page.law_case;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.case_bean.CaseInfoBean;

import java.util.List;

/**
 * 案件详情 - 追踪列表   - 适配器
 *
 * @aouther Ma
 * @date 2019/3/17
 */
public class CaseFollowListAdapter extends BaseQuickAdapter<CaseInfoBean.DataBean.CaseChildListBean, BaseViewHolder> {
    public CaseFollowListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseInfoBean.DataBean.CaseChildListBean item) {

        ImageView imageView = helper.getView(R.id.item_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoadUtil.loadImageCache(mContext,item.getPhotoOne(),imageView);
        helper.setText(R.id.item_title, item.getAddress()+ " (" + (helper.getAdapterPosition()) + ")")
                .setText(R.id.item_content,"跟踪记录: " +  item.getCaseContent())
                .setText(R.id.item_time,  "跟踪时间: " + item.getHappenDate());
        TextView tagTv = helper.getView(R.id.item_right);
        tagTv.setVisibility(View.VISIBLE);
        tagTv.setWidth(DisplayUtil.dp2px(mContext,20));
        tagTv.setHeight(DisplayUtil.dp2px(mContext,20));
        helper.setBackgroundRes(R.id.item_right,R.mipmap.arrow_right);
    }
}