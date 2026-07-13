package org.nobiam.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private static final String PREFS = "nobiam_settings";
    private static final String KEY_LANGUAGE = "language";

    public static void setLanguage(Context context, String langCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANGUAGE, langCode).apply();

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANGUAGE, "ru");
    }

    public static void loadSavedLanguage(Context context) {
        String langCode = getLanguage(context);
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
