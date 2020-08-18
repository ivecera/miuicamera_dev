package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.i;

/* compiled from: PreFillType */
public final class c {
    @VisibleForTesting
    static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.RGB_565;
    private final Bitmap.Config config;
    private final int height;
    private final int weight;
    private final int width;

    /* compiled from: PreFillType */
    public static class a {
        private Bitmap.Config config;
        private final int height;
        private int weight;
        private final int width;

        public a(int i) {
            this(i, i);
        }

        public a(int i, int i2) {
            this.weight = 1;
            if (i <= 0) {
                throw new IllegalArgumentException("Width must be > 0");
            } else if (i2 > 0) {
                this.width = i;
                this.height = i2;
            } else {
                throw new IllegalArgumentException("Height must be > 0");
            }
        }

        /* access modifiers changed from: package-private */
        public c build() {
            return new c(this.width, this.height, this.config, this.weight);
        }

        /* access modifiers changed from: package-private */
        public Bitmap.Config getConfig() {
            return this.config;
        }

        public a setConfig(@Nullable Bitmap.Config config2) {
            this.config = config2;
            return this;
        }

        public a setWeight(int i) {
            if (i > 0) {
                this.weight = i;
                return this;
            }
            throw new IllegalArgumentException("Weight must be > 0");
        }
    }

    c(int i, int i2, Bitmap.Config config2, int i3) {
        i.b(config2, "Config must not be null");
        this.config = config2;
        this.width = i;
        this.height = i2;
        this.weight = i3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return this.height == cVar.height && this.width == cVar.width && this.weight == cVar.weight && this.config == cVar.config;
    }

    /* access modifiers changed from: package-private */
    public Bitmap.Config getConfig() {
        return this.config;
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public int getWeight() {
        return this.weight;
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (((((this.width * 31) + this.height) * 31) + this.config.hashCode()) * 31) + this.weight;
    }

    public String toString() {
        return "PreFillSize{width=" + this.width + ", height=" + this.height + ", config=" + this.config + ", weight=" + this.weight + '}';
    }
}
