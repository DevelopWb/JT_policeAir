package com.juntai.wisdom.policeAir.home_page.conciliation.conciliation_publish;

import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.policeAir.R;

/**
 * @description 申请成功
 * @aouther ZhangZhenlong
 * @date 2020-7-18
 */
public class SucessResultActivity extends BaseActivity {

    private ImageView mItemIv;
    /**
     * 申请成功
     */
    private TextView mItemName;
    /**
     * 我们会在1至3个工作日审核您的申请信息。您可以到 个人中心—我的调解申请列表中查看结果。
     */
    private TextView mItemInfo;

    @Override
    public int getLayoutView() {
        return R.layout.activity_sucess_result;
    }

    @Override
    public void initView() {
        setTitleName("");
        mItemIv = (ImageView) findViewById(R.id.item_iv);
        mItemName = (TextView) findViewById(R.id.item_name);
        mItemInfo = (TextView) findViewById(R.id.item_info);
    }

    @Override
    public void initData() {

    }
}
