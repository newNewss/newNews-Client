package com.example.newnewss.activity;

import android.content.Context;
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
        // HTML 태그 제거 후 설정
        holder.title.setText(Html.fromHtml(newsItem.getTitle()));
        holder.category.setText(newsItem.getCategory());

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터베이스에 저장
                NewsItemEntity newsItemEntity = new NewsItemEntity();
                newsItemEntity.setCategory(newsItem.getCategory());
                // HTML 태그 제거 후 저장
                newsItemEntity.setTitle(Html.fromHtml(newsItem.getTitle()).toString());

                NewsDatabase db = NewsDatabase.getInstance(context);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.newsItemDao().insert(newsItemEntity);
                        ((ApiActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "좋아한 기사에 추가되었습니다!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category;
        public Button likeBtn;

        public NewsViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            category = view.findViewById(R.id.category);
            likeBtn = view.findViewById(R.id.like_btn);
        }
    }
}
