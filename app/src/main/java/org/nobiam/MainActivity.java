package org.nobiam;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.nobiam.ui.HomeFragment;
import org.nobiam.ui.SettingsFragment;
import org.nobiam.ui.AboutFragment;
import org.nobiam.utils.AccentColorManager;
import org.nobiam.utils.ThemeManager;

public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment;
    private Fragment settingsFragment;
    private Fragment aboutFragment;
    private ImageButton navHome, navSettings, navAbout;
    private Fragment currentFragment;
    private TextView headerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);

        super.onCreate(savedInstanceState);

        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 28) {
            getWindow().getAttributes().layoutInDisplayCutoutMode =
                    android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        hideSystemBars();

        int accentColor = AccentColorManager.getColor(this);

        headerTitle = findViewById(R.id.header_title);
        if (headerTitle != null) {
            headerTitle.setTextColor(accentColor);
        }

        homeFragment = new HomeFragment();
        settingsFragment = new SettingsFragment();
        aboutFragment = new AboutFragment();

        navHome = findViewById(R.id.navHome);
        navSettings = findViewById(R.id.navSettings);
        navAbout = findViewById(R.id.navAbout);

        if (savedInstanceState == null) {
            currentFragment = homeFragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment)
                    .commit();
            updateNavState(navHome);
        } else {
            currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof HomeFragment) {
                updateNavState(navHome);
            } else if (currentFragment instanceof SettingsFragment) {
                updateNavState(navSettings);
            } else if (currentFragment instanceof AboutFragment) {
                updateNavState(navAbout);
            }
        }

        if (currentFragment == null) {
            currentFragment = homeFragment;
            updateNavState(navHome);
        }

        navHome.setOnClickListener(v -> {
            currentFragment = homeFragment;
            switchFragment(homeFragment);
            updateNavState(navHome);
        });

        navSettings.setOnClickListener(v -> {
            currentFragment = settingsFragment;
            switchFragment(settingsFragment);
            updateNavState(navSettings);
        });

        navAbout.setOnClickListener(v -> {
            currentFragment = aboutFragment;
            switchFragment(aboutFragment);
            updateNavState(navAbout);
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void updateNavState(ImageButton activeButton) {
        int accentColor = AccentColorManager.getColor(this);
        int inactiveColor = getColor(R.color.text_inactive);

        navHome.setBackgroundResource(R.drawable.nav_inactive);
        navHome.setImageTintList(android.content.res.ColorStateList.valueOf(inactiveColor));
        navSettings.setBackgroundResource(R.drawable.nav_inactive);
        navSettings.setImageTintList(android.content.res.ColorStateList.valueOf(inactiveColor));
        navAbout.setBackgroundResource(R.drawable.nav_inactive);
        navAbout.setImageTintList(android.content.res.ColorStateList.valueOf(inactiveColor));

        activeButton.setBackgroundResource(R.drawable.nav_active);
        activeButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(accentColor));
        activeButton.setImageTintList(android.content.res.ColorStateList.valueOf(getColor(R.color.text_primary)));
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
        if (headerTitle != null) {
            headerTitle.setTextColor(AccentColorManager.getColor(this));
        }
    }
}
