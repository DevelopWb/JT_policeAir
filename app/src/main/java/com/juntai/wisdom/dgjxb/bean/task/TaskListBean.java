package com.juntai.wisdom.dgjxb.bean.task;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:任务列表
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class TaskListBean extends BaseResult {

    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "datas": [
     *             {
     *                 "id": 1,
     *                 "taskPeopleId": 3,
     *                 "name": "河东人民大街巡逻执法",
     *                 "remark": "对河东人民大街附近进行巡逻查看",
     *                 "state": 0
     *             },
     *             {
     *                 "id": 5,
     *                 "taskPeopleId": 9,
     *                 "name": "测试发布任务1号",
     *                 "remark": "测试发布任务1号描述：立刻马上去现场",
     *                 "state": 0
     *             }
     *         ],
     *         "total": 2,
     *         "listSize": 2,
     *         "pageCount": 1
     *     },
     *     "type": null,
     *     "message": null,
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
        private List<DataBean.TaskBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<DataBean.TaskBean> getDatas() {
            if (datas == null){
                datas = new ArrayList<>();
            }
            return datas;
        }

        public void setDatas(List<DataBean.TaskBean> datas) {
            this.datas = datas;
        }

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

        public static class TaskBean implements Serializable {

            /**
             * id : 1
             * taskPeopleId : 1
             * name : 河东人民大街巡逻执法
             * remark : 对河东人民大街附近进行巡逻查看
             * state : 2
             */

            private int id;//id，获取任务详情用
            private int taskPeopleId;//任务id,发布任务用
            private String name;//任务名称
            private String remark;//任务描述
            private int state;//0:未提交；1:已提交
            private int isRead;//是否已读（0已读；1未读）

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTaskPeopleId() {
                return taskPeopleId;
            }

            public void setTaskPeopleId(int taskPeopleId) {
                this.taskPeopleId = taskPeopleId;
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

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }
        }
    }
}
