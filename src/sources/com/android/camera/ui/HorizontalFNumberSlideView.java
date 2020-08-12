package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.android.camera.R;

public class HorizontalFNumberSlideView extends HorizontalSlideView {
    private int mLineColorSelected;
    private float mLineSelectedHalfHeight;
    private int mLineWidth;
    private Paint mPaint;

    public HorizontalFNumberSlideView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalFNumberSlideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public HorizontalFNumberSlideView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.HorizontalSlideView
    public void init(Context context) {
        super.init(context);
        this.mLineColorSelected = context.getColor(R.color.bokeh_popup_line_color_selected);
        this.mLineSelectedHalfHeight = ((float) context.getResources().getDimensionPixelSize(R.dimen.bokeh_line_selected_height)) / 2.0f;
        this.mLineWidth = context.getResources().getDimensionPixelSize(R.dimen.bokeh_line_width);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
        this.mPaint.setColor(this.mLineColorSelected);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.HorizontalSlideView
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(((HorizontalSlideView) this).mOriginX, ((float) getHeight()) / 2.0f);
        float f2 = this.mLineSelectedHalfHeight;
        canvas.drawLine(0.0f, -f2, 0.0f, f2, this.mPaint);
        canvas.restore();
    }
}
