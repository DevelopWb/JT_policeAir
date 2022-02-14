package com.juntai.wisdom.dgjxb.home_page.inspection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.GsonTools;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.Logger;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionForScanBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionPointInfoBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionScanResult;
import com.juntai.wisdom.dgjxb.home_page.PublishContract;
import com.juntai.wisdom.dgjxb.home_page.PublishPresent;
import com.juntai.wisdom.dgjxb.utils.DateUtil;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 发布巡检
 *
 * @aouther ZhangZhenlong
 * @date 2020-3-16
 */
public class PublishInspectionActivity extends BaseMvpActivity<PublishPresent> implements PublishContract.IPublishView,
        View.OnClickListener,
        SelectPhotosFragment.OnPhotoItemClick, SelectPhotosFragment.OnPicCalculateed {

    /**
     * 请输入标题
     */
    private EditText mTitleTv;
    private LinearLayout mTitleLayout;
    private View mTitleLine;

    /**
     * 请输入描述信息
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/200
     */
    private TextView mInputNumTv;
    /**
     * 请选择地点
     */
    private TextView mAddressTv;
    private ImageView mAddressIv;
    private FrameLayout mPictureFragment;
    private ImageView mItemVideoPic, mDeleteVedio, mItemVideoTag;
    /**
     * 确认
     */
    private TextView mOkTv;
    private TextView imageTag, addressTag;

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

    private String xjContent;
    private SelectPhotosFragment selectPhotosFragment;
    private int imgMaxCount = 3;//图片数量
    private InspectionScanResult inspectionScanResult;//二维码直接读取巡检点信息
    private InspectionForScanBean.DataBean pointInfoBean;//根据id网络获取巡检点信息
    /**
     * 资讯标题
     */
    private TextView mTitleTag;


    @Override
    protected PublishPresent createPresenter() {
        return new PublishPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_edit_inspection;
    }

    @Override
    public void initView() {
        getScanResult();
        mTitleTag = (TextView) findViewById(R.id.title_tag);
        mTitleTv = (EditText) findViewById(R.id.title_tv);
        mTitleTv.setEnabled(false);
        mTitleTv.setFocusable(false);
        mTitleLayout = (LinearLayout) findViewById(R.id.title_layout);
        mTitleLine = (View) findViewById(R.id.title_line);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mInputNumTv = (TextView) findViewById(R.id.input_num_tv);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mAddressIv = findViewById(R.id.address_iv);
        mAddressTv.setOnClickListener(this);
        mAddressIv.setOnClickListener(this);
        mPictureFragment = (FrameLayout) findViewById(R.id.picture_fragment);
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedio = (ImageView) findViewById(R.id.push_case_delete_vedio_iv);
        mItemVideoPic.setOnClickListener(this);
        mDeleteVedio.setOnClickListener(this);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        imageTag = findViewById(R.id.image_tag);
        addressTag = findViewById(R.id.address_tag);
        mDescriptionEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

        mDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

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
                .setPhotoSpace(60).setPicCalculateCallBack(this);

        setTitleName("发布巡检");
        mTitleTag.setText("巡检点");
        mTitleTv.setHint("请输入巡检点名称");
        mInputNumTv.setText("已输入0/200");
        imgMaxCount = 3;
        addressTag.setText("巡检地点");
        imageTag.setText("照片上传（需上传3张）");
        selectPhotosFragment.setType(1);
        if (inspectionScanResult != null) {
            mTitleTv.setText(inspectionScanResult.getName());
        }

        selectPhotosFragment.setMaxCount(imgMaxCount);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.picture_fragment, selectPhotosFragment);
        //最后一步 记得commit
        beginTransaction.commit();
    }

    /**
     * 获取巡检点信息
     */
    public void getScanResult(){
        xjContent = getIntent().getStringExtra("result");
        Logger.e("xjContent",xjContent+"--");
        if (StringTools.isStringValueOk(xjContent)) {
            if (xjContent.contains("http") && xjContent.contains("id")){
                //https://server.dgjpcs.cn/xj/?id=7e7q5c
                String[] results = xjContent.split("id");
                if (results.length >1){
                    String resultId = results[1].substring(1);
                    mPresenter.getInspectionInfo(PublishContract.GET_INSPECTION_INFO_SCAN, resultId);
                }else {
                    ToastUtils.warning(mContext, "无此巡检点！");
                    onBackPressed();
                }
            }else {
                inspectionScanResult = GsonTools.changeGsonToBean(xjContent, InspectionScanResult.class);
                if (inspectionScanResult == null || inspectionScanResult.getId() == 0) {
                    ToastUtils.warning(mContext, "无此巡检点！");
                    onBackPressed();
                }
            }
        }
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_iv:
            case R.id.address_tv:
                startActivityForResult(new Intent(mContext, LocationSeltionActivity.class).putExtra(LocationSeltionActivity.CAN_MOVE_CHANGE, false),
                        PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.item_video_pic:
                //选择视频
                mPresenter.recordVideo(PublishInspectionActivity.this);
                break;
            case R.id.push_case_delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedio.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext,"点击过于频繁");
                    return;
                }
                //发布
                if (!StringTools.isStringValueOk(getTextViewValue(mAddressTv))) {
                    ToastUtils.warning(mContext, "请选择地点");
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mTitleTv))) {
                    ToastUtils.warning(mContext, "请输入巡检点名称");
                    return;
                }
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("source","0")//信息来源(0：警小宝）（1：巡小管）（2：邻小帮）（3：微信小程序）
                        .addFormDataPart("longitude", String.valueOf(locLon))
                        .addFormDataPart("latitude", String.valueOf(locLat))
                        .addFormDataPart("patrolName", getTextViewValue(mTitleTv))
                        .addFormDataPart("content", getTextViewValue(mDescriptionEt));
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
                        String key = String.format("%s%d", "picture", i + 1);
                        builder.addFormDataPart(key, key, RequestBody.create(MediaType.parse("file"), new File(path)));
                    }
                }
                if (StringTools.isStringValueOk(videoPath)) {
                    builder.addFormDataPart("video", "video", RequestBody.create(MediaType.parse("file"), new File(videoPath)));
                }
                builder.addFormDataPart("patrolAddress", locAddress)
                        .addFormDataPart("patrolTime", DateUtil.getCurrentTime());
                if (inspectionScanResult != null) {
                    builder.addFormDataPart("clientId", String.valueOf(inspectionScanResult.getId()));
                    mPresenter.publishInspection(PublishContract.PUBLISH_INSPECTION, builder.build());
                }else if (pointInfoBean != null){
                    builder.addFormDataPart("clientId", String.valueOf(pointInfoBean.getClientId()));
                    mPresenter.publishInspection(PublishContract.PUBLISH_INSPECTION, builder.build());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case PublishContract.GET_INSPECTION_INFO_SCAN:
                InspectionForScanBean inspectionForScanBean = (InspectionForScanBean) o;
                pointInfoBean = inspectionForScanBean.getData();
                mTitleTv.setText(pointInfoBean.getName());
                break;
            case PublishContract.PUBLISH_INSPECTION:
                BaseResult baseResult = (BaseResult) o;
                ToastUtils.success(mContext, baseResult.getMessage());
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        if (PublishContract.GET_INSPECTION_INFO_SCAN.equals(tag)){
            onBackPressed();
        }
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
        } else if (requestCode == PublishContract.SELECT_VIDEO_RESULT && resultCode == RESULT_OK) {
            List<String> paths = Matisse.obtainPathResult(data);
            if (paths != null && paths.size() > 0) {
                if (new File(paths.get(0)).length() / 1024 < 10000) {
                    videoPath = paths.get(0);
                    videoScreen = paths.get(0);
                    LogUtil.e("size-->" + new File(videoPath));
                    ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
                    mDeleteVedio.setVisibility(View.VISIBLE);
                    mItemVideoTag.setVisibility(View.VISIBLE);
                } else {
                    ToastUtils.warning(this, "请选择小于10m的视频");
                }
            }
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
            mDeleteVedio.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }
}
