package com.juntai.wisdom.policeAir.entrance;

import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述  APP入口
 * @CreateDate: 2020/3/5 15:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/5 15:53
 */
public interface EntranceContract {
    String BIND_QQ_OR_WECHAT = "bindQQOrWeChat";
    String BIND_PHONE = "bindphone";
    String LOGIN_TAG = "login";//登录的标识
    String OTHER_REGIST = "other_regist";//第三方注册

    interface IEntranceView extends IView {
    }

    interface IEntrancePresent {
        void login(String account, String password, String weChatId, String qqId, String tag);

        void bindQQOrWeChat(String account, String token,String weChatId, String weChatName, String qqId, String qqName,
                            String tag);

        void bindPhoneNum(RequestBody requestBody, String tag);


    }
}
