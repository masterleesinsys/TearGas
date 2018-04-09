package com.fly.teargas.activity;

import com.fly.teargas.R;

import org.xutils.view.annotation.ContentView;

/**
 * 设备状态
 */
@ContentView(R.layout.activity_device_status)
public class DeviceStatusActivity extends BaseActivity {

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("设备状态");
    }
}
