package com.atguigu.p2p;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2p.base.BaseAvtivity;
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
                        Log.e("TAG", "注册界面成功Success" + content);
                    }

                    @Override
                    public void onFailure(String content) {
                        Log.e("TAG", "注册界面失败Failure" + content);
                    }
                });


                break;
            case R.id.login_regitster_tv:
                // Intent intent = new Intent(this, .class);
                //  startActivity(intent);
                break;
        }
    }
}
