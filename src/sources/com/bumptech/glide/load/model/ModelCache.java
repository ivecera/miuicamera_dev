package com.bumptech.glide.load.model;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.f;
import com.bumptech.glide.util.l;
import java.util.Queue;

public class ModelCache<A, B> {
    private static final int DEFAULT_SIZE = 250;
    private final f<ModelKey<A>, B> cache;

    @VisibleForTesting
    static final class ModelKey<A> {
        private static final Queue<ModelKey<?>> cq = l.createQueue(0);
        private int height;
        private A model;
        private int width;

        private ModelKey() {
        }

        static <A> ModelKey<A> b(A a2, int i, int i2) {
            ModelKey<A> poll;
            synchronized (cq) {
                poll = cq.poll();
            }
            if (poll == null) {
                poll = new ModelKey<>();
            }
            poll.d(a2, i, i2);
            return poll;
        }

        private void d(A a2, int i, int i2) {
            this.model = a2;
            this.width = i;
            this.height = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ModelKey)) {
                return false;
            }
            ModelKey modelKey = (ModelKey) obj;
            return this.width == modelKey.width && this.height == modelKey.height && this.model.equals(modelKey.model);
        }

        public int hashCode() {
            return (((this.height * 31) + this.width) * 31) + this.model.hashCode();
        }

        public void release() {
            synchronized (cq) {
                cq.offer(this);
            }
        }
    }

    public ModelCache() {
        this(250);
    }

    public ModelCache(long j) {
        this.cache = new s(this, j);
    }

    public void a(A a2, int i, int i2, B b2) {
        this.cache.put(ModelKey.b(a2, i, i2), b2);
    }

    @Nullable
    public B b(A a2, int i, int i2) {
        ModelKey<A> b2 = ModelKey.b(a2, i, i2);
        B b3 = this.cache.get(b2);
        b2.release();
        return b3;
    }

    public void clear() {
        this.cache.aa();
    }
}
