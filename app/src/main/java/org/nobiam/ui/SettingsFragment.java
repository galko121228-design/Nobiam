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

    private void setupColorPicker(View view) {
        LinearLayout colorContainer = view.findViewById(R.id.color_preset_container);
        if (colorContainer == null) return;

        colorContainer.removeAllViews();
        colorContainer.setOrientation(LinearLayout.HORIZONTAL);
        colorContainer.setGravity(Gravity.CENTER);

        int[] colors = AccentColorManager.ACCENT_COLORS;
        int currentColor = AccentColorManager.getAccentColor(requireContext());

        for (int color : colors) {
            FrameLayout circle = createColorCircle(color, color == currentColor);
            colorContainer.addView(circle);
        }
    }

    private FrameLayout createColorCircle(int color, boolean isSelected) {
        float density = getResources().getDisplayMetrics().density;
        int size = (int) (32 * density);

        FrameLayout wrapper = new FrameLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins((int)(6 * density), 0, (int)(6 * density), 0);
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
            ImageView check = new ImageView(getContext());
            FrameLayout.LayoutParams checkParams = new FrameLayout.LayoutParams(
                    (int)(14 * density), (int)(14 * density)
            );
            checkParams.gravity = Gravity.CENTER;
            check.setLayoutParams(checkParams);
            check.setImageResource(R.drawable.ic_check);
            check.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.white));
            wrapper.addView(check);
        }
        circle.setBackground(gd);
        wrapper.addView(circle);

        wrapper.setOnClickListener(v -> {
            AccentColorManager.setAccentColor(getContext(), color);
            requireActivity().recreate();
        });

        return wrapper;
    }
}
