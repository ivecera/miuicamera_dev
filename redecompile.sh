#!/usr/bin/env bash

PKG="MiuiCamera"
FILE="${PKG}.apk"
DEST="out/system/priv-app/${PKG}"

test -f "${DEST}/${FILE}" || exit 1

../jadx/bin/jadx -d src -r "${DEST}/${FILE}" --show-bad-code --fs-case-sensitive -j 8
