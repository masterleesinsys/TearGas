package com.fly.teargas.activity;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备状态
 */
@ContentView(R.layout.activity_device_status)
public class DeviceStatusActivity extends BaseActivity {
    @ViewInject(R.id.tv_device_name)
    private TextView tv_device_name;    //设备名

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String deviceID = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("设备状态");
        showNameTvRight(MyApplication.getUserName());

        if (getIntent().hasExtra("deviceID"))
            deviceID = getIntent().getStringExtra("deviceID");

        tv_device_name.setText("设备" + deviceID);

        Map<String, String> map = new HashMap<>();
        map.put("deviceID", deviceID);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.CMD_CHA_XUN), map, spin_kit, new onCmdChaXunXCallBack());
    }

    /**
     * 查询
     */
    private class onCmdChaXunXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            LogUtils.e(result);
            String data = "";
            try {
                data = getHttpResult(result, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (!"".equals(data)) {
                showToastText("查询成功");
            }
        }
    }
}
