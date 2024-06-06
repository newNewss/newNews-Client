package com.example.newnewss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnewss.R;
import com.example.newnewss.api.ApiClient;
import com.example.newnewss.api.ApiInterface;
import com.example.newnewss.api.NewsItem;
import com.example.newnewss.api.NewsSearchResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "W0pelb0v3yyc82lSyV17"; // 애플리케이션 클라이언트 아이디
    private static final String CLIENT_SECRET = "sbZ7kkmwn9"; // 애플리케이션 클라이언트 시크릿
    private static final String sort = "sim";

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsItemList = new ArrayList<>();
    private List<String> selectedCategories = new ArrayList<>();
    private int articleCount;

    private static final int REQUEST_CATEGORY_SELECTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        // 선택된 카테고리를 Intent로부터 가져오기
        Intent intent = getIntent();
        selectedCategories = intent.getStringArrayListExtra("selectedCategories");
        articleCount = intent.getIntExtra("articleCount", 3); // Default to 3 if not provided

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(newsItemList, this); // Context를 추가로 전달
        recyclerView.setAdapter(newsAdapter);

        FloatingActionButton selectCategoryButton = findViewById(R.id.selectCategoryButton);
        selectCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApiActivity.this, NewsCategoriesActivity.class);
                startActivityForResult(intent, REQUEST_CATEGORY_SELECTION);
            }
        });

        // 메인페이지(추천페이지)에서 하단바 클릭시 이벤트
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab_home); // 현재 화면을 표시
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.tab_home) {
                    Toast.makeText(ApiActivity.this, "현재 페이지입니다.", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.tab_likeNews) {
                    Intent intent = new Intent(ApiActivity.this, LikedNewsActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.userSetting) {
                    Toast.makeText(ApiActivity.this, "구현 중인 기능입니다.", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });

        fetchNews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CATEGORY_SELECTION && resultCode == RESULT_OK && data != null) {
            selectedCategories = data.getStringArrayListExtra("selectedCategories");
            articleCount = data.getIntExtra("articleCount", 3);
            if (selectedCategories != null && !selectedCategories.isEmpty()) {
                newsItemList.clear();
                fetchNews();
            }
        }
    }

    private void fetchNews() {
        ApiInterface apiService = ApiClient.getInstance().create(ApiInterface.class);

        //선택된 카테고리 사용
        for (String category : selectedCategories) {
            apiService.searchNews(CLIENT_ID, CLIENT_SECRET, category, sort, articleCount).enqueue(new Callback<NewsSearchResponse>() {
                @Override
                public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<NewsItem> items = response.body().getItems();

                        // 카테고리 정보 추가
                        for (NewsItem item : items) {
                            item.setCategory(category);
                        }

                        newsItemList.addAll(items);
                        newsAdapter.notifyDataSetChanged();
                        Log.d("ApiActivity", "Category: " + category);
                        for (NewsItem item : items) {
                            Log.d("ApiActivity", "Title: " + item.getTitle());
                            Log.d("ApiActivity", "Description: " + item.getDescription());
                            Log.d("ApiActivity", "link: " + item.getLink());
                        }
                    } else {
                        Log.e("ApiActivity", "Response failed for category: " + category);
                    }
                }

                @Override
                public void onFailure(Call<NewsSearchResponse> call, Throwable t) {
                    Log.e("ApiActivity", "API call failed for category: " + category, t);
                    t.printStackTrace();
                }
            });
        }
    }
}
