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

import com.atguigu.p2p.MainActivity;
import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.bean.UserInfo;
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
                // .load(AppNetConfig.BASE_URL + "/images/tx.png")
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489605907571&di=e338ef47bc28e44dd102be1bd1de8c7c&imgtype=0&src=http%3A%2F%2Fimgtu.5011.net%2Fuploads%2Fcontent%2F20160803%2F1495791470209434.jpg")
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


    @OnClick({R.id.ll_touzi, R.id.ll_touzi_zhiguan, R.id.ll_zichan, R.id.tv_settings, R.id.recharge, R.id.withdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_settings:
                Intent intent = new Intent(getActivity(), ImageSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_touzi:
                break;
            case R.id.ll_touzi_zhiguan:
                break;
            case R.id.ll_zichan:
                break;
            case R.id.recharge: //充值
                startActivity(new Intent(getActivity(), ReChargeActivity.class));
                break;
            case R.id.withdraw: //提现
                startActivity(new Intent(getActivity(),WithDrawActivity.class));
                break;
        }
    }
}
