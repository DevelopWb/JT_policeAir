package com.juntai.wisdom.dgjxb.home_page.equipment;

import com.juntai.wisdom.basecomponent.mvp.IPresenter;
import com.juntai.wisdom.basecomponent.mvp.IView;

/**
 * Describe: 设备接口类
 * Create by zhangzhenlong
 * 2020-11-21
 * email:954101549@qq.com
 */
public interface IEquipmentContract {
    String GET_MY_EQUIPMENT_LIST = "get_my_equipment_list";//获取我的设备列表

    interface IEquipmentView extends IView {

    }

    interface IEquipmentPresent extends IPresenter<IEquipmentView> {
        /**
         * 获取我的设备列表
         * @param pageNum
         * @param pageSize
         * @param tag
         * @param showProgress
         */
        void getMyEquipmentList(int pageNum, int pageSize, String tag, boolean showProgress);
    }
}
