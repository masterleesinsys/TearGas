package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 添加用户
 */
@ContentView(R.layout.activity_add_user)
public class AddUserActivity extends BaseActivity {
    @ViewInject(R.id.et_fullName)
    private EditText et_fullName;   //姓名
    @ViewInject(R.id.tv_position)
    private TextView tv_position;   //选择职位
    @ViewInject(R.id.tv_save)
    private TextView tv_save;       //保存
    @ViewInject(R.id.tv_activation)
    private TextView tv_activation;   //激活此账户
    @ViewInject(R.id.tv_freeze)
    private TextView tv_freeze;   //冻结此账户

    @ViewInject(R.id.sv_addUser)
    private ScrollView sv_addUser;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("添加用户");

        OverScrollDecoratorHelper.setUpOverScroll(sv_addUser);

        if (getIntent().hasExtra("name")) {
            tv_freeze.setVisibility(View.VISIBLE);
            et_fullName.setText(getIntent().getStringExtra("name"));
        } else {
            tv_freeze.setVisibility(View.GONE);
        }
    }

    @Event(value = {R.id.tv_position, R.id.tv_save, R.id.tv_activation,R.id.tv_freeze})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_position:  //选择职位
                showAlertView();
                break;
            case R.id.tv_save:      //保存
                showToastText("保存成功");
                break;
            case R.id.tv_activation:    //激活此账户
                showToastText("已成功激活");
                break;
            case R.id.tv_freeze:    //冻结此账户
                showToastText("已成功冻结");
                break;
        }
    }

    private void showAlertView() {
        new AlertView(null, null, null, null, new String[]{"管理员", "代维文员"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                switch (position) {
                    case 0:
                        tv_position.setText("管理员");
                        break;
                    case 1:
                        tv_position.setText("代维文员");
                        break;
                }
            }
        }).show();
    }
}
