package com.android.camera2.vendortag.struct;

import com.android.camera.log.Log;
import java.io.Serializable;

public class HdrEvValue implements Serializable {
    public static final String TAG = "HdrEvValue";
    private static final long serialVersionUID = 1;
    private int[] mHdrCheckerEvValue;
    private int mSequenceNum;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: int[]} */
    /* JADX WARN: Multi-variable type inference failed */
    public HdrEvValue(byte[] bArr) {
        if (bArr != 0 && bArr.length >= 1) {
            int i = 0;
            if (bArr[0] != 0) {
                this.mSequenceNum = bArr[0];
                int i2 = this.mSequenceNum;
                if (i2 <= 6) {
                    this.mHdrCheckerEvValue = new int[i2];
                    if (i2 > 0 && i2 < 6) {
                        while (i < this.mSequenceNum) {
                            int i3 = i + 1;
                            this.mHdrCheckerEvValue[i] = bArr[i3 * 4];
                            String str = TAG;
                            Log.d(str, "HdrEvValue: evValue[" + i + "]=" + this.mHdrCheckerEvValue[i]);
                            i = i3;
                        }
                        return;
                    }
                    return;
                }
                throw new RuntimeException("wrong sequenceNum " + this.mSequenceNum);
            }
        }
        this.mSequenceNum = 3;
        this.mHdrCheckerEvValue = new int[]{-6, 0, 6};
    }

    public int[] getHdrCheckerEvValue() {
        return this.mHdrCheckerEvValue;
    }

    public int getSequenceNum() {
        return this.mSequenceNum;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        int[] iArr = this.mHdrCheckerEvValue;
        if (iArr != null && iArr.length > 0) {
            sb.append("[");
            int i = 0;
            while (true) {
                int[] iArr2 = this.mHdrCheckerEvValue;
                if (i >= iArr2.length) {
                    break;
                }
                sb.append(iArr2[i]);
                if (i != this.mHdrCheckerEvValue.length - 1) {
                    sb.append(",");
                }
                i++;
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
