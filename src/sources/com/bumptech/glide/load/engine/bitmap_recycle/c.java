package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.l;
import java.util.Queue;

/* compiled from: BaseKeyPool */
abstract class c<T extends l> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> _n = com.bumptech.glide.util.l.createQueue(20);

    c() {
    }

    public void a(T t) {
        if (this._n.size() < 20) {
            this._n.offer(t);
        }
    }

    /* access modifiers changed from: package-private */
    public abstract T create();

    /* access modifiers changed from: package-private */
    public T get() {
        T poll = this._n.poll();
        return poll == null ? create() : poll;
    }
}
