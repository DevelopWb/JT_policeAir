package com.juntai.wisdom.dgjxb.bean.message;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * Describe:未读消息数
 * Create by zhangzhenlong
 * 2020-11-2
 * email:954101549@qq.com
 */
public class UnReadCountBean extends BaseResult {

    /**
     * error : null
     * data : {"notificationMessage":0,"commentMessage":0,"messageCount":0,"missionCount":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * notificationMessage : 0
         * commentMessage : 0
         * messageCount : 0
         * missionCount : 0
         */

        private int notificationMessage;//我的消息-通知消息未读数
        private int commentMessage;//我的消息-评论点赞未读数
        private int messageCount;//我的消息未读总数（notificationMessage + commentMessage）
        private int missionCount;//任务未读数
        private int ImCount;//聊天未读消息数

        public int getImCount() {
            return ImCount;
        }

        public void setImCount(int imCount) {
            ImCount = imCount;
        }

        public int getNotificationMessage() {
            return notificationMessage;
        }

        public void setNotificationMessage(int notificationMessage) {
            this.notificationMessage = notificationMessage;
        }

        public int getCommentMessage() {
            return commentMessage;
        }

        public void setCommentMessage(int commentMessage) {
            this.commentMessage = commentMessage;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        public int getMissionCount() {
            return missionCount;
        }

        public void setMissionCount(int missionCount) {
            this.missionCount = missionCount;
        }
    }
}
