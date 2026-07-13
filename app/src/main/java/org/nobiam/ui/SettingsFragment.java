package org.nobiam.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import org.nobiam.R;

public class SettingsFragment extends Fragment {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        prefs = getContext().getSharedPreferences("nobiam_settings", 0);
        editor = prefs.edit();

        // Темы
        LinearLayout themeLight = view.findViewById(R.id.theme_light);
        LinearLayout themeDark = view.findViewById(R.id.theme_dark);
        LinearLayout themeSystem = view.findViewById(R.id.theme_system);

        // Загружаем сохранённую тему
        String currentTheme = prefs.getString("theme", "dark");
        applyThemeSelection(themeLight, themeDark, themeSystem, currentTheme);

        View.OnClickListener themeListener = v -> {
            String theme = "dark";
            if (v.getId() == R.id.theme_light) {
                theme = "light";
            } else if (v.getId() == R.id.theme_dark) {
                theme = "dark";
            } else if (v.getId() == R.id.theme_system) {
                theme = "system";
            }
            editor.putString("theme", theme);
            editor.apply();
            applyThemeSelection(themeLight, themeDark, themeSystem, theme);
            Toast.makeText(getContext(), "Theme: " + theme, Toast.LENGTH_SHORT).show();
        };

        themeLight.setOnClickListener(themeListener);
        themeDark.setOnClickListener(themeListener);
        themeSystem.setOnClickListener(themeListener);

        // Цвета
        FrameLayout colorBlue = view.findViewById(R.id.color_blue);
        FrameLayout colorPurple = view.findViewById(R.id.color_purple);
        FrameLayout colorGreen = view.findViewById(R.id.color_green);
        FrameLayout colorOrange = view.findViewById(R.id.color_orange);
        FrameLayout colorPink = view.findViewById(R.id.color_pink);
        FrameLayout colorRed = view.findViewById(R.id.color_red);

        // Загружаем сохранённый цвет
        String currentColor = prefs.getString("accent_color", "#00D4FF");
        applyColorSelection(view, currentColor);

        View.OnClickListener colorListener = v -> {
            String color = "#00D4FF";
            if (v.getId() == R.id.color_blue) color = "#00D4FF";
            else if (v.getId() == R.id.color_purple) color = "#7C4DFF";
            else if (v.getId() == R.id.color_green) color = "#00E676";
            else if (v.getId() == R.id.color_orange) color = "#FFA726";
            else if (v.getId() == R.id.color_pink) color = "#FF4081";
            else if (v.getId() == R.id.color_red) color = "#EF5350";

            editor.putString("accent_color", color);
            editor.apply();
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
            Toast.makeText(getContext(), "Color changed", Toast.LENGTH_SHORT).show();
        };

        colorBlue.setOnClickListener(colorListener);
        colorPurple.setOnClickListener(colorListener);
        colorGreen.setOnClickListener(colorListener);
        colorOrange.setOnClickListener(colorListener);
        colorPink.setOnClickListener(colorListener);
        colorRed.setOnClickListener(colorListener);

        // Язык
        LinearLayout langSelector = view.findViewById(R.id.language_selector);
        TextView langText = view.findViewById(R.id.language_text);

        // Загружаем сохранённый язык
        String currentLang = prefs.getString("language", "Русский");
        langText.setText(currentLang);

        if (langSelector != null) {
            langSelector.setOnClickListener(v -> {
                String newLang;
                if (langText.getText().equals("Русский")) {
                    newLang = "English";
                } else {
                    newLang = "Русский";
                }
                langText.setText(newLang);
                editor.putString("language", newLang);
                editor.apply();
                Toast.makeText(getContext(), "Language: " + newLang, Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }

    private void applyThemeSelection(LinearLayout light, LinearLayout dark, LinearLayout system, String theme) {
        light.setBackgroundResource(R.drawable.theme_selector);
        dark.setBackgroundResource(R.drawable.theme_selector);
        system.setBackgroundResource(R.drawable.theme_selector);

        switch (theme) {
            case "light":
                light.setBackgroundResource(R.drawable.theme_selector_active);
                break;
            case "dark":
                dark.setBackgroundResource(R.drawable.theme_selector_active);
                break;
            case "system":
                system.setBackgroundResource(R.drawable.theme_selector_active);
                break;
        }
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

    private void applyColorSelection(View view, String color) {
        int colorId = R.id.color_blue;
        switch (color) {
            case "#7C4DFF": colorId = R.id.color_purple; break;
            case "#00E676": colorId = R.id.color_green; break;
            case "#FFA726": colorId = R.id.color_orange; break;
            case "#FF4081": colorId = R.id.color_pink; break;
            case "#EF5350": colorId = R.id.color_red; break;
            default: colorId = R.id.color_blue; break;
        }
        // Активируем нужный цвет через симуляцию клика
        FrameLayout target = view.findViewById(colorId);
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
}
