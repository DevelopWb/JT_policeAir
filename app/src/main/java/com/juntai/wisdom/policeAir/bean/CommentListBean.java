package com.juntai.wisdom.policeAir.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:评论列表
 * Create by zhangzhenlong
 * 2020-4-1
 * email:954101549@qq.com
 */
public class CommentListBean extends BaseResult {
    /**
     *{
     *     "error": null,
     *     "status": 200,
     *     "data": {
     *         "datas": [
     *             {
     *                 "id": 268,
     *                 "userId": 101,
     *                 "nickname": "rep蔡徐坤",
     *                 "headPortrait": "https://www.juntaikeji.com:17002/head_img/ce7066cfd91b47128d20181fd151d97d.jpeg",
     *                 "content":"�a",
     *                 "gmtCreate": "2020-10-21 16:47:41",
     *                 "likeCount": 0,
     *                 "isLike": 0,
     *                 "commentChildList": [
     *                     {
     *                         "id": 269,
     *                         "userId": 96,
     *                         "nickname": "张振隆",
     *                         "headPortrait": "https://www.juntaikeji.com:17002/head_img/c558c8bd582c4514984a5c39f26b87f8.jpeg",
     *                         "content": "i�",
     *                         "gmtCreate": "2020-10-21 16:54:23"
     *                     }
     *                 ]
     *             }
     *         ],
     *         "total": 1,
     *         "listSize": 1,
     *         "pageCount": 1
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
        private List<CommentBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<CommentBean> getDatas() {
            return datas;
        }

        public void setDatas(List<CommentBean> datas) {
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
    }
}
