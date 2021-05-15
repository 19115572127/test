package com.ydy.taole.activists;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ydy.taole.R;

public class ChouTiActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private FrameLayout content_frame;
    private ListView left_drawer;
    private DrawerLayout drawer_layout=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_ti);
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        left_drawer = (ListView) findViewById(R.id.right_drawer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                drawer_layout.openDrawer(Gravity.RIGHT);
                break;
        }
    }
}