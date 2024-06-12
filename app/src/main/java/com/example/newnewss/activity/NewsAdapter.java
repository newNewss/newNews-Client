package com.example.newnewss.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnewss.R;
import com.example.newnewss.api.NewsItem;
import com.example.newnewss.DB.NewsDatabase;
import com.example.newnewss.DB.NewsItemEntity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;
    private Context context;

    public NewsAdapter(List<NewsItem> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsList = newsItemList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);
        holder.title.setText(Html.fromHtml(newsItem.getTitle()));
        holder.category.setText(newsItem.getCategory());

        holder.likeBtn.setOnClickListener(v -> {
            NewsItemEntity newsItemEntity = new NewsItemEntity();
            newsItemEntity.setCategory(newsItem.getCategory());
            newsItemEntity.setDescription(newsItem.getDescription());
            newsItemEntity.setLink(newsItem.getLink());
            newsItemEntity.setTitle(Html.fromHtml(newsItem.getTitle()).toString());

            NewsDatabase db = NewsDatabase.getInstance(context);
            new Thread(() -> {
                db.newsItemDao().insert(newsItemEntity);
                ((ApiActivity) context).runOnUiThread(() ->
                        Toast.makeText(context, "좋아한 기사에 추가되었습니다!", Toast.LENGTH_SHORT).show());
            }).start();
        });

        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("title", Html.fromHtml(newsItem.getTitle()).toString());
            intent.putExtra("description", Html.fromHtml(newsItem.getDescription()).toString());
            intent.putExtra("link", Html.fromHtml(newsItem.getLink()).toString());
            intent.putExtra("memo", newsItem.getMemo());  // 메모 전달
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category;
        public Button likeBtn, detailBtn;

        public NewsViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            category = view.findViewById(R.id.category);
            likeBtn = view.findViewById(R.id.like_btn);
            detailBtn = view.findViewById(R.id.detail_btn);
        }
    }
}
