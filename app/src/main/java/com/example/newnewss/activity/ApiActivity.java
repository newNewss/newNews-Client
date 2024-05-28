package com.example.newnewss.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.newnewss.R;
import com.example.newnewss.api.ApiClient;
import com.example.newnewss.api.ApiInterface;
import com.example.newnewss.api.NewsItem;
import com.example.newnewss.api.NewsSearchResponse;


import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "W0pelb0v3yyc82lSyV17"; // 애플리케이션 클라이언트 아이디
    private static final String CLIENT_SECRET = "sbZ7kkmwn9"; // 애플리케이션 클라이언트 시크릿
    private static final List<String> categories = Arrays.asList("스포츠", "연애", "경제", "사회");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiInterface apiService = ApiClient.getInstance().create(ApiInterface.class);

        for (String category : categories) {
            String query;
            try {
                query = URLEncoder.encode(category + " 뉴스", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("검색어 인코딩 실패", e);
            }

            apiService.searchNews(CLIENT_ID, CLIENT_SECRET, query).enqueue(new Callback<NewsSearchResponse>() {
                @Override
                public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<NewsItem> items = response.body().getItems();
                        System.out.println("Category: " + category);
                        for (NewsItem item : items) {
                            System.out.println("Title: " + item.getTitle());
                            System.out.println("Description: " + item.getDescription());
                        }
                    } else {
                        System.err.println("Response failed for category: " + category);
                    }
                }

                @Override
                public void onFailure(Call<NewsSearchResponse> call, Throwable t) {
                    System.err.println("API call failed for category: " + category);
                    t.printStackTrace();
                }
            });
        }
    }
}
