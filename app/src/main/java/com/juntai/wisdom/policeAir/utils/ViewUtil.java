package com.juntai.wisdom.policeAir.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.juntai.wisdom.policeAir.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baidu on 16/7/21.
 */
public class ViewUtil {

    private Toast mToast = null;

    private TextView mTextView = null;

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static void initSystemBar(Activity activity, int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);

        // 使用颜色资源
        tintManager.setStatusBarTintResource(res);

    }

    public static void startActivityForResult(Activity fromActivity, Class<?> toClass, int requestCode) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivityForResult(intent, requestCode);
        fromActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public static void startActivity(Activity fromActivity, Class<?> toClass) {
        Intent intent = new Intent(fromActivity, toClass);
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 调整Picker布局
     *
     * @param frameLayout
     */
    public static void resizePicker(int width,FrameLayout frameLayout) {
        List<NumberPicker> numberPickers = findNumberPicker(frameLayout);
        for (NumberPicker numberPicker : numberPickers) {
            resizeNumberPicker(width,numberPicker);
        }
    }

    /**
     * 获取ViewGroup中的NumberPicker组件
     *
     * @param viewGroup
     *
     * @return
     */
    private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> numberPickers = new ArrayList<>();
        View child;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    numberPickers.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return numberPickers;
    }

    /**
     * 调整NumberPicker大小
     *
     * @param numberPicker
     */
    private static void resizeNumberPicker(int width,NumberPicker numberPicker) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(15, 0, 15, 0);
        numberPicker.setLayoutParams(params);
    }

    /**
     * 获取到Activity的实际屏幕信息。
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(FragmentActivity activity) {
//        DisplayMetrics dm = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
//        return dm.heightPixels;
        Rect outRect= new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.bottom;//可视区域底部的坐标  (屏幕高度-导航栏-键盘的高度)outRect.top -> 可视区域顶部的坐标  (也就是状态栏的高度)
    }
    /**
     * 获取到Activity的实际屏幕信息。
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(FragmentActivity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        return dm.widthPixels;
    }

}
