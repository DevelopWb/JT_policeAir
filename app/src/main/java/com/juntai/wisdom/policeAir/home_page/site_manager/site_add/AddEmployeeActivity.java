package com.juntai.wisdom.policeAir.home_page.site_manager.site_add;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.policeAir.home_page.PublishContract;
import com.juntai.wisdom.policeAir.home_page.PublishPresent;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerContract;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerPresent;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 添加从业人员
 * @aouther ZhangZhenlong
 * @date 2020-7-4
 */
public class AddEmployeeActivity extends BaseMvpActivity<SiteManagerPresent> implements SiteManagerContract.ISiteManagerView,
        SelectPhotosFragment.OnPhotoItemClick, SelectPhotosFragment.OnPicCalculateed, View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    /**
     * 请输入姓名
     */
    private EditText mNameTv;
    private RadioGroup mRgSex;
    /**
     * 请输入身份证号码
     */
    private EditText mIdNumberTv;
    /**
     * 请选择居住地址
     */
    private TextView mAddressTv;
    private ImageView mAddressIv;
    /**
     * 请输入联系电话
     */
    private EditText mPhoneTv;
    /**
     * 请输入所属单位
     */
    private EditText mUnitNameTv;
    /**
     * 请输入备注信息（选填）
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/200
     */
    private TextView mInputNumTv;
    private ImageView mItemVideoPic;
    private ImageView mItemVideoTag;
    private ImageView mDeleteVedioIv;
    /**
     * 确认
     */
    private TextView mOkTv;
    private NestedScrollView mPushCaseNsv;

    //地址
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat, locLon;
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;
    //视频
    private String videoScreen;//视频封面
    private String videoPath;//视频地址

    private SelectPhotosFragment selectPhotosFragment;
    PublishPresent publishPresent;

    private int unitId;
    private String unitName;
    private int sex = 1;

    @Override
    protected SiteManagerPresent createPresenter() {
        return new SiteManagerPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_publish_employee;
    }

    @Override
    public void initView() {
        setTitleName("添加从业人员");
        unitId = getIntent().getIntExtra(AppUtils.ID_KEY,0);
        unitName = getIntent().getStringExtra("unitName");
        mNameTv = (EditText) findViewById(R.id.name_tv);
        mRgSex = (RadioGroup) findViewById(R.id.rgSex);
        mIdNumberTv = (EditText) findViewById(R.id.id_number_tv);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mAddressTv.setOnClickListener(this);
        mAddressIv = (ImageView) findViewById(R.id.address_iv);
        mAddressIv.setOnClickListener(this);
        mPhoneTv = (EditText) findViewById(R.id.phone_tv);
        mUnitNameTv = (EditText) findViewById(R.id.unit_name_tv);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mInputNumTv = (TextView) findViewById(R.id.input_num_tv);
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedioIv = (ImageView) findViewById(R.id.delete_vedio_iv);
        mItemVideoPic.setOnClickListener(this);
        mDeleteVedioIv.setOnClickListener(this);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        mPushCaseNsv = (NestedScrollView) findViewById(R.id.push_case_nsv);
        publishPresent = new PublishPresent();
        publishPresent.setCallBack(this);
        mRgSex.setOnCheckedChangeListener(this);

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

        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);
        selectPhotosFragment = SelectPhotosFragment.newInstance().setPhotoTitle("")
                .setPhotoSpace(60).setType(1).setPicCalculateCallBack(this);
        selectPhotosFragment.setMaxCount(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.picture_fragment, selectPhotosFragment);
        //最后一步 记得commit
        beginTransaction.commit();
    }

    @Override
    public void initData() {
        setDefaultData();
    }

    /**
     * 设置默认数据
     */
    public void setDefaultData() {
        bdLocation = LocateAndUpload.bdLocation;
        if (bdLocation != null) {
            locAddress = bdLocation.getAddrStr() != null ? bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() : "";
            locLat = bdLocation.getLatitude();
            locLon = bdLocation.getLongitude();
        } else {
            locAddress = "";
            locLat = 0.0;
            locLon = 0.0;
        }
        mAddressTv.setText(locAddress);
        if (StringTools.isStringValueOk(unitName)){
            mUnitNameTv.setText(unitName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.address_tv:
            case R.id.address_iv:
                startActivityForResult(new Intent(mContext, LocationSeltionActivity.class), PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.item_video_pic:
                //选择视频
                publishPresent.recordVideo(AddEmployeeActivity.this);
                break;
            case R.id.delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedioIv.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext,"点击过于频繁");
                    return;
                }
                //发布
                // TODO: 2021/6/2 暂时全部改为非必填  王彬修改 
                if (!StringTools.isStringValueOk(getTextViewValue(mNameTv))) {
                    ToastUtils.warning(mContext, "请输入姓名");
                    return;
                }
//                if (!StringTools.isStringValueOk(getTextViewValue(mIdNumberTv))) {
//                    ToastUtils.warning(mContext, "请输入身份证号码");
//                    return;
//                }
//                if (!StringTools.isStringValueOk(getTextViewValue(mAddressTv))) {
//                    ToastUtils.warning(mContext, "请输入居住地址");
//                    return;
//                }
//                if (!StringTools.isStringValueOk(getTextViewValue(mPhoneTv))) {
//                    ToastUtils.warning(mContext, "请输入联系电话");
//                    return;
//                }
//                if (!SendCodeModel.isMobileNO(getTextViewValue(mPhoneTv))) {
//                    ToastUtils.error(mContext, "手机号码格式不正确");
//                    return;
//                }
//                if (!StringTools.isStringValueOk(getTextViewValue(mUnitNameTv))) {
//                    ToastUtils.warning(mContext, "请输入所属单位");
//                    return;
//                }
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("longitude", String.valueOf(locLon))
                        .addFormDataPart("latitude", String.valueOf(locLat))
                        .addFormDataPart("clientId",String.valueOf(unitId))
                        .addFormDataPart("name",getTextViewValue(mNameTv))
                        .addFormDataPart("sex",String.valueOf(sex))
                        .addFormDataPart("idNumber",getTextViewValue(mIdNumberTv))
                        .addFormDataPart("phone",getTextViewValue(mPhoneTv))
                        .addFormDataPart("address", locAddress)
                        .addFormDataPart("remake", getTextViewValue(mDescriptionEt));
                List<String> pics = selectPhotosFragment.getPhotosPath();
                // TODO: 2021/6/2 暂时全部改为非必填  王彬修改
//                if (pics.size() < 1) {
//                    ToastUtils.warning(mContext, "请选择图片");
//                    return;
//                }

                if (pics.size() > 0) {
                    for (int i = 0; i < pics.size(); i++) {
                        String path = pics.get(i);
                        String fileName = String.format("%s%d", "picture", i + 1);
                        builder.addFormDataPart(fileName, fileName, RequestBody.create(MediaType.parse("file"), new File(path)));
                    }
                }
                if (StringTools.isStringValueOk(videoPath)) {
                    builder.addFormDataPart("video", "video", RequestBody.create(MediaType.parse("file"), new File(videoPath)));
                }
                mPresenter.addEmployee("", builder.build());
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_nan:
                //男
                sex = 1;
                break;
            case R.id.radio_nv:
                //女
                sex = 2;
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        ToastUtils.success(mContext, "保存成功！");
//        setResult(AppUtils.PUBLISH_EMPLOYEE_BACK);
        EventManager.sendStringMsg(ActionConfig.REFRASH_SITE_EMPLOYEE_LIST);
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
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
            mAddressTv.setText(locAddress);
            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
        }
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    /**
     * 动态获取到fragment中图片的宽高后 改变mItemVideoPic的宽高
     *
     * @param width
     */
    @Override
    public void picCalculateed(int width) {
        ViewGroup.LayoutParams params = mItemVideoPic.getLayoutParams();
        params.width = DisplayUtil.dp2px(mContext, width);
        params.height = DisplayUtil.dp2px(mContext, width);
        mItemVideoPic.setLayoutParams(params);
    }


    /**
     * 视频录制成功回调广播
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            videoPath = intent.getStringExtra("videoUri");
            videoScreen = intent.getStringExtra("videoScreenshotUri");
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
            mDeleteVedioIv.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }
}
