package com.ss.android.vesdk;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.ss.android.ttve.model.VEMusicWaveBean;
import com.ss.android.ttve.model.VEWaveformVisualizer;
import com.ss.android.ttve.monitor.MonitorUtils;
import com.ss.android.ttve.nativePort.TEEffectUtils;
import com.ss.android.ttve.nativePort.TEVideoUtils;
import com.ss.android.ttve.nativePort.TEVideoUtilsCallback;
import com.ss.android.vesdk.VEListener;
import com.ss.android.vesdk.keyvaluepair.VEKeyValue;
import java.util.ArrayList;

public class VEUtils {

    public interface VEExecFFmpegCommandCallback {
        void onProgressChanged(int i);
    }

    public static VESize calcTargetRes(int[] iArr, int[] iArr2, ROTATE_DEGREE[] rotate_degreeArr, int i) {
        int length;
        int i2;
        int i3;
        VESize vESize = new VESize(-1, -1);
        if (iArr2 == null || iArr == null || rotate_degreeArr == null || (length = iArr.length) < 1) {
            return vESize;
        }
        float f2 = 0.0f;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            int i7 = iArr2[i6];
            int i8 = iArr[i6];
            if (rotate_degreeArr[i6] == ROTATE_DEGREE.ROTATE_90 || rotate_degreeArr[i6] == ROTATE_DEGREE.ROTATE_270) {
                i8 = iArr2[i6];
                i7 = iArr[i6];
            }
            f2 = Math.max(f2, ((float) i7) / ((float) i8));
            i4 = Math.max(i4, i8);
            i5 = Math.max(i5, i7);
        }
        if (length != 1) {
            f2 = Math.min(f2, 1.7777778f);
        }
        if (f2 >= 1.0f) {
            i3 = Math.min(i4, i);
            i2 = (int) (((float) i3) * f2);
        } else {
            i2 = Math.min(i5, i);
            i3 = (int) (((float) i2) / f2);
        }
        vESize.width = ((i3 + 16) - 1) & -16;
        vESize.height = ((i2 + 16) - 1) & -16;
        return vESize;
    }

    public static int concatVideo(String[] strArr, String str) {
        return TEVideoUtils.concat(strArr, str);
    }

    public static int execFFmpegCommand(@NonNull String str, @Nullable final VEExecFFmpegCommandCallback vEExecFFmpegCommandCallback) {
        return TEVideoUtils.executeFFmpegCommand(str, new TEVideoUtils.ExecuteCommandListener() {
            /* class com.ss.android.vesdk.VEUtils.AnonymousClass1 */

            @Override // com.ss.android.ttve.nativePort.TEVideoUtils.ExecuteCommandListener
            public void onProgressChanged(int i) {
                VEExecFFmpegCommandCallback vEExecFFmpegCommandCallback = vEExecFFmpegCommandCallback;
                if (vEExecFFmpegCommandCallback != null) {
                    vEExecFFmpegCommandCallback.onProgressChanged(i);
                }
            }
        });
    }

    public static String findBestRemuxSuffix(@NonNull String str) throws VEException {
        return TEVideoUtils.findBestRemuxSuffix(str);
    }

    public static int getAudioFileInfo(@NonNull String str, @NonNull int[] iArr) {
        MonitorUtils.monitorStatistics("iesve_veutils_get_audio_info", 1, null);
        return TEVideoUtils.getAudioFileInfo(str, iArr);
    }

    @Nullable
    public static VEMusicWaveBean getMusicWaveData(@NonNull String str, int i, int i2) {
        return getMusicWaveData(str, i, i2, 10);
    }

    @Nullable
    public static VEMusicWaveBean getMusicWaveData(@NonNull String str, int i, int i2, int i3) {
        ArrayList<Float> waveBean;
        if (i == 0) {
            i = VEWaveformVisualizer.Default;
        }
        VEMusicWaveBean audioWaveData = TEVideoUtils.getAudioWaveData(str, i, i2, i3);
        if (audioWaveData == null || (waveBean = audioWaveData.getWaveBean()) == null || waveBean.size() == 0) {
            return null;
        }
        return audioWaveData;
    }

    public static int getQREncodedData(String str, final VEListener.VEImageListener vEImageListener) {
        return TEEffectUtils.getQREncodedData(str, null, new TEEffectUtils.ImageListener() {
            /* class com.ss.android.vesdk.VEUtils.AnonymousClass2 */

            @Override // com.ss.android.ttve.nativePort.TEEffectUtils.ImageListener
            public void onData(int[] iArr, int i, int i2, int i3) {
                VEListener.VEImageListener vEImageListener = vEImageListener;
                if (vEImageListener != null) {
                    vEImageListener.onImage(iArr, i, i2, Bitmap.Config.ARGB_8888);
                }
            }
        });
    }

    @Nullable
    public static VEMusicWaveBean getResampleMusicWaveData(@NonNull ArrayList<Float> arrayList, int i, int i2) {
        if (arrayList.size() == 0) {
            return null;
        }
        float[] fArr = new float[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            fArr[i3] = arrayList.get(i3).floatValue();
        }
        return TEVideoUtils.getResampleMusicWaveData(fArr, i, i2);
    }

    public static int getVideoFileInfo(@NonNull String str, @NonNull int[] iArr) {
        MonitorUtils.monitorStatistics("iesve_veutils_get_video_info", 1, null);
        return TEVideoUtils.getVideoFileInfo(str, iArr);
    }

    public static int getVideoFrames(@NonNull String str, @NonNull int[] iArr, int i, int i2, boolean z, @NonNull VEFrameAvailableListener vEFrameAvailableListener) {
        TEVideoUtilsCallback tEVideoUtilsCallback = new TEVideoUtilsCallback();
        tEVideoUtilsCallback.setListener(vEFrameAvailableListener);
        MonitorUtils.monitorStatistics("iesve_veutils_extract_video_frames", 1, null);
        return TEVideoUtils.getVideoFrames(str, iArr, i, i2, z, tEVideoUtilsCallback);
    }

    public static int getVideoFrames(@NonNull String str, @NonNull int[] iArr, @NonNull VEFrameAvailableListener vEFrameAvailableListener) {
        TEVideoUtilsCallback tEVideoUtilsCallback = new TEVideoUtilsCallback();
        tEVideoUtilsCallback.setListener(vEFrameAvailableListener);
        MonitorUtils.monitorStatistics("iesve_veutils_extract_video_frames", 1, null);
        return TEVideoUtils.getVideoFrames(str, iArr, 0, 0, false, tEVideoUtilsCallback);
    }

    public static int getVideoThumb(@NonNull String str, int i, @NonNull VEFrameAvailableListener vEFrameAvailableListener, boolean z, int i2, int i3, long j, int i4) {
        TEVideoUtilsCallback tEVideoUtilsCallback = new TEVideoUtilsCallback();
        tEVideoUtilsCallback.setListener(vEFrameAvailableListener);
        MonitorUtils.monitorStatistics("iesve_veutils_extract_video_thumb", 1, null);
        return TEVideoUtils.getVideoThumb(str, i, tEVideoUtilsCallback, z, i2, i3, j, i4);
    }

    public static int isCanImport(String str) {
        MonitorUtils.monitorStatistics("iesve_veutils_if_video_support_import", 1, null);
        return TEVideoUtils.isCanImport(str);
    }

    public static int isCanTransCode(@NonNull String str, int i, int i2) {
        return TEVideoUtils.isCanTransCode(str, i, i2);
    }

    public static int mux(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return -100;
        }
        MonitorUtils.monitorStatistics("iesve_veutils_combine_audio_and_video_start", 1, null);
        int mux = TEVideoUtils.mux(str, str2, str3);
        VEKeyValue vEKeyValue = new VEKeyValue();
        vEKeyValue.add("iesve_veutils_combine_audio_and_video_finish_result", mux == 0 ? "succ" : "fail");
        vEKeyValue.add("iesve_veutils_combine_audio_and_video_finish_reason", "");
        MonitorUtils.monitorStatistics("iesve_veutils_combine_audio_and_video_finish", 1, vEKeyValue);
        return mux;
    }

    public static int transCodeAudio(@NonNull String str, @NonNull String str2, int i, int i2) {
        return TEVideoUtils.transCodeAudioFile(str, str2, i, i2);
    }
}
