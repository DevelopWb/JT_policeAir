package com.juntai.wisdom.policeAir.home_page.business;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.BaseSelectPicsActivity;
import com.juntai.wisdom.policeAir.home_page.baseInfo.TextListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @aouther tobato
 * @description 描述  办理业务的基类  提交业务  业务详情
 * 只单纯选择图片
 * * @date 2020/5/21 9:33
 */
public abstract class BaseBusinessActivity extends BaseSelectPicsActivity<BusinessPresent> {
    private TextListAdapter textListAdapter;
    private RecyclerView mRecyclerViewTextRv;
    protected TextView mNeedInfoTv;
    protected RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    protected int businessId;
    private String businessName;

    protected abstract BaseQuickAdapter getAdapter();

    @Override
    public int getLayoutView() {
        return R.layout.recycleview_layout;
    }

    @Override
    public void initView() {
        businessId = getIntent().getIntExtra("id", 1);
        businessName = getIntent().getStringExtra("businessName");
        setTitleName(businessName);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setPadding(0,0,0, DisplayUtil.dp2px(mContext,15));
        getAdapter().setHeaderView(adapterHeadLayout());

        initRecyclerview(mRecyclerview, getAdapter(), LinearLayout.VERTICAL);

        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        mSmartrefreshlayout.setEnableLoadMore(false);
    }


    /**
     * 配置头布局
     */
    private View adapterHeadLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_transact_business, null);
        mRecyclerViewTextRv = view.findViewById(R.id.key_value_rv);
        mNeedInfoTv = view.findViewById(R.id.need_info_tv);
        mRecyclerViewTextRv.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        textListAdapter = new TextListAdapter(R.layout.key_value_item, mPresenter.getUserBaseInfo(businessName));
        addDivider(true,mRecyclerViewTextRv,false,false);
        mRecyclerViewTextRv.setAdapter(textListAdapter);
        return view;
    }

    @Override
    protected BusinessPresent createPresenter() {
        return new BusinessPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        super.onError(tag, o);
    }
}
