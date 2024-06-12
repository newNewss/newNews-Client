package com.example.newnewss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.newnewss.DB.NewsDatabase;
import com.example.newnewss.DB.NewsItemEntity;
import com.example.newnewss.R;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView linkTextView;
    private EditText memoEditText;
    private Button shareButton;
    private Button saveMemoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        linkTextView = findViewById(R.id.linkTextView);
        memoEditText = findViewById(R.id.memoEditText);
        shareButton = findViewById(R.id.shareButton);
        saveMemoButton = findViewById(R.id.saveMemoButton);

        // Intent로부터 데이터 가져오기
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String link = getIntent().getStringExtra("link");
        String memo = getIntent().getStringExtra("memo");
        String category = getIntent().getStringExtra("category"); // 카테고리 정보 추가

        // TextView에 데이터 설정
        titleTextView.setText(title);
        descriptionTextView.setText(HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY));

        String linkText = "<a href=\"" + link + "\">" + link + "</a>";
        linkTextView.setText(HtmlCompat.fromHtml(linkText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        memoEditText.setText(memo);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + description + "\n" + link);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "공유"));
            }
        });

        saveMemoButton.setOnClickListener(v -> {
            String newMemo = memoEditText.getText().toString();
            saveMemoToDatabase(title, description, link, category, newMemo);
        });
    }

    private void saveMemoToDatabase(String title, String description, String link, String category, String newMemo) {
        NewsDatabase db = NewsDatabase.getInstance(this);
        new Thread(() -> {
            NewsItemEntity newsItem = db.newsItemDao().findByTitle(title);
            if (newsItem != null) {
                newsItem.setMemo(newMemo);
                db.newsItemDao().update(newsItem);
            } else {
                newsItem = new NewsItemEntity();
                newsItem.setTitle(title);
                newsItem.setDescription(description);
                newsItem.setLink(link);
                newsItem.setCategory(category); // 카테고리 설정
                newsItem.setMemo(newMemo);
                db.newsItemDao().insert(newsItem);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "메모가 저장되었습니다!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
