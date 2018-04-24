package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.R;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 信息修改
 */
@ContentView(R.layout.activity_information_modification)
public class InformationModificationActivity extends BaseActivity {
    @ViewInject(R.id.tv_selectTemplate)
    private TextView tv_selectTemplate; //选择模版
    @ViewInject(R.id.tv_set_stencil)
    private TextView tv_set_stencil; //修改当前模版
    @ViewInject(R.id.tv_save)
    private TextView tv_save; //保存

    private AlertView alertView=null;

    private String deviceID = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("管理");
    }

    @Event(value = {R.id.tv_save, R.id.tv_set_stencil, R.id.tv_selectTemplate})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_selectTemplate:    //选择模版
                showAlertView();
                break;
            case R.id.tv_set_stencil:    //修改当前模版
                openActivity(SetStencilActivity.class);
                break;
            case R.id.tv_save:    //保存
                finish();
                showToastText("已保存");
                break;
        }
    }

    private void showAlertView() {
        alertView=new AlertView(null, null, null, null, new String[]{"添加模版", "模版一","模版二","模版三"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                switch (position) {
                    case 0:
                        alertView.dismiss();
                        openActivity(SetStencilActivity.class);
                        break;
                    case 1:
                        tv_selectTemplate.setText("模版一");
                        break;
                    case 2:
                        tv_selectTemplate.setText("模版二");
                        break;
                    case 3:
                        tv_selectTemplate.setText("模版三");
                        break;
                }
            }
        });
        alertView.show();
    }
}
