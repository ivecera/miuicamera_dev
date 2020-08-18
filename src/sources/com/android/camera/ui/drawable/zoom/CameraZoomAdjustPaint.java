package com.android.camera.ui.drawable.zoom;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraZoomAdjustPaint extends CameraPaintBase {
    private static final int ANIM_NONE = 0;
    private static final int ANIM_SLIDER_BACK = 5;
    private static final int ANIM_ZOOM = 1;
    private static final int OVER_FOR_ROUND = 20;
    private static final int RECT_WIDTH = 30;
    public static int SLIDER_HEIGHT;
    private static int TOUCH_RECT_WIDTH;
    private int mAnimState = 0;
    private float mCurrentZoomRatio;
    public boolean mIsHorizontal;
    private Point mLineEndPosition = new Point();
    private Paint mLinePaint;
    private Point mLineStartPosition = new Point();
    private float mMaxZoomRatio;
    private float mMinZoomRatio;
    private int mOrientation;
    public Point mSliderOriginalPosition = new Point();
    private Paint mSliderPaint;
    private Point mSliderPosition = new Point();
    private Rect mSliderTouchRect = new Rect();
    private Point mSliderTouchUpPosition = new Point();
    private int mZoomLastColor;
    private Paint mZoomLinePaint;
    private int mZoomMidColor;
    private int mZoomStartColor;

    CameraZoomAdjustPaint(Context context, boolean z) {
        super(context);
        this.mIsHorizontal = z;
        SLIDER_HEIGHT = context.getResources().getDimensionPixelSize(R.dimen.pro_view_zoom_view_height) - 20;
        TOUCH_RECT_WIDTH = context.getResources().getDimensionPixelSize(R.dimen.pro_view_zoom_view_width);
        if (this.mIsHorizontal) {
            Point point = this.mLineStartPosition;
            Point point2 = this.mLineEndPosition;
            int i = TOUCH_RECT_WIDTH / 2;
            point2.y = i;
            point.y = i;
            point.x = 10;
            point2.x = context.getResources().getDimensionPixelSize(R.dimen.pro_view_zoom_view_height) - 10;
            Point point3 = this.mSliderOriginalPosition;
            Point point4 = this.mSliderPosition;
            int i2 = TOUCH_RECT_WIDTH;
            int i3 = i2 / 2;
            point4.y = i3;
            point3.y = i3;
            int i4 = (SLIDER_HEIGHT + 20) / 2;
            point4.x = i4;
            point3.x = i4;
            Rect rect = this.mSliderTouchRect;
            int i5 = point4.x;
            rect.set(i5 - (i2 / 2), 0, i5 + i2, i2);
        } else {
            Point point5 = this.mLineStartPosition;
            Point point6 = this.mLineEndPosition;
            int i6 = TOUCH_RECT_WIDTH / 2;
            point6.x = i6;
            point5.x = i6;
            point5.y = 10;
            point6.y = context.getResources().getDimensionPixelSize(R.dimen.pro_view_zoom_view_height) - 10;
            Point point7 = this.mSliderOriginalPosition;
            Point point8 = this.mSliderPosition;
            int i7 = TOUCH_RECT_WIDTH;
            int i8 = i7 / 2;
            point8.x = i8;
            point7.x = i8;
            int i9 = (SLIDER_HEIGHT + 20) / 2;
            point8.y = i9;
            point7.y = i9;
            Rect rect2 = this.mSliderTouchRect;
            int i10 = point8.y;
            rect2.set(0, i10 - (i7 / 2), i7, i10 + i7);
        }
        this.mZoomStartColor = context.getResources().getColor(R.color.zoom_change_process_start);
        this.mZoomMidColor = context.getResources().getColor(R.color.zoom_change_process_mid);
        this.mZoomLastColor = context.getResources().getColor(R.color.zoom_change_process_last);
        this.mCurrentZoomRatio = 0.0f;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        boolean z;
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        float f2 = this.mCurrentZoomRatio;
        if (f2 <= this.mMinZoomRatio) {
            z = false;
            if (this.mAnimState == 1) {
                this.mLinePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
                this.mLinePaint.setAlpha(64);
            } else {
                this.mLinePaint.setColor(-1);
                this.mLinePaint.setAlpha(80);
            }
        } else {
            float f3 = this.mMaxZoomRatio;
            if (((double) f2) <= ((double) f3) * 0.7d) {
                this.mLinePaint.setColor(-1);
                this.mLinePaint.setAlpha(80);
                this.mZoomLinePaint.setColor(this.mZoomStartColor);
            } else if (((double) f2) < ((double) f3) * 0.9d) {
                float f4 = (f2 - (f3 * 0.7f)) / ((0.8f * f3) - (f3 * 0.7f));
                int i = this.mZoomMidColor;
                if (f4 <= 1.0f) {
                    i = ((Integer) argbEvaluator.evaluate(f4, Integer.valueOf(this.mZoomStartColor), Integer.valueOf(this.mZoomMidColor))).intValue();
                }
                this.mLinePaint.setColor(-1);
                this.mLinePaint.setAlpha(80);
                this.mZoomLinePaint.setColor(i);
            } else {
                int intValue = ((Integer) argbEvaluator.evaluate((f2 - (f3 * 0.9f)) / (f3 - (0.9f * f3)), Integer.valueOf(this.mZoomMidColor), Integer.valueOf(this.mZoomLastColor))).intValue();
                this.mZoomLinePaint.setStrokeCap(Paint.Cap.ROUND);
                this.mZoomLinePaint.setColor(intValue);
                this.mLinePaint.setColor(-1);
                this.mLinePaint.setAlpha(80);
            }
            z = true;
        }
        Point point = this.mLineStartPosition;
        float f5 = (float) point.y;
        Point point2 = this.mLineEndPosition;
        canvas.drawLine((float) point.x, f5, (float) point2.x, (float) point2.y, this.mLinePaint);
        float f6 = this.mCurrentZoomRatio / (this.mMaxZoomRatio - this.mMinZoomRatio);
        int i2 = SLIDER_HEIGHT;
        float f7 = f6 * ((float) i2);
        if (this.mIsHorizontal) {
            float max = Math.max(10.0f, (((float) (i2 + 20)) - f7) * 0.5f);
            float min = Math.min((float) this.mLineEndPosition.x, (((float) (SLIDER_HEIGHT + 20)) + f7) * 0.5f);
            if (z) {
                int i3 = this.mLineStartPosition.y;
                canvas.drawLine(max, (float) i3, min, (float) i3, this.mZoomLinePaint);
            }
            Point point3 = this.mSliderPosition;
            int i4 = point3.x;
            int i5 = point3.y;
            canvas.drawLine((float) i4, (float) (i5 - 30), (float) i4, (float) (i5 + 30), this.mSliderPaint);
            return;
        }
        float max2 = Math.max(10.0f, (((float) (i2 + 20)) - f7) * 0.5f);
        float min2 = Math.min((float) this.mLineEndPosition.y, (((float) (SLIDER_HEIGHT + 20)) + f7) * 0.5f);
        if (z) {
            int i6 = this.mLineStartPosition.x;
            canvas.drawLine((float) i6, max2, (float) i6, min2, this.mZoomLinePaint);
        }
        Point point4 = this.mSliderPosition;
        int i7 = point4.x;
        int i8 = point4.y;
        canvas.drawLine((float) (i7 - 30), (float) i8, (float) (i7 + 30), (float) i8, this.mSliderPaint);
    }

    public Rect getSliderTouchRect() {
        return this.mSliderTouchRect;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.mSliderPaint = new Paint();
        this.mSliderPaint.setAntiAlias(true);
        this.mSliderPaint.setStrokeWidth((float) Util.dpToPixel(3.0f));
        this.mSliderPaint.setStyle(Paint.Style.FILL);
        this.mSliderPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mSliderPaint.setColor(-1);
        this.mLinePaint = new Paint();
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStrokeWidth((float) Util.dpToPixel(2.0f));
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mLinePaint.setColor(-1);
        this.mLinePaint.setAlpha(80);
        this.mZoomLinePaint = new Paint();
        this.mZoomLinePaint.setAntiAlias(true);
        this.mZoomLinePaint.setStrokeWidth((float) Util.dpToPixel(2.0f));
        this.mZoomLinePaint.setStyle(Paint.Style.FILL);
        this.mZoomLinePaint.setColor(-16711936);
    }

    public void move(Point point) {
        if (this.mAnimState != 1) {
            this.mSliderPaint.setStrokeWidth((float) Util.dpToPixel(5.0f));
        }
        this.mAnimState = 1;
        if (this.mIsHorizontal) {
            this.mSliderPosition.x = Util.clamp(point.x, 10, this.mLineEndPosition.x);
            Rect rect = this.mSliderTouchRect;
            int i = this.mSliderPosition.x;
            int i2 = TOUCH_RECT_WIDTH;
            rect.set(i - (i2 / 2), 0, i + i2, i2);
            return;
        }
        this.mSliderPosition.y = Util.clamp(point.y, 10, this.mLineEndPosition.y);
        Rect rect2 = this.mSliderTouchRect;
        int i3 = this.mSliderPosition.y;
        int i4 = TOUCH_RECT_WIDTH;
        rect2.set(0, i3 - (i4 / 2), i4, i3 + i4);
    }

    public void reset() {
        this.mCurrentZoomRatio = this.mMinZoomRatio;
    }

    public void setCurrentZoomRatio(float f2) {
        this.mCurrentZoomRatio = f2;
    }

    public void setZoomRange(float f2, float f3) {
        this.mMinZoomRatio = f2;
        this.mMaxZoomRatio = f3;
    }

    public void startSliderBackAnimation() {
        this.mSliderPaint.setStrokeWidth((float) Util.dpToPixel(3.0f));
        this.mAnimState = 5;
        if (this.mIsHorizontal) {
            this.mSliderTouchUpPosition.x = this.mSliderPosition.x;
            return;
        }
        this.mSliderTouchUpPosition.y = this.mSliderPosition.y;
    }

    public void updateSliderPositionForBackAnim(float f2) {
        if (this.mAnimState == 5) {
            if (this.mIsHorizontal) {
                Point point = this.mSliderPosition;
                point.x = (int) (((1.0f - f2) * ((float) this.mSliderTouchUpPosition.x)) + (((float) this.mSliderOriginalPosition.x) * f2));
                Rect rect = this.mSliderTouchRect;
                int i = point.x;
                int i2 = TOUCH_RECT_WIDTH;
                rect.set(i - (i2 / 2), 0, i + i2, i2);
            } else {
                Point point2 = this.mSliderPosition;
                point2.y = (int) (((1.0f - f2) * ((float) this.mSliderTouchUpPosition.y)) + (((float) this.mSliderOriginalPosition.y) * f2));
                Rect rect2 = this.mSliderTouchRect;
                int i3 = point2.y;
                int i4 = TOUCH_RECT_WIDTH;
                rect2.set(0, i3 - (i4 / 2), i4, i3 + i4);
            }
            if (f2 >= 1.0f) {
                this.mAnimState = 0;
            }
        }
    }
}
