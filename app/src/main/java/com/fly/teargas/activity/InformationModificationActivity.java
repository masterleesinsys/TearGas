package com.fly.teargas.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.ModelInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 信息修改
 */
@ContentView(R.layout.activity_information_modification)
public class InformationModificationActivity extends BaseActivity {
    @ViewInject(R.id.tv_selectTemplate)
    private TextView tv_selectTemplate; //选择模版
    @ViewInject(R.id.tv_set_stencil)
    private TextView tv_set_stencil; //修改当前模版
    @ViewInject(R.id.tv_save)
    private TextView tv_save; //保存
    @ViewInject(R.id.iv_get_location)
    private ImageView iv_get_location;  //获取定位
    @ViewInject(R.id.tv_device_name)
    private TextView tv_device_name;    //设备名
    @ViewInject(R.id.et_lat)
    private EditText et_lat;
    @ViewInject(R.id.et_lng)
    private EditText et_lng;
    @ViewInject(R.id.et_deviceAddress)
    private EditText et_deviceAddress;  //设备地址

    @ViewInject(R.id.et_IN1)
    private EditText et_IN1;
    @ViewInject(R.id.et_OUT1)
    private EditText et_OUT1;

    @ViewInject(R.id.et_IN2)
    private EditText et_IN2;
    @ViewInject(R.id.et_OUT2)
    private EditText et_OUT2;

    @ViewInject(R.id.et_IN3)
    private EditText et_IN3;
    @ViewInject(R.id.et_OUT3)
    private EditText et_OUT3;

    @ViewInject(R.id.et_IN4)
    private EditText et_IN4;
    @ViewInject(R.id.et_OUT4)
    private EditText et_OUT4;

    @ViewInject(R.id.et_IN5)
    private EditText et_IN5;
    @ViewInject(R.id.et_OUT5)
    private EditText et_OUT5;

    @ViewInject(R.id.et_IN6)
    private EditText et_IN6;
    @ViewInject(R.id.et_OUT6)
    private EditText et_OUT6;

    @ViewInject(R.id.et_IN7)
    private EditText et_IN7;
    @ViewInject(R.id.et_OUT7)
    private EditText et_OUT7;

    @ViewInject(R.id.et_IN8)
    private EditText et_IN8;
    @ViewInject(R.id.et_OUT8)
    private EditText et_OUT8;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.sv_information_modification)
    private ScrollView sv_information_modification;

    private AlertView alertView = null;

    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;

    private String deviceID = "";
    private String modelID = "";
    private String lat = "";
    private String lng = "";
    private String deviceAddress = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("管理");
        showNameTvRight(MyApplication.getUserName());

        OverScrollDecoratorHelper.setUpOverScroll(sv_information_modification);

        if (getIntent().hasExtra("deviceID"))
            deviceID = getIntent().getStringExtra("deviceID");

        tv_device_name.setText("设备" + deviceID);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        et_lat.setText("" + String.valueOf(MyApplication.getUserLat()));
        et_lng.setText("" + String.valueOf(MyApplication.getUserLng()));
        et_deviceAddress.setText("" + String.valueOf(MyApplication.getUserAddress()));
    }

    @Event(value = {R.id.tv_save, R.id.tv_set_stencil, R.id.tv_selectTemplate, R.id.iv_get_location})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        lat = et_lat.getText().toString().trim();
        lng = et_lng.getText().toString().trim();
        deviceAddress = et_deviceAddress.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_selectTemplate:    //选择模版
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_ALL_MODEL), null, spin_kit, new onGetAllModelXCallBack());
                break;
            case R.id.tv_set_stencil:    //修改当前模版
                if ("".equals(modelID)) {
                    new AlertView("错误提示", "当前未选择任何模版", null, new String[]{"确定"}, null, this, AlertView.Style.Alert, null).show();
                    return;
                }
                intent = new Intent();
                intent.putExtra("modelID", modelID);
                openActivity(intent, SetStencilActivity.class);
                break;
            case R.id.tv_save:    //保存
                if ("".equals(lat) || "".equals(lng)) {
                    new AlertView("错误提示", "经纬度为必填项", null, new String[]{"确定"}, null, this, AlertView.Style.Alert, null).show();
                    return;
                }
                new AlertView("确认修改", "是否确认修改", "取消", new String[]{"确定"}, null, InformationModificationActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("deviceID", deviceID);
                            map.put("userOfDeviceID", deviceID);
                            map.put("beizhu", deviceAddress);
                            map.put("lat", lat);
                            map.put("lng", lng);
                            map.put("modelID", modelID);
                            HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.SET_DEVICE_BY_ID), map, spin_kit, new onSetDeviceByIdXCallBack());
                        }
                    }
                }).show();
                break;
            case R.id.iv_get_location:  //获取定位
                initLocation();
                break;
        }
    }

    private void showAlertView(int type, final String[] strs, final List<ModelInfo> list) {
        switch (type) {
            case 0:
                alertView = new AlertView(null, null, null, null, strs, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            showToastText("添加模版功能暂不可用");
//                            alertView.dismiss();
//                            openActivity(SetStencilActivity.class);
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("modelID", list.get(position - 1).getModelID());
                            HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_MODEL), map, spin_kit, new HttpHelper.XCallBack() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onResponse(String result) {
                                    ModelInfo modelInfo = null;
                                    try {
                                        modelInfo = getHttpResult(result, ModelInfo.class);
                                    } catch (Exception e) {
                                        LogUtils.e(e.toString());
                                        Placard.showInfo(e.toString());
                                        e.printStackTrace();
                                    }
                                    if (null != modelInfo) {
                                        modelID = modelInfo.getModelID();
                                        tv_selectTemplate.setText("模版" + modelInfo.getModelID());
                                        et_IN1.setText(modelInfo.getIn1());
                                        et_IN2.setText(modelInfo.getIn2());
                                        et_IN3.setText(modelInfo.getIn3());
                                        et_IN4.setText(modelInfo.getIn4());
                                        et_IN5.setText(modelInfo.getIn5());
                                        et_IN6.setText(modelInfo.getIn6());
                                        et_IN7.setText(modelInfo.getIn7());
                                        et_IN8.setText(modelInfo.getIn8());

                                        et_OUT1.setText(modelInfo.getIn1());
                                        et_OUT2.setText(modelInfo.getIn2());
                                        et_OUT3.setText(modelInfo.getIn3());
                                        et_OUT4.setText(modelInfo.getIn4());
                                        et_OUT5.setText(modelInfo.getIn5());
                                        et_OUT6.setText(modelInfo.getIn6());
                                        et_OUT7.setText(modelInfo.getIn7());
                                        et_OUT8.setText(modelInfo.getIn8());
                                        alertView.dismiss();
                                    } else {
                                        showToastText("获取数据失败，请重试");
                                        LogUtils.e("获取数据失败");
                                        alertView.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
                break;
            case 1:
                alertView = new AlertView(null, null, null, null, new String[]{"添加模版"}, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                showToastText("添加模版功能暂不可用");
//                                alertView.dismiss();
//                                openActivity(SetStencilActivity.class);
                                break;
                        }
                    }
                });
                break;
        }
        alertView.show();
    }

    /**
     * 获取所有模版列表
     */
    private class onGetAllModelXCallBack implements HttpHelper.XCallBack {
        private List<ModelInfo> list = null;
        private List<String> strList = new ArrayList<>();
        private String[] str = new String[]{};

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, ModelInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != list && 0 < list.size()) {
                strList.add("添加模版");
                for (ModelInfo modelInfo : list) {
                    strList.add("模版" + modelInfo.getModelID());
                }
                str = strList.toArray(str);
                showAlertView(0, str, list);
            } else {
                showAlertView(1, null, null);
            }
        }
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
        @SuppressLint("SetTextI18n")
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

                    et_lat.setText("" + String.valueOf(MyApplication.getUserLat()));
                    et_lng.setText("" + String.valueOf(MyApplication.getUserLng()));
                    et_deviceAddress.setText("" + String.valueOf(MyApplication.getUserAddress()));

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

    /**
     * 修改设备信息
     */
    private class onSetDeviceByIdXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if ("{}".equals(data))
                return;
            if ("true".equals(data)) {
                finish();
                showToastText("保存成功");
            } else {
                showToastText("保存失败，请重试");
            }
        }
    }
}
