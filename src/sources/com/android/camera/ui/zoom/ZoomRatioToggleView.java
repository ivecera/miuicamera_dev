package com.android.camera.ui.zoom;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.mi.config.b;
import d.c.a.a.l;
import d.c.a.a.m;
import d.h.a.w;
import java.util.ArrayList;
import java.util.Arrays;

public class ZoomRatioToggleView extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {
    private static final int DEFAULT_DURATION_OF_FADEOUT_ANIMATION = 400;
    private static final int DEFAULT_DURATION_OF_MOVING_ANIMATION = 260;
    private static final int INVALID_INDEX = -1;
    private static final float INVALID_ZOOM_RATIO = -1.0f;
    private static final int MOVING_DIRECTION_LEFT_TO_RIGHT = 1;
    private static final int MOVING_DIRECTION_RIGHT_TO_LEFT = -1;
    private static final int PIXELS_PER_SECOND = 1000;
    private static final String TAG = "ZoomRatioToggleView";
    private static final int TIMEOUT_FOR_EXIT_ACTIVE_STATE = 2000;
    private static final int TOUCH_SCROLL_THRESHOLD = 10;
    private static final int TOUCH_STATE_CLICK = 1;
    private static final int TOUCH_STATE_HIDE_VIEW = 5;
    private static final int TOUCH_STATE_IDLE = 0;
    private static final int TOUCH_STATE_LONG_CLICK = 3;
    private static final int TOUCH_STATE_SCROLL = 2;
    private static final int TOUCH_STATE_SHOWVIEW = 4;
    private static final boolean UI_DEBUG_ENABLED = Log.isLoggable(TAG, 3);
    private static final int VELOCITY_THRESHOLD = 100;
    /* access modifiers changed from: private */
    public ToggleStateListener mActionListener;
    private int mCircleSize;
    private int mColor;
    /* access modifiers changed from: private */
    public int mCurrentModule;
    /* access modifiers changed from: private */
    public int mCurrentSelectedChildIndex;
    private AnimatorSet mFadeInAnimatorSet;
    /* access modifiers changed from: private */
    public AnimatorSet mFadeOutAnimatorSet;
    private final Runnable mInactiveTask;
    private final Runnable mIndexUpdater;
    /* access modifiers changed from: private */
    public boolean mIsImmersive;
    /* access modifiers changed from: private */
    public boolean mIsSuppressed;
    private int mItemHeight;
    private int mItemWidth;
    private Runnable mLongPressRunnable;
    private AnimatorSet mMovingAnimatorSet;
    private ZoomRatioView mOverlayView;
    private boolean mOverlayViewHasBeenAdded;
    private Paint mPaint;
    private Rect mRect;
    private float mScaleX;
    private float mScaleY;
    private SliderStateListener mSliderStateListener;
    private int mStrokeColor;
    private int mStrokeWidth;
    /* access modifiers changed from: private */
    public int mTouchStartX;
    /* access modifiers changed from: private */
    public int mTouchStartY;
    /* access modifiers changed from: private */
    public int mTouchState;
    private final Handler mUiHandler;
    private boolean mUseSliderAllowed;
    private VelocityTracker mVelocityTracker;
    private AnimatorSet mZoomInOutAnimatorSet;
    /* access modifiers changed from: private */
    public float mZoomRatio;

    public interface SliderStateListener {
        boolean isSliderViewVisible();

        boolean toHideSlideView();

        boolean toShowSlideView();
    }

    public interface ToggleStateListener {
        boolean isInteractive();

        void onClick(ZoomRatioView zoomRatioView);

        boolean onLongClick(ZoomRatioView zoomRatioView);

        boolean onTouch(ZoomRatioToggleView zoomRatioToggleView, MotionEvent motionEvent);
    }

    public static class ViewSpec {
        public final boolean immersive;
        public final boolean suppress;
        public final boolean useSlider;
        public final int visibility;

        public ViewSpec(int i, boolean z, boolean z2, boolean z3) {
            this.visibility = i;
            this.suppress = z;
            this.immersive = z2;
            this.useSlider = z3;
        }
    }

    public ZoomRatioToggleView(@NonNull Context context) {
        this(context, null);
    }

    public ZoomRatioToggleView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomRatioToggleView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ZoomRatioToggleView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTouchState = 0;
        this.mCurrentModule = 163;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mUseSliderAllowed = false;
        this.mIsImmersive = false;
        this.mIsSuppressed = false;
        this.mUiHandler = new Handler();
        this.mOverlayViewHasBeenAdded = false;
        this.mIndexUpdater = new Runnable() {
            /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass1 */

            public void run() {
                int opticalZoomRatioIndex = HybridZoomingSystem.getOpticalZoomRatioIndex(ZoomRatioToggleView.this.mCurrentModule, ZoomRatioToggleView.this.mZoomRatio);
                if (ZoomRatioToggleView.this.isAnimating()) {
                    ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "postpone index updater again: " + opticalZoomRatioIndex);
                    ZoomRatioToggleView.this.post(this);
                    return;
                }
                ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "Macro mode not change");
                ZoomRatioToggleView.this.setSelectedChildIndex(opticalZoomRatioIndex, false);
                ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "run index updater: " + opticalZoomRatioIndex);
            }
        };
        this.mInactiveTask = new Runnable() {
            /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass4 */

            public void run() {
                ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "mInactiveTask: run...");
                ArrayList arrayList = new ArrayList();
                int childCount = ZoomRatioToggleView.this.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ZoomRatioView zoomRatioView = (ZoomRatioView) ZoomRatioToggleView.this.getChildAt(i);
                    if (i != ZoomRatioToggleView.this.mCurrentSelectedChildIndex) {
                        arrayList.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, zoomRatioView.getAlpha(), 0.6f));
                    } else {
                        arrayList.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 0.0f));
                    }
                }
                ZoomRatioToggleView.this.mFadeOutAnimatorSet.removeAllListeners();
                ZoomRatioToggleView.this.mFadeOutAnimatorSet.cancel();
                ZoomRatioToggleView.this.mFadeOutAnimatorSet.end();
                ZoomRatioToggleView.this.mFadeOutAnimatorSet.playTogether(arrayList);
                ZoomRatioToggleView.this.mFadeOutAnimatorSet.start();
            }
        };
        init();
    }

    static /* synthetic */ void a(AnimatorSet animatorSet) {
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
            animatorSet.end();
        }
    }

    private void announceCurrentZoomRatioForAccessibility() {
        String valueOf = String.valueOf(HybridZoomingSystem.toDecimal(this.mZoomRatio));
        setContentDescription(getString(R.string.accessibility_focus_status, valueOf));
        announceForAccessibility(getString(R.string.accessibility_focus_status, valueOf));
    }

    private void clickChildAt(int i, int i2) {
        int containingChildIndex = getContainingChildIndex(i, i2);
        int i3 = -1;
        if (containingChildIndex != -1) {
            if (containingChildIndex == this.mCurrentSelectedChildIndex) {
                if (containingChildIndex != HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentModule)) {
                    this.mZoomInOutAnimatorSet.start();
                    if (!this.mIsImmersive && !this.mIsSuppressed) {
                        containingChildIndex = (HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentModule) == -1 || this.mCurrentSelectedChildIndex + 1 != getChildCount()) ? (this.mCurrentSelectedChildIndex + 1) % getChildCount() : (this.mCurrentSelectedChildIndex + 2) % getChildCount();
                    }
                } else {
                    return;
                }
            }
            if (!isLayoutRTL() ? containingChildIndex < this.mCurrentSelectedChildIndex : containingChildIndex > this.mCurrentSelectedChildIndex) {
                i3 = 1;
            }
            moveTo(containingChildIndex, i3, 1, false);
        }
    }

    /* access modifiers changed from: private */
    public static void debugUi(String str, String str2) {
        if (UI_DEBUG_ENABLED || b.hv || b.jv) {
            com.android.camera.log.Log.d(str, str2);
        }
    }

    private void endTouch(float f2) {
        if (this.mSliderStateListener != null && Math.abs(f2) >= 100.0f && !this.mIsImmersive && !this.mIsSuppressed && this.mUseSliderAllowed) {
            this.mSliderStateListener.toShowSlideView();
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        removeCallbacks(this.mLongPressRunnable);
        this.mTouchState = 0;
    }

    private void ensureViewOverlayHasBeenAdded() {
        if (!this.mOverlayViewHasBeenAdded) {
            getOverlay().add(this.mOverlayView);
            debugUi(TAG, "addOverlayView()");
            this.mOverlayViewHasBeenAdded = true;
        }
    }

    private void ensureViewOverlayHasBeenRemoved() {
        if (this.mOverlayViewHasBeenAdded) {
            getOverlay().remove(this.mOverlayView);
            debugUi(TAG, "removeOverlayView()");
            this.mOverlayViewHasBeenAdded = false;
        }
    }

    /* access modifiers changed from: private */
    public int getContainingChildIndex(int i, int i2) {
        if (this.mRect == null) {
            this.mRect = new Rect();
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).getHitRect(this.mRect);
            if (this.mRect.contains(i, i2)) {
                return i3;
            }
        }
        return -1;
    }

    private int getDimensionPixelSize(@DimenRes int i) {
        return getResources().getDimensionPixelSize(i);
    }

    private ZoomRatioView getOverlayView() {
        ensureViewOverlayHasBeenAdded();
        return this.mOverlayView;
    }

    private String getString(@StringRes int i, Object... objArr) {
        return getResources().getString(i, objArr);
    }

    private void init() {
        setWillNotDraw(false);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mCircleSize = getDimensionPixelSize(R.dimen.manually_indicator_layout_height) - (getDimensionPixelSize(R.dimen.manually_indicator_background_margin_top_bottom) * 2);
        this.mStrokeWidth = getDimensionPixelSize(R.dimen.zoom_button_background_stroke_width);
        this.mStrokeColor = getResources().getColor(R.color.zoom_button_background_stroke_color);
        this.mColor = getResources().getColor(R.color.zoom_button_background_color);
        this.mMovingAnimatorSet = new AnimatorSet();
        this.mMovingAnimatorSet.setDuration(260L);
        this.mMovingAnimatorSet.setInterpolator(new l());
        this.mFadeInAnimatorSet = new AnimatorSet();
        this.mFadeInAnimatorSet.setDuration(400L);
        this.mFadeInAnimatorSet.setInterpolator(new m());
        this.mFadeOutAnimatorSet = new AnimatorSet();
        this.mFadeOutAnimatorSet.setDuration(400L);
        this.mFadeOutAnimatorSet.setInterpolator(new m());
        this.mZoomInOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in_out);
        this.mZoomInOutAnimatorSet.setTarget(this);
        this.mZoomInOutAnimatorSet.setInterpolator(new w());
    }

    /* access modifiers changed from: private */
    public boolean isAnimating() {
        if (this.mMovingAnimatorSet.isRunning()) {
            debugUi(TAG, "isAnimating(): move");
            return true;
        } else if (this.mFadeInAnimatorSet.isRunning()) {
            debugUi(TAG, "isAnimating(): fadein");
            return true;
        } else if (!this.mFadeOutAnimatorSet.isRunning()) {
            return false;
        } else {
            debugUi(TAG, "isAnimating(): fadeout");
            return true;
        }
    }

    private boolean isLayoutRTL() {
        if (getContext() == null) {
            return false;
        }
        return getResources().getConfiguration().getLayoutDirection() == 1;
    }

    /* access modifiers changed from: private */
    public void longClickChild(int i) {
        View childAt;
        if (i == this.mCurrentSelectedChildIndex && this.mUseSliderAllowed) {
            if ((!this.mIsImmersive || this.mIsSuppressed) && i != HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentModule) && this.mActionListener != null && (childAt = getChildAt(i)) != null) {
                performHapticFeedback(0);
                this.mActionListener.onLongClick((ZoomRatioView) childAt);
            }
        }
    }

    private void moveTo(int i, int i2, final int i3, final boolean z) {
        ToggleStateListener toggleStateListener;
        debugUi(TAG, "move E: target = " + i + " V.S. current = " + this.mCurrentSelectedChildIndex);
        if (i != -1 && i != this.mCurrentSelectedChildIndex && !this.mIsSuppressed) {
            removeCallbacks(this.mIndexUpdater);
            int abs = Math.abs(i - this.mCurrentSelectedChildIndex);
            char c2 = 0;
            int i4 = (i3 == 1 || i3 == 2) ? 260 : 0;
            int i5 = abs * i2 * this.mItemWidth;
            ArrayList arrayList = new ArrayList();
            int childCount = getChildCount();
            int i6 = 0;
            while (i6 < childCount) {
                ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i6);
                float translationX = zoomRatioView.getTranslationX();
                if (i6 == i) {
                    arrayList.add(ObjectAnimator.ofFloat(zoomRatioView, View.ALPHA, 1.0f, 0.0f));
                    Property property = View.TRANSLATION_X;
                    float[] fArr = new float[2];
                    fArr[c2] = translationX;
                    fArr[1] = translationX + ((float) i5);
                    arrayList.add(ObjectAnimator.ofFloat(zoomRatioView, property, fArr));
                } else if (i6 == this.mCurrentSelectedChildIndex) {
                    arrayList.add(ObjectAnimator.ofFloat(zoomRatioView, View.ALPHA, 0.0f, 1.0f));
                    arrayList.add(ObjectAnimator.ofFloat(zoomRatioView, View.TRANSLATION_X, translationX, translationX + ((float) i5)));
                } else {
                    arrayList.add(ObjectAnimator.ofFloat(zoomRatioView, View.ALPHA, 1.0f, 1.0f));
                    arrayList.add(ObjectAnimator.ofFloat(zoomRatioView, View.TRANSLATION_X, translationX, translationX + ((float) i5)));
                }
                i6++;
                c2 = 0;
            }
            final ZoomRatioView zoomRatioView2 = (ZoomRatioView) getChildAt(this.mCurrentSelectedChildIndex);
            final ZoomRatioView zoomRatioView3 = (ZoomRatioView) getChildAt(i);
            this.mCurrentSelectedChildIndex = i;
            this.mMovingAnimatorSet.setDuration((long) i4);
            this.mMovingAnimatorSet.removeAllListeners();
            this.mMovingAnimatorSet.cancel();
            this.mMovingAnimatorSet.end();
            this.mMovingAnimatorSet.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass3 */

                public void onAnimationEnd(Animator animator) {
                    ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "onAnimationEnd()");
                    ZoomRatioView zoomRatioView = zoomRatioView2;
                    if (zoomRatioView != null) {
                        zoomRatioView2.setZoomRatio(HybridZoomingSystem.getOpticalZoomRatioAt(ZoomRatioToggleView.this.mCurrentModule, zoomRatioView.getZoomRatioIndex()));
                    }
                    if (z && !ZoomRatioToggleView.this.mIsImmersive && !ZoomRatioToggleView.this.mIsSuppressed && ZoomRatioToggleView.this.isEnabled()) {
                        ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "onAnimationEnd(): startFadeInAnimation");
                        ZoomRatioToggleView.this.startFadeInAnimation();
                    }
                }

                public void onAnimationStart(Animator animator) {
                    ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "onAnimationStart()");
                    ZoomRatioView zoomRatioView = zoomRatioView3;
                    if (zoomRatioView != null) {
                        int zoomRatioIndex = zoomRatioView.getZoomRatioIndex();
                        int i = i3;
                        if (i == 1) {
                            float opticalZoomRatioAt = HybridZoomingSystem.getOpticalZoomRatioAt(ZoomRatioToggleView.this.mCurrentModule, zoomRatioIndex);
                            ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "onAnimationStart(): click, set zoom ratio to " + opticalZoomRatioAt);
                            zoomRatioView3.setZoomRatio(opticalZoomRatioAt);
                        } else if (i != 2) {
                            ZoomRatioToggleView.debugUi(ZoomRatioToggleView.TAG, "onAnimationStart(): others, set zoom ratio to " + ZoomRatioToggleView.this.mZoomRatio);
                            zoomRatioView3.setZoomRatio(ZoomRatioToggleView.this.mZoomRatio);
                        }
                        int i2 = i3;
                        if ((i2 == 1 || i2 == 2) && ZoomRatioToggleView.this.mActionListener != null) {
                            ZoomRatioToggleView.this.mActionListener.onClick(zoomRatioView3);
                        }
                    }
                }
            });
            this.mMovingAnimatorSet.playTogether(arrayList);
            this.mMovingAnimatorSet.start();
            debugUi(TAG, "start moving to: " + i);
        } else if (i == this.mCurrentSelectedChildIndex) {
            if (z && !this.mIsImmersive && !this.mIsSuppressed && isEnabled()) {
                debugUi(TAG, "same index only run startFadeInAnimation");
                startFadeInAnimation();
            }
            ZoomRatioView zoomRatioView4 = (ZoomRatioView) getChildAt(i);
            if (zoomRatioView4 != null) {
                zoomRatioView4.setZoomRatio(this.mZoomRatio);
                if ((i3 == 1 || i3 == 2) && (toggleStateListener = this.mActionListener) != null) {
                    toggleStateListener.onClick(zoomRatioView4);
                }
            }
        } else {
            debugUi(TAG, "moving ignored: " + i);
        }
        announceCurrentZoomRatioForAccessibility();
        debugUi(TAG, "move X: " + i);
    }

    private void resetAnimators() {
        debugUi(TAG, "resetAnimators");
        this.mScaleY = 1.0f;
        this.mScaleX = 1.0f;
        Arrays.asList(this.mMovingAnimatorSet, this.mFadeOutAnimatorSet, this.mFadeInAnimatorSet, this.mZoomInOutAnimatorSet).forEach(a.INSTANCE);
    }

    /* access modifiers changed from: private */
    public void setSelectedChildIndex(int i, boolean z) {
        int i2 = 1;
        if (!isLayoutRTL() ? i >= this.mCurrentSelectedChildIndex : i <= this.mCurrentSelectedChildIndex) {
            i2 = -1;
        }
        moveTo(i, i2, 0, z);
    }

    /* access modifiers changed from: private */
    public void startFadeInAnimation() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i);
            if (i != this.mCurrentSelectedChildIndex) {
                arrayList.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 1.0f));
            } else {
                arrayList.add(ObjectAnimator.ofFloat(zoomRatioView.getIconView(), View.ALPHA, 0.0f, 0.0f));
            }
        }
        this.mFadeInAnimatorSet.removeAllListeners();
        this.mFadeInAnimatorSet.cancel();
        this.mFadeInAnimatorSet.end();
        this.mFadeInAnimatorSet.playTogether(arrayList);
        this.mFadeInAnimatorSet.start();
    }

    private void startLongPressCheck() {
        if (this.mLongPressRunnable == null) {
            this.mLongPressRunnable = new Runnable() {
                /* class com.android.camera.ui.zoom.ZoomRatioToggleView.AnonymousClass2 */

                public void run() {
                    if (ZoomRatioToggleView.this.mTouchState == 1) {
                        ZoomRatioToggleView zoomRatioToggleView = ZoomRatioToggleView.this;
                        int access$800 = zoomRatioToggleView.getContainingChildIndex(zoomRatioToggleView.mTouchStartX, ZoomRatioToggleView.this.mTouchStartY);
                        if (access$800 != -1) {
                            ZoomRatioToggleView.this.longClickChild(access$800);
                        }
                        int unused = ZoomRatioToggleView.this.mTouchState = 3;
                    }
                }
            };
        }
        postDelayed(this.mLongPressRunnable, (long) ViewConfiguration.getLongPressTimeout());
    }

    private boolean startScrollIfNeeded(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int i = this.mTouchStartX;
        if (x >= i - 10 && x <= i + 10) {
            int i2 = this.mTouchStartY;
            if (y >= i2 - 10 && y <= i2 + 10) {
                return false;
            }
        }
        removeCallbacks(this.mLongPressRunnable);
        this.mTouchState = 2;
        return true;
    }

    private void startTouch(MotionEvent motionEvent) {
        this.mTouchStartX = (int) motionEvent.getX();
        this.mTouchStartY = (int) motionEvent.getY();
        startLongPressCheck();
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mVelocityTracker.addMovement(motionEvent);
        this.mTouchState = 1;
    }

    private void toHideView() {
        SliderStateListener sliderStateListener;
        if (this.mTouchState != 4 && (sliderStateListener = this.mSliderStateListener) != null && sliderStateListener.isSliderViewVisible() && this.mUseSliderAllowed) {
            this.mSliderStateListener.toHideSlideView();
            removeCallbacks(this.mLongPressRunnable);
            this.mTouchState = 5;
        }
    }

    private void toShowView(float f2) {
        SliderStateListener sliderStateListener;
        if (Math.abs(f2) >= 100.0f && this.mUseSliderAllowed && getContainingChildIndex(this.mTouchStartX, this.mTouchStartY) != -1 && (sliderStateListener = this.mSliderStateListener) != null) {
            sliderStateListener.toShowSlideView();
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        removeCallbacks(this.mLongPressRunnable);
        this.mTouchState = 4;
    }

    public boolean isImmersive() {
        return this.mIsImmersive;
    }

    public boolean isSuppressed() {
        return this.mIsSuppressed;
    }

    public void onClick(View view) {
        if (getVisibility() == 0) {
            debugUi(TAG, "UI AUTOMATIC TEST: CLICKED");
            clickChildAt(getWidth() / 2, getHeight() / 2);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int width = (getWidth() / 2) - (this.mCircleSize / 2);
        int height = (getHeight() / 2) - (this.mCircleSize / 2);
        int width2 = (getWidth() / 2) + (this.mCircleSize / 2);
        int height2 = (getHeight() / 2) + (this.mCircleSize / 2);
        int save = canvas.save();
        canvas.scale(this.mScaleX, this.mScaleY, ((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setStyle(Paint.Style.FILL);
        float f2 = (float) width;
        float f3 = (float) height;
        float f4 = (float) width2;
        float f5 = (float) height2;
        canvas.drawOval(f2, f3, f4, f5, this.mPaint);
        this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
        this.mPaint.setColor(this.mStrokeColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(f2, f3, f4, f5, this.mPaint);
        canvas.restoreToCount(save);
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCurrentSelectedChildIndex = HybridZoomingSystem.getDefaultOpticalZoomRatioIndex(this.mCurrentModule);
        this.mZoomRatio = HybridZoomingSystem.getOpticalZoomRatioAt(this.mCurrentModule, this.mCurrentSelectedChildIndex);
        int length = HybridZoomingSystem.getSupportedOpticalZoomRatios(this.mCurrentModule).length;
        if (length > 0) {
            this.mOverlayView = (ZoomRatioView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_ratio_item_view, (ViewGroup) null);
            this.mOverlayView.getTextView().setVisibility(0);
            this.mOverlayView.setIconify(false);
            int i = this.mCurrentSelectedChildIndex;
            if (length != getChildCount()) {
                removeAllViews();
                int i2 = 0;
                while (i2 < length) {
                    ZoomRatioView zoomRatioView = (ZoomRatioView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_ratio_item_view, (ViewGroup) null);
                    zoomRatioView.getIconView().setVisibility(0);
                    zoomRatioView.setZoomRatioIcon(HybridZoomingSystem.getOpticalZoomRatioAt(this.mCurrentModule, i2));
                    zoomRatioView.getTextView().setVisibility(0);
                    zoomRatioView.setZoomRatio(HybridZoomingSystem.getOpticalZoomRatioAt(this.mCurrentModule, i2));
                    zoomRatioView.setZoomRatioIndex(i2);
                    zoomRatioView.setIconify(i2 != i);
                    addView(zoomRatioView, new ViewGroup.LayoutParams(-2, -2));
                    i2++;
                }
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            debugUi(TAG, "onInterceptTouchEvent() DOWN: " + this.mIsImmersive);
            if ((this.mIsImmersive || this.mIsSuppressed) && getContainingChildIndex((int) motionEvent.getX(), (int) motionEvent.getY()) != this.mCurrentSelectedChildIndex) {
                return false;
            }
            this.mUiHandler.removeCallbacks(this.mInactiveTask);
            if (isAnimating() || !isEnabled()) {
                return false;
            }
            ToggleStateListener toggleStateListener = this.mActionListener;
            if (toggleStateListener != null && !toggleStateListener.isInteractive()) {
                return false;
            }
            startTouch(motionEvent);
            return false;
        } else if (action != 2) {
            endTouch(0.0f);
            return false;
        } else {
            debugUi(TAG, "onInterceptTouchEvent() MOVE: " + this.mIsImmersive);
            return startScrollIfNeeded(motionEvent);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int childCount = getChildCount();
        if (childCount > 0) {
            int defaultOpticalZoomRatioIndex = HybridZoomingSystem.getDefaultOpticalZoomRatioIndex(this.mCurrentModule);
            boolean isLayoutRTL = isLayoutRTL();
            if (!isLayoutRTL) {
                int i6 = this.mItemWidth;
                i5 = ((getWidth() / 2) - (i6 / 2)) - (defaultOpticalZoomRatioIndex * i6);
            } else {
                int i7 = this.mItemWidth;
                i5 = ((getWidth() / 2) - (i7 / 2)) + (defaultOpticalZoomRatioIndex * i7);
            }
            int height = (getHeight() / 2) - (this.mItemHeight / 2);
            int i8 = i5;
            for (int i9 = 0; i9 < childCount; i9++) {
                getChildAt(i9).layout(i8, height, this.mItemWidth + i8, this.mItemHeight + height);
                i8 = !isLayoutRTL ? i8 + this.mItemWidth : i8 - this.mItemWidth;
            }
            int width = (getWidth() / 2) - (this.mItemWidth / 2);
            int height2 = (getHeight() / 2) - (this.mItemHeight / 2);
            int width2 = (getWidth() / 2) + (this.mItemWidth / 2);
            int height3 = (getHeight() / 2) + (this.mItemHeight / 2);
            this.mOverlayView.measure(View.MeasureSpec.makeMeasureSpec(this.mItemWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mItemHeight, 1073741824));
            this.mOverlayView.getTextView().measure(View.MeasureSpec.makeMeasureSpec(this.mItemWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mItemHeight, 1073741824));
            this.mOverlayView.layout(width, height2, width2, height3);
            this.mOverlayView.getTextView().layout(0, 0, this.mItemWidth, this.mItemHeight);
        }
    }

    public boolean onLongClick(View view) {
        if (getVisibility() != 0) {
            return false;
        }
        debugUi(TAG, "UI AUTOMATIC TEST: LONGCLICKED");
        longClickChild(this.mCurrentSelectedChildIndex);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                i4 += childAt.getMeasuredWidth();
                i5 = Math.max(i5, childAt.getMeasuredHeight());
                i3++;
            }
        }
        this.mItemWidth = i3 == 0 ? 0 : i4 / i3;
        this.mItemHeight = i5;
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(i4 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, 0), ViewGroup.resolveSizeAndState(Math.max(i5 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, 0));
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 4 || eventType == 32768) {
            accessibilityEvent.getText().add(getString(R.string.accessibility_focus_status, Float.valueOf(this.mZoomRatio)));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ToggleStateListener toggleStateListener;
        ToggleStateListener toggleStateListener2;
        if (getChildCount() == 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            float f2 = 0.0f;
            if (action != 1) {
                if (action == 2) {
                    debugUi(TAG, "onTouchEvent() MOVE: " + this.mIsImmersive);
                    if (this.mTouchState == 1 && startScrollIfNeeded(motionEvent)) {
                        toHideView();
                    }
                    if (this.mTouchState == 3) {
                        startScrollIfNeeded(motionEvent);
                    }
                    if ((!this.mUseSliderAllowed || this.mTouchState != 4 || (toggleStateListener2 = this.mActionListener) == null || !toggleStateListener2.isInteractive() || !this.mActionListener.onTouch(this, motionEvent)) && isEnabled() && this.mTouchState == 2) {
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mVelocityTracker.computeCurrentVelocity(1000);
                        toShowView(this.mVelocityTracker.getXVelocity());
                    }
                } else if (action != 3) {
                    endTouch(0.0f);
                }
            }
            debugUi(TAG, "onTouchEvent() UP: " + this.mIsImmersive);
            ToggleStateListener toggleStateListener3 = this.mActionListener;
            if (toggleStateListener3 != null && toggleStateListener3.isInteractive() && this.mTouchState == 1) {
                clickChildAt((int) motionEvent.getX(), (int) motionEvent.getY());
            }
            if (!this.mUseSliderAllowed || this.mTouchState != 4 || (toggleStateListener = this.mActionListener) == null || !toggleStateListener.isInteractive() || !this.mActionListener.onTouch(this, motionEvent)) {
                if (this.mTouchState == 2) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    f2 = this.mVelocityTracker.getXVelocity();
                }
                endTouch(f2);
            } else {
                this.mTouchState = 0;
                return true;
            }
        } else {
            debugUi(TAG, "onTouchEvent() DOWN: " + this.mIsImmersive);
            if ((this.mIsImmersive || this.mIsSuppressed) && getContainingChildIndex((int) motionEvent.getX(), (int) motionEvent.getY()) != this.mCurrentSelectedChildIndex) {
                return false;
            }
            this.mUiHandler.removeCallbacks(this.mInactiveTask);
            if (isAnimating() || !isEnabled()) {
                return false;
            }
            ToggleStateListener toggleStateListener4 = this.mActionListener;
            if (toggleStateListener4 != null && !toggleStateListener4.isInteractive()) {
                return false;
            }
            debugUi(TAG, "onTouchEvent() start inactive timer");
            startInactiveTimer();
            startTouch(motionEvent);
        }
        return true;
    }

    public void setActionListener(ToggleStateListener toggleStateListener) {
        this.mActionListener = toggleStateListener;
    }

    public void setCaptureCount(int i) {
        if (this.mIsImmersive || this.mIsSuppressed) {
            getOverlayView().setCaptureCount(i);
            return;
        }
        ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(this.mCurrentSelectedChildIndex);
        if (zoomRatioView != null) {
            zoomRatioView.setCaptureCount(i);
        }
    }

    public boolean setCapturingMode(int i) {
        this.mCurrentModule = i;
        float[] supportedOpticalZoomRatios = HybridZoomingSystem.getSupportedOpticalZoomRatios(this.mCurrentModule);
        int length = supportedOpticalZoomRatios.length;
        if (length <= 0 || length == getChildCount()) {
            return false;
        }
        int defaultOpticalZoomRatioIndex = HybridZoomingSystem.getDefaultOpticalZoomRatioIndex(this.mCurrentModule);
        resetAnimators();
        removeAllViews();
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 < length) {
                ZoomRatioView zoomRatioView = (ZoomRatioView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_ratio_item_view, (ViewGroup) null);
                zoomRatioView.getIconView().setVisibility(0);
                zoomRatioView.setZoomRatioIcon(supportedOpticalZoomRatios[i2]);
                zoomRatioView.getTextView().setVisibility(0);
                zoomRatioView.setZoomRatio(supportedOpticalZoomRatios[i2]);
                zoomRatioView.setZoomRatioIndex(i2);
                if (i2 == defaultOpticalZoomRatioIndex) {
                    z = false;
                }
                zoomRatioView.setIconify(z);
                addView(zoomRatioView, new ViewGroup.LayoutParams(-2, -2));
                i2++;
            } else {
                this.mCurrentSelectedChildIndex = defaultOpticalZoomRatioIndex;
                this.mZoomRatio = supportedOpticalZoomRatios[defaultOpticalZoomRatioIndex];
                return true;
            }
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        debugUi(TAG, "setEnabled(): " + z);
    }

    public void setImmersive(boolean z, boolean z2) {
        debugUi(TAG, "setImmersive(): " + z);
        if (z != this.mIsImmersive || z2) {
            this.mIsImmersive = z;
            if (this.mIsImmersive) {
                getOverlayView().setZoomRatio(this.mZoomRatio);
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i);
                    if (zoomRatioView != null) {
                        zoomRatioView.setVisibility(4);
                    }
                }
            } else if (!this.mIsSuppressed) {
                ensureViewOverlayHasBeenRemoved();
                int childCount2 = getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    ((ZoomRatioView) getChildAt(i2)).setVisibility(0);
                }
                setSelectedChildIndex(HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, this.mZoomRatio), true);
                if (isEnabled()) {
                    debugUi(TAG, "setImmersive() start inactive timer");
                    startInactiveTimer();
                }
            }
        } else {
            debugUi(TAG, "setImmersive() ignored: " + z);
        }
    }

    public void setRotation(float f2) {
        ZoomRatioView zoomRatioView = this.mOverlayView;
        if (zoomRatioView != null) {
            zoomRatioView.setRotation(f2);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                childAt.setRotation(f2);
            }
        }
    }

    public void setScaleX(float f2) {
        this.mScaleX = f2;
        ZoomRatioView zoomRatioView = this.mOverlayView;
        if (zoomRatioView != null && this.mOverlayViewHasBeenAdded) {
            zoomRatioView.setScaleX(f2);
        }
        invalidate();
    }

    public void setScaleY(float f2) {
        this.mScaleY = f2;
        ZoomRatioView zoomRatioView = this.mOverlayView;
        if (zoomRatioView != null && this.mOverlayViewHasBeenAdded) {
            zoomRatioView.setScaleY(f2);
        }
        invalidate();
    }

    public void setSlideStateListener(SliderStateListener sliderStateListener) {
        this.mSliderStateListener = sliderStateListener;
    }

    public void setSuppressed(boolean z, boolean z2) {
        debugUi(TAG, "setSuppressed(): " + z);
        if (z != this.mIsSuppressed || z2) {
            this.mIsSuppressed = z;
            if (z) {
                this.mIsImmersive = true;
            }
            if (this.mIsSuppressed) {
                getOverlayView().setZoomRatio(this.mZoomRatio);
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    ((ZoomRatioView) getChildAt(i)).setVisibility(4);
                }
            } else if (!this.mIsImmersive) {
                ensureViewOverlayHasBeenRemoved();
                int opticalZoomRatioIndex = HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, this.mZoomRatio);
                int childCount2 = getChildCount();
                for (int i2 = 0; i2 < childCount2; i2++) {
                    ZoomRatioView zoomRatioView = (ZoomRatioView) getChildAt(i2);
                    if (i2 == opticalZoomRatioIndex) {
                        zoomRatioView.setZoomRatio(this.mZoomRatio);
                    }
                    zoomRatioView.setVisibility(0);
                }
            }
        } else {
            debugUi(TAG, "setSuppressed() ignored: " + z);
        }
    }

    public void setUseSliderAllowed(boolean z) {
        this.mUseSliderAllowed = z;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        debugUi(TAG, "setVisibility(): " + Util.viewVisibilityToString(i));
        if (i != 0) {
            resetAnimators();
        }
    }

    public void setZoomRatio(float f2, int i) {
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            int macroZoomRatioIndex = f2 == 0.0f ? HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentModule) : HybridZoomingSystem.getOpticalZoomRatioIndex(this.mCurrentModule, f2);
            debugUi(TAG, "setZoomRatio(): zooming action = " + ZoomingAction.toString(i));
            debugUi(TAG, "setZoomRatio():  current index = " + this.mCurrentSelectedChildIndex);
            debugUi(TAG, "setZoomRatio():   current zoom = " + this.mZoomRatio);
            debugUi(TAG, "setZoomRatio():   target index = " + macroZoomRatioIndex);
            debugUi(TAG, "setZoomRatio():    target zoom = " + f2);
            this.mZoomRatio = f2;
            if (this.mIsSuppressed) {
                getOverlayView().setZoomRatio(this.mZoomRatio);
                announceCurrentZoomRatioForAccessibility();
                debugUi(TAG, "setZoomRatio(): mIsSuppressed");
            } else if (this.mIsImmersive) {
                getOverlayView().setZoomRatio(this.mZoomRatio);
                announceCurrentZoomRatioForAccessibility();
                debugUi(TAG, "setZoomRatio(): mIsImmersive");
            } else if (i == 0) {
                debugUi(TAG, "setZoomRatio(): ignored as source is toggle button");
            } else {
                boolean z = UI_DEBUG_ENABLED;
                this.mUiHandler.removeCallbacks(this.mInactiveTask);
                removeCallbacks(this.mIndexUpdater);
                post(this.mIndexUpdater);
            }
        } else {
            throw new IllegalStateException("setZoomRatio() must be called on main ui thread.");
        }
    }

    public void startInactiveTimer() {
        this.mUiHandler.removeCallbacks(this.mInactiveTask);
        if (!this.mIsImmersive && !this.mIsSuppressed && !this.mFadeOutAnimatorSet.isRunning()) {
            this.mUiHandler.postDelayed(this.mInactiveTask, 2000);
        }
    }
}
