package com.example.appbase.base.multi.adapters;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.example.appbase.base.customview.selectPics.SelectPicVideoBean;
import com.example.appbase.base.customview.selectPics.SelectPicVideoRv;
import com.example.appbase.base.displayPicVideo.DisplayPicAndVideosActivity;
import com.example.appbase.bean.nong_fa_manager.CommoditySourceBean;
import com.example.appbase.util.bannerImageLoader.BannerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommodityManagerSourceDetailAdapter extends BaseQuickAdapter<CommoditySourceBean, BaseViewHolder> {
    public CommodityManagerSourceDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommoditySourceBean item) {

        SelectPicVideoRv selectPicVideoRv = helper.getView(R.id.source_spr);
        List<SelectPicVideoBean> pics = new ArrayList<>();

        List<String> photos = item.getPics();
        if (photos != null&&!photos.isEmpty()) {
            for (String pic : photos) {
                pics.add(new SelectPicVideoBean(SelectPicVideoBean.TYPE_IMAGE,pic));
            }
        }
        selectPicVideoRv.setDetail(true)
                .setmMaxCount(pics.size())
        .setOnPhotoItemClick(new SelectPicVideoRv.OnPhotoItemClick() {
            @Override
            public void onVedioPicClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onPicClick(BaseQuickAdapter adapter, View view, int position) {
                List<BannerObject> bannerObjects = new ArrayList<>();
                List<SelectPicVideoBean> arrays = adapter.getData();
                for (SelectPicVideoBean array : arrays) {
                    if (SelectPicVideoBean.TYPE_NULL!=array.getType()) {
                        bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE,array.getPath()));
                    }
                }
                DisplayPicAndVideosActivity.startPicVideoPlayActivity(mContext, bannerObjects, position);
            }

            @Override
            public void onSelectPic(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        selectPicVideoRv.setData(pics);
        helper.setGone(R.id.source_spr, !pics.isEmpty());
        helper.setGone(R.id.source_pic_title_tv, !TextUtils.isEmpty(item.getTitle()));
        helper.setText(R.id.source_pic_title_tv, item.getTitle());


    }
}
