package com.fly.teargas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 修改密码
 */
@ContentView(R.layout.activity_password_modi)
public class PasswordModiActivity extends BaseActivity {
    @ViewInject(R.id.et_pwmodi_oldpassword)
    private EditText et_pwmodi_oldpassword;     //旧密码

    @ViewInject(R.id.et_pwmodi_setpassword)
    private EditText et_pwmodi_setpassword;     //设置密码

    @ViewInject(R.id.et_pwmodi_againpassword)
    private EditText et_pwmodi_againpassword;   //再次输入

    @ViewInject(R.id.tv_pwmodi_determine)
    private TextView tv_pwmodi_determine;   //确认

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String username = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("修改密码");

        et_pwmodi_oldpassword.setText("admin");
        et_pwmodi_setpassword.setText("admins");
        et_pwmodi_againpassword.setText("admins");

        if (getIntent().hasExtra("username"))
            username =getIntent().getStringExtra("username");
    }

    @Event(value = {R.id.tv_pwmodi_determine})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_pwmodi_determine:
                String oldpassowrd = et_pwmodi_oldpassword.getText().toString().trim();
                String newpassowrd = et_pwmodi_setpassword.getText().toString().trim();
                String againpassword = et_pwmodi_againpassword.getText().toString().trim();
                if (oldpassowrd.equals("") || newpassowrd.equals("") || againpassword.equals("")) {
                    showToastText("您的密码输入有误,请重新输入!");
                    et_pwmodi_oldpassword.setText("");
                    return;
                }
                if (oldpassowrd.equals(newpassowrd)) {
                    showToastText("您输入的新密码与旧密码相同,请重新输入!");
                    return;
                }
                if (!newpassowrd.equals(againpassword)) {
                    showToastText("您俩次输入的密码不一致,请重新输入!");
                    return;
                }
//                if (newpassowrd.length() < 6) {
//                    showToastText("请输入不少于六位密码!");
//                    return;
//                }
                HashMap<String, Object> params = new HashMap<>();
                params.put("username", username);
                params.put("oldpassword", oldpassowrd);
                params.put("newpassword", newpassowrd);
                params.put("confirmpassword", againpassword);
                HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.CHANGE_PASSWORD), params, spin_kit, new onChangePasswordCallback());

//                MyApplication.userLogout();
//                MyApplication.is_exit = true;
//                openActivity(LoginActivity.class);

                break;
        }
    }

    /**
     * 修改密码
     */
    private class onChangePasswordCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            LogUtils.e(result);

//            //同步服务器返回的最新token
//            UserInfo localuser = MyApplication.getDetailFromLocal();
//            if (null != localuser)
//                localuser.setToken(user.getToken());
//            MyApplication.syncDetail(localuser);
            showToastText("修改成功，请重新登录");
            finish();
        }
    }
}
