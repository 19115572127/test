package com.ydy.taole.activists;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.R;
import com.ydy.taole.beans.User;
import com.ydy.taole.database.DBOpenHelper;

import java.util.List;


public class SystemUserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button insert_date;
    private Button delete_date;
    private Button update_date;
    private Button show_date;
    private Button close_login;
    private EditText loginName;
    private EditText loginPassword;
    private ListView messageView;
    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;
    private Log log;
    private CheckBox checkBox;
    private String is_admin = "false";
    private List<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemuser);
        init();//初始化
        dbOpenHelper = new DBOpenHelper(this);
        /**
         * 按钮点击事件
         */
        show_date.setOnClickListener(this);
        insert_date.setOnClickListener(this);
        update_date.setOnClickListener(this);
        delete_date.setOnClickListener(this);
        close_login.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    is_admin = "true";
                    Toast.makeText(SystemUserActivity.this, buttonView.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    is_admin = "false";
                }
            }
        });
        //listView点击事件获取账号
        messageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = listUser.get(position).getName();
                String pwd = listUser.get(position).getPassword();
                loginName.setText(name);
                loginPassword.setText(pwd);
            }
        });
    }

    //初始化变量
    public void init() {
        insert_date = findViewById(R.id.insert_button);
        delete_date = findViewById(R.id.delete_button);
        update_date = findViewById(R.id.update_button);
        show_date = findViewById(R.id.show_button);
        close_login = findViewById(R.id.close_button);
        loginName = findViewById(R.id.name_text);
        loginPassword = findViewById(R.id.et_time);
        messageView = findViewById(R.id.list_view);
        checkBox = findViewById(R.id.checkBox);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_button:
                selectcheck();
                break;
            case R.id.insert_button:
//                insertcheck();
                //startActivity(new Intent(SystemUserActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.delete_button:
                deletecheck();
                break;
            case R.id.update_button:
                updatecheck();
                break;
            case R.id.close_button:
                startActivity(new Intent(SystemUserActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void updatecheck() {
        String username = loginName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();//密码

        //验证输入是否为空
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            //验证账号
            SQLiteDatabase db = new DBOpenHelper(this).getWritableDatabase();
            //根据画面上输入的账号/密码去数据库中进行查询（user是表名）
            Cursor c = db.query("user", null, "username=? ", new String[]{username}, null, null, null);
            //如果有查询到数据
            if (c != null && c.getCount() >= 1) {
                dbOpenHelper.updata(username, password, is_admin);
                Toast.makeText(SystemUserActivity.this, "修改成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "账号不存在！！！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SystemUserActivity.this, "密码不能为空！！!", Toast.LENGTH_LONG).show();
        }
    }

    private void insertcheck() {
        String username = loginName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();//密码

        //验证输入是否为空
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            //验证账号
            SQLiteDatabase db = new DBOpenHelper(this).getWritableDatabase();
            //根据画面上输入的账号/密码去数据库中进行查询（user是表名）
            Cursor c = db.rawQuery("select username from user where username=?", new String[]{username});
            //如果有查询到数据
            if (c != null && c.getCount() >= 1) {
                Toast.makeText(this, "账号已存在！！！", Toast.LENGTH_SHORT).show();
            } else {
                dbOpenHelper.add(username, password, null, null, null, null, is_admin);
                Toast.makeText(SystemUserActivity.this, "添加成功", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(SystemUserActivity.this, "不能为空", Toast.LENGTH_LONG).show();
        }
    }

    private void deletecheck() {
        String username = loginName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();//密码

        //验证输入是否为空
        if (!TextUtils.isEmpty(username)) {
            //验证账号
            SQLiteDatabase db = new DBOpenHelper(this).getWritableDatabase();
            //根据画面上输入的账号/密码去数据库中进行查询（user是表名）
            Cursor c = db.query("user", null, "username=? AND password=? ", new String[]{username, password}, null, null, null);
            //如果有查询到数据
            if (c != null && c.getCount() >= 1) {
                dbOpenHelper.delete(username);
                Toast.makeText(SystemUserActivity.this, "已执行!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "账号不存在！！！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SystemUserActivity.this, "删除失败!!!", Toast.LENGTH_LONG).show();
        }
    }

    private void selectcheck() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String Name = sp.getString("account",null);
        db = dbOpenHelper.getWritableDatabase();
        if (Name.equals("admin")) {
            listUser = dbOpenHelper.getAllData(Name);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listUser);
            messageView.setAdapter(adapter);
        } else {
            Toast.makeText(this,"您没有权限使用",Toast.LENGTH_LONG).show();
        }
    }
}





