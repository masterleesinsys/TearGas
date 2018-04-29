package com.fly.teargas.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.DeviceInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理
 */
@ContentView(R.layout.activity_management)
public class ManagementActivity extends BaseActivity {
    @ViewInject(R.id.tv_device_info)
    private TextView tv_device_info;    //设备信息
    @ViewInject(R.id.ll_arming)
    private LinearLayout ll_arming;         //布防
    @ViewInject(R.id.ll_withdrawAGarrison)
    private LinearLayout ll_withdrawAGarrison; //撤防
    @ViewInject(R.id.ll_bomb)
    private LinearLayout ll_bomb;       //爆弹
    @ViewInject(R.id.ll_deviceStatus)
    private LinearLayout ll_deviceStatus; //设备状态
    @ViewInject(R.id.ll_informationModification)
    private LinearLayout ll_informationModification; //信息修改
    @ViewInject(R.id.ll_logInformation)
    private LinearLayout ll_logInformation; //日志信息

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private int id = 0;
    private String deviceID = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("管理");
        showNameTvRight(MyApplication.getUserName());

        if (getIntent().hasExtra("id"))
            id = getIntent().getIntExtra("id", 0);
        if (getIntent().hasExtra("deviceID"))
            deviceID = getIntent().getStringExtra("deviceID");

        Map<String, String> map = new HashMap<>();
        map.put("deviceID", deviceID);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_DEVICE), map, spin_kit, new onGetDeviceXCallBack());
    }

    @Event(value = {R.id.ll_arming, R.id.ll_withdrawAGarrison, R.id.ll_bomb, R.id.ll_deviceStatus, R.id.ll_informationModification, R.id.ll_logInformation})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_arming:     //布防
                new AlertView("是否布防", "布防前请确认站内无人员工作，门已关好", "取消", new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("deviceID", deviceID);
                            HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.CMD_BU_FANG), map, spin_kit, new onCmdBuFangXCallBack());
                        }
                    }
                }).show();
                break;
            case R.id.ll_withdrawAGarrison:     //撤防
                new AlertView("是否撤防", "撤防前请确认有工作人员入内", "取消", new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("deviceID", deviceID);
                            HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.CMD_CHE_FANG), map, spin_kit, new onCmdCheFangXCallBack());
                        }
                    }
                }).show();
                break;
            case R.id.ll_bomb:     //爆弹
//                爆弹前请确认是否为犯罪分子盗窃，请选择爆弹通道
                new AlertView("是否爆弹", "爆弹前请确认是否为犯罪分子盗窃", "取消", new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("deviceID", deviceID);
                            HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.CMD_BAO_DAN), map, spin_kit, new onCmdBaoDanXCallBack());
                        }
                    }
                }).show();
                break;
            case R.id.ll_deviceStatus:     //设备状态
                new AlertView("温馨提示", "设备状态功能维护中，待后续升级添加", null, new String[]{"确定"}, null, this, AlertView.Style.Alert, null).show();
//                intent = new Intent();
//                intent.putExtra("deviceID", deviceID);
//                openActivity(intent, DeviceStatusActivity.class);
                break;
            case R.id.ll_informationModification:     //信息修改
                intent = new Intent();
                intent.putExtra("deviceID", deviceID);
                openActivity(intent, InformationModificationActivity.class);
                break;
            case R.id.ll_logInformation:     //日志信息
                intent = new Intent();
                intent.putExtra("deviceID", deviceID);
                openActivity(intent, LogInformationActivity.class);
                break;
        }
    }

    /**
     * 获取设备信息
     */
    private class onGetDeviceXCallBack implements HttpHelper.XCallBack {
        @SuppressLint("SetTextI18n")
        @Override
        public void onResponse(String result) {
            DeviceInfo deviceInfo = null;
            try {
                deviceInfo = getHttpResult(result, DeviceInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != deviceInfo) {
                tv_device_info.setText("设备" + deviceInfo.getDeviceID() + "    " + deviceInfo.getCurState());
            }
        }
    }

    /**
     * 布防
     */
    private class onCmdBuFangXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            LogUtils.e(result);
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
                new AlertView("温馨提示", "布防成功", null, new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, null).show();
            } else {
                new AlertView("温馨提示", "布防失败", null, new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, null).show();
            }
        }
    }

    /**
     * 撤防
     */
    private class onCmdCheFangXCallBack implements HttpHelper.XCallBack {
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
                new AlertView("温馨提示", "撤防成功", null, new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, null).show();
            } else {
                new AlertView("温馨提示", "撤防失败", null, new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, null).show();
            }
        }
    }

    /**
     * 爆弹
     */
    private class onCmdBaoDanXCallBack implements HttpHelper.XCallBack {
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
                new AlertView("温馨提示", "爆弹成功", null, new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, null).show();
            } else {
                new AlertView("温馨提示", "爆弹失败", null, new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, null).show();
            }
        }
    }
}
