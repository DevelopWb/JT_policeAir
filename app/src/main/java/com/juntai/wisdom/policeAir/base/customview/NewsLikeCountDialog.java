package com.juntai.wisdom.policeAir.base.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.juntai.wisdom.policeAir.R;

/**
 * Describe:弹出窗（需传入view）
 * Create by zhangzhenlong
 * 2020-8-18
 * email:954101549@qq.com
 */
public class NewsLikeCountDialog {
    TextView nameTv;
    TextView countTv;
    TextView okBtn;

    static View contentView;
    private Context context;
    private Dialog dialog;
    private Display display;

    public NewsLikeCountDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public NewsLikeCountDialog builder() {
        contentView = LayoutInflater.from(context).inflate(
                R.layout.news_like_count_dialog, null);
        nameTv = contentView.findViewById(R.id.dialog_name_tv);
        countTv = contentView.findViewById(R.id.dialog_like_count_tv);
        okBtn = contentView.findViewById(R.id.dialog_ok_btn);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(contentView);
        return this;
    }

    /**
     * 设置数据
     * @param name
     * @param count
     * @return
     */
    public NewsLikeCountDialog setData(String name, int count){
        nameTv.setText(name);
        countTv.setText(count + "");
        return this;
    }

    public void dismissDialog() {
        contentView = null;
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 设置确认监听
     * @param text
     * @param listener
     * @return
     */
    public NewsLikeCountDialog setOkButton(String text, final View.OnClickListener listener) {
        if (text != null && !text.equals("")){
            okBtn.setText(text);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentView = null;
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
    public NewsLikeCountDialog setCanceledOnTouchOutside(boolean isCan) {
        dialog.setCanceledOnTouchOutside(isCan);
        dialog.setCancelable(isCan);
        return this;
    }

    public void show() {
        dialog.show();
    }
}
