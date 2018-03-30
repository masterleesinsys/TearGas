package com.fly.teargas.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.fly.teargas.MyApplication;


/**
 * Created by Fly to 2117.9.29.
 * Version 1.1.1
 * Email m15896847719@163.com
 * 输出格式:
 * tagPrefix :className.methodName(Line:lineNumber),
 * tagPrefix 为空时只输出：className.methodName(Line:lineNumber)。
 * 统计程序输入日志
 */
public class LogUtils {
    public static String tagPrefix = "Fly";//log前缀

    public static void d(Object o) {
        logger("d", o);
    }

    public static void e(Object o) {
        logger("e", o);
    }

    public static void i(Object o) {
        logger("i", o);
    }

    public static void w(Object o) {
        logger("w", o);
    }

    /**
     * @param type logger级别
     * @param o    logger内容
     */
    private static void logger(String type, Object o) {
        if (!MyApplication.DEBUG) {
            return;
        }
        String msg = o + "";
        String tag = getTag(getCallerStackTraceElement());
        switch (type) {
            case "i":
                Log.i(tag, msg);
                break;
            case "d":
                Log.d(tag, msg);
                break;
            case "e":
                Log.e(tag, msg);
                break;
            case "w":
                Log.w(tag, msg);
                break;
        }
    }


    @SuppressLint("DefaultLocale")
    private static String getTag(StackTraceElement element) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = element.getClassName(); // 获取到类名

        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, element.getMethodName(), element.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":" + tag;
        return tag;
    }

    /**
     * 获取线程状态
     *
     * @return
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[5];
    }
}
