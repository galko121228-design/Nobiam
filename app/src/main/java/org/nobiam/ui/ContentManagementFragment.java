package org.nobiam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

public class ContentManagementFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_management, container, false);

        int accentColor = AccentColorManager.getColor(requireContext());

        // Заголовок
        TextView title = view.findViewById(R.id.title);
        if (title != null) title.setTextColor(accentColor);

        // Красим все иконки в акцентный цвет
        int[] iconIds = {
            R.id.content_worlds_row,
            R.id.content_skin_packs_row,
            R.id.content_screenshots_row,
            R.id.content_resource_packs_row,
            R.id.content_behavior_packs_row
        };

        for (int id : iconIds) {
            LinearLayout row = view.findViewById(id);
            if (row != null) {
                ImageView icon = (ImageView) row.getChildAt(0);
                if (icon != null) {
                    icon.setColorFilter(accentColor);
                }
            }
        }

        // Назад
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Worlds
        LinearLayout worldsRow = view.findViewById(R.id.content_worlds_row);
        worldsRow.setOnClickListener(v -> {
            WorldsFragment fragment = new WorldsFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Resource Packs
        LinearLayout resourcePacksRow = view.findViewById(R.id.content_resource_packs_row);
        resourcePacksRow.setOnClickListener(v -> {
            ResourcePacksFragment fragment = new ResourcePacksFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Skin Packs (тост)
        LinearLayout skinPacksRow = view.findViewById(R.id.content_skin_packs_row);
        skinPacksRow.setOnClickListener(v ->
                Toast.makeText(getContext(), "Skin Packs (coming soon)", Toast.LENGTH_SHORT).show()
        );

        // Screenshots (тост)
        LinearLayout screenshotsRow = view.findViewById(R.id.content_screenshots_row);
        screenshotsRow.setOnClickListener(v ->
                Toast.makeText(getContext(), "Screenshots (coming soon)", Toast.LENGTH_SHORT).show()
        );

        // Behavior Packs (тост)
        LinearLayout behaviorPacksRow = view.findViewById(R.id.content_behavior_packs_row);
        behaviorPacksRow.setOnClickListener(v ->
                Toast.makeText(getContext(), "Behavior Packs (coming soon)", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}
