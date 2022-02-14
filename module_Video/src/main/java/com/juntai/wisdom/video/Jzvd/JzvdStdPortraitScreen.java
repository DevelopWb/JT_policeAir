package com.juntai.wisdom.video.Jzvd;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import cn.jzvd.JzvdStd;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-12-27
 * email:954101549@qq.com
 */
public class JzvdStdPortraitScreen extends JzvdStd {
    public JzvdStdPortraitScreen(Context context) {
        super(context);
    }

    public JzvdStdPortraitScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        fullscreenButton.setVisibility(INVISIBLE);
        topContainer.setVisibility(GONE);
    }

    //changeUiTo 真能能修改ui的方法
    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
        fullscreenButton.setVisibility(INVISIBLE);
        topContainer.setVisibility(GONE);
    }

    @Override
    public void changeUiToPreparing() {
        super.changeUiToPreparing();
    }

    @Override
    public void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
//        bottomProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPlayingClear() {
        super.changeUiToPlayingClear();
//        bottomProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPauseShow() {
        super.changeUiToPauseShow();
//        bottomProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPauseClear() {
        super.changeUiToPauseClear();
//        bottomProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToComplete() {
        super.changeUiToComplete();
    }

    @Override
    public void changeUiToError() {
        super.changeUiToError();
    }

    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
        Log.i(TAG, "click blank");
//        startButton.performClick();
        fullscreenButton.setVisibility(INVISIBLE);
        topContainer.setVisibility(GONE);
    }
}
