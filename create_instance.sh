#!/data/data/com.termux/files/usr/bin/bash

BASE="/storage/emulated/0/Nobiam/instances/default"

echo "📁 Создание изолированной среды..."

mkdir -p $BASE/game
mkdir -p $BASE/mods
mkdir -p $BASE/config
mkdir -p $BASE/logs

echo "✔ Папки созданы:"
ls $BASE
