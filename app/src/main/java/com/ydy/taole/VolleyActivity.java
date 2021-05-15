package com.ydy.taole;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        initView();
        /**
         * 创建一个RequestQueue对象。
         * 创建一个StringRequest对象。
         * 将StringRequest对象添加到RequestQueue里面
         */
        RequestQueue mQueue = Volley.newRequestQueue(this);
        //GET请求
        StringRequest stringRequest = new StringRequest("https://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage(), error);
            }
        });
        //将这个StringRequest对象添加到RequestQueue里面
        mQueue.add(stringRequest);
        //加载网络图片
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest("https://img1.baidu.com/it/u=2496571732,442429806&fm=26&fmt=auto&gp=0.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        img.setImageResource(R.mipmap.ic_launcher);
                    }
                });
        requestQueue.add(imageRequest);
    }

    private void initView() {
        img = (ImageView) findViewById(R.id.img);
    }
}