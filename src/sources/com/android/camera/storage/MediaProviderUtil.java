package com.android.camera.storage;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.android.camera.log.Log;
import com.arcsoft.camera.wideselfie.WideSelfieEngine;
import java.io.File;

public class MediaProviderUtil {
    public static final String TAG = "MediaProviderUtil";

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    private MediaProviderUtil() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0067, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0068, code lost:
        if (r10 != null) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006a, code lost:
        $closeResource(r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006d, code lost:
        throw r0;
     */
    public static Uri getContentUriFromPath(Context context, String str) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        String[] strArr = {"_id", "media_type"};
        try {
            Cursor query = context.getContentResolver().query(contentUri, strArr, "_data = ?", new String[]{str}, "");
            if (query.moveToNext()) {
                long j = (long) query.getInt(0);
                int i = query.getInt(1);
                if (i == 1) {
                    Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Images.Media.getContentUri("external"), j);
                    if (query != null) {
                        $closeResource(null, query);
                    }
                    return withAppendedId;
                } else if (i == 3) {
                    Uri withAppendedId2 = ContentUris.withAppendedId(MediaStore.Video.Media.getContentUri("external"), j);
                    if (query != null) {
                        $closeResource(null, query);
                    }
                    return withAppendedId2;
                } else {
                    Uri withAppendedId3 = ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), j);
                    if (query != null) {
                        $closeResource(null, query);
                    }
                    return withAppendedId3;
                }
            } else {
                if (query != null) {
                    $closeResource(null, query);
                }
                return null;
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0040, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        if (r8 != null) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        $closeResource(r7, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
        throw r0;
     */
    private static long getParent(Context context, String str) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        try {
            Cursor query = context.getContentResolver().query(contentUri, new String[]{"_id"}, "_data = ?", new String[]{new File(str).getParent()}, "");
            if (query.moveToNext()) {
                long j = query.getLong(0);
                if (query != null) {
                    $closeResource(null, query);
                }
                return j;
            } else if (query == null) {
                return 0;
            } else {
                $closeResource(null, query);
                return 0;
            }
        } catch (Exception unused) {
            return 0;
        }
    }

    public static Uri insertCameraDirectory(Context context, String str) {
        long parent = getParent(context, str);
        if (parent > 0) {
            return insertDirectory(context, str, parent);
        }
        Uri insertDirectory = insertDirectory(context, new File(str).getParent(), 0);
        if (insertDirectory == null) {
            return null;
        }
        return insertDirectory(context, str, ContentUris.parseId(insertDirectory));
    }

    public static Uri insertDirectory(Context context, String str, long j) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        ContentValues contentValues = new ContentValues();
        contentValues.put("format", Integer.valueOf((int) WideSelfieEngine.HORIZ_FLIP));
        contentValues.put("media_type", (Integer) 0);
        contentValues.put("_data", str);
        contentValues.put("parent", Long.valueOf(j));
        File file = new File(str);
        if (file.exists()) {
            contentValues.put("date_modified", Long.valueOf(file.lastModified() / 1000));
        }
        try {
            return context.getContentResolver().insert(contentUri, contentValues);
        } catch (Exception e2) {
            Log.w(TAG, "insertDirectory fail, path = " + str, e2);
            return null;
        }
    }
}
