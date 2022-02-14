package com.juntai.wisdom.policeAir.mine.score;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.UserScoreListBean;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.mine.exchange_mall.AllGoodsListActivity;
import com.juntai.wisdom.policeAir.mine.exchange_mall.HistoryListActivity;
import com.juntai.wisdom.policeAir.utils.UserInfoManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的积分
 *
 * @aouther ZhangZhenlong
 * @date 2020-3-12
 */
public class MyScoreActivity extends BaseMvpActivity<MyScorePresent> implements MyCenterContract.IMyScoreView, View.OnClickListener {
    PopupWindow popupWindow;
    String type = null;//1获取；2使用,null全部
    int page = 1, pagesize = 20;

    MyScoreDetailAdapter scoreDetailAdapter;
    List<UserScoreListBean.DataBean.ScoreBean> scoreList = new ArrayList<>();
    /**
     * 98000
     */
    private TextView mScoreTv;
    private RecyclerView mDetailRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private ImageView mScoreMoreIv;
    /**
     * 积分兑换
     */
    private TextView mExchangeBtn;

    @Override
    protected MyScorePresent createPresenter() {
        return new MyScorePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_score;
    }

    @Override
    public void initView() {
        setTitleName("我的积分");
        mScoreTv = findViewById(R.id.score_tv);
        mDetailRecyclerview = findViewById(R.id.detail_recyclerview);
        mSmartrefreshlayout = findViewById(R.id.smartrefreshlayout);
        mScoreMoreIv = findViewById(R.id.score_more_iv);
        mScoreMoreIv.setOnClickListener(this);

        getTitleRightTv().setText("兑换记录");
        getTitleRightTv().setOnClickListener(v -> {
            startActivity(new Intent(mContext,HistoryListActivity.class));
        });

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });
        mDetailRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        scoreDetailAdapter = new MyScoreDetailAdapter(R.layout.item_my_score, scoreList);
        mDetailRecyclerview.setAdapter(scoreDetailAdapter);
//        mSmartrefreshlayout.autoRefresh();
        mExchangeBtn = (TextView) findViewById(R.id.exchange_btn);
        mExchangeBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mScoreTv.setText(UserInfoManager.getScore());
        page = 1;
        getData(true);
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress) {
        mPresenter.getScoreDetail(type, page, pagesize, MyCenterContract.GET_SCORE_DATA, showProgress);
    }

    public void showPopType(View view) {
        View viewPop = LayoutInflater.from(this).inflate(R.layout.pop_score_type_view, null);
        //背景颜色
//        viewPop.setBackgroundColor(Color.WHITE);
        popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(view, 0, 10);//显示在控件下面
        viewPop.findViewById(R.id.aaaaa).setOnClickListener(v -> {
            if (type != null) {
                page = 1;
                type = null;
                getData(true);
            }
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.bbbb).setOnClickListener(v -> {
            if (type != "1") {
                type = "1";
                page = 1;
                getData(true);
            }
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.cccc).setOnClickListener(v -> {
            if (type != "2") {
                type = "2";
                page = 1;
                getData(true);
            }
            popupWindow.dismiss();
        });
    }


    @Override
    public void onSuccess(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        UserScoreListBean userScoreListBean = (UserScoreListBean) o;
        if (page == 1) {
            scoreDetailAdapter.getData().clear();
            if (userScoreListBean.getData().getDatas().size() == 0) {
                ToastUtils.success(mContext, "暂无数据");
            }
        }
        if (userScoreListBean.getData().getDatas().size() < pagesize) {
            mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
        }else {
            mSmartrefreshlayout.setNoMoreData(false);
        }
        scoreDetailAdapter.addData(userScoreListBean.getData().getDatas());
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        ToastUtils.error(mContext, String.valueOf(o));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.score_more_iv:
                //条件弹窗
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                showPopType(mScoreMoreIv);
                break;
            case R.id.exchange_btn:
                startActivity(new Intent(mContext, AllGoodsListActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void updateScore(String test) {
        if (ActionConfig.UPDATE_MY_SCORE.equals(test)){
            page = 1;
            getData(false);
        }
    }
}
