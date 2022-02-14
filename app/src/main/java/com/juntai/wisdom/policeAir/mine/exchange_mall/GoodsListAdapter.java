package com.juntai.wisdom.policeAir.mine.exchange_mall;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.exchang_mall.GoodsListBean;

import java.util.List;

/**
 * Describe:商品列表适配器
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public class GoodsListAdapter extends BaseQuickAdapter<GoodsListBean.GoodsBean, BaseViewHolder> {

    public GoodsListAdapter(int layoutResId, @Nullable List<GoodsListBean.GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsListBean.GoodsBean item) {
        ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getCommodityImg(),
                R.drawable.nopicture,R.drawable.nopicture,helper.getView(R.id.item_iv));
        helper.setText(R.id.item_name,item.getCommodityName());
        helper.setText(R.id.item_price,item.getPrice()+"积分");
        helper.addOnClickListener(R.id.item_pay);
    }
}
