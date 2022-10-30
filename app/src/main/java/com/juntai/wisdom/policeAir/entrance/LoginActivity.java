package com.juntai.wisdom.policeAir.entrance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.bean.UnionidBean;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.basecomponent.utils.GsonTools;
import com.juntai.wisdom.basecomponent.utils.HttpUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.PubUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.base.BaseAppActivity;
import com.juntai.wisdom.policeAir.base.MainActivity;
import com.juntai.wisdom.basecomponent.bean.UserBean;
import com.juntai.wisdom.policeAir.entrance.regist.RegistActivity;
import com.juntai.wisdom.basecomponent.utils.AppUtils;
import com.juntai.wisdom.basecomponent.utils.MD5;
import com.juntai.wisdom.basecomponent.utils.UserInfoManager;
import com.orhanobut.hawk.Hawk;
import com.videoaudiocall.net.AppHttpPathSocket;
import com.videoaudiocall.webSocket.MyWsManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  登录
 * @date 2020/3/6 9:12
 */
public class LoginActivity extends BaseAppActivity<EntrancePresent> implements EntranceContract.IEntranceView,
        View.OnClickListener {
    public String otherHeadIcon = "";
    /**
     * 登录
     */
    private TextView mLoginTv;
    /**
     * 账号
     */
    private EditText mAccount;
    /**
     * 密码
     */
    private EditText mPassword;
    String account, password;
    private SwitchCompat mIsShowPwdSc;
    /**
     * 注册新用户
     */
    private TextView mRegistAccountTv;
    /**
     * 找回密码
     */
    private TextView mRebackPwdTv;
    private ImageView mLoginByWchatIv;
    private ImageView mLoginByQqIv;

    private MyHandler myHandler = new MyHandler(this);

    static class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;//弱引用

        MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity theActivity = (LoginActivity) mActivity.get();
            switch (msg.what) {
                //此处可以根据what的值处理多条信息
                case 1:
                    theActivity.otherLogin();
                    break;
            }
        }
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_dg_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //注册成功后，填写手机号
        mAccount.setText(UserInfoManager.getPhoneNumber());
        super.onNewIntent(intent);
    }

    @Override
    public void initData() {
        mLoginTv = (TextView) findViewById(R.id.login_tv);
        mLoginTv.setOnClickListener(this);
        mAccount = (EditText) findViewById(R.id.regist_phone_et);
        mPassword = (EditText) findViewById(R.id.password);
        initLeftBackTv(false);
        setTitleName("登录");
        mIsShowPwdSc.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected EntrancePresent createPresenter() {
        return new EntrancePresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            //登录成功
            case EntranceContract.LOGIN_TAG:
                UserBean loginBean = (UserBean) o;
                if (loginBean != null) {
                    if (loginBean.status == 200) {
                        ToastUtils.success(mContext, "登录成功");
                        MyApp.isReLoadWarn = true;
                        Hawk.put(AppUtils.SP_KEY_USER, loginBean);
                        Hawk.put(AppUtils.SP_KEY_TOKEN, loginBean.getData().getToken());
                        Hawk.put(AppUtils.SP_RONGYUN_TOKEN, loginBean.getData().getrOngYunToken());
                        EventManager.sendStringMsg(ActionConfig.BROAD_LOGIN_AFTER);
                        //ws连接
                        MyWsManager.getInstance().setWsUrl(AppHttpPathSocket.BASE_SOCKET +  UserInfoManager.getUserId());
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                        LogUtil.d("token=" + MyApp.getUserToken());
                    } else if (loginBean.status == 1301) {
                        //未绑定 将第三方信息注册到平台
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("nickname", UserInfoManager.OTHER_NICK_NAME);
                        builder.add("headPortrait", otherHeadIcon);
                        if (UserInfoManager.QQ_ID == null) {
                            builder.add("weChatId", UserInfoManager.WECHAT_ID);
                        } else {
                            builder.add("qqId", UserInfoManager.QQ_ID);
                        }
                        builder.add("source", "1");//（1警小宝；2巡小管；3邻小帮）
                        mPresenter.regist(builder.build(), EntranceContract.OTHER_REGIST);

                        //                        ToastUtils.error(mContext, loginBean.message);
                        //                        startActivity(new Intent(mContext, BindingPhoneActivity.class)
                        //                                .putExtra(BindingPhoneActivity.QQID, QQId)
                        //                                .putExtra(BindingPhoneActivity.QQNAME, QQName)
                        //                                .putExtra(BindingPhoneActivity.WECHATID, WeChatId)
                        //                                .putExtra(BindingPhoneActivity.WECHATNAME, WeChatName));
                    } else {
                        ToastUtils.error(mContext, loginBean.message == null ? PubUtil.ERROR_NOTICE :
                                loginBean.message);
                    }
                } else {
                    ToastUtils.error(mContext, PubUtil.ERROR_NOTICE);
                }
                break;
            case EntranceContract.OTHER_REGIST:
                mPresenter.login(null, null, UserInfoManager.WECHAT_ID, UserInfoManager.QQ_ID,
                        EntranceContract.LOGIN_TAG);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_tv:
                account = mAccount.getText().toString();
                password = mPassword.getText().toString();
                if (account.isEmpty()) {
                    ToastUtils.error(mContext, "账号不可为空");
                    return;
                }
                if (password.isEmpty()) {
                    ToastUtils.error(mContext, "登录密码不能为空");
                    return;
                }
                mPresenter.login(account, MD5.md5(String.format("%s#%s", account, password)), null, null,
                        EntranceContract.LOGIN_TAG);
                break;
            case R.id.regist_account_tv:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.reback_pwd_tv:
                startActivity(new Intent(this, BackPwdActivity.class));
                break;
            case R.id.login_by_wchat_iv:
                loginForQQWeChat(Wechat.NAME);
                break;
            case R.id.login_by_qq_iv:
                loginForQQWeChat(QQ.NAME);
                break;
            //是否显示密码
            case R.id.is_show_pwd_sc:
                if (mIsShowPwdSc.isChecked()) {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //隐藏密码
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                //把光标设置在文字结尾
                mPassword.setSelection(mPassword.getText().length());
                break;
        }
    }


    @Override
    public void initView() {
        mIsShowPwdSc = (SwitchCompat) findViewById(R.id.is_show_pwd_sc);
        mLoginTv = (TextView) findViewById(R.id.login_tv);
        mLoginTv.setOnClickListener(this);
        mRegistAccountTv = (TextView) findViewById(R.id.regist_account_tv);
        mRegistAccountTv.setOnClickListener(this);
        mRebackPwdTv = (TextView) findViewById(R.id.reback_pwd_tv);
        mRebackPwdTv.setOnClickListener(this);
        mLoginByWchatIv = (ImageView) findViewById(R.id.login_by_wchat_iv);
        mLoginByWchatIv.setOnClickListener(this);
        mLoginByQqIv = (ImageView) findViewById(R.id.login_by_qq_iv);
        mLoginByQqIv.setOnClickListener(this);
    }

    PlatformDb platDB;

    /**
     * 第三方数据
     *
     * @param name
     */
    public void loginForQQWeChat(String name) {
        UserInfoManager.QQ_ID = null;
        UserInfoManager.WECHAT_ID = null;
        otherHeadIcon = null;

        Platform plat = ShareSDK.getPlatform(name);
        if (!plat.isClientValid()) {
            //判断是否存在授权凭条的客户端，true是有客户端，false是无
            if (name.equals(QQ.NAME)) {
                ToastUtils.warning(mContext, "未安装QQ");
            } else {
                ToastUtils.warning(mContext, "未安装微信");
            }
        }

        plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false); //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        //        ShareSDK.setActivity(this);//抖音登录适配安卓9.0
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //通过打印res数据看看有哪些数据是你想要的
                if (i == Platform.ACTION_USER_INFOR) {
                    platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    LogUtil.e("id=" + platDB.getUserId());
                    UserInfoManager.OTHER_NICK_NAME = platDB.getUserName();
                    otherHeadIcon = platDB.getUserIcon();
                    if (platform.getName().equals(QQ.NAME)) {
                        String params = "access_token=" + platform.getDb().getToken() + "&unionid=1&fmt=json";
                        HttpUtil.sendGet("https://graph.qq.com/oauth2.0/me", params, new HttpUtil.NetCallBack() {
                            @Override
                            public void onSuccess(String str) {
                                if (!TextUtils.isEmpty(str)) {
                                    UnionidBean unionidBean = GsonTools.changeGsonToBean(str, UnionidBean.class);
                                    UserInfoManager.QQ_ID = unionidBean.getUnionid();
                                    myHandler.sendEmptyMessage(1);
                                }
                            }

                            @Override
                            public void onError(String str) {
                            }
                        });

                    } else {
                        UserInfoManager.WECHAT_ID = platform.getDb().get("unionid");
                        myHandler.sendEmptyMessage(1);
                    }

                }
            }


            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.e(throwable.toString());
                //                plat.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

    /**
     * 第三方登录
     */
    public void otherLogin() {
        mPresenter.login(null, null, UserInfoManager.WECHAT_ID, UserInfoManager.QQ_ID, EntranceContract.LOGIN_TAG);
    }

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
