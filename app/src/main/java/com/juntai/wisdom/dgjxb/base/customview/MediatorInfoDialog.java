package com.juntai.wisdom.dgjxb.base.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.conciliation.MediatorAllListBean;
import com.juntai.wisdom.dgjxb.utils.StringTools;

/**
 * Describe: 调解员介绍弹窗
 * Create by zhangzhenlong
 * 2020-7-18
 * email:954101549@qq.com
 */
public class MediatorInfoDialog {
    private ImageView mItemClose;
    private ImageView mItemIv;
    /**
     * 姓名
     */
    private TextView mItemName;
    /**
     * 18763739973
     */
    private TextView mItemTel;
    /**
     * 姓名
     */
    private TextView mItemInfo;

    MediatorAllListBean.MediatorBean mediatorBean;

    private Context context;
    private Dialog dialog;

    public MediatorInfoDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
    }

    public MediatorInfoDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_mediator_info_dialog, null);

        // 获取自定义Dialog布局中的控件
        mItemClose = view.findViewById(R.id.item_close);
        mItemIv = view.findViewById(R.id.item_iv);
        mItemName = view.findViewById(R.id.item_name);
        mItemTel = view.findViewById(R.id.item_tel);
        mItemInfo = view.findViewById(R.id.item_info);
        mItemClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        return this;
    }

    public MediatorInfoDialog setMediatorBean(MediatorAllListBean.MediatorBean mediatorBean1) {
        this.mediatorBean = mediatorBean1;
        ImageLoadUtil.loadCircularImage(context.getApplicationContext(), mediatorBean.getHeadPortrait(),
                R.mipmap.default_user_head_icon, R.mipmap.default_user_head_icon, mItemIv);
        mItemName.setText(mediatorBean.getName());
        if (StringTools.isStringValueOk(mediatorBean.getPhone())){
            mItemTel.setText(mediatorBean.getPhone());
        }else {
            mItemTel.setVisibility(View.GONE);
        }
        mItemInfo.setText(mediatorBean.getIntroduction());
        return this;
    }

    /**
     * 是否点击外部消失
     *
     * @param isCan
     * @return
     */
    public MediatorInfoDialog setCanceledOnTouchOutside(boolean isCan) {
        dialog.setCanceledOnTouchOutside(isCan);
        dialog.setCancelable(isCan);
        return this;
    }

    public void show() {
        dialog.show();
    }
}
