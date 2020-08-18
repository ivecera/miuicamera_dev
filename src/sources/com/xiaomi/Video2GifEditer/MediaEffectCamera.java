package com.xiaomi.Video2GifEditer;

import android.media.Image;
import android.util.Log;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGLContext;

public class MediaEffectCamera {
    private static String TAG = "MediaEffectCamera";
    private long mMediaFilterCamera = 0;
    EffectCameraNotifier mMediaFilterCameraNotify = null;

    public MediaEffectCamera() {
        Log.d(TAG, "construct MediaEffectCamera");
    }

    private static native void CancelRecordingJni(long j);

    private static native long ConstructMediaEffectCameraJni(EGLContext eGLContext, int i, int i2, int i3, int i4, EffectCameraNotifier effectCameraNotifier);

    private static native void DestructMediaEffectCameraJni(long j);

    private static native int GetRecordingStatusJni(long j);

    private static native void PauseRecordingJni(long j);

    private static native void PushExtraYAndUVFrameJni(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2, long j2);

    private static native void ResumeRecordingJni(long j);

    private static native void SetOrientationJni(long j, int i);

    private static native void StartRecordingJni(long j, int i, String str, String str2, String str3, String str4, long j2);

    private static native void StopRecordingJni(long j);

    public static String Version() {
        return VersionJni();
    }

    private static native String VersionJni();

    public void CancelRecording() {
        String str = TAG;
        Log.d(str, "Cancel MediaFilterCamera: " + this.mMediaFilterCamera);
        CancelRecordingJni(this.mMediaFilterCamera);
    }

    public void ConstructMediaEffectCamera(int i, int i2, int i3, int i4, EffectCameraNotifier effectCameraNotifier) {
        this.mMediaFilterCameraNotify = effectCameraNotifier;
        this.mMediaFilterCamera = ConstructMediaEffectCameraJni(null, i, i2, i3, i4, this.mMediaFilterCameraNotify);
        String str = TAG;
        Log.d(str, "construct MediaFilterCamera: " + this.mMediaFilterCamera);
    }

    public void DestructMediaEffectCamera() {
        String str = TAG;
        Log.d(str, "destruct MediaEffectCamera: " + this.mMediaFilterCamera);
        DestructMediaEffectCameraJni(this.mMediaFilterCamera);
        this.mMediaFilterCamera = 0;
        this.mMediaFilterCameraNotify = null;
    }

    public RecordingStatus GetRecordingStatus() {
        Log.d(TAG, "GetRecordingStatus ");
        return RecordingStatus.int2enum(GetRecordingStatusJni(this.mMediaFilterCamera));
    }

    public void PauseRecording() {
        String str = TAG;
        Log.d(str, "Pause MediaFilterCamera: " + this.mMediaFilterCamera);
        PauseRecordingJni(this.mMediaFilterCamera);
    }

    public void PushExtraYAndUVFrame(Image image) {
        String str = TAG;
        Log.d(str, "PushExtraYUVFrame MediaFilterCamera: " + this.mMediaFilterCamera);
        Image.Plane[] planes = image.getPlanes();
        PushExtraYAndUVFrameJni(this.mMediaFilterCamera, planes[0].getBuffer(), planes[2].getBuffer(), planes[0].getRowStride(), image.getHeight(), image.getTimestamp() / 1000000);
    }

    public void ResumeRecording() {
        String str = TAG;
        Log.d(str, "Resume MediaFilterCamera: " + this.mMediaFilterCamera);
        ResumeRecordingJni(this.mMediaFilterCamera);
    }

    public void SetOrientation(int i) {
        String str = TAG;
        Log.d(str, "SetOrientation MediaFilterCamera: " + i);
        SetOrientationJni(this.mMediaFilterCamera, i);
    }

    public void StartRecording(int i, String str, String str2, String str3, String str4, long j) {
        String str5 = TAG;
        Log.d(str5, "Start MediaFilterCamera: " + this.mMediaFilterCamera + " filePath: " + str);
        StartRecordingJni(this.mMediaFilterCamera, i, str, str2, str3, str4, j);
    }

    public void StopRecording() {
        String str = TAG;
        Log.d(str, "Stop MediaFilterCamera: " + this.mMediaFilterCamera);
        StopRecordingJni(this.mMediaFilterCamera);
    }
}
