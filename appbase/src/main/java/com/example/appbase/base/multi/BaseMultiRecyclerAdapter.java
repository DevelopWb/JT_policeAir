package com.example.appbase.base.multi;

import android.content.res.ColorStateList;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.example.appbase.base.selectPics.SelectPhotosFragment;
import com.example.appbase.base.web.BaseWebviewFragment;
import com.example.appbase.bean.BasePicVideoBean;
import com.example.appbase.bean.multiBean.ImportantTagBean;
import com.example.appbase.bean.multiBean.ItemFragmentBean;
import com.example.appbase.bean.multiBean.LocationBean;
import com.example.appbase.bean.multiBean.MultiNormalRecyclerviewBean;
import com.example.appbase.bean.multiBean.MultiPicBean;
import com.example.appbase.bean.multiBean.MultiRadioBean;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.example.appbase.bean.nong_fa_manager.CommoditySourceBean;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.MultipleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class BaseMultiRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    private boolean isDetail;
    private FragmentManager mFragmentManager;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseMultiRecyclerAdapter(List<MultipleItem> data, boolean isDetail, FragmentManager mFragmentManager) {
        super(data);
        addItemType(MultipleItem.ITEM_TITILE_SMALL, R.layout.multi_item_layout_type_title_small);
        addItemType(MultipleItem.ITEM_TITILE_BIG, R.layout.multi_item_layout_type_title_big);
        addItemType(MultipleItem.ITEM_EDIT, R.layout.multi_item_layout_type_edit);
        addItemType(MultipleItem.ITEM_PIC, R.layout.multi_item_pic);
        addItemType(MultipleItem.ITEM_RADIO, R.layout.multi_item_layout_type_radio);
        addItemType(MultipleItem.ITEM_NORMAL_RECYCLEVIEW, R.layout.multi_item_layout_type_recyclerview);
        addItemType(MultipleItem.ITEM_SELECT, R.layout.multi_item_layout_type_select);
        addItemType(MultipleItem.ITEM_FRAGMENT, R.layout.multi_item_pic_fragment);
        addItemType(MultipleItem.ITEM_RICH_TEXT, R.layout.multi_item_rich_text);

        this.isDetail = isDetail;
        this.mFragmentManager = mFragmentManager;

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

        switch (item.getItemType()) {
            case MultipleItem.ITEM_RICH_TEXT:
                String richText = (String) item.getObject();
                BaseWebviewFragment webviewFragment = (BaseWebviewFragment) mFragmentManager.findFragmentById(R.id.base_webview_fg);
                webviewFragment.setWebData(richText);
                break;
            case MultipleItem.ITEM_TITILE_BIG:
                helper.setText(R.id.item_big_title_tv, (String) item.getObject());
                break;
            case MultipleItem.ITEM_RADIO:
                MultiRadioBean radioBean = (MultiRadioBean) item.getObject();
                RadioGroup radioGroup = helper.getView(R.id.item_radio_g);
                if (isDetail) {
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        radioGroup.getChildAt(i).setEnabled(false);
                    }
                } else {
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        radioGroup.getChildAt(i).setEnabled(true);
                    }
                }
                radioGroup.setTag(radioBean);
                RadioButton radioButton0 = helper.getView(R.id.radio_zero_rb);
                RadioButton radioButton1 = helper.getView(R.id.radio_first_rb);
                RadioButton radioButton2 = helper.getView(R.id.radio_second_rb);
                RadioButton radioButton3 = helper.getView(R.id.radio_third_rb);
                String[] values = radioBean.getValues();
                radioButton2.setVisibility(View.GONE);
                radioButton3.setVisibility(View.GONE);
                if (values != null) {
                    if (values.length > 1) {
                        radioButton0.setText(values[0]);
                        radioButton1.setText(values[1]);
                        if (values.length == 3) {
                            radioButton2.setVisibility(View.VISIBLE);
                            radioButton2.setText(values[2]);
                        }
                        if (values.length == 4) {
                            radioButton2.setVisibility(View.VISIBLE);
                            radioButton2.setText(values[2]);
                            radioButton3.setVisibility(View.VISIBLE);
                            radioButton3.setText(values[3]);
                        }
                    }

                } else {
                    radioButton0.setText("是");
                    radioButton1.setText("否");
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        if (radioCheckedCallBack != null) {
//                            radioCheckedCallBack.radioChecked(group, checkedId);
//                        }
                        MultiRadioBean radioBean = (MultiRadioBean) group.getTag();
                        if (checkedId == R.id.radio_zero_rb) {
                            radioBean.setDefaultSelectedIndex(0);
                        } else if (checkedId == R.id.radio_first_rb) {
                            radioBean.setDefaultSelectedIndex(1);
                        } else if (checkedId == R.id.radio_second_rb) {
                            radioBean.setDefaultSelectedIndex(2);
                        } else if (checkedId == R.id.radio_third_rb) {
                            radioBean.setDefaultSelectedIndex(3);
                        }
                    }
                });
                int defaultIndex = radioBean.getDefaultSelectedIndex();

                switch (defaultIndex) {
                    case 0:
                        radioButton0.setChecked(true);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        break;
                    case 1:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(true);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        break;
                    case 2:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(true);
                        radioButton3.setChecked(false);
                        break;
                    case 3:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(true);
                        break;
                    default:
                        radioButton0.setChecked(false);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        break;
                }

                break;
            case MultipleItem.ITEM_PIC:
                MultiPicBean businessPicBean = (MultiPicBean) item.getObject();
                String picPath = businessPicBean.getPicPath();
                String name = businessPicBean.getPicName();
                int index = businessPicBean.getPicNameIndex();
                if (index > 0) {
                    helper.setText(R.id.form_pic_title_tv, String.format("%s%s%s", String.valueOf(index), ".",
                            name));
                } else {
                    helper.setText(R.id.form_pic_title_tv, name);
                }
                ImageView picIv = helper.getView(R.id.form_pic_src_iv);
                helper.setGone(R.id.pic_form_notice_tv, false);
                helper.addOnClickListener(R.id.form_pic_src_iv);
                if (!TextUtils.isEmpty(picPath)) {
                    ImageLoadUtil.loadImage(mContext, picPath, picIv);

                } else {
                    ImageLoadUtil.loadImage(mContext, 0, picIv);
                }
                break;
            case MultipleItem.ITEM_FRAGMENT:
            case MultipleItem.ITEM_FRAGMENT2:
                //上传材料时 多选照片
                ItemFragmentBean itemFragmentBean = (ItemFragmentBean) item.getObject();
                SelectPhotosFragment fragment = null;
                fragment = (SelectPhotosFragment) mFragmentManager.findFragmentById(R.id.photo_fg);
//                switch (itemFragmentBean.getKey()) {
//                    case HomePageContract.COMMODITY_PRIMARY_PIC:
//
//
//                        break;
//                    case HomePageContract.COMMODITY_BANNER_PICS:
//                        fragment = (SelectPhotosFragment) mFragmentManager.findFragmentById(R.id.photo_fg2);
//
//                        break;
//                    case HomePageContract.COMMODITY_VIDEO:
//                        fragment = (SelectPhotosFragment) mFragmentManager.findFragmentById(R.id.photo_fg3);
//                        break;
//                    default:
//                        break;
//                }


                fragment.setPhotoDelateable(false)
                        .setShowTag(itemFragmentBean.isShowTag())
                        .setSpanCount(itemFragmentBean.getmSpanCount())
                        .setMaxCount(itemFragmentBean.getFragmentPics().size())
                        .setObject(itemFragmentBean);
                if (isDetail) {
                    if (!itemFragmentBean.getFragmentPics().isEmpty()) {
                        fragment.setIcons(itemFragmentBean.getFragmentPics());
                    }
                }

                SelectPhotosFragment finalFragment = fragment;
                fragment.setOnPicLoadSuccessCallBack(new SelectPhotosFragment.OnPicLoadSuccessCallBack() {
                    @Override
                    public void loadSuccess(List<BasePicVideoBean> icons) {

                        ItemFragmentBean itemFragmentBean = (ItemFragmentBean) finalFragment.getObject();
//                        if (onPicVideoLoadSuccessCallBack != null) {
//                            onPicVideoLoadSuccessCallBack.uploadPicVideo(itemFragmentBean, icons);
//                        }

                    }

                    @Override
                    public void delete(BasePicVideoBean basePicVideoBean) {

                    }
                });
                itemFragmentBean = (ItemFragmentBean) finalFragment.getObject();
                List<BasePicVideoBean> pics = itemFragmentBean.getFragmentPics();
                if (pics.size() > 0) {
                    fragment.setIcons(pics);
                }
                break;
            case MultipleItem.ITEM_TEXT:
//                BaseStringBean baseStringBean = (BaseStringBean) item.getObject();
////                String des = mContext.getString(R.string.test);
//                TextView textView = helper.getView(R.id.single_text_tv);
//                helper.setText(R.id.single_text_tv, baseStringBean.getContent());
//                textView.setGravity(baseStringBean.getGrivityType());
                break;
            case MultipleItem.ITEM_TITILE_SMALL:
                ImportantTagBean importantTagBean = (ImportantTagBean) item.getObject();
                helper.setGone(R.id.important_tag_tv, importantTagBean.isImportant());
                helper.setText(R.id.item_small_title_tv, importantTagBean.getTitleName());
                break;
            case MultipleItem.ITEM_EDIT:
                TextKeyValueBean textValueEditBean = (TextKeyValueBean) item.getObject();
                EditText editText = helper.getView(R.id.edit_value_et);
                if (textValueEditBean.isDetail()) {
                    editText.setClickable(false);
                    editText.setFocusable(false);
                    helper.setBackgroundRes(R.id.edit_value_et, R.drawable.sp_filled_gray_lighter);
                } else {
                    editText.setClickable(true);
                    editText.setFocusable(true);
                    helper.setBackgroundRes(R.id.edit_value_et, R.drawable.stroke_gray_square_bg);

                }
                int editType = textValueEditBean.getType();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) editText.getLayoutParams();
                if (0 == editType) {
                    lp.height = DisplayUtil.dp2px(mContext, 32);
                    editText.setGravity(Gravity.CENTER_VERTICAL);
                    editText.setSingleLine(true);
                } else {
                    lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    editText.setMinimumHeight(DisplayUtil.dp2px(mContext, 100));
                    editText.setGravity(Gravity.TOP);
                    editText.setSingleLine(false);
                }
                editText.setLayoutParams(lp);
                editText.setTag(textValueEditBean);
                addTextChange(editText);
//                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        TextKeyValueBean editBean = (TextKeyValueBean) editText.getTag();
//                        if (!hasFocus) {
//                            if (checkEdittextValueFormatCallBack != null) {
//                                checkEdittextValueFormatCallBack.checkEdittextValueFormat(editBean);
//                            }
//                        }
//                    }
//                });
                editText.setHint(textValueEditBean.getHint());
                editText.setText(textValueEditBean.getValue());
                String editKey = ((TextKeyValueBean) editText.getTag()).getKey();
                //正则
//                switch (editKey) {
//                    case HomePageContract.SHOP_TEL:
//                        //联系方式
//                        editText.setInputType(InputType.TYPE_CLASS_PHONE);
//                        break;
//                    default:
//                        //输入类型为普通文本
//                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
//                        break;
//                }
                break;
//            case MultipleItem.ITEM_EDIT2:
//                TextKeyValueBean textValueEditBean2 = (TextKeyValueBean) item.getObject();
//                EditText editText2 = helper.getView(R.id.value_et);
//                initEdittextFocuseStatus(editText2);
//                TextView textView2 = helper.getView(R.id.key_tv);
//                editText2.setTag(textValueEditBean2);
//                addTextChange(editText2);
//                editText2.setText(textValueEditBean2.getValue());
//                break;
            case MultipleItem.ITEM_SELECT:
                TextKeyValueBean textValueSelectBean = (TextKeyValueBean) item.getObject();
                TextView textViewTv = helper.getView(R.id.select_value_tv);
                String selectTextValue = textValueSelectBean.getValue();
                if (!textValueSelectBean.isDetail()) {
                    helper.addOnClickListener(R.id.select_value_tv);
                    helper.addOnClickListener(R.id.tool_pic_iv);
                    helper.setBackgroundRes(R.id.select_value_tv, R.drawable.stroke_gray_square_bg);
                    helper.setGone(R.id.select_arrow_right_iv, true);
                } else {
                    helper.setGone(R.id.select_arrow_right_iv, false);
                    helper.setBackgroundRes(R.id.select_value_tv, R.drawable.sp_filled_gray_lighter);
                }
                textViewTv.setTag(textValueSelectBean);
                textViewTv.setHint(textValueSelectBean.getHint());
                if (selectTextValue.contains("\\n")) {
                    selectTextValue = selectTextValue.replace("\\n", "\n");
                }
                textViewTv.setText(selectTextValue);
                if (isDetail) {
                    textViewTv.setText(TextUtils.isEmpty(selectTextValue) ? "暂无" : selectTextValue);
                    helper.setGone(R.id.select_arrow_right_iv, false);
                } else {
                    textViewTv.setText(selectTextValue);
                    helper.setGone(R.id.select_arrow_right_iv, true);
                }
                break;
            case MultipleItem.ITEM_NORMAL_RECYCLEVIEW:
                //recycleview
                MultiNormalRecyclerviewBean baseNormalRecyclerviewBean = (MultiNormalRecyclerviewBean) item.getObject();
                RecyclerView recyclerView = helper.getView(R.id.item_normal_rv);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL
                        , false) {
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }

                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                BaseQuickAdapter adapter = baseNormalRecyclerviewBean.getBaseQuickAdapter();
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

                switch (baseNormalRecyclerviewBean.getKey()) {
                    case MultiContact.COMMODITY_SOURCE:
                        CommodityManagerDetailBean.DataBean.TraceabilityBean traceabilityBean = (CommodityManagerDetailBean.DataBean.TraceabilityBean) baseNormalRecyclerviewBean.getObject();


                        List<CommoditySourceBean> arrays = new ArrayList<>();
                        List<String> pics1 = new ArrayList<>();
                        if (!TextUtils.isEmpty(traceabilityBean.getPhotoOne())) {
                            pics1.add(traceabilityBean.getPhotoOne());
                        }
                        if (!TextUtils.isEmpty(traceabilityBean.getPhotoTwo())) {
                            pics1.add(traceabilityBean.getPhotoTwo());

                        }
                        if (!TextUtils.isEmpty(traceabilityBean.getPhotoThree())) {
                            pics1.add(traceabilityBean.getPhotoThree());

                        }
                        CommoditySourceBean sourceBean1 = new CommoditySourceBean("商家证件", pics1);
                        arrays.add(sourceBean1);
                        List<String> pics2 = new ArrayList<>();

                        List<CommodityManagerDetailBean.DataBean.TraceabilityBean.TraceabilityFileBean> traceabilityFileBeans = traceabilityBean.getTraceabilityFile();
                        if (traceabilityFileBeans != null && !traceabilityFileBeans.isEmpty()) {
                            for (CommodityManagerDetailBean.DataBean.TraceabilityBean.TraceabilityFileBean traceabilityFileBean : traceabilityFileBeans) {
                                pics2.add(traceabilityFileBean.getFileUrl());
                            }
                        }
                        CommoditySourceBean sourceBean2 = new CommoditySourceBean("产品检测检疫等合格证件", pics2);
                        arrays.add(sourceBean2);
                        adapter.setNewData(arrays);
                        break;
                    default:
                        break;
                }
                break;
            case MultipleItem.ITEM_LOCATION:
                LocationBean locationBean = (LocationBean) item.getObject();
                if (!isDetail) {
                    helper.addOnClickListener(R.id.location_ll);
                    helper.setGone(R.id.location_iv, true);
                } else {
                    helper.setGone(R.id.location_iv, false);
                }
                if (!TextUtils.isEmpty(locationBean.getAddress())) {

                    helper.setText(R.id.location_tv, locationBean.getAddress());
                }

                break;
        }
    }

    private void addTextChange(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (editText.getId() == R.id.edit_value_et) {
                    TextKeyValueBean editBean = (TextKeyValueBean) editText.getTag();
                    editBean.setValue(s.toString().trim());
                }
            }
        });
    }

    // 改变底部图标颜色
    private void changeIconColor(ImageView imageView, int color) {
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color));
    }

    private void initEdittextFocuseStatus(EditText editText) {
        if (isDetail) {
            editText.setClickable(false);
            editText.setFocusable(false);
        } else {
            editText.setClickable(true);
            editText.setFocusable(true);
        }
    }

    /**
     * 控件失去焦点后  检测edittext控件输入内容的格式
     */
    interface OnCheckEdittextValueFormatCallBack {
        void checkEdittextValueFormat(TextKeyValueBean keyValueBean);
    }


    public interface OnPicVideoLoadSuccessCallBack {
        void uploadPicVideo(ItemFragmentBean itemFragmentBean, List<String> icons);
    }

}
