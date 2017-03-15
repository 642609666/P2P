package com.atguigu.p2p.banking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.p2p.LoginActivity;
import com.atguigu.p2p.MainActivity;
import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.bean.UserInfo;
import com.atguigu.p2p.utils.AppNetConfig;
import com.atguigu.p2p.utils.BitmapUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:资产
 */

public class BankingFragment extends BaseFragment {

    @InjectView(R.id.tv_settings)
    TextView tvSettings;
    @InjectView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @InjectView(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @InjectView(R.id.tv_me_name)
    TextView tvMeName;
    @InjectView(R.id.rl_me)
    RelativeLayout rlMe;
    @InjectView(R.id.recharge)
    ImageView recharge;
    @InjectView(R.id.withdraw)
    ImageView withdraw;
    @InjectView(R.id.ll_touzi)
    TextView llTouzi;
    @InjectView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @InjectView(R.id.ll_zichan)
    TextView llZichan;

    @Override
    public int getLayoutid() {
        return R.layout.fragment_property;
    }

    @Override
    protected void initData(String json) {
        MainActivity mainActivity = (MainActivity) getActivity();
        UserInfo user = mainActivity.getUser();
        Log.e("TAG", "" + user.getData().getName());
        //设置用户名
        tvMeName.setText(user.getData().getName());
        //设置图片
        Glide.with(getActivity())
                .load(AppNetConfig.BASE_URL + "/images/tx.png")
                .transform(new BitmapTransformation(getActivity()) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        Bitmap bitmap = BitmapUtils.circleBitmap(toTransform);
                        toTransform.recycle();//原来的必须回收释放
                        return bitmap;
                    }

                    @Override
                    public String getId() {
                        return "";//不能返回空
                    }
                })
                .into(ivMeIcon);
    }


    @Override
    public String getChildUrl() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.tv_settings)
    public void onClick() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
