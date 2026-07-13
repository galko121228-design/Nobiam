package org.nobiam;

import android.app.Application;

import org.nobiam.utils.ThemeManager;

public class NobiamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Применяем тему при старте приложения
        ThemeManager.applyTheme(this);
    }
}
