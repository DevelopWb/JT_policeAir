package com.juntai.wisdom.policeAir.home_page.inspection;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.bdmap.act.NavigationDialog;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.inspection.InspectionPointInfoBean;
import com.juntai.wisdom.policeAir.bean.inspection.InspectionRecordBean;
import com.juntai.wisdom.policeAir.home_page.map.MapContract;
import com.juntai.wisdom.policeAir.home_page.map.MapPresenter;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.GlideImageLoader;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.policeAir.utils.UrlFormatUtil;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

/**
 * @aouther tobato
 * @description 描述  巡检点信息
 * @date 2020/5/12 15:58
 */
public class InspectionInfoActivity extends BaseMvpActivity<MapPresenter> implements MapContract.View {
    private int inspectionPointId;
    TextView trackingBtn, navigationBtn;
    private Banner banner;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private GlideImageLoader imageLoader;
    private ArrayList<String> images;
    private InspectionRecordAdapter adapter;
    private TextView mInspectionNameTv;
    private TextView mInspectionAddrTv;
    private TextView mInspectionPhoneTv;
    private LinearLayout mRecordTitleLl;
    private int currentPage = 1;//当前页数
    private int limit = 15;//默认一次请求15条记录
    NavigationDialog navigationDialog;
    private ImageView mNavigationIv;
    private InspectionPointInfoBean.DataBean pointBean;
    protected boolean hasVideo = false;//是否有视频文件

    @Override
    public int getLayoutView() {
        return R.layout.case_info_layout;
    }

    @Override
    public void initView() {
        navigationDialog = new NavigationDialog();
        setTitleName("巡检点信息");
        images = new ArrayList<>();

        inspectionPointId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        /*anner*/
        banner = findViewById(R.id.banner);
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

        smartRefreshLayout = findViewById(R.id.smartrefreshlayout);
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            getRecord(false);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            getRecord(false);
        });

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InspectionRecordAdapter(R.layout.item_inspection_record);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InspectionRecordBean.DataBean.DatasBean datasBean = (InspectionRecordBean.DataBean.DatasBean) adapter.getData().get(position);
                startActivity(new Intent(InspectionInfoActivity.this, InspectionDetailActivity.class).putExtra("id", datasBean.getPatrolId()));
            }
        });
        initRecyclerview(mRecyclerView, adapter, LinearLayoutManager.VERTICAL);
        setHeadView();
        View view = getAdapterEmptyView("暂无巡检记录", -1);
        LinearLayout linearLayout = view.findViewById(R.id.noneView_root_ll);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = DisplayUtil.dp2px(mContext,200);
        linearLayout.setLayoutParams(params);
        adapter.setEmptyView(view);
    }

    /**
     * 设置头部
     */
    public void setHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.inspection_info_head_layout, null);
        mInspectionNameTv = view.findViewById(R.id.inspection_name_tv);
        mInspectionAddrTv = view.findViewById(R.id.inspection_addr_tv);
        mInspectionPhoneTv = view.findViewById(R.id.inspection_phone_tv);
        mRecordTitleLl = view.findViewById(R.id.inspection_record_ll);
        mNavigationIv = view.findViewById(R.id.inspection_navigation_iv);
        mNavigationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pointBean != null) {
                    navigationDialog.showMenu(getSupportFragmentManager(), pointBean.getLatitude(), pointBean.getLongitude(), pointBean.getAddress());
                }

            }
        });
        adapter.setHeaderView(view);
    }

    @Override
    public void initData() {
        mPresenter.getInspectionPointInfo(inspectionPointId, MapContract.INSPECTION_POINT_INFO);
        getRecord(false);
    }

    private void getRecord(boolean isShowProgress){
        mPresenter.getInspectionRecord(inspectionPointId, currentPage, limit, MapContract.INSPECTIONS_RECORD, isShowProgress);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    public void onError(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        switch (tag) {
            case MapContract.INSPECTION_POINT_INFO:
                InspectionPointInfoBean inspectionPointInfoBean =  Hawk.get(MapContract.INSPECTION_POINT_INFO+inspectionPointId);
                if (inspectionPointInfoBean != null) {
                    onSuccessInspectionPointInfoLogic(inspectionPointInfoBean);
                }
                break;
            case MapContract.INSPECTIONS_RECORD:
                break;
            default:
                break;
        }
        super.onError(tag, o);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        switch (tag) {
            case MapContract.INSPECTION_POINT_INFO:
                InspectionPointInfoBean inspectionPointInfoBean = (InspectionPointInfoBean) o;
                if (inspectionPointInfoBean != null && inspectionPointInfoBean.getData() != null) {
                    Hawk.put(MapContract.INSPECTION_POINT_INFO+inspectionPointId,inspectionPointInfoBean);
                    onSuccessInspectionPointInfoLogic(inspectionPointInfoBean);
                }
                break;
            case MapContract.INSPECTIONS_RECORD:
                //巡检记录
                InspectionRecordBean recordBean = (InspectionRecordBean) o;
                InspectionRecordBean.DataBean dataBean = recordBean.getData();
                if (dataBean != null) {
                    List<InspectionRecordBean.DataBean.DatasBean> arrays = dataBean.getDatas();
                    if (arrays != null) {
                        if (currentPage == 1) {
                            adapter.setNewData(null);
                        }
                        if (arrays.size() < limit) {
                            smartRefreshLayout.finishLoadMoreWithNoMoreData();
                        }else {
                            smartRefreshLayout.setNoMoreData(false);
                        }
                        adapter.addData(arrays);
                    }
                }
                break;
            default:
                break;
        }

    }

    /**
     * 巡检点信息接口成功后的逻辑
     * @param inspectionPointInfoBean
     */
    private void onSuccessInspectionPointInfoLogic(InspectionPointInfoBean inspectionPointInfoBean) {
        pointBean = inspectionPointInfoBean.getData();
        if (pointBean != null) {
            images.clear();
            if (StringTools.isStringValueOk(pointBean.getVideoUrl())) {
                hasVideo = true;
                images.add(pointBean.getVideoUrl());
            } else {
                hasVideo = false;
            }
            String imageIds = pointBean.getImgId();
            if (StringTools.isStringValueOk(imageIds)) {
                if (imageIds.contains(",")) {
                    String[] ids = imageIds.split(",");
                    for (String id : ids) {
                        images.add(UrlFormatUtil.getInspectionOriginalImg(Integer.parseInt(id)));
                    }
                } else {
                    images.add(UrlFormatUtil.getInspectionOriginalImg(Integer.parseInt(imageIds)));
                }

            }
            imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
                @Override
                public void onFullScreen() {
                    if (pointBean != null && StringTools.isStringValueOk(pointBean.getVideoUrl())) {
                        Intent intent = new Intent(mContext, PlayerActivity.class);
                        intent.putExtra(PlayerActivity.VIDEO_PATH, pointBean.getVideoUrl());
                        startActivity(intent);
                    }
                }
            });
            banner.setImages(images).setImageLoader(imageLoader).start();
            mInspectionNameTv.setText(pointBean.getName());
            mInspectionAddrTv.setText(pointBean.getAddress());
            mInspectionPhoneTv.setText(pointBean.getContactsPhone());
        }
    }

    @Override
    public void onDestroy() {
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
}