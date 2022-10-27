package com.juntai.wisdom.policeAir.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class FlyOperatorsBean extends BaseResult {


    /**
     * error : null
     * data : [{"id":1,"name":"曹守琪","account":"15610678315","latitude":"35.0905720","longitude":"118.4020750"},{"id":3,"name":"王彬","account":"17568086930","latitude":"35.0913590","longitude":"118.4008240"},{"id":5,"name":"高中军","account":"15905391897","latitude":"35.0752350","longitude":"118.3545310"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 曹守琪
         * account : 15610678315
         * latitude : 35.0905720
         * longitude : 118.4020750
         */

        private int id;
        private String name;
        private String account;
        private String latitude;
        private String longitude;
        private String img;

        public int getId() {
            return id;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img == null ? "" : img;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
