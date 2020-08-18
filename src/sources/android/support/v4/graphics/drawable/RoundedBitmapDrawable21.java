package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.view.Gravity;

@RequiresApi(21)
class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    protected RoundedBitmapDrawable21(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
    }

    public void getOutline(Outline outline) {
        updateDstRect();
        outline.setRoundRect(((RoundedBitmapDrawable) this).mDstRect, getCornerRadius());
    }

    /* access modifiers changed from: package-private */
    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    public void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2) {
        Gravity.apply(i, i2, i3, rect, rect2, 0);
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    public boolean hasMipMap() {
        Bitmap bitmap = ((RoundedBitmapDrawable) this).mBitmap;
        return bitmap != null && bitmap.hasMipMap();
    }

    @Override // android.support.v4.graphics.drawable.RoundedBitmapDrawable
    public void setMipMap(boolean z) {
        Bitmap bitmap = ((RoundedBitmapDrawable) this).mBitmap;
        if (bitmap != null) {
            bitmap.setHasMipMap(z);
            invalidateSelf();
        }
    }
}
