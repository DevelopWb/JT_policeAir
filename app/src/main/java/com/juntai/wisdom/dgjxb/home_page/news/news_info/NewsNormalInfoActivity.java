package com.juntai.wisdom.dgjxb.home_page.news.news_info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.news.NewsDetailBean;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.dgjxb.utils.UserInfoManager;
import com.juntai.wisdom.video.img.ImageZoomActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 图文资讯详情
 * @aouther ZhangZhenlong
 * @date 2020-8-13
 */
public class NewsNormalInfoActivity extends BaseNewsInfoActivity {
    private TextView mInfoTitleTv;
    private WebView mNewsContentWeb;
    private FrameLayout mWebContainer;
    public static List<String> images = new ArrayList<>();
    public static final String NEWS_CONTENT = "news_content";
    public static final String NEWS_TITLE = "news_title";
    private String newsContent;
    private String newsTitle;

    private NestedScrollView nested_scrollview;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_news_normal_info;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        super.initView();
        setTitleName("资讯详情");
        newsContent = getIntent().getStringExtra(NEWS_CONTENT);
        newsTitle = getIntent().getStringExtra(NEWS_TITLE);
        nested_scrollview = findViewById(R.id.nested_scrollview);
        mInfoTitleTv = (TextView) findViewById(R.id.info_title_tv);
        mWebContainer = (FrameLayout) findViewById(R.id.web_container);
        mNewsContentWeb = new WebView(mContext.getApplicationContext());
        //        ScrollView 内置 Webview导致底部页面下方空白区域无限下滑,设置height=LayoutParams.WRAP_CONTENT解决
        mNewsContentWeb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mNewsContentWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mNewsContentWeb.setScrollBarSize(0);

        mNewsContentWeb.getSettings().setJavaScriptEnabled(true);
        //        // 解决对某些标签的不支持出现白屏
        mNewsContentWeb.getSettings().setDomStorageEnabled(true);
        //        //设置字符编码，避免乱码
        mNewsContentWeb.getSettings().setDefaultTextEncodingName("utf-8");
        mNewsContentWeb.addJavascriptInterface(new JavascriptInterface(), "newsInfoActivity");
        mWebContainer.addView(mNewsContentWeb);
    }

    @Override
    public void initData() {
        super.initData();
        //获取详情
        mPresenter.getNewsInfo(newsId, NewsContract.GET_NEWS_INFO);
        if (StringTools.isStringValueOk(newsContent)){
            mInfoTitleTv.setText(newsTitle);
            initNewsContent(newsContent);
        }
    }

    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void startPhotoActivity(String imageUrl) {
            //根据URL查看大图逻辑
            if (images != null && images.size() > 0) {
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).equals(imageUrl)) {
                        mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
                                .putExtra("paths", (ArrayList) images)
                                .putExtra("item", i));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.info_comment_iv){//滚动到评论位置
            nested_scrollview.scrollTo(0, mInfoCommentRecycleview.getTop());
        }
        super.onClick(v);
        switch (v.getId()) {
            case R.id.info_guanzhu_btn:
                if (0 == UserInfoManager.getAccountStatus()) {
                    //游客模式
                    MyApp.goLogin();
                    return;
                }
                //关注
                if (newsDetailBean.getIsFollow() > 0) {
                    //删除
                    mPresenter.addFollowOrDelete(2, newsDetailBean.getUserId(), NewsContract.DELETE_FOLLOW);
                } else {
                    //添加
                    mPresenter.addFollowOrDelete(1, newsDetailBean.getUserId(), NewsContract.ADD_FOLLOW);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case NewsContract.GET_NEWS_INFO:
                NewsDetailBean news = (NewsDetailBean) o;
                newsDetailBean = news.getData();
                if (newsDetailBean.getId() == 0) {
                    ToastUtils.warning(this, "数据已删除！");
                    finish();
                } else {
                    viewSetData();
                    mInfoTimeRead.setVisibility(View.VISIBLE);
                    mInfoTimeRead.setText(newsDetailBean.getBrowseNum() + "次查看\u3000" + newsDetailBean.getGmtCreate() + "发布");
                    mInfoTitleTv.setText(newsDetailBean.getTitle());
                    if (!StringTools.isStringValueOk(newsContent)){
                        initNewsContent(newsDetailBean.getContent());
                    }
                    images.clear();
                    images.addAll(newsDetailBean.getFileList());
                }
                break;
        }
    }

    /**
     * 格式化资讯内容
     * @param content
     */
    private void initNewsContent(String content){
        if (StringTools.isStringValueOk(content)) {
            //点击查看
            String jsimg = "function()\n { var imgs = document.getElementsByTagName(\"img\");for(var i = " +
                    "0; i < imgs.length; i++){  imgs[i].onclick = function()\n{newsInfoActivity" +
                    ".startPhotoActivity(this.src);}}}";
            StringBuffer sb = new StringBuffer();
            sb.append("<html><head><link href=\"show_style.css\" type=\"text/css\" " +
                    "rel=\"stylesheet\"/></head><body>");
            sb.append(content.replaceAll("\n", "</br>") + "</body></html>");
            mNewsContentWeb.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html",
                    "utf-8", null);
            mNewsContentWeb.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView webView, String s) {
                    mNewsContentWeb.loadUrl("javascript:(" + jsimg + ")()");
                }
            });
        }
    }

    @Override
    protected String getTitleRightName() {
        return "";
    }

    @Override
    protected String getDownLoadPath() {
        return "";
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    public void destroyWebView() {
        if (mNewsContentWeb != null) {
            ViewParent parent = mNewsContentWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mNewsContentWeb);
            }
            mNewsContentWeb.stopLoading();
            mNewsContentWeb.getSettings().setJavaScriptEnabled(false);
            mNewsContentWeb.clearHistory();
            mNewsContentWeb.removeAllViews();
            mNewsContentWeb.destroy();
        }
    }
}
