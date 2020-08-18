package com.android.camera.module;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import com.android.camera.CameraApplicationDelegate;
import com.android.camera.FileCompat;
import com.android.camera.LocationManager;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.groupshot.GroupShot;
import com.android.camera.log.Log;
import com.android.camera.storage.SaverCallback;
import com.android.camera.storage.Storage;
import com.android.gallery3d.exif.ExifHelper;
import java.io.File;
import java.lang.ref.WeakReference;

public class SaveOutputImageTask extends AsyncTask<Void, Integer, Thumbnail> {
    private static final String TAG = "SaveOutputImageTask";
    private GroupShot mGroupShotInternal;
    private int mHeight;
    private Location mLocation;
    private int mOrientation;
    private WeakReference<SaverCallback> mSaverCallbackWeakReference;
    private volatile long mStartTime;
    private long mTimeTaken;
    private String mTitle;
    private int mWidth;

    public SaveOutputImageTask(SaverCallback saverCallback, long j, Location location, int i, int i2, int i3, String str, GroupShot groupShot) {
        this.mSaverCallbackWeakReference = new WeakReference<>(saverCallback);
        this.mTimeTaken = j;
        this.mLocation = location;
        this.mWidth = i;
        this.mHeight = i2;
        this.mOrientation = i3;
        this.mTitle = str;
        this.mGroupShotInternal = groupShot;
    }

    private void finishGroupShot() {
        this.mGroupShotInternal.clearImages();
        this.mGroupShotInternal.finish();
        this.mGroupShotInternal = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
        if (r0 != null) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
        r4.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        throw r1;
     */
    private void saveGroupShotImage(String str) {
        if (!Storage.isUseDocumentMode()) {
            this.mGroupShotInternal.getImageAndSaveJpeg(str);
            ExifHelper.writeExifByFilePath(str, this.mOrientation, LocationManager.instance().getCurrentLocation(), this.mTimeTaken);
            return;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, true);
            this.mGroupShotInternal.getImageAndSaveJpeg(parcelFileDescriptor.getFileDescriptor());
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (Exception e2) {
            Log.e(TAG, "open file failed, filePath " + str, e2);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0153  */
    public Thumbnail doInBackground(Void... voidArr) {
        String str;
        Log.v(TAG, "doInBackground start");
        try {
            Log.v(TAG, String.format("attach_end() = 0x%08x", Integer.valueOf(this.mGroupShotInternal.attach_end())));
            if (isCancelled()) {
                return null;
            }
            Log.v(TAG, String.format("setBaseImage() = 0x%08x", Integer.valueOf(this.mGroupShotInternal.setBaseImage(0))));
            this.mGroupShotInternal.setBestFace();
            Log.v(TAG, "groupshot attach end & setbestface cost " + (System.currentTimeMillis() - this.mStartTime));
            str = Storage.generateFilepath4Image(this.mTitle, false);
            try {
                saveGroupShotImage(str);
                Log.v(TAG, "groupshot finish group cost " + (System.currentTimeMillis() - this.mStartTime) + ", path = " + str);
                if (isCancelled()) {
                    return null;
                }
                if (Util.sIsDumpOrigJpg) {
                    String substring = str.substring(0, str.lastIndexOf(Storage.JPEG_SUFFIX));
                    new File(substring).mkdirs();
                    this.mGroupShotInternal.saveInputImages(substring + File.separator);
                }
                if (isCancelled() || isCancelled()) {
                    return null;
                }
                Context androidContext = CameraApplicationDelegate.getAndroidContext();
                Uri addImageForGroupOrPanorama = Storage.addImageForGroupOrPanorama(androidContext, str, this.mOrientation, this.mTimeTaken, this.mLocation, this.mWidth, this.mHeight);
                Log.v(TAG, "groupshot insert db cost " + (System.currentTimeMillis() - this.mStartTime) + ", uri = " + addImageForGroupOrPanorama);
                SaverCallback saverCallback = this.mSaverCallbackWeakReference.get();
                if (saverCallback == null || addImageForGroupOrPanorama == null) {
                    return null;
                }
                saverCallback.notifyNewMediaData(addImageForGroupOrPanorama, this.mTitle, 2);
                Thumbnail createThumbnailFromUri = Thumbnail.createThumbnailFromUri(androidContext, addImageForGroupOrPanorama, false);
                Log.v(TAG, "groupshot asynctask cost " + (System.currentTimeMillis() - this.mStartTime));
                return createThumbnailFromUri;
            } catch (Exception e2) {
                e = e2;
                Log.e(TAG, "SaveOutputImageTask exception occurs, " + e.getMessage());
                if (str != null) {
                }
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            str = null;
            Log.e(TAG, "SaveOutputImageTask exception occurs, " + e.getMessage());
            if (str != null) {
                new File(str).delete();
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        Log.v(TAG, "SaveOutputImageTask onCancelled");
        finishGroupShot();
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Thumbnail thumbnail) {
        Log.v(TAG, "SaveOutputImageTask onPostExecute");
        SaverCallback saverCallback = this.mSaverCallbackWeakReference.get();
        if (saverCallback != null) {
            if (thumbnail == null) {
                Log.e(TAG, "onPostExecute thumbnail is null");
                saverCallback.postHideThumbnailProgressing();
            } else {
                Log.v(TAG, "onPostExecute thumbnail = " + thumbnail);
                saverCallback.postUpdateThumbnail(thumbnail, false);
            }
            Log.v(TAG, "groupshot image process cost " + (System.currentTimeMillis() - this.mStartTime));
            finishGroupShot();
        }
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        this.mStartTime = System.currentTimeMillis();
    }
}
