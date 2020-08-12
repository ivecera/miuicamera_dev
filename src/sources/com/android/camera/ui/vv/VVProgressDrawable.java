package com.android.camera.ui.vv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import com.android.camera.R;
import java.util.List;
import java.util.Locale;

public class VVProgressDrawable extends Drawable {
    private Paint mBgPaint = new Paint();
    private List<Long> mDurationList;
    private float mHeight;
    private Paint mLinePaint;
    private int mSplitOffset;
    private Paint mSplitPaint;
    private Paint mTextPaint;
    private long mTotalDuration;
    private RectF mTotalRectF;
    private float mWidth;

    public VVProgressDrawable(Context context) {
        this.mBgPaint.setAntiAlias(true);
        this.mBgPaint.setStyle(Paint.Style.FILL);
        this.mBgPaint.setColor(context.getColor(R.color.vv_progress_bg_color));
        this.mLinePaint = new Paint();
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mLinePaint.setColor(context.getColor(R.color.vv_progress_line_color));
        this.mSplitPaint = new Paint();
        this.mSplitPaint.setAntiAlias(true);
        this.mSplitPaint.setStyle(Paint.Style.FILL);
        this.mSplitPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mSplitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.mSplitOffset = context.getResources().getDimensionPixelSize(R.dimen.vv_duration_split_offset);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SettingStatusBarText, new int[]{16842901, 16842904});
        int color = obtainStyledAttributes.getColor(obtainStyledAttributes.getIndex(1), -1);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), 0);
        obtainStyledAttributes.recycle();
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(color);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setTextSize((float) dimensionPixelSize);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setAlpha(192);
        this.mTotalRectF = new RectF();
    }

    private void drawDurationText(Canvas canvas, float f2, float f3, float f4) {
        String format = String.format(Locale.ENGLISH, "%.1fs", Float.valueOf(Math.abs(f2 / 1000.0f)));
        Rect rect = new Rect();
        this.mTextPaint.getTextBounds(format, 0, format.length(), rect);
        canvas.save();
        canvas.translate((this.mWidth / 2.0f) - (((float) rect.height()) / 2.0f), ((f3 + f4) / 2.0f) - (((float) rect.width()) / 2.0f));
        canvas.rotate(90.0f);
        canvas.drawText(format, 0.0f, 0.0f, this.mTextPaint);
        canvas.restore();
    }

    private void drawRecording(Canvas canvas) {
        int size = this.mDurationList.size();
        int i = size - 1;
        float f2 = this.mHeight - ((float) (this.mSplitOffset * i));
        float f3 = 0.0f;
        for (int i2 = 0; i2 < size; i2++) {
            float abs = (((float) Math.abs(this.mDurationList.get(i2).longValue())) / ((float) this.mTotalDuration)) * f2;
            if (i2 < i) {
                float f4 = f3 + abs;
                canvas.drawRect(new RectF(0.0f, f4, this.mWidth, ((float) this.mSplitOffset) + f4), this.mSplitPaint);
            }
            if (this.mDurationList.get(i2).longValue() < 0) {
                canvas.drawRect(new RectF(0.0f, f3 - 1.0f, this.mWidth, f3 + abs + 1.0f), this.mSplitPaint);
            }
            f3 = f3 + abs + ((float) this.mSplitOffset);
        }
    }

    private void drawSplit(Canvas canvas) {
        int size = this.mDurationList.size();
        int i = size - 1;
        float f2 = this.mHeight - ((float) (this.mSplitOffset * i));
        float f3 = 0.0f;
        for (int i2 = 0; i2 < size; i2++) {
            float abs = (((float) Math.abs(this.mDurationList.get(i2).longValue())) / ((float) this.mTotalDuration)) * f2;
            if (i2 < i) {
                float f4 = f3 + abs;
                canvas.drawRect(new RectF(0.0f, f4, this.mWidth, ((float) this.mSplitOffset) + f4), this.mSplitPaint);
            }
            f3 = f3 + abs + ((float) this.mSplitOffset);
        }
    }

    public void draw(@NonNull Canvas canvas) {
        RectF rectF = this.mTotalRectF;
        float f2 = this.mWidth;
        canvas.drawRoundRect(rectF, f2 / 2.0f, f2 / 2.0f, this.mBgPaint);
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, this.mWidth, this.mHeight, null, 31);
        RectF rectF2 = this.mTotalRectF;
        float f3 = this.mWidth;
        canvas.drawRoundRect(rectF2, f3 / 2.0f, f3 / 2.0f, this.mLinePaint);
        drawRecording(canvas);
        canvas.restoreToCount(saveLayer);
        drawSplit(canvas);
    }

    public int getOpacity() {
        return -1;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setDurationList(List<Long> list) {
        this.mTotalDuration = 0;
        this.mDurationList = list;
        for (Long l : list) {
            this.mTotalDuration += Math.abs(l.longValue());
        }
    }

    public void setWidthHeight(float f2, float f3) {
        this.mWidth = f2;
        this.mHeight = f3;
        this.mTotalRectF.set(0.0f, 0.0f, this.mWidth, this.mHeight);
    }

    public void updateDuration(int i, long j) {
        this.mDurationList.set(i, Long.valueOf(j));
    }
}
