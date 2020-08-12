package com.bef.effectsdk;

import android.content.Context;
import android.text.TextUtils;
import com.ss.android.ttve.monitor.MonitorUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EffectSDKUtils {
    private static List<String> assetFiles = ModelsList.list;
    private static Set<File> localFiles = new HashSet();
    private static Set<File> needRemoveFiles = new HashSet();

    /* access modifiers changed from: private */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    private static void copyAssets(Context context, String str, String[] strArr, boolean z) throws Throwable {
        boolean z2;
        if (!needRemoveFiles.isEmpty()) {
            needRemoveFiles.clear();
        }
        needRemoveFiles.addAll(localFiles);
        if (!str.endsWith("/")) {
            str = str + "/";
        }
        for (String str2 : assetFiles) {
            final String fileName = getFileName(str2);
            File takeFirstMatchingOrNull = takeFirstMatchingOrNull(needRemoveFiles, new FileFilter() {
                /* class com.bef.effectsdk.EffectSDKUtils.AnonymousClass2 */

                public boolean accept(File file) {
                    return file.getName().contains(fileName);
                }
            });
            boolean z3 = false;
            if (takeFirstMatchingOrNull == null || !new File(str, getAssetRelativePath(str2)).exists()) {
                z2 = true;
            } else {
                needRemoveFiles.remove(takeFirstMatchingOrNull);
                z2 = false;
            }
            if (z2) {
                if (strArr != null && !TextUtils.isEmpty(fileName)) {
                    int length = strArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        } else if (fileName.equals(strArr[i])) {
                            z3 = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (z3 && z) {
                    copyFile(context, str2, str);
                }
                if (!z3 && !z) {
                    copyFile(context, str2, str);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.io.InputStream} */
    /* JADX WARN: Multi-variable type inference failed */
    private static void copyFile(Context context, String str, String str2) throws Throwable {
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        InputStream inputStream2 = null;
        inputStream2 = null;
        try {
            inputStream = context.getAssets().open(str);
            try {
                String str3 = str2 + str.substring(str.indexOf(MonitorUtils.KEY_MODEL) + 6, str.lastIndexOf("/"));
                File file = new File(str3);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        throw new IOException("Can not mkdirs " + file.getPath());
                    }
                }
                fileOutputStream = new FileOutputStream(new File(str3 + "/" + getFileName(str)));
            } catch (Throwable th) {
                th = th;
                try {
                    closeQuietly(inputStream2);
                    throw th;
                } finally {
                    closeQuietly(inputStream2);
                }
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        try {
                            closeQuietly(fileOutputStream);
                            return;
                        } finally {
                            closeQuietly(fileOutputStream);
                        }
                    }
                }
            } catch (Throwable th2) {
                inputStream2 = fileOutputStream;
                th = th2;
                closeQuietly(inputStream2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = inputStream2;
            closeQuietly(inputStream2);
            throw th;
        }
    }

    private static void deleteNoUseModel() {
        for (File file : localFiles) {
            if (needRemoveFiles.contains(file) && file.exists()) {
                file.delete();
            }
        }
    }

    public static void flushAlgorithmModelFiles(Context context, String str) throws Throwable {
        if (!localFiles.isEmpty()) {
            localFiles.clear();
        }
        scanRecursive(str, localFiles);
        copyAssets(context, str, null, false);
        deleteNoUseModel();
        localFiles.clear();
    }

    public static void flushAlgorithmModelFiles(Context context, String str, String[] strArr, boolean z) throws Throwable {
        if (!localFiles.isEmpty()) {
            localFiles.clear();
        }
        scanRecursive(str, localFiles);
        copyAssets(context, str, strArr, z);
        deleteNoUseModel();
        localFiles.clear();
    }

    private static String getAssetRelativePath(String str) {
        int indexOf = str.indexOf("model/");
        return indexOf >= 0 ? str.substring(indexOf + 6, str.length()) : str;
    }

    private static String getFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf != -1 ? str.substring(lastIndexOf + 1, str.length()) : "";
    }

    public static String getSdkVersion() {
        return nativeGetSdkVersion();
    }

    private static native String nativeGetSdkVersion();

    public static boolean needUpdate(final Context context, String str) {
        if (!localFiles.isEmpty()) {
            localFiles.clear();
        }
        scanRecursive(str, localFiles);
        try {
            if (assetFiles.size() > localFiles.size()) {
                return true;
            }
            for (final String str2 : assetFiles) {
                if (takeFirstMatchingOrNull(localFiles, new FileFilter() {
                    /* class com.bef.effectsdk.EffectSDKUtils.AnonymousClass1 */

                    public boolean accept(File file) {
                        boolean z = false;
                        if (str2.contains(file.getName())) {
                            InputStream inputStream = null;
                            try {
                                inputStream = context.getAssets().open(str2);
                                if (file.length() == ((long) inputStream.available())) {
                                    z = true;
                                }
                                return z;
                            } catch (IOException unused) {
                            } finally {
                                EffectSDKUtils.closeQuietly(inputStream);
                            }
                        }
                        return false;
                    }
                }) == null) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return true;
        }
    }

    private static void scanRecursive(String str, Set<File> set) {
        File[] listFiles;
        File file = new File(str);
        if (file.exists() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    scanRecursive(file2.getAbsolutePath(), set);
                } else {
                    set.add(file2);
                }
            }
        }
    }

    private static File takeFirstMatchingOrNull(Set<File> set, FileFilter fileFilter) {
        for (File file : set) {
            if (fileFilter.accept(file)) {
                return file;
            }
        }
        return null;
    }
}
