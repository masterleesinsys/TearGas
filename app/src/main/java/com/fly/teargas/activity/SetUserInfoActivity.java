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

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String name = "";
    private String tel = "";
    private String qx = "";
    private String dq = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("修改用户信息");

        if (getIntent().hasExtra("name"))
            name = getIntent().getStringExtra("name");
        if (getIntent().hasExtra("tel"))
            tel = getIntent().getStringExtra("tel");
        if (getIntent().hasExtra("qx"))
            qx = getIntent().getStringExtra("qx");
        if (getIntent().hasExtra("dq"))
            dq = getIntent().getStringExtra("dq");

        et_fullName.setText(name);
        et_tel.setText(tel);
        tv_competence.setText(qx);
        tv_area.setText(dq);
    }

    @Event(value = {R.id.tv_competence, R.id.tv_area, R.id.tv_save})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
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
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("tel", tel);
                map.put("diqu", competence);
                map.put("quanxian", area);
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.SET_USER_BY_ID), map, spin_kit, new onSetUserByIdXCallBack());
                break;
        }
    }

    /**
     * 修改用户信息
     */
    private class onSetUserByIdXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            LogUtils.e(result);
            Boolean isSet = false;
            try {
                isSet = getHttpResultBoolean(result);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (isSet) {
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
                new AlertView("错误提示", "未获取到权限列表，请重试", null, new String[]{"确定"}, null, SetUserInfoActivity.this, AlertView.Style.Alert, null).show();
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
                new AlertView("错误提示", "未获取到地区列表，请重试", null, new String[]{"确定"}, null, SetUserInfoActivity.this, AlertView.Style.Alert, null).show();
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
}
