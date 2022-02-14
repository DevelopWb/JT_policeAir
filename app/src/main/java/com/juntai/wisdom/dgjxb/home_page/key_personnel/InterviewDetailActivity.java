package com.juntai.wisdom.dgjxb.home_page.key_personnel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.TextListBean;
import com.juntai.wisdom.dgjxb.bean.key_personnel.InterviewDetailBean;
import com.juntai.wisdom.dgjxb.home_page.InfoDetailContract;
import com.juntai.wisdom.dgjxb.home_page.InfoDetailPresent;
import com.juntai.wisdom.dgjxb.home_page.baseInfo.BaseBannnerDetail;
import com.juntai.wisdom.dgjxb.home_page.baseInfo.TextListAdapter;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.GlideImageLoader;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 走访详情
 * @aouther ZhangZhenlong
 * @date 2020-7-2
 */
public class InterviewDetailActivity extends BaseBannnerDetail<InfoDetailPresent> implements InfoDetailContract.IInfoDetailView {

    @Override
    public void initData() {
        dataId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        mPresenter.getInterviewDetail(dataId, "");
    }

    @Override
    protected InfoDetailPresent createPresenter() {
        return new InfoDetailPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mPageDetailSrl.finishRefresh();
        InterviewDetailBean interviewDetailBean = (InterviewDetailBean) o;
        onSuccessInterviewDetailLogic(interviewDetailBean);
    }

    /**
     * 走访详情接口成功后的逻辑
     *
     * @param
     */
    private void onSuccessInterviewDetailLogic(InterviewDetailBean interviewDetailBean) {
        InterviewDetailBean.DataBean dataBean = interviewDetailBean.getData();
        if (dataBean != null) {
            images.clear();
            String videoUrl = null;
            for (int i = 0; i < dataBean.getKFileDos().size(); i++) {
                InterviewDetailBean.DataBean.KFileDosBean fileDosBean = dataBean.getKFileDos().get(i);
                if (fileDosBean.isFlag()) {
                    videoUrl = fileDosBean.getUrl();
                    hasVideo = true;
                } else {
                    images.add(fileDosBean.getUrl());
                }
            }
            if (videoUrl != null) {
                images.add(0, videoUrl);
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
            mDescriptionTv.setText(dataBean.getContent() + "");
            textListBeans.clear();
            textListBeans.add(new TextListBean("走访地址", dataBean.getLogPlace()));
            textListBeans.add(new TextListBean("走访时间", dataBean.getGmtCreate()));
            textListBeans.add(new TextListBean("走访警员", dataBean.getLogUser()));
            textListAdapter.notifyDataSetChanged();
        }else {
            ToastUtils.warning(this, "数据已删除！");
            onBackPressed();
        }
    }

    @Override
    protected String getTitleContent() {
        return "走访详情";
    }
}
