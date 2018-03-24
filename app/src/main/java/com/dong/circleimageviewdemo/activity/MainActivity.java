package com.dong.circleimageviewdemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dong.circleimageview.widget.CircleImageView;
import com.dong.circleimageviewdemo.R;
import com.dong.circleimageviewdemo.model.UrlConfige;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCircleImageView = findViewById(R.id.btnCircleImageView);
        Button btnRoundImageView = findViewById(R.id.btnRoundImageView);

        btnCircleImageView.setOnClickListener(this);
        btnRoundImageView.setOnClickListener(this);
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
                Intent intent =new Intent(this, RoundImageViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
