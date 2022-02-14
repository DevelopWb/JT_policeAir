package com.juntai.wisdom.dgjxb.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:我的积分
 * Create by zhangzhenlong
 * 2020-3-26
 * email:954101549@qq.com
 */
public class UserScoreListBean extends BaseResult {
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
     *                 "type": 1,
     *                 "memo": "上传巡检奖励积分",
     *                 "score": 5,
     *                 "gmtCreate": "2020-03-23 11:03:02"
     *             }
     *         ],
     *         "total": 4,
     *         "listSize": 4,
     *         "pageCount": 1
     *     },
     *     "type": null,
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
        private List<ScoreBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<ScoreBean> getDatas() {
            if (datas == null){
                datas = new ArrayList<>();
            }
            return datas;
        }

        public void setDatas(List<ScoreBean> datas) {
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

        public static class ScoreBean implements Serializable {

            /**
             * {
             *                 "type": 1,
             *                 "memo": "上传巡检奖励积分",
             *                 "score": 5,
             *                 "gmtCreate": "2020-03-23 11:03:02"
             *             }
             */

            private int type;//用途（1获取；2使用）
            private String memo;//用途说明
            private int score;//积分
            private String gmtCreate;//时间
            private int totalScore;//总积分

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getMemo() {
                return memo == null? "" : memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "" : gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public int getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(int totalScore) {
                this.totalScore = totalScore;
            }
        }
    }

}
