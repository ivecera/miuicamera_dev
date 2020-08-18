package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import d.h.a.y;

public class ToggleSwitch extends CompoundButton implements Checkable {
    private static final int DEFAULT_ANIMATION_DURATION = 300;
    private static final int DEFAULT_HEIGHT = 28;
    private static final float DEFAULT_SHADOW_RADIUS = 1.33f;
    private static final int DEFAULT_STROKE_WIDTH = 1;
    private static final int DEFAULT_SWITCH_BACKGROUND_COLOR = -2013265920;
    private static final int DEFAULT_SWITCH_STROKE_COLOR = -1275068417;
    private static final int DEFAULT_TEXT_OFF_COLOR = -1275068417;
    private static final int DEFAULT_TEXT_OFF_SHADOW_COLOR = -1090519040;
    private static final int DEFAULT_TEXT_ON_COLOR = -1;
    private static final int DEFAULT_TEXT_SIZE = 48;
    private static final int DEFAULT_THUMB_COLOR = -13391105;
    private static final int DEFAULT_THUMB_PADDING = 2;
    private static final int DEFAULT_THUMB_SHADOW_COLOR = 1291845632;
    private static final int DEFAULT_WIDTH = 54;
    private static final int SWITCH_OFF_POS = 0;
    private static final int SWITCH_ON_POS = 1;
    private static final String TAG = "ToggleSwitch";
    /* access modifiers changed from: private */
    public boolean isMoving;
    private int mAnimDuration;
    private boolean mBroadcasting;
    private boolean mChecked;
    /* access modifiers changed from: private */
    public float mCurrentPos;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private int mStrokeWidth;
    private int mSwitchBackgroundColor;
    private int mSwitchBackgroundStrokeColor;
    private String mTextOff;
    private int mTextOffColor;
    private int mTextOffShadowColor;
    private float mTextOffShadowRadius;
    private int mTextOffWidth;
    private String mTextOn;
    private int mTextOnColor;
    private int mTextOnWidth;
    private int mTextSize;
    private int mThumbColor;
    private int mThumbPadding;
    private int mThumbShadowColor;
    private float mThumbShadowRadius;
    private ValueAnimator mValueAnimator;
    private State state;

    /* renamed from: com.android.camera.ui.ToggleSwitch$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$android$camera$ui$ToggleSwitch$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$com$android$camera$ui$ToggleSwitch$State[State.SWITCH_ON.ordinal()] = 1;
            $SwitchMap$com$android$camera$ui$ToggleSwitch$State[State.SWITCH_OFF.ordinal()] = 2;
            $SwitchMap$com$android$camera$ui$ToggleSwitch$State[State.SWITCHING_ANIM_ON.ordinal()] = 3;
            try {
                $SwitchMap$com$android$camera$ui$ToggleSwitch$State[State.SWITCHING_ANIM_OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(ToggleSwitch toggleSwitch, boolean z);
    }

    private enum State {
        SWITCHING_ANIM_OFF,
        SWITCHING_ANIM_ON,
        SWITCH_ON,
        SWITCH_OFF
    }

    public ToggleSwitch(Context context) {
        super(context);
        this.mTextOn = "ON";
        this.mTextOff = "OFF";
        this.mSwitchBackgroundColor = DEFAULT_SWITCH_BACKGROUND_COLOR;
        this.mSwitchBackgroundStrokeColor = ColorConstant.COLOR_COMMON_NORMAL;
        this.mStrokeWidth = dp2px(1.0f);
        this.mThumbColor = DEFAULT_THUMB_COLOR;
        this.mThumbShadowColor = DEFAULT_THUMB_SHADOW_COLOR;
        this.mThumbShadowRadius = dp2pxFloat(DEFAULT_SHADOW_RADIUS);
        this.mTextOffShadowColor = DEFAULT_TEXT_OFF_SHADOW_COLOR;
        this.mTextOffShadowRadius = dp2pxFloat(DEFAULT_SHADOW_RADIUS);
        this.mThumbPadding = dp2px(2.0f);
        this.mAnimDuration = 300;
        this.state = this.mChecked ? State.SWITCH_ON : State.SWITCH_OFF;
        setClickable(true);
    }

    public ToggleSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextOn = "ON";
        this.mTextOff = "OFF";
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ToggleSwitch);
        this.mSwitchBackgroundColor = obtainStyledAttributes.getColor(0, DEFAULT_SWITCH_BACKGROUND_COLOR);
        this.mSwitchBackgroundStrokeColor = obtainStyledAttributes.getColor(1, ColorConstant.COLOR_COMMON_NORMAL);
        this.mStrokeWidth = obtainStyledAttributes.getDimensionPixelSize(2, dp2px(1.0f));
        this.mThumbColor = obtainStyledAttributes.getColor(12, DEFAULT_THUMB_COLOR);
        this.mThumbShadowColor = obtainStyledAttributes.getColor(14, DEFAULT_THUMB_SHADOW_COLOR);
        this.mThumbShadowRadius = obtainStyledAttributes.getDimension(15, dp2pxFloat(DEFAULT_SHADOW_RADIUS));
        this.mThumbPadding = obtainStyledAttributes.getDimensionPixelSize(13, dp2px(2.0f));
        this.mTextOn = (String) obtainStyledAttributes.getText(9);
        this.mTextOff = (String) obtainStyledAttributes.getText(5);
        this.mTextOnColor = obtainStyledAttributes.getInteger(10, -1);
        this.mTextOffColor = obtainStyledAttributes.getInteger(6, ColorConstant.COLOR_COMMON_NORMAL);
        this.mTextOffShadowColor = obtainStyledAttributes.getColor(7, DEFAULT_TEXT_OFF_SHADOW_COLOR);
        this.mTextOffShadowRadius = obtainStyledAttributes.getDimension(8, dp2pxFloat(DEFAULT_SHADOW_RADIUS));
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(11, 48);
        this.mAnimDuration = obtainStyledAttributes.getInteger(4, 300);
        this.mChecked = obtainStyledAttributes.getBoolean(3, false);
        obtainStyledAttributes.recycle();
        caculateTextWidth();
        this.state = this.mChecked ? State.SWITCH_ON : State.SWITCH_OFF;
        setClickable(true);
    }

    private void animateToCheckedState() {
        this.mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mValueAnimator.setDuration((long) this.mAnimDuration);
        this.mValueAnimator.setInterpolator(new y());
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.ToggleSwitch.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float unused = ToggleSwitch.this.mCurrentPos = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ToggleSwitch.this.invalidate();
            }
        });
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.ui.ToggleSwitch.AnonymousClass2 */

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                boolean unused = ToggleSwitch.this.isMoving = false;
            }

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                boolean unused = ToggleSwitch.this.isMoving = true;
            }
        });
        if (!this.mValueAnimator.isRunning()) {
            this.mValueAnimator.start();
            this.mCurrentPos = 0.0f;
        }
    }

    private void caculateTextWidth() {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        Paint paint = new Paint();
        paint.setTextSize((float) this.mTextSize);
        String str = this.mTextOn;
        paint.getTextBounds(str, 0, str.length(), rect);
        String str2 = this.mTextOff;
        paint.getTextBounds(str2, 0, str2.length(), rect2);
        this.mTextOnWidth = rect.width();
        this.mTextOffWidth = rect2.width();
    }

    private void cancelPositionAnimator() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    private float[] compBackgroundRoundRectAttr() {
        int width = getWidth();
        float height = ((float) getHeight()) - 0.0f;
        return new float[]{0.0f, 0.0f, ((float) width) - 0.0f, height, (height - 0.0f) * 0.5f};
    }

    private float[] compThumbRoundRectAttr(float f2) {
        int width = getWidth();
        int height = getHeight();
        int i = this.mThumbPadding;
        int i2 = this.mStrokeWidth;
        float f3 = ((float) width) / 2.0f;
        float f4 = (((float) (i + i2)) * (1.0f - f2)) + (((((float) i) / 2.0f) + f3) * f2);
        float f5 = (float) (i + i2);
        float f6 = (((float) (height - (i * 2))) + f5) - ((float) (i2 * 2));
        return new float[]{f4, f5, ((f3 - ((float) i2)) - ((((float) i) * 3.0f) / 2.0f)) + f4, f6, (f6 - f5) * 0.5f};
    }

    private void drawRoundRect(Canvas canvas, int i, float[] fArr, boolean z) {
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        RectF rectF = new RectF();
        paint.setColor(i);
        if (z) {
            paint.setShadowLayer(this.mThumbShadowRadius, 0.0f, 0.0f, this.mThumbShadowColor);
        }
        rectF.set(fArr[0], fArr[1], fArr[2], fArr[3]);
        canvas.drawRoundRect(rectF, fArr[4], fArr[4], paint);
    }

    private void drawRoundRectStroke(Canvas canvas, int i) {
        if (this.mStrokeWidth > 0) {
            int width = getWidth();
            int height = getHeight();
            int i2 = this.mStrokeWidth;
            float f2 = ((float) i2) / 2.0f;
            float f3 = ((float) i2) / 2.0f;
            float f4 = ((float) height) - f3;
            float f5 = (f4 - f3) * 0.5f;
            Paint paint = new Paint(1);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(i);
            paint.setStrokeWidth((float) this.mStrokeWidth);
            RectF rectF = new RectF();
            rectF.set(f2, f3, ((float) width) - f2, f4);
            canvas.drawRoundRect(rectF, f5, f5, paint);
        }
    }

    private void drawSwitchOff(Canvas canvas) {
        drawRoundRect(canvas, this.mSwitchBackgroundColor, compBackgroundRoundRectAttr(), false);
        float[] compThumbRoundRectAttr = compThumbRoundRectAttr(0.0f);
        drawRoundRect(canvas, this.mThumbColor, compThumbRoundRectAttr, true);
        drawRoundRectStroke(canvas, this.mSwitchBackgroundStrokeColor);
        drawText(canvas, compThumbRoundRectAttr);
    }

    private void drawSwitchOffAnim(Canvas canvas) {
        drawRoundRect(canvas, this.mSwitchBackgroundColor, compBackgroundRoundRectAttr(), false);
        drawRoundRectStroke(canvas, this.mSwitchBackgroundStrokeColor);
        float[] compThumbRoundRectAttr = compThumbRoundRectAttr(1.0f - this.mCurrentPos);
        drawRoundRect(canvas, this.mThumbColor, compThumbRoundRectAttr, true);
        drawText(canvas, compThumbRoundRectAttr);
    }

    private void drawSwitchOn(Canvas canvas) {
        drawRoundRect(canvas, this.mSwitchBackgroundColor, compBackgroundRoundRectAttr(), false);
        float[] compThumbRoundRectAttr = compThumbRoundRectAttr(1.0f);
        drawRoundRect(canvas, this.mThumbColor, compThumbRoundRectAttr, true);
        drawRoundRectStroke(canvas, this.mSwitchBackgroundStrokeColor);
        drawText(canvas, compThumbRoundRectAttr);
    }

    private void drawSwitchOnAnim(Canvas canvas) {
        drawRoundRect(canvas, this.mSwitchBackgroundColor, compBackgroundRoundRectAttr(), false);
        drawRoundRectStroke(canvas, this.mSwitchBackgroundStrokeColor);
        float[] compThumbRoundRectAttr = compThumbRoundRectAttr(this.mCurrentPos);
        drawRoundRect(canvas, this.mThumbColor, compThumbRoundRectAttr, true);
        drawText(canvas, compThumbRoundRectAttr);
    }

    private void drawText(Canvas canvas, float[] fArr) {
        if (this.mTextOff != null) {
            Paint paint = new Paint(1);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize((float) this.mTextSize);
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int i = fontMetricsInt.top;
            int measuredHeight = (((getMeasuredHeight() - fontMetricsInt.bottom) + i) / 2) - i;
            if (this.mChecked) {
                paint.setColor(this.mTextOffColor);
                paint.setShadowLayer(this.mTextOffShadowRadius, 0.0f, 0.0f, this.mTextOffShadowColor);
            } else {
                paint.setColor(this.mTextOnColor);
            }
            canvas.drawText(this.mTextOff, ((((((float) getWidth()) * 0.5f) + (((float) this.mThumbPadding) / 2.0f)) + ((float) this.mStrokeWidth)) - ((float) this.mTextOffWidth)) * 0.5f, (float) measuredHeight, paint);
        }
        if (this.mTextOn != null) {
            Paint paint2 = new Paint(1);
            paint2.setTextAlign(Paint.Align.LEFT);
            paint2.setTextSize((float) this.mTextSize);
            Paint.FontMetricsInt fontMetricsInt2 = paint2.getFontMetricsInt();
            int i2 = fontMetricsInt2.top;
            int measuredHeight2 = (((getMeasuredHeight() - fontMetricsInt2.bottom) + i2) / 2) - i2;
            if (this.mChecked) {
                paint2.setColor(this.mTextOnColor);
            } else {
                paint2.setColor(this.mTextOffColor);
                paint2.setShadowLayer(this.mTextOffShadowRadius, 0.0f, 0.0f, this.mTextOffShadowColor);
            }
            canvas.drawText(this.mTextOn, (((((((float) getWidth()) * 3.0f) / 2.0f) - (((float) this.mThumbPadding) / 2.0f)) - ((float) this.mStrokeWidth)) - ((float) this.mTextOnWidth)) * 0.5f, (float) measuredHeight2, paint2);
        }
    }

    public int dp2px(float f2) {
        return (int) ((f2 * getResources().getDisplayMetrics().density) + 0.5f);
    }

    public float dp2pxFloat(float f2) {
        return (f2 * getResources().getDisplayMetrics().density) + 0.5f;
    }

    public String getTextOff() {
        return this.mTextOff;
    }

    public String getTextOn() {
        return this.mTextOn;
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(1, null);
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int width2 = getWidth();
        canvas.translate((float) (paddingLeft + ((((width - paddingLeft) - getPaddingRight()) - width2) / 2)), (float) (paddingTop + ((((height - paddingTop) - getPaddingBottom()) - getHeight()) / 2)));
        int i = AnonymousClass3.$SwitchMap$com$android$camera$ui$ToggleSwitch$State[this.state.ordinal()];
        if (i == 1) {
            drawSwitchOn(canvas);
        } else if (i == 2) {
            drawSwitchOff(canvas);
        } else if (i == 3) {
            drawSwitchOnAnim(canvas);
        } else if (i == 4) {
            drawSwitchOffAnim(canvas);
        }
    }

    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int dp2px = dp2px(54.0f) + this.mTextOnWidth + this.mTextOffWidth + getPaddingLeft() + getPaddingRight();
        int dp2px2 = dp2px(28.0f) + getPaddingTop() + getPaddingBottom();
        if (mode != Integer.MIN_VALUE) {
            dp2px = Math.max(dp2px, size);
        }
        if (mode2 != Integer.MIN_VALUE) {
            dp2px2 = Math.max(dp2px2, size2);
        }
        setMeasuredDimension(dp2px, dp2px2);
    }

    public void setAnimDuration(int i) {
        this.mAnimDuration = i;
    }

    public void setChecked(boolean z) {
        if (!this.isMoving && this.mChecked != z) {
            this.mChecked = z;
            if (!this.mBroadcasting) {
                this.mBroadcasting = true;
                OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(this, this.mChecked);
                }
                this.mBroadcasting = false;
                if (this.mChecked) {
                    this.state = State.SWITCHING_ANIM_ON;
                } else {
                    this.state = State.SWITCHING_ANIM_OFF;
                }
                if (!isAttachedToWindow() || !isLaidOut()) {
                    cancelPositionAnimator();
                    this.mCurrentPos = 0.0f;
                    if (this.mChecked) {
                        this.state = State.SWITCH_ON;
                    } else {
                        this.state = State.SWITCH_OFF;
                    }
                } else {
                    animateToCheckedState();
                }
            }
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setTextOnAndOff(String str, String str2) {
        this.mTextOn = str;
        this.mTextOff = str2;
        caculateTextWidth();
        requestLayout();
    }

    public void toggle() {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null || !cameraAction.isDoingAction()) {
            setChecked(!this.mChecked);
        }
    }
}
