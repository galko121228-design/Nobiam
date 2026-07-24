#!/data/data/com.termux/files/usr/bin/bash

SRC="/storage/emulated/0/games/com.mojang"
DST="/storage/emulated/0/Nobiam/instances/default/game"

echo "📦 Импорт Minecraft..."

if [ -d "$SRC" ]; then
    cp -r $SRC/* $DST/
    echo "✔ Данные скопированы"
else
    echo "❌ Minecraft папка не найдена"
fi
