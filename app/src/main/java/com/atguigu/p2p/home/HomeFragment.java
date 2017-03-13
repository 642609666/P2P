package com.atguigu.p2p.home;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.home.bean.HomeBean;
import com.atguigu.p2p.utils.AppNetConfig;
import com.atguigu.p2p.utils.LoadNet;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:主页
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @InjectView(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @InjectView(R.id.banner)
    Banner banner;

    private HomeBean mHomeBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        Log.e("TAG", "主页加载视图成功");
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        //设置视图数据以及隐藏
        initViewShow();
        Log.e("TAG", "主页加载数据成功");
        //从网络获取数据
        initFromNet();


    }


    private void initViewShow() {
        baseTitle.setText("主页");
        baseBack.setVisibility(View.INVISIBLE);
        baseSetting.setVisibility(View.INVISIBLE);
    }

    private void initFromNet() {
        LoadNet.getDataNet(AppNetConfig.INDEX, new LoadNet.OnGetNet() {
            @Override
            public void onSuccess(String content) {
                Log.e("TAG", "主页请求数据成功");

                mHomeBean = JSON.parseObject(content, HomeBean.class);

                tvHomeProduct.setText(mHomeBean.getProInfo().getName());
                tvHomeYearrate.setText(mHomeBean.getProInfo().getYearRate() + " %");

                //设置banner数据
                initBanner();
            }

            @Override
            public void onFailure(String content) {
                Log.e("TAG", "主页请求数据失败" + content);

            }
        });
    }

    private void initBanner() {
        List<String> image = new ArrayList<>();
        Log.e("TAG", "mHomeBean.getImageArr().size()" + mHomeBean.getImageArr().size());
        for (int i = 0; i < mHomeBean.getImageArr().size(); i++) {
            image.add(AppNetConfig.BASE_URL + mHomeBean.getImageArr().get(i).getIMAURL());
            Log.e("TAG", "home获取的图片数据" + AppNetConfig.BASE_URL + mHomeBean.getImageArr().get(i).getIMAURL());
        }

        //设置图片集合
        //简单使用
        banner.setImages(image)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
                        Glide.with(context)
                                .load(path)
                                .crossFade()
                                .into(imageView);
                    }
                })
                .start();

        //设置样式
        banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
