package com.example.newnewss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newnewss.R;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoriesActivity extends AppCompatActivity {

    private ListView listView;
    private Button selectButton;
    private ArrayAdapter<String> adapter;
    private List<String> newsCategoriesList;
    private List<String> selectedCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_categories);

        listView = findViewById(R.id.newsCategories);
        selectButton = findViewById(R.id.selectButton);

        newsCategoriesList = new ArrayList<>();
        newsCategoriesList.add("연예");
        newsCategoriesList.add("IT");
        newsCategoriesList.add("스포츠");
        newsCategoriesList.add("경제");
        newsCategoriesList.add("정치");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, newsCategoriesList);
        listView.setAdapter(adapter);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCategories = new ArrayList<>();
                for (int i = 0; i < listView.getCount(); i++) {
                    if (listView.isItemChecked(i)) {
                        selectedCategories.add(newsCategoriesList.get(i));
                    }
                }

                if (selectedCategories.isEmpty()) {
                    Toast.makeText(NewsCategoriesActivity.this, "항목을 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NewsCategoriesActivity.this, ApiActivity.class);
                    intent.putStringArrayListExtra("selectedCategories", (ArrayList<String>) selectedCategories);
                    startActivity(intent);
                }
            }
        });
    }
}
