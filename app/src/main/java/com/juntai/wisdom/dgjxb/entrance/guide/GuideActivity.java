package com.juntai.wisdom.dgjxb.entrance.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.SPTools;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.MainActivity;
import com.juntai.wisdom.dgjxb.entrance.LoginActivity;
import com.juntai.wisdom.dgjxb.mine.UserAgreementActivity;
import com.juntai.wisdom.dgjxb.base.customview.WarnDialog;

import java.util.ArrayList;
/**
 * @aouther tobato
 * @description 引导页
 * @date 2020/3/5 16:03
 */
public class GuideActivity extends BaseActivity {

    private ViewPager vp;
    private LinearLayout llPoints;
    private TextView experienceNowTv;
    private int lastPointIndex = 0,pointViewSize = 12;
    int[] imgs = new int[]{R.mipmap.bg_guide_news, R.mipmap.bg_guide_intergral,R.mipmap.bg_guide_alarm, R.mipmap.bg_guide_pay,R.mipmap.bg_guide_shop};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        pointViewSize = DisplayUtil.dp2px(this,pointViewSize);
        llPoints = findViewById(R.id.guideActivity_ll_points);
        vp = findViewById(R.id.guideActivity_vp);
        experienceNowTv = findViewById(R.id.experience_tv);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                llPoints.getChildAt(lastPointIndex).setEnabled(false);
                //将当前的点选中
                llPoints.getChildAt(i).setEnabled(true);
                if (i == (imgs.length -1)){
//                    experienceNowTv.setVisibility(View.VISIBLE);
                    showAgreementAlter();
                }else {
//                    experienceNowTv.setVisibility(View.GONE);
                }
                lastPointIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        experienceNowTv.setOnClickListener(v -> {
                SPTools.saveBoolean(GuideActivity.this,"first_start",false);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
        });
    }

    @Override
    public void initData() {

        ArrayList<ImageView> imageViews = new ArrayList<>();
        ImageView imageView;
        View pointView = null;
        LinearLayout.LayoutParams layoutParams = null;
        for (int i = 0; i < imgs.length; i++) {

            //添加图片
            imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgs[i]);

            imageViews.add(imageView);
            //添加指针
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.guide_point_selecter);

            //指针的宽度和高度
            layoutParams = new LinearLayout.LayoutParams(pointViewSize,pointViewSize);

            if (i==0){
                pointView.setEnabled(true);
            } else {
                //指针距离左边的间距
                layoutParams.leftMargin = pointViewSize;
                pointView.setEnabled(false);
            }

            llPoints.addView(pointView,layoutParams);

        }
        GuideAdapter guideAdapter = new GuideAdapter(imageViews);
        vp.setAdapter(guideAdapter);
    }

    private void showAgreementAlter(){
        Intent intentAgreement = new Intent(this, UserAgreementActivity.class);
        SpannableStringBuilder spannable = new SpannableStringBuilder(getString(R.string.agreement_xieyi_tag));
        // 在设置点击事件、同时设置字体颜色
        ClickableSpan clickableSpanOne = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                LogUtil.e("isGo", "点击了用户协议");
                intentAgreement.putExtra("url",getString(R.string.user_xieyi_url));
                GuideActivity.this.startActivity(intentAgreement);
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setColor(getResources().getColor(R.color.colorTheme));
                // 设置下划线 true显示、false不显示
                paint.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpanTwo = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                intentAgreement.putExtra("url",getString(R.string.secret_xieyi_url));
                GuideActivity.this.startActivity(intentAgreement);
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setColor(getResources().getColor(R.color.colorTheme));
                // 设置下划线 true显示、false不显示
                paint.setUnderlineText(false);
            }
        };
        spannable.setSpan(clickableSpanOne, 106, 112, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(clickableSpanTwo, 113, 119, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        WarnDialog agreementDialog = new WarnDialog(this).builder();
        agreementDialog.getContentTextView().setMovementMethod(LinkMovementMethod.getInstance());
        agreementDialog.setCanceledOnTouchOutside(false)
                .setTitle("用户协议和隐私协议")
                .setContent(spannable)
                .setCancelButton("暂不使用", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setOkButton("同意并进入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPTools.saveBoolean(GuideActivity.this,"first_start",false);
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                        finish();
                    }
                }).show();
    }

}
