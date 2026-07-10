package org.nobiam;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

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
    }

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
