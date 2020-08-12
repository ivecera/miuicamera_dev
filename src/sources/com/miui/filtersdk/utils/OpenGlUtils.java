package com.miui.filtersdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

public class OpenGlUtils {
    public static final int NOT_INIT = -1;
    public static final int NO_TEXTURE = -1;
    public static final int ON_DRAWN = 1;
    private static final String TAG = "OpenGlUtils";

    public static void checkGlError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            String str2 = str + ": glError 0x" + Integer.toHexString(glGetError);
            Log.e(TAG, str2);
            throw new RuntimeException(str2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0031 A[SYNTHETIC, Splitter:B:21:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003c A[SYNTHETIC, Splitter:B:26:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    public static Bitmap getImageFromAssetsFile(Context context, String str) {
        Bitmap bitmap;
        InputStream inputStream = null;
        Bitmap bitmap2 = null;
        try {
            InputStream inputStream2 = context.getResources().getAssets().open(str);
            try {
                bitmap2 = BitmapFactory.decodeStream(inputStream2);
                inputStream2.close();
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                return bitmap2;
            } catch (IOException e3) {
                e = e3;
                inputStream = inputStream2;
                bitmap = bitmap2;
                try {
                    e.printStackTrace();
                    if (inputStream != null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    inputStream2 = inputStream;
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStream2 != null) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            bitmap = null;
            e.printStackTrace();
            if (inputStream != null) {
                return bitmap;
            }
            try {
                inputStream.close();
                return bitmap;
            } catch (IOException e6) {
                e6.printStackTrace();
                return bitmap;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x001e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0026 A[SYNTHETIC, Splitter:B:21:0x0026] */
    public static Bitmap getImageFromPath(String str) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        r0 = null;
        Bitmap bitmap = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                bitmap = BitmapFactory.decodeStream(fileInputStream);
                try {
                    fileInputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (IOException e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    if (fileInputStream != null) {
                    }
                    return bitmap;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
        } catch (IOException e5) {
            e = e5;
            fileInputStream = null;
            e.printStackTrace();
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return bitmap;
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream2 != null) {
            }
            throw th;
        }
        return bitmap;
    }

    public static int initEffectTexture(int i, int i2, int[] iArr, int i3) {
        if (iArr == null || iArr.length < 1) {
            return -1;
        }
        if (iArr[0] == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(i3, iArr[0]);
            GLES20.glTexParameterf(i3, 10240, 9729.0f);
            GLES20.glTexParameterf(i3, 10241, 9729.0f);
            GLES20.glTexParameterf(i3, 10242, 33071.0f);
            GLES20.glTexParameterf(i3, 10243, 33071.0f);
            GLES20.glTexImage2D(i3, 0, 6408, i, i2, 0, 6408, 5121, null);
        } else {
            GLES20.glBindTexture(i3, iArr[0]);
            GLES20.glTexImage2D(i3, 0, 6408, i, i2, 0, 6408, 5121, null);
        }
        return iArr[0];
    }

    public static int loadProgram(String str, String str2) {
        int[] iArr = new int[1];
        int loadShader = loadShader(str, 35633);
        if (loadShader == 0) {
            Log.d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        int loadShader2 = loadShader(str2, 35632);
        if (loadShader2 == 0) {
            Log.d("Load Program", "Fragment Shader Failed");
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glCreateProgram, loadShader);
        GLES20.glAttachShader(glCreateProgram, loadShader2);
        GLES20.glLinkProgram(glCreateProgram);
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] <= 0) {
            Log.d("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(loadShader);
        GLES20.glDeleteShader(loadShader2);
        return glCreateProgram;
    }

    private static int loadShader(String str, int i) {
        int[] iArr = new int[1];
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.e("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(glCreateShader));
        return 0;
    }

    public static int loadTexture(Context context, String str) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        if (iArr[0] != 0) {
            Bitmap imageFromAssetsFile = getImageFromAssetsFile(context, str);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameteri(3553, 10240, 9729);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GLUtils.texImage2D(3553, 0, imageFromAssetsFile, 0);
            imageFromAssetsFile.recycle();
        }
        if (iArr[0] != 0) {
            return iArr[0];
        }
        Log.e(TAG, String.format("loadTexture failed,path:%s", str));
        return -1;
    }

    public static int loadTexture(Bitmap bitmap, int i) {
        return loadTexture(bitmap, i, false);
    }

    public static int loadTexture(Bitmap bitmap, int i, boolean z) {
        if (bitmap == null) {
            return -1;
        }
        int[] iArr = new int[1];
        if (i == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        } else {
            GLES20.glBindTexture(3553, i);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
            iArr[0] = i;
        }
        if (z) {
            bitmap.recycle();
        }
        return iArr[0];
    }

    public static int loadTexture(Buffer buffer, int i, int i2, int i3) {
        if (buffer == null) {
            return -1;
        }
        int[] iArr = new int[1];
        if (i3 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, buffer);
        } else {
            GLES20.glBindTexture(3553, i3);
            GLES20.glTexSubImage2D(3553, 0, 0, 0, i, i2, 6408, 5121, buffer);
            iArr[0] = i3;
        }
        return iArr[0];
    }

    public static int loadTexture(Buffer buffer, int i, int i2, int i3, int i4) {
        if (buffer == null) {
            return -1;
        }
        int[] iArr = new int[1];
        if (i3 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, i4, buffer);
        } else {
            GLES20.glBindTexture(3553, i3);
            GLES20.glTexSubImage2D(3553, 0, 0, 0, i, i2, 6408, i4, buffer);
            iArr[0] = i3;
        }
        return iArr[0];
    }

    public static int loadTextureFromPath(String str) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        if (iArr[0] != 0) {
            Bitmap imageFromPath = getImageFromPath(str);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glTexParameteri(3553, 10240, 9729);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GLUtils.texImage2D(3553, 0, imageFromPath, 0);
            imageFromPath.recycle();
        }
        if (iArr[0] != 0) {
            return iArr[0];
        }
        throw new RuntimeException("Error loading texture.");
    }

    public static void loadYuvToTextures(Buffer buffer, Buffer buffer2, int i, int i2, int[] iArr) {
        float f2;
        float f3;
        int i3;
        if (buffer != null && buffer2 != null && iArr != null && iArr.length >= 2) {
            if (iArr[0] == -1) {
                GLES20.glGenTextures(1, iArr, 0);
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, iArr[0]);
                i3 = 3553;
                f2 = 9729.0f;
                f3 = 33071.0f;
                GLES20.glTexImage2D(3553, 0, 6409, i, i2, 0, 6409, 5121, buffer);
                GLES20.glTexParameterf(3553, 10240, 9729.0f);
                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                GLES20.glTexParameterf(3553, 10243, 33071.0f);
            } else {
                i3 = 3553;
                f2 = 9729.0f;
                f3 = 33071.0f;
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, iArr[0]);
                GLES20.glTexImage2D(3553, 0, 6409, i, i2, 0, 6409, 5121, buffer);
            }
            if (iArr[1] == -1) {
                GLES20.glGenTextures(1, iArr, 1);
                GLES20.glActiveTexture(33985);
                GLES20.glBindTexture(i3, iArr[1]);
                GLES20.glTexImage2D(3553, 0, 6410, i / 2, i2 / 2, 0, 6410, 5121, buffer2);
                GLES20.glTexParameterf(i3, 10240, f2);
                GLES20.glTexParameterf(i3, 10241, f2);
                GLES20.glTexParameterf(i3, 10242, f3);
                GLES20.glTexParameterf(i3, 10243, f3);
                return;
            }
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(i3, iArr[1]);
            GLES20.glTexImage2D(3553, 0, 6410, i / 2, i2 / 2, 0, 6410, 5121, buffer2);
        }
    }
}
