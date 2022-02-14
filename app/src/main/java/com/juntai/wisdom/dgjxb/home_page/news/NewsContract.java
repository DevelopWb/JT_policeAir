package com.juntai.wisdom.dgjxb.home_page.news;

import com.juntai.wisdom.basecomponent.mvp.IPresenter;
import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;
import retrofit2.http.Query;

/**
 * Describe:资讯关联类
 * Create by zhangzhenlong
 * 2020-7-29
 * email:954101549@qq.com
 */
public interface NewsContract {
    String GET_NEWS_LIST = "get_news_list";
    String REPORT_TYPES = "report_types";
    String UPLOAD_NEWS_PHOTO = "upload_news_photo";
    int REQUEST_CODE_CHOOSE = 0x3;
    String YASUO_PHOTO_TAG ="yaSuoPhotoTag";//压缩图片
    String PUBLISH_IMAGE_TEXT_NEWS = "publish_image_text_news";//图文资讯
    String UPDATE_IMAGE_TEXT_NEWS = "update_image_text_news";//修改图文资讯
    String UPDATE_VIDEO_NEWS = "update_video_news";//修改视频资讯
    String PUBLISH_VIDEO_NEWS = "publish_video_news";//视频资讯
    String SEARCH_NEWS_LIST = "search_news_list";//资讯搜索
    String GET_NEWS_INFO = "get_news_info";

    String GET_AUTHOR_INFO = "get_author_info";//获取资讯作者信息
    String GET_AUTHOR_NEWS_LIST = "get_author_news_list";//该作者的资讯列表
    String GET_FANS_LIST = "get_fans_list";//个人粉丝列表

    String DELETE_FOLLOW = "delete_follow";//取消关注
    String ADD_FOLLOW = "add_follow";//添加关注

    interface INewsView extends IView {

    }

    interface INewsPresent extends IPresenter<INewsView> {
        /**
         * 获取资讯列表
         *
         * @param tag
         */
        void getNewsList(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 发布资讯
         *
         * @param tag
         */
        void publishNews(String tag, RequestBody requestBody);
        /**
         * 修改资讯
         *
         * @param tag
         */
        void updateNews(String tag, RequestBody requestBody);
        /**
         * 资讯图片上传
         *
         * @param tag
         */
        void uploadNewsPhoto(String tag, RequestBody requestBody);

        /**
         * 搜索资讯
         * @param keyWord
         * @param pageNum
         * @param pageSize
         * @param tag
         */
        void searchNewsList(String keyWord, int pageNum, int pageSize, String tag);

        /**
         * 获取资讯详情
         * @param newsId
         * @param tag
         */
        void getNewsInfo(int newsId, String tag);

        /**
         * 资讯个人主页资料
         */
        void getAuthorInfo(int authorId, String tag);

        /**
         * 个人主页资讯列表
         * @param authorId
         * @param typeId
         * @param tag
         */
        void getNewsListByAuthorId(int authorId, int typeId, int pageNum, int pageSize, String tag, boolean showProgress);

        /**
         * 获取粉丝列表
         * @param followId
         * @param pageNum
         * @param pageSize
         * @param tag
         * @param showProgress
         */
        void getFansList(int followId, int pageNum, int pageSize, String tag, boolean showProgress);

        /**
         * 获取关注列表
         * @param fansId
         * @param pageNum
         * @param pageSize
         * @param tag
         * @param showProgress
         */
        void getFollowList(int fansId, int pageNum, int pageSize, String tag, boolean showProgress);

        /**
         * 关注或取消关注
         * @param typeId 1:关注；2:取消关注
         * @param followId 目标id
         * @param tag
         */
        void addFollowOrDelete(int typeId, int followId, String tag);
        /**
         * 获取评论
         * @param
         */
        void getCommentList(int commentedId, int pageSize, int currentPage, String tag);
        /**
         * 获取子评论
         * @param
         */
        void getCommentChildList(int commentedId, int unreadId, int pageSize, int currentPage, String tag);
        /**
         * 点赞
         * @param id 重复添加点赞是传likeid
         * @param isType 状态（0：点赞）（1：取消点赞）必传
         * @param typeId 类型id （取消点赞时不传）
         * @param likeId 点赞时为被点赞的主键；取消点赞时为点赞表的主键
         */
        void like(int id, int userId, int isType, int typeId, int likeId,String tag);





    }
}
