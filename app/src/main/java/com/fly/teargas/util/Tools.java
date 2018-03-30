package com.fly.teargas.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.WIFI_SERVICE;

public class Tools {
    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        float scale = x.app().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = x.app().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        float scale = x.app().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = x.app().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 是否有存储卡是可读可写状态
     *
     * @return
     */
    public static Boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getSDPath() {

        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return "";
        }
    }

    /**
     * 将json数组转化为int型
     *
     * @param JSONArray
     * @return
     */
    public static int[] getJsonToIntArray(JSONArray jsonArray) {
        int[] arr = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                arr[i] = jsonArray.getInt(i);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return arr;
    }

    // 0 无网络 1 WIFI 2 移动互联网 3 未知网络
    public static int checkNet(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                return 1;

            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                return 2;

            } else {

                return 3;
            }
        } else {
            return 0;
        }
    }

    // 读取网络类型

    public static String getNetWorkType(int num) {

        switch (num) {
            case 0:
                return "无网络";
            case 1:
                return "WIFI";
            case 2:
                return "2G/3G";
            case 3:
                return "UNKNOW";
        }
        return "UNKNOW";

    }

    public static void delay(int times) {
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getSerialNum(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        // String serialNum = tm.getSimSerialNumber();
        return deviceid;
    }

    public static int getVersionCode(Context mContext) {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception ex) {
            return 0;
        }
    }

    public static String getVersionName(Context mContext) {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionName;
        } catch (Exception ex) {
            return "0";
        }
    }


    /**
     * 读取settings中自动离线新闻的设置
     *
     * @param context
     * @return 0关闭；1仅wifi时开启;2一直开启
     */
    public static int getSettingByOfflineRead(Context context) {
        Map<String, String> settings = new HashMap<String, String>();
        settings.put("仅wifi时开启", "1");
        settings.put("一直开启", "2");
        settings.put("关闭", "0");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String read = sp.getString("offlineread", "仅wifi时开启"); // 仅wifi开启,一直开启,关闭

        return Integer.parseInt(settings.get(read));
    }

    /**
     * 结合网络情况判断是否离线新闻
     *
     * @param context
     * @return
     */
    public static boolean getSettingByOfflineReadNetStatus(Context context) {

        int offlineRead = getSettingByOfflineRead(context);
        int netStatus = checkNet(context);

        if (netStatus > 0) {
            // 如果配置为一直开启下载或者仅在wifi时下载和网络是wifi的情况下，保存新闻
            if (offlineRead == 2 || (offlineRead == 1 && netStatus == 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 读取settings中"3G/2G网络禁止下图"的设置
     *
     * @param context
     * @return false允许下图 true非wifi下禁止下图
     */
    public static boolean getSettingByDownPic(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("downpic", false);
    }

    /**
     * 是否可以下载图片
     *
     * @param context
     * @return true下载false禁止下载
     */
    public static boolean getSettingByDownPicNetStatus(Context context) {
        /*
         * boolean isDownPic = getSettingByDownPic(context); int netStatus =
		 * checkNet(context);
		 * 
		 * if (netStatus > 0) { if (isDownPic == false || (isDownPic == true &&
		 * netStatus == 1)) { return true; } }
		 * 
		 * return false;
		 */
        int netStatus = checkNet(context);

        if (netStatus > 0)
            return true;
        else
            return false;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 窗口的宽度
        int screenWidth = dm.widthPixels;

        return screenWidth;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 窗口高度
        int screenHeight = dm.heightPixels;

        return screenHeight;
    }

    // 将Drawable转化为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    // 获取getassets文件夹的图片
    public static Drawable getRes(Context context, String name) {

        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(name);
        } catch (IOException e) {
            //
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) 80 / w);
        float scaleHeight = ((float) 100 / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return new BitmapDrawable(newbmp);

    }

    /**
     * TODO 计算hash
     *
     * @param toHash
     * @return
     */
    public static String hash(String toHash) {
        String hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02X", b));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 判断是否为平板
     *
     * @return
     */
    public static boolean isPad(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public static boolean isRoot() {
        boolean bool = false;

        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {

        }
        return bool;
    }

    /**
     * 获取手机mac地址<br/>
     * 错误返回12个0
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
//                    macAddress = info.getMacAddress().replace(":", "");
                    macAddress = info.getMacAddress();
                else
                    return macAddress;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    // 获取本机WIFI ip
    public static String getLocalIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        // 返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }


    //清除应用缓存
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    public static File getFileByPath(String filePath) {
        return isNullString(filePath) ? null : new File(filePath);
    }

    public static boolean isNullString(@Nullable String str) {
        return str == null || str.length() == 0 || "".equals(str) || "null".equals(str);
    }

    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        } else if (!dir.exists()) {
            return true;
        } else if (!dir.isDirectory()) {
            return false;
        } else {
            File[] files = dir.listFiles();
            File[] var2 = files;
            int var3 = files.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                File file = var2[var4];
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory() && !deleteDir(file)) {
                    return false;
                }
            }

            return dir.delete();
        }
    }

    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }
}
