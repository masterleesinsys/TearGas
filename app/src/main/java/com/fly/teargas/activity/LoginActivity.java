package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.teargas.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 登录注册
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @ViewInject(R.id.iv_logo)
    private ImageView iv_logo;

    @ViewInject(R.id.et_mailbox)
    private EditText et_mailbox;
    @ViewInject(R.id.et_password)
    private EditText et_password;

    @ViewInject(R.id.cb_rememberPassword)
    private CheckBox cb_rememberPassword;

    @ViewInject(R.id.tv_login)
    private TextView tv_login;

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("登录");
    }

    @Event(value = {R.id.iv_logo, R.id.tv_login})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_login: //登录
                openActivity(MainActivity.class);
                break;
        }
    }
}
