package com.android.camera.features.mimoji2.fragment.gif;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

public class LongClickEditText extends EditText {
    private Context mContext;

    public LongClickEditText(Context context) {
        super(context);
        this.mContext = context;
    }

    public LongClickEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public LongClickEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }
}
