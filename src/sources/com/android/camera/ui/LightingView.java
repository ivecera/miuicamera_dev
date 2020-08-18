package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.android.camera.ui.drawable.lighting.LightingAnimateDrawable;

public class LightingView extends View implements Rotatable {
    private RectF faceViewRectF;
    private RectF focusRectF;
    private int mDisplayRectTopMargin;
    private int mHeight;
    private boolean mIsCinematicAspectRatio;
    private LightingAnimateDrawable mLightingAnimateDrawable;
    private int mRotation;
    private int mWidth;

    public LightingView(Context context) {
        super(context);
        initView(context);
    }

    public LightingView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public LightingView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        this.mLightingAnimateDrawable = new LightingAnimateDrawable(context);
        this.mLightingAnimateDrawable.setCallback(this);
        this.faceViewRectF = new RectF();
        this.focusRectF = new RectF();
    }

    public void clear() {
        this.mLightingAnimateDrawable.clear();
    }

    public RectF getFaceViewRectF() {
        return this.faceViewRectF;
    }

    public RectF getFocusRectF() {
        return this.focusRectF;
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mLightingAnimateDrawable.setCallback(null);
        this.mLightingAnimateDrawable.clear();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mLightingAnimateDrawable.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            super.onMeasure(i, i2);
            return;
        }
        this.mWidth = View.MeasureSpec.getSize(i);
        this.mHeight = View.MeasureSpec.getSize(i2);
        setMeasuredDimension(this.mWidth, this.mHeight);
        LightingAnimateDrawable lightingAnimateDrawable = this.mLightingAnimateDrawable;
        if (lightingAnimateDrawable != null) {
            if (this.mIsCinematicAspectRatio) {
                lightingAnimateDrawable.setCircleRatio(0.8f);
            } else {
                lightingAnimateDrawable.setCircleRatio(1.0f);
            }
            this.mLightingAnimateDrawable.setWidthHeight(this.mWidth, this.mHeight, this.mRotation);
            float max = (((float) Math.max(this.mWidth, this.mHeight)) / 2.0f) * 0.576f;
            float f2 = ((float) this.mWidth) / 2.0f;
            float f3 = (((float) this.mHeight) / 2.0f) * 0.8f;
            this.focusRectF.set(f2 - max, f3 - max, f2 + max, f3 + max);
        }
    }

    public void setCinematicAspectRatio(boolean z) {
        this.mIsCinematicAspectRatio = z;
        requestLayout();
    }

    public void setCircleHeightRatio(float f2) {
        this.mLightingAnimateDrawable.setCircleHeightRatio(f2);
    }

    public void setCircleRatio(float f2) {
        this.mLightingAnimateDrawable.setCircleRatio(f2);
    }

    @Override // com.android.camera.ui.Rotatable
    public void setOrientation(int i, boolean z) {
        if (this.mRotation != i) {
            this.mRotation = i;
            LightingAnimateDrawable lightingAnimateDrawable = this.mLightingAnimateDrawable;
            if (lightingAnimateDrawable != null) {
                lightingAnimateDrawable.updateMiddleValue(this.mRotation, true);
            }
        }
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void triggerAnimateExit() {
        this.mLightingAnimateDrawable.animateExit();
    }

    public void triggerAnimateFocusing() {
        this.mLightingAnimateDrawable.triggerFocusing();
    }

    public void triggerAnimateStart() {
        this.mLightingAnimateDrawable.animateStart();
    }

    public void triggerAnimateSuccess() {
        this.mLightingAnimateDrawable.triggerSuccess();
    }
}
