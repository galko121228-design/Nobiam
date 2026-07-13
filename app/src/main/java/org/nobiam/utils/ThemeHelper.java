package org.nobiam.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {
    public static void applyTheme(Activity activity, String theme) {
        FeatureSettings settings = FeatureSettings.getInstance();
        settings.setTheme(theme);

        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    public static void loadSavedTheme(Activity activity) {
        FeatureSettings settings = FeatureSettings.getInstance();
        String theme = settings.getTheme();

        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    public static String getSavedTheme(Activity activity) {
        FeatureSettings settings = FeatureSettings.getInstance();
        return settings.getTheme();
    }
}
