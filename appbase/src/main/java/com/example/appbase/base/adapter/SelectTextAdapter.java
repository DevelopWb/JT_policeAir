package com.example.appbase.base.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.example.appbase.bean.SelectTextBean;
import com.example.appbase.bean.nong_fa_manager.SchoolListBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class SelectTextAdapter extends BaseQuickAdapter<SelectTextBean, BaseViewHolder> {
    public SelectTextAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectTextBean item) {
        switch (item.getKey()) {
            case SelectTextBean.SCHOOL_KEY:
                SchoolListBean.DataBean schoolListBean = (SchoolListBean.DataBean) item.getObject();
                helper.setText(R.id.single_text_tv, schoolListBean.getName());

                break;
            default:
                break;
        }
        if (helper.getAdapterPosition()==getData().size()-1) {
            helper.setGone(R.id.divider_v,false);
        }else {
            helper.setGone(R.id.divider_v,true);

        }
    }
}
