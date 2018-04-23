package com.fly.teargas.activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.DeviceInfo;
import com.fly.teargas.entity.UserInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {
    private ImageView iv_location;  //定位当前

    private SpinKitView spin_kit;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;

    private List<DeviceInfo> list = null;

    private Double lat = 0.0;
    private Double lng = 0.0;

    private BitmapDescriptor bitmap1 = null;
    private BitmapDescriptor bitmap2 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.bmapView);
        iv_location = findViewById(R.id.iv_location);
        spin_kit = findViewById(R.id.spin_kit);

        if (getIntent().hasExtra("lat") && getIntent().hasExtra("lng")) {
            lat = getIntent().getDoubleExtra("lat", 0.0);
            lng = getIntent().getDoubleExtra("lng", 0.0);
        }

        setStyle(STYLE_HOME);
        setCaption("首页");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_USER), null, spin_kit, new getUserXCallBack());

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

    @Override
    protected void initView() {

    }

    private void initMap() {
        if (MyApplication.getUserLat() == 0 || MyApplication.getUserLng() == 0) {
            new AlertView("定位服务出错", "定位服务出错，可能导致定位失败或定位不准，请打开位置开关后重启该页面,否则将无法获取到数据!", "返回", new String[]{"去设置"}, null, MainActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position >= 0) {
                        LocationManager alm = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                        if (alm != null && alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            LogUtils.e("GPS模块正常");
                        }
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
                    }
                }
            }).show();
            return;
        }

        //获取地图控件引用
        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        //隐藏比例尺
        mMapView.showScaleControl(false);
        //默认显示普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        locationNow(0);

        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationNow(1);
            }
        });

        bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.ico_coordinates);
        bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.ico_coordinates_fill);

        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_DEVICES), map, spin_kit, new HttpHelper.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    String data = getHttpResultList(result);
                    list = JSON.parseArray(data, DeviceInfo.class);
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                    Placard.showInfo(e.toString());
                    e.printStackTrace();
                }
                if (null != list && 0 <= list.size()) {
                    for (DeviceInfo deviceInfo : list) {
                        if (deviceInfo.getCurState().contains("布防")) {
                            MarkerOptions markerOptions1 = new MarkerOptions().icon(bitmap1).position(new LatLng(deviceInfo.getLat(), deviceInfo.getLng()));
                            Marker marker1 = (Marker) mBaiduMap.addOverlay(markerOptions1);
                            Bundle mBundle1 = new Bundle();
                            mBundle1.putInt("id", 1);
                            mBundle1.putString("deviceID", deviceInfo.getDeviceID());
                            marker1.setExtraInfo(mBundle1);
                        } else {
                            MarkerOptions markerOptions2 = new MarkerOptions().icon(bitmap2).position(new LatLng(deviceInfo.getLat(), deviceInfo.getLng()));
                            Marker marker2 = (Marker) mBaiduMap.addOverlay(markerOptions2);
                            Bundle mBundle2 = new Bundle();
                            mBundle2.putInt("id", 2);
                            mBundle2.putString("deviceID", deviceInfo.getDeviceID());
                            marker2.setExtraInfo(mBundle2);
                        }
                    }
                }
            }
        });

        //添加marker
        MarkerOptions markerOptions1 = new MarkerOptions().icon(bitmap1).position(new LatLng(34.840288, 113.534613));
        MarkerOptions markerOptions2 = new MarkerOptions().icon(bitmap2).position(new LatLng(34.830189, 113.524411));
        Marker marker1 = (Marker) mBaiduMap.addOverlay(markerOptions1);
        Marker marker2 = (Marker) mBaiduMap.addOverlay(markerOptions2);

        Bundle mBundle1 = new Bundle();
        mBundle1.putInt("id", 1);
        Bundle mBundle2 = new Bundle();
        mBundle2.putInt("id", 2);

        marker1.setExtraInfo(mBundle1);
        marker2.setExtraInfo(mBundle2);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = null;
                Bundle bundle = marker.getExtraInfo();
                int id = bundle.getInt("id");
                String deviceID = bundle.getString("deviceID");
                switch (id) {
                    case 1:
                        intent = new Intent();
                        intent.putExtra("id", id);
                        intent.putExtra("deviceID", deviceID);
                        openActivity(intent, ManagementActivity.class);
                        break;
                    case 2:
                        intent = new Intent();
                        intent.putExtra("id", id);
                        intent.putExtra("deviceID", deviceID);
                        openActivity(intent, ManagementActivity.class);
                        break;
                }
                return false;
            }
        });
    }

    private void locationNow(int type) {
        MapStatus mMapStatus = null;
        if (type == 0) {
            if (getIntent().hasExtra("lat") && getIntent().hasExtra("lng")) {
                mMapStatus = new MapStatus.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(15)
                        .build();
            } else {
                mMapStatus = new MapStatus.Builder()
                        .target(new LatLng(MyApplication.getUserLat(), MyApplication.getUserLng()))
                        .zoom(15)
                        .build();
            }
        } else if (type == 1) {
            mMapStatus = new MapStatus.Builder()
                    .target(new LatLng(MyApplication.getUserLat(), MyApplication.getUserLng()))
                    .zoom(15)
                    .build();
        }
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 获取账户信息
     */
    private class getUserXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            UserInfo userInfo = null;
            try {
                userInfo = getHttpResult(result, UserInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != userInfo) {
                MyApplication.setUserName(userInfo.getName());
                showNameTvLift(MyApplication.getUserName());
            }
        }
    }

    /**
     * 判断是否有新的警情信息
     */
    private class getChechNewXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            Boolean isNew = false;
            try {
                isNew = getHttpResultBoolean(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (isNew) {
                LogUtils.e("有新的警情");
            } else {
                LogUtils.e("没有新的警情");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        MapView.setMapCustomEnable(false);
        mMapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume()，实现地图生命周期管理
        mMapView.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_CHECKNEW), null, spin_kit, new getChechNewXCallBack());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause()，实现地图生命周期管理
        mMapView.onPause();
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

                    LogUtils.e(latitude);
                    LogUtils.e(longitude);

                    MyApplication.setUserLat(latitude);
                    MyApplication.setUserLng(longitude);

                    if (mLocationClient.isStarted()) {
                        // 获得位置之后停止定位
                        mLocationClient.stop();
                        initMap();
                    }
                } catch (Exception e) {
                    showToastText("定位服务出错,请检查您是否已开启定位权限!");
                    LogUtils.e("定位服务出错!" + e.toString());
                }
            }
        }
    }
}
