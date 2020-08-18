package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.android.camera.CameraSettings;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.ui.drawable.zoom.CameraZoomAnimateDrawable;
import io.reactivex.Completable;

public class ZoomView extends View {
    private static final String TAG = "ZoomView";
    private CameraZoomAnimateDrawable mCameraZoomAnimateDrawable;
    private boolean mIsHorizontal;
    private boolean mIsInited;
    private boolean mIsVisible = true;
    private boolean mIsZoomMoving;
    private float mMaxZoomRatio;
    private float mMinZoomRatio;
    private Point mPointView;
    public zoomValueChangeListener mZoomValueChangeListener;

    public interface zoomValueChangeListener {
        void onZoomValueChanged(float f2);
    }

    public ZoomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public float getCurrentZoomRatio() {
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable;
        if (cameraZoomAnimateDrawable != null) {
            return cameraZoomAnimateDrawable.getCurrentZoomRatio();
        }
        return 1.0f;
    }

    public void hide() {
        this.mIsVisible = false;
        this.mIsZoomMoving = false;
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable;
        if (cameraZoomAnimateDrawable != null) {
            cameraZoomAnimateDrawable.stopTouchUpAnimation();
        }
        Completable.create(new AlphaOutOnSubscribe(this)).subscribe();
    }

    public void init() {
        if (!this.mIsInited) {
            this.mIsInited = true;
            this.mCameraZoomAnimateDrawable = new CameraZoomAnimateDrawable(getContext(), this.mIsHorizontal);
            this.mCameraZoomAnimateDrawable.setCallback(this);
            this.mCameraZoomAnimateDrawable.updateZoomRatio(this.mMinZoomRatio, this.mMaxZoomRatio);
            this.mPointView = new Point();
            invalidate();
        }
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidate();
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public boolean isZoomMoving() {
        return this.mIsZoomMoving;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable;
        if (this.mIsVisible && this.mIsInited && (cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable) != null) {
            cameraZoomAnimateDrawable.draw(canvas);
        }
    }

    public boolean onViewTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsVisible || !this.mIsInited || this.mCameraZoomAnimateDrawable == null) {
            return false;
        }
        int[] iArr = new int[2];
        getLocationInWindow(iArr);
        this.mPointView.set((int) (motionEvent.getX() - ((float) iArr[0])), (int) (motionEvent.getY() - ((float) iArr[1])));
        if (motionEvent.getActionMasked() == 0 && this.mCameraZoomAnimateDrawable.startTouchDownAnimation(this.mPointView, this.mZoomValueChangeListener)) {
            this.mIsZoomMoving = true;
        }
        if (1 == motionEvent.getAction() || 3 == motionEvent.getAction()) {
            this.mIsZoomMoving = false;
            this.mCameraZoomAnimateDrawable.stopTouchUpAnimation();
        }
        if (2 == motionEvent.getAction()) {
            this.mCameraZoomAnimateDrawable.move(this.mPointView);
        }
        return true;
    }

    public void reInit() {
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable;
        if (CameraSettings.readZoom() <= this.mMinZoomRatio && (cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable) != null) {
            cameraZoomAnimateDrawable.reset();
            invalidate();
        }
    }

    public void reset() {
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable;
        if (cameraZoomAnimateDrawable != null) {
            cameraZoomAnimateDrawable.reset();
        }
    }

    public void setCurrentZoomRatio(float f2) {
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable;
        if (cameraZoomAnimateDrawable != null) {
            cameraZoomAnimateDrawable.setCurrentZoomRatio(f2);
            invalidate();
        }
    }

    public void setIsHorizonal(boolean z) {
        this.mIsHorizontal = z;
    }

    public void setOrientation(int i, boolean z) {
        setRotation((float) i);
    }

    public void setZoomValueChangeListener(zoomValueChangeListener zoomvaluechangelistener) {
        this.mZoomValueChangeListener = zoomvaluechangelistener;
    }

    public void show() {
        this.mIsVisible = true;
        Completable.create(new AlphaInOnSubscribe(this)).subscribe();
        invalidate();
    }

    public void updateZoomRatio(float f2, float f3) {
        this.mMinZoomRatio = f2;
        this.mMaxZoomRatio = f3;
        CameraZoomAnimateDrawable cameraZoomAnimateDrawable = this.mCameraZoomAnimateDrawable;
        if (cameraZoomAnimateDrawable != null) {
            cameraZoomAnimateDrawable.updateZoomRatio(this.mMinZoomRatio, this.mMaxZoomRatio);
        }
    }
}
