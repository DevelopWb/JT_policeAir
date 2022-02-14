package com.juntai.wisdom.dgjxb.home_page.camera.yunkong;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.stream.PreSetBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

/**
 * @Author: tobato
 * @Description: 作用描述  云控配置
 * @CreateDate: 2020/8/15 8:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/15 8:58
 */
public class CameraPrePositionAdapter extends BaseQuickAdapter<PreSetBean.DataBean, BaseViewHolder> {
    public CameraPrePositionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PreSetBean.DataBean item) {
        ImageLoadUtil.loadImageCache(mContext, UrlFormatUtil.formatPicUrl(item.getPicture()),
                helper.getView(R.id.pre_position_bg_iv));

        if (item.isEdit()) {
            helper.setGone(R.id.del_pre_position_iv,true);
        }else {
            helper.setGone(R.id.del_pre_position_iv,false);
        }
    }
}
