package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.module.BaseModule;
import com.android.camera.ui.BaseHorizontalZoomView;
import java.util.Arrays;
import java.util.List;

public class ExtraSlideFNumberAdapter extends BaseHorizontalZoomView.HorizontalDrawAdapter implements BaseHorizontalZoomView.OnPositionSelectListener {
    private static final int ENTRY_COUNT_TOTAL = F_NUMBERS.length;
    public static final String[] F_NUMBERS = {"1.0", "1.1", "1.2", "1.4", "1.6", "1.8", "2", "2.2", "2.5", "2.8", "3.2", "3.5", "4", "4.5", "5.0", "5.6", "6.3", "7.1", "8", "9", "10", ComponentRunningShine.SHINE_LIVE_BEAUTY, "13", ComponentRunningShine.SHINE_VIDEO_BOKEH_LEVEL, "16"};
    private int mCurrentMode;
    private String mCurrentValue = DataRepository.dataItemConfig().getString(CameraSettings.KEY_ZOOM, "1.0");
    private boolean mEnable;
    private int mLineCircleRadius;
    private int mLineColorDefault;
    private int mLineColorDefaultCircle;
    private int mLineColorDivider;
    private int mLineDefaultCircleGap;
    private float mLineHalfHeight;
    private int mLineLineGap;
    private int mLineWidth;
    private ManuallyListener mManuallyListener;
    private Paint mPaint;
    private int mTextSize;
    private List<String> mValidFNumbers = Arrays.asList(F_NUMBERS);

    public ExtraSlideFNumberAdapter(Context context, int i, ManuallyListener manuallyListener) {
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        BaseModule baseModule = (BaseModule) ((ActivityBase) context).getCurrentModule();
        initStyle(context);
    }

    private void initStyle(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SingeTextItemTextStyle, new int[]{16842901, 16842904});
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), this.mTextSize);
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mLineHalfHeight = ((float) resources.getDimensionPixelSize(R.dimen.bokeh_line_height)) / 2.0f;
        this.mLineWidth = resources.getDimensionPixelSize(R.dimen.bokeh_line_width);
        this.mLineLineGap = resources.getDimensionPixelSize(R.dimen.bokeh_line_line_gap);
        this.mLineColorDefault = resources.getColor(R.color.bokeh_popup_line_color_default);
        this.mLineColorDivider = resources.getColor(R.color.bokeh_popup_line_color_divider);
        this.mLineColorDefaultCircle = resources.getColor(R.color.bokeh_popup_line_default_circle);
        this.mLineCircleRadius = resources.getDimensionPixelSize(R.dimen.bokeh_line_default_circle_radius);
        this.mLineDefaultCircleGap = resources.getDimensionPixelSize(R.dimen.bokeh_line_default_circle_gap);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setTextAlign(Paint.Align.LEFT);
    }

    private String mapPositionToFNumber(float f2) {
        return F_NUMBERS[Math.round(f2 * ((float) (F_NUMBERS.length - 1)))];
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public void draw(int i, Canvas canvas, boolean z) {
        int i2 = this.mLineColorDefault;
        if (i % 3 == 0) {
            i2 = this.mLineColorDivider;
        }
        if (F_NUMBERS[i].equals(DataRepository.dataItemFeature().s(CameraSettings.isFrontCamera())) && !z) {
            this.mPaint.setColor(this.mLineColorDefaultCircle);
            canvas.drawCircle(0.0f, (float) (-this.mLineDefaultCircleGap), (float) this.mLineCircleRadius, this.mPaint);
        }
        this.mPaint.setColor(i2);
        float f2 = this.mLineHalfHeight;
        canvas.drawLine(0.0f, -f2, 0.0f, f2, this.mPaint);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public Paint.Align getAlign(int i) {
        return Paint.Align.CENTER;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return ENTRY_COUNT_TOTAL;
    }

    public float mapFNumberToPosition(String str) {
        return (((float) this.mValidFNumbers.indexOf(str)) * 1.0f) / ((float) (F_NUMBERS.length - 1));
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureGap(int i) {
        return (float) this.mLineLineGap;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public float measureWidth(int i) {
        return (float) this.mLineWidth;
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.OnPositionSelectListener
    public void onPositionSelect(View view, int i, float f2) {
        if (this.mEnable) {
            String mapPositionToFNumber = mapPositionToFNumber(f2);
            if (!mapPositionToFNumber.equals(this.mCurrentValue)) {
                ManuallyListener manuallyListener = this.mManuallyListener;
                if (manuallyListener != null) {
                    manuallyListener.onManuallyDataChanged(null, this.mCurrentValue, mapPositionToFNumber, false, this.mCurrentMode);
                }
                this.mCurrentValue = mapPositionToFNumber;
            }
        }
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
    }
}
