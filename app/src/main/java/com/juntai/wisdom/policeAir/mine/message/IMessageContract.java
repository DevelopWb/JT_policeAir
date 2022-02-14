package com.juntai.wisdom.policeAir.mine.message;

import com.juntai.wisdom.basecomponent.mvp.IView;

/**
 * Describe:我的消息关联类
 * Create by zhangzhenlong
 * 2020-3-13
 * email:954101549@qq.com
 */
public interface IMessageContract {
    String GET_MESSAGE_LIST = "getMessageList";
    String ALL_READ = "allRead";
    String GET_INFORMATION_DETAIL = "getInformationDetail";

    String DELETE_SYSTEM_MESSAGE = "delete_system_message";

    interface IMessageModel{

    }
    interface IMessageView extends IView {

    }
    interface IMessagePresent{
//        /**
//         * 获取通知消息列表
//         * @param type 类型id（1:通知消息）（2:互动消息）（3:物流售后）（4:评论和赞）
//         * @param pageNum
//         * @param pageSize
//         * @param tag
//         */
//        void getInformMsgList(int type, int pageNum, int pageSize, String tag);
        /**
         * 获取评论消息列表
         * @param type 类型id（1:通知消息）（2:互动消息）（3:物流售后）（4:评论和赞）
         * @param pageNum
         * @param pageSize
         * @param tag
         */
        void getLikeMsgList(int type, int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 消息全部已读
         * @param type 类型id（1:通知消息）（2:互动消息）（3:物流售后）（4:评论和赞）
         * @param tag
         */
        void allReadMsg(int type, int messageId, String tag);
        /**
         * 获取通知消息详情
         * @param id
         */
        void getInformMsgDetail(int id, String tag);

        /**
         * 删除通知消息
         * @param logId
         */
        void deleteSystemMsg(int logId, String tag);
    }
}
