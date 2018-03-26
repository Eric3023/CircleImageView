package com.dong.circleimageviewdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dong.circleimageviewdemo.R;

public class PhotoSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);

        Button btn = findViewById(R.id.btn_photo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhotoSelectActivity.this, PhotoPickerActivity.class);
                PhotoSelectActivity.this.startActivity(i);
            }
        });
    }
}
