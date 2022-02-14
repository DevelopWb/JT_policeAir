package com.juntai.wisdom.dgjxb.mine.score;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.MyMenuBean;
import com.juntai.wisdom.dgjxb.bean.TextListBean;
import com.juntai.wisdom.dgjxb.bean.UserScoreListBean;

import java.util.List;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020/3/10
 * email:954101549@qq.com
 */
public class MyScoreDetailAdapter extends BaseQuickAdapter<UserScoreListBean.DataBean.ScoreBean, BaseViewHolder> {

    public MyScoreDetailAdapter(int layoutResId, @Nullable List<UserScoreListBean.DataBean.ScoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserScoreListBean.DataBean.ScoreBean item) {
        helper.setText(R.id.item_name, item.getMemo());
        helper.setText(R.id.item_date,item.getGmtCreate());
        if (item.getType() == 1){
            helper.setText(R.id.item_value, "+"+item.getScore());
            helper.setTextColor(R.id.item_value,mContext.getResources().getColor(R.color.red));
        }else {
            helper.setText(R.id.item_value, "-"+item.getScore());
            helper.setTextColor(R.id.item_value,mContext.getResources().getColor(R.color.text_label));
        }
    }
}
