package org.nobiam.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

public class MinecraftLauncher {

    private static final String MINECRAFT_PACKAGE = "com.mojang.minecraftpe";

    public static boolean isMinecraftInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getPackageInfo(MINECRAFT_PACKAGE, PackageManager.PackageInfoFlags.of(0));
            } else {
                pm.getPackageInfo(MINECRAFT_PACKAGE, 0);
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void launchMinecraft(Context context) {
        PackageManager pm = context.getPackageManager();

        if (!isMinecraftInstalled(context)) {
            Toast.makeText(context, "Minecraft not installed", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent launchIntent = pm.getLaunchIntentForPackage(MINECRAFT_PACKAGE);
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        } else {
            Toast.makeText(context, "Cannot launch Minecraft", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getMinecraftVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return pm.getPackageInfo(MINECRAFT_PACKAGE, PackageManager.PackageInfoFlags.of(0)).versionName;
            } else {
                return pm.getPackageInfo(MINECRAFT_PACKAGE, 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static String getMinecraftVersionLabel(Context context) {
        String version = getMinecraftVersion(context);
        if (version != null) {
            return "Minecraft " + version;
        }
        return "Minecraft not found";
    }
}
