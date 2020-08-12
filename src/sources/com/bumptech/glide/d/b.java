package com.bumptech.glide.d;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.ImageHeaderParser;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ImageHeaderParserRegistry */
public final class b {
    private final List<ImageHeaderParser> cm = new ArrayList();

    @NonNull
    public synchronized List<ImageHeaderParser> Qj() {
        return this.cm;
    }

    public synchronized void b(@NonNull ImageHeaderParser imageHeaderParser) {
        this.cm.add(imageHeaderParser);
    }
}
