package org.nobiam;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import org.nobiam.ui.HomeFragment;
import org.nobiam.ui.SettingsFragment;
import org.nobiam.ui.AboutFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment;
    private Fragment settingsFragment;
    private Fragment aboutFragment;
    private Button navHome, navSettings, navAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        // ❗ Отключаем safe area полностью
        WindowCompat.setDecorFitsSystemWindows(window, false);

        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 28) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = 
                android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        hideSystemBars();

        // ===== ИНИЦИАЛИЗАЦИЯ ФРАГМЕНТОВ =====
        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        aboutFragment = new AboutFragment();

        navHome = findViewById(R.id.navHome);
        navSettings = findViewById(R.id.navSettings);
        navAbout = findViewById(R.id.navAbout);

        // Стартовый фрагмент — Home
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, homeFragment)
            .commit();
        updateNavState(navHome);

        // ===== НАВИГАЦИЯ =====
        navHome.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment)
                .commit();
            updateNavState(navHome);
        });

        navSettings.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, settingsFragment)
                .commit();
            updateNavState(navSettings);
        });

        navAbout.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, aboutFragment)
                .commit();
            updateNavState(navAbout);
        });
    }

    // ===== ОБНОВЛЕНИЕ СОСТОЯНИЯ КНОПОК =====
    private void updateNavState(Button activeButton) {
        // Сброс всех кнопок в неактивное состояние
        navHome.setBackgroundResource(R.drawable.nav_inactive);
        navHome.setTextColor(getColor(R.color.text_inactive));
        navSettings.setBackgroundResource(R.drawable.nav_inactive);
        navSettings.setTextColor(getColor(R.color.text_inactive));
        navAbout.setBackgroundResource(R.drawable.nav_inactive);
        navAbout.setTextColor(getColor(R.color.text_inactive));

        // Активация выбранной кнопки
        activeButton.setBackgroundResource(R.drawable.nav_active);
        activeButton.setTextColor(getColor(R.color.text_primary));
    }

    // ===== СКРЫТИЕ СИСТЕМНЫХ ПАНЕЛЕЙ =====
    private void hideSystemBars() {
        Window window = getWindow();

        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(window, window.getDecorView());

        // ❗ СКРЫВАЕМ ВСЁ
        controller.hide(WindowInsetsCompat.Type.systemBars());

        // ❗ ВАЖНО: чтобы не возвращались
        controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ❗ Android любит возвращать бары → прячем снова
        hideSystemBars();
    }
}
