package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.load.j;
import com.bumptech.glide.m;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

class GifFrameLoader {
    private j<Bitmap> Sn;
    private final com.bumptech.glide.b.a Sr;
    private boolean Tr;
    private boolean Ur;
    private com.bumptech.glide.j<Bitmap> Vr;
    private boolean Wr;
    private final d Xi;
    private DelayTarget Xr;
    @Nullable
    private OnEveryFrameListener Yr;
    private final List<a> callbacks;
    private DelayTarget current;
    private Bitmap firstFrame;
    private final Handler handler;
    private boolean isRunning;
    private DelayTarget next;
    final m za;

    @VisibleForTesting
    static class DelayTarget extends com.bumptech.glide.request.target.m<Bitmap> {
        private final long It;
        private final Handler handler;
        final int index;
        private Bitmap resource;

        DelayTarget(Handler handler2, int i, long j) {
            this.handler = handler2;
            this.index = i;
            this.It = j;
        }

        public void a(@NonNull Bitmap bitmap, @Nullable f<? super Bitmap> fVar) {
            this.resource = bitmap;
            this.handler.sendMessageAtTime(this.handler.obtainMessage(1, this), this.It);
        }

        @Override // com.bumptech.glide.request.target.o
        public /* bridge */ /* synthetic */ void a(@NonNull Object obj, @Nullable f fVar) {
            a((Bitmap) obj, (f<? super Bitmap>) fVar);
        }

        /* access modifiers changed from: package-private */
        public Bitmap wj() {
            return this.resource;
        }
    }

    @VisibleForTesting
    interface OnEveryFrameListener {
        void G();
    }

    public interface a {
        void G();
    }

    private class b implements Handler.Callback {
        static final int Qr = 1;
        static final int Rr = 2;

        b() {
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                GifFrameLoader.this.onFrameReady((DelayTarget) message.obj);
                return true;
            } else if (i != 2) {
                return false;
            } else {
                GifFrameLoader.this.za.d((DelayTarget) message.obj);
                return false;
            }
        }
    }

    GifFrameLoader(c cVar, com.bumptech.glide.b.a aVar, int i, int i2, j<Bitmap> jVar, Bitmap bitmap) {
        this(cVar.Fi(), c.H(cVar.getContext()), aVar, null, b(c.H(cVar.getContext()), i, i2), jVar, bitmap);
    }

    GifFrameLoader(d dVar, m mVar, com.bumptech.glide.b.a aVar, Handler handler2, com.bumptech.glide.j<Bitmap> jVar, j<Bitmap> jVar2, Bitmap bitmap) {
        this.callbacks = new ArrayList();
        this.za = mVar;
        handler2 = handler2 == null ? new Handler(Looper.getMainLooper(), new b()) : handler2;
        this.Xi = dVar;
        this.handler = handler2;
        this.Vr = jVar;
        this.Sr = aVar;
        a(jVar2, bitmap);
    }

    private static com.bumptech.glide.load.c Ln() {
        return new com.bumptech.glide.e.d(Double.valueOf(Math.random()));
    }

    private void Mn() {
        if (this.isRunning && !this.Tr) {
            if (this.Ur) {
                i.b(this.Xr == null, "Pending target must be null when starting from the first frame");
                this.Sr.M();
                this.Ur = false;
            }
            DelayTarget delayTarget = this.Xr;
            if (delayTarget != null) {
                this.Xr = null;
                onFrameReady(delayTarget);
                return;
            }
            this.Tr = true;
            long uptimeMillis = SystemClock.uptimeMillis() + ((long) this.Sr.ca());
            this.Sr.advance();
            this.next = new DelayTarget(this.handler, this.Sr.P(), uptimeMillis);
            this.Vr.b(com.bumptech.glide.request.f.h(Ln())).load((Object) this.Sr).c(this.next);
        }
    }

    private void Nn() {
        Bitmap bitmap = this.firstFrame;
        if (bitmap != null) {
            this.Xi.a(bitmap);
            this.firstFrame = null;
        }
    }

    private static com.bumptech.glide.j<Bitmap> b(m mVar, int i, int i2) {
        return mVar.Ni().b(com.bumptech.glide.request.f.b(o.NONE).D(true).B(true).l(i, i2));
    }

    private int getFrameSize() {
        return l.g(Jj().getWidth(), Jj().getHeight(), Jj().getConfig());
    }

    private void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.Wr = false;
            Mn();
        }
    }

    private void stop() {
        this.isRunning = false;
    }

    /* access modifiers changed from: package-private */
    public j<Bitmap> Aa() {
        return this.Sn;
    }

    /* access modifiers changed from: package-private */
    public Bitmap Jj() {
        DelayTarget delayTarget = this.current;
        return delayTarget != null ? delayTarget.wj() : this.firstFrame;
    }

    /* access modifiers changed from: package-private */
    public void Kj() {
        i.b(!this.isRunning, "Can't restart a running animation");
        this.Ur = true;
        DelayTarget delayTarget = this.Xr;
        if (delayTarget != null) {
            this.za.d(delayTarget);
            this.Xr = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(j<Bitmap> jVar, Bitmap bitmap) {
        i.checkNotNull(jVar);
        this.Sn = jVar;
        i.checkNotNull(bitmap);
        this.firstFrame = bitmap;
        this.Vr = this.Vr.b(new com.bumptech.glide.request.f().c(jVar));
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        if (this.Wr) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        } else if (!this.callbacks.contains(aVar)) {
            boolean isEmpty = this.callbacks.isEmpty();
            this.callbacks.add(aVar);
            if (isEmpty) {
                start();
            }
        } else {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
    }

    /* access modifiers changed from: package-private */
    public void b(a aVar) {
        this.callbacks.remove(aVar);
        if (this.callbacks.isEmpty()) {
            stop();
        }
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.callbacks.clear();
        Nn();
        stop();
        DelayTarget delayTarget = this.current;
        if (delayTarget != null) {
            this.za.d(delayTarget);
            this.current = null;
        }
        DelayTarget delayTarget2 = this.next;
        if (delayTarget2 != null) {
            this.za.d(delayTarget2);
            this.next = null;
        }
        DelayTarget delayTarget3 = this.Xr;
        if (delayTarget3 != null) {
            this.za.d(delayTarget3);
            this.Xr = null;
        }
        this.Sr.clear();
        this.Wr = true;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer getBuffer() {
        return this.Sr.getData().asReadOnlyBuffer();
    }

    /* access modifiers changed from: package-private */
    public int getCurrentIndex() {
        DelayTarget delayTarget = this.current;
        if (delayTarget != null) {
            return delayTarget.index;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int getFrameCount() {
        return this.Sr.getFrameCount();
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return Jj().getHeight();
    }

    /* access modifiers changed from: package-private */
    public int getLoopCount() {
        return this.Sr.Z();
    }

    /* access modifiers changed from: package-private */
    public int getSize() {
        return this.Sr.R() + getFrameSize();
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return Jj().getWidth();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void onFrameReady(DelayTarget delayTarget) {
        OnEveryFrameListener onEveryFrameListener = this.Yr;
        if (onEveryFrameListener != null) {
            onEveryFrameListener.G();
        }
        this.Tr = false;
        if (this.Wr) {
            this.handler.obtainMessage(2, delayTarget).sendToTarget();
        } else if (!this.isRunning) {
            this.Xr = delayTarget;
        } else {
            if (delayTarget.wj() != null) {
                Nn();
                DelayTarget delayTarget2 = this.current;
                this.current = delayTarget;
                for (int size = this.callbacks.size() - 1; size >= 0; size--) {
                    this.callbacks.get(size).G();
                }
                if (delayTarget2 != null) {
                    this.handler.obtainMessage(2, delayTarget2).sendToTarget();
                }
            }
            Mn();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setOnEveryFrameReadyListener(@Nullable OnEveryFrameListener onEveryFrameListener) {
        this.Yr = onEveryFrameListener;
    }

    /* access modifiers changed from: package-private */
    public Bitmap ya() {
        return this.firstFrame;
    }
}
