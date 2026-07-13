package org.nobiam.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {
    private static final String PREFS_NAME = "nobiam_settings";
    private static final String PREF_THEME = "pref_theme";

    public static void applyTheme(Activity activity, String theme) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, 0);
        prefs.edit().putString(PREF_THEME, theme).apply();

        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        activity.recreate();
    }

    public static void loadSavedTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, 0);
        String theme = prefs.getString(PREF_THEME, "dark");

        switch (theme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    public static String getSavedTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_THEME, "dark");
    }
}
