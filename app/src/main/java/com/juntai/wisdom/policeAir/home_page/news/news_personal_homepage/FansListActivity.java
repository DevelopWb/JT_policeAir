package com.juntai.wisdom.policeAir.home_page.news.news_personal_homepage;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.news.NewsFansListBean;
import com.juntai.wisdom.policeAir.home_page.news.NewsContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 粉丝、关注列表
 * @aouther ZhangZhenlong
 * @date 2020-8-18
 */
public class FansListActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView {
    List<NewsFansListBean.DataBean.DatasBean> fansList = new ArrayList<>();
    FansListAdapter fansListAdapter;
    int type = 1;//1粉丝，2关注
    int page = 1, pagesize = 20;
    private int authorId;//作者id

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private int nowPosition;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.normal_recycleview_layout;
    }

    @Override
    public void initView() {
        authorId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        type = getIntent().getIntExtra("type", 1);
        if (authorId == MyApp.getUid()){
            if (type == 1) {
                setTitleName("我的粉丝");
            } else {
                setTitleName("我的关注");
            }
        }else {
            if (type == 1) {
                setTitleName("TA的粉丝");
            } else {
                setTitleName("TA的关注");
            }
        }
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });

        fansListAdapter = new FansListAdapter(R.layout.item_news_fans_list, fansList);
        if (type == 1) {
            fansListAdapter.setEmptyView(getAdapterEmptyView("一个粉丝也没有", -1));
        } else {
            fansListAdapter.setEmptyView(getAdapterEmptyView("一个粉丝关注也没有", -1));
        }
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.setAdapter(fansListAdapter);
        fansListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY, fansListAdapter.getItem(position).getId()));
            }
        });
        fansListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.info_guanzhu_btn){
                    nowPosition = position;
                    if (fansListAdapter.getItem(position).getIsFollow() > 0){
                        //取消
                        mPresenter.addFollowOrDelete(2, fansListAdapter.getItem(position).getId(), NewsContract.DELETE_FOLLOW);
                    }else {
                        //添加
                        mPresenter.addFollowOrDelete(1, fansListAdapter.getItem(position).getId(), NewsContract.ADD_FOLLOW);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        page = 1;
        getData(true);
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        if (type == 1){
            mPresenter.getFansList(authorId, page, pagesize, NewsContract.GET_FANS_LIST, showProgress);
        }else {
            mPresenter.getFollowList(authorId, page, pagesize, NewsContract.GET_FANS_LIST, showProgress);
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            default:
                break;
            case NewsContract.GET_FANS_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                NewsFansListBean newsFansListBean = (NewsFansListBean) o;
                if (page == 1){
                    fansListAdapter.getData().clear();
                }
                if (newsFansListBean.getData().getDatas().size() < pagesize){
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                fansListAdapter.addData(newsFansListBean.getData().getDatas());
                break;
            case NewsContract.DELETE_FOLLOW:
                fansList.get(nowPosition).setIsFollow(0);
                fansListAdapter.notifyItemChanged(nowPosition);
                break;
            case NewsContract.ADD_FOLLOW://添加成功
                fansList.get(nowPosition).setIsFollow(1);
                fansListAdapter.notifyItemChanged(nowPosition);
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        switch (tag){
            case NewsContract.GET_FANS_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                break;
            default:
                break;
        }
    }
}
