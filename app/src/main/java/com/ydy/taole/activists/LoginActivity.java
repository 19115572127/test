package com.ydy.taole.activists;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.R;
import com.ydy.taole.utils.Commint;
import com.ydy.taole.utils.RegularUtil;
import com.ydy.taole.utils.WebUtil;

import org.jetbrains.annotations.NotNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView td_user;
    private EditText edUser;
    private EditText edPassWord;
    private Button login;
    private TextView td_register;
    private ImageView image_touxiang;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Handler handler;
    private Retrofit retrofit;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        init();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==0x11){
                    String result = msg.obj.toString().trim();
                    CheckLogin(result);
                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 对账号密码校验
     * @param result 响应数据
     */
    private void CheckLogin(String result){
        String account = edUser.getText().toString().trim();
        String password = edPassWord.getText().toString().trim();
        if(result.equals("ok")){
            editor = pref.edit();
            if(rememberPass.isChecked()){
                editor.putBoolean(Commint.remember_password,true);
                editor.putString(Commint.account,account);
                editor.putString(Commint.password,password);
            }else {
                editor.clear();//清空
            }
            //如果没勾选记住密码也将登录状态写入
            editor.putString(Commint.account,account);//账号名称
            editor.putBoolean(Commint.status,true);//添加登录状态
            editor.apply();//提交
            startActivity(new Intent(LoginActivity.this, PersonalActivity.class));
        }else{
            Toast.makeText(LoginActivity.this, "请检查账号或密码是否正确", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        boolean isRemember = pref.getBoolean(Commint.remember_password,false);
        if(isRemember){
            //将账号和密码设置到文本框
            String account = pref.getString(Commint.account,"");
            String password = pref.getString(Commint.password,"");
            edUser.setText(account);
            edPassWord.setText(password);
            rememberPass.setChecked(true);
        }
        retrofit = new Retrofit.Builder().baseUrl(Commint.Localhost+'/')
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void initView() {
        td_user = (TextView) findViewById(R.id.td_user);
        edUser = (EditText) findViewById(R.id.edUser);
        edPassWord = (EditText) findViewById(R.id.edPassWord);
        login = (Button) findViewById(R.id.login);
        td_register = (TextView) findViewById(R.id.td_register);
        image_touxiang = (ImageView) findViewById(R.id.image_touxiang);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        login.setOnClickListener(this);
        td_register.setOnClickListener(this);
        rememberPass = (CheckBox) findViewById(R.id.rememberPass);
    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.login:
                if (submit()) {
                    String account = edUser.getText().toString().trim();
                    String password = edPassWord.getText().toString().trim();
                    String url = Commint.Localhost+"Loginserv?account="+account+"&password="+password;
                    new LoginThread(url).start();
                }
                break;
            case R.id.td_register:
               startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    /**
     * 正则校验
     * @return
     */
    @NotNull
    private Boolean submit() {
        // validate
        String edUserString = edUser.getText().toString().trim();
        if (TextUtils.isEmpty(edUserString)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String edPassWordString = edPassWord.getText().toString().trim();
        if (TextUtils.isEmpty(edPassWordString)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        //限制长度
        if(edPassWordString.length()<6||edPassWordString.length()>15){
            Toast.makeText(this, "密码最小长度6位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!RegularUtil.isUser(edUserString)) {
            Toast.makeText(this, "用户名输入不规范(最少长度5)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!RegularUtil.isPsw(edPassWordString)) {
            Toast.makeText(this, "密码输入不规范(必须包含字母和数字)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // TODO validate success, do something
        return true;
    }

    /**
     * 请求响应数据 result
     */
    public class LoginThread extends Thread{
        String url="";
        public LoginThread(String url) {
            this.url = url;
        }
        @Override
        public void run() {
            WebUtil webUtil = new WebUtil();
            String result = webUtil.executeGet(url);
            Message message = new Message();
            message.obj=result;
            message.what=0x11;
            handler.sendMessage(message);
        }
    }
}