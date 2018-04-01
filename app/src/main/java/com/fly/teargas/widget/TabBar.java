package com.fly.teargas.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.fly.teargas.R;
import com.fly.teargas.activity.MainActivity;
import com.skydoves.elasticviews.ElasticAction;

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
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);

        navigationController = tab.custom()
//                .addItem(newItem(R.drawable.btn_shipin, R.drawable.btn_shipin_def, "视频"))
//                .addItem(newItem(R.drawable.btn_mingshi, R.drawable.btn_mingshi_def, "名师"))
//                .addItem(newItem(R.drawable.btn_shangcheng, R.drawable.btn_shangcheng_def, "商城"))
//                .addItem(newItem(R.drawable.btn_wode, R.drawable.btn_wode_def, "我的"))
                .build();
        navigationController.setSelect(mPage);
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Intent intent = null;
                switch (index) {
                    case 0://视频
                        intent = new Intent(mContext, MainActivity.class);
                        break;
                    case 1://名师
//                        intent = new Intent(mContext, TeacherActivity.class);
                        break;
                    case 2://商城
//                        intent = new Intent(mContext, MallActivity.class);
                        break;
                    case 3://我的
//                        intent = new Intent(mContext, MineActivity.class);
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
//        normalItemView.setTextDefaultColor(getResources().getColor(R.color.tabbar_item));
//        normalItemView.setTextCheckedColor(getResources().getColor(R.color.tabbar_textChecked));
        return normalItemView;
    }

}
