package com.android.camera.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import com.android.camera.R;

@SuppressLint({"AppCompatCustomView"})
public class AdaptiveTextView extends TextView {
    public static final String TAG = " AdaptiveTv";
    private float mMaxFontScale = 1.4f;
    private float mRealFontScale = 0.0f;

    public AdaptiveTextView(Context context) {
        super(context);
    }

    public AdaptiveTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public AdaptiveTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public AdaptiveTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AdaptiveTextView);
        this.mMaxFontScale = obtainStyledAttributes.getFloat(0, 1.4f);
        obtainStyledAttributes.recycle();
        Log.i(TAG, "init:  mMaxFontScale :" + this.mMaxFontScale);
    }

    public boolean isFocused() {
        return true;
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        if (this.mMaxFontScale > 0.0f) {
            float f2 = getResources().getConfiguration().fontScale;
            if (f2 >= this.mMaxFontScale) {
                if (this.mRealFontScale == 0.0f) {
                    this.mRealFontScale = getTextSize() * (this.mMaxFontScale / f2);
                }
                setTextSize(0, this.mRealFontScale);
                Log.i(TAG, "setText:  mMaxFontScale :" + this.mMaxFontScale + "   mRealFontScale : " + this.mRealFontScale);
            }
        }
    }

    public void setmMaxFontScale(float f2) {
        this.mMaxFontScale = f2;
    }
}
