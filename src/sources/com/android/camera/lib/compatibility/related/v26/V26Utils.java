package com.android.camera.lib.compatibility.related.v26;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.GraphicBuffer;
import android.hardware.camera2.CaptureRequest;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

@TargetApi(26)
public class V26Utils {
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING = 802;
    public static final int MEDIA_RECORDER_INFO_NEXT_OUTPUT_FILE_STARTED = 803;
    private static final String TAG = "V26Utils";

    private V26Utils() {
    }

    public static void addChannelForNotificationBuilder(NotificationManager notificationManager, String str, CharSequence charSequence, Notification.Builder builder) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(str, charSequence, 2);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setLockscreenVisibility(-1);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId(str);
        }
    }

    public static void allocGraphicBuffers() {
        GraphicBuffer.create(12800, 2560, 1, 51).destroy();
    }

    public static boolean setNextOutputFile(MediaRecorder mediaRecorder, FileDescriptor fileDescriptor) {
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                mediaRecorder.setNextOutputFile(fileDescriptor);
                return true;
            } catch (IOException e2) {
                Log.e(TAG, e2.getMessage(), e2);
            }
        }
        return false;
    }

    public static boolean setNextOutputFile(MediaRecorder mediaRecorder, String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                mediaRecorder.setNextOutputFile(new File(str));
                return true;
            } catch (IOException e2) {
                Log.e(TAG, e2.getMessage(), e2);
            }
        }
        return false;
    }

    public static void setZsl(CaptureRequest.Builder builder, boolean z) {
        if (Build.VERSION.SDK_INT >= 26) {
            builder.set(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.valueOf(z));
        }
    }
}
