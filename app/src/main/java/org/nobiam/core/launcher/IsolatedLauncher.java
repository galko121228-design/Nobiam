package org.nobiam.core.launcher;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

public class IsolatedLauncher {

    private static final String TAG = "IsolatedLauncher";

    public static void launch(Context context) {

        // 📁 создаём изолированную папку
        File gameDir = new File(context.getFilesDir(), "isolated_mc");

        if (!gameDir.exists()) {
            boolean created = gameDir.mkdirs();
            Log.d(TAG, "Game dir created: " + created);
        }

        Log.d(TAG, "Game dir: " + gameDir.getAbsolutePath());

        // 🚀 запускаем activity (пока пустую)
        try {
            Intent intent = new Intent();
            intent.setClassName(
                    context,
                    "org.nobiam.game.MinecraftActivity"
            );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Failed to start activity", e);
        }
    }
}
