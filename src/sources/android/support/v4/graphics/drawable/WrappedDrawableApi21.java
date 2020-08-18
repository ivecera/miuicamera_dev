package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.WrappedDrawableApi14;
import android.util.Log;
import java.lang.reflect.Method;

@RequiresApi(21)
class WrappedDrawableApi21 extends WrappedDrawableApi14 {
    private static final String TAG = "WrappedDrawableApi21";
    private static Method sIsProjectedDrawableMethod;

    private static class DrawableWrapperStateLollipop extends WrappedDrawableApi14.DrawableWrapperState {
        DrawableWrapperStateLollipop(@Nullable WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources);
        }

        @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14.DrawableWrapperState
        @NonNull
        public Drawable newDrawable(@Nullable Resources resources) {
            return new WrappedDrawableApi21(this, resources);
        }
    }

    WrappedDrawableApi21(Drawable drawable) {
        super(drawable);
        findAndCacheIsProjectedDrawableMethod();
    }

    WrappedDrawableApi21(WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, Resources resources) {
        super(drawableWrapperState, resources);
        findAndCacheIsProjectedDrawableMethod();
    }

    private void findAndCacheIsProjectedDrawableMethod() {
        if (sIsProjectedDrawableMethod == null) {
            try {
                sIsProjectedDrawableMethod = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
            } catch (Exception e2) {
                Log.w(TAG, "Failed to retrieve Drawable#isProjected() method", e2);
            }
        }
    }

    @NonNull
    public Rect getDirtyBounds() {
        return ((WrappedDrawableApi14) this).mDrawable.getDirtyBounds();
    }

    public void getOutline(@NonNull Outline outline) {
        ((WrappedDrawableApi14) this).mDrawable.getOutline(outline);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14
    public boolean isCompatTintEnabled() {
        if (Build.VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = ((WrappedDrawableApi14) this).mDrawable;
        return (drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable) || (drawable instanceof RippleDrawable);
    }

    public boolean isProjected() {
        Method method;
        Drawable drawable = ((WrappedDrawableApi14) this).mDrawable;
        if (!(drawable == null || (method = sIsProjectedDrawableMethod) == null)) {
            try {
                return ((Boolean) method.invoke(drawable, new Object[0])).booleanValue();
            } catch (Exception e2) {
                Log.w(TAG, "Error calling Drawable#isProjected() method", e2);
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14
    @NonNull
    public WrappedDrawableApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateLollipop(((WrappedDrawableApi14) this).mState, null);
    }

    public void setHotspot(float f2, float f3) {
        ((WrappedDrawableApi14) this).mDrawable.setHotspot(f2, f3);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        ((WrappedDrawableApi14) this).mDrawable.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // android.support.v4.graphics.drawable.WrappedDrawableApi14
    public boolean setState(@NonNull int[] iArr) {
        if (!super.setState(iArr)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.support.v4.graphics.drawable.TintAwareDrawable, android.support.v4.graphics.drawable.WrappedDrawableApi14
    public void setTint(int i) {
        if (isCompatTintEnabled()) {
            super.setTint(i);
        } else {
            ((WrappedDrawableApi14) this).mDrawable.setTint(i);
        }
    }

    @Override // android.support.v4.graphics.drawable.TintAwareDrawable, android.support.v4.graphics.drawable.WrappedDrawableApi14
    public void setTintList(ColorStateList colorStateList) {
        if (isCompatTintEnabled()) {
            super.setTintList(colorStateList);
        } else {
            ((WrappedDrawableApi14) this).mDrawable.setTintList(colorStateList);
        }
    }

    @Override // android.support.v4.graphics.drawable.TintAwareDrawable, android.support.v4.graphics.drawable.WrappedDrawableApi14
    public void setTintMode(PorterDuff.Mode mode) {
        if (isCompatTintEnabled()) {
            super.setTintMode(mode);
        } else {
            ((WrappedDrawableApi14) this).mDrawable.setTintMode(mode);
        }
    }
}
