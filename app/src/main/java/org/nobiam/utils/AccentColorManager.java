package org.nobiam.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccentColorManager {
    private static final String PREFS = "nobiam_settings";
    private static final String KEY_ACCENT_COLOR = "accent_color";
    private static final int DEFAULT_COLOR = 0xFF00D4FF; // ✅ С альфа-каналом

    public static int getAccentColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_ACCENT_COLOR, DEFAULT_COLOR);
    }

    public static void setAccentColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_ACCENT_COLOR, color).apply();
    }
}
