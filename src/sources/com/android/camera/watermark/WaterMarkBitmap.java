package com.android.camera.watermark;

import android.graphics.Bitmap;
import com.android.camera.log.Log;
import java.util.List;

public class WaterMarkBitmap {
    private static final String TAG = "WaterMarkBitmap";
    private List<WaterMarkData> mWaterInfos;
    private WaterMarkData mWaterMarkData = generateWaterMarkData();
    private BaseWaterMarkDrawable mWaterMarkDrawable;

    public WaterMarkBitmap(List<WaterMarkData> list) {
        this.mWaterInfos = list;
    }

    public WaterMarkData generateWaterMarkData() {
        List<WaterMarkData> list = this.mWaterInfos;
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "The watermark data is empty.");
            return null;
        }
        int watermarkType = this.mWaterInfos.get(0).getWatermarkType();
        if (watermarkType == 1) {
            this.mWaterMarkDrawable = new MagicMirrorWaterMarkDrawable(this.mWaterInfos);
            return this.mWaterMarkDrawable.getWaterMarkData();
        } else if (watermarkType != 2) {
            String str = TAG;
            Log.w(str, "unexpected watermark type " + watermarkType);
            return null;
        } else {
            this.mWaterMarkDrawable = new AgeGenderWaterMarkDrawable(this.mWaterInfos);
            return this.mWaterMarkDrawable.getWaterMarkData();
        }
    }

    public WaterMarkData getWaterMarkData() {
        return this.mWaterMarkData;
    }

    public void releaseBitmap() {
        Bitmap bitmap;
        BaseWaterMarkDrawable baseWaterMarkDrawable = this.mWaterMarkDrawable;
        if (baseWaterMarkDrawable != null && (bitmap = baseWaterMarkDrawable.getBitmap()) != null) {
            bitmap.recycle();
        }
    }
}
