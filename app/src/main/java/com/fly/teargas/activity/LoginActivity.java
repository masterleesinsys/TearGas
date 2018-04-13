package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.fly.teargas.util.SharedPreferencesUtils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

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

    private String mailbox = "";
    private String password = "";

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("登录");

        //仅供测试使用，待删除
        et_mailbox.setText("admin");
        et_password.setText("admin");
    }

    @Event(value = {R.id.iv_logo, R.id.tv_login})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        mailbox = et_mailbox.getText().toString().trim();
        password = et_password.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_login: //登录
                if ("".equals(mailbox)) {
                    showToastText("账户不能为空");
                    return;
                } else if ("".equals(password)) {
                    showToastText("密码不能为空");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("username", mailbox);
                map.put("password", password);
                HttpHelper.getInstance().upLoadFile(MyApplication.getURL(Constants.GET_TOKEN), map, null, spin_kit, new getTokenXCallBack());
                break;
        }
    }

    /**
     * 获取账户token
     */
    private class getTokenXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (!"".equals(data)) {
                //记住密码
                rememberPassword();

                //缓存token信息
                MyApplication.syncDetail(data);
                openActivity(MainActivity.class);
            }
        }
    }

    /**
     * 记住密码
     */
    private void rememberPassword() {
        //如果被选中，则记录当前账户密码；否则清空混存账户密码
        if (cb_rememberPassword.isSelected()) {
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_USERNAME, mailbox);
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_PASSWORD, password);
        } else {
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_USERNAME, "");
            SharedPreferencesUtils.save(SharedPreferencesUtils.CONFIG_REMBER_PASSWORD, "");
        }
    }
}
