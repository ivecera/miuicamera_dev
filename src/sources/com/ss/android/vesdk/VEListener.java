package com.ss.android.vesdk;

import android.graphics.Bitmap;
import org.json.JSONObject;

public class VEListener {

    public interface VEAudioRecorderStateListener {
        void audioRecorderOpenFailed(int i, String str);

        void onAudioRecordError();

        void onPCMDataAvailable(byte[] bArr, int i);

        void onStartRecord(int i, int i2, int i3);

        void onStopRecord(boolean z);
    }

    public interface VECallListener {
        void onDone(int i);
    }

    public interface VECameraStateExtListener extends VECameraStateListener {
        void onError(int i, String str);

        void onInfo(int i, int i2, String str);
    }

    public interface VECameraStateListener {
        void cameraOpenFailed(int i);

        void cameraOpenSuccess();
    }

    public interface VEEditorCompileListener {
        void onCompileDone();

        void onCompileError(int i, int i2, float f2, String str);

        void onCompileProgress(float f2);
    }

    public interface VEEditorEffectListener {
        void onDone(int i, boolean z);
    }

    public interface VEEditorGenReverseListener {
        void onReverseDone(int i);
    }

    public interface VEEditorSeekListener {
        void onSeekDone(int i);
    }

    public interface VEEncoderListener {
        void onEncoderDataAvailable(byte[] bArr, int i, int i2, boolean z);
    }

    public interface VEFirstFrameListener {
        void onRendered();
    }

    public interface VEGetImageListener {
        int onGetImageData(byte[] bArr, int i, int i2, int i3);
    }

    public interface VEImageListener {
        void onImage(int[] iArr, int i, int i2, Bitmap.Config config);
    }

    public interface VEMonitorListener {
        void monitorLog(String str, JSONObject jSONObject);
    }

    public interface VERecorderNativeInitExtListener extends VERecorderNativeInitListener {
        void onError(int i);
    }

    public interface VERecorderNativeInitListener {
        void onHardEncoderInit(boolean z);

        void onNativeInit(int i, String str);
    }

    public interface VERecorderPreviewListener {
        void onPreviewResult(int i, String str);
    }
}
