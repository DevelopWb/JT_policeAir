package com.juntai.wisdom.policeAir.home_page.conciliation.conciliation_list;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.conciliation.ConciliationListBean;

import java.util.List;

/**
 * Describe:调解列表
 * Create by zhangzhenlong
 * 2020-5-21
 * email:954101549@qq.com
 */
public class ConciliationListAdapter extends BaseQuickAdapter<ConciliationListBean.DataBean.ConciliationItemBean, BaseViewHolder> {


    public ConciliationListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConciliationListBean.DataBean.ConciliationItemBean item) {
        helper.setText(R.id.item_name, item.getIntroduction());
        helper.setText(R.id.item_content, item.getGmtCreate());
        helper.setVisible(R.id.item_status,true);
//        helper.setGone(R.id.item_edit_btn,false);
        setStatus(helper, item.getType());
//        helper.addOnClickListener(R.id.item_edit_btn);
//        helper.addOnClickListener(R.id.item_agreement_btn);
    }
    //（0：未受理；1：已受理；2：二次调解；3：已完结；4：完结转为案件；5：审核失败）
    public void setStatus(BaseViewHolder helper, int status) {
        switch (status) {
            case 0:
                helper.setTextColor(R.id.item_status, mContext.getResources().getColor(R.color.text_content));
                helper.setText(R.id.item_status,"审核中");
                break;
            case 1:
                helper.setTextColor(R.id.item_status, mContext.getResources().getColor(R.color.text_content));
                helper.setText(R.id.item_status,"审核通过");
                break;
            case 2:
                helper.setTextColor(R.id.item_status,mContext.getResources().getColor(R.color.text_content));
                helper.setText(R.id.item_status,"二次调解");
                break;
            case 3:
                helper.setTextColor(R.id.item_status,mContext.getResources().getColor(R.color.success_color));
                helper.setText(R.id.item_status,"完成调解");
                break;
            case 4:
                helper.setTextColor(R.id.item_status,mContext.getResources().getColor(R.color.text_content));
                helper.setText(R.id.item_status,"完结转为案件");
                break;
            case 5:
                helper.setTextColor(R.id.item_status,mContext.getResources().getColor(R.color.orange));
                helper.setText(R.id.item_status,"审核失败");
//                helper.setVisible(R.id.item_edit_btn,true);
                break;
            default:
                break;
        }
    }
}
