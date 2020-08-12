package com.xiaomi.protocol;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;

public class IImageReaderParameterSets implements Parcelable {
    public static final Parcelable.Creator<IImageReaderParameterSets> CREATOR = new Parcelable.Creator<IImageReaderParameterSets>() {
        /* class com.xiaomi.protocol.IImageReaderParameterSets.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public IImageReaderParameterSets createFromParcel(Parcel parcel) {
            return new IImageReaderParameterSets(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public IImageReaderParameterSets[] newArray(int i) {
            return new IImageReaderParameterSets[i];
        }
    };
    public int format;
    public int height;
    public boolean isParallel;
    public int maxImages;
    private boolean shouldHoldImages = true;
    public int targetCamera;
    public int width;

    public IImageReaderParameterSets(int i, int i2, int i3, int i4, int i5) {
        this.width = i;
        this.height = i2;
        this.format = i3;
        this.maxImages = i4;
        this.targetCamera = i5;
    }

    protected IImageReaderParameterSets(Parcel parcel) {
        boolean z = true;
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.format = parcel.readInt();
        this.maxImages = parcel.readInt();
        this.targetCamera = parcel.readInt();
        this.shouldHoldImages = parcel.readByte() != 0;
        this.isParallel = parcel.readByte() == 0 ? false : z;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IImageReaderParameterSets)) {
            return super.equals(obj);
        }
        IImageReaderParameterSets iImageReaderParameterSets = (IImageReaderParameterSets) obj;
        return this.targetCamera == iImageReaderParameterSets.targetCamera && this.width == iImageReaderParameterSets.width && this.height == iImageReaderParameterSets.height && this.format == iImageReaderParameterSets.format && this.maxImages == iImageReaderParameterSets.maxImages && this.shouldHoldImages == iImageReaderParameterSets.shouldHoldImages;
    }

    public boolean isShouldHoldImages() {
        return this.shouldHoldImages;
    }

    public void setShouldHoldImages(boolean z) {
        this.shouldHoldImages = z;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "IImageReaderParameterSets[ %d, %d, %d, %d, %s, %s]", Integer.valueOf(this.width), Integer.valueOf(this.height), Integer.valueOf(this.format), Integer.valueOf(this.maxImages), Integer.valueOf(this.targetCamera), Boolean.valueOf(this.shouldHoldImages));
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.format);
        parcel.writeInt(this.maxImages);
        parcel.writeInt(this.targetCamera);
        parcel.writeByte(this.shouldHoldImages ? (byte) 1 : 0);
        parcel.writeByte(this.isParallel ? (byte) 1 : 0);
    }
}
