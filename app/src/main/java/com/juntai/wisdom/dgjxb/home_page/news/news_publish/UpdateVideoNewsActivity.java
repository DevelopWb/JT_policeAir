package com.juntai.wisdom.dgjxb.home_page.news.news_publish;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.news.NewsDetailBean;
import com.juntai.wisdom.dgjxb.home_page.PublishContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 视频资讯修改
 * @aouther ZhangZhenlong
 * @date 2020-11-13
 */
public class UpdateVideoNewsActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView, View.OnClickListener {

    private ImageView mVideoImage;
    /**
     * 起一个吸引人的名称让更多人看到哟
     */
    private EditText mTitleEt;
    /**
     * 请选择发布位置
     */
    private TextView mAddressTv;
    /**
     * 简单介绍下视频吧
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/500
     */
    private TextView mLimitTv;

    private String videoScreen;//视频封面
    private String locAddress = "";
    private Double locLat, locLon;
    protected int newsId;//资讯id
    protected NewsDetailBean.DataBean newsDetailBean;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_update_video_news;
    }

    @Override
    public void initView() {
        setTitleName("资讯编辑");
        newsId = getIntent().getIntExtra(AppUtils.ID_KEY,0);
        mVideoImage = (ImageView) findViewById(R.id.video_image);
        mVideoImage.setOnClickListener(this);
        mTitleEt = (EditText) findViewById(R.id.title_et);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mAddressTv.setOnClickListener(this);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mLimitTv = (TextView) findViewById(R.id.limit_tv);

        getTitleRightTv().setText("发布");
        getTitleRightTv().setOnClickListener(v -> {
            submitData();
        });

        mDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mLimitTv.setText("已输入" + s.length() + "/500");
                }
            }
        });
    }

    @Override
    public void initData() {
        initViewRightDrawable(mAddressTv, R.mipmap.ic_push_location,23,23);
        //获取详情
        mPresenter.getNewsInfo(newsId, NewsContract.GET_NEWS_INFO);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case NewsContract.UPDATE_VIDEO_NEWS:
                ToastUtils.success(mContext, getString(R.string.publish_success_tag));
                EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                onBackPressed();
                break;
            case NewsContract.GET_NEWS_INFO:
                NewsDetailBean news = (NewsDetailBean) o;
                newsDetailBean = news.getData();
                if (newsDetailBean == null || newsDetailBean.getId() == 0){
                    ToastUtils.warning(mContext,"数据已删除！");
                    onBackPressed();
                }else {
                    if (StringTools.isStringValueOk(newsDetailBean.getContent())){
                        mDescriptionEt.setText(newsDetailBean.getContent());
                        mTitleEt.setText(newsDetailBean.getTitle());
                        locAddress = newsDetailBean.getAddress();
                        locLat = newsDetailBean.getLatitude();
                        locLon = newsDetailBean.getLongitude();
                        mAddressTv.setText(newsDetailBean.getAddress());
                        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), newsDetailBean.getCoverUrl(), mVideoImage);
                    }
                }
                break;
            case NewsContract.YASUO_PHOTO_TAG:
                hideLoading();
                List<String> photos = (List<String>) o;
                if (photos != null && photos.size() > 0){
                    for (String photoPath:photos) {
                        if (StringTools.isStringValueOk(photoPath)){
                            videoScreen = photoPath;
                            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mVideoImage);
                        }
                    }
                }else {
                    return;
                }
                break;
            default:
                break;
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.video_image:
                //图片选择
                mPresenter.imageChoose((BaseActivity)mContext, 1);
                break;
            case R.id.address_tv:
                //地址选择
                Intent intent = new Intent(mContext, LocationSeltionActivity.class);
                startActivityForResult(intent, PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
        }
    }

    private void submitData() {
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
        //发布
        MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
        builder.addFormDataPart("longitude", String.valueOf(locLon))
                .addFormDataPart("latitude", String.valueOf(locLat))
                .addFormDataPart("id", String.valueOf(newsId))
                .addFormDataPart("title", getTextViewValue(mTitleEt))
                .addFormDataPart("content", getTextViewValue(mDescriptionEt))
                .addFormDataPart("address", locAddress)
                .addFormDataPart("typeId", "1");//类型（1：视频；2：图文）

        if (StringTools.isStringValueOk(videoScreen)) {
            builder.addFormDataPart("coverFile", "coverFile", RequestBody.create(MediaType.parse("file"), new File(videoScreen)));
        }
        mPresenter.updateNews(NewsContract.UPDATE_VIDEO_NEWS, builder.build());
    }
}
