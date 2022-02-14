package com.juntai.wisdom.policeAir.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * author:wong
 * Date: 2019/3/19
 * Description:
 */
public class MapMenuButton extends BaseResult {

    /**
     {
     "error": null,
     "status": 200,
     "data": [
     {
     "id": 1,
     "uncheckedUrl": "https://www.juntaikeji.com:17002/appMapButton/地图.png",
     "checkedUrl": "https://www.juntaikeji.com:17002/select/appMapButton/地图.png"
     },
     {
     "id": 2,
     "uncheckedUrl": "https://www.juntaikeji.com:17002/appMapButton/天气.png",
     "checkedUrl": "https://www.juntaikeji.com:17002/select/appMapButton/天气.png"
     },
     {
     "id": 3,
     "uncheckedUrl": "https://www.juntaikeji.com:17002/appMapButton/监控.png",
     "checkedUrl": "https://www.juntaikeji.com:17002/select/appMapButton/监控.png"
     },
     {
     "id": 4,
     "uncheckedUrl": "https://www.juntaikeji.com:17002/appMapButton/案件.png",
     "checkedUrl": "https://www.juntaikeji.com:17002/select/appMapButton/案件.png"
     }
     ],
     "message": "成功",
     "success": true
     }
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * "id": 1,
         * "pictureUrl": "/appMapButton/地图.png"
         */

        private boolean isSelected = false;
        private int id;//地图按钮id
        private String uncheckedUrl;//未选中图片
        private String checkedUrl;//选中的图片

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getUncheckedUrl() {
            return uncheckedUrl == null? "" : uncheckedUrl;
        }

        public void setUncheckedUrl(String uncheckedUrl) {
            this.uncheckedUrl = uncheckedUrl;
        }

        public String getCheckedUrl() {
            return checkedUrl == null? "" : checkedUrl;
        }

        public void setCheckedUrl(String checkedUrl) {
            this.checkedUrl = checkedUrl;
        }
    }
}
