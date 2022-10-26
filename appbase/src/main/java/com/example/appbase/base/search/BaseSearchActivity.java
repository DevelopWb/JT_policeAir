package com.example.appbase.base.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.R;
import com.example.appbase.base.BaseRecyclerviewActivity;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;

/**
 * @aouther tobato
 * @description 描述  这种搜索是跳转到一个新的界面搜索
 * @date 2022-01-25 9:32
 */
public abstract class BaseSearchActivity<P extends BasePresenter> extends BaseRecyclerviewActivity<P> implements View.OnClickListener {

    public SearchView mSearchContentSv;
    /**
     * 取消
     */
    public TextView mBackTv;


    @Override
    public int getLayoutView() {
        return R.layout.sell_activity_base_search;
    }

    @Override
    public void initView() {
        super.initView();
        setTitleName(getTitleName());
        initToolbarAndStatusBar(false);
        mImmersionBar.statusBarDarkFont(true).init();
        mSearchContentSv = (SearchView) findViewById(R.id.search_content_sv);
        mBackTv = (TextView) findViewById(R.id.cancel_tv);
        mBackTv.setOnClickListener(this);
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) mSearchContentSv.findViewById(R.id.search_src_text);
        textView.setTextSize(12);
        mSearchContentSv.requestFocus();
        mSearchContentSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (TextUtils.isEmpty(s)) {
                    return true;
                }
                commitSearch(s);
//                getViewFocus(mRecyclerview);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    return true;
                }
                commitSearch(s);
                return false;
            }
        });


    }

    protected abstract String getTitleName();

    /**
     * 提交搜索
     * @param s
     * @return
     */
    protected abstract boolean commitSearch(String s);




    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onAdapterItemClick(adapter,position);
            }
        });
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }


    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

   

    protected abstract void onAdapterItemClick(BaseQuickAdapter adapter, int position);




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_tv) {
            finish();
        }
    }

}
