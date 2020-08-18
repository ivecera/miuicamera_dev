package com.bumptech.glide.load.engine.a;

import com.bumptech.glide.util.i;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: DiskCacheWriteLocker */
final class c {
    private final Map<String, a> Bo = new HashMap();
    private final b Co = new b();

    /* compiled from: DiskCacheWriteLocker */
    private static class a {
        final Lock lock = new ReentrantLock();
        int zo;

        a() {
        }
    }

    /* compiled from: DiskCacheWriteLocker */
    private static class b {
        private static final int Ao = 10;
        private final Queue<a> pool = new ArrayDeque();

        b() {
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar) {
            synchronized (this.pool) {
                if (this.pool.size() < 10) {
                    this.pool.offer(aVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public a obtain() {
            a poll;
            synchronized (this.pool) {
                poll = this.pool.poll();
            }
            return poll == null ? new a() : poll;
        }
    }

    c() {
    }

    /* access modifiers changed from: package-private */
    public void B(String str) {
        a aVar;
        synchronized (this) {
            aVar = this.Bo.get(str);
            if (aVar == null) {
                aVar = this.Co.obtain();
                this.Bo.put(str, aVar);
            }
            aVar.zo++;
        }
        aVar.lock.lock();
    }

    /* access modifiers changed from: package-private */
    public void C(String str) {
        a aVar;
        synchronized (this) {
            a aVar2 = this.Bo.get(str);
            i.checkNotNull(aVar2);
            aVar = aVar2;
            if (aVar.zo >= 1) {
                aVar.zo--;
                if (aVar.zo == 0) {
                    a remove = this.Bo.remove(str);
                    if (remove.equals(aVar)) {
                        this.Co.a(remove);
                    } else {
                        throw new IllegalStateException("Removed the wrong lock, expected to remove: " + aVar + ", but actually removed: " + remove + ", safeKey: " + str);
                    }
                }
            } else {
                throw new IllegalStateException("Cannot release a lock that is not held, safeKey: " + str + ", interestedThreads: " + aVar.zo);
            }
        }
        aVar.lock.unlock();
    }
}
