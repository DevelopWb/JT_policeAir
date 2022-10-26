package com.videoaudiocall.videocall;

import android.media.MediaPlayer;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.videoaudiocall.library.R;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/12/11 15:29
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/11 15:29
 */
public abstract class SoundManagerActivity<P extends BasePresenter> extends BaseMvpActivity<P> {
    private MediaPlayer mediaPlayer;

    @Override
    public void initView() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.shake);
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        play();
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
            }
        }
    }

    /**
     * 开始播放无声音乐
     */
    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * 暂停无声音乐
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 暂停无声音乐
     */
    private void release() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    protected void onDestroy() {
        release();
        super.onDestroy();
    }
}
