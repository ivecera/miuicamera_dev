package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.panorama.MorphoPanoramaGP3;
import com.android.camera.ui.drawable.TriangleIndicatorDrawable;
import com.android.volley.DefaultRetryPolicy;
import java.util.HashMap;
import java.util.Objects;

public class PanoMovingIndicatorView extends View {
    private static final int DRAW_TAIL = 1;
    private static final int MAX_GAP = Util.dpToPixel(6.0f);
    private static final int MAX_SPEED_THRESHOLD = 7000;
    private static final int MOVING_LEFT_TO_RIGHT = 0;
    private static final int MOVING_RIGHT_TO_LEFT = 1;
    private static final float SHOW_ALIGN_THRESHOLD = 0.25f;
    private static final int SPEED_DEVIATION = (DefaultRetryPolicy.DEFAULT_TIMEOUT_MS / MAX_GAP);
    private static final float SPEED_FILTER_THRESHOLD = 0.1f;
    private static final int STONE_WIDTH = Util.dpToPixel(10.67f);
    public static final String TAG = "PanoMovingIndicatorView";
    private static int[] sBlockWidth = {Util.dpToPixel(0.67f), Util.dpToPixel(2.0f), Util.dpToPixel(3.34f)};
    private static int[] sGapWidth = {Util.dpToPixel(2.67f), Util.dpToPixel(2.0f), Util.dpToPixel(1.34f)};
    private static HashMap<Boolean, Integer> sTimesMap = new HashMap<>(2);
    private int mArrowMargin = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelOffset(R.dimen.pano_arrow_margin);
    private Point mCurrentFramePos = new Point();
    private int mDirection;
    private int mDisplayCenterY;
    private boolean mFast;
    private int mFilterMoveSpeed;
    private int mHalfStoneHeight;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.ui.PanoMovingIndicatorView.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 1) {
                PanoMovingIndicatorView panoMovingIndicatorView = PanoMovingIndicatorView.this;
                if (((float) panoMovingIndicatorView.getPointGap(panoMovingIndicatorView.mLatestSpeed)) != PanoMovingIndicatorView.this.mPointGap) {
                    PanoMovingIndicatorView panoMovingIndicatorView2 = PanoMovingIndicatorView.this;
                    panoMovingIndicatorView2.filterSpeed(panoMovingIndicatorView2.mLatestSpeed);
                    PanoMovingIndicatorView.this.applyNewGap();
                    sendEmptyMessageDelayed(1, 10);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public int mLatestSpeed;
    private TriangleIndicatorDrawable mMovingDirectionIc = new TriangleIndicatorDrawable();
    private int mOffsetX;
    protected int mOrientation;
    /* access modifiers changed from: private */
    public float mPointGap = -1.0f;
    private int mPreviewCenterY;
    private StateChangeTrigger<Boolean> mStateChangeTrigger = new StateChangeTrigger<>(false, sTimesMap);
    private Paint mTailPaint;

    class StateChangeTrigger<T> {
        private T mCurrentState;
        private T mLatestState;
        private int mLatestTimes = 0;
        private int mMaxTimes;
        private HashMap<T, Integer> mMaxTimesMap;

        public StateChangeTrigger(T t, HashMap<T, Integer> hashMap) {
            this.mLatestState = t;
            this.mCurrentState = t;
            this.mMaxTimesMap = hashMap;
        }

        public T filter(T t) {
            if (!Objects.equals(this.mLatestState, t)) {
                this.mLatestState = t;
                this.mLatestTimes = 1;
                Integer num = this.mMaxTimesMap.get(this.mLatestState);
                this.mMaxTimes = num == null ? 3 : num.intValue();
            } else {
                int i = this.mLatestTimes;
                if (i < this.mMaxTimes) {
                    this.mLatestTimes = i + 1;
                    String str = PanoMovingIndicatorView.TAG;
                    Log.v(str, "mLatestState=" + ((Object) this.mLatestState) + "  " + this.mLatestTimes);
                }
            }
            if (this.mMaxTimes == this.mLatestTimes && !Objects.equals(this.mCurrentState, this.mLatestState)) {
                this.mCurrentState = this.mLatestState;
            }
            return this.mCurrentState;
        }

        public void setCurrentState(T t) {
            this.mCurrentState = t;
        }
    }

    static {
        sTimesMap.put(true, 1);
        sTimesMap.put(false, 4);
    }

    public PanoMovingIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMovingDirectionIc.setWidth(context.getResources().getDimensionPixelSize(R.dimen.pano_arrow_width));
        this.mMovingDirectionIc.setHeight(context.getResources().getDimensionPixelSize(R.dimen.pano_arrow_height));
        this.mTailPaint = new Paint();
        this.mTailPaint.setColor(-1);
        this.mHalfStoneHeight = context.getResources().getDimensionPixelSize(R.dimen.pano_tail_height);
    }

    /* access modifiers changed from: private */
    public void applyNewGap() {
        this.mPointGap = (float) getPointGap(this.mFilterMoveSpeed);
        invalidate();
    }

    /* access modifiers changed from: private */
    public void filterSpeed(int i) {
        this.mFilterMoveSpeed = (int) ((((float) this.mFilterMoveSpeed) * 0.9f) + (((float) i) * 0.1f));
    }

    /* access modifiers changed from: private */
    public int getPointGap(int i) {
        if (i > 4500) {
            return (MAX_GAP * ((i - MorphoPanoramaGP3.FAST_SPEED_THRESHOLD) + SPEED_DEVIATION)) / DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
        }
        return -1;
    }

    public boolean isFar() {
        int i;
        int i2 = this.mCurrentFramePos.y;
        if (i2 == Integer.MIN_VALUE || (i = this.mPreviewCenterY) == 0 || ((float) Math.abs(i2 - i)) < ((float) this.mPreviewCenterY) * SHOW_ALIGN_THRESHOLD) {
            return false;
        }
        String str = TAG;
        Log.e(str, "too far current relative y is " + this.mCurrentFramePos.y + " refy is " + this.mPreviewCenterY);
        return true;
    }

    public boolean isTooFast() {
        return this.mPointGap > 0.0f;
    }

    public void onDraw(Canvas canvas) {
        Log.v(TAG, "onDraw mPointGap=" + this.mPointGap);
        Point point = this.mCurrentFramePos;
        int i = point.x;
        if (i != Integer.MIN_VALUE && point.y != Integer.MIN_VALUE) {
            int i2 = this.mArrowMargin;
            TriangleIndicatorDrawable triangleIndicatorDrawable = this.mMovingDirectionIc;
            int i3 = this.mDirection;
            if (i3 == 0) {
                i += this.mOffsetX + i2;
            } else if (1 == i3) {
                i -= this.mOffsetX + i2;
            }
            int i4 = (this.mDisplayCenterY + this.mCurrentFramePos.y) - this.mPreviewCenterY;
            canvas.save();
            canvas.translate((float) i, (float) i4);
            if (1 == this.mDirection) {
                canvas.rotate(180.0f);
            }
            int i5 = -triangleIndicatorDrawable.getIntrinsicWidth();
            triangleIndicatorDrawable.setBounds(i5, (-triangleIndicatorDrawable.getIntrinsicHeight()) / 2, 0, triangleIndicatorDrawable.getIntrinsicHeight() / 2);
            triangleIndicatorDrawable.draw(canvas);
            canvas.translate((float) triangleIndicatorDrawable.getIntrinsicWidth(), 0.0f);
            float f2 = this.mPointGap;
            int i6 = (int) (((float) i5) - (((float) STONE_WIDTH) + f2));
            int i7 = (int) f2;
            for (int i8 = 0; i8 < sGapWidth.length && i7 > 0; i8++) {
                int i9 = this.mHalfStoneHeight;
                canvas.drawRect((float) i6, (float) (-i9), (float) (sBlockWidth[i8] + i6), (float) i9, this.mTailPaint);
                int i10 = i6 + sBlockWidth[i8];
                if (i7 >= sGapWidth[i8]) {
                    i6 = i10 + 8;
                    i7 -= 8;
                } else {
                    i6 = i10 + i7;
                    i7 = 0;
                }
            }
            canvas.drawRect((float) i6, (float) (-this.mHalfStoneHeight), (float) (-triangleIndicatorDrawable.getIntrinsicWidth()), (float) this.mHalfStoneHeight, this.mTailPaint);
            if (1 == this.mDirection) {
                canvas.rotate(-180.0f);
            }
            canvas.translate((float) (-i), (float) (-i4));
            canvas.restore();
        }
    }

    public void setDisplayCenterY(int i) {
        this.mDisplayCenterY = i;
    }

    public void setMovingAttribute(int i, int i2, int i3) {
        if (i == 3) {
            this.mDirection = 0;
        } else if (i == 4) {
            this.mDirection = 1;
        }
        this.mOffsetX = i2;
        this.mFast = false;
        this.mFilterMoveSpeed = MorphoPanoramaGP3.FAST_SPEED_THRESHOLD;
        this.mStateChangeTrigger.setCurrentState(Boolean.valueOf(this.mFast));
        this.mCurrentFramePos.set(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.mPointGap = -1.0f;
    }

    public void setPosition(Point point, int i) {
        this.mCurrentFramePos.set(point.x, point.y);
        this.mPreviewCenterY = i;
        invalidate();
    }

    public void setTooFast(boolean z, int i) {
        String str = TAG;
        Log.i(str, "setTooFast moveSpeed=" + i + " fastFlag:" + z);
        int i2 = MAX_SPEED_THRESHOLD;
        if (i <= MAX_SPEED_THRESHOLD) {
            i2 = i;
        }
        this.mLatestSpeed = i2;
        if (((float) getPointGap(this.mLatestSpeed)) != this.mPointGap && !this.mHandler.hasMessages(1)) {
            this.mHandler.sendEmptyMessage(1);
        }
    }
}
