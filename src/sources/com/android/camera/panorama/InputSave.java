package com.android.camera.panorama;

import android.media.Image;
import android.os.Environment;
import com.android.camera.log.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputSave {
    public static final String TAG = "InputSave";
    private String FOLDER_PATH = (Environment.getExternalStorageDirectory() + "/Panorama/");
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private String mFileNamePrefix = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
    private int mIndex;

    private class InputSaveRunnable implements Runnable {
        final String imageFormat;
        byte[] mImageBytes;

        public InputSaveRunnable(CaptureImage captureImage, String str) {
            this.imageFormat = str;
            Image image = captureImage.mImage;
            if (image == null) {
                Log.w(InputSave.TAG, "save failed, image is null");
                return;
            }
            Log.d(InputSave.TAG, image.getWidth() + "X" + image.getHeight() + ", imageFormat = " + this.imageFormat);
            if (PanoramaGP3ImageFormat.YUV420_PLANAR.equals(this.imageFormat)) {
                this.mImageBytes = new ConvertFromYuv420Planar().image2bytes(image);
            } else if (PanoramaGP3ImageFormat.YUV420_SEMIPLANAR.equals(this.imageFormat)) {
                this.mImageBytes = new ConvertFromYuv420SemiPlanar().image2bytes(image);
            } else if (PanoramaGP3ImageFormat.YVU420_SEMIPLANAR.equals(this.imageFormat)) {
                this.mImageBytes = new ConvertFromYvu420SemiPlanar().image2bytes(image);
            } else {
                this.mImageBytes = null;
                Log.e(InputSave.TAG, "Image format error.");
            }
        }

        public void run() {
            byte[] bArr = this.mImageBytes;
            if (bArr != null) {
                InputSave.this.saveImage(bArr, this.imageFormat);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0065, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x006b, code lost:
        r4.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006e, code lost:
        throw r5;
     */
    public void saveImage(byte[] bArr, String str) {
        this.mIndex++;
        String generalFileName = generalFileName(this.FOLDER_PATH + this.mFileNamePrefix, String.format(Locale.US, "%06d.yuv", Integer.valueOf(this.mIndex)));
        if (generalFileName == null) {
            Log.e("InputSaveState", "saveImage() error.");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(generalFileName));
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            Log.d(TAG, "write file success,  " + generalFileName);
            fileOutputStream.close();
        } catch (Exception e2) {
            Log.e("InputSaveState", "saveImage() error.", e2);
        }
    }

    public String generalFileName(String str, String str2) {
        File file = new File(str);
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }
        File file2 = new File(str, str2);
        int i = 0;
        while (file2.exists()) {
            i++;
            String[] split = str2.split("\\.");
            String str3 = split[0] + "-" + Integer.toString(i) + "." + split[1];
            File file3 = new File(str, str3);
            Log.d(TAG, "NewFilename:" + str3);
            if (i >= 1000) {
                Log.e(TAG, "NewFilename 1000 count over!!");
                return null;
            }
            file2 = file3;
        }
        return file2.getAbsolutePath();
    }

    public void onSaveImage(CaptureImage captureImage, String str) {
        this.mExecutor.submit(new InputSaveRunnable(captureImage, str));
    }
}
