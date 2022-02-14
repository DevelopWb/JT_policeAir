package com.juntai.wisdom.dgjxb.mine.task;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.task.TaskListBean;
import com.juntai.wisdom.dgjxb.mine.MyCenterContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的任务列表
 *
 * @aouther ZhangZhenlong
 * @date 2020-5-16
 */
public class MyTaskListActivity extends BaseMvpActivity<MyTaskPresent> implements MyCenterContract.ITaskView, View.OnClickListener {
    int page = 1, pagesize = 20;
    private MyTaskListAdapter myTaskListAdapter;
    private List<TaskListBean.DataBean.TaskBean> taskBeanList = new ArrayList<>();

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    private String keyWord;//模糊搜索关键词
    private String typeId;//状态值 0正在审核；1审核通过；2审核失败；null全部
    PopupWindow popupWindow;

    private SearchView mSearchContentSv;
    /**
     * 审核拒绝
     */
    private TextView mTypeTv;

    @Override
    protected MyTaskPresent createPresenter() {
        return new MyTaskPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_task_list;
    }

    @Override
    public void initView() {
        setTitleName("我的任务");
        mRecyclerview = findViewById(R.id.recyclerview);
        mSmartrefreshlayout = findViewById(R.id.smartrefreshlayout);
        mSearchContentSv = (SearchView) findViewById(R.id.search_content_sv);
        mSearchContentSv.setFocusable(false);
        mTypeTv = (TextView) findViewById(R.id.type_tv);
        mTypeTv.setOnClickListener(this);
        TextView textView = (TextView) mSearchContentSv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //设置字体大小为14sp
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

        myTaskListAdapter = new MyTaskListAdapter(R.layout.item_task_list, taskBeanList);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(myTaskListAdapter);
        myTaskListAdapter.setEmptyView(getAdapterEmptyView(getString(R.string.none_task), R.mipmap.none_collect));

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });

        myTaskListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TaskListBean.DataBean.TaskBean taskBean = myTaskListAdapter.getData().get(position);
                taskBean.setIsRead(0);
                myTaskListAdapter.notifyItemChanged(position);
                if (taskBean.getState() == 0) {//未提交
                    startActivityForResult(new Intent(mContext, PublishTReportActivity.class)
                            .putExtra("id", taskBean.getId())
                            .putExtra("taskPeopleId", taskBean.getTaskPeopleId()), ActivityResult);
                } else {
                    startActivity(new Intent(mContext, TaskInfoActivity.class)
                            .putExtra("id", taskBean.getId())
                            .putExtra("taskPeopleId", taskBean.getTaskPeopleId()));
                }
            }
        });

        mSearchContentSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                if (!StringTools.isStringValueOk(s)) {
//                    ToastUtils.warning(mContext, "请输入要搜索的内容");
//                    return false;
//                }
                initSearchClick(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
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
        mPresenter.getMyTaskList(page, pagesize, keyWord, typeId, MyCenterContract.GET_TASK_LIST, showProgress);
    }

    private void initSearchClick(String content) {
        keyWord = content;
        initData();
        mSearchContentSv.setFocusable(false);
        mSearchContentSv.setFocusableInTouchMode(false);
        mSearchContentSv.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityResult) {
            getData(false);
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        TaskListBean taskListBean = (TaskListBean) o;
        if (page == 1) {
            myTaskListAdapter.getData().clear();
            if (taskListBean.getData().getDatas().size() == 0) {
                ToastUtils.success(mContext, "暂无数据");
            }
        }
        if (taskListBean.getData().getDatas().size() < pagesize) {
            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        } else {
            mSmartrefreshlayout.setNoMoreData(false);
        }
        myTaskListAdapter.addData(taskListBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        super.onError(tag, o);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.type_tv:
                //条件弹窗
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                showPopType(mTypeTv);
                break;
        }
    }

    public void showPopType(View view) {
        View viewPop = LayoutInflater.from(this).inflate(R.layout.pop_task_type_view, null);
        popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(view, 0, 10);//显示在控件下面
        viewPop.findViewById(R.id.item_one).setOnClickListener(v -> {
            typeId = null;
            initData();
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.item_two).setOnClickListener(v -> {
            typeId = "0";
            initData();
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.item_third).setOnClickListener(v -> {
            typeId = "1";
            initData();
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.item_four).setOnClickListener(v -> {
            typeId = "2";
            initData();
            popupWindow.dismiss();
        });
    }
}
