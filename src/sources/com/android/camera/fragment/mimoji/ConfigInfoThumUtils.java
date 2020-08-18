package com.android.camera.fragment.mimoji;

import android.graphics.Bitmap;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import com.arcsoft.avatar.util.AvatarConfigUtils;
import com.arcsoft.avatar.util.LOG;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class ConfigInfoThumUtils {
    private static final String TAG = "ConfigInfoThumUtils";
    private final float[] mBeardStyleRegion = {522.0f, 628.0f, 160.0f, 326.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mEarShapeRegion = {1031.0f, 1240.0f, 179.0f, 543.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mEyeBrowSharpRegion = {528.0f, 635.0f, 164.0f, 209.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mEyeSharpRegion = {528.0f, 635.0f, 164.0f, 209.0f, 200.0f, 200.0f, 1.0f};
    private ArrayList<AvatarConfig.ASAvatarConfigInfo> mEyeWearList;
    private final float[] mEyeWearStyleRegin = {261.0f, 314.0f, 35.0f, 40.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mEyelashStyleRegion = {1306.0f, 1570.0f, 389.0f, 668.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mFaceSharpRegion = {261.0f, 314.0f, 35.0f, 40.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mFrecklesRegion = {601.0f, 723.0f, 201.0f, 274.0f, 200.0f, 200.0f, 1.0f};
    private ArrayList<AvatarConfig.ASAvatarConfigInfo> mHairList;
    private final float[] mHairStyleRegion = {270.0f, 325.0f, 23.0f, 37.0f, 220.0f, 220.0f, 1.0f};
    private final String mHat = "Hat";
    private final float[] mHatStyleRegion = {270.0f, 325.0f, 23.0f, 20.0f, 220.0f, 220.0f, 1.0f};
    private final String mHeadWear = "Hea";
    private final float[] mHeadWearStyleRegion = {422.0f, 510.0f, 98.0f, 15.0f, 220.0f, 220.0f, 0.8f};
    private final float[] mMouthSharpRegion = {1198.0f, 1442.0f, 499.0f, 784.0f, 200.0f, 200.0f, 1.0f};
    private final float[] mNevusRegion = {601.0f, 723.0f, 201.0f, 274.0f, 200.0f, 200.0f, 1.0f};
    private boolean mNoEyeWear = false;
    private boolean mNoHair = false;
    private final float[] mNoseShapeRegion = {737.0f, 887.0f, 270.0f, 378.0f, 200.0f, 200.0f, 1.0f};
    private float[] mTempRegion = new float[7];
    private final float[] mnNormalRegion = {200.0f, 200.0f, 0.0f, 0.0f, 200.0f, 200.0f, 1.0f};

    public void renderThumb(AvatarEngine avatarEngine, AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, int i, float[] fArr) {
        this.mNoHair = false;
        this.mNoEyeWear = false;
        LOG.d(TAG, "configType : " + aSAvatarConfigInfo.configType);
        int i2 = aSAvatarConfigInfo.configType;
        if (i2 == 1) {
            this.mTempRegion = this.mHairStyleRegion;
        } else if (i2 == 12) {
            this.mTempRegion = aSAvatarConfigInfo.name.substring(0, 3).equalsIgnoreCase("Hat") ? this.mHatStyleRegion : this.mHeadWearStyleRegion;
        } else if (i2 == 14) {
            this.mTempRegion = this.mBeardStyleRegion;
            this.mNoEyeWear = true;
            this.mNoHair = true;
        } else if (i2 == 18) {
            this.mTempRegion = this.mEyelashStyleRegion;
            this.mNoEyeWear = true;
        } else if (i2 == 7) {
            this.mTempRegion = this.mFrecklesRegion;
            this.mNoEyeWear = true;
        } else if (i2 == 8) {
            this.mTempRegion = this.mNevusRegion;
            this.mNoEyeWear = true;
        } else if (i2 != 9) {
            switch (i2) {
                case 20:
                    this.mTempRegion = this.mFaceSharpRegion;
                    break;
                case 21:
                    this.mTempRegion = this.mEyeSharpRegion;
                    this.mNoEyeWear = true;
                    break;
                case 22:
                    this.mTempRegion = this.mMouthSharpRegion;
                    this.mNoEyeWear = true;
                    break;
                case 23:
                    this.mTempRegion = this.mNoseShapeRegion;
                    this.mNoEyeWear = true;
                    break;
                case 24:
                    this.mTempRegion = this.mEarShapeRegion;
                    this.mNoHair = true;
                    this.mNoEyeWear = true;
                    break;
                case 25:
                    this.mTempRegion = this.mEyeBrowSharpRegion;
                    this.mNoEyeWear = true;
                    break;
                default:
                    this.mTempRegion = this.mnNormalRegion;
                    break;
            }
        } else {
            this.mTempRegion = this.mEyeWearStyleRegin;
        }
        if (this.mNoHair) {
            if (this.mHairList == null) {
                this.mHairList = avatarEngine.getConfig(1, i);
            }
            if (this.mHairList.size() > 0) {
                ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList = this.mHairList;
                avatarEngine.setConfig(arrayList.get(arrayList.size() - 1));
            }
        }
        if (this.mNoEyeWear) {
            if (this.mEyeWearList == null) {
                this.mEyeWearList = avatarEngine.getConfig(9, i);
            }
            if (this.mEyeWearList.size() > 0) {
                avatarEngine.setConfig(this.mEyeWearList.get(0));
            }
        }
        avatarEngine.setConfig(aSAvatarConfigInfo);
        float[] fArr2 = this.mTempRegion;
        int i3 = (int) fArr2[4];
        int i4 = (int) fArr2[5];
        byte[] bArr = new byte[(i3 * i4 * 4)];
        avatarEngine.renderThumb((int) fArr2[0], (int) fArr2[1], (int) fArr2[2], (int) fArr2[3], bArr, i3, i4, i3 * 4, fArr, fArr2[6]);
        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        aSAvatarConfigInfo.thum = createBitmap;
    }

    public void reset(AvatarEngine avatarEngine, AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue) {
        AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo;
        AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo2;
        if (this.mNoHair) {
            int currentConfigIdWithType = AvatarConfigUtils.getCurrentConfigIdWithType(1, aSAvatarConfigValue);
            if (currentConfigIdWithType == -1) {
                currentConfigIdWithType = 0;
            }
            Iterator<AvatarConfig.ASAvatarConfigInfo> it = this.mHairList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    aSAvatarConfigInfo2 = null;
                    break;
                }
                aSAvatarConfigInfo2 = it.next();
                if (aSAvatarConfigInfo2.configID == currentConfigIdWithType) {
                    break;
                }
            }
            if (aSAvatarConfigInfo2 != null) {
                avatarEngine.setConfig(aSAvatarConfigInfo2);
            }
        }
        if (this.mNoEyeWear) {
            int currentConfigIdWithType2 = AvatarConfigUtils.getCurrentConfigIdWithType(9, aSAvatarConfigValue);
            if (currentConfigIdWithType2 == -1) {
                currentConfigIdWithType2 = 0;
            }
            Iterator<AvatarConfig.ASAvatarConfigInfo> it2 = this.mEyeWearList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    aSAvatarConfigInfo = null;
                    break;
                }
                aSAvatarConfigInfo = it2.next();
                if (aSAvatarConfigInfo.configID == currentConfigIdWithType2) {
                    break;
                }
            }
            if (aSAvatarConfigInfo != null) {
                avatarEngine.setConfig(aSAvatarConfigInfo);
            }
        }
    }
}
