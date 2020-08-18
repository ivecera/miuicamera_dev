package com.android.camera.storage;

import android.content.ContentUris;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.db.DbRepository;
import com.android.camera.db.element.SaveTask;
import com.android.camera.log.Log;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.gallery3d.exif.ExifHelper;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;

public final class ParallelSaveRequest extends AbstractSaveRequest {
    private static final String TAG = "ParallelSaveRequest";
    private boolean isHeic = isHeicSavingRequest();
    private String mAlgorithmName;
    private Context mContext;
    private byte[] mData;
    private PictureInfo mInfo;
    private int mJpegRotation;
    private Location mLocation;
    private boolean mNeedThumbnail;
    private String mSavePath;
    private SaverCallback mSaverCallback;
    private int mSize = calculateMemoryUsed();
    private long mTimestamp;

    public ParallelSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback) {
        ((AbstractSaveRequest) this).mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback);
    }

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return this.mSize;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return true;
    }

    @Override // com.android.camera.storage.SaveRequest
    public void onFinish() {
        ParallelTaskData parallelTaskData = ((AbstractSaveRequest) this).mParallelTaskData;
        if (!(parallelTaskData == null || parallelTaskData.getCaptureTime() == 0)) {
            ScenarioTrackUtil.trackShotToViewEnd(true, ((AbstractSaveRequest) this).mParallelTaskData.getCaptureTime());
        }
        PerformanceTracker.trackImageSaver(this.mData, 1);
        this.mData = null;
        ParallelTaskData parallelTaskData2 = ((AbstractSaveRequest) this).mParallelTaskData;
        if (parallelTaskData2 != null) {
            parallelTaskData2.releaseImageData();
            ((AbstractSaveRequest) this).mParallelTaskData = null;
        }
        this.mSaverCallback.onSaveFinish(this.mSize);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.storage.AbstractSaveRequest
    public void reFillSaveRequest(byte[] bArr, long j, long j2, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
        this.mData = bArr;
        this.mTimestamp = j;
        ((AbstractSaveRequest) this).date = j2;
        this.mLocation = location == null ? null : new Location(location);
        this.mJpegRotation = i;
        this.mSavePath = str;
        ((AbstractSaveRequest) this).width = i2;
        ((AbstractSaveRequest) this).height = i3;
        this.mNeedThumbnail = z;
        this.mAlgorithmName = str2;
        this.mInfo = pictureInfo;
    }

    public void run() {
        save();
        onFinish();
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x01c9  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01f6 A[ADDED_TO_REGION] */
    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        boolean z;
        int i;
        boolean z2;
        Thumbnail createThumbnail;
        parserParallelTaskData();
        Log.d(TAG, "save: " + this.mSavePath + " | " + this.mTimestamp);
        synchronized (this.mSavePath.intern()) {
            SaveTask itemByPath = DbRepository.dbItemSaveTask().getItemByPath(this.mSavePath);
            if (itemByPath == null && !Storage.isSaveToHidenFolder(Util.getFileTitleFromPath(this.mSavePath))) {
                SaveTask saveTask = (SaveTask) DbRepository.dbItemSaveTask().generateItem(System.currentTimeMillis());
                saveTask.setPath(this.mSavePath);
                saveTask.setStartTime(-1L);
                DbRepository.dbItemSaveTask().endItemAndInsert(saveTask, 0);
                Log.w(TAG, "insert full size picture:" + this.mSavePath);
            }
            int i2 = ((AbstractSaveRequest) this).width;
            int i3 = ((AbstractSaveRequest) this).height;
            int orientation = ExifHelper.getOrientation(this.mData);
            if ((this.mJpegRotation + orientation) % 180 != 0) {
                i3 = i2;
                i2 = i3;
            }
            if (itemByPath != null) {
                if (itemByPath.isValid()) {
                    String fileTitleFromPath = Util.getFileTitleFromPath(this.mSavePath);
                    Uri resultUri = ParallelUtil.getResultUri(itemByPath.getMediaStoreId().longValue());
                    Log.d(TAG, "algo mark: uri: " + resultUri.toString() + " | " + itemByPath.getPath());
                    Log.d(TAG, "algo mark: " + this.mJpegRotation + " | " + i2 + " | " + i3 + " | " + orientation);
                    Storage.updateImageWithExtraExif(this.mContext, this.mData, this.isHeic, null, ContentUris.withAppendedId(Storage.getMediaUri(this.mContext, false, this.mSavePath), itemByPath.getMediaStoreId().longValue()), fileTitleFromPath, this.mLocation, orientation, i2, i3, null, false, false, this.mAlgorithmName, this.mInfo);
                    ParallelUtil.markTaskFinish(this.mContext, itemByPath, false);
                }
            }
            String fileTitleFromPath2 = this.mSavePath != null ? Util.getFileTitleFromPath(this.mSavePath) : Util.createJpegName(((AbstractSaveRequest) this).date);
            Uri addImage = Storage.addImage(this.mContext, fileTitleFromPath2, ((AbstractSaveRequest) this).date, this.mLocation, orientation, this.mData, this.isHeic, i2, i3, false, false, false, false, itemByPath != null, this.mAlgorithmName, this.mInfo);
            if (addImage != null) {
                if (this.mNeedThumbnail) {
                    i = orientation;
                    Thumbnail createThumbnail2 = Thumbnail.createThumbnail(this.mData, i, Integer.highestOneBit((int) Math.ceil(Math.max((double) i2, (double) i3) / 512.0d)), addImage, false);
                    if (createThumbnail2 != null) {
                        z = true;
                        this.mSaverCallback.postUpdateThumbnail(createThumbnail2, true);
                        z2 = true;
                        this.mSaverCallback.notifyNewMediaData(addImage, fileTitleFromPath2, 2);
                        if (itemByPath == null) {
                            Log.d(TAG, "algo mark: " + addImage.toString());
                            itemByPath.setMediaStoreId(Long.valueOf(ContentUris.parseId(addImage)));
                            ParallelUtil.markTaskFinish(this.mContext, itemByPath, false);
                        } else {
                            if (!z2 && (createThumbnail = Thumbnail.createThumbnail(this.mData, i, Integer.highestOneBit((int) Math.ceil(Math.max((double) i2, (double) i3) / 512.0d)), addImage, false)) != null) {
                                this.mSaverCallback.postUpdateThumbnail(createThumbnail, z);
                            }
                            ParallelUtil.insertImageToParallelService(this.mContext, ContentUris.parseId(addImage), this.mSavePath);
                        }
                    } else {
                        z = true;
                        this.mSaverCallback.postHideThumbnailProgressing();
                    }
                } else {
                    i = orientation;
                    z = true;
                }
                z2 = false;
                this.mSaverCallback.notifyNewMediaData(addImage, fileTitleFromPath2, 2);
                if (itemByPath == null) {
                }
            } else if (Storage.isSaveToHidenFolder(fileTitleFromPath2)) {
                this.mSaverCallback.notifyNewMediaData(null, fileTitleFromPath2, 3);
            }
        }
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context, SaverCallback saverCallback) {
        this.mContext = context;
        this.mSaverCallback = saverCallback;
    }
}
