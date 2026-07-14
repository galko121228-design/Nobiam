package org.nobiam.ui;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;
import org.nobiam.utils.LanguageManager;
import org.nobiam.utils.ThemeManager;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private LinearLayout themeLight, themeDark, themeSystem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Заголовок
        TextView settingsTitle = view.findViewById(R.id.settings_title);
        if (settingsTitle != null) {
            settingsTitle.setTextColor(AccentColorManager.getColor(requireContext()));
        }

        // Язык
        TextView langText = view.findViewById(R.id.language_text);
        if (langText != null) {
            String currentLang = LanguageManager.getLanguage(requireContext());
            langText.setText(currentLang.equals("ru") ? "Русский" : "English");
        }

        // Темы
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

        setupLanguageSelector(view);
        setupAccentColorPicker(view);

        return view;
    }

    private void applyThemeSelection(int mode) {
        int accentColor = AccentColorManager.getColor(requireContext());

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

    private void setupLanguageSelector(View view) {
        LinearLayout langSelector = view.findViewById(R.id.language_selector);
        TextView langText = view.findViewById(R.id.language_text);
        if (langSelector != null) {
            langSelector.setOnClickListener(v -> {
                String currentLang = LanguageManager.getLanguage(requireContext());
                String newLang = currentLang.equals("ru") ? "en" : "ru";
                LanguageManager.setLanguage(requireContext(), newLang);
                requireActivity().recreate();
            });
        }
    }

    private void setupAccentColorPicker(View rootView) {
        LinearLayout colorContainer = rootView.findViewById(R.id.color_preset_container);
        if (colorContainer == null) return;

        colorContainer.removeAllViews();

        int currentColor = AccentColorManager.getColor(requireContext());

        for (int color : AccentColorManager.ACCENT_COLORS) {
            View circle = createColorCircle(color, color == currentColor);
            circle.setOnClickListener(v -> {
                AccentColorManager.saveColor(requireContext(), color);
                requireActivity().recreate();
            });
            colorContainer.addView(circle);
        }
    }

    private View createColorCircle(int color, boolean isSelected) {
        float density = getResources().getDisplayMetrics().density;
        int sizeDp = (int) (36 * density);

        ImageView circle = new ImageView(requireContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeDp, sizeDp);
        params.setMargins((int)(6 * density), 0, (int)(6 * density), 0);
        circle.setLayoutParams(params);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        if (isSelected) {
            drawable.setStroke((int)(3 * density), 0xFFFFFFFF);
        }
        circle.setImageDrawable(drawable);
        circle.setClickable(true);
        circle.setFocusable(true);

        return circle;
    }
}
