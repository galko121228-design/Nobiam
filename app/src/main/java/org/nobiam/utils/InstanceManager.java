package org.nobiam.utils;

import android.content.Context;
import android.util.Log;
import java.io.File;

public class InstanceManager {

    public static File getInstanceDir(Context context) {
        File dir = new File(context.getFilesDir(), "instance1");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static void prepareInstance(Context context) {
        File instance = getInstanceDir(context);

        File mcDir = new File(instance, "games/com.mojang");

        if (!mcDir.exists()) {
            mcDir.mkdirs();
        }

        Log.d("Nobiam", "Instance prepared: " + mcDir.getAbsolutePath());
    }
}
