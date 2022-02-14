package com.juntai.wisdom.policeAir.utils;

import com.juntai.wisdom.policeAir.bean.UserBean;
import com.orhanobut.hawk.Hawk;

/**
 * @Author: tobato
 * @Description: 作用描述  用户信息管理类
 * @CreateDate: 2020/12/19 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/19 14:04
 */
public class UserInfoManager {
    public static String QQ_ID = null;//qqid
    public static String WECHAT_ID = null;//wechatid
    public static String OTHER_NICK_NAME = null;//第三方昵称

    /**
     * 获取账号的状态  0 代表游客登录 1代表手机号登录 2代表第三方登录（未绑定手机号）
     *
     * @return
     */
    public static int getAccountStatus() {
        int status = -1;
        if (isLogin()) {
            String phoneNum = getUser().getData().getPhoneNumber();
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
     * 获取用户信息
     *
     * @return
     */
    public static UserBean getUser() {
        return Hawk.get(AppUtils.SP_KEY_USER);
    }

    /**
     * 判定用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (getUser() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getPhoneNumber() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getPhoneNumber() : "";
    }
    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getScore() {
        return getUser() != null && getUser().getData() != null ? String.valueOf(getUser().getData().getScore()) : "";
    }


}
