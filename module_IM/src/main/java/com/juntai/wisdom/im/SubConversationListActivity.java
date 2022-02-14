package com.juntai.wisdom.im;

import android.support.v4.app.FragmentTransaction;

import com.juntai.wisdom.basecomponent.base.BaseActivity;

import io.rong.imkit.fragment.SubConversationListFragment;

/**
 * Created by Ma
 * on 2019/5/11
 */
public class SubConversationListActivity extends BaseActivity {
    @Override
    public int getLayoutView() {
        return R.layout.activity_msg;
    }

    @Override
    public void initView() {
        setTitleName("系统消息");
        SubConversationListFragment fragment = new SubConversationListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }

    @Override
    public void initData() {

    }
}