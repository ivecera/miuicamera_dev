package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import java.util.concurrent.locks.Lock;

/* compiled from: DrawableToBitmapConverter */
final class q {
    private static final String TAG = "DrawableToBitmap";
    private static final d jr = new p();

    private q() {
    }

    @Nullable
    static A<Bitmap> a(d dVar, Drawable drawable, int i, int i2) {
        Bitmap bitmap;
        Drawable current = drawable.getCurrent();
        boolean z = false;
        if (current instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) current).getBitmap();
        } else if (!(current instanceof Animatable)) {
            bitmap = b(dVar, current, i, i2);
            z = true;
        } else {
            bitmap = null;
        }
        if (!z) {
            dVar = jr;
        }
        return f.a(bitmap, dVar);
    }

    @Nullable
    private static Bitmap b(d dVar, Drawable drawable, int i, int i2) {
        if (i == Integer.MIN_VALUE && drawable.getIntrinsicWidth() <= 0) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to draw " + drawable + " to Bitmap with Target.SIZE_ORIGINAL because the Drawable has no intrinsic width");
            }
            return null;
        } else if (i2 != Integer.MIN_VALUE || drawable.getIntrinsicHeight() > 0) {
            if (drawable.getIntrinsicWidth() > 0) {
                i = drawable.getIntrinsicWidth();
            }
            if (drawable.getIntrinsicHeight() > 0) {
                i2 = drawable.getIntrinsicHeight();
            }
            Lock Ij = y.Ij();
            Ij.lock();
            Bitmap d2 = dVar.d(i, i2, Bitmap.Config.ARGB_8888);
            try {
                Canvas canvas = new Canvas(d2);
                drawable.setBounds(0, 0, i, i2);
                drawable.draw(canvas);
                canvas.setBitmap(null);
                return d2;
            } finally {
                Ij.unlock();
            }
        } else {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to draw " + drawable + " to Bitmap with Target.SIZE_ORIGINAL because the Drawable has no intrinsic height");
            }
            return null;
        }
    }
}
