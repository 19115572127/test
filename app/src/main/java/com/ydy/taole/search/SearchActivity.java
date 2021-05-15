package com.ydy.taole.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private ClearEditTextView et;
    private TextView back;
    private LinearLayout ll;
    private LabelLayoutView label;
    private LinearLayout labeled;
    //搜索记录
    private List<String> searchList = new ArrayList<>();
    //热门标签
    private String[] labelList = new String[]{"标签一", "标签二", "标签三", "标签四", "标签五", "标签六"};
    private TextView name;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initLabel();
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (et.getCompoundDrawables()[2] != null) {
                    //当抬起时
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //当点击位置的X坐标大于EditText宽度减去右间距减去清除图标的宽度并且小于EditText宽度减去右间距之间的区域，即清除图标的位置
                        boolean xTouchable = event.getX() > (et.getWidth() - et.getPaddingRight() - et.mClearDrawable.getIntrinsicWidth())
                                && (event.getX() < (et.getWidth() - et.getPaddingRight()));
                        boolean yTouchable = event.getY() > (et.getHeight() - et.getPaddingBottom() - et.mClearDrawable.getIntrinsicHeight())
                                && (event.getY() < (et.getHeight() + et.mClearDrawable.getIntrinsicHeight()) / 2);
                        //清除文本
                        if (xTouchable && yTouchable) {
                            et.setText("");
                            if (searchList.size() != 0) {
                                searchList.clear();
                                //initLabel();
                                labeled.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                return false;
            }
        });
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("".equals(et.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        ((InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        searchList.clear();
                        searchList.add(et.getText().toString());
                        //initLabel();
                        labeled.setVisibility(View.GONE);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initLabel() {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 10, 10);
        for (int i = 0; i < labelList.length; i++) {
            TextView textView = new TextView(this);
            textView.setTag(i);
            textView.setTextSize(13);
            textView.setText(labelList[i]);
            textView.setPadding(25, 10, 25, 10);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            label.addView(textView, layoutParams);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll.setFocusable(true);
                    ll.setFocusableInTouchMode(true);
                    ll.requestFocus();
                    et.getText().clear();
                    et.setText(labelList[finalI]);
                    ((InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchList.clear();
                    //initLabel();
                    labeled.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void initView() {
        et = (ClearEditTextView) findViewById(R.id.main_et);
        back = (TextView) findViewById(R.id.main_back);
        ll = (LinearLayout) findViewById(R.id.main_ll);
        label = (LabelLayoutView) findViewById(R.id.main_label);
        labeled = (LinearLayout) findViewById(R.id.main_labeled);
        name = (TextView) findViewById(R.id.name);

    }

}