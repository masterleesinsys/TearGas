package com.fly.teargas.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fly to 2117.8.9.
 * Version 2.1.2.
 * Email m15896847719@163.com
 */
public final class DateUtils {
    private int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0;
    private Calendar c = Calendar.getInstance();
    private int oldYear = 0, oldMonth = 0, oldDay = 0;

    /**
     * 获取当前年月日N天后的年月日
     *
     * @param dateTime 当前年月日字符串（2017-8-9）
     * @param days     N天（如3，2017-8-12）
     */
    public DateUtils(String dateTime, int days) {
        getDateAfterNDays(dateTime, days);
    }

    public DateUtils() {
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    ////**********获取当前年月日时分秒*****///////

    /**
     * 将字符串转为时间戳
     *
     * @param user_time
     * @return
     */
    public static String getTime(String user_time) {
        String re_time = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
            LogUtils.e(e.toString());
            Placard.showInfo(e.toString());
        }
        return re_time;
    }

    /**
     * 比较俩个时间字符串的大小
     *
     * @param s1
     * @param s2
     * @return 如果s1大于s2, 返回true;否则,返回false.
     * @throws Exception
     */
    public static Boolean DateCompare(String s1, String s2) throws Exception {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);
        //比较
        if (d1.getTime() > d2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTodayDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(new Date());
    }

    public int getYear() {
        year = c.get(Calendar.YEAR);
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        minute = c.get(Calendar.MINUTE);
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getOldYear() {
        return oldYear;
    }

    public void setOldYear(int oldYear) {
        this.oldYear = oldYear;
    }

    public int getOldMonth() {
        return oldMonth;
    }

    public void setOldMonth(int oldMonth) {
        this.oldMonth = oldMonth;
    }

    public int getOldDay() {
        return oldDay;
    }

    public void setOldDay(int oldDay) {
        this.oldDay = oldDay;
    }

    /**
     * 获取给定日期N天后的日期
     *
     * @author GaoHuanjie
     */
    public String getDateAfterNDays(String dateTime, int days) {
        Calendar calendar = Calendar.getInstance();
        String[] dateTimeArray = dateTime.split("-");
        int year = Integer.parseInt(dateTimeArray[0]);
        int month = Integer.parseInt(dateTimeArray[1]);
        int day = Integer.parseInt(dateTimeArray[2]);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONDAY, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.add(Calendar.DATE, days);

        this.oldYear = calendar.get(Calendar.YEAR);
        this.oldMonth = calendar.get(Calendar.MONTH) + 1;
        this.oldDay = calendar.get(Calendar.DAY_OF_MONTH);

        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
