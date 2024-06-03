package com.example.newnewss.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsItemDao {
    @Insert
    void insert(NewsItemEntity newsItem);

    @Query("SELECT * FROM liked_news")
    List<NewsItemEntity> getAllLikedNews();
}
