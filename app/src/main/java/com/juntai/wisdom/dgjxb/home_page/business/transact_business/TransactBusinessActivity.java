package com.juntai.wisdom.dgjxb.home_page.business.transact_business;

import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.business.BusinessNeedInfoBean;
import com.juntai.wisdom.dgjxb.home_page.business.BaseBusinessActivity;
import com.juntai.wisdom.dgjxb.home_page.business.BusinessContract;
import com.juntai.wisdom.video.img.ImageZoomActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述  办理业务
 * @date 2020/5/21 9:33
 */
public class TransactBusinessActivity extends BaseBusinessActivity implements BusinessContract.IBusinessView,
        View.OnClickListener {
    private TextView mCommitTv;
    private ArrayMap<String, String> materials = new ArrayMap<>();
    private List<BusinessNeedInfoBean.DataBean> arrays;
    private BusinessNeedDataAdapter needDataAdapter;
    protected ImageView currentImageView;
    protected String currentKey;//当前材料的key

    @Override
    protected BaseQuickAdapter getAdapter() {
        return needDataAdapter;
    }


    @Override
    public void initView() {
        needDataAdapter = new BusinessNeedDataAdapter(R.layout.business_need_info_item);
        needDataAdapter.setFooterView(adapterFootLayout());
        needDataAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BusinessNeedInfoBean.DataBean dataBean =
                        (BusinessNeedInfoBean.DataBean) adapter.getData().get(position);
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(dataBean.getExamplePicture());
                currentKey = ((BusinessNeedInfoBean.DataBean) adapter.getData().get(position)).getName();
                currentImageView = (ImageView) adapter.getViewByPosition(mRecyclerview,
                        position + adapter.getHeaderLayoutCount(), R.id.info_img_new_iv);
                switch (view.getId()) {
                    case R.id.info_img_old_iv:
                        startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", arrayList).putExtra("item", 0));

                        break;
                    case R.id.info_img_new_iv:
                        choseImage(0, TransactBusinessActivity.this, 1);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_foot_view, null);
        mCommitTv = view.findViewById(R.id.foot_commit_tv);
        mCommitTv.setOnClickListener(this);
        return view;
    }


    @Override
    public void initData() {
        mPresenter.businessDataNeeded(businessId, "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //确认
            case R.id.foot_commit_tv:
                if (materials.size() < arrays.size()) {
                    ToastUtils.toast(mContext, "请选择所需材料的图片");
                    return;
                }
                MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
                builder.addFormDataPart("typeId", String.valueOf(businessId))
                        .addFormDataPart("realName", MyApp.getUser().getData().getRealName())
                        .addFormDataPart("IDNumber", MyApp.getUser().getData().getIdNumber())
                        .addFormDataPart("phoneNumber", MyApp.getUser().getData().getAccount());

                for (Map.Entry<String, String> entry : materials.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    builder.addFormDataPart("materialsName", key)
                            .addFormDataPart("file", "file", RequestBody.create(MediaType.parse("file"), new File(value)));
                }
                mPresenter.creatBusiness(builder.build(), BusinessContract.CREAT_BUSINESS);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case BusinessContract.CREAT_BUSINESS:
                startActivity(new Intent(mContext, SubmitSuccessActivity.class));
                finish();
                break;
            default:
                BusinessNeedInfoBean needInfoBean = (BusinessNeedInfoBean) o;
                if (needInfoBean != null) {
                    arrays = needInfoBean.getData();
                    if (arrays != null && arrays.size() > 0) {
                        needDataAdapter.setNewData(arrays);
                        mNeedInfoTv.setVisibility(View.VISIBLE);
                    } else {
                        mNeedInfoTv.setVisibility(View.INVISIBLE);
                    }
                }
                break;
        }
        super.onSuccess(tag, o);
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            ImageLoadUtil.loadImage(mContext, icons.get(0), currentImageView);
            materials.put(currentKey, path);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        materials.clear();
        materials = null;
    }
}
