package com.juntai.wisdom.dgjxb.home_page.news.news_info.report;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.basecomponent.bean.ReportTypeBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/12/26 15:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/26 15:58
 */
public class ReportAdapter extends BaseQuickAdapter<ReportTypeBean.DataBean, BaseViewHolder> {
    public ReportAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportTypeBean.DataBean item) {
        helper.setText(R.id.report_item_type_tv, item.getName());
        if (item.isSelected()) {
            ((CardView) helper.getView(R.id.report_card)).setCardBackgroundColor(ContextCompat.getColor(mContext,
                    R.color.colorAccent));
            helper.setTextColor(R.id.report_item_type_tv, ContextCompat.getColor(mContext, R.color.white));
        } else {
            ((CardView) helper.getView(R.id.report_card)).setCardBackgroundColor(ContextCompat.getColor(mContext,
                    R.color.gray_light));
            helper.setTextColor(R.id.report_item_type_tv, ContextCompat.getColor(mContext, R.color.text_content));
        }

    }
}
