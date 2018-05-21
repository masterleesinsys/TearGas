package com.fly.teargas.activity;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.DeviceStateInfo;
import com.fly.teargas.entity.ModelInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备状态
 */
@ContentView(R.layout.activity_device_status)
public class DeviceStatusActivity extends BaseActivity {
    @ViewInject(R.id.tv_device_name)
    private TextView tv_device_name;    //设备名
    @ViewInject(R.id.tv_device_info)
    private TextView tv_device_info;    //设备信息

    @ViewInject(R.id.tv_in1_name)
    private TextView tv_in1_name;
    @ViewInject(R.id.tv_in1_info)
    private TextView tv_in1_info;
    @ViewInject(R.id.tv_in2_name)
    private TextView tv_in2_name;
    @ViewInject(R.id.tv_in2_info)
    private TextView tv_in2_info;
    @ViewInject(R.id.tv_in3_name)
    private TextView tv_in3_name;
    @ViewInject(R.id.tv_in3_info)
    private TextView tv_in3_info;
    @ViewInject(R.id.tv_in4_name)
    private TextView tv_in4_name;
    @ViewInject(R.id.tv_in4_info)
    private TextView tv_in4_info;
    @ViewInject(R.id.tv_in5_name)
    private TextView tv_in5_name;
    @ViewInject(R.id.tv_in5_info)
    private TextView tv_in5_info;
    @ViewInject(R.id.tv_in6_name)
    private TextView tv_in6_name;
    @ViewInject(R.id.tv_in6_info)
    private TextView tv_in6_info;
    @ViewInject(R.id.tv_in7_name)
    private TextView tv_in7_name;
    @ViewInject(R.id.tv_in7_info)
    private TextView tv_in7_info;
    @ViewInject(R.id.tv_in8_name)
    private TextView tv_in8_name;
    @ViewInject(R.id.tv_in8_info)
    private TextView tv_in8_info;

    @ViewInject(R.id.tv_out1_name)
    private TextView tv_out1_name;
    @ViewInject(R.id.tv_out1_info)
    private TextView tv_out1_info;
    @ViewInject(R.id.tv_out2_name)
    private TextView tv_out2_name;
    @ViewInject(R.id.tv_out2_info)
    private TextView tv_out2_info;
    @ViewInject(R.id.tv_out3_name)
    private TextView tv_out3_name;
    @ViewInject(R.id.tv_out3_info)
    private TextView tv_out3_info;
    @ViewInject(R.id.tv_out4_name)
    private TextView tv_out4_name;
    @ViewInject(R.id.tv_out4_info)
    private TextView tv_out4_info;
    @ViewInject(R.id.tv_out5_name)
    private TextView tv_out5_name;
    @ViewInject(R.id.tv_out5_info)
    private TextView tv_out5_info;
    @ViewInject(R.id.tv_out6_name)
    private TextView tv_out6_name;
    @ViewInject(R.id.tv_out6_info)
    private TextView tv_out6_info;
    @ViewInject(R.id.tv_out7_name)
    private TextView tv_out7_name;
    @ViewInject(R.id.tv_out7_info)
    private TextView tv_out7_info;
    @ViewInject(R.id.tv_out8_name)
    private TextView tv_out8_name;
    @ViewInject(R.id.tv_out8_info)
    private TextView tv_out8_info;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String deviceID = "";
    private String modelID = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("设备状态");
        showNameTvRight(MyApplication.getUserName());

        if (getIntent().hasExtra("deviceID"))
            deviceID = getIntent().getStringExtra("deviceID");
        if (getIntent().hasExtra("modelID"))
            modelID = getIntent().getStringExtra("modelID");

        tv_device_name.setText("设备" + deviceID);

        Map<String, String> map = new HashMap<>();
        map.put("modelID", modelID);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_MODEL), map, spin_kit, new onGetModelXCallBack());
    }

    /**
     * 获取指定模版
     */
    private class onGetModelXCallBack implements HttpHelper.XCallBack {
        private ModelInfo modelInfo = null;

        private String in1 = "";
        private String in2 = "";
        private String in3 = "";
        private String in4 = "";
        private String in5 = "";
        private String in6 = "";
        private String in7 = "";
        private String in8 = "";

        private String out1 = "";
        private String out2 = "";
        private String out3 = "";
        private String out4 = "";
        private String out5 = "";
        private String out6 = "";
        private String out7 = "";
        private String out8 = "";

        @Override
        public void onResponse(String result) {
            try {
                modelInfo = getHttpResult(result, ModelInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != modelInfo) {
                in1 = modelInfo.getIn1();
                in2 = modelInfo.getIn2();
                in3 = modelInfo.getIn3();
                in4 = modelInfo.getIn4();
                in5 = modelInfo.getIn5();
                in6 = modelInfo.getIn6();
                in7 = modelInfo.getIn7();
                in8 = modelInfo.getIn8();

                out1 = modelInfo.getOut1();
                out2 = modelInfo.getOut2();
                out3 = modelInfo.getOut3();
                out4 = modelInfo.getOut4();
                out5 = modelInfo.getOut5();
                out6 = modelInfo.getOut6();
                out7 = modelInfo.getOut7();
                out8 = modelInfo.getOut8();

                tv_in1_name.setText(modelInfo.getIn1());
                tv_in2_name.setText(modelInfo.getIn2());
                tv_in3_name.setText(modelInfo.getIn3());
                tv_in4_name.setText(modelInfo.getIn4());
                tv_in5_name.setText(modelInfo.getIn5());
                tv_in6_name.setText(modelInfo.getIn6());
                tv_in7_name.setText(modelInfo.getIn7());
                tv_in8_name.setText(modelInfo.getIn8());

                tv_out1_name.setText(modelInfo.getOut1());
                tv_out2_name.setText(modelInfo.getOut2());
                tv_out3_name.setText(modelInfo.getOut3());
                tv_out4_name.setText(modelInfo.getOut4());
                tv_out5_name.setText(modelInfo.getOut5());
                tv_out6_name.setText(modelInfo.getOut6());
                tv_out7_name.setText(modelInfo.getOut7());
                tv_out8_name.setText(modelInfo.getOut8());

                Map<String, String> map = new HashMap<>();
                map.put("deviceID", deviceID);
                map.put("index", "0");
                map.put("len", "1");
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_DEVICE_STATES), map, spin_kit, new HttpHelper.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        List<DeviceStateInfo> list = null;
                        try {
                            String data = getHttpResultList(result);
                            list = JSON.parseArray(data, DeviceStateInfo.class);
                        } catch (Exception e) {
                            LogUtils.e(e.toString());
                            Placard.showInfo(e.toString());
                            e.printStackTrace();
                        }
                        if (null != list && 0 < list.size()) {
                            DeviceStateInfo deviceStateInfo = list.get(0);
                            List<Integer> inValues = deviceStateInfo.getInValues();
                            List<Integer> outValues = deviceStateInfo.getOutValues();

                            tv_device_info.setText(deviceStateInfo.getInfo());

                            if (0 >= inValues.size() || 0 >= outValues.size()) {
                                showToastText("未获取到相关状态数据，请重试");
                                return;
                            }

                            if (!"".equals(in1)) {
                                if (inValues.get(0) == 1) {
                                    tv_in1_info.setText("正常");
                                    tv_in1_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in1_info.setText("报警");
                                    tv_in1_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out1)) {
                                if (outValues.get(0) == 1) {
                                    tv_out1_info.setText("正常");
                                    tv_out1_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out1_info.setText("报警");
                                    tv_out1_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in2)) {
                                if (inValues.get(1) == 1) {
                                    tv_in2_info.setText("正常");
                                    tv_in2_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in2_info.setText("报警");
                                    tv_in2_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out2)) {
                                if (outValues.get(1) == 1) {
                                    tv_out2_info.setText("正常");
                                    tv_out2_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out2_info.setText("报警");
                                    tv_out2_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in3)) {
                                if (inValues.get(2) == 1) {
                                    tv_in3_info.setText("正常");
                                    tv_in3_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in3_info.setText("报警");
                                    tv_in3_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out3)) {
                                if (outValues.get(2) == 1) {
                                    tv_out3_info.setText("正常");
                                    tv_out3_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out3_info.setText("报警");
                                    tv_out3_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in4)) {
                                if (inValues.get(3) == 1) {
                                    tv_in4_info.setText("正常");
                                    tv_in4_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in4_info.setText("报警");
                                    tv_in4_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out4)) {
                                if (outValues.get(3) == 1) {
                                    tv_out4_info.setText("正常");
                                    tv_out4_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out4_info.setText("报警");
                                    tv_out4_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in5)) {
                                if (inValues.get(4) == 1) {
                                    tv_in5_info.setText("正常");
                                    tv_in5_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in5_info.setText("报警");
                                    tv_in5_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out5)) {
                                if (outValues.get(4) == 1) {
                                    tv_out5_info.setText("正常");
                                    tv_out5_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out5_info.setText("报警");
                                    tv_out5_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in6)) {
                                if (inValues.get(5) == 1) {
                                    tv_in6_info.setText("正常");
                                    tv_in6_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in6_info.setText("报警");
                                    tv_in6_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out6)) {
                                if (outValues.get(5) == 1) {
                                    tv_out6_info.setText("正常");
                                    tv_out6_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out6_info.setText("报警");
                                    tv_out6_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in7)) {
                                if (inValues.get(6) == 1) {
                                    tv_in7_info.setText("正常");
                                    tv_in7_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in7_info.setText("报警");
                                    tv_in7_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out7)) {
                                if (outValues.get(6) == 1) {
                                    tv_out7_info.setText("正常");
                                    tv_out7_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out7_info.setText("报警");
                                    tv_out7_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }

                            if (!"".equals(in8)) {
                                if (inValues.get(7) == 1) {
                                    tv_in8_info.setText("正常");
                                    tv_in8_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_in8_info.setText("报警");
                                    tv_in8_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                            if (!"".equals(out8)) {
                                if (outValues.get(7) == 1) {
                                    tv_out8_info.setText("正常");
                                    tv_out8_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.normal));
                                } else {
                                    tv_out8_info.setText("报警");
                                    tv_out8_info.setTextColor(ContextCompat.getColor(DeviceStatusActivity.this, R.color.main_update_color));
                                }
                            }
                        } else {
                            showToastText("设备状态信息获取失败，请重试");
                        }
                    }
                });
            } else {
                showToastText("设备状态信息获取失败，请重试");
            }
        }
    }
}
