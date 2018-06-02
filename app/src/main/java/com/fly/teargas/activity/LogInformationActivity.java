package com.fly.teargas.activity;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.adapter.LogInformationAdapter;
import com.fly.teargas.entity.RecordInfo;
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
 * 日志信息
 */
@ContentView(R.layout.activity_log_information)
public class LogInformationActivity extends BaseActivity {
    @ViewInject(R.id.rv_log_information)
    private RecyclerView rv_log_information;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String deviceID = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        showNameTvRight(MyApplication.getUserName());

        mInitRecyclerView(rv_log_information, 2);

        Map<String, String> map = new HashMap<>();
        map.put("deviceID", deviceID);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_RECORD_BY_DEVICEID), map, spin_kit, new onGetRecordByDeviceIdXCallBack());
    }

    /**
     * 获取操作记录，须指定设备ID
     */
    private class onGetRecordByDeviceIdXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            List<RecordInfo> list = null;
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, RecordInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != list && 0 < list.size()) {
                setCaption("日志信息(" + list.size() + ")");
                LogInformationAdapter logInformationAdapter = new LogInformationAdapter(LogInformationActivity.this, list);
                rv_log_information.setAdapter(logInformationAdapter);
            } else {
                rv_log_information.setAdapter(null);
            }
        }
    }
}
