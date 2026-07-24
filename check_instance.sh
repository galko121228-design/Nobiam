#!/data/data/com.termux/files/usr/bin/bash

BASE="/storage/emulated/0/Nobiam/instances/default"

echo "🔍 Проверка инстанса..."

du -sh $BASE/game 2>/dev/null
ls $BASE/game | head
