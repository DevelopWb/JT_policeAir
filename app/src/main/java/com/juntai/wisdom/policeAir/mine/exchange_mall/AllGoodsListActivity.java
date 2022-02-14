package com.juntai.wisdom.policeAir.mine.exchange_mall;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.DialogUtil;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.exchang_mall.GoodsListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 商品列表
 * @aouther ZhangZhenlong
 * @date 2020-6-2
 */
public class AllGoodsListActivity extends BaseMvpActivity<ExchangeMallPresent> implements ExchangeMallContract.IMallView {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    int page = 1, pagesize = 20;
    private GoodsListAdapter goodsListAdapter;
    private List<GoodsListBean.GoodsBean> goodsBeans = new ArrayList<>();

    @Override
    protected ExchangeMallPresent createPresenter() {
        return new ExchangeMallPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_all_goods_list;
    }

    @Override
    public void initView() {
        setTitleName("兑换商城");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);

        goodsListAdapter = new GoodsListAdapter(R.layout.item_mall_goods_list,goodsBeans);
        mRecyclerview.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerview.setAdapter(goodsListAdapter);
        goodsListAdapter.setEmptyView(getAdapterEmptyView(getString(R.string.none_goods),R.mipmap.none_collect));

        mSmartrefreshlayout.setEnableRefresh(false);
        mSmartrefreshlayout.setEnableLoadMore(false);

        goodsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext,GoodsInfoActivity.class).putExtra("id",goodsListAdapter.getData().get(position).getCommodityId()));
            }
        });

        goodsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_pay){
                    if (goodsListAdapter.getData().get(position).getInventoryNum() < 1){
                        ToastUtils.warning(mContext,"库存不足");
                        return;
                    }
                    DialogUtil.getConfirmDialog(mContext, "确定兑换该商品？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GoodsListBean.GoodsBean goodsBean = goodsListAdapter.getData().get(position);
                            if (MyApp.getUser().getData().getScore() < goodsBean.getPrice()){
                                ToastUtils.warning(mContext,"积分不足");
                                return;
                            }
                            mPresenter.exchangeGoods(goodsBean.getPrice(),goodsBean.getCommodityId(),goodsBean.getCommodityName(),ExchangeMallContract.EXCHANGE_GOODS);
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });

    }

    @Override
    public void initData() {
        getData();
    }

    /**
     * 获取数据
     */
    public void getData() {
        mPresenter.getAllGoodsList(ExchangeMallContract.GET_ALL_GOODS);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case ExchangeMallContract.EXCHANGE_GOODS:
//                ToastUtils.success(mContext,"兑换成功");
                DialogUtil.getMessageDialog(mContext, getString(R.string.exchange_success_remind)).setTitle("兑换成功").show();
                EventManager.sendStringMsg(ActionConfig.UPDATE_MY_SCORE);
                break;
            case ExchangeMallContract.GET_ALL_GOODS:
                GoodsListBean goodsListBean = (GoodsListBean) o;
                goodsListAdapter.getData().clear();
                if (goodsListBean.getData().size() == 0) {
                    ToastUtils.success(mContext, "暂无数据");
                }
                goodsListAdapter.addData(goodsListBean.getData());
                break;
            default:
                break;
        }
    }

}
