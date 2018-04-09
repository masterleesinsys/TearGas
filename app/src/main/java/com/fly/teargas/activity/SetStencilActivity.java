package com.fly.teargas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fly.teargas.R;
import com.skydoves.elasticviews.ElasticAction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 设置模版
 */
@ContentView(R.layout.activity_set_stencil)
public class SetStencilActivity extends BaseActivity {
    @ViewInject(R.id.tv_save)
    private TextView tv_save; //保存

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("管理");
    }

    @Event(value = {R.id.tv_save, R.id.tv_set_stencil, R.id.tv_selectTemplate})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_save:    //保存
                finish();
                showToastText("已保存");
                break;
        }
    }
}
