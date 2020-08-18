package com.bumptech.glide.request.target;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import com.bumptech.glide.util.i;

/* compiled from: FixedSizeDrawable */
public class g extends Drawable {
    private Drawable Qf;
    private final RectF _f;
    private boolean bg;
    private final RectF bounds;
    private final Matrix matrix;
    private a state;

    /* compiled from: FixedSizeDrawable */
    static final class a extends Drawable.ConstantState {
        private final Drawable.ConstantState Qf;
        final int height;
        final int width;

        a(Drawable.ConstantState constantState, int i, int i2) {
            this.Qf = constantState;
            this.width = i;
            this.height = i2;
        }

        a(a aVar) {
            this(aVar.Qf, aVar.width, aVar.height);
        }

        public int getChangingConfigurations() {
            return 0;
        }

        @NonNull
        public Drawable newDrawable() {
            return new g(this, this.Qf.newDrawable());
        }

        @NonNull
        public Drawable newDrawable(Resources resources) {
            return new g(this, this.Qf.newDrawable(resources));
        }
    }

    public g(Drawable drawable, int i, int i2) {
        this(new a(drawable.getConstantState(), i, i2), drawable);
    }

    g(a aVar, Drawable drawable) {
        i.checkNotNull(aVar);
        this.state = aVar;
        i.checkNotNull(drawable);
        this.Qf = drawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        this.matrix = new Matrix();
        this._f = new RectF(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
        this.bounds = new RectF();
    }

    private void Mm() {
        this.matrix.setRectToRect(this._f, this.bounds, Matrix.ScaleToFit.CENTER);
    }

    public void clearColorFilter() {
        this.Qf.clearColorFilter();
    }

    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.concat(this.matrix);
        this.Qf.draw(canvas);
        canvas.restore();
    }

    @RequiresApi(19)
    public int getAlpha() {
        return this.Qf.getAlpha();
    }

    public Drawable.Callback getCallback() {
        return this.Qf.getCallback();
    }

    public int getChangingConfigurations() {
        return this.Qf.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    @NonNull
    public Drawable getCurrent() {
        return this.Qf.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.state.height;
    }

    public int getIntrinsicWidth() {
        return this.state.width;
    }

    public int getMinimumHeight() {
        return this.Qf.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.Qf.getMinimumWidth();
    }

    public int getOpacity() {
        return this.Qf.getOpacity();
    }

    public boolean getPadding(@NonNull Rect rect) {
        return this.Qf.getPadding(rect);
    }

    public void invalidateSelf() {
        super.invalidateSelf();
        this.Qf.invalidateSelf();
    }

    @NonNull
    public Drawable mutate() {
        if (!this.bg && super.mutate() == this) {
            this.Qf = this.Qf.mutate();
            this.state = new a(this.state);
            this.bg = true;
        }
        return this;
    }

    public void scheduleSelf(@NonNull Runnable runnable, long j) {
        super.scheduleSelf(runnable, j);
        this.Qf.scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.Qf.setAlpha(i);
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.bounds.set((float) i, (float) i2, (float) i3, (float) i4);
        Mm();
    }

    public void setBounds(@NonNull Rect rect) {
        super.setBounds(rect);
        this.bounds.set(rect);
        Mm();
    }

    public void setChangingConfigurations(int i) {
        this.Qf.setChangingConfigurations(i);
    }

    public void setColorFilter(int i, @NonNull PorterDuff.Mode mode) {
        this.Qf.setColorFilter(i, mode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.Qf.setColorFilter(colorFilter);
    }

    @Deprecated
    public void setDither(boolean z) {
        this.Qf.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.Qf.setFilterBitmap(z);
    }

    public boolean setVisible(boolean z, boolean z2) {
        return this.Qf.setVisible(z, z2);
    }

    public void unscheduleSelf(@NonNull Runnable runnable) {
        super.unscheduleSelf(runnable);
        this.Qf.unscheduleSelf(runnable);
    }
}
