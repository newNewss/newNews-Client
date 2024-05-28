package com.example.newnewss.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsItem implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

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
}
