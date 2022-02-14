package com.juntai.wisdom.policeAir.home_page;

import com.juntai.wisdom.basecomponent.mvp.IView;

/**
 * Describe:详情关联类
 * Create by zhangzhenlong
 * 2020-3-19
 * email:954101549@qq.com
 */
public interface InfoDetailContract {
    String GET_INSPECTION_DETAIL = "get_inspection_detail";//巡检详情
    String GET_NEWS_DETAIL = "get_news_detail";// 资讯详情
    String GET_COMMENT_LIST = "get_comment_list";//评论列表
    String GET_COMMENT_CHILD_LIST = "get_comment_child_list";//获取子评论
    String LIKE_TAG = "like_tag";//点赞
    String LIKE_COMMENT_TAG = "like_comment_tag";//评论点赞
    String COLLECT_TAG = "collect_tag";//收藏
    String SHARE_TAG = "share_tag";//分享

    interface IInfoDetailView extends IView {}
    interface IInfoDetailModel{}
    interface IInfoDetailPresent{

        /**
         * 获取巡检
         * @param patrolId 巡检id
         * @param tag
         */
        void getInspection(int patrolId,String tag);

        /**
         * 添加分享
         * @param isType 状态（0：分享）（1：取消分享）必传
         * @param typeId typeId 类型id （取消分享时不传）
         * @param shareId @param shareId 分享时为被分享的主键
         * @param tag
         */
        void addShare(int isType, int typeId, int shareId, String tag);
        /**
         * 获取走访详情
         * @param id 走访id
         * @param tag
         */
        void getInterviewDetail(int id,String tag);
    }
}
