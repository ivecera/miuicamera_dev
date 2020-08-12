package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import com.ss.android.vesdk.VEEditor;

public class VEEditorModel implements Parcelable {
    public static final Parcelable.Creator<VEEditorModel> CREATOR = new Parcelable.Creator<VEEditorModel>() {
        /* class com.ss.android.vesdk.VEEditorModel.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VEEditorModel createFromParcel(Parcel parcel) {
            return new VEEditorModel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VEEditorModel[] newArray(int i) {
            return new VEEditorModel[i];
        }
    };
    public int audioEffectFilterIndex;
    public String[] audioPaths;
    @ColorInt
    public int backgroundColor;
    public int colorFilterIndex;
    public float colorFilterIntensity;
    public String colorFilterLeftPath;
    public float colorFilterPosition;
    public String colorFilterRightPath;
    public int inPoint;
    public int masterTrackIndex;
    public String modelDir;
    public String outputFile;
    public int outputPoint;
    public String projectXML;
    public boolean reverseDone;
    public boolean separateAV;
    public String[] transitions;
    public boolean useColorFilterResIntensity;
    public VEEditor.VIDEO_RATIO videoOutRes;
    public String[] videoPaths;
    public String watermarkFile;
    public double watermarkHeight;
    public double watermarkOffsetX;
    public double watermarkOffsetY;
    public double watermarkWidth;

    public VEEditorModel() {
    }

    protected VEEditorModel(Parcel parcel) {
        this.projectXML = parcel.readString();
        this.inPoint = parcel.readInt();
        this.outputPoint = parcel.readInt();
        boolean z = false;
        this.reverseDone = parcel.readInt() == 1;
        this.videoOutRes = VEEditor.VIDEO_RATIO.values()[parcel.readInt()];
        this.separateAV = parcel.readInt() == 1;
        this.masterTrackIndex = parcel.readInt();
        this.audioEffectFilterIndex = parcel.readInt();
        this.modelDir = parcel.readString();
        this.colorFilterIndex = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt != -1) {
            this.videoPaths = new String[readInt];
            parcel.readStringArray(this.videoPaths);
        } else {
            this.videoPaths = null;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 != -1) {
            this.audioPaths = new String[readInt2];
            parcel.readStringArray(this.audioPaths);
        } else {
            this.audioPaths = null;
        }
        int readInt3 = parcel.readInt();
        if (readInt3 != -1) {
            this.transitions = new String[readInt3];
            parcel.readStringArray(this.transitions);
        } else {
            this.transitions = null;
        }
        this.backgroundColor = parcel.readInt();
        this.outputFile = parcel.readString();
        this.watermarkFile = parcel.readString();
        this.watermarkWidth = parcel.readDouble();
        this.watermarkHeight = parcel.readDouble();
        this.watermarkOffsetX = parcel.readDouble();
        this.watermarkOffsetY = parcel.readDouble();
        this.colorFilterLeftPath = parcel.readString();
        this.colorFilterRightPath = parcel.readString();
        this.colorFilterPosition = parcel.readFloat();
        this.colorFilterIntensity = parcel.readFloat();
        this.useColorFilterResIntensity = parcel.readInt() == 1 ? true : z;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.projectXML);
        parcel.writeInt(this.inPoint);
        parcel.writeInt(this.outputPoint);
        parcel.writeInt(this.reverseDone ? 1 : 0);
        parcel.writeInt(this.videoOutRes.ordinal());
        parcel.writeInt(this.separateAV ? 1 : 0);
        parcel.writeInt(this.masterTrackIndex);
        parcel.writeInt(this.audioEffectFilterIndex);
        parcel.writeString(this.modelDir);
        parcel.writeInt(this.colorFilterIndex);
        String[] strArr = this.videoPaths;
        if (strArr != null) {
            parcel.writeInt(strArr.length);
            parcel.writeStringArray(this.videoPaths);
        } else {
            parcel.writeInt(-1);
        }
        String[] strArr2 = this.audioPaths;
        if (strArr2 != null) {
            parcel.writeInt(strArr2.length);
            parcel.writeStringArray(this.audioPaths);
        } else {
            parcel.writeInt(-1);
        }
        String[] strArr3 = this.transitions;
        if (strArr3 != null) {
            parcel.writeInt(strArr3.length);
            parcel.writeStringArray(this.transitions);
        } else {
            parcel.writeInt(-1);
        }
        parcel.writeInt(this.backgroundColor);
        parcel.writeString(this.outputFile);
        parcel.writeString(this.watermarkFile);
        parcel.writeDouble(this.watermarkWidth);
        parcel.writeDouble(this.watermarkHeight);
        parcel.writeDouble(this.watermarkOffsetX);
        parcel.writeDouble(this.watermarkOffsetY);
        parcel.writeString(this.colorFilterLeftPath);
        parcel.writeString(this.colorFilterRightPath);
        parcel.writeFloat(this.colorFilterPosition);
        parcel.writeFloat(this.colorFilterIntensity);
        parcel.writeInt(this.useColorFilterResIntensity ? 1 : 0);
    }
}
