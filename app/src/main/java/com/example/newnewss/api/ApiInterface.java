package com.example.newnewss.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search/{type}")
    Call<String> getBookSearchResult(
            @Header("X-Naver-Client-Id") String ClientId,
            @Header("X-Naver-Client-Secret") String secretPwd,
            @Path("type") String type,
            @Query("query") String query,
            @Query("sort") String sort,
            @Query("display") int display
    );
}
