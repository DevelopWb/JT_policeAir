package com.juntai.wisdom.policeAir.home_page.news.news_common;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseDownLoadFragment;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseMoreBottomDialog;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.news.NewsListBean;
import com.juntai.wisdom.policeAir.home_page.InfoDetailContract;
import com.juntai.wisdom.policeAir.home_page.InfoDetailPresent;
import com.juntai.wisdom.policeAir.home_page.news.NewsContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.home_page.news.news_info.report.ReportActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_publish.PublishNewsActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_search.NewsSearchActivity;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.policeAir.utils.ToolShare;
import com.juntai.wisdom.policeAir.utils.UserInfoManager;
import com.juntai.wisdom.video.Jzvd.AutoPlayUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @description 资讯列表
 * @aouther ZhangZhenlong
 * @date 2020-7-29
 */
public class NewsListFragment extends BaseDownLoadFragment<NewsPresent> implements NewsContract.INewsView, View.OnClickListener {
    private LinearLayout mSearchLl;
    /**
     * 发布
     */
    private ImageView mPublishBtn;
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    int page = 1, pagesize = 20;
    private List<NewsListBean.DataBean.DatasBean> newsList = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    PopupWindow popupWindow;
    private LinearLayoutManager mLayoutManager;

    private BaseMoreBottomDialog moreBottomDialog;
    private BaseMoreBottomDialog.OnItemClick onItemClick;
    InfoDetailPresent infoDetailPresent;
    private int currPosition;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        mSearchLl = getView(R.id.search_ll);
        mSearchLl.setOnClickListener(this);
        mPublishBtn = getView(R.id.publish_btn);
        mPublishBtn.setOnClickListener(this);
        mRecyclerview = getView(R.id.recyclerview);
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mSearchLl.getLayoutParams();
        layoutParams.setMargins(DisplayUtil.dp2px(mContext, 15), MyApp.statusBarH + DisplayUtil.dp2px(mContext, 10),
                DisplayUtil.dp2px(mContext, 10),DisplayUtil.dp2px(mContext, 8));
        mSearchLl.setLayoutParams(layoutParams);

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });

        infoDetailPresent = new InfoDetailPresent();
        infoDetailPresent.setCallBack(this);

        newsListAdapter = new NewsListAdapter(newsList);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerview.setLayoutManager(mLayoutManager);
        newsListAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.load_more_no_data),R.mipmap.none_publish));
        mRecyclerview.setAdapter(newsListAdapter);
        newsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyApp.gotoNewsInfo(newsListAdapter.getItem(position).getTypeId(), newsListAdapter.getItem(position).getId(),
                        newsListAdapter.getItem(position).getTitle(), newsListAdapter.getItem(position).getContent(), mContext);
                newsListAdapter.getItem(position).setLooked(true);
                newsListAdapter.notifyItemChanged(position);
            }
        });
        newsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_more:
                        if (!MyApp.isLogin()){
                            MyApp.goLogin();
                            return ;
                        }
                        currPosition = position;
                        initBottomDialog();
                        break;
                    default:
                        break;
                }
            }
        });

        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    AutoPlayUtils.onScrollPlayVideo(recyclerView, R.id.videoplayer, mLayoutManager.findFirstVisibleItemPosition(), mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy != 0) {
                    AutoPlayUtils.onScrollReleaseAllVideos(mLayoutManager.findFirstVisibleItemPosition(), mLayoutManager.findLastVisibleItemPosition(), 0.2f);
                }
            }
        });
    }

    @Override
    protected void initData() {}

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    protected void lazyLoad() {
        if (newsList == null || newsList.isEmpty()){
            //加载数据
            page = 1;
            getData(true);
        }
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        mPresenter.getNewsList(page,pagesize, NewsContract.GET_NEWS_LIST, showProgress);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
//            case InfoDetailContract.COLLECT_TAG:
//                newsListAdapter.getItem(currPosition).setIsCollect(((BaseDataBean) o).getData());
//                break;
            case NewsContract.GET_NEWS_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();

                NewsListBean newsListBean = (NewsListBean)o;
                if (page == 1){
                    newsListAdapter.getData().clear();
                }
                if (newsListBean.getData().getDatas().size() < pagesize){
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                newsListAdapter.addData(newsListBean.getData().getDatas());
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        mSmartrefreshlayout.finishRefresh();
        mSmartrefreshlayout.finishLoadMore();
        ToastUtils.error(mContext, String.valueOf(o));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.search_ll:
                //资讯搜索
                startActivity(new Intent(mContext, NewsSearchActivity.class));
                break;
            case R.id.publish_btn:
                //发布
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }
                showPopType(mPublishBtn);
                break;
        }
    }

    public void showPopType(View view) {
        View viewPop = LayoutInflater.from(mContext).inflate(R.layout.pop_publish_item, null);
        popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(view, 0, 5);//显示在控件下面
        viewPop.findViewById(R.id.item_one).setOnClickListener(v -> {
            startActivity(new Intent(mContext, PublishNewsActivity.class).putExtra("type",0));
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.item_two).setOnClickListener(v -> {
            startActivity(new Intent(mContext, PublishNewsActivity.class).putExtra("type",1));
            popupWindow.dismiss();
        });
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
                            initShare(newsListAdapter.getItem(currPosition), Wechat.NAME);
                            break;
                        case 1://朋友圈
                            initShare(newsListAdapter.getItem(currPosition), WechatMoments.NAME);
                            break;
                        case 2://微信收藏
                            initShare(newsListAdapter.getItem(currPosition), WechatFavorite.NAME);
                            break;
                        case 3://qq
                            initShare(newsListAdapter.getItem(currPosition), QQ.NAME);//QZone.NAME空间
                            break;
                        case 4://qq空间
                            initShare(newsListAdapter.getItem(currPosition), QZone.NAME);//QZone.NAME空间
                            break;
                        case 5:
                            if (UserInfoManager.getAccountStatus()==0) {
                                MyApp.goLogin();
                                return;
                            }
                            NewsListBean.DataBean.DatasBean bean = newsListAdapter.getItem(currPosition);
                            startActivity(new Intent(mContext, ReportActivity.class).putExtra(ReportActivity.INFO_ID,bean.getId()));
                            break;
//                        case 6://收藏
//                            int collectId = newsListAdapter.getItem(currPosition).getIsCollect();
//                            if (collectId != 0) {
//                                List<Integer> ids = new ArrayList<>();
//                                ids.add(newsListAdapter.getItem(currPosition).getIsCollect());
//                                myCollectPresent.deleteCollecListNews(-1, -1, 1, -1, -1, ids, InfoDetailContract.COLLECT_TAG);
//                            } else {
//                                myCollectPresent.deleteCollecListNews(collectId, MyApp.getUid(), 0, 8, newsListAdapter.getItem(currPosition).getId(), null,
//                                        InfoDetailContract.COLLECT_TAG);
//                            }
//                            break;
                        case 6:
                            if (newsListAdapter.getItem(currPosition).getTypeId() == 1){
                                //视频下载
                                downloadFileContent(newsListAdapter.getItem(currPosition).getVideoUrl());
                            }
                            break;
                        case 7://复制链接
                            BaseAppUtils.copyContentToClipboard(newsListAdapter.getItem(currPosition).getShareUrl(), mContext);
                            break;
                    }
                    moreBottomDialog.dismiss();
                }
            };
            moreBottomDialog = new BaseMoreBottomDialog();
//            moreBottomDialog.setData(mPresenter.getMoreMenu());
            moreBottomDialog.setOnBottomDialogCallBack(onItemClick);
        }
        if (newsListAdapter.getItem(currPosition).getTypeId() == 1){
            moreBottomDialog.setData(mPresenter.getMoreMenu(false, true));
        }else {
            moreBottomDialog.setData(mPresenter.getMoreMenu(false, false));
        }
        moreBottomDialog.show(getChildFragmentManager(), "moreMenu");
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

    @Override
    public void onDestroyView() {
        infoDetailPresent.setCallBack(null);
        super.onDestroyView();
        releaseDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.UPDATE_NEWS_LIST.equals(test)){
            //刷新
            page = 1;
            getData(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser){
            Jzvd.releaseAllVideos();
        }
    }

    /**
     * 分享初始化
     */
    public void initShare(NewsListBean.DataBean.DatasBean newsDataBean, String platform) {
        if (newsDataBean != null || newsDataBean.getShareUrl() != null) {
            String shareContent = newsDataBean.getTitle();
            String picPath = null;
            if (newsDataBean.getFileList().size() > 0) {
                picPath = newsDataBean.getFileList().get(0).getFileUrl();
            } else if (StringTools.isStringValueOk(newsDataBean.getCoverUrl())){
                picPath = newsDataBean.getCoverUrl();
            }else {
                picPath = getString(R.string.logo_url);
            }
            ToolShare.shareForMob(mContext,
                    newsDataBean.getTitle(),
                    newsDataBean.getShareUrl(),
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
            infoDetailPresent.addShare(0, 8, newsListAdapter.getItem(currPosition).getId(), InfoDetailContract.SHARE_TAG);
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
