package com.fly.teargas.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.fly.teargas.R;
import com.fly.teargas.adapter.AccountInformationAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 查看所有账户
 */
@ContentView(R.layout.activity_account_information)
public class AccountInformationActivity extends BaseActivity {
    @ViewInject(R.id.rv_account_information)
    private RecyclerView rv_account_information;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("所有账户");

        mInitRecyclerView(rv_account_information,2);

        AccountInformationAdapter accountInformationAdapter=new AccountInformationAdapter(this);
        rv_account_information.setAdapter(accountInformationAdapter);

        accountInformationAdapter.setOnItemClickListener(new AccountInformationAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent=new Intent();
                intent.putExtra("name","张三");
                openActivity(intent,AddUserActivity.class);
            }
        });
    }
}
