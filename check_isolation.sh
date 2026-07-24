#!/data/data/com.termux/files/usr/bin/bash

echo "Проверка старых запусков..."

grep -r "launchMinecraft" app/src || echo "✔ launchMinecraft не используется"

echo "------"

grep -r "MinecraftLauncher" app/src

echo "------"

grep -r "startActivity(intent)" app/src
