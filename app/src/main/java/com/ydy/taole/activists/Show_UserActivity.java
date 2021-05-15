package com.ydy.taole.activists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.ydy.taole.R;
import com.ydy.taole.database.DBOpenHelper;

public class Show_UserActivity extends AppCompatActivity {
    public TextView name, sex, age, phone, Email_txt,back;
    private DBOpenHelper dbOpenHelper;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__user);
        dbOpenHelper = new DBOpenHelper(this);
        init();
        query();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Show_UserActivity.this, PersonalActivity.class));
            }
        });
    }

    public void init() {

        name = findViewById(R.id.name_text);
        sex = findViewById(R.id.sex_text);
        age = findViewById(R.id.age_text);
        phone = findViewById(R.id.phone_text);
        Email_txt = findViewById(R.id.email_text);
        back = findViewById(R.id.back_txt);
    }

    public void query() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String Name = sp.getString("account",null);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username = ?", new String[]{Name});
        while (cursor.moveToNext()) {//获取Cutsor对象遍历数据

            this.name.setText("用户名:"+cursor.getString(cursor.getColumnIndex("username")));//账号
            this.sex.setText("性　　别:"+cursor.getString(cursor.getColumnIndex("sex")));
            this.age.setText("年　龄:"+cursor.getString(cursor.getColumnIndex("age")));
            this.phone.setText("手机号:"+cursor.getString(cursor.getColumnIndex("phone")));
            String emaile = cursor.getString(cursor.getColumnIndex("Email"));
            Email_txt.setText("邮　箱:"+emaile);
        }
        cursor.close();
    }
}
