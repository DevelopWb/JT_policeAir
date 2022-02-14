package com.juntai.wisdom.dgjxb.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.dgjxb.MyApp;
import com.sunfusheng.marqueeview.IMarqueeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  滚动新闻
 * @CreateDate: 2020/3/24 15:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/24 15:37
 */
public class BannerNewsBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"id":11,"name":"记抗疫一线的90后00后年轻人"}]
     * type : null
     * message : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements IMarqueeItem {
        /**
         * id : 11
         * name : 记抗疫一线的90后00后年轻人
         */

        private int id;
        private String name;
        private int typeId;//1视频，2图文b


        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

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

        @Override
        public CharSequence marqueeMessage() {
            return name == null? "" : name;
        }
    }
}
