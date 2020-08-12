package com.android.camera.lib.compatibility.related.v29;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Size;
import android.view.IWindowManager;
import android.view.ViewConfiguration;
import com.android.camera.log.Log;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@TargetApi(29)
public class V29Utils {
    private static final String TAG = "V29Utils";

    private static class Resizer implements ImageDecoder.OnHeaderDecodedListener {
        private final Size size;

        private Resizer(Size size2) {
            this.size = size2;
        }

        public void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
            imageDecoder.setAllocator(1);
            int max = Math.max(imageInfo.getSize().getWidth() / this.size.getWidth(), imageInfo.getSize().getHeight() / this.size.getHeight());
            if (max > 1) {
                imageDecoder.setTargetSampleSize(max);
            }
        }
    }

    public static StreamConfigurationMap createStreamConfigMap(List<StreamConfiguration> list, CameraCharacteristics cameraCharacteristics) {
        return new StreamConfigurationMap((StreamConfiguration[]) list.toArray(new StreamConfiguration[0]), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS), (StreamConfiguration[]) cameraCharacteristics.get(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS), (StreamConfiguration[]) cameraCharacteristics.get(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS), (StreamConfiguration[]) cameraCharacteristics.get(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS), (StreamConfigurationDuration[]) cameraCharacteristics.get(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS), (HighSpeedVideoConfiguration[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS), (ReprocessFormatsMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP), true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0077, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007c, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007d, code lost:
        r8.addSuppressed(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0080, code lost:
        throw r9;
     */
    public static Bitmap createVideoThumbnailBitmap(String str, FileDescriptor fileDescriptor, int i, int i2) {
        Size size = new Size(i, i2);
        Resizer resizer = new Resizer(size);
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            if (str != null) {
                mediaMetadataRetriever.setDataSource(str);
            } else {
                mediaMetadataRetriever.setDataSource(fileDescriptor);
            }
            byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
            if (embeddedPicture != null) {
                Bitmap decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(embeddedPicture), resizer);
                mediaMetadataRetriever.close();
                return decodeBitmap;
            }
            int parseInt = Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
            int parseInt2 = Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
            long parseLong = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
            if (size.getWidth() <= parseInt || size.getHeight() <= parseInt2) {
                Bitmap scaledFrameAtTime = mediaMetadataRetriever.getScaledFrameAtTime(parseLong / 2, 2, size.getWidth(), size.getHeight());
                mediaMetadataRetriever.close();
                return scaledFrameAtTime;
            }
            Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(parseLong / 2, 2);
            mediaMetadataRetriever.close();
            return frameAtTime;
        } catch (Exception e2) {
            Log.e(TAG, e2.getMessage(), e2);
            return null;
        }
    }

    public static Uri getMediaUri(Context context, boolean z, boolean z2) {
        Uri uri = z ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (!z2) {
            return uri;
        }
        ArrayList<String> arrayList = new ArrayList(MediaStore.getExternalVolumeNames(context));
        if (arrayList.isEmpty() || arrayList.size() <= 1) {
            return uri;
        }
        for (String str : arrayList) {
            if (!"external_primary".equals(str)) {
                return z ? MediaStore.Video.Media.getContentUri(str) : MediaStore.Images.Media.getContentUri(str);
            }
        }
        return uri;
    }

    public static Set<String> getPhysicalCameraIds(CameraCharacteristics cameraCharacteristics) {
        return cameraCharacteristics.getPhysicalCameraIds();
    }

    public static int getScaledMinimumScalingSpan(Context context) {
        return ViewConfiguration.get(context).getScaledMinimumScalingSpan();
    }

    public static boolean hasNavigationBar(Context context, IWindowManager iWindowManager) {
        try {
            return iWindowManager.hasNavigationBar(context.getDisplayId());
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isHeicSupported(CameraCharacteristics cameraCharacteristics) {
        StreamConfigurationMap streamConfigurationMap;
        int[] outputFormats;
        if (Build.VERSION.SDK_INT < 29 || (streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)) == null || (outputFormats = streamConfigurationMap.getOutputFormats()) == null || outputFormats.length == 0) {
            return false;
        }
        return Arrays.stream(outputFormats).anyMatch(a.INSTANCE);
    }

    static /* synthetic */ boolean p(int i) {
        return i == 1212500294;
    }
}
