package com.dong.circleimageviewdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dong.circleimageview.widget.PhotoPickerImageView;
import com.dong.circleimageviewdemo.R;

public class PhotoPickerActivity extends AppCompatActivity implements View.OnClickListener {

    private final String FILE_PATH= "/storage/emulated/0/Pictures/dongqiudi/1519716797741.jpg";
    private PhotoPickerImageView photoPickerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        photoPickerImageView = findViewById(R.id.photopickerimageview);
        photoPickerImageView.setFilePath(FILE_PATH)//设置头像源图片路径
                            .setRadius((int) getResources().getDimension(R.dimen.x100))//设置头像选择器半径/边长
                            .setMaskID(R.drawable.photo_mask_img)//设置遮罩层
                            .setShape(PhotoPickerImageView.CICLE_IMG)//设置遮罩层形状，CICLE_IMG圆形,SQUARE_IMG正方形
                            .setMaxScale(3)//设置图片最大放大倍数，默认为4
                            .setMinScale(0.6f);//设置图片最小缩小倍数，默认为0.5


        Button resetBtn = findViewById(R.id.reset);
        Button commitBtn = findViewById(R.id.commit);

        resetBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.reset:
                photoPickerImageView.reset();//截取效果还原
                break;
            case R.id.commit:
                photoPickerImageView.saveBitmap();//截取的头像保存到本地（只保存正方形头像即可）
                break;
        }
    }
}
