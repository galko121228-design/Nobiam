package org.nobiam.ui;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;
import org.nobiam.utils.ThemeManager;

public class SettingsFragment extends Fragment {

    private LinearLayout themeLight, themeDark, themeSystem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView settingsTitle = view.findViewById(R.id.settings_title);
        if (settingsTitle != null) {
            settingsTitle.setTextColor(AccentColorManager.getAccentColor(requireContext()));
        }

        themeLight = view.findViewById(R.id.theme_light);
        themeDark = view.findViewById(R.id.theme_dark);
        themeSystem = view.findViewById(R.id.theme_system);

        int currentMode = ThemeManager.getSavedTheme(requireContext());
        applyThemeSelection(currentMode);

        themeLight.setOnClickListener(v -> {
            ThemeManager.setTheme(requireContext(), AppCompatDelegate.MODE_NIGHT_NO);
            applyThemeSelection(AppCompatDelegate.MODE_NIGHT_NO);
            requireActivity().recreate();
        });

        themeDark.setOnClickListener(v -> {
            ThemeManager.setTheme(requireContext(), AppCompatDelegate.MODE_NIGHT_YES);
            applyThemeSelection(AppCompatDelegate.MODE_NIGHT_YES);
            requireActivity().recreate();
        });

        themeSystem.setOnClickListener(v -> {
            ThemeManager.setTheme(requireContext(), AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            applyThemeSelection(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            requireActivity().recreate();
        });

        // Цвета временно отключены
        // setupColorPicker(view);

        return view;
    }

    private void applyThemeSelection(int mode) {
        int accentColor = AccentColorManager.getAccentColor(requireContext());

        themeLight.setBackgroundResource(R.drawable.theme_selector);
        themeDark.setBackgroundResource(R.drawable.theme_selector);
        themeSystem.setBackgroundResource(R.drawable.theme_selector);

        LinearLayout activeTile = null;
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                activeTile = themeLight;
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                activeTile = themeDark;
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                activeTile = themeSystem;
                break;
        }

        if (activeTile != null) {
            activeTile.setBackgroundResource(R.drawable.theme_selector_active);
            GradientDrawable gd = (GradientDrawable) activeTile.getBackground();
            if (gd != null) gd.setStroke(2, accentColor);
        }
    }
}
