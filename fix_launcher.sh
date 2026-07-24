#!/data/data/com.termux/files/usr/bin/bash

echo "[1] Удаляем старый запуск из MinecraftLauncher..."

FILE="app/src/main/java/org/nobiam/utils/MinecraftLauncher.java"

if [ -f "$FILE" ]; then
    sed -i 's/context.startActivity(intent);//g' "$FILE"
    sed -i 's/launchMinecraft(Context context)/launchMinecraft(Context context) {\n        android.util.Log.d("Nobiam", "Blocked old launcher");\n        return;\n    }\n\n    public static void old_launchMinecraft_disabled(Context context)/' "$FILE"
    echo "✔ Старый запуск отключён"
else
    echo "❌ Файл не найден: $FILE"
fi

echo "[2] Готово"
