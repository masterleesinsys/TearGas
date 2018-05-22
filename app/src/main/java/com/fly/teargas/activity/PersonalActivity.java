package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.UserInfo;
import com.fly.teargas.service.VerUpdateService;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.fly.teargas.util.Tools;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.fly.teargas.MyApplication.ROOT_PATH;

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
    private TextView tv_numberOfDevices;    //当前用户权限
    @ViewInject(R.id.tv_versionNumber)
    private TextView tv_versionNumber;    //版本更新

    @ViewInject(R.id.tv_logout)
    private TextView tv_logout;    //退出账户

    @ViewInject(R.id.ll_setPassword)
    private LinearLayout ll_setPassword;    //修改密码
    @ViewInject(R.id.ll_addUser)
    private LinearLayout ll_addUser;        //添加用户
    @ViewInject(R.id.ll_accountInformation)
    private LinearLayout ll_accountInformation;    //查看所有账户
    @ViewInject(R.id.ll_updata)
    private LinearLayout ll_updata;     //检查更新

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private AlertView mAlertView = null;

    private String username = "";

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("个人中心");
        showNameTvLift(MyApplication.getUserName());

        tv_versionNumber.setText("版本号:" + Tools.getVersionName(this) + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_USER), null, spin_kit, new getUserXCallBack());
    }

    @Event(value = {R.id.tv_logout, R.id.ll_setPassword, R.id.ll_addUser, R.id.ll_accountInformation, R.id.ll_updata})
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
            case R.id.ll_updata:    //检查更新
                MyApplication.DIALOG_NEW_VER = true;
                VersionParams.Builder builder = new VersionParams.Builder()
                        .setRequestUrl(MyApplication.getURL(Constants.GET_LATEST_APP))
                        .setService(VerUpdateService.class);
                stopService(new Intent(this, VerUpdateService.class));
                builder.setPauseRequestTime(Long.valueOf(0));
                builder.setDownloadAPKPath(ROOT_PATH);
                AllenChecker.startVersionCheck(this, builder.build());
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

                if ("超级管理员".equals(userInfo.getQx())) {
                    ll_addUser.setVisibility(View.VISIBLE);
                    ll_accountInformation.setVisibility(View.VISIBLE);
                } else {
                    ll_addUser.setVisibility(View.GONE);
                    ll_accountInformation.setVisibility(View.GONE);
                }
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
