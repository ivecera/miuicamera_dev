package com.xiaomi.engine;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;
import java.util.Objects;

public final class GraphDescriptorBean implements Parcelable {
    public static final Parcelable.Creator<GraphDescriptorBean> CREATOR = new Parcelable.Creator<GraphDescriptorBean>() {
        /* class com.xiaomi.engine.GraphDescriptorBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public GraphDescriptorBean createFromParcel(Parcel parcel) {
            return new GraphDescriptorBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public GraphDescriptorBean[] newArray(int i) {
            return new GraphDescriptorBean[i];
        }
    };
    private int mCameraCombinationMode;
    private boolean mIsSnapshot;
    private int mOperationModeID;
    private int mStreamNumber;

    public GraphDescriptorBean() {
        this.mOperationModeID = 0;
        this.mStreamNumber = 0;
        this.mIsSnapshot = true;
        this.mCameraCombinationMode = 0;
    }

    public GraphDescriptorBean(int i, int i2, boolean z, int i3) {
        this.mOperationModeID = i;
        this.mStreamNumber = i2;
        this.mIsSnapshot = z;
        this.mCameraCombinationMode = i3;
    }

    protected GraphDescriptorBean(Parcel parcel) {
        this.mOperationModeID = parcel.readInt();
        this.mStreamNumber = parcel.readInt();
        this.mIsSnapshot = parcel.readByte() != 0;
        this.mCameraCombinationMode = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphDescriptorBean)) {
            return false;
        }
        GraphDescriptorBean graphDescriptorBean = (GraphDescriptorBean) obj;
        return this.mOperationModeID == graphDescriptorBean.mOperationModeID && this.mStreamNumber == graphDescriptorBean.mStreamNumber && this.mIsSnapshot == graphDescriptorBean.mIsSnapshot && this.mCameraCombinationMode == graphDescriptorBean.mCameraCombinationMode;
    }

    public int getCameraCombinationMode() {
        return this.mCameraCombinationMode;
    }

    public int getOperationModeID() {
        return this.mOperationModeID;
    }

    public int getStreamNumber() {
        return this.mStreamNumber;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOperationModeID), Integer.valueOf(this.mStreamNumber), Boolean.valueOf(this.mIsSnapshot), Integer.valueOf(this.mCameraCombinationMode));
    }

    public boolean isSnapshot() {
        return this.mIsSnapshot;
    }

    public void setCameraCombinationMode(int i) {
        this.mCameraCombinationMode = i;
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

    public String toString() {
        return String.format(Locale.ENGLISH, "GraphDescriptorBean{ mOperationModeID=%s , mStreamNumber=%s, mIsSnapshot=%s, mCameraCombinationMode=0x%x }", Integer.valueOf(this.mOperationModeID), Integer.valueOf(this.mStreamNumber), Boolean.valueOf(this.mIsSnapshot), Integer.valueOf(this.mCameraCombinationMode));
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mOperationModeID);
        parcel.writeInt(this.mStreamNumber);
        parcel.writeByte(this.mIsSnapshot ? (byte) 1 : 0);
        parcel.writeInt(this.mCameraCombinationMode);
    }
}
