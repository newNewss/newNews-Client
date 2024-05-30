package com.example.newnewss.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsItem implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    // 카테고리 정보 추가
    @SerializedName("category")
    private String category;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
