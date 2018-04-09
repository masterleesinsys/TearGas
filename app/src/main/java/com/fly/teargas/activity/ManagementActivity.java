package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.R;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 管理
 */
@ContentView(R.layout.activity_management)
public class ManagementActivity extends BaseActivity {
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

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("管理");
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
                            showToastText("已布防");
                        }
                    }
                }).show();
                break;
            case R.id.ll_withdrawAGarrison:     //撤防
                new AlertView("是否撤防", "撤防前请确认有工作人员入内", "取消", new String[]{"确定"}, null, ManagementActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            showToastText("已撤防");
                        }
                    }
                }).show();
                break;
            case R.id.ll_bomb:     //爆弹
                new AlertView("是否爆弹", "爆弹前请确认是否为犯罪分子盗窃，请选择爆弹通道", "取消", new String[]{"弹1", "弹2", "弹3", "空调"}, null, ManagementActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            showToastText("已爆弹");
                        }
                    }
                }).show();
                break;
            case R.id.ll_deviceStatus:     //设备状态
                openActivity(DeviceStatusActivity.class);
                break;
            case R.id.ll_informationModification:     //信息修改
                openActivity(InformationModificationActivity.class);
                break;
            case R.id.ll_logInformation:     //日志信息
                openActivity(LogInformationActivity.class);
                break;
        }
    }
}
