package com.juntai.wisdom.policeAir.home_page.equipment.my_equipment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.user_equipment.EquipmentListBean;

/**
 * Describe:设备列表适配器
 * Create by zhangzhenlong
 * 2020-11-21
 * email:954101549@qq.com
 */
public class EquipmentListAdapter extends BaseQuickAdapter<EquipmentListBean.DataBean, BaseViewHolder> {

    public EquipmentListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EquipmentListBean.DataBean item) {
        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.getEzOpen(), helper.getView(R.id.item_image));
        helper.setText(R.id.item_title, item.getName())
                .setText(R.id.item_number, "编号:" + item.getNumber())
                .setText(R.id.item_address, item.getAddress());
    }
}
