package com.android.camera.effect.renders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomTextWaterMark {
    private static final int SIZE_CN = 70;
    private static final int SIZE_EN = 77;
    private static final String TAG = "CustomTextWaterMark";
    private static final int TEXT_COLOR = -1;
    private static final int TYPE_CN = 1;
    private static final int TYPE_EN = 2;
    private static final float WATER_MARK_SHADOW_Y = 2.0f;
    private static final int WATER_MARK_SHADOW_Y_COLOR = 771751936;
    private final TextPaint mCNPaint;
    private final TextPaint mENPaint;
    private Bitmap mInputBitmap;
    private final Paint.FontMetricsInt mMetrics;
    private float mStartX;
    private float mStartY;
    private String mText;

    public CustomTextWaterMark(Bitmap bitmap, float f2, float f3, String str, TextPaint textPaint, TextPaint textPaint2, Paint.FontMetricsInt fontMetricsInt) {
        this.mInputBitmap = bitmap;
        this.mStartX = f2;
        this.mStartY = f3;
        this.mText = str;
        this.mCNPaint = textPaint;
        this.mENPaint = textPaint2;
        this.mMetrics = fontMetricsInt;
    }

    public static TextPaint getDefaultPaint(float f2, int i, int i2, float f3) {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(f2);
        textPaint.setAntiAlias(true);
        textPaint.setColor(i);
        if (i2 == 1) {
            textPaint.setTypeface(Util.getLanTineGBTypeface(CameraAppImpl.getAndroidContext()));
        } else if (i2 == 2) {
            textPaint.setFakeBoldText(true);
            textPaint.setTypeface(Util.getMFYueYuanTypeface(CameraAppImpl.getAndroidContext()));
        }
        textPaint.setShadowLayer(0.1f, 0.0f, 2.0f, 771751936);
        textPaint.setStrokeWidth(0.5f);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        setLongshotMode(textPaint, f3);
        return textPaint;
    }

    public static CustomTextWaterMark newInstance(Bitmap bitmap, float f2, float f3, String str, float f4) {
        float resourceFloat = CameraSettings.getResourceFloat(R.dimen.custom_watermark_text_size_ratio, 1.0f);
        TextPaint defaultPaint = getDefaultPaint((float) ((int) (70.0f * resourceFloat)), -1, 1, f4);
        TextPaint defaultPaint2 = getDefaultPaint((float) ((int) (resourceFloat * 77.0f)), -1, 2, f4);
        return new CustomTextWaterMark(bitmap, f2, f3, str, defaultPaint, defaultPaint2, defaultPaint2.getFontMetricsInt());
    }

    private static void setLongshotMode(TextPaint textPaint, float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            CompatibilityUtils.setTextPaintLetterSpacing(textPaint, f2);
        }
    }

    public Bitmap drawToBitmap() {
        Bitmap copy = this.mInputBitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.mInputBitmap.recycle();
        copy.setPremultiplied(true);
        onDraw(new Canvas(copy), copy);
        return copy;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas, Bitmap bitmap) {
        float f2;
        float f3;
        long nanoTime = System.nanoTime();
        canvas.translate(this.mStartX, this.mStartY - ((float) this.mMetrics.ascent));
        Matcher matcher = Pattern.compile("([\\sa-zA-Z0-9]+)|([^\\sa-zA-Z0-9]+)").matcher(this.mText);
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            if (group.matches("[\\sa-zA-Z0-9]+")) {
                f3 = (float) i;
                canvas.drawText(group, f3, 0.0f, this.mENPaint);
                f2 = this.mENPaint.measureText(group);
            } else {
                f3 = (float) i;
                canvas.drawText(group, f3, 0.0f, this.mCNPaint);
                f2 = this.mCNPaint.measureText(group);
            }
            i = (int) (f3 + f2);
        }
        String str = TAG;
        Log.v(str, "Custom watermark cost time = " + (System.nanoTime() - nanoTime));
    }
}
