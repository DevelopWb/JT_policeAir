package com.juntai.wisdom.policeAir.home_page.news.news_comment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.CommentListBean;
import com.juntai.wisdom.policeAir.home_page.InfoDetailContract;
import com.juntai.wisdom.policeAir.home_page.camera.PlayPresent;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.home_page.news.news_personal_homepage.PersonalHomepageActivity;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * 资讯评论回复列表
 * Created by Ma
 * on 2019/7/20
 */
public class CommentChildActivity extends BaseMvpActivity<NewsPresent> implements InfoDetailContract.IInfoDetailView {
    SmartRefreshLayout smartRefeshLayout;
    RecyclerView recyclerView;
    CommentChildAdapter commentAdapter;
    int page = 1,pagesize = 20;
    int comtId;
    int type = 8;//8：资讯,0:监控
    int unReadId;
    private PlayPresent playPresent;

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        setTitleName("回复列表");
        comtId = getIntent().getIntExtra("id",0);
        type = getIntent().getIntExtra("type",8);
        unReadId = getIntent().getIntExtra("unReadId",0);
        //
        smartRefeshLayout = findViewById(R.id.smartrefreshlayout);
        smartRefeshLayout.setBackgroundColor(getResources().getColor(R.color.white));
        //刷新
        smartRefeshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
            }
        });
        //加载更多
        smartRefeshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page ++;
                getData();
            }
        });
        smartRefeshLayout.setEnableLoadMore(false);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        commentAdapter = new CommentChildAdapter(R.layout.item_comment_news_child, new ArrayList());
        recyclerView.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_comment_user_image:
                        if (type == 8){
                            startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY,
                                    commentAdapter.getItem(position).getUserId()));
                        }
                        break;
                    case R.id.item_comment_iv:
                        //查看大图
                        ArrayList<String> images = new ArrayList<>();
                        images.add(commentAdapter.getItem(position).getCommentUrl());
                        LogUtil.e("images--->" + images.size());
                        mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
                                .putExtra("paths", images));
                        break;
                }
            }
        });

        playPresent = new PlayPresent();
        playPresent.setCallBack(this);
    }

    @Override
    public void initData() {
        page = 1;
        getData();
    }
    public void getData(){
        switch (type){
            case 0:
                playPresent.getCommentChildList(comtId, unReadId, pagesize, page, InfoDetailContract.GET_COMMENT_CHILD_LIST);
                break;
            case 8:
                mPresenter.getCommentChildList(comtId, unReadId, pagesize, page, InfoDetailContract.GET_COMMENT_CHILD_LIST);
                break;
        }
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        CommentListBean commentListBean = (CommentListBean) o;
        if (page == 1){
            commentAdapter.getData().clear();
        }
        if (commentListBean.getData().getDatas().size() < pagesize){
            smartRefeshLayout.finishLoadMoreWithNoMoreData();
        }else {
            smartRefeshLayout.setNoMoreData(false);
        }
        smartRefeshLayout.finishRefresh();
        smartRefeshLayout.finishLoadMore();
        commentAdapter.addData(commentListBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        smartRefeshLayout.finishRefresh();
        smartRefeshLayout.finishLoadMore();
    }

    @Override
    protected void onDestroy() {
        playPresent.setCallBack(null);
        super.onDestroy();
    }
}
