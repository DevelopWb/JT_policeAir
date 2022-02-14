package com.juntai.wisdom.dgjxb.home_page.business.my_business;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.business.MyBusinessBean;
import com.juntai.wisdom.dgjxb.home_page.business.BusinessContract;
import com.juntai.wisdom.dgjxb.home_page.business.BusinessPresent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @aouther tobato
 * @description 描述  我的业务
 * @date 2020/5/22 10:13
 */
public class MyBusinessActivity extends BaseMvpActivity<BusinessPresent> implements BusinessContract.IBusinessView {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private MyBusinessAdapter adapter;
    private int currentPage = 1;//当前页数
    private int limit = 15;//默认一次请求15条记录

    @Override
    protected BusinessPresent createPresenter() {
        return new BusinessPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        setTitleName("我的业务");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        adapter = new MyBusinessAdapter(R.layout.item_mycollect);
        initRecyclerview(mRecyclerview, adapter, LinearLayout.VERTICAL);
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentPage = 1;
                initData();
            }
        });
        mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                currentPage ++;
                initData();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyBusinessBean.DataBean.DatasBean datasBean = (MyBusinessBean.DataBean.DatasBean) adapter.getData().get(position);
                int businessId = datasBean.getBusinessId();
                String businessName = datasBean.getTitle();
                Intent intent = new Intent(mContext, MyBusinessDetialActivity.class).putExtra("id", businessId).putExtra("businessName", businessName);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.businessProgress(mPresenter.getPublishMultipartBody().addFormDataPart("pageSize", limit+"").addFormDataPart("currentPage",currentPage+"").build(),"");
    }


    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        MyBusinessBean myBusinessBean = (MyBusinessBean) o;
        if (currentPage == 1){
            adapter.getData().clear();
        }
        if (myBusinessBean.getData().getDatas().size() < limit){
            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        }else {
            mSmartrefreshlayout.setNoMoreData(false);
        }
        adapter.addData(myBusinessBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        super.onError(tag, o);
    }
}
