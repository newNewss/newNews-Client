package com.example.newnewss.activity;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnewss.R;
import com.example.newnewss.DB.NewsDatabase;
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
        holder.memo.setText(newsItem.getMemo());  // 메모 표시

        holder.unlikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    NewsItemEntity currentNewsItem = likedNewsList.get(adapterPosition);
                    NewsDatabase db = NewsDatabase.getInstance(holder.itemView.getContext());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.newsItemDao().deleteByTitleAndCategory(currentNewsItem.getTitle(), currentNewsItem.getCategory());
                            ((AppCompatActivity) holder.itemView.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    likedNewsList.remove(adapterPosition);
                                    notifyItemRemoved(adapterPosition);
                                    notifyItemRangeChanged(adapterPosition, likedNewsList.size());
                                    Toast.makeText(holder.itemView.getContext(), "좋아요가 취소되었습니다!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                intent.putExtra("title", newsItem.getTitle());
                intent.putExtra("description", Html.fromHtml(newsItem.getDescription()).toString());
                intent.putExtra("link", Html.fromHtml(newsItem.getLink()).toString());
                intent.putExtra("memo", newsItem.getMemo());  // 메모 전달
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return likedNewsList.size();
    }

    public static class LikedNewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category, memo;
        public Button unlikeBtn, detailBtn;

        public LikedNewsViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            category = view.findViewById(R.id.category);
            memo = view.findViewById(R.id.memoTextView);  // 메모 텍스트뷰
            unlikeBtn = view.findViewById(R.id.unlike_btn);
            detailBtn = view.findViewById(R.id.detail_btn);
        }
    }
}
