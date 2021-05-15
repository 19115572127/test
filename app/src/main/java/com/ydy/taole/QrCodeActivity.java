package com.ydy.taole;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.utils.EncodingUtils;

/**
 * 生成二维码
 */
public class QrCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_txt;
    private Button encode_btn1;
    private ImageView code_image1;
    private Button encode_btn2;
    private ImageView code_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        initView();

    }

    private void initView() {
        input_txt = (EditText) findViewById(R.id.input_txt);
        encode_btn1 = (Button) findViewById(R.id.encode_btn1);
        code_image1 = (ImageView) findViewById(R.id.code_image1);
        encode_btn2 = (Button) findViewById(R.id.encode_btn2);
        code_image2 = (ImageView) findViewById(R.id.code_image2);

        encode_btn1.setOnClickListener(this);
        encode_btn2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 生成不带Logo的二维码点击事件
             * @param view
             */
            case R.id.encode_btn1:
                submit();
                //生成二维码
                Bitmap codeBitmap = EncodingUtils.createQRCode(input_txt.getText().toString(),500,500,null);
                code_image1.setImageBitmap(codeBitmap);//显示二维码
                break;
            /**
             * 生成带Logo的二维码
             * @param view
             */
            case R.id.encode_btn2:
                submit();
                //获取logo资源,
                //R.drawable.logo为logo图片
                Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                //生成二维码
                Bitmap codeBitmap1 = EncodingUtils.createQRCode(input_txt.getText().toString(),500,500,logoBitmap);
                code_image2.setImageBitmap(codeBitmap1);//显示二维码
                break;
        }
    }

    private void submit() {
        // validate
        String txt = input_txt.getText().toString().trim();
        if (TextUtils.isEmpty(txt)) {
            Toast.makeText(this, "Enter the content to generate a two-digit code", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
