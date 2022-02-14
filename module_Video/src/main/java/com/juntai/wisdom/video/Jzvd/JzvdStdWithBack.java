package com.juntai.wisdom.video.Jzvd;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.juntai.wisdom.video.R;

import cn.jzvd.JzvdStd;

public class JzvdStdWithBack extends JzvdStd {
    private OnFullScreenClickListener onFullScreenClickListener;
    public JzvdStdWithBack(Context context) {
        super(context);
    }

    public JzvdStdWithBack(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == cn.jzvd.R.id.fullscreen) {
            Log.i(TAG, "onClick: fullscreen button");
            if (onFullScreenClickListener != null){
                onFullScreenClickListener.onFullScreenClick();
                return;
            }
        }
        super.onClick(v);
    }

    //changeUiTo 真能能修改ui的方法
    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPreparing() {
        super.changeUiToPreparing();
    }

    @Override
    public void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPlayingClear() {
        super.changeUiToPlayingClear();
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPauseShow() {
        super.changeUiToPauseShow();
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
    }

    @Override
    public void changeUiToPauseClear() {
        super.changeUiToPauseClear();
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
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
        backButton.setVisibility(VISIBLE);
        topContainer.setVisibility(VISIBLE);
    }

    public OnFullScreenClickListener getOnFullScreenClickListener() {
        return onFullScreenClickListener;
    }

    public void setOnFullScreenClickListener(OnFullScreenClickListener onFullScreenClickListener) {
        this.onFullScreenClickListener = onFullScreenClickListener;
    }
}
