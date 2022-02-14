package com.juntai.wisdom.policeAir.base;

import com.juntai.wisdom.basecomponent.mvp.IPresenter;
import com.juntai.wisdom.basecomponent.mvp.IView;

/**
 * Describe: 首页
 * Create by zhangzhenlong
 * 2020-8-8
 * email:954101549@qq.com
 */
public interface MainPageContract {
    String DELETE_NEWS_DRAFTS = "delete_news_drafts";
    String UPLOAD_HISTORY = "upload_history";


    interface IMainPageView extends IView {
    }

    interface IMainPagePresent extends IPresenter<IMainPageView> {
        /**
         * 删除资讯图片
         * @param informationId 资讯id
         * @param tag
         */
        void deleteNewsDrafts(String informationId, String tag);

        /**
         * 轨迹长传
         * @param data
         */
        void uploadHistory(String data, String tag);
    }
}
