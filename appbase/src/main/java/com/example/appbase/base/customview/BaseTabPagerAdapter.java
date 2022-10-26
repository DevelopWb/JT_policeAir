package com.example.appbase.base.customview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.appbase.R;
import com.example.appbase.bean.BaseTabBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页tablayout适配器
 */
public class BaseTabPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    SparseArray<Fragment> mFragments;
    private List<BaseTabBean> titles;

    public List<BaseTabBean> getTitles() {
        if (titles == null) {
            return new ArrayList<>();
        }
        return titles;
    }

    public BaseTabPagerAdapter(FragmentManager fm, Context contexts, List<BaseTabBean> title, SparseArray<Fragment> fragments) {
        super(fm);
        mContext = contexts;
        mFragments = fragments;
        this.titles = title;
    }

    public void setTitles(List<BaseTabBean> titles) {
        this.titles = titles;
       notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    /**
     * 自定义底部消息tab
     * @param position
     * @return
     */
    public View getCustomTabView(int position) {
        BaseTabBean baseTabBean = titles.get(position);
        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_home_shop_tabitem, null);
       TextView msgUnReadCountTv = v.findViewById(R.id.tabitem_count);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setTextSize(14);
        title.setText(baseTabBean.getTitle());
        msgUnReadCountTv.setVisibility(baseTabBean.getUnreadAmount()>0?View.VISIBLE:View.INVISIBLE);
        msgUnReadCountTv.setText(String.valueOf(baseTabBean.getUnreadAmount()));
        return v;
    }



}
