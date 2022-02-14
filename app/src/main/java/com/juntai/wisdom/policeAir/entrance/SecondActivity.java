package com.juntai.wisdom.policeAir.entrance;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.policeAir.R;
/**
 * @description web网页打开指定页面并传参测试
 * @aouther ZhangZhenlong
 * @date 2020-12-23
 */
public class SecondActivity extends BaseMvpActivity {

    private TextView mWebData;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_second;
    }

    @Override
    public void initView() {
        setTitleName("来自网页");
        mWebData = (TextView) findViewById(R.id.web_data);
    }

    @Override
    public void initData() {
        //获取网页传递过来的参数
        Intent mgetvalue = getIntent();
        String maction = mgetvalue.getAction();
        if (Intent.ACTION_VIEW.equals(maction)) {
            Uri uri = mgetvalue.getData();
            if (uri != null) {
                String title = uri.getQueryParameter("title");
                String content = uri.getQueryParameter("content");
                mWebData.setText("网页传递值为：title=" + title + ",content=" + content);
            }
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    /**
     *返回处理，防止点击返回按钮的时候，会直接退出当前app
     **/
    @Override
    public void onBackPressed() {
        //NavUtils.getParentActivityIntent()方法可以获取到跳转至父Activity的Intent或者为null
        //NavUtils.shouldUpRecreateTask() 返回true父Activity应该重新创建一个新的任务栈，返回false同样的任务栈应该被使用作为目标Intent
        //isTaskRoot()用来判断该Activity是否为任务栈中的根Activity，即启动应用的第一个Activity
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent) || isTaskRoot()) {
            //如果父Activity和当前Activity不在同一个Task中的，则需要借助TaskStackBuilder创建一个新的Task
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            //如果父Activity和当前Activity是在同一个Task中的，则直接调用navigateUpTo()方法进行跳转
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, upIntent);
        }
        super.onBackPressed();
    }
}
