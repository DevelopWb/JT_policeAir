package com.juntai.wisdom.dgjxb.home_page.key_personnel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.TextListBean;
import com.juntai.wisdom.dgjxb.bean.UserScoreListBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionPointInfoBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionRecordBean;
import com.juntai.wisdom.dgjxb.bean.key_personnel.InterviewListBean;
import com.juntai.wisdom.dgjxb.bean.key_personnel.KeyPersonnelInfoBean;
import com.juntai.wisdom.dgjxb.home_page.baseInfo.TextListAdapter;
import com.juntai.wisdom.dgjxb.home_page.map.MapContract;
import com.juntai.wisdom.dgjxb.home_page.map.MapPresenter;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 重点人员详情
 * @aouther ZhangZhenlong
 * @date 2020-7-2
 */
public class KeyPersonnelInfoActivity extends BaseMvpActivity<MapPresenter> implements MapContract.View, View.OnClickListener {
    private RecyclerView mKeyPersonnelDetailTexts;
    private KPInfoAdapter textListAdapter;
    private List<TextListBean> textListBeans = new ArrayList<>();
    private ImageView mHeadIv;
    /**
     * 走访按钮
     */
    private TextView mInterviewBtn;
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    InterViewAdapter interViewAdapter;
    //走访记录
    private List<InterviewListBean.DataBean.DatasBean> interviewList = new ArrayList<>();
    private int pageNo = 1;//当前页数
    private int pageSize = 20;//每页数量
    private int personId;//人员id
//    private KeyPersonnelInfoBean keyPersonnelInfo;//重点人员

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_key_personnel_info;
    }

    @Override
    public void initView() {
        setTitleName("重点人员详情");
        personId = getIntent().getIntExtra(AppUtils.ID_KEY,0);
        mKeyPersonnelDetailTexts = (RecyclerView) findViewById(R.id.key_personnel_detail_texts);
        mHeadIv = (ImageView) findViewById(R.id.head_iv);
        mInterviewBtn = (TextView) findViewById(R.id.interview_btn);
        mInterviewBtn.setOnClickListener(this);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);

        mKeyPersonnelDetailTexts.setLayoutManager(new LinearLayoutManager(this));
        textListAdapter = new KPInfoAdapter(R.layout.item_key_personnel_value, textListBeans);
        mKeyPersonnelDetailTexts.setAdapter(textListAdapter);

        interViewAdapter = new InterViewAdapter(R.layout.item_inspection_record,interviewList);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(interViewAdapter);
        interViewAdapter.setEmptyView(getAdapterEmptyView("暂无走访记录", -1));
        interViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //详情
                startActivity(new Intent(mContext,InterviewDetailActivity.class).putExtra("id",interViewAdapter.getData().get(position).getId()));
            }
        });

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            pageNo = 1;
            mPresenter.getInterviewList(personId, pageNo, pageSize, MapContract.GET_INTERVIEW);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            pageNo++;
            mPresenter.getInterviewList(personId, pageNo, pageSize, MapContract.GET_INTERVIEW);
        });
    }

    @Override
    public void initData() {
        mPresenter.getKeyPersonnelInfo(MapContract.GET_KEY_PERSONNEL_INFO,personId);
        mPresenter.getInterviewList(personId, pageNo, pageSize, MapContract.GET_INTERVIEW);
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        switch (tag) {
            case MapContract.INSPECTION_POINT_INFO:
                KeyPersonnelInfoBean keyPersonnelInfoBean =  Hawk.get(MapContract.GET_KEY_PERSONNEL_INFO+personId);
                if (keyPersonnelInfoBean != null) {
                    onSuccessKeyPersonnelInfoLogic(keyPersonnelInfoBean.getData());
                }
                break;
            case MapContract.GET_INTERVIEW:
                break;
            default:
                break;
        }
        super.onError(tag, o);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        switch (tag) {
            case MapContract.GET_KEY_PERSONNEL_INFO:
                KeyPersonnelInfoBean keyPersonnelInfoBean = (KeyPersonnelInfoBean) o;
                if (keyPersonnelInfoBean != null) {
                    Hawk.put(MapContract.GET_KEY_PERSONNEL_INFO+personId,keyPersonnelInfoBean);
                    onSuccessKeyPersonnelInfoLogic(keyPersonnelInfoBean.getData());
                }
                break;
            case MapContract.GET_INTERVIEW:
                //走访记录
                InterviewListBean interviewListBean = (InterviewListBean) o;
                if (pageNo == 1) {
                    interViewAdapter.getData().clear();
                }
                if (interviewListBean.getData().getDatas().size() < pageSize) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                interViewAdapter.addData(interviewListBean.getData().getDatas());
                break;
            default:
                break;
        }
    }

    private void onSuccessKeyPersonnelInfoLogic(KeyPersonnelInfoBean.DataBean keyPersonnelInfo) {
        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), keyPersonnelInfo.getHeadId(), mHeadIv);
        textListBeans.clear();
        textListBeans.add(new TextListBean("姓名", keyPersonnelInfo.getName()));
        textListBeans.add(new TextListBean("性别", keyPersonnelInfo.getSex() == 0? "男":"女"));
        textListBeans.add(new TextListBean("年龄", String.valueOf(keyPersonnelInfo.getAge())));
        textListBeans.add(new TextListBean("联系电话", keyPersonnelInfo.getPhone()));
        textListBeans.add(new TextListBean("身份证号", keyPersonnelInfo.getIdNo()));
        textListBeans.add(new TextListBean("住址", keyPersonnelInfo.getAddress()));
        textListBeans.add(new TextListBean("单位", keyPersonnelInfo.getUnit()));
        textListBeans.add(new TextListBean("所属网格", keyPersonnelInfo.getGridName()));
        textListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.interview_btn:
                //添加走访
                startActivityForResult(new Intent(mContext,PublishInterviewActivity.class).putExtra("id",personId), AppUtils.PUBLISH_INTERVIEW_BACK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (AppUtils.PUBLISH_INTERVIEW_BACK == resultCode) {
            pageNo = 1;
            mPresenter.getInterviewList(personId, pageNo, pageSize, MapContract.GET_INTERVIEW);
        }
    }
}
