package com.dong.circleimageviewdemo.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dong.circleimageview.widget.PhotoPickerImageView;
import com.dong.circleimageviewdemo.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PhotoPickerActivity extends AppCompatActivity implements View.OnClickListener {

    private final String FILE_PATH= "/sdcard/Pictures/2018032601.jpg";
    private PhotoPickerImageView photoPickerImageView;
    private final int REQUEST_ALBUM_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        //打开相册
        startPhotoApplication();


        photoPickerImageView = findViewById(R.id.photopickerimageview);
        Button resetBtn = findViewById(R.id.reset);
        Button commitBtn = findViewById(R.id.commit);

        resetBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }

    private void startPhotoApplication() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_ALBUM_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ALBUM_OK:
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream inputStream = resolver.openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    initPhotoPickerImageView(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void initPhotoPickerImageView(Bitmap bitmap) {
        photoPickerImageView
//                .setFilePath(FILE_PATH)//设置头像源图片路径（本地文件路径）
                .setBitmap(bitmap)////设置头像源Bitmap,此处为相册选择返回的bitmap
                .setRadius((int) getResources().getDimension(R.dimen.x100))//设置头像选择器半径/边长
                .setMaskID(R.drawable.photo_mask_img)//设置遮罩层
                .setShape(PhotoPickerImageView.CICLE_IMG)//设置遮罩层形状，CICLE_IMG圆形,SQUARE_IMG正方形
                .setMaxScale(3)//设置图片最大放大倍数，默认为4
                .setMinScale(0.6f);//设置图片最小缩小倍数，默认为0.5
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.reset:
                photoPickerImageView.reset();//截取效果还原
                break;
            case R.id.commit:
                Bitmap bitmap = photoPickerImageView.saveBitmap(true);//截取头像（return截取的bitmap， param1是否保存到本地）
                break;
        }
    }
}
