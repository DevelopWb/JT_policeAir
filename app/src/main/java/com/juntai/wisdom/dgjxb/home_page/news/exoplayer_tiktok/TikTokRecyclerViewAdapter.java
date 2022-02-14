package com.juntai.wisdom.dgjxb.home_page.news.exoplayer_tiktok;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.danikula.videocache2.HttpProxyCacheServer;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.video.Jzvd.JzvdStdTikTok;

import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;

import static com.juntai.wisdom.dgjxb.MyApp.getProxy;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2021-1-9
 * email:954101549@qq.com
 */
public class TikTokRecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TikTokRecyclerViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //标题
//        helper.setText(R.id.usertitle_tv, "");
        //声明 代理服务缓存
        HttpProxyCacheServer proxy = getProxy(mContext);
        //这个缓存下一个
        if (helper.getLayoutPosition() + 1 < getItemCount()) {
            String item1 = getItem(helper.getLayoutPosition() + 1);
            //缓存下一个5秒
            proxy.preLoad(item1, 5);
        }

        //缓存当前，播放当前
        String proxyUrl =proxy.getProxyUrl(item); //获取缓存地址
        JZDataSource jzDataSource = new JZDataSource(proxyUrl,"");
        jzDataSource.looping = true;
        Jzvd.SAVE_PROGRESS = true;
        JzvdStdTikTok jzvdStdTikTok = helper.getView(R.id.video_item);
        jzvdStdTikTok.setUp(jzDataSource, Jzvd.SCREEN_NORMAL);
        Glide.with(mContext).load(R.drawable.panorama).into(jzvdStdTikTok.thumbImageView);
    }
}
