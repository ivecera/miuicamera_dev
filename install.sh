#!/usr/bin/env bash

FILE="MiuiCamera"
SRC="out/system/priv-app/${FILE}/${FILE}.apk"
DEST="/data/adb/modules/miuicamera_a3_pyxis/system/priv-app/MiuiCamera/"

test -f "${SRC}" || exit 1

adb push "${SRC}" "${DEST}"
adb shell pm compile -m speed -f com.android.camera
