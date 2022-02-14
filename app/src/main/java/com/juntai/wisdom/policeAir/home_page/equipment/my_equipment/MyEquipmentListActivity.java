package com.juntai.wisdom.policeAir.home_page.equipment.my_equipment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.user_equipment.EquipmentListBean;
import com.juntai.wisdom.policeAir.home_page.camera.ijkplayer.PlayerLiveActivity;
import com.juntai.wisdom.policeAir.home_page.equipment.EquipmentPresent;
import com.juntai.wisdom.policeAir.home_page.equipment.IEquipmentContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @description 我的设备列表
 * @aouther ZhangZhenlong
 * @date 2020-11-21
 */
public class MyEquipmentListActivity extends BaseMvpActivity<EquipmentPresent> implements IEquipmentContract.IEquipmentView {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private EquipmentListAdapter equipmentListAdapter;
    private int currentPage = 1;//当前页数
    private int pageSize = 20;//默认一次请求20条记录

    @Override
    protected EquipmentPresent createPresenter() {
        return new EquipmentPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_equipment_list;
    }

    @Override
    public void initView() {
        setTitleName("我的设备");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        equipmentListAdapter = new EquipmentListAdapter(R.layout.item_equipment_list);
        initRecyclerview(mRecyclerview, equipmentListAdapter, LinearLayout.VERTICAL);
        equipmentListAdapter.setEmptyView(getAdapterEmptyView("暂无设备", 0));
        mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentPage = 1;
                getData(false);
            }
        });
        mSmartrefreshlayout.setEnableLoadMore(false);
        equipmentListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EquipmentListBean.DataBean equipmentListBean = equipmentListAdapter.getItem(position);
                if (equipmentListBean != null){
                    startActivity(new Intent(mContext.getApplicationContext(), PlayerLiveActivity.class)
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_ID, equipmentListBean.getId())
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_NUM, equipmentListBean.getNumber())
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL, equipmentListBean.getEzOpen()));
                }
            }
        });
    }

    @Override
    public void initData() {
        getData(true);
    }

    private void getData(boolean isShow){
        mPresenter.getMyEquipmentList(currentPage, pageSize, IEquipmentContract.GET_MY_EQUIPMENT_LIST, isShow);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        EquipmentListBean equipmentListBean = (EquipmentListBean) o;
        if (currentPage == 1){
            equipmentListAdapter.getData().clear();
        }
        equipmentListAdapter.addData(equipmentListBean.getData());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        super.onError(tag, o);
    }
}
