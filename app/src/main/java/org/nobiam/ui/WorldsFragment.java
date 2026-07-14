package org.nobiam.ui;

import android.content.res.ColorStateList;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

import java.util.ArrayList;
import java.util.List;

public class WorldsFragment extends Fragment {

    private View view;
    private LinearLayout worldsList;
    private TextView emptyText;
    private final List<String> allWorlds = new ArrayList<>();
    private List<String> filteredWorlds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_worlds, container, false);

        worldsList = view.findViewById(R.id.worlds_list);
        emptyText = view.findViewById(R.id.empty_text);

        filteredWorlds = new ArrayList<>(allWorlds);

        setupBackButton();
        setupSearch();
        setupCreateFlatButton();
        applyAccentColor();
        renderList();

        return view;
    }

    private void setupBackButton() {
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v ->
                    requireActivity().getSupportFragmentManager().popBackStack());
        }
    }

    private void setupSearch() {
        EditText searchInput = view.findViewById(R.id.search_input);
        if (searchInput == null) return;

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
    }

    private void filterWorlds(String query) {
        String q = query.toLowerCase().trim();
        filteredWorlds.clear();

        if (q.isEmpty()) {
            filteredWorlds.addAll(allWorlds);
        } else {
            for (String world : allWorlds) {
                if (world.toLowerCase().contains(q)) {
                    filteredWorlds.add(world);
                }
            }
        }
        renderList();
    }

    private void setupCreateFlatButton() {
        Button btnCreateFlat = view.findViewById(R.id.btn_create_flat);
        if (btnCreateFlat != null) {
            // Применяем акцентный цвет через код
            int accentColor = AccentColorManager.getColor(requireContext());
            btnCreateFlat.setBackgroundTintList(ColorStateList.valueOf(accentColor));

            btnCreateFlat.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Create Flat World — coming soon", Toast.LENGTH_SHORT).show());
        }
    }

    private void renderList() {
        worldsList.removeAllViews();

        if (filteredWorlds.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            return;
        }

        emptyText.setVisibility(View.GONE);
        float density = requireContext().getResources().getDisplayMetrics().density;
        int accentColor = AccentColorManager.getColor(requireContext());

        for (String worldName : filteredWorlds) {
            CardView card = new CardView(requireContext());
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, (int) (6 * density), 0, (int) (6 * density));
            card.setLayoutParams(cardParams);
            card.setRadius(14 * density);
            card.setCardElevation(0);
            card.setUseCompatPadding(true);
            card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg_glass));
            card.setContentPadding(
                    (int) (16 * density), (int) (12 * density),
                    (int) (16 * density), (int) (12 * density)
            );

            TextView nameText = new TextView(requireContext());
            nameText.setText(worldName);
            nameText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary));
            nameText.setTextSize(16);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);

            card.addView(nameText);

            card.setOnClickListener(v ->
                    Toast.makeText(getContext(), worldName, Toast.LENGTH_SHORT).show());

            worldsList.addView(card);
        }
    }

    private void applyAccentColor() {
        int accentColor = AccentColorManager.getColor(requireContext());

        TextView title = view.findViewById(R.id.title_worlds);
        if (title != null) title.setTextColor(accentColor);
    }

    @Override
    public void onResume() {
        super.onResume();
        applyAccentColor();
    }
}
