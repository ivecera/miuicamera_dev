package com.xiaomi.camera.parallelservice.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.android.camera.db.DbContainer;
import com.android.camera.db.DbRepository;
import com.android.camera.db.element.SaveTask;
import com.android.camera.db.item.DbItemSaveTask;
import com.android.camera.log.Log;
import com.arcsoft.camera.wideselfie.WideSelfieEngine;
import com.google.android.apps.photos.api.ProcessingMetadataQuery;
import java.io.File;
import java.io.FileNotFoundException;

public class ParallelProcessProvider extends ContentProvider {
    private static final int DELETE_BY_ID = 6;
    private static final String MATCH_PATH_DELETE_BY_ID = "delete/#";
    private static final String MATCH_PROCESSING_BY_ID = "processing/#";
    private static final String MATCH_PROCESSING_METADATA = "processing";
    private static final int QUERY_PROCESSING_METADATA = 7;
    private static final int QUERY_PROCESSING_METADATA_BY_ID = 8;
    private static final String TAG = "ParallelProcessProvider";
    private static final int VERSION = 1;
    private DbItemSaveTask dbItemSaveTask;
    private UriMatcher mUriMatcher;

    private int deleteProcessingMetadata(Uri uri) {
        long parseId = ContentUris.parseId(uri);
        SaveTask itemByMediaId = this.dbItemSaveTask.getItemByMediaId(Long.valueOf(parseId));
        Log.d(TAG, "deleteProcessingMetadata: mediaStoreId=" + parseId);
        if (itemByMediaId != null) {
            this.dbItemSaveTask.removeItem(itemByMediaId);
            notifyChange(uri);
            return 1;
        }
        Log.v(TAG, "deleteProcessingMetadata: no match task found");
        return 0;
    }

    private Uri insertProcessingMetadata(Uri uri, @Nullable Long l, String str) {
        if (l == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("error insert values");
        }
        Log.d(TAG, "algo db: insert: " + l + " | " + str);
        SaveTask itemByPath = this.dbItemSaveTask.getItemByPath(str);
        itemByPath.setMediaStoreId(l);
        this.dbItemSaveTask.updateItem(itemByPath);
        Uri withAppendedPath = Uri.withAppendedPath(uri, l.toString());
        notifyChange(withAppendedPath);
        return withAppendedPath;
    }

    private ParcelFileDescriptor loadProcessingImage(long j) throws FileNotFoundException {
        SaveTask itemByMediaId = this.dbItemSaveTask.getItemByMediaId(Long.valueOf(j));
        if (itemByMediaId != null) {
            return ParcelFileDescriptor.open(new File(itemByMediaId.getPath()), WideSelfieEngine.MPAF_RGB_BASE);
        }
        throw new FileNotFoundException("Media removed: " + j);
    }

    private void notifyChange(Uri uri) {
        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }
    }

    private Cursor queryProcessingMetadata(@Nullable Long l) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{ProcessingMetadataQuery.MEDIA_STORE_ID, ProcessingMetadataQuery.MEDIA_PATH, ProcessingMetadataQuery.PROGRESS_STATUS, ProcessingMetadataQuery.PROGRESS_PERCENTAGE, ProcessingMetadataQuery.START_TIME});
        boolean z = l == null;
        for (SaveTask saveTask : this.dbItemSaveTask.getAllItems()) {
            if (z || saveTask.getMediaStoreId() == l) {
                matrixCursor.addRow(new Object[]{saveTask.getMediaStoreId(), saveTask.getPath(), Integer.valueOf(ProcessingMetadataQuery.ProgressStatus.INDETERMINATE.getIdentifier()), Integer.valueOf(saveTask.getPercentage()), saveTask.getStartTime()});
            }
        }
        matrixCursor.moveToPosition(-1);
        return matrixCursor;
    }

    private Bundle querySpecialTypesVersion() {
        Bundle bundle = new Bundle();
        bundle.putInt("version", 1);
        return bundle;
    }

    private void updateProcessingMetadata(long j, int i) {
        Log.v(TAG, "updateProcessingMetadata: mediaStoreId=" + j);
        SaveTask itemByMediaId = this.dbItemSaveTask.getItemByMediaId(Long.valueOf(j));
        if (itemByMediaId != null) {
            itemByMediaId.setPercentage(i);
            this.dbItemSaveTask.updateItem(itemByMediaId);
            return;
        }
        Log.v(TAG, "updateProcessingMetadata: no match task found");
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        this.mUriMatcher = new UriMatcher(-1);
        this.mUriMatcher.addURI(providerInfo.authority, MATCH_PATH_DELETE_BY_ID, 6);
        this.mUriMatcher.addURI(providerInfo.authority, "processing", 7);
        this.mUriMatcher.addURI(providerInfo.authority, MATCH_PROCESSING_BY_ID, 8);
    }

    @Nullable
    public Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        return TextUtils.equals("version", str) ? querySpecialTypesVersion() : super.call(str, str2, bundle);
    }

    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        Log.v(TAG, "delete uri: " + uri);
        getContext();
        if (this.mUriMatcher.match(uri) == 8) {
            return deleteProcessingMetadata(uri);
        }
        throw new IllegalArgumentException("Unrecognized uri: " + uri);
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.v(TAG, "insert uri: " + uri);
        getContext();
        if (this.mUriMatcher.match(uri) != 7) {
            throw new IllegalArgumentException("Unrecognized uri: " + uri);
        } else if (contentValues != null) {
            return insertProcessingMetadata(uri, contentValues.getAsLong(ProcessingMetadataQuery.MEDIA_STORE_ID), contentValues.getAsString(ProcessingMetadataQuery.MEDIA_PATH));
        } else {
            throw new IllegalArgumentException("contentValues is null");
        }
    }

    public boolean onCreate() {
        Log.v(TAG, "onCreate");
        DbContainer.init(getContext());
        this.dbItemSaveTask = DbRepository.dbItemSaveTask();
        return true;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String str) throws FileNotFoundException {
        if (!"r".equals(str)) {
            throw new IllegalArgumentException("Unsupported mode: " + str);
        } else if (this.mUriMatcher.match(uri) == 8) {
            Log.v(TAG, "loading processing thumb: " + uri);
            return loadProcessingImage(ContentUris.parseId(uri));
        } else {
            throw new IllegalArgumentException("Unrecognized format: " + uri);
        }
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        Log.v(TAG, "query uri: " + uri);
        getContext();
        int match = this.mUriMatcher.match(uri);
        if (match == 7) {
            return queryProcessingMetadata(null);
        }
        if (match == 8) {
            return queryProcessingMetadata(Long.valueOf(ContentUris.parseId(uri)));
        }
        throw new IllegalArgumentException("Unrecognized uri: " + uri);
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        Log.d(TAG, "update uri: " + uri);
        if (this.mUriMatcher.match(uri) != 8) {
            throw new IllegalArgumentException("Unrecognized uri: " + uri);
        } else if (contentValues != null) {
            updateProcessingMetadata(ContentUris.parseId(uri), contentValues.getAsInteger(ProcessingMetadataQuery.PROGRESS_PERCENTAGE).intValue());
            notifyChange(uri);
            return 1;
        } else {
            throw new IllegalArgumentException("contentValues is null");
        }
    }
}
