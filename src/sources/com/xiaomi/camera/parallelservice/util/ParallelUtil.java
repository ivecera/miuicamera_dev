package com.xiaomi.camera.parallelservice.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.android.camera.db.element.SaveTask;
import com.android.camera.log.Log;
import com.google.android.apps.photos.api.ProcessingMetadataQuery;

public class ParallelUtil {
    private static final String PROCESSING_URI = "content://com.xiaomi.camera.parallelservice.provider.SpecialTypesProvider/processing";
    /* access modifiers changed from: private */
    public static final String TAG = "ParallelUtil";

    public static class DEBUG {
        public static final boolean ENABLE = false;

        public static void crash() {
        }

        public static void doAssert(boolean z) {
        }

        public static void doAssert(boolean z, String str) {
        }
    }

    public static class ParallelProvider {
        public static void deleteInProvider(Context context, long j) {
            deleteInProvider(context, ParallelUtil.getResultUri(j));
        }

        public static void deleteInProvider(Context context, Uri uri) {
            if (context != null) {
                String access$000 = ParallelUtil.TAG;
                Log.v(access$000, "deleteInProvider resultUri: " + uri);
                context.getContentResolver().delete(uri, null, null);
            }
        }
    }

    public static class ParallelService {
        public static void start(Context context) {
        }
    }

    public static Uri getResultUri(long j) {
        return ContentUris.withAppendedId(Uri.parse(PROCESSING_URI), j);
    }

    public static void insertImageToParallelService(Context context, long j, String str) {
        String str2 = TAG;
        Log.i(str2, "algo db: first: " + j + " | " + str);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProcessingMetadataQuery.MEDIA_STORE_ID, Long.valueOf(j));
        contentValues.put(ProcessingMetadataQuery.MEDIA_PATH, str);
        try {
            Uri insert = context.getContentResolver().insert(Uri.parse(PROCESSING_URI), contentValues);
            String str3 = TAG;
            Log.i(str3, "algo db: uri: " + insert.toString());
        } catch (Exception e2) {
            Log.e(TAG, "Error! insert to parallel provider failed!!!", e2);
        }
    }

    public static void markTaskFinish(Context context, SaveTask saveTask, boolean z) {
        String str = TAG;
        Log.d(str, "algo db: finish: " + saveTask.getMediaStoreId() + " | " + saveTask.getPath());
        if (z) {
            ParallelExifUtil.updateExif(saveTask.getPath());
        }
        ParallelProvider.deleteInProvider(context, saveTask.getMediaStoreId().longValue());
    }
}
