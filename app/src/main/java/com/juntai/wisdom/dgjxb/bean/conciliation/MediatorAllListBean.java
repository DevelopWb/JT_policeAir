package com.juntai.wisdom.dgjxb.bean.conciliation;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe: 所有调解员列表
 * Create by zhangzhenlong
 * 2020-7-17
 * email:954101549@qq.com
 */
public class MediatorAllListBean extends BaseResult {
    /**
     * {
     *     "error": null,
     *     "data": [
     *         {
     *             "id": 33,
     *             "name": "田庆松",
     *             "headPortrait": "/net_lawyer_img/3b8cf990e022458f81f688b15b780208.jpeg",
     *             "introduction": "田庆松，男，汉族，1971年生，执业证号：13713200910448534，2009年执业。",
     *             "phone": "18605390578",
     *             "typeId": 1
     *         }
     *     ],
     *     "status": 200,
     *     "message": "成功",
     *     "success": true
     * }
     */
    private List<MediatorBean> data;

    public List<MediatorBean> getData() {
        if (data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<MediatorBean> data) {
        this.data = data;
    }

    public static class MediatorBean implements Serializable {

        /**
         * id : 6
         * name : 田庆松
         * headPortrait : /net_lawyer_img/3b8cf990e022458f81f688b15b780208.jpeg
         * introduction : 田庆松，男，汉族，1971年生，执业证号：13713200910448534，2009年执业。
         * phone : 18605390578
         * typeId : 1
         */

        private int id;
        private String name;//姓名
        private String headPortrait;//头像
        private String introduction;//简介
        private String phone;
        private int typeId;//类别（1：律师；2：人民调解员；3：其他）

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

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getIntroduction() {
            return introduction == null? "" : introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getPhone() {
            return phone == null? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }
}
