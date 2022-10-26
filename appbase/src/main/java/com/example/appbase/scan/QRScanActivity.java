package com.example.appbase.scan;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.example.appbase.R;
import com.example.appbase.base.BaseAppModuleActivity;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.JsonUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;

import java.util.List;

/**
 * 扫码
 *
 * @date 2020-3-18
 * @update 2020-06-08 tobato
 */
public class QRScanActivity extends BaseAppModuleActivity implements View.OnClickListener, OnCaptureCallback {
    private SurfaceView mSurfaceView;
    private ViewfinderView mViewfinderView;
    private ImageView mZxingPic;
    private ImageView mIvTorch;
    private ImageView mZxingBackBtn;
    private CaptureHelper mCaptureHelper;

    private int SELECT_PIC_RESULT = 1001;

    //    private int pageType;//0扫码，1扫描巡检内容

    @Override
    public int getLayoutView() {
        return R.layout.activity_qrscan;
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        mImmersionBar.reset().statusBarDarkFont(false).init();
        //        pageType = getIntent().getIntExtra("pageType",0);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mViewfinderView = (ViewfinderView) findViewById(R.id.viewfinderView);
        mZxingPic = (ImageView) findViewById(R.id.zxing_pic);
        mZxingPic.setOnClickListener(this);
        mIvTorch = (ImageView) findViewById(R.id.ivTorch);
        mZxingBackBtn = (ImageView) findViewById(R.id.zxing_back_btn);
        mZxingBackBtn.setOnClickListener(this);
        mIvTorch.setOnClickListener(this);

        mCaptureHelper = new CaptureHelper(this, mSurfaceView, mViewfinderView, null);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.characterSet("ISO-8859-1");
        mCaptureHelper.onCreate();
        mCaptureHelper.playBeep(true);
    }

    @Override
    public void initData() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.zxing_pic) {
            choseImage(0, this, 1);
        } else if (id == R.id.zxing_back_btn) {
            finish();
        } else if (id == R.id.ivTorch) {
            mIvTorch.setSelected(!mIvTorch.isSelected());
            mCaptureHelper.getCameraManager().setTorch(mIvTorch.isSelected());
        }
    }


    @Override
    protected void onPicsAndEmpressed(List icons) {
        super.onPicsAndEmpressed(icons);

        if (icons != null && icons.size() > 0) {
            String pic = (String) icons.get(0);
            resolveQrcodeFromPic(pic);

        }

    }

    private void resolveQrcodeFromPic(String pic) {
        String result = null;
        Bitmap bitmap = FileCacheUtils.getImageBitmap(pic);
        //“QRCODE_SCAN_TYPE ”和“ DATAMATRIX_SCAN_TYPE表示只扫描QR和Data Matrix的码
        HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE).setPhotoMode(true).create();
        HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(mContext, bitmap, options);
        //处理扫码结果
        if (hmsScans != null && hmsScans.length > 0) {
            result = hmsScans[0].showResult;
            resolveQrcode(result);
        } else {
            ToastUtils.toast(mContext, "没有检测到有效的二维码");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mCaptureHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
        mCaptureHelper = null;
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCaptureHelper.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onResultCallback(String result) {
        String resultData = JsonUtil.transcoding(result);
        if (JsonUtil.isNumber(resultData)) {
            mCaptureHelper.onPause();
            mCaptureHelper.onResume();
        } else {
            resolveQrcode(resultData);
        }
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onError(String tag, Object o) {

    }
}
