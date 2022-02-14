package com.juntai.wisdom.policeAir.home_page.site_manager.site_add;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.PickerManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.BaseSelectPicsActivity;
import com.juntai.wisdom.policeAir.bean.CityBean;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.bean.site.CustomerSourceBean;
import com.juntai.wisdom.policeAir.bean.site.SiteTypeBean;
import com.juntai.wisdom.policeAir.bean.weather.PoliceGriddingBean;
import com.juntai.wisdom.policeAir.entrance.regist.RegistContract;
import com.juntai.wisdom.policeAir.entrance.regist.RegistPresent;
import com.juntai.wisdom.policeAir.home_page.PublishContract;
import com.juntai.wisdom.policeAir.home_page.PublishPresent;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerContract;
import com.juntai.wisdom.policeAir.home_page.site_manager.SiteManagerPresent;
import com.juntai.wisdom.policeAir.home_page.weather.SelectLocation;
import com.juntai.wisdom.policeAir.utils.StringTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 新增场所管理
 * @aouther ZhangZhenlong
 * @date 2020-7-11
 */
public class AddNewSiteActivity extends BaseSelectPicsActivity<SiteManagerPresent> implements SiteManagerContract.ISiteManagerView,
        View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    /**
     * 请输入单位名称
     */
    private EditText mItemSiteNameEt;
    /**
     * 请选择单位类型
     */
    private TextView mItemSiteTypeTv;

    /**
     * 请输入经营品类
     */
    private EditText mItemRunTypeEt;
    /**
     * 请选择区域
     */
    private TextView mItemXiaquTv;
    /**
     * 网格
     */
    private TextView mItemGriddingTv;
    /**
     * 请选择地点
     */
    private TextView mItemAddressTv;
    /**
     * 请输入联系人姓名
     */
    private EditText mItemUserNameEt;
    /**
     * 男
     */
    private RadioButton mRadioNan;
    /**
     * 女
     */
    private RadioButton mRadioNv;
    private RadioGroup mRgSex;
    /**
     * 请输入联系人姓名
     */
    private EditText mItemUserPhoneEt;
    /**
     * 客户来源
     */
    private TextView mItemCustomerSourceTv;
    /**
     * 请输入备用联系人
     */
    private EditText mItemSpareUserEt;
    /**
     * 请输入联系姓名
     */
    private EditText mItemSparePhoneEt;
    /**
     * 单位简介
     */
    private EditText mJianjieEt;
    /**
     * 已输入0/500
     */
    private TextView mJianjieNumTv;
    /**
     * 备注信息
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/200
     */
    private TextView mDescriptionNumTv;
    private RecyclerView mItemPicsRv;
    AddPicsAdapter addPicsAdapter;
    List<TextListBean> pictures = new ArrayList<>();

    private ImageView mItemVideoPic;
    private ImageView mItemVideoTag;
    private ImageView mDeleteVedioIv;
    /**
     * 确认
     */
    private TextView mOkTv;
    private PublishPresent publishPresent;

    //地址
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat = 0.0;
    private Double locLon = 0.0;
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;
    //视频
    private String videoScreen;//视频封面
    private String videoPath;//视频地址

    private OptionsPickerView optionsPickerViewTypes;
    private OptionsPickerView optionsPickerViewGriddings;
    private OptionsPickerView optionsPickerViewCS;
    private int currentPosition;//当前图片位置
    private int maxPics;//最大图片数量

    private SiteTypeBean.DataBean siteTypeBean = null;//单位类型
    private CustomerSourceBean.DataBean customerSourceBean;//客户来源
    private PoliceGriddingBean.DataBean griddingBean;//网格

    CityBean.DataBean dataBeanPrivince;
    CityBean.DataBean dataBeanCity;
    CityBean.DataBean dataBeanTown;
    CityBean.DataBean dataBeanStreet;
    private int sex = 1;
    private RegistPresent registPresent;


    @Override
    protected SiteManagerPresent createPresenter() {
        return new SiteManagerPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_add_new_site;
    }

    @Override
    public void initView() {
        maxPics = 9;
        mItemSiteNameEt = (EditText) findViewById(R.id.item_site_name_et);
        mItemSiteTypeTv = (TextView) findViewById(R.id.item_site_type_tv);
        mItemSiteTypeTv.setOnClickListener(this);
        mItemRunTypeEt = (EditText) findViewById(R.id.item_run_type_et);
        mItemXiaquTv = (TextView) findViewById(R.id.item_xiaqu_tv);
        mItemXiaquTv.setOnClickListener(this);
        mItemAddressTv = (TextView) findViewById(R.id.item_address_tv);
        mItemAddressTv.setOnClickListener(this);
        mItemUserNameEt = (EditText) findViewById(R.id.item_user_name_et);
        mRadioNan = (RadioButton) findViewById(R.id.radio_nan);
        mRadioNv = (RadioButton) findViewById(R.id.radio_nv);
        mRgSex = (RadioGroup) findViewById(R.id.rgSex);
        mItemUserPhoneEt = (EditText) findViewById(R.id.item_user_phone_et);
        mItemSpareUserEt = (EditText) findViewById(R.id.item_spare_user_et);
        mItemSparePhoneEt = (EditText) findViewById(R.id.item_spare_phone_et);
        mJianjieEt = (EditText) findViewById(R.id.jianjie_et);
        mJianjieNumTv = (TextView) findViewById(R.id.jianjie_num_tv);
        mDescriptionEt = (EditText) findViewById(R.id.description_et);
        mDescriptionNumTv = (TextView) findViewById(R.id.description_num_tv);
        mItemPicsRv = (RecyclerView) findViewById(R.id.item_pics_rv);
        mItemVideoPic = (ImageView) findViewById(R.id.item_video_pic);
        mItemVideoPic.setOnClickListener(this);
        mItemVideoTag = (ImageView) findViewById(R.id.item_video_tag);
        mDeleteVedioIv = (ImageView) findViewById(R.id.delete_vedio_iv);
        mDeleteVedioIv.setOnClickListener(this);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        mItemCustomerSourceTv = findViewById(R.id.item_customer_source_tv);
        mItemCustomerSourceTv.setOnClickListener(this);
        mRgSex.setOnCheckedChangeListener(this);
        mItemGriddingTv = findViewById(R.id.item_gridding_tv);
        mItemGriddingTv.setOnClickListener(this);
        registPresent = new RegistPresent();
        registPresent.setCallBack(this);

        addPicsAdapter = new AddPicsAdapter(R.layout.item_add_site_pic, pictures);
        mItemPicsRv.setAdapter(addPicsAdapter);
        mItemPicsRv.setLayoutManager(new GridLayoutManager(mContext, 3));

        mDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mDescriptionNumTv.setText("已输入" + s.length() + "/200");
                }
            }
        });

        mJianjieEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mJianjieNumTv.setText("已输入" + s.length() + "/200");
                }
            }
        });

        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        registerReceiver(videoBroadcastReceiver, intentFilter);

        addPicsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextListBean dataBean = pictures.get(position);
                currentPosition = position;
                switch (view.getId()) {
                    case R.id.select_pic_icon_iv:
                        if (StringTools.isStringValueOk(dataBean.getRight())) {
                            return;
                        }
                        //拍照
                        choseImage(0, AddNewSiteActivity.this, 1);
                        break;
                    case R.id.delete_pushed_news_iv:
                        //删除图片
                        if (position < 5) {
                            dataBean.setRight("");
                        } else {
                            pictures.remove(position);
                            if (!pictures.get(pictures.size() - 1).getRight().equals("")) {
                                pictures.add(new TextListBean("更多", ""));
                            }
                        }
                        addPicsAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        setTitleName("添加场所");
        initViewRightDrawable(mItemAddressTv, R.mipmap.ic_push_location, 23, 23);
        initViewRightDrawable(mItemXiaquTv, R.mipmap.arrow_right, 23, 23);
        initViewRightDrawable(mItemSiteTypeTv, R.mipmap.arrow_right, 23, 23);
        initViewRightDrawable(mItemCustomerSourceTv, R.mipmap.arrow_right, 23, 23);
        initViewRightDrawable(mItemGriddingTv, R.mipmap.arrow_right, 23, 23);
        publishPresent = new PublishPresent();
        publishPresent.setCallBack(this);
        addPicsAdapter.addData(mPresenter.getPics());
        setDefaultData();
    }

    /**
     * 设置默认数据
     */
    public void setDefaultData() {
        bdLocation = LocateAndUpload.bdLocation;
        if (bdLocation != null) {
            locAddress = bdLocation.getAddrStr() != null ?
                    bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() : "";
            locLat = bdLocation.getLatitude();
            locLon = bdLocation.getLongitude();
        } else {
            locAddress = "";
            locLat = 0.0;
            locLon = 0.0;
        }
        mItemAddressTv.setText(locAddress);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case SiteManagerContract.GET_SITE_TYPE_LIST:
                SiteTypeBean typeBean = (SiteTypeBean) o;
                List<SiteTypeBean.DataBean> dataBeans = typeBean.getData();
                if (dataBeans != null && dataBeans.size() > 0) {
                    if (optionsPickerViewTypes == null) {
                        optionsPickerViewTypes = PickerManager.getInstance().getOptionPicker(mContext, "单位类型",
                                dataBeans, new PickerManager.OnOptionPickerSelectedListener() {
                                    @Override
                                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                        mItemSiteTypeTv.setText(dataBeans.get(options1).getName());
                                        siteTypeBean = dataBeans.get(options1);
                                    }
                                });
                    }
                    if (optionsPickerViewTypes != null && !optionsPickerViewTypes.isShowing()) {
                        optionsPickerViewTypes.show();
                    }
                }
                break;
            case SiteManagerContract.GET_CUSTOMER_SOURCE_LIST:
                CustomerSourceBean csBean = (CustomerSourceBean) o;
                if (csBean != null) {
                    List<CustomerSourceBean.DataBean> csBeanDatas = csBean.getData();
                    if (csBeanDatas != null && csBeanDatas.size() > 0) {
                        if (optionsPickerViewCS == null) {
                            optionsPickerViewCS = PickerManager.getInstance().getOptionPicker(mContext, "客户来源",
                                    csBeanDatas, new PickerManager.OnOptionPickerSelectedListener() {
                                        @Override
                                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                            mItemCustomerSourceTv.setText(csBeanDatas.get(options1).getName());
                                            customerSourceBean = csBeanDatas.get(options1);
                                        }
                                    });
                        }
                        if (optionsPickerViewCS != null && !optionsPickerViewCS.isShowing()) {
                            optionsPickerViewCS.show();
                        }
                    }
                }
                break;
            case RegistContract.GET_POLICE_GRIDDING:
                PoliceGriddingBean gridBean = (PoliceGriddingBean) o;
                if (gridBean != null) {
                    List<PoliceGriddingBean.DataBean> arrays = gridBean.getData();
                    if (arrays != null && arrays.size() > 0) {
                        if (optionsPickerViewGriddings == null) {
                            optionsPickerViewGriddings = PickerManager.getInstance().getOptionPicker(this, "选择责任网格",
                                    arrays, new PickerManager.OnOptionPickerSelectedListener() {
                                        @Override
                                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                            mItemGriddingTv.setText(arrays.get(options1).getName());
                                            griddingBean = arrays.get(options1);
                                        }
                                    });
                        }
                    }
                    if (optionsPickerViewGriddings != null && !optionsPickerViewGriddings.isShowing()) {
                        optionsPickerViewGriddings.show();
                    }
                }
                break;
            case SiteManagerContract.ADD_SITE_MANAGER:
                ToastUtils.success(mContext, getString(R.string.publish_success_tag));
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.item_site_type_tv://单位类型
                if (optionsPickerViewTypes != null && !optionsPickerViewTypes.isShowing()) {
                    optionsPickerViewTypes.show();
                } else {
                    mPresenter.getSiteTypes(SiteManagerContract.GET_SITE_TYPE_LIST,
                            mPresenter.getPublishMultipartBody().build());
                }
                break;
            case R.id.item_xiaqu_tv:
                //选择区域
                startActivityForResult(new Intent(mContext, SelectLocation.class).putExtra("type", 1),
                        SelectLocation.SELECT_LOCATION);
                break;
            case R.id.item_gridding_tv:
                //责任网格
                if (optionsPickerViewGriddings != null && !optionsPickerViewGriddings.isShowing()) {
                    optionsPickerViewGriddings.show();
                } else {
                    registPresent.getPoliceGridding(MyApp.getUser().getData().getDepartmentId(),
                            RegistContract.GET_POLICE_GRIDDING);
                }
                break;
            case R.id.item_customer_source_tv:
                //客户来源
                if (optionsPickerViewCS != null && !optionsPickerViewCS.isShowing()) {
                    optionsPickerViewCS.show();
                } else {
                    mPresenter.getCustomerSources(SiteManagerContract.GET_CUSTOMER_SOURCE_LIST,
                            mPresenter.getPublishMultipartBody().build());
                }
                break;
            case R.id.item_address_tv:
                //地址
                startActivityForResult(new Intent(mContext, LocationSeltionActivity.class),
                        PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.item_video_pic:
                //选择视频
                publishPresent.recordVideo(AddNewSiteActivity.this);
                break;
            case R.id.delete_vedio_iv:
                //删除录制得视频
                videoPath = null;
                mItemVideoTag.setVisibility(View.GONE);
                mDeleteVedioIv.setVisibility(View.GONE);
                ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, mItemVideoPic);
                break;
            case R.id.ok_tv:
                if (MyApp.isFastClick()) {
                    ToastUtils.warning(mContext, "点击过于频繁");
                    return;
                }
                // TODO: 2021/6/2   暂时全部改为非必填  王彬修改
                //                //发布
                if (!StringTools.isStringValueOk(getTextViewValue(mItemSiteNameEt))) {
                    ToastUtils.warning(mContext, "请输入单位名称");
                    return;
                }
                //                if (!StringTools.isStringValueOk(getTextViewValue(mItemRunTypeEt))) {
                //                    ToastUtils.warning(mContext, "请输入经营品类");
                //                    return;
                //                }
                //                if (siteTypeBean == null) {
                //                    ToastUtils.warning(mContext, "请输选择单位类型");
                //                    return;
                //                }
                                if (!StringTools.isStringValueOk(getTextViewValue(mItemXiaquTv))) {
                                    ToastUtils.warning(mContext, "请输选择所属区域");
                                    return;
                                }
                                if (griddingBean == null) {
                                    ToastUtils.warning(mContext, "请输选择所属网格");
                                    return;
                                }
                //                if (!StringTools.isStringValueOk(getTextViewValue(mItemAddressTv))) {
                //                    ToastUtils.warning(mContext, "请选择单位地址");
                //                    return;
                //                }
                                if (!StringTools.isStringValueOk(getTextViewValue(mItemUserNameEt))) {
                                    ToastUtils.warning(mContext, "请输入联系人姓名");
                                    return;
                                }
                                if (!StringTools.isStringValueOk(getTextViewValue(mItemUserPhoneEt))) {
                                    ToastUtils.warning(mContext, "请输入联系人电话");
                                    return;
                                }
                //                if (!SendCodeModel.isMobileNO(getTextViewValue(mItemUserPhoneEt))) {
                //                    ToastUtils.warning(mContext, "联系人手机号码格式不正确");
                //                    return;
                //                }
                //                if (StringTools.isStringValueOk(getTextViewValue(mItemSparePhoneEt)) &&
                //                !SendCodeModel.isMobileNO(getTextViewValue(mItemUserPhoneEt))) {
                //                    ToastUtils.warning(mContext, "备用手机号码格式不正确");
                //                    return;
                //                }
                //                if (customerSourceBean == null) {
                //                    ToastUtils.warning(mContext, "请选择客户来源");
                //                    return;
                //                }
                //                if (!StringTools.isStringValueOk(getTextViewValue(mJianjieEt))) {
                //                    ToastUtils.warning(mContext, "请输入单位简介");
                //                    return;
                //                }
                //
                                if (!checkPicSelectedStatus()) {
                                    return;
                                }
                //                if (!StringTools.isStringValueOk(videoPath)) {
                //                    ToastUtils.warning(mContext, "请选择视频");
                //                    return;
                //                }
                collateData();
                break;
        }
    }

    /**
     * 整理提交数据
     */
    private void collateData() {
        MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
        builder.addFormDataPart("userid", String.valueOf(MyApp.getUid()))
                .addFormDataPart("longitude", String.valueOf(locLon))
                .addFormDataPart("latitude", String.valueOf(locLat))
                .addFormDataPart("name", getTextViewValue(mItemSiteNameEt))
                .addFormDataPart("region", getTextViewValue(mItemXiaquTv))
                .addFormDataPart("provinceCode", dataBeanPrivince == null ? "" : dataBeanPrivince.getCityNum())
                .addFormDataPart("cityCode", dataBeanCity == null ? "" : dataBeanCity.getCityNum())
                .addFormDataPart("areaCode", dataBeanTown == null ? "" : dataBeanTown.getCityNum())
                .addFormDataPart("streetCode", dataBeanStreet == null ? "" : dataBeanStreet.getCityNum())
                .addFormDataPart("address", locAddress)
                .addFormDataPart("category", getTextViewValue(mItemRunTypeEt))
                .addFormDataPart("contactsname", getTextViewValue(mItemUserNameEt))
                .addFormDataPart("contactssex", String.valueOf(sex))
                .addFormDataPart("contactsphone", getTextViewValue(mItemUserPhoneEt))
                .addFormDataPart("standbycontacts", getTextViewValue(mItemSpareUserEt))
                .addFormDataPart("standbyphone", getTextViewValue(mItemSparePhoneEt))
                .addFormDataPart("synopsis", getTextViewValue(mJianjieEt))
                .addFormDataPart("remark", getTextViewValue(mDescriptionEt) + "")
                .addFormDataPart("appType", "1")
                .addFormDataPart("creater", MyApp.getUser().getData().getRealName())
                .addFormDataPart("departmentId", String.valueOf(MyApp.getUser().getData().getDepartmentId()));
        if (siteTypeBean != null) {
            builder.addFormDataPart("type", String.valueOf(siteTypeBean.getId()));
        }
        if (customerSourceBean != null) {
            builder.addFormDataPart("contactssource", String.valueOf(customerSourceBean.getId()));
        }
        if (griddingBean != null) {
            builder.addFormDataPart("grId", String.valueOf(griddingBean.getId()));
        }
        if (addPicsAdapter.getData().size() > 0) {//logoFile
            String path0 = addPicsAdapter.getData().get(0).getRight();
            if (StringTools.isStringValueOk(path0)) {
                builder.addFormDataPart("logoFile", "logoFile", RequestBody.create(MediaType.parse("file"),
                        new File(path0)));
            }
            for (int i = 1; i < addPicsAdapter.getData().size(); i++) {
                String path = addPicsAdapter.getData().get(i).getRight();
                if (StringTools.isStringValueOk(path)) {
                    builder.addFormDataPart("pictureFile", "pictureFile", RequestBody.create(MediaType.parse("file"),
                            new File(path)));
                }
            }
        }
        if (StringTools.isStringValueOk(videoPath)) {
            builder.addFormDataPart("videoFile", "videoFile", RequestBody.create(MediaType.parse("file"),
                    new File(videoPath)));
        }
        mPresenter.addSiteManager(SiteManagerContract.ADD_SITE_MANAGER, builder.build());
    }

    /**
     * 检测照片选择的状态
     *
     * @return
     */
    private boolean checkPicSelectedStatus() {
        List<TextListBean> arrays = addPicsAdapter.getData();
        if (arrays.size() < 1) {
            ToastUtils.toast(mContext, "最少需要传入门面照");
            return false;
        }
        boolean isOk = true;
        // : 2021/6/2 最少一张
        for (int i = 0; i < 1; i++) {
            TextListBean bean = arrays.get(i);
            String name = bean.getLeft();
            String path = bean.getRight();
            if ("".equals(path)) {
                ToastUtils.warning(mContext, String.format("%s%s%s", "请选择", name, "的图片"));
                isOk = false;
                break;
            }
        }
        return isOk;
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            if (currentPosition < 5) {
                String path = icons.get(0);
                List<TextListBean> arrays = addPicsAdapter.getData();
                for (int i = 0; i < arrays.size(); i++) {
                    TextListBean bean = arrays.get(i);
                    if (currentPosition == i) {
                        bean.setRight(path);
                        addPicsAdapter.notifyItemChanged(currentPosition);
                        break;
                    }
                }
            } else {
                pictures.remove(pictures.size() - 1);
                for (String path : icons) {
                    pictures.add(new TextListBean("", path));
                }
                if (pictures.size() < maxPics) {
                    pictures.add(new TextListBean("更多", ""));
                }
                addPicsAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (videoBroadcastReceiver != null) {
            unregisterReceiver(videoBroadcastReceiver);
        }
        publishPresent.setCallBack(null);
        registPresent.setCallBack(null);
        releasePickerView(optionsPickerViewTypes);
        releasePickerView(optionsPickerViewGriddings);
        releasePickerView(optionsPickerViewCS);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == RESULT_OK) {
            //地址选择
            locLat = data.getDoubleExtra("lat", 0.0);
            locLon = data.getDoubleExtra("lng", 0.0);
            locAddress = data.getStringExtra("address");
            mItemAddressTv.setText(locAddress);
            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
        } else if (requestCode == SelectLocation.SELECT_LOCATION && resultCode == SelectLocation.RESULT_OK) {
            dataBeanPrivince = (CityBean.DataBean) data.getSerializableExtra("privince");
            dataBeanCity = (CityBean.DataBean) data.getSerializableExtra("city");
            dataBeanTown = (CityBean.DataBean) data.getSerializableExtra("town");
            dataBeanStreet = (CityBean.DataBean) data.getSerializableExtra("street");
            if (dataBeanTown != null && dataBeanStreet != null) {
                mItemXiaquTv.setText(dataBeanPrivince.getName() + dataBeanCity.getName() + dataBeanTown.getName() + dataBeanStreet.getName());
            } else if (dataBeanTown == null) {
                mItemXiaquTv.setText(dataBeanPrivince.getName() + dataBeanCity.getName());
            } else if (dataBeanStreet == null) {
                mItemXiaquTv.setText(dataBeanPrivince.getName() + dataBeanCity.getName() + dataBeanTown.getName());
            }
        }
    }

    /**
     * 释放pickerview
     *
     * @param optionsPickerView
     */
    private void releasePickerView(OptionsPickerView optionsPickerView) {
        if (optionsPickerView != null) {
            if (optionsPickerView.isShowing()) {
                optionsPickerView.dismiss();
                optionsPickerView = null;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_nan:
                //男
                sex = 1;
                break;
            case R.id.radio_nv:
                //女
                sex = 2;
                break;
        }
    }

    /**
     * 视频录制成功回调广播
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            videoPath = intent.getStringExtra("videoUri");
            videoScreen = intent.getStringExtra("videoScreenshotUri");
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mItemVideoPic);
            mDeleteVedioIv.setVisibility(View.VISIBLE);
            mItemVideoTag.setVisibility(View.VISIBLE);
        }
    }
}
