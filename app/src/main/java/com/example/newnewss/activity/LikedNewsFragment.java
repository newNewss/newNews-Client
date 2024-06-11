package com.example.newnewss.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnewss.DB.NewsDatabase;
import com.example.newnewss.DB.NewsItemEntity;
import com.example.newnewss.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class LikedNewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LikedNewsAdapter likedNewsAdapter;

    public LikedNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liked_news, container, false);

        recyclerView = view.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        NewsDatabase db = NewsDatabase.getInstance(requireContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NewsItemEntity> likedNews = db.newsItemDao().getAllLikedNews();
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        likedNewsAdapter = new LikedNewsAdapter(likedNews);
                        recyclerView.setAdapter(likedNewsAdapter);
                    }
                });
            }
        }).start();

        return view;
    }
}
