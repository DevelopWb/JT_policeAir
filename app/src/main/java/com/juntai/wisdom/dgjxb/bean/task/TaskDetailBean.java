package com.juntai.wisdom.dgjxb.bean.task;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:任务详情
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class TaskDetailBean extends BaseResult {

    /**
     * {
     *     "error": null,
     *     "status": 200,
     *     "data": {
     *         "id": 53,
     *         "taskPeopleId": 280,
     *         "name": "暑期高考生防诈骗宣传",
     *         "remark": "高考结束后，有些家长“熬不住”，骗子会利用这种焦躁情绪作案，通过伪基站群发短信，称可查询高考成绩，并附有一个网址链接。不少考生家长收到此类信息后，便迫不及待点开网址链接，手机就会被偷偷植入盗取网银的木马软件。还有的骗子会直接开价，要求家长支付500元至1000元提前查询分数。考生和家长查分时应认准教育部门指定的查询方式及查分网址，不要点击手机短信来历不明的链接。如果不慎点击，要立即卸载手机上的支付宝等快速支付工具，确保资金安全，并在备份手机通讯录等重要信息后，将手机重置或恢复出厂设置，然后向专业技术人员求助清除病毒。",
     *         "flag": 1,
     *         "pictureOneUrl": "https://www.juntaikeji.com:17002/thumbnail/taskImg/cea37bf297cf4bf4bf647fc36ea182eb.jpeg",
     *         "pictureTwoUrl": null,
     *         "pictureThreeUrl": null,
     *         "videoUrl": null,
     *         "missionList": [
     *             {
     *                 "id": 18,
     *                 "create": "张振隆",
     *                 "gmtCreate": "2020-07-27 09:07:45",
     *                 "pictureOneUrl": "https://www.juntaikeji.com:17002/thumbnail/MissionImg/fec9b143e2424decbb81ee5b97ec392e.jpeg",
     *                 "state": 1
     *             }
     *         ]
     *     },
     *     "message": "成功",
     *     "success": true
     * }
     */

    private DataBean data;

    public DataBean getData() {
        if (data == null){
            data = new DataBean();
        }
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        private int id;
        private int taskPeopleId;//任务id,发布任务用
        private String name;
        private String remark;
        private int flag;//APP上报任务是否允许调起手机相册标记（0是；1否）
        private String pictureOneUrl;//图片
        private String pictureTwoUrl;//图片
        private String pictureThreeUrl;//图片
        private String videoUrl;//视频
        private List<ReportBean> missionList;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getPictureOneUrl() {
            return pictureOneUrl == null? "" : pictureOneUrl;
        }

        public void setPictureOneUrl(String pictureOneUrl) {
            this.pictureOneUrl = pictureOneUrl;
        }

        public String getPictureTwoUrl() {
            return pictureTwoUrl == null? "" : pictureTwoUrl;
        }

        public void setPictureTwoUrl(String pictureTwoUrl) {
            this.pictureTwoUrl = pictureTwoUrl;
        }

        public String getPictureThreeUrl() {
            return pictureThreeUrl == null? "" : pictureThreeUrl;
        }

        public void setPictureThreeUrl(String pictureThreeUrl) {
            this.pictureThreeUrl = pictureThreeUrl;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark == null? "" : remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getTaskPeopleId() {
            return taskPeopleId;
        }

        public void setTaskPeopleId(int taskPeopleId) {
            this.taskPeopleId = taskPeopleId;
        }

        public List<ReportBean> getMissionList() {
            if (missionList == null){
                missionList = new ArrayList<>();
            }
            return missionList;
        }

        public void setMissionList(List<ReportBean> missionList) {
            this.missionList = missionList;
        }

        public static class ReportBean implements Serializable{

            /**
             * id : 1
             * create : 测试人员1
             * gmtCreate : 2020-05-12 15:43:48
             * pictureOneUrl : "https://www.juntaikeji.com:17002/thumbnail/MissionImg/fec9b143e2424decbb81ee5b97ec392e.jpeg"
             * state : 2
             */

            private int id;
            private String create;
            private String gmtCreate;
            private String pictureOneUrl;
            private int state;//（0：审核中；1：审核成功；2：审核失败）

            public ReportBean(){}

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreate() {
                return create == null? "" : create;
            }

            public void setCreate(String create) {
                this.create = create;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "" : gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getPictureOneUrl() {
                return pictureOneUrl == null? "" : pictureOneUrl;
            }

            public void setPictureOneUrl(String pictureOneUrl) {
                this.pictureOneUrl = pictureOneUrl;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}
