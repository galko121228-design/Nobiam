package org.nobiam;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // УБИРАЕМ ВСЕ системные отступы
        View decorView = getWindow().getDecorView();

        WindowInsetsControllerCompat controller =
                ViewCompat.getWindowInsetsController(decorView);

        if (controller != null) {
            controller.hide(WindowInsetsCompat.Type.systemBars());
            controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
        }

        // УБИРАЕМ inset padding вообще
        ViewCompat.setOnApplyWindowInsetsListener(decorView, (v, insets) -> {
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
