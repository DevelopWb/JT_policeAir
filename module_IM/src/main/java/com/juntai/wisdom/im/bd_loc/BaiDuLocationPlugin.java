package com.juntai.wisdom.im.bd_loc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.im.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.LocationMessage;

/**
 * @aouther Ma
 * @date 2019/4/7
 */
public class BaiDuLocationPlugin implements IPluginModule {
    private Conversation.ConversationType conversationType;
    private String targetId;
    @Override
    public Drawable obtainDrawable(Context context) {
        //设置插件 Plugin 图标
        return context.getResources().getDrawable(R.drawable.rc_ext_plugin_location_selector);
    }
    @Override
    public String obtainTitle(Context context) {
        //设置插件 Plugin 展示文字
        return "位置";
    }
    @Override
    public void onClick(final Fragment currentFragment, RongExtension extension) {
        //示例获取 会话类型、targetId、Context,此处可根据产品需求自定义逻辑，如:开启新的 Activity 等。
        conversationType = extension.getConversationType();
        targetId = extension.getTargetId();
        //只有通过 extension 中的 startActivityForPluginResult 才会返回到本类中的 onActivityResult
        extension.startActivityForPluginResult(new Intent()
                .setAction(ActionConfig.ACTION_LOCATION_SELTION)
                .putExtra("isIM",true),5, this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            double latitude = data.getDoubleExtra("latitude",0.00);
            double longitude = data.getDoubleExtra("longitude",0.00);
            String address = data.getStringExtra("address");
            String uri = data.getStringExtra("locuri");
            LocationMessage locationMessage = LocationMessage.obtain(latitude,longitude,address, Uri.parse(uri));
            RongIM.getInstance().sendLocationMessage(Message.obtain(targetId, conversationType, locationMessage), null, null, null);
        }
    }
}
