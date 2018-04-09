package com.fly.teargas.activity;

import android.support.v7.widget.RecyclerView;

import com.fly.teargas.R;
import com.fly.teargas.adapter.LogInformationAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 日志信息
 */
@ContentView(R.layout.activity_log_information)
public class LogInformationActivity extends BaseActivity {
    @ViewInject(R.id.rv_log_information)
    private RecyclerView rv_log_information;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("管理");

        mInitRecyclerView(rv_log_information,2);
        LogInformationAdapter logInformationAdapter=new LogInformationAdapter(this);
        rv_log_information.setAdapter(logInformationAdapter);

        logInformationAdapter.setOnItemClickListener(new LogInformationAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
    }
}
