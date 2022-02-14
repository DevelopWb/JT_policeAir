package com.juntai.wisdom.policeAir.home_page.site_manager.site_info;

import android.view.View;

import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.bean.site.EmployeeDetailBean;
import com.juntai.wisdom.policeAir.home_page.baseInfo.BaseInfoDetail;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerContract;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerPresent;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  从业人员详情
 * @date 2020/4/5 16:53
 */
public class EmployeeDetailActivity extends BaseInfoDetail<SiteManagerPresent> implements SiteManagerContract.ISiteManagerView {

    private ArrayList<String> photos = new ArrayList<>();
    private EmployeeDetailBean.DataBean dataBean;


    @Override
    public void initData() {
        INFO_ID = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        mPresenter.getEmployeeDetail("", INFO_ID);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        EmployeeDetailBean employeeDetailBean = (EmployeeDetailBean) o;
        photos.clear();
        if (employeeDetailBean != null) {
            dataBean = employeeDetailBean.getData();
            List<String> images = new ArrayList<>();
            if (StringTools.isStringValueOk(dataBean.getVideoUrl())) {
                images.add(dataBean.getVideoUrl());
                hasVideo = true;
            }else{
                hasVideo = false;
            }
            if (StringTools.isStringValueOk(dataBean.getPictureOneUrl())) {
                images.add(dataBean.getPictureOneUrl());
                photos.add(dataBean.getPictureOneUrl());
            }
            if (StringTools.isStringValueOk(dataBean.getPictureTwoUrl())) {
                images.add(dataBean.getPictureTwoUrl());
                photos.add(dataBean.getPictureTwoUrl());
            }
            if (StringTools.isStringValueOk(dataBean.getPictureThreeUrl())) {
                images.add(dataBean.getPictureThreeUrl());
                photos.add(dataBean.getPictureThreeUrl());
            }

            if (StringTools.isStringValueOk(dataBean.getRemake())){
                mDesTv.setVisibility(View.VISIBLE);
                mDesTv.setText(dataBean.getRemake());
            }else {
                mDesTv.setVisibility(View.GONE);
            }

            selectPhotosFragment.setMaxCount(images.size());
            selectPhotosFragment.setIcons(images);
            textListAdapter.setNewData(null);
            textListAdapter.addData(new TextListBean("姓名", dataBean.getName()));
            textListAdapter.addData(new TextListBean("性别", dataBean.getSex() == 1? "男":"女"));
            textListAdapter.addData(new TextListBean("身份证号", dataBean.getIdNumber()));
            textListAdapter.addData(new TextListBean("居住地址", dataBean.getAddress()));
            textListAdapter.addData(new TextListBean("联系电话", dataBean.getPhone()));
            textListAdapter.addData(new TextListBean("所属单位", dataBean.getTypeName()));
        }
    }

    @Override
    protected ArrayList<String> getPhotos() {
        return photos;
    }

    @Override
    protected String getTitleContent() {
        return "从业人员详情";
    }


    @Override
    protected SiteManagerPresent createPresenter() {
        return new SiteManagerPresent();
    }
}