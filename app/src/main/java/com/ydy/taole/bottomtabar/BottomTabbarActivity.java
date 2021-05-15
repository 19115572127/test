package com.ydy.taole.bottomtabar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hjm.bottomtabbar.BottomTabBar;
import com.ydy.taole.QrCodeActivity;
import com.ydy.taole.R;
import com.ydy.taole.activists.LoginActivity;
import com.ydy.taole.activists.PersonalActivity;
import com.ydy.taole.activists.SystemUserActivity;
import com.ydy.taole.activists.UpdatePassActivity;
import com.ydy.taole.fragments.FirstFragment;
import com.ydy.taole.fragments.FourthFragment;
import com.ydy.taole.fragments.SecondFragment;
import com.ydy.taole.fragments.ThirdFragment;
import com.ydy.taole.utils.Commint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomTabbarActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private BottomTabBar bottom_tab_bar;
    private ImageView title_imv;
    private TextView title_text_tv;
    private ImageView title_shezhi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tabbar);
        initView();
        init();

    }
    private void init(){
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        //初始化Fragment
        bottom_tab_bar.init(getSupportFragmentManager())
                .setImgSize(60, 60)   //图片大小
                .setFontSize(14)            //字体大小
                .setTabPadding(4, 6, 10)//选项卡的间距
                .setChangeColor(Color.WHITE, Color.BLACK)   //选项卡的选择颜色
                .addTabItem("首页", R.drawable.first, FirstFragment.class)
                .addTabItem("分类", R.drawable.second, SecondFragment.class)
                .addTabItem("发现", R.drawable.third, ThirdFragment.class)
                .addTabItem("我的", R.drawable.fourth, FourthFragment.class)
                .isShowDivider(true)  //是否包含分割线
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name) {
                        Log.e("TGA", "位置：" + position + "   选项卡：" + name);
                        title_text_tv.setText(name);
                    }
                })
                .setTabBarBackgroundResource(R.drawable.buttonshape)
                .setBackgroundResource(R.drawable.buttonshape);
    }

    private void initView() {
        bottom_tab_bar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
        title_imv = (ImageView) findViewById(R.id.title_imv);
        title_imv.setOnClickListener(this);
        title_text_tv = (TextView) findViewById(R.id.title_text_tv);
        title_shezhi = (ImageView) findViewById(R.id.title_shezhi);
        title_shezhi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}