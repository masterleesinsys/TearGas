package com.fly.teargas.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.adapter.EquimentManagementAdapter;
import com.fly.teargas.entity.DeviceInfo;
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

/**
 * 设备管理
 */
@ContentView(R.layout.activity_equipment_management)
public class EquipmentManagementActivity extends BaseActivity {
    @ViewInject(R.id.rv_equipment_management)
    private RecyclerView rv_equipment_management;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @ViewInject(R.id.tv_entire)
    private TextView tv_entire;     //全部
    @ViewInject(R.id.tv_normal)
    private TextView tv_normal;     //正常
    @ViewInject(R.id.tv_abnormal)
    private TextView tv_abnormal;   //异常

    private EquimentManagementAdapter equimentManagementAdapter = null;

    private List<DeviceInfo> list = null;
    private List<DeviceInfo> deviceInfoList = null;

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("设备管理");
        showNameTvLift(MyApplication.getUserName());

        mInitRecyclerView(rv_equipment_management, 2);

        clearColor();
        tv_entire.setBackgroundResource(R.color.titleBar_checked);
        getDevices(0);
    }

    @Event(value = {R.id.tv_entire, R.id.tv_normal, R.id.tv_abnormal})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_entire:    //全部
                clearColor();
                tv_entire.setBackgroundResource(R.color.titleBar_checked);
                getDevices(0);
                break;
            case R.id.tv_normal:    //正常
                clearColor();
                tv_normal.setBackgroundResource(R.color.titleBar_checked);
                getDevices(1);
                break;
            case R.id.tv_abnormal:  //异常
                clearColor();
                tv_abnormal.setBackgroundResource(R.color.titleBar_checked);
                getDevices(2);
                break;
        }
    }

    private void getDevices(final int type) {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_DEVICES), map, spin_kit, new HttpHelper.XCallBack() {
            @SuppressLint("SetTextI18n")
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
                    switch (type) {
                        case 0:
                            tv_entire.setText("全部 (" + list.size() + ")");
                            equimentManagementAdapter = new EquimentManagementAdapter(EquipmentManagementActivity.this, list);
                            rv_equipment_management.setAdapter(equimentManagementAdapter);
                            break;
                        case 1:
                            deviceInfoList = new ArrayList<>();
                            for (DeviceInfo deviceInfo : list) {
                                if ("布防".equals(deviceInfo.getCurState()))
                                    deviceInfoList.add(deviceInfo);
                            }
                            tv_normal.setText("正常 (" + deviceInfoList.size() + ")");
                            equimentManagementAdapter = new EquimentManagementAdapter(EquipmentManagementActivity.this, deviceInfoList);
                            rv_equipment_management.setAdapter(equimentManagementAdapter);
                            break;
                        case 2:
                            deviceInfoList = new ArrayList<>();
                            for (DeviceInfo deviceInfo : list) {
                                if (!"布防".equals(deviceInfo.getCurState()))
                                    deviceInfoList.add(deviceInfo);
                            }
                            tv_abnormal.setText("异常 (" + deviceInfoList.size() + ")");
                            equimentManagementAdapter = new EquimentManagementAdapter(EquipmentManagementActivity.this, deviceInfoList);
                            rv_equipment_management.setAdapter(equimentManagementAdapter);
                            break;
                    }

                    equimentManagementAdapter.setOnImgClickListener(new EquimentManagementAdapter.OnImgClickListener() {
                        @Override
                        public void onImgClickListener(int position) {
                            Intent intent = new Intent();
                            intent.putExtra("type", "EquipmentManagement");
                            intent.putExtra("lat", list.get(position).getLat());
                            intent.putExtra("lng", list.get(position).getLng());
                            openActivity(intent, MainActivity.class);
                        }
                    });

                    equimentManagementAdapter.setOnViewClickListener(new EquimentManagementAdapter.OnViewClickListener() {
                        @Override
                        public void onViewClickListener(int position, String deviceID) {
                            Intent intent = new Intent();
                            intent.putExtra("deviceID", deviceID);
                            openActivity(intent, ManagementActivity.class);
                        }
                    });
                } else {
                    rv_equipment_management.setAdapter(null);
                }
            }
        });
    }

    private void clearColor() {
        tv_entire.setBackgroundResource(R.color.titleBar_unChecked);
        tv_normal.setBackgroundResource(R.color.titleBar_unChecked);
        tv_abnormal.setBackgroundResource(R.color.titleBar_unChecked);
    }
}
