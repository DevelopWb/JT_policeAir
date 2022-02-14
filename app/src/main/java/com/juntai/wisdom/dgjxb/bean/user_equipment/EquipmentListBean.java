package com.juntai.wisdom.dgjxb.bean.user_equipment;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe: 我的设备列表
 * Create by zhangzhenlong
 * 2020-11-21
 * email:954101549@qq.com
 */
public class EquipmentListBean extends BaseResult {

    /**
     * error : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() : data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * number : 37130201561187053901
         * address : 中国山东省临沂市河东区九曲街道人民大街中段
         * name : 东关街
         * ezOpen : https://www.juntaikeji.com:17002/thumbnail/cameraImg/2a711bd1bae040bf8f10b90b9966b672.jpeg
         * dvrFlag : 硬盘录像机
         * isOnline : 在线
         * typeName : 其他
         */

        private int id;//id
        private String number;//监控编号
        private String address;//地址
        private String name;//名称
        private String ezOpen;//封面图片
        private String dvrFlag;//硬盘录像机标识（0监控；1DVR）
        private String isOnline;//是否在线（0离线；1在线）
        private String typeName;//类型名称

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEzOpen() {
            return ezOpen;
        }

        public void setEzOpen(String ezOpen) {
            this.ezOpen = ezOpen;
        }

        public String getDvrFlag() {
            return dvrFlag;
        }

        public void setDvrFlag(String dvrFlag) {
            this.dvrFlag = dvrFlag;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
