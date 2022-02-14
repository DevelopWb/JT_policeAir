package com.juntai.wisdom.dgjxb.home_page.law_case;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.NavigationDialog;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.case_bean.CaseInfoBean;
import com.juntai.wisdom.dgjxb.home_page.map.MapContract;
import com.juntai.wisdom.dgjxb.home_page.map.MapPresenter;
import com.juntai.wisdom.dgjxb.home_page.PublishContract;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.GlideImageLoader;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

/**
 * 案件信息 (详情)
 * Created by Ma
 * update  tobato  2020/03/26
 * on 2019/4/16
 */
public class CaseInfoActivity extends BaseMvpActivity<MapPresenter> implements MapContract.View {
    private int id;
    private TextView placeTv, timeTv, typeTv, kindTv, followStatusTv, mCaseDesTv, gridTv;
    TextView trackingBtn, navigationBtn;
    private Banner banner;
    private CaseFollowListAdapter caseDetailsAdapter;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private NavigationDialog navigationDialog;
    private CaseInfoBean.DataBean dataBean;
    private GlideImageLoader imageLoader;
    protected boolean hasVideo = false;//是否有视频文件
    private ArrayList<String> images;

    @Override
    public int getLayoutView() {
        return R.layout.case_info_layout;
    }

    @Override
    public void initView() {
        setTitleName("案件信息");
        id = getIntent().getIntExtra("id", 0);
        /*anner*/
        banner = findViewById(R.id.banner);
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
        //recycleview
        smartRefreshLayout = findViewById(R.id.smartrefreshlayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        caseDetailsAdapter = new CaseFollowListAdapter(R.layout.item_case, new ArrayList());
        recyclerView.setAdapter(caseDetailsAdapter);
        caseDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, CaseDetailActivity.class)
                        .putExtra(AppUtils.ID_KEY, caseDetailsAdapter.getData().get(position).getChildId())
                        .putExtra("CaseDetailsChildActivityContent", caseDetailsAdapter.getData().get(position).getCaseContent()));
            }
        });
        setHeadView();
        navigationDialog = new NavigationDialog();
    }

    /**
     * 设置头部
     */
    public void setHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_case_details, null);
        placeTv = view.findViewById(R.id.case_details_place);
        timeTv = view.findViewById(R.id.case_details_time);
        mCaseDesTv = view.findViewById(R.id.case_des_tv);
        typeTv = view.findViewById(R.id.case_details_type);
        kindTv = view.findViewById(R.id.case_kind_tv);
        followStatusTv = view.findViewById(R.id.case_follow_tv);
        trackingBtn = view.findViewById(R.id.case_details_tracking);
        navigationBtn = view.findViewById(R.id.case_details_navigation);
        gridTv = view.findViewById(R.id.case_grid_tv);
        caseDetailsAdapter.setHeaderView(view);
        trackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getCaseType() == 3) {
                    ToastUtils.toast(mContext, "案件已完成，不需要跟踪");
                } else {
                    startActivityForResult(new Intent(CaseInfoActivity.this, PublishCaseActivity.class)
                            .putExtra(PublishCaseActivity.CASE_INFO_DATA_KEY, dataBean), PublishCaseActivity.FOLLOW_CASE_FINISH);
                }
            }
        });
        navigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationDialog.showMenu(getSupportFragmentManager(), dataBean.getLatitude(), dataBean.getLongitude(), dataBean.getAddress());
            }
        });
    }

    @Override
    public void initData() {
        //测试id
        //        id = 127;
        mPresenter.getCaseInfo(PublishContract.CASE_INFO, id);
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
    public void onSuccess(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        switch (tag) {
            case PublishContract.CASE_INFO:
                CaseInfoBean caseDetailBean = (CaseInfoBean) o;
                if (caseDetailBean != null && caseDetailBean.getData() != null && caseDetailBean.getData().getId() != 0) {
                    dataBean = caseDetailBean.getData();
                    images.clear();
                    if (StringTools.isStringValueOk(dataBean.getVideo())) {
                        hasVideo = true;
                        images.add(dataBean.getVideo());
                    } else {
                        hasVideo = false;
                    }
                    if (StringTools.isStringValueOk(dataBean.getPhotoOne())) {
                        images.add(dataBean.getPhotoOne());
                    }
                    if (StringTools.isStringValueOk(dataBean.getPhotoTwo())) {
                        images.add(dataBean.getPhotoTwo());
                    }
                    if (StringTools.isStringValueOk(dataBean.getPhotoThree())) {
                        images.add(dataBean.getPhotoThree());
                    }
                    imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
                        @Override
                        public void onFullScreen() {
                            if (dataBean != null && StringTools.isStringValueOk(dataBean.getVideo())) {
                                Intent intent = new Intent(mContext, PlayerActivity.class);
                                intent.putExtra(PlayerActivity.VIDEO_PATH, dataBean.getVideo());
                                startActivity(intent);
                            }

                        }
                    });
                    banner.setImages(images).setImageLoader(imageLoader).start();

                    placeTv.setText(dataBean.getAddress());
                    timeTv.setText(dataBean.getHappenDate());
                    typeTv.setText(dataBean.getBigName());
                    kindTv.setText(dataBean.getSmallName());
                    mCaseDesTv.setText(dataBean.getCaseContent());
                    gridTv.setText(dataBean.getGridName());
                    followStatusTv.setText(mPresenter.getCaseTypeName(dataBean.getCaseType()));
                    caseDetailsAdapter.setNewData(dataBean.getCaseChildList());
                } else {
                    ToastUtils.warning(this, "数据已删除！");
                    finish();
                }
                break;
            default:
                break;
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (PublishCaseActivity.FOLLOW_CASE_FINISH == resultCode) {
            mPresenter.getCaseInfo(PublishContract.CASE_INFO, id);
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

}
