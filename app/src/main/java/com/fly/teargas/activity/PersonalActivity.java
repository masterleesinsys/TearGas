package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.UserInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 个人中心
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_personal)
public class PersonalActivity extends BaseActivity {
    @ViewInject(R.id.tv_account)
    private TextView tv_account;    //当前账户
    @ViewInject(R.id.tv_username)
    private TextView tv_username;    //用户姓名
    @ViewInject(R.id.tv_userType)
    private TextView tv_userType;    //用户身份
    @ViewInject(R.id.tv_registerTime)
    private TextView tv_registerTime;    //用户身份
    @ViewInject(R.id.tv_tel)
    private TextView tv_tel;            //联系电话
    @ViewInject(R.id.tv_numberOfDevices)
    private TextView tv_numberOfDevices;    //当前管理设备数

    @ViewInject(R.id.tv_logout)
    private TextView tv_logout;    //退出账户

    @ViewInject(R.id.ll_setPassword)
    private LinearLayout ll_setPassword;    //修改密码
    @ViewInject(R.id.ll_addUser)
    private LinearLayout ll_addUser;        //添加用户
    @ViewInject(R.id.ll_accountInformation)
    private LinearLayout ll_accountInformation;    //查看所有账户

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private AlertView mAlertView = null;

    private String username = "";

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("个人中心");
        showNameTv("张三");
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_USER), null, spin_kit, new getUserXCallBack());
    }

    @Event(value = {R.id.tv_logout, R.id.ll_setPassword, R.id.ll_addUser, R.id.ll_accountInformation})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_setPassword:   //修改密码
                intent = new Intent();
                intent.putExtra("username", username);
                openActivity(intent, PasswordModiActivity.class);
                break;
            case R.id.ll_addUser:   //添加用户
                openActivity(AddUserActivity.class);
                break;
            case R.id.ll_accountInformation:    //查看所有账户
                openActivity(AccountInformationActivity.class);
                break;
            case R.id.tv_logout:    //退出账户
                logout();
                break;
        }
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
                username = userInfo.getUser();

                tv_account.setText(userInfo.getUser());
                tv_username.setText(userInfo.getName());
                tv_userType.setText(userInfo.getQx());
                tv_registerTime.setText(userInfo.getRegisterTime());
                tv_tel.setText(userInfo.getTel());
                tv_numberOfDevices.setText(userInfo.getDq());
            }
        }
    }

    /**
     * 退出账户
     */
    private void logout() {
        mAlertView = null;
        mAlertView = new AlertView("退出账户", "是否确认退出当前账户?", "取消", new String[]{"确定"}, null, PersonalActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    MyApplication.userLogout();

                    MyApplication.is_exit = true;
                    openActivity(LoginActivity.class);
                } else {
                    mAlertView.dismiss();
                }
            }
        });
        mAlertView.show();
    }
}
