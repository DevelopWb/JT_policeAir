package com.juntai.wisdom.policeAir.mine.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseBottomDialog;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.policeAir.bean.task.TaskDetailBean;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.home_page.PublishContract;
import com.juntai.wisdom.policeAir.home_page.PublishPresent;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 任务上报
 * @aouther ZhangZhenlong
 * @date 2020-5-16
 */
public class PublishTReportActivity extends BaseMvpActivity<PublishPresent> implements PublishContract.IPublishView,
        View.OnClickListener, SelectPhotosFragment.OnPhotoItemClick, SelectPhotosFragment.OnPicCalculateed {
    /**
     * 请输入标题
     */
    private TextView mTitleTag;
    private EditText mTitleTv;
    private LinearLayout mTitleLayout;
    private View mTitleLine;
    /**
     * 请输入描述信息
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/2000
     */
    private TextView mInputNumTv;
    /**
     * 案件地点
     */
    private TextView mAddressTag;
    /**
     * 请选择地点
     */
    private TextView mAddressTv;
    private ImageView mAddressIv;
    /**
     * 照片上传（最多3张）
     */
    private TextView mImageTag;
    private FrameLayout mPictureFragment;
    private ImageView mItemVideoPic;
    private ImageView mItemVideoTag;
    private ImageView mDeleteVedio;
    /**
     * 确认
     */
    private TextView mOkTv;

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

    private MyTaskPresent myTaskPresent;
    private TaskDetailBean taskDetailBean;
    private int taskId;
    private int taskPeopleId;//人员任务分配id
    private BaseBottomDialog baseBottomDialog;
    private BaseBottomDialog.OnItemClick onItemClick;

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
        setTitleName("上报任务");
        taskId = getIntent().getIntExtra("id",0);
        taskPeopleId = getIntent().getIntExtra("taskPeopleId", 0);
        myTaskPresent = new MyTaskPresent();
        myTaskPresent.setCallBack(this);

        mTitleTag = (TextView) findViewById(R.id.title_tag);
        mTitleTv = (EditText) findViewById(R.id.title_tv);
        mTitleLayout = (LinearLayout) findViewById(R.id.title_layout);
        mTitleLine = (View) findViewById(R.id.title_line);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mInputNumTv = (TextView) findViewById(R.id.input_num_tv);
        mAddressTag = (TextView) findViewById(R.id.address_tag);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mAddressIv = (ImageView) findViewById(R.id.address_iv);
        mImageTag = (TextView) findViewById(R.id.image_tag);
        mPictureFragment = (FrameLayout) findViewById(R.id.picture_fragment);
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedio = (ImageView) findViewById(R.id.push_case_delete_vedio_iv);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mInputNumTv.setVisibility(View.GONE);
        mTitleTv.setEnabled(false);
        mTitleTv.setFocusable(false);
        mDescriptionEt.setEnabled(false);
        mDescriptionEt.setFocusable(false);
        mAddressTv.setOnClickListener(this);
        mAddressIv.setOnClickListener(this);
        mItemVideoPic.setOnClickListener(this);
        mDeleteVedio.setOnClickListener(this);
        mOkTv.setOnClickListener(this);

        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);

        selectPhotosFragment = SelectPhotosFragment.newInstance().setPhotoTitle("").setType(1)
                .setMaxCount(3).setPhotoSpace(60).setPicCalculateCallBack(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.picture_fragment, selectPhotosFragment);
        //最后一步 记得commit
        beginTransaction.commit();
    }

    @Override
    public void initData() {
        mAddressTag.setText("所在位置");
        mImageTag.setText("照片上传（最多上传3张）");
        mTitleTag.setText("任务名称");
        setDefaultData();
        myTaskPresent.getTaskInfo(taskId, taskPeopleId, MyCenterContract.GET_TASK_DETAIL);
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
                //选择位置
                startActivityForResult(new Intent(mContext, LocationSeltionActivity.class), PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.item_video_pic:
                if (taskDetailBean != null && taskDetailBean.getData().getFlag() == 1){
                    //录制视频
                    mPresenter.recordVideo(PublishTReportActivity.this);
                }else {
                    //视频选择
                    initBottomDialog(Arrays.asList("直接拍摄", "本地视频"));
                }
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
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("address", locAddress)
                        .addFormDataPart("taskPeopleId", taskPeopleId + "")
                        .addFormDataPart("source", "0")
                        .addFormDataPart("longitude", String.valueOf(locLon))
                        .addFormDataPart("latitude", String.valueOf(locLat));
                List<String> pics = selectPhotosFragment.getPhotosPath();
                if (pics.size() < 3) {
                    ToastUtils.warning(mContext, "请选择3张图片");
                    return;
                }
                if (!StringTools.isStringValueOk(videoPath)) {
                    ToastUtils.warning(mContext, "请选择视频");
                    return;
                }
                for (int i = 0; i < pics.size(); i++) {
                    String path = pics.get(i);
                    String key = String.format("%s%d", "picture", i + 1);
                    builder.addFormDataPart(key, key, RequestBody.create(MediaType.parse("file"), new File(path)));
                }
                if (StringTools.isStringValueOk(videoPath)){
                    builder.addFormDataPart("video", "video", RequestBody.create(MediaType.parse("file"), new File(videoPath)));
                }
                mPresenter.publishTaskReport(PublishContract.PUBLISH_TASK_REPORT, builder.build());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        if (tag.equals(MyCenterContract.GET_TASK_DETAIL)){
            taskDetailBean = (TaskDetailBean) o;
            if (taskDetailBean.getData() != null){
                mTitleTv.setText(taskDetailBean.getData().getName());
                mDescriptionEt.setText(taskDetailBean.getData().getRemark());
                taskPeopleId = taskDetailBean.getData().getTaskPeopleId();
                if (taskDetailBean.getData().getFlag() == 0){
                    selectPhotosFragment.setType(0);
                }else {
                    selectPhotosFragment.setType(1);
                }
            }
        }else {
            setResult(ActivityResult);
            ToastUtils.success(mContext, getString(R.string.publish_success_tag));
            onBackPressed();
        }
    }

    /**
     * 初始化dialog
     */
    public void initBottomDialog(List<String> arrays) {
        if (baseBottomDialog == null) {
            onItemClick = new BaseBottomDialog.OnItemClick() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position) {
                        case 0:
                            //拍摄
                            mPresenter.recordVideo(PublishTReportActivity.this);
                            break;
                        case 1:
                            //选择
                            mPresenter.videoChoose(PublishTReportActivity.this);
                            break;
                    }
                    baseBottomDialog.dismiss();
                }
            };
            baseBottomDialog = new BaseBottomDialog();
            baseBottomDialog.setOnBottomDialogCallBack(onItemClick);
        }
        baseBottomDialog.setData(arrays);
        baseBottomDialog.show(getSupportFragmentManager(), "arrays");
    }


    @Override
    protected void onDestroy() {
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
        releaseDialog();
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
            ImageLoadUtil.loadImage(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
            mDeleteVedio.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 释放dialog
     */
    private void releaseDialog() {
        if (baseBottomDialog != null) {
            if (baseBottomDialog.isAdded()) {
                onItemClick = null;
                baseBottomDialog.setOnBottomDialogCallBack(null);
                if (baseBottomDialog.getDialog().isShowing()){
                    if (baseBottomDialog.getDialog().isShowing()){
                        baseBottomDialog.dismiss();
                    }
                }
            }
        }
        baseBottomDialog = null;
    }
}
