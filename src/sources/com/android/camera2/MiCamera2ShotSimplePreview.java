package com.android.camera2;

import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.location.Location;
import android.media.Image;
import com.android.camera.EncodingQuality;
import com.android.camera.LocationManager;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.storage.ImageSaver;
import com.android.camera2.Camera2Proxy;
import com.android.gallery3d.exif.ExifInterface;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MiCamera2ShotSimplePreview extends MiCamera2Shot<byte[]> implements SurfaceTextureScreenNail.PreviewSaveListener {
    private static final String TAG = "MiCamera2ShotSimplePreview";
    private ImageSaver mSaver;

    public MiCamera2ShotSimplePreview(MiCamera2 miCamera2) {
        super(miCamera2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        return null;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    @Override // com.android.camera.SurfaceTextureScreenNail.PreviewSaveListener
    public void save(byte[] bArr, int i, int i2, int i3) {
        byte[] bArr2;
        Camera2Proxy.PictureCallback pictureCallback;
        ImageSaver imageSaver;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        byte[] bitmapData = Util.getBitmapData(createBitmap, EncodingQuality.NORMAL.toInteger(false));
        long currentTimeMillis = System.currentTimeMillis();
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExifInterface exifInterface = new ExifInterface();
        try {
            exifInterface.readExif(bitmapData);
            try {
                exifInterface.addParallelProcessComment("None", i3, i, i2);
                exifInterface.removeParallelProcessComment();
                exifInterface.writeExif(bitmapData, byteArrayOutputStream);
                bArr2 = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            Log.e(TAG, "updateExif error", e);
            bArr2 = bitmapData;
            pictureCallback = getPictureCallback();
            if (pictureCallback != null) {
            }
            imageSaver = this.mSaver;
            if (imageSaver == null) {
            }
        }
        pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onPictureTakenFinished(true);
        }
        imageSaver = this.mSaver;
        if (imageSaver == null) {
            imageSaver.addImage(bArr2, true, Util.createJpegName(currentTimeMillis), null, System.currentTimeMillis(), null, currentLocation, i, i2, null, i3, false, false, true, false, false, null, null, -1, null);
        }
    }

    public void setImageSaver(ImageSaver imageSaver) {
        this.mSaver = imageSaver;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onCaptureShutter(true);
        } else {
            Log.w(TAG, "startSessionCapture: null picture callback");
        }
    }
}
