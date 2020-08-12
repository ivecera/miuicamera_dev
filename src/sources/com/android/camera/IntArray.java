package com.android.camera;

public class IntArray {
    private static final int INIT_CAPACITY = 8;
    private int[] mData = new int[8];
    private int mSize = 0;

    public void add(int i) {
        int[] iArr = this.mData;
        int length = iArr.length;
        int i2 = this.mSize;
        if (length == i2) {
            int[] iArr2 = new int[(i2 + i2)];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.mData = iArr2;
        }
        int[] iArr3 = this.mData;
        int i3 = this.mSize;
        this.mSize = i3 + 1;
        iArr3[i3] = i;
    }

    public int size() {
        return this.mSize;
    }

    public int[] toArray(int[] iArr) {
        if (iArr == null || iArr.length < this.mSize) {
            iArr = new int[this.mSize];
        }
        System.arraycopy(this.mData, 0, iArr, 0, this.mSize);
        return iArr;
    }
}
