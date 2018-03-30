package com.fly.teargas.util;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.teargas.MyApplication;
import com.fly.teargas.R;

import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Toast操作类
 */
public class Placard {
    /**
     * 普通Toast
     *
     * @param msg
     */
    public static void showInfo(CharSequence msg) {
        Toast toast = Toast.makeText(x.app(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 自定义时长显示
     *
     * @param msg
     * @param cnt
     */
    public static void showInfo(CharSequence msg, int cnt) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(x.app(), msg, Toast.LENGTH_LONG);
        showMyInfo(toast, cnt);
    }

    private static void showMyInfo(final Toast toast, int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    /**
     * 程序调试专用Toast
     *
     * @param msg
     */
    public static void showInfoDebug(CharSequence msg) {
        if (MyApplication.DEBUG) {
            if ("".equals(msg)) {
                msg = "msg为空";
            }
            Toast toast = Toast.makeText(x.app(), msg, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public static void showInfoDebug(CharSequence msg, int cnt) {
        if (MyApplication.DEBUG) {
            if ("".equals(msg)) {
                msg = "msg为空";
            }
            @SuppressLint("ShowToast") Toast toast = Toast.makeText(x.app(), msg, Toast.LENGTH_LONG);
            showMyInfo(toast, cnt);
        }
    }

    /**
     * 自定义Toast样式
     */
    public static void ToastMessage(String messages) {
        View view = View.inflate(x.app(), R.layout.toast_style, null); //加載layout下的布局
        TextView text = view.findViewById(R.id.tv_toast);
        text.setText(messages); //toast内容
        Toast toast = new Toast(x.app());
        toast.setGravity(Gravity.BOTTOM, 0, 80);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }

    /**
     * 自定义Toast样式(自定义时长)
     */
    public static void ToastMessage(String messages, int cnt) {
        View view = View.inflate(x.app(), R.layout.toast_style, null); //加載layout下的布局
        TextView text = view.findViewById(R.id.tv_toast);
        text.setText(messages); //toast内容
        Toast toast = new Toast(x.app());
        toast.setGravity(Gravity.BOTTOM, 0, 80);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        showMyInfo(toast, cnt);
    }
}
