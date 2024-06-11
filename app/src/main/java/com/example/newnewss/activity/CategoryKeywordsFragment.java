package com.example.newnewss.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newnewss.R;

public class CategoryKeywordsFragment extends Fragment {

    private Spinner categorySpinner;
    private TextView keywordsTextView;

    public CategoryKeywordsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_keywords, container, false);

        categorySpinner = view.findViewById(R.id.category_spinner);
        keywordsTextView = view.findViewById(R.id.keywords_text_view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        view.findViewById(R.id.btn_show_keywords).setOnClickListener(v -> {
            showKeywordsForCategory(categorySpinner.getSelectedItem().toString());
        });

        return view;
    }

    private void showKeywordsForCategory(String category) {

    }
}
