package com.fly.teargas.service;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.allenliu.versionchecklib.core.VersionDialogActivity;
import com.fly.teargas.MyApplication;


/**
 * @author allenliu
 * @email alexliusheng@163.com
 * @link :http://github.com/alexliusheng
 * 注意为了展示本库的所有功能
 * 所以代码看上去会比较多，不过都是重写方法和监听回调
 * 如果不想自定义界面和一些自定义功能不用设置
 * versionParams.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
 * 使用库默认自带的就行了
 * @important 如果要重写几个ui:
 * 请分别使用父类的versionDialog／loadingDialog/failDialog以便库管理显示和消失
 */
public class CustomVersionDialogActivity extends VersionDialogActivity {
    public static int customVersionDialogIndex = 3;
    public static boolean isForceUpdate = false;
    public static boolean isCustomDownloading = false;
    public static boolean isNeedUpdate = false;      //当前是否需要更新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里是几个回调
//        setApkDownloadListener(this);
//        setCommitClickListener(this);
//        setDialogDimissListener(this);
        showVersionDialog();
    }


    /**
     * 自定义更新展示界面 直接重写此方法就好
     */
    @Override
    public void showVersionDialog() {
        //使用默认的提示框直接调用父类的方法,如果需要自定义的对话框，那么直接重写此方法
        // super.showVersionDialog();
        //如果不需要更新，提示已经是最新版本，否则调用默认更新
        if (!isNeedUpdate) {
            customVersionDialogOne();
        } else {
            super.showVersionDialog();
        }
    }

    /**
     * 自定义dialog one
     * 使用父类的versionDialog字段来初始化
     *
     * @see
     */
    private void customVersionDialogOne() {
        versionDialog = new AlertDialog.Builder(this).setTitle("版本更新").setMessage("已经是最新版本").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyApplication.DIALOG_NEW_VER = false;
                finish();

            }
        }).create();
        versionDialog.show();
    }
}
