package com.example.newnewss.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "liked_news")
public class NewsItemEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;

    private String link;
    private String category;
    private String title;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getDescription() {return description; }

    public String getLink() {return link; }
    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) { this.description = description; }

    public void setLink(String link) { this.link = link; }
}