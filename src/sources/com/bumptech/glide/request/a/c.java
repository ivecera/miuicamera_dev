package com.bumptech.glide.request.a;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;

/* compiled from: DrawableCrossFadeFactory */
public class c implements g<Drawable> {
    private final boolean Yt;
    private d _t;
    private final int duration;

    /* compiled from: DrawableCrossFadeFactory */
    public static class a {
        private static final int Zt = 300;
        private boolean Yt;
        private final int durationMillis;

        public a() {
            this(300);
        }

        public a(int i) {
            this.durationMillis = i;
        }

        public c build() {
            return new c(this.durationMillis, this.Yt);
        }

        public a setCrossFadeEnabled(boolean z) {
            this.Yt = z;
            return this;
        }
    }

    protected c(int i, boolean z) {
        this.duration = i;
        this.Yt = z;
    }

    private f<Drawable> ho() {
        if (this._t == null) {
            this._t = new d(this.duration, this.Yt);
        }
        return this._t;
    }

    @Override // com.bumptech.glide.request.a.g
    public f<Drawable> a(DataSource dataSource, boolean z) {
        return dataSource == DataSource.MEMORY_CACHE ? e.get() : ho();
    }
}
