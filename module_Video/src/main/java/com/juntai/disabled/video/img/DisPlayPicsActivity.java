package com.juntai.disabled.video.img;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juntai.disabled.basecomponent.base.BaseDownLoadActivity;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.video.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片大图查看
 * Created by Ma
 * on 2019/5/22
 */
public class DisPlayPicsActivity extends BaseDownLoadActivity {
    MyViewPagerAdapter myViewPagerAdapter;
    ViewPager viewPager;
    List<View> viewList = new ArrayList<>();
    private ArrayList<String> paths;
    private String diaplayPath;
   public static String   PICS = "pics";
    public static String  PIC_POSITION = "picPositon";

    public static  void  startDisplayPics(Context mContext, ArrayList<String> pics, int picPosition){
        mContext.startActivity(new Intent(mContext, DisPlayPicsActivity.class)
                .putExtra(PICS, pics)
                .putExtra(PIC_POSITION, picPosition));
    }


    @Override
    public int getLayoutView() {
        return R.layout.activity_imagezoom;
    }

    @Override
    public void initView() {
        mBaseRootCol.setFitsSystemWindows(true);
        viewPager = findViewById(R.id.imagezoom_viewpager);
       LinearLayout mDisplayLl = findViewById(R.id.display_pic_ll);
        mDisplayLl.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        setFileDownLoadCallBack(new OnFileDownloaded() {
            @Override
            public void onFileDownloaded(String fileName) {
                ToastUtils.toast(mContext, "已保存");
            }
        });
    }


    @Override
    public void initData() {
        paths = getIntent().getStringArrayListExtra(PICS);
        if (paths == null) {
            ToastUtils.toast(mContext,"请传入需要展示图片的路径");
            finish();
            return;
        }
        int item = getIntent().getIntExtra(PIC_POSITION, 0);
        if (item>=paths.size()) {
            ToastUtils.toast(mContext,"图片的索引越界");
            finish();
            return;
        }
        diaplayPath = paths.get(item);
        for (String path : paths) {

            PhotoView photoView = new PhotoView(this);
            photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            photoView.enable();
            ImageLoadUtil.loadImageCache(this, path, photoView);
            viewList.add(photoView);
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    initBottomDialog(getBaseBottomDialogMenus("保存图片"),diaplayPath);
                    return false;
                }
            });
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        myViewPagerAdapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(item);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                setTitleName("第 " + (i + 1) + " 张");
                diaplayPath = paths.get(i);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }


    @Override
    protected String getDownLoadPath() {
        return diaplayPath;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
