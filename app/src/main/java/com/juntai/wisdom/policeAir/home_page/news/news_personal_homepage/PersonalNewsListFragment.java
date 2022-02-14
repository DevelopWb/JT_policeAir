package com.juntai.wisdom.policeAir.home_page.news.news_personal_homepage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.news.NewsListBean;
import com.juntai.wisdom.policeAir.home_page.news.NewsContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.home_page.news.news_common.NewsListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 个人主页资讯列表
 * @aouther ZhangZhenlong
 * @date 2020-8-16
 */
public class PersonalNewsListFragment extends BaseMvpFragment<NewsPresent> implements NewsContract.INewsView {
    private int authorId;//作者id
    private int type;// 1视频，2图文
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private LinearLayout mSearchLl;
    /**
     * 发布
     */
    private ImageView mPublishBtn;

    int page = 1, pagesize = 20;
    private List<NewsListBean.DataBean.DatasBean> newsList = new ArrayList<>();
    private NewsListAdapter newsListAdapter;

    public static PersonalNewsListFragment newInstance(int authorId, int type) {
        Bundle args = new Bundle();
        args.putInt("authorId",authorId);
        args.putInt("type", type);
        PersonalNewsListFragment fragment = new PersonalNewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorId = getArguments().getInt("authorId");
        type = getArguments().getInt("type");
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        mRecyclerview = getView(R.id.recyclerview);
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);
        mSearchLl = getView(R.id.search_ll);
        mSearchLl.setVisibility(View.GONE);
        mPublishBtn = getView(R.id.publish_btn);
        mPublishBtn.setVisibility(View.GONE);

        newsListAdapter = new NewsListAdapter(newsList);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        newsListAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.load_more_no_data),R.mipmap.none_publish));
        mRecyclerview.setAdapter(newsListAdapter);
        newsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyApp.gotoNewsInfo(newsListAdapter.getItem(position).getTypeId(), newsListAdapter.getItem(position).getId(), mContext);
            }
        });

        mSmartrefreshlayout.setEnableRefresh(false);
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });
    }

    @Override
    protected void initData() {
        page = 1;
        getData(false);
    }


    @Override
    protected void lazyLoad() {}

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        mPresenter.getNewsListByAuthorId(authorId, type , page, pagesize, "", showProgress);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();

        NewsListBean newsListBean = (NewsListBean)o;
        if (page == 1){
            newsListAdapter.getData().clear();
        }
        if (newsListBean.getData().getDatas().size() < pagesize){
            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        }else {
            mSmartrefreshlayout.setNoMoreData(false);
        }
        newsListAdapter.addData(newsListBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        super.onError(tag,o);
    }
}
