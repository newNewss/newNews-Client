package com.example.newnewss.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsSearchResponse {
    @SerializedName("items")
    private List<NewsItem> items;

    public List<NewsItem> getItems() {
        return items;
    }
}
