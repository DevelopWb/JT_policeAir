package com.juntai.wisdom.dgjxb.base.customview;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.utils.StringTools;

/**
 * Describe:删除消息弹窗
 * Create by zhangzhenlong
 * 2020-8-21
 * email:954101549@qq.com
 */
public class DeleteMsgDialog {
    Button btn;

    private Context context;
    private Dialog dialog;
    private DisplayMetrics display;
    private RelativeLayout lLayout_bg;

    public DeleteMsgDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = context.getResources().getDisplayMetrics();
    }

    public DeleteMsgDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.dialog_delete_msg, null);
        btn = contentView.findViewById(R.id.delete_btn);
        lLayout_bg = contentView.findViewById(R.id.page_layout);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.BaseDialog);
        dialog.setContentView(contentView);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.widthPixels * 0.6),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 设置数据
     * @param name
     * @return
     */
    public DeleteMsgDialog setData(String name){
        if (StringTools.isStringValueOk(name)){
            btn.setText(name);
        }
        return this;
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 设置确认监听
     * @param listener
     * @return
     */
    public DeleteMsgDialog setButtonClickListener(final View.OnClickListener listener) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });
        return this;
    }

    /**
     * 是否点击外部消失
     * @param isCan
     * @return
     */
    public DeleteMsgDialog setCanceledOnTouchOutside(boolean isCan) {
        dialog.setCanceledOnTouchOutside(isCan);
        dialog.setCancelable(isCan);
        return this;
    }

    public void show() {
        dialog.show();
    }
}
