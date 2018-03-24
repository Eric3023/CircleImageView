package com.dong.circleimageviewdemo.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dong.circleimageview.widget.CircleImageView;
import com.dong.circleimageviewdemo.R;
import com.dong.circleimageviewdemo.model.UrlConfige;

public class CircleImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_image_view);

        CircleImageView circleImageView = findViewById(R.id.cicleimageview);
        circleImageView
                .setResourceID(R.drawable.img0)//圆形图片源为本地资源文件时
                .setPath(UrlConfige.IMG_URL)//圆形图片源为网络图片
                .setEdge(true)//设置是否显示边缘圆环
                .setEdgeColor(Color.WHITE)//设置边缘颜色
                .setEdgeWidth((int) getResources().getDimension(R.dimen.x3));//设置边缘宽度
    }
}
