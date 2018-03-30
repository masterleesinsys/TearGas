package com.fly.teargas.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fly.teargas.R;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

public class HttpHelper {
    private final static String SAVE_FILE_PATH = "";
    private volatile static HttpHelper instance;
    private Handler handler;

    private HttpHelper() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 检查网络是否可用
     *
     * @param context 上下文
     * @return true为可用，false为不可用
     */
    private static boolean isNetworkAvailable(Context context) {
        NetworkInfo networkinfo = null;
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null) {
                return false;
            }
            networkinfo = manager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
        return !(networkinfo == null || !networkinfo.isAvailable());
    }

    /**
     * 异步get请求
     *
     * @param url      地址
     * @param paramMap 参数
     * @param sd       加载进度条
     * @param is_show  是否显示进度条
     * @param callBack 回调函数
     */
    private void get(String url, Map<String, String> paramMap, SpinKitView sd, final Boolean is_show, final XCallBack callBack) {
        if (!isNetworkAvailable(x.app())) {
            Placard.showInfo("您的网络可能存在问题，请检查网络连接是否正常");
            return;
        }
        RequestParams params = new RequestParams(url);
        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        if (sd != null && is_show) {
            sd.setVisibility(View.VISIBLE);
        }
        final SpinKitView finalSd = sd;
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
                onSuccessResponse(result, callBack);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                LogUtils.e(ex.toString());
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
                Placard.showInfo("远程接口异常！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e(cex.toString());
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 异步get请求(带加载进度条请求)
     */
    public void get(String url, Map<String, String> paramMap, SpinKitView sd, final XCallBack callBack) {
        get(url, paramMap, sd, true, callBack);
    }

    /**
     * 异步get请求(不带加载进度条请求)
     */
    public void get(String url, Map<String, String> paramMap, final XCallBack callBack) {
        get(url, paramMap, null, false, callBack);
    }

    /**
     * 异步post请求
     *
     * @param url      地址
     * @param maps     参数
     * @param sd       加载进度条
     * @param is_show  是否显示进度条
     * @param callback 回调函数
     */
    public void post(String url, Map<String, Object> maps, SpinKitView sd, final Boolean is_show, final XCallBack callback) {
        if (!isNetworkAvailable(x.app())) {
            Placard.showInfo("您的网络可能存在问题，请检查网络连接是否正常");
            return;
        }
        RequestParams params = new RequestParams(url);
        JSONObject js_request = new JSONObject();//服务器需要传参的json对象
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                try {
                    js_request.put(entry.getKey(), entry.getValue());//根据实际需求添加相应键值对
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.e(e.toString());
                }
            }
        }
        params.setAsJsonContent(true);
        params.setBodyContent(js_request.toString());
        if (sd != null && is_show) {
            sd.setVisibility(View.VISIBLE);
        }
        final SpinKitView finalSd = sd;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e(ex.toString());
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
                Placard.showInfo("远程接口异常！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e(cex.toString());
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                if (finalSd != null && is_show)
                    finalSd.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 异步post请求(带加载进度条请求)
     */
    public void post(String url, Map<String, Object> maps, SpinKitView sd, final XCallBack callback) {
        post(url, maps, sd, true, callback);
    }

    /**
     * 异步post请求(不带加载进度条请求)
     */
    public void post(String url, Map<String, Object> maps, final XCallBack callback) {
        post(url, maps, null, false, callback);
    }

    /**
     * 带缓存数据的异步 get请求(带加载进度条请求)
     *
     * @param url       地址
     * @param maps      参数
     * @param pnewCache 是否缓存
     * @param sd        加载进度条
     * @param callback  回调函数
     */
    public void getCache(String url, Map<String, String> maps, final boolean pnewCache, SpinKitView sd, final XCallBack callback) {
        if (!isNetworkAvailable(x.app())) {
            Placard.showInfo("您的网络可能存在问题，请检查网络连接是否正常");
            return;
        }
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        if (sd != null) {
            sd.setVisibility(View.VISIBLE);
        }
        final SpinKitView finalSd = sd;
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e(ex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
                Placard.showInfo("远程接口异常！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e(cex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public boolean onCache(String result) {
                boolean newCache = pnewCache;
                if (newCache) {
                    newCache = false;
                } else {
                    newCache = true;
                    onSuccessResponse(result, callback);
                }
                return newCache;
            }
        });
    }

    /**
     * 带缓存数据的异步 post请求(带加载进度条请求)
     *
     * @param url       地址
     * @param maps      参数
     * @param pnewCache 是否缓存
     * @param sd        加载进度条
     * @param callback  回调函数
     */
    public void postCache(String url, Map<String, String> maps, final boolean pnewCache, SpinKitView sd, final XCallBack callback) {
        if (!isNetworkAvailable(x.app())) {
            Placard.showInfo("您的网络可能存在问题，请检查网络连接是否正常");
            return;
        }
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (sd != null) {
            sd.setVisibility(View.VISIBLE);
        }
        final SpinKitView finalSd = sd;
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e(ex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
                Placard.showInfo("远程接口异常！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e(cex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public boolean onCache(String result) {
                boolean newCache = pnewCache;
                if (newCache) {
                    newCache = false;
                } else {
                    newCache = true;
                    onSuccessResponse(result, callback);
                }
                return newCache;
            }
        });
    }

    /**
     * 文件上传(带加载进度条请求)
     *
     * @param url      地址
     * @param maps     参数
     * @param file     文件
     * @param sd       加载进度条
     * @param callback 回调函数
     */
    public void upLoadFile(String url, Map<String, String> maps, Map<String, File> file, SpinKitView sd, final XCallBack callback) {
        if (!isNetworkAvailable(x.app())) {
            Placard.showInfo("您的网络可能存在问题，请检查网络连接是否正常");
            return;
        }
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (file != null) {
            for (Map.Entry<String, File> entry : file.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue().getAbsoluteFile());
            }
        }
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        params.setMultipart(true);

        if (sd != null) {
            sd.setVisibility(View.VISIBLE);
        }
        final SpinKitView finalSd = sd;
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
                onSuccessResponse(result, callback);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e(ex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
                Placard.showInfo("远程接口异常！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e(cex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 文件下载(带加载进度条请求)
     *
     * @param url      地址
     * @param maps     参数
     * @param sd       加载进度条
     * @param callBack 回调函数
     */
    public void downLoadFile(String url, Map<String, String> maps, SpinKitView sd, final XDownLoadCallBack callBack) {
        if (!isNetworkAvailable(x.app())) {
            Placard.showInfo("您的网络可能存在问题，请检查网络连接是否正常");
            return;
        }
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setAutoRename(true);// 断点续传
        params.setSaveFilePath(SAVE_FILE_PATH);
        if (sd != null) {
            sd.setVisibility(View.VISIBLE);
        }
        final SpinKitView finalSd = sd;
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(final File result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onResponse(result);
                        }
                    }
                });
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e(ex.toString());
                Placard.showInfo("下载失败！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e(cex.toString());
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFinished();
                        }
                    }
                });
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onWaiting() {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onStarted() {
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }

            @Override
            public void onLoading(final long total, final long current, final boolean isDownloading) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onLoading(total, current, isDownloading);
                        }
                    }
                });
                if (finalSd != null)
                    finalSd.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 异步get请求返回结果,json字符串
     *
     * @param result   请求字符串
     * @param callBack 回调函数
     */
    private void onSuccessResponse(final String result, final XCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(result);
                }
            }
        });
    }

    /**
     * TODO Glide 加载图片
     *
     * @param imagePath 图片地址
     * @param iv        显示图片的控件
     */
    public void mLoadPicture(String imagePath, ImageView iv) {
        try {
            Glide.with(x.app()).load(imagePath).placeholder(R.drawable.general_kecheng_no).error(R.drawable.general_kecheng_no).into(iv);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * TODO Glide 加载图片(尺寸压缩)
     *
     * @param imagePath 图片地址
     * @param iv        显示图片的控件
     */
    public void mLoadPicture(String imagePath, ImageView iv, int width, int height) {
        try {
            Glide.with(x.app()).load(imagePath).override(width, height).placeholder(R.drawable.general_kecheng_no).error(R.drawable.general_kecheng_no).into(iv);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    /**
     * TODO 加载圆型网络图片
     *
     * @param url       图片地址
     * @param imageView
     */
    public void mLoadCirclePicture(String url, final ImageView imageView) {
        Glide.with(x.app()).load(url).asBitmap().centerCrop().placeholder(R.drawable.general_touxiang_y_no)
                .error(R.drawable.general_touxiang_y_no).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(x.app().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * TODO Glide 下载图片
     *
     * @param imagePath 图片地址
     */
    public Bitmap mDownLoadPicture(String imagePath, int width, int height) {
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(x.app()).load(imagePath).asBitmap().centerCrop().into(width, height).get();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
        return myBitmap;
    }

    public interface XCallBack {
        void onResponse(String result);
    }

    public interface XDownLoadCallBack extends XCallBack {
        void onResponse(File result);

        void onLoading(long total, long current, boolean isDownloading);

        void onFinished();
    }
}
