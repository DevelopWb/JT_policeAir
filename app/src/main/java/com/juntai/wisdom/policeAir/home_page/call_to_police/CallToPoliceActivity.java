package com.juntai.wisdom.policeAir.home_page.call_to_police;

import android.support.constraint.Group;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.PolicePickerBean;
import com.juntai.wisdom.policeAir.home_page.HomePageContract;
import com.juntai.wisdom.policeAir.home_page.HomePagePresent;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.im.ModuleIm_Init;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  一键报警
 * @date 2020/3/11 15:41
 */
public class CallToPoliceActivity extends BaseMvpActivity<HomePagePresent> implements HomePageContract.IHomePageView, View.OnClickListener {

    /**
     * 谎报警情,依法追责！
     */
    private TextView mCallPoliceNoticeTv;
    private Group mVerifiedInfoFilledG;
    /**
     * 提交成功！\n  我们需要人工审核, 最长1-3个工作日\n如遇紧急事情，请及时拨打110！
     */
    private TextView mVerifiedSuccessTagTv;
    /**
     * 确认
     */
    private TextView mVerifiedSucceedConfirmTv;
    private TextView mVerifiedSuccessTitleTv;
    private PolicePickerBean.DataBean dataBean;

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_call_to_police;
    }

    @Override
    public void initView() {
        setTitleName("一键报警");
        mVerifiedSuccessTitleTv = (TextView) findViewById(R.id.verified_success_title_tv);
        mVerifiedSuccessTitleTv.setText("警方提示");
        mVerifiedSuccessTagTv = (TextView) findViewById(R.id.verified_success_tag_tv);
        mVerifiedSuccessTagTv.setText(getResources().getString(R.string.call_police_warn));
        mVerifiedSucceedConfirmTv = (TextView) findViewById(R.id.verified_succeed_confirm_tv);
        mVerifiedSucceedConfirmTv.setOnClickListener(this);
        mVerifiedSuccessTagTv.setGravity(Gravity.START);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        PolicePickerBean policePickerBean = (PolicePickerBean) o;
        if (policePickerBean != null ) {
            List<PolicePickerBean.DataBean> list = policePickerBean.getData();
            if (list != null&&list.size()>0) {
                int index = (int) (Math.random() * list.size());
                dataBean = policePickerBean.getData().get(index);
                if (dataBean != null) {
                    if (StringTools.isStringValueOk(dataBean.getAccount()) && StringTools.isStringValueOk(dataBean.getNickname())) {
                        ModuleIm_Init.callVideo(mContext, dataBean.getAccount());
                    }
                }
            }else {
                ToastUtils.toast(mContext,"接警平台忙，请稍后再试");
            }
        }else {
            ToastUtils.toast(mContext,"接警平台忙，请稍后再试");
        }
    }

    @Override
    public void onError(String tag, Object o) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.verified_succeed_confirm_tv:
                mPresenter.getPolicePickerInfo();
                break;
        }
    }
}
