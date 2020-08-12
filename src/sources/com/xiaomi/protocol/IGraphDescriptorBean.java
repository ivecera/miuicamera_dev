package com.xiaomi.protocol;

import android.os.Parcel;
import android.os.Parcelable;

public final class IGraphDescriptorBean implements Parcelable {
    public static final Parcelable.Creator<IGraphDescriptorBean> CREATOR = new Parcelable.Creator<IGraphDescriptorBean>() {
        /* class com.xiaomi.protocol.IGraphDescriptorBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public IGraphDescriptorBean createFromParcel(Parcel parcel) {
            return new IGraphDescriptorBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public IGraphDescriptorBean[] newArray(int i) {
            return new IGraphDescriptorBean[i];
        }
    };
    private boolean mIsFrontCamera;
    private boolean mIsSnapshot;
    private int mOperationModeID;
    private int mStreamNumber;

    public IGraphDescriptorBean() {
        this.mOperationModeID = 0;
        this.mStreamNumber = 0;
        this.mIsSnapshot = true;
        this.mIsFrontCamera = false;
    }

    public IGraphDescriptorBean(int i, int i2, boolean z, boolean z2) {
        this.mOperationModeID = i;
        this.mStreamNumber = i2;
        this.mIsSnapshot = z;
        this.mIsFrontCamera = z2;
    }

    protected IGraphDescriptorBean(Parcel parcel) {
        this.mOperationModeID = parcel.readInt();
        this.mStreamNumber = parcel.readInt();
        boolean z = true;
        this.mIsSnapshot = parcel.readByte() != 0;
        this.mIsFrontCamera = parcel.readByte() == 0 ? false : z;
    }

    public int describeContents() {
        return 0;
    }

    public int getOperationModeID() {
        return this.mOperationModeID;
    }

    public int getStreamNumber() {
        return this.mStreamNumber;
    }

    public boolean isFrontCamera() {
        return this.mIsFrontCamera;
    }

    public boolean isSnapshot() {
        return this.mIsSnapshot;
    }

    public void setFrontCamera(boolean z) {
        this.mIsFrontCamera = z;
    }

    public void setOperationModeID(int i) {
        this.mOperationModeID = i;
    }

    public void setSnapshot(boolean z) {
        this.mIsSnapshot = z;
    }

    public void setStreamNumber(int i) {
        this.mStreamNumber = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mOperationModeID);
        parcel.writeInt(this.mStreamNumber);
        parcel.writeByte(this.mIsSnapshot ? (byte) 1 : 0);
        parcel.writeByte(this.mIsFrontCamera ? (byte) 1 : 0);
    }
}
