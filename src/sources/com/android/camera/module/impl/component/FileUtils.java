package com.android.camera.module.impl.component;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    public static final String BEAUTY_12_DIR = (RESOURCE_DIR + "Beauty_12/");
    public static final String BEAUTY_12_FILENAME = "Beauty_12.zip";
    public static final String CACHE = (ROOT_DIR + "cache/");
    public static final String CONCAT_VIDEO_DIR = (ROOT_DIR + "concat/");
    public static final String DOC = (ROOT_DIR + "doc/");
    public static final String FACE_RESHAPE = "FaceReshape_V2.zip";
    public static final String FILTER = (FILTER_DIR + "Filter_02/");
    public static final String FILTER_DIR = (RESOURCE_DIR + "filter/");
    public static final String FILTER_FILE_SUFFIX = ".png";
    public static final String MODELS_DIR = (ROOT_DIR + "models/");
    public static final String MUSIC = (ROOT_DIR + "music/");
    public static final String MUSIC_EFFECT_DIR = RESOURCE_DIR;
    public static final String MUSIC_LOCAL = (MUSIC + "local/");
    public static final String MUSIC_ONLINE = (MUSIC + "online/");
    public static final String PHONEPARAM = "phoneParams.txt";
    public static final String RESHAPE_DIR_NAME = (RESOURCE_DIR + "FaceReshape_V2/");
    public static final String RESOURCE_DIR = (ROOT_DIR + "resource/");
    public static List<String> RESOURCE_LIST_CN = new ArrayList();
    public static List<String> RESOURCE_LIST_GLOBAL = new ArrayList();
    public static final String ROOT_DIR = (Environment.getExternalStorageDirectory().getPath() + "/MIUI/Camera/");
    public static final String STICKER_RESOURCE_DIR = (RESOURCE_DIR + "stickers/");
    public static final String SUFFIX = ".zip";
    public static final String TAG = "FileUtils";
    public static final String VIDEO_DUMP = (ROOT_DIR + "dump/");
    public static final String VIDEO_TMP = (ROOT_DIR + "tmp/");
    public static final List<String> musicList = new ArrayList();

    static {
        RESOURCE_LIST_CN.add("0eb0e0214f7bc7f7bbfb4e9f4dba7f99");
        RESOURCE_LIST_CN.add("a75682e81788cc12f68682b9c9067f70");
        RESOURCE_LIST_CN.add("24991e783f23920397ac8aeed15994c2");
        RESOURCE_LIST_GLOBAL.add("24991e783f23920397ac8aeed15994c2");
        RESOURCE_LIST_GLOBAL.add("9b74385fe7e8cb81e1b88ce3b293bdf2");
        RESOURCE_LIST_GLOBAL.add("0a673064d64fce91ee41b405c6f74dca");
        musicList.add("music00001.mp3");
        musicList.add("music00002.mp3");
        musicList.add("music00003.mp3");
        musicList.add("music00004.mp3");
    }

    public static void UnZipAssetFolder(Context context, String str, String str2) throws Exception {
        String str3 = "live/" + str;
        InputStream open = context.getAssets().open(str3);
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        } else if (file.isFile()) {
            file.delete();
            file.mkdirs();
        }
        File file2 = new File(str2 + File.separator + getFileName(str3));
        if (file2.exists()) {
            deleteDir(file2);
        }
        file2.mkdirs();
        open.close();
        UnZipFolder(context.getAssets().open(str3), str2 + getFileName(str3));
    }

    public static int UnZipFileFolder(String str, String str2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        } else if (file.isFile()) {
            file.delete();
            file.mkdirs();
        }
        File file2 = new File(str2 + File.separator + getFileName(str));
        if (file2.exists()) {
            deleteDir(file2);
        }
        file2.mkdirs();
        fileInputStream.close();
        UnZipFolder(new FileInputStream(new File(str)), str2 + File.separator + getFileName(str));
        return 0;
    }

    private static void UnZipFolder(InputStream inputStream, String str) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                break;
            }
            String name = nextEntry.getName();
            if (nextEntry.isDirectory()) {
                String substring = name.substring(0, name.length() - 1);
                File file = new File(str + File.separator + substring);
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                File file2 = new File(str + File.separator + name);
                if (file2.exists()) {
                    break;
                }
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = zipInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
            }
        }
        zipInputStream.close();
    }

    public static boolean checkFileConsist(String str) {
        File file = new File(str);
        return file.exists() && !file.isDirectory();
    }

    public static boolean checkFileDirectoryConsist(String str) {
        File file = new File(str);
        return file.exists() && file.isDirectory();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static boolean copyAssetsDirectory(Context context, String str, String str2) {
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        AssetManager assets = context.getAssets();
        try {
            if (str.endsWith("/")) {
                str = str.substring(0, str.length() - 1);
            }
            String[] list = assets.list(str);
            for (String str3 : list) {
                String str4 = str + File.separator + str3;
                String str5 = str2 + File.separator + str3;
                try {
                    InputStream open = assets.open(str4);
                    copyFileIfNeed(context, str2 + File.separator, str4, str3);
                    open.close();
                } catch (FileNotFoundException unused) {
                    copyAssetsDirectory(context, str4, str5);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return true;
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel channel = fileInputStream.getChannel();
        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static boolean copyFileIfNeed(Context context, String str, String str2) {
        return copyFileIfNeed(context, str, str2, str2);
    }

    private static boolean copyFileIfNeed(Context context, String str, String str2, String str3) {
        String str4 = "live/" + str2;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str5 = str + str3;
        if (str5.isEmpty()) {
            return true;
        }
        File file2 = new File(str5);
        if (file2.exists()) {
            return true;
        }
        try {
            file2.createNewFile();
            InputStream open = context.getApplicationContext().getAssets().open(str4);
            if (open == null) {
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = open.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    open.close();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (IOException unused) {
            file2.delete();
            return false;
        }
    }

    public static synchronized String createtFileName(String str, String str2) {
        String str3;
        synchronized (FileUtils.class) {
            str3 = str + new SimpleDateFormat("MMddHHmmssSSS").format(new Date(System.currentTimeMillis())) + '.' + str2;
        }
        return str3;
    }

    public static boolean delDir(String str) {
        if (str.isEmpty()) {
            return false;
        }
        File file = new File(str);
        return !file.isFile() && file.exists() && deleteDir(file);
    }

    private static boolean deleteDir(File file) {
        String[] list;
        if (file.isDirectory()) {
            for (String str : file.list()) {
                if (!deleteDir(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        for (File file2 : file.listFiles()) {
            if (!deleteFile(file2)) {
                return false;
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return deleteFile(new File(str));
    }

    public static boolean deleteSubFiles(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
        for (File file2 : file.listFiles()) {
            file2.delete();
        }
        return true;
    }

    private static void doDeleteEmptyDir(String str) {
        if (new File(str).delete()) {
            PrintStream printStream = System.out;
            printStream.println("Successfully deleted empty directory: " + str);
            return;
        }
        PrintStream printStream2 = System.out;
        printStream2.println("Failed to delete empty directory: " + str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x007c A[SYNTHETIC, Splitter:B:53:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0086 A[SYNTHETIC, Splitter:B:58:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0092 A[SYNTHETIC, Splitter:B:65:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x009c A[SYNTHETIC, Splitter:B:70:0x009c] */
    public static byte[] getFileBytes(String str) {
        FileInputStream fileInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists() || file.length() <= 0) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[1024];
                byteArrayOutputStream = null;
                while (true) {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        if (byteArrayOutputStream == null) {
                            byteArrayOutputStream = new ByteArrayOutputStream();
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            e.printStackTrace();
                            if (byteArrayOutputStream != null) {
                            }
                            if (fileInputStream != null) {
                            }
                            return null;
                        } catch (Throwable th) {
                            th = th;
                            if (byteArrayOutputStream != null) {
                            }
                            if (fileInputStream != null) {
                            }
                            throw th;
                        }
                    }
                }
                if (byteArrayOutputStream != null) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    return byteArray;
                }
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                try {
                    fileInputStream.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
                return null;
            } catch (Exception e7) {
                e = e7;
                byteArrayOutputStream = null;
                e.printStackTrace();
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e10) {
                        e10.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e11) {
                        e11.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e12) {
            e = e12;
            fileInputStream = null;
            byteArrayOutputStream = null;
            e.printStackTrace();
            if (byteArrayOutputStream != null) {
            }
            if (fileInputStream != null) {
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
            }
            if (fileInputStream != null) {
            }
            throw th;
        }
    }

    public static String getFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf2 != -1) {
            return str.substring(lastIndexOf + 1, lastIndexOf2);
        }
        return null;
    }

    public static File getOutputMediaFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (file.exists() || file.mkdirs()) {
            String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
            return new File(file.getPath() + File.separator + "IMG_" + format + Storage.JPEG_SUFFIX);
        }
        Log.e("FileUtil", "failed to create directory");
        return null;
    }

    public static boolean hasDir(String str) {
        File file = new File(str);
        return file.exists() && file.isDirectory();
    }

    private static boolean hasParentDir(InputStream inputStream) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        boolean z = true;
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return z;
            } else if (nextEntry.getName().equals(ComposerHelper.CONFIG_FILE_NAME)) {
                z = false;
            }
        }
    }

    public static boolean makeDir(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    public static boolean makeNoMediaDir(String str) {
        if (makeDir(str)) {
            makeSureNoMedia(str);
            return true;
        }
        makeSureNoMedia(str);
        return false;
    }

    public static boolean makeSureNoMedia(String str) {
        File file = new File(str, Storage.AVOID_SCAN_FILE_NAME);
        if (file.exists()) {
            return true;
        }
        try {
            file.createNewFile();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void saveBitmap(Bitmap bitmap, String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.d(TAG, "saveBitmap: " + file.getAbsolutePath());
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.d(TAG, "saveBitmap: return");
        }
    }
}
