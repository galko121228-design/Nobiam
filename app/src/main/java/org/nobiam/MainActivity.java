package org.nobiam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.nobiam.ui.HomeFragment;
import org.nobiam.ui.SettingsFragment;
import org.nobiam.ui.AboutFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment;
    private Fragment settingsFragment;
    private Fragment aboutFragment;
    private ImageButton navHome, navSettings, navAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Применяем сохранённую тему
        SharedPreferences prefs = getSharedPreferences("nobiam_settings", 0);
        String theme = prefs.getString("theme", "dark");
        // TODO: применить тему (Light/Dark/System) через DayNight или пересоздание Activity

        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 28) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = 
                android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        hideSystemBars();

        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        aboutFragment = new AboutFragment();

        navHome = findViewById(R.id.navHome);
        navSettings = findViewById(R.id.navSettings);
        navAbout = findViewById(R.id.navAbout);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, homeFragment)
            .commit();
        updateNavState(navHome);

        navHome.setOnClickListener(v -> {
            switchFragment(homeFragment);
            updateNavState(navHome);
        });

        navSettings.setOnClickListener(v -> {
            switchFragment(settingsFragment);
            updateNavState(navSettings);
        });

        navAbout.setOnClickListener(v -> {
            switchFragment(aboutFragment);
            updateNavState(navAbout);
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
            R.anim.fade_in,  // enter
            R.anim.fade_out, // exit
            R.anim.fade_in,  // popEnter
            R.anim.fade_out  // popExit
        );
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void updateNavState(ImageButton activeButton) {
        navHome.setBackgroundResource(R.drawable.nav_inactive);
        navHome.setColorFilter(getColor(R.color.text_inactive));
        navSettings.setBackgroundResource(R.drawable.nav_inactive);
        navSettings.setColorFilter(getColor(R.color.text_inactive));
        navAbout.setBackgroundResource(R.drawable.nav_inactive);
        navAbout.setColorFilter(getColor(R.color.text_inactive));

        activeButton.setBackgroundResource(R.drawable.nav_active);
        activeButton.setColorFilter(getColor(R.color.text_primary));
    }

    private void hideSystemBars() {
        Window window = getWindow();
        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(window, window.getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemBars();
    }
}
