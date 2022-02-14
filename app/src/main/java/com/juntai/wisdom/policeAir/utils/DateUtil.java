package com.juntai.wisdom.policeAir.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Describe:时间工具
 * Create by zhangzhenlong
 * 2020-3-16
 * email:954101549@qq.com
 */
public class DateUtil {

    /**
     * 格式化时分秒
     * @param useTime s
     * @return 00:00:00
     */
    public static String formatUseTime(long useTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //- TimeZone.getDefault().getRawOffset();消除8小时时间差
        String currentTime = sdf.format(useTime*1000- TimeZone.getDefault().getRawOffset());
        return currentTime;
    }

    /**
     * 根据date获取星期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String dateToWeek(String year, String month, String day){
        Calendar calendar = Calendar.getInstance();//获得一个日历
        calendar.set(Integer.valueOf(year), Integer.valueOf(month)-1, Integer.valueOf(day));//设置当前时间,月份是从0月开始计算
        int number = calendar.get(Calendar.DAY_OF_WEEK);//星期表示1-7，是从星期日开始，
        String [] str = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
        return str[number];
    }

    /**
     * 获取当前系统时间
     * @param format "yyyy-MM-dd  HH:mm:ss"
     * @return
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * date格式化
     * @param date
     * @param format "yyyy-MM-dd  HH:mm:ss"
     * @return
     */
    public static String getDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }

    /**
     * 获取当前系统时间
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 比较两个时间串的大小
     *
     * @param dateFormat 时间格式
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    public static boolean compareDateSize(String dateFormat, String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.CHINA);
        try {
            Long a = sdf.parse(startTime).getTime();
            Long b = sdf.parse(endTime).getTime();
            if (a > b) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
