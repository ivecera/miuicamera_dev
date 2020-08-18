package com.bumptech.glide.load.engine.b;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/* compiled from: RuntimeCompat */
class f implements FilenameFilter {
    final /* synthetic */ Pattern rp;

    f(Pattern pattern) {
        this.rp = pattern;
    }

    public boolean accept(File file, String str) {
        return this.rp.matcher(str).matches();
    }
}
