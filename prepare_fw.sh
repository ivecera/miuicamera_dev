#!/usr/bin/env bash

DIR="frameworks"
FILES="framework-res miui framework-ext-res miuisystem"

test -d "$DIR" && { rm -rf "$DIR" || {
	echo "Failed to remove '$DIR' subdir"
	exit 1
	}
}

mkdir "$DIR"

for f in ${FILES}; do
	full="orig/${f}.apk"
	test -f "$full" || {
		echo "File '${f}.apk' is missing in orig/ sub-directory"
		exit 1
	}

	echo "Processing '${f}.apk'"
	apktool install-framework --frame-path "$DIR" "$full"
done

echo "Done ;-)"

