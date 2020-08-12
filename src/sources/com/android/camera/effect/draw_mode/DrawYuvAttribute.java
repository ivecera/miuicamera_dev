package com.android.camera.effect.draw_mode;

import android.media.Image;
import android.util.Size;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.watermark.WaterMarkData;
import java.util.List;

public class DrawYuvAttribute extends DrawAttribute {
    public WatermarkItem mAIWatermarkItem;
    public boolean mApplyWaterMark;
    public EffectController.EffectRectAttribute mAttribute;
    public int mBlockHeight;
    public int mBlockWidth;
    public int[] mCoordinatesOfTheRegionUnderWatermarks = null;
    public byte[] mDataOfTheRegionUnderWatermarks = null;
    public long mDate;
    public int mEffectIndex;
    public Image mImage;
    public int mJpegQuality = 97;
    public int mJpegRotation;
    public boolean mMirror;
    public int mOffsetUV;
    public int mOffsetY;
    public int mOrientation;
    public Size mOriginalSize;
    public Size mOutputSize;
    public Size mPictureSize;
    public Size mPreviewSize;
    public float mShootRotation;
    public String mTiltShiftMode;
    public String mTimeWatermark;
    public List<WaterMarkData> mWaterInfos;
    public MiYuvImage mYuvImage;

    public DrawYuvAttribute(Image image, Size size, Size size2, int i, int i2, int i3, float f2, long j, boolean z, boolean z2, String str, String str2, EffectController.EffectRectAttribute effectRectAttribute, List<WaterMarkData> list, WatermarkItem watermarkItem) {
        this.mImage = image;
        this.mPreviewSize = size;
        this.mPictureSize = size2;
        this.mEffectIndex = i;
        this.mOrientation = i2;
        this.mJpegRotation = i3;
        this.mShootRotation = f2;
        this.mDate = j;
        this.mMirror = z;
        this.mApplyWaterMark = z2;
        this.mTiltShiftMode = str;
        this.mTimeWatermark = str2;
        this.mAttribute = effectRectAttribute;
        ((DrawAttribute) this).mTarget = 11;
        this.mWaterInfos = list;
        this.mAIWatermarkItem = watermarkItem;
    }

    public boolean isOutputSquare() {
        Size size = this.mOutputSize;
        return size != null && size.getWidth() == this.mOutputSize.getHeight();
    }
}
