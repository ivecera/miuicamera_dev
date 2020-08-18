package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

@Keep
public class VEWatermarkParam implements Parcelable {
    public static final Parcelable.Creator<VEWatermarkParam> CREATOR = new Parcelable.Creator<VEWatermarkParam>() {
        /* class com.ss.android.vesdk.VEWatermarkParam.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VEWatermarkParam createFromParcel(Parcel parcel) {
            return new VEWatermarkParam(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VEWatermarkParam[] newArray(int i) {
            return new VEWatermarkParam[i];
        }
    };
    public long duration = -1;
    public String extFile;
    public int height;
    public String[] images;
    public int interval;
    public VEWatermarkMask mask;
    public boolean needExtFile;
    public VEWaterMarkPosition position = VEWaterMarkPosition.TL_BR;
    public String[] secondHalfImages;
    public int width;
    public int xOffset;
    public int yOffset;

    @Keep
    public static class VEWatermarkMask implements Parcelable {
        public static final Parcelable.Creator<VEWatermarkMask> CREATOR = new Parcelable.Creator<VEWatermarkMask>() {
            /* class com.ss.android.vesdk.VEWatermarkParam.VEWatermarkMask.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public VEWatermarkMask createFromParcel(Parcel parcel) {
                return new VEWatermarkMask(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public VEWatermarkMask[] newArray(int i) {
                return new VEWatermarkMask[i];
            }
        };
        public int height;
        public String maskImage;
        public int width;
        public int xOffset;
        public int yOffset;

        public VEWatermarkMask() {
        }

        protected VEWatermarkMask(Parcel parcel) {
            this.maskImage = parcel.readString();
            this.xOffset = parcel.readInt();
            this.yOffset = parcel.readInt();
            this.width = parcel.readInt();
            this.height = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.maskImage);
            parcel.writeInt(this.xOffset);
            parcel.writeInt(this.yOffset);
            parcel.writeInt(this.width);
            parcel.writeInt(this.height);
        }
    }

    public VEWatermarkParam() {
    }

    protected VEWatermarkParam(Parcel parcel) {
        this.needExtFile = parcel.readByte() != 0;
        this.extFile = parcel.readString();
        this.images = parcel.createStringArray();
        this.secondHalfImages = parcel.createStringArray();
        this.interval = parcel.readInt();
        this.xOffset = parcel.readInt();
        this.yOffset = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.duration = parcel.readLong();
        int readInt = parcel.readInt();
        this.position = readInt == -1 ? null : VEWaterMarkPosition.values()[readInt];
        this.mask = (VEWatermarkMask) parcel.readParcelable(VEWatermarkMask.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.needExtFile ? (byte) 1 : 0);
        parcel.writeString(this.extFile);
        parcel.writeStringArray(this.images);
        parcel.writeStringArray(this.secondHalfImages);
        parcel.writeInt(this.interval);
        parcel.writeInt(this.xOffset);
        parcel.writeInt(this.yOffset);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeLong(this.duration);
        VEWaterMarkPosition vEWaterMarkPosition = this.position;
        parcel.writeInt(vEWaterMarkPosition == null ? -1 : vEWaterMarkPosition.ordinal());
        parcel.writeParcelable(this.mask, i);
    }
}
