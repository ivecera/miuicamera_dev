#!/usr/bin/env bash

DIR="src"
FILE="MiuiCamera.apk"

test -d "$DIR" || {
	mkdir "$DIR" || {
		echo "Failed to create '$DIR'"
		exit 1
	}
}

test -f "orig/$FILE" || {
	echo "File '$FILE' is missing on orig/ sub-directory"
	exit 1
}

apktool decode --frame-path frameworks --force --no-debug-info	\
	--output "$DIR" "orig/$FILE"

