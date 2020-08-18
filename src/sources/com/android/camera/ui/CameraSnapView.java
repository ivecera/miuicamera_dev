package com.android.camera.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.ui.drawable.snap.CameraSnapAnimateDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CameraSnapView extends View {
    private static final int LONG_PRESS_TIME = 800;
    private static final int MSG_START_CLICK = 1;
    private static final int MSG_START_LONG_PRESS = 2;
    private static final int MSG_START_LONG_PRESS_CANCEL_IN = 4;
    private static final int MSG_START_LONG_PRESS_CANCEL_OUT = 3;
    private static final float RECT_WIDTH = 0.75f;
    private static final String TAG = "CameraSnapView";
    private CameraSnapAnimateDrawable cameraSnapAnimateDrawable;
    private int mCurrentMode;
    private boolean mEnableSnapClick = true;
    private Bitmap mExtraBitmap;
    private Matrix mExtraBitmapMatrix;
    private Paint mExtraBitmapPaint;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.ui.CameraSnapView.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (CameraSnapView.this.mSnapListener != null) {
                int i = message.what;
                if (i == 1) {
                    CameraSnapView.this.mSnapListener.onSnapClick();
                } else if (i == 2) {
                    CameraSnapView.this.mSnapListener.onSnapLongPress();
                } else if (i == 3) {
                    CameraSnapView.this.mSnapListener.onSnapLongPressCancelOut();
                } else if (i == 4) {
                    CameraSnapView.this.mSnapListener.onSnapLongPressCancelIn();
                }
            }
        }
    };
    private int mHeight;
    private int mLongPressTime = 800;
    private boolean mMissTaken;
    private long mPressDownTime;
    private long mPressUpTime;
    /* access modifiers changed from: private */
    public SnapListener mSnapListener;
    private int mWidth;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SnapEvent {
    }

    public interface SnapListener {
        boolean canSnap();

        void onSnapClick();

        void onSnapLongPress();

        void onSnapLongPressCancelIn();

        void onSnapLongPressCancelOut();

        void onSnapPrepare();

        void onTrackSnapMissTaken(long j);

        void onTrackSnapTaken(long j);
    }

    public CameraSnapView(Context context) {
        super(context);
        initView();
    }

    public CameraSnapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public CameraSnapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private boolean inRegion(int i, int i2) {
        if ("hercules".equals(Build.DEVICE)) {
            i2 -= 96;
        }
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        return rect.contains(i, i2);
    }

    private void initView() {
    }

    private void recycleBitmap() {
        Bitmap bitmap = this.mExtraBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mExtraBitmap = null;
        }
    }

    private void setImageBitmap(Bitmap bitmap) {
        this.mExtraBitmap = bitmap;
        float width = ((float) getWidth()) * RECT_WIDTH;
        float height = ((float) getHeight()) * RECT_WIDTH;
        float height2 = width / height > ((float) bitmap.getWidth()) / ((float) bitmap.getHeight()) ? height / ((float) bitmap.getHeight()) : width / ((float) bitmap.getWidth());
        this.mExtraBitmapMatrix = new Matrix();
        this.mExtraBitmapMatrix.postScale(height2, height2);
        this.mExtraBitmapMatrix.postTranslate((((float) getWidth()) - width) / 2.0f, (((float) getHeight()) - height) / 2.0f);
        this.mExtraBitmapPaint = new Paint();
        this.mExtraBitmapPaint.setAntiAlias(true);
        this.mExtraBitmapPaint.setFilterBitmap(true);
    }

    public void addSegmentNow() {
        this.cameraSnapAnimateDrawable.addSegmentNow();
    }

    public void directFinishRecord() {
        this.cameraSnapAnimateDrawable.directFinishRecord();
    }

    public boolean hasSegments() {
        return this.cameraSnapAnimateDrawable.hasSegments();
    }

    public void hidePaintCenterVVItem() {
        this.cameraSnapAnimateDrawable.hidePaintCenterVVItem();
    }

    public void hideRoundPaintItem() {
        this.cameraSnapAnimateDrawable.hideRoundPaintItem();
        invalidate();
    }

    public void intoPattern(int i, boolean z) {
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.initParameters(i, z);
            invalidate();
        }
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidate();
    }

    public boolean isSnapEnableClick() {
        return this.mEnableSnapClick;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.cancelAnimation();
            this.cameraSnapAnimateDrawable.setCallback(null);
        }
        recycleBitmap();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.draw(canvas);
        }
        Bitmap bitmap = this.mExtraBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, this.mExtraBitmapMatrix, this.mExtraBitmapPaint);
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
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 != null) {
            cameraSnapAnimateDrawable2.setWidthHeight((float) this.mWidth, (float) this.mHeight);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
        if (r1 != 6) goto L_0x016a;
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isSnapEnableClick()) {
            String str = TAG;
            Log.d(str, "this view is disabled. action=" + motionEvent.getAction());
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    return false;
                }
                if (action != 3) {
                }
            }
            this.mHandler.removeCallbacksAndMessages(null);
            this.mPressUpTime = System.currentTimeMillis();
            if (this.mPressUpTime - this.mPressDownTime < ((long) this.mLongPressTime)) {
                if (inRegion((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    Log.d(TAG, "snap click action_up");
                    this.mHandler.sendEmptyMessage(1);
                } else {
                    Log.d(TAG, "out of shutter button when you touch up");
                }
            }
            this.mPressUpTime = System.currentTimeMillis();
            long j = this.mPressUpTime - this.mPressDownTime;
            if (j > ((long) this.mLongPressTime)) {
                if (inRegion((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    this.mHandler.sendEmptyMessage(4);
                } else {
                    this.mHandler.sendEmptyMessage(3);
                }
            }
            long j2 = j > 120 ? 0 : 120 - j;
            int i = this.mCurrentMode;
            if (!(i == 161 || i == 162 || i == 166 || i == 169 || i == 176 || i == 179 || i == 183)) {
                switch (i) {
                    case 172:
                    case 173:
                    case 174:
                        break;
                    default:
                        ViberatorContext.getInstance(getContext().getApplicationContext()).performSnapClick();
                        this.cameraSnapAnimateDrawable.startScaleUpAnimation(j2, null);
                        break;
                }
            }
        } else {
            int i2 = this.mCurrentMode;
            if (!(i2 == 161 || i2 == 162 || i2 == 166 || i2 == 169 || i2 == 176 || i2 == 183 || i2 == 179 || i2 == 180)) {
                switch (i2) {
                    case 172:
                    case 173:
                    case 174:
                        break;
                    default:
                        if (this.mSnapListener.canSnap()) {
                            ViberatorContext.getInstance(getContext().getApplicationContext()).performSnapClick();
                            this.cameraSnapAnimateDrawable.startScaleDownAnimation();
                            break;
                        } else {
                            if (!this.mMissTaken) {
                                this.mMissTaken = true;
                                if (this.mPressUpTime > 0) {
                                    this.mSnapListener.onTrackSnapMissTaken(System.currentTimeMillis() - this.mPressUpTime);
                                }
                            }
                            Log.d(TAG, "can not snap");
                            return false;
                        }
                }
            }
            Log.d(TAG, "snap click action_down");
            this.mMissTaken = false;
            this.mSnapListener.onSnapPrepare();
            this.mPressDownTime = System.currentTimeMillis();
            long j3 = this.mPressUpTime;
            if (j3 > 0) {
                this.mSnapListener.onTrackSnapTaken(this.mPressDownTime - j3);
            }
            this.mHandler.sendEmptyMessageDelayed(2, (long) this.mLongPressTime);
        }
        return true;
    }

    public void pauseRecording() {
        this.cameraSnapAnimateDrawable.pauseRecording();
    }

    public boolean performClick() {
        if (!Util.isAccessible()) {
            return super.performClick();
        }
        super.performClick();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendEmptyMessage(1);
        return true;
    }

    public void prepareRecording(BottomAnimationConfig bottomAnimationConfig) {
        this.cameraSnapAnimateDrawable.prepareRecording(bottomAnimationConfig);
    }

    public void removeLastSegment() {
        this.cameraSnapAnimateDrawable.removeLastSegment();
    }

    public void resumeRecording() {
        this.cameraSnapAnimateDrawable.resumeRecording();
    }

    public void setDurationText(String str) {
        this.cameraSnapAnimateDrawable.setDurationText(str);
    }

    public void setParameters(int i, boolean z, boolean z2) {
        this.mPressUpTime = 0;
        this.mCurrentMode = i;
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable2 == null) {
            this.cameraSnapAnimateDrawable = new CameraSnapAnimateDrawable(getContext());
            this.cameraSnapAnimateDrawable.setCallback(this);
            this.cameraSnapAnimateDrawable.initParameters(i, z2);
        } else {
            cameraSnapAnimateDrawable2.resetRecordingState();
            if (z) {
                this.cameraSnapAnimateDrawable.initTargetValues(i, z2);
                this.cameraSnapAnimateDrawable.startModeChangeAnimation();
            } else {
                this.cameraSnapAnimateDrawable.initParameters(i, z2);
                invalidate();
            }
        }
        if (i == 177 || i == 184) {
            this.mLongPressTime = 500;
        }
    }

    public void setSnapClickEnable(boolean z) {
        String str = TAG;
        Log.d(str, "setClickEnable: " + z);
        this.mEnableSnapClick = z;
    }

    public void setSnapListener(SnapListener snapListener) {
        this.mSnapListener = snapListener;
    }

    public void showPaintCenterVVItem() {
        this.cameraSnapAnimateDrawable.showPaintCenterVVItem();
    }

    public void showRoundPaintItem() {
        this.cameraSnapAnimateDrawable.showRoundPaintItem();
        invalidate();
    }

    public void startRing() {
        setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.v6_ic_audio_shutter_button_normal));
        this.cameraSnapAnimateDrawable.startRingAnimation();
    }

    public void stopRing() {
        recycleBitmap();
        this.cameraSnapAnimateDrawable.stopRingAnimation();
    }

    public void triggerAnimation(BottomAnimationConfig bottomAnimationConfig) {
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable2;
        int i = this.mCurrentMode;
        if (i != 161) {
            if (!(i == 162 || i == 166)) {
                if (i != 169) {
                    if (i != 176) {
                        if (!(i == 177 || i == 179)) {
                            if (i != 180) {
                                if (!(i == 183 || i == 184)) {
                                    switch (i) {
                                        case 172:
                                        case 173:
                                        case 174:
                                            break;
                                        default:
                                            return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if ((bottomAnimationConfig.mIsVideoBokeh || !bottomAnimationConfig.mIsStart) && (cameraSnapAnimateDrawable2 = this.cameraSnapAnimateDrawable) != null) {
                cameraSnapAnimateDrawable2.startRecordAnimation(bottomAnimationConfig);
                return;
            }
            return;
        }
        CameraSnapAnimateDrawable cameraSnapAnimateDrawable3 = this.cameraSnapAnimateDrawable;
        if (cameraSnapAnimateDrawable3 != null) {
            cameraSnapAnimateDrawable3.startRecordAnimation(bottomAnimationConfig);
        }
    }
}
