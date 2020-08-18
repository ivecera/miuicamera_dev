package com.bumptech.glide.request.target;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.l;

/* compiled from: SimpleTarget */
public abstract class m<Z> extends b<Z> {
    private final int height;
    private final int width;

    public m() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public m(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    @Override // com.bumptech.glide.request.target.o
    public void a(@NonNull n nVar) {
    }

    @Override // com.bumptech.glide.request.target.o
    public final void b(@NonNull n nVar) {
        if (l.o(this.width, this.height)) {
            nVar.a(this.width, this.height);
            return;
        }
        throw new IllegalArgumentException("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: " + this.width + " and height: " + this.height + ", either provide dimensions in the constructor or call override()");
    }
}
