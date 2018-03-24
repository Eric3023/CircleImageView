package com.dong.circleimageviewdemo.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dong.circleimageview.widget.RoundImageView;
import com.dong.circleimageviewdemo.R;
import com.dong.circleimageviewdemo.model.UrlConfige;

public class RoundImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view);

        RoundImageView roundImageView =findViewById(R.id.roundimageview);
        roundImageView
                .setResourceID(R.drawable.img0)//圆角图片源为资源图片时
//                .setPath(UrlConfige.IMG_URL)//圆角图片源为网咯图片
                .setRadius((int) getResources().getDimension(R.dimen.x10))//圆角弧度
                .setImageScale(RoundImageView.CENTER_CROP)//填充类型
                .setBackgroudColor(Color.WHITE);//圆角图片的背景颜色
    }
}
