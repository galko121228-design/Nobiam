package org.nobiam.ui;

import android.content.SharedPreferences;
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
import org.nobiam.utils.ThemeManager;

public class SettingsFragment extends Fragment {

    private LinearLayout themeLight, themeDark, themeSystem;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        prefs = getContext().getSharedPreferences("nobiam_settings", 0);

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

        // Цвета
        setupColorPicker(view);

        // Язык (заглушка)
        setupLanguageSelector(view);

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
            gd.setStroke(2, accentColor);
        }
    }

    private void setupColorPicker(View view) {
        FrameLayout colorBlue = view.findViewById(R.id.color_blue);
        FrameLayout colorPurple = view.findViewById(R.id.color_purple);
        FrameLayout colorGreen = view.findViewById(R.id.color_green);
        FrameLayout colorOrange = view.findViewById(R.id.color_orange);
        FrameLayout colorPink = view.findViewById(R.id.color_pink);
        FrameLayout colorRed = view.findViewById(R.id.color_red);

        int currentColor = AccentColorManager.getAccentColor(requireContext());
        resetColorSelection(view);
        applyColorSelection(view, currentColor);

        View.OnClickListener colorListener = v -> {
            int color = 0xFF00D4FF;
            if (v.getId() == R.id.color_blue) color = 0xFF00D4FF;
            else if (v.getId() == R.id.color_purple) color = 0xFF7C4DFF;
            else if (v.getId() == R.id.color_green) color = 0xFF00E676;
            else if (v.getId() == R.id.color_orange) color = 0xFFFFA726;
            else if (v.getId() == R.id.color_pink) color = 0xFFFF4081;
            else if (v.getId() == R.id.color_red) color = 0xFFEF5350;

            AccentColorManager.setAccentColor(requireContext(), color);
            resetColorSelection(view);
            v.setBackgroundResource(R.drawable.color_circle_active);
            FrameLayout frame = (FrameLayout) v;
            ImageView check = new ImageView(getContext());
            check.setImageResource(R.drawable.ic_check);
            check.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.white));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.CENTER;
            frame.addView(check, params);
            requireActivity().recreate();
        };

        colorBlue.setOnClickListener(colorListener);
        colorPurple.setOnClickListener(colorListener);
        colorGreen.setOnClickListener(colorListener);
        colorOrange.setOnClickListener(colorListener);
        colorPink.setOnClickListener(colorListener);
        colorRed.setOnClickListener(colorListener);
    }

    private void resetColorSelection(View view) {
        FrameLayout[] colors = {
            view.findViewById(R.id.color_blue),
            view.findViewById(R.id.color_purple),
            view.findViewById(R.id.color_green),
            view.findViewById(R.id.color_orange),
            view.findViewById(R.id.color_pink),
            view.findViewById(R.id.color_red)
        };
        for (FrameLayout color : colors) {
            if (color != null) {
                color.setBackgroundResource(R.drawable.color_circle);
                color.removeAllViews();
                View circle = new View(getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(28, 28);
                params.gravity = Gravity.CENTER;
                circle.setLayoutParams(params);
                if (color.getId() == R.id.color_blue) {
                    circle.setBackgroundResource(R.drawable.color_blue);
                } else if (color.getId() == R.id.color_purple) {
                    circle.setBackgroundResource(R.drawable.color_purple);
                } else if (color.getId() == R.id.color_green) {
                    circle.setBackgroundResource(R.drawable.color_green);
                } else if (color.getId() == R.id.color_orange) {
                    circle.setBackgroundResource(R.drawable.color_orange);
                } else if (color.getId() == R.id.color_pink) {
                    circle.setBackgroundResource(R.drawable.color_pink);
                } else if (color.getId() == R.id.color_red) {
                    circle.setBackgroundResource(R.drawable.color_red);
                }
                color.addView(circle);
            }
        }
    }

    private void applyColorSelection(View view, int color) {
        int id = R.id.color_blue;
        if (color == 0xFF7C4DFF) id = R.id.color_purple;
        else if (color == 0xFF00E676) id = R.id.color_green;
        else if (color == 0xFFFFA726) id = R.id.color_orange;
        else if (color == 0xFFFF4081) id = R.id.color_pink;
        else if (color == 0xFFEF5350) id = R.id.color_red;

        FrameLayout target = view.findViewById(id);
        if (target != null) {
            target.setBackgroundResource(R.drawable.color_circle_active);
            ImageView check = new ImageView(getContext());
            check.setImageResource(R.drawable.ic_check);
            check.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.white));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.CENTER;
            target.addView(check, params);
        }
    }

    private void setupLanguageSelector(View view) {
        LinearLayout langSelector = view.findViewById(R.id.language_selector);
        TextView langText = view.findViewById(R.id.language_text);
        if (langSelector != null) {
            langSelector.setOnClickListener(v -> {
                if (langText.getText().equals("Русский")) {
                    langText.setText("English");
                } else {
                    langText.setText("Русский");
                }
            });
        }
    }
}
