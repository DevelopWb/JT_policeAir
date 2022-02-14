package com.juntai.wisdom.im;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.DestructionTag;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * 案件/任务/巡检  推送消息
 */
@DestructionTag
@MessageTag(value = "app:custom", flag = MessageTag.NONE)
public class CustomCaseMessage extends MessageContent {
    private static final String TAG = "CustomCaseMessage";
    private String content;//推送内容
    private Integer genre;//推送类型 1:案件处理推送,3巡检退回
    private Long id;//相关id,
    public static final Creator<CustomCaseMessage> CREATOR = new Creator<CustomCaseMessage>() {
        public CustomCaseMessage createFromParcel(Parcel source) {
            return new CustomCaseMessage(source);
        }

        public CustomCaseMessage[] newArray(int size) {
            return new CustomCaseMessage[size];
        }
    };

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", this.getEmotion(this.getContent()));
            jsonObj.put("genre", this.getGenre());
            jsonObj.put("id", this.getId());
            if (this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }

            if (this.getJsonMentionInfo() != null) {
                jsonObj.putOpt("mentionedInfo", this.getJsonMentionInfo());
            }

            jsonObj.put("isBurnAfterRead", this.isDestruct());
            jsonObj.put("burnDuration", this.getDestructTime());
        } catch (JSONException var4) {
            RLog.e("CustomCaseMessage", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            RLog.e("CustomCaseMessage", "UnsupportedEncodingException ", var3);
            return null;
        }
    }

    private String getEmotion(String content) {
        Pattern pattern = Pattern.compile("\\[/u([0-9A-Fa-f]+)\\]");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            int inthex = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, String.valueOf(Character.toChars(inthex)));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    protected CustomCaseMessage() {
    }

    public static CustomCaseMessage obtain(String text) {
        CustomCaseMessage model = new CustomCaseMessage();
        model.setContent(text);
        return model;
    }

    public CustomCaseMessage(byte[] data) {
        String jsonStr = null;

        try {
            if (data != null && data.length >= 40960) {
                RLog.e("CustomCaseMessage", "TextMessage length is larger than 40KB, length :" + data.length);
            }

            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            RLog.e("CustomCaseMessage", "UnsupportedEncodingException ", var5);
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content")) {
                this.setContent(jsonObj.optString("content"));
            }

            if (jsonObj.has("genre")) {
                this.setGenre(jsonObj.optInt("genre"));
            }

            if (jsonObj.has("id")) {
                this.setId(jsonObj.optLong("id"));
            }

            if (jsonObj.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }

            if (jsonObj.has("mentionedInfo")) {
                this.setMentionedInfo(this.parseJsonToMentionInfo(jsonObj.getJSONObject("mentionedInfo")));
            }

            if (jsonObj.has("isBurnAfterRead")) {
                this.setDestruct(jsonObj.getBoolean("isBurnAfterRead"));
            }

            if (jsonObj.has("burnDuration")) {
                this.setDestructTime(jsonObj.getLong("burnDuration"));
            }
        } catch (JSONException var4) {
            RLog.e("TextMessage", "JSONException " + var4.getMessage());
        }

    }

    public void setContent(String content) {
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getGenre());
        ParcelUtils.writeToParcel(dest, this.getId());
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
        ParcelUtils.writeToParcel(dest, this.isDestruct() ? 1 : 0);
        ParcelUtils.writeToParcel(dest, this.getDestructTime());
    }

    public CustomCaseMessage(Parcel in) {
        this.setGenre(ParcelUtils.readIntFromParcel(in));
        this.setId(ParcelUtils.readLongFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo)ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo)ParcelUtils.readFromParcel(in, MentionedInfo.class));
        this.setDestruct(ParcelUtils.readIntFromParcel(in) == 1);
        this.setDestructTime(ParcelUtils.readLongFromParcel(in));
    }

    @Override
    public String toString() {
        return "CustomCaseMessage{" +
                "content='" + content + '\'' +
                ", genre=" + genre +
                ", id=" + id +
                '}';
    }

    public CustomCaseMessage(String content) {
        this.setContent(content);
    }

    public String getContent() {
        return this.content;
    }

    public List<String> getSearchableWord() {
        List<String> words = new ArrayList();
        words.add(this.content);
        return words;
    }
}