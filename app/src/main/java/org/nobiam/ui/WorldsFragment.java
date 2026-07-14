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

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

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

        // Временные данные (позже заменим на реальные)
        loadDemoWorlds();

        // Обновляем список
        updateWorldsList();

        // Поиск в реальном времени
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

    private void loadDemoWorlds() {
        allWorlds.clear();
        // Временные демо-миры (потом заменим на реальные из папки Minecraft)
        allWorlds.add("My Survival World");
        allWorlds.add("Creative Flat");
        allWorlds.add("Hardcore Island");
        allWorlds.add("Skyblock v2");
        allWorlds.add("Redstone Lab");
        allWorlds.add("Parkour Adventure");
        allWorlds.add("Castle Siege");
        allWorlds.add("Underwater City");
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
            return;
        }

        float density = getResources().getDisplayMetrics().density;
        int accentColor = AccentColorManager.getColor(requireContext());

        for (String world : displayList) {
            // Карточка мира
            CardView card = new CardView(getContext());
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, (int)(6 * density), 0, (int)(6 * density));
            card.setLayoutParams(cardParams);
            card.setRadius(12 * density);
            card.setCardElevation(2 * density);
            card.setUseCompatPadding(true);
            card.setPreventCornerOverlap(true);
            card.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg_glass));

            // Строка с миром
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(View.TEXT_ALIGNMENT_CENTER);
            row.setPadding(
                    (int)(16 * density), (int)(12 * density),
                    (int)(16 * density), (int)(12 * density)
            );

            // Иконка мира (эмодзи/символ)
            TextView iconView = new TextView(getContext());
            iconView.setText("🗺️");
            iconView.setTextSize(22);
            iconView.setPadding(0, 0, (int)(12 * density), 0);
            row.addView(iconView);

            // Название мира
            TextView nameText = new TextView(getContext());
            nameText.setText(world);
            nameText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_primary));
            nameText.setTextSize(16);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);
            nameText.setLayoutParams(new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1
            ));
            row.addView(nameText);

            // Стрелка вправо (индикатор)
            TextView arrowView = new TextView(getContext());
            arrowView.setText("›");
            arrowView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
            arrowView.setTextSize(22);
            row.addView(arrowView);

            card.addView(row);

            // Клик по карточке
            card.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Open world: " + world, Toast.LENGTH_SHORT).show();
            });

            worldsList.addView(card);
        }
    }
}
