package com.fly.teargas.activity;

import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.adapter.AlarmCenterAdapter;
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
 * 告警中心
 */
@ContentView(R.layout.activity_alarm_center)
public class AlarmCenterActivity extends BaseActivity {
    @ViewInject(R.id.rv_alarm_center)
    private RecyclerView rv_alarm_center;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        showNameTvRight(MyApplication.getUserName());

        mInitRecyclerView(rv_alarm_center, 2);

        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_RECORD_BY_USERID), map, spin_kit, new onGetDevicesXCallBack());
    }

    /**
     * 获取指定用户的所有设备
     */
    private class onGetDevicesXCallBack implements HttpHelper.XCallBack {
        private List<RecordInfo> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, RecordInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if (null != list && 0 < list.size()) {
                setCaption("告警中心(" + list.size() + ")");
                AlarmCenterAdapter alarmCenterAdapter = new AlarmCenterAdapter(AlarmCenterActivity.this, list);
                rv_alarm_center.setAdapter(alarmCenterAdapter);
            } else {
                setCaption("告警中心");
                rv_alarm_center.setAdapter(null);
            }
        }
    }
}
