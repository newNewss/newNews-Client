package com.example.newnewss.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.newnewss.R;
import com.example.newnewss.api.ApiClient;
import com.example.newnewss.api.ApiInterface;
import com.example.newnewss.api.NewsItem;
import com.example.newnewss.api.NewsSearchResponse;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "W0pelb0v3yyc82lSyV17"; // 애플리케이션 클라이언트 아이디
    private static final String CLIENT_SECRET = "sbZ7kkmwn9"; // 애플리케이션 클라이언트 시크릿
    private static final List<String> categories = Arrays.asList("연애", "IT", "스포츠", "경제", "정치");
    private static final String sort = "sim";
    private static final Integer display = 3;

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(newsItemList);
        recyclerView.setAdapter(newsAdapter);

        fetchNews();
    }

    private void fetchNews() {
        ApiInterface apiService = ApiClient.getInstance().create(ApiInterface.class);

        for (String category : categories) {
            apiService.searchNews(CLIENT_ID, CLIENT_SECRET, category, sort, display).enqueue(new Callback<NewsSearchResponse>() {
                @Override
                public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<NewsItem> items = response.body().getItems();
                        newsItemList.addAll(items);
                        newsAdapter.notifyDataSetChanged();
                        Log.d("ApiActivity", "Category: " + category);
                        for (NewsItem item : items) {
                            Log.d("ApiActivity", "Title: " + item.getTitle());
                            Log.d("ApiActivity", "Description: " + item.getDescription());
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
