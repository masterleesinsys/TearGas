package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加用户
 */
@ContentView(R.layout.activity_add_user)
public class AddUserActivity extends BaseActivity {
    @ViewInject(R.id.et_userName)
    private EditText et_userName;   //账号
    @ViewInject(R.id.et_password)
    private TextView et_password;   //密码
    @ViewInject(R.id.et_fullName)
    private EditText et_fullName;   //名称
    @ViewInject(R.id.et_tel)
    private EditText et_tel;    //电话
    @ViewInject(R.id.tv_competence)
    private TextView tv_competence; //权限
    @ViewInject(R.id.tv_area)
    private TextView tv_area;   //地区
    @ViewInject(R.id.tv_save)
    private TextView tv_save;       //保存

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("添加用户");
    }

    @Event(value = {R.id.tv_competence, R.id.tv_area, R.id.tv_save})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        String userName = et_userName.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String name = et_fullName.getText().toString().trim();
        String tel = et_tel.getText().toString().trim();
        String competence = tv_competence.getText().toString().trim();
        String area = tv_area.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_competence:    //权限
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_ALL_QUAN_XIAN), null, spin_kit, new onGetAllQuanXainXCallBack());
                break;
            case R.id.tv_area:    //地区
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_ALL_DI_QU), null, spin_kit, new onGetAllDiQuXCallBack());
                break;
            case R.id.tv_save:      //添加
                if ("".equals(userName)) {
                    new AlertView("错误提示", "请填写用户账号", null, new String[]{"确定"}, null, this,
                            AlertView.Style.Alert, null).show();
                    return;
                } else if ("".equals(password)) {
                    new AlertView("错误提示", "请填写用户密码", null, new String[]{"确定"}, null, this,
                            AlertView.Style.Alert, null).show();
                    return;
                } else if ("".equals(name)) {
                    new AlertView("错误提示", "请填写用户姓名", null, new String[]{"确定"}, null, this,
                            AlertView.Style.Alert, null).show();
                    return;
                } else if ("".equals(tel)) {
                    new AlertView("错误提示", "请填写用户电话", null, new String[]{"确定"}, null, this,
                            AlertView.Style.Alert, null).show();
                    return;
                } else if ("无".equals(competence)) {
                    new AlertView("错误提示", "请选择用户权限", null, new String[]{"确定"}, null, this,
                            AlertView.Style.Alert, null).show();
                    return;
                } else if ("未知".equals(area)) {
                    new AlertView("错误提示", "请选择用户管辖范围", null, new String[]{"确定"}, null, this,
                            AlertView.Style.Alert, null).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("username", userName);
                map.put("password", password);
                map.put("name", name);
                map.put("tel", tel);
                map.put("diqu", area);
                map.put("quanxian", competence);
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.ADD_USER), map, spin_kit, new onAddUserXCallBack());
                break;
        }
    }

    /**
     * 获取所有权限列表
     */
    private class onGetAllQuanXainXCallBack implements HttpHelper.XCallBack {
        private List<String> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != list && 0 < list.size()) {
                showAlertView(list, 0);
            } else {
                showToastText("未获取到权限列表，请重试");
            }
        }
    }

    /**
     * 获取所有地区列表
     */
    private class onGetAllDiQuXCallBack implements HttpHelper.XCallBack {
        private List<String> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != list && 0 < list.size()) {
                showAlertView(list, 1);
            } else {
                showToastText("未获取到地区列表，请重试");
            }
        }
    }

    private void showAlertView(final List<String> list, int type) {
        String[] str = new String[]{};
        str = list.toArray(str);
        switch (type) {
            case 0:
                new AlertView(null, null, null, null, str, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        tv_competence.setText(list.get(position));
                    }
                }).show();
                break;
            case 1:
                new AlertView(null, null, null, null, str, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        tv_area.setText(list.get(position));
                    }
                }).show();
                break;
        }
    }

    /**
     * 添加用户
     */
    private class onAddUserXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data;
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if (null != data) {
                finish();
                showToastText("添加成功");
            }
        }
    }
}
