package com.bumptech.glide.util.a;

import android.support.annotation.NonNull;

/* compiled from: StateVerifier */
public abstract class g {
    private static final boolean DEBUG = false;

    /* compiled from: StateVerifier */
    private static class a extends g {
        private volatile RuntimeException uu;

        a() {
            super();
        }

        /* access modifiers changed from: package-private */
        @Override // com.bumptech.glide.util.a.g
        public void F(boolean z) {
            if (z) {
                this.uu = new RuntimeException("Released");
            } else {
                this.uu = null;
            }
        }

        @Override // com.bumptech.glide.util.a.g
        public void Pk() {
            if (this.uu != null) {
                throw new IllegalStateException("Already released", this.uu);
            }
        }
    }

    /* compiled from: StateVerifier */
    private static class b extends g {
        private volatile boolean Cm;

        b() {
            super();
        }

        @Override // com.bumptech.glide.util.a.g
        public void F(boolean z) {
            this.Cm = z;
        }

        @Override // com.bumptech.glide.util.a.g
        public void Pk() {
            if (this.Cm) {
                throw new IllegalStateException("Already released");
            }
        }
    }

    private g() {
    }

    @NonNull
    public static g newInstance() {
        return new b();
    }

    /* access modifiers changed from: package-private */
    public abstract void F(boolean z);

    public abstract void Pk();
}
