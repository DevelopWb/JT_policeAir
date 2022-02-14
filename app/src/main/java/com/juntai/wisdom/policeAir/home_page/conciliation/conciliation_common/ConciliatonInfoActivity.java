package com.juntai.wisdom.policeAir.home_page.conciliation.conciliation_common;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.policeAir.bean.conciliation.ConciliationInfoBean;
import com.juntai.wisdom.policeAir.bean.conciliation.ConciliationPeopleBean;
import com.juntai.wisdom.policeAir.home_page.conciliation.conciliation.ConciliationContract;
import com.juntai.wisdom.policeAir.home_page.conciliation.conciliation.ConciliationPresent;
import com.juntai.wisdom.policeAir.home_page.conciliation.conciliation.JoinRoomActivity;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.juntai.wisdom.video.player.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 调解详情
 * @aouther ZhangZhenlong
 * @date 2020-7-18
 */
public class ConciliatonInfoActivity extends BaseMvpActivity<ConciliationPresent> implements
        ConciliationContract.IConciliationView, View.OnClickListener,
        SelectPhotosFragment.OnPhotoItemClick {
    ConciliationInfoBean conciliationInfoBean;
    int conciliationId;
    /**
     * 请填写申请人姓名
     */
    private TextView mNameEt;
    /**
     * 请填写申请人电话
     */
    private TextView mTelEt;
    /**
     * 请填写被申请人姓名
     */
    private TextView mNameEt2;
    /**
     * 请填写被申请人电话
     */
    private TextView mTelEt2;
    /**
     * 请简要描述您的调解，比如描述事发时间、地点、事件经过。
     */
    private TextView mDescriptionTv;
    private RecyclerView mLawyerRecyclerView;
    /**
     * 确认
     */
    private TextView mOkTv;
    private boolean hasVideo = false;
    /**
     * 证据材料
     */
    private TextView zhengJuTagTv;

    SelectPhotosFragment selectPhotosFragment;
    ConciliationPeopleAdapter conciliationPeopleAdapter;
    List<ConciliationPeopleBean> people = new ArrayList<>();//第三方调解人员
    private ArrayList<String> photos = new ArrayList<>();
    private TextView mTypeTv;
    private TextView mChildTypeTv;
    private TextView mUnitNameTv;

    @Override
    protected ConciliationPresent createPresenter() {
        return new ConciliationPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_conciliaton_info;
    }

    @Override
    public void initView() {
        setTitleName("调解详情");
        conciliationId = getIntent().getIntExtra("id", 0);
        mNameEt = (TextView) findViewById(R.id.name_et);
        mTelEt = (TextView) findViewById(R.id.tel_et);
        mNameEt2 = (TextView) findViewById(R.id.name_et_2);
        mTelEt2 = (TextView) findViewById(R.id.tel_et_2);
        mDescriptionTv = (TextView) findViewById(R.id.description_tv);
        mLawyerRecyclerView = (RecyclerView) findViewById(R.id.lawyer_recycler_view);
        mOkTv = (TextView) findViewById(R.id.ok_tv);
        mOkTv.setOnClickListener(this);
        zhengJuTagTv = findViewById(R.id.zhengju_tag_tv);
        mTypeTv = (TextView) findViewById(R.id.type_tv);
        mChildTypeTv = (TextView) findViewById(R.id.child_type_tv);
        mUnitNameTv = (TextView) findViewById(R.id.unit_name_tv);

        conciliationPeopleAdapter = new ConciliationPeopleAdapter(R.layout.item_conciliation_people, people);
        mLawyerRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mLawyerRecyclerView.setAdapter(conciliationPeopleAdapter);
        conciliationPeopleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //
            }
        });
        selectPhotosFragment = (SelectPhotosFragment) getSupportFragmentManager().findFragmentById(R.id.photo_fg);
        selectPhotosFragment.setSpanCount(3).setPhotoDelateable(false);
    }

    @Override
    public void initData() {
        mPresenter.getConciliationInfo(conciliationId, ConciliationContract.GET_CONCILIATION_INFO);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        conciliationInfoBean = (ConciliationInfoBean) o;
        if (conciliationInfoBean.getData() != null) {
            mNameEt.setText(conciliationInfoBean.getData().getApplicantName());
            mTelEt.setText(conciliationInfoBean.getData().getApplicantPhone());
            mNameEt2.setText(conciliationInfoBean.getData().getRespondentName());
            mTelEt2.setText(conciliationInfoBean.getData().getRespondentPhone());
            mDescriptionTv.setText(conciliationInfoBean.getData().getIntroduction());
            mTypeTv.setText(conciliationInfoBean.getData().getBigName());
            mChildTypeTv.setText(conciliationInfoBean.getData().getSmallName());
            mUnitNameTv.setText(conciliationInfoBean.getData().getUnitName());
            conciliationPeopleAdapter.getData().clear();
            conciliationPeopleAdapter.addData(new ConciliationPeopleBean("调解警官",
                    conciliationInfoBean.getData().getPoliceHeadPortrait(),
                    conciliationInfoBean.getData().getPoliceName()));
            conciliationPeopleAdapter.addData(new ConciliationPeopleBean("律师援助",
                    conciliationInfoBean.getData().getLawyerHeadPortrait(),
                    conciliationInfoBean.getData().getLawyerName()));
            conciliationPeopleAdapter.addData(new ConciliationPeopleBean("人民调解员",
                    conciliationInfoBean.getData().getPeopleHeadPortrait(),
                    conciliationInfoBean.getData().getPeopleName()));
            List<String> images = new ArrayList<>();
            photos.clear();
            if (StringTools.isStringValueOk(conciliationInfoBean.getData().getVideoUrl())) {
                hasVideo = true;
                images.add(conciliationInfoBean.getData().getVideoUrl());
            }
            for (ConciliationInfoBean.DataBean.EvidenceBean evidenceBean : conciliationInfoBean.getData().getEvidence()) {
                if (StringTools.isStringValueOk(evidenceBean.getFileUrl())) {
                    photos.add(evidenceBean.getFileUrl());
                    images.add(evidenceBean.getFileUrl());
                }
            }
            selectPhotosFragment.setMaxCount(images.size());
            selectPhotosFragment.setIcons(images);
            if (images.size() == 0) {
                zhengJuTagTv.setVisibility(View.GONE);
            } else {
                zhengJuTagTv.setVisibility(View.VISIBLE);
            }
        } else {
            ToastUtils.warning(this, "数据已删除！");
            onBackPressed();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ok_tv:
                //进入调解室
                startActivity(new Intent(mContext, JoinRoomActivity.class).putExtra("roomId", "")
                        .putExtra("userName", MyApp.getUser().getData().getRealName()));
                break;
        }
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {
        String playPath = (String) adapter.getData().get(position);
        //播放视频
        if (StringTools.isStringValueOk(playPath)) {
            Intent intent = new Intent(mContext, PlayerActivity.class);
            intent.putExtra(PlayerActivity.VIDEO_PATH, playPath);
            startActivity(intent);
        }

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {
        //查看图片
        startActivity(new Intent(mContext, ImageZoomActivity.class)
                .putExtra("paths", photos)
                .putExtra("item", position));
    }
}
