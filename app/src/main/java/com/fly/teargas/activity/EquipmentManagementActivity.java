package com.fly.teargas.activity;

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
import com.fly.teargas.entity.UserInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("设备管理");

        mInitRecyclerView(rv_equipment_management, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_USER), null, spin_kit, new getUserXCallBack());
        clearColor();
        tv_entire.setBackgroundResource(R.color.titleBar_checked);
        getDevices(0);
    }

    /**
     * 获取账户信息
     */
    private class getUserXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            UserInfo userInfo = null;
            try {
                userInfo = getHttpResult(result, UserInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != userInfo) {
                showNameTv(userInfo.getName());
            }
        }
    }

    private void getDevices(final int type) {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_DEVICES), map, spin_kit, new HttpHelper.XCallBack() {
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
                    equimentManagementAdapter = new EquimentManagementAdapter(EquipmentManagementActivity.this, type, list);
                    rv_equipment_management.setAdapter(equimentManagementAdapter);

                    equimentManagementAdapter.setOnItemClickListener(new EquimentManagementAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClickListener(int position) {
                            Intent intent = new Intent();
                            intent.putExtra("lat", list.get(position).getLat());
                            intent.putExtra("lng", list.get(position).getLng());
                            openActivity(intent, MainActivity.class);
                        }
                    });
                } else {
                    rv_equipment_management.setAdapter(null);
                }
            }
        });
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

    private void clearColor() {
        tv_entire.setBackgroundResource(R.color.titleBar_unChecked);
        tv_normal.setBackgroundResource(R.color.titleBar_unChecked);
        tv_abnormal.setBackgroundResource(R.color.titleBar_unChecked);
    }
}
