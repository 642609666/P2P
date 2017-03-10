package com.atguigu.p2p.banking;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.p2p.base.BaseFragment;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:资产
 */

public class BankingFragment extends BaseFragment {

    private TextView mTextView;
    @Override
    public View initView() {
        mTextView = new TextView(mContext);
        mTextView.setText("资产");
        mTextView.setGravity(Gravity.CENTER);
        Log.e("TAG", "资产加载视图成功");
        return mTextView;
    }

    @Override
    protected void initData() {

        Log.e("TAG", "资产加载数据成功");
    }
}
