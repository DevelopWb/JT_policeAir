package com.juntai.wisdom.policeAir.home_page.conciliation.conciliation_publish;

import android.content.Intent;
import android.view.View;

import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.PickerManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.conciliation.ConciliationTypesBean;
import com.juntai.wisdom.policeAir.bean.conciliation.MediatorAllListBean;
import com.juntai.wisdom.policeAir.bean.conciliation.UnitListBean;
import com.juntai.wisdom.policeAir.entrance.sendcode.SendCodeModel;
import com.juntai.wisdom.policeAir.home_page.conciliation.conciliation.ConciliationContract;
import com.juntai.wisdom.policeAir.home_page.weather.SelectLocation;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 申请调解
 *
 * @aouther ZhangZhenlong
 * @date 2020-5-20
 */
public class PublishConciliationActivity extends BasePublishActivity {
    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case ConciliationContract.PUBLISH_CONCILIATION:
                startActivity(new Intent(mContext, SucessResultActivity.class));
                EventManager.sendStringMsg(ActionConfig.REFRASH_CONCILIATION_LIST);
                finish();
                break;
            case ConciliationContract.GET_UNIT_LIST:
                UnitListBean unitListBean = (UnitListBean) o;
                List<UnitListBean.DataBean> units = unitListBean.getData();
                if (units != null && units.size() > 0) {
                    optionsPickerViewUnits = PickerManager.getInstance().getOptionPicker(mContext, "选择单位",
                            units, new PickerManager.OnOptionPickerSelectedListener() {
                                @Override
                                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                    if (unitBean != null && unitBean.getId() == units.get(options1).getId()) {
                                        return;
                                    }
                                    mUnitTv.setText(units.get(options1).getName());
                                    unitBean = units.get(options1);
                                    //刷新人员数据
                                    mPresenter.getMediatorList(areaBean.getCityNum(), unitBean.getId(), ConciliationContract.GET_ALL_MEDIATOR_LIST);
                                }
                            });
                    optionsPickerViewUnits.show();
                }else {
                    ToastUtils.warning(mContext, "暂无该辖区单位信息");
                    mUnitTv.setText("");
                    unitBean = null;
                }
                break;
            case ConciliationContract.GET_ALL_MEDIATOR_LIST:
                MediatorAllListBean mediatorAllListBean = (MediatorAllListBean) o;
                List<MediatorAllListBean.MediatorBean> mediatorBeans = mediatorAllListBean.getData();
                if (mediatorBeans != null && mediatorBeans.size() > 0) {
                    clearMediatorData();
                    for (MediatorAllListBean.MediatorBean mediator : mediatorBeans) {
                        if (mediator.getTypeId() == 1) {//（1：律师；2：人民调解员；3：其他）
                            lawyers.add(mediator);
                        } else if (mediator.getTypeId() == 2) {
                            mediators.add(mediator);
                        } else {
                            polices.add(mediator);
                        }
                    }
                }else {
                    ToastUtils.warning(mContext, "暂无调解员信息");
                    clearMediatorData();
                }
                chooseMediatorsAdapterMediators.notifyDataSetChanged();
                chooseMediatorsAdapterLawyers.notifyDataSetChanged();
                chooseMediatorsAdapterPolices.notifyDataSetChanged();
                break;
            case ConciliationContract.GET_CONCILIATION_TYPE_LIST:
                ConciliationTypesBean typesBean = (ConciliationTypesBean) o;
                List<ConciliationTypesBean.DataBean> types = typesBean.getData();
                if (types != null && types.size() > 0) {
                    if (optionsPickerViewTypes == null) {
                        optionsPickerViewTypes = PickerManager.getInstance().getOptionPicker(mContext, "调解类型",
                                types, new PickerManager.OnOptionPickerSelectedListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                        if (typeBean != null && typeBean.getId() == types.get(options1).getId()) {
                                            return;
                                        }
                                        mTypeTv.setText(types.get(options1).getName());
                                        typeBean = types.get(options1);
                                        mPresenter.getConciliationTypeList(typeBean.getId(), ConciliationContract.GET_CHILD_TYPE_LIST, true);
                                    }
                                });
                    }
                    if (optionsPickerViewTypes != null && !optionsPickerViewTypes.isShowing()) {
                        optionsPickerViewTypes.show();
                    }
                }
                break;
            case ConciliationContract.GET_CHILD_TYPE_LIST:
                ConciliationTypesBean childTypesBean = (ConciliationTypesBean) o;
                List<ConciliationTypesBean.DataBean> childTypes = childTypesBean.getData();
                if (childTypes != null && childTypes.size() > 0) {
                    optionsPickerViewChildTypes = PickerManager.getInstance().getOptionPicker(mContext, "调解子类型",
                            childTypes, new PickerManager.OnOptionPickerSelectedListener() {
                                @Override
                                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                    if (childTypeBean != null && childTypeBean.getId() == childTypes.get(options1).getId()) {
                                        return;
                                    }
                                    mChildTypeTv.setText(childTypes.get(options1).getName());
                                    childTypeBean = childTypes.get(options1);
                                }
                            });
//                    }
                    childTypeBean = childTypes.get(0);
                    mChildTypeTv.setText(childTypeBean.getName());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_video_pic:
                //视频选择
                initBottomDialog(Arrays.asList("直接拍摄", "本地视频"));
                break;
            case R.id.item_delete_vedio_iv:
                //删除视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedio.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext, "点击过于频繁");
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mAddressEt))) {
                    ToastUtils.warning(mContext, "请输入申请人地址！");
                    return;
                }
                if (StringTools.isStringValueOk(getTextViewValue(mTelEt2)) && !SendCodeModel.isMobileNO(getTextViewValue(mTelEt2))) {
                    ToastUtils.warning(mContext, "被申请人电话格式不正确！");
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mDescriptionEt))) {
                    ToastUtils.warning(mContext, "请输入调解描述！");
                    return;
                }
                if (typeBean == null){
                    ToastUtils.warning(mContext, "请选择调解类型！");
                    return;
                }
                if (childTypeBean == null){
                    ToastUtils.warning(mContext, "请选择调解子类型！");
                    return;
                }
                if (unitBean == null){
                    ToastUtils.warning(mContext, "请选择单位！");
                    return;
                }

                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("applicantName", getTextViewValue(mNameEt))
                        .addFormDataPart("applicantPhone", getTextViewValue(mTelEt))
                        .addFormDataPart("applicantAddress", getTextViewValue(mAddressEt))
                        .addFormDataPart("respondentName", getTextViewValue(mNameEt2))
                        .addFormDataPart("respondentPhone", getTextViewValue(mTelEt2))
                        .addFormDataPart("introduction", getTextViewValue(mDescriptionEt))
                        .addFormDataPart("bigTypeId",String.valueOf(typeBean.getId()))
                        .addFormDataPart("smallTypeId",String.valueOf(childTypeBean.getId()))
                        .addFormDataPart("regionId",String.valueOf(areaBean.getId()))
                        .addFormDataPart("unitId",String.valueOf(unitBean.getId()));
                if (policeBean != null) {
                    builder.addFormDataPart("policeId", policeBean.getId() + "");
                }
                if (lawyerBean != null) {
                    builder.addFormDataPart("lawyerId", lawyerBean.getId() + "");
                }
                if (mediatorBean != null) {
                    builder.addFormDataPart("peopleId", mediatorBean.getId() + "");
                }
                List<String> pics = selectPhotosFragment.getPhotosPath();
                if (pics.size() > 0) {
                    for (String path : pics) {
                        //这里上传的是多图
                        builder.addFormDataPart("picture", "picture", RequestBody.create(MediaType.parse("file"), new File(path)));
                    }
                }
                if (StringTools.isStringValueOk(videoPath)) {//视频
                    builder.addFormDataPart("video", "video", RequestBody.create(MediaType.parse("file"), new File(videoPath)));
                }
                mPresenter.publishConciliation(ConciliationContract.PUBLISH_CONCILIATION, builder.build());
                break;
            case R.id.area_tv:
                //选择区域
                startActivityForResult(new Intent(mContext, SelectLocation.class).putExtra("type", 1), SelectLocation.SELECT_LOCATION);
                break;
            case R.id.unit_tv:
                if (areaBean == null) {
                    ToastUtils.warning(mContext, "请先选择辖区");
                    return;
                }
                mPresenter.getUnitList(areaBean.getCityNum(), ConciliationContract.GET_UNIT_LIST, true);
                break;
            case R.id.type_tv:
                if (optionsPickerViewTypes == null){
                    mPresenter.getConciliationTypeList(0, ConciliationContract.GET_CONCILIATION_TYPE_LIST,true);
                }else {
                    if (!optionsPickerViewTypes.isShowing()) {
                        optionsPickerViewTypes.show();
                    }
                }
                break;
            case R.id.child_type_tv:
                if (typeBean == null){
                    ToastUtils.warning(mContext, "请先选择主类型");
                    return;
                }
                if (optionsPickerViewChildTypes != null && !optionsPickerViewChildTypes.isShowing()) {
                    optionsPickerViewChildTypes.show();
                } else {
                    if (optionsPickerViewChildTypes == null) {
                        mPresenter.getConciliationTypeList(typeBean.getId(), ConciliationContract.GET_CHILD_TYPE_LIST, true);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 清空调解员信息
     */
    private void clearMediatorData(){
        polices.clear();
        lawyers.clear();
        mediators.clear();
        policeBean = null;
        lawyerBean = null;
        mediatorBean = null;
        chooseMediatorsAdapterPolices.setChoosePosition(-1);
        chooseMediatorsAdapterLawyers.setChoosePosition(-1);
        chooseMediatorsAdapterMediators.setChoosePosition(-1);
    }
}
