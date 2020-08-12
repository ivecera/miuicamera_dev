package com.android.camera;

public enum EncodingQuality {
    LOW(67, 81),
    NORMAL(87, 89),
    HIGH(CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.high_jpeg_quality), 95);
    
    public static final int MAX_QUALITY_FOR_108MP = 90;
    public static final int MAX_QUALITY_FOR_AFTER_EFFECT = 97;
    private final int heicQuality;
    private final int jpegQuality;

    private EncodingQuality(int i, int i2) {
        this.jpegQuality = i;
        this.heicQuality = i2;
    }

    public static EncodingQuality enumOf(String str) {
        EncodingQuality[] values = values();
        EncodingQuality encodingQuality = null;
        for (EncodingQuality encodingQuality2 : values) {
            if (encodingQuality2.name().equalsIgnoreCase(str) == 1) {
                encodingQuality = encodingQuality2;
            }
        }
        return encodingQuality;
    }

    public int toInteger(boolean z) {
        return z ? this.heicQuality : this.jpegQuality;
    }
}
