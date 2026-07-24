package org.nobiam.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MinecraftLauncher {

    private static final String PACKAGE_NAME = "com.mojang.minecraftpe";

    public static boolean isMinecraftInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getMinecraftVersionLabel(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(PACKAGE_NAME, 0).versionName;
        } catch (Exception e) {
            return "unknown";
        }
    }

    public static void launchMinecraft(Context context) {
        if (!isMinecraftInstalled(context)) {
            Toast.makeText(context, "Minecraft не установлен", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔥 подготовка инстанса
        InstanceManager.prepareInstance(context);

        // если есть ClientManager — раскомментируй
        // ClientManager.init(context);

        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // context.startActivity(intent);
        }
    }
}
