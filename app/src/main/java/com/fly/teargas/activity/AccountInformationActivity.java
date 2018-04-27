package com.fly.teargas.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.fly.teargas.Constants;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.adapter.AccountInformationAdapter;
import com.fly.teargas.entity.UserInfo;
import com.fly.teargas.util.HttpHelper;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.github.ybq.android.spinkit.SpinKitView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 查看所有账户
 */
@ContentView(R.layout.activity_account_information)
public class AccountInformationActivity extends BaseActivity {
    @ViewInject(R.id.rv_account_information)
    private RecyclerView rv_account_information;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("所有账户");

        mInitRecyclerView(rv_account_information, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.GET_ALL_USER), null, spin_kit, new onGetAllUserXCallBack());
    }

    /**
     * 获取所有用户信息
     */
    private class onGetAllUserXCallBack implements HttpHelper.XCallBack {
        private List<UserInfo> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, UserInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                Placard.showInfo(e.toString());
                e.printStackTrace();
            }
            if (null != list && 0 < list.size()) {
                AccountInformationAdapter accountInformationAdapter = new AccountInformationAdapter(AccountInformationActivity.this, list);
                rv_account_information.setAdapter(accountInformationAdapter);

                accountInformationAdapter.setOnItemClickListener(new AccountInformationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("name", list.get(position).getName());
                        intent.putExtra("tel", list.get(position).getTel());
                        intent.putExtra("qx", list.get(position).getQx());
                        intent.putExtra("dq", list.get(position).getDq());
                        openActivity(intent, SetUserInfoActivity.class);
                    }
                });
            } else {
                rv_account_information.setAdapter(null);
            }
        }
    }
}
