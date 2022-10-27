package com.juntai.wisdom.basecomponent.utils.eventbus;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/6 14:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/6 14:10
 */
public class EventBusObject {
    /**
     * 收到未读消息
     */
    public final static String HANDLER_UNREAD_MESSAGE = "HANDLER_UNREAD_MESSAGE";
    public final static String NETWORK_STATUS = "NETWORK_STATUS";
    public final static String VIDEO_CALL = "VIDEO_CALL";
    public final static String FINISH_ACTIVITY = "FINISH_ACTIVITY";


    private String eventKey;

    private Object eventObj;

    public EventBusObject(String eventKey, Object eventObj) {
        this.eventKey = eventKey;
        this.eventObj = eventObj;
    }

    public String getEventKey() {
        return eventKey == null ? "" : eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey == null ? "" : eventKey;
    }

    public Object getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object eventObj) {
        this.eventObj = eventObj;
    }
}
