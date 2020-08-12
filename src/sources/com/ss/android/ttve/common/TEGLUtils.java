package com.ss.android.ttve.common;

import android.opengl.GLES20;
import com.arcsoft.camera.wideselfie.ArcWideSelfieDef;
import com.ss.android.vesdk.VELogUtil;

public class TEGLUtils {
    public static final float[] FULLSCREEN_VERTICES = {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    private static final String TAG = "TEGLUtils";

    public static void checkGLError(String str) {
        String str2;
        int glGetError = GLES20.glGetError();
        for (int i = 0; i < 4 && glGetError != 0; i++) {
            switch (glGetError) {
                case 1280:
                    str2 = "invalid enum";
                    break;
                case ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_YUYV:
                    str2 = "invalid value";
                    break;
                case ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_YVYU:
                    str2 = "invalid operation";
                    break;
                case ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_UYVY:
                case ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_VYUY:
                default:
                    str2 = "unknown error";
                    break;
                case ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_YUYV2:
                    str2 = "out of memory";
                    break;
                case ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_YVYU2:
                    str2 = "invalid framebuffer operation";
                    break;
            }
            VELogUtil.e(TAG, String.format("After tag \"%s\" glGetError %s(0x%x) ", str, str2, Integer.valueOf(glGetError)));
            glGetError = GLES20.glGetError();
        }
    }
}
