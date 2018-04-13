package com.fly.teargas.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.xutils.x;

/**
 * Created by Fly to 2117.8.9.
 * Version 2.7.3.
 * Email m15896847719@163.com
 * sharedpreferences存储读取操作工具包
 */
@SuppressWarnings("ALL")
public class SharedPreferencesUtils {
    public static final String CONFIG_REMBER_USERNAME = "rember_username";      //记住账户
    public static final String CONFIG_REMBER_PASSWORD = "rember_password";      //记住密码
    public static final String CONFIG_IS_QUIT = "is_quit";      //判断当前账户是否已退出

    public static final String CONFIG_USER_TYPE = "user_type";      //用户类型

    private static final String CONFIG_NAME = "teargas_config";
    private static final String CONFIG_FILE_NAME = "filename";

    private static SharedPreferences preferences = null;
    private static SharedPreferences.Editor editor = null;

    /**
     * 实现应用参数保存
     *
     * @param name 要保存的字符串数据
     */
    public static void save(String name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(CONFIG_FILE_NAME, name);
        editor.apply();
    }

    public static void save(String name, String file_name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(file_name, name);
        editor.apply();
    }

    //要保存的Integer数据
    public static void save(Integer id) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(CONFIG_FILE_NAME, id);
        editor.apply();
    }

    public static void save(Integer id, String file_name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(file_name, id);
        editor.apply();
    }

    //要保存的Long数据
    public static void save(Long id, String file_name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putLong(file_name, id);
        editor.apply();
    }

    /**
     * 实现应用参数提取
     *
     * @return
     */
    public static String getStringPreferences() {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return preferences.getString(CONFIG_FILE_NAME, "");
    }

    public static String getStringPreferences(String file_name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return preferences.getString(file_name, "");
    }

    public static int getIntPreferences(String file_name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(file_name, 0);
    }

    public static Long getLongPreferences(String file_name) {
        preferences = x.app().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(file_name, 0);
    }
}