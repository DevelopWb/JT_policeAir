package com.example.appbase.base;


import android.text.TextUtils;

import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.mvp.IPresenter;

/**
 * @aouther tobato
 * @description 描述  app的fragment的基类
 * @date 2020/7/18 16:43
 */
public abstract class BaseAppModuleFragment<P extends IPresenter> extends BaseMvpFragment<P> {


    /**
     * 获取activity
     *
     * @return
     */
    public BaseAppModuleActivity getBaseAppActivity() {
        return (BaseAppModuleActivity) getActivity();
    }
    /**
     * 获取文件名称  带后缀   图片的缓存需要
     * @return
     */
    public String getSavedFileName(String  content){
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/")+1,content.length());
        }
        return content;
    }

    /**
     * 获取文件名称  后缀
     * @return
     */
    public String getSavedFileNameWithoutSuffix(String content){
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/")+1,content.lastIndexOf("."));
        }
        return content;
    }
    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }
}
