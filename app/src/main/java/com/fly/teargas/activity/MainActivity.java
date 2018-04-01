package com.fly.teargas.activity;

import android.view.View;
import android.widget.TextView;

import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.DESUtil;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.tv_encryption)
    private TextView tv_encryption;

    private String tokenTest = "26e948f0f9fa431bb161c42ee918fd1c";

    @Override
    protected void initView() {

    }

    @Event(value = {R.id.tv_encryption})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.tv_encryption:    //验证token
                certificateToken();
                break;
        }
    }

    /**
     * 验证token
     */
    private void certificateToken() {
        String newToken = DESUtil.chenMengEncrypt(tokenTest);
        Map<String, String> map = new HashMap<>();
        map.put("token", newToken);
        HttpHelper.getInstance().get(MyApplication.getURL(Constants.MOBILE_TOKEN), map, new HttpHelper.XCallBack() {
            @Override
            public void onResponse(String result) {
                LogUtils.e(result);
            }
        });
    }
}
