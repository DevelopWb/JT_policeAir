package com.juntai.wisdom.policeAir.mine.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.policeAir.bean.task.TaskSubmitedBean;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 上报详情
 *
 * @aouther tobato
 * @date 2020-5-16
 */
public class ReportDetailActivity extends BaseMvpActivity<MyTaskPresent> implements MyCenterContract.ITaskView, SelectPhotosFragment.OnPhotoItemClick, View.OnClickListener {

    private int reportId;
    private SelectPhotosFragment selectPhotosFragment;
    private ArrayList<String> photos = new ArrayList<>();
    private boolean hasVideo;
    /**
     * 所在位置:
     */
    private RatingBar mQualityStarRb;
    private RatingBar mSpeedStarRb;
    private TextView mItemTextListRight;
    private View mShowLine;
    private LinearLayout mFailLayout;
    private LinearLayout mScoreLayout;
    /**
     * 重新上报
     */
    private TextView mOkTv;
    TaskSubmitedBean taskSubmitedBean;
    /**
     * 照片不清晰
     */
    private TextView mFailTv;

    @Override
    protected MyTaskPresent createPresenter() {
        return new MyTaskPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_report_detail;
    }

    @Override
    public void initView() {
        setTitleName("上报详情");
        reportId = getIntent().getIntExtra("reportId", 0);
        selectPhotosFragment = (SelectPhotosFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fg);
        selectPhotosFragment.setSpanCount(4).setPhotoDelateable(false);
        mQualityStarRb = (RatingBar) findViewById(R.id.quality_star_rb);
        mSpeedStarRb = (RatingBar) findViewById(R.id.speed_star_rb);
        mItemTextListRight = (TextView) findViewById(R.id.item_text_list_right);
        mShowLine = (View) findViewById(R.id.show_line);
        mFailLayout = (LinearLayout) findViewById(R.id.fail_layout);
        mScoreLayout = findViewById(R.id.score_layout);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        mFailTv = (TextView) findViewById(R.id.fail_tv);
    }

    @Override
    public void initData() {
        mPresenter.getTaskSubmitedDetail(reportId, "");
    }


    @Override
    public void onSuccess(String tag, Object o) {
        taskSubmitedBean = (TaskSubmitedBean) o;
        if (taskSubmitedBean != null) {
            TaskSubmitedBean.DataBean dataBean = taskSubmitedBean.getData();
            updateView(dataBean.getState());
            if (dataBean != null) {
                photos.clear();
                List<String> images = new ArrayList<>();
                if (StringTools.isStringValueOk(dataBean.getVideoUrl())) {
                    images.add(dataBean.getVideoUrl());
                    hasVideo = true;
                } else {
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

                selectPhotosFragment.setMaxCount(images.size());
                selectPhotosFragment.setIcons(images);
                mItemTextListRight.setText(dataBean.getAddress());
                mQualityStarRb.setRating(Float.parseFloat(String.valueOf(dataBean.getQualityStart())));
                mSpeedStarRb.setRating(Float.parseFloat(String.valueOf(dataBean.getSpeedStart())));
            }else {
                ToastUtils.warning(this, "数据已删除！");
                onBackPressed();
            }
        }
    }

    @Override
    public void onError(String tag, Object o) {
        if (String.valueOf(o).equals("error")){
            ToastUtils.error(mContext,"无法获取数据或已删除");
            onBackPressed();
        }else {
            super.onError(tag, o);
        }
    }

    /**
     * 更新view
     *
     * @param state
     */
    private void updateView(int state) {
        switch (state) {
            case 0:
                break;
            case 1:
                mScoreLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                mFailTv.setText(taskSubmitedBean.getData().getRejectReason());
                mScoreLayout.setVisibility(View.VISIBLE);
                mFailLayout.setVisibility(View.VISIBLE);
                mShowLine.setVisibility(View.VISIBLE);
                mOkTv.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {
        String playPath = (String) adapter.getData().get(position);
        //播放视频
        if (StringTools.isStringValueOk(playPath)) {
            Intent intent = new Intent(mContext, PlayerActivity.class);
            intent.putExtra(PlayerActivity.VIDEO_PATH, playPath);
            startActivity(intent);
        }

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {
        //查看图片
        startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item", hasVideo ? position - 1 : position));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ok_tv:
                //重新上报
                startActivity(new Intent(mContext, PublishTReportActivity.class).putExtra("id", taskSubmitedBean.getData().getId()));
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
