package com.example.newnewss.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newnewss.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

public class LikedNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_news);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.tab_home) {
                    Intent intent = new Intent(LikedNewsActivity.this, ApiActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.tab_likeBooks) {
                    Toast.makeText(LikedNewsActivity.this, "현재 페이지입니다.", Toast.LENGTH_SHORT).show();
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
