package com.xiaomi.Video2GifEditer;

import android.util.Log;

public class VideoThumbnailHelper {
    private static String TAG = "MediaThumbnail";
    private long mNativeThumbnail = 0;
    private VideoThumbnailNotifier mNotifier = null;

    public interface VideoThumbnailNotifier {
        void OnReceiveAllComplete();

        void OnReceivePngFile(String str, long j);
    }

    public VideoThumbnailHelper() {
        Log.d(TAG, "VideoThumbnailHelper ConstructThumbnail");
        this.mNativeThumbnail = ConstructThumbnailJni();
    }

    private native void CancelThumbnailsJni();

    private native long ConstructThumbnailJni();

    private native void DestructThumbnailJni();

    private native boolean GenerateThumbnailsJni(String str, String str2, int i, int i2, int i3, boolean z, long j);

    private void OnReceiveAllComplete() {
        VideoThumbnailNotifier videoThumbnailNotifier = this.mNotifier;
        if (videoThumbnailNotifier != null) {
            videoThumbnailNotifier.OnReceiveAllComplete();
        }
    }

    private void OnReceivePngFile(String str, long j) {
        VideoThumbnailNotifier videoThumbnailNotifier = this.mNotifier;
        if (videoThumbnailNotifier != null) {
            videoThumbnailNotifier.OnReceivePngFile(str, j);
        }
    }

    public void CancelThumbnails() {
        Log.d(TAG, "CancelThumbnails");
        if (this.mNativeThumbnail != 0) {
            CancelThumbnailsJni();
        }
    }

    public boolean GenerateThumbnails(String str, String str2, int i, int i2, int i3, boolean z, long j, VideoThumbnailNotifier videoThumbnailNotifier) {
        Log.d(TAG, "GenerateThumbnails");
        this.mNotifier = videoThumbnailNotifier;
        return GenerateThumbnailsJni(str, str2, i, i2, i3, z, j);
    }

    public void ReleaseThumbnail() {
        Log.d(TAG, "ReleaseThumbnail");
        if (this.mNativeThumbnail != 0) {
            DestructThumbnailJni();
            this.mNotifier = null;
            this.mNativeThumbnail = 0;
        }
    }
}
