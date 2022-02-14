package com.juntai.wisdom.policeAir.entrance.sendcode;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.base.BaseSelectPicsActivity;
import com.juntai.wisdom.policeAir.entrance.regist.RegistContract;

/**
 * @aouther tobato 短信验证码接收
 * @description 描述
 * @date 2020/3/25 8:46
 */
public abstract class SmsCheckCodeActivity<P extends BasePresenter> extends BaseSelectPicsActivity<P> {
    @Override
    public void onSuccess(String tag, Object o) {
        if (tag.equals(RegistContract.GET_MS_CODE)){
            ToastUtils.success(SmsCheckCodeActivity.this, "发送成功");
            initGetTestCodeButtonStatusStart();
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        if (tag.equals(RegistContract.GET_MS_CODE)){
            initGetTestCodeButtonStatusStop();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 更改获取验证码按钮得状态
     */
    protected abstract void initGetTestCodeButtonStatusStart();

    protected abstract void initGetTestCodeButtonStatusStop();
}
