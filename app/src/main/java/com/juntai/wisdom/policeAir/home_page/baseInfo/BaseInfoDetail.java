package com.juntai.wisdom.policeAir.home_page.baseInfo;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述   详情的父类
 * @date 2020/4/5 15:24
 */
public abstract class BaseInfoDetail<P extends BasePresenter> extends BaseMvpActivity<P> implements SelectPhotosFragment.OnPhotoItemClick {
    private RecyclerView recyclerViewRv;
    private List<TextListBean> textListBeans = new ArrayList<>();
    protected TextListAdapter textListAdapter;
    protected int INFO_ID;//信息的id
    protected SelectPhotosFragment selectPhotosFragment;
    protected SmartRefreshLayout smartRefreshLayout;
    protected boolean hasVideo = false;//是否有视频文件
    protected TextView mDesTv;

    @Override
    public int getLayoutView() {
        return R.layout.activity_case_detailschild;
    }


    @Override
    public void initView() {
        setTitleName(getTitleContent());
        selectPhotosFragment = (SelectPhotosFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fg);
        selectPhotosFragment.setSpanCount(4)
                .setPhotoDelateable(false);
        smartRefreshLayout = findViewById(R.id.case_detail_srl);
        recyclerViewRv = findViewById(R.id.case_detail_child_texts);
        mDesTv = findViewById(R.id.description_tv);
        recyclerViewRv.setLayoutManager(new LinearLayoutManager(this));
//        textListAdapter = new TextListAdapter(R.layout.item_case_detail_child, textListBeans);
        textListAdapter = new TextListAdapter(R.layout.item_inspection_detail, textListBeans);
        recyclerViewRv.setAdapter(textListAdapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        smartRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void onError(String tag, Object o) {
        smartRefreshLayout.finishRefresh();
        super.onError(tag, o);
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
                .putExtra("paths", getPhotos())
                .putExtra("item", hasVideo ?position-1:position));
    }

    protected abstract ArrayList<String> getPhotos();

    protected abstract String getTitleContent();
}
