package com.fly.teargas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import com.fly.teargas.util.FileUtils;
import com.fly.teargas.util.ImageUtil;
import com.fly.teargas.util.LogUtils;

import org.xutils.x;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("ALL")
public class MyApplication extends Application {
    public static String SERVICE_HOST = "http://60.205.213.120/";    //服务地址
    public static String IMAGE_HOST = "http://60.205.213.120/";
    public final static int DATA_PAGE_SIZE = 10;         //数据分页大小
    public static boolean isProgramExit = false;        // 设置程序关闭状态
    public static boolean DEBUG = true;        // 程序当前是调试还是发布状态,默认为调试状态
    public static String ROOT_PATH = FileUtils.basePath;
    public static boolean DIALOG_NEW_VER = false;       //版本更新时最新版本是否提示
    public static Date SendMoblieCodePreLimitTime = null;       // 时间限制
    public static String USER_WEIXIN_OPEN_ID = "";  //用户的微信ID
    private static boolean running = false;     // 程序是否在运行
    private static Date startTime;      // 统计开始时间
    private static String KEY_LOGIN_AUTH = "LOGIN-AUTH";
    private static MyApplication mInstance;     // 接口服务地址
    private List<Activity> mList = new LinkedList<>();      // 运用list来保存们每一个activity是关键
    public static Boolean is_exit = false;     //账户是否注销

    public static MyApplication getInstance() {
        if (mInstance == null) {
            synchronized (MyApplication.class) {
                if (mInstance == null) {
                    mInstance = new MyApplication();
                }
            }
        }
        return mInstance;
    }

    //无需身份验证地址
    public static String getURL(String path) {
        return String.format("%s%s", SERVICE_HOST, path);
    }

    //须身份验证地址，携带token
    public static String getTokenURL(String path) {
        @SuppressLint("DefaultLocale") String retVal = String.format("%s%s?token=%s", SERVICE_HOST, path, MyApplication.getToken());
        return retVal;
    }

    public static Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date starttime) {
        startTime = starttime;
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        MyApplication.running = running;
    }

    public static void syncDetail(String info) {
        FileUtils.savePrivateObject(info, KEY_LOGIN_AUTH);
    }

    public static String getDetailFromLocal() {
        Object o = FileUtils.getPrivateObject(KEY_LOGIN_AUTH);
        if (o != null)
            return (String) o;
        return null;
    }

    public static void deleteDetail() {
        FileUtils.deletePrivateObject(KEY_LOGIN_AUTH);
    }

    /**
     * TODO 读取用户id
     *
     * @return 0未登录,>=1已登录
     */
    public static String getToken() {
        String info = getDetailFromLocal();
        if (info != null)
            return info;
        return "";
    }

    /**
     * TODO 用户注销
     */
    public static void userLogout() {
        deleteDetail();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        x.Ext.init(this);
        x.Ext.setDebug(false); // 开启debug会影响性能

        // 检查缓存目录是否存在
        File file = new File(getCachePath());
        if (!file.exists()) {
            file.mkdir();
        }
        File file1 = new File(FileUtils.basePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        ImageUtil.createTempImageFolder();
        ImageUtil.createImageFolder();

        // 设置启动时间
        setStartTime(new Date());
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // remove Activity
    public void removeActivity(Activity activity) {
        mList.remove(activity);
    }

    // 关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        } finally {
            System.exit(0);
        }
    }

    // 杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public String getCachePath() {
        return "/data/data/" + getPackageName() + "/cache/";
    }

    public String getDataBasePath() {
        return "/data/data/" + getPackageName() + "/database/";
    }

    public boolean isExit() {
        return isProgramExit;
    }

    public void setExit(boolean exit) {
        isProgramExit = exit;
    }
}
