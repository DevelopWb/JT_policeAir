package com.juntai.wisdom.dgjxb.mine.setting;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.MyMenuBean;

import java.util.List;

/**
 * Describe:设置菜单
 * Create by zhangzhenlong
 * 2020/3/9
 * email:954101549@qq.com
 */
public class SettingMenuAdapter extends BaseQuickAdapter<MyMenuBean, BaseViewHolder> {

    public SettingMenuAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMenuBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setImageResource(R.id.item_iv, item.getImageId());
        helper.getView(R.id.item_number).setVisibility(View.INVISIBLE);
    }
}