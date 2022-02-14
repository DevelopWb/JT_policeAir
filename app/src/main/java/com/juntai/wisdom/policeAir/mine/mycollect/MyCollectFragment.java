package com.juntai.wisdom.policeAir.mine.mycollect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.CollectListBean;
import com.juntai.wisdom.policeAir.home_page.camera.ijkplayer.PlayerLiveActivity;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏、分享
 *
 * @aouther ZhangZhenlong
 * @date 2020-3-12
 */
public class MyCollectFragment extends BaseMvpFragment<MyCollectPresent> implements MyCenterContract.ICollectView,
        View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private TextView deleteBtn;
    LinearLayout linearLayout;

    List<CollectListBean.DataBean.CollectBean> collectBeans = new ArrayList<>();
    MyCollectAdapter adapter;
    int type;//（8：资讯,0:监控）
    List<Integer> ids = new ArrayList<>();
    int function = 1;//功能，1=我的收藏  2我的分享
    int page = 1, pagesize = 20;

    public static MyCollectFragment newInstance(int type, int f) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt("function", f);
        MyCollectFragment fragment = new MyCollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        function = getArguments().getInt("function");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_collect;
    }

    @Override
    protected void initView() {
        mRecyclerview = getView(R.id.recyclerview);
        mRecyclerview.setBackgroundColor(getResources().getColor(R.color.transparent));
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);
        deleteBtn = getView(R.id.delete_btn);
        deleteBtn.setOnClickListener(this);
        linearLayout = getView(R.id.mycollect_bottom_layout);

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });

        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyCollectAdapter(R.layout.item_mycollect, collectBeans, function);
        mRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (adapter.getEdit()) {
                CheckBox checkBox = view.findViewById(R.id.item_collect_check);
                checkBox.setChecked(!checkBox.isChecked());
                adapter.getData().get(position).setChecked(checkBox.isChecked());
                return;
            }
            if (type == 8) {//资讯
                MyApp.gotoNewsInfo(adapter.getItem(position).getTypeId(), adapter.getItem(position).getId(), mContext);
            } else if (type == 0) {//监控
                if (StringTools.isStringValueOk(adapter.getItem(position).getNumber())) {
                    startActivity(new Intent(mContext.getApplicationContext(), PlayerLiveActivity.class)
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_ID, adapter.getData().get(position).getId())
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_NUM, adapter.getItem(position).getNumber())
                            .putExtra(PlayerLiveActivity.STREAM_CAMERA_THUM_URL, adapter.getItem(position).getUrl()));
                }else {
                    ToastUtils.warning(mContext, "数据不存在");
                }
            }
        });

        if (function == 1) {//收藏
            adapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.none_collect),
                    R.mipmap.none_collect));
        } else {
            adapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.none_share),
                    R.mipmap.none_share));
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected MyCollectPresent createPresenter() {
        return new MyCollectPresent();
    }

    @Override
    protected void lazyLoad() {
        page = 1;
        getData(true);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MyCenterContract.LOAD_COLLECT_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                CollectListBean collectListBean = (CollectListBean) o;
                if (page == 1) {
                    adapter.getData().clear();
                }
                if (collectListBean.getData().getDatas().size() < pagesize) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                List<CollectListBean.DataBean.CollectBean> arrays = collectListBean.getData().getDatas();
                for (CollectListBean.DataBean.CollectBean array : arrays) {
                    array.setType(type);
                }
                adapter.addData(arrays);
                break;
            case MyCenterContract.DELETE_COLLECT_DATA:
                //删除
                for (int i = 0; i < adapter.getData().size(); i++) {//必须索引遍历
                    if (adapter.getData().get(i).isChecked()) {
                        adapter.getData().remove(i);
                        i--;
                    }
                }
                ((MyCollectActivity) getActivity()).setEdit();
                setEdit(false);
                if (function == 2){
                    ToastUtils.success(mContext, "删除成功");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, String.valueOf(o));
        switch (tag) {
            case MyCenterContract.LOAD_COLLECT_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_btn:
                //                DialogUtil.getMessageDialog(mContext, getString(R.string.delete_waring),
                //                        new DialogInterface.OnClickListener() {
                //                            @Override
                //                            public void onClick(DialogInterface dialog, int which) {
                deleteItem();
                //                            }
                //                        }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化删除
     */
    private void deleteItem() {
        ids.clear();
        if (function == 1) {
            for (CollectListBean.DataBean.CollectBean bean : adapter.getData()) {
                if (bean.isChecked()) {
                    ids.add(bean.getCollectId());
                }
            }
        } else {//分享
            for (CollectListBean.DataBean.CollectBean bean : adapter.getData()) {
                if (bean.isChecked()) {
                    ids.add(bean.getShareId());
                }
            }
        }
        if (ids.size() != 0) {
            del();
        } else {
            ToastUtils.warning(getContext(), "请选择要删除的项目");
        }
    }

    /**
     * 编辑状态
     *
     * @param isEdit
     */
    public void setEdit(boolean isEdit) {
        adapter.setEdit(isEdit);
        linearLayout.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        mSmartrefreshlayout.setEnableRefresh(!isEdit);
    }

    /**
     * 获取编辑状态
     *
     * @return
     */
    public boolean getEdit() {
        return adapter.getEdit();
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress) {
        if (function == 1) {
            //收藏
            switch (type){
                case 0:
                    mPresenter.getCollectListCamera(page, pagesize, MyCenterContract.LOAD_COLLECT_LIST, showProgress);
                    break;
                case 8:
                    mPresenter.getCollectListNews(page, pagesize, MyCenterContract.LOAD_COLLECT_LIST, showProgress);
                    break;
            }
        } else {
            //我的分享
            switch (type){
                case 0:
                    mPresenter.getShareListCamera(page, pagesize, MyCenterContract.LOAD_COLLECT_LIST, showProgress);
                    break;
                case 8:
                    mPresenter.getShareListNews(page, pagesize, MyCenterContract.LOAD_COLLECT_LIST, showProgress);
                    break;
            }
        }
    }

    /**
     * 批量删除
     */
    public void del() {
        if (function == 1) {
            //收藏
            switch (type){
                case 0:
                    mPresenter.deleteCollecListCamera(-1, -1, 1, -1, -1, ids, MyCenterContract.DELETE_COLLECT_DATA);
                    break;
                case 8:
                    mPresenter.deleteCollecListNews(-1, -1, 1, -1, -1, ids, MyCenterContract.DELETE_COLLECT_DATA);
                    break;
            }
        } else {
            //我的分享
            switch (type){
                case 0:
                    mPresenter.deleteShareListCamera(ids, MyCenterContract.DELETE_COLLECT_DATA);
                    break;
                case 8:
                    mPresenter.deleteShareListNews(ids, MyCenterContract.DELETE_COLLECT_DATA);
                    break;
            }
        }
    }
}
