package com.ss.android.vesdk.runtime;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.ss.android.vesdk.VEFileUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class VESafelyLibsLoader {
    private static final String LIB_DIR = "libso";
    private static final String TAG = "VESafelyLibsLoader";
    private static List<String> sLoadedLibs = new ArrayList();

    private static File getLibraryFile(Context context, String str) {
        String mapLibraryName = System.mapLibraryName(str);
        File libraryFolder = getLibraryFolder(context);
        if (libraryFolder != null) {
            return new File(libraryFolder, mapLibraryName);
        }
        return null;
    }

    private static File getLibraryFolder(Context context) {
        if (context == null || context.getFilesDir() == null) {
            return null;
        }
        File file = new File(context.getFilesDir(), LIB_DIR);
        if (!file.exists()) {
            VEFileUtils.mkdir(file.getAbsolutePath());
        }
        return file;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009b, code lost:
        return true;
     */
    public static synchronized boolean loadLibrary(String str, Context context) {
        synchronized (VESafelyLibsLoader.class) {
            if (sLoadedLibs.contains(str)) {
                return true;
            }
            try {
                System.loadLibrary(str);
                sLoadedLibs.add(str);
            } catch (UnsatisfiedLinkError e2) {
                Log.e(TAG, "loadLibrary " + str + " error", e2);
                File libraryFile = getLibraryFile(context, str);
                if (libraryFile == null) {
                    return false;
                }
                if (libraryFile.exists()) {
                    libraryFile.delete();
                }
                String unpackLibrary = unpackLibrary(context, str, libraryFile);
                if (unpackLibrary != null) {
                    Log.e("loadLibrary", e2.getMessage() + "[" + unpackLibrary + "]");
                    return false;
                }
                System.load(libraryFile.getAbsolutePath());
                sLoadedLibs.add(str);
            } catch (Throwable unused) {
                return false;
            }
        }
    }

    private static String unpackLibrary(Context context, String str, File file) {
        InputStream inputStream;
        ZipFile zipFile;
        String message;
        InputStream inputStream2;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            zipFile = new ZipFile(new File(context.getApplicationInfo().sourceDir), 1);
            try {
                ZipEntry entry = zipFile.getEntry("lib/" + Build.CPU_ABI + "/" + System.mapLibraryName(str));
                if (entry == null) {
                    int indexOf = Build.CPU_ABI.indexOf(45);
                    StringBuilder sb = new StringBuilder();
                    sb.append("lib/");
                    String str2 = Build.CPU_ABI;
                    if (indexOf <= 0) {
                        indexOf = Build.CPU_ABI.length();
                    }
                    sb.append(str2.substring(0, indexOf));
                    sb.append("/");
                    sb.append(System.mapLibraryName(str));
                    String sb2 = sb.toString();
                    ZipEntry entry2 = zipFile.getEntry(sb2);
                    if (entry2 == null) {
                        message = "Library entry not found:" + sb2;
                        VEFileUtils.close((Closeable) null);
                        VEFileUtils.close((Closeable) null);
                        VEFileUtils.close(zipFile);
                        return message;
                    }
                    entry = entry2;
                }
                file.createNewFile();
                inputStream2 = zipFile.getInputStream(entry);
            } catch (Throwable th) {
                th = th;
                inputStream = null;
                try {
                    message = th.getMessage();
                    VEFileUtils.close(fileOutputStream2);
                    VEFileUtils.close(inputStream);
                    VEFileUtils.close(zipFile);
                    return message;
                } catch (Throwable th2) {
                    VEFileUtils.close(fileOutputStream2);
                    VEFileUtils.close(inputStream);
                    VEFileUtils.close(zipFile);
                    throw th2;
                }
            }
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (Throwable th3) {
                inputStream = inputStream2;
                th = th3;
                message = th.getMessage();
                VEFileUtils.close(fileOutputStream2);
                VEFileUtils.close(inputStream);
                VEFileUtils.close(zipFile);
                return message;
            }
            try {
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream2.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        VEFileUtils.setPermissions(file.getAbsolutePath(), 493);
                        VEFileUtils.close(fileOutputStream);
                        VEFileUtils.close(inputStream2);
                        VEFileUtils.close(zipFile);
                        return null;
                    }
                }
            } catch (Throwable th4) {
                fileOutputStream2 = fileOutputStream;
                inputStream = inputStream2;
                th = th4;
                message = th.getMessage();
                VEFileUtils.close(fileOutputStream2);
                VEFileUtils.close(inputStream);
                VEFileUtils.close(zipFile);
                return message;
            }
        } catch (Throwable th5) {
            th = th5;
            inputStream = null;
            zipFile = null;
            message = th.getMessage();
            VEFileUtils.close(fileOutputStream2);
            VEFileUtils.close(inputStream);
            VEFileUtils.close(zipFile);
            return message;
        }
    }
}
