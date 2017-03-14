package com.atguigu.p2p.invest;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.invest.adapter.InvesAdapger;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:投资
 */

public class InvestFragment extends BaseFragment {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.invest_vp)
    ViewPager investVp;
    @InjectView(R.id.tv_invest_all)
    TextView tvInvestAll;
    @InjectView(R.id.tv_invest_hot)
    TextView tvInvestHot;
    @InjectView(R.id.tv_invest_recommend)
    TextView tvInvestRecommend;

    private InvesAdapger mInvesAdapger;

    @Override
    public int getLayoutid() {
        return R.layout.fragment_invest;
    }

    @Override
    public String getChildUrl() {
        return null;
    }


    @OnClick({R.id.tv_invest_all, R.id.tv_invest_hot, R.id.tv_invest_recommend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invest_all:
                investVp.setCurrentItem(0);
                break;
            case R.id.tv_invest_hot:
                investVp.setCurrentItem(1);
                break;
            case R.id.tv_invest_recommend:
                investVp.setCurrentItem(2);
                break;
        }
    }

    @Override
    protected void initData(String json) {

        //设置标头
        initTitle();
        //初始化fragment
        initFragment();
        //初始化viewpager
        initViewPager();
        //初始化默认tab
        initTab();

        //设置监听
        initListeners();

    }

    private void initListeners() {
        investVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Toast.makeText(getActivity(), "hhhh", Toast.LENGTH_SHORT).show();
                selectText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTab() {
        selectText(0);
    }

    private void selectText(int id) {
        hiddenTextViewState();
        switch (id) {
            case 0:
                tvInvestAll.setBackgroundColor(Color.parseColor("#000088"));
                break;
            case 1:
                tvInvestHot.setBackgroundColor(Color.parseColor("#000088"));
                break;
            case 2:
                tvInvestRecommend.setBackgroundColor(Color.parseColor("#000088"));
                break;
        }
    }

    private void hiddenTextViewState() {
        tvInvestRecommend.setBackgroundColor(Color.parseColor("#665522"));
        tvInvestHot.setBackgroundColor(Color.parseColor("#665522"));
        tvInvestAll.setBackgroundColor(Color.parseColor("#665522"));
    }


    private void initViewPager() {

        mInvesAdapger = new InvesAdapger(getChildFragmentManager(), mList);
        investVp.setAdapter(mInvesAdapger);


    }

    private List<BaseFragment> mList = new ArrayList<>();

    private void initFragment() {
        mList.add(new InvestAllFragment());
        mList.add(new InvestHotFragment());
        mList.add(new InvestRecommendFragment());
    }

    private void initTitle() {
        baseSetting.setVisibility(View.GONE);
        baseBack.setVisibility(View.GONE);
        baseTitle.setText("投资");
    }


}
