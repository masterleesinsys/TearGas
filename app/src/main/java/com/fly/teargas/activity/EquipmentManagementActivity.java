package com.fly.teargas.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fly.teargas.R;
import com.fly.teargas.adapter.EquimentManagementAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("设备管理");

        mInitRecyclerView(rv_equipment_management, 2);

        clearColor();
        tv_entire.setBackgroundResource(R.color.titleBar_checked);

        equimentManagementAdapter = new EquimentManagementAdapter(this, 0);
        rv_equipment_management.setAdapter(equimentManagementAdapter);

        equimentManagementAdapter.setOnItemClickListener(new EquimentManagementAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
    }

    @Event(value = {R.id.tv_entire, R.id.tv_normal, R.id.tv_abnormal})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent=null;
        switch (view.getId()) {
            case R.id.tv_entire:    //全部
                clearColor();
                tv_entire.setBackgroundResource(R.color.titleBar_checked);

                equimentManagementAdapter = new EquimentManagementAdapter(this, 0);
                rv_equipment_management.setAdapter(equimentManagementAdapter);

                equimentManagementAdapter.setOnItemClickListener(new EquimentManagementAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {

                    }
                });
                break;
            case R.id.tv_normal:    //正常
                clearColor();
                tv_normal.setBackgroundResource(R.color.titleBar_checked);

                equimentManagementAdapter = new EquimentManagementAdapter(this, 1);
                rv_equipment_management.setAdapter(equimentManagementAdapter);

                equimentManagementAdapter.setOnItemClickListener(new EquimentManagementAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {

                    }
                });
                break;
            case R.id.tv_abnormal:  //异常
                clearColor();
                tv_abnormal.setBackgroundResource(R.color.titleBar_checked);

                equimentManagementAdapter = new EquimentManagementAdapter(this, 2);
                rv_equipment_management.setAdapter(equimentManagementAdapter);

                equimentManagementAdapter.setOnItemClickListener(new EquimentManagementAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {

                    }
                });
                break;
        }
    }

    private void clearColor() {
        tv_entire.setBackgroundResource(R.color.titleBar_unChecked);
        tv_normal.setBackgroundResource(R.color.titleBar_unChecked);
        tv_abnormal.setBackgroundResource(R.color.titleBar_unChecked);
    }
}
