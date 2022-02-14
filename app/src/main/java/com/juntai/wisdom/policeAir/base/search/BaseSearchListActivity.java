package com.juntai.wisdom.policeAir.base.search;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.customview.flowlayout.FlowLayout;
import com.juntai.wisdom.policeAir.base.customview.flowlayout.TagAdapter;
import com.juntai.wisdom.policeAir.base.customview.flowlayout.TagFlowLayout;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:搜索基类
 * Create by zhangzhenlong
 * 2020-8-8
 * email:954101549@qq.com
 */
public abstract class BaseSearchListActivity<P extends BasePresenter> extends BaseMvpActivity<P> implements View.OnClickListener {
    public static String SEARCH_HIS_KEY = "search_his_key";//保存本地的历史记录的key,子类记得修改值
    /**
     * 返回
     */
    protected TextView mBackTv;
    protected SearchView mSearchContentSv;
    /**
     * 搜索
     */
    protected TextView mSearchTv;
    protected ImageView mDeleteHistoryIv;
    protected TagFlowLayout tagFlowLayout;
    protected TagAdapter mRecordsAdapter;
    protected ImageView mShowMoreArrowIv;
    protected ConstraintLayout mHisRecordCl;
    protected RecyclerView mSearchInfoRv;
    protected SmartRefreshLayout mSmartrefreshlayout;

    protected int page = 1, pagesize = 20;

    @Override
    public int getLayoutView() {
        return R.layout.activity_search_base;
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        mBackTv = (TextView) findViewById(R.id.back_textview);
        mBackTv.setOnClickListener(this);
        mSearchContentSv = (SearchView) findViewById(R.id.search_content_sv);
        mSearchTv = (TextView) findViewById(R.id.search_tv);
        mSearchTv.setOnClickListener(this);
        mDeleteHistoryIv = (ImageView) findViewById(R.id.delete_history_iv);
        mDeleteHistoryIv.setOnClickListener(this);
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.history_item_tfl);
        mShowMoreArrowIv = (ImageView) findViewById(R.id.show_more_arrow_iv);
        mHisRecordCl = (ConstraintLayout) findViewById(R.id.his_record_cl);
        mSearchInfoRv = (RecyclerView) findViewById(R.id.search_info_rv);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.search_srl);
        mSearchContentSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!StringTools.isStringValueOk(s)) {
                    ToastUtils.warning(mContext, "请输入要搜索的内容");
                    return false;
                }
                //保存搜索历史
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
        initViewLeftDrawable(mBackTv, R.drawable.app_back, 23, 23);
        initFlowLayoutData();
    }

    /**
     * 初始化流式布局数据
     */
    public void initFlowLayoutData() {
        List<String> recordList = Hawk.get(SEARCH_HIS_KEY);
        if (recordList != null && !recordList.isEmpty()) {
            mHisRecordCl.setVisibility(View.VISIBLE);
        } else {
            mHisRecordCl.setVisibility(View.GONE);
        }
        //创建历史标签适配器
        //为标签设置对应的内容
        mRecordsAdapter = new TagAdapter<String>(recordList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.search_item_textview,
                        tagFlowLayout, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };

        tagFlowLayout.setAdapter(mRecordsAdapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                List<String> his_data = Hawk.get(SEARCH_HIS_KEY);
                mSearchContentSv.setQuery(his_data.get(position), true);
            }
        });
        //删除某个条目
        tagFlowLayout.setOnLongClickListener(new TagFlowLayout.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                showDialog("确定要删除该条历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除某一条记录
                        deletHisItem(position);
                    }
                });
            }
        });

        //view加载完成时回调
        tagFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = tagFlowLayout.isOverFlow();
                boolean isLimit = tagFlowLayout.isLimit();
                if (isLimit && isOverFlow) {
                    mShowMoreArrowIv.setVisibility(View.VISIBLE);
                } else {
                    mShowMoreArrowIv.setVisibility(View.GONE);
                }
            }
        });

        mShowMoreArrowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
                mHisRecordCl.setVisibility(View.GONE);
            }
        });
    }


    public void initSearchClick(String content) {
        //将搜索的内容添加到本地
        List<String> his_data = null;
        if (Hawk.contains(SEARCH_HIS_KEY)) {
            his_data = Hawk.get(SEARCH_HIS_KEY);
        } else {
            his_data = new ArrayList<>();
        }
        if (!his_data.contains(content)) {
            his_data.add(0, content);
            Hawk.put(SEARCH_HIS_KEY, his_data);
            initFlowLayoutData();
        }
        mSearchContentSv.setFocusable(false);
        mSearchContentSv.setFocusableInTouchMode(false);
        mSearchContentSv.clearFocus();
        gotoSearch(content);
    }

    /**
     * 删除历史条目
     *
     * @param position
     */
    public void deletHisItem(int position) {
        List<String> his_data = Hawk.get(SEARCH_HIS_KEY);
        his_data.remove(position);
        Hawk.put(SEARCH_HIS_KEY, his_data);
        initFlowLayoutData();
    }

    /**
     * 删除条目的对话框
     *
     * @param dialogTitle
     * @param onClickListener
     */
    public void showDialog(String dialogTitle, @NonNull DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(dialogTitle);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back_textview:
                onBackPressed();
                break;
            case R.id.search_tv:
                //搜索
                String content = mSearchContentSv.getQuery().toString();
                if (!StringTools.isStringValueOk(content)) {
                    ToastUtils.warning(mContext, "请输入要搜索的内容");
                    return;
                }
                initSearchClick(content);
                break;
            case R.id.delete_history_iv:
                //删除所有的记录
                showDialog("确定要删除所有历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除所有记录
                        Hawk.delete(SEARCH_HIS_KEY);
                        initFlowLayoutData();
                    }
                });
                break;
        }
    }

    /**
     * 搜索
     * @param content
     */
    public abstract void gotoSearch(String content);
}
