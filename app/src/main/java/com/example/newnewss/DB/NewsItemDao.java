package com.example.newnewss.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface NewsItemDao {
    @Insert
    void insert(NewsItemEntity newsItem);

    @Query("SELECT * FROM liked_news")
    List<NewsItemEntity> getAllLikedNews();

    @Query("DELETE FROM liked_news WHERE title = :title AND category = :category")
    void deleteByTitleAndCategory(String title, String category);

    @Query("SELECT * FROM liked_news WHERE category = :category")
    List<NewsItemEntity> getNewsByCategory(String category);

    @Query("SELECT * FROM liked_news WHERE title = :title LIMIT 1")
    NewsItemEntity findByTitle(String title);

    @Update
    void update(NewsItemEntity newsItem);
}
