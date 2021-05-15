package com.ydy.taole.activists;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.R;
import com.ydy.taole.utils.CaptchaUtil;
import com.ydy.taole.utils.Commint;
import com.ydy.taole.utils.RegularUtil;
import com.ydy.taole.utils.WebUtil;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_register;
    private EditText ed_account;
    private EditText ed_nickName;
    private EditText ed_password;
    private EditText ed_passwordtwo;
    private EditText ed_phone;
    private EditText ed_captche;
    private ImageView image_captche;
    private CaptchaUtil mCaptcha;
    private Handler handler;
    private Timer timer;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioGroup group_sex;
    private RadioButton sex;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        init();
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
                    setImageCapt();
                }
                if (msg.what == 0x11) {
                    String result = (String) msg.obj;
                    if ("0k".equals(result)) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败账号已存在!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                super.handleMessage(msg);
            }
        };
        timer = new Timer();//创建timer对象
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 1000, 20 * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();//关闭timer
        }
    }

    private void initView() {
        bt_register = (Button) findViewById(R.id.bt_register);
        ed_account = (EditText) findViewById(R.id.ed_account);
        ed_nickName = (EditText) findViewById(R.id.ed_nickName);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_passwordtwo = (EditText) findViewById(R.id.ed_passwordtwo);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_captche = (EditText) findViewById(R.id.ed_captche);
        image_captche = (ImageView) findViewById(R.id.image_captche);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        group_sex = (RadioGroup) findViewById(R.id.group_sex);
        sex = (RadioButton)findViewById(R.id.radioButton);
        group_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex = group.findViewById(checkedId);
            }
        });

        bt_register.setOnClickListener(this);
        image_captche.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                if (submit()) {
                    String account = ed_account.getText().toString();//账号
                    String password = ed_password.getText().toString();//密码
                    String nickName = ed_nickName.getText().toString();//昵称
                    String sex= this.sex.getText().toString();//性别
                    String url = Commint.Localhost + "Registerserv?account=" + account + "&password=" + password+"" +
                            "&nickName="+nickName+"&Sex="+sex;
                    new RegisterThread(url).start();
                }
                break;
            case R.id.image_captche:
                setImageCapt();
                break;
        }
    }

    /**
     * 设置验证码
     */
    private void setImageCapt() {
        mCaptcha = CaptchaUtil.getInstance()
                .setType(CaptchaUtil.TYPE.CHARS)
                .setSize(200, 80)
                .setBackgroundColor(Color.WHITE)
                .setLength(4)
                .setLineNumber(2)
                .setFontSize(50)
                .setFontPadding(20, 20, 45, 15);
        image_captche.setImageBitmap(mCaptcha.create());//绑定到图片对象
    }

    /**
     * 请求响应数据 result
     */
    public class RegisterThread extends Thread {
        String url = "";

        public RegisterThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            WebUtil webUtil = new WebUtil();
            String result = webUtil.executeGet(url);
            Message message = new Message();
            message.obj = result;
            message.what = 0x11;
            handler.sendMessage(message);
        }
    }

    private boolean submit() {
        // validate
        String account = ed_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return false;
        }

        String nickName = ed_nickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return false;
        }

        String password = ed_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        String passwordTwo = ed_passwordtwo.getText().toString().trim();
        if (TextUtils.isEmpty(passwordTwo)) {
            Toast.makeText(this, "请再确认一次", Toast.LENGTH_SHORT).show();
            return false;
        }

        String phone = ed_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!RegularUtil.isPhone(phone)) {
            ed_phone.setText("");
            ed_phone.setHint("请输入正确手机号！");
            return false;
        }

        String captcha = ed_captche.getText().toString().trim();
        if (TextUtils.isEmpty(captcha)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        String result = ed_captche.getText().toString();
        if (!result.equals(mCaptcha.getCode())) {
            Toast.makeText(this, "验证码输入错误", Toast.LENGTH_SHORT).show();
        }
        if (!RegularUtil.isUser(account)) {
            Toast.makeText(this, "用户名输入不规范(最少长度5)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!RegularUtil.isfalseName(nickName)) {
            Toast.makeText(this, "昵称输入不规范...", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!RegularUtil.isPsw(password)) {
            ed_password.setText("");
            Toast.makeText(this, "密码输入不规范(必须包含字母和数字)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!RegularUtil.isPsw(passwordTwo)) {
            ed_passwordtwo.setText("");
            Toast.makeText(this, "密码输入不规范(必须包含字母和数字)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(passwordTwo)) {
            this.ed_passwordtwo.setText("");
            this.ed_passwordtwo.setHint("密码不一致！");
            return false;
        }
        // TODO validate success, do something
        return true;
    }
}