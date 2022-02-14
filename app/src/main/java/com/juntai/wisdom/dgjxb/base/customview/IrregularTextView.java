package com.juntai.wisdom.dgjxb.base.customview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.dgjxb.R;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  不规则text
 * @date 2020/3/14 16:40
 */

public class IrregularTextView extends LinearLayout {

    private Context context;
    private static int ITEM_LEFT_MARGIN = 10;//item左边距
    private static int LAYOUT_TOP_MARGIN = 8;//layout 顶边据

    public IrregularTextView(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);//设置方向
    }

    public IrregularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);//设置方向
    }

    public IrregularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);//设置方向
    }


    /**
     * 外部接口调用
     *
     * @param items
     * @param OnTextItemClick
     */
    public void initViews(String items[], final OnTextItemClick OnTextItemClick) {
        removeAllViews();
        int length = 0;//一行加载item 的宽度

        LinearLayout layout = null;

        LayoutParams layoutLp = null;

        boolean isNewLine = true;//是否换行

        int screenWidth = getScreenWidth();//屏幕的宽度
//        int screenWidth = 150;//屏幕的宽度

        int size = items.length;
        for (int i = 0; i < size; i++) {//便利items
            View view = LayoutInflater.from(context).inflate(R.layout.search_item_textview, null);
            TextView itemView = (TextView) view.findViewById(R.id.text);
            itemView.setText(items[i]);
            //设置item的参数
            LayoutParams itemLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemLp.leftMargin = DisplayUtil.dp2px(context,ITEM_LEFT_MARGIN);
            final int j = i;
            itemView.setOnClickListener(new OnClickListener() {//给每个item设置点击事件
                @Override
                public void onClick(View v) {
                    if (null != OnTextItemClick) {
                        OnTextItemClick.onClick(j);
                    }
                }
            });

            if (isNewLine) {//是否开启新的一行
                layout = new LinearLayout(context);
                layout.setOrientation(HORIZONTAL);
                layoutLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i>0) {
                    layoutLp.topMargin = DisplayUtil.dp2px(context,LAYOUT_TOP_MARGIN);
                }
                //如果当前view超过一行的宽度,直接换行
                if (getViewWidth(view) > screenWidth) {
                    isNewLine = true;
                    addView(layout, layoutLp);
                    layout.addView(view, itemLp);
                    return;
                }
            }
            //得到当前行的长度
            length += DisplayUtil.dp2px(context,ITEM_LEFT_MARGIN) + getViewWidth(view);
            if (length > screenWidth) {//当前行的长度大于屏幕宽度则换行
                length = 0;
                addView(layout, layoutLp);
                isNewLine = true;
                i--;
            } else {//否则添加到当前行
                isNewLine = false;
                layout.addView(view, itemLp);
            }
        }

    }

    /**
     * @param items
     * @param OnTextItemClick
     */
    public void initViews(List<String> items, OnTextItemClick OnTextItemClick) {
        initViews((String[]) items.toArray(new String[items.size()]), OnTextItemClick);
    }

    /**
     * 得到手机屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 得到手机屏幕的高度
     *
     * @return
     */
    private int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 得到view控件的宽度
     *
     * @param view
     * @return
     */
    private int getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    public interface OnTextItemClick {
        void onClick(int position);
    }
}