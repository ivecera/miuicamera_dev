package com.ss.android.vesdk;

import android.os.StatFs;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipFile;

public class VEFileUtils {
    public static boolean canWrite(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        File file2 = new File(file, "." + System.currentTimeMillis());
        boolean mkdir = file2.mkdir();
        return mkdir ? file2.delete() : mkdir;
    }

    public static void clearPath(String str) {
        String str2;
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            String[] list = file.list();
            for (String str3 : list) {
                if (str.endsWith(File.separator)) {
                    str2 = str + str3;
                } else {
                    str2 = str + File.separator + str3;
                }
                File file2 = new File(str2);
                if (file2.isFile()) {
                    file2.delete();
                }
                if (file2.isDirectory()) {
                    deletePath(str2);
                }
            }
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void close(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x006a A[SYNTHETIC, Splitter:B:43:0x006a] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0074 A[SYNTHETIC, Splitter:B:48:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0082 A[SYNTHETIC, Splitter:B:56:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x008c A[SYNTHETIC, Splitter:B:61:0x008c] */
    public static boolean copyFile(File file, File file2) {
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        if (!file.exists() || !file.isFile() || file2.isDirectory()) {
            return false;
        }
        if (file2.exists()) {
            file2.delete();
        }
        BufferedInputStream bufferedInputStream2 = null;
        r2 = null;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            byte[] bArr = new byte[2048];
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                while (true) {
                    try {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, read);
                    } catch (IOException e2) {
                        e = e2;
                        bufferedInputStream2 = bufferedInputStream;
                        try {
                            e.printStackTrace();
                            if (bufferedInputStream2 != null) {
                            }
                            if (bufferedOutputStream != null) {
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bufferedInputStream = bufferedInputStream2;
                            bufferedOutputStream2 = bufferedOutputStream;
                            if (bufferedInputStream != null) {
                            }
                            if (bufferedOutputStream2 != null) {
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream2 = bufferedOutputStream;
                        if (bufferedInputStream != null) {
                        }
                        if (bufferedOutputStream2 != null) {
                        }
                        throw th;
                    }
                }
                bufferedOutputStream.flush();
                try {
                    bufferedInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                try {
                    bufferedOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return true;
            } catch (IOException e5) {
                e = e5;
                bufferedOutputStream = null;
                bufferedInputStream2 = bufferedInputStream;
                e.printStackTrace();
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (bufferedOutputStream2 != null) {
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e10) {
            e = e10;
            bufferedOutputStream = null;
            e.printStackTrace();
            if (bufferedInputStream2 != null) {
            }
            if (bufferedOutputStream != null) {
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            bufferedInputStream = null;
            if (bufferedInputStream != null) {
            }
            if (bufferedOutputStream2 != null) {
            }
            throw th;
        }
    }

    public static boolean copyFile(String str, String str2) {
        return copyFile(new File(str), new File(str2));
    }

    public static void deleteFile(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    public static void deletePath(String str) {
        String str2;
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            String[] list = file.list();
            if (list != null) {
                for (String str3 : list) {
                    if (str3 != null) {
                        if (str.endsWith(File.separator)) {
                            str2 = str + str3;
                        } else {
                            str2 = str + File.separator + str3;
                        }
                        File file2 = new File(str2);
                        if (file2.isFile()) {
                            file2.delete();
                        }
                        if (file2.isDirectory()) {
                            deletePath(str2);
                        }
                    }
                }
                file.delete();
            }
        }
    }

    public static boolean exists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static long getAllBytes(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                StatFs statFs = new StatFs(str);
                return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }

    public static long getAvailableBytes(String str) {
        try {
            if (TextUtils.isEmpty(str) || !new File(str).exists()) {
                return 0;
            }
            StatFs statFs = new StatFs(str);
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String getFileExtension(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return (lastIndexOf < 0 || lastIndexOf >= str.length() + -1) ? "" : str.substring(lastIndexOf + 1).toUpperCase();
    }

    public static String getFileName(String str) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf("/");
        return (lastIndexOf <= 0 || lastIndexOf >= str.length() + -1) ? str : str.substring(lastIndexOf + 1);
    }

    public static String getFileNameWithoutExtension(String str) {
        int lastIndexOf;
        String fileName = getFileName(str);
        return (fileName == null || fileName.length() <= 0 || (lastIndexOf = fileName.lastIndexOf(46)) <= 0) ? fileName : fileName.substring(0, lastIndexOf);
    }

    public static long getFileSize(String str) {
        File[] listFiles;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        File file = new File(str);
        if (!file.exists()) {
            return 0;
        }
        long length = file.length();
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            int length2 = listFiles.length;
            int i = 0;
            while (i < length2) {
                try {
                    length += getFileSize(listFiles[i].getAbsolutePath());
                    i++;
                } catch (StackOverflowError e2) {
                    e2.printStackTrace();
                    return 0;
                } catch (OutOfMemoryError e3) {
                    e3.printStackTrace();
                    return 0;
                }
            }
        }
        return length;
    }

    public static String getParentFilePath(String str) {
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf("/");
        if (lastIndexOf >= 0) {
            return str.substring(0, lastIndexOf);
        }
        return null;
    }

    public static boolean mkdir(String str) {
        return new File(str).mkdirs();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0026 A[SYNTHETIC, Splitter:B:17:0x0026] */
    public static String readFileFirstLine(String str) {
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
            try {
                String readLine = bufferedReader2.readLine();
                bufferedReader2.close();
                try {
                    bufferedReader2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return readLine;
            } catch (Exception unused) {
                bufferedReader = bufferedReader2;
                if (bufferedReader != null) {
                }
                return "";
            } catch (Throwable th) {
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception unused2) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return "";
        }
    }

    public static boolean renameFile(String str, String str2) {
        File file = new File(str);
        File file2 = new File(str2);
        if (!file.exists()) {
            return false;
        }
        return file.renameTo(file2);
    }

    public static void setPermissions(String str, int i) {
        VEJavaCalls.callStaticMethod("android.os.FileUtils", "setPermissions", str, Integer.valueOf(i), -1, -1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    public static void write(String str, String str2, boolean z) {
        FileWriter fileWriter = null;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fileWriter2 = new FileWriter(str, z);
            try {
                fileWriter2.write(str2);
                try {
                    fileWriter2.close();
                } catch (Exception unused) {
                }
            } catch (IOException e2) {
                e = e2;
                fileWriter = fileWriter2;
                e.printStackTrace();
                if (fileWriter == null) {
                }
                fileWriter.close();
            } catch (Throwable th) {
                th = th;
                fileWriter = fileWriter2;
                try {
                    th.printStackTrace();
                    if (fileWriter == null) {
                    }
                    fileWriter.close();
                } catch (Throwable th2) {
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (Exception unused2) {
                        }
                    }
                    throw th2;
                }
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            if (fileWriter == null) {
                return;
            }
            fileWriter.close();
        } catch (Throwable th3) {
            th = th3;
            th.printStackTrace();
            if (fileWriter == null) {
                return;
            }
            fileWriter.close();
        }
    }
}
