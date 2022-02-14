package com.juntai.wisdom.dgjxb.home_page.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;

public class PanoramaActivity extends BaseActivity implements View.OnClickListener
        , PanoramaViewListener {
    private PanoramaView panoramaView;

    @Override
    public int getLayoutView() {
        BMapManager bMapManager = new BMapManager(this.getApplication());
        bMapManager.init(i -> LogUtil.d(String.valueOf(i)));
        return R.layout.activity_panorama;
    }

    double lat, lng;

    @Override
    public void initView() {
        panoramaView = findViewById(R.id.panorama_view);
        setTitleName("全景图");
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        try {
            panoramaView.setPanorama(lng, lat);
        }catch (Exception e){
            ToastUtils.toast(mContext,"定位发生错误,请稍后重试");
        }
        findViewById(R.id.high).setOnClickListener(this);
        findViewById(R.id.low).setOnClickListener(this);
        panoramaView.setPanoramaViewListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.high) {
            panoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);
            panoramaView.setPanorama(lng,lat);
        } else if (v.getId() == R.id.low) {
            panoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionLow);
            panoramaView.setPanorama(lng,lat);
        }
    }

    @Override
    protected void onPause() {
        panoramaView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        panoramaView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        panoramaView.destroy();
        panoramaView = null;
        super.onDestroy();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDescriptionLoadEnd(String s) {

    }

    @Override
    public void onLoadPanoramaBegin() {

    }

    @Override
    public void onLoadPanoramaEnd(String s) {

    }

    @Override
    public void onLoadPanoramaError(String s) {

    }

    @Override
    public void onMessage(String s, int i) {

    }

    @Override
    public void onCustomMarkerClick(String s) {

    }

    @Override
    public void onMoveStart() {

    }

    @Override
    public void onMoveEnd() {

    }
}
