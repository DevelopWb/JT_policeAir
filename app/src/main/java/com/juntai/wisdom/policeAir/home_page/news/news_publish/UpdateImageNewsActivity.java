package com.juntai.wisdom.policeAir.home_page.news.news_publish;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.news.NewsDetailBean;
import com.juntai.wisdom.policeAir.bean.news.NewsUploadPhotoBean;
import com.juntai.wisdom.policeAir.home_page.PublishContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsContract;
import com.juntai.wisdom.policeAir.home_page.news.NewsPresent;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.zhihu.matisse.Matisse;


import java.io.File;
import java.util.List;

import cn.qzb.richeditor.RE;
import cn.qzb.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 图文资讯修改编辑
 * @aouther ZhangZhenlong
 * @date 2020-9-23
 */
public class UpdateImageNewsActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView, View.OnClickListener {
    /**
     * 起一个吸引人的名称让更多人看到哟
     */
    private EditText mTitleEt;
    /**
     * 请选择发布位置
     */
    private TextView mAddressTv;//地址
    private ImageView mActionImg;//图片选择
    private ImageView mActionBold;//加粗
    private ImageView mActionItalic;//斜体
    private ImageView mActionUnderline;//下划线
    private LinearLayout mBottom;//
    private RichEditor mEditor;//
    public RE re;
    private FrameLayout mWebContainer;

    //地址
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat, locLon;
    protected int newsId;//资讯id
    protected NewsDetailBean.DataBean newsDetailBean;

    private int iconSelectColor = Color.BLACK;
    private int iconDefaultColor = Color.parseColor("#CDCDCD");
    private String informationId;
    private ImageView mActionAlignLeft;
    private ImageView mActionAlignCenter;
    private ImageView mActionAlignRight;
    private ImageView mActionUndo;
    private ImageView mActionRedo;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_publish_image_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            re.reFreshState();
        }
    }

    @Override
    public void initView() {
        setTitleName("资讯编辑");
        newsId = getIntent().getIntExtra(AppUtils.ID_KEY,0);
        mTitleEt = (EditText) findViewById(R.id.title_et);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mAddressTv.setOnClickListener(this);
        mActionImg = (ImageView) findViewById(R.id.action_img);
        mActionImg.setOnClickListener(this);
        mActionBold = (ImageView)findViewById(R.id.action_bold);
        mActionBold.setOnClickListener(this);
        mActionItalic = (ImageView)findViewById(R.id.action_italic);
        mActionItalic.setOnClickListener(this);
        mActionUnderline = (ImageView)findViewById(R.id.action_underline);
        mActionUnderline.setOnClickListener(this);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mWebContainer = (FrameLayout) findViewById(R.id.web_container);
        mEditor = new RichEditor(mContext.getApplicationContext());
        mWebContainer.addView(mEditor);
        mActionAlignLeft = findViewById(R.id.action_align_left);
        mActionAlignLeft.setOnClickListener(this);
        mActionAlignCenter = findViewById(R.id.action_align_center);
        mActionAlignCenter.setOnClickListener(this);
        mActionAlignRight = findViewById(R.id.action_align_right);
        mActionAlignRight.setOnClickListener(this);
        mActionUndo = findViewById(R.id.action_undo);
        mActionUndo.setOnClickListener(this);
        mActionRedo = findViewById(R.id.action_redo);
        mActionRedo.setOnClickListener(this);

        getTitleRightTv().setText("发布");
        getTitleRightTv().setOnClickListener(v -> {
            submitData();
        });

        re = RE.getInstance(mEditor);
        re.setPlaceHolder(getString(R.string.news_publish_article_hint));
        re.setPadding(20, 20, 20, 20);

        mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mBottom.setVisibility(View.VISIBLE);
                }else {
                    mBottom.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initData() {
        changeIconColor(mActionBold, iconDefaultColor);
        changeIconColor(mActionItalic, iconDefaultColor);
        changeIconColor(mActionUnderline, iconDefaultColor);
        changeIconColor(mActionAlignLeft, iconDefaultColor);
        changeIconColor(mActionAlignCenter, iconDefaultColor);
        changeIconColor(mActionAlignRight, iconDefaultColor);
        initViewRightDrawable(mAddressTv, R.mipmap.ic_push_location,23,23);
        //获取详情
        mPresenter.getNewsInfo(newsId, NewsContract.GET_NEWS_INFO);
    }

    // 改变底部图标颜色
    private void changeIconColor(ImageView imageView, int color) {
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color));
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            default:
                break;
            case NewsContract.YASUO_PHOTO_TAG:
                List<String> photos = (List<String>) o;
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("informationId",informationId);
                if (photos != null && photos.size() > 0){
                    for (String photoPath:photos) {
                        if (StringTools.isStringValueOk(photoPath)){
                            builder.addFormDataPart("file", "file", RequestBody.create(MediaType.parse("file"), new File(photoPath)));
                        }
                    }
                }else {
                    return;
                }
                mPresenter.uploadNewsPhoto(NewsContract.UPLOAD_NEWS_PHOTO, builder.build());
                break;
            case NewsContract.UPLOAD_NEWS_PHOTO:
                NewsUploadPhotoBean photoBean = (NewsUploadPhotoBean) o;
                List<String> photoList = photoBean.getData();
                if (photoList != null && photoList.size() > 0){
                    for (String path:photoList){
                        // 插入图片
                        re.insertImage(path, "image");
                    }
                }
                break;
            case NewsContract.UPDATE_IMAGE_TEXT_NEWS:
                ToastUtils.success(mContext, getString(R.string.publish_success_tag));
                EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                finish();
                break;
            case NewsContract.GET_NEWS_INFO:
                NewsDetailBean news = (NewsDetailBean) o;
                newsDetailBean = news.getData();
                if (newsDetailBean == null || newsDetailBean.getId() == 0){
                    ToastUtils.warning(mContext,"数据已删除！");
                    finish();
                }else {
                    if (StringTools.isStringValueOk(newsDetailBean.getContent())){
                        String data = newsDetailBean.getContent();
                        mTitleEt.setText(newsDetailBean.getTitle());
                        locAddress = newsDetailBean.getAddress();
                        locLat = newsDetailBean.getLatitude();
                        locLon = newsDetailBean.getLongitude();
                        mAddressTv.setText(newsDetailBean.getAddress());
                        informationId = newsDetailBean.getInformationId();
                        re.setHtml(data);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.address_tv:
                //地址选择
                Intent intent = new Intent(mContext, LocationSeltionActivity.class);
                startActivityForResult(intent, PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.action_img:
                //图片选择
                mPresenter.imageChoose((BaseActivity)mContext, 9);
                break;
            case R.id.action_bold:
                re.setBold();
                if (re.isBold()) {
                    changeIconColor(mActionBold, iconSelectColor);
                } else {
                    changeIconColor(mActionBold, iconDefaultColor);
                }
                break;
            case R.id.action_italic:
                re.setItalic();
                if (re.isItalic()) {
                    changeIconColor(mActionItalic, iconSelectColor);
                } else {
                    changeIconColor(mActionItalic, iconDefaultColor);
                }
                break;
            case R.id.action_underline:
                re.setUnderLine();
                if (re.isUnderline()) {
                    changeIconColor(mActionUnderline, iconSelectColor);
                } else {
                    changeIconColor(mActionUnderline, iconDefaultColor);
                }
                break;
            case R.id.action_align_left:
                if (re.getTextAlign() == 1){
                    return;
                }
                re.setAlignLeft();
                changeIconColor(mActionAlignLeft, iconSelectColor);
                changeIconColor(mActionAlignCenter, iconDefaultColor);
                changeIconColor(mActionAlignRight, iconDefaultColor);
                break;
            case R.id.action_align_center:
                if (re.getTextAlign() == 2){
                    return;
                }
                re.setAlignCenter();
                changeIconColor(mActionAlignLeft, iconDefaultColor);
                changeIconColor(mActionAlignCenter, iconSelectColor);
                changeIconColor(mActionAlignRight, iconDefaultColor);
                break;
            case R.id.action_align_right:
                if (re.getTextAlign() == 3){
                    return;
                }
                re.setAlignRight();
                changeIconColor(mActionAlignLeft, iconDefaultColor);
                changeIconColor(mActionAlignCenter, iconDefaultColor);
                changeIconColor(mActionAlignRight, iconSelectColor);
                break;
            case R.id.action_undo:
                re.undo();
                break;
            case R.id.action_redo:
                re.redo();
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
        if (!StringTools.isStringValueOk(getTextViewValue(mTitleEt))) {
            ToastUtils.warning(mContext, "请输入标题");
            return;
        }
        if (!StringTools.isStringValueOk(locAddress)){
            ToastUtils.warning(mContext, "请选择发布位置");
            return;
        }
        if (!StringTools.isStringValueOk(re.getHtml())){
            ToastUtils.warning(mContext, "请完成你的创作");
            return;
        }
        //发布
        MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
        builder.addFormDataPart("longitude", String.valueOf(locLon))
                .addFormDataPart("latitude", String.valueOf(locLat))
                .addFormDataPart("id", String.valueOf(newsId))
                .addFormDataPart("informationId", informationId)
                .addFormDataPart("title", getTextViewValue(mTitleEt))
                .addFormDataPart("content", re.getHtml())
                .addFormDataPart("address", locAddress)
                .addFormDataPart("typeId", "2");//类型（1：视频；2：图文）;
        mPresenter.updateNews(NewsContract.UPDATE_IMAGE_TEXT_NEWS, builder.build());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == RESULT_OK) {
            //地址选择
            locLat = data.getDoubleExtra("lat", 0.0);
            locLon = data.getDoubleExtra("lng", 0.0);
            locAddress = data.getStringExtra("address");
            mAddressTv.setText(locAddress);
            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
        }else if (requestCode == NewsContract.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mPresenter.imageCompress(Matisse.obtainPathResult(data),(BaseMvpActivity) this);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setCancelable(true)
                .setMessage("确定要放弃编辑么？")
                .setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    public void destroyWebView() {
        if (mEditor != null) {
            ViewParent parent = mEditor.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mEditor);
            }
            mEditor.stopLoading();
            mEditor.getSettings().setJavaScriptEnabled(false);
            mEditor.clearHistory();
            mEditor.removeAllViews();
            mEditor.destroy();
        }
    }

}
