package com.ss.android.medialib.common;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.arcsoft.camera.wideselfie.ArcWideSelfieDef;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Common {
    public static final boolean DEBUG = true;
    public static final float[] FULLSCREEN_VERTICES = {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    public static final String TAG = "WIX";
    public static final boolean USE_TEXTURE_ENCODE_INPUT = true;

    public interface IGetTimestampCallback {
        void getTimestamp(double d2);
    }

    public interface IOnOpenGLCallback {
        void onOpenGLCreate();

        void onOpenGLDestroy();

        int onOpenGLRunning();
    }

    public interface IShotScreenCallback {
        void onShotScreen(int i);
    }

    private static void _texParamHelper(int i, int i2, int i3) {
        GLES20.glTexParameteri(i, 10241, i2);
        GLES20.glTexParameteri(i, 10240, i2);
        GLES20.glTexParameteri(i, 10242, i3);
        GLES20.glTexParameteri(i, 10243, i3);
    }

    public static void checkGLError(String str) {
        String str2;
        int glGetError = GLES20.glGetError();
        for (int i = 0; i < 32 && glGetError != 0; i++) {
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
            Log.e("WIX", String.format("After tag \"%s\" glGetError %s(0x%x) ", str, str2, Integer.valueOf(glGetError)));
            glGetError = GLES20.glGetError();
        }
    }

    public static Bitmap createBitmap(int i, int i2, Bitmap.Config config) {
        if (i > 0 && i2 > 0) {
            try {
                return Bitmap.createBitmap(i, i2, config);
            } catch (Error e2) {
                Log.e("WIX", "createBitmap: Error" + e2.getMessage());
            }
        }
        return null;
    }

    public static void deleteTextureID(int i) {
        GLES20.glDeleteTextures(1, new int[]{i}, 0);
    }

    public static int genBlankTextureID(int i, int i2) {
        return genBlankTextureID(i, i2, 9729, 33071);
    }

    public static int genBlankTextureID(int i, int i2, int i3, int i4) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
        _texParamHelper(3553, i3, i4);
        return iArr[0];
    }

    public static int genFullscreenVertexArrayBuffer() {
        int[] iArr = new int[1];
        GLES20.glGenBuffers(1, iArr, 0);
        if (iArr[0] == 0) {
            Log.e("WIX", "Invalid VertexBuffer! You must call this within an OpenGL thread!");
            return 0;
        }
        GLES20.glBindBuffer(34962, iArr[0]);
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(FULLSCREEN_VERTICES.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        asFloatBuffer.put(FULLSCREEN_VERTICES).position(0);
        GLES20.glBufferData(34962, 32, asFloatBuffer, 35044);
        return iArr[0];
    }

    public static int genNormalTextureID(Bitmap bitmap) {
        return genNormalTextureID(bitmap, 9729, 33071);
    }

    public static int genNormalTextureID(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return 0;
        }
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        _texParamHelper(3553, i, i2);
        return iArr[0];
    }

    public static int genSurfaceTextureID() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        _texParamHelper(36197, 9729, 33071);
        return iArr[0];
    }
}
