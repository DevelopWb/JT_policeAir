package cn.rongcloud.rtc.initRoom;

import android.content.Context;

import cn.rongcloud.rtc.message.RoomInfoMessage;
import cn.rongcloud.rtc.message.RoomKickOffMessage;
import cn.rongcloud.rtc.message.WhiteBoardInfoMessage;
import cn.rongcloud.rtc.util.SessionManager;
import cn.rongcloud.rtc.util.Utils;
import cn.rongcloud.rtc.utils.FileLogUtil;
import io.rong.common.FileUtils;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-5-29
 * email:954101549@qq.com
 */
public class IMRoom_Init {
    private static Context mContext;
    public static void init(Context app){
        mContext = app;

        Utils.init(mContext);
        SessionManager.initContext(mContext);
        if (mContext.getApplicationInfo().packageName.equals(Utils.getCurProcessName(mContext))) {
            String logPath = FileUtils.getCachePath(mContext, "/ronglog");
            String filePath = logPath + "/rcvoip.log";
            FileLogUtil.setFileLog(filePath);
        }

//        if (getApplicationContext().getApplicationInfo().getid)

        try {
            RongIMClient.registerMessageType(RoomInfoMessage.class);
            RongIMClient.registerMessageType(WhiteBoardInfoMessage.class);
            RongIMClient.registerMessageType(RoomKickOffMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
    }
}
