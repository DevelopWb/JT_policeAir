package com.juntai.wisdom.policeAir.home_page.news.news_info;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseDownLoadActivity;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseMoreBottomDialog;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.BaseDataBean;
import com.juntai.wisdom.policeAir.bean.CommentListBean;
import com.juntai.wisdom.policeAir.bean.news.NewsDetailBean;
import com.juntai.wisdom.policeAir.home_page.InfoDetailContract;
import com.juntai.wisdom.policeAir.home_page.InfoDetailPresent;
import com.juntai.wisdom.policeAir.home_page.news.NewsContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.home_page.news.news_comment.CommentAdapter;
import com.juntai.wisdom.policeAir.home_page.news.news_comment.CommentChildActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_comment.EditCommentDialog;
import com.juntai.wisdom.policeAir.home_page.news.news_info.report.ReportActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_personal_homepage.PersonalHomepageActivity;
import com.juntai.wisdom.policeAir.mine.mycollect.MyCollectPresent;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.policeAir.utils.ToolShare;
import com.juntai.wisdom.policeAir.utils.UserInfoManager;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Describe:资讯详情展示基类
 * Create by zhangzhenlong
 * 2020-8-13
 * email:954101549@qq.com
 */
public abstract class BaseNewsInfoActivity extends BaseDownLoadActivity<NewsPresent> implements NewsContract.INewsView,
        View.OnClickListener {
    protected ImageView mInfoUserImage;
    /**
     * 姓名
     */
    protected TextView mInfoUserName;
    /**
     * 218次播放\u30002019年5月19日发布
     */
    protected TextView mInfoTimeRead;
    /**
     * info_comment_recycleview
     * 关注
     */
    protected TextView mInfoGuanzhuBtn;
    protected RecyclerView mInfoCommentRecycleview;
    protected SmartRefreshLayout mInfoSmartrefreshlayout;
    /**
     * 写评论···
     */
    protected TextView mCommentEdittext;
    protected ImageView mInfoCommentIv;
    protected ImageView mInfoCollectIv;
    protected ImageView mInfoLikeIv;
    protected ImageView mInfoShareIv;
    /**
     * 0
     */
    protected TextView mInfoCommentCount;
    /**
     * 6
     */
    protected TextView mInfoLikeCount;
    protected int newsId;//资讯id

    protected NewsDetailBean.DataBean newsDetailBean;
    //是否收藏，收藏操作是否执行中，是否点赞，点赞操作是否执行中,详情未加载出来不可以点赞收藏
    protected boolean isCollect = false, isCollectIng = true, isLike = false, isLikeIng = true;
    protected int likeId, collectId;//点赞收藏id
    protected EditCommentDialog editCommentDialog;
    protected String shareContent = "";
    protected int page = 1, pagesize = 10;
    protected int commentPositon;//点赞评论位置

    CommentAdapter commentAdapter;
    MyCollectPresent myCollectPresent;
    InfoDetailPresent infoDetailPresent;

    private BaseMoreBottomDialog moreBottomDialog;
    private BaseMoreBottomDialog.OnItemClick onItemClick;

    @Override
    public void initView() {
        //        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        newsId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        mInfoUserImage = (ImageView) findViewById(R.id.info_user_image);
        mInfoUserImage.setOnClickListener(this);
        mInfoUserName = (TextView) findViewById(R.id.info_user_name);
        mInfoTimeRead = (TextView) findViewById(R.id.info_time_read);
        mInfoGuanzhuBtn = (TextView) findViewById(R.id.info_guanzhu_btn);
        mInfoGuanzhuBtn.setOnClickListener(this);
        mInfoCommentRecycleview = (RecyclerView) findViewById(R.id.info_comment_recycleview);
        mInfoSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.info_smartrefreshlayout);
        mCommentEdittext = (TextView) findViewById(R.id.comment_edittext);
        mCommentEdittext.setOnClickListener(this);
        mInfoCommentIv = (ImageView) findViewById(R.id.info_comment_iv);
        mInfoCommentIv.setOnClickListener(this);
        mInfoCollectIv = (ImageView) findViewById(R.id.info_collect_iv);
        mInfoCollectIv.setOnClickListener(this);
        mInfoLikeIv = (ImageView) findViewById(R.id.info_like_iv);
        mInfoLikeIv.setOnClickListener(this);
        mInfoShareIv = (ImageView) findViewById(R.id.info_share_iv);
        mInfoShareIv.setOnClickListener(this);
        mInfoCommentCount = (TextView) findViewById(R.id.info_comment_count);
        mInfoCommentCount.setVisibility(View.GONE);
        mInfoLikeCount = (TextView) findViewById(R.id.info_like_count);

        infoDetailPresent = new InfoDetailPresent();
        infoDetailPresent.setCallBack(this);
        myCollectPresent = new MyCollectPresent();
        myCollectPresent.setCallBack(this);
        editCommentDialog = new EditCommentDialog();

        //评论
        mInfoCommentRecycleview = (RecyclerView) findViewById(R.id.info_comment_recycleview);
        mInfoCommentRecycleview.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(R.layout.item_comment_news, new ArrayList());
        commentAdapter.setEmptyView(getAdapterEmptyView("还没有评论呢", -1));
        mInfoCommentRecycleview.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_comment_iv){
                    //查看大图
                    ArrayList<String> images = new ArrayList<>();
                    images.add(commentAdapter.getItem(position).getCommentUrl());
                    mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
                            .putExtra("paths", images));
                }else if (view.getId() == R.id.child1_iv){
                    ArrayList<String> images = new ArrayList<>();
                    images.add(commentAdapter.getItem(position).getCommentChildList().get(0).getCommentUrl());
                    mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
                            .putExtra("paths", images));
                }else if (view.getId() == R.id.child2_iv){
                    ArrayList<String> images = new ArrayList<>();
                    images.add(commentAdapter.getItem(position).getCommentChildList().get(1).getCommentUrl());
                    mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
                            .putExtra("paths", images));
                }
                if (!MyApp.isLogin()) {
                    MyApp.goLogin();
                    return;
                }
                switch (view.getId()){
                    case R.id.item_comment_huifu:
                        //回复
                        editCommentDialog.show(getSupportFragmentManager(),
                                "commentDialog",
                                8,
                                newsId,
                                commentAdapter.getItem(position).getId(),
                                commentAdapter.getItem(position).getUserId(),
                                refreshListener, BaseNewsInfoActivity.this);
                        break;
                    case R.id.item_comment_like:
                        //评论点赞
                        commentPositon = position;
                        if (commentAdapter.getItem(position).getIsLike() > 0) {//取消
                            mPresenter.like(-1, -1, 1, 0, commentAdapter.getItem(position).getIsLike(),
                                    InfoDetailContract.LIKE_COMMENT_TAG);
                        } else {
                            mPresenter.like(commentAdapter.getItem(position).getIsLike(), MyApp.getUid(), 0, 2,
                                    commentAdapter.getItem(position).getId(), InfoDetailContract.LIKE_COMMENT_TAG);
                        }
                        break;
                    case R.id.item_comment_user_more:
                        //更多--子评论
                        startActivity(new Intent(mContext, CommentChildActivity.class)
                                .putExtra("id", commentAdapter.getItem(position).getId())
                                .putExtra("type", 8));
                        break;
                    case R.id.item_comment_user_image:
                        startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY,
                                commentAdapter.getItem(position).getUserId()));
                        break;
                    case R.id.child1_name:
                        startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY,
                                commentAdapter.getItem(position).getCommentChildList().get(0).getUserId()));
                        break;
                    case R.id.child2_name:
                        startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY,
                                commentAdapter.getItem(position).getCommentChildList().get(1).getUserId()));
                        break;
                }
            }
        });
        //刷新
        mInfoSmartrefreshlayout.setEnableRefresh(false);
        //加载更多
        mInfoSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.getCommentList(newsId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
            }
        });
    }

    /**
     * 评论后刷新
     */
    EditCommentDialog.RefreshListener refreshListener = new EditCommentDialog.RefreshListener() {
        @Override
        public void refresh() {
            page = 1;
            newsDetailBean.setCommentCount(newsDetailBean.getCommentCount() + 1);
            mInfoCommentCount.setText(String.valueOf(newsDetailBean.getCommentCount()));
            mInfoCommentCount.setVisibility(View.VISIBLE);
            mPresenter.getCommentList(newsId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
        }
    };

    @Override
    public void initData() {
        mPresenter.getCommentList(newsId, pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
    }

    /**
     * 根据数据内容初始化view
     */
    public void viewSetData() {
        isCollectIng = false;
        isLikeIng = false;
        likeId = newsDetailBean.getIsLike();
        collectId = newsDetailBean.getIsCollect();

        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), newsDetailBean.getHeadPortrait(),
                R.mipmap.my_hint_head, R.mipmap.my_hint_head, mInfoUserImage);
        mInfoUserName.setVisibility(View.VISIBLE);
        mInfoUserName.setText(newsDetailBean.getNickname());

        //是否点赞收藏
        isCollect = newsDetailBean.getIsCollect() != 0;
        isLike = newsDetailBean.getIsLike() != 0;
        mInfoCollectIv.setSelected(isCollect);
        mInfoLikeIv.setSelected(isLike);
        if (newsDetailBean.getCommentCount() == 0) {
            mInfoCommentCount.setVisibility(View.GONE);
        } else {
            mInfoCommentCount.setVisibility(View.VISIBLE);
            mInfoCommentCount.setText(String.valueOf(newsDetailBean.getCommentCount()));
        }

        if (newsDetailBean.getLikeCount() == 0) {
            mInfoLikeCount.setVisibility(View.GONE);
        } else {
            mInfoLikeCount.setVisibility(View.VISIBLE);
            mInfoLikeCount.setText(String.valueOf(newsDetailBean.getLikeCount()));
        }


        if (newsDetailBean.getUserId() == MyApp.getUid()) {
            mInfoGuanzhuBtn.setVisibility(View.INVISIBLE);
        } else {
            mInfoGuanzhuBtn.setVisibility(View.VISIBLE);
            if (newsDetailBean.getIsFollow() > 0) {
                mInfoGuanzhuBtn.setText("已关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
            } else {
                mInfoGuanzhuBtn.setText("关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.white));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_blue);
            }
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case InfoDetailContract.GET_COMMENT_LIST:
                CommentListBean commentListBean = (CommentListBean) o;
                if (page == 1) {
                    commentAdapter.getData().clear();
                }
                if (commentListBean.getData().getDatas().size() < pagesize) {
                    mInfoSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                } else {
                    mInfoSmartrefreshlayout.setNoMoreData(false);
                }
                mInfoSmartrefreshlayout.finishRefresh();
                mInfoSmartrefreshlayout.finishLoadMore();
                commentAdapter.addData(commentListBean.getData().getDatas());
                break;
            case InfoDetailContract.GET_COMMENT_CHILD_LIST:
                break;
            case InfoDetailContract.LIKE_TAG:
                newsDetailBean.setLikeCount(isLike ? newsDetailBean.getLikeCount() - 1 :
                        newsDetailBean.getLikeCount() + 1);
                if (newsDetailBean.getLikeCount() == 0) {
                    mInfoLikeCount.setVisibility(View.GONE);
                } else {
                    mInfoLikeCount.setVisibility(View.VISIBLE);
                    mInfoLikeCount.setText(String.valueOf(newsDetailBean.getLikeCount()));
                }
                isLike = !isLike;
                likeId = ((BaseDataBean) o).getData();
                break;
            case InfoDetailContract.LIKE_COMMENT_TAG:
                if (commentAdapter.getData().get(commentPositon).getIsLike() > 0) {//取消
                    commentAdapter.getData().get(commentPositon).setIsLike(0);
                    commentAdapter.getData().get(commentPositon).setLikeCount(commentAdapter.getData().get(commentPositon).getLikeCount() - 1);
                } else {
                    commentAdapter.getData().get(commentPositon).setIsLike(((BaseDataBean) o).getData());
                    commentAdapter.getData().get(commentPositon).setLikeCount(commentAdapter.getData().get(commentPositon).getLikeCount() + 1);
                }
                commentAdapter.notifyDataSetChanged();
                break;
            case InfoDetailContract.COLLECT_TAG:
                isCollect = !isCollect;
                collectId = ((BaseDataBean) o).getData();
                break;
            case NewsContract.DELETE_FOLLOW:
                newsDetailBean.setIsFollow(0);
                mInfoGuanzhuBtn.setText("关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.white));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_blue);
                break;
            case NewsContract.ADD_FOLLOW://添加成功
                newsDetailBean.setIsFollow(1);
                mInfoGuanzhuBtn.setText("已关注");
                mInfoGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
                mInfoGuanzhuBtn.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        switch (tag) {
            case InfoDetailContract.GET_NEWS_DETAIL:
                finish();
                break;
            case InfoDetailContract.GET_COMMENT_LIST:
                mInfoSmartrefreshlayout.finishRefresh();
                mInfoSmartrefreshlayout.finishLoadMore();
                break;
            case InfoDetailContract.LIKE_TAG:
                //点赞失败
                mInfoLikeIv.setSelected(isLike);
                break;
            case InfoDetailContract.COLLECT_TAG:
                //收藏失败
                mInfoCollectIv.setSelected(isCollect);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (MyApp.isFastClick()) {
            ToastUtils.warning(this, "操作过于频繁！");
            return;
        }
        if (0 == UserInfoManager.getAccountStatus()) {
            //游客模式
            MyApp.goLogin();
            return;
        }
        switch (v.getId()) {
            default:
                break;
            case R.id.info_user_image:
                //作者头像
                startActivity(new Intent(mContext, PersonalHomepageActivity.class).putExtra(AppUtils.ID_KEY,
                        newsDetailBean.getUserId()));
                break;
            case R.id.comment_edittext:
                    //弹窗评论窗
                    editCommentDialog.show(getSupportFragmentManager(), "commentDialog", 8,
                            newsId, -1, -1, refreshListener, BaseNewsInfoActivity.this);
                break;
            case R.id.info_collect_iv:
                //收藏
                if (isCollect) {
                    List<Integer> ids = new ArrayList<>();
                    ids.add(collectId);
                    myCollectPresent.deleteCollecListNews(-1, -1, 1, -1, -1, ids, InfoDetailContract.COLLECT_TAG);
                } else {
                    myCollectPresent.deleteCollecListNews(collectId, MyApp.getUid(), 0, 8, newsId, null,
                            InfoDetailContract.COLLECT_TAG);
                }
                mInfoCollectIv.setSelected(!isCollect);
                break;
            case R.id.info_like_iv:
                //点赞
                if (isLike) {//取消
                    mPresenter.like(-1, -1, 1, 0, likeId, InfoDetailContract.LIKE_TAG);
                } else {
                    mPresenter.like(likeId, MyApp.getUid(), 0, 1, newsId, InfoDetailContract.LIKE_TAG);
                }
                mInfoLikeIv.setSelected(!isLike);
                break;
            case R.id.info_share_iv:
                //更多
                initBottomDialog();
                break;
        }
    }

    /**
     * 初始化dialog
     */
    public void initBottomDialog() {
        if (moreBottomDialog == null) {
            onItemClick = new BaseMoreBottomDialog.OnItemClick() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position){
                        case 0://微信
                            initShare(Wechat.NAME);
                            break;
                        case 1://朋友圈
                            initShare(WechatMoments.NAME);
                            break;
                        case 2://微信收藏
                            initShare(WechatFavorite.NAME);
                            break;
                        case 3://qq
                            initShare(QQ.NAME);//QZone.NAME空间
                            break;
                        case 4://qq空间
                            initShare(QZone.NAME);//QZone.NAME空间
                            break;
                        case 5:
                            if (UserInfoManager.getAccountStatus()==0) {
                                MyApp.goLogin();
                                return;
                            }
                            startActivity(new Intent(mContext, ReportActivity.class).putExtra(ReportActivity.INFO_ID, newsDetailBean.getId()));
                            break;
                        case 6:
                            if (newsDetailBean.getTypeId() == 1){
                                //视频下载
                                downloadFileContent(newsDetailBean.getVideoUrl());
                            }
                            break;
                        case 7://复制链接
                            BaseAppUtils.copyContentToClipboard(newsDetailBean.getShareUrl(), mContext);
                            break;
                    }
                    moreBottomDialog.dismiss();
                }
            };
            moreBottomDialog = new BaseMoreBottomDialog();
            moreBottomDialog.setOnBottomDialogCallBack(onItemClick);
        }
        if (newsDetailBean.getTypeId() == 1){
            moreBottomDialog.setData(mPresenter.getMoreMenu(false, true));
        }else {
            moreBottomDialog.setData(mPresenter.getMoreMenu(false, false));
        }
        moreBottomDialog.show(getSupportFragmentManager(), "moreMenu");
    }

    /**
     * 释放dialog
     */
    private void releaseDialog() {
        if (moreBottomDialog != null) {
            if (moreBottomDialog.isAdded()) {
                onItemClick = null;
                moreBottomDialog.setOnBottomDialogCallBack(null);
                if (moreBottomDialog.getDialog().isShowing()){
                    moreBottomDialog.dismiss();
                }
            }
        }
        moreBottomDialog = null;
    }

    /**
     * 分享初始化
     */
    public void initShare(String platform) {
        if (newsDetailBean != null || newsDetailBean.getShareUrl() != null) {
            shareContent = newsDetailBean.getTitle();
            String picPath = null;
            if (newsDetailBean.getFileList().size() > 0) {
                picPath = newsDetailBean.getFileList().get(0);
            } else if (StringTools.isStringValueOk(newsDetailBean.getCoverUrl())){
                picPath = newsDetailBean.getCoverUrl();
            }else {
                picPath = getString(R.string.logo_url);
            }
            ToolShare.shareForMob(mContext,
                    newsDetailBean.getTitle(),
                    newsDetailBean.getShareUrl(),
                    shareContent,
                    picPath,
                    callback,
                    platform);
        } else {
            ToastUtils.warning(mContext, "分享失败！");
        }
    }

    /**
     * 分享外部回调
     */
    protected PlatformActionListener callback = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //  分享成功后的操作或者提示
            ToastUtils.success(mContext, "分享成功！");
            infoDetailPresent.addShare(0, 8, newsId, InfoDetailContract.SHARE_TAG);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            //  失败，打印throwable为错误码
            ToastUtils.warning(mContext, "分享失败！");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            //  分享取消操作
            ToastUtils.warning(mContext, "分享已取消！");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NewsContract.REQUEST_CODE_CHOOSE == requestCode){
            if (editCommentDialog!= null) {
                editCommentDialog.onActivityResult(requestCode,resultCode,data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            releaseDialog();
            callback = null;
            infoDetailPresent.setCallBack(null);
            myCollectPresent.setCallBack(null);
            commentAdapter.getData().clear();
        } catch (Exception e) {
        }
        super.onDestroy();
    }
}
