package com.atguigu.p2p;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2p.base.BaseAvtivity;
import com.atguigu.p2p.bean.UserInfo;
import com.atguigu.p2p.utils.AppNetConfig;
import com.atguigu.p2p.utils.LoadNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseAvtivity {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.tv_login_number)
    TextView tvLoginNumber;
    @InjectView(R.id.login_et_number)
    EditText loginEtNumber;
    @InjectView(R.id.rl_login)
    RelativeLayout rlLogin;
    @InjectView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @InjectView(R.id.login_et_pwd)
    EditText loginEtPwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.login_regitster_tv)
    TextView loginRegitsterTv;
    /**
     * 注册的数据传递到这里
     * 0 账号  1 密码  2名字
     */
    private String[] mSplit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        baseBack.setVisibility(View.INVISIBLE);
        baseSetting.setVisibility(View.INVISIBLE);
        baseTitle.setText("登录");
    }

    @Override
    protected void initData() {
        //接受注册时的数据
        String phone_password = getIntent().getStringExtra("phone_password");
        if (!TextUtils.isEmpty(phone_password)) {
            mSplit = phone_password.split(",");
            if (!TextUtils.isEmpty(mSplit[0]) && !TextUtils.isEmpty(mSplit[1])) {
                loginEtNumber.setText(mSplit[0]);
                loginEtPwd.setText(mSplit[1]);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_login, R.id.login_regitster_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                Toast.makeText(LoginActivity.this, "登录", Toast.LENGTH_SHORT).show();
                //获得账号和密码
                String phone = loginEtNumber.getText().toString().trim();
                String password = loginEtPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("password", password);
                //去服务器登录

                LoadNet.getDataNet(AppNetConfig.LOGIN, map, new LoadNet.OnGetNet() {

                    @Override
                    public void onSuccess(String content) {
                        Log.e("TAG", "登录界面成功Success" + content);
                        JSONObject jsonObject = JSON.parseObject(content);
                        Boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            //解析数据
                            UserInfo userInfo = JSON.parseObject(content, UserInfo.class);
                            //判断账号是否相同
                            if (mSplit[0].equals(userInfo.getData().getPhone())) {
                                //把名字传递过去
                                userInfo.getData().setName(mSplit[2]);
                                Log.e("TAG", "赋值名字进去");
                            }
                            //保存数据到sp
                            seveUser(userInfo);
                            //跳转
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            //结束当前页面
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "账号不存在或者密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String content) {
                        Log.e("TAG", "注册界面失败Failure" + content);
                    }
                });


                break;
            case R.id.login_regitster_tv:
                Intent intent = new Intent(this, RegesterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
