package com.android.camera.watermark;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.android.camera.R;
import java.util.List;

public class AgeGenderWaterMarkDrawable extends BaseWaterMarkDrawable {
    private static final int GENDER_FEMALE_RECT_COLOR = -1152383;
    private static final int GENDER_MALE_RECT_COLOR = -9455628;
    private Paint mFaceRectPaint;
    private Drawable mFemaleAgeInfoPop;
    private int mFemaleHonPadding;
    private Drawable mMaleAgeInfoPop;
    private int mMaleHonPadding;
    private Drawable mSexFemaleIc;
    private Drawable mSexMaleIc;

    public AgeGenderWaterMarkDrawable(List<WaterMarkData> list) {
        super(list);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public Paint getFaceRectPaint(WaterMarkData waterMarkData) {
        if (waterMarkData.isFemale()) {
            this.mFaceRectPaint.setColor(GENDER_FEMALE_RECT_COLOR);
        } else {
            this.mFaceRectPaint.setColor(GENDER_MALE_RECT_COLOR);
        }
        return this.mFaceRectPaint;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public int getHonPadding(WaterMarkData waterMarkData) {
        return waterMarkData.isFemale() ? this.mFemaleHonPadding : this.mMaleHonPadding;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public Drawable getTopBackgroundDrawable(WaterMarkData waterMarkData) {
        return waterMarkData.isFemale() ? this.mFemaleAgeInfoPop : this.mMaleAgeInfoPop;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public Drawable getTopIndicatorDrawable(WaterMarkData waterMarkData) {
        return waterMarkData.isFemale() ? this.mSexFemaleIc : this.mSexMaleIc;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.watermark.BaseWaterMarkDrawable
    public void initBeforeDraw() {
        this.mMaleAgeInfoPop = ((BaseWaterMarkDrawable) this).mContext.getResources().getDrawable(R.drawable.male_age_info_pop);
        this.mFemaleAgeInfoPop = ((BaseWaterMarkDrawable) this).mContext.getResources().getDrawable(R.drawable.female_age_info_pop);
        this.mSexMaleIc = ((BaseWaterMarkDrawable) this).mContext.getResources().getDrawable(R.drawable.ic_sex_male);
        this.mSexFemaleIc = ((BaseWaterMarkDrawable) this).mContext.getResources().getDrawable(R.drawable.ic_sex_female);
        this.mFaceRectPaint = new Paint();
        this.mFaceRectPaint.setAntiAlias(true);
        this.mFaceRectPaint.setStrokeWidth((float) ((BaseWaterMarkDrawable) this).mContext.getResources().getDimensionPixelSize(R.dimen.face_rect_width));
        this.mFaceRectPaint.setStyle(Paint.Style.STROKE);
        this.mFaceRectPaint.setColor(GENDER_MALE_RECT_COLOR);
        this.mMaleHonPadding = ((BaseWaterMarkDrawable) this).mContext.getResources().getDimensionPixelSize(R.dimen.face_info_male_hon_padding);
        this.mFemaleHonPadding = ((BaseWaterMarkDrawable) this).mContext.getResources().getDimensionPixelSize(R.dimen.face_info_female_hon_padding);
        ((BaseWaterMarkDrawable) this).mFacePopupBottom = (int) (((double) this.mMaleAgeInfoPop.getIntrinsicHeight()) * 0.12d);
    }
}
