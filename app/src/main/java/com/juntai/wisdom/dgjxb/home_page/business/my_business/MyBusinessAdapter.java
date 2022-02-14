package com.juntai.wisdom.dgjxb.home_page.business.my_business;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.business.MyBusinessBean;

/**
 * Describe:任务上报列表
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class MyBusinessAdapter extends BaseQuickAdapter<MyBusinessBean.DataBean.DatasBean, BaseViewHolder> {

    public MyBusinessAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyBusinessBean.DataBean.DatasBean item) {
       helper.setImageResource(R.id.item_iv,getBusinessResId(item.getCategoryId()));
        helper.setText(R.id.item_name,item.getTitle());
        helper.setText(R.id.item_content,item.getGmtCreate());
        helper.setVisible(R.id.item_status,true);
        setStatus(helper.getView(R.id.item_status),item.getStatusX());
    }

    /**
     * 获取业务对应的id
     * @param typeId
     * @return
     */
    private int getBusinessResId(int typeId) {
        int resourceId = 0;
        switch (typeId) {
            case 1:
                resourceId = R.mipmap.business_hukou_by_present;
                break;
            case 2:
                resourceId = R.mipmap.business_die_unregist;
                break;
            case 3:
                resourceId = R.mipmap.business_join;
                break;
            case 4:
                resourceId = R.mipmap.business_house_move;
                break;
            case 5:
                resourceId = R.mipmap.business_hukou_by_house;
                break;
            case 6:
                resourceId = R.mipmap.business_hukou_out_by_prove;
                break;
            case 7:
                resourceId = R.mipmap.business_hukou_out_by_school;
                break;
            case 8:
                resourceId = R.mipmap.business_change_name;
                break;
            case 9:
                resourceId = R.mipmap.business_change_idcard;
                break;
            case 10:
                resourceId = R.mipmap.business_idcard_iterim;
                break;
            case 11:
                resourceId = R.mipmap.business_receive_live_card;
                break;
            default:
                resourceId = R.mipmap.business_more;
                break;

        }
        return resourceId;
    }
    //审批状态（0：审核中）（1：审核通过）（2：审核失败）
    public void setStatus(TextView textView, int status){
        switch (status){
            case 1:
                textView.setTextColor(mContext.getResources().getColor(R.color.success_color));
                textView.setText("审核通过");
                break;
            case 2:
                textView.setTextColor(mContext.getResources().getColor(R.color.fail_color));
                textView.setText("审核拒绝");
                break;
            default:
                textView.setTextColor(mContext.getResources().getColor(R.color.orange));
                textView.setText("审核中");
                break;
        }
    }
}
