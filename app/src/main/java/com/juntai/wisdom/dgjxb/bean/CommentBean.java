package com.juntai.wisdom.dgjxb.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论
 * @aouther ZhangZhenlong
 * @date 2020-4-1
 */
public class CommentBean {

    /**
     * "id": 268,
     *                 "userId": 101,
     *                 "nickname": "rep蔡徐坤",
     *                 "headPortrait": "https://www.juntaikeji.com:17002/head_img/ce7066cfd91b47128d20181fd151d97d.jpeg",
     *                 "content":"�a",
     *                 "gmtCreate": "2020-10-21 16:47:41",
     *                 "likeCount": 0,
     *                 "isLike": 0,
     *                 "commentChildList": [
     */

    private int id;
    private int userId;
    private String nickname;
    private String headPortrait;
    private String content;
    private String gmtCreate;
    private String commentUrl;//评论图片地址
    private List<CommentBean> commentChildList;
    private int likeCount;//评论数
    private int isLike;//0未点赞，其他点赞(为评论id)


    public String getCommentUrl() {
        return commentUrl == null? "" : commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<CommentBean> getCommentChildList() {
        if (commentChildList == null){
            commentChildList = new ArrayList<>();
        }
        return commentChildList;
    }

    public void setCommentChildList(List<CommentBean> commentChildList) {
        this.commentChildList = commentChildList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname == null? "xx":nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait == null? "":headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getContent() {
        return content == null? "":content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGmtCreate() {
        return gmtCreate == null? "" : gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
