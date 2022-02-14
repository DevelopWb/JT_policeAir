package com.juntai.wisdom.policeAir.home_page.business;

import com.juntai.wisdom.basecomponent.mvp.IPresenter;
import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/3/12 16:00
 */
public interface BusinessContract {
    String CREAT_BUSINESS = "creat_business";//创建业务


    interface IBusinessView extends IView {

    }

    interface IBusinessPresent extends IPresenter<IBusinessView> {
        void businessList(String account,String token, String keyWord, int pageSize, int currentPage, String tag);

        void businessDataNeeded(int declareId, String tag);

        void creatBusiness(RequestBody requestBody, String tag);

        /**
         * 业务详情
         * @param requestBody
         * @param tag
         */
        void businessDetail(RequestBody requestBody, String tag);

        void businessProgress(RequestBody requestBody, String tag);
    }

}
