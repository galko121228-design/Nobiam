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

public class ResourcePacksFragment extends Fragment {

    private View view;
    private LinearLayout packsList;
    private TextView emptyText;
    private final List<String> allPacks = new ArrayList<>();
    private List<String> filteredPacks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_resource_packs, container, false);

        packsList = view.findViewById(R.id.packs_list);
        emptyText = view.findViewById(R.id.empty_text);

        filteredPacks = new ArrayList<>(allPacks);

        setupBackButton();
        setupSearch();
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
                filterPacks(s.toString());
            }
        });
    }

    private void filterPacks(String query) {
        String q = query.toLowerCase().trim();
        filteredPacks.clear();

        if (q.isEmpty()) {
            filteredPacks.addAll(allPacks);
        } else {
            for (String pack : allPacks) {
                if (pack.toLowerCase().contains(q)) {
                    filteredPacks.add(pack);
                }
            }
        }
        renderList();
    }

    private void renderList() {
        packsList.removeAllViews();

        if (filteredPacks.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            return;
        }

        emptyText.setVisibility(View.GONE);
        float density = requireContext().getResources().getDisplayMetrics().density;

        for (String packName : filteredPacks) {
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
            nameText.setText(packName);
            nameText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary));
            nameText.setTextSize(16);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);

            card.addView(nameText);

            card.setOnClickListener(v ->
                    Toast.makeText(getContext(), packName, Toast.LENGTH_SHORT).show());

            packsList.addView(card);
        }
    }

    private void applyAccentColor() {
        int accentColor = AccentColorManager.getColor(requireContext());

        TextView title = view.findViewById(R.id.title_packs);
        if (title != null) title.setTextColor(accentColor);
    }

    @Override
    public void onResume() {
        super.onResume();
        applyAccentColor();
    }
}
