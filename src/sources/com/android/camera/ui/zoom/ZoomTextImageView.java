package com.android.camera.ui.zoom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.android.camera.R;

public class ZoomTextImageView extends View {
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_TEXT_NORMAL = 0;
    public static final int TYPE_TEXT_SNAP = 1;
    private static final int[] mTextActivatedColorState = {16843518};
    private Matrix mBitmapMatrix;
    private Bitmap mCurrentImage;
    private String mCurrentText;
    private int mCurrentType;
    private Paint mImagePaint;
    private ColorStateList mNormalTextColor;
    private int mNormalTextSize;
    private ColorStateList mSnapTextColor;
    private int mSnapTextSize;
    private Paint mTextPaint;
    private ColorStateList mXTextColor;
    private Paint mXTextPaint;
    private int mXTextSize;

    public ZoomTextImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomTextImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ZoomTextImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public ZoomTextImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context);
    }

    private void init(Context context) {
        this.mTextPaint = new Paint();
        this.mImagePaint = new Paint();
        this.mXTextPaint = new Paint();
        this.mImagePaint.setAntiAlias(true);
        this.mImagePaint.setStyle(Paint.Style.STROKE);
        int[] iArr = {16842901, 16842904};
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.ZoomButtonDigitsTextStyle, iArr);
        this.mNormalTextSize = obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), this.mNormalTextSize);
        this.mNormalTextColor = obtainStyledAttributes.getColorStateList(obtainStyledAttributes.getIndex(1));
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(R.style.ZoomButtonXTextStyle, iArr);
        this.mXTextSize = obtainStyledAttributes2.getDimensionPixelSize(obtainStyledAttributes2.getIndex(0), this.mXTextSize);
        this.mXTextColor = obtainStyledAttributes2.getColorStateList(obtainStyledAttributes2.getIndex(1));
        TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(R.style.SnapTipTextStyle, iArr);
        this.mSnapTextSize = obtainStyledAttributes3.getDimensionPixelSize(obtainStyledAttributes3.getIndex(0), this.mSnapTextSize);
        this.mSnapTextColor = obtainStyledAttributes3.getColorStateList(obtainStyledAttributes3.getIndex(1));
    }

    public void onDraw(Canvas canvas) {
        int i = this.mCurrentType;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    Matrix matrix = this.mBitmapMatrix;
                    if (matrix == null) {
                        this.mBitmapMatrix = new Matrix();
                    } else {
                        matrix.reset();
                    }
                    if (!this.mCurrentImage.isRecycled()) {
                        this.mBitmapMatrix.postTranslate((float) ((getWidth() / 2) - (this.mCurrentImage.getWidth() / 2)), (float) ((getHeight() / 2) - (this.mCurrentImage.getHeight() / 2)));
                        canvas.drawBitmap(this.mCurrentImage, this.mBitmapMatrix, this.mImagePaint);
                    }
                }
            } else if (this.mCurrentText != null) {
                this.mTextPaint.setColor(this.mSnapTextColor.getColorForState(mTextActivatedColorState, 0));
                this.mTextPaint.setTextSize((float) this.mSnapTextSize);
                float measureText = this.mTextPaint.measureText(this.mCurrentText);
                float ascent = this.mTextPaint.ascent() + this.mTextPaint.descent();
                canvas.save();
                canvas.translate(0.0f, (float) (getHeight() / 2));
                canvas.drawText(this.mCurrentText, ((float) (getWidth() / 2)) - (measureText / 2.0f), ((-ascent) / 2.0f) + 1.0f, this.mTextPaint);
                canvas.restore();
            }
        } else if (this.mCurrentText != null) {
            this.mTextPaint.setColor(this.mNormalTextColor.getColorForState(mTextActivatedColorState, 0));
            this.mTextPaint.setTextSize((float) this.mNormalTextSize);
            String replaceAll = this.mCurrentText.replaceAll("X", "");
            float measureText2 = this.mTextPaint.measureText(replaceAll);
            float ascent2 = this.mTextPaint.ascent() + this.mTextPaint.descent();
            this.mXTextPaint.setColor(this.mXTextColor.getColorForState(mTextActivatedColorState, 0));
            this.mXTextPaint.setTextSize((float) this.mXTextSize);
            float measureText3 = this.mXTextPaint.measureText("X");
            float ascent3 = this.mXTextPaint.ascent() + this.mXTextPaint.descent();
            canvas.save();
            canvas.translate(0.0f, (float) (getHeight() / 2));
            canvas.drawText(replaceAll, ((float) (getWidth() / 2)) - ((measureText2 + measureText3) / 2.0f), ((-ascent2) / 2.0f) + 1.0f, this.mTextPaint);
            canvas.drawText("X", ((((float) getWidth()) + measureText2) - measureText3) / 2.0f, ((-ascent3) / 2.0f) + 1.0f, this.mXTextPaint);
            canvas.restore();
        }
    }

    public void setImage(Bitmap bitmap) {
        this.mCurrentType = 2;
        this.mCurrentImage = bitmap;
        invalidate();
    }

    public void setText(int i, String str) {
        this.mCurrentType = i;
        this.mCurrentText = str;
        invalidate();
    }
}
