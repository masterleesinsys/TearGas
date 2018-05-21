package com.fly.teargas.activity;

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
 * 修改用户信息
 */
@ContentView(R.layout.activity_set_user_info)
public class SetUserInfoActivity extends BaseActivity {
    @ViewInject(R.id.et_fullName)
    private EditText et_fullName;   //名称
    @ViewInject(R.id.et_tel)
    private EditText et_tel;    //电话
    @ViewInject(R.id.tv_competence)
    private TextView tv_competence; //权限
    @ViewInject(R.id.tv_area)
    private TextView tv_area;   //地区
    @ViewInject(R.id.tv_save)
    private TextView tv_save;   //保存
    @ViewInject(R.id.tv_activation)
    private TextView tv_activation;   //激活
    @ViewInject(R.id.tv_freeze)
    private TextView tv_freeze;   //冻结

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String userID = "";
    private String name = "";
    private String tel = "";
    private String qx = "";
    private String dq = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("修改用户信息");

        showQueryTv();
        setTitleBarRightTvVisibility(View.VISIBLE);
        setTitleBarRightTvText("删除");

        if (getIntent().hasExtra("userID"))
            userID = getIntent().getStringExtra("userID");
        if (getIntent().hasExtra("name"))
            name = getIntent().getStringExtra("name");
        if (getIntent().hasExtra("tel"))
            tel = getIntent().getStringExtra("tel");
        if (getIntent().hasExtra("qx"))
            qx = getIntent().getStringExtra("qx");
        if (getIntent().hasExtra("dq"))
            dq = getIntent().getStringExtra("dq");

        setOnTitleBarRightTvListener(new onTitleBarRightTvListener() {
            @Override
            public void onTitleBarRightTvListener() {
                new AlertView("删除账户", "是否确认删除该账户?", "取消", new String[]{"确定"}, null, SetUserInfoActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position >= 0) {
                            Map<String, String> map = new HashMap<>();
                            map.put("userID", userID);
                            HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.DEL_USER), map, spin_kit, new onDelUserXCallBack());
                        }
                    }
                }).show();
            }
        });

        et_fullName.setText(name);
        et_tel.setText(tel);
        tv_competence.setText(qx);
        tv_area.setText(dq);
    }

    @Event(value = {R.id.tv_competence, R.id.tv_area, R.id.tv_save, R.id.tv_activation, R.id.tv_freeze})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Map<String, String> map = null;
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
            case R.id.tv_save:    //保存
                if ("".equals(name)) {
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
                map = new HashMap<>();
                map.put("name", name);
                map.put("tel", tel);
                map.put("diqu", area);
                map.put("quanxian", competence);
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.SET_USER_BY_ID), map, spin_kit, new onSetUserByIdXCallBack());
                break;
            case R.id.tv_activation:    //激活
                map = new HashMap<>();
                map.put("userID", userID);
                map.put("state", "1");
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.SET_USER_STATE), map, spin_kit, new onSetUserState1XCallBack());
                break;
            case R.id.tv_freeze:    //冻结
                map = new HashMap<>();
                map.put("userID", userID);
                map.put("state", "0");
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.SET_USER_STATE), map, spin_kit, new onSetUserState0XCallBack());
                break;
        }
    }

    /**
     * 修改用户信息
     */
    private class onSetUserByIdXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if ("{}".equals(data))
                return;
            if ("true".equals(data)) {
                finish();
                showToastText("修改成功");
            } else {
                showToastText("修改失败，请重试");
            }
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
     * 删除账户
     */
    private class onDelUserXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if ("{}".equals(data))
                return;
            if ("true".equals(data)) {
                finish();
                showToastText("删除成功");
                finish();
            } else {
                showToastText("删除失败，请重试");
            }
        }
    }

    /**
     * 激活
     */
    private class onSetUserState1XCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if ("{}".equals(data))
                return;
            if ("true".equals(data)) {
                finish();
                showToastText("激活成功");
            } else {
                showToastText("激活失败，请重试");
            }
        }
    }

    /**
     * 冻结
     */
    private class onSetUserState0XCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = "";
            try {
                data = getHttpResultList(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
                return;
            }
            if ("{}".equals(data))
                return;
            if ("true".equals(data)) {
                finish();
                showToastText("冻结成功");
            } else {
                showToastText("冻结失败，请重试");
            }
        }
    }
}
