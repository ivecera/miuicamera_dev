package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.effect.EffectController;
import com.mi.config.b;
import d.h.a.m;

public class V6EffectCropView extends View implements V6FunctionUI {
    private static final int ANIMATE_RADIUS = 2;
    private static final int ANIMATE_RANGE = 1;
    private static final int ANIMATE_START_RADIUS;
    private static final int ANIMATE_START_RANGE;
    private static final int ANIMATION_TIME = 600;
    private static final int CIRCLE_RESIZE_TOUCH_TOLERANCE = Util.dpToPixel(36.0f);
    private static final int CORNER_BALL_RADIUS = Util.dpToPixel(5.0f);
    private static final int DEFAULT_RADIUS;
    private static final int DEFAULT_RANGE;
    private static final int HIDE_TILT_SHIFT_MASK = 2;
    private static final int MIN_CROP_WIDTH_HEIGHT = Util.dpToPixel(64.0f);
    private static final float MIN_DIS_FOR_MOVE_POINT = ((float) (Util.dpToPixel(30.0f) * Util.dpToPixel(30.0f)));
    private static final int MIN_DIS_FOR_SLOPE = (Util.dpToPixel(10.0f) * Util.dpToPixel(10.0f));
    private static final int MIN_RANGE = Util.dpToPixel(20.0f);
    private static final int MOVE_BLOCK = 16;
    private static final int MOVE_BOTTOM = 8;
    private static final int MOVE_LEFT = 1;
    private static final int MOVE_POINT1 = 257;
    private static final int MOVE_POINT2 = 258;
    private static final int MOVE_RADIUS = 32;
    private static final int MOVE_RANGE = 260;
    private static final int MOVE_RIGHT = 4;
    private static final int MOVE_TOP = 2;
    private static final int SHOW_TILT_SHIFT_MASK = 1;
    private static final int TOUCH_TOLERANCE = Util.dpToPixel(18.0f);
    private Handler mAnimateHandler;
    /* access modifiers changed from: private */
    public int mAnimateRadius = 0;
    /* access modifiers changed from: private */
    public int mAnimateRangeWidth = 0;
    private HandlerThread mAnimateThread;
    /* access modifiers changed from: private */
    public int mAnimationStartRadius;
    /* access modifiers changed from: private */
    public int mAnimationStartRange;
    /* access modifiers changed from: private */
    public long mAnimationStartTime;
    /* access modifiers changed from: private */
    public long mAnimationTotalTime;
    private final Paint mBorderPaint = new Paint();
    private int mCenterLineSquare;
    private final Paint mCornerPaint;
    /* access modifiers changed from: private */
    public final RectF mCropBounds = new RectF();
    /* access modifiers changed from: private */
    public final RectF mDefaultCircleBounds = new RectF();
    private final RectF mDefaultRectBounds = new RectF();
    private final RectF mDisplayBounds = new RectF();
    private final PointF mEffectPoint1 = new PointF();
    private final PointF mEffectPoint2 = new PointF();
    private final RectF mEffectRect = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    /* access modifiers changed from: private */
    public Interpolator mInterpolator = new m();
    private boolean mIsCircle;
    private boolean mIsInTapSlop;
    private boolean mIsRect;
    private double mLastMoveDis;
    private float mLastX;
    private float mLastY;
    private int mMaxRange;
    private int mMovingEdges;
    private float mNormalizedWidth = 0.0f;
    private final Point mPoint1 = new Point();
    private final Point mPoint2 = new Point();
    /* access modifiers changed from: private */
    public int mRadius = 0;
    /* access modifiers changed from: private */
    public int mRangeWidth = 0;
    private int mTapSlop;
    /* access modifiers changed from: private */
    public boolean mTiltShiftMaskAlive;
    /* access modifiers changed from: private */
    public ObjectAnimator mTiltShiftMaskFadeInAnimator;
    /* access modifiers changed from: private */
    public ObjectAnimator mTiltShiftMaskFadeOutAnimator;
    private AnimatorListenerAdapter mTiltShiftMaskFadeOutListener = new AnimatorListenerAdapter() {
        /* class com.android.camera.ui.V6EffectCropView.AnonymousClass1 */

        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            if (V6EffectCropView.this.mTiltShiftMaskFadeOutAnimator.isRunning()) {
                boolean unused = V6EffectCropView.this.mTiltShiftMaskAlive = false;
            }
        }
    };
    private Handler mTiltShiftMaskHandler;
    private final Point mTouchCenter = new Point();
    private boolean mVisible;

    static {
        int i = Util.sWindowHeight;
        DEFAULT_RANGE = i / 3;
        DEFAULT_RADIUS = i / 6;
        ANIMATE_START_RANGE = i;
        ANIMATE_START_RADIUS = i / 2;
    }

    public V6EffectCropView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setColor(-1);
        this.mBorderPaint.setStrokeWidth((float) (b.tl() ? 4 : 2));
        this.mCornerPaint = new Paint();
        this.mCornerPaint.setAntiAlias(true);
        this.mCornerPaint.setColor(-1);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTapSlop = viewConfiguration.getScaledTouchSlop() * viewConfiguration.getScaledTouchSlop();
        this.mTiltShiftMaskFadeInAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.anim.tilt_shift_mask_fade_in);
        this.mTiltShiftMaskFadeOutAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.anim.tilt_shift_mask_fade_out);
        this.mTiltShiftMaskFadeInAnimator.setTarget(EffectController.getInstance());
        ObjectAnimator objectAnimator = this.mTiltShiftMaskFadeInAnimator;
        objectAnimator.setValues(PropertyValuesHolder.ofKeyframe(objectAnimator.getPropertyName(), Keyframe.ofFloat(0.0f), Keyframe.ofFloat(0.3f, 1.0f), Keyframe.ofFloat(1.0f, 1.0f)));
        this.mTiltShiftMaskFadeOutAnimator.setTarget(EffectController.getInstance());
        this.mTiltShiftMaskFadeOutAnimator.addListener(this.mTiltShiftMaskFadeOutListener);
    }

    private void computeCertenLineCrossPoints(Point point, Point point2) {
        int i;
        int i2;
        int i3;
        if (squareOfPoints(point, point2) >= MIN_DIS_FOR_SLOPE) {
            int width = (int) this.mDisplayBounds.width();
            int height = (int) this.mDisplayBounds.height();
            int i4 = point.x;
            int i5 = point2.x;
            if (i4 == i5) {
                int clamp = Util.clamp(i4, 0, width);
                this.mPoint1.set(clamp, 0);
                this.mPoint2.set(clamp, height);
                return;
            }
            int i6 = point.y;
            int i7 = point2.y;
            if (i6 == i7) {
                int clamp2 = Util.clamp(i6, 0, height);
                this.mPoint1.set(0, clamp2);
                this.mPoint2.set(width, clamp2);
                return;
            }
            Point[] pointArr = new Point[2];
            float f2 = ((float) (i7 - i6)) / ((float) (i5 - i4));
            int i8 = (int) (((float) i4) - (((float) i6) / f2));
            if (i8 < 0 || i8 > width) {
                i = 0;
            } else {
                pointArr[0] = new Point(i8, 0);
                i = 1;
            }
            int i9 = (int) (((float) point.x) + (((float) (height - point.y)) / f2));
            if (i9 >= 0 && i9 <= width) {
                pointArr[i] = new Point(i9, height);
                i++;
            }
            int i10 = (int) (((float) point.y) - (((float) point.x) * f2));
            if (i10 >= 0 && i10 <= height && !isContained(pointArr, 0, i10)) {
                pointArr[i] = new Point(0, i10);
                i++;
            }
            int i11 = (int) (((float) point.y) + (((float) (width - point.x)) * f2));
            if (i11 < 0 || i11 > height || isContained(pointArr, width, i11)) {
                i2 = i;
            } else {
                i2 = i + 1;
                pointArr[i] = new Point(width, i11);
            }
            if (i2 == 1) {
                i3 = i2 + 1;
                pointArr[i2] = new Point(pointArr[0]);
            } else {
                i3 = i2;
            }
            if (i3 == 2) {
                int i12 = MIN_CROP_WIDTH_HEIGHT;
                if (i12 * i12 <= squareOfPoints(pointArr[0], pointArr[1])) {
                    this.mPoint1.set(pointArr[0].x, pointArr[0].y);
                    this.mPoint2.set(pointArr[1].x, pointArr[1].y);
                }
            }
        }
    }

    private Point computePointWithDistance(int i) {
        Point point = new Point();
        Point point2 = this.mPoint1;
        int i2 = point2.x;
        Point point3 = this.mPoint2;
        if (i2 == point3.x) {
            point.set(i2 - i, point2.y);
        } else {
            int i3 = point2.y;
            if (i3 == point3.y) {
                point.set(i2, i3 - i);
            } else {
                float sqrt = (float) Math.sqrt((double) this.mCenterLineSquare);
                Point point4 = this.mPoint1;
                int i4 = point4.y;
                Point point5 = this.mPoint2;
                int i5 = point4.x;
                point.set(i5 + ((int) (((float) ((i4 - point5.y) * i)) / sqrt)), i4 - ((int) (((float) ((i5 - point5.x) * i)) / sqrt)));
            }
        }
        return point;
    }

    private void detectMovingEdges(float f2, float f3) {
        boolean z = false;
        this.mMovingEdges = 0;
        if (this.mIsRect) {
            RectF rectF = this.mCropBounds;
            float f4 = rectF.bottom;
            int i = TOUCH_TOLERANCE;
            if (f3 <= f4 + ((float) i) && rectF.top - ((float) i) <= f3) {
                float abs = Math.abs(f2 - rectF.left);
                float abs2 = Math.abs(f2 - this.mCropBounds.right);
                if (abs <= ((float) TOUCH_TOLERANCE) && abs < abs2) {
                    this.mMovingEdges |= 1;
                } else if (abs2 <= ((float) TOUCH_TOLERANCE)) {
                    this.mMovingEdges |= 4;
                }
            }
            RectF rectF2 = this.mCropBounds;
            float f5 = rectF2.right;
            int i2 = TOUCH_TOLERANCE;
            if (f2 <= f5 + ((float) i2) && rectF2.left - ((float) i2) <= f2) {
                float abs3 = Math.abs(f3 - rectF2.top);
                float abs4 = Math.abs(f3 - this.mCropBounds.bottom);
                boolean z2 = abs3 <= ((float) TOUCH_TOLERANCE);
                if (abs3 < abs4) {
                    z = true;
                }
                if (z && z2) {
                    this.mMovingEdges |= 2;
                } else if (abs4 <= ((float) TOUCH_TOLERANCE)) {
                    this.mMovingEdges |= 8;
                }
            }
            if (this.mCropBounds.contains(f2, f3) && this.mMovingEdges == 0) {
                this.mMovingEdges = 16;
            }
        } else if (this.mIsCircle) {
            showTiltShiftMask();
            float centerX = this.mCropBounds.centerX();
            float centerY = this.mCropBounds.centerY();
            float width = (this.mCropBounds.width() + this.mCropBounds.height()) / 4.0f;
            float f6 = width * width;
            int i3 = CIRCLE_RESIZE_TOUCH_TOLERANCE;
            float f7 = (((float) i3) + width) * (width + ((float) i3));
            float f8 = f2 - centerX;
            float f9 = f3 - centerY;
            float f10 = (f8 * f8) + (f9 * f9);
            if (f10 > f6 && f10 <= f7) {
                this.mMovingEdges = 32;
            }
            if (this.mCropBounds.contains(f2, f3) && this.mMovingEdges == 0) {
                this.mMovingEdges = 16;
            }
        } else {
            showTiltShiftMask();
            Point point = new Point((int) f2, (int) f3);
            Point point2 = this.mTouchCenter;
            Point point3 = this.mPoint1;
            int i4 = point3.x;
            Point point4 = this.mPoint2;
            point2.set((i4 + point4.x) / 2, (point3.y + point4.y) / 2);
            if (MIN_DIS_FOR_MOVE_POINT < ((float) this.mCenterLineSquare) && squareOfPoints(point, this.mPoint1) < this.mCenterLineSquare / 16) {
                this.mMovingEdges = 257;
            } else if (MIN_DIS_FOR_MOVE_POINT >= ((float) this.mCenterLineSquare) || squareOfPoints(point, this.mPoint2) >= this.mCenterLineSquare / 16) {
                float squareOfDistance = getSquareOfDistance(f2, f3, new PointF(this.mPoint1), new PointF(this.mPoint2), false);
                int i5 = this.mRangeWidth;
                if (squareOfDistance < ((float) ((i5 * i5) / 9))) {
                    this.mMovingEdges = 16;
                    return;
                }
                this.mLastMoveDis = Math.sqrt((double) squareOfDistance);
                this.mMovingEdges = 260;
            } else {
                this.mMovingEdges = 258;
            }
        }
    }

    private float getSquareOfDistance(float f2, float f3, PointF pointF, PointF pointF2, boolean z) {
        float f4 = pointF.x;
        float f5 = pointF.y;
        float f6 = pointF2.x;
        float f7 = pointF2.y;
        if (f4 == f6) {
            float f8 = f2 - f4;
            return f8 * f8;
        } else if (f5 == f7) {
            float f9 = f3 - f5;
            return f9 * f9;
        } else {
            float f10 = f6 - f4;
            float f11 = f2 - f4;
            float f12 = f7 - f5;
            float f13 = f3 - f5;
            float f14 = (f10 * f11) + (f12 * f13);
            if (z && ((double) f14) <= 0.0d) {
                return (f11 * f11) + (f13 * f13);
            }
            float f15 = (f10 * f10) + (f12 * f12);
            if (!z || f14 < f15) {
                float f16 = f14 / f15;
                float f17 = f2 - (f4 + (f10 * f16));
                float f18 = (f5 + (f12 * f16)) - f3;
                return (f17 * f17) + (f18 * f18);
            }
            float f19 = f2 - f6;
            float f20 = f3 - f7;
            return (f19 * f19) + (f20 * f20);
        }
    }

    /* access modifiers changed from: private */
    public void hideTiltShiftMask() {
        Handler handler = this.mTiltShiftMaskHandler;
        if (handler != null) {
            handler.sendEmptyMessage(2);
        }
    }

    private void initHandler() {
        if (this.mTiltShiftMaskHandler == null) {
            this.mTiltShiftMaskHandler = new Handler(Looper.getMainLooper()) {
                /* class com.android.camera.ui.V6EffectCropView.AnonymousClass2 */

                public void dispatchMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        V6EffectCropView.this.mTiltShiftMaskFadeOutAnimator.cancel();
                        if (!V6EffectCropView.this.mTiltShiftMaskAlive) {
                            boolean unused = V6EffectCropView.this.mTiltShiftMaskAlive = true;
                            V6EffectCropView.this.mTiltShiftMaskFadeInAnimator.setupStartValues();
                            V6EffectCropView.this.mTiltShiftMaskFadeInAnimator.start();
                        }
                    } else if (i == 2) {
                        if (V6EffectCropView.this.mTiltShiftMaskFadeInAnimator.isRunning()) {
                            V6EffectCropView.this.mTiltShiftMaskFadeOutAnimator.setStartDelay(V6EffectCropView.this.mTiltShiftMaskFadeInAnimator.getDuration() - V6EffectCropView.this.mTiltShiftMaskFadeInAnimator.getCurrentPlayTime());
                        } else {
                            V6EffectCropView.this.mTiltShiftMaskFadeOutAnimator.setStartDelay(0);
                        }
                        if (V6EffectCropView.this.mTiltShiftMaskAlive) {
                            V6EffectCropView.this.mTiltShiftMaskFadeOutAnimator.start();
                        }
                    }
                }
            };
        }
        if (this.mAnimateHandler == null) {
            this.mAnimateThread = new HandlerThread("animateThread");
            this.mAnimateThread.start();
            this.mAnimateHandler = new Handler(this.mAnimateThread.getLooper()) {
                /* class com.android.camera.ui.V6EffectCropView.AnonymousClass3 */

                public void dispatchMessage(Message message) {
                    long currentTimeMillis = System.currentTimeMillis() - V6EffectCropView.this.mAnimationStartTime;
                    int i = message.what;
                    float f2 = 1.0f;
                    if (i == 1) {
                        if (currentTimeMillis < 600) {
                            f2 = V6EffectCropView.this.mInterpolator.getInterpolation(((float) currentTimeMillis) / ((float) V6EffectCropView.this.mAnimationTotalTime));
                            sendEmptyMessageDelayed(1, 30);
                        } else {
                            V6EffectCropView.this.hideTiltShiftMask();
                        }
                        V6EffectCropView v6EffectCropView = V6EffectCropView.this;
                        int unused = v6EffectCropView.mRangeWidth = v6EffectCropView.mAnimationStartRange + ((int) (((float) V6EffectCropView.this.mAnimateRangeWidth) * f2));
                        V6EffectCropView.this.onCropChange();
                    } else if (i == 2) {
                        if (currentTimeMillis < 600) {
                            f2 = V6EffectCropView.this.mInterpolator.getInterpolation(((float) currentTimeMillis) / ((float) V6EffectCropView.this.mAnimationTotalTime));
                            sendEmptyMessageDelayed(2, 30);
                        } else {
                            V6EffectCropView.this.hideTiltShiftMask();
                        }
                        float centerX = V6EffectCropView.this.mDefaultCircleBounds.centerX();
                        float centerY = V6EffectCropView.this.mDefaultCircleBounds.centerY();
                        V6EffectCropView v6EffectCropView2 = V6EffectCropView.this;
                        int unused2 = v6EffectCropView2.mRadius = v6EffectCropView2.mAnimationStartRadius + ((int) (((float) V6EffectCropView.this.mAnimateRadius) * f2));
                        V6EffectCropView.this.mCropBounds.set(centerX - ((float) V6EffectCropView.this.mRadius), centerY - ((float) V6EffectCropView.this.mRadius), centerX + ((float) V6EffectCropView.this.mRadius), centerY + ((float) V6EffectCropView.this.mRadius));
                        V6EffectCropView.this.onCropChange();
                    }
                }
            };
        }
    }

    private void innerShow() {
        int i = 1;
        boolean z = !isTiltShift();
        boolean isCircle = isCircle();
        if (!this.mVisible || this.mIsRect != z || this.mIsCircle != isCircle) {
            this.mVisible = true;
            this.mMovingEdges = 0;
            setVisibility(0);
            this.mIsRect = z;
            this.mIsCircle = isCircle;
            if (isTiltShift()) {
                this.mPoint1.set(0, ((int) this.mDisplayBounds.height()) / 2);
                this.mPoint2.set((int) this.mDisplayBounds.width(), ((int) this.mDisplayBounds.height()) / 2);
                this.mRangeWidth = ANIMATE_START_RANGE;
                this.mRadius = ANIMATE_START_RADIUS;
                this.mAnimationStartTime = System.currentTimeMillis();
                this.mAnimationTotalTime = 600;
                int i2 = DEFAULT_RANGE;
                int i3 = this.mRangeWidth;
                this.mAnimateRangeWidth = i2 - i3;
                this.mAnimationStartRange = i3;
                int i4 = DEFAULT_RADIUS;
                int i5 = this.mRadius;
                this.mAnimateRadius = i4 - i5;
                this.mAnimationStartRadius = i5;
                float centerX = this.mDefaultCircleBounds.centerX();
                float centerY = this.mDefaultCircleBounds.centerY();
                RectF rectF = this.mCropBounds;
                int i6 = this.mRadius;
                rectF.set(centerX - ((float) i6), centerY - ((float) i6), centerX + ((float) i6), centerY + ((float) i6));
                showTiltShiftMask();
                Handler handler = this.mAnimateHandler;
                if (handler != null) {
                    if (isCircle) {
                        i = 2;
                    }
                    handler.sendEmptyMessage(i);
                }
                invalidate();
            } else {
                this.mCropBounds.set(this.mDefaultRectBounds);
                setLayerType(2, null);
            }
            EffectController.getInstance().setInvertFlag(0);
            onCropChange();
        }
    }

    private static boolean isCircle() {
        if (CameraSettings.isTiltShiftOn()) {
            return DataRepository.dataItemRunning().getComponentRunningTiltValue().getComponentValue(160).equals(ComponentRunningTiltValue.TILT_CIRCLE);
        }
        return false;
    }

    private boolean isContained(Point[] pointArr, int i, int i2) {
        if (pointArr == null || pointArr.length == 0) {
            return false;
        }
        for (Point point : pointArr) {
            if (point == null) {
                return false;
            }
            if (point.x == i || point.y == i2) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTiltShift() {
        return CameraSettings.isTiltShiftOn();
    }

    private void moveCircle(float f2, float f3, float f4, float f5) {
        if (this.mMovingEdges == 16) {
            this.mCropBounds.offset(f4 > 0.0f ? Math.min(this.mDisplayBounds.right - this.mCropBounds.right, f4) : Math.max(this.mDisplayBounds.left - this.mCropBounds.left, f4), f5 > 0.0f ? Math.min(this.mDisplayBounds.bottom - this.mCropBounds.bottom, f5) : Math.max(this.mDisplayBounds.top - this.mCropBounds.top, f5));
        } else {
            float centerX = this.mCropBounds.centerX();
            float centerY = this.mCropBounds.centerY();
            float f6 = f2 - centerX;
            float f7 = f3 - centerY;
            float min = Math.min(Math.min(this.mDisplayBounds.width(), this.mDisplayBounds.height()) / 2.0f, Math.max((float) (MIN_CROP_WIDTH_HEIGHT / 2), (float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)))));
            this.mCropBounds.set(centerX - min, centerY - min, centerX + min, centerY + min);
        }
        onCropChange();
    }

    private void moveCrop(float f2, float f3, float f4, float f5) {
        int i = this.mMovingEdges;
        if (i == 260) {
            double sqrt = Math.sqrt((double) getSquareOfDistance(f2, f3, new PointF(this.mPoint1), new PointF(this.mPoint2), false));
            this.mRangeWidth = Util.clamp(this.mRangeWidth + ((int) (sqrt - this.mLastMoveDis)), MIN_RANGE, this.mMaxRange);
            this.mLastMoveDis = sqrt;
        } else if (i == 257 || i == 258) {
            computeCertenLineCrossPoints(this.mTouchCenter, new Point((int) f2, (int) f3));
        } else if (i == 16) {
            Point point = this.mPoint1;
            int i2 = (int) f4;
            int i3 = (int) f5;
            Point point2 = new Point(point.x + i2, point.y + i3);
            Point point3 = this.mPoint2;
            computeCertenLineCrossPoints(point2, new Point(point3.x + i2, point3.y + i3));
        }
        onCropChange();
    }

    private void moveEdges(float f2, float f3) {
        int i = this.mMovingEdges;
        if (i == 16) {
            this.mCropBounds.offset(f2 > 0.0f ? Math.min(this.mDisplayBounds.right - this.mCropBounds.right, f2) : Math.max(this.mDisplayBounds.left - this.mCropBounds.left, f2), f3 > 0.0f ? Math.min(this.mDisplayBounds.bottom - this.mCropBounds.bottom, f3) : Math.max(this.mDisplayBounds.top - this.mCropBounds.top, f3));
        } else {
            int i2 = MIN_CROP_WIDTH_HEIGHT;
            float f4 = (float) i2;
            float f5 = (float) i2;
            if ((i & 1) != 0) {
                RectF rectF = this.mCropBounds;
                rectF.left = Math.min(rectF.left + f2, rectF.right - f4);
            }
            if ((this.mMovingEdges & 2) != 0) {
                RectF rectF2 = this.mCropBounds;
                rectF2.top = Math.min(rectF2.top + f3, rectF2.bottom - f5);
            }
            if ((this.mMovingEdges & 4) != 0) {
                RectF rectF3 = this.mCropBounds;
                rectF3.right = Math.max(rectF3.right + f2, rectF3.left + f4);
            }
            if ((this.mMovingEdges & 8) != 0) {
                RectF rectF4 = this.mCropBounds;
                rectF4.bottom = Math.max(rectF4.bottom + f3, rectF4.top + f5);
            }
            this.mCropBounds.intersect(this.mDisplayBounds);
        }
        onCropChange();
    }

    private void normalizeRangeWidth() {
        Point computePointWithDistance = computePointWithDistance(this.mRangeWidth);
        this.mNormalizedWidth = (float) Math.sqrt((double) getSquareOfDistance(((float) computePointWithDistance.x) / this.mDisplayBounds.width(), ((float) computePointWithDistance.y) / this.mDisplayBounds.height(), this.mEffectPoint1, this.mEffectPoint2, false));
    }

    /* access modifiers changed from: private */
    public void onCropChange() {
        float width = this.mDisplayBounds.width();
        float height = this.mDisplayBounds.height();
        RectF rectF = this.mEffectRect;
        RectF rectF2 = this.mCropBounds;
        rectF.set(rectF2.left / width, rectF2.top / height, rectF2.right / width, rectF2.bottom / height);
        PointF pointF = this.mEffectPoint1;
        Point point = this.mPoint1;
        pointF.set(((float) point.x) / width, ((float) point.y) / height);
        PointF pointF2 = this.mEffectPoint2;
        Point point2 = this.mPoint2;
        pointF2.set(((float) point2.x) / width, ((float) point2.y) / height);
        this.mCenterLineSquare = squareOfPoints(this.mPoint1, this.mPoint2);
        normalizeRangeWidth();
        EffectController.getInstance().setEffectAttribute(this.mEffectRect, this.mEffectPoint1, this.mEffectPoint2, this.mNormalizedWidth);
        if (this.mIsRect) {
            invalidate();
        }
    }

    private void showTiltShiftMask() {
        Handler handler = this.mTiltShiftMaskHandler;
        if (handler != null) {
            handler.sendEmptyMessage(1);
        }
    }

    private int squareOfPoints(Point point, Point point2) {
        int i = point.x - point2.x;
        int i2 = point.y - point2.y;
        return (i * i) + (i2 * i2);
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void enableControls(boolean z) {
    }

    public void hide() {
        if (this.mVisible) {
            this.mVisible = false;
            setVisibility(4);
            EffectController.getInstance().clearEffectAttribute();
            EffectController.getInstance().setInvertFlag(0);
        }
    }

    public boolean isMoved() {
        return !this.mIsInTapSlop && this.mMovingEdges != 0;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onCameraOpen() {
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onCreate() {
        initHandler();
    }

    public void onDestroy() {
        HandlerThread handlerThread = this.mAnimateThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mAnimateThread = null;
            this.mAnimateHandler = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mVisible && this.mIsRect) {
            canvas.drawRect(this.mCropBounds, this.mBorderPaint);
            RectF rectF = this.mCropBounds;
            canvas.drawCircle(rectF.left, rectF.top, (float) CORNER_BALL_RADIUS, this.mCornerPaint);
            RectF rectF2 = this.mCropBounds;
            canvas.drawCircle(rectF2.right, rectF2.top, (float) CORNER_BALL_RADIUS, this.mCornerPaint);
            RectF rectF3 = this.mCropBounds;
            canvas.drawCircle(rectF3.left, rectF3.bottom, (float) CORNER_BALL_RADIUS, this.mCornerPaint);
            RectF rectF4 = this.mCropBounds;
            canvas.drawCircle(rectF4.right, rectF4.bottom, (float) CORNER_BALL_RADIUS, this.mCornerPaint);
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onPause() {
        Handler handler = this.mAnimateHandler;
        if (handler != null && handler.hasMessages(1)) {
            if (this.mAnimateHandler.hasMessages(1)) {
                this.mAnimateHandler.removeMessages(1);
                this.mRangeWidth = this.mAnimationStartRange + this.mAnimateRangeWidth;
            }
            if (this.mAnimateHandler.hasMessages(2)) {
                this.mAnimateHandler.removeMessages(2);
                this.mRadius = this.mAnimationStartRadius + this.mAnimateRadius;
            }
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onResume() {
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        float f2 = (float) i;
        float f3 = (float) i2;
        this.mDisplayBounds.set(0.0f, 0.0f, f2, f3);
        this.mDefaultRectBounds.set((float) ((i * 3) / 8), (float) ((i2 * 3) / 8), (float) ((i * 5) / 8), (float) ((i2 * 5) / 8));
        float f4 = (float) DEFAULT_RADIUS;
        float f5 = f2 / 2.0f;
        float f6 = f3 / 2.0f;
        this.mDefaultCircleBounds.set(f5 - f4, f6 - f4, f5 + f4, f6 + f4);
        this.mCropBounds.set(this.mIsRect ? this.mDefaultRectBounds : this.mDefaultCircleBounds);
        int i5 = i2 / 2;
        this.mPoint1.set(0, i5);
        this.mPoint2.set(i, i5);
        this.mMaxRange = (i2 * 2) / 3;
        this.mRangeWidth = this.mVisible ? DEFAULT_RANGE : ANIMATE_START_RANGE;
        onCropChange();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        if (r7 != 5) goto L_0x008b;
     */
    public boolean onViewTouchEvent(MotionEvent motionEvent) {
        if (!this.mVisible) {
            return false;
        }
        if (isEnabled()) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int action = motionEvent.getAction() & 255;
            if (action != 0) {
                if (action != 1) {
                    if (action == 2) {
                        float f2 = x - this.mLastX;
                        float f3 = y - this.mLastY;
                        if (this.mIsInTapSlop && ((float) this.mTapSlop) < (f2 * f2) + (f3 * f3)) {
                            this.mIsInTapSlop = false;
                        }
                        if (!this.mIsInTapSlop) {
                            if (this.mMovingEdges != 0) {
                                if (this.mIsRect) {
                                    moveEdges(x - this.mLastX, y - this.mLastY);
                                } else if (this.mIsCircle) {
                                    moveCircle(x, y, x - this.mLastX, y - this.mLastY);
                                } else {
                                    moveCrop(x, y, x - this.mLastX, y - this.mLastY);
                                }
                            }
                            this.mLastX = x;
                            this.mLastY = y;
                        }
                    } else if (action != 3) {
                    }
                }
                this.mMovingEdges = 0;
                hideTiltShiftMask();
                invalidate();
            } else {
                detectMovingEdges(x, y);
                this.mIsInTapSlop = true;
                this.mLastX = x;
                this.mLastY = y;
            }
        }
        return true;
    }

    public void removeTiltShiftMask() {
        Handler handler = this.mTiltShiftMaskHandler;
        if (handler != null) {
            handler.removeMessages(1);
            this.mTiltShiftMaskHandler.removeMessages(2);
        }
    }

    public void show() {
        if (EffectController.getInstance().isNeedRect(EffectController.getInstance().getEffectForPreview(false)) || isTiltShift()) {
            innerShow();
        }
    }

    public void show(float f2, float f3) {
        if (!this.mVisible) {
            this.mVisible = true;
            setVisibility(0);
            RectF rectF = this.mIsRect ? this.mDefaultRectBounds : this.mDefaultCircleBounds;
            this.mCropBounds.set(f2 - (rectF.width() / 2.0f), f3 - (rectF.height() / 2.0f), f2 + (rectF.width() / 2.0f), f3 + (rectF.height() / 2.0f));
            onCropChange();
        }
    }

    public void updateVisible() {
        if (EffectController.getInstance().isNeedRect(EffectController.getInstance().getEffectForPreview(false)) || isTiltShift()) {
            innerShow();
        } else {
            hide();
        }
    }

    public void updateVisible(int i) {
        if (EffectController.getInstance().isNeedRect(i)) {
            innerShow();
        } else {
            hide();
        }
    }
}
