package com.juntai.wisdom.dgjxb.home_page.site_manager.site_info;

import android.content.Intent;

import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.bean.TextListBean;
import com.juntai.wisdom.dgjxb.bean.site.SiteInspectionDetailBean;
import com.juntai.wisdom.dgjxb.home_page.baseInfo.BaseBannnerDetail;
import com.juntai.wisdom.dgjxb.home_page.site_manager.SiteManagerContract;
import com.juntai.wisdom.dgjxb.home_page.site_manager.SiteManagerPresent;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.GlideImageLoader;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.video.player.PlayerActivity;
/**
 * @description 单位检查记录详情
 * @aouther ZhangZhenlong
 * @date 2020-7-4
 */
public class UnitInspectionDetailActivity extends BaseBannnerDetail<SiteManagerPresent> implements SiteManagerContract.ISiteManagerView {

    @Override
    public void initData() {
        dataId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        mPresenter.getSiteInspectionDetail("",dataId);
    }

    @Override
    protected SiteManagerPresent createPresenter() {
        return new SiteManagerPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mPageDetailSrl.finishRefresh();
        SiteInspectionDetailBean inspectionDetailBean = (SiteInspectionDetailBean) o;
        onSuccessInterviewDetailLogic(inspectionDetailBean);
    }

    /**
     * 走访详情接口成功后的逻辑
     *
     * @param
     */
    private void onSuccessInterviewDetailLogic(SiteInspectionDetailBean inspectionDetailBean) {
        SiteInspectionDetailBean.DataBean dataBean = inspectionDetailBean.getData();
        if (dataBean != null) {
            images.clear();
            if (StringTools.isStringValueOk(dataBean.getVideoUrl())) {
                images.add(dataBean.getVideoUrl());
                hasVideo = true;
            }else{
                hasVideo = false;
            }
            for (int i = 0; i < dataBean.getFile().size(); i++) {
                images.add(dataBean.getFile().get(i).getFileUrl());
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
            mDescriptionTv.setText(dataBean.getRemake() + "");
            textListBeans.clear();
            textListBeans.add(new TextListBean("检查地址", dataBean.getAddress()));
            textListBeans.add(new TextListBean("检查时间", dataBean.getGmtCreate()));
            textListBeans.add(new TextListBean("检查警员", dataBean.getName()));
            textListAdapter.notifyDataSetChanged();
        }else {
            ToastUtils.warning(this, "数据已删除！");
            onBackPressed();
        }
    }

    @Override
    protected String getTitleContent() {
        return "检查详情";
    }
}
