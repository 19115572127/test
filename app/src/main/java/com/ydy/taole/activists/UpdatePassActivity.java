package com.ydy.taole.activists;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.R;
import com.ydy.taole.database.DBOpenHelper;


public class UpdatePassActivity extends AppCompatActivity {

    private DBOpenHelper dbOpenHelper;
    SQLiteDatabase db;
    private EditText etYuanPass;
    private EditText etPass;
    private Button up_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        init();
        up_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_datecheck();
            }
        });
    }

    private void up_datecheck() {
        String yuanmima = etYuanPass.getText().toString().trim();//原密码
        String password = etPass.getText().toString().trim();//密码
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String Name = sp.getString("account",null);
        //验证输入是否为空
        if (!TextUtils.isEmpty(yuanmima)&&!TextUtils.isEmpty(password)) {
            //验证账号
            SQLiteDatabase db = new DBOpenHelper(this).getWritableDatabase();
            //根据画面上输入的账号/密码去数据库中进行查询（user是表名）
            Cursor c = db.rawQuery("select username,password from user where username = ? and password = ?", new String[]{Name, yuanmima});
            //如果有查询到数据
            if (c != null && c.getCount() >= 1) {
                if (!yuanmima.equals(password)) {
                    db.execSQL("update user set password = ? where username = ?",new String[]{password,Name});
                    Toast.makeText(this, "已执行!!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }else{
                    Toast.makeText(this, "修改的密码不能与原密码一致!!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "原密码不正确！！！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "更新失败!!!可能输入空值", Toast.LENGTH_LONG).show();
        }
    }

    public void init() {
        etYuanPass = findViewById(R.id.et_yuanmima);
        etPass = findViewById(R.id.delete_password);
        up_date = findViewById(R.id.button_delete);
    }


}
