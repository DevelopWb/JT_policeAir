package com.juntai.wisdom.policeAir.mine.exchange_mall;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.exchang_mall.HistoryGoodsListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 历史兑换列表
 * @aouther ZhangZhenlong
 * @date 2020-6-2
 */
public class HistoryListActivity extends BaseMvpActivity<ExchangeMallPresent> implements ExchangeMallContract.IMallView {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    private ExchangeHistoryAdapter historyAdapter;
    private List<HistoryGoodsListBean.DataBean> historyListBeans = new ArrayList<>();

    @Override
    protected ExchangeMallPresent createPresenter() {
        return new ExchangeMallPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.normal_recycleview_layout;
    }

    @Override
    public void initView() {
        setTitleName("兑换记录");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        historyAdapter = new ExchangeHistoryAdapter(R.layout.item_exchange_history,historyListBeans);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(historyAdapter);
        historyAdapter.setEmptyView(getAdapterEmptyView("暂无兑换记录",R.mipmap.none_collect));
        mSmartrefreshlayout.setEnableRefresh(false);
        mSmartrefreshlayout.setEnableLoadMore(false);
    }

    @Override
    public void initData() {
        mPresenter.getExchangeHistoryList(ExchangeMallContract.GET_HISTORY_GOODS);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        HistoryGoodsListBean historyGoodsListBean = (HistoryGoodsListBean) o;
        historyAdapter.getData().clear();
        if (historyGoodsListBean.getData().size() == 0) {
            ToastUtils.success(mContext, "暂无数据");
        }
        historyAdapter.addData(historyGoodsListBean.getData());
    }
}
