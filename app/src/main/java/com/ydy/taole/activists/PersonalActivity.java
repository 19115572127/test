package com.ydy.taole.activists;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.ydy.taole.MainActivity;
import com.ydy.taole.QrCodeActivity;
import com.ydy.taole.R;
import com.ydy.taole.utils.Commint;
import com.ydy.taole.utils.DrawableUtil;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_touxiang;
    private TextView td_dologin;
    private ImageView img_shezhi;
    private RelativeLayout first_layout;
    private RelativeLayout second_layout;
    private RelativeLayout third_layout;
    private RelativeLayout fourth_layout;
    private Button bt_quit;
    private TextView td_name;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private GridView grid_geren;
    private ListView list_geren;
    private ListView right_drawer;
    private DrawerLayout drawer_layout;
    private TextView td_qianming;
    private ImageView img_qianming;
    private Banner banner_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        init();
        setGridDate();//菜单导航
        setListData();//列表
        setRight_drawer();//抽屉菜单
        setBanner();//轮播图
    }

    private void setBanner() {
        List<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.bg_0);
        imgs.add(R.drawable.bg_1);
        imgs.add(R.drawable.bg_2);
        imgs.add(R.drawable.bg_3);
        banner_person.setImages(imgs)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object o, ImageView imageView) {
                        Glide.with(context).load(o).into(imageView);
                    }
                })
                .setDelayTime(15000)
                .start();
    }

    //将base64转为Bitmap
    private static Bitmap stringToBitmap(String photo_base64) {
        Bitmap bitmap = null;
        byte[] bitmapArry = Base64.decode(photo_base64, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(bitmapArry, 0, bitmapArry.length);
        return bitmap;
    }

    //打开相机
    private void startCamera(int flag) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            startActivityForResult(intent, flag);
        } catch (Exception ex) {
            Toast.makeText(this, "打开相机失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 应用栏
     */
    private void setGridDate() {
        final String[] titles = new String[]{"待付款", "待发货", "待收货", "待评价", "退款"};
        int[] images = new int[]{R.drawable.daifukuan, R.drawable.daifahuo,
                R.drawable.daishouhuo, R.drawable.pingjia, R.drawable.tuikuan};
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", titles[i]);
            map.put("image", images[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_item_grid,
                new String[]{"image", "title"},
                new int[]{R.id.image_grid, R.id.title_grid});
        grid_geren.setAdapter(adapter);
        grid_geren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PersonalActivity.this, titles[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 列表栏
     */
    private void setListData() {
        final String[] titles = new String[]{"全部订单", "收获地址", "我的店铺", "收藏夹", "反馈与帮助", "应用推荐", "分享应用"};
        int[] images = new int[]{R.drawable.quanbudingdan, R.drawable.shouhuodizhi, R.drawable.dianpu,
                R.drawable.shoucang, R.drawable.fankui, R.drawable.yinyong, R.drawable.fenxiang};
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", titles[i]);
            map.put("image", images[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_item_list,
                new String[]{"image", "title"},
                new int[]{R.id.image_iv, R.id.title_iv});
        list_geren.setAdapter(adapter);
        list_geren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PersonalActivity.this, titles[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 抽屉菜单
     *
     * @return
     */
    private void setRight_drawer() {
        String[] itemname = {"个人信息", "信息管理", "更改密码", "制作二维码", "退出登录"};
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < itemname.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", itemname[i]);
            list.add(map);
        }
        String[] from = {"name"};
        int[] to = {R.id.item_text};
        final Class[] NewActivity = {LoginActivity.class, SystemUserActivity.class, UpdatePassActivity.class,
                LoginActivity.class, QrCodeActivity.class};
        //创建适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item_shezhi, from, to);
        right_drawer.setAdapter(simpleAdapter);
        right_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 4) {
                    startActivity(new Intent(PersonalActivity.this, NewActivity[position]));
                } else {
                    td_name.setVisibility(View.GONE);
                    td_qianming.setVisibility(View.GONE);
                    img_qianming.setVisibility(View.GONE);
                    td_dologin.setVisibility(View.VISIBLE);

                    editor.putBoolean(Commint.status, false);
                    editor.apply();
                    startActivity(new Intent(PersonalActivity.this, PersonalActivity.class));
                }
            }
        });
    }

    private void init() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        if (sp.getBoolean(Commint.status, false)) {
            td_dologin.setVisibility(View.GONE);
            td_name.setVisibility(View.VISIBLE);
            td_qianming.setVisibility(View.VISIBLE);
            img_qianming.setVisibility(View.VISIBLE);
            td_name.setText(sp.getString(Commint.account, "游客账号234282918dhajz"));
        } else {
            td_name.setVisibility(View.GONE);
            img_qianming.setVisibility(View.GONE);
            td_dologin.setVisibility(View.VISIBLE);
        }

    }

    private void initView() {
        img_touxiang = (ImageView) findViewById(R.id.img_touxiang);
        img_touxiang.setOnClickListener(this);
        td_dologin = (TextView) findViewById(R.id.td_dologin);
        img_shezhi = (ImageView) findViewById(R.id.img_shezhi);
        img_shezhi.setOnClickListener(this);

        td_dologin.setOnClickListener(this);
        first_layout = (RelativeLayout) findViewById(R.id.first_layout);
        first_layout.setOnClickListener(this);
        second_layout = (RelativeLayout) findViewById(R.id.second_layout);
        second_layout.setOnClickListener(this);
        third_layout = (RelativeLayout) findViewById(R.id.third_layout);
        third_layout.setOnClickListener(this);
        fourth_layout = (RelativeLayout) findViewById(R.id.fourth_layout);
        fourth_layout.setOnClickListener(this);
        bt_quit = (Button) findViewById(R.id.bt_quit);
        bt_quit.setOnClickListener(this);
        td_name = (TextView) findViewById(R.id.td_name);
        grid_geren = (GridView) findViewById(R.id.grid_geren);
        list_geren = (ListView) findViewById(R.id.list_geren);

        right_drawer = (ListView) findViewById(R.id.right_drawer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        td_qianming = (TextView) findViewById(R.id.td_qianming);
        td_qianming.setOnClickListener(this);
        img_qianming = (ImageView) findViewById(R.id.img_qianming);
        img_qianming.setOnClickListener(this);
        bt_quit.setOnClickListener(this);
        banner_person = (Banner) findViewById(R.id.banner_person);
    }

    @Override
    public void onClick(@NotNull View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.td_dologin:
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);//跳转页面
                break;
            case R.id.first_layout:
                intent.setClass(this, MainActivity.class);
                startActivity(intent);//跳转页面
                finish();
                break;
            case R.id.second_layout:

            case R.id.third_layout:
                break;
            case R.id.fourth_layout:
                intent.setClass(this, PersonalActivity.class);
                startActivity(intent);//跳转页面
                finish();
                break;
            case R.id.img_shezhi:
                drawer_layout.openDrawer(Gravity.RIGHT);//打开菜单
                break;
            case R.id.img_touxiang:
                startActivity(new Intent(this, CaptainActivity.class));
                //startCamera(1);
                //img_touxiang.setImageBitmap(stringToBitmap());
                break;
            case R.id.bt_quit:
                td_name.setVisibility(View.GONE);
                td_qianming.setVisibility(View.GONE);
                img_qianming.setVisibility(View.GONE);
                td_dologin.setVisibility(View.VISIBLE);

                editor.putBoolean(Commint.status, false);
                editor.apply();
                break;
        }
    }
}