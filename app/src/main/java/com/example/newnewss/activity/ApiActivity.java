package com.example.newnewss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newnewss.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ApiActivity extends AppCompatActivity {

    private static final int TAB_HOME = R.id.tab_home;
    private static final int TAB_LIKE_NEWS = R.id.tab_likeNews;
    private static final int USER_SETTING = R.id.userSetting;
    private static final int TAB_KEYWORDS = R.id.tab_keywords;

    private NewsListFragment newsListFragment; // 멤버 변수로 선언

    private static final int REQUEST_CATEGORY_SELECTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        // 뉴스 리스트 프래그먼트 초기화
        newsListFragment = new NewsListFragment();

        // 선택된 카테고리 및 기사 수 가져오기
        Intent intent = getIntent();
        if (intent != null) {
            newsListFragment.setNewsPreferences(intent.getStringArrayListExtra("selectedCategories"), intent.getIntExtra("articleCount", 3));
        }

        // 뉴스 리스트 프래그먼트를 로드
        loadFragment(newsListFragment);

        // 카테고리 선택 버튼 이벤트 설정
        findViewById(R.id.selectCategoryButton).setOnClickListener(view -> {
            Intent categoryIntent = new Intent(ApiActivity.this, NewsCategoriesActivity.class);
        });

        // 하단바 클릭 이벤트 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab_home); // 현재 화면을 표시
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == TAB_HOME) {
                loadFragment(newsListFragment);
                return true;
            } else if (id == TAB_LIKE_NEWS) {
                Intent intent2 = new Intent(ApiActivity.this, LikedNewsActivity.class);
                startActivity(intent2);
                return true;
            } else if (id == USER_SETTING) {
                Toast.makeText(ApiActivity.this, "구현 중인 기능입니다.", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == TAB_KEYWORDS) {
                loadFragment(new CategoryKeywordsFragment());
                return true;
            }
            return false;
        });
    }

    // 프래그먼트 로드 메소드
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // 카테고리 선택 결과 처리 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CATEGORY_SELECTION && resultCode == RESULT_OK && data != null) {
            newsListFragment.setNewsPreferences(data.getStringArrayListExtra("selectedCategories"), data.getIntExtra("articleCount", 3));
        }
    }
}
