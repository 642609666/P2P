package com.atguigu.p2p;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguigu.p2p.banking.BankingFragment;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.home.HomeFragment;
import com.atguigu.p2p.invest.InvestFragment;
import com.atguigu.p2p.more.MoreFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.rl_main)
    FrameLayout rlMain;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    private List<BaseFragment> mList;
    /**
     * 记录下标
     */
    private int position;
    /**
     * 临时缓存
     */
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        //初始化fragment
        initFragment();
        //设置监听
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_invest:
                        position = 1;
                        break;
                    case R.id.rb_banking:
                        position = 2;
                        break;
                    case R.id.rb_more:
                        position = 3;
                        break;
                }

                switchFragment(mList.get(position));
            }


        });

        //设置默认布局
        rgMain.check(R.id.rb_home);
    }

    /**
     * 切换fragment布局
     *
     * @param baseFragment
     */
    private void switchFragment(BaseFragment baseFragment) {

        //判断是否点击的是同一个fragment
        if (baseFragment != tempFragment) {

            //得到管理事务
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            //如果没有被添加
            if (!baseFragment.isAdded()) {

                //缓存的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                //添加
                ft.add(R.id.rl_main, baseFragment);

            } else {

                //缓存的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                //显示
                ft.show(baseFragment);

            }

            //提交
            ft.commit();

            //缓存
            tempFragment = baseFragment;
        }
    }


    private void initFragment() {
        mList = new ArrayList<>();
        mList.add(new HomeFragment());
        mList.add(new InvestFragment());
        mList.add(new BankingFragment());
        mList.add(new MoreFragment());
    }

    private boolean isBack = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isBack) {
                //退出
                finish();
            } else {
                Toast.makeText(MainActivity.this, "再点击一次退出", Toast.LENGTH_SHORT).show();
            }
            isBack = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 2000);

            //事件拦截
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
