package com.juntai.wisdom.policeAir.home_page.law_case;

import com.juntai.wisdom.policeAir.bean.case_bean.CaseInfoBean;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.home_page.baseInfo.BaseInfoDetail;
import com.juntai.wisdom.policeAir.home_page.map.MapContract;
import com.juntai.wisdom.policeAir.home_page.map.MapPresenter;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 案件跟踪详情 -
 * Created by Ma
 * on 2019/4/16
 */
public class CaseDetailActivity extends BaseInfoDetail<MapPresenter> implements MapContract.View {
    private ArrayList<String> photos = new ArrayList<>();
    private String content;
    private CaseInfoBean.DataBean dataBean;


    @Override
    public void initData() {
        INFO_ID = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        content = getIntent().getStringExtra("CaseDetailsChildActivityContent");
        mPresenter.getCaseInfo(null, INFO_ID);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        CaseInfoBean caseDetailBean = (CaseInfoBean) o;
        photos.clear();
        if (caseDetailBean != null && caseDetailBean.getData() != null) {
            dataBean = caseDetailBean.getData();
            List<String> images = new ArrayList<>();
            if (StringTools.isStringValueOk(dataBean.getVideo())) {
                images.add(dataBean.getVideo());
                hasVideo = true;
            }else{
                hasVideo = false;
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoOne())) {
                images.add(dataBean.getPhotoOne());
                photos.add(dataBean.getPhotoOne());
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoTwo())) {
                images.add(dataBean.getPhotoTwo());
                photos.add(dataBean.getPhotoTwo());
            }
            if (StringTools.isStringValueOk(dataBean.getPhotoThree())) {
                images.add(dataBean.getPhotoThree());
                photos.add(dataBean.getPhotoThree());
            }

            selectPhotosFragment.setMaxCount(images.size());
            selectPhotosFragment.setIcons(images);
            textListAdapter.setNewData(null);
            textListAdapter.addData(new TextListBean("案件地点:", dataBean.getAddress()));
            textListAdapter.addData(new TextListBean("报警时间:", dataBean.getHappenDate()));
            textListAdapter.addData(new TextListBean("接警时间:", dataBean.getCaseAcceptTime()));
            textListAdapter.addData(new TextListBean("案件简要:", content));
            textListAdapter.addData(new TextListBean("案件类型(大):", dataBean.getBigName()));
            textListAdapter.addData(new TextListBean("案件类型(小):", dataBean.getSmallName()));
            textListAdapter.addData(new TextListBean("所属网格:", dataBean.getGridName()));
        }
    }

    @Override
    protected ArrayList<String> getPhotos() {
        return photos;
    }

    @Override
    protected String getTitleContent() {
        return "案件详情";
    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }
}
