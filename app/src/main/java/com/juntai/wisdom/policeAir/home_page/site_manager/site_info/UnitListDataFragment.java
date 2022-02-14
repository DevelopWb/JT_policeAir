package com.juntai.wisdom.policeAir.home_page.site_manager.site_info;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.site.EmployeeListBean;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerContract;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerPresent;
import com.juntai.wisdom.policeAir.home_page.site_manager.site_add.AddEmployeeActivity;
import com.juntai.wisdom.policeAir.home_page.site_manager.site_add.AddUnitInspectionActivity;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 单位相关列表数据
 * @aouther ZhangZhenlong
 * @date 2020-7-4
 */
public class UnitListDataFragment extends BaseMvpFragment<SiteManagerPresent> implements SiteManagerContract.ISiteManagerView,View.OnClickListener {
    int type = 1;//1从业人员，2检查记录
    private int unitId;
    private String unitName;

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private TextView addBtn;
    LinearLayout linearLayout;

    List<EmployeeListBean.DataBean.DatasBean> employeeListBeans = new ArrayList<>();
    ListDataAdapter listDataAdapter;
    int pageNo = 1, pagesize = 20;

    public static UnitListDataFragment newInstance(int type, int dataId, String unitName) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        args.putInt(AppUtils.ID_KEY,dataId);
        args.putString(NewUnitDetailActivity.UNIT_NAME,unitName);
        UnitListDataFragment fragment = new UnitListDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        unitId = getArguments().getInt(AppUtils.ID_KEY);
        unitName = getArguments().getString(NewUnitDetailActivity.UNIT_NAME);
    }

    @Override
    protected SiteManagerPresent createPresenter() {
        return new SiteManagerPresent();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_unit_list_data;
    }

    @Override
    protected void initView() {
        mRecyclerview = getView(R.id.recyclerview);
        mRecyclerview.setBackgroundColor(getResources().getColor(R.color.transparent));
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);
        addBtn = getView(R.id.add_btn);
        addBtn.setOnClickListener(this);
        linearLayout = getView(R.id.bottom_layout);

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            pageNo = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            pageNo++;
            getData(false);
        });

        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        listDataAdapter = new ListDataAdapter(R.layout.item_inspection_record,employeeListBeans);
        listDataAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(type == 1? "暂无人员":"暂无记录",-1));
        mRecyclerview.setAdapter(listDataAdapter);
        listDataAdapter.setOnItemClickListener((adapter1, view, position) -> {
            switch (type){
                case 1:
                    //1从业人员
                    startActivity(new Intent(mContext, EmployeeDetailActivity.class).putExtra(AppUtils.ID_KEY, listDataAdapter.getData().get(position).getId()));
                    break;
                case 2:
                    //2检查记录
                    startActivity(new Intent(mContext, UnitInspectionDetailActivity.class).putExtra(AppUtils.ID_KEY, listDataAdapter.getData().get(position).getId()));
                    break;
                default:
                    break;
            }
        });
        if (type == 1){
            addBtn.setText("添加从业人员");
        }else {
            addBtn.setText("添加检查记录");
        }
    }

    @Override
    protected void initData() {}

    @Override
    protected void lazyLoad() {
        pageNo = 1;
        getData(true);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        EmployeeListBean employeeListBean = (EmployeeListBean) o;
        if (pageNo == 1){
            listDataAdapter.getData().clear();
        }
        if (employeeListBean.getData().getDatas().size() < pagesize){
            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        }else {
            mSmartrefreshlayout.setNoMoreData(false);
        }
        listDataAdapter.addData(employeeListBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        ToastUtils.error(mContext, String.valueOf(o));
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        if (type == 1){
            mPresenter.getEmployeeList(unitId, pagesize, pageNo, SiteManagerContract.GET_EMPLOYEE_LIST, showProgress);
        }else {
            mPresenter.getSiteInspectionList(unitId, pagesize, pageNo, SiteManagerContract.GET_SITE_INSPECTTION_LIST, showProgress);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                if (type == 1){
                    //从业人员添加
                    startActivity(new Intent(mContext, AddEmployeeActivity.class)
                            .putExtra(AppUtils.ID_KEY,unitId).putExtra("unitName",unitName));
                }else if (type == 2){
                    //检查记录添加
                    if (MyApp.isCompleteUserInfo()){
                        startActivity(new Intent(mContext, AddUnitInspectionActivity.class).putExtra(AppUtils.ID_KEY, unitId));
                    }
                }
                break;
            default:
                break;
        }
    }
}
