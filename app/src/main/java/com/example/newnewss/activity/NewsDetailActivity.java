package com.example.newnewss.activity;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newnewss.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);

        // Intent로부터 데이터 가져오기
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");

        // TextView에 데이터 설정
        titleTextView.setText(title);
        descriptionTextView.setText(description);
    }
}
