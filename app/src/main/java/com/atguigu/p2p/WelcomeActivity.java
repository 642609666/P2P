package com.atguigu.p2p;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeActivity extends AppCompatActivity {
    @InjectView(R.id.tv_name)
    TextView tvName;
    private Handler handler = new Handler();

    @InjectView(R.id.iv_main)
    ImageView ivMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.inject(this);


        //加载gif图画
        Glide.with(WelcomeActivity.this).load(R.drawable.dwonlo)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
        overridePendingTransition(R.anim.main_alpha_press, R.anim.main_alpha);
        finish();
    }
}
