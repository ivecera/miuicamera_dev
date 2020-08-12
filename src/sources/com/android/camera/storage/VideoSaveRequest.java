package com.android.camera.storage;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.android.camera.FileCompat;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.xiaomi.stat.d;
import java.io.File;

public class VideoSaveRequest implements SaveRequest {
    private static final String TAG = "VideoSaveRequest";
    private Context context;
    private ContentValues mContentValues;
    private boolean mIsFinal;
    public Uri mUri;
    private String mVideoPath;
    private SaverCallback saverCallback;

    public VideoSaveRequest(String str, ContentValues contentValues, boolean z) {
        this.mVideoPath = str;
        this.mContentValues = contentValues;
        this.mIsFinal = z;
    }

    private Uri addVideoToMediaStore(String str, ContentValues contentValues) {
        StringBuilder sb;
        long videoDuration = getVideoDuration(str);
        Uri uri = null;
        if (videoDuration == 0) {
            boolean delete = new File(str).delete();
            Log.e(TAG, "delete invalid video: " + str + ", deleted : " + delete);
            return null;
        }
        updateContentValue(contentValues, new File(str).length(), videoDuration);
        try {
            long currentTimeMillis = System.currentTimeMillis();
            uri = this.context.getContentResolver().insert(Storage.getMediaUri(this.context, true, str), contentValues);
            Log.d(TAG, "addVideoToMediaStore: insert video cost: " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
            sb = new StringBuilder();
        } catch (Exception e2) {
            Log.e(TAG, "failed to add video to media store", e2);
            sb = new StringBuilder();
        } catch (Throwable th) {
            Log.d(TAG, "Current video URI: " + uri);
            throw th;
        }
        sb.append("Current video URI: ");
        sb.append(uri);
        Log.d(TAG, sb.toString());
        return uri;
    }

    private boolean checkExternalStorageThumbnailInterupt(String str) {
        boolean isSecondPhoneStorage = Storage.isSecondPhoneStorage(str);
        boolean isUsePhoneStorage = Storage.isUsePhoneStorage();
        if (!isSecondPhoneStorage || !isUsePhoneStorage) {
            return true;
        }
        Log.w(TAG, "save video: sd card was ejected");
        return false;
    }

    /* JADX INFO: finally extract failed */
    private long getVideoDuration(String str) {
        if (!Storage.isUseDocumentMode()) {
            return Util.getDuration(str);
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, false);
            long duration = Util.getDuration(parcelFileDescriptor.getFileDescriptor());
            Util.closeSafely(parcelFileDescriptor);
            FileCompat.removeDocumentFileForPath(str);
            return duration;
        } catch (Exception unused) {
            Util.closeSafely(parcelFileDescriptor);
            FileCompat.removeDocumentFileForPath(str);
            return 0;
        } catch (Throwable th) {
            Util.closeSafely(parcelFileDescriptor);
            FileCompat.removeDocumentFileForPath(str);
            throw th;
        }
    }

    private ContentValues updateContentValue(ContentValues contentValues, long j, long j2) {
        contentValues.put("_size", Long.valueOf(j));
        contentValues.put("duration", Long.valueOf(j2));
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        return contentValues;
    }

    private int updateVideoToMediaStore(Uri uri, String str, ContentValues contentValues) {
        StringBuilder sb;
        long videoDuration = getVideoDuration(str);
        int i = 0;
        if (videoDuration == 0) {
            boolean delete = new File(str).delete();
            Log.e(TAG, "delete invalid video: " + str + ", deleted : " + delete);
            return 0;
        }
        updateContentValue(contentValues, new File(str).length(), videoDuration);
        try {
            long currentTimeMillis = System.currentTimeMillis();
            i = this.context.getContentResolver().update(uri, contentValues, null, null);
            Log.d(TAG, "updateVideoToMediaStore: insert video cost: " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
            sb = new StringBuilder();
        } catch (Exception e2) {
            Log.e(TAG, "failed to add video to media store", e2);
            sb = new StringBuilder();
        } catch (Throwable th) {
            Log.d(TAG, "Current video URI: " + uri);
            throw th;
        }
        sb.append("Current video URI: ");
        sb.append(uri);
        Log.d(TAG, sb.toString());
        return i;
    }

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return 0;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return this.mIsFinal;
    }

    @Override // com.android.camera.storage.SaveRequest
    public void onFinish() {
        Log.d(TAG, "onFinish: runnable process finished");
        this.saverCallback.onSaveFinish(getSize());
    }

    public void run() {
        save();
        onFinish();
    }

    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        Log.d(TAG, "save video: start");
        String asString = this.mContentValues.getAsString("_data");
        if (!asString.equals(this.mVideoPath)) {
            if (new File(this.mVideoPath).renameTo(new File(asString))) {
                this.mVideoPath = asString;
            } else {
                this.mContentValues.put("_data", this.mVideoPath);
            }
        }
        boolean needThumbnail = this.saverCallback.needThumbnail(isFinal());
        this.mUri = addVideoToMediaStore(this.mVideoPath, this.mContentValues);
        if (this.mUri == null) {
            Log.w(TAG, "insert MediaProvider failed, attempt to find uri by path, " + this.mVideoPath);
            this.mUri = MediaProviderUtil.getContentUriFromPath(this.context, this.mVideoPath);
            if (this.mUri != null) {
                Log.d(TAG, "insert MediaProvider failed, need update mContentValues by Uri");
                updateVideoToMediaStore(this.mUri, this.mVideoPath, this.mContentValues);
            }
        }
        Log.d(TAG, "save video: media has been stored, Uri: " + this.mUri + ", has thumbnail: " + needThumbnail);
        if (this.mUri != null && checkExternalStorageThumbnailInterupt(this.mVideoPath)) {
            boolean z = false;
            if (needThumbnail) {
                Bitmap createVideoThumbnailBitmap = Thumbnail.createVideoThumbnailBitmap(this.mVideoPath, 512, 512);
                if (createVideoThumbnailBitmap != null) {
                    this.saverCallback.postUpdateThumbnail(Thumbnail.createThumbnail(this.mUri, createVideoThumbnailBitmap, 0, false), true);
                } else {
                    this.saverCallback.postHideThumbnailProgressing();
                }
            }
            this.saverCallback.notifyNewMediaData(this.mUri, this.mContentValues.getAsString("title"), 1);
            Context context2 = this.context;
            String str = this.mVideoPath;
            if (this.mContentValues.get("latitude") == null && this.mContentValues.get("longitude") == null) {
                z = true;
            }
            Storage.saveToCloudAlbum(context2, str, -1, z);
        } else if (needThumbnail) {
            this.saverCallback.postHideThumbnailProgressing();
        }
        Log.d(TAG, "save video: end");
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
