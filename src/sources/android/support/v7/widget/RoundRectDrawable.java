package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
class RoundRectDrawable extends Drawable {
    private ColorStateList mBackground;
    private final RectF mBoundsF;
    private final Rect mBoundsI;
    private boolean mInsetForPadding = false;
    private boolean mInsetForRadius = true;
    private float mPadding;
    private final Paint mPaint;
    private float mRadius;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

    RoundRectDrawable(ColorStateList colorStateList, float f2) {
        this.mRadius = f2;
        this.mPaint = new Paint(5);
        setBackground(colorStateList);
        this.mBoundsF = new RectF();
        this.mBoundsI = new Rect();
    }

    private PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    private void setBackground(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.mBackground = colorStateList;
        this.mPaint.setColor(this.mBackground.getColorForState(getState(), this.mBackground.getDefaultColor()));
    }

    private void updateBounds(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.mBoundsF.set((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom);
        this.mBoundsI.set(rect);
        if (this.mInsetForPadding) {
            float calculateVerticalPadding = RoundRectDrawableWithShadow.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius);
            this.mBoundsI.inset((int) Math.ceil((double) RoundRectDrawableWithShadow.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius)), (int) Math.ceil((double) calculateVerticalPadding));
            this.mBoundsF.set(this.mBoundsI);
        }
    }

    public void draw(Canvas canvas) {
        boolean z;
        Paint paint = this.mPaint;
        if (this.mTintFilter == null || paint.getColorFilter() != null) {
            z = false;
        } else {
            paint.setColorFilter(this.mTintFilter);
            z = true;
        }
        RectF rectF = this.mBoundsF;
        float f2 = this.mRadius;
        canvas.drawRoundRect(rectF, f2, f2, paint);
        if (z) {
            paint.setColorFilter(null);
        }
    }

    public ColorStateList getColor() {
        return this.mBackground;
    }

    public int getOpacity() {
        return -3;
    }

    public void getOutline(Outline outline) {
        outline.setRoundRect(this.mBoundsI, this.mRadius);
    }

    /* access modifiers changed from: package-private */
    public float getPadding() {
        return this.mPadding;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.mTint;
        return (colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = this.mBackground) != null && colorStateList.isStateful()) || super.isStateful();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateBounds(rect);
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mBackground;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        boolean z = colorForState != this.mPaint.getColor();
        if (z) {
            this.mPaint.setColor(colorForState);
        }
        ColorStateList colorStateList2 = this.mTint;
        if (colorStateList2 == null || (mode = this.mTintMode) == null) {
            return z;
        }
        this.mTintFilter = createTintFilter(colorStateList2, mode);
        return true;
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColor(@Nullable ColorStateList colorStateList) {
        setBackground(colorStateList);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    /* access modifiers changed from: package-private */
    public void setPadding(float f2, boolean z, boolean z2) {
        if (f2 != this.mPadding || this.mInsetForPadding != z || this.mInsetForRadius != z2) {
            this.mPadding = f2;
            this.mInsetForPadding = z;
            this.mInsetForRadius = z2;
            updateBounds(null);
            invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void setRadius(float f2) {
        if (f2 != this.mRadius) {
            this.mRadius = f2;
            updateBounds(null);
            invalidateSelf();
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = createTintFilter(this.mTint, this.mTintMode);
        invalidateSelf();
    }

    public void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = createTintFilter(this.mTint, this.mTintMode);
        invalidateSelf();
    }
}
