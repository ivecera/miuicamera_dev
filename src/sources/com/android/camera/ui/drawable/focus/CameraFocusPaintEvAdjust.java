package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import com.android.camera.Util;
import com.android.camera.ui.FocusView;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraFocusPaintEvAdjust extends CameraPaintBase {
    private static final int MARGIN = Util.dpToPixel(12.0f);
    private static final int TRIANGLE_BASE_DIS = Util.dpToPixel(3.0f);
    private static final int TRIANGLE_BASE_HEIGHT = Util.dpToPixel(5.0f);
    private static final int TRIANGLE_BASE_LEN = Util.dpToPixel(8.0f);
    private static final int TRIANGLE_MIN_MARGIN = Util.dpToPixel(25.0f);
    private float mCurrentDistanceY;
    private float mCurrentOffsetY;
    private Rect mDisplayRect;
    private float mEvValue = -1.0f;
    private int mLineHeight = FocusView.MAX_SLIDE_DISTANCE;
    private int mLineMargin = Util.dpToPixel(2.0f);
    private Paint mLinePaint;
    private int mLineWidth = Util.dpToPixel(1.0f);
    private int mRotation;
    private boolean mRtl = false;
    private boolean mShowLine = true;
    private float mStartOffsetY;

    public CameraFocusPaintEvAdjust(Context context) {
        super(context);
    }

    private boolean isNearlyOutOfEdge() {
        int width = this.mDisplayRect.width();
        int height = this.mDisplayRect.height();
        int i = this.mRotation;
        if ((i / 90) % 2 == 0) {
            return this.mRtl ? (((CameraPaintBase) this).mMiddleX - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) >= ((float) TRIANGLE_MIN_MARGIN) : ((((float) width) - ((CameraPaintBase) this).mMiddleX) - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) < ((float) TRIANGLE_MIN_MARGIN);
        }
        if (i == 90) {
            return ((((float) height) - ((CameraPaintBase) this).mMiddleY) - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) < ((float) TRIANGLE_MIN_MARGIN);
        }
        if (i == 270) {
            return (((CameraPaintBase) this).mMiddleY - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) < ((float) TRIANGLE_MIN_MARGIN);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        int i;
        float f2;
        Path path = new Path();
        if (isNearlyOutOfEdge()) {
            f2 = (((CameraPaintBase) this).mMiddleX - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN);
            i = TRIANGLE_BASE_LEN / 2;
        } else {
            f2 = ((CameraPaintBase) this).mMiddleX + ((float) CameraFocusAnimateDrawable.BIG_RADIUS) + ((float) MARGIN);
            i = TRIANGLE_BASE_LEN / 2;
        }
        float f3 = f2 - ((float) i);
        float f4 = ((((CameraPaintBase) this).mMiddleY - this.mCurrentOffsetY) + this.mCurrentDistanceY) - ((float) (TRIANGLE_BASE_DIS / 2));
        path.moveTo(f3, f4);
        path.lineTo(((float) TRIANGLE_BASE_LEN) + f3, f4);
        path.lineTo(((float) (TRIANGLE_BASE_LEN / 2)) + f3, f4 - ((float) TRIANGLE_BASE_HEIGHT));
        path.lineTo(f3, f4);
        int i2 = TRIANGLE_BASE_LEN;
        float f5 = ((float) (i2 / 2)) + f3;
        float f6 = ((float) (i2 / 2)) + f3;
        float f7 = ((CameraPaintBase) this).mMiddleY - ((float) (this.mLineHeight / 2));
        if (this.mShowLine) {
            int i3 = TRIANGLE_BASE_HEIGHT;
            int i4 = this.mLineMargin;
            if ((f4 - ((float) i3)) - f7 > ((float) i4)) {
                this.mLinePaint.setColor(((CameraPaintBase) this).mCurrentColor);
                this.mLinePaint.setAlpha(204);
                canvas.drawLine(f5, f7, f6, (f4 - ((float) i3)) - ((float) i4), this.mLinePaint);
            }
        }
        float f8 = ((CameraPaintBase) this).mMiddleY + this.mCurrentOffsetY + this.mCurrentDistanceY + ((float) (TRIANGLE_BASE_DIS / 2));
        path.moveTo(f3, f8);
        path.lineTo(((float) TRIANGLE_BASE_LEN) + f3, f8);
        path.lineTo(((float) (TRIANGLE_BASE_LEN / 2)) + f3, ((float) TRIANGLE_BASE_HEIGHT) + f8);
        path.lineTo(f3, f8);
        canvas.drawPath(path, ((CameraPaintBase) this).mPaint);
        if (this.mShowLine) {
            float f9 = ((CameraPaintBase) this).mMiddleY + ((float) (this.mLineHeight / 2));
            int i5 = this.mLineMargin;
            int i6 = TRIANGLE_BASE_HEIGHT;
            if (f9 - ((float) i5) > ((float) i6) + f8) {
                this.mLinePaint.setColor(((CameraPaintBase) this).mCurrentColor);
                this.mLinePaint.setAlpha(204);
                canvas.drawLine(f5, f8 + ((float) i6) + ((float) i5), f6, f9, this.mLinePaint);
            }
        }
    }

    public float getEvValue() {
        return this.mEvValue;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.FILL);
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        this.mLinePaint = new Paint();
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mLinePaint.setStrokeWidth((float) this.mLineWidth);
        this.mLinePaint.setColor(Color.argb(102, 255, 255, 255));
    }

    public void setDistanceY(float f2) {
        this.mCurrentDistanceY = f2;
    }

    public void setEvValue(float f2) {
        this.mEvValue = f2;
    }

    public void setOrientation(int i) {
        this.mRotation = i;
    }

    public void setRtlAndDisplayRect(boolean z, Rect rect) {
        this.mRtl = z;
        this.mDisplayRect = rect;
    }

    public void setShowLine(boolean z) {
        this.mShowLine = z;
    }

    public void setStartOffsetY(float f2) {
        this.mStartOffsetY = f2;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void updateValue(float f2) {
        super.updateValue(f2);
        float f3 = this.mStartOffsetY;
        this.mCurrentOffsetY = f3 - (f2 * f3);
    }
}
