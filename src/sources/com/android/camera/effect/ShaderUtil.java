package com.android.camera.effect;

import android.content.res.AssetManager;
import android.opengl.GLES20;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.Buffer;

public class ShaderUtil {
    public static final String DIR = "shading_script";
    public static final int NO_TEXTURE = -1;
    private static final String TAG = "Camera_ShaderUtil";

    public static void checkGlError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.e(TAG, "ES20_ERROR: op " + str + ": glError " + glGetError);
            throw new RuntimeException(str + ": glError " + glGetError);
        }
    }

    public static int createProgram(String str, String str2) {
        int loadShader = loadShader(35633, str);
        if (loadShader == 0) {
            Log.e(TAG, "Fail to init vertex shader " + str);
            return 0;
        }
        int loadShader2 = loadShader(35632, str2);
        if (loadShader2 == 0) {
            Log.e(TAG, "Fail to init fragment shader " + str2);
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram != 0) {
            GLES20.glGetError();
            GLES20.glAttachShader(glCreateProgram, loadShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(glCreateProgram, loadShader2);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(glCreateProgram);
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(glCreateProgram));
                GLES20.glDeleteProgram(glCreateProgram);
                glCreateProgram = 0;
            }
        }
        GLES20.glDeleteShader(loadShader);
        GLES20.glDeleteShader(loadShader2);
        return glCreateProgram;
    }

    public static String loadFromAssetsFile(String str) {
        String str2 = null;
        try {
            AssetManager assets = CameraAppImpl.getAndroidContext().getAssets();
            InputStream open = assets.open("shading_script/" + str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = open.read();
                if (read != -1) {
                    byteArrayOutputStream.write(read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    open.close();
                    String str3 = new String(byteArray, "UTF-8");
                    try {
                        return str3.replaceAll("\\r\\n", "\n");
                    } catch (Exception e2) {
                        str2 = str3;
                        e = e2;
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return str2;
        }
    }

    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            return glCreateShader;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.e(TAG, "Could not compile shader " + i + ":" + str);
        StringBuilder sb = new StringBuilder();
        sb.append("Info: ");
        sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
        Log.e(TAG, sb.toString());
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public static void loadYuvImageTextures(int i, int i2, int i3, int i4, int[] iArr) {
        int i5 = 0;
        if (iArr == null || iArr.length < 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid output texture size ");
            if (iArr != null) {
                i5 = iArr.length;
            }
            sb.append(i5);
            Log.e(TAG, sb.toString());
            return;
        }
        if (iArr[0] == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, iArr[0]);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, iArr[0]);
        }
        ShaderNativeUtil.texChannelY(i, i2, i3);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        if (iArr[1] == -1) {
            GLES20.glGenTextures(1, iArr, 1);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, iArr[1]);
        } else {
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, iArr[1]);
        }
        ShaderNativeUtil.texChannelUV(i >> 1, i2 >> 1, i4);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
    }

    public static void loadYuvToTextures(Buffer buffer, Buffer buffer2, int i, int i2, int[] iArr) {
        int i3;
        float f2;
        int i4;
        if (buffer == null || buffer2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid channel ");
            sb.append(buffer == null ? "Y" : "UV");
            Log.e(TAG, sb.toString());
            return;
        }
        int i5 = 0;
        if (iArr == null || iArr.length < 2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("invalid output texture size ");
            if (iArr != null) {
                i5 = iArr.length;
            }
            sb2.append(i5);
            Log.e(TAG, sb2.toString());
            return;
        }
        if (iArr[0] == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexImage2D(3553, 0, 6409, i, i2, 0, 6409, 5121, buffer);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexImage2D(3553, 0, 6409, i, i2, 0, 6409, 5121, buffer);
        }
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        if (iArr[1] == -1) {
            GLES20.glGenTextures(1, iArr, 1);
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, iArr[1]);
            f2 = 33071.0f;
            GLES20.glTexImage2D(3553, 0, 6410, i / 2, i2 / 2, 0, 6410, 5121, buffer2);
            i3 = 10242;
            i4 = 10243;
        } else {
            f2 = 33071.0f;
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, iArr[1]);
            i3 = 10242;
            i4 = 10243;
            GLES20.glTexImage2D(3553, 0, 6410, i / 2, i2 / 2, 0, 6410, 5121, buffer2);
        }
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, i3, f2);
        GLES20.glTexParameterf(3553, i4, f2);
    }
}
