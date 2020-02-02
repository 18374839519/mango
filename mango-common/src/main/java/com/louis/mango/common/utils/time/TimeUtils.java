package com.louis.mango.common.utils.time;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间格式转换
 */
public class TimeUtils {

    public static String formatDate(Date date) {
        DateTime dateTime = new DateTime(date);
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        return dateTime.toString(formatStr);
    }
    public static Date parse(String strDate) {
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(strDate, dateTimeFormat);
        dateTime = dateTime.plusDays(1);
        return dateTime.toDate();
    }

    /**
     * 获取当前毫秒数
     * @return
     */
    public static String getTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }
}
