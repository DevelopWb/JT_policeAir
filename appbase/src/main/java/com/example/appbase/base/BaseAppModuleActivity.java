package com.example.appbase.base;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.example.appbase.R;
import com.example.appbase.base.selectPics.BaseSelectPicsActivity;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseWebViewActivity;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppModuleActivity<P extends BasePresenter> extends BaseSelectPicsActivity<P> {

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public void copy(String content) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        ToastUtils.toast(mContext, "已复制");
    }

    @Override
    protected String getUpdateHttpUrl() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    /**
     * 解析二维码
     *
     * @param result
     */
    public void resolveQrcode(String result) {
        // : 2022/7/15 分享的逻辑需要调整
        /**
         *  21960 菜优选的端口号
         */
        if (result.contains("juntaikeji") && result.contains("juntaitype")) {
            //内部二维码
            String type = result.substring(result.lastIndexOf("=") + 1, result.length());
            String id = result.substring(result.indexOf("=") + 1, result.indexOf("&"));
            /**
             * 1 商品 2 商家 3 直播
             */
            switch (type) {
                case "1":
                    // : 2022/5/31 商品分享
                    ARouter.getInstance().build(ARouterPath.appCommodityDetailActivity)
                            .withInt(BaseActivity.BASE_ID, Integer.parseInt(id))
                            .navigation(BaseAppModuleActivity.this, BaseActivity.BASE_REQUEST_RESULT);
                    break;
                case "2":
                    //店铺分享

                    ARouter.getInstance().build(ARouterPath.appShopActivity)
                            .withInt(BaseActivity.BASE_ID, Integer.parseInt(id))
                            .navigation(BaseAppModuleActivity.this, BaseActivity.BASE_REQUEST_RESULT);
                    break;
                case "3":
                    //直播
                    ARouter.getInstance().build(ARouterPath.live_LiveRoomActivity)
                            .withString(BASE_STRING, id)
                            .navigation();

                    break;
                case "4":
                    // 农发 订单详情
                    ARouter.getInstance().build(ARouterPath.nf_NFOrderDetailActivity)
                            .withInt(BaseActivity.BASE_ID, Integer.parseInt(id))
                            .navigation();
                    break;
                default:
                    break;
            }
        } else {
            startActivity(new Intent(mContext, BaseWebViewActivity.class).putExtra("url", result));
        }
    }

    /**
     * 获取文件名称
     *
     * @return
     */
    public String getSavedFileName(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/") + 1, content.length());
        }
        return content;
    }

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("account", UserInfoManager.getAccount())
                .add("token", UserInfoManager.getUserToken())
                .add("userId", String.valueOf(UserInfoManager.getUserId()));
        if (!TextUtils.isEmpty(UserInfoManager.getDevType())) {
            builder.add("typeEnd", UserInfoManager.getDevType());
        }
        if (UserInfoManager.getSchoolId() > 0) {
            builder.add("schoolId", String.valueOf(UserInfoManager.getSchoolId()));
        }
        return builder;
    }


    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }


    @Override
    public boolean requestLocation() {
        return false;
    }


    @Override
    protected void onPicsAndEmpressed(List<String> icons) {

    }

    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

    /**
     * 展示带对话框的dialog
     */
    public void showAlertDialogWithEditText(String title, OnBaseInterface.OnCommitInterface baseCommitInterface) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_alertdialog, null);
        AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.custom_alertdialog_style)
                .setView(view)
                .setCancelable(false)
                .show();
        TextView contentTv = view.findViewById(R.id.content_et);
        TextView titleTv = view.findViewById(R.id.alert_title_tv);
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
        view.findViewById(R.id.alert_title_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.commit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getTextViewValue(contentTv))) {
                    ToastUtils.toast(mContext, "请输入内容");
                    return;
                }
                alertDialog.dismiss();
                if (baseCommitInterface != null) {
                    baseCommitInterface.commit(getTextViewValue(contentTv));
                }
            }
        });
        setAlertDialogHeightWidth(alertDialog, (int) (getScreenWidth() * 0.8f), 0);

    }


}
