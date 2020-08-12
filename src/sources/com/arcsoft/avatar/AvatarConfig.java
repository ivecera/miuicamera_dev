package com.arcsoft.avatar;

import android.graphics.Bitmap;
import com.arcsoft.avatar.util.LOG;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

public interface AvatarConfig {

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ASAvatarConfigComponentType {
        public static final int BEARD_COLOR = 15;
        public static final int BEARD_STYLE = 14;
        public static final int CUSTOM_EXPRESSION = 27;
        public static final int EARRING_COLOR = 17;
        public static final int EARRING_STYLE = 16;
        public static final int EAR_SHAPE = 24;
        public static final int EYEBROW_COLOR = 19;
        public static final int EYEBROW_SHAPE = 25;
        public static final int EYELASH_STYLE = 18;
        public static final int EYEWEAR_FRAME = 10;
        public static final int EYEWEAR_LENSES = 11;
        public static final int EYEWEAR_STYLE = 9;
        public static final int EYE_COLOR = 4;
        public static final int EYE_SHAPE = 21;
        public static final int FACE_COLOR = 3;
        public static final int FACE_SHAPE = 20;
        public static final int FRECKLES = 7;
        public static final int GENDER = 26;
        public static final int HAIR_COLOR = 2;
        public static final int HAIR_HIGHLIGHT_COLOR = 6;
        public static final int HAIR_STYLE = 1;
        public static final int HEADWEAR_COLOR = 13;
        public static final int HEADWEAR_STYLE = 12;
        public static final int LIP_COLOR = 5;
        public static final int MOUTH_SHAPE = 22;
        public static final int NEVUS = 8;
        public static final int NONE = 0;
        public static final int NOSE_SHAPE = 23;
    }

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ASAvatarConfigGenderType {
        public static final int FEMALE = 2;
        public static final int MALE = 1;
        public static final int UNKNOWN = 0;
    }

    public static class ASAvatarConfigInfo {
        public int configID;
        public String configThumbPath;
        public int configType;
        public float continuousValue;
        public int endColorValue;
        public int gender;
        public boolean isDefault;
        public boolean isSupportContinuous;
        public boolean isValid;
        public String name;
        public int startColorValue;
        public Bitmap thum;

        public String toString() {
            return "configID = " + this.configID + " configType = " + this.configType + " gender = " + this.gender + " name = " + this.name + " configThumbPath = " + this.configThumbPath + " isDefault = " + this.isDefault + " isValid = " + this.isValid + " isSupportContinuous = " + this.isSupportContinuous + " continuousValue = " + this.continuousValue + " startColorValue = " + this.startColorValue + " endColorValue = " + this.endColorValue + "thum = " + this.thum + "\n";
        }
    }

    public static class ASAvatarConfigType {
        public int configType;
        public String configTypeDesc;
        public boolean refreshThum = true;

        public String toString() {
            return "configTypeDesc = " + this.configTypeDesc + " configType = " + this.configType + " refreshThum = " + this.refreshThum;
        }
    }

    public static class ASAvatarConfigValue implements Cloneable {
        public int configBeardColorID;
        public float configBeardColorValue;
        public int configBeardStyleID;
        public int configCustomExpressionID;
        public int configEarShapeID;
        public float configEarShapeValue;
        public int configEarringColorID;
        public float configEarringColorValue;
        public int configEarringStyleID;
        public int configEyeColorID;
        public float configEyeColorValue;
        public int configEyeShapeID;
        public float configEyeShapeValue;
        public int configEyebrowColorID;
        public float configEyebrowColorValue;
        public int configEyebrowShapeID;
        public float configEyebrowShapeValue;
        public int configEyelashStyleID;
        public int configEyewearFrameID;
        public float configEyewearFrameValue;
        public int configEyewearLensesID;
        public float configEyewearLensesValue;
        public int configEyewearStyleID;
        public int configFaceColorID;
        public float configFaceColorValue;
        public int configFaceShapeID;
        public float configFaceShapeValue;
        public int configFrecklesID;
        public int configGenderID;
        public int configHairColorID;
        public float configHairColorValue;
        public int configHairHighlightColorID;
        public float configHairHighlightColorValue;
        public int configHairStyleID;
        public int configHeadwearColorID;
        public float configHeadwearColorValue;
        public int configHeadwearStyleID;
        public int configLipColorID;
        public float configLipColorValue;
        public int configMouthShapeID;
        public float configMouthShapeValue;
        public int configNevusID;
        public int configNoseShapeID;
        public float configNoseShapeValue;
        public int gender;

        @Override // java.lang.Object
        public Object clone() {
            try {
                return (ASAvatarConfigValue) super.clone();
            } catch (CloneNotSupportedException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        public String toString() {
            return "gender = " + this.gender + " hairStyleID = " + this.configHairStyleID + " hairColorID = " + this.configHairColorID + " hairColorValue = " + this.configHairColorValue + " faceColorID = " + this.configFaceColorID + " faceColorValue = " + this.configFaceColorValue + " eyeColorID = " + this.configEyeColorID + " eyeColorValue = " + this.configEyeColorValue + " lipColorID = " + this.configLipColorID + " lipColorValue = " + this.configLipColorValue + " hairHighlightColorID = " + this.configHairHighlightColorID + " hairHighlightColorValue = " + this.configHairHighlightColorValue + " frecklesID = " + this.configFrecklesID + " nevusID = " + this.configNevusID + " eyewearStyleID = " + this.configEyewearStyleID + " eyewearFrameID = " + this.configEyewearFrameID + " eyewearFrameValue = " + this.configEyewearFrameValue + " eyewearLensesID = " + this.configEyewearLensesID + " eyewearLensesValue = " + this.configEyewearLensesValue + " headwearStyleID = " + this.configHeadwearStyleID + " headwearColorID = " + this.configHeadwearColorID + " headwearColorValue = " + this.configHeadwearColorValue + " beardStyleID = " + this.configBeardStyleID + " beardColorID = " + this.configBeardColorID + " beardColorValue = " + this.configBeardColorValue + " earringStyleID = " + this.configEarringStyleID + " eyelashStyleID = " + this.configEyelashStyleID + " eyebrowColorID = " + this.configEyebrowColorID + " eyebrowColorValue = " + this.configEyebrowColorValue + " faceShapeID = " + this.configFaceShapeID + " faceShapeValue = " + this.configFaceShapeValue + " eyeShapeID = " + this.configEyeShapeID + " eyeShapeValue = " + this.configEyeShapeValue + " mouthShapeID = " + this.configMouthShapeID + " mouthShapeValue = " + this.configMouthShapeValue + " configEarringColorValue = " + this.configEarringColorValue + " configEarringColorID = " + this.configEarringColorID + " noseShapeID = " + this.configNoseShapeID + " noseShapeValue = " + this.configNoseShapeValue + " earShapeID = " + this.configEarShapeID + " earShapeValue = " + this.configEarShapeValue + " eyebrowShapeID = " + this.configEyebrowShapeID + " eyebrowShapeValue = " + this.configEyebrowShapeValue + "\n";
        }
    }

    public interface ASAvatarOutLineStatusCode {
        public static final int STATUS_FACE_BEYOND_20_DEGREES = 9;
        public static final int STATUS_FACE_OCCLUSION = 6;
        public static final int STATUS_FACE_TOO_BIG = 7;
        public static final int STATUS_FACE_TOO_SMALL = 8;
        public static final int STATUS_LEFT_EYES_OCCLUSION = 2;
        public static final int STATUS_MOUTH_OCCLUSION = 4;
        public static final int STATUS_MULTIPLE_FACES = 10;
        public static final int STATUS_NORMAL = 0;
        public static final int STATUS_NOSE_OCCLUSION = 5;
        public static final int STATUS_NO_FACE = 1;
        public static final int STATUS_RIGHT_EYES_OCCLUSION = 3;
    }

    public static class ASAvatarProcessInfo {
        private static final float F_THRESHOLD = 0.5f;
        private static final int Max_Express_Num = 569;
        private static final int Max_Outline_Num = 154;
        private static final float OUTLINE_THRESHOLD_VALUE = 0.8f;
        private float drumStatus;
        private float[] expWeights;
        private int eyeBlink;
        private int eyeBrowRaise;
        private ASRect face;
        private int faceCount;
        private float[] faceOrientations;
        private int headPoseLr;
        private boolean isMirror;
        private int mouthOpen;
        private int nodHead;
        private int orientation;
        private float[] orientationLeftEyes;
        private float[] orientationRightEyes;
        private float[] orientations;
        private ASPointF[] outlines;
        private int pitchStatus;
        private int processHeight;
        private int processWidth;
        private int result;
        private int rollStatus;
        private float[] shelterFlags;
        private int tongueStatus;
        private int yawStatus;
        private float zoomInScale;

        public ASAvatarProcessInfo() {
            this.outlines = new ASPointF[154];
            this.mouthOpen = 0;
            this.eyeBlink = 0;
            this.eyeBrowRaise = 0;
            this.nodHead = 0;
            this.headPoseLr = 0;
            this.yawStatus = 0;
            this.rollStatus = 0;
            this.pitchStatus = 0;
        }

        public ASAvatarProcessInfo(int i, int i2, int i3, boolean z) {
            this.processHeight = i2;
            this.processWidth = i;
            this.orientation = i3;
            this.isMirror = z;
            this.shelterFlags = new float[154];
            this.outlines = new ASPointF[154];
            for (int i4 = 0; i4 < 154; i4++) {
                this.outlines[i4] = new ASPointF();
            }
            this.face = new ASRect();
            this.faceOrientations = new float[3];
            this.result = 0;
            this.faceCount = 1;
            this.zoomInScale = 1.0f;
            this.mouthOpen = 0;
            this.eyeBlink = 0;
            this.eyeBrowRaise = 0;
            this.nodHead = 0;
            this.headPoseLr = 0;
            this.yawStatus = 0;
            this.rollStatus = 0;
            this.pitchStatus = 0;
        }

        public static int getMaxExpressNum() {
            return Max_Express_Num;
        }

        public boolean checkFaceBlocking() {
            float f2 = 0.0f;
            int i = 0;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            float f9 = 0.0f;
            float f10 = 0.0f;
            while (true) {
                float[] fArr = this.shelterFlags;
                if (i >= fArr.length) {
                    break;
                }
                if (i >= 0 && i <= 18) {
                    f7 += fArr[i];
                } else if (i >= 19 && i <= 36) {
                    f8 += this.shelterFlags[i];
                } else if (i >= 37 && i <= 46) {
                    f9 += this.shelterFlags[i];
                } else if (i >= 47 && i <= 56) {
                    f10 += this.shelterFlags[i];
                } else if (i >= 57 && i <= 68) {
                    f3 += this.shelterFlags[i];
                } else if (i >= 69 && i <= 80) {
                    f4 += this.shelterFlags[i];
                } else if (i >= 81 && i <= 92) {
                    f5 += this.shelterFlags[i];
                } else if (i >= 93 && i <= 112) {
                    f6 += this.shelterFlags[i];
                }
                i++;
            }
            for (int i2 = 7; i2 <= 29; i2++) {
                f2 += this.shelterFlags[i2];
            }
            float f11 = f7 / 19.0f;
            float f12 = f8 / 18.0f;
            float f13 = f9 / 10.0f;
            float f14 = f10 / 10.0f;
            float f15 = f3 / 12.0f;
            float f16 = f4 / 12.0f;
            float f17 = f5 / 12.0f;
            float f18 = f6 / 20.0f;
            float f19 = f2 / 23.0f;
            if (f11 > 0.5f && f13 > 0.5f && f15 > 0.5f) {
                return true;
            }
            if (f12 > 0.5f && f14 > 0.5f && f16 > 0.5f) {
                return true;
            }
            if (f13 > 0.4f && f14 > 0.4f && f16 > 0.4f && f15 > 0.4f) {
                return true;
            }
            int i3 = (f19 > 0.4f ? 1 : (f19 == 0.4f ? 0 : -1));
            if (i3 <= 0 || f18 <= 0.4f || f17 <= 0.4f) {
                return f11 > 0.4f && f12 > 0.4f && i3 > 0;
            }
            return true;
        }

        public int checkOutLineInfo() {
            int i;
            float[] fArr = this.faceOrientations;
            float f2 = fArr[0];
            float f3 = fArr[1];
            int i2 = 2;
            float f4 = fArr[2];
            if (((f2 < -110.0f || f2 > -70.0f) && ((f2 < -20.0f || f2 > 20.0f) && ((f2 < 160.0f || f2 > 180.0f) && ((f2 < -180.0f || f2 > -160.0f) && (f2 < 70.0f || f2 > 110.0f))))) || -20.0f > f3 || f3 > 20.0f || -20.0f > f4 || f4 > 20.0f) {
                return 9;
            }
            float f5 = 0.0f;
            float f6 = 0.0f;
            for (int i3 = 0; i3 <= 36; i3++) {
                f6 += this.shelterFlags[i3];
            }
            float f7 = f6 / 36.0f;
            LOG.d("CheckOutLine", "fFaceValue = " + f7);
            if (f7 > OUTLINE_THRESHOLD_VALUE) {
                return 6;
            }
            float f8 = 0.0f;
            for (int i4 = 69; i4 <= 80; i4++) {
                f8 += this.shelterFlags[i4];
            }
            float f9 = f8 / 12.0f;
            LOG.d("CheckOutLine", "fLeftEyeValue = " + f9);
            float f10 = 0.0f;
            for (int i5 = 57; i5 <= 68; i5++) {
                f10 += this.shelterFlags[i5];
            }
            float f11 = f10 / 12.0f;
            LOG.d("CheckOutLine", "fRightEyeValue = " + f11);
            if (f11 > f9) {
                i2 = 3;
            } else {
                f11 = f9;
            }
            float f12 = 0.0f;
            for (int i6 = 93; i6 <= 112; i6++) {
                f12 += this.shelterFlags[i6];
            }
            float f13 = f12 / 20.0f;
            LOG.d("CheckOutLine", "fMouthEyeValue = " + f13);
            if (f13 > f11) {
                i2 = 4;
                f11 = f13;
            }
            for (int i7 = 81; i7 <= 119; i7++) {
                f5 += this.shelterFlags[i7];
            }
            float f14 = f5 / 39.0f;
            LOG.d("CheckOutLine", "fNOSEEyeValue = " + f14);
            if (f14 > f11) {
                i = 5;
            } else {
                f14 = f11;
                i = i2;
            }
            LOG.d("CheckOutLine", "fMax = " + f14 + " res = " + i);
            if (f14 > OUTLINE_THRESHOLD_VALUE) {
                return i;
            }
            return 0;
        }

        public float[] getExpWeights() {
            return this.expWeights;
        }

        public int getEyeBlink() {
            return this.eyeBlink;
        }

        public int getFaceCount() {
            return this.faceCount;
        }

        public int getHeadPoseLr() {
            return this.headPoseLr;
        }

        public int getHeadRollStatus() {
            return this.rollStatus;
        }

        public int getMouthOpen() {
            return this.mouthOpen;
        }

        public float[] getOrientationLeftEyes() {
            return this.orientationLeftEyes;
        }

        public float[] getOrientationRightEyes() {
            return this.orientationRightEyes;
        }

        public float[] getOrientations() {
            return this.orientations;
        }

        public int getPitchStatus() {
            return this.pitchStatus;
        }

        public String getString() {
            return "mouthOpen = " + this.mouthOpen + ", eyeBlink = " + this.eyeBlink + ", eyeBrowRaise = " + this.eyeBrowRaise + ", nodHead = " + this.nodHead + ", headPoseLr = " + this.headPoseLr + ", yawStatus = " + this.yawStatus + ", rollStatus = " + this.rollStatus + ", pitchStatus = " + this.pitchStatus;
        }

        public int getTongueStatus() {
            return this.tongueStatus;
        }

        public void setEmpty() {
            this.processHeight = 0;
            this.processWidth = 0;
            this.orientation = 0;
            this.isMirror = false;
            this.faceCount = 0;
            ASPointF[] aSPointFArr = this.outlines;
            for (ASPointF aSPointF : aSPointFArr) {
                aSPointF.x = 0.0f;
                aSPointF.y = 0.0f;
            }
            ASRect aSRect = this.face;
            aSRect.bottom = 0;
            aSRect.right = 0;
            aSRect.top = 0;
            aSRect.left = 0;
            Arrays.fill(this.faceOrientations, 0.0f);
            this.result = 0;
            Arrays.fill(this.orientations, 0.0f);
            Arrays.fill(this.orientationLeftEyes, 0.0f);
            Arrays.fill(this.orientationRightEyes, 0.0f);
            Arrays.fill(this.expWeights, 0.0f);
            this.zoomInScale = 0.0f;
        }

        public void setExpWeights(float[] fArr) {
            this.expWeights = fArr;
        }

        public void setOrientationLeftEyes(float[] fArr) {
            this.orientationLeftEyes = fArr;
        }

        public void setOrientationRightEyes(float[] fArr) {
            this.orientationRightEyes = fArr;
        }

        public void setOrientations(float[] fArr) {
            this.orientations = fArr;
        }

        public void setTongueStatus(int i) {
            this.tongueStatus = i;
        }

        public boolean shelterIsNull() {
            return this.shelterFlags == null;
        }
    }

    public static class ASAvatarProfileInfo implements Serializable {
        private int eyeShape;
        private int faceShape;
        private int gender;
        private int glassType;
        private byte[] hairColor;
        private int hairType;
        private int hasFringe;
        private int mouthShape;
        private int noseShape;
        private byte[] skinColor;
        private int skinColorScale;

        public String getHairType() {
            String str;
            switch (this.hairType) {
                case 0:
                    str = "光寸头";
                    break;
                case 1:
                    str = "直短发";
                    break;
                case 2:
                    str = "卷短发";
                    break;
                case 3:
                    str = "丸子马尾";
                    break;
                case 4:
                    str = "哪吒头";
                    break;
                case 5:
                    str = "直中短发";
                    break;
                case 6:
                    str = "卷中短发";
                    break;
                case 7:
                    str = "直中发";
                    break;
                case 8:
                    str = "卷中发";
                    break;
                case 9:
                    str = "直长发";
                    break;
                case 10:
                    str = "卷长发";
                    break;
                case 11:
                    str = "双马尾";
                    break;
                case 12:
                    str = "双麻花辫";
                    break;
                default:
                    str = "unknow";
                    break;
            }
            return "Hair Type = " + str;
        }

        public String getHasFringe() {
            return this.hasFringe == 0 ? "无" : "有";
        }

        public String toString() {
            return "gender = " + this.gender + "\nfaceShape = " + this.faceShape + "\neyeShape = " + this.eyeShape + "\nmouthShape = " + this.mouthShape + "\nnoseShape = " + this.noseShape + "\nhairType = " + this.hairType + "\nhasFringe = " + this.hasFringe + "\nhairColor = " + Arrays.toString(this.hairColor) + "\nskinColor = " + Arrays.toString(this.skinColor) + "\nskinColorScale = " + this.skinColorScale + "\nglassType = " + this.glassType;
        }
    }

    public static class ASAvatarProfileResult implements Serializable {
        public int gender;
        public int status;
    }

    public interface ASAvatarProfileStatusCode {
        public static final int STATUS_FAILED_NOFACE = 1;
        public static final int STATUS_SUCCESS_FACESHAPE = 128;
        public static final int STATUS_SUCCESS_FACIAL = 2;
        public static final int STATUS_SUCCESS_GENDER = 16;
        public static final int STATUS_SUCCESS_GLASS = 64;
        public static final int STATUS_SUCCESS_HAIRCOLOR = 8;
        public static final int STATUS_SUCCESS_HAIRSTYLE = 4;
        public static final int STATUS_SUCCESS_SKINCOLOR = 32;
        public static final int STATUS_UNKNOWN = 0;
    }

    public static class ASAvatarTongueAnimationParam {
        private float fBackSlowLevel;
        private float fLevel;
        private float fMouthExpressionBak;
        private int nTongueAnimationStatus;
        private int nTongueStatus;

        public ASAvatarTongueAnimationParam(int i, float f2, float f3, int i2, float f4) {
            this.nTongueStatus = i;
            this.fLevel = f2;
            this.fBackSlowLevel = f3;
            this.nTongueAnimationStatus = i2;
            this.fMouthExpressionBak = f4;
        }
    }

    public static class ASPointF {
        public float x;
        public float y;
    }

    public static class ASRect {
        public int bottom;
        public int left;
        public int right;
        public int top;
    }

    public interface GetConfigCallback {
        void onGetConfig(int i, int i2, int i3, int i4, String str, String str2, int i5, int i6, boolean z, boolean z2, boolean z3, float f2);
    }

    public interface GetSupportConfigTypeCallback {
        void onGetSupportConfigType(String str, int i);
    }

    public interface UpdateProgressCallback {
        void onUpdateProgress(int i);
    }
}
