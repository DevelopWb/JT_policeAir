package com.juntai.wisdom.policeAir.home_page.site_manager.site_info;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.site.UnitDetailBean;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.home_page.baseInfo.TextListAdapter;
import com.juntai.wisdom.policeAir.home_page.map.MapContract;
import com.juntai.wisdom.policeAir.home_page.map.MapPresenter;
import com.juntai.wisdom.policeAir.home_page.site_manager.site_add.UpdateSiteActivity;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.GlideImageLoader;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.policeAir.utils.UrlFormatUtil;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

/**
 * @description 单位详情
 * @aouther ZhangZhenlong
 * @date 2020-7-4
 */
public class UnitDetailFragment extends BaseMvpFragment<MapPresenter> implements MapContract.View, View.OnClickListener {
    int type = 0;
    private Banner mBanner;
    private RecyclerView mDetailTexts;
    private TextView mIntroTv;
    private TextView mDescriptionTv;
    private SmartRefreshLayout mPageDetailSrl;
    protected List<TextListBean> textListBeans = new ArrayList<>();
    protected TextListAdapter textListAdapter;
    /**
     * 修改
     */
    private TextView mUpdateBtn;
    /**
     * 描述
     */
    protected int dataId;
    protected GlideImageLoader imageLoader;
    protected ArrayList<String> images = new ArrayList<>();//包含视频
    protected boolean hasVideo = false;//是否有视频文件
    private UnitDetailBean.DataBean dataBean;

    public static UnitDetailFragment newInstance(int type, int dataId) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt(AppUtils.ID_KEY,dataId);
        UnitDetailFragment fragment = new UnitDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        dataId = getArguments().getInt(AppUtils.ID_KEY);
    }


    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_unit_detail;
    }

    @Override
    protected void initView() {
        mBanner = getView(R.id.banner);
        mDetailTexts = getView(R.id.detail_texts);
        mIntroTv = getView(R.id.intro_tv);
        mDescriptionTv = getView(R.id.description_tv);
        mPageDetailSrl = getView(R.id.page_detail_srl);
        mUpdateBtn = getView(R.id.update_btn);
        mUpdateBtn.setOnClickListener(this);

        mDetailTexts.setLayoutManager(new LinearLayoutManager(mContext));
        textListAdapter = new TextListAdapter(R.layout.item_inspection_detail, textListBeans);
        mDetailTexts.setAdapter(textListAdapter);

        mPageDetailSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getData(false);
            }
        });
        mPageDetailSrl.setEnableRefresh(false);
        mPageDetailSrl.setEnableLoadMore(false);

        mBanner.isAutoPlay(false);
        ArrayList<String> photos = new ArrayList<>();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //查看图片
                photos.clear();
                photos.addAll(images);
                if (hasVideo) {
                    photos.remove(0);
                }
                startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item", hasVideo ? position - 1 : position));

            }
        });
    }

    @Override
    protected void initData() {}

    @Override
    protected void lazyLoad() {
        getData(true);
    }

    @Override
    public void onError(String tag, Object o) {
        mPageDetailSrl.finishRefresh();
        ToastUtils.error(mContext, String.valueOf(o));
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mPageDetailSrl.finishRefresh();
        UnitDetailBean unitDetailBean = (UnitDetailBean) o;
        if (unitDetailBean != null && unitDetailBean.getData() != null) {
            dataBean = unitDetailBean.getData();
            onSuccessUnitDetailLogic();
            mIntroTv.setText(dataBean.getSynopsis() + "");
            if (StringTools.isStringValueOk(dataBean.getRemark())){
                mDescriptionTv.setText(dataBean.getRemark());
                mDescriptionTv.setVisibility(View.VISIBLE);
            }else {
                mDescriptionTv.setVisibility(View.GONE);
            }

            textListBeans.clear();
            textListBeans.add(new TextListBean("单位名称:", dataBean.getName()));
            textListBeans.add(new TextListBean("单位类型:", dataBean.getTypeName()));
            textListBeans.add(new TextListBean("所属网格:", dataBean.getGridName()));
            textListBeans.add(new TextListBean("经营品类:", dataBean.getCategory()));
            textListBeans.add(new TextListBean("所在区域:", dataBean.getRegion()));
            textListBeans.add(new TextListBean("单位地址:", dataBean.getAddress()));
            textListBeans.add(new TextListBean("联系人姓名:", dataBean.getContactsName()));
            textListBeans.add(new TextListBean("性别:", dataBean.getContactsSex() == 1? "男":"女"));
            textListBeans.add(new TextListBean("联系电话:", dataBean.getContactsPhone()));
            textListBeans.add(new TextListBean("备用联系人:", dataBean.getStandbyContacts()));
            textListBeans.add(new TextListBean("联系电话:", dataBean.getStandbyPhone()));
            textListAdapter.notifyDataSetChanged();
            if (dataBean.getUserId() == MyApp.getUid()){
                mUpdateBtn.setVisibility(View.VISIBLE);
            }else {
                mUpdateBtn.setVisibility(View.GONE);
            }
        }else {
            ToastUtils.warning(mContext, "数据已删除！");
            getActivity().onBackPressed();
        }
    }

    /**
     * 单位详情图片逻辑
     *
     * @param
     */
    private void onSuccessUnitDetailLogic() {
        images.clear();

        if (StringTools.isStringValueOk(dataBean.getVideoUrl())) {
            hasVideo = true;
            images.add(dataBean.getVideoUrl());
        } else {
            hasVideo = false;
        }
        String imageIds = dataBean.getImgId();
        if (StringTools.isStringValueOk(imageIds)) {
            if (imageIds.contains(",")) {
                String[] ids = imageIds.split(",");
                for (String id : ids) {
                    images.add(UrlFormatUtil.getInspectionOriginalImg(Integer.parseInt(id)));
                }
            } else {
                images.add(UrlFormatUtil.getInspectionOriginalImg(Integer.parseInt(imageIds)));
            }
        }

        imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
            @Override
            public void onFullScreen() {
                if (hasVideo && StringTools.isStringValueOk(images.get(0))) {
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    intent.putExtra(PlayerActivity.VIDEO_PATH, images.get(0));
                    startActivity(intent);
                }

            }
        });
        mBanner.setImages(images).setImageLoader(imageLoader).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBanner != null) {
            mBanner.releaseBanner();
            mBanner.removeAllViews();
            mBanner.setOnBannerListener(null);
            if (imageLoader != null) {
                imageLoader.setOnFullScreenCallBack(null);
                imageLoader.release();
            }

        }
        mBanner = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        mPresenter.getUnitDetail(dataId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.update_btn:
                //修改
                startActivity(new Intent(mContext, UpdateSiteActivity.class).putExtra("dataBean",dataBean));
                break;
        }
    }
}
