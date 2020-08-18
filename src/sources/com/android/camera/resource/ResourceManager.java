package com.android.camera.resource;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Deprecated
public class ResourceManager {
    private static final String PREFIX_CLOUD_RESOURCE = "https://";
    private static final String PREFIX_EXTERNAL_RESOURCE = "sdcard/";
    private static final String PREFIX_NATIVE_RESOURCE = "assets://";
    private static final String TEST_PATH = (Environment.getExternalStorageDirectory().getPath() + "/MIUI/Camera/Test/");
    private static ResourceManager sInstance;
    private SparseArray<BaseResourceList> mStoragedResourceList = new SparseArray<>();

    public interface ResourceListener {
        void onExtraFinished(boolean z);
    }

    private void decompressNativeResource(Context context, BaseResourceItem baseResourceItem, String str, boolean z) throws IOException {
        String str2 = baseResourceItem.uri.split(PREFIX_NATIVE_RESOURCE)[1];
        String str3 = str + baseResourceItem.id + File.separator;
        if (!z || !baseResourceItem.simpleVerification(str3)) {
            String str4 = TEST_PATH + str2;
            if (new File(str4).exists()) {
                Util.verifySdcardZip(context, str4, str3, 32768);
            } else {
                Util.verifyAssetZip(context, str2, str3, 32768);
            }
            baseResourceItem.onDecompressFinished(str3);
            return;
        }
        baseResourceItem.onDecompressFinished(str3);
    }

    private void decompressSdcardResource(Context context, String str, BaseResourceItem baseResourceItem, String str2) throws IOException {
        baseResourceItem.onDecompressFinished(str2 + baseResourceItem.id + File.separator);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0095 A[SYNTHETIC, Splitter:B:32:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    private void decompressZip(String str, String str2) {
        ZipFile zipFile = null;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            ZipFile zipFile2 = new ZipFile(str);
            try {
                Enumeration<? extends ZipEntry> entries = zipFile2.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                    String name = zipEntry.getName();
                    if (zipEntry.isDirectory()) {
                        new File(str2 + name).mkdirs();
                    } else {
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(str2 + name));
                        byte[] bArr = new byte[1024];
                        InputStream inputStream = zipFile2.getInputStream(zipEntry);
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                            fileOutputStream.flush();
                        }
                        fileOutputStream.close();
                        inputStream.close();
                    }
                }
                try {
                    zipFile2.close();
                } catch (IOException unused) {
                }
            } catch (Exception e2) {
                e = e2;
                zipFile = zipFile2;
                try {
                    e.printStackTrace();
                    if (zipFile == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    zipFile2 = zipFile;
                    if (zipFile2 != null) {
                        try {
                            zipFile2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (zipFile2 != null) {
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            if (zipFile == null) {
                zipFile.close();
            }
        }
    }

    public static final void destroy() {
        ResourceManager resourceManager = sInstance;
        if (resourceManager != null) {
            resourceManager.mStoragedResourceList.clear();
            sInstance = null;
        }
    }

    public static ResourceManager getInstance() {
        if (sInstance == null) {
            synchronized (ResourceManager.class) {
                if (sInstance == null) {
                    sInstance = new ResourceManager();
                }
            }
        }
        return sInstance;
    }

    public String convertStreamToString(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }

    public <P extends BaseResourceList> void decompressResource(Context context, P p, String str, boolean z) {
        try {
            for (BaseResourceItem baseResourceItem : p.getResourceList()) {
                String str2 = baseResourceItem.uri;
                if (str2.startsWith(PREFIX_NATIVE_RESOURCE)) {
                    decompressNativeResource(context, baseResourceItem, str, z);
                } else if (str2.startsWith(PREFIX_EXTERNAL_RESOURCE)) {
                    decompressSdcardResource(context, baseResourceItem.uri, baseResourceItem, str);
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        this.mStoragedResourceList.put(p.getResourceType(), p);
    }

    public void downloadCLoudResource(Context context, BaseResourceItem baseResourceItem, String str) throws IOException {
    }

    public String getAssetCache(String str, Context context) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return convertStreamToString(context.getApplicationContext().getAssets().open(str));
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public <P extends BaseResourceList> P getResourceList(int i) {
        return this.mStoragedResourceList.get(i);
    }
}
