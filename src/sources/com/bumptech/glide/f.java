package com.bumptech.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import com.bumptech.glide.request.target.n;
import com.bumptech.glide.util.l;
import java.util.List;
import java.util.Queue;

/* compiled from: ListPreloader */
public class f<T> implements AbsListView.OnScrollListener {
    private final int pj;
    private final d qj;
    private final a<T> rj;
    private final b<T> sj;
    private int tj;
    private int uj;
    private int vj = -1;
    private int wj;
    private boolean xj = true;
    private final m za;

    /* compiled from: ListPreloader */
    public interface a<U> {
        @Nullable
        j<?> f(@NonNull U u);

        @NonNull
        List<U> g(int i);
    }

    /* compiled from: ListPreloader */
    public interface b<T> {
        @Nullable
        int[] a(@NonNull T t, int i, int i2);
    }

    /* compiled from: ListPreloader */
    private static final class c extends com.bumptech.glide.request.target.b<Object> {
        int Gt;
        int Ht;

        c() {
        }

        @Override // com.bumptech.glide.request.target.o
        public void a(@NonNull n nVar) {
        }

        @Override // com.bumptech.glide.request.target.o
        public void a(@NonNull Object obj, @Nullable com.bumptech.glide.request.a.f<? super Object> fVar) {
        }

        @Override // com.bumptech.glide.request.target.o
        public void b(@NonNull n nVar) {
            nVar.a(this.Ht, this.Gt);
        }
    }

    /* compiled from: ListPreloader */
    private static final class d {
        private final Queue<c> queue;

        d(int i) {
            this.queue = l.createQueue(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.queue.offer(new c());
            }
        }

        public c f(int i, int i2) {
            c poll = this.queue.poll();
            this.queue.offer(poll);
            poll.Ht = i;
            poll.Gt = i2;
            return poll;
        }
    }

    public f(@NonNull m mVar, @NonNull a<T> aVar, @NonNull b<T> bVar, int i) {
        this.za = mVar;
        this.rj = aVar;
        this.sj = bVar;
        this.pj = i;
        this.qj = new d(i + 1);
    }

    private void a(int i, boolean z) {
        if (this.xj != z) {
            this.xj = z;
            cancelAll();
        }
        i(i, (z ? this.pj : -this.pj) + i);
    }

    private void a(List<T> list, int i, boolean z) {
        int size = list.size();
        if (z) {
            for (int i2 = 0; i2 < size; i2++) {
                c(list.get(i2), i, i2);
            }
            return;
        }
        for (int i3 = size - 1; i3 >= 0; i3--) {
            c(list.get(i3), i, i3);
        }
    }

    private void c(@Nullable T t, int i, int i2) {
        int[] a2;
        j<?> f2;
        if (t != null && (a2 = this.sj.a(t, i, i2)) != null && (f2 = this.rj.f(t)) != null) {
            f2.c(this.qj.f(a2[0], a2[1]));
        }
    }

    private void cancelAll() {
        for (int i = 0; i < this.pj; i++) {
            this.za.d(this.qj.f(0, 0));
        }
    }

    private void i(int i, int i2) {
        int i3;
        int i4;
        if (i < i2) {
            i3 = Math.max(this.tj, i);
            i4 = i2;
        } else {
            i4 = Math.min(this.uj, i);
            i3 = i2;
        }
        int min = Math.min(this.wj, i4);
        int min2 = Math.min(this.wj, Math.max(0, i3));
        if (i < i2) {
            for (int i5 = min2; i5 < min; i5++) {
                a(this.rj.g(i5), i5, true);
            }
        } else {
            for (int i6 = min - 1; i6 >= min2; i6--) {
                a(this.rj.g(i6), i6, false);
            }
        }
        this.uj = min2;
        this.tj = min;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        this.wj = i3;
        int i4 = this.vj;
        if (i > i4) {
            a(i2 + i, true);
        } else if (i < i4) {
            a(i, false);
        }
        this.vj = i;
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
    }
}
