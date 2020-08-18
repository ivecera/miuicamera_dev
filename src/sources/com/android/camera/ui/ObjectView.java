package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera2.CameraHardwareFace;

public class ObjectView extends FrameView {
    private static final int ADJUST_TIME_INTERVAL = 0;
    private static final int ANIMATION_DOING = 1;
    private static final int ANIMATION_DONE = 5;
    private static final int ANIMATION_IDLE = 0;
    private static final int ANIMATION_ZOOMIN_DOING = 2;
    private static final int ANIMATION_ZOOMIN_DONE = 3;
    private static final int ANIMATION_ZOOMOUT_DOING = 4;
    private static final int MIN_CROP_WIDTH_HEIGHT = 200;
    private static final int MOVE_BLOCK = 16;
    private static final int MOVE_BOTTOM = 8;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_RIGHT = 4;
    private static final int MOVE_TOP = 2;
    private static final int MOVE_VERTEXS = 32;
    private static final int MSG_ADJUST_DONE = 1;
    private static final int MSG_TRACK_FAIL = 2;
    private static final int NEAR_EDGE = Util.dpToPixel(35.0f);
    public static final int OBJECT_FOCUSED = 1;
    public static final int OBJECT_FOCUSING = 0;
    private static final int[] OBJECT_TRACKING_ICON = {R.drawable.ic_object_tracking, R.drawable.ic_object_tracking_succeed};
    public static final int OBJECT_TRACKING_SUCCEED = 1;
    private static final int OUT_OF_PREVIEW_THRESHOLD = 150;
    private static final int RECT_EDGE_WIDTH = Util.dpToPixel(10.0f);
    private static final int SCALING_DOWN_TIME = 80;
    private static final int SCALING_UP_TIME = 150;
    private static final String TAG = "ObjectView";
    private static final int TOUCH_TOLERANCE = Util.dpToPixel(25.0f);
    private static final int TRACKING_FAIL_THRESHOLD = 50;
    private static final int VERTEXS_TOUCH_TOLERANCE = Util.dpToPixel(35.0f);
    private static final int WAIT_TRACKING_TIME = 1000;
    private final RectF mDisplayBounds = new RectF();
    private Runnable mEndAction = new EndAction();
    protected CameraHardwareFace mFace;
    private Matrix mFace2UIMatrix = new Matrix();
    /* access modifiers changed from: private */
    public Filter mFilter = new Filter();
    private Handler mHandler = new Handler() {
        /* class com.android.camera.ui.ObjectView.AnonymousClass1 */

        public void dispatchMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ObjectView.this.showStart();
                if (!(ObjectView.this.mListener == null || ObjectView.this.getFocusRect() == null)) {
                    ObjectView.this.mListener.startObjectTracking();
                    ObjectView.this.mFilter.resetState();
                }
                sendEmptyMessageDelayed(2, 1000);
            } else if (i == 2 && ObjectView.this.mListener != null) {
                ObjectView.this.mListener.stopObjectTracking(true);
            }
        }
    };
    private float mLastX;
    private float mLastY;
    /* access modifiers changed from: private */
    public ObjectViewListener mListener;
    private int mLostTrackThreshold;
    private int mLostTrackingNum = 0;
    private int mMovingMode;
    protected RectF mObjectRect = new RectF();
    protected RectF mOldObjectRect = new RectF();
    private int mPreviewHeight;
    private int mPreviewWidth;
    private Runnable mStartAction = new StartAction();
    protected Drawable mTrackIndicator;
    private Drawable[] mTrackStatusIndicator = new Drawable[OBJECT_TRACKING_ICON.length];
    /* access modifiers changed from: private */
    public int mZoomAnimState = 0;

    private class EndAction implements Runnable {
        private EndAction() {
        }

        public void run() {
            int unused = ObjectView.this.mZoomAnimState = 5;
        }
    }

    /* access modifiers changed from: private */
    public class Filter {
        private static final int SHIFT_TIMES = 4;
        int mCurrentValue;
        int mHoldTimes;
        int mHoldValue;

        private Filter() {
            this.mCurrentValue = 0;
            this.mHoldValue = -1;
        }

        private int filter(int i) {
            int i2;
            if (i != this.mHoldValue) {
                this.mHoldValue = i;
                this.mHoldTimes = 1;
            } else {
                int i3 = this.mHoldTimes;
                if (i3 < 4) {
                    this.mHoldTimes = i3 + 1;
                }
                if (this.mHoldTimes == 4 && (i2 = this.mHoldValue) != this.mCurrentValue) {
                    this.mCurrentValue = i2;
                }
            }
            return this.mCurrentValue;
        }

        public boolean isBecomeStable(int i) {
            return this.mCurrentValue == 0 && filter(i) == 1;
        }

        public void resetState() {
            this.mCurrentValue = 0;
            this.mHoldValue = -1;
        }
    }

    public interface ObjectViewListener {
        void onObjectStable();

        void startObjectTracking();

        void stopObjectTracking(boolean z);
    }

    private class StartAction implements Runnable {
        private StartAction() {
        }

        public void run() {
            int unused = ObjectView.this.mZoomAnimState = 3;
        }
    }

    public ObjectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        for (int i = 0; i < OBJECT_TRACKING_ICON.length; i++) {
            this.mTrackStatusIndicator[i] = getContext().getResources().getDrawable(OBJECT_TRACKING_ICON[i]);
        }
        this.mTrackIndicator = this.mTrackStatusIndicator[0];
    }

    private void detectMovingStyle(float f2, float f3, float f4, float f5) {
        if (f4 < 0.0f || f5 < 0.0f) {
            boolean z = false;
            this.mMovingMode = 0;
            float abs = Math.abs(f2 - this.mObjectRect.left);
            float abs2 = Math.abs(f2 - this.mObjectRect.right);
            if (abs <= ((float) TOUCH_TOLERANCE) && abs < abs2) {
                this.mMovingMode |= 1;
            } else if (abs2 <= ((float) TOUCH_TOLERANCE)) {
                this.mMovingMode |= 4;
            }
            float abs3 = Math.abs(f3 - this.mObjectRect.top);
            float abs4 = Math.abs(f3 - this.mObjectRect.bottom);
            boolean z2 = abs3 <= ((float) TOUCH_TOLERANCE);
            if (abs3 < abs4) {
                z = true;
            }
            if (z && z2) {
                this.mMovingMode |= 2;
            } else if (abs4 <= ((float) TOUCH_TOLERANCE)) {
                this.mMovingMode |= 8;
            }
            if (this.mObjectRect.contains(f2, f3) && this.mMovingMode == 0) {
                this.mMovingMode = 16;
            }
        } else if (Math.abs(this.mObjectRect.left - Math.min(f2, f4)) <= ((float) VERTEXS_TOUCH_TOLERANCE) && Math.abs(this.mObjectRect.right - Math.max(f2, f4)) <= ((float) VERTEXS_TOUCH_TOLERANCE) && Math.abs(this.mObjectRect.top - Math.min(f3, f5)) <= ((float) VERTEXS_TOUCH_TOLERANCE) && Math.abs(this.mObjectRect.bottom - Math.max(f3, f5)) <= ((float) VERTEXS_TOUCH_TOLERANCE)) {
            this.mMovingMode = 32;
        }
        invalidate();
    }

    private boolean isInObjectViewArea(float f2, float f3) {
        RectF rectF = this.mObjectRect;
        float f4 = rectF.left;
        int i = RECT_EDGE_WIDTH;
        return f2 >= f4 - ((float) i) && f2 < rectF.right + ((float) i) && f3 >= rectF.top - ((float) i) && f3 < rectF.bottom + ((float) i);
    }

    private RectF mapRect(RectF rectF, boolean z) {
        RectF rectF2 = new RectF();
        rectF2.set(rectF);
        if (z) {
            ((FrameView) this).mMatrix.mapRect(rectF2);
        } else {
            this.mFace2UIMatrix.mapRect(rectF2);
        }
        return rectF2;
    }

    private void moveObjectView(MotionEvent motionEvent) {
        if (this.mMovingMode == 32) {
            float x = motionEvent.getX(0);
            float y = motionEvent.getY(0);
            float x2 = motionEvent.getX(1);
            float y2 = motionEvent.getY(1);
            if (200.0f < Math.abs(x2 - x)) {
                this.mObjectRect.left = Math.min(x, x2);
                this.mObjectRect.right = Math.max(x, x2);
            }
            if (200.0f < Math.abs(y2 - y)) {
                this.mObjectRect.top = Math.min(y, y2);
                this.mObjectRect.bottom = Math.max(y, y2);
            }
        } else {
            float x3 = motionEvent.getX() - this.mLastX;
            float y3 = motionEvent.getY() - this.mLastY;
            int i = this.mMovingMode;
            if (i == 16) {
                this.mObjectRect.offset(x3 > 0.0f ? Math.min(this.mDisplayBounds.right - this.mObjectRect.right, x3) : Math.max(this.mDisplayBounds.left - this.mObjectRect.left, x3), y3 > 0.0f ? Math.min(this.mDisplayBounds.bottom - this.mObjectRect.bottom, y3) : Math.max(this.mDisplayBounds.top - this.mObjectRect.top, y3));
            } else {
                if ((1 & i) != 0) {
                    RectF rectF = this.mObjectRect;
                    rectF.left = Math.min(rectF.left + x3, rectF.right - 200.0f);
                }
                if ((this.mMovingMode & 2) != 0) {
                    RectF rectF2 = this.mObjectRect;
                    rectF2.top = Math.min(rectF2.top + y3, rectF2.bottom - 200.0f);
                }
                if ((this.mMovingMode & 4) != 0) {
                    RectF rectF3 = this.mObjectRect;
                    rectF3.right = Math.max(rectF3.right + x3, rectF3.left + 200.0f);
                }
                if ((this.mMovingMode & 8) != 0) {
                    RectF rectF4 = this.mObjectRect;
                    rectF4.bottom = Math.max(rectF4.bottom + y3, rectF4.top + 200.0f);
                }
                this.mObjectRect.intersect(this.mDisplayBounds);
            }
        }
        invalidate();
    }

    private void resetView() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        setBackground(null);
        animate().cancel();
        setScaleX(1.0f);
        setScaleY(1.0f);
        layoutParams.width = (int) this.mDisplayBounds.width();
        layoutParams.height = (int) this.mDisplayBounds.height();
        layoutParams.setMargins(0, 0, 0, 0);
        this.mTrackIndicator = this.mTrackStatusIndicator[0];
    }

    private void setMatrix() {
        if (this.mPreviewWidth != 0 && this.mPreviewHeight != 0 && this.mDisplayBounds.width() != 0.0f && this.mDisplayBounds.height() != 0.0f) {
            ((FrameView) this).mMatrix.reset();
            ((FrameView) this).mMatrix.postScale(((float) this.mPreviewWidth) / this.mDisplayBounds.width(), ((float) this.mPreviewHeight) / this.mDisplayBounds.height());
            this.mFace2UIMatrix.reset();
            ((FrameView) this).mMatrix.invert(this.mFace2UIMatrix);
        }
    }

    private void updateAnimateView() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        layoutParams.width = (int) this.mObjectRect.width();
        layoutParams.height = (int) this.mObjectRect.height();
        RectF rectF = this.mObjectRect;
        layoutParams.setMargins((int) rectF.left, (int) rectF.top, 0, 0);
        requestLayout();
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void clear() {
        this.mFace = null;
        this.mOldObjectRect.set(this.mObjectRect);
        this.mObjectRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        resetView();
        this.mZoomAnimState = 0;
        invalidate();
    }

    @Override // com.android.camera.ui.FrameView
    public boolean faceExists() {
        return this.mFace != null || isAdjusting();
    }

    @Override // com.android.camera.ui.FrameView
    public RectF getFocusRect() {
        if (((ActivityBase) getContext()).getCameraScreenNail() == null || ((FrameView) this).mPause || this.mObjectRect.width() <= 0.0f || this.mObjectRect.height() <= 0.0f) {
            return null;
        }
        return this.mObjectRect;
    }

    public RectF getFocusRectInPreviewFrame() {
        RectF focusRect = getFocusRect();
        if (focusRect != null) {
            return mapRect(focusRect, true);
        }
        return null;
    }

    public boolean initializeTrackView(RectF rectF, boolean z) {
        if (rectF != null) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_T2T_TIMES, null, null);
            rectF.intersect(this.mDisplayBounds);
            if (rectF.width() < 200.0f || rectF.height() < 200.0f) {
                return false;
            }
            this.mObjectRect.set(rectF);
            this.mOldObjectRect.set(rectF);
        } else {
            this.mObjectRect.set(this.mOldObjectRect);
        }
        setVisibility(0);
        resume();
        requestLayout();
        if (z) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 0);
        }
        return true;
    }

    public boolean isAdjusting() {
        return this.mMovingMode != 0 || (this.mObjectRect.width() > 0.0f && this.mObjectRect.height() > 0.0f && this.mZoomAnimState == 0);
    }

    public boolean isTrackFailed() {
        CameraHardwareFace cameraHardwareFace = this.mFace;
        return cameraHardwareFace == null || 1 > cameraHardwareFace.score;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i;
        if (Util.sIsDumpLog) {
            Log.v(TAG, "onDraw(), mZoomAnimState:" + this.mZoomAnimState + ", mPause=" + ((FrameView) this).mPause + " mObjectRect=" + this.mObjectRect + " mFace=" + this.mFace + ", mTrackIndicator=" + this.mTrackIndicator + ", getWidth=" + getWidth() + ", getheight=" + getHeight() + ", mDisplayBounds=" + this.mDisplayBounds);
        }
        if (((FrameView) this).mPause || ((i = this.mZoomAnimState) != 0 && (i != 5 || this.mFace == null))) {
            super.onDraw(canvas);
            return;
        }
        canvas.save();
        canvas.translate(this.mObjectRect.centerX(), this.mObjectRect.centerY());
        Drawable drawable = this.mTrackIndicator;
        RectF rectF = this.mObjectRect;
        int round = Math.round((-(rectF.right - rectF.left)) / 2.0f);
        RectF rectF2 = this.mObjectRect;
        int round2 = Math.round((-(rectF2.bottom - rectF2.top)) / 2.0f);
        RectF rectF3 = this.mObjectRect;
        int round3 = Math.round((rectF3.right - rectF3.left) / 2.0f);
        RectF rectF4 = this.mObjectRect;
        drawable.setBounds(round, round2, round3, Math.round((rectF4.bottom - rectF4.top) / 2.0f));
        this.mTrackIndicator.draw(canvas);
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (((FrameView) this).mPause || ((this.mMovingMode == 0 && !isInObjectViewArea(x, y)) || this.mLostTrackingNum != 0)) {
            return false;
        }
        if (isEnabled()) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        if (this.mMovingMode != 0) {
                            moveObjectView(motionEvent);
                        }
                        this.mLastX = x;
                        this.mLastY = y;
                    } else if (actionMasked != 3) {
                        if (actionMasked != 5) {
                            if (actionMasked == 6 && motionEvent.getActionIndex() < 2 && this.mMovingMode == 32) {
                                this.mLastX = motionEvent.getX(1 - motionEvent.getActionIndex());
                                this.mLastY = motionEvent.getY(1 - motionEvent.getActionIndex());
                                detectMovingStyle(this.mLastX, this.mLastY, -1.0f, -1.0f);
                            }
                        } else if (motionEvent.getPointerCount() == 2) {
                            detectMovingStyle(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                            if (this.mMovingMode == 32 && this.mListener != null) {
                                int i = this.mZoomAnimState;
                                if (i == 4 || i == 2) {
                                    clearAnimation();
                                }
                                this.mZoomAnimState = 0;
                                this.mListener.stopObjectTracking(false);
                                moveObjectView(motionEvent);
                            }
                        }
                    }
                }
                this.mMovingMode = 0;
                invalidate();
                this.mHandler.sendEmptyMessageDelayed(1, 0);
            } else {
                if (!this.mHandler.hasMessages(1)) {
                    if (this.mListener != null) {
                        int i2 = this.mZoomAnimState;
                        if (i2 == 4 || i2 == 2) {
                            clearAnimation();
                        }
                        this.mZoomAnimState = 0;
                        this.mListener.stopObjectTracking(false);
                    }
                    initializeTrackView(null, false);
                }
                this.mHandler.removeMessages(1);
                detectMovingStyle(x, y, -1.0f, -1.0f);
                this.mLastX = x;
                this.mLastY = y;
            }
        }
        return true;
    }

    public void setDisplaySize(int i, int i2) {
        this.mDisplayBounds.set(0.0f, 0.0f, (float) i, (float) i2);
        setMatrix();
    }

    public void setObject(CameraHardwareFace cameraHardwareFace) {
        if (Util.sIsDumpLog) {
            Log.i(TAG, "setObject(), mZoomAnimState:" + this.mZoomAnimState + " , face.rect:" + cameraHardwareFace.rect + " , face.score:" + cameraHardwareFace.score + " , face.t2tStop:" + cameraHardwareFace.t2tStop + " , moving=" + this.mFilter.mCurrentValue + " , mPause=" + ((FrameView) this).mPause + " , visible=" + getVisibility() + " , getWidth()=" + getWidth() + " ,mDisplayBounds.width()=" + this.mDisplayBounds.width() + " , getHeight()=" + getHeight() + " ,mDisplayBounds.height()=" + this.mDisplayBounds.height());
        }
        int i = this.mZoomAnimState;
        if (i > 2) {
            if (i == 3) {
                if (cameraHardwareFace.score >= 1) {
                    this.mHandler.removeMessages(2);
                    showSuccess();
                }
            } else if (i == 5 && !((FrameView) this).mPause) {
                if (cameraHardwareFace.score < 1) {
                    if (this.mLostTrackThreshold == 0) {
                        RectF rectF = this.mObjectRect;
                        float f2 = rectF.left;
                        RectF rectF2 = this.mDisplayBounds;
                        float f3 = f2 - rectF2.left;
                        int i2 = NEAR_EDGE;
                        if (f3 < ((float) i2) || rectF.top - rectF2.top < ((float) i2) || rectF2.right - rectF.right < ((float) i2) || rectF2.bottom - rectF.bottom < ((float) i2)) {
                            this.mLostTrackThreshold = 150;
                        } else {
                            this.mLostTrackThreshold = 50;
                        }
                    }
                    this.mLostTrackingNum++;
                    int i3 = this.mLostTrackingNum;
                    if (i3 % 5 == 0 || i3 == this.mLostTrackThreshold) {
                        Log.v(TAG, "lost " + this.mLostTrackingNum + " times");
                    }
                    if (this.mLostTrackThreshold <= this.mLostTrackingNum) {
                        this.mListener.stopObjectTracking(true);
                    }
                } else {
                    this.mLostTrackThreshold = 0;
                    this.mLostTrackingNum = 0;
                }
                ((FrameView) this).mIsBigEnoughRect = ((double) cameraHardwareFace.rect.width()) / ((double) this.mPreviewWidth) > 0.33d || ((double) cameraHardwareFace.rect.height()) / ((double) this.mPreviewHeight) > 0.33d;
                if (((float) getWidth()) != this.mDisplayBounds.width() || ((float) getHeight()) != this.mDisplayBounds.height()) {
                    resetView();
                } else if (cameraHardwareFace == null || cameraHardwareFace.score < 1) {
                    this.mFace = null;
                } else {
                    this.mFace = cameraHardwareFace;
                    this.mTrackIndicator = this.mTrackStatusIndicator[1];
                    this.mObjectRect.set(mapRect(new RectF(this.mFace.rect), false));
                    this.mOldObjectRect.set(this.mObjectRect);
                    if (this.mFilter.isBecomeStable(cameraHardwareFace.t2tStop)) {
                        this.mListener.onObjectStable();
                    }
                }
                invalidate();
            }
        }
    }

    public void setObjectViewListener(ObjectViewListener objectViewListener) {
        this.mListener = objectViewListener;
    }

    public void setPreviewSize(int i, int i2) {
        this.mPreviewWidth = i;
        this.mPreviewHeight = i2;
        setMatrix();
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void showFail() {
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void showStart() {
        Log.i(TAG, "showStart()");
        if (this.mZoomAnimState == 0) {
            this.mZoomAnimState = 1;
            this.mTrackIndicator = this.mTrackStatusIndicator[0];
            updateAnimateView();
            setBackground(this.mTrackIndicator);
            animate().withLayer().setDuration(150).scaleX(1.2f).scaleY(1.2f).withEndAction(this.mStartAction);
        }
    }

    @Override // com.android.camera.ui.FrameView, com.android.camera.ui.FocusIndicator
    public void showSuccess() {
        Log.i(TAG, "showSuccess()");
        if (this.mZoomAnimState == 3) {
            this.mZoomAnimState = 4;
            this.mTrackIndicator = this.mTrackStatusIndicator[1];
            updateAnimateView();
            setBackground(this.mTrackIndicator);
            animate().withLayer().setDuration(80).scaleX(0.7f).scaleY(0.7f).withEndAction(this.mEndAction);
        }
    }
}
