package com.juntai.wisdom.policeAir.mine.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.bean.UnionidBean;
import com.juntai.wisdom.basecomponent.utils.DialogUtil;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.GsonTools;
import com.juntai.wisdom.basecomponent.utils.HttpUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.PubUtil;
import com.juntai.wisdom.basecomponent.utils.RxScheduler;
import com.juntai.wisdom.basecomponent.utils.RxTask;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.BaseAppActivity;
import com.juntai.wisdom.policeAir.bean.MyMenuBean;
import com.juntai.wisdom.basecomponent.bean.UserBean;
import com.juntai.wisdom.policeAir.entrance.BackPwdActivity;
import com.juntai.wisdom.policeAir.entrance.EntranceContract;
import com.juntai.wisdom.policeAir.entrance.EntrancePresent;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.base.update.UpdateActivity;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.basecomponent.utils.UserInfoManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * ????????????
 *
 * @aouther ZhangZhenlong
 * @date 2020/3/9
 */
public class MySettingActivity extends BaseAppActivity<EntrancePresent> implements EntranceContract.IEntranceView {
    SettingMenuAdapter settingMenuAdapter;
    List<MyMenuBean> menuBeans = new ArrayList<>();

    private RecyclerView mMenuRecycler;
    PlatformDb platDB;
    public String QQId = "", QQName = "", WeChatId = "", WeChatName = "";

    MyHandler myHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        WeakReference<Activity> mActivity;//?????????

        MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MySettingActivity theActivity = (MySettingActivity) mActivity.get();
            switch (msg.what) {
                //??????????????????what????????????????????????
                case 1:
                    theActivity.updateBind();
                    break;
            }
        }
    }

    @Override
    protected EntrancePresent createPresenter() {
        return new EntrancePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void initView() {
        setTitleName("????????????");
        mMenuRecycler = (RecyclerView) findViewById(R.id.menu_recycler);

        settingMenuAdapter = new SettingMenuAdapter(R.layout.my_center_menu_item, menuBeans);
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMenuRecycler.setAdapter(settingMenuAdapter);

        settingMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (settingMenuAdapter.getData().get(position).getTag()) {
                    case MyCenterContract.SET_UPDATE_TAG:
                        update(true);
                        break;
                    case MyCenterContract.SET_CLEAR_TAG:
                        DialogUtil.getMessageDialog(mContext, "??????????????????????????????????????????????????????",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        RxScheduler.doTask(MySettingActivity.this, new RxTask<String>() {
                                            @Override
                                            public String doOnIoThread() {
                                                FileCacheUtils.clearAll(mContext.getApplicationContext());
                                                return "????????????";
                                            }

                                            @Override
                                            public void doOnUIThread(String s) {
                                                ToastUtils.success(mContext.getApplicationContext(), s);
                                            }
                                        });
                                    }
                                }).show();
                        break;
                    case MyCenterContract.SET_UPDATE_TEL_TAG:
                        //???????????????
                        if (UserInfoManager.getAccountStatus() == 1) {
                            //???????????????  ?????????????????????????????????
                            startActivity(new Intent(mContext, BackPwdActivity.class).putExtra("pageType", 1));
                        } else {
                            //??????????????? ???????????????
                            startActivityForResult(new Intent(mContext,
                                    settingMenuAdapter.getData().get(position).getCls()), BASE_REQUEST_RESULT);
                        }

                        break;
                    case MyCenterContract.SET_WEIXIN_TAG:
                        //??????
                        if (StringTools.isStringValueOk(MyApp.getUser().getData().getWeChatName())) {
                            ToastUtils.info(mContext, "???????????????");
                        } else {
                            bindQQOrWeChat(Wechat.NAME);
                        }
                        break;
                    case MyCenterContract.SET_QQ_TAG:
                        //qq
                        if (StringTools.isStringValueOk(MyApp.getUser().getData().getQqName())) {
                            ToastUtils.info(mContext, "QQ?????????");
                        } else {
                            bindQQOrWeChat(QQ.NAME);
                        }
                        break;
                    case MyCenterContract.SET_UPDATE_PSD_TAG://????????????
                        startActivity(new Intent(mContext, settingMenuAdapter.getData().get(position).getCls()).putExtra("pageType", 2));
                        break;
                    default:
                        if (settingMenuAdapter.getData().get(position).getCls() != null) {
                            startActivity(new Intent(mContext, settingMenuAdapter.getData().get(position).getCls()));
                        }
                        break;
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }

    @Override
    public void initData() {
        menuBeans.clear();
//        if (UserInfoManager.getAccountStatus() == 1) {
//            menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.set_psd, MyCenterContract.SET_UPDATE_PSD_TAG,
//                    BackPwdActivity.class));
//        }
//        //        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.set_address, MyCenterContract.SET_ADDRESS_TAG,
//        //        MySettingActivity.class));
//        if (UserInfoManager.getAccountStatus() == 1) {
//            menuBeans.add(new MyMenuBean("???????????????", 0, R.mipmap.set_tel, MyCenterContract.SET_UPDATE_TEL_TAG,
//                    BackPwdActivity.class));
//        } else {
//            //???????????????
//            menuBeans.add(new MyMenuBean("???????????????", 0, R.mipmap.set_tel, MyCenterContract.SET_UPDATE_TEL_TAG,
//                    BindingPhoneActivity.class));
//        }
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.set_psd, MyCenterContract.SET_UPDATE_PSD_TAG,
                BackPwdActivity.class));
        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.set_clear, MyCenterContract.SET_CLEAR_TAG, null));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.set_update, MyCenterContract.SET_UPDATE_TAG, null));
//        menuBeans.add(new MyMenuBean("????????????", -1, R.mipmap.set_about, MyCenterContract.SET_ABOUT_TAG,
//                AboutActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.set_wexin, MyCenterContract.SET_WEIXIN_TAG, null));
//        menuBeans.add(new MyMenuBean("??????QQ", 0, R.mipmap.set_qq, MyCenterContract.SET_QQ_TAG, null));
        settingMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        BaseResult baseResult = (BaseResult) o;
        if (baseResult != null) {
            if (baseResult.status == 200) {
                UserBean userBean = MyApp.getUser();
                if (StringTools.isStringValueOk(WeChatId)) {
                    ToastUtils.success(mContext, "??????????????????");
                    userBean.getData().setWeChatName(WeChatName);
                }
                if (StringTools.isStringValueOk(QQId)) {
                    ToastUtils.success(mContext, "QQ????????????");
                    userBean.getData().setQqName(QQName);
                }
                MyApp.setUser(userBean);
            } else {
                ToastUtils.error(mContext, baseResult.message == null ? PubUtil.ERROR_NOTICE : baseResult.message);
            }
        } else {
            ToastUtils.error(mContext, PubUtil.ERROR_NOTICE);
        }
    }

    /**
     * ?????????????????????
     *
     * @param name
     */
    public void bindQQOrWeChat(String name) {
        WeChatName = null;
        QQName = null;
        QQId = null;
        WeChatId = null;

        Platform plat = ShareSDK.getPlatform(name);

        if (!plat.isClientValid()) {
            //?????????????????????????????????????????????true??????????????????false??????
            if (name.equals(QQ.NAME)) {
                ToastUtils.warning(mContext, "?????????QQ");
            } else {
                ToastUtils.warning(mContext, "???????????????");
            }
        }

        plat.removeAccount(true); //???????????????????????????????????????????????????????????????
        plat.SSOSetting(false); //SSO????????????false????????????????????????????????????????????????????????????????????????????????????web??????
        //???????????????????????????????????????????????????????????????????????????????????????????????????UI????????????????????????handler????????????
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //????????????res??????????????????????????????????????????
                if (i == Platform.ACTION_USER_INFOR) {
                    platDB = platform.getDb();//?????????????????????DB
                    //??????DB??????????????????
                    LogUtil.e("id=" + platDB.getUserId());
                    if (platform.getName().equals(QQ.NAME)) {
                        QQName = platDB.getUserName();
                        String params = "access_token=" + platform.getDb().getToken() + "&unionid=1&fmt=json";
                        HttpUtil.sendGet("https://graph.qq.com/oauth2.0/me", params, new HttpUtil.NetCallBack() {
                            @Override
                            public void onSuccess(String str) {
                                if (!TextUtils.isEmpty(str)) {
                                    UnionidBean unionidBean = GsonTools.changeGsonToBean(str, UnionidBean.class);
                                    QQId = unionidBean.getUnionid();
                                    myHandler.sendEmptyMessage(1);
                                }
                            }

                            @Override
                            public void onError(String str) {
                            }
                        });

                    } else {
                        WeChatName = platDB.getUserName();
                        WeChatId = platform.getDb().get("unionid");
                        myHandler.sendEmptyMessage(1);
                    }

                }
            }


            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e(throwable.toString());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        //        ShareSDK.setActivity(this);//????????????????????????9.0
        plat.showUser(null);    //?????????????????????????????????????????????????????????????????????
        //??????????????????3189087725????????????
        //plat.showUser(???3189087725???);
    }

    /**
     * ??????????????????
     */
    public void updateBind() {
        mPresenter.bindQQOrWeChat(MyApp.getAccount(), MyApp.getUserToken(), WeChatId, WeChatName, QQId, QQName,
                EntranceContract.BIND_QQ_OR_WECHAT);
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
