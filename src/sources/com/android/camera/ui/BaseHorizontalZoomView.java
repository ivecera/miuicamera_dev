package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public abstract class BaseHorizontalZoomView extends View {
    private static final String TAG = "BaseHorizontalZoomView";

    public static abstract class HorizontalDrawAdapter {
        public void draw(float f2, int i, Canvas canvas, boolean z) {
        }

        public void draw(int i, Canvas canvas, boolean z) {
        }

        public abstract Paint.Align getAlign(int i);

        public abstract int getCount();

        public float measureGap(int i) {
            return 0.0f;
        }

        public abstract float measureWidth(int i);
    }

    public interface OnPositionSelectListener {
        void onPositionSelect(View view, int i, float f2);
    }

    public BaseHorizontalZoomView(Context context) {
        super(context);
    }

    public BaseHorizontalZoomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseHorizontalZoomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean canPositionScroll() {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        return cameraAction == null || !cameraAction.isDoingAction();
    }

    public boolean isIdle() {
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || canPositionScroll()) {
            return true;
        }
        Log.d(TAG, "cannot scroll to apply zoom value, do not process the down event.");
        return false;
    }

    public abstract void setDrawAdapter(HorizontalDrawAdapter horizontalDrawAdapter);

    public void setEvent(MotionEvent motionEvent) {
        onTouchEvent(motionEvent);
    }

    public abstract void setJustifyEnabled(boolean z);

    public abstract void setOnPositionSelectListener(OnPositionSelectListener onPositionSelectListener);

    public abstract void setSelection(float f2, boolean z);

    public void setSelection(int i) {
    }

    public void setSelectionUpdateUI(int i) {
    }
}
