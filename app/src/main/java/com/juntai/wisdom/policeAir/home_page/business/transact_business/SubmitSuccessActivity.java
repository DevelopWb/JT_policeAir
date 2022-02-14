package com.juntai.wisdom.policeAir.home_page.business.transact_business;

import android.view.View;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.utils.StringTools;

public class SubmitSuccessActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 提交成功！\n  我们需要人工审核, 最长1-3个工作日\n如遇紧急事情，请及时拨打110！
     */
    private TextView mVerifiedSuccessTagTv;
    /**
     * 确认
     */
    private TextView mVerifiedSucceedConfirmTv;

    @Override
    public int getLayoutView() {
        return R.layout.activity_submit_success;
    }

    @Override
    public void initView() {
        setTitleName("业务办理");
        mVerifiedSuccessTagTv = (TextView) findViewById(R.id.verified_success_tag_tv);
        String text = getString(R.string.business_submit_success);
        mVerifiedSuccessTagTv.setOnClickListener(this);
        StringTools.setTextPartColor(mVerifiedSuccessTagTv, text, text.lastIndexOf("个人中心"), text.lastIndexOf("中查看"), "#2C8FFC");
        mVerifiedSucceedConfirmTv = (TextView) findViewById(R.id.verified_succeed_confirm_tv);
        mVerifiedSucceedConfirmTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.verified_succeed_confirm_tv:
                finish();
                break;
            case R.id.verified_success_tag_tv:
                //  跳转到个人中心-业务办理
                break;
        }
    }
}
