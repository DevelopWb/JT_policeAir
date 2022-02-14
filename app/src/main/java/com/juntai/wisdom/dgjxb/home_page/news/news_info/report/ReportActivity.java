package com.juntai.wisdom.dgjxb.home_page.news.news_info.report;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.basecomponent.bean.ReportTypeBean;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  举报
 * @date 2020/12/26 15:21
 */
public class ReportActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView,
        View.OnClickListener {

    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private ReportAdapter adapter;
    private int reportTypeId = -1;//举报类型
    public static String INFO_ID = "infoid";//资讯id
    private int infoId;

    @Override
    public int getLayoutView() {
        return R.layout.activity_report;
    }

    @Override
    public void initView() {
        setTitleName("举报笔记");
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableLoadMore(false);
        mSmartrefreshlayout.setEnableRefresh(false);

        adapter = new ReportAdapter(R.layout.report_item);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(adapter);
        adapter.setHeaderView(getHeadLayout());
        adapter.setFooterView(getFootLayout());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ReportTypeBean.DataBean> arrays = adapter.getData();
                if (arrays != null) {
                    for (int i = 0; i < arrays.size(); i++) {
                        ReportTypeBean.DataBean bean = arrays.get(i);
                        if (position == i) {
                            bean.setSelected(true);
                            reportTypeId = bean.getId();
                        } else {
                            bean.setSelected(false);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            infoId = getIntent().getIntExtra(INFO_ID,0);
        }
        mPresenter.getReportTypes(mPresenter.getPublishMultipartBody().build(), NewsContract.REPORT_TYPES);

    }

    /**
     * 获取头布局
     *
     * @return
     */
    private View getHeadLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_text_layout, null);
        TextView mNoticeTv = (TextView) view.findViewById(R.id.single_text_tv);
        mNoticeTv.setBackgroundResource(0);
        mNoticeTv.setPadding(DisplayUtil.dp2px(mContext, 10), 0, 0, 0);
        mNoticeTv.setGravity(Gravity.CENTER | Gravity.LEFT);
        mNoticeTv.setText(getString(R.string.notice_report));
        return view;
    }

    /**
     * 获取尾布局
     *
     * @return
     */
    private View getFootLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_foot_view, null);
        TextView mCommmitTv = (TextView) view.findViewById(R.id.foot_commit_tv);
        mCommmitTv.setOnClickListener(this);
        mCommmitTv.setText("提交");
        return view;
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case NewsContract.REPORT_TYPES:
                ReportTypeBean reportTypeBean = (ReportTypeBean) o;
                if (reportTypeBean != null) {
                    if (reportTypeBean.getData() != null) {
                        List<ReportTypeBean.DataBean> arrays = reportTypeBean.getData();
                        adapter.setNewData(arrays);
                    }
                }

                break;
            default:
                //提交成功
                ToastUtils.success(mContext, ((BaseResult) o).getMessage());
                finish();

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.foot_commit_tv:
                //提交举报
                if (-1 == reportTypeId) {
                    ToastUtils.toast(mContext, "请选择举报的类型");
                } else {
                    mPresenter.report(mPresenter.getPublishMultipartBody().addFormDataPart("informationId", String.valueOf(infoId)).addFormDataPart(
                            "typeId", String.valueOf(reportTypeId)).build(), "");
                }
                break;
            default:
                break;
        }
    }
}
