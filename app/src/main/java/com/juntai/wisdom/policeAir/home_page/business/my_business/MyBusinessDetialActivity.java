package com.juntai.wisdom.policeAir.home_page.business.my_business;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.business.MyBusinessDetailBean;
import com.juntai.wisdom.policeAir.home_page.business.BaseBusinessActivity;
import com.juntai.wisdom.policeAir.home_page.business.BusinessContract;
import com.juntai.wisdom.video.img.ImageZoomActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 我的业务详情
 * @date 2020/5/22 15:17
 */

public class MyBusinessDetialActivity extends BaseBusinessActivity implements BusinessContract.IBusinessView, View.OnClickListener {

    private MyBusinessDetailAdapter adapter;
    private TextView keyValue;

    @Override
    public void initData() {
        mPresenter.businessDetail(mPresenter.getPublishMultipartBody().addFormDataPart("businessId", String.valueOf(businessId)).build(), "");
    }

    @Override
    public void initView() {
        adapter = new MyBusinessDetailAdapter(R.layout.business_need_info_item);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyBusinessDetailBean.DataBean.PictureBean dateBean = (MyBusinessDetailBean.DataBean.PictureBean) adapter.getData().get(position);
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(dateBean.getPictureUrl());
                switch (view.getId()) {
                    case R.id.info_img_new_iv:
                        startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", arrayList).putExtra("item", 0));
                        break;
                    default:
                        break;
                }
            }
        });
        super.initView();
    }

    /**
     * 配置尾布局
     */
    private View adapterFootLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.business_detail_foot_layout, null);
        TextView keyTv = view.findViewById(R.id.item_text_list_left);
        keyTv.setText("拒绝原因");
        keyValue = view.findViewById(R.id.item_text_list_right);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    protected BaseQuickAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        MyBusinessDetailBean myBusinessDetailBean = (MyBusinessDetailBean) o;
        if (myBusinessDetailBean != null) {
            MyBusinessDetailBean.DataBean dataBean = myBusinessDetailBean.getData();
            //审批状态（0：审核中）（1：审核通过）（2：审核失败）
            int status = dataBean.getStatus();
            if (2 == status) {
               adapter.setFooterView(adapterFootLayout());
                keyValue.setText(dataBean.getAuditOpinion());
            }
            List<MyBusinessDetailBean.DataBean.PictureBean> pictureBeans = dataBean.getPicture();
            if (pictureBeans != null && pictureBeans.size() > 0) {
                adapter.setNewData(pictureBeans);
                mNeedInfoTv.setVisibility(View.VISIBLE);
            } else {
                mNeedInfoTv.setVisibility(View.INVISIBLE);
            }
        }
    }
}
