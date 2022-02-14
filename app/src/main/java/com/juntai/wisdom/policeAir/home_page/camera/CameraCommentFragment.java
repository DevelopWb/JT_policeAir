package com.juntai.wisdom.policeAir.home_page.camera;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.BaseDataBean;
import com.juntai.wisdom.policeAir.bean.CommentListBean;
import com.juntai.wisdom.policeAir.bean.stream.StreamCameraDetailBean;
import com.juntai.wisdom.policeAir.home_page.InfoDetailContract;
import com.juntai.wisdom.policeAir.home_page.news.news_comment.CommentAdapter;
import com.juntai.wisdom.policeAir.home_page.news.news_comment.CommentChildActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_comment.EditCommentDialog;
import com.juntai.wisdom.policeAir.mine.mycollect.MyCollectPresent;
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

/**
 * @Author: tobato
 * @Description: 作用描述  摄像头评论
 * @CreateDate: 2020/8/14 9:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/14 9:23
 */
public class CameraCommentFragment extends BaseMvpFragment<PlayPresent> implements PlayContract.IPlayView,
        View.OnClickListener {
    /**
     * 设备位置:
     */
    private TextView mDeviceplace;
    /**
     * 设备状态:
     */
    private TextView mDevicestate;
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    /**
     * 写评论···
     */
    private TextView mCommentEdittext;
    private ImageView mInfoCommentIv;
    private ImageView mInfoCollectIv;
    private ImageView mInfoLikeIv;
    private ImageView mInfoShareIv;
    /**
     * 0
     */
    private TextView mInfoCommentCount;
    /**
     * 6
     */
    private TextView mInfoLikeCount;

    //是否收藏，收藏操作是否执行中，是否点赞，点赞操作是否执行中,详情未加载出来不可以点赞收藏
    protected boolean isCollect = false, isCollectIng = true, isLike = false, isLikeIng = true;
    protected int likeId, collectId;//点赞收藏id
    protected EditCommentDialog editCommentDialog;
    protected int page = 1, pagesize = 10;
    protected int commentPositon;//点赞评论位置
    private StreamCameraDetailBean.DataBean mStreamCameraBean;//详情

    CommentAdapter commentAdapter;
    MyCollectPresent myCollectPresent;

    @Override
    protected PlayPresent createPresenter() {
        return new PlayPresent();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_camera_comment;
    }

    @Override
    protected void initView() {
        myCollectPresent = new MyCollectPresent();
        myCollectPresent.setCallBack(this);
        editCommentDialog = new EditCommentDialog();
        mDeviceplace = (TextView) getView(R.id.deviceplace);
        mDevicestate = (TextView) getView(R.id.devicestate);
        mRecyclerview = (RecyclerView) getView(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) getView(R.id.smartrefreshlayout);
        mCommentEdittext = (TextView) getView(R.id.comment_edittext);
        mCommentEdittext.setOnClickListener(this);
        mInfoCommentIv = (ImageView) getView(R.id.info_comment_iv);
        mInfoCommentIv.setOnClickListener(this);
        mInfoCollectIv = (ImageView) getView(R.id.info_collect_iv);
        mInfoCollectIv.setOnClickListener(this);
        mInfoLikeIv = (ImageView) getView(R.id.info_like_iv);
        mInfoLikeIv.setOnClickListener(this);
        mInfoShareIv = (ImageView) getView(R.id.info_share_iv);
        mInfoShareIv.setOnClickListener(this);
        mInfoCommentCount = (TextView) getView(R.id.info_comment_count);
        mInfoCommentCount.setVisibility(View.GONE);
        mInfoLikeCount = (TextView) getView(R.id.info_like_count);

        //评论
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        commentAdapter = new CommentAdapter(R.layout.item_comment_news, new ArrayList());
        mRecyclerview.setAdapter(commentAdapter);
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
                if (view.getId() == R.id.item_comment_huifu) {
                    //回复
                    editCommentDialog.show(getChildFragmentManager(),
                            "commentDialog",
                            0,
                            mStreamCameraBean.getId(),
                            commentAdapter.getData().get(position).getId(),
                            commentAdapter.getData().get(position).getUserId(),
                            refreshListener, (BaseMvpActivity) getActivity());
                } else if (view.getId() == R.id.item_comment_like) {
                    //评论点赞
                    commentPositon = position;
                    if (commentAdapter.getData().get(position).getIsLike() > 0) {//取消
                        mPresenter.like(-1, -1, 1, 0, commentAdapter.getData().get(position).getIsLike(),
                                InfoDetailContract.LIKE_COMMENT_TAG);
                    } else {
                        mPresenter.like(commentAdapter.getData().get(position).getIsLike(), MyApp.getUid(), 0,
                                2, commentAdapter.getData().get(position).getId(), InfoDetailContract.LIKE_COMMENT_TAG);
                    }
                } else if (view.getId() == R.id.item_comment_user_more) {
                    //更多--子评论
                    startActivity(new Intent(mContext, CommentChildActivity.class)
                            .putExtra("id", commentAdapter.getData().get(position).getId())
                            .putExtra("type", 0));
                }
            }
        });
        //刷新
        mSmartrefreshlayout.setEnableRefresh(false);
        //加载更多
        mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                if (mStreamCameraBean != null) {
                    mPresenter.getCommentList(mStreamCameraBean.getId(), pagesize, page,
                            InfoDetailContract.GET_COMMENT_LIST);
                }
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
            mStreamCameraBean.setCommentCount(mStreamCameraBean.getCommentCount() + 1);
            mInfoCommentCount.setVisibility(View.VISIBLE);
            mInfoCommentCount.setText(String.valueOf(mStreamCameraBean.getCommentCount()));
            mPresenter.getCommentList(mStreamCameraBean.getId(), pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
        }
    };

    /**
     * 初始化数据
     */
    public void initStatusData(String status) {
        mDevicestate.setText(String.format("%s%s", "设备状态:", status));
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case InfoDetailContract.GET_COMMENT_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                CommentListBean commentListBean = (CommentListBean) o;
                if (page == 1) {
                    commentAdapter.getData().clear();
                }
                if (commentListBean.getData().getDatas().size() < pagesize) {
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                } else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                commentAdapter.addData(commentListBean.getData().getDatas());
                break;
            case InfoDetailContract.LIKE_TAG:
                mStreamCameraBean.setLikeCount(isLike ? mStreamCameraBean.getLikeCount() - 1 :
                        mStreamCameraBean.getLikeCount() + 1);
                if (mStreamCameraBean.getLikeCount() == 0) {
                    mInfoLikeCount.setVisibility(View.GONE);
                } else {
                    mInfoLikeCount.setVisibility(View.VISIBLE);
                    mInfoLikeCount.setText(String.valueOf(mStreamCameraBean.getLikeCount()));
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
                //收藏
                isCollect = !isCollect;
                collectId = ((BaseDataBean) o).getData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        switch (tag) {
            case InfoDetailContract.GET_COMMENT_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
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

    /**
     * 初始化数据
     */
    public void initViewData(StreamCameraDetailBean.DataBean cameraBean) {
        mStreamCameraBean = cameraBean;
        if (mStreamCameraBean != null) {
            mDeviceplace.setText(String.format("%s%s", "设备位置:", mStreamCameraBean.getAddress()));
            isCollectIng = false;
            isLikeIng = false;
            likeId = mStreamCameraBean.getIsLike();
            collectId = mStreamCameraBean.getIsCollect();
            //是否点赞收藏
            isCollect = mStreamCameraBean.getIsCollect() != 0;
            isLike = mStreamCameraBean.getIsLike() != 0;
            mInfoCollectIv.setSelected(isCollect);
            mInfoLikeIv.setSelected(isLike);
            if (mStreamCameraBean.getCommentCount() == 0) {
                mInfoCommentCount.setVisibility(View.GONE);
            } else {
                mInfoCommentCount.setVisibility(View.VISIBLE);
                mInfoCommentCount.setText(String.valueOf(mStreamCameraBean.getCommentCount()));
            }
            if (mStreamCameraBean.getLikeCount() == 0) {
                mInfoLikeCount.setVisibility(View.GONE);
            } else {
                mInfoLikeCount.setVisibility(View.VISIBLE);
                mInfoLikeCount.setText(String.valueOf(mStreamCameraBean.getLikeCount()));
            }
            mPresenter.getCommentList(mStreamCameraBean.getId(), pagesize, page, InfoDetailContract.GET_COMMENT_LIST);
        }
    }

    @Override
    public void onClick(View v) {
        if (0 == UserInfoManager.getAccountStatus()) {
            //游客模式
            MyApp.goLogin();
            return;
        }
        if (mStreamCameraBean == null) {
            return;
        }
        switch (v.getId()) {
            default:
                break;
            case R.id.comment_edittext:
                //弹窗评论窗
                editCommentDialog.show(getChildFragmentManager(), "commentDialog", 0,
                        mStreamCameraBean.getId(), -1, -1, refreshListener, (BaseMvpActivity) getActivity());
                break;
            case R.id.info_comment_iv:
                break;
            case R.id.info_collect_iv:
                if (isCollect) {
                    List<Integer> ids = new ArrayList<>();
                    ids.add(collectId);
                    myCollectPresent.deleteCollecListCamera(-1, -1, 1, -1, -1, ids, InfoDetailContract.COLLECT_TAG);
                } else {
                    myCollectPresent.deleteCollecListCamera(collectId, MyApp.getUid(), 0, 0,
                            mStreamCameraBean.getId(), null,
                            InfoDetailContract.COLLECT_TAG);
                }
                mInfoCollectIv.setSelected(!isCollect);
                break;
            case R.id.info_like_iv:
                //点赞
                if (isLike) {//取消
                    mPresenter.like(-1, -1, 1, 0, likeId, InfoDetailContract.LIKE_TAG);
                } else {
                    mPresenter.like(likeId, MyApp.getUid(), 0, 1, mStreamCameraBean.getId(),
                            InfoDetailContract.LIKE_TAG);
                }
                mInfoLikeIv.setSelected(!isLike);
                break;
            case R.id.info_share_iv:
                //分享微信
                if (mStreamCameraBean != null || mStreamCameraBean.getShareUrl() != null) {
                    ToolShare.shareForMob(mContext,
                            mStreamCameraBean.getName(),
                            mStreamCameraBean.getShareUrl(),
                            mStreamCameraBean.getAddress(),
                            mStreamCameraBean.getEzOpen(),
                            callback);
                } else {
                    ToastUtils.warning(mContext, "敬请期待！");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (editCommentDialog!= null) {
            editCommentDialog.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onDestroyView() {
        try {
            myCollectPresent.setCallBack(null);
            commentAdapter.getData().clear();
        } catch (Exception e) {
        }
        super.onDestroyView();
    }

    /**
     * 分享外部回调
     */
    protected PlatformActionListener callback = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //  分享成功后的操作或者提示
            ToastUtils.success(mContext, "分享成功！");
            mPresenter.addShareRecord(mPresenter.getBaseBuilder()
                            .add("isType", "0")
                            .add("number", mStreamCameraBean.getNumber())
                            .build(),
                    "");
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

}
