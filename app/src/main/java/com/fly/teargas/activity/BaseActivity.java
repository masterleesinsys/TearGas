package com.fly.teargas.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fly.teargas.MyApplication;
import com.fly.teargas.R;
import com.fly.teargas.util.LogUtils;
import com.fly.teargas.util.Placard;
import com.fly.teargas.util.Tools;
import com.skydoves.elasticviews.ElasticAction;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

@SuppressWarnings("ALL")
public abstract class BaseActivity extends AppCompatActivity {
    final int RIGHT = 0;
    final int LEFT = 1;
    public ImageView btn_collect = null;
    @ViewInject(R.id.toolbar)
    protected Toolbar mToolbar;
    protected Context mContext = this;
    protected int STYLE_MAIN = 0;
    protected int STYLE_HOME = 1;
    protected int STYLE_BACK = 2;
    protected int STYLE_BACK_QUERY = 201;
    protected int STYLE_LOGIN = 3;
    protected int STYLE_USERCENTER = 4;
    protected int STYLE_BACK_SET = 5;
    protected int STYLE_FENXI = 6;
    protected int STYLE_NAME = 7;
    protected int STYLE_VERUPDATE = 8;
    protected int STYLE_JINGPIN = 9;
    protected int STYLE_BACK_ADD = 101;
    protected int STYLE_RIGHT_IMG_ADD = 10;
    protected int STYLE_BACK_ACTIVITY_DETAIL = 102;
    protected int mStyle = 0;
    protected boolean mIsExit;
    protected ImageView editbutton;
    protected ImageView deletebutton;
    protected TextView txtCaption;
    protected TextView mBtnRightAction;
    protected TextView textName;
    protected TextView mBtnLeftAction;
    protected ImageView mBtnActivityAdd;
    protected ImageView mBtnActivityRemove;
    protected ImageView mBtnCompanyDetail;
    protected TextView mbtnBack = null;
    protected ImageView mbtnSettings = null;
    protected ImageView mbtnAreaselect = null;
    protected ImageView mbtnZhaoshang = null;
    protected ImageView mbtnSearch = null;
    protected ImageView mbtnAdd = null;
    protected RelativeLayout mRel_top = null;
    protected LinearLayout mLinCaption = null;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            mIsExit = false;
        }

    };
    private ImageView imgMore, imgBack;
    private TextView tvPrompt;
    private Toolbar toolbar = null;
    private onTitleBarRightImgListener onTitleBarRightImgListener;
    private onBackButtonListener onBackButtonListener;
    private onTitleBarRightTvListener onTitleBarRightTvListener;
    private boolean isListener = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 添加Activity到列表
        MyApplication.getInstance().addActivity(this);

        try {
            x.view().inject(this);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        initView();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        super.onResume();
    }

    /**
     * TODO 设置标题显示风格
     *
     * @param value
     */
    protected void setStyle(int value) {
        mStyle = value;

        if (value == STYLE_MAIN) {
            showBackButton(false);
        } else if (value == STYLE_BACK) {
            showBackButton(true);
        } else if (value == STYLE_FENXI) {
            showBackButton(true);
        } else if (value == STYLE_LOGIN) {
            showBackButton(false);
        } else if (value == STYLE_USERCENTER) {
            showBackButton(false);
        } else if (value == STYLE_BACK_SET) {
            showBackButton(true);
        } else if (value == STYLE_HOME) {
            showBackButton(false);
        } else if (value == STYLE_NAME) {
            showBackButton(false);
        } else if (value == STYLE_JINGPIN) {
            showBackButton(true);
            showQueryTv();
        } else if (value == STYLE_VERUPDATE) {
            showBackButton(true);
        } else if (value == STYLE_BACK_ADD) {
            showBackButton(true);
            showAddIco();
        } else if (value == STYLE_BACK_ACTIVITY_DETAIL) {
            showBackButton(true);
        } else if (value == STYLE_BACK_QUERY) {
            showBackButton(true);
//            showQueryIco();
            showQueryImg();
        }
    }

    public BaseActivity showNameTv(String name){
        textName=findViewById(R.id.textName);
        textName.setVisibility(View.VISIBLE);
        textName.setText(name);
        return this;
    }

    private BaseActivity showQueryTv() {
        tvPrompt = (TextView) findViewById(R.id.tvPrompt);
        tvPrompt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ElasticAction.doAction(view, 400, 0.85f, 0.85f);
                onTitleBarRightTvListener.onTitleBarRightTvListener();
            }
        });
        return this;
    }

    public TextView getRightTvView() {
        if (tvPrompt == null) {
            tvPrompt = (TextView) findViewById(R.id.tvPrompt);
            return tvPrompt;
        }
        return tvPrompt;
    }

    public BaseActivity setTitleBarRightTvVisibility(int visibility) {
        tvPrompt.setVisibility(visibility);
        return this;
    }

    public BaseActivity setTitleBarRightTvText(String text) {
        tvPrompt.setText(text);
        return this;
    }

    public void setOnTitleBarRightTvListener(BaseActivity.onTitleBarRightTvListener onTitleBarRightTvListener) {
        this.onTitleBarRightTvListener = onTitleBarRightTvListener;
    }

    public BaseActivity showQueryImg() {
        imgMore = (ImageView) findViewById(R.id.imgMore);
        imgMore.setVisibility(View.VISIBLE);
        imgMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ElasticAction.doAction(view, 400, 0.85f, 0.85f);
                onTitleBarRightImgListener.onTitleBarRightImgListener();
            }
        });
        return this;
    }

    public BaseActivity setTitleBarImageResource(int imgRes) {
        imgMore.setImageResource(imgRes);
        return this;
    }

    public ImageView getImageView() {
        if (imgMore == null) {
            return null;
        }
        return imgMore;
    }

    public void setOnTitleBarRightImgListener(BaseActivity.onTitleBarRightImgListener onTitleBarRightImgListener) {
        this.onTitleBarRightImgListener = onTitleBarRightImgListener;
    }

    public void setCaption(String strCaption) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtCaption = (TextView) findViewById(R.id.txtCaption);
        txtCaption.setText(strCaption);
        txtCaption.setVisibility(View.VISIBLE);
    }

    protected void setCaption(int resId) {
        txtCaption = (TextView) findViewById(R.id.txtCaption);
        txtCaption.setText(resId);
        txtCaption.setVisibility(View.VISIBLE);

    }

    protected void setTopNotVisble(boolean isShow) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (isShow)
            toolbar.setVisibility(View.VISIBLE);
        else
            toolbar.setVisibility(View.GONE);
    }

    protected void showBackButton(boolean isShow) {
        try {
            // 显示返回按钮时
            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ElasticAction.doAction(view, 400, 0.85f, 0.85f);
                    if (isListener) {
                        onBackButtonListener.onBackButtonListener();
                    }
                    finish();
                }
            });
            if (isShow) {
                imgBack.setVisibility(View.VISIBLE);
            } else {
                imgBack.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
            Placard.showInfo(e.toString());
            e.printStackTrace();
        }
    }

    protected void setOnBackButtonListener(boolean isListener, BaseActivity.onBackButtonListener onBackButtonListener) {
        this.onBackButtonListener = onBackButtonListener;
        this.isListener = isListener;
    }

    protected void showQueryIco() {
        mbtnSearch = (ImageView) findViewById(R.id.btnSearch);
        mbtnSearch.setVisibility(View.VISIBLE);
    }

    protected void showAddIco() {
        mbtnAdd = (ImageView) findViewById(R.id.btnAdd);
        mbtnAdd.setVisibility(View.VISIBLE);
    }

    protected void showBackButtonNoLogin(boolean isShow) {
        // 显示返回按钮时，隐藏图标和设�?
        mBtnLeftAction = (TextView) findViewById(R.id.btnLeftAction);
        mBtnLeftAction.setText(" 返  回 ");
        if (isShow) {
            mBtnLeftAction.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    onBackonNoLogin();
                }
            });
            mBtnLeftAction.setVisibility(View.VISIBLE);
        } else {
            mBtnLeftAction.setVisibility(View.GONE);
        }
    }

    protected void showImgLogo(boolean isShow) {
        ImageView titelLogo = (ImageView) findViewById(R.id.img_logo);

        if (isShow)
            titelLogo.setVisibility(View.VISIBLE);
        else
            titelLogo.setVisibility(View.GONE);
    }

    protected void addActionButton(int imgResId, OnClickListener onClickListener) {
        LinearLayout actionBarRight = (LinearLayout) findViewById(R.id.ActionBarRight);
        actionBarRight.setGravity(Gravity.CENTER_VERTICAL);
        ImageView imgSeparator = new ImageView(this);
        actionBarRight.addView(imgSeparator);
        actionBarRight.setVisibility(View.VISIBLE);
        Button btnAction = new Button(this);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 8;
        lp.rightMargin = 5;
        btnAction.setPadding(15, 5, 15, 5);
        btnAction.setGravity(Gravity.CENTER);
        btnAction.setLayoutParams(lp);
        actionBarRight.addView(btnAction);
        if (onClickListener != null) {
            btnAction.setBackgroundResource(imgResId);
            btnAction.setOnClickListener(onClickListener);
        }
    }

    protected Button addActionButton(String strText, OnClickListener onClickListener) {
        LinearLayout actionBarRight = (LinearLayout) findViewById(R.id.ActionBarRight);
        ImageView imgSeparator = new ImageView(this);
        actionBarRight.addView(imgSeparator);
        actionBarRight.setVisibility(View.VISIBLE);
        Button btnAction = new Button(this);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.leftMargin = 3;
        lp.rightMargin = 3;
        btnAction.setText(strText);
        btnAction.setTextColor(0xffffffff);
        btnAction.setTextSize(16);
        btnAction.setBackgroundDrawable(null);
        btnAction.setPadding(15, 0, 15, 0);
        btnAction.setGravity(Gravity.CENTER);
        btnAction.setLayoutParams(lp);
        actionBarRight.addView(btnAction);

        if (onClickListener != null) {
            btnAction.setOnClickListener(onClickListener);
        }
        return btnAction;
    }

    protected void setLeftActionButton(String strText, OnClickListener onClickListener) {
        mBtnLeftAction = (TextView) findViewById(R.id.btnLeftAction);
        mBtnLeftAction.setVisibility(View.VISIBLE);

        mBtnLeftAction.setText(strText);

        if (onClickListener != null) {
            mBtnLeftAction.setOnClickListener(onClickListener);
        }
    }

    protected void setRightActionButton(String strText,
                                        OnClickListener onClickListener) {
        // mBtnRightAction = (TextView) findViewById(R.id.btnRightAction);
        mBtnRightAction.setVisibility(View.VISIBLE);

        mBtnRightAction.setText(strText);

        if (onClickListener != null) {
            mBtnRightAction.setOnClickListener(onClickListener);
        }
    }

    protected void openActivity(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        startActivity(intent);
    }

    protected void openActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

    protected void openActivity(Intent intent, Class cls) {
        intent.setClass(mContext, cls);
        startActivity(intent);
    }

    protected void onBack() {
        finish();
    }

    protected void onBackonNoLogin() {
        finish();
    }

    protected void showToastText(String msg) {
        if (msg != null && !msg.equals("-1") && !msg.equals(""))
            Placard.showInfo(msg);
    }

    protected void showToastText(int resId) {
        String msg = getResources().getString(resId);
        if (msg != null && !msg.equals("-1") && !msg.equals(""))
            Placard.showInfo(msg);
    }

    public interface onTitleBarRightTvListener {
        void onTitleBarRightTvListener();
    }

    public interface onTitleBarRightImgListener {
        void onTitleBarRightImgListener();
    }

    public interface onBackButtonListener {
        void onBackButtonListener();
    }

    protected void openReLoginActivityForResult(int type) {
        Intent intent = new Intent();
        intent.setClass(mContext, LoginActivity.class);
        startActivityForResult(intent, type);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // 按下键盘上返回按�?
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK && mStyle == STYLE_HOME) {
            exit();
            return false;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    public void exit() {
        if (!mIsExit) {
            mIsExit = true;
            Toast.makeText(getApplicationContext(), R.string.app_exit, Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            // 关闭Activity到列表
            MyApplication.getInstance().exit();
        }
    }

    //解析服务端返回结果
    public <T> T getHttpResult(String result, Class<T> clazz) {
        Object o = new Object();
        JSONObject json = null;
        try {
            json = new JSONObject(result);
            if (json.getInt("success") == 1) {
                if (json.has("data")) {
                    String data = (String) json.getString("data");
                    o = JSON.parseObject(data, clazz);
                    return (T) o;
                } else
                    return null;
            } else if (json.getInt("success") == 0) {
                String errors = json.getString("error");
                Placard.showInfo(errors);
                LogUtils.e(errors);
                return null;
            } else if (json.getInt("success") == 2) {
//                Placard.showInfo("您的令牌失效，请重新登录");
//                openReLoginActivityForResult(99);
                String errors = json.getString("error");
                Placard.showInfo(errors);
                LogUtils.e(errors);
            } else if (json.getInt("success") == 3) {
                String errors = json.getString("error");
                Placard.showInfo(errors);
                LogUtils.e(errors);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToastText(e.toString());
            LogUtils.e(e.toString());
        }
        return null;
    }

    //解析服务端返回结果
    public String getHttpResultList(String result) {
        String o = "";
        JSONObject json = null;
        try {
            json = new JSONObject(result);
            if (json.getInt("success") == 1) {
                if (json.has("data")) {
                    String data = (String) json.getString("data");
                    return data;
                } else
                    return null;
            } else if (json.getInt("success") == 0) {
                String errors = json.getString("error");
                LogUtils.e(errors);
                Placard.showInfo(errors);
                return null;
            } else if (json.getInt("success") == 2) {
                Placard.showInfo("您的令牌失效，请重新登录");
                openReLoginActivityForResult(99);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToastText(e.toString());
            LogUtils.e(e.toString());
            return null;
        }
        return null;
    }

    //解析服务端返回结果
    public Boolean getHttpResultBoolean(String result) {
        String o = "";
        JSONObject json = null;
        try {
            json = new JSONObject(result);
            if (json.getInt("success") == 1) {
                if (json.has("data")) {
                    Boolean data = (Boolean) json.getBoolean("data");
                    return data;
                } else
                    return null;
            } else if (json.getInt("success") == 0) {
                String errors = json.getString("error");
                LogUtils.e(errors);
                Placard.showInfo(errors);
                return null;
            } else if (json.getInt("success") == 2) {
                Placard.showInfo("您的令牌失效，请重新登录");
                openReLoginActivityForResult(99);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToastText(e.toString());
            LogUtils.e(e.toString());
            return null;
        }
        return null;
    }

    //解析服务端返回结果
    public int getHttpResult(String result) {
        Object o = new Object();
        JSONObject json = null;
        int success = 0;
        try {
            json = new JSONObject(result);
            if (json.has("success")) {
                success = json.getInt("success");
                if (success == 1) {
                    return success;
                } else if (json.getInt("success") == 0) {
                    String errors = json.getString("error");
                    Placard.showInfo(errors);
                    LogUtils.e(errors);
                } else if (json.getInt("success") == 2) {
                    Placard.showInfo("您的令牌失效，请重新登录");
                    openReLoginActivityForResult(99);
                } else if (json.getInt("success") == 3) {
                    String errors = json.getString("error");
                    Placard.showInfo(errors);
                    LogUtils.e(errors);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showToastText(e.toString());
            LogUtils.e(e.toString());
        }
        return success;
    }

    /**
     * TODO 初始化 RecyclerView
     *
     * @param rv
     * @param numColumn
     * @param type      弹性滑动类型  默认0无，1 横向滑动 ，2 水平滑动
     */
    protected void mInitRecyclerView(RecyclerView rv, int numColumn, int type) {
        if (numColumn == 0) {
            rv.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            rv.setLayoutManager(new GridLayoutManager(mContext, numColumn));
        }

        //rv.addItemDecoration(rv.new DividerItemDecoration(ContextCompat.getDrawable(context,R.drawable.divider)));//添加分割线

        if (0 == type) {
            return;
        }
        switch (type) {
            case 1:
                // HORIZONTAL
                OverScrollDecoratorHelper.setUpOverScroll(rv, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
                break;
            case 2:
                // VERTICAL
                OverScrollDecoratorHelper.setUpOverScroll(rv, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
                break;
        }
    }

    protected void mInitRecyclerView(RecyclerView rv) {
        mInitRecyclerView(rv, 0, 0);
    }

    protected void mInitRecyclerView(RecyclerView rv, int type) {
        mInitRecyclerView(rv, 0, type);
    }

    /**
     * TODO Integer类型跳转
     */
    protected void openActivity(Context context, Integer integer, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("type", integer);
        context.startActivity(intent);
    }

    /**
     * TODO 获取视频缩略图
     *
     * @param filePath
     * @return
     */
    protected Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            LogUtils.e(e.toString());
            showToastText("获取视频缩略图失败!" + e.toString());
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                LogUtils.e(e.toString());
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * TODO 获取网络视频缩略图
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    protected Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            retriever.setDataSource(url, new HashMap<String, String>());
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            LogUtils.e(ex.toString());
        } catch (Exception e) {
            LogUtils.e(e.toString());
            showToastText("获取网络视频缩略图失败!" + e.toString());
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                LogUtils.e(ex.toString());
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * 获取网络接入方式(0:移动网络  /  1:WIFI)
     */
    protected static Integer getAccessToTheNetwork(Context context) {
        int netWorkType = -1;
        //获取网络连接管理者
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = 1;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = 0;
            }
        }
        return netWorkType;
    }

    /**
     * 设置按钮圆角及边框颜色
     *
     * @param status_color 颜色值
     * @param roundRadius  弧度
     * @param strokeWidth  边框宽度
     * @param stroke_color 边框颜色
     * @return
     */
    protected GradientDrawable mDrawableStyle(@ColorRes int status_color, int roundRadius, int strokeWidth, @ColorRes int stroke_color) {
        GradientDrawable gd = null;
        try {
            int fillColor = ContextCompat.getColor(x.app(), status_color);//内部填充颜色
            gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(Tools.dip2px(roundRadius));
            int strokeColor = ContextCompat.getColor(x.app(), stroke_color);//外边框颜色
            gd.setStroke(Tools.dip2px(strokeWidth), strokeColor);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return gd;
    }

    protected GradientDrawable mDrawableStyle(@ColorRes int status_color, int roundRadius) {
        GradientDrawable gd = null;
        try {
            int fillColor = ContextCompat.getColor(x.app(), status_color);//内部填充颜色
            gd = new GradientDrawable();//创建drawable
            gd.setColor(fillColor);
            gd.setCornerRadius(Tools.dip2px(roundRadius));
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return gd;
    }

    /**
     * 调用系统提示音效
     */
    protected void callTheSystemPrompt() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notification == null) return;
        Ringtone r = RingtoneManager.getRingtone(this, notification);
        r.play();
    }
}
