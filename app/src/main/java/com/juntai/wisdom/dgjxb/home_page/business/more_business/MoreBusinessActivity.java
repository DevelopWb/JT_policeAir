package com.juntai.wisdom.dgjxb.home_page.business.more_business;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.bean.business.BusinessListBean;
import com.juntai.wisdom.dgjxb.home_page.business.BusinessContract;
import com.juntai.wisdom.dgjxb.home_page.business.BusinessPresent;
import com.juntai.wisdom.dgjxb.home_page.business.transact_business.TransactBusinessActivity;
import com.juntai.wisdom.dgjxb.home_page.call_to_police.VerifiedActivity;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述   更多业务
 * @date 2020/5/20 11:43
 */
public class MoreBusinessActivity extends BaseMvpActivity<BusinessPresent> implements BusinessContract.IBusinessView {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private int currentPage = 1;//当前页数
    private int limit = 15;//默认一次请求15条记录
    private BusinessItemAllAdapter allAdapter;

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
        setTitleName("户籍业务列表");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mRecyclerview.setBackgroundResource(R.drawable.stroke_gray_square_bg);
        allAdapter = new BusinessItemAllAdapter(R.layout.all_business_item);
        initRecyclerview(mRecyclerview, allAdapter, LinearLayout.VERTICAL);
        addDivider(true, mRecyclerview, false, true);
        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            mPresenter.businessList(MyApp.getAccount(),MyApp.getUserToken(),null, limit, currentPage, "");
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            mPresenter.businessList(MyApp.getAccount(),MyApp.getUserToken(),null, limit, currentPage, "");
        });
        allAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //首先判断下用户是否已经实名认证
                UserBean userBean = Hawk.get(AppUtils.SP_KEY_USER);
                if (userBean != null) {
                    //实名认证状态（0：未提交）（1：提交审核中）（2：审核通过）（3：审核失败）
                    int status = userBean.getData().getRealNameStatus();
                    if (2 != status) {
                        startActivity(new Intent(mContext, VerifiedActivity.class).putExtra(VerifiedActivity.VERIFIED_STATUS, status));
                        return;
                    }
                }
                BusinessListBean.DataBean.DatasBean datasBean = (BusinessListBean.DataBean.DatasBean) adapter.getData().get(position);
                Intent intent = new Intent(mContext, TransactBusinessActivity.class).putExtra("id", datasBean.getId()).putExtra("businessName", datasBean.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        currentPage = 1;
        mPresenter.businessList(MyApp.getAccount(),MyApp.getUserToken(),null, limit, currentPage, "");
        SmartRefreshLayout.LayoutParams parm = new SmartRefreshLayout.LayoutParams(SmartRefreshLayout.LayoutParams.MATCH_PARENT, SmartRefreshLayout.LayoutParams.MATCH_PARENT);
        parm.setMargins(DisplayUtil.dp2px(mContext, 15), DisplayUtil.dp2px(mContext, 10), DisplayUtil.dp2px(mContext, 15), DisplayUtil.dp2px(mContext, 10));
        mRecyclerview.setLayoutParams(parm);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        BusinessListBean businessListBean = (BusinessListBean) o;
        if (businessListBean != null) {
            List<BusinessListBean.DataBean.DatasBean> arrays = businessListBean.getData().getDatas();
            if (currentPage == 1) {
                allAdapter.setNewData(arrays);
            } else {
                allAdapter.addData(arrays);
            }
            if (arrays.size() < limit) {
                mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
            }else {
                mSmartrefreshlayout.setNoMoreData(false);
            }
        }
    }
}
