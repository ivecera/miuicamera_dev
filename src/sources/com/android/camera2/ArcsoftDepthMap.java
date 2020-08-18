package com.android.camera2;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Xml;
import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.android.camera.Util;
import com.android.camera.XmpHelper;
import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.core.PictureInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import org.xmlpull.v1.XmlSerializer;

public class ArcsoftDepthMap {
    private static final int BLUR_LEVEL_START_ADDR = 16;
    private static final int DAPTH_MAP_START_ADDR = 152;
    private static final int DATA_LENGTH_4 = 4;
    private static final int DATA_LENGTH_START_ADDR = 148;
    private static final int HEADER_LENGTH_START_ADDR = 4;
    private static final int HEADER_START_ADDR = 0;
    private static final int POINT_X_START_ADDR = 8;
    private static final int POINT_Y_START_ADDR = 12;
    private static final String TAG = "ArcsoftDepthMap";
    @Deprecated
    public static final int TAG_DEPTH_MAP_BLUR_LEVEL = ExifInterface.defineTag(2, -30575);
    @Deprecated
    public static final int TAG_DEPTH_MAP_FOCUS_POINT = ExifInterface.defineTag(2, -30576);
    private byte[] mDepthMapHeader;
    private byte[] mDepthMapOriginalData;

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public ArcsoftDepthMap(byte[] bArr) {
        if (bArr != null) {
            int headerTag = getHeaderTag(bArr);
            if (headerTag == 128) {
                this.mDepthMapOriginalData = bArr;
                this.mDepthMapHeader = getDepthMapHeader();
                return;
            }
            throw new IllegalArgumentException("Illegal depth format! 0x80 != " + headerTag);
        }
        throw new IllegalArgumentException("Null depth data!");
    }

    private static byte[] getBytes(byte[] bArr, int i, int i2) {
        if (i2 <= 0 || i < 0 || i2 > bArr.length - i) {
            throw new IllegalArgumentException("WRONG ARGUMENT: from =" + i + ", length = " + i2);
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    private static int getHeaderTag(byte[] bArr) {
        return getInteger(getBytes(bArr, 0, 4));
    }

    private static int getInteger(byte[] bArr) {
        if (bArr.length == 4) {
            int i = 0;
            for (int i2 = 0; i2 < 4; i2++) {
                i += (bArr[i2] & 255) << (i2 * 8);
            }
            return i;
        }
        throw new IllegalArgumentException("bytes can not covert to a integer value!");
    }

    public static boolean isDepthMapData(byte[] bArr) {
        boolean z = bArr != null && bArr.length > 4 && getHeaderTag(bArr) == 128;
        if (!z) {
            Log.e(TAG, "Illegal depthmap format");
        }
        return z;
    }

    public int getBlurLevel() {
        return getInteger(getBytes(this.mDepthMapHeader, 16, 4));
    }

    public byte[] getDepthMapData() {
        return getBytes(this.mDepthMapOriginalData, 152, getDepthMapLength());
    }

    public byte[] getDepthMapHeader() {
        return getBytes(this.mDepthMapOriginalData, 0, getInteger(getBytes(this.mDepthMapOriginalData, 4, 4)));
    }

    public int getDepthMapLength() {
        return getInteger(getBytes(this.mDepthMapHeader, 148, 4));
    }

    public Point getFocusPoint() {
        return new Point(getInteger(getBytes(this.mDepthMapHeader, 8, 4)), getInteger(getBytes(this.mDepthMapHeader, 12, 4)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ff, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0100, code lost:
        $closeResource(r0, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0104, code lost:
        throw r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0469  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0471 A[SYNTHETIC, Splitter:B:123:0x0471] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x0505  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0399  */
    public byte[] writePortraitExif(int i, byte[] bArr, byte[] bArr2, @NonNull int[] iArr, byte[] bArr3, @NonNull int[] iArr2, byte[] bArr4, int[] iArr3, int i2, boolean z, boolean z2, boolean z3, PictureInfo pictureInfo, int i3, int i4, long j) {
        byte[] bArr5;
        File file;
        long j2;
        File file2;
        long j3;
        int i5;
        int i6;
        int i7;
        int i8;
        long j4;
        int i9;
        int i10;
        File file3;
        byte[] bArr6;
        byte[] bArr7;
        String str;
        byte[] bArr8;
        byte[] bArr9;
        byte[] bArr10;
        byte[] bArr11;
        Throwable th;
        byte[] bArr12;
        StringWriter stringWriter;
        long j5;
        int i11;
        String str2;
        int i12;
        int i13;
        Point focusPoint = getFocusPoint();
        int blurLevel = getBlurLevel();
        boolean isFrontCamera = pictureInfo.isFrontCamera();
        int i14 = 10;
        int i15 = 5;
        if (i <= 0) {
            i15 = -1;
            i14 = -1;
        } else if (isFrontCamera) {
            i14 = (!pictureInfo.isAiEnabled() || pictureInfo.getAiType() != 10) ? 40 : 70;
        } else if (pictureInfo.isAiEnabled() && pictureInfo.getAiType() == 10) {
            i14 = 30;
        }
        Log.d(TAG, "writePortraitExif: focusPoint: " + focusPoint);
        Log.d(TAG, "writePortraitExif: blurLevel: " + blurLevel);
        Log.d(TAG, "writePortraitExif: shineThreshold: " + i15);
        Log.d(TAG, "writePortraitExif: shineLevel: " + i14);
        Log.d(TAG, "writePortraitExif: lightingPattern: " + i2);
        Log.d(TAG, "writePortraitExif: isCinematicAspectRatio: " + z3);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExifInterface exifInterface = new ExifInterface();
            exifInterface.readExif(bArr);
            exifInterface.addXiaomiDepthmapVersion(i);
            exifInterface.addDepthMapBlurLevel(blurLevel);
            exifInterface.addPortraitLighting(i2);
            if (z2) {
                exifInterface.addFrontMirror(z ? 1 : 0);
            }
            exifInterface.writeExif(bArr, byteArrayOutputStream);
            bArr5 = byteArrayOutputStream.toByteArray();
            try {
                $closeResource(null, byteArrayOutputStream);
            } catch (IOException unused) {
            }
        } catch (IOException unused2) {
            bArr5 = null;
            Log.e(TAG, "writePortraitExif(): Failed to write depthmap associated exif metadata");
            if (bArr5 != null) {
            }
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return bArr;
        }
        if (bArr5 != null || bArr5.length <= bArr.length) {
            Log.e(TAG, "writePortraitExif(): #1: return original jpeg");
            return bArr;
        }
        String str3 = "sdcard/DCIM/Camera/evMinusMainImage" + j + ".yuv";
        File file4 = new File("sdcard/DCIM/Camera/evZeroMainImage" + j + ".yuv");
        File file5 = new File("sdcard/DCIM/Camera/evZeroSubImage" + j + ".yuv");
        if (!file4.exists() || !file5.exists()) {
            file2 = file5;
            file = file4;
            j3 = 0;
            j2 = 0;
            i8 = 0;
            i7 = 0;
            i6 = 0;
            i5 = 0;
        } else {
            i6 = Util.getHeader2Int(file4, 0);
            i5 = Util.getHeader2Int(file4, 4);
            j3 = file4.length() - 8;
            file = file4;
            i8 = Util.getHeader2Int(file5, 0);
            i7 = Util.getHeader2Int(file5, 4);
            j2 = file5.length() - 8;
            file2 = file5;
            Log.d(TAG, "main width = " + i6 + ", main height = " + i5 + ", sub width =" + i8 + ", sub height = " + i7);
        }
        File file6 = new File(str3);
        if (file6.exists()) {
            i10 = Util.getHeader2Int(file6, 0);
            i9 = Util.getHeader2Int(file6, 4);
            j4 = file6.length() - 8;
        } else {
            j4 = 0;
            i10 = 0;
            i9 = 0;
        }
        try {
            XmlSerializer newSerializer = Xml.newSerializer();
            file3 = file6;
            try {
                stringWriter = new StringWriter();
                newSerializer.setOutput(stringWriter);
                newSerializer.startDocument("UTF-8", true);
                newSerializer.startTag(null, "depthmap");
            } catch (IOException unused3) {
                bArr12 = bArr3;
                bArr8 = bArr4;
                bArr7 = bArr2;
                Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                str = null;
                if (str != null) {
                }
            }
            try {
                newSerializer.attribute(null, "version", String.valueOf(i));
                newSerializer.attribute(null, "focuspoint", focusPoint.x + "," + focusPoint.y);
                newSerializer.attribute(null, "blurlevel", String.valueOf(blurLevel));
                newSerializer.attribute(null, "shinethreshold", String.valueOf(i15));
                newSerializer.attribute(null, "shinelevel", String.valueOf(i14));
                newSerializer.attribute(null, "rawlength", String.valueOf(i3));
                newSerializer.attribute(null, "depthlength", String.valueOf(i4));
                newSerializer.attribute(null, "mimovie", String.valueOf(z3));
                newSerializer.endTag(null, "depthmap");
                long j6 = j3 + j2 + j4;
                if (j6 != 0) {
                    i11 = i9;
                    newSerializer.startTag(null, "mainyuv");
                    long j7 = (long) (i3 + i4);
                    j5 = j6;
                    newSerializer.attribute(null, "offset", String.valueOf(j7 + j6));
                    str2 = "length";
                    newSerializer.attribute(null, str2, String.valueOf(j3));
                    newSerializer.attribute(null, "width", String.valueOf(i6));
                    newSerializer.attribute(null, "height", String.valueOf(i5));
                    newSerializer.endTag(null, "mainyuv");
                    newSerializer.startTag(null, "subyuv");
                    newSerializer.attribute(null, "offset", String.valueOf(j7 + j2 + j4));
                    newSerializer.attribute(null, str2, String.valueOf(j2));
                    newSerializer.attribute(null, "width", String.valueOf(i8));
                    newSerializer.attribute(null, "height", String.valueOf(i7));
                    newSerializer.endTag(null, "subyuv");
                } else {
                    j5 = j6;
                    i11 = i9;
                    str2 = "length";
                }
                if (j4 != 0) {
                    newSerializer.startTag(null, "evminusyuv");
                    newSerializer.attribute(null, "offset", String.valueOf(((long) (i3 + i4)) + j4));
                    newSerializer.attribute(null, str2, String.valueOf(j4));
                    newSerializer.attribute(null, "width", String.valueOf(i10));
                    newSerializer.attribute(null, "height", String.valueOf(i11));
                    newSerializer.endTag(null, "evminusyuv");
                }
                bArr8 = bArr4;
                if (bArr8 != null) {
                    try {
                        if (bArr8.length > 0) {
                            if (iArr3 != null) {
                                try {
                                    if (iArr3.length >= 4) {
                                        newSerializer.startTag(null, "subimage");
                                        int length = bArr8.length;
                                        bArr7 = bArr2;
                                        if (bArr7 != null) {
                                            try {
                                                i12 = bArr7.length;
                                            } catch (IOException unused4) {
                                            }
                                        } else {
                                            i12 = 0;
                                        }
                                        int i16 = length + i12;
                                        bArr12 = bArr3;
                                        if (bArr12 != null) {
                                            try {
                                                i13 = bArr12.length;
                                            } catch (IOException unused5) {
                                            }
                                        } else {
                                            i13 = 0;
                                        }
                                        newSerializer.attribute(null, "offset", String.valueOf(((long) (i16 + i13)) + j5 + ((long) i3) + ((long) i4)));
                                        newSerializer.attribute(null, str2, String.valueOf(bArr8.length));
                                        newSerializer.attribute(null, "paddingx", String.valueOf(iArr3[0]));
                                        newSerializer.attribute(null, "paddingy", String.valueOf(iArr3[1]));
                                        newSerializer.attribute(null, "width", String.valueOf(iArr3[2]));
                                        newSerializer.attribute(null, "height", String.valueOf(iArr3[3]));
                                        newSerializer.endTag(null, "subimage");
                                        if (bArr7 != null || bArr7.length <= 0) {
                                            bArr6 = bArr3;
                                        } else {
                                            newSerializer.startTag(null, "lenswatermark");
                                            bArr6 = bArr3;
                                            newSerializer.attribute(null, "offset", String.valueOf(((long) (bArr7.length + (bArr6 != null ? bArr6.length : 0))) + j5 + ((long) i3) + ((long) i4)));
                                            newSerializer.attribute(null, str2, String.valueOf(bArr7.length));
                                            newSerializer.attribute(null, "width", String.valueOf(iArr[0]));
                                            newSerializer.attribute(null, "height", String.valueOf(iArr[1]));
                                            newSerializer.attribute(null, "paddingx", String.valueOf(iArr[2]));
                                            newSerializer.attribute(null, "paddingy", String.valueOf(iArr[3]));
                                            newSerializer.endTag(null, "lenswatermark");
                                        }
                                        if (bArr6 != null && bArr6.length > 0) {
                                            newSerializer.startTag(null, "timewatermark");
                                            newSerializer.attribute(null, "offset", String.valueOf(((long) bArr6.length) + j5 + ((long) i3) + ((long) i4)));
                                            newSerializer.attribute(null, str2, String.valueOf(bArr6.length));
                                            newSerializer.attribute(null, "width", String.valueOf(iArr2[0]));
                                            newSerializer.attribute(null, "height", String.valueOf(iArr2[1]));
                                            newSerializer.attribute(null, "paddingx", String.valueOf(iArr2[2]));
                                            newSerializer.attribute(null, "paddingy", String.valueOf(iArr2[3]));
                                            newSerializer.endTag(null, "timewatermark");
                                        }
                                        newSerializer.endDocument();
                                        str = stringWriter.toString();
                                        if (str != null) {
                                            Log.e(TAG, "writePortraitExif(): #2: return original jpeg");
                                            return bArr;
                                        }
                                        try {
                                            bArr9 = bArr5;
                                            try {
                                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr9);
                                                try {
                                                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                                    try {
                                                        XMPMeta createXMPMeta = XmpHelper.createXMPMeta();
                                                        createXMPMeta.setProperty(XmpHelper.XIAOMI_XMP_METADATA_NAMESPACE, XmpHelper.XIAOMI_XMP_METADATA_PROPERTY_NAME, str);
                                                        XmpHelper.writeXMPMeta(byteArrayInputStream, byteArrayOutputStream2, createXMPMeta);
                                                        if (bArr8 != null) {
                                                            try {
                                                                if (bArr8.length > 0 && iArr3 != null && iArr3.length >= 4) {
                                                                    byteArrayOutputStream2.write(bArr8);
                                                                }
                                                            } catch (Throwable th2) {
                                                                th = th2;
                                                                bArr11 = null;
                                                                try {
                                                                    throw th;
                                                                } catch (Throwable th3) {
                                                                    th = th3;
                                                                    bArr10 = bArr11;
                                                                    try {
                                                                        throw th;
                                                                    } catch (XMPException | IOException unused6) {
                                                                        Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                                                                        if (bArr10 == null) {
                                                                        }
                                                                        Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                                                                        return bArr;
                                                                    } catch (Throwable th4) {
                                                                        $closeResource(th, byteArrayInputStream);
                                                                        throw th4;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (bArr7 != null) {
                                                            byteArrayOutputStream2.write(bArr7);
                                                        }
                                                        if (bArr6 != null) {
                                                            byteArrayOutputStream2.write(bArr6);
                                                        }
                                                        if (j3 != 0) {
                                                            Util.writeFile2Stream(file, byteArrayOutputStream2, 8);
                                                            file.delete();
                                                        }
                                                        if (j2 != 0) {
                                                            Util.writeFile2Stream(file2, byteArrayOutputStream2, 8);
                                                            file2.delete();
                                                        }
                                                        if (j4 != 0) {
                                                            Util.writeFile2Stream(file3, byteArrayOutputStream2, 8);
                                                            file3.delete();
                                                        }
                                                        byteArrayOutputStream2.flush();
                                                        bArr10 = byteArrayOutputStream2.toByteArray();
                                                    } catch (Throwable th5) {
                                                        bArr11 = null;
                                                        th = th5;
                                                        throw th;
                                                    }
                                                    try {
                                                        $closeResource(null, byteArrayOutputStream2);
                                                        $closeResource(null, byteArrayInputStream);
                                                    } catch (Throwable th6) {
                                                        th = th6;
                                                        throw th;
                                                    }
                                                } catch (Throwable th7) {
                                                    th = th7;
                                                    bArr11 = null;
                                                    bArr10 = bArr11;
                                                    throw th;
                                                }
                                            } catch (XMPException | IOException unused7) {
                                                bArr10 = null;
                                                Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                                                if (bArr10 == null) {
                                                }
                                                Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                                                return bArr;
                                            }
                                        } catch (XMPException | IOException unused8) {
                                            bArr9 = bArr5;
                                            bArr10 = null;
                                            Log.d(TAG, "writePortraitExif(): Failed to insert depthmap associated xmp metadata");
                                            if (bArr10 == null) {
                                            }
                                            Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                                            return bArr;
                                        }
                                        if (bArr10 == null && bArr10.length > bArr9.length) {
                                            return bArr10;
                                        }
                                        Log.e(TAG, "writePortraitExif(): #3: return original jpeg");
                                        return bArr;
                                    }
                                } catch (IOException unused9) {
                                    bArr7 = bArr2;
                                    bArr12 = bArr3;
                                    Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                                    str = null;
                                    if (str != null) {
                                    }
                                }
                            }
                            bArr7 = bArr2;
                            if (bArr7 != null) {
                            }
                            bArr6 = bArr3;
                            newSerializer.startTag(null, "timewatermark");
                            newSerializer.attribute(null, "offset", String.valueOf(((long) bArr6.length) + j5 + ((long) i3) + ((long) i4)));
                            newSerializer.attribute(null, str2, String.valueOf(bArr6.length));
                            newSerializer.attribute(null, "width", String.valueOf(iArr2[0]));
                            newSerializer.attribute(null, "height", String.valueOf(iArr2[1]));
                            newSerializer.attribute(null, "paddingx", String.valueOf(iArr2[2]));
                            newSerializer.attribute(null, "paddingy", String.valueOf(iArr2[3]));
                            newSerializer.endTag(null, "timewatermark");
                            newSerializer.endDocument();
                            str = stringWriter.toString();
                            if (str != null) {
                            }
                        }
                    } catch (IOException unused10) {
                        bArr7 = bArr2;
                        bArr12 = bArr3;
                        Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                        str = null;
                        if (str != null) {
                        }
                    }
                }
                bArr7 = bArr2;
                if (bArr7 != null) {
                }
                bArr6 = bArr3;
                newSerializer.startTag(null, "timewatermark");
                newSerializer.attribute(null, "offset", String.valueOf(((long) bArr6.length) + j5 + ((long) i3) + ((long) i4)));
                newSerializer.attribute(null, str2, String.valueOf(bArr6.length));
                newSerializer.attribute(null, "width", String.valueOf(iArr2[0]));
                newSerializer.attribute(null, "height", String.valueOf(iArr2[1]));
                newSerializer.attribute(null, "paddingx", String.valueOf(iArr2[2]));
                newSerializer.attribute(null, "paddingy", String.valueOf(iArr2[3]));
                newSerializer.endTag(null, "timewatermark");
                newSerializer.endDocument();
                str = stringWriter.toString();
            } catch (IOException unused11) {
                bArr7 = bArr2;
                bArr12 = bArr3;
                bArr8 = bArr4;
                Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
                str = null;
                if (str != null) {
                }
            }
        } catch (IOException unused12) {
            bArr12 = bArr3;
            file3 = file6;
            bArr8 = bArr4;
            bArr7 = bArr2;
            Log.e(TAG, "writePortraitExif(): Failed to generate depthmap associated xmp metadata");
            str = null;
            if (str != null) {
            }
        }
        if (str != null) {
        }
    }
}
