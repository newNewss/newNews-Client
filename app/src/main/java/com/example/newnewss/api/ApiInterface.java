package com.example.newnewss.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search/news.json")
    Call<NewsSearchResponse> searchNews(
            @Header("X-Naver-Client-Id") String ClientId,
            @Header("X-Naver-Client-Secret") String secretPwd,
            @Query("query") String query,
            @Query("sort") String sort,
            @Query("display") Integer display
    );
}
