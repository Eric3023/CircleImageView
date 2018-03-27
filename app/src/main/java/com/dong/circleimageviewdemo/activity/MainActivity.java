package com.dong.circleimageviewdemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dong.circleimageview.widget.CircleImageView;
import com.dong.circleimageview.widget.PhotoPickerImageView;
import com.dong.circleimageviewdemo.R;
import com.dong.circleimageviewdemo.model.UrlConfige;
import com.tencent.bugly.crashreport.CrashReport;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCircleImageView = findViewById(R.id.btnCircleImageView);
        Button btnRoundImageView = findViewById(R.id.btnRoundImageView);
        Button btnPhotoPickerImageView = findViewById(R.id.btnPhotoPickerImageView);


        btnCircleImageView.setOnClickListener(this);
        btnRoundImageView.setOnClickListener(this);
        btnPhotoPickerImageView.setOnClickListener(this);

//        CrashReport.testJavaCrash();//Bugly异常上报测试
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnCircleImageView:
                Intent i =new Intent(this, CircleImageViewActivity.class);
                startActivity(i);
                break;
            case R.id.btnRoundImageView:
                Intent it =new Intent(this, RoundImageViewActivity.class);
                startActivity(it);
                break;
            case R.id.btnPhotoPickerImageView:
                Intent intent =new Intent(this, PhotoSelectActivity.class);
                startActivity(intent);
                break;
        }
    }
}
