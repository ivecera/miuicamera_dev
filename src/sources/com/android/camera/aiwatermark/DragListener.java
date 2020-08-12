package com.android.camera.aiwatermark;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;

public class DragListener implements View.OnTouchListener {
    private ComponentRunningAIWatermark mAIWatermark = null;
    private int[] mLocation = new int[4];
    private float mLocationLeft;
    private float mLocationTop;
    private float mOriginalX;
    private float mOriginalY;
    private View mParent = null;
    private Rect mRect;

    public DragListener(Rect rect) {
        this.mRect = rect;
        this.mAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
    }

    private void fixLocation(View view) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = view.getLeft();
        layoutParams.topMargin = view.getTop();
        layoutParams.setMargins(view.getLeft(), view.getTop(), 0, 0);
        view.setLayoutParams(layoutParams);
    }

    private int[] getNewLocation(View view, float f2, float f3, Rect rect) {
        int i = (int) (this.mLocationLeft + f2);
        int i2 = (int) (this.mLocationTop + f3);
        int width = view.getWidth() + i;
        int height = view.getHeight() + i2;
        int i3 = rect.left;
        if (i <= i3) {
            width = i3 + view.getWidth();
            i = i3;
        }
        int i4 = rect.top;
        if (i2 <= i4) {
            height = i4 + view.getHeight();
            i2 = i4;
        }
        int i5 = rect.right;
        if (width >= i5) {
            i = i5 - view.getWidth();
            width = i5;
        }
        int i6 = rect.bottom;
        if (height >= i6) {
            i2 = i6 - view.getHeight();
        } else {
            i6 = height;
        }
        return new int[]{i, i2, width, i6};
    }

    private void updateLocation(MotionEvent motionEvent, View view) {
        float rawX = motionEvent.getRawX() - this.mOriginalX;
        float rawY = motionEvent.getRawY() - this.mOriginalY;
        Rect rect = this.mRect;
        if (rect != null) {
            this.mLocation = getNewLocation(view, rawX, rawY, rect);
        } else {
            if (this.mParent == null) {
                this.mParent = (View) view.getParent();
            }
            this.mRect = new Rect(this.mParent.getLeft(), this.mParent.getTop(), this.mParent.getRight(), this.mParent.getBottom());
            this.mLocation = getNewLocation(view, rawX, rawY, this.mRect);
        }
        int[] iArr = this.mLocation;
        view.layout(iArr[0], iArr[1], iArr[2], iArr[3]);
        if (motionEvent.getAction() == 1) {
            fixLocation(view);
        }
        this.mAIWatermark.updateLocation(this.mLocation, this.mRect);
        this.mAIWatermark.setHasMove(true);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mOriginalX = motionEvent.getRawX();
            this.mOriginalY = motionEvent.getRawY();
            this.mLocationLeft = (float) view.getLeft();
            this.mLocationTop = (float) view.getTop();
            updateLocation(motionEvent, view);
        } else if (action == 1 || action == 2 || action == 7) {
            updateLocation(motionEvent, view);
        }
        return true;
    }

    public void reInit(Rect rect, int[] iArr) {
        this.mRect = rect;
        this.mLocation = iArr;
        this.mAIWatermark.updateLocation(this.mLocation, this.mRect);
    }
}
