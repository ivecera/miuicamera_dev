package com.android.camera.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseWaterMarkDrawable {
    private static final String TAG = "BaseWaterMarkDrawable";
    protected final Context mContext = CameraAppImpl.getAndroidContext();
    private int mCorrection;
    private Paint mFaceInfoNumberPaint;
    private Paint mFaceInfoTextPaint;
    protected int mFacePopupBottom;
    private int mGap;
    private int mHeight;
    private int mOrientation;
    protected int mPopBottomMargin;
    private Pattern mSplitFaceInfoPattern;
    protected int mVerPadding;
    private Bitmap mWaterMarkBitmap;
    private WaterMarkData mWaterMarkData;
    protected List<WaterMarkData> mWaterMarkInfos;
    private int mWidth;

    public BaseWaterMarkDrawable(List<WaterMarkData> list) {
        this.mWaterMarkInfos = list;
        List<WaterMarkData> list2 = this.mWaterMarkInfos;
        if (list2 != null && !list2.isEmpty()) {
            this.mWidth = this.mWaterMarkInfos.get(0).getFaceViewWidth();
            this.mHeight = this.mWaterMarkInfos.get(0).getFaceViewHeight();
            this.mOrientation = this.mWaterMarkInfos.get(0).getOrientation();
        }
        this.mFaceInfoTextPaint = new Paint();
        this.mFaceInfoTextPaint.setAntiAlias(true);
        this.mFaceInfoTextPaint.setColor(-1);
        this.mFaceInfoTextPaint.setTextSize(CameraAppImpl.getAndroidContext().getResources().getDimension(R.dimen.face_info_magic_textSize));
        this.mFaceInfoTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mFaceInfoTextPaint.setFakeBoldText(true);
        this.mGap = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelSize(R.dimen.face_info_text_left_dis);
        this.mCorrection = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelOffset(R.dimen.face_info_correction);
        this.mFaceInfoNumberPaint = new Paint(this.mFaceInfoTextPaint);
        this.mSplitFaceInfoPattern = Pattern.compile("(\\D+)|(\\d+\\.?\\d*)");
        this.mVerPadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.face_info_ver_padding);
        this.mGap = this.mContext.getResources().getDimensionPixelSize(R.dimen.face_info_text_left_dis);
        this.mCorrection = this.mContext.getResources().getDimensionPixelOffset(R.dimen.face_info_correction);
        this.mPopBottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.face_pop_bottom_margin);
        initBeforeDraw();
        draw();
    }

    private void draw() {
        List<WaterMarkData> list = this.mWaterMarkInfos;
        if (list != null && !list.isEmpty()) {
            this.mWaterMarkData = new WaterMarkData();
            this.mWaterMarkData.setWatermarkType(this.mWaterMarkInfos.get(0).getWatermarkType());
            this.mWaterMarkBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
            onDraw(new Canvas(this.mWaterMarkBitmap));
        }
    }

    private void drawFaceInfoText(Canvas canvas, String str, int i, int i2) {
        float f2;
        Matcher matcher = this.mSplitFaceInfoPattern.matcher(str);
        float f3 = (float) i;
        float f4 = (float) i2;
        while (matcher.find()) {
            String group = matcher.group();
            if (group.matches("\\d+\\.?\\d*")) {
                f2 = this.mFaceInfoNumberPaint.measureText(group);
                canvas.drawText(group, (f2 / 2.0f) + f3, f4, this.mFaceInfoNumberPaint);
            } else {
                f2 = this.mFaceInfoTextPaint.measureText(group);
                canvas.drawText(group, (f2 / 2.0f) + f3, f4, this.mFaceInfoTextPaint);
            }
            f3 += f2;
        }
    }

    public Bitmap getBitmap() {
        return this.mWaterMarkBitmap;
    }

    /* access modifiers changed from: protected */
    public abstract Paint getFaceRectPaint(WaterMarkData waterMarkData);

    /* access modifiers changed from: protected */
    public abstract int getHonPadding(WaterMarkData waterMarkData);

    /* access modifiers changed from: protected */
    public abstract Drawable getTopBackgroundDrawable(WaterMarkData waterMarkData);

    /* access modifiers changed from: protected */
    public abstract Drawable getTopIndicatorDrawable(WaterMarkData waterMarkData);

    public WaterMarkData getWaterMarkData() {
        return this.mWaterMarkData;
    }

    /* access modifiers changed from: protected */
    public abstract void initBeforeDraw();

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        long currentTimeMillis = System.currentTimeMillis();
        Log.e(TAG, "start make water mark.");
        canvas.save();
        canvas.rotate((float) (-this.mOrientation));
        for (Iterator it = new ArrayList(this.mWaterMarkInfos).iterator(); it.hasNext(); it = it) {
            WaterMarkData waterMarkData = (WaterMarkData) it.next();
            canvas.drawRoundRect(waterMarkData.getFaceRectF(), waterMarkData.getFaceRectF().width() * 0.015f, waterMarkData.getFaceRectF().height() * 0.015f, getFaceRectPaint(waterMarkData));
            Matcher matcher = this.mSplitFaceInfoPattern.matcher(waterMarkData.getInfo());
            float f2 = 0.0f;
            while (matcher.find()) {
                String group = matcher.group();
                f2 += group.matches("\\d+\\.?\\d*") ? this.mFaceInfoNumberPaint.measureText(group) : this.mFaceInfoTextPaint.measureText(group);
            }
            int honPadding = getHonPadding(waterMarkData);
            int intrinsicHeight = (int) ((((float) this.mVerPadding) * 3.6f) + ((float) getTopIndicatorDrawable(waterMarkData).getIntrinsicHeight()));
            int intrinsicWidth = ((int) ((((float) ((getTopIndicatorDrawable(waterMarkData).getIntrinsicWidth() + honPadding) + this.mGap)) + f2) + ((float) honPadding))) / 2;
            Rect rect = new Rect(((int) waterMarkData.getFaceRectF().centerX()) - intrinsicWidth, ((((int) waterMarkData.getFaceRectF().top) - intrinsicHeight) - this.mPopBottomMargin) - this.mFacePopupBottom, ((int) waterMarkData.getFaceRectF().centerX()) + intrinsicWidth, ((int) waterMarkData.getFaceRectF().top) - this.mPopBottomMargin);
            if (getTopBackgroundDrawable(waterMarkData) != null) {
                getTopBackgroundDrawable(waterMarkData).setBounds(rect);
                getTopBackgroundDrawable(waterMarkData).draw(canvas);
            }
            Bitmap createBitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap);
            Rect rect2 = new Rect();
            rect2.set(honPadding, (int) ((((float) this.mVerPadding) * 1.8f) - ((float) this.mCorrection)), getTopIndicatorDrawable(waterMarkData).getIntrinsicWidth() + honPadding, (int) (((((float) this.mVerPadding) * 1.8f) - ((float) this.mCorrection)) + ((float) getTopIndicatorDrawable(waterMarkData).getIntrinsicHeight())));
            getTopIndicatorDrawable(waterMarkData).setBounds(rect2);
            getTopIndicatorDrawable(waterMarkData).draw(canvas2);
            if (f2 != 0.0f) {
                Paint.FontMetricsInt fontMetricsInt = this.mFaceInfoTextPaint.getFontMetricsInt();
                drawFaceInfoText(canvas2, waterMarkData.getInfo(), rect2.right + this.mGap, (((rect2.bottom + rect2.top) - fontMetricsInt.bottom) - fontMetricsInt.top) / 2);
            }
            if (!CameraSettings.isFrontMirror()) {
                createBitmap = Util.flipBitmap(createBitmap, 0);
            }
            canvas.drawBitmap(createBitmap, (float) (((int) waterMarkData.getFaceRectF().centerX()) - intrinsicWidth), (float) (((((int) waterMarkData.getFaceRectF().top) - intrinsicHeight) - this.mPopBottomMargin) - this.mFacePopupBottom), (Paint) null);
        }
        canvas.restore();
        this.mWaterMarkData.setOrientation(this.mOrientation);
        if (!CameraSettings.isFrontMirror()) {
            int i = this.mOrientation;
            if (i == 90 || i == 270) {
                this.mWaterMarkBitmap = Util.flipBitmap(this.mWaterMarkBitmap, 1);
            } else {
                this.mWaterMarkBitmap = Util.flipBitmap(this.mWaterMarkBitmap, 0);
            }
        }
        this.mWaterMarkData.setImage(this.mWaterMarkBitmap);
        String str = TAG;
        Log.e(str, "end make water mark...time consuming：" + (System.currentTimeMillis() - currentTimeMillis));
    }

    public void setWaterMarkInfos(List<WaterMarkData> list) {
        this.mWaterMarkInfos = list;
    }
}
