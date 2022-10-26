package com.example.appbase.base.selectPics;


import com.example.appbase.R;

/**
 * @Author: tobato
 * @Description: 作用描述  选择大图 一行一个那种
 * @CreateDate: 2022/6/9 16:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/6/9 16:55
 */
public class SelectBigPicFragment  extends BaseSelectPhotosFragment{
    @Override
    protected ShowSelectedPicsAdapter getPicAdapter() {
        return new ShowSelectedPicsAdapter(R.layout.sell_show_selected_pic_item_big,true);
    }

    @Override
    protected void lazyloadGone() {

    }
}
