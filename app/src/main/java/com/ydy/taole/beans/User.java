package com.ydy.taole.beans;

public class User {
    private String name;            //用户名
    private String password;        //密码
    private String is_admin;
    private String age,sex,phone,Email;

    public User( String name, String password,String age,String sex,String phone,String Email,String is_admin) {//构造user方法
        this.name = name;
        this.password = password;
        this.is_admin = is_admin;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.Email = Email;
    }

    public String getName() {//获取账号
        return name;
    }

    public void setName(String name) {//设置账号值
        this.name = name;
    }

    public String getPassword() {//获取密码
        return password;
    }

    public void setPassword(String password) {//设置密码
        this.password = password;
    }

    private void setIs_admin(String is_admin){
        this.name = name;
    }
    public String getIs_admin(){
        return is_admin;
    }
    private void setAge(String age){
        this.age = age;
    }
    public String getAge(){
        return age;
    }
    public String getEmail(){
        return Email;
    }
    private void setEmail(String Email){
        this.Email = Email;
    }

    @Override
    public String toString() {//转字符串连接方法
        return "{name = "+ name +"，password ="+password +",root="+is_admin+"}";

    }
}