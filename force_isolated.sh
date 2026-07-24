#!/data/data/com.termux/files/usr/bin/bash

echo "Перекидываем запуск на IsolatedLauncher..."

TARGET="app/src/main/java/org/nobiam/ui/HomeFragment.java"

if [ -f "$TARGET" ]; then
    sed -i 's/MinecraftLauncher.launchMinecraft/IsolatedLauncher.launch/g' "$TARGET"
    echo "✔ Теперь используется IsolatedLauncher"
else
    echo "❌ HomeFragment не найден"
fi
