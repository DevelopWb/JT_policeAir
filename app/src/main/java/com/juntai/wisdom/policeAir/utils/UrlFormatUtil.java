package com.juntai.wisdom.policeAir.utils;

import com.juntai.wisdom.policeAir.AppHttpPath;

/**
 * author:wong
 * Date: 2019/3/27
 * Description:
 */
public class UrlFormatUtil {
    /**
     * 车辆直播流
     *
     * @param path
     * @return
     */
    public static String getCarStream(String path) {
        String url = AppHttpPath.CAR_STREAM +  path;
        return url;
    }

    /**
     * 缩略图url拼接（巡检）
     *
     * @param pathId
     * @return
     */
    public static String getInspectionThumImg(int pathId) {
        return String.format("%s%s%s", AppHttpPath.INSPECTION_THUMBNAI_IMAGE, "?id=", pathId);
    }

    /**
     * 原图url拼接（巡检）
     *
     * @param pathId
     * @return
     */
    public static String getInspectionOriginalImg(int pathId) {
        return String.format("%s%s%s", AppHttpPath.INSPECTION_ORIGINAL_IMAGE, "?id=", pathId);
    }

    /**
     * 内容图片url拼接
     *
     * @param path
     * @return
     */
    public static String formatPicUrl(String path) {
        return AppHttpPath.BASE_IMAGE + path;
    }
}
