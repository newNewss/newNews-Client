package com.example.newnewss.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.newnewss.activity.ApiActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ApiActivity를 시작하는 Intent 생성
        Intent intent = new Intent(this, ApiActivity.class);
        startActivity(intent);

        // MainActivity를 종료하여 ApiActivity가 표시되도록 함
        finish();
    }

}