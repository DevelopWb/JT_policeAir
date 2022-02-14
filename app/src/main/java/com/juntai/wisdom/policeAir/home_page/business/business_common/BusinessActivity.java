package com.juntai.wisdom.policeAir.home_page.business.business_common;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.MyMenuBean;
import com.juntai.wisdom.policeAir.bean.UserBean;
import com.juntai.wisdom.policeAir.home_page.business.BusinessContract;
import com.juntai.wisdom.policeAir.home_page.business.BusinessPresent;
import com.juntai.wisdom.policeAir.home_page.business.more_business.MoreBusinessActivity;
import com.juntai.wisdom.policeAir.home_page.business.transact_business.TransactBusinessActivity;
import com.juntai.wisdom.policeAir.home_page.call_to_police.VerifiedActivity;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * @aouther tobato
 * @description 描述  业务办理  常用
 * @date 2020/5/20 9:21
 */
public class BusinessActivity extends BaseMvpActivity<BusinessPresent> implements BusinessContract.IBusinessView {

    private RecyclerView mRecyclerview;
    private BusinessItemAdapter adapter;
    private SmartRefreshLayout mSmartrefreshlayout;

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
        setTitleName("业务办理");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new BusinessItemAdapter(R.layout.business_item);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(adapter);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);
    }

    @Override
    public void initData() {
        adapter.setNewData(mPresenter.getBusinessList());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {



                //每个item的业务id 对应position+1
                MyMenuBean bean = (MyMenuBean) adapter.getData().get(position);
                int num = bean.getNumber();
                int businessId = position + 1;
                Intent intent = new Intent(mContext, TransactBusinessActivity.class).putExtra("id", businessId).putExtra("businessName", bean.getName());
                if (11==num) {
                    //更多
                    intent = new Intent(mContext, MoreBusinessActivity.class);
                }else {
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

                }

                startActivity(intent);
            }
        });
    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
