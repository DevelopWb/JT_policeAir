package com.juntai.wisdom.dgjxb.home_page.weather;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.weather.WeatherEveryDayBean;

import java.util.List;

/**
 * Describe: 天气日常
 * Create by zhangzhenlong
 * 2020-6-27
 * email:954101549@qq.com
 */
public class WeatherEveryDayAdapter extends BaseQuickAdapter<WeatherEveryDayBean, BaseViewHolder> {

    public WeatherEveryDayAdapter(int layoutResId, @Nullable List<WeatherEveryDayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeatherEveryDayBean item) {
        helper.setImageResource(R.id.item_image,item.getImageInt());
        helper.setText(R.id.item_name,item.getContent());
    }
}
