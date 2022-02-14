package com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation_publish;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.conciliation.MediatorAllListBean;

import java.util.List;

/**
 * Describe:带选择调解员列表适配器
 * Create by zhangzhenlong
 * 2020-7-17
 * email:954101549@qq.com
 */
public class ChooseMediatorsAdapter extends BaseQuickAdapter<MediatorAllListBean.MediatorBean, BaseViewHolder> {
    private int choosePosition = -1;
    public ChooseMediatorsAdapter(int layoutResId, @Nullable List<MediatorAllListBean.MediatorBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediatorAllListBean.MediatorBean item) {
        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), item.getHeadPortrait(),
                R.mipmap.default_user_head_icon, R.mipmap.default_user_head_icon, helper.getView(R.id.item_iv));
        helper.setText(R.id.item_name, item.getName());
        if (choosePosition == helper.getAdapterPosition()){
            helper.setBackgroundRes(R.id.item_tag, R.drawable.bg_choose_tag);
        }else {
            helper.setBackgroundRes(R.id.item_tag, R.drawable.bg_login_edit_shape);
        }

        helper.addOnClickListener(R.id.item_iv);
        helper.addOnClickListener(R.id.item_choose);
    }

    public int getChoosePosition() {
        return choosePosition;
    }

    public void setChoosePosition(int choosePosition) {
        this.choosePosition = choosePosition;
    }
}
