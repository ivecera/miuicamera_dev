package com.android.camera.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.camera.R;
import com.android.camera.Util;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MutiStateButton extends LinearLayout {
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final int DEFAULT_TEXT_SIZE = 30;
    private RectF mAniBGRectF = null;
    private int mAnimDuration;
    private boolean mAnimation;
    private ValueAnimator mAnimator;
    private int mBGColor;
    private Paint mBGPaint;
    private int mCurrentIndex = -1;
    private TextView mCurrentView = null;
    private int mItemLeftRightMargin;
    private int mItemLeftRightPaddingOther;
    private int mItemLeftRightPaddingZH;
    private LinkedList<String> mItemLists = new LinkedList<>();
    private int mItemTopBottomMargin;
    private int mItemTopBottomPaddingOther;
    private int mItemTopBottomPaddingZH;
    private ArrayList<TextView> mItems = new ArrayList<>();
    private float mRadius;
    private int mTextColor;
    private float mTextSize;

    public MutiStateButton(Context context) {
        super(context);
    }

    public MutiStateButton(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        dealWithAttributeSet(attributeSet);
    }

    public MutiStateButton(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void dealWithAttributeSet(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MutiStateButton);
        this.mItemTopBottomPaddingZH = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        this.mItemLeftRightPaddingZH = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        this.mItemTopBottomPaddingOther = obtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mItemLeftRightPaddingOther = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        this.mItemTopBottomMargin = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        this.mItemLeftRightMargin = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        this.mAnimDuration = obtainStyledAttributes.getInteger(2, 200);
        this.mRadius = (float) obtainStyledAttributes.getDimensionPixelSize(1, 0);
        this.mBGColor = obtainStyledAttributes.getColor(0, 0);
        this.mTextColor = obtainStyledAttributes.getColor(9, -1);
        this.mTextSize = (float) obtainStyledAttributes.getDimensionPixelSize(10, 30);
        obtainStyledAttributes.recycle();
    }

    private RectF getBGRectF() {
        RectF rectF = new RectF();
        rectF.left = (float) this.mCurrentView.getLeft();
        rectF.top = (float) this.mCurrentView.getTop();
        rectF.right = (float) this.mCurrentView.getRight();
        rectF.bottom = (float) this.mCurrentView.getBottom();
        return rectF;
    }

    private ViewGroup.MarginLayoutParams getItemMarginLayoutParams(View view, boolean z, boolean z2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i = this.mItemTopBottomMargin;
        marginLayoutParams.topMargin = i;
        marginLayoutParams.bottomMargin = i;
        if (z) {
            int i2 = this.mItemLeftRightMargin;
            marginLayoutParams.rightMargin = i2;
            if (!z2) {
                i2 = 0;
            }
            marginLayoutParams.leftMargin = i2;
        } else {
            int i3 = this.mItemLeftRightMargin;
            marginLayoutParams.leftMargin = i3;
            if (!z2) {
                i3 = 0;
            }
            marginLayoutParams.rightMargin = i3;
        }
        return marginLayoutParams;
    }

    private void startSwtichAnimation(TextView textView, int i) {
        if (this.mAnimator == null) {
            this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        }
        if (this.mAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (this.mAniBGRectF == null) {
            this.mAniBGRectF = new RectF();
        }
        this.mAniBGRectF.left = (float) textView.getLeft();
        this.mAniBGRectF.top = (float) textView.getTop();
        this.mAniBGRectF.right = (float) textView.getRight();
        this.mAniBGRectF.bottom = (float) textView.getBottom();
        float left = (float) textView.getLeft();
        float right = (float) textView.getRight();
        float left2 = ((float) this.mCurrentView.getLeft()) - left;
        float right2 = ((float) this.mCurrentView.getRight()) - right;
        this.mAnimator.setDuration(i > 1 ? (long) (((double) this.mAnimDuration) * 1.5d) : (long) this.mAnimDuration);
        this.mAnimator.addUpdateListener(new a(this, left, left2, right, right2));
        this.mAnimator.start();
    }

    public /* synthetic */ void a(float f2, float f3, float f4, float f5, ValueAnimator valueAnimator) {
        this.mAniBGRectF.left = f2 + (((Float) valueAnimator.getAnimatedValue()).floatValue() * f3);
        this.mAniBGRectF.right = f4 + (((Float) valueAnimator.getAnimatedValue()).floatValue() * f5);
        invalidate();
    }

    public void initItems(LinkedHashMap<String, Integer> linkedHashMap, View.OnClickListener onClickListener) {
        boolean isLocaleChinese = Util.isLocaleChinese();
        boolean isLayoutRTL = Util.isLayoutRTL(getContext());
        this.mBGPaint = new Paint();
        this.mBGPaint.setColor(this.mBGColor);
        this.mBGPaint.setAntiAlias(true);
        for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            TextView textView = new TextView(getContext());
            textView.setTag(entry.getKey());
            textView.setText(entry.getValue().intValue());
            textView.setTextColor(this.mTextColor);
            textView.setGravity(17);
            if (isLocaleChinese) {
                int i = this.mItemLeftRightPaddingZH;
                int i2 = this.mItemTopBottomPaddingZH;
                textView.setPadding(i, i2, i, i2);
            } else {
                int i3 = this.mItemLeftRightPaddingOther;
                int i4 = this.mItemTopBottomPaddingOther;
                textView.setPadding(i3, i4, i3, i4);
            }
            boolean z = false;
            textView.setTextSize(0, this.mTextSize);
            textView.setOnClickListener(onClickListener);
            addView(textView);
            this.mItems.add(textView);
            this.mItemLists.add(entry.getKey());
            if (this.mItems.size() == linkedHashMap.size()) {
                z = true;
            }
            textView.setLayoutParams(getItemMarginLayoutParams(textView, isLayoutRTL, z));
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        RectF rectF;
        super.onDraw(canvas);
        if (this.mCurrentView != null) {
            if (!this.mAnimation || (rectF = this.mAniBGRectF) == null) {
                RectF bGRectF = getBGRectF();
                float f2 = this.mRadius;
                canvas.drawRoundRect(bGRectF, f2, f2, this.mBGPaint);
                return;
            }
            float f3 = this.mRadius;
            canvas.drawRoundRect(rectF, f3, f3, this.mBGPaint);
        }
    }

    public void updateCurrentIndex(String str, boolean z) {
        int indexOf = this.mItemLists.indexOf(str);
        int i = this.mCurrentIndex;
        if (i != indexOf) {
            int abs = Math.abs(i - indexOf);
            TextView textView = this.mCurrentView;
            this.mCurrentIndex = indexOf;
            this.mCurrentView = this.mItems.get(this.mCurrentIndex);
            this.mAnimation = z;
            if (z) {
                startSwtichAnimation(textView, abs);
            } else {
                invalidate();
            }
        }
    }
}
