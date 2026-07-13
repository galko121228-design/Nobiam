package org.nobiam.ui;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;
import org.nobiam.utils.LanguageManager;
import org.nobiam.utils.ThemeManager;

public class SettingsFragment extends Fragment {

    private LinearLayout themeLight, themeDark, themeSystem;
    private LinearLayout colorFullContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView settingsTitle = view.findViewById(R.id.settings_title);
        if (settingsTitle != null) {
            settingsTitle.setTextColor(AccentColorManager.getAccentColor(requireContext()));
        }

        // Язык
        TextView langText = view.findViewById(R.id.language_text);
        if (langText != null) {
            String currentLang = LanguageManager.getLanguage(requireContext());
            langText.setText(currentLang.equals("ru") ? "Русский" : "English");
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

        setupLanguageSelector(view);
        setupColorPicker(view);

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

    private void setupLanguageSelector(View view) {
        LinearLayout langSelector = view.findViewById(R.id.language_selector);
        TextView langText = view.findViewById(R.id.language_text);
        if (langSelector != null) {
            langSelector.setOnClickListener(v -> {
                String currentLang = LanguageManager.getLanguage(requireContext());
                String newLang = currentLang.equals("ru") ? "en" : "ru";
                LanguageManager.setLanguage(requireContext(), newLang);
                langText.setText(newLang.equals("ru") ? "Русский" : "English");
                requireActivity().recreate();
            });
        }
    }

    private void setupColorPicker(View view) {
        LinearLayout presetContainer = view.findViewById(R.id.color_preset_container);
        colorFullContainer = view.findViewById(R.id.color_full_container);
        TextView moreToggle = view.findViewById(R.id.more_colors_toggle);

        if (presetContainer == null) return;

        // Основные цвета (первые 8)
        int[] allColors = AccentColorManager.ACCENT_COLORS;
        int[] presetColors = new int[8];
        System.arraycopy(allColors, 0, presetColors, 0, 8);
        int currentColor = AccentColorManager.getAccentColor(requireContext());

        // Заполняем основные цвета
        presetContainer.removeAllViews();
        for (int color : presetColors) {
            FrameLayout circle = createColorCircle(color, color == currentColor, false);
            presetContainer.addView(circle);
        }

        // Заполняем все цвета (скрыто)
        colorFullContainer.removeAllViews();
        int cols = 10;
        for (int i = 0; i < allColors.length; i += cols) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            row.setGravity(Gravity.CENTER);

            for (int j = 0; j < cols && (i + j) < allColors.length; j++) {
                int color = allColors[i + j];
                FrameLayout circle = createColorCircle(color, color == currentColor, true);
                row.addView(circle);
            }
            colorFullContainer.addView(row);
        }

        // Toggle More Colors
        moreToggle.setOnClickListener(v -> {
            if (colorFullContainer.getVisibility() == View.GONE) {
                colorFullContainer.setVisibility(View.VISIBLE);
                moreToggle.setText("More colors ▲");
            } else {
                colorFullContainer.setVisibility(View.GONE);
                moreToggle.setText("More colors ▼");
            }
        });
    }

    private FrameLayout createColorCircle(int color, boolean isSelected, boolean showCheck) {
        float density = getResources().getDisplayMetrics().density;
        int size = (int) (28 * density);

        FrameLayout wrapper = new FrameLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins((int)(4 * density), (int)(4 * density), (int)(4 * density), (int)(4 * density));
        wrapper.setLayoutParams(params);
        wrapper.setClickable(true);
        wrapper.setFocusable(true);
        wrapper.setForeground(getResources().getDrawable(android.R.attr.selectableItemBackground, null));

        View circle = new View(getContext());
        FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(size, size);
        circle.setLayoutParams(circleParams);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(color);
        if (isSelected) {
            gd.setStroke((int)(2 * density), getResources().getColor(android.R.color.white));
        }
        circle.setBackground(gd);
        wrapper.addView(circle);

        if (isSelected && showCheck) {
            ImageView check = new ImageView(getContext());
            FrameLayout.LayoutParams checkParams = new FrameLayout.LayoutParams(
                    (int)(12 * density), (int)(12 * density)
            );
            checkParams.gravity = Gravity.CENTER;
            check.setLayoutParams(checkParams);
            check.setImageResource(R.drawable.ic_check);
            check.setColorFilter(getResources().getColor(android.R.color.white));
            wrapper.addView(check);
        }

        wrapper.setOnClickListener(v -> {
            AccentColorManager.setAccentColor(getContext(), color);
            requireActivity().recreate();
        });

        return wrapper;
    }
}
