package com.ydy.taole.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ydy.taole.beans.User;

import java.util.ArrayList;


public class DBOpenHelper extends SQLiteOpenHelper {//继承SQLiteOpenHelper类

    private SQLiteDatabase db;//创建个SQLiteDatabase类对象

    public DBOpenHelper(Context context) {//重写构造方法
        super(context, "db_news", null, 1);//数据库名，值，版本号
        db = getWritableDatabase();//获取数据库
    }


    @Override
    //新建表user  主键id   账号name  密码password
    public void onCreate(SQLiteDatabase db) {//初始化数据
        //创建数据表user  账号name 密码 password  序号id主键
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +//execSQL执行SQL语句
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "username TEXT," + "password TEXT," +
                "phone int(11)," + "sex varchar(4)," +
                "age int(3)," + "Email varchar(50)," +
                "is_admin text(4))");
    }

    @Override
    //版本变化更新需要调用的方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");//是否存在user表
        onCreate(db);
    }

    //添加信息
    public void add(String name, String password, String age, String sex, String phone, String email, String is_admin) {
        db.execSQL("INSERT INTO user (username,password,age,sex,phone,Email,is_admin)VALUES(?,?,?,?,?,?,?)", new String[]{name, password, age, sex, phone, email, is_admin});
    }

    //execSQL执行sql删除语句
    public void delete(String Name) {
        db.execSQL("DELETE FROM user WHERE username = ? ", new Object[]{Name});
    }

    //更新信息
    public void updata(String name, String password, String is_admin) {
        db.execSQL("UPDATE user SET password = ? ,is_admin = ? where username = ?", new Object[]{password, is_admin, name});
    }

    //查找信息
    public ArrayList<User> getAllData(String name) {
        ArrayList<User> list = new ArrayList<User>();
        if ("admin".equals(name)) {
            Cursor cursor = db.rawQuery("select * from user ", null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));//账号
                String password = cursor.getString(cursor.getColumnIndex("password"));//密码
                String is_admin = cursor.getString(cursor.getColumnIndex("is_admin"));//是否管理员
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String email = cursor.getString(cursor.getColumnIndex("Email"));
                list.add(new User(username, password, age, sex, phone, email, is_admin));//添加到表
            }
            cursor.close();
            return list;
        } else {
            Cursor cursor1 = db.rawQuery("select * from user where username = ? or username = ?", new String[]{name, "%"});
            while (cursor1.moveToNext()) {
                String username = cursor1.getString(cursor1.getColumnIndex("username"));//账号
                String password = cursor1.getString(cursor1.getColumnIndex("password"));//密码
                String is_admin = cursor1.getString(cursor1.getColumnIndex("is_admin"));//是否管理员
                String sex = cursor1.getString(cursor1.getColumnIndex("sex"));
                String age = cursor1.getString(cursor1.getColumnIndex("age"));
                String phone = cursor1.getString(cursor1.getColumnIndex("phone"));
                String email = cursor1.getString(cursor1.getColumnIndex("Email"));
                list.add(new User(username, password, age, sex, phone, email, is_admin));//添加到表
            }
            cursor1.close();
            return list;
        }
    }
}