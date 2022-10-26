package com.example.appbase.util;

import com.juntai.disabled.basecomponent.bean.ContactBean;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tobato
 * @Description: 作用描述  用户信息管理类
 * @CreateDate: 2020/12/19 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/19 14:04
 */
public class UserInfoManager {

    public static String WECHAT_ID = null;//wechatid
    public static String OTHER_NICK_NAME = null;//第三方昵称

    // : 2022/7/25 这个地方需要更改逻辑

    public static String getDevType() {
        int accoutType = getAccoutType();
        if (accoutType == 1) {
            return "app_buy";
        } else if (accoutType == 2) {
            return "app_seller";
        }
        return "";

    }


    /**
     * 获取账号的状态  0 代表游客登录 1代表手机号登录 2代表第三方登录（未绑定手机号）
     *
     * @return
     */
    public static int getAccountStatus() {
        int status = -1;
        if (isLogin()) {
            String phoneNum = getUser().getPhoneNumber();
            if ("未绑定".equals(phoneNum)) {
                status = 2;
            } else {
                status = 1;
            }
        } else {
            status = 0;
        }
        return status;
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static int getShopId() {
        return getUser() != null ? getUser().getShopId() : 0;
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static String getShopName() {
        if (Hawk.contains(HawkProperty.SHOP_NAME)) {
            return Hawk.get(HawkProperty.SHOP_NAME);
        }
        return "";
    }

    /**
     * 获取所有联系人信息
     *
     * @return
     */
    public static Map<Integer, ContactBean> getAllContacts() {
        Map<Integer, ContactBean> contactBeans = Hawk.get(HawkProperty.All_CONTACT);
        if (contactBeans == null) {
            return new HashMap<>();
        }
        return contactBeans;
    }

    /**
     * 添加联系人
     *
     * @param contactBean
     */
    public static void initContacts(ContactBean contactBean) {
        Map<Integer, ContactBean> contactBeans = getAllContacts();

        if (contactBean != null) {
            if (!contactBeans.containsKey(contactBean.getUserId())) {
                contactBeans.put(contactBean.getUserId(), contactBean);
            }
        }
        Hawk.put(HawkProperty.All_CONTACT, contactBeans);

    }


    /**
     * 退出登录清理缓存配置
     */
    public static void clearUserData() {
        Hawk.delete(HawkProperty.SP_KEY_USER);
        Hawk.delete(HawkProperty.SP_KEY_TOKEN);
        Hawk.delete(HawkProperty.SP_KEY_PWD);
//        Hawk.delete(HawkPropertyChat.SP_KEY_UNREAD_COUNT);

//        AliPushManager.getInstance().unbindAccount(UserInfoManager.getUserUUID());

    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static ContactBean getUser() {
        ContactBean userBean = Hawk.get(HawkProperty.SP_KEY_USER);
        return userBean;
    }

    /**
     * 判定用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return Hawk.contains(HawkProperty.SP_KEY_USER) && Hawk.contains(HawkProperty.SP_KEY_TOKEN)&& Hawk.contains(HawkProperty.SP_KEY_PWD);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getPhoneNumber() {
        return getUser() != null && getUser() != null ? getUser().getPhoneNumber() : "";
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getAccount() {
        return getUser() != null && getUser() != null ? getUser().getAccount() : "";
    }
    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getSchoolNumber() {
        return getUser() != null && getUser() != null ? getUser().getSchoolNumber() : "";
    }

    public static boolean canUsePubAccount() {
        return (getUser() != null && getUser() != null) && getUser().getType() == 1;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getSchoolName() {
        return getUser() != null && getUser() != null ? getUser().getSchoolName() : "";
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static int getSchoolId() {
        return getUser() != null && getUser() != null ? getUser().getSchoolId() : 0;
    }


    public static String getHeadPic() {
        return getUser() != null && getUser() != null ? getUser().getHeadPortrait() : "";
    }

    /**
     * 获取账户
     *
     * @return
     */
    public static String getUserNickName() {
        return getUser() != null && getUser() != null ? getUser().getNickname() : "";
    }


    /**
     * 获取usertoken
     *
     * @return
     */
    public static String getUserToken() {
        if (Hawk.contains(HawkProperty.SP_KEY_TOKEN)) {
            return Hawk.get(HawkProperty.SP_KEY_TOKEN);
        }
        return "";
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static int getUserId() {
        return getUser() != null && getUser() != null ? getUser().getUserId() : -1;
    }

    /**
     * getAccoutType
     * type;//账号类型（1学校人员；2商户人员；3农发人员）
     *
     * @return
     */
    public static int getAccoutType() {
        return getUser() != null && getUser() != null ? getUser().getType() : 1;
    }


}
