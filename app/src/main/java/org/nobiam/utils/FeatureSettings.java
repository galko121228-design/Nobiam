package org.nobiam.utils;

import android.content.Context;

public class FeatureSettings {
    private static volatile FeatureSettings INSTANCE;
    private static Context appContext;

    private String theme = "dark";
    private String accentColor = "#00D4FF";
    private String language = "Русский";

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static FeatureSettings getInstance() {
        if (INSTANCE == null) {
            synchronized (FeatureSettings.class) {
                if (INSTANCE == null) {
                    INSTANCE = SettingsStorage.load(appContext);
                    if (INSTANCE == null) {
                        INSTANCE = new FeatureSettings();
                    }
                }
            }
        }
        return INSTANCE;
    }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; autoSave(); }

    public String getAccentColor() { return accentColor; }
    public void setAccentColor(String accentColor) { this.accentColor = accentColor; autoSave(); }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; autoSave(); }

    private void autoSave() {
        if (appContext != null) {
            SettingsStorage.save(appContext, this);
        }
    }
}
