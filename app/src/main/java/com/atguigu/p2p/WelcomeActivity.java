package com.atguigu.p2p;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2p.base.BaseAvtivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        handler.removeCallbacksAndMessages(null);
        overridePendingTransition(R.anim.main_alpha, R.anim.main_alpha_press);
        finish();
    }


}
