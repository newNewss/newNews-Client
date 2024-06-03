package com.example.newnewss.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newnewss.R;
import com.example.newnewss.DB.NewsDatabase;
import com.example.newnewss.DB.NewsItemEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class LikedNewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LikedNewsAdapter likedNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_news);

        recyclerView = findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NewsDatabase db = NewsDatabase.getInstance(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NewsItemEntity> likedNews = db.newsItemDao().getAllLikedNews();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        likedNewsAdapter = new LikedNewsAdapter(likedNews);
                        recyclerView.setAdapter(likedNewsAdapter);
                    }
                });
            }
        }).start();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.tab_home) {
                    finish();
                    return true;
                } else if (id == R.id.tab_likeNews) {
                    Toast.makeText(LikedNewsActivity.this, "현재 화면입니다.", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.userSetting) {
                    Toast.makeText(LikedNewsActivity.this, "구현 중인 기능입니다.", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
