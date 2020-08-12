package com.android.camera.features.mimoji2.widget.helper;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.media.Image;
import android.os.Environment;
import com.android.camera.R;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.log.Log;
import com.ss.android.vesdk.VEEditor;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MimojiHelper2 {
    public static final int COLOR_FormatI420 = 1;
    public static final int COLOR_FormatNV21 = 2;
    public static final String CUSTOM_DIR = (ROOT_DIR + "custom/");
    public static final String DATA_DIR = (MIMOJI_DIR + "data/");
    public static final String EMOTICON_CACHE_DIR = (MIMOJI_DIR + "emoticon" + File.separator);
    public static final String EMOTICON_GIF_CACHE_DIR = (EMOTICON_CACHE_DIR + VEEditor.MVConsts.TYPE_GIF + File.separator);
    public static final String EMOTICON_JPEG_CACHE_DIR = (EMOTICON_CACHE_DIR + "jpeg" + File.separator);
    public static final String EMOTICON_MP4_CACHE_DIR = (EMOTICON_CACHE_DIR + "mp4" + File.separator);
    public static final String GIF_CACHE_FILE = (VIDEO_CACHE_DIR + "gif.mp4");
    public static final String MIMOJI_DIR = (ROOT_DIR + "mimoji/");
    public static final String MIMOJI_PREFIX = "vendor/camera/mimoji/";
    public static final String MODEL_PATH = (MIMOJI_DIR + "model/");
    private static final int ORIENTATION_HYSTERESIS = 5;
    public static final String ROOT_DIR = (Environment.getExternalStorageDirectory().getPath() + "/MIUI/Camera/");
    public static final String VIDEO_CACHE_DIR = (MIMOJI_DIR + "video" + File.separator);
    public static final String VIDEO_DEAL_CACHE_FILE = (VIDEO_CACHE_DIR + "mimoji_deal.mp4");
    public static final String VIDEO_NORMAL_CACHE_FILE;
    private static int mCurrentOrientation = -1;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(VIDEO_CACHE_DIR);
        sb.append("mimoji_normal.mp4");
        VIDEO_NORMAL_CACHE_FILE = sb.toString();
    }

    public static byte[] getDataFromImage(Image image, int i) {
        Rect rect;
        int i2;
        int i3 = i;
        int i4 = 2;
        int i5 = 1;
        if (i3 == 1 || i3 == 2) {
            Rect cropRect = image.getCropRect();
            int format = image.getFormat();
            int width = cropRect.width();
            int height = cropRect.height();
            Image.Plane[] planes = image.getPlanes();
            int i6 = width * height;
            byte[] bArr = new byte[((ImageFormat.getBitsPerPixel(format) * i6) / 8)];
            int i7 = 0;
            byte[] bArr2 = new byte[planes[0].getRowStride()];
            int i8 = 1;
            int i9 = 0;
            int i10 = 0;
            while (i9 < planes.length) {
                if (i9 != 0) {
                    if (i9 != i5) {
                        if (i9 == i4) {
                            if (i3 == i5) {
                                i10 = (int) (((double) i6) * 1.25d);
                                i8 = i5;
                            } else if (i3 == i4) {
                                i8 = i4;
                            }
                        }
                    } else if (i3 == i5) {
                        i8 = i5;
                    } else if (i3 == i4) {
                        i10 = i6 + 1;
                        i8 = i4;
                    }
                    i10 = i6;
                } else {
                    i8 = i5;
                    i10 = i7;
                }
                ByteBuffer buffer = planes[i9].getBuffer();
                int rowStride = planes[i9].getRowStride();
                int pixelStride = planes[i9].getPixelStride();
                int i11 = i9 == 0 ? i7 : i5;
                int i12 = width >> i11;
                int i13 = height >> i11;
                buffer.position(((cropRect.top >> i11) * rowStride) + ((cropRect.left >> i11) * pixelStride));
                int i14 = 0;
                while (i14 < i13) {
                    if (pixelStride == 1 && i8 == 1) {
                        buffer.get(bArr, i10, i12);
                        i10 += i12;
                        rect = cropRect;
                        i2 = i12;
                    } else {
                        rect = cropRect;
                        i2 = ((i12 - 1) * pixelStride) + 1;
                        buffer.get(bArr2, 0, i2);
                        int i15 = i10;
                        for (int i16 = 0; i16 < i12; i16++) {
                            bArr[i15] = bArr2[i16 * pixelStride];
                            i15 += i8;
                        }
                        i10 = i15;
                    }
                    if (i14 < i13 - 1) {
                        buffer.position((buffer.position() + rowStride) - i2);
                    }
                    i14++;
                    cropRect = rect;
                }
                i9++;
                i3 = i;
                width = width;
                i4 = 2;
                i5 = 1;
                i7 = 0;
            }
            return bArr;
        }
        throw new IllegalArgumentException("only support COLOR_FormatI420 and COLOR_FormatNV21");
    }

    public static List<MimojiInfo2> getMimojiInfoList() {
        File file = new File(MODEL_PATH);
        if (!file.exists()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        ArrayList arrayList = new ArrayList();
        for (File file2 : listFiles) {
            String absolutePath = file2.getAbsolutePath();
            File file3 = new File(absolutePath + "/save");
            if (file3.exists()) {
                File[] listFiles2 = file3.listFiles();
                for (File file4 : listFiles2) {
                    if (file4.getPath().substring(file4.getPath().length() - 4).equals(".dat")) {
                        MimojiInfo2 mimojiInfo2 = new MimojiInfo2();
                        mimojiInfo2.mAvatarTemplatePath = absolutePath;
                        mimojiInfo2.mConfigPath = file4.getAbsolutePath();
                    }
                }
            }
        }
        return arrayList;
    }

    public static int getOutlineOrientation(int i, int i2, boolean z) {
        mCurrentOrientation = roundOrientation(i2, mCurrentOrientation);
        int i3 = z ? ((i - mCurrentOrientation) + 360) % 360 : (mCurrentOrientation + i) % 360;
        Log.d("OrientationUtil", "cameraRotation = " + i + " sensorOrientation = " + mCurrentOrientation + "outlineOrientation = " + i3);
        return i3;
    }

    public static Bitmap getThumbnailBitmapFromData(byte[] bArr, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        return createBitmap;
    }

    public static int getTipsResId(int i) {
        switch (i) {
            case 1:
                return R.string.mimoji_check_no_face;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return R.string.mimoji_check_face_occlusion;
            case 7:
            case 8:
            default:
                return -1;
            case 9:
                return R.string.mimoji_check_beyond_20_degrees;
            case 10:
                return R.string.mimoji_check_multi_face;
        }
    }

    public static int getTipsResIdFace(int i) {
        if (i == 3) {
            return R.string.mimoji_check_face_too_close;
        }
        if (i == 4) {
            return R.string.mimoji_check_face_too_far;
        }
        if (i != 7) {
            return -1;
        }
        return R.string.mimoji_check_low_light;
    }

    private static int roundOrientation(int i, int i2) {
        boolean z = true;
        if (i2 != -1) {
            int abs = Math.abs(i - i2);
            if (Math.min(abs, 360 - abs) < 50) {
                z = false;
            }
        }
        return z ? (((i + 45) / 90) * 90) % 360 : i2;
    }
}
