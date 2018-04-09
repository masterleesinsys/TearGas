package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.HttpHelper;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 个人中心
 */
@ContentView(R.layout.activity_personal)
public class PersonalActivity extends BaseActivity {
    @ViewInject(R.id.iv_headImage)
    private ImageView iv_headImage;

    @ViewInject(R.id.tv_account)
    private TextView tv_account;    //当前账户
    @ViewInject(R.id.tv_username)
    private TextView tv_username;    //用户姓名
    @ViewInject(R.id.tv_sex)
    private TextView tv_sex;            //性别
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

    private String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523218080777&di=65948e4c13975bd29e5833c2631ff9c2&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-29%2F121415995.jpg";

    @Override
    protected void initView() {
        setStyle(STYLE_HOME);
        setCaption("个人中心");

        HttpHelper.getInstance().mLoadPicture(url,iv_headImage);
    }

    @Event(value = {R.id.tv_logout,R.id.ll_setPassword,R.id.ll_addUser,R.id.ll_accountInformation})
    private void onClick(View view){
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent=null;
        switch (view.getId()){
            case R.id.ll_setPassword:   //修改密码
                openActivity(PasswordModiActivity.class);
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
