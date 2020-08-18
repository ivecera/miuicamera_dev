package com.xiaomi.protocol;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ICustomCaptureResult implements Parcelable {
    private static final String CAPTURE_RESULT_EXTRA_CLASS = "android.hardware.camera2.impl.CaptureResultExtras";
    public static final Parcelable.Creator<ICustomCaptureResult> CREATOR = new Parcelable.Creator<ICustomCaptureResult>() {
        /* class com.xiaomi.protocol.ICustomCaptureResult.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public ICustomCaptureResult createFromParcel(Parcel parcel) {
            return new ICustomCaptureResult(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ICustomCaptureResult[] newArray(int i) {
            return new ICustomCaptureResult[i];
        }
    };
    private static final String PHYSICAL_CAPTURE_RESULT_CLASS = "android.hardware.camera2.impl.PhysicalCaptureResultInfo";
    private static final String TAG = "ICustomCaptureResult";
    private long mFrameNumber;
    private Parcelable mParcelRequest;
    private CaptureRequest mRequest;
    private Parcelable mResults;
    private int mSequenceId;
    private int mSessionId;
    private long mTimestamp;

    public ICustomCaptureResult() {
    }

    public ICustomCaptureResult(int i, int i2, long j, Parcelable parcelable, CaptureRequest captureRequest) {
        this.mSessionId = i;
        this.mSequenceId = i2;
        this.mFrameNumber = j;
        this.mResults = parcelable;
        this.mRequest = captureRequest;
    }

    public ICustomCaptureResult(int i, int i2, long j, Parcelable parcelable, Parcelable parcelable2) {
        this.mSessionId = i;
        this.mSequenceId = i2;
        this.mFrameNumber = j;
        this.mResults = parcelable;
        this.mParcelRequest = parcelable2;
    }

    protected ICustomCaptureResult(Parcel parcel) {
        this.mSessionId = parcel.readInt();
        this.mSequenceId = parcel.readInt();
        this.mFrameNumber = parcel.readLong();
        this.mTimestamp = parcel.readLong();
        this.mResults = parcel.readParcelable(Parcelable.class.getClassLoader());
        this.mRequest = (CaptureRequest) parcel.readParcelable(Parcelable.class.getClassLoader());
    }

    public static Object getCameraMetaDataCopy(Object obj) {
        try {
            Class<?> cls = Class.forName("android.hardware.camera2.impl.CameraMetadataNative");
            return cls.getDeclaredConstructor(cls).newInstance(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.e(TAG, "getCameraMetaDataCopy: failed", e2);
            return null;
        }
    }

    public static TotalCaptureResult toTotalCaptureResult(ICustomCaptureResult iCustomCaptureResult, int i) {
        Object obj;
        Constructor<?> constructor;
        try {
            int sequenceId = iCustomCaptureResult.getSequenceId();
            long frameNumber = iCustomCaptureResult.getFrameNumber();
            Log.d(TAG, "toTotalCaptureResult: " + i + "|" + sequenceId + "|" + frameNumber);
            Class<?> cls = Class.forName(CAPTURE_RESULT_EXTRA_CLASS);
            if (Build.VERSION.SDK_INT >= 29) {
                constructor = cls.getDeclaredConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Long.TYPE, Integer.TYPE, Integer.TYPE, String.class);
                obj = constructor.newInstance(Integer.valueOf(sequenceId), 0, 0, 0, Long.valueOf(frameNumber), 0, 0, null);
            } else {
                constructor = cls.getDeclaredConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Long.TYPE, Integer.TYPE, Integer.TYPE);
                obj = constructor.newInstance(Integer.valueOf(sequenceId), 0, 0, 0, Long.valueOf(frameNumber), 0, 0);
            }
            Constructor<?>[] declaredConstructors = TotalCaptureResult.class.getDeclaredConstructors();
            int length = declaredConstructors.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Constructor<?> constructor2 = declaredConstructors[i2];
                if (constructor2.getParameters().length > 2) {
                    constructor = constructor2;
                    break;
                }
                i2++;
            }
            Object cameraMetaDataCopy = getCameraMetaDataCopy(iCustomCaptureResult.getResults());
            if (cameraMetaDataCopy == null) {
                Log.e(TAG, "null native metadata", new RuntimeException());
                return null;
            } else if (Build.VERSION.SDK_INT < 28) {
                return (TotalCaptureResult) constructor.newInstance(cameraMetaDataCopy, iCustomCaptureResult.getRequest(), obj, null, Integer.valueOf(i));
            } else {
                return (TotalCaptureResult) constructor.newInstance(cameraMetaDataCopy, iCustomCaptureResult.getRequest(), obj, null, Integer.valueOf(i), Array.newInstance(Class.forName(PHYSICAL_CAPTURE_RESULT_CLASS), 0));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.e(TAG, "null capture result!", new RuntimeException());
            return null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public Parcelable getParcelRequest() {
        return this.mParcelRequest;
    }

    public CaptureRequest getRequest() {
        return this.mRequest;
    }

    public Parcelable getResults() {
        return this.mResults;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public long getTimeStamp() {
        return this.mTimestamp;
    }

    public void setFrameNumber(long j) {
        this.mFrameNumber = j;
    }

    public void setParcelRequest(Parcelable parcelable) {
        this.mParcelRequest = parcelable;
    }

    public void setRequest(CaptureRequest captureRequest) {
        this.mRequest = captureRequest;
    }

    public void setResults(Parcelable parcelable) {
        this.mResults = parcelable;
    }

    public void setSequenceId(int i) {
        this.mSequenceId = i;
    }

    public void setSessionId(int i) {
        this.mSessionId = i;
    }

    public void setTimeStamp(long j) {
        this.mTimestamp = j;
    }

    public String toString() {
        return "ICustomCaptureResult{mSessionId=" + this.mSessionId + ", mSequenceId=" + this.mSequenceId + ", mFrameNumber=" + this.mFrameNumber + ", mResults=" + this.mResults + ", mRequest=" + this.mRequest + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSessionId);
        parcel.writeInt(this.mSequenceId);
        parcel.writeLong(this.mFrameNumber);
        parcel.writeLong(this.mTimestamp);
        parcel.writeParcelable(this.mResults, i);
        parcel.writeParcelable(this.mRequest, i);
    }
}
