package com.juntai.wisdom.policeAir.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.video.Jzvd.MyJzvdStd;
import com.juntai.wisdom.video.Jzvd.OnFullScreenClickListener;
import com.youth.banner.loader.ImageLoaderInterface;

public class GlideImageLoader implements ImageLoaderInterface<View> {
    MyJzvdStd videoPlayer;
    long seek = 0;
    String  pathV;
    private OnFullScreenListener onFullScreenListener;

    public GlideImageLoader setOnFullScreenCallBack(OnFullScreenListener onFullScreenListener) {
        this.onFullScreenListener = onFullScreenListener;
        return this;
    }

    @Override
    public void displayImage(Context context, Object path, View view) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        //eg：
        if (((String)path).contains(".mp4") || ((String) path).contains("getVideo")){
            pathV = (String) path;
            startVideo(context);
        }else {
            ImageView imageView = (ImageView) view;
            //Glide 加载图片简单用法
            ImageLoadUtil.loadImageCache(context, (String) path,imageView);
        }
    }


    @Override
    public View createImageView(Context context, Object path) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        if (((String) path).contains(".mp4") || ((String) path).contains("getVideo")){
            videoPlayer = new MyJzvdStd(context);
            return videoPlayer;
        }else {
            ImageView simpleDraweeView = new ImageView(context);
            return simpleDraweeView;
        }
    }

    private void startVideo(Context mContext) {
        videoPlayer.setUp(pathV, "");
        Glide.with(mContext.getApplicationContext()).load(pathV).into(videoPlayer.thumbImageView);
        //设置全屏按键功能
        if (onFullScreenListener != null){
            videoPlayer.setOnFullScreenClickListener(new OnFullScreenClickListener() {
                @Override
                public void onFullScreenClick() {
                    onFullScreenListener.onFullScreen();
                }
            });
        }
    }

    public void release(){
        if (videoPlayer != null){
            videoPlayer.setOnFullScreenClickListener(null);
        }
    }

    /**
     * 全屏得点击事件
     */
    public interface OnFullScreenListener{
        void onFullScreen();
    }
}
