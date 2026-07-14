package org.nobiam.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class MinecraftLauncher {

    private static final String[] MINECRAFT_PACKAGES = {
        "com.mojang.minecraftpe",
        "com.mojang.minecraftpe.demo"
    };

    public static boolean isMinecraftInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        for (String pkg : MINECRAFT_PACKAGES) {
            try {
                pm.getPackageInfo(pkg, 0);
                return true;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return false;
    }

    public static void launchMinecraft(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = null;

        for (String pkg : MINECRAFT_PACKAGES) {
            try {
                intent = pm.getLaunchIntentForPackage(pkg);
                if (intent != null) break;
            } catch (Exception ignored) {
            }
        }

        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Minecraft not found", Toast.LENGTH_SHORT).show();
        }
    }
}
