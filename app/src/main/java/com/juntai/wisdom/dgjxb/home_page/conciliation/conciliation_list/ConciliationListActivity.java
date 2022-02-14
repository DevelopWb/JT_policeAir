package com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation_list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationListBean;
import com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation.ConciliationContract;
import com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation.ConciliationPresent;
import com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation.JoinRoomActivity;
import com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation_common.ConciliatonInfoActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 调解列表
 * @aouther ZhangZhenlong
 * @date 2020-10-7
 */
public class ConciliationListActivity extends BaseMvpActivity<ConciliationPresent> implements
        ConciliationContract.IConciliationView {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    int page = 1, pagesize = 20;
    ConciliationListAdapter conciliationListAdapter;
    List<ConciliationListBean.DataBean.ConciliationItemBean> conciliationListBeans = new ArrayList<>();

    private int pageType = 1;//1全部，2正在调解，3调解完成

    @Override
    protected ConciliationPresent createPresenter() {
        return new ConciliationPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_conciliation_list;
    }

    @Override
    public void initView() {
        setTitleName("调解申请");
        pageType = getIntent().getIntExtra("pageType", 1);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);

        conciliationListAdapter = new ConciliationListAdapter(R.layout.item_conciliation_list, conciliationListBeans);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(conciliationListAdapter);
        conciliationListAdapter.setEmptyView(getAdapterEmptyView(getString(R.string.none_conciliation), R.mipmap.none_collect));
        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });
        conciliationListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ConciliatonInfoActivity.class).putExtra("id", conciliationListAdapter.getItem(position).getId()));
            }
        });
        getTitleRightTv().setText("登录调解室");
        getTitleRightTv().setOnClickListener(v -> {
            startActivity(new Intent(mContext, JoinRoomActivity.class).putExtra("roomId","")
                    .putExtra("userName", MyApp.getUser().getData().getRealName()));
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
    public void getData(boolean showProgress) {
        mPresenter.getMyConciliationList(pageType, pagesize, page, ConciliationContract.GET_CONCILIATION_LIST, showProgress);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case ConciliationContract.GET_CONCILIATION_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                ConciliationListBean conciliationListBean = (ConciliationListBean) o;
                if (page == 1) {
                    conciliationListAdapter.getData().clear();
                }
                if (conciliationListBean.getData().getDatas().size() < pagesize) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                } else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                conciliationListAdapter.addData(conciliationListBean.getData().getDatas());
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        switch (tag) {
            case ConciliationContract.GET_CONCILIATION_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.REFRASH_CONCILIATION_LIST.equals(test)){
            page = 1;
            getData(false);
        }
    }
}
