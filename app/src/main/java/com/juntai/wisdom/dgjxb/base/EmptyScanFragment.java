package com.juntai.wisdom.dgjxb.base;

import com.juntai.wisdom.basecomponent.base.BaseLazyFragment;
import com.juntai.wisdom.dgjxb.R;

/**
 * 扫一扫的空fragment
 * @aouther Ma
 * @date 2019/3/17
 */
public class EmptyScanFragment extends BaseLazyFragment {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void lazyLoad() {
    }
}
