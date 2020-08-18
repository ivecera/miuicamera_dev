package com.arcsoft.camera.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.text.format.DateFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: FileUtil */
public class a {
    private a() {
    }

    public static String a(String str, String str2, String str3, int i, int i2) {
        if (str == null) {
            return null;
        }
        if (!str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        String str4 = str + str2 + DateFormat.format("yyyyMMdd", System.currentTimeMillis()).toString() + ("_" + String.format("%03d", Integer.valueOf(i2)) + "_" + i) + str3;
        File file = new File(str4);
        if (file.exists()) {
            file.delete();
        }
        return str4;
    }

    public static boolean a(InputStream inputStream, String str) {
        if (inputStream == null || str == null) {
            return false;
        }
        File file = new File(str);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    inputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public static boolean a(byte[] bArr, int i, int i2, int i3, String str) {
        if (str != null) {
            File file = new File(str);
            int i4 = 0;
            while (file.exists()) {
                i4++;
                file = new File(str + i4);
            }
            file.getParentFile().mkdirs();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str);
                YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, null);
                yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), i3, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }

    public static boolean c(Context context, String str, String str2) {
        if (context == null || str == null || str2 == null) {
            return false;
        }
        try {
            return a(context.getAssets().open(str), str2);
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean isVideoFile(String str) {
        if (str == null) {
            return false;
        }
        String substring = str.substring(str.lastIndexOf(46));
        return ".3gp".equals(substring) || ".mp4".equals(substring);
    }

    public static String l(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        if (!str.isEmpty() && !str.endsWith(File.separator)) {
            str = str + File.separator;
        }
        if (!str2.isEmpty() && str2.startsWith(File.separator)) {
            str2.substring(str2.indexOf(File.separator) + 1);
        }
        return str + str2;
    }

    public static String x(String str) {
        int lastIndexOf;
        if (str == null || str.isEmpty() || (lastIndexOf = str.lastIndexOf(46)) < 0 || lastIndexOf >= str.length() - 1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    public static boolean y(String str) {
        if (str == null) {
            return false;
        }
        return new File(str).exists();
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0039 A[SYNTHETIC, Splitter:B:24:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004f A[SYNTHETIC, Splitter:B:34:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    public static byte[] z(String str) {
        byte[] bArr;
        byte[] bArr2;
        File file = new File(str);
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                byte[] bArr3 = new byte[fileInputStream2.available()];
                do {
                } while (fileInputStream2.read(bArr3) > 0);
                try {
                    fileInputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return bArr3;
            } catch (FileNotFoundException e3) {
                e = e3;
                bArr = null;
                fileInputStream = fileInputStream2;
                e.printStackTrace();
                if (fileInputStream != null) {
                }
            } catch (IOException e4) {
                e = e4;
                bArr2 = null;
                fileInputStream = fileInputStream2;
                try {
                    e.printStackTrace();
                    if (fileInputStream != null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            bArr = null;
            e.printStackTrace();
            if (fileInputStream != null) {
                return bArr;
            }
            fileInputStream.close();
            return bArr;
        } catch (IOException e7) {
            e = e7;
            bArr2 = null;
            e.printStackTrace();
            if (fileInputStream != null) {
                return bArr2;
            }
            try {
                fileInputStream.close();
                return bArr2;
            } catch (IOException e8) {
                e8.printStackTrace();
                return bArr2;
            }
        }
    }
}
