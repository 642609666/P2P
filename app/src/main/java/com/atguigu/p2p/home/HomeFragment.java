package com.atguigu.p2p.home;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.home.bean.HomeBean;
import com.atguigu.p2p.home.view.MyProgress;
import com.atguigu.p2p.utils.AppNetConfig;
import com.atguigu.p2p.utils.ThreadPool;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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
    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @InjectView(R.id.home_progress)
    MyProgress homeProgress;
    @InjectView(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;


    @Override
    public int getLayoutid() {
        return R.layout.fragment_home;
    }

    @Override
    public String getChildUrl() {
        return AppNetConfig.INDEX;
    }


    public void initData(String json) {
        //设置标头
        initTitle();
        HomeBean homeBean = JSON.parseObject(json, HomeBean.class);
        //Log.i("http", "success: "+homeBean.getImageArr().size());
        tvHomeYearrate.setText(homeBean.getProInfo().getYearRate() + "%");
        tvHomeProduct.setText(homeBean.getProInfo().getName());
        //注意：展示UI一定要判断是不是主线程
        initProgress(homeBean.getProInfo());
        initBanner(homeBean);
    }

    private void initTitle() {
        //初始化title
        baseTitle.setText("首页");
        baseBack.setVisibility(View.INVISIBLE);
        baseSetting.setVisibility(View.INVISIBLE);
    }

    private void initProgress(final HomeBean.ProInfoBean proInfo) {

        ThreadPool.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                int progress = Integer.parseInt(proInfo.getProgress());
                for (int i = 0; i <= progress; i++) {
                    SystemClock.sleep(50);
                    homeProgress.setProgress(i);
                }
            }
        });
    }

    private void initBanner(HomeBean homeBean) {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //转化成url集合
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < homeBean.getImageArr().size(); i++) {
            urls.add(AppNetConfig.BASE_URL + homeBean.getImageArr().get(i).getIMAURL());
        }
        //设置图片集合
        banner.setImages(urls);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Picasso 加载图片简单用法
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
