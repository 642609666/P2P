package com.atguigu.p2p.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atguigu.p2p.bean.DataBean;
import com.atguigu.p2p.bean.UserInfo;
import com.atguigu.p2p.utils.AppManager;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/15 0015.
 * 功能:
 */

public abstract class BaseAvtivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);

        AppManager.getInstance().addActivity(this);
        //初始化布局
        initTitle();
        //初始化fragment
        initData();
        //设置监听
        initListener();
    }

    protected abstract int getLayoutId();

    /**
     * 设置标头的数据
     */
    protected abstract void initTitle();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 设置监听
     */
    protected abstract void initListener();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        AppManager.getInstance().remove(this);
    }

    /**
     * 获取用户信息
     */
    public UserInfo getUser() {
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String imageurl = sp.getString("imageurl", "");
        String iscredit = sp.getString("iscredit", "");
        String name = sp.getString("name", "");
        String phone = sp.getString("phone", "");
        UserInfo userInfo = new UserInfo();
        DataBean dataBean = new DataBean();
        dataBean.setImageurl(imageurl);
        dataBean.setIscredit(iscredit);
        dataBean.setName(name);
        dataBean.setPhone(phone);
        userInfo.setData(dataBean);
        Log.e("TAG", "读取" + name);
        return userInfo;
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     */
    public void seveUser(UserInfo userInfo) {
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("imageurl", userInfo.getData().getImageurl());
        edit.putString("iscredit", userInfo.getData().getIscredit());
        edit.putString("name", userInfo.getData().getName());
        edit.putString("phone", userInfo.getData().getPhone());
        Log.e("TAG", "保存" + userInfo.getData().getName());
        edit.commit();
    }

    public void saveImage(Boolean isUpdate) {
        SharedPreferences sp = getSharedPreferences("image", MODE_PRIVATE);
        sp.edit().putBoolean("update", isUpdate).commit();
    }

    public Boolean isUpdate() {
        SharedPreferences sp = getSharedPreferences("image", MODE_PRIVATE);
        return sp.getBoolean("update", false);
    }

    //清楚所有的sp操作
    public void clearSp() {
        SharedPreferences user = getSharedPreferences("user_info", MODE_PRIVATE);
        SharedPreferences image = getSharedPreferences("image", MODE_PRIVATE);
        user.edit().clear().commit();
        image.edit().clear().commit();
    }

    //删除file
    public void clearFile() {
        File fileDir = null;
        //判断是否挂载了sd卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //外部存储路径
            fileDir = getExternalFilesDir("");
        } else {
            fileDir = getFilesDir();
        }

        //全路径
        File path = new File(fileDir, "p2p_icon.png");

        if (path.exists()) {
            path.delete();//删除目录中的内容
        }
    }


}
