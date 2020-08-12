package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.ui.BaseHorizontalZoomView;

public class HorizontalZoomView extends BaseHorizontalZoomView {
    private static final String TAG = "HorizontalZoomView";
    private static final int TOUCH_SCROLL_THRESHOLD = 10;
    private static final int TOUCH_STATE_CLICK = 1;
    private static final int TOUCH_STATE_IDLE = 0;
    private static final int TOUCH_STATE_SCROLL = 2;
    private boolean isOnlyDrawLine;
    private BaseHorizontalZoomView.HorizontalDrawAdapter mDrawAdapter;
    private float mDrawEndX = -1.0f;
    private float mDrawStartX = -1.0f;
    private boolean mEnableCallBack = true;
    private float mFirstItemWidth;
    protected float mItemGap;
    private float mItemWidthNormal;
    private float mLastItemWidth;
    private int mMargin;
    private BaseHorizontalZoomView.OnPositionSelectListener mOnPositionZoomSelectListener;
    private float mSelectPointX;
    protected float mTotalWidth;
    private float mTouchStartTime;
    private float mTouchStartX;
    private float mTouchStartY;
    private int mTouchState = 0;
    private float mTouchX;

    public HorizontalZoomView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalZoomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public HorizontalZoomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private float getFirstOrLastWidth(boolean z) {
        if (Util.isLayoutRTL(getContext())) {
            z = !z;
        }
        return (z ? this.mFirstItemWidth : this.mLastItemWidth) - (this.mItemWidthNormal / 2.0f);
    }

    private float getItemWidth(int i) {
        return this.mDrawAdapter.measureWidth(i);
    }

    private void setSelectPointXToEffectiveRang(float f2) {
        this.mSelectPointX = Util.clamp(f2, this.mDrawStartX + getFirstOrLastWidth(true), this.mDrawEndX - getFirstOrLastWidth(false));
    }

    private boolean startScrollIfNeeded(MotionEvent motionEvent) {
        int y = (int) motionEvent.getY();
        float x = (float) ((int) motionEvent.getX());
        float f2 = this.mTouchStartX;
        if (x >= f2 - 10.0f && x <= f2 + 10.0f) {
            float f3 = (float) y;
            float f4 = this.mTouchStartY;
            if (f3 >= f4 - 10.0f && f3 <= f4 + 10.0f) {
                return false;
            }
        }
        this.mTouchState = 2;
        return true;
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        this.mMargin = context.getResources().getDimensionPixelSize(R.dimen.zoom_popup_padding_left);
        int i = this.mMargin;
        this.mDrawStartX = (float) i;
        int i2 = Util.sWindowWidth;
        this.mDrawEndX = (float) (i2 - i);
        this.mTotalWidth = (float) (i2 - (i * 2));
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public boolean isIdle() {
        return this.mTouchState == 0;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f2;
        setSelectPointXToEffectiveRang(this.mSelectPointX);
        float f3 = 2.0f;
        float height = (((float) getHeight()) * 2.0f) / 5.0f;
        boolean z = true;
        if (this.mDrawAdapter != null) {
            boolean isLayoutRTL = Util.isLayoutRTL(getContext());
            int count = isLayoutRTL ? this.mDrawAdapter.getCount() - 1 : 0;
            int count2 = isLayoutRTL ? 0 : this.mDrawAdapter.getCount() - 1;
            int i = isLayoutRTL ? -1 : 1;
            boolean z2 = true;
            float f4 = this.mDrawStartX;
            int i2 = 0;
            int i3 = -1;
            while (i2 < this.mDrawAdapter.getCount()) {
                int i4 = (i2 * i) + count;
                float itemWidth = getItemWidth(i4);
                float firstOrLastWidth = i2 == 0 ? (f4 + getFirstOrLastWidth(z)) - this.mSelectPointX : ((this.mItemWidthNormal / f3) + f4) - this.mSelectPointX;
                if (z2) {
                    if (this.mSelectPointX <= this.mDrawStartX + getFirstOrLastWidth(z)) {
                        i3 = count;
                    } else if (this.mSelectPointX >= this.mDrawEndX - getFirstOrLastWidth(false)) {
                        i3 = count2;
                    } else {
                        f2 = 2.0f;
                        if (Math.abs(firstOrLastWidth) <= this.mItemWidthNormal / 2.0f || (this.isOnlyDrawLine && Math.abs(firstOrLastWidth) <= (this.mItemGap / 2.0f) + (this.mItemWidthNormal / 2.0f))) {
                            i3 = i4;
                            z2 = false;
                        }
                    }
                    f2 = 2.0f;
                    z2 = false;
                } else {
                    f2 = 2.0f;
                }
                canvas.save();
                canvas.translate(0.0f, height);
                this.mDrawAdapter.draw(f4, i4, canvas, !z2 && i3 == i4);
                canvas.restore();
                f4 += itemWidth + this.mItemGap;
                i2++;
                f3 = f2;
                z = true;
            }
            canvas.save();
            canvas.translate(0.0f, height);
            if (z2) {
                this.mDrawAdapter.draw(this.mSelectPointX, -1, canvas, z2);
            }
            canvas.restore();
            if (this.mOnPositionZoomSelectListener != null && this.mEnableCallBack) {
                float firstOrLastWidth2 = ((this.mSelectPointX - getFirstOrLastWidth(true)) - ((float) this.mMargin)) / ((this.mTotalWidth - getFirstOrLastWidth(true)) - getFirstOrLastWidth(false));
                if (isLayoutRTL) {
                    firstOrLastWidth2 = 1.0f - firstOrLastWidth2;
                }
                this.mOnPositionZoomSelectListener.onPositionSelect(this, i3, firstOrLastWidth2);
            }
        }
        this.isOnlyDrawLine = false;
        this.mEnableCallBack = true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0015, code lost:
        if (r0 != 3) goto L_0x007f;
     */
    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!super.onTouchEvent(motionEvent)) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.mTouchState == 1) {
                        startScrollIfNeeded(motionEvent);
                    }
                    if (this.mTouchState == 2) {
                        this.mSelectPointX += motionEvent.getX() - this.mTouchX;
                        invalidate();
                    }
                }
            }
            if (((float) System.currentTimeMillis()) - this.mTouchStartTime > ((float) ViewConfiguration.getLongPressTimeout())) {
                this.mTouchState = 0;
                return true;
            }
            if (this.mTouchState == 2) {
                this.mSelectPointX += motionEvent.getX() - this.mTouchX;
            } else {
                this.mSelectPointX = motionEvent.getX();
            }
            this.mTouchState = 0;
            this.isOnlyDrawLine = true;
            invalidate();
        } else {
            this.mTouchStartTime = (float) System.currentTimeMillis();
            this.mTouchStartX = motionEvent.getX();
            this.mTouchStartY = motionEvent.getY();
            if (this.mTouchState != 2) {
                this.mTouchState = 1;
            }
        }
        this.mTouchX = motionEvent.getX();
        return true;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public void setDrawAdapter(BaseHorizontalZoomView.HorizontalDrawAdapter horizontalDrawAdapter) {
        this.mDrawAdapter = horizontalDrawAdapter;
        float f2 = 0.0f;
        for (int i = 0; i < this.mDrawAdapter.getCount(); i++) {
            if (i == 0) {
                this.mFirstItemWidth = getItemWidth(i);
            } else if (i == this.mDrawAdapter.getCount() - 1) {
                this.mLastItemWidth = getItemWidth(i);
            } else if (this.mItemWidthNormal == 0.0f) {
                this.mItemWidthNormal = getItemWidth(i);
            }
            f2 += getItemWidth(i);
        }
        this.mItemGap = (this.mTotalWidth - f2) / ((float) (this.mDrawAdapter.getCount() - 1));
        invalidate();
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public void setEvent(MotionEvent motionEvent) {
        this.mTouchState = 2;
        onTouchEvent(motionEvent);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public void setJustifyEnabled(boolean z) {
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public void setOnPositionSelectListener(BaseHorizontalZoomView.OnPositionSelectListener onPositionSelectListener) {
        this.mOnPositionZoomSelectListener = onPositionSelectListener;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView
    public void setSelection(float f2, boolean z) {
        if (Util.isLayoutRTL(getContext()) && this.mDrawAdapter != null) {
            f2 = 1.0f - f2;
        }
        this.mSelectPointX = (((this.mTotalWidth - getFirstOrLastWidth(true)) - getFirstOrLastWidth(false)) * f2) + getFirstOrLastWidth(true) + ((float) this.mMargin);
        this.mEnableCallBack = !z;
        this.isOnlyDrawLine = z;
        Log.d(TAG, "setSelection   " + this.mSelectPointX + "  " + f2);
        invalidate();
    }
}
