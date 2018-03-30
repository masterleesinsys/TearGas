package com.fly.teargas.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fly to 2117.8.9.
 * Version 3.1.2.
 * Email m15896847719@163.com
 * 以友好的方法展示时间
 */

public class TimeUtils {
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将传入Date值转换为想要显示的字符串
     *
     * @param value 只允许java.util.Date 或 String 类型,其他类型不处理
     * @return 几秒前，几分钟前，几小时前，几天前，几个月前，几年前，很久以前（10年前）
     */
    public static String convertToShowStr(Object value) {
        String result = null;
        if (value instanceof Date) {
            result = convertDateToShowStr((Date) value);
        } else if (value instanceof String) {
            result = convertDateToShowStr(convertToDate((String) value));
        }
        return result;
    }

    /**
     * 转换日期到指定格式方便查看的描述说明
     *
     * @param date
     * @return 几秒前，几分钟前，几小时前，几天前，几个月前，几年前，很久以前（10年前）,如果出现之后的时间，则提示：未知
     */
    private static String convertDateToShowStr(Date date) {
        String showStr = "";
        long yearSeconds = 31536000L;//365 * 24 * 60 * 60;
        long monthSeconds = 2592000L;//30 * 24 * 60 * 60;
        long daySeconds = 86400L;//24 * 60 * 60;
        long hourSeconds = 3600L;//60 * 60;
        long minuteSeconds = 60L;

        long time = (new Date().getTime() - date.getTime()) / 1000;
        if (time <= 0) {
            showStr = "刚刚";
            return showStr;
        }
        if (time / yearSeconds > 0) {
            int year = (int) (time / yearSeconds);
            if (year > 10)
                showStr = "很久前";
            else {
                showStr = year + "年前";
            }
        } else if (time / monthSeconds > 0) {
            showStr = time / monthSeconds + "个月前";
        } else if (time / daySeconds > 0) {
            showStr = time / daySeconds + "天前";
        } else if (time / hourSeconds > 0) {
            showStr = time / hourSeconds + "小时前";
        } else if (time / minuteSeconds > 0) {
            showStr = time / minuteSeconds + "分钟前";
        } else if (time > 0) {
            showStr = time + "秒前";
        }
        return showStr;
    }

    /**
     * 将传入的时间字符串转换为Date
     *
     * @param value
     * @return
     */
    private static Date convertToDate(String value) {
        Date result = null;
        try {
            if (value.indexOf(":") == -1) {
                result = sdfDate.parse(value);
            } else {
                result = sdfDateTime.parse(value);
            }
        } catch (ParseException e) {
            Log.e("TimeUtils_error", "Could not parse date" + e.getMessage());
        }
        return result;
    }

}
