package com.android.camera.fragment.manually.adapter.sat;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.TextAppearanceSpan;
import android.util.Spline;
import android.view.View;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter;
import com.android.camera.module.BaseModule;

public class DuoSatZoomSliderAdapter extends AbstractZoomSliderAdapter {
    private static final int ENTRY_COUNT_1X_TO_2X = 11;
    private static final int ENTRY_COUNT_2X_TO_MAX = 38;
    private static final int ENTRY_COUNT_TOTAL = 48;
    private static final int ENTRY_INDEX_1X = 0;
    private static final int ENTRY_INDEX_2X = 10;
    private static final int ENTRY_INDEX_MAX = 47;
    private static final float[] RANGE_1X_TO_2X_ENTRY_INDEX_X = {0.0f, 3.0f, 5.0f, 7.0f, 10.0f};
    private static final float[] RANGE_1X_TO_2X_ZOOM_RATIO_Y = {1.0f, 1.3f, 1.5f, 1.7f, 2.0f};
    private static final int[] sTextActivatedColorState = {16843518};
    private static final int[] sTextDefaultColorState = {0};
    private static float[] sX = {10.0f, 12.0f, 20.0f, 25.0f, 27.0f, 29.0f, 30.0f, 32.0f, 35.0f};
    private static float[] sY = {2.0f, 2.2f, 3.7f, 5.1f, 5.8f, 6.6f, 7.0f, 8.0f, 10.0f};
    private int ENTRY_INDEX_TELE_REAL = 10;
    private int mCurrentMode;
    private String mCurrentValue;
    private TextAppearanceSpan mDigitsTextStyle;
    private int mDotColorActivated;
    private int mDotRadius;
    private boolean mEnable;
    private CharSequence[] mEntries;
    private StaticLayout[] mEntryLayouts;
    private Spline mEntryToZoomRatioSpline;
    private int mLineColorDefault;
    private int mLineDotYGap;
    private float mLineHalfHeight;
    private int mLineLineGap;
    private int mLineTextGap;
    private int mLineWidth;
    private Paint mPaint;
    private Spline mRange1XTo2XEntryToZoomRatioSpline;
    private Spline mRange1XTo2XZoomRatioToEntrySpline;
    private ColorStateList mTextColor;
    private TextPaint mTextPaint;
    private int mTextSize;
    private TextAppearanceSpan mXTextStyle;
    private float mZoomRatioMax;
    private float mZoomRatioTele;
    private Spline mZoomRatioToEntrySpline;
    private float mZoomRatioWide;

    public DuoSatZoomSliderAdapter(Context context, int i, ZoomValueListener zoomValueListener) {
        this.mCurrentMode = i;
        ((AbstractZoomSliderAdapter) this).mZoomValueListener = zoomValueListener;
        this.mCurrentValue = DataRepository.dataItemConfig().getString(CameraSettings.KEY_ZOOM, "1.0");
        this.mZoomRatioWide = 1.0f;
        this.mZoomRatioTele = HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE;
        this.mZoomRatioMax = ((BaseModule) ((ActivityBase) context).getCurrentModule()).getMaxZoomRatio();
        this.mRange1XTo2XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_1X_TO_2X_ENTRY_INDEX_X, RANGE_1X_TO_2X_ZOOM_RATIO_Y);
        this.mRange1XTo2XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_1X_TO_2X_ZOOM_RATIO_Y, RANGE_1X_TO_2X_ENTRY_INDEX_X);
        float[] convertSplineXToEntryX = convertSplineXToEntryX(sX);
        float[] convertSplineYToZoomRatioY = convertSplineYToZoomRatioY(sY);
        this.mEntryToZoomRatioSpline = Spline.createMonotoneCubicSpline(convertSplineXToEntryX, convertSplineYToZoomRatioY);
        this.mZoomRatioToEntrySpline = Spline.createMonotoneCubicSpline(convertSplineYToZoomRatioY, convertSplineXToEntryX);
        initStyle(context);
        int i2 = 0;
        this.mEntries = new CharSequence[]{getDisplayedZoomRatio(this.mZoomRatioWide), getDisplayedZoomRatio(this.mZoomRatioTele), getDisplayedZoomRatio(this.mZoomRatioMax)};
        this.mEntryLayouts = new StaticLayout[this.mEntries.length];
        while (true) {
            CharSequence[] charSequenceArr = this.mEntries;
            if (i2 >= charSequenceArr.length) {
                break;
            }
            this.mEntryLayouts[i2] = new StaticLayout(charSequenceArr[i2], this.mTextPaint, Util.sWindowWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            i2++;
        }
        int realZoomRatioTele = AbstractZoomSliderAdapter.getRealZoomRatioTele();
        float f2 = (float) realZoomRatioTele;
        float f3 = this.mZoomRatioTele;
        if (f2 != f3 * 10.0f) {
            float f4 = this.mZoomRatioWide;
            if (f2 > f4 * 10.0f && f2 < f3 * 10.0f) {
                this.ENTRY_INDEX_TELE_REAL = realZoomRatioTele - (((int) f4) * 10);
            }
        }
    }

    private float[] convertSplineXToEntryX(float[] fArr) {
        int i = (int) ((fArr[fArr.length - 1] - 10.0f) + 1.0f);
        float[] fArr2 = new float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (fArr[i2] <= 10.0f) {
                fArr2[i2] = fArr[i2];
            } else {
                fArr2[i2] = (((fArr[i2] - 10.0f) / ((float) (i - 1))) * 37.0f) + 10.0f;
            }
        }
        return fArr2;
    }

    private float[] convertSplineYToZoomRatioY(float[] fArr) {
        int i = (int) fArr[fArr.length - 1];
        float[] fArr2 = new float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            float f2 = fArr[i2];
            float f3 = this.mZoomRatioTele;
            if (f2 <= f3) {
                fArr2[i2] = fArr[i2];
            } else {
                fArr2[i2] = (((fArr[i2] - f3) / (((float) i) - f3)) * (this.mZoomRatioMax - f3)) + f3;
            }
        }
        return fArr2;
    }

    private void drawText(int i, Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, ((float) (this.mEntryLayouts[i].getLineAscent(0) - this.mEntryLayouts[i].getLineDescent(0))) / 2.0f);
        this.mEntryLayouts[i].draw(canvas);
        canvas.restore();
    }

    private CharSequence getDisplayedZoomRatio(float f2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        Util.appendInApi26(spannableStringBuilder, String.valueOf((int) f2), this.mDigitsTextStyle, 33);
        Util.appendInApi26(spannableStringBuilder, "X", this.mXTextStyle, 33);
        return spannableStringBuilder;
    }

    private int indexToSection(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 10) {
            return 1;
        }
        return i == 47 ? 2 : -1;
    }

    private void initStyle(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SingeTextItemTextStyle, new int[]{16842901, 16842904});
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), this.mTextSize);
        this.mTextColor = obtainStyledAttributes.getColorStateList(obtainStyledAttributes.getIndex(1));
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mLineHalfHeight = ((float) resources.getDimensionPixelSize(R.dimen.focus_line_height)) / 2.0f;
        this.mLineWidth = resources.getDimensionPixelSize(R.dimen.focus_line_width);
        this.mDotRadius = resources.getDimensionPixelSize(R.dimen.zoom_popup_dot_radius);
        this.mLineLineGap = resources.getDimensionPixelSize(R.dimen.focus_line_line_gap);
        this.mLineTextGap = resources.getDimensionPixelSize(R.dimen.focus_line_text_gap);
        this.mLineDotYGap = resources.getDimensionPixelSize(R.dimen.zoom_popup_line_dot_y_gap);
        this.mLineColorDefault = resources.getColor(R.color.zoom_popup_line_color_default);
        this.mDotColorActivated = resources.getColor(R.color.zoom_popup_dot_color_activated);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint = new TextPaint(this.mPaint);
        this.mDigitsTextStyle = new TextAppearanceSpan(context, R.style.ZoomPopupDigitsTextStyle);
        this.mXTextStyle = new TextAppearanceSpan(context, R.style.ZoomPopupXTextStyle);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z) {
        if (i == 0 || i == 10 || i == 47) {
            this.mTextPaint.drawableState = z ? sTextActivatedColorState : sTextDefaultColorState;
            drawText(indexToSection(i), canvas);
            return;
        }
        this.mPaint.setColor(z ? this.mTextColor.getColorForState(sTextActivatedColorState, 0) : this.mLineColorDefault);
        float f2 = this.mLineHalfHeight;
        canvas.drawLine(0.0f, -f2, 0.0f, f2, this.mPaint);
        if (i == this.ENTRY_INDEX_TELE_REAL) {
            this.mPaint.setColor(z ? this.mDotColorActivated : this.mLineColorDefault);
            float f3 = (-this.mLineHalfHeight) - ((float) this.mLineDotYGap);
            int i2 = this.mDotRadius;
            canvas.drawCircle(0.0f, f3 - ((float) i2), (float) i2, this.mPaint);
        }
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return (i == 0 || i == 10 || i == 47) ? Paint.Align.LEFT : Paint.Align.CENTER;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return 48;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public boolean isEnable() {
        return this.mEnable;
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public float mapPositionToZoomRatio(float f2) {
        return (0.0f > f2 || f2 > 10.0f) ? this.mEntryToZoomRatioSpline.interpolate(f2) : this.mRange1XTo2XEntryToZoomRatioSpline.interpolate(f2);
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public float mapZoomRatioToPosition(float f2) {
        return (1.0f > f2 || f2 > HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE) ? this.mZoomRatioToEntrySpline.interpolate(f2) : this.mRange1XTo2XZoomRatioToEntrySpline.interpolate(f2);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureGap(int i) {
        return (float) ((i == 0 || i == 10 || i == 47) ? this.mLineTextGap : this.mLineLineGap);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return (i == 0 || i == 10 || i == 47) ? this.mEntryLayouts[indexToSection(i)].getLineWidth(0) : (float) this.mLineWidth;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onPositionSelect(View view, int i, float f2) {
        if (this.mEnable) {
            String valueOf = i <= 10 ? String.valueOf(HybridZoomingSystem.add(1.0f, ((float) i) * 0.1f)) : String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
            if (!valueOf.equals(this.mCurrentValue)) {
                ZoomValueListener zoomValueListener = ((AbstractZoomSliderAdapter) this).mZoomValueListener;
                if (zoomValueListener != null) {
                    zoomValueListener.onManuallyDataChanged(valueOf);
                }
                this.mCurrentValue = valueOf;
            }
        }
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
