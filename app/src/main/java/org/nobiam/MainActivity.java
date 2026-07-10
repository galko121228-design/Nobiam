package org.nobiam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button navHome, navSettings, navAbout;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
getWindow().setDecorFitsSystemWindows(false);
android.view.WindowInsetsController controller = getWindow().getInsetsController();
if (controller != null) {
    controller.hide(android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars());
    controller.setSystemBarsBehavior(
        android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    );
}
        setContentView(R.layout.activity_main);
getWindow().getDecorView().setSystemUiVisibility(
    android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY 
    | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN 
    | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 
    | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 
    | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 
    | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
);

        navHome = findViewById(R.id.navHome);
        navSettings = findViewById(R.id.navSettings);
        navAbout = findViewById(R.id.navAbout);
        container = findViewById(R.id.container);

        loadFragment(new HomeFragment());
        setActive(navHome);

        navHome.setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            setActive(navHome);
        });

        navSettings.setOnClickListener(v -> {
            loadFragment(new SettingsFragment());
            setActive(navSettings);
        });

        navAbout.setOnClickListener(v -> {
            loadFragment(new AboutFragment());
            setActive(navAbout);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private void setActive(Button active) {
        navHome.setTextColor(getColor(R.color.inactive));
        navSettings.setTextColor(getColor(R.color.inactive));
        navAbout.setTextColor(getColor(R.color.inactive));
        active.setTextColor(getColor(R.color.active));
    }
}
