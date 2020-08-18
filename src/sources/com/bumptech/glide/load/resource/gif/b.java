package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.view.Gravity;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.j;
import com.bumptech.glide.load.resource.gif.GifFrameLoader;
import com.bumptech.glide.util.i;
import java.nio.ByteBuffer;

/* compiled from: GifDrawable */
public class b extends Drawable implements GifFrameLoader.a, Animatable {
    private static final int GRAVITY = 119;
    public static final int Yf = -1;
    public static final int Zf = 0;
    private boolean Rf;
    private boolean Sf;
    private boolean Tf;
    private int Uf;
    private int Vf;
    private boolean Wf;
    private Rect Xf;
    private boolean isRunning;
    private Paint paint;
    private final a state;

    /* compiled from: GifDrawable */
    static final class a extends Drawable.ConstantState {
        @VisibleForTesting
        final GifFrameLoader frameLoader;

        a(GifFrameLoader gifFrameLoader) {
            this.frameLoader = gifFrameLoader;
        }

        public int getChangingConfigurations() {
            return 0;
        }

        @NonNull
        public Drawable newDrawable() {
            return new b(this);
        }

        @NonNull
        public Drawable newDrawable(Resources resources) {
            return newDrawable();
        }
    }

    @Deprecated
    public b(Context context, com.bumptech.glide.b.a aVar, d dVar, j<Bitmap> jVar, int i, int i2, Bitmap bitmap) {
        this(context, aVar, jVar, i, i2, bitmap);
    }

    public b(Context context, com.bumptech.glide.b.a aVar, j<Bitmap> jVar, int i, int i2, Bitmap bitmap) {
        this(new a(new GifFrameLoader(c.get(context), aVar, i, i2, jVar, bitmap)));
    }

    @VisibleForTesting
    b(GifFrameLoader gifFrameLoader, Paint paint2) {
        this(new a(gifFrameLoader));
        this.paint = paint2;
    }

    b(a aVar) {
        this.Tf = true;
        this.Vf = -1;
        i.checkNotNull(aVar);
        this.state = aVar;
    }

    private Drawable.Callback Hm() {
        Drawable.Callback callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        return callback;
    }

    private Rect Im() {
        if (this.Xf == null) {
            this.Xf = new Rect();
        }
        return this.Xf;
    }

    private void Jm() {
        this.Uf = 0;
    }

    private void Km() {
        i.b(!this.Sf, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.state.frameLoader.getFrameCount() == 1) {
            invalidateSelf();
        } else if (!this.isRunning) {
            this.isRunning = true;
            this.state.frameLoader.a(this);
            invalidateSelf();
        }
    }

    private void Lm() {
        this.isRunning = false;
        this.state.frameLoader.b(this);
    }

    private Paint getPaint() {
        if (this.paint == null) {
            this.paint = new Paint(2);
        }
        return this.paint;
    }

    public j<Bitmap> Aa() {
        return this.state.frameLoader.Aa();
    }

    public void Ba() {
        i.b(!this.isRunning, "You cannot restart a currently running animation.");
        this.state.frameLoader.Kj();
        start();
    }

    @Override // com.bumptech.glide.load.resource.gif.GifFrameLoader.a
    public void G() {
        if (Hm() == null) {
            stop();
            invalidateSelf();
            return;
        }
        invalidateSelf();
        if (za() == getFrameCount() - 1) {
            this.Uf++;
        }
        int i = this.Vf;
        if (i != -1 && this.Uf >= i) {
            stop();
        }
    }

    public void a(j<Bitmap> jVar, Bitmap bitmap) {
        this.state.frameLoader.a(jVar, bitmap);
    }

    public void draw(@NonNull Canvas canvas) {
        if (!this.Sf) {
            if (this.Wf) {
                Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), Im());
                this.Wf = false;
            }
            canvas.drawBitmap(this.state.frameLoader.Jj(), (Rect) null, Im(), getPaint());
        }
    }

    public ByteBuffer getBuffer() {
        return this.state.frameLoader.getBuffer();
    }

    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    public int getFrameCount() {
        return this.state.frameLoader.getFrameCount();
    }

    public int getIntrinsicHeight() {
        return this.state.frameLoader.getHeight();
    }

    public int getIntrinsicWidth() {
        return this.state.frameLoader.getWidth();
    }

    public int getOpacity() {
        return -2;
    }

    public int getSize() {
        return this.state.frameLoader.getSize();
    }

    /* access modifiers changed from: package-private */
    public boolean isRecycled() {
        return this.Sf;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    /* access modifiers changed from: package-private */
    public void n(boolean z) {
        this.isRunning = z;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.Wf = true;
    }

    public void recycle() {
        this.Sf = true;
        this.state.frameLoader.clear();
    }

    public void setAlpha(int i) {
        getPaint().setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        getPaint().setColorFilter(colorFilter);
    }

    public void setLoopCount(int i) {
        if (i <= 0 && i != -1 && i != 0) {
            throw new IllegalArgumentException("Loop count must be greater than 0, or equal to GlideDrawable.LOOP_FOREVER, or equal to GlideDrawable.LOOP_INTRINSIC");
        } else if (i == 0) {
            int loopCount = this.state.frameLoader.getLoopCount();
            if (loopCount == 0) {
                loopCount = -1;
            }
            this.Vf = loopCount;
        } else {
            this.Vf = i;
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        i.b(!this.Sf, "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
        this.Tf = z;
        if (!z) {
            Lm();
        } else if (this.Rf) {
            Km();
        }
        return super.setVisible(z, z2);
    }

    public void start() {
        this.Rf = true;
        Jm();
        if (this.Tf) {
            Km();
        }
    }

    public void stop() {
        this.Rf = false;
        Lm();
    }

    public Bitmap ya() {
        return this.state.frameLoader.ya();
    }

    public int za() {
        return this.state.frameLoader.getCurrentIndex();
    }
}
