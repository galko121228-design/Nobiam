#!/data/data/com.termux/files/usr/bin/bash

FILE="app/src/main/java/org/nobiam/utils/MinecraftLauncher.java"

echo "💣 Полное отключение launchMinecraft..."

sed -i '/launchMinecraft(Context context)/,/}/c\
    public static void launchMinecraft(Context context) {\
        android.util.Log.d("Nobiam", "Old launcher blocked");\
    }' "$FILE"

echo "✔ Готово"
