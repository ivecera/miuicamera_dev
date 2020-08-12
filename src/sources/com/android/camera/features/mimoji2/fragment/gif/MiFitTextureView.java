package com.android.camera.features.mimoji2.fragment.gif;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

public class MiFitTextureView extends TextureView {
    private int mRatioHeight;
    private int mRatioWidth;

    public MiFitTextureView(Context context) {
        this(context, null);
    }

    public MiFitTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MiFitTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRatioWidth = 0;
        this.mRatioHeight = 0;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i4 = this.mRatioWidth;
        if (i4 == 0 || (i3 = this.mRatioHeight) == 0) {
            setMeasuredDimension(size, size2);
        } else if (size < (size2 * i4) / i3) {
            setMeasuredDimension(size, (i3 * size) / i4);
        } else {
            setMeasuredDimension((i4 * size2) / i3, size2);
        }
    }

    public void setAspectRatio(int i, int i2) {
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        this.mRatioWidth = i;
        this.mRatioHeight = i2;
        requestLayout();
    }
}
