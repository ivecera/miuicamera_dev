package com.android.camera.module.loader.camera2;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.camera.log.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FocusTask implements Parcelable {
    public static final Parcelable.Creator<FocusTask> CREATOR = new Parcelable.Creator<FocusTask>() {
        /* class com.android.camera.module.loader.camera2.FocusTask.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public FocusTask createFromParcel(Parcel parcel) {
            return new FocusTask(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public FocusTask[] newArray(int i) {
            return new FocusTask[i];
        }
    };
    private static final String TAG = "FocusTask";
    public static final int TRIGGER_BY_AUTO = 2;
    public static final int TRIGGER_BY_LOCK = 3;
    public static final int TRIGGER_BY_MANUALLY = 1;
    private long mElapsedTime;
    private int mFocusBy;
    private long mFocusStartTime;
    private boolean mIsDepthFocus;
    private int mRequestHash;
    private boolean mSuccess;
    private boolean mTaskProcessed;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusTriggerBy {
    }

    private FocusTask(int i) {
        this.mRequestHash = -1;
        this.mTaskProcessed = false;
        this.mIsDepthFocus = false;
        this.mFocusBy = i;
        this.mFocusStartTime = System.currentTimeMillis();
    }

    protected FocusTask(Parcel parcel) {
        this.mRequestHash = -1;
        boolean z = false;
        this.mTaskProcessed = false;
        this.mIsDepthFocus = false;
        this.mFocusBy = parcel.readInt();
        this.mFocusStartTime = parcel.readLong();
        this.mElapsedTime = parcel.readLong();
        this.mSuccess = parcel.readByte() != 0;
        this.mRequestHash = parcel.readInt();
        this.mTaskProcessed = parcel.readByte() != 0;
        this.mIsDepthFocus = parcel.readByte() != 0 ? true : z;
    }

    public static final FocusTask create(int i) {
        return new FocusTask(i);
    }

    public int describeContents() {
        return 0;
    }

    public long getElapsedTime() {
        if (this.mFocusStartTime != 0) {
            return this.mElapsedTime;
        }
        throw new RuntimeException("unknown focus time");
    }

    public int getFocusTrigger() {
        return this.mFocusBy;
    }

    public boolean isFocusing() {
        return this.mElapsedTime == 0;
    }

    public boolean isIsDepthFocus() {
        return this.mIsDepthFocus;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public boolean isTaskProcessed() {
        return this.mTaskProcessed;
    }

    public void processResult(CaptureResult captureResult) {
        int hashCode = captureResult.getRequest().hashCode();
        if (hashCode == this.mRequestHash) {
            if (!this.mTaskProcessed) {
                String str = TAG;
                Log.d(str, "processResult the task=" + hashCode() + ", request=" + hashCode);
            }
            this.mTaskProcessed = true;
        }
    }

    public void setIsDepthFocus(boolean z) {
        this.mIsDepthFocus = z;
    }

    public void setRequest(CaptureRequest captureRequest) {
        this.mRequestHash = captureRequest.hashCode();
        String str = TAG;
        Log.d(str, "setRequest the task=" + hashCode() + ", request=" + this.mRequestHash);
    }

    public void setResult(boolean z) {
        if (!this.mTaskProcessed) {
            Log.d(TAG, "warning. set the focus result before the request is processed.");
        }
        this.mSuccess = z;
        this.mElapsedTime = System.currentTimeMillis() - this.mFocusStartTime;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mFocusBy);
        parcel.writeLong(this.mFocusStartTime);
        parcel.writeLong(this.mElapsedTime);
        parcel.writeByte(this.mSuccess ? (byte) 1 : 0);
        parcel.writeInt(this.mRequestHash);
        parcel.writeByte(this.mTaskProcessed ? (byte) 1 : 0);
        parcel.writeByte(this.mIsDepthFocus ? (byte) 1 : 0);
    }
}
