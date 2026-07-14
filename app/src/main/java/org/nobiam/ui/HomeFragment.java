package org.nobiam.ui;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

public class HomeFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        applyAccentColor();
        setupListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyAccentColor();
    }

    private void applyAccentColor() {
        if (view == null) return;
        int accentColor = AccentColorManager.getColor(requireContext());

        TextView minecraftTitle = view.findViewById(R.id.minecraft_title_text);
        if (minecraftTitle != null) {
            minecraftTitle.setTextColor(accentColor);
        }

        Button launchButton = view.findViewById(R.id.launch_button);
        if (launchButton != null) {
            launchButton.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        }

        TextView modsTitle = view.findViewById(R.id.mods_title);
        if (modsTitle != null) {
            modsTitle.setTextColor(accentColor);
            tintDrawableStart(modsTitle, accentColor);
        }

        Button manageMods = view.findViewById(R.id.manage_mods_button);
        if (manageMods != null) {
            manageMods.setTextColor(accentColor);
            GradientDrawable gd = (GradientDrawable) manageMods.getBackground();
            if (gd != null) gd.setStroke(1, accentColor);
        }

        TextView contentTitle = view.findViewById(R.id.content_title);
        if (contentTitle != null) {
            contentTitle.setTextColor(accentColor);
            tintDrawableStart(contentTitle, accentColor);
        }

        TextView viewAll = view.findViewById(R.id.content_view_all);
        if (viewAll != null) {
            viewAll.setTextColor(accentColor);
        }

        TextView miscTitle = view.findViewById(R.id.misc_title);
        if (miscTitle != null) {
            miscTitle.setTextColor(accentColor);
            tintDrawableStart(miscTitle, accentColor);
        }
    }

    private void tintDrawableStart(TextView textView, int color) {
        Drawable[] drawables = textView.getCompoundDrawablesRelative();
        if (drawables[0] != null) {
            Drawable wrapped = DrawableCompat.wrap(drawables[0].mutate());
            DrawableCompat.setTint(wrapped, color);
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    wrapped, drawables[1], drawables[2], drawables[3]
            );
        }
    }

    private void setupListeners() {
        // Current Instance
        LinearLayout selectVersion = view.findViewById(R.id.select_version_button);
        if (selectVersion != null) {
            selectVersion.setOnClickListener(v -> {
                TextView versionText = view.findViewById(R.id.text_minecraft_version);
                InstanceSelectorDialog.show(requireContext(), v, versionText);
            });
        }

        // Launch
        Button launchButton = view.findViewById(R.id.launch_button);
        if (launchButton != null) {
            launchButton.setOnClickListener(v ->
                Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show()
            );
        }

        // Manage Mods
        Button manageMods = view.findViewById(R.id.manage_mods_button);
        if (manageMods != null) {
            manageMods.setOnClickListener(v ->
                Toast.makeText(getContext(), "Manage Mods (coming soon)", Toast.LENGTH_SHORT).show()
            );
        }

        // Worlds
        LinearLayout worldsRow = view.findViewById(R.id.content_worlds_row);
        if (worldsRow != null) {
            worldsRow.setOnClickListener(v -> {
                WorldsFragment fragment = new WorldsFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        // Resource Packs
        LinearLayout resourcePacksRow = view.findViewById(R.id.content_resource_packs_row);
        if (resourcePacksRow != null) {
            resourcePacksRow.setOnClickListener(v -> {
                ResourcePacksFragment fragment = new ResourcePacksFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
