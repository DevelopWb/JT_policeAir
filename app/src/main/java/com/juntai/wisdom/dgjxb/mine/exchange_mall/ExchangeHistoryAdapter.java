package com.juntai.wisdom.dgjxb.mine.exchange_mall;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.AppHttpPath;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.exchang_mall.HistoryGoodsListBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

import java.util.List;

/**
 * Describe: 兑换历史列表适配器
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public class ExchangeHistoryAdapter extends BaseQuickAdapter<HistoryGoodsListBean.DataBean, BaseViewHolder> {
    public ExchangeHistoryAdapter(int layoutResId, @Nullable List<HistoryGoodsListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryGoodsListBean.DataBean item) {
        ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getCommodityImg(),
                R.drawable.nopicture,R.drawable.nopicture,helper.getView(R.id.item_iv));
        helper.setText(R.id.item_name,item.getCommodityName());
        helper.setText(R.id.item_price,item.getThisScore()+"积分");
        helper.setText(R.id.item_date,item.getCreateTime());
    }
}
