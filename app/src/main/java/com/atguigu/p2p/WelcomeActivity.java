package com.atguigu.p2p;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.p2p.base.BaseAvtivity;
import com.atguigu.p2p.bean.UpdateBean;
import com.atguigu.p2p.utils.AppNetConfig;
import com.atguigu.p2p.utils.LoadNet;
import com.atguigu.p2p.utils.ThreadPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;


public class WelcomeActivity extends BaseAvtivity {
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    private Handler handler = new Handler();

    @InjectView(R.id.iv_main)
    ImageView ivMain;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //设置动画
        setAnimation();

        //设置版本号
        setVersions();

        //检查版本是否更新
        checkUpdate();
    }

    private void checkUpdate() {
        if (isOnLine()) {
            //连网
            LoadNet.getDataNet(AppNetConfig.UPDATE, new LoadNet.OnGetNet() {

                        @Override
                        public void onSuccess(String content) {
                            Log.i("update", "success: " + content);
                            final UpdateBean updateBean = JSON.parseObject(content, UpdateBean.class);
                            //判断当前的版本号
                            if (!getVersions().equals(updateBean.getVersion())) {
                                //提示有新版本
                                new AlertDialog.Builder(WelcomeActivity.this)
                                        .setTitle("有新版本你怎么看？")
                                        .setMessage(updateBean.getDesc())
                                        .setPositiveButton("不差钱", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                updateVerison(updateBean.getApkUrl());
                                            }
                                        })
                                        .setNegativeButton("哥很穷", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                toMain();
                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onFailure(String content) {

                        }
                    }

            );
        } else {
            //不要在动画没有执行完之前做进入主界面的动作
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Toast.makeText(WelcomeActivity.this, "当前没有网络", Toast.LENGTH_SHORT).show();
                    toMain();
                }
            }, 2000);
        }
    }

    private void updateVerison(final String apkUrl) {
        //展示进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        //下载
        ThreadPool.getInstance().getExecutorService().execute(new Runnable() {

            private FileOutputStream os;
            private InputStream inputStream;

            @Override
            public void run() {
                try {
                    //获取url地址
                    URL url = new URL(AppNetConfig.BASE_URL+"app_new.apk");
                    //打开连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(5000);//读取超时
                    conn.setConnectTimeout(5000);//连接超时
                    conn.setRequestMethod("GET");//请求方式
                    conn.connect();//连接网络

                    if (conn.getResponseCode() == 200) {//连网成功
                        //进度条的总长度
                        progressDialog.setMax(conn.getContentLength());

                        inputStream = conn.getInputStream();

                        File path;
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            path = getExternalFilesDir("");
                        } else {
                            path = getFilesDir();
                        }
                        File file = new File(path, "update.apk");
                        os = new FileOutputStream(file);

                        byte[] bytes = new byte[1024];
                        int len;
                                                        /*
                        * inputStream.read(bytes) 将数据装到bytes数组里
                        * */
                        while ((len = inputStream.read(bytes)) != -1) {
                            progressDialog.incrementProgressBy(len);
                            os.write(bytes, 0, len);
                        }

                        //下载成功了
                        progressDialog.dismiss();
                        //安装
                        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
                        intent.setData(Uri.parse("file:" + file.getAbsolutePath()));
                        startActivity(intent);
                    } else {//连网失败
                        Toast.makeText(WelcomeActivity.this, "没网你看着办", Toast.LENGTH_SHORT).show();
                        toMain();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

    }

    public void toMain() {
        if (isLogin()) {
            //登录过进入主界面
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        } else {
            //没有登录过进入登录界面
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();
        }
    }

    private boolean isLogin() {
        String name = getUser().getData().getName();
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return true;
    }

    //判断是否有网络
    private boolean isOnLine() {
        boolean connected = false;
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }
        // return connected;
        return true;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * 设置版本号
     */
    private void setVersions() {
        tvNumber.setText(getVersions());
    }

    /**
     * 获得版本号
     *
     * @return
     */
    public String getVersions() {
        String versionName = null;

        try {
            //拿到包的管理器
            PackageManager packageManager = getPackageManager();
            //拿到包的管理信息
            PackageInfo packageArchiveInfo = packageManager.getPackageInfo(getPackageName(), 0);

            //得到版本 ,每次提交需要增加1
            int versionCode = packageArchiveInfo.versionCode;

            //得到版本号
            versionName = packageArchiveInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return "";
    }

    private void setAnimation() {
        //加载gif图画
        Glide.with(WelcomeActivity.this).load(R.drawable.dwonlo)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //磁盘高速缓存策略
                // DiskCacheStrategy.NONE 什么都不缓存
                // DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。
                // DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
                // DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                .into(ivMain);


        //设置八秒后自动进入主页面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, 8000);

        //设置三秒后提示点击进入
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvName.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.removeCallbacksAndMessages(null);
            gotoMainActivity();
            return true;
        }
        return super.onTouchEvent(event);


    }

    private void gotoMainActivity() {
        handler.removeCallbacksAndMessages(null);
        if (isRegister()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.main_alpha, R.anim.main_alpha_press);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.main_alpha, R.anim.main_alpha_press);
        }
        //关闭页面
        finish();
    }

    private boolean isRegister() {
        String name = getUser().getData().getName();
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        return true;
    }


}
