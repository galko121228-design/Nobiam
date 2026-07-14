package org.nobiam.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccentColorManager {
    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_ACCENT_COLOR = "accent_color";

    // 10 цветов, ровно по твоей палитре
    public static final int[] ACCENT_COLORS = {
        0xFF2196F3, // Blue
        0xFFF44336, // Red
        0xFF4CAF50, // Green
        0xFFFF9800, // Orange
        0xFF9C27B0, // Purple
        0xFF00BCD4, // Cyan
        0xFFFFEB3B, // Yellow
        0xFFE91E63, // Pink
        0xFF607D8B, // Blue Grey
        0xFF795548  // Brown
    };

    public static void saveColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_ACCENT_COLOR, color).apply();
    }

    public static int getColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_ACCENT_COLOR, ACCENT_COLORS[0]); // default = синий
    }
}
