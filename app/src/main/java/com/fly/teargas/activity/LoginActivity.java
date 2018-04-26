package com.fly.teargas.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.fly.teargas.util.SharedPreferencesUtils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录注册
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @ViewInject(R.id.iv_logo)
    private ImageView iv_logo;

    @ViewInject(R.id.et_mailbox)
    private EditText et_mailbox;
    @ViewInject(R.id.et_password)
    private EditText et_password;

    @ViewInject(R.id.cb_rememberPassword)
    private CheckBox cb_rememberPassword;

    @ViewInject(R.id.tv_login)
    private TextView tv_login;

    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;

    private String mailbox = "";
    private String password = "";

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("登录");

        //仅供测试使用，待删除
        et_mailbox.setText("admin");
        et_password.setText("admin");

        initLocation();
    }

    private void initLocation() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        StartLocation();
        // 启动定位
        mLocationClient.start();
    }

    @Event(value = {R.id.iv_logo, R.id.tv_login})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        mailbox = et_mailbox.getText().toString().trim();
        password = et_password.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_login: //登录
                if ("".equals(mailbox)) {
                    showToastText("账户不能为空");
                    return;
                } else if ("".equals(password)) {
                    showToastText("密码不能为空");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("username", mailbox);
                map.put("password", password);
                HttpHelper.getInstance().upLoadFile(MyApplication.getURL(Constants.GET_TOKEN), map, null, spin_kit, new getTokenXCallBack());
                break;
        }
    }

    /**
     * 获取账户token
     */
    private class getTokenXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (!"".equals(data)) {
                //记住密码
                rememberPassword();

                //缓存token信息
                MyApplication.syncDetail(data);
                openActivity(MainActivity.class);
            }
        }
    }

    /**
     * 记住密码
     */
    private void rememberPassword() {
        //如果被选中，则记录当前账户密码；否则清空混存账户密码
        if (cb_rememberPassword.isSelected()) {
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_USERNAME, mailbox);
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_PASSWORD, password);
        } else {
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_USERNAME, "");
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_PASSWORD, "");
        }
    }

    /**
     * 获得所在位置经纬度及详细地址
     */
    private void StartLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 设置定位模式 高精度
        option.setCoorType("gcj02");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
    }

    private class MyBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
//                 根据BDLocation 对象获得经纬度以及详细地址信息
//                当检测到系统版本为6.0，并且用户未打开按钮时，进行提示。
                LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locManager != null && !locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
                    showToastText("未打开位置开关，可能导致定位失败或定位不准，请打开位置开关后重启该页面,否则将无法获取到数据!");
                    return;
                }
                try {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String address = location.getAddrStr();

                    MyApplication.setUserLat(latitude);
                    MyApplication.setUserLng(longitude);
                    MyApplication.setUserAddress(address);

                    if (mLocationClient.isStarted()) {
                        // 获得位置之后停止定位
                        mLocationClient.stop();
                    }
                } catch (Exception e) {
                    showToastText("定位服务出错,请检查您是否已开启定位权限!");
                    LogUtils.e("定位服务出错!" + e.toString());
                }
            }
        }
    }
}
