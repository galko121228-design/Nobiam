package org.nobiam.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import org.nobiam.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Current Instance selector
        LinearLayout selectVersion = view.findViewById(R.id.select_version_button);
        if (selectVersion != null) {
            selectVersion.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Instance selector (coming soon)", Toast.LENGTH_SHORT).show();
            });
        }

        // Launch button
        Button launchButton = view.findViewById(R.id.launch_button);
        if (launchButton != null) {
            launchButton.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show();
            });
        }

        // Тестовая кнопка для смены темы
        Button testThemeButton = view.findViewById(R.id.test_theme_button);
        if (testThemeButton != null) {
            testThemeButton.setOnClickListener(v -> {
                SharedPreferences prefs = getContext().getSharedPreferences("nobiam_settings", 0);
                String current = prefs.getString("pref_theme", "dark");
                String newTheme = current.equals("dark") ? "light" : "dark";
                prefs.edit().putString("pref_theme", newTheme).apply();

                if (newTheme.equals("dark")) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                requireActivity().recreate();
            });
        }

        return view;
    }
}
