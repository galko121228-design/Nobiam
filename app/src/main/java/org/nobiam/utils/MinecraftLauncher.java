public class MinecraftLauncher {

    private static final String PACKAGE_NAME = "com.mojang.minecraftpe";

    public static boolean isInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getVersion(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(PACKAGE_NAME, 0).versionName;
        } catch (Exception e) {
            return "unknown";
        }
    }

    public static void launch(Context context) {
        if (!isInstalled(context)) {
            Toast.makeText(context, "Minecraft не установлен", Toast.LENGTH_SHORT).show();
            return;
        }

        // 👉 перед запуском инициализируем клиент
        ClientManager.init(context);

        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
