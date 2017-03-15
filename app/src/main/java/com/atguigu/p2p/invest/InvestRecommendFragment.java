package com.atguigu.p2p.invest;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.utils.UiUtils;
import com.atguigu.p2p.utils.randomLayout.StellarMap;

import java.util.Random;

import butterknife.InjectView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/14 0014.
 * 功能:
 */

public class InvestRecommendFragment extends BaseFragment {
    @InjectView(R.id.invest_rec_sm)
    StellarMap investRecSm;
    //随机
    private Random mRandom = new Random();
    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
            "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划"
    };

    private String[] oneDatas = new String[datas.length / 2];
    private String[] twoDatas = new String[datas.length - datas.length / 2];


    @Override
    public int getLayoutid() {
        return R.layout.fragmemt_invest_recommend;
    }

    @Override
    public String getChildUrl() {
        return "";
    }

    @Override
    protected void initData(String json) {
        for (int i = 0; i < datas.length; i++) {
            if (i < datas.length / 2) {
                //第一个数组填入数据
                oneDatas[i] = datas[i];
            } else {
                //第二个数组填入剩余数据
                twoDatas[i - datas.length / 2] = datas[i];
            }
        }
        //设置适配器
        investRecSm.setAdapter(new RecommendAdapter());
        //必须调用如下的两个方法，否则stellarMap不能显示数据
        //设置显示的数据在x轴、y轴方向上的稀疏度
        investRecSm.setRegularity(5, 7);
        //设置初始化显示的组别，以及是否需要使用动画
        investRecSm.setGroup(0, true);
        investRecSm.setInnerPadding(UiUtils.dp2px(10), UiUtils.dp2px(10),
                UiUtils.dp2px(10), UiUtils.dp2px(10));
    }


    class RecommendAdapter implements StellarMap.Adapter {
        /**
         * 有几个组
         *
         * @return
         */
        @Override
        public int getGroupCount() {
            return 2;
        }

        /**
         * 每组有多少数量, 根据不同的组返回不同的view
         *
         * @param group
         * @return
         */
        @Override
        public int getCount(int group) {
            if (group == 0) {
                return datas.length / 2;
            } else {
                return datas.length - datas.length / 2;
            }
        }

        /**
         * 返回view  根据不同的组返回不同的view
         *
         * @param group
         * @param position
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView textView = new TextView(getActivity());
            if (group == 0) {
                textView.setText(oneDatas[position]);
            } else {
                textView.setText(twoDatas[position]);
            }
            int red = mRandom.nextInt(255);
            int blue = mRandom.nextInt(255);
            int greed = mRandom.nextInt(255);
            if (red >= 220 && blue >= 220 && greed >= 220) {
                red = 155;
                blue = 155;
                greed = 155;
            }
            textView.setTextColor(Color.rgb(red, blue, greed));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "" + textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            return textView;
        }

        /**
         * 每个组都是从0开始   预留方法
         *
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        /**
         * 返回下一组的组号
         *
         * @param group
         * @param isZoomIn
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
