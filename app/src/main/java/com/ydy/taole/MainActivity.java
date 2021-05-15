package com.ydy.taole;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.ydy.taole.activists.PersonalActivity;
import com.ydy.taole.activists.VideoActivity;
import com.ydy.taole.adapters.RecyclerAdapter;
import com.ydy.taole.utils.DrawableUtil;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView autoComplete;
    private Button bt_select;
    private GridView grid;
    private RecyclerView recycler;
    private RelativeLayout first_layout;
    private RelativeLayout second_layout;
    private RelativeLayout third_layout;
    private RelativeLayout fourth_layout;
    private List<String> autoCompList = new ArrayList<>();
    /**
     * First time click on back
     */
    private Long mExitTime = 0L;
    private Banner banner_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //super.onBackPressed();//注释掉这行,back键不退出activity
        initView();
        init();
    }

    //监听返回键 点击两次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 菜单栏
     *
     * @return
     */
    private void setGridDate() {
        int[] images = new int[]{R.drawable.app1, R.drawable.app2, R.drawable.app3,
                R.drawable.app4, R.drawable.app5, R.drawable.app6, R.drawable.app7, R.drawable.app8};
        final String[] titles = new String[]{"饿了么", "乐乐新品", "分类", "机票酒店",
                "会员中心", "今日爆款", "天猫", "健康"};
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            map.put("title", titles[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_item_grid,
                new String[]{"image", "title"},
                new int[]{R.id.image_grid, R.id.title_grid});
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, titles[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 内容页
     */
    private void setRecycleData() {
        final int[] images = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3,
                R.drawable.image4, R.drawable.image5,R.drawable.lv1,R.drawable.lv2,
                R.drawable.lv3, R.drawable.lv4,R.drawable.lv5};
        final List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            list.add(map);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(this, list);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter.setOnItemClickListener(new RecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
            }
        });
        recycler.setAdapter(adapter);
        //分割线
        //recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    /**
     * 初始化对象
     */
    private void init() {
        setGridDate();//绑定推荐应用
        setRecycleData();//绑定推荐内容
        setBanner();//轮播图
    }

    /**
     * 轮播图
     */
    private void setBanner() {
        List<Drawable> imps = new ArrayList<>();
        DrawableUtil drawableUtil = new DrawableUtil(this);
        imps=drawableUtil.getDrawable("bn");
        banner_main.setImages(imps)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object o, ImageView imageView) {
                        Glide.with(context).load(o).into(imageView);
                    }
                }).setDelayTime(3000).setBannerAnimation(Transformer.CubeOut).start();
    }

    /**
     * 搜索框
     */
    private void setAutoList() {
        String text = autoComplete.getText().toString();
        autoCompList.add(text);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompList);
        autoComplete.setAdapter(adapter);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        bt_select = (Button) findViewById(R.id.bt_select);
        bt_select.setOnClickListener(this);
        grid = (GridView) findViewById(R.id.grid);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        first_layout = (RelativeLayout) findViewById(R.id.first_layout);
        first_layout.setOnClickListener(this);
        second_layout = (RelativeLayout) findViewById(R.id.second_layout);
        second_layout.setOnClickListener(this);
        third_layout = (RelativeLayout) findViewById(R.id.third_layout);
        third_layout.setOnClickListener(this);
        fourth_layout = (RelativeLayout) findViewById(R.id.fourth_layout);
        fourth_layout.setOnClickListener(this);

        banner_main = (Banner) findViewById(R.id.banner_main);

    }

    @Override
    public void onClick(@NotNull View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.first_layout:
                intent.setClass(this, MainActivity.class);
                startActivity(intent);//跳转页面
                break;
            case R.id.second_layout:
                intent.setClass(this, VideoActivity.class);
                startActivity(intent);//跳转页面
                break;
            case R.id.third_layout:
                //intent.setClass(this,MainActivity.class);
//                startActivity(intent);//跳转页面
                break;
            case R.id.fourth_layout:
                intent.setClass(this, PersonalActivity.class);
                startActivity(intent);//跳转页面
                break;
            case R.id.bt_select:
                setAutoList();
                Toast.makeText(this, "查询", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}