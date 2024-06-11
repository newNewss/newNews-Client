package com.example.newnewss.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newnewss.R;
import com.example.newnewss.api.NewsItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryKeywordsFragment extends Fragment {

    private Spinner categorySpinner;
    private TextView keywordsTextView;

    private Map<String, List<NewsItem>> newsItemsByCategory = new HashMap<>();
    private Context context;

    public CategoryKeywordsFragment() {
        // 기본 생성자
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_keywords, container, false);

        categorySpinner = view.findViewById(R.id.category_spinner);
        keywordsTextView = view.findViewById(R.id.keywords_text_view);

        view.findViewById(R.id.btn_show_keywords).setOnClickListener(v -> showKeywords());

        if (!newsItemsByCategory.isEmpty()) {
            setupCategorySpinner();
        }

        return view;
    }

    public void setNewsItemsByCategory(Map<String, List<NewsItem>> newsItemsByCategory) {
        this.newsItemsByCategory = newsItemsByCategory;
        if (context != null) {
            setupCategorySpinner();
        }
    }

    private void setupCategorySpinner() {
        List<String> categories = new ArrayList<>(newsItemsByCategory.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void showKeywords() {
        String selectedCategory = (String) categorySpinner.getSelectedItem();
        if (selectedCategory != null && newsItemsByCategory.containsKey(selectedCategory)) {
            List<NewsItem> newsItems = newsItemsByCategory.get(selectedCategory);
            Map<String, Integer> keywordFrequency = new HashMap<>();

            for (NewsItem item : newsItems) {
                String keywords = extractKeywords(item.getTitle() + " " + item.getDescription());
                StringTokenizer tokenizer = new StringTokenizer(keywords, " ");
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (keywordFrequency.containsKey(token)) {
                        keywordFrequency.put(token, keywordFrequency.get(token) + 1);
                    } else {
                        keywordFrequency.put(token, 1);
                    }
                }
            }

            List<Map.Entry<String, Integer>> sortedKeywords = new ArrayList<>(keywordFrequency.entrySet());
            Collections.sort(sortedKeywords, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            StringBuilder keywordsDisplay = new StringBuilder();
            for (int i = 0; i < Math.min(sortedKeywords.size(), 5); i++) {
                Map.Entry<String, Integer> entry = sortedKeywords.get(i);
                keywordsDisplay.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            keywordsTextView.setText(keywordsDisplay.toString().trim());
        }
    }

    private String extractKeywords(String text) {
        // 단어만 추출하는 정규 표현식, 조사와 동사 제외
        Pattern pattern = Pattern.compile("\\b[가-힣]{2,}\\b");
        Matcher matcher = pattern.matcher(text);
        StringBuilder keywords = new StringBuilder();
        while (matcher.find()) {
            String word = matcher.group();
            if (!isStopWord(word)) {
                keywords.append(word).append(" ");
            }
        }
        return keywords.toString().trim();
    }

    private boolean isStopWord(String word) {
        // 조사 및 불용어 리스트
        String[] stopWords = {"은", "는", "이", "가", "을", "를", "에", "와", "과", "로", "으로", "에서", "의", "에게", "한", "하고", "도", "로서", "이라", "다고", "이다", "했다", "라고", "인", "만", "어디"};
        for (String stopWord : stopWords) {
            if (word.endsWith(stopWord)) {
                return true;
            }
        }
        return false;
    }
}
