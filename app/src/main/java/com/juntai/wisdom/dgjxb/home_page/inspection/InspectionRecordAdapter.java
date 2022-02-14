package com.juntai.wisdom.dgjxb.home_page.inspection;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionRecordBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

/**
 * 巡逻记录
 *
 * @aouther Ma
 * @date 2019/3/17
 */
public class InspectionRecordAdapter extends BaseQuickAdapter<InspectionRecordBean.DataBean.DatasBean, BaseViewHolder> {

    public InspectionRecordAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionRecordBean.DataBean.DatasBean item) {

        ImageView imageView = helper.getView(R.id.item_image);
        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), item.getHeadPortrait(),R.mipmap.default_user_head_icon,R.mipmap.default_user_head_icon,imageView);
        helper.setText(R.id.item_title, item.getNickname())
                .setText(R.id.item_time,   item.getPatrolTime());
    }


}