package org.nobiam.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.R;

import java.util.ArrayList;
import java.util.List;

public class WorldsFragment extends Fragment {

    private EditText searchInput;
    private LinearLayout worldsList;
    private List<String> allWorlds = new ArrayList<>();
    private List<String> filteredWorlds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worlds, container, false);

        // Назад
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Кнопка "Custom flat World"
        Button btnCustomFlat = view.findViewById(R.id.btn_custom_flat);
        btnCustomFlat.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Custom flat World (coming soon)", Toast.LENGTH_SHORT).show();
        });

        // Поиск
        searchInput = view.findViewById(R.id.search_input);
        worldsList = view.findViewById(R.id.worlds_list);

        // Показываем пустой список
        updateWorldsList();

        // Поиск
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterWorlds(s.toString());
            }
        });

        return view;
    }

    private void filterWorlds(String query) {
        filteredWorlds.clear();
        if (query.isEmpty()) {
            filteredWorlds.addAll(allWorlds);
        } else {
            for (String world : allWorlds) {
                if (world.toLowerCase().contains(query.toLowerCase())) {
                    filteredWorlds.add(world);
                }
            }
        }
        updateWorldsList();
    }

    private void updateWorldsList() {
        worldsList.removeAllViews();

        List<String> displayList = filteredWorlds.isEmpty() ? allWorlds : filteredWorlds;

        if (displayList.isEmpty()) {
            TextView emptyText = new TextView(getContext());
            emptyText.setText("No worlds found");
            emptyText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
            emptyText.setTextSize(16);
            emptyText.setGravity(View.TEXT_ALIGNMENT_CENTER);
            emptyText.setPadding(0, 80, 0, 80);
            worldsList.addView(emptyText);
        }
    }
}
