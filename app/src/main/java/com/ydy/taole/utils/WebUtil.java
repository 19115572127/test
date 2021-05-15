package com.ydy.taole.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 请求接口连接服务器
 */
public class WebUtil {
    public String executeGet(String url) {
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        //发送请求
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //判断响应码是不是成功的响应码
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            } else {
                Log.e("LoginThread", "连接服务器失败");
                return "连接服务器失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
