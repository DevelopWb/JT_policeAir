package com.juntai.wisdom.policeAir.utils;

import android.content.Context;

import com.juntai.wisdom.policeAir.bean.history_track.MyObjectBox;

import io.objectbox.BoxStore;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/9/11 15:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/9/11 15:51
 */
public class ObjectBox {

    public ObjectBox() {
    }

    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get() { return boxStore; }
}
