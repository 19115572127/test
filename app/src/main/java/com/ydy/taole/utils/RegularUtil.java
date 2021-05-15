package com.ydy.taole.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 正则校验表达式
 */
public class RegularUtil {
    //身份证
    public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    //验证邮箱
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    //手机号
    public static final String REGEX_PHONE = "0?(13|14|15|17|18|19)[0-9]{9}";

    //验证IP地址
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    //汉字
    public static final String REGEX_falseNAME = "^[\\u4e00-\\u9fa5]*$";

    //银行卡
    public static final String BANKNUMBER="^([0-9]{16}|[0-9]{19})$";

    //固话电话正则
    public static final String TELE= "([0-9]{3,4}-)?[0-9]{7,8}";

    //密码强度验证   (?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)排除只有数字或字母的
    public static final String REGEX_User="^((?=.*[0-9])|[0-9A-Za-z]{5,16}$)";//可以纯数字和字母
    //账号验证
    public static final String REGEX_PWS="^((?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$)";//排除纯数字和字母

    public static Boolean isPhone(String mobiles){
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }else{
            return mobiles.matches(REGEX_PHONE);
        }
    }

    public static Boolean isTel(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(TELE);
        }
    }
    public static Boolean isIp(String ip){
        if (TextUtils.isEmpty(ip)) {
            return false;
        } else {
            return ip.matches(REGEX_IP_ADDR);
        }
    }

    public static Boolean isIDacard(String idcard){
        if (TextUtils.isEmpty(idcard))
            return false;
        else
            return idcard.matches(REGEX_ID_CARD);
    }

    public static Boolean isBankCard(String bankcard){
        if (TextUtils.isEmpty(bankcard))
            return false;
        else
            return bankcard.matches(BANKNUMBER);
    }

    public static Boolean isEmail(String email){
        if (TextUtils.isEmpty(email))
            return false;
        else
            return email.matches(REGEX_EMAIL);
    }

    public static Boolean isfalseName(String name){
        if (TextUtils.isEmpty(name))
            return false;
        else
            return name.matches(REGEX_falseNAME);
    }
    //验证密码强度
    public static Boolean isPsw(String psw) {
        //包含数字，字母，特殊符号其中至少两种
        if (TextUtils.isEmpty(psw)) {
            return false;
        }else {
            return psw.matches(REGEX_PWS);//matches():字符串是否在给定的正则表达式匹配
        }
    }
    //验证账号强度
    public static Boolean isUser(String user) {
        //包含数字，字母，特殊符号其中至少两种
        if (TextUtils.isEmpty(user)) {
            return false;
        }else {
            return user.matches(REGEX_User);//matches():字符串是否在给定的正则表达式匹配
        }
    }
}
