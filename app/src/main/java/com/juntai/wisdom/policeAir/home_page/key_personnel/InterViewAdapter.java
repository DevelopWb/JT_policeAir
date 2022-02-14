package com.juntai.wisdom.policeAir.home_page.key_personnel;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.key_personnel.InterviewListBean;

import java.util.List;

/**
 * Describe:走访列表适配器
 * Create by zhangzhenlong
 * 2020-7-2
 * email:954101549@qq.com
 */
public class InterViewAdapter extends BaseQuickAdapter<InterviewListBean.DataBean.DatasBean, BaseViewHolder> {
    public InterViewAdapter(int layoutResId, @Nullable List<InterviewListBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InterviewListBean.DataBean.DatasBean item) {
        helper.setGone(R.id.item_image,false);
        helper.setText(R.id.item_title,item.getLogUser())
                .setText(R.id.item_time,item.getGmtCreate());
    }
}
