package org.nobiam;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ВКЛЮЧАЕМ нормальный edge-to-edge (как в документации)
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        View root = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // ❗ КЛЮЧ: НЕ ТРОГАЕМ ЛЕВО/ПРАВО
            v.setPadding(
                0,
                bars.top,
                0,
                bars.bottom
            );

            return insets;
        });
    }
}
