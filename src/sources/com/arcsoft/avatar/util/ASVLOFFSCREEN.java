package com.arcsoft.avatar.util;

public class ASVLOFFSCREEN implements Cloneable {
    public static final int ASVL_PAF_NV21 = 2050;
    public static final int ASVL_PAF_RGB32_R8G8B8A8 = 773;
    private byte[] RGBA8888;
    private int height;
    private int pixelFormat;
    private int[] rowStride;
    private byte[] vuData;
    private int width;
    private byte[] yData;

    public ASVLOFFSCREEN(int i, int i2, byte[] bArr) {
        this.width = i;
        this.height = i2;
        this.pixelFormat = 773;
        this.RGBA8888 = bArr;
    }

    public ASVLOFFSCREEN(byte[] bArr, int i, int i2, int i3) {
        this.width = i2;
        this.height = i3;
        this.pixelFormat = 2050;
        this.yData = bArr;
        this.vuData = null;
        this.rowStride = new int[]{i, i};
    }

    public ASVLOFFSCREEN(byte[] bArr, int i, int i2, int i3, boolean z) {
        this(bArr, i, i2, i3);
        if (z) {
            this.yData = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.yData, 0, bArr.length);
        }
    }

    public ASVLOFFSCREEN(byte[] bArr, byte[] bArr2, int[] iArr, int i, int i2) {
        this.width = i;
        this.height = i2;
        this.pixelFormat = 2050;
        this.yData = bArr;
        this.vuData = bArr2;
        this.rowStride = iArr;
    }

    @Override // java.lang.Object
    public Object clone() {
        try {
            return (ASVLOFFSCREEN) super.clone();
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public int getHeight() {
        return this.height;
    }

    public int getPixelFormat() {
        return this.pixelFormat;
    }

    public byte[] getRGBA8888() {
        return this.RGBA8888;
    }

    public int[] getRowStride() {
        return this.rowStride;
    }

    public byte[] getVuData() {
        return this.vuData;
    }

    public int getWidth() {
        return this.width;
    }

    public byte[] getYData() {
        return this.yData;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setPixelFormat(int i) {
        this.pixelFormat = i;
    }

    public void setRGBA8888(byte[] bArr) {
        this.RGBA8888 = bArr;
    }

    public void setRowStride(int i) {
        this.rowStride = new int[]{i, i};
    }

    public void setVuData(byte[] bArr) {
        this.vuData = bArr;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setYData(byte[] bArr) {
        this.yData = bArr;
    }
}
