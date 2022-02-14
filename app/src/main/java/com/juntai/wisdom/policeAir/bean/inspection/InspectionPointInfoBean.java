package com.juntai.wisdom.policeAir.bean.inspection;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述  巡检点详情
 * @CreateDate: 2020/5/9 16:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/9 16:33
 */
public class InspectionPointInfoBean  extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * status : 200
     * data : {"id":26,"imgId":"297,298,299,300","name":"临沂市公安局兰山分局东关街派出所","address":"山东省临沂市兰山区琅琊街","contactsPhone":"617201","typeName":"公司"}
     * type : null
     * message : 成功
     * success : true
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
         * "id": 52,
         *         "name": "临沂一小",
         *         "address": "山东省临沂市兰山区刘家园街",
         *         "contactsPhone": "13969958998",
         *         "latitude": 35.0682860,
         *         "longitude": 118.3595970,
         *         "imgId": "421,422,423,424,425,426,427",
         *         "videoUrl": "http://61.156.157.132:32288/crm_client_video/49de9371f0be420dae66d208c46bd199.mp4"
         */

        private int id;//巡检点id
        private String name;//地点名称
        private String address;//地址
        private String contactsPhone;//负责人联系方式
        private double latitude;//维度
        private double longitude;//经度
        private String imgId;//图片id
        private String videoUrl;//视频地址

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
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

        public String getImgId() {
            return imgId == null? "" : imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address  == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContactsPhone() {
            return contactsPhone  == null? "" : contactsPhone;
        }

        public void setContactsPhone(String contactsPhone) {
            this.contactsPhone = contactsPhone;
        }
    }
}
