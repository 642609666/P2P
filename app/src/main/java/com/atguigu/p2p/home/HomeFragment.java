package com.atguigu.p2p.home;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.p2p.base.BaseFragment;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:主页
 */

public class HomeFragment extends BaseFragment {

    private TextView mTextView;
    @Override
    public View initView() {
        mTextView = new TextView(mContext);
        mTextView.setText("主页");
        mTextView.setGravity(Gravity.CENTER);
        Log.e("TAG", "主页加载视图成功");
        return mTextView;
    }

    @Override
    protected void initData() {

        Log.e("TAG", "主页加载数据成功");
    }
}
