package com.android.camera.ui.vv;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

public class VVProgressView extends View {
    private int mHeight;
    private int mWidth;
    private VVProgressDrawable progressDrawable;

    public VVProgressView(Context context) {
        super(context);
        init(context);
    }

    public VVProgressView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public VVProgressView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.progressDrawable = new VVProgressDrawable(context);
        this.progressDrawable.setCallback(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        VVProgressDrawable vVProgressDrawable = this.progressDrawable;
        if (vVProgressDrawable != null) {
            vVProgressDrawable.setCallback(null);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        VVProgressDrawable vVProgressDrawable = this.progressDrawable;
        if (vVProgressDrawable != null) {
            vVProgressDrawable.draw(canvas);
        }
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
        VVProgressDrawable vVProgressDrawable = this.progressDrawable;
        if (vVProgressDrawable != null) {
            vVProgressDrawable.setWidthHeight((float) this.mWidth, (float) this.mHeight);
        }
    }

    public void setDurationList(List<Long> list) {
        this.progressDrawable.setDurationList(list);
    }

    public void updateDuration(int i, long j) {
        this.progressDrawable.updateDuration(i, j);
        invalidate();
    }
}
