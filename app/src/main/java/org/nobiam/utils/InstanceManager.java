package org.nobiam.utils;

import android.content.Context;
import java.io.File;

public class InstanceManager {

    private static final String BASE_PATH = "/storage/emulated/0/Nobiam/instances/";

    public static File getInstanceDir(String name) {
        File dir = new File(BASE_PATH + name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getMinecraftDir() {
        return new File("/storage/emulated/0/games/com.mojang/");
    }
}
