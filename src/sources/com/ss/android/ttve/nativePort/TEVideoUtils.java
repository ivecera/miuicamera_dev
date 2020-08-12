package com.ss.android.ttve.nativePort;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.ss.android.ttve.model.VEMusicWaveBean;
import com.ss.android.vesdk.VEException;
import com.ss.android.vesdk.VEResult;

@Keep
public final class TEVideoUtils {

    public interface ExecuteCommandListener {
        void onProgressChanged(int i);
    }

    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static int clearWavSeg(String str, int i, int i2) {
        return nativeClearWavSeg(str, i, i2);
    }

    public static int concat(String[] strArr, String str) {
        return nativeConcat(strArr, str);
    }

    public static int executeFFmpegCommand(String str, @Nullable ExecuteCommandListener executeCommandListener) {
        return nativeExecuteFFmpegCommand(str, executeCommandListener);
    }

    public static String findBestRemuxSuffix(@NonNull String str) throws VEException {
        if (str.length() > 0) {
            String nativeFindBestRemuxSuffix = nativeFindBestRemuxSuffix(str);
            if (nativeFindBestRemuxSuffix.length() > 0) {
                return nativeFindBestRemuxSuffix;
            }
            throw new VEException(VEResult.TER_BAD_FILE, "该文件暂不支持转封装!");
        }
        throw new VEException(-100, "请检查输入参数!");
    }

    public static int generateMuteWav(String str, int i, int i2, int i3) {
        return nativeGenerateMuteWav(str, i, i2, i3);
    }

    public static int getAudioFileInfo(String str, int[] iArr) {
        return nativeGetAudioFileInfo(str, iArr);
    }

    public static VEMusicWaveBean getAudioWaveData(String str, int i, int i2, int i3) {
        return nativeGetAudioWaveData(str, i, i2, i3);
    }

    public static VEMusicWaveBean getResampleMusicWaveData(float[] fArr, int i, int i2) {
        return nativeGetResampleMusicWaveData(fArr, i, i2);
    }

    public static int getVideoFileInfo(String str, int[] iArr) {
        return nativeGetFileInfo(str, iArr);
    }

    public static int getVideoFrames(String str, int[] iArr, int i, int i2, boolean z, Object obj) {
        return nativeGetVideoFrame(str, iArr, i, i2, z, obj);
    }

    public static int getVideoThumb(String str, int i, Object obj, boolean z, int i2, int i3, long j, int i4) {
        return nativeGetVideoThumb(str, i, obj, z, i2, i3, j, i4);
    }

    public static int isCanImport(String str) {
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeIsCanImport(str);
    }

    public static int isCanTransCode(@NonNull String str, int i, int i2) {
        return nativeIsCanTransCode(str, i, i2);
    }

    public static int mux(String str, String str2, String str3) {
        return nativeMux(str, str2, str3);
    }

    private static native int nativeClearWavSeg(String str, int i, int i2);

    private static native int nativeConcat(String[] strArr, String str);

    private static native int nativeExecuteFFmpegCommand(String str, ExecuteCommandListener executeCommandListener);

    private static native String nativeFindBestRemuxSuffix(@NonNull String str);

    private static native int nativeGenerateMuteWav(String str, int i, int i2, int i3);

    private static native int nativeGetAudioFileInfo(String str, int[] iArr);

    private static native VEMusicWaveBean nativeGetAudioWaveData(String str, int i, int i2, int i3);

    private static native int nativeGetFileInfo(String str, int[] iArr);

    private static native VEMusicWaveBean nativeGetResampleMusicWaveData(float[] fArr, int i, int i2);

    private static native int nativeGetVideoFrame(String str, int[] iArr, int i, int i2, boolean z, Object obj);

    private static native int nativeGetVideoThumb(String str, int i, Object obj, boolean z, int i2, int i3, long j, int i4);

    private static native int nativeIsCanImport(String str);

    private static native int nativeIsCanTransCode(String str, int i, int i2);

    private static native int nativeMux(String str, String str2, String str3);

    private static native int nativeReverseAllIVideo(String str, String str2);

    private static native int nativeTransCodecAudioFile(String str, String str2, int i, int i2);

    public static int reverseAllIVideo(@NonNull String str, @NonNull String str2) {
        return nativeReverseAllIVideo(str, str2);
    }

    public static int transCodeAudioFile(String str, String str2, int i, int i2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            return nativeTransCodecAudioFile(str, str2, i, i2);
        }
        return -100;
    }
}
