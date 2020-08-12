package com.ss.android.ttve.common;

import android.graphics.Bitmap;
import com.android.camera.storage.Storage;
import com.ss.android.vesdk.VELogUtil;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TEImageUtils {
    private static final String TAG = "TEImageUtils";

    public static void saveBitmap(Bitmap bitmap) {
        saveBitmap(bitmap, System.currentTimeMillis() + Storage.JPEG_SUFFIX);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0088 A[SYNTHETIC, Splitter:B:29:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0092 A[SYNTHETIC, Splitter:B:34:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009e A[SYNTHETIC, Splitter:B:40:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a8 A[SYNTHETIC, Splitter:B:45:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    public static void saveBitmap(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream;
        String str2 = TEFileUtils.getPath() + "/" + str;
        VELogUtil.i(TAG, "saving Bitmap : " + str);
        BufferedOutputStream bufferedOutputStream = null;
        bufferedOutputStream = null;
        bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(str2);
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream2);
                    bufferedOutputStream2.flush();
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    VELogUtil.i(TAG, "Bitmap " + str + " saved!");
                } catch (IOException e4) {
                    e = e4;
                    bufferedOutputStream = bufferedOutputStream2;
                    try {
                        VELogUtil.e(TAG, "Err when saving bitmap...");
                        e.printStackTrace();
                        if (bufferedOutputStream != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream = bufferedOutputStream2;
                    if (bufferedOutputStream != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            } catch (IOException e7) {
                e = e7;
                VELogUtil.e(TAG, "Err when saving bitmap...");
                e.printStackTrace();
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
            }
        } catch (IOException e10) {
            e = e10;
            fileOutputStream = null;
            VELogUtil.e(TAG, "Err when saving bitmap...");
            e.printStackTrace();
            if (bufferedOutputStream != null) {
            }
            if (fileOutputStream != null) {
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (bufferedOutputStream != null) {
            }
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }
}
