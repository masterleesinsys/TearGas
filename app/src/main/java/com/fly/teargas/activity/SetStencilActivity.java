package com.fly.teargas.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.entity.ModelInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置模版
 */
@ContentView(R.layout.activity_set_stencil)
public class SetStencilActivity extends BaseActivity {
    @ViewInject(R.id.tv_save)
    private TextView tv_save; //保存

    @ViewInject(R.id.et_IN1)
    private EditText et_IN1;
    @ViewInject(R.id.et_OUT1)
    private EditText et_OUT1;

    @ViewInject(R.id.et_IN2)
    private EditText et_IN2;
    @ViewInject(R.id.et_OUT2)
    private EditText et_OUT2;

    @ViewInject(R.id.et_IN3)
    private EditText et_IN3;
    @ViewInject(R.id.et_OUT3)
    private EditText et_OUT3;

    @ViewInject(R.id.et_IN4)
    private EditText et_IN4;
    @ViewInject(R.id.et_OUT4)
    private EditText et_OUT4;

    @ViewInject(R.id.et_IN5)
    private EditText et_IN5;
    @ViewInject(R.id.et_OUT5)
    private EditText et_OUT5;

    @ViewInject(R.id.et_IN6)
    private EditText et_IN6;
    @ViewInject(R.id.et_OUT6)
    private EditText et_OUT6;

    @ViewInject(R.id.et_IN7)
    private EditText et_IN7;
    @ViewInject(R.id.et_OUT7)
    private EditText et_OUT7;

    @ViewInject(R.id.et_IN8)
    private EditText et_IN8;
    @ViewInject(R.id.et_OUT8)
    private EditText et_OUT8;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String modelID = "";

    private String type = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);

        if (getIntent().hasExtra("type"))
            type = getIntent().getStringExtra("type");
        if (getIntent().hasExtra("modelID"))
            modelID = getIntent().getStringExtra("modelID");

        switch (type) {
            case "add":
                setCaption("添加模版");
                break;
            case "set":
                setCaption("修改模版" + modelID);

                getRightTvView();
                setTitleBarRightTvVisibility(View.VISIBLE);
                setTitleBarRightTvText("删除");

                setOnTitleBarRightTvListener(new onTitleBarRightTvListener() {
                    @Override
                    public void onTitleBarRightTvListener() {
                        Map<String, String> map = new HashMap<>();
                        map.put("modelID", modelID);
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.DEL_MODEL), map, spin_kit, new onDelModelXCallBack());
                    }
                });

                Map<String, String> map = new HashMap<>();
                map.put("modelID", modelID);
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_MODEL), map, spin_kit, new onGetModelXCallBack());
                break;
        }
    }

    @Event(value = {R.id.tv_save})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        String IN1 = et_IN1.getText().toString().trim();
        String IN2 = et_IN2.getText().toString().trim();
        String IN3 = et_IN3.getText().toString().trim();
        String IN4 = et_IN4.getText().toString().trim();
        String IN5 = et_IN5.getText().toString().trim();
        String IN6 = et_IN6.getText().toString().trim();
        String IN7 = et_IN7.getText().toString().trim();
        String IN8 = et_IN8.getText().toString().trim();

        String OUT1 = et_OUT1.getText().toString().trim();
        String OUT2 = et_OUT2.getText().toString().trim();
        String OUT3 = et_OUT3.getText().toString().trim();
        String OUT4 = et_OUT4.getText().toString().trim();
        String OUT5 = et_OUT5.getText().toString().trim();
        String OUT6 = et_OUT6.getText().toString().trim();
        String OUT7 = et_OUT7.getText().toString().trim();
        String OUT8 = et_OUT8.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_save:    //保存
                Map<String, String> map = new HashMap<>();
                switch (type) {
                    case "add":
                        map.put("in1", IN1);
                        map.put("in2", IN2);
                        map.put("in3", IN3);
                        map.put("in4", IN4);
                        map.put("in5", IN5);
                        map.put("in6", IN6);
                        map.put("in7", IN7);
                        map.put("in8", IN8);
                        map.put("out1", OUT1);
                        map.put("out2", OUT2);
                        map.put("out3", OUT3);
                        map.put("out4", OUT4);
                        map.put("out5", OUT5);
                        map.put("out6", OUT6);
                        map.put("out7", OUT7);
                        map.put("out8", OUT8);
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.ADD_MODEL_BY_ID), map, spin_kit, new onAddModelByIdXCallBack());
                        break;
                    case "set":
                        map.put("modelID", modelID);
                        map.put("in1", IN1);
                        map.put("in2", IN2);
                        map.put("in3", IN3);
                        map.put("in4", IN4);
                        map.put("in5", IN5);
                        map.put("in6", IN6);
                        map.put("in7", IN7);
                        map.put("in8", IN8);
                        map.put("out1", OUT1);
                        map.put("out2", OUT2);
                        map.put("out3", OUT3);
                        map.put("out4", OUT4);
                        map.put("out5", OUT5);
                        map.put("out6", OUT6);
                        map.put("out7", OUT7);
                        map.put("out8", OUT8);
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.SET_MODEL_BY_ID), map, spin_kit, new onSetModelByIdXCallBack());
                        break;
                }
                break;
        }
    }

    /**
     * 获取指定模版
     */
    private class onGetModelXCallBack implements HttpHelper.XCallBack {
        private ModelInfo modelInfo = null;

        @Override
        public void onResponse(String result) {
            try {
                modelInfo = getHttpResult(result, ModelInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != modelInfo) {
                et_IN1.setText(modelInfo.getIn1());
                et_IN2.setText(modelInfo.getIn2());
                et_IN3.setText(modelInfo.getIn3());
                et_IN4.setText(modelInfo.getIn4());
                et_IN5.setText(modelInfo.getIn5());
                et_IN6.setText(modelInfo.getIn6());
                et_IN7.setText(modelInfo.getIn7());
                et_IN8.setText(modelInfo.getIn8());

                et_OUT1.setText(modelInfo.getIn1());
                et_OUT2.setText(modelInfo.getIn2());
                et_OUT3.setText(modelInfo.getIn3());
                et_OUT4.setText(modelInfo.getIn4());
                et_OUT5.setText(modelInfo.getIn5());
                et_OUT6.setText(modelInfo.getIn6());
                et_OUT7.setText(modelInfo.getIn7());
                et_OUT8.setText(modelInfo.getIn8());
            } else {
                showToastText("未获取到模版信息，请重试");
            }
        }
    }

    /**
     * 修改模版信息
     */
    private class onSetModelByIdXCallBack implements HttpHelper.XCallBack {
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
     * 删除模版
     */
    private class onDelModelXCallBack implements HttpHelper.XCallBack {
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
                showToastText("删除成功");
                finish();
            } else {
                showToastText("删除失败，请重试");
            }
        }
    }

    /**
     * 添加模版
     */
    private class onAddModelByIdXCallBack implements HttpHelper.XCallBack {
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
                showToastText("添加成功");
                finish();
            } else {
                showToastText("添加失败，请重试");
            }
        }
    }
}
