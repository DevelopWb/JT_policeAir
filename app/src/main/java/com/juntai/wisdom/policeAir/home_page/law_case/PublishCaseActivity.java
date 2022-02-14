package com.juntai.wisdom.policeAir.home_page.law_case;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.PickerManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.base.BaseSelectPicsAndVedioActivity;
import com.juntai.wisdom.policeAir.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.policeAir.bean.case_bean.CaseInfoBean;
import com.juntai.wisdom.policeAir.bean.case_bean.CaseTypeBean;
import com.juntai.wisdom.policeAir.entrance.regist.RegistPresent;
import com.juntai.wisdom.policeAir.home_page.PublishContract;
import com.juntai.wisdom.policeAir.home_page.PublishPresent;
import com.juntai.wisdom.policeAir.utils.DateUtil;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.io.File;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * 案件发布
 * @date 2020-3-14
 */
public class PublishCaseActivity extends BaseSelectPicsAndVedioActivity<PublishPresent> implements PublishContract.IPublishView,
        View.OnClickListener {

    private View view;
    /**
     * 请选择地点
     */
    private TextView mAddressTv;
    /**
     * 请选择时间
     */
    private TextView mDateTv;
    /**
     * 请选择类型
     */
    private TextView mTypeTv;
    /**
     * 请选择种类
     */
    private TextView mKindTv;
    /**
     * 请输入描述信息
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/200
     */
    private TextView mInputNumTv;

    /**
     * 确认
     */
    private TextView mOkTv;

    //地址
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat, locLon;
    private int adCode;//661010

    private RegistPresent mRegistPresent;
    private CaseTypeBean.DataBean currentTypeBean = null;//当前类型实体类
    private CaseTypeBean.DataBean.CaseSmallTypeBean currentKindBean = null;//当前种类得实体类
    private List<CaseTypeBean.DataBean.CaseSmallTypeBean> currentKinds = null;//当前种类得实体类

    public static String CASE_INFO_DATA_KEY = "case_info_data";//案件信息中的基本信息
    public static int FOLLOW_CASE_FINISH = 1000;//跟踪案件提交后
    private CaseInfoBean.DataBean dataBean;
    private OptionsPickerView optionsPickerViewKinds;
    private OptionsPickerView optionsPickerViewType;

    @Override
    public int getLayoutView() {
        return R.layout.activity_edit_law_case;
    }

    //选择视频
    @Override
    protected void recordVedio() {
        mPresenter.recordVideo(this);
    }

    @Override
    protected SelectPhotosFragment getFragment() {
        return SelectPhotosFragment.newInstance().setPhotoTitle("")
                .setPhotoSpace(60)
                .setMaxCount(3);
    }

    @Override
    public void initView() {
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mDateTv = (TextView) findViewById(R.id.date_tv);
        mTypeTv = (TextView) findViewById(R.id.type_tv);
        mKindTv = (TextView) findViewById(R.id.kind_tv);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mInputNumTv = (TextView) findViewById(R.id.input_num_tv);

        mDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mInputNumTv.setText("已输入" + s.length() + "/200");
                }
            }
        });

        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        super.initView();
    }

    @Override
    public void initData() {
        mDateTv.setOnClickListener(this);
        initViewRightDrawable(mDateTv, R.mipmap.ic_date);
        Intent intent = getIntent();
        if (intent != null) {
            dataBean = (CaseInfoBean.DataBean) intent.getSerializableExtra(CASE_INFO_DATA_KEY);
        }
        if (dataBean != null) {
            setTitleName("跟踪案件");
            mAddressTv.setText(dataBean.getAddress());
            mTypeTv.setText(dataBean.getBigName());
            mKindTv.setText(dataBean.getSmallName());
        } else {
            setTitleName("发布警情");
            mAddressTv.setOnClickListener(this);
            mTypeTv.setOnClickListener(this);
            mKindTv.setOnClickListener(this);
            initViewRightDrawable(mAddressTv, R.mipmap.ic_push_location);
            initViewRightDrawable(mTypeTv, R.mipmap.arrow_right);
            initViewRightDrawable(mKindTv, R.mipmap.arrow_right);
            setDefaultData();
        }
        mDateTv.setText(DateUtil.getCurrentTime());
        mRegistPresent = new RegistPresent();
        mRegistPresent.setCallBack(this);
    }

    /**
     * 设置图标
     *
     * @param textView
     * @param drawableId
     */
    private void initViewRightDrawable(TextView textView, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, 23), DisplayUtil.dp2px(this, 23));//第一个 0 是距左边距离，第二个 0
        // 是距上边距离，40 分别是长宽
        textView.setCompoundDrawables(null, null, drawable, null);//只放右边
    }

    /**
     * 设置默认数据
     */
    public void setDefaultData() {
        bdLocation = LocateAndUpload.bdLocation;
        if (bdLocation != null) {
            locAddress = bdLocation.getAddrStr() != null ?
                    bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() : "";
            locLat = bdLocation.getLatitude();
            locLon = bdLocation.getLongitude();
            adCode = Integer.valueOf(bdLocation.getAdCode());
        } else {
            locAddress = "";
            locLat = 0.0;
            locLon = 0.0;
        }
        mAddressTv.setText(locAddress);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            default:
                break;
            case R.id.address_tv:
                //选择地点
                Intent intent = new Intent(mContext, LocationSeltionActivity.class);
                startActivityForResult(intent, PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.date_tv:
                //选择时间
                PickerManager.getInstance().showTimePickerView(this,
                        PickerManager.getInstance().getTimeType("second"), "选择时间",
                        new PickerManager.OnTimePickerTimeSelectedListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                mDateTv.setText(DateUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss"));
                            }
                        });
                break;
            case R.id.type_tv:
                //选择类型
                mPresenter.getCaseType(PublishContract.GET_CASE_TYPE, 1);
                break;
            case R.id.kind_tv:
                if (currentTypeBean == null) {
                    ToastUtils.warning(mContext, "请先选择类型");
                    return;
                }
                //选择种类
                if (currentKinds != null && currentKinds.size() > 0) {
                    optionsPickerViewKinds = PickerManager.getInstance().getOptionPicker(mContext, "案件种类",
                            currentKinds, new PickerManager.OnOptionPickerSelectedListener() {
                                @Override
                                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                    currentKindBean = currentKinds.get(options1);
                                    mKindTv.setText(currentKindBean.getCateName());
                                }
                            });
                    if (!optionsPickerViewKinds.isShowing()) {
                        optionsPickerViewKinds.show();
                    }
                } else {
                    ToastUtils.warning(mContext, "此类型没有种类");
                }
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext, "点击过于频繁");
                    return;
                }
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                List<String> pics = selectPhotosFragment.getPhotosPath();
                if (pics.size() < 3) {
                    ToastUtils.warning(mContext, "请选择3张图片");
                    return;
                }
                if (!StringTools.isStringValueOk(videoPath)) {
                    ToastUtils.warning(mContext, "请选择视频");
                    return;
                }

                if (pics.size() > 0) {
                    for (int i = 0; i < pics.size(); i++) {
                        String path = pics.get(i);
                        String key = String.format("%s%d", "photo", i + 1);
                        builder.addFormDataPart(key, key, RequestBody.create(MediaType.parse("file"), new File(path)));
                    }
                }
                if (StringTools.isStringValueOk(videoPath)) {
                    builder.addFormDataPart("caseVideo", "caseVideo", RequestBody.create(MediaType.parse("file"),
                            new File(videoPath)));
                }
                if (dataBean != null) {
                    //跟踪案件
                    builder.addFormDataPart("address", dataBean.getAddress())
                            .addFormDataPart("provinceCode", dataBean.getProvinceCode())
                            .addFormDataPart("cityCode", dataBean.getCityCode())
                            .addFormDataPart("areaCode", dataBean.getAreaCode())
                            .addFormDataPart("longitude", String.valueOf(dataBean.getLongitude()))
                            .addFormDataPart("latitude", String.valueOf(dataBean.getLatitude()))
                            .addFormDataPart("bigType", String.valueOf(dataBean.getBigType()))
                            .addFormDataPart("happenDate", getTextViewValue(mDateTv))
                            .addFormDataPart("caseContent", getTextViewValue(mDescriptionEt))
                            .addFormDataPart("smallType", String.valueOf(dataBean.getSmallType()))
                            .addFormDataPart("caseFId", String.valueOf(dataBean.getId()))
                            .addFormDataPart("reportSource", "1");
                    mPresenter.publishCase(PublishContract.FOLLOW_CASE, builder.build());
                } else {
                    //确认
                    if (!StringTools.isStringValueOk(getTextViewValue(mAddressTv))) {
                        ToastUtils.warning(mContext, "请选择地点");
                        return;
                    }
                    if (currentTypeBean == null) {
                        ToastUtils.warning(mContext, "请选择类型");
                        return;
                    }
                    builder.addFormDataPart("address", locAddress)
                            .addFormDataPart("provinceCode", (adCode/10000)+"0000")
                            .addFormDataPart("cityCode", (adCode/100)+"00")
                            .addFormDataPart("areaCode", adCode+"")
                            .addFormDataPart("longitude", String.valueOf(locLon))
                            .addFormDataPart("latitude", String.valueOf(locLat))
                            .addFormDataPart("bigType", String.valueOf(currentTypeBean.getId()))
                            .addFormDataPart("happenDate", getTextViewValue(mDateTv))
                            .addFormDataPart("caseContent", getTextViewValue(mDescriptionEt))
                            .addFormDataPart("reportSource", "1");
                    if (currentKindBean != null) {
                        builder.addFormDataPart("smallType", String.valueOf(currentKindBean.getId()));
                    }
                    mPresenter.publishCase(PublishContract.PUBLISH_CASE, builder.build());
                }
                break;
        }
    }


    @Override
    protected PublishPresent createPresenter() {
        return new PublishPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case PublishContract.GET_CASE_TYPE:
                CaseTypeBean caseTypeBean = (CaseTypeBean) o;
                if (caseTypeBean != null) {
                    List<CaseTypeBean.DataBean> caseDataBeans = caseTypeBean.getData();
                    if (caseDataBeans != null && caseDataBeans.size() > 0) {
                        if (optionsPickerViewType == null) {
                            optionsPickerViewType = PickerManager.getInstance().getOptionPicker(mContext, "案件类型",
                                    caseDataBeans, new PickerManager.OnOptionPickerSelectedListener() {
                                        @Override
                                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                            currentTypeBean = caseDataBeans.get(options1);
                                            mTypeTv.setText(currentTypeBean.getCateName());
                                            currentKinds = currentTypeBean.getCaseSmallType();
                                            if (currentKinds != null && currentKinds.size() > 0) {
                                                currentKindBean = currentKinds.get(0);
                                                mKindTv.setText(currentKindBean.getCateName());
                                            } else {
                                                currentKinds = null;
                                                currentKindBean = null;
                                                mKindTv.setText("无");
                                            }

                                        }
                                    });
                        }
                        if (!optionsPickerViewType.isShowing()) {
                            optionsPickerViewType.show();
                        }
                    }
                }
                break;
            case PublishContract.PUBLISH_CASE:
                ToastUtils.success(mContext, getString(R.string.publish_success_tag));
                onBackPressed();
                break;
            case PublishContract.FOLLOW_CASE:
                //跟踪案件
                ToastUtils.success(mContext, getString(R.string.publish_success_tag));
                setResult(FOLLOW_CASE_FINISH);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mRegistPresent.setCallBack(null);
        releasePickerView(optionsPickerViewKinds);
        releasePickerView(optionsPickerViewType);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == RESULT_OK) {
            //地址选择
            locLat = data.getDoubleExtra("lat", 0.0);
            locLon = data.getDoubleExtra("lng", 0.0);
            locAddress = data.getStringExtra("address");
            adCode = data.getIntExtra("adCode", 0);
            mAddressTv.setText(locAddress);
            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
        }

    }

    /**
     * 释放pickerview
     *
     * @param optionsPickerView
     */
    private void releasePickerView(OptionsPickerView optionsPickerView) {
        if (optionsPickerView != null) {
            if (optionsPickerView.isShowing()) {
                optionsPickerView.dismiss();
                optionsPickerView = null;
            }
        }
    }
}
