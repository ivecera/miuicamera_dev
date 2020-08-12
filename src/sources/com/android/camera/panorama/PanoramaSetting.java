package com.android.camera.panorama;

import android.content.Context;
import android.util.JsonReader;
import com.android.camera.log.Log;
import java.io.IOException;

public class PanoramaSetting {
    private static final boolean DEBUG = false;
    public static final String KEY_AOVX = "aovx";
    public static final String KEY_AOVY = "aovy";
    public static final String KEY_AOV_GAIN = "aov_gain";
    public static final String KEY_CALCSEAM_PIXNUM = "calcseam_pixnum";
    public static final String KEY_DISTORTION_K1 = "distortion_k1";
    public static final String KEY_DISTORTION_K2 = "distortion_k2";
    public static final String KEY_DISTORTION_K3 = "distortion_k3";
    public static final String KEY_DISTORTION_K4 = "distortion_k4";
    public static final String KEY_DRAW_THRESHOLD = "draw_threshold";
    public static final String KEY_ROTATION_RATIO = "rotation_ratio";
    public static final String KEY_SEAMSEARCH_RATIO = "seamsearch_ratio";
    public static final String KEY_SHRINK_RATIO = "shrink_ratio";
    public static final String KEY_USE_DEFORM = "use_deform";
    public static final String KEY_USE_LUMINANCE_CORRECTION = "use_luminance_correction";
    public static final String KEY_ZROTATION_COEFF = "zrotation_coeff";
    private static final String TAG = "PanoramaSetting";
    private double aov_gain = 1.0d;
    private double aovx = 70.4000015258789d;
    private double aovy = 55.70000076293945d;
    private int calcseam_pixnum = 32400;
    private double distortion_k1 = 0.0d;
    private double distortion_k2 = 0.0d;
    private double distortion_k3 = 0.0d;
    private double distortion_k4 = 0.0d;
    private double draw_threshold = 0.5d;
    private int motion_detection_mode = 0;
    private int projection_mode = 0;
    private double rotation_ratio = 1.0d;
    private double seamsearch_ratio = 1.0d;
    private float shrink_ratio = 7.5f;
    private boolean use_deform = false;
    private boolean use_luminance_correction = true;
    private double zrotation_coeff = 0.95d;

    public PanoramaSetting(Context context) {
        Log.d(TAG, toString());
    }

    private void parseSetting(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String nextName = jsonReader.nextName();
            Log.d(TAG, "read key " + nextName);
            char c2 = 65535;
            try {
                switch (nextName.hashCode()) {
                    case -1913048451:
                        if (nextName.equals(KEY_USE_DEFORM)) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case -1861106499:
                        if (nextName.equals(KEY_USE_LUMINANCE_CORRECTION)) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case -1476979982:
                        if (nextName.equals(KEY_ZROTATION_COEFF)) {
                            c2 = 7;
                            break;
                        }
                        break;
                    case -701256694:
                        if (nextName.equals(KEY_ROTATION_RATIO)) {
                            c2 = 14;
                            break;
                        }
                        break;
                    case -591575248:
                        if (nextName.equals(KEY_DRAW_THRESHOLD)) {
                            c2 = '\b';
                            break;
                        }
                        break;
                    case -234320299:
                        if (nextName.equals(KEY_SHRINK_RATIO)) {
                            c2 = 2;
                            break;
                        }
                        break;
                    case 3000176:
                        if (nextName.equals(KEY_AOVX)) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 3000177:
                        if (nextName.equals(KEY_AOVY)) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 15721843:
                        if (nextName.equals(KEY_CALCSEAM_PIXNUM)) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case 107805618:
                        if (nextName.equals(KEY_SEAMSEARCH_RATIO)) {
                            c2 = 6;
                            break;
                        }
                        break;
                    case 451710806:
                        if (nextName.equals(KEY_AOV_GAIN)) {
                            c2 = '\t';
                            break;
                        }
                        break;
                    case 617635848:
                        if (nextName.equals(KEY_DISTORTION_K1)) {
                            c2 = '\n';
                            break;
                        }
                        break;
                    case 617635849:
                        if (nextName.equals(KEY_DISTORTION_K2)) {
                            c2 = 11;
                            break;
                        }
                        break;
                    case 617635850:
                        if (nextName.equals(KEY_DISTORTION_K3)) {
                            c2 = '\f';
                            break;
                        }
                        break;
                    case 617635851:
                        if (nextName.equals(KEY_DISTORTION_K4)) {
                            c2 = '\r';
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        this.aovx = jsonReader.nextDouble();
                        continue;
                    case 1:
                        this.aovy = jsonReader.nextDouble();
                        continue;
                    case 2:
                        this.shrink_ratio = (float) jsonReader.nextDouble();
                        continue;
                    case 3:
                        this.calcseam_pixnum = jsonReader.nextInt();
                        continue;
                    case 4:
                        this.use_deform = jsonReader.nextBoolean();
                        continue;
                    case 5:
                        this.use_luminance_correction = jsonReader.nextBoolean();
                        continue;
                    case 6:
                        this.seamsearch_ratio = jsonReader.nextDouble();
                        continue;
                    case 7:
                        this.zrotation_coeff = jsonReader.nextDouble();
                        continue;
                    case '\b':
                        this.draw_threshold = jsonReader.nextDouble();
                        continue;
                    case '\t':
                        this.aov_gain = jsonReader.nextDouble();
                        continue;
                    case '\n':
                        this.distortion_k1 = jsonReader.nextDouble();
                        continue;
                    case 11:
                        this.distortion_k2 = jsonReader.nextDouble();
                        continue;
                    case '\f':
                        this.distortion_k3 = jsonReader.nextDouble();
                        continue;
                    case '\r':
                        this.distortion_k4 = jsonReader.nextDouble();
                        continue;
                    case 14:
                        this.rotation_ratio = jsonReader.nextDouble();
                        continue;
                    default:
                        jsonReader.skipValue();
                        continue;
                }
            } catch (Exception unused) {
                Log.d(TAG, "parse error, name = " + nextName);
            }
        }
        jsonReader.endObject();
    }

    public double getAov_gain() {
        return this.aov_gain;
    }

    public double getAovx() {
        return this.aovx;
    }

    public double getAovy() {
        return this.aovy;
    }

    public int getCalcseam_pixnum() {
        return this.calcseam_pixnum;
    }

    public double getDistortion_k1() {
        return this.distortion_k1;
    }

    public double getDistortion_k2() {
        return this.distortion_k2;
    }

    public double getDistortion_k3() {
        return this.distortion_k3;
    }

    public double getDistortion_k4() {
        return this.distortion_k4;
    }

    public double getDraw_threshold() {
        return this.draw_threshold;
    }

    public int getMotion_detection_mode() {
        return this.motion_detection_mode;
    }

    public int getProjection_mode() {
        return this.projection_mode;
    }

    public double getRotation_ratio() {
        return this.rotation_ratio;
    }

    public double getSeamsearch_ratio() {
        return this.seamsearch_ratio;
    }

    public float getShrink_ratio() {
        return this.shrink_ratio;
    }

    public double getZrotation_coeff() {
        return this.zrotation_coeff;
    }

    public boolean isUse_deform() {
        return this.use_deform;
    }

    public boolean isUse_luminance_correction() {
        return this.use_luminance_correction;
    }

    public String toString() {
        return "PanoramaSetting{aovx=" + this.aovx + ", aovy=" + this.aovy + ", shrink_ratio=" + this.shrink_ratio + ", calcseam_pixnum=" + this.calcseam_pixnum + ", use_deform=" + this.use_deform + ", use_luminance_correction=" + this.use_luminance_correction + ", seamsearch_ratio=" + this.seamsearch_ratio + ", zrotation_coeff=" + this.zrotation_coeff + ", draw_threshold=" + this.draw_threshold + ", aov_gain=" + this.aov_gain + ", distortion_k1=" + this.distortion_k1 + ", distortion_k2=" + this.distortion_k2 + ", distortion_k3=" + this.distortion_k3 + ", distortion_k4=" + this.distortion_k4 + ", rotation_ratio=" + this.rotation_ratio + '}';
    }
}
