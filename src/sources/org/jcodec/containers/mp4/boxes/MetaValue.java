package org.jcodec.containers.mp4.boxes;

import com.android.gallery3d.exif.ExifInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.jcodec.platform.Platform;

public class MetaValue {
    public static final int TYPE_BMP = 27;
    public static final int TYPE_FLOAT_32 = 23;
    public static final int TYPE_FLOAT_64 = 24;
    public static final int TYPE_INT_16 = 66;
    public static final int TYPE_INT_32 = 67;
    public static final int TYPE_INT_8 = 65;
    public static final int TYPE_INT_V = 22;
    public static final int TYPE_JPEG = 13;
    public static final int TYPE_PNG = 13;
    public static final int TYPE_STRING_UTF16 = 2;
    public static final int TYPE_STRING_UTF8 = 1;
    public static final int TYPE_UINT_V = 21;
    private byte[] data;
    private int locale;
    private int type;

    private MetaValue(int i, int i2, byte[] bArr) {
        this.type = i;
        this.locale = i2;
        this.data = bArr;
    }

    public static MetaValue createFloat(float f2) {
        return new MetaValue(23, 0, fromFloat(f2));
    }

    public static MetaValue createInt(int i) {
        return new MetaValue(21, 0, fromInt(i));
    }

    public static MetaValue createOther(int i, byte[] bArr) {
        return new MetaValue(i, 0, bArr);
    }

    public static MetaValue createOtherWithLocale(int i, int i2, byte[] bArr) {
        return new MetaValue(i, i2, bArr);
    }

    public static MetaValue createString(String str) {
        return new MetaValue(1, 0, Platform.getBytesForCharset(str, "UTF-8"));
    }

    private static byte[] fromFloat(float f2) {
        byte[] bArr = new byte[4];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        wrap.putFloat(f2);
        return bArr;
    }

    private static byte[] fromInt(int i) {
        byte[] bArr = new byte[4];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        wrap.putInt(i);
        return bArr;
    }

    private double toDouble(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return wrap.getDouble();
    }

    private float toFloat(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return wrap.getFloat();
    }

    private int toInt16(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return wrap.getShort();
    }

    private int toInt24(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return (wrap.get() & 255) | ((wrap.getShort() & ExifInterface.ColorSpace.UNCALIBRATED) << 8);
    }

    private int toInt32(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return wrap.getInt();
    }

    public byte[] getData() {
        return this.data;
    }

    public double getFloat() {
        int i = this.type;
        if (i == 23) {
            return (double) toFloat(this.data);
        }
        if (i == 24) {
            return toDouble(this.data);
        }
        return 0.0d;
    }

    public int getInt() {
        int i = this.type;
        if (i == 21 || i == 22) {
            byte[] bArr = this.data;
            int length = bArr.length;
            if (length == 1) {
                return bArr[0];
            }
            if (length == 2) {
                return toInt16(bArr);
            }
            if (length == 3) {
                return toInt24(bArr);
            }
            if (length == 4) {
                return toInt32(bArr);
            }
        }
        int i2 = this.type;
        if (i2 == 65) {
            return this.data[0];
        }
        if (i2 == 66) {
            return toInt16(this.data);
        }
        if (i2 == 67) {
            return toInt32(this.data);
        }
        return 0;
    }

    public int getLocale() {
        return this.locale;
    }

    public String getString() {
        int i = this.type;
        if (i == 1) {
            return Platform.stringFromCharset(this.data, "UTF-8");
        }
        if (i == 2) {
            return Platform.stringFromCharset(this.data, Platform.UTF_16BE);
        }
        return null;
    }

    public int getType() {
        return this.type;
    }

    public boolean isBlob() {
        return !isFloat() && !isInt() && !isString();
    }

    public boolean isFloat() {
        int i = this.type;
        return i == 23 || i == 24;
    }

    public boolean isInt() {
        int i = this.type;
        return i == 21 || i == 22 || i == 65 || i == 66 || i == 67;
    }

    public boolean isString() {
        int i = this.type;
        return i == 1 || i == 2;
    }

    public String toString() {
        return isInt() ? String.valueOf(getInt()) : isFloat() ? String.valueOf(getFloat()) : isString() ? String.valueOf(getString()) : "BLOB";
    }
}
