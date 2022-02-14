package com.juntai.wisdom.dgjxb.mine.task;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.task.TaskDetailBean;
import com.juntai.wisdom.dgjxb.mine.MyCenterContract;
import com.juntai.wisdom.dgjxb.utils.GlideImageLoader;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

/**
 * 任务信息（已上报）
 *
 * @aouther ZhangZhenlong
 * @date 2020-5-16
 */
public class TaskInfoActivity extends BaseMvpActivity<MyTaskPresent> implements MyCenterContract.ITaskView {
    /**
     * 标题
     */
    private Banner banner;
    private TextView mTitleTv;
    private TextView mDescriptionTv;
    private RecyclerView mRecordRecyclerview;
    TaskReportListAdapter reportListAdapter;
    private int taskId;//任务id
    private int taskPeopleId;//人员任务分配id
    private TaskDetailBean taskDetailBean;
    private SmartRefreshLayout mSmartrefreshlayout;

    private GlideImageLoader imageLoader;
    protected boolean hasVideo = false;//是否有视频文件
    private ArrayList<String> images;

    @Override
    protected MyTaskPresent createPresenter() {
        return new MyTaskPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_task_detail;
    }

    @Override
    public void initView() {
        setTitleName("任务信息");
        taskId = getIntent().getIntExtra("id", 0);
        taskPeopleId = getIntent().getIntExtra("taskPeopleId", 0);
        mTitleTv = findViewById(R.id.title_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mRecordRecyclerview = findViewById(R.id.record_recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        banner = findViewById(R.id.banner);

        reportListAdapter = new TaskReportListAdapter(R.layout.item_mycollect, new ArrayList<>());
        mRecordRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecordRecyclerview.setAdapter(reportListAdapter);

        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);

        reportListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ReportDetailActivity.class)
                        .putExtra("reportId", reportListAdapter.getData().get(position).getId()));
            }
        });

        images = new ArrayList<>();
        banner.isAutoPlay(false);
        ArrayList<String> photos = new ArrayList<>();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //查看图片
                photos.clear();
                photos.addAll(images);
                if (hasVideo) {
                    photos.remove(0);
                }
                startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item", hasVideo ? position - 1 : position));

            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getTaskInfo(taskId, taskPeopleId, MyCenterContract.GET_TASK_DETAIL);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        taskDetailBean = (TaskDetailBean) o;
        mTitleTv.setText(taskDetailBean.getData().getName());
        mDescriptionTv.setText(taskDetailBean.getData().getRemark());
        reportListAdapter.getData().clear();
        reportListAdapter.addData(taskDetailBean.getData().getMissionList());

        images.clear();
        if (StringTools.isStringValueOk(taskDetailBean.getData().getVideoUrl())) {
            hasVideo = true;
            images.add(taskDetailBean.getData().getVideoUrl());
        } else {
            hasVideo = false;
        }
        if (StringTools.isStringValueOk(taskDetailBean.getData().getPictureOneUrl())) {
            images.add(taskDetailBean.getData().getPictureOneUrl());
        }
        if (StringTools.isStringValueOk(taskDetailBean.getData().getPictureTwoUrl())) {
            images.add(taskDetailBean.getData().getPictureTwoUrl());
        }
        if (StringTools.isStringValueOk(taskDetailBean.getData().getPictureThreeUrl())) {
            images.add(taskDetailBean.getData().getPictureThreeUrl());
        }
        imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
            @Override
            public void onFullScreen() {
                if (taskDetailBean != null && StringTools.isStringValueOk(taskDetailBean.getData().getVideoUrl())) {
                    Intent intent = new Intent(mContext, PlayerActivity.class);
                    intent.putExtra(PlayerActivity.VIDEO_PATH, taskDetailBean.getData().getVideoUrl());
                    startActivity(intent);
                }
            }
        });
        banner.setImages(images).setImageLoader(imageLoader).start();
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

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (banner != null) {
            banner.releaseBanner();
            banner.removeAllViews();
            banner.setOnBannerListener(null);
            if (imageLoader != null) {
                imageLoader.setOnFullScreenCallBack(null);
                imageLoader.release();
            }
        }
        banner = null;
    }
}
