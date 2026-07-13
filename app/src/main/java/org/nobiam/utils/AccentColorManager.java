package org.nobiam.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccentColorManager {
    private static final String PREFS = "nobiam_settings";
    private static final String KEY_ACCENT_COLOR = "accent_color";
    private static final int DEFAULT_COLOR = 0xFF00D4FF;

    // 10 популярных цветов
    public static final int[] ACCENT_COLORS = {
        0xFF00D4FF, // Nobiam Blue
        0xFF7C4DFF, // Violet
        0xFF00E676, // Mint
        0xFFFFA726, // Orange
        0xFFFF4081, // Pink
        0xFFEF5350, // Red
        0xFFFFD54F, // Gold
        0xFFCE93D8, // Lavender
        0xFF26C6DA, // Cyan
        0xFF78909C  // Slate
    };

    public static int getAccentColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_ACCENT_COLOR, DEFAULT_COLOR);
    }

    public static void setAccentColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_ACCENT_COLOR, color).apply();
    }
}
