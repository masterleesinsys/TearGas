package com.fly.teargas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.UserInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("修改密码");
    }

    @Event(value = {R.id.tv_pwmodi_determine})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_pwmodi_determine:
//                String oldpassowrd = et_pwmodi_oldpassword.getText().toString().trim();
//                String newpassowrd = et_pwmodi_setpassword.getText().toString().trim();
//                if (oldpassowrd.equals("") || et_pwmodi_setpassword.getText().toString().equals("")) {
//                    showToastText("您的密码输入有误,请重新输入!");
//                    et_pwmodi_oldpassword.setText("");
//                    return;
//                }
//                if (oldpassowrd.equals(newpassowrd)) {
//                    showToastText("您输入的新密码与旧密码相同,请重新输入!");
//                    return;
//                }
//                if (!newpassowrd.equals(et_pwmodi_againpassword.getText().toString().trim())) {
//                    showToastText("您俩次输入的密码不一致,请重新输入!");
//                    return;
//                }
//                if (newpassowrd.length() < 6) {
//                    showToastText("请输入不少于六位密码!");
//                    return;
//                }
//                HashMap<String, Object> params2 = new HashMap<>();
//                params2.put("password", oldpassowrd);
//                params2.put("new_password", newpassowrd);
//                HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.USER_PASSWORD_CHANGE), params2, spin_kit, new onModiPasswordCallback());

                showToastText("修改成功，请重新登录");

                MyApplication.userLogout();
                MyApplication.is_exit = true;
                openActivity(LoginActivity.class);

                break;
        }
    }

    /**
     * 修改密码
     */
    private class onModiPasswordCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            UserInfo user = null;
            try {
                user = getHttpResult(result, UserInfo.class);
                user.setId(user.getUser());
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }

            //同步服务器返回的最新token
            UserInfo localuser = MyApplication.getDetailFromLocal();
            if (null != localuser)
                localuser.setToken(user.getToken());
            MyApplication.syncDetail(localuser);
            showToastText("修改成功");
            finish();
        }
    }
}
