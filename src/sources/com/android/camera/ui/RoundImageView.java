package com.android.camera.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.camera.R;

public class RoundImageView extends ImageView {
    private final PaintFlagsDrawFilter mDrawFilter;
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    private float mRadius;
    private RectF mSrcRectF;
    private float mSrcSize;
    private Xfermode mXfermode;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mPaint = new Paint();
        this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mPaint.setAntiAlias(true);
        this.mSrcSize = getResources().getDimension(R.dimen.live_filter_item_size);
        float f2 = this.mSrcSize;
        this.mSrcRectF = new RectF(0.0f, 0.0f, f2, f2);
        this.mRadius = (float) getResources().getDimensionPixelSize(R.dimen.live_filter_item_corners_size);
        this.mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        float f3 = this.mSrcSize;
        this.mMaskBitmap = Bitmap.createBitmap((int) f3, (int) f3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.mMaskBitmap);
        new Paint().setColor(ViewCompat.MEASURED_STATE_MASK);
        RectF rectF = this.mSrcRectF;
        float f4 = this.mRadius;
        canvas.drawRoundRect(rectF, f4, f4, this.mPaint);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int saveLayer = canvas.saveLayer(this.mSrcRectF, null, 31);
        super.onDraw(canvas);
        canvas.setDrawFilter(this.mDrawFilter);
        this.mPaint.setXfermode(this.mXfermode);
        canvas.drawBitmap(this.mMaskBitmap, 0.0f, 0.0f, this.mPaint);
        this.mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    public void setRoundRadius(float f2) {
        this.mRadius = f2;
    }
}
