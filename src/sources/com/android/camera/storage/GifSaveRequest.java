package com.android.camera.storage;

import android.content.Context;
import android.net.Uri;
import com.android.camera.Thumbnail;
import com.android.camera.log.Log;

public class GifSaveRequest implements SaveRequest {
    private static final String TAG = "GifSaveRequest";
    private Context context;
    private long dateTaken;
    private int height;
    private String mGifPath;
    public Uri mUri;
    private int orientation;
    private SaverCallback saverCallback;
    private String title;
    private int width;

    public GifSaveRequest(String str, long j, String str2, int i, int i2, int i3) {
        this.mGifPath = str;
        this.dateTaken = j;
        this.title = str2;
        this.width = i;
        this.height = i2;
        this.orientation = i3;
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

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return 0;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return true;
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
        Log.d(TAG, "save gif: start");
        boolean needThumbnail = this.saverCallback.needThumbnail(isFinal());
        this.mUri = Storage.addGifToDataBase(this.context, this.mGifPath, this.title, this.dateTaken, this.width, this.height);
        Log.d(TAG, "save gif: media has been stored, Uri: " + this.mUri + ", has thumbnail: " + needThumbnail);
        if (this.mUri != null && checkExternalStorageThumbnailInterupt(this.mGifPath)) {
            if (needThumbnail) {
                Thumbnail createThumbnailFromUri = Thumbnail.createThumbnailFromUri(this.context, this.mUri, false);
                if (createThumbnailFromUri != null) {
                    this.saverCallback.postUpdateThumbnail(createThumbnailFromUri, true);
                } else {
                    this.saverCallback.postHideThumbnailProgressing();
                }
            }
            this.saverCallback.notifyNewMediaData(this.mUri, this.title, 2);
            Storage.saveToCloudAlbum(this.context, this.mGifPath, -1, true);
        } else if (needThumbnail) {
            this.saverCallback.postHideThumbnailProgressing();
        }
        Storage.getAvailableSpace();
        Log.d(TAG, "save gif: end");
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
