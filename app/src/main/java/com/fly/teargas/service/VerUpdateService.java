package com.fly.teargas.service;

import android.content.Intent;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.core.AVersionService;
import com.fly.teargas.MyApplication;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.fly.teargas.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

public class VerUpdateService extends AVersionService {

    private String apkfilename = "app/TearsGas.apk";

    public VerUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        //可以在判断版本之后在设置是否强制更新或者VersionParams
        String data = null;
        try {
            data = getHttpResult(response, String.class);
            String content = new JSONObject(data).getString("content");
//            String title = new JSONObject(data).getString("title");
            int version = new JSONObject(data).getInt("version");
            String apkurl = MyApplication.getURL(apkfilename);

            LogUtils.e(apkurl);

            if (Tools.getVersionCode(this) < version) {
                CustomVersionDialogActivity.isNeedUpdate = true;
                showVersionDialog(apkurl, "版本更新", content);
            } else {
                CustomVersionDialogActivity.isNeedUpdate = false;
                if (MyApplication.DIALOG_NEW_VER)
                    showVersionDialog("", "版本判断", "已经是最新版本");
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    //解析服务端返回结果
    public <T> T getHttpResult(String result, Class<T> clazz) {
        Object o = new Object();
        JSONObject json = null;
        try {
            json = new JSONObject(result);
            if (json.getInt("success") == 1) {
                if (json.has("data")) {
                    String data = (String) json.getString("data");
                    o = JSON.parseObject(data, clazz);
                    return (T) o;
                } else
                    return null;
            } else if (json.getInt("success") == 0) {
                String errors = json.getString("error");
                Placard.showInfo(errors);
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
        return null;
    }
}
