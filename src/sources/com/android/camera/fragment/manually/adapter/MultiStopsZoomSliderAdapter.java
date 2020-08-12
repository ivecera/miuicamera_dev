package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;

public abstract class MultiStopsZoomSliderAdapter extends AbstractZoomSliderAdapter {
    private static final String TAG = "MultiStopsZoomSliderAdapter";
    protected final int mCurrentMode;
    private String mCurrentValue = "-1";
    private int mCurrentValueIndex = -1;
    private boolean mEnable;
    private StaticLayout[] mEntryLayouts;
    private boolean mIsLayoutRTL;
    private int mLineColorDefault;
    private float mLineHalfHeight;
    private int mLineTextGap;
    private int mLineWidth;
    private Paint mPaint;
    private int mSelectHalfHeight;
    private Paint mSelectPaint;
    private AbsoluteSizeSpan mSpaceTextStyle;
    private int mTargetHalfHeight;
    private StyleSpan mTextBoldStyle;
    private int mTextColor;
    private TypefaceSpan mTextFontStyle;
    private TextPaint mTextPaint;
    private int mTextSize;
    private final float mZoomRatioMax;
    private final float mZoomRatioMin;
    protected final MultiStopsZoomRatioResolver mZoomRatioResolver;

    public MultiStopsZoomSliderAdapter(Context context, int i, ZoomValueListener zoomValueListener) {
        this.mCurrentMode = i;
        ((AbstractZoomSliderAdapter) this).mZoomValueListener = zoomValueListener;
        this.mIsLayoutRTL = Util.isLayoutRTL(context);
        this.mCurrentValue = DataRepository.dataItemConfig().getString(CameraSettings.KEY_ZOOM, "1.0");
        BaseModule baseModule = (BaseModule) ((ActivityBase) context).getCurrentModule();
        this.mZoomRatioMin = baseModule.getMinZoomRatio();
        this.mZoomRatioMax = baseModule.getMaxZoomRatio();
        Log.d(TAG, "ZOOM RATIO RANGE [" + this.mZoomRatioMin + ", " + this.mZoomRatioMax + "]");
        this.mZoomRatioResolver = getZoomRatioResolver();
        initStyle(context);
        CharSequence[] charSequenceArr = {createZoomRatioLabel(this.mZoomRatioMin), createZoomRatioLabel(this.mZoomRatioMax)};
        this.mEntryLayouts = new StaticLayout[charSequenceArr.length];
        for (int i2 = 0; i2 < charSequenceArr.length; i2++) {
            this.mEntryLayouts[i2] = new StaticLayout(charSequenceArr[i2], this.mTextPaint, Util.sWindowWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
    }

    private CharSequence createZoomRatioLabel(float f2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        float decimal = HybridZoomingSystem.toDecimal(f2);
        int i = (int) decimal;
        if (((int) ((10.0f * decimal) - ((float) (i * 10)))) == 0) {
            setStyleSpan(spannableStringBuilder, String.valueOf(i));
        } else {
            setStyleSpan(spannableStringBuilder, String.valueOf(decimal));
        }
        Util.appendInApi26(spannableStringBuilder, " ", this.mSpaceTextStyle, 33);
        setStyleSpan(spannableStringBuilder, "X");
        return spannableStringBuilder;
    }

    private void drawText(float f2, int i, Canvas canvas) {
        canvas.save();
        canvas.translate(f2, ((float) (this.mEntryLayouts[i].getLineAscent(0) - this.mEntryLayouts[i].getLineDescent(0))) / 2.0f);
        this.mEntryLayouts[i].draw(canvas);
        canvas.restore();
    }

    private boolean drawTextForItemAt(int i) {
        return this.mZoomRatioResolver.isFirstStopPoint(i) || this.mZoomRatioResolver.isLastStopPoint(i);
    }

    private int indexToSection(int i) {
        if (this.mZoomRatioResolver.isFirstStopPoint(i)) {
            return 0;
        }
        return this.mZoomRatioResolver.isLastStopPoint(i) ? 1 : -1;
    }

    private void initStyle(Context context) {
        Resources resources = context.getResources();
        this.mTextSize = resources.getDimensionPixelSize(R.dimen.zoom_popup_text_size);
        this.mTextColor = resources.getColor(R.color.zoom_popup_color_new_default);
        this.mLineHalfHeight = (float) (resources.getDimensionPixelSize(R.dimen.zoom_popup_line_height_nornal) / 2);
        this.mTargetHalfHeight = resources.getDimensionPixelSize(R.dimen.zoom_popup_line_height_target) / 2;
        this.mSelectHalfHeight = resources.getDimensionPixelSize(R.dimen.zoom_popup_line_height_select) / 2;
        this.mLineWidth = resources.getDimensionPixelSize(R.dimen.zoom_popup_line_width);
        this.mLineTextGap = resources.getDimensionPixelSize(R.dimen.zoom_popup_line_text_margin);
        this.mLineColorDefault = resources.getColor(R.color.zoom_popup_color_new_default);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
        this.mPaint.setColor(this.mLineColorDefault);
        this.mSelectPaint = new Paint(this.mPaint);
        this.mSelectPaint.setColor(ColorConstant.COLOR_COMMON_SELECTED);
        this.mPaint.setShadowLayer(1.0f, 0.0f, 1.0f, -1090519040);
        this.mTextPaint = new TextPaint(this.mPaint);
        this.mTextPaint.setTextSize((float) this.mTextSize);
        this.mTextPaint.setColor(this.mTextColor);
        this.mSpaceTextStyle = new AbsoluteSizeSpan(18, true);
        this.mTextFontStyle = new TypefaceSpan(Util.getLanTineGBTypeface(context));
        this.mTextBoldStyle = new StyleSpan(1);
    }

    private void setStyleSpan(SpannableStringBuilder spannableStringBuilder, CharSequence charSequence) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan(CharacterStyle.wrap(this.mTextFontStyle), length, spannableStringBuilder.length(), 33);
        spannableStringBuilder.setSpan(CharacterStyle.wrap(this.mTextBoldStyle), length, spannableStringBuilder.length(), 33);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(float f2, int i, Canvas canvas, boolean z) {
        if ((this.mZoomRatioResolver.isFirstStopPoint(i) && !this.mIsLayoutRTL) || (this.mZoomRatioResolver.isLastStopPoint(i) && this.mIsLayoutRTL)) {
            drawText(f2, indexToSection(i), canvas);
            f2 += measureWidth(i) - ((float) this.mLineWidth);
        }
        if (z) {
            int i2 = this.mSelectHalfHeight;
            canvas.drawLine(f2, (float) (-i2), f2, (float) i2, this.mSelectPaint);
        } else if (this.mZoomRatioResolver.isStopPoint(i)) {
            int i3 = this.mTargetHalfHeight;
            canvas.drawLine(f2, (float) (-i3), f2, (float) i3, this.mPaint);
        } else {
            float f3 = this.mLineHalfHeight;
            canvas.drawLine(f2, -f3, f2, f3, this.mPaint);
        }
        if ((this.mZoomRatioResolver.isLastStopPoint(i) && !this.mIsLayoutRTL) || (this.mZoomRatioResolver.isFirstStopPoint(i) && this.mIsLayoutRTL)) {
            drawText(f2 + ((float) this.mLineWidth) + ((float) this.mLineTextGap), indexToSection(i), canvas);
        }
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    public int getCurrentCapturingMode() {
        return this.mCurrentMode;
    }

    public String getCurrentValue() {
        return this.mCurrentValue;
    }

    public int getCurrentValueIndex() {
        return this.mCurrentValueIndex;
    }

    public float getZoomRatioMax() {
        return this.mZoomRatioMax;
    }

    public float getZoomRatioMin() {
        return this.mZoomRatioMin;
    }

    /* access modifiers changed from: protected */
    public abstract MultiStopsZoomRatioResolver getZoomRatioResolver();

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public boolean isEnable() {
        return this.mEnable;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return drawTextForItemAt(i) ? this.mEntryLayouts[indexToSection(i)].getLineWidth(0) + ((float) this.mLineWidth) + ((float) this.mLineTextGap) : (float) this.mLineWidth;
    }

    public void notifyDataChanged(int i, String str) {
        ZoomValueListener zoomValueListener;
        if (!(i == -1 || getCurrentValueIndex() == i || (zoomValueListener = ((AbstractZoomSliderAdapter) this).mZoomValueListener) == null)) {
            zoomValueListener.onZoomItemSlideOn(i, this.mZoomRatioResolver.isStopPoint(i));
        }
        ZoomValueListener zoomValueListener2 = ((AbstractZoomSliderAdapter) this).mZoomValueListener;
        if (zoomValueListener2 != null) {
            zoomValueListener2.onManuallyDataChanged(str);
        }
        this.mCurrentValue = str;
        this.mCurrentValueIndex = i;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onPositionSelect(View view, int i, float f2) {
        if (this.mEnable) {
            float f3 = 0.0f;
            if (f2 >= 0.0f) {
                f3 = f2;
            }
            if (f3 >= 1.0f) {
                f3 = 1.0f;
            }
            this.mZoomRatioResolver.checkAndNotifyIfValueChanged(i, f3);
        }
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
