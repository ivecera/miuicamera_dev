package com.arcsoft.camera.wideselfie;

public class AwsInitParameter {

    /* renamed from: a  reason: collision with root package name */
    private int f240a;

    /* renamed from: b  reason: collision with root package name */
    private int f241b;

    /* renamed from: c  reason: collision with root package name */
    private int f242c;
    public float cameraViewAngleForHeight;
    public float cameraViewAngleForWidth;
    public int changeDirectionThumbThreshold;
    public boolean convertNV21;

    /* renamed from: d  reason: collision with root package name */
    private int f243d;

    /* renamed from: e  reason: collision with root package name */
    private int f244e;

    /* renamed from: f  reason: collision with root package name */
    private int f245f;
    public int guideStableBarThumbHeight;
    public int guideStopBarThumbHeight;
    public int maxResultWidth;
    public int mode;
    public int progressBarThumbHeight;
    public float progressBarThumbHeightCropRatio;
    public float resultAngleForHeight;
    public float resultAngleForWidth;
    public int thumbnailHeight;
    public int thumbnailWidth;

    private AwsInitParameter() {
    }

    public static AwsInitParameter getDefaultInitParams(int i, int i2, int i3, int i4) {
        AwsInitParameter awsInitParameter = new AwsInitParameter();
        awsInitParameter.f240a = 0;
        awsInitParameter.mode = 64;
        awsInitParameter.cameraViewAngleForHeight = 42.9829f;
        awsInitParameter.cameraViewAngleForWidth = 55.3014f;
        awsInitParameter.resultAngleForWidth = 180.0f;
        awsInitParameter.resultAngleForHeight = 180.0f;
        awsInitParameter.changeDirectionThumbThreshold = 120;
        awsInitParameter.f241b = i3;
        awsInitParameter.f242c = i;
        awsInitParameter.f243d = i2;
        awsInitParameter.f244e = awsInitParameter.f241b;
        awsInitParameter.thumbnailWidth = awsInitParameter.f242c;
        awsInitParameter.thumbnailHeight = awsInitParameter.f243d;
        awsInitParameter.f245f = i4;
        awsInitParameter.guideStopBarThumbHeight = 0;
        awsInitParameter.maxResultWidth = 0;
        awsInitParameter.progressBarThumbHeight = 0;
        awsInitParameter.guideStableBarThumbHeight = 5;
        awsInitParameter.progressBarThumbHeightCropRatio = 0.0f;
        awsInitParameter.convertNV21 = false;
        return awsInitParameter;
    }

    public int getBufferSize() {
        return this.f240a;
    }

    public int getDeviceOrientation() {
        return this.f245f;
    }

    public int getFullImageHeight() {
        return this.f243d;
    }

    public int getFullImageWidth() {
        return this.f242c;
    }

    public int getSrcFormat() {
        return this.f241b;
    }

    public int getThumbForamt() {
        return this.f244e;
    }

    public int getThumbnailHeight() {
        return this.thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return this.thumbnailWidth;
    }
}
