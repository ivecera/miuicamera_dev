package com.android.camera.sticker;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    /* JADX WARNING: Removed duplicated region for block: B:52:0x007b A[SYNTHETIC, Splitter:B:52:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0085 A[SYNTHETIC, Splitter:B:57:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0094 A[SYNTHETIC, Splitter:B:66:0x0094] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x009e A[SYNTHETIC, Splitter:B:71:0x009e] */
    public static boolean copyFileIfNeed(Context context, File file, String str) {
        Throwable th;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        if (file == null || TextUtils.isEmpty(str)) {
            return true;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file.exists()) {
            return true;
        }
        InputStream inputStream2 = null;
        r0 = null;
        FileOutputStream fileOutputStream2 = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            inputStream = context.getAssets().open(str);
            if (inputStream == null) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                return false;
            }
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (IOException unused) {
                fileOutputStream = null;
                inputStream2 = inputStream;
                try {
                    file.delete();
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                    fileOutputStream2 = fileOutputStream;
                    if (inputStream != null) {
                    }
                    if (fileOutputStream2 != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                inputStream.close();
                fileOutputStream.close();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                try {
                    fileOutputStream.close();
                    return true;
                } catch (IOException e8) {
                    e8.printStackTrace();
                    return true;
                }
            } catch (IOException unused2) {
                inputStream2 = inputStream;
                file.delete();
                if (inputStream2 != null) {
                }
                if (fileOutputStream != null) {
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream2 = fileOutputStream;
                if (inputStream != null) {
                }
                if (fileOutputStream2 != null) {
                }
                throw th;
            }
        } catch (IOException unused3) {
            fileOutputStream = null;
            file.delete();
            if (inputStream2 != null) {
            }
            if (fileOutputStream != null) {
            }
            return false;
        } catch (Throwable th5) {
            th = th5;
            inputStream = null;
            if (inputStream != null) {
            }
            if (fileOutputStream2 != null) {
            }
            throw th;
        }
    }
}
