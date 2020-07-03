#!/usr/bin/env bash

PATH=/opt/android-sdk-update-manager/build-tools/29.0.3:$PATH

PKG="MiuiCamera"
UNSIGNED="out/${PKG}-Unsigned.apk"
UNALIGNED="out/${PKG}-Unaligned.apk"
FINAL="${PKG}.apk"
DEST="out/system/priv-app/${PKG}"

test "$1" = "-f" && {
	rm -f src/build/apk/*.dex
}

apktool build --frame-path frameworks -o "${UNALIGNED}" src || {
	echo "Build failure..."
	exit 1
}

zipalign -f 4 "${UNALIGNED}" "${UNSIGNED}" || {
	echo "Failed to zipalign APK..."
	rm -f "${UNALIGNED}"
	exit 1
}

rm -f "${UNALIGNED}"

apksigner sign --key testkey.pk8 --cert testkey.x509.pem "${UNSIGNED}" || {
	echo "Failed to sign APK..."
	rm -f "${UNSIGNED}"
	exit 1
}

test -d "$DEST" || {
	mkdir -p "$DEST"
}

mv -f "${UNSIGNED}" "${DEST}/${FINAL}"
