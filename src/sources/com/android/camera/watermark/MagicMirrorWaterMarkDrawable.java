package com.android.camera.watermark;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import java.util.List;

public class MagicMirrorWaterMarkDrawable extends BaseWaterMarkDrawable {
    private static final int MAGIC_MIRROR_RECT_COLOR = -18377;
    private Drawable mBeautyScoreIc;
    private Paint mFaceRectPaint;
    private int mHonPadding;
    private Drawable mMagicMirrorInfoPop;

    public MagicMirrorWaterMarkDrawable(List<WaterMarkData> list) {
        super(list);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public Paint getFaceRectPaint(WaterMarkData waterMarkData) {
        return this.mFaceRectPaint;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public int getHonPadding(WaterMarkData waterMarkData) {
        return this.mHonPadding;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public Drawable getTopBackgroundDrawable(WaterMarkData waterMarkData) {
        return this.mMagicMirrorInfoPop;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public Drawable getTopIndicatorDrawable(WaterMarkData waterMarkData) {
        return this.mBeautyScoreIc;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public void initBeforeDraw() {
        this.mMagicMirrorInfoPop = ((BaseWaterMarkDrawable) this).mContext.getResources().getDrawable(R.drawable.magic_mirror_info_pop);
        this.mBeautyScoreIc = ((BaseWaterMarkDrawable) this).mContext.getResources().getDrawable(R.drawable.ic_beauty_score);
        this.mFaceRectPaint = new Paint();
        this.mFaceRectPaint.setAntiAlias(true);
        this.mFaceRectPaint.setStrokeWidth((float) CameraAppImpl.getAndroidContext().getResources().getDimensionPixelSize(R.dimen.face_rect_width));
        this.mFaceRectPaint.setStyle(Paint.Style.STROKE);
        this.mFaceRectPaint.setColor(MAGIC_MIRROR_RECT_COLOR);
        ((BaseWaterMarkDrawable) this).mFacePopupBottom = (int) (((double) this.mMagicMirrorInfoPop.getIntrinsicHeight()) * 0.12d);
        this.mHonPadding = ((BaseWaterMarkDrawable) this).mContext.getResources().getDimensionPixelSize(R.dimen.face_info_female_hon_padding);
        ((BaseWaterMarkDrawable) this).mVerPadding = ((BaseWaterMarkDrawable) this).mContext.getResources().getDimensionPixelSize(R.dimen.face_info_ver_padding);
    }
}
