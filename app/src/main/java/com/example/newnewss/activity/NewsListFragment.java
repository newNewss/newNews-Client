package com.example.newnewss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnewss.R;
import com.example.newnewss.api.ApiClient;
import com.example.newnewss.api.ApiInterface;
import com.example.newnewss.api.NewsItem;
import com.example.newnewss.api.NewsSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment {

    private static final String CLIENT_ID = "W0pelb0v3yyc82lSyV17"; // 애플리케이션 클라이언트 아이디
    private static final String CLIENT_SECRET = "sbZ7kkmwn9"; // 애플리케이션 클라이언트 시크릿
    private static final String sort = "sim";

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsItemList = new ArrayList<>();
    private List<String> selectedCategories = new ArrayList<>();
    private int articleCount;

    private static final int REQUEST_CATEGORY_SELECTION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        recyclerView = view.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter(newsItemList, getActivity());
        recyclerView.setAdapter(newsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 뉴스 데이터 가져오기
        fetchNews();
    }

    private void fetchNews() {
        ApiInterface apiService = ApiClient.getInstance().create(ApiInterface.class);

        // 선택된 카테고리 사용
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
                    } else {
                        Toast.makeText(getContext(), "뉴스를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NewsSearchResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "뉴스를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }

    // 선택된 카테고리와 기사 수를 설정하는 메소드
    public void setNewsPreferences(List<String> selectedCategories, int articleCount) {
        this.selectedCategories = selectedCategories;
        this.articleCount = articleCount;
    }
}