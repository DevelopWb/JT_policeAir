package com.juntai.wisdom.dgjxb.home_page.inspection;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.AppHttpPath;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionDetailBean;
import com.juntai.wisdom.dgjxb.bean.TextListBean;
import com.juntai.wisdom.dgjxb.home_page.baseInfo.TextListAdapter;
import com.juntai.wisdom.dgjxb.home_page.InfoDetailContract;
import com.juntai.wisdom.dgjxb.home_page.InfoDetailPresent;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检详情
 *
 * @aouther ZhangZhenlong
 * @date 2020-3-31
 */
public class InspectionDetailActivity extends BaseMvpActivity<InfoDetailPresent> implements InfoDetailContract.IInfoDetailView,
        SelectPhotosFragment.OnPhotoItemClick {

    private RecyclerView mRecyclerViewTextRv;
    private ArrayList<String> photos = new ArrayList<>();
    private List<TextListBean> textListBeans = new ArrayList<>();
    private TextListAdapter textListAdapter;
    private SelectPhotosFragment selectPhotosFragment;
    private SmartRefreshLayout smartRefreshLayout;

//    private TextView desTv;

    private int inspectionId;//巡检记录id
    private InspectionDetailBean.DataBean inspectionDetail;
    private boolean hasVideo;
    private RatingBar mQualityStarRb;
    private RatingBar mSpeedStarRb;
    /**
     * 提交评分
     */
    private LinearLayout mScoreLayout;
    /**
     * 照片不清晰
     */
    private TextView mFailTv;
    private LinearLayout mFailLayout;
    private View mShowLine;

    @Override
    protected InfoDetailPresent createPresenter() {
        return new InfoDetailPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_inspection_detail;
    }

    @Override
    public void initView() {
        setTitleName("巡检详情");
        inspectionId = getIntent().getIntExtra("id", 0);
        if (inspectionId == 0) {
            finish();
        }
        selectPhotosFragment = (SelectPhotosFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fg);
        selectPhotosFragment.setSpanCount(4)
                .setPhotoDelateable(false);
        smartRefreshLayout = findViewById(R.id.case_detail_srl);
        mRecyclerViewTextRv = findViewById(R.id.case_detail_child_texts);
        mRecyclerViewTextRv.setLayoutManager(new LinearLayoutManager(this));
        textListAdapter = new TextListAdapter(R.layout.item_inspection_detail, textListBeans);
        mRecyclerViewTextRv.setAdapter(textListAdapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        smartRefreshLayout.setEnableLoadMore(false);
//        setHeadView();
        mQualityStarRb = (RatingBar) findViewById(R.id.quality_star_rb);
        mSpeedStarRb = (RatingBar) findViewById(R.id.speed_star_rb);
        mScoreLayout = (LinearLayout) findViewById(R.id.score_layout);
        mFailTv = (TextView) findViewById(R.id.fail_tv);
        mFailLayout = (LinearLayout) findViewById(R.id.fail_layout);
        mShowLine = (View) findViewById(R.id.show_line);
    }

//    /**
//     * 设置头部
//     */
//    public void setHeadView() {
//        View view = LayoutInflater.from(this).inflate(R.layout.head_layout_inspection, null);
//        desTv = view.findViewById(R.id.description_et);
//        textListAdapter.setHeaderView(view);
//    }

    @Override
    public void initData() {
        mPresenter.getInspection(inspectionId, InfoDetailContract.GET_INSPECTION_DETAIL);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        InspectionDetailBean inspectionDetailBean = (InspectionDetailBean) o;
        inspectionDetail = inspectionDetailBean.getData();
        photos.clear();
        if (inspectionDetail != null) {
            List<String> images = new ArrayList<>();
            if (StringTools.isStringValueOk(inspectionDetail.getVideoUrl())) {
                images.add(inspectionDetail.getVideoUrl());
                hasVideo = true;
            } else {
                hasVideo = false;
            }
            if (StringTools.isStringValueOk(inspectionDetail.getPictureOneUrl())) {
                images.add(inspectionDetail.getPictureOneUrl());
                photos.add(inspectionDetail.getPictureOneUrl());
            }
            if (StringTools.isStringValueOk(inspectionDetail.getPictureTwoUrl())) {
                images.add(inspectionDetail.getPictureTwoUrl());
                photos.add(inspectionDetail.getPictureTwoUrl());
            }
            if (StringTools.isStringValueOk(inspectionDetail.getPictureThreeUrl())) {
                images.add(inspectionDetail.getPictureThreeUrl());
                photos.add(inspectionDetail.getPictureThreeUrl());
            }

            selectPhotosFragment.setMaxCount(images.size());
            selectPhotosFragment.setIcons(images);
            textListAdapter.setNewData(null);
            textListAdapter.addData(new TextListBean("巡检点", inspectionDetail.getName()));
            textListAdapter.addData(new TextListBean("所在位置", inspectionDetail.getPatrolAddress()));
            textListAdapter.addData(new TextListBean("巡检人员", inspectionDetail.getRealName()));
            textListAdapter.addData(new TextListBean("巡检时间", inspectionDetail.getPatrolTime()));
            textListAdapter.addData(new TextListBean("巡检描述", inspectionDetail.getContent()));
            if (inspectionDetail.getState() == 3 || inspectionDetail.getState() == 4) {
                mScoreLayout.setVisibility(View.VISIBLE);
                mQualityStarRb.setRating(inspectionDetail.getQualityStart());
                mSpeedStarRb.setRating(inspectionDetail.getSpeedStart());
            }
            //1待处理；2处理中；3已完成；4已退回）
            if (inspectionDetail.getState() == 4) {
                mShowLine.setVisibility(View.VISIBLE);
                mFailLayout.setVisibility(View.VISIBLE);
                mFailTv.setText(inspectionDetail.getRejectReason());
            }
//            desTv.setText(inspectionDetail.getContent());
        } else {
            ToastUtils.warning(this, "数据已删除！");
            onBackPressed();
        }
    }

    @Override
    public void onError(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        if (String.valueOf(o).equals("error")){
            ToastUtils.error(mContext,"无法获取数据或已删除");
            onBackPressed();
        }else {
            super.onError(tag, o);
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
        startActivity(new Intent(mContext, ImageZoomActivity.class)
                .putExtra("paths", photos)
                .putExtra("item", hasVideo ? position - 1 : position));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
