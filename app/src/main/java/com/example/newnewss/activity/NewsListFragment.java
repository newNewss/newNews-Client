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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment {

    private static final String CLIENT_ID = "W0pelb0v3yyc82lSyV17"; // 애플리케이션 클라이언트 아이디
    private static final String CLIENT_SECRET = "sbZ7kkmwn9"; // 애플리케이션 클라이언트 시크릿
    private static final String SORT = "sim"; // 정렬 기준

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsItemList;
    private List<String> selectedCategories;
    private int articleCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        recyclerView = view.findViewById(R.id.newsRecyclerView);
        newsItemList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsItemList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);

        // 선택된 카테고리와 기사 수를 받아옴
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            selectedCategories = intent.getStringArrayListExtra("selectedCategories");
            articleCount = intent.getIntExtra("articleCount", 3);
        }

        fetchNews();
        return view;
    }

    void fetchNews() {
        if (selectedCategories == null || selectedCategories.isEmpty()) {
            Toast.makeText(getContext(), "카테고리가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        newsItemList.clear();
        ApiInterface apiService = ApiClient.getInstance().create(ApiInterface.class);
        Map<String, List<NewsItem>> newsItemsByCategory = new HashMap<>();

        for (String category : selectedCategories) {
            apiService.searchNews(CLIENT_ID, CLIENT_SECRET, category, SORT, articleCount).enqueue(new Callback<NewsSearchResponse>() {
                @Override
                public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<NewsItem> items = response.body().getItems();

                        for (NewsItem item : items) {
                            item.setCategory(category);
                        }

                        if (!newsItemsByCategory.containsKey(category)) {
                            newsItemsByCategory.put(category, new ArrayList<>());
                        }
                        newsItemsByCategory.get(category).addAll(items);

                        newsItemList.addAll(items);
                        newsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "뉴스를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    if (category.equals(selectedCategories.get(selectedCategories.size() - 1))) {
                        getActivity().runOnUiThread(() -> ((ApiActivity) getActivity()).passNewsDataToKeywordsFragment(newsItemsByCategory));
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
