package com.android.camera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.v4.provider.DocumentFile;
import android.text.TextUtils;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FileCompat {
    static final FileCompatCommonImpl IMPL_COMMON;
    static final FileCompatOperateImpl IMPL_OPERATE;
    public static final int REQUEST_CODE_OPEN_EXTERNAL_DOCUMENT_PERMISSION = 161;
    public static final String TAG = "FileCompat";

    static class BaseFileCompatImpl implements FileCompatOperateImpl {
        HashMap<String, DocumentFile> mDocumentFileHashMap = new HashMap<>(1);

        BaseFileCompatImpl() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:37:0x004d A[SYNTHETIC, Splitter:B:37:0x004d] */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0052 A[SYNTHETIC, Splitter:B:41:0x0052] */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x0060 A[SYNTHETIC, Splitter:B:49:0x0060] */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x0065 A[SYNTHETIC, Splitter:B:53:0x0065] */
        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public boolean copyFile(String str, String str2) {
            FileInputStream fileInputStream;
            OutputStream outputStream;
            FileInputStream fileInputStream2 = null;
            r2 = null;
            OutputStream outputStream2 = null;
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    OutputStream fileOutputStream = getFileOutputStream(str2, false);
                    if (fileOutputStream == null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception unused) {
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e2) {
                                Log.w(FileCompat.TAG, "copyFile error", e2);
                            }
                        }
                        return false;
                    }
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            try {
                                break;
                            } catch (Exception unused2) {
                            }
                        }
                    }
                    fileInputStream.close();
                    if (fileOutputStream == null) {
                        return true;
                    }
                    try {
                        fileOutputStream.close();
                        return true;
                    } catch (Exception e3) {
                        Log.w(FileCompat.TAG, "copyFile error", e3);
                        return true;
                    }
                } catch (Exception e4) {
                    e = e4;
                    outputStream = null;
                    fileInputStream2 = fileInputStream;
                    try {
                        Log.w(FileCompat.TAG, "copyFile error", e);
                        if (fileInputStream2 != null) {
                        }
                        if (outputStream != null) {
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        outputStream2 = outputStream;
                        if (fileInputStream != null) {
                        }
                        if (outputStream2 != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream != null) {
                    }
                    if (outputStream2 != null) {
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                outputStream = null;
                Log.w(FileCompat.TAG, "copyFile error", e);
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused3) {
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e6) {
                        Log.w(FileCompat.TAG, "copyFile error", e6);
                    }
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused4) {
                    }
                }
                if (outputStream2 != null) {
                    try {
                        outputStream2.close();
                    } catch (Exception e7) {
                        Log.w(FileCompat.TAG, "copyFile error", e7);
                    }
                }
                throw th;
            }
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public boolean createNewFile(String str) {
            try {
                return new File(str).createNewFile();
            } catch (IOException e2) {
                Log.w(FileCompat.TAG, "createNewFile error", e2);
                return false;
            }
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public String createNewFileFixPath(String str) {
            if (createNewFile(str) == 1) {
                return str;
            }
            return null;
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public FileWrapper createNewFileWrapper(String str) {
            return null;
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public boolean deleteFile(String str) {
            return new File(str).delete();
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public boolean exists(String str) {
            return new File(str).exists();
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public OutputStream getFileOutputStream(String str, boolean z) {
            if (z != 1 && exists(str) != 1) {
                return null;
            }
            try {
                return new FileOutputStream(new File(str));
            } catch (Exception unused) {
                Log.w(FileCompat.TAG, "getFileOutputStream error, path = " + str);
                return null;
            }
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public ParcelFileDescriptor getParcelFileDescriptor(String str, boolean z) throws IOException {
            return CameraAppImpl.getAndroidContext().getContentResolver().openFileDescriptor(Uri.fromFile(new File(str)), "rw");
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public boolean mkdirs(String str) {
            return new File(str).mkdirs();
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public void removeDocumentFileForPath(String str) {
            this.mDocumentFileHashMap.remove(str);
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl
        public boolean renameFile(String str, String str2) throws IOException {
            return new File(str).renameTo(new File(str2));
        }
    }

    interface FileCompatCommonImpl {
        String getSDPath(String str);

        boolean getStorageAccessForLOLLIPOP(Activity activity, String str);

        Uri getTreeUri(String str);

        boolean handleActivityResult(Activity activity, int i, int i2, Intent intent);

        boolean hasStoragePermission(String str);

        boolean isExternalSDFile(String str);

        void updateSDPath();
    }

    interface FileCompatOperateImpl {
        boolean copyFile(String str, String str2);

        boolean createNewFile(String str);

        String createNewFileFixPath(String str);

        FileWrapper createNewFileWrapper(String str);

        boolean deleteFile(String str);

        boolean exists(String str);

        OutputStream getFileOutputStream(String str, boolean z);

        ParcelFileDescriptor getParcelFileDescriptor(String str, boolean z) throws IOException;

        boolean mkdirs(String str);

        void removeDocumentFileForPath(String str);

        boolean renameFile(String str, String str2) throws IOException;
    }

    static class FileWrapper {
        DocumentFile documentFile;
        File legacyFile;

        FileWrapper() {
        }

        public long getLength() {
            return this.legacyFile.length();
        }
    }

    static class KitKatFileCompatCommonImpl implements FileCompatCommonImpl {
        protected String accessSDPath;
        protected String[] sdPaths;

        public KitKatFileCompatCommonImpl() {
            update();
        }

        /* access modifiers changed from: protected */
        @TargetApi(19)
        public String[] getExtSDCardPaths() {
            String[] strArr = new String[0];
            ArrayList arrayList = new ArrayList();
            String sdcardPath = CompatibilityUtils.getSdcardPath(CameraAppImpl.getAndroidContext());
            Log.d(FileCompat.TAG, "getExtSDCardPaths: activePath = " + sdcardPath);
            if (!TextUtils.isEmpty(sdcardPath)) {
                arrayList.add(sdcardPath);
            }
            return !arrayList.isEmpty() ? (String[]) arrayList.toArray(new String[arrayList.size()]) : strArr;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public String getSDPath(String str) {
            String[] strArr = this.sdPaths;
            if (strArr == null) {
                return null;
            }
            for (String str2 : strArr) {
                if (str.startsWith(str2) == 1) {
                    return str2;
                }
            }
            return null;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public boolean getStorageAccessForLOLLIPOP(Activity activity, String str) {
            return false;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public Uri getTreeUri(String str) {
            return null;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public boolean handleActivityResult(Activity activity, int i, int i2, Intent intent) {
            return false;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public boolean hasStoragePermission(String str) {
            return true;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public boolean isExternalSDFile(String str) {
            String[] strArr = this.sdPaths;
            if (strArr == null) {
                return false;
            }
            for (String str2 : strArr) {
                if (str.startsWith(str2) == 1) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: protected */
        public void update() {
            this.sdPaths = getExtSDCardPaths();
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl
        public void updateSDPath() {
            update();
        }
    }

    static class LollipopFileCompatCommonImpl extends KitKatFileCompatCommonImpl {
        private static final String SD_PATH_TREE_URI = "sd_path_tree_uri";

        public LollipopFileCompatCommonImpl() {
            update();
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl, com.android.camera.FileCompat.KitKatFileCompatCommonImpl
        @TargetApi(21)
        public boolean getStorageAccessForLOLLIPOP(Activity activity, String str) {
            String[] extSDCardPaths = getExtSDCardPaths();
            if (extSDCardPaths == null || extSDCardPaths.length == 0) {
                return false;
            }
            int length = extSDCardPaths.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String str2 = extSDCardPaths[i];
                if (str.startsWith(str2) == 1) {
                    ((KitKatFileCompatCommonImpl) this).accessSDPath = str2;
                    break;
                }
                i++;
            }
            List<StorageVolume> storageVolumes = ((StorageManager) activity.getSystemService("storage")).getStorageVolumes();
            if (storageVolumes.size() > 1) {
                Intent createAccessIntent = storageVolumes.get(1).createAccessIntent(Environment.DIRECTORY_DCIM);
                if (createAccessIntent == null) {
                    Log.w(FileCompat.TAG, "getStorageAccessForLOLLIPOP error, intent is null");
                    return false;
                }
                try {
                    activity.startActivityForResult(createAccessIntent, 161);
                } catch (Exception e2) {
                    Log.w(FileCompat.TAG, "getStorageAccessForLOLLIPOP error", e2);
                }
            }
            return true;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl, com.android.camera.FileCompat.KitKatFileCompatCommonImpl
        public Uri getTreeUri(String str) {
            String string = CameraAppImpl.getAndroidContext().getSharedPreferences(SD_PATH_TREE_URI, 0).getString(str, null);
            if (string == null) {
                return null;
            }
            return Uri.parse(string);
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl, com.android.camera.FileCompat.KitKatFileCompatCommonImpl
        @TargetApi(19)
        public boolean handleActivityResult(Activity activity, int i, int i2, Intent intent) {
            if (i == 161 && i2 == -1) {
                Uri data = intent.getData();
                if (data == null) {
                    Log.d(FileCompat.TAG, "handleActivityResult: uri is null, documents permission is Failed!");
                    return false;
                } else if (!DocumentFile.fromTreeUri(activity, data).exists()) {
                    Log.d(FileCompat.TAG, "handleActivityResult: documentFile is not exist, documents permission is Failed!");
                    return false;
                } else {
                    try {
                        activity.getContentResolver().takePersistableUriPermission(data, intent.getFlags() & 3);
                        activity.getSharedPreferences(SD_PATH_TREE_URI, 0).edit().putString(((KitKatFileCompatCommonImpl) this).accessSDPath, data.toString()).apply();
                        return true;
                    } catch (Exception e2) {
                        Log.w(FileCompat.TAG, "cacheUri failed, uri = " + data, e2);
                        update();
                    }
                }
            }
            return false;
        }

        @Override // com.android.camera.FileCompat.FileCompatCommonImpl, com.android.camera.FileCompat.KitKatFileCompatCommonImpl
        public boolean hasStoragePermission(String str) {
            if (!isExternalSDFile(str)) {
                return true;
            }
            String sDPath = super.getSDPath(str);
            if (sDPath == null || getTreeUri(sDPath) == null) {
                return false;
            }
            DocumentFile fromTreeUri = DocumentFile.fromTreeUri(CameraAppImpl.getAndroidContext(), getTreeUri(sDPath));
            Log.d(FileCompat.TAG, "hasStoragePermission document = " + fromTreeUri);
            if (fromTreeUri == null) {
                return false;
            }
            Log.d(FileCompat.TAG, "hasStoragePermission document = " + fromTreeUri.exists() + ", " + fromTreeUri.canRead() + ", " + fromTreeUri.canWrite());
            return (fromTreeUri.exists() == 1 && fromTreeUri.canRead() == 1 && fromTreeUri.canWrite() == 1) ? true : false;
        }
    }

    static class LollipopFileCompatImpl extends BaseFileCompatImpl {
        LollipopFileCompatImpl() {
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public boolean copyFile(String str, String str2) {
            return super.copyFile(str, str2);
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public boolean createNewFile(String str) {
            if (super.createNewFile(str) == 1) {
                return true;
            }
            if (!FileCompat.isSDFile(str)) {
                return false;
            }
            return getDocumentFileByPath(str, true, null, false) != null ? true : false;
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public String createNewFileFixPath(String str) {
            DocumentFile documentFileByPath;
            if (super.createNewFile(str) == 1) {
                return str;
            }
            if (FileCompat.isSDFile(str) != 1 || (documentFileByPath = getDocumentFileByPath(str, true, null, false)) == null) {
                return null;
            }
            String substring = str.substring(0, str.lastIndexOf(File.separator));
            return substring + File.separator + documentFileByPath.getName();
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public boolean deleteFile(String str) {
            boolean z = true;
            if (super.deleteFile(str) == 1) {
                return true;
            }
            if (!FileCompat.isSDFile(str)) {
                return false;
            }
            DocumentFile documentFileByPath = getDocumentFileByPath(str, false, null, false);
            if (documentFileByPath != null) {
                z = documentFileByPath.delete();
            }
            removeDocumentFileForPath(str);
            return z;
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public boolean exists(String str) {
            if (super.exists(str) == 1) {
                return true;
            }
            if (!FileCompat.isSDFile(str)) {
                return false;
            }
            return getDocumentFileByPath(str, false, null, false) != null ? true : false;
        }

        /* JADX WARN: Failed to insert an additional move for type inference into block B:70:0x00e9 */
        /* JADX WARN: Failed to insert an additional move for type inference into block B:71:0x00e9 */
        /* JADX DEBUG: Additional 1 move instruction added to help type inference */
        public DocumentFile getDocumentFileByPath(String str, boolean z, String str2, boolean z2) {
            String str3;
            DocumentFile createDirectory;
            Log.d(FileCompat.TAG, "getDocumentFileByPath start>>");
            String access$000 = FileCompat.getSDPath(str);
            String str4 = null;
            str4 = null;
            if (access$000 == null) {
                Log.w(FileCompat.TAG, "getDocumentFileByPath: no sd path for " + str);
                return null;
            }
            DocumentFile documentFile = ((BaseFileCompatImpl) this).mDocumentFileHashMap.get(str);
            if (documentFile != null && documentFile.exists()) {
                return documentFile;
            }
            Uri access$100 = FileCompat.getTreeUri(access$000);
            if (access$100 == null) {
                Log.w(FileCompat.TAG, "getDocumentFileByPath: no tree uri for " + access$000);
                return null;
            }
            DocumentFile fromTreeUri = DocumentFile.fromTreeUri(CameraAppImpl.getAndroidContext(), access$100);
            String str5 = access$000 + File.separator + Environment.DIRECTORY_DCIM;
            if (str.equals(str5) == 1) {
                return fromTreeUri;
            }
            String substring = str.substring(str5.length() + 1);
            if (TextUtils.isEmpty(substring) == 1) {
                Log.w(FileCompat.TAG, "getDocumentFileByPath: empty relative path");
                return null;
            }
            String[] split = substring.split("\\" + File.separator);
            String[] strArr = new String[(split.length - 1)];
            System.arraycopy(split, 0, strArr, 0, split.length - 1);
            int length = strArr.length;
            DocumentFile documentFile2 = fromTreeUri;
            int i = 0;
            boolean z3 = false;
            while (i < length) {
                String str6 = strArr[i];
                if (documentFile2 == null) {
                    break;
                }
                if (z3 == 1) {
                    createDirectory = documentFile2.createDirectory(str6);
                } else {
                    DocumentFile findFile = documentFile2.findFile(str6);
                    if (findFile != null) {
                        documentFile2 = findFile;
                        i++;
                        z3 = z3;
                    } else if (z == 1) {
                        createDirectory = documentFile2.createDirectory(str6);
                        z3 = true;
                    } else {
                        Log.d(FileCompat.TAG, "getDocumentFileByPath: no document for " + str6);
                        return null;
                    }
                }
                documentFile2 = createDirectory;
                i++;
                z3 = z3;
            }
            if (documentFile2 == null) {
                Log.d(FileCompat.TAG, "getDocumentFileByPath: no document for " + substring);
                return null;
            }
            String str7 = split[split.length - 1];
            Log.d(FileCompat.TAG, "getDocumentFileByPath: createIfNotFound = " + z);
            long currentTimeMillis = System.currentTimeMillis();
            if (z != 1) {
                documentFile = documentFile2.findFile(str7);
            } else if (z2 == 1) {
                try {
                    documentFile = documentFile2.findFile(str7);
                    if (documentFile == null) {
                        documentFile = documentFile2.createDirectory(str7);
                    }
                } catch (Exception e2) {
                    Log.w(FileCompat.TAG, "createFile error", e2);
                }
            } else {
                int indexOf = str7.indexOf(".");
                if (!TextUtils.isEmpty(str2) || indexOf <= 0) {
                    str3 = str2;
                } else {
                    str3 = FileCompat.getMimeTypeFromPath(str7);
                    if (!TextUtils.isEmpty(str3)) {
                        str4 = str7.substring(0, indexOf);
                    }
                }
                if (str4 != null) {
                    str7 = str4;
                }
                try {
                    documentFile = documentFile2.createFile(str3, str7);
                } catch (Exception e3) {
                    Log.w(FileCompat.TAG, "createFile error", e3);
                }
            }
            if (documentFile != null) {
                ((BaseFileCompatImpl) this).mDocumentFileHashMap.put(str, documentFile);
            }
            Log.d(FileCompat.TAG, "getDocumentFileByPath end<< cost time = " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            return documentFile;
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public OutputStream getFileOutputStream(String str, boolean z) {
            DocumentFile documentFileByPath;
            OutputStream fileOutputStream;
            if ((Build.VERSION.SDK_INT < 28 || !FileCompat.isSDFile(str)) && (fileOutputStream = super.getFileOutputStream(str, z)) != null) {
                return fileOutputStream;
            }
            if (FileCompat.isSDFile(str) != 1 || (documentFileByPath = getDocumentFileByPath(str, z, null, false)) == null) {
                return null;
            }
            try {
                return CameraAppImpl.getAndroidContext().getContentResolver().openOutputStream(documentFileByPath.getUri());
            } catch (FileNotFoundException e2) {
                Log.w(FileCompat.TAG, "getFileOutputStream error", e2);
                return null;
            }
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public ParcelFileDescriptor getParcelFileDescriptor(String str, boolean z) throws IOException {
            if (!FileCompat.isSDFile(str)) {
                return super.getParcelFileDescriptor(str, z);
            }
            return CameraAppImpl.getAndroidContext().getContentResolver().openFileDescriptor(getDocumentFileByPath(str, z, FileCompat.getMimeTypeFromPath(str), false).getUri(), "rw");
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public boolean mkdirs(String str) {
            if (super.mkdirs(str) == 1) {
                return true;
            }
            if (!FileCompat.isSDFile(str)) {
                return false;
            }
            return getDocumentFileByPath(str, true, null, true) != null ? true : false;
        }

        @Override // com.android.camera.FileCompat.FileCompatOperateImpl, com.android.camera.FileCompat.BaseFileCompatImpl
        public boolean renameFile(String str, String str2) throws IOException {
            if (super.renameFile(str, str2) == 1) {
                return true;
            }
            if (!new File(str).getParent().equalsIgnoreCase(new File(str2).getParent())) {
                Log.w(FileCompat.TAG, "only support rename to the same folder");
                return false;
            }
            String name = new File(str2).getName();
            DocumentFile documentFileByPath = getDocumentFileByPath(str, false, null, false);
            if (documentFileByPath == null) {
                Log.w(FileCompat.TAG, "renameFile: null document");
                return false;
            }
            try {
                return documentFileByPath.renameTo(name);
            } catch (Exception e2) {
                throw new IOException("renameFile error, path = " + str, e2);
            }
        }
    }

    static class MarshmallowFileCompatImpl extends LollipopFileCompatImpl {
        MarshmallowFileCompatImpl() {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 28) {
            IMPL_COMMON = new LollipopFileCompatCommonImpl();
            IMPL_OPERATE = new MarshmallowFileCompatImpl();
            return;
        }
        IMPL_COMMON = new KitKatFileCompatCommonImpl();
        IMPL_OPERATE = new BaseFileCompatImpl();
    }

    public static boolean copyFile(String str, String str2) {
        return IMPL_OPERATE.copyFile(str, str2);
    }

    public static boolean createNewFile(String str) {
        return IMPL_OPERATE.createNewFile(str);
    }

    public static String createNewFileFixPath(String str) {
        return IMPL_OPERATE.createNewFileFixPath(str);
    }

    public static boolean deleteFile(String str) {
        return IMPL_OPERATE.deleteFile(str);
    }

    public static boolean exists(String str) {
        return IMPL_OPERATE.exists(str);
    }

    public static OutputStream getFileOutputStream(String str, boolean z) {
        return IMPL_OPERATE.getFileOutputStream(str, z);
    }

    /* access modifiers changed from: private */
    public static String getMimeTypeFromPath(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return null;
        }
        String lowerCase = str.substring(lastIndexOf + 1).toLowerCase(Locale.ENGLISH);
        if ("jpg".equals(lowerCase) == 1 || "jpeg".equals(lowerCase) == 1) {
            return Storage.MIME_JPEG;
        }
        if ("png".equals(lowerCase) == 1) {
            return "image/png";
        }
        if ("mp4".equals(lowerCase) == 1) {
            return "video/mp4";
        }
        return null;
    }

    public static ParcelFileDescriptor getParcelFileDescriptor(String str, boolean z) throws IOException {
        return IMPL_OPERATE.getParcelFileDescriptor(str, z);
    }

    /* access modifiers changed from: private */
    public static String getSDPath(String str) {
        return IMPL_COMMON.getSDPath(str);
    }

    @TargetApi(21)
    public static boolean getStorageAccessForLOLLIPOP(Activity activity, String str) {
        return IMPL_COMMON.getStorageAccessForLOLLIPOP(activity, str);
    }

    /* access modifiers changed from: private */
    public static Uri getTreeUri(String str) {
        return IMPL_COMMON.getTreeUri(str);
    }

    @TargetApi(21)
    public static boolean handleActivityResult(Activity activity, int i, int i2, Intent intent) {
        return IMPL_COMMON.handleActivityResult(activity, i, i2, intent);
    }

    @TargetApi(21)
    public static boolean hasStoragePermission(String str) {
        return IMPL_COMMON.hasStoragePermission(str);
    }

    @TargetApi(19)
    public static boolean isSDFile(String str) {
        return IMPL_COMMON.isExternalSDFile(str);
    }

    public static boolean mkdirs(String str) {
        return IMPL_OPERATE.mkdirs(str);
    }

    public static void removeDocumentFileForPath(String str) {
        IMPL_OPERATE.removeDocumentFileForPath(str);
    }

    public static boolean renameFile(String str, String str2) throws IOException {
        return IMPL_OPERATE.renameFile(str, str2);
    }

    @TargetApi(21)
    public static void updateSDPath() {
        IMPL_COMMON.updateSDPath();
    }
}
