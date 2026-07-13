package org.nobiam.ui;

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
import org.nobiam.R;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Темы
        LinearLayout themeLight = view.findViewById(R.id.theme_light);
        LinearLayout themeDark = view.findViewById(R.id.theme_dark);
        LinearLayout themeSystem = view.findViewById(R.id.theme_system);

        View.OnClickListener themeListener = v -> {
            themeLight.setBackgroundResource(R.drawable.theme_selector);
            themeDark.setBackgroundResource(R.drawable.theme_selector);
            themeSystem.setBackgroundResource(R.drawable.theme_selector);
            v.setBackgroundResource(R.drawable.theme_selector_active);
            Toast.makeText(getContext(), "Theme changed", Toast.LENGTH_SHORT).show();
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

        View.OnClickListener colorListener = v -> {
            resetColorSelection(view);
            v.setBackgroundResource(R.drawable.color_circle_active);
            FrameLayout frame = (FrameLayout) v;
            ImageView check = new ImageView(getContext());
            check.setImageResource(R.drawable.ic_check);
            check.setColorFilter(getResources().getColor(android.R.color.white));
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

        // Language
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

        return view;
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
}
