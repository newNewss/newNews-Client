package com.example.newnewss.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnewss.R;
import com.example.newnewss.DB.NewsItemEntity;

import java.util.List;

public class LikedNewsAdapter extends RecyclerView.Adapter<LikedNewsAdapter.LikedNewsViewHolder> {

    private List<NewsItemEntity> likedNewsList;

    public LikedNewsAdapter(List<NewsItemEntity> likedNewsList) {
        this.likedNewsList = likedNewsList;
    }

    @NonNull
    @Override
    public LikedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_liked_news, parent, false);
        return new LikedNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedNewsViewHolder holder, int position) {
        NewsItemEntity newsItem = likedNewsList.get(position);
        holder.title.setText(newsItem.getTitle());
        holder.category.setText(newsItem.getCategory());
    }

    @Override
    public int getItemCount() {
        return likedNewsList.size();
    }

    public static class LikedNewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category;

        public LikedNewsViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            category = view.findViewById(R.id.category);
        }
    }
}
