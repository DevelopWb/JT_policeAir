package com.juntai.wisdom.dgjxb.bean.inspection;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述 巡检记录列表
 * @CreateDate: 2020/5/10 13:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/10 13:35
 */
public class InspectionRecordBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"datas":[{"patrolId":108,"userId":100,"realName":"韩国杰","headPortrait":"/head_img/79abaa9f62c944618c2774e3876e63f3.jpeg","patrolTime":"2020-04-23 14:34:12"},{"patrolId":110,"userId":101,"realName":"铁人王进喜","headPortrait":"/head_img/37bf47a24ae44bd9ab641a6983119ed0.jpeg","patrolTime":"2020-04-23 17:03:48"},{"patrolId":111,"userId":95,"realName":"小虎","headPortrait":"/head_img/d1fc9db2f2b34bb48eba3ee804268cb8.jpeg","patrolTime":"2020-04-24 13:55:48"},{"patrolId":114,"userId":95,"realName":"小虎","headPortrait":"/head_img/d1fc9db2f2b34bb48eba3ee804268cb8.jpeg","patrolTime":"2020-04-27 15:17:50"}],"total":8,"listSize":8,"pageCount":1}
     * type : null
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
         * datas : [{"patrolId":108,"userId":100,"realName":"韩国杰","headPortrait":"/head_img/79abaa9f62c944618c2774e3876e63f3.jpeg","patrolTime":"2020-04-23 14:34:12"},{"patrolId":110,"userId":101,"realName":"铁人王进喜","headPortrait":"/head_img/37bf47a24ae44bd9ab641a6983119ed0.jpeg","patrolTime":"2020-04-23 17:03:48"},{"patrolId":111,"userId":95,"realName":"小虎","headPortrait":"/head_img/d1fc9db2f2b34bb48eba3ee804268cb8.jpeg","patrolTime":"2020-04-24 13:55:48"},{"patrolId":114,"userId":95,"realName":"小虎","headPortrait":"/head_img/d1fc9db2f2b34bb48eba3ee804268cb8.jpeg","patrolTime":"2020-04-27 15:17:50"}]
         * total : 8
         * listSize : 8
         * pageCount : 1
         */

        private int total;
        private int listSize;
        private int pageCount;
        private List<DatasBean> datas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * "patrolId": 179,
             *                 "userId": 13,
             *                 "name": "临沂一小",
             *                 "nickname": "汤小芹",
             *                 "headPortrait": "https://www.juntaikeji.com:17002/head_img/4c57e1a4c7414baa8d0330fb13cf5e2a.jpeg",
             *                 "patrolTime": "2020-05-23 14:40:20"
             */


            private Integer patrolId;//巡检任务id
            private Integer userId;//用户id
            private String name;//巡检点名称
            private String nickname;//用户昵称
            private String headPortrait;//头像
            private String patrolTime;//时间

            public void setPatrolId(Integer patrolId) {
                this.patrolId = patrolId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getName() {
                return name == null? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickname() {
                return nickname == null? "" : nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getPatrolId() {
                return patrolId;
            }

            public void setPatrolId(int patrolId) {
                this.patrolId = patrolId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }


            public String getHeadPortrait() {
                return headPortrait  == null? "" : headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public String getPatrolTime() {
                return patrolTime  == null? "" : patrolTime;
            }

            public void setPatrolTime(String patrolTime) {
                this.patrolTime = patrolTime;
            }
        }
    }
}
