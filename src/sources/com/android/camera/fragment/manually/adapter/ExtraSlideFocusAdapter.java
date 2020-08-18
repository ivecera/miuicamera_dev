package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.BaseHorizontalZoomView;
import com.android.camera.ui.HorizontalSlideView;

public class ExtraSlideFocusAdapter extends BaseHorizontalZoomView.HorizontalDrawAdapter implements HorizontalSlideView.OnItemSelectListener {
    private static final int ENTRY_GAP = 10;
    private static final int MAX_ENTRY_SECTION = 10;
    private static final int MAX_POSITION = 1000;
    private static final int MAX_SECTION = 100;
    private static final int MAX_VALUE = 100;
    private static final int[] sTextActivatedColorState = {16843518};
    private static final int[] sTextDefaultColorState = {0};
    private ComponentData mComponentData;
    private int mCurrentMode;
    private String mCurrentValue;
    private CharSequence[] mEntries;
    private int mLineColorDefault;
    private float mLineHalfHeight;
    private int mLineLineGap;
    private int mLineTextGap;
    private int mLineWidth;
    private ManuallyListener mManuallyListener;
    private float mMinimumFocusDistance;
    private Paint mPaint;
    private ColorStateList mTextColor;
    private int mTextSize;
    private int mTrackedFocusPosition;

    public ExtraSlideFocusAdapter(Context context, ComponentData componentData, int i, ManuallyListener manuallyListener) {
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        this.mCurrentValue = componentData.getComponentValue(this.mCurrentMode);
        try {
            this.mTrackedFocusPosition = Integer.parseInt(this.mCurrentValue);
        } catch (NumberFormatException unused) {
            this.mTrackedFocusPosition = 1000;
        }
        initStyle(context);
        CharSequence[] charSequenceArr = new CharSequence[11];
        for (int i2 = 0; i2 <= 10; i2++) {
            charSequenceArr[i2] = getDisplayedFocusValue(context, i2 * 10);
        }
        this.mEntries = charSequenceArr;
    }

    private void drawText(int i, Canvas canvas) {
        canvas.drawText(this.mEntries[i].toString(), 0.0f, (-(this.mPaint.ascent() + this.mPaint.descent())) / 2.0f, this.mPaint);
    }

    private String getDisplayedFocusValue(Context context, int i) {
        return i == 0 ? context.getString(R.string.pref_camera_focusmode_entry_auto_abbr) : String.valueOf(i);
    }

    private void initStyle(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SingeTextItemTextStyle, new int[]{16842901, 16842904});
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), this.mTextSize);
        this.mTextColor = obtainStyledAttributes.getColorStateList(obtainStyledAttributes.getIndex(1));
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mLineHalfHeight = ((float) resources.getDimensionPixelSize(R.dimen.focus_line_height)) / 2.0f;
        this.mLineWidth = resources.getDimensionPixelSize(R.dimen.focus_line_width);
        this.mLineLineGap = resources.getDimensionPixelSize(R.dimen.focus_line_line_gap);
        this.mLineTextGap = resources.getDimensionPixelSize(R.dimen.focus_line_text_gap);
        this.mLineColorDefault = resources.getColor(R.color.manual_focus_line_color_default);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
        this.mPaint.setShadowLayer(2.75f, 0.0f, 0.0f, -1157627904);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTextAlign(Paint.Align.LEFT);
        this.mPaint.setTypeface(this.mPaint.getTypeface());
    }

    private int mapIndexToFocus(int i) {
        return 1000 - ((i * 1000) / 100);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z) {
        if (i % 10 == 0) {
            this.mPaint.setColor(z ? this.mTextColor.getColorForState(sTextActivatedColorState, 0) : this.mTextColor.getColorForState(sTextDefaultColorState, 0));
            drawText(i / 10, canvas);
            return;
        }
        this.mPaint.setColor(z ? this.mTextColor.getColorForState(sTextActivatedColorState, 0) : this.mLineColorDefault);
        float f2 = this.mLineHalfHeight;
        canvas.drawLine(0.0f, -f2, 0.0f, f2, this.mPaint);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return Paint.Align.LEFT;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return 101;
    }

    public int mapFocusToIndex(int i) {
        return 100 - (Util.clamp(i, 0, 1000) / 10);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureGap(int i) {
        return (float) ((i % 10 == 0 || (i + 1) % 10 == 0) ? this.mLineTextGap : this.mLineLineGap);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return i % 10 == 0 ? this.mPaint.measureText(this.mEntries[i / 10].toString()) : (float) this.mLineWidth;
    }

    @Override // com.android.camera.ui.HorizontalSlideView.OnItemSelectListener
    public void onItemSelect(HorizontalSlideView horizontalSlideView, int i) {
        int mapIndexToFocus = mapIndexToFocus(i);
        if (!horizontalSlideView.isScrolling() && this.mTrackedFocusPosition != mapIndexToFocus) {
            this.mTrackedFocusPosition = mapIndexToFocus;
            CameraStatUtils.trackFocusPositionChanged(mapIndexToFocus, this.mCurrentMode);
        }
        String valueOf = String.valueOf(mapIndexToFocus);
        if (!valueOf.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, valueOf);
            ManuallyListener manuallyListener = this.mManuallyListener;
            if (manuallyListener != null) {
                manuallyListener.onManuallyDataChanged(this.mComponentData, this.mCurrentValue, valueOf, false, this.mCurrentMode);
            }
            this.mCurrentValue = valueOf;
        }
    }
}
