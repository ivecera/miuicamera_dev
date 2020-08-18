package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.android.camera.R;
import com.android.camera.ui.drawable.CameraPaintBase;
import java.util.Locale;

public class CameraFocusPaintEvText extends CameraPaintBase {
    private final int mDefaultPaintAlpha = 192;
    private final int mDefaultPaintColor;
    private final int mDefaultTextSize;
    private int mEvTextMargin;
    private float mEvValue;

    public CameraFocusPaintEvText(Context context) {
        super(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SettingStatusBarText, new int[]{16842901, 16842904});
        this.mDefaultPaintColor = obtainStyledAttributes.getColor(obtainStyledAttributes.getIndex(1), -1);
        this.mDefaultTextSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), 0);
        obtainStyledAttributes.recycle();
        this.mEvTextMargin = context.getResources().getDimensionPixelSize(R.dimen.focus_view_ev_text_margin);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        float f2 = this.mEvValue;
        if (f2 != 0.0f) {
            String str = f2 < 0.0f ? "-" : "+";
            String format = String.format(Locale.ENGLISH, "%s %.1f", str, Float.valueOf(Math.abs(this.mEvValue)));
            canvas.drawText(format, (((CameraPaintBase) this).mMiddleX - (((CameraPaintBase) this).mPaint.measureText(format) / 2.0f)) - (((CameraPaintBase) this).mPaint.measureText(str) / 2.0f), (((CameraPaintBase) this).mMiddleY - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) this.mEvTextMargin), ((CameraPaintBase) this).mPaint);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        ((CameraPaintBase) this).mPaint.setColor(this.mDefaultPaintColor);
        ((CameraPaintBase) this).mPaint.setStyle(Paint.Style.FILL);
        ((CameraPaintBase) this).mPaint.setTextSize((float) this.mDefaultTextSize);
        ((CameraPaintBase) this).mPaint.setTextAlign(Paint.Align.LEFT);
        ((CameraPaintBase) this).mPaint.setAntiAlias(true);
        ((CameraPaintBase) this).mPaint.setAlpha(192);
    }

    public void resetPaint() {
        initPaint(null);
    }

    public void setEvValue(float f2) {
        this.mEvValue = f2;
    }
}
