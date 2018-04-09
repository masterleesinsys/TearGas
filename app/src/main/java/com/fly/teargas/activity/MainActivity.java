package com.fly.teargas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.fly.teargas.R;
import com.fly.teargas.util.LogUtils;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.iv_location)
    private ImageView iv_location;  //定位当前

    private MapView mMapView;

    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();

    private LatLng latLng = null;
    private boolean isFirstLoc = true; // 是否首次定位

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        initMap();
    }

    @Override
    protected void initView() {

    }

    @Event(value = {R.id.iv_location})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_location:  //定位当前
                //图片点击事件，回到定位点
//                isFirstLoc = true;
                mLocationClient.requestLocation();
                break;
        }
    }

    private void initMap() {
        setStyle(STYLE_HOME);
        setCaption("首页");

        mMapView = findViewById(R.id.bmapView);

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

        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.ico_coordinates);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.ico_coordinates_fill);

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
                switch (id) {
                    case 1:
                        intent=new Intent();
                        intent.putExtra("id",id);
                        openActivity(intent,ManagementActivity.class);
                        break;
                    case 2:
                        intent=new Intent();
                        intent.putExtra("id",id);
                        openActivity(intent,ManagementActivity.class);
                        break;
                }
                return false;
            }
        });

        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        //开启热力图
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            LogUtils.e(location.getLatitude());
            LogUtils.e(location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(15.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (mLocationClient.isStarted())
                    mLocationClient.stop();

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
//                    showToastText(location.getAddrStr());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
//                    showToastText(location.getAddrStr());
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
//                    showToastText(location.getAddrStr());
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    showToastText("服务器错误，请检查");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    showToastText("网络错误，请检查");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    showToastText("手机模式错误，请检查是否飞行");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        if (mLocationClient.isStarted())
            mLocationClient.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        if (mLocationClient.isStarted())
            mLocationClient.stop();
    }
}
