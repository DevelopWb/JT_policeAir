package com.juntai.wisdom.im.ry;

import com.juntai.wisdom.im.bd_loc.BaiDuLocationPlugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.callkit.AudioPlugin;
import io.rong.callkit.VideoPlugin;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.RongExtension;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * @aouther Ma
 * @date 2019/4/6
 */
public class MyExtensionModule extends DefaultExtensionModule {

    @Override
    public void onInit(String appKey) {
        super.onInit(appKey);
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
    }

    @Override
    public void onConnect(String token) {
        super.onConnect(token);
    }

    @Override
    public void onAttachedToExtension(RongExtension extension) {
        super.onAttachedToExtension(extension);
    }

    @Override
    public void onDetachedFromExtension() {
        super.onDetachedFromExtension();
    }

    @Override
    public void onReceivedMessage(Message message) {
        super.onReceivedMessage(message);
    }

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  new ArrayList<>();
        IPluginModule filePlugin = new FilePlugin();
        IPluginModule imagePlugin = new ImagePlugin();
        IPluginModule videoPlugin = new VideoPlugin();
        IPluginModule audioPlugin = new AudioPlugin();
        BaiDuLocationPlugin locationPlugin = new BaiDuLocationPlugin();
        pluginModules.add(imagePlugin);
        pluginModules.add(filePlugin);
        pluginModules.add(audioPlugin);
        pluginModules.add(videoPlugin);
        pluginModules.add(locationPlugin);
        return pluginModules;
//        if (conversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE)) {
//            List<IPluginModule> pluginModuleList = new ArrayList<>();
//            IPluginModule image = new ImagePlugin();
//            IPluginModule locationPlugin = new DefaultLocationPlugin();
//            pluginModuleList.add(image);
//            pluginModuleList.add(locationPlugin);
//            try {
//                String clsName = "com.iflytek.cloud.SpeechUtility";
//                Class<?> cls = Class.forName(clsName);
//                if (cls != null) {
//                    cls = Class.forName("io.rong.recognizer.RecognizePlugin");
//                    Constructor<?> constructor = cls.getConstructor();
//                    IPluginModule recognizer = (IPluginModule) constructor.newInstance();
//                    pluginModuleList.add(recognizer);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return pluginModuleList;
//        } else if (conversationType == Conversation.ConversationType.CUSTOMER_SERVICE) {
//            List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
//            Log.e("fffffffffff",pluginModules.size()+"???");
//            if (conversationType == Conversation.ConversationType.CUSTOMER_SERVICE) {
//                if (pluginModules != null) {
//                    for (IPluginModule module : pluginModules) {
//                        if (module instanceof FilePlugin) {
//                            pluginModules.remove(module);
//                            break;
//                        }
//                    }
//                }
//            }
//            return pluginModules;
//        } else {
//            return super.getPluginModules(conversationType);
//        }
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
