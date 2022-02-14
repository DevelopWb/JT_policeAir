package com.juntai.wisdom.policeAir.mine.mypublish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseBottomDialog;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.PublishListBean;
import com.juntai.wisdom.policeAir.home_page.law_case.CaseInfoActivity;
import com.juntai.wisdom.policeAir.home_page.inspection.InspectionDetailActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_publish.UpdateImageNewsActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_publish.UpdateVideoNewsActivity;
import com.juntai.wisdom.policeAir.home_page.site_manager.site_info.NewUnitDetailActivity;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describe:我的发布
 * Create by zhangzhenlong
 * 2020-3-12
 * email:954101549@qq.com
 */
public class MyPublishListFragment extends BaseMvpFragment<MyPublishListPresent> implements MyCenterContract.IMyPublishListView, View.OnClickListener {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private TextView deleteBtn;
    LinearLayout linearLayout;
    private int curPosition = -1;//当前选中

    private BaseBottomDialog baseBottomDialog;
    private BaseBottomDialog.OnItemClick onItemClick;

    List<PublishListBean.DataBean.PublishBean> publishBeans = new ArrayList<>();
    MyPublishAdapter publishListAdapter;
    int type = 1;//功能，1案件，7巡检，8资讯，11场所
    List<Integer> ids = new ArrayList<>();
    int page = 1, pagesize = 20;

    public static MyPublishListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        MyPublishListFragment fragment = new MyPublishListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
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
        publishListAdapter = new MyPublishAdapter(R.layout.item_mycollect,publishBeans,type);
        publishListAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.none_publish),R.mipmap.none_publish));
        mRecyclerview.setAdapter(publishListAdapter);
        publishListAdapter.setOnItemClickListener((adapter1, view, position) -> {
            if (publishListAdapter.isEdit()){
                CheckBox checkBox = view.findViewById(R.id.item_collect_check);
                checkBox.setChecked(!checkBox.isChecked());
                publishListAdapter.getData().get(position).setChecked(checkBox.isChecked());
                return;
            }
            switch (type){
                case 1:
                    //案件
                    startActivity(new Intent(mContext, CaseInfoActivity.class).putExtra("id",publishListAdapter.getData().get(position).getId()));
                    break;
                case 7://巡检
                    startActivity(new Intent(mContext, InspectionDetailActivity.class).putExtra("id", publishListAdapter.getData().get(position).getId()));
                    break;
                case 8://资讯
                    MyApp.gotoNewsInfo(publishListAdapter.getItem(position).getTypeId(), publishListAdapter.getItem(position).getId(), mContext);
                    break;
                case 11:
                    startActivity(new Intent(mContext, NewUnitDetailActivity.class)
                            .putExtra(AppUtils.ID_KEY, publishListAdapter.getItem(position).getId())
                            .putExtra(NewUnitDetailActivity.UNIT_NAME, publishListAdapter.getItem(position).getTitle()));
                    break;
                default:
                    break;
            }
        });

        publishListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_more_iv){
                curPosition = position;
                //编辑弹窗
                initBottomDialog(Arrays.asList("修改"));
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected MyPublishListPresent createPresenter() {
        return new MyPublishListPresent();
    }

    @Override
    protected void lazyLoad() {
        page = 1;
        getData(true);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MyCenterContract.LOAD_PUBLISH_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                PublishListBean publishListBean = (PublishListBean)o;
                if (page == 1){
                    publishListAdapter.getData().clear();
                }
                if (publishListBean.getData().getDatas().size() < pagesize){
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                publishListAdapter.addData(publishListBean.getData().getDatas());
                break;
            case MyCenterContract.DELETE_PUBLISH_DATA:
                //删除
                BaseResult baseResult = (BaseResult) o;
                for (int i = 0; i< publishListAdapter.getData().size(); i++) {//必须索引遍历
                    if (publishListAdapter.getData().get(i).isChecked()){
                        publishListAdapter.getData().remove(i);
                        i--;
                    }
                }
                ((MyPublishListActivity)getActivity()).setEdit();
                setEdit(false);
                ToastUtils.success(mContext, "删除成功");
                if (type == 8){//刷新资讯列表
                    EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, String.valueOf(o));
        switch (tag){
            case MyCenterContract.LOAD_PUBLISH_LIST:
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
    private void deleteItem(){
        ids.clear();
        for (PublishListBean.DataBean.PublishBean bean: publishListAdapter.getData()) {
            if (bean.isChecked()){
                ids.add(bean.getId());
            }
        }
        if (ids.size() != 0){
            del();
        }else {
            ToastUtils.warning(getContext(),"请选择要删除的项目");
        }
    }

    /**
     * 编辑状态
     * @param isEdit
     */
    public void setEdit(boolean isEdit){
        if (publishListAdapter == null){
         return;
        }
        publishListAdapter.setEdit(isEdit);
        linearLayout.setVisibility(isEdit?View.VISIBLE:View.GONE);
        mSmartrefreshlayout.setEnableRefresh(!isEdit);
    }

    /**
     * 获取编辑状态
     * @return
     */
    public boolean getEdit(){
        return publishListAdapter.isEdit;
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        switch (type){
            case 1:
                //案件
                mPresenter.getPublishCaseList(page,pagesize,MyCenterContract.LOAD_PUBLISH_LIST, showProgress);
                break;
            case 7://巡检
                mPresenter.getPublishInspectionList(page,pagesize,MyCenterContract.LOAD_PUBLISH_LIST, showProgress);
                break;
            case 8://资讯
                mPresenter.getPublishNewsList(page,pagesize,MyCenterContract.LOAD_PUBLISH_LIST, showProgress);
                break;
            case 11://场所
                mPresenter.getPublishSiteList(page,pagesize,MyCenterContract.LOAD_PUBLISH_LIST, showProgress);
                break;
        }
    }

    /**
     * 批量删除
     */
    public void del(){
        switch (type){
            case 1:
                //案件
                mPresenter.deletePublishCase(ids,MyCenterContract.DELETE_PUBLISH_DATA);
                break;
            case 7://巡检
                mPresenter.deletePublishInspection(ids,MyCenterContract.DELETE_PUBLISH_DATA);
                break;
            case 8://资讯
                mPresenter.deletePublishNews(ids,MyCenterContract.DELETE_PUBLISH_DATA);
                break;
            case 11://场所
                mPresenter.deletePublishSite(ids,MyCenterContract.DELETE_PUBLISH_DATA);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        releaseDialog();
        super.onDestroyView();
    }

    /**
     * 初始化dialog
     */
    public void initBottomDialog(List<String> arrays) {

        if (baseBottomDialog == null) {
            onItemClick = new BaseBottomDialog.OnItemClick() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position){
                        case 0:
                            //编辑
                            if (publishListAdapter.getItem(curPosition).getTypeId() == 1){
                                startActivity(new Intent(mContext, UpdateVideoNewsActivity.class)
                                        .putExtra(AppUtils.ID_KEY, publishListAdapter.getItem(curPosition).getId()));
                            }else {
                                startActivity(new Intent(mContext, UpdateImageNewsActivity.class)
                                        .putExtra(AppUtils.ID_KEY, publishListAdapter.getItem(curPosition).getId()));
                            }
                            break;
                    }
                    baseBottomDialog.dismiss();
                }
            };
            baseBottomDialog = new BaseBottomDialog();
            baseBottomDialog.setOnBottomDialogCallBack(onItemClick);
        }
        baseBottomDialog.setData(arrays);
        baseBottomDialog.show(getChildFragmentManager(), "arrays");
    }

    /**
     * 释放dialog
     */
    private void releaseDialog() {
        if (baseBottomDialog != null) {
            if (baseBottomDialog.isAdded()) {
                onItemClick = null;
                baseBottomDialog.setOnBottomDialogCallBack(null);
                if (baseBottomDialog.getDialog().isShowing()){
                    baseBottomDialog.dismiss();
                }
            }
        }
        baseBottomDialog = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.UPDATE_NEWS_LIST.equals(test)){
            //刷新
            page = 1;
            getData(false);
        }
    }
}
