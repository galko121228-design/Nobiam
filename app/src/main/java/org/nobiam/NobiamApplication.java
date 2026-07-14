package org.nobiam;

import android.app.Application;

import org.nobiam.utils.LanguageManager;
import org.nobiam.utils.ThemeManager;

public class NobiamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ThemeManager.applyTheme(this);
        LanguageManager.loadSavedLanguage(this);
        // Принудительно проверяем язык
        String lang = LanguageManager.getLanguage(this);
        android.util.Log.d("Nobiam", "Current language: " + lang);
    }
}
