package com.fly.teargas.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.fly.teargas.R;
import com.fly.teargas.activity.PersonalActivity;
import com.fly.teargas.activity.EquipmentManagementActivity;
import com.fly.teargas.activity.MainActivity;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

@SuppressWarnings("ALL")
public class TabBar extends RelativeLayout {
    private final static String TAG = TabBar.class.getSimpleName();

    private Context mContext;
    private int mPage;
    private NavigationController navigationController;

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.TabBar);
        mPage = types.getInt(R.styleable.TabBar_page, -1);
        types.recycle();
    }

    public void setMessageNumber(int index, int num) {
        //设置消息数
        if (navigationController != null)
            navigationController.setMessageNumber(index, num);
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.tab_bar, this);

        PageBottomTabLayout tab = view.findViewById(R.id.tab);
//        ElasticAction.doAction(view, 400, 0.85f, 0.85f);

        navigationController = tab.custom()
                .addItem(newItem(R.drawable.home, R.drawable.home_open, "首页"))
                .addItem(newItem(R.drawable.ico_device, R.drawable.ico_device_open, "管理"))
                .addItem(newItem(R.drawable.ico_personcenter, R.drawable.ico_persioncenter_open, "个人"))
                .build();
        navigationController.setSelect(mPage);
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Intent intent = null;
                switch (index) {
                    case 0://首页
                        intent = new Intent(mContext, MainActivity.class);
                        break;
                    case 1://设备管理
                        intent = new Intent(mContext, EquipmentManagementActivity.class);
                        break;
                    case 2://告警中心
                        intent = new Intent(mContext, PersonalActivity.class);
                        break;
            }

                if (intent != null) {
                    mContext.startActivity(intent);
                    //清除上一个activity缓存
                    Activity activity = (Activity) mContext;
                    activity.finish();
                }
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(mContext);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(getResources().getColor(R.color.textColor_default));
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.main_update_color));
        return normalItemView;
    }

}
