package com.fly.teargas.activity;

import android.support.v7.widget.RecyclerView;

import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.github.ybq.android.spinkit.SpinKitView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

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
        setCaption("告警中心");
        showNameTvRight(MyApplication.getUserName());

        mInitRecyclerView(rv_alarm_center, 2);
    }
}
