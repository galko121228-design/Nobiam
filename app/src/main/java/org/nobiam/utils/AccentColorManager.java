package org.nobiam.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccentColorManager {
    private static final String PREFS = "nobiam_settings";
    private static final String KEY_ACCENT_COLOR = "accent_color";
    private static final int DEFAULT_COLOR = 0xFF00D4FF;

    // 50 цветов
    public static final int[] ACCENT_COLORS = {
        0xFF00D4FF, 0xFF29B6F6, 0xFF4FC3F7, 0xFF0D47A1, 0xFF1976D2,
        0xFF1565C0, 0xFF006064, 0xFF00BCD4, 0xFF00E676, 0xFF8BC34A,
        0xFFAFB42B, 0xFF1B5E20, 0xFFFFD54F, 0xFFFFC107, 0xFFFFA000,
        0xFFFFA726, 0xFFFF5722, 0xFFEF5350, 0xFF880E4F, 0xFFFF4081,
        0xFFF48FB1, 0xFF7C4DFF, 0xFFB39DDB, 0xFF4A148C, 0xFFCE93D8,
        0xFFE1BEE7, 0xFF4A148C, 0xFFCE93D8, 0xFFE1BEE7, 0xFF3F51B5,
        0xFF1A237E, 0xFF01579B, 0xFF26C6DA, 0xFF00838F, 0xFF2E7D32,
        0xFFC0CA33, 0xFFA5D6A7, 0xFF004D40, 0xFFFFA000, 0xFFF9A825,
        0xFFE65100, 0xFFD50000, 0xFFB71C1C, 0xFFC2185B, 0xFFF50057,
        0xFFF8BBD0, 0xFF78909C, 0xFFB0BEC5, 0xFF455A64, 0xFF263238
    };

    public static int getAccentColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_ACCENT_COLOR, DEFAULT_COLOR);
    }

    public static void setAccentColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_ACCENT_COLOR, color).apply();
    }

    public static int getColorIndex(int color) {
        for (int i = 0; i < ACCENT_COLORS.length; i++) {
            if (ACCENT_COLORS[i] == color) return i;
        }
        return 0;
    }
}
