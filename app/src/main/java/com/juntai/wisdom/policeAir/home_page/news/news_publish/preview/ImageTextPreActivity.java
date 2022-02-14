package com.juntai.wisdom.policeAir.home_page.news.news_publish.preview;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.home_page.news.NewsContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;

/**
 * @description 图文资讯预览
 * @aouther ZhangZhenlong
 * @date 2020-12-31
 */
public class ImageTextPreActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView {
    public static final String NEWS_PART_BODY = "news_part_body";
    /**
     * 标题
     */
    private TextView mInfoTitleTv;
    private WebView mNewsContentWeb;
    private FrameLayout mWebContainer;
    Map<String, String> bodyMap;
    private String newsContent;
    private String newsTitle;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_image_text_pre;
    }

    @Override
    public void initView() {
        setTitleName("资讯预览");
        bodyMap = (Map<String, String>) getIntent().getSerializableExtra(NEWS_PART_BODY);
        mInfoTitleTv = (TextView) findViewById(R.id.info_title_tv);
        mWebContainer = (FrameLayout) findViewById(R.id.web_container);
        newsTitle = bodyMap.get("title");
        newsContent = bodyMap.get("content");

        getTitleRightTv().setText("发布");
        getTitleRightTv().setOnClickListener(v -> {
            if (bodyMap != null && bodyMap.size() > 0){
                submitData();
            }else {
                ToastUtils.warning(mContext, "请重新编辑！");
            }
        });

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
        if (newsTitle != null && newsContent != null){
            mInfoTitleTv.setText(newsTitle);
            initNewsContent(newsContent);
        }
    }

    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void startPhotoActivity(String imageUrl) {
            //根据URL查看大图逻辑
//            if (images != null && images.size() > 0) {
//                for (int i = 0; i < images.size(); i++) {
//                    if (images.get(i).equals(imageUrl)) {
//                        mContext.startActivity(new Intent(mContext, ImageZoomActivity.class)
//                                .putExtra("paths", (ArrayList) images)
//                                .putExtra("item", i));
//                        return;
//                    }
//                }
//            }
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case NewsContract.PUBLISH_IMAGE_TEXT_NEWS:
                ToastUtils.success(mContext, (String)o);
                EventManager.sendStringMsg(ActionConfig.FINISH_AFTER_PUBISH);
                EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    /**
     * 发布
     */
    public void submitData(){
        if (MyApp.isFastClick()) {
            ToastUtils.warning(mContext,"点击过于频繁");
            return;
        }
        //发布
        MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
        builder.addFormDataPart("userid",String.valueOf(MyApp.getUid()))
                .addFormDataPart("longitude", Objects.requireNonNull(bodyMap.get("longitude")))
                .addFormDataPart("latitude", Objects.requireNonNull(bodyMap.get("latitude")))
                .addFormDataPart("typeId", Objects.requireNonNull(bodyMap.get("typeId")))
                .addFormDataPart("informationId", Objects.requireNonNull(bodyMap.get("informationId")))
                .addFormDataPart("title", Objects.requireNonNull(bodyMap.get("title")))
                .addFormDataPart("content", Objects.requireNonNull(bodyMap.get("content")))
                .addFormDataPart("address", Objects.requireNonNull(bodyMap.get("address")));
        mPresenter.publishNews(NewsContract.PUBLISH_IMAGE_TEXT_NEWS, builder.build());
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
