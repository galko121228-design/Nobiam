package org.nobiam.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.R;

public class WorldsFragment extends Fragment {

    private EditText searchInput;
    private LinearLayout worldsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worlds, container, false);

        // Назад
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Поиск
        searchInput = view.findViewById(R.id.search_input);
        worldsList = view.findViewById(R.id.worlds_list);

        showEmptyState();

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                showEmptyState();
            }
        });

        return view;
    }

    private void showEmptyState() {
        worldsList.removeAllViews();
        TextView emptyText = new TextView(getContext());
        emptyText.setText("No worlds found");
        emptyText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        emptyText.setTextSize(16);
        emptyText.setGravity(View.TEXT_ALIGNMENT_CENTER);
        emptyText.setPadding(0, 80, 0, 80);
        worldsList.addView(emptyText);
    }
}
