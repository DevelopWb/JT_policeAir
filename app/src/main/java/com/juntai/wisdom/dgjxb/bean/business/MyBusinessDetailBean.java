package com.juntai.wisdom.dgjxb.bean.business;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/22 15:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/22 15:33
 */
public class MyBusinessDetailBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"businessId":50,"name":"参军入伍","realName":"王彬","phoneNumber":"17568086930","picture":[{"imgId":196,"materialsName":"居民户口本","pictureUrl":"/householdBusiness/9d03eebccbc54111ad61f4c8006e08d4.jpeg"},{"imgId":197,"materialsName":"应征公民入伍通知书（含军校录取通知书）","pictureUrl":"/householdBusiness/8d45e7ec4c6b49a080d42f41b8328012.jpeg"}],"idnumber":"371325198702255555"}
     * type : null
     * message : null
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
         * businessId : 50
         * name : 参军入伍
         * realName : 王彬
         * phoneNumber : 17568086930
         * picture : [{"imgId":196,"materialsName":"居民户口本","pictureUrl":"/householdBusiness/9d03eebccbc54111ad61f4c8006e08d4.jpeg"},{"imgId":197,"materialsName":"应征公民入伍通知书（含军校录取通知书）","pictureUrl":"/householdBusiness/8d45e7ec4c6b49a080d42f41b8328012.jpeg"}]
         * idnumber : 371325198702255555
         */

        private int businessId;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        private String name;
        private String realName;
        private String phoneNumber;
        private String idnumber;
        private String auditOpinion;
        private List<PictureBean> picture;

        public String getAuditOpinion() {
            return auditOpinion == null ? "" : auditOpinion;
        }

        public void setAuditOpinion(String auditOpinion) {
            this.auditOpinion = auditOpinion == null ? "" : auditOpinion;
        }

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRealName() {
            return realName == null? "" : realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhoneNumber() {
            return phoneNumber == null? "" : phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getIdnumber() {
            return idnumber == null? "" : idnumber;
        }

        public void setIdnumber(String idnumber) {
            this.idnumber = idnumber;
        }

        public List<PictureBean> getPicture() {
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        public static class PictureBean {
            /**
             * imgId : 196
             * materialsName : 居民户口本
             * pictureUrl : /householdBusiness/9d03eebccbc54111ad61f4c8006e08d4.jpeg
             */

            private int imgId;
            private String materialsName;
            private String pictureUrl;

            public int getImgId() {
                return imgId;
            }

            public void setImgId(int imgId) {
                this.imgId = imgId;
            }

            public String getMaterialsName() {
                return materialsName == null? "" : materialsName;
            }

            public void setMaterialsName(String materialsName) {
                this.materialsName = materialsName;
            }

            public String getPictureUrl() {
                return pictureUrl == null? "" : pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }
        }
    }
}
