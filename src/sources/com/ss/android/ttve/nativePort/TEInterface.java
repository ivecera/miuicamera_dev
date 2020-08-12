package com.ss.android.ttve.nativePort;

import android.graphics.Bitmap;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Surface;
import com.ss.android.vesdk.ROTATE_DEGREE;
import com.ss.android.vesdk.VEException;
import com.ss.android.vesdk.VEGestureType;
import com.ss.android.vesdk.VEMusicSRTEffectParam;
import com.ss.android.vesdk.VERect;
import com.ss.android.vesdk.VEResult;
import com.ss.android.vesdk.VEStickerAnimator;
import com.ss.android.vesdk.VETimelineParams;
import com.ss.android.vesdk.VEWaterMarkPosition;
import com.ss.android.vesdk.VEWatermarkParam;
import java.util.ArrayList;
import java.util.Arrays;

@Keep
public final class TEInterface extends TENativeServiceBase {
    private static final int OPTION_UPDATE_ANYTIME = 1;
    private static final int OPTION_UPDATE_BEFORE_PREPARE = 0;
    private static final String TAG = "TEInterface";
    private int mHostTrackIndex = -1;
    private long mNative = 0;

    static {
        TENativeLibsLoader.loadLibrary();
    }

    private TEInterface() {
    }

    public static TEInterface createEngine() {
        TEInterface tEInterface = new TEInterface();
        long nativeCreateEngine = tEInterface.nativeCreateEngine();
        if (nativeCreateEngine == 0) {
            return null;
        }
        tEInterface.mNative = nativeCreateEngine;
        return tEInterface;
    }

    private native int nativeAddAudioTrack(long j, String str, int i, int i2, int i3, int i4, boolean z);

    private native int nativeAddAudioTrack2(long j, String str, int i, int i2, int i3, int i4, boolean z, int i5, int i6);

    private native int nativeAddAudioTrackMV(long j, String str, int i, int i2, int i3, int i4, int i5, boolean z);

    private native int nativeAddExternalTrack(long j, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, double d2, double d3, double d4, double d5, int i, int i2);

    private native int nativeAddExternalTrackMV(long j, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, double d2, double d3, double d4, double d5, int i, int i2);

    private native int[] nativeAddFilters(long j, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5);

    private native int nativeAddInfoSticker(long j, String str, String[] strArr);

    private native void nativeAddMetaData(long j, String str, String str2);

    private native int nativeAddVideoTrack(long j, String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, int i);

    private native int nativeAdjustFilterInOut(long j, int i, int i2, int i3);

    private native int nativeBegin2DBrush(long j);

    private native int nativeCancelGetImages(long j);

    private native void nativeClearDisplay(long j, int i);

    private native long nativeCreateEngine();

    private native int nativeCreateImageScene(long j, Bitmap[] bitmapArr, int[] iArr, int[] iArr2, String[] strArr, int[] iArr3, int[] iArr4, String[] strArr2, String[][] strArr3, float[] fArr, int i);

    private native int nativeCreateScene(long j, String str, String[] strArr, String[] strArr2, String[] strArr3, String[][] strArr4, int i);

    private native int nativeCreateScene2(long j, String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, int[] iArr3, int[] iArr4, String[] strArr3, String[][] strArr4, float[] fArr, int[] iArr5, int i);

    private native int nativeCreateSceneMV(long j, String[] strArr, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, String[] strArr2, int[] iArr5, int[] iArr6, int[] iArr7, int[] iArr8, int[] iArr9, String[] strArr3, String[][] strArr4, float[] fArr, int i);

    private native int nativeCreateTimeline(long j);

    private native int nativeDeleteAudioTrack(long j, int i);

    private native int nativeDeleteExternalTrack(long j, int i);

    private native int nativeDestroyEngine(long j);

    private native int nativeEnd2DBrush(long j, String str);

    private native int nativeGet2DBrushStrokeCount(long j);

    private native int nativeGetCurPosition(long j);

    private native int nativeGetCurState(long j);

    private native int[] nativeGetDisplayDumpSize(long j);

    private native int nativeGetDisplayImage(long j, byte[] bArr, int i, int i2);

    private native int nativeGetDuration(long j);

    private native int nativeGetExternalTrackFilter(long j, int i);

    private native int nativeGetImages(long j, int[] iArr, int i, int i2);

    private native float[] nativeGetInfoStickerBoundingBox(long j, int i);

    private native int[] nativeGetInitResolution(long j);

    private native String nativeGetMetaData(long j, String str);

    private native float nativeGetTrackVolume(long j, int i, int i2, int i3);

    private native Object nativeInitMVResources(long j, String str, String[] strArr, String[] strArr2, boolean z);

    private native boolean nativeIsInfoStickerAnimatable(long j, int i);

    private native int nativePause(long j);

    private native int nativePauseInfoStickerAnimation(long j, boolean z);

    private native int nativePauseSync(long j);

    private native int nativePrepareEngine(long j, int i);

    private native int nativeProcessLongPressEvent(long j, float f2, float f3);

    private native int nativeProcessPanEvent(long j, float f2, float f3, float f4, float f5, float f6);

    private native int nativeProcessRotationEvent(long j, float f2, float f3);

    private native int nativeProcessScaleEvent(long j, float f2, float f3);

    private native int nativeProcessTouchDownEvent(long j, float f2, float f3, int i);

    private native int nativeProcessTouchMoveEvent(long j, float f2, float f3);

    private native int nativeProcessTouchUpEvent(long j, float f2, float f3, int i);

    private native void nativeReleasePreviewSurface(long j);

    private native int nativeRemoveFilter(long j, int[] iArr);

    private native int nativeRemoveInfoSticker(long j, int i);

    private native int nativeRestore(long j, String str);

    private native String nativeSave(long j);

    private native int nativeSeek(long j, int i, int i2, int i3, int i4);

    private native int nativeSet2DBrushCanvasColor(long j, float f2);

    private native int nativeSet2DBrushColor(long j, float f2, float f3, float f4, float f5);

    private native int nativeSet2DBrushSize(long j, float f2);

    private native void nativeSetBackGroundColor(long j, int i);

    private native int nativeSetClipAttr(long j, int i, int i2, int i3, String str, String str2);

    private native int nativeSetDestroyVersion(long j, boolean z);

    private native void nativeSetDisplayState(long j, float f2, float f3, float f4, float f5, int i, int i2, int i3);

    private native int nativeSetDldThrVal(long j, int i);

    private native int nativeSetDleEnabled(long j, boolean z);

    private native void nativeSetEnableMultipleAudioFilter(long j, boolean z);

    private native void nativeSetEnableRemuxVideo(long j, boolean z);

    private native void nativeSetEncoderParallel(long j, boolean z);

    private native void nativeSetExpandLastFrame(long j, boolean z);

    private native int nativeSetFilterParam(long j, int i, String str, VEMusicSRTEffectParam vEMusicSRTEffectParam);

    private native int nativeSetFilterParam(long j, int i, String str, VEStickerAnimator vEStickerAnimator);

    private native int nativeSetFilterParam(long j, int i, String str, String str2);

    private native void nativeSetOption(long j, int i, String str, float f2);

    private native void nativeSetOption(long j, int i, String str, long j2);

    private native void nativeSetOption(long j, int i, String str, String str2);

    private native void nativeSetOptionArray(long j, int i, String[] strArr, long[] jArr);

    private native void nativeSetPreviewFps(long j, int i);

    private native int nativeSetPreviewScaleMode(long j, int i);

    private native void nativeSetPreviewSurface(long j, Surface surface);

    private native void nativeSetSpeedRatio(long j, float f2);

    private native void nativeSetSurfaceSize(long j, int i, int i2);

    private native int nativeSetTimeRange(long j, int i, int i2, int i3);

    private native boolean nativeSetTrackVolume(long j, int i, int i2, float f2);

    private native void nativeSetViewPort(long j, int i, int i2, int i3, int i4);

    private native void nativeSetWaterMark(long j, ArrayList<String[]> arrayList, int i, int i2, int i3, int i4, int i5, long j2, int i6, VEWatermarkParam.VEWatermarkMask vEWatermarkMask);

    private native int nativeStart(long j);

    private native int nativeStop(long j);

    private native boolean nativeTestSerialization(long j);

    private native int nativeUndo2DBrush(long j);

    private native int nativeUpdateAudioTrack(long j, int i, int i2, int i3, int i4, int i5, boolean z);

    private native int nativeUpdateAudioTrack2(long j, int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7);

    private native int nativeUpdateScene(long j, String[] strArr, int[] iArr, int[] iArr2);

    private native int nativeUpdateSceneFileOrder(long j, int[] iArr);

    private native int nativeUpdateSceneTime(long j, boolean[] zArr, int[] iArr, int[] iArr2, int[] iArr3, double[] dArr);

    private native int nativeUpdateTrackClip(long j, int i, int i2, String[] strArr);

    private native int nativeUpdateTrackFilter(long j, int i, int i2, boolean z);

    public int addAudioTrack(String str, int i, int i2, int i3, int i4, boolean z) {
        if (this.mNative == 0) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeAddAudioTrack(this.mNative, str, i, i2, i3, i4, z);
    }

    public int addAudioTrack(String str, int i, int i2, int i3, int i4, boolean z, int i5, int i6) {
        if (this.mNative == 0) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeAddAudioTrack2(this.mNative, str, i, i2, i3, i4, z, i5, i6);
    }

    public int addAudioTrackForMV(String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        if (this.mNative == 0) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -100;
        }
        return nativeAddAudioTrackMV(this.mNative, str, i, i2, i3, i4, i5, z);
    }

    public int addExternalTrack(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, double d2, double d3, double d4, double d5, int i) {
        return nativeAddExternalTrack(this.mNative, strArr, strArr2, iArr, iArr2, iArr, iArr2, d2, d3, d4, d5, 5, this.mHostTrackIndex);
    }

    public int[] addFilters(int[] iArr, String[] strArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        long j = this.mNative;
        if (j != 0) {
            return nativeAddFilters(j, iArr, strArr, iArr2, iArr3, iArr4, iArr5);
        }
        return new int[]{-1};
    }

    public int addInfoSticker(String str, String[] strArr) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : this.mHostTrackIndex < 0 ? VEResult.TER_INVALID_STAT : nativeAddInfoSticker(j, str, strArr);
    }

    public void addMetaData(String str, String str2) {
        long j = this.mNative;
        if (j != 0) {
            nativeAddMetaData(j, str, str2);
        }
    }

    public int addSticker(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, double d2, double d3, double d4, double d5) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int i = this.mHostTrackIndex;
        return i < 0 ? VEResult.TER_INVALID_STAT : nativeAddExternalTrack(j, strArr, strArr2, iArr, iArr2, iArr3, iArr4, d2, d3, d4, d5, 0, i);
    }

    public int addVideoTrackForMV(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        int i = this.mHostTrackIndex;
        return i < 0 ? VEResult.TER_INVALID_STAT : nativeAddVideoTrack(j, strArr, strArr2, iArr, iArr2, iArr3, iArr4, iArr5, i);
    }

    public int addWaterMark(String[] strArr, String[] strArr2, int[] iArr, int[] iArr2, double d2, double d3, double d4, double d5) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int i = this.mHostTrackIndex;
        return i < 0 ? VEResult.TER_INVALID_STAT : nativeAddExternalTrack(j, strArr, strArr2, iArr, iArr2, iArr, iArr2, d2, d3, d4, d5, 5, i);
    }

    public int adjustFilterInOut(int i, int i2, int i3) {
        return nativeAdjustFilterInOut(this.mNative, i, i2, i3);
    }

    public int begin2DBrush() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeBegin2DBrush(j);
    }

    public int cancelGetImages() {
        return nativeCancelGetImages(this.mNative);
    }

    public void clearDisplay(int i) {
        nativeClearDisplay(this.mNative, i);
    }

    public int createImageScene(Bitmap[] bitmapArr, int[] iArr, int[] iArr2, String[] strArr, int[] iArr3, int[] iArr4, String[] strArr2, String[][] strArr3, float[] fArr, int i) {
        float[] fArr2;
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (fArr == null) {
            float[] fArr3 = new float[bitmapArr.length];
            Arrays.fill(fArr3, 1.0f);
            fArr2 = fArr3;
        } else {
            fArr2 = fArr;
        }
        int nativeCreateImageScene = nativeCreateImageScene(this.mNative, bitmapArr, iArr, iArr2, strArr, iArr3, iArr4, strArr2, strArr3, fArr2, i);
        if (nativeCreateImageScene < 0) {
            return nativeCreateImageScene;
        }
        this.mHostTrackIndex = nativeCreateImageScene;
        return 0;
    }

    public int createScene(String str, String[] strArr, String[] strArr2, String[] strArr3, String[][] strArr4, int i) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int nativeCreateScene = nativeCreateScene(j, str, strArr, strArr2, strArr3, strArr4, i);
        if (nativeCreateScene < 0) {
            return nativeCreateScene;
        }
        this.mHostTrackIndex = nativeCreateScene;
        return 0;
    }

    public int createScene2(String[] strArr, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, String[] strArr2, int[] iArr5, int[] iArr6, int[] iArr7, int[] iArr8, int[] iArr9, String[] strArr3, String[][] strArr4, float[] fArr, int i) {
        float[] fArr2;
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (fArr == null) {
            float[] fArr3 = new float[strArr.length];
            Arrays.fill(fArr3, 1.0f);
            fArr2 = fArr3;
        } else {
            fArr2 = fArr;
        }
        int nativeCreateSceneMV = nativeCreateSceneMV(this.mNative, strArr, iArr, iArr2, iArr3, iArr4, strArr2, iArr5, iArr6, iArr7, iArr8, iArr9, strArr3, strArr4, fArr2, i);
        if (nativeCreateSceneMV < 0) {
            return nativeCreateSceneMV;
        }
        this.mHostTrackIndex = nativeCreateSceneMV;
        return 0;
    }

    public int createScene2(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, int[] iArr3, int[] iArr4, String[] strArr3, String[][] strArr4, float[] fArr, int i) {
        return createScene2(strArr, iArr, iArr2, strArr2, iArr3, iArr4, strArr3, strArr4, fArr, null, i);
    }

    public int createScene2(String[] strArr, int[] iArr, int[] iArr2, String[] strArr2, int[] iArr3, int[] iArr4, String[] strArr3, String[][] strArr4, float[] fArr, int[] iArr5, int i) {
        float[] fArr2;
        if (this.mNative == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (fArr == null) {
            float[] fArr3 = new float[strArr.length];
            Arrays.fill(fArr3, 1.0f);
            fArr2 = fArr3;
        } else {
            fArr2 = fArr;
        }
        int nativeCreateScene2 = nativeCreateScene2(this.mNative, strArr, iArr, iArr2, strArr2, iArr3, iArr4, strArr3, strArr4, fArr2, iArr5, i);
        if (nativeCreateScene2 < 0) {
            return nativeCreateScene2;
        }
        this.mHostTrackIndex = nativeCreateScene2;
        return 0;
    }

    public int createTimeline() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeCreateTimeline(j);
    }

    public int deleteAudioTrack(int i) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return nativeDeleteAudioTrack(j, i);
    }

    public int deleteSticker(int i) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (i < 0) {
            return -100;
        }
        return nativeDeleteExternalTrack(j, i);
    }

    public int deleteWatermark(int i) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (i < 0) {
            return -100;
        }
        return nativeDeleteExternalTrack(j, i);
    }

    public int destroyEngine() {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int nativeDestroyEngine = nativeDestroyEngine(j);
        this.mNative = 0;
        return nativeDestroyEngine;
    }

    public void enableSimpleProcessor(boolean z) {
        setOption(0, "engine processor mode", z ? 1 : 0);
    }

    public int end2DBrush(@NonNull String str) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeEnd2DBrush(j, str);
    }

    public int get2DBrushStrokeCount() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeGet2DBrushStrokeCount(j);
    }

    public int getCurPosition() {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return nativeGetCurPosition(j);
    }

    public int getCurState() {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return nativeGetCurState(j);
    }

    public int getDisplayImage(byte[] bArr, int i, int i2) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeGetDisplayImage(j, bArr, i, i2);
    }

    public VERect getDisplayRect() {
        long j = this.mNative;
        if (j == 0) {
            return new VERect(0, 0, 0, 0);
        }
        int[] nativeGetDisplayDumpSize = nativeGetDisplayDumpSize(j);
        return new VERect(nativeGetDisplayDumpSize[0], nativeGetDisplayDumpSize[1], nativeGetDisplayDumpSize[2], nativeGetDisplayDumpSize[3]);
    }

    public int getDuration() {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        return nativeGetDuration(j);
    }

    public int getImages(int[] iArr, int i, int i2) {
        return nativeGetImages(this.mNative, iArr, i, i2);
    }

    public float[] getInfoStickerBoundingBox(int i) throws VEException {
        long j = this.mNative;
        if (j == 0) {
            throw new VEException(VEResult.TER_INVALID_HANDLER, "");
        } else if (this.mHostTrackIndex >= 0) {
            float[] nativeGetInfoStickerBoundingBox = nativeGetInfoStickerBoundingBox(j, i);
            if (nativeGetInfoStickerBoundingBox[0] == 0.0f) {
                float[] fArr = new float[4];
                System.arraycopy(nativeGetInfoStickerBoundingBox, 1, fArr, 0, 4);
                return fArr;
            }
            throw new VEException(-1, "native getInfoStickerBoundingBox failed: " + nativeGetInfoStickerBoundingBox[0]);
        } else {
            throw new VEException(-100, "");
        }
    }

    public int[] getInitResolution() {
        int[] iArr = {-1, -1, -1, -1};
        long j = this.mNative;
        return j == 0 ? iArr : nativeGetInitResolution(j);
    }

    public String getMetaData(String str) {
        long j = this.mNative;
        return j == 0 ? "" : nativeGetMetaData(j, str);
    }

    public long getNativeHandler() {
        return this.mNative;
    }

    public int getStickerFilterIndex(int i) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        if (i < 0) {
            return -100;
        }
        return nativeGetExternalTrackFilter(j, i);
    }

    public float getTrackVolume(int i, int i2, int i3) {
        long j = this.mNative;
        if (j == 0) {
            return 0.0f;
        }
        return nativeGetTrackVolume(j, i, i2, i3);
    }

    public Object initMVResources(String str, String[] strArr, String[] strArr2, boolean z) {
        return nativeInitMVResources(this.mNative, str, strArr, strArr2, z);
    }

    public boolean isInfoStickerAnimatable(int i) {
        long j = this.mNative;
        if (j != 0 && this.mHostTrackIndex >= 0) {
            return nativeIsInfoStickerAnimatable(j, i);
        }
        return false;
    }

    public native int nativeSetAudioOffset(long j, int i, int i2);

    public int pause() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativePause(j);
    }

    public int pauseInfoStickerAnimation(boolean z) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : this.mHostTrackIndex < 0 ? VEResult.TER_INVALID_STAT : nativePauseInfoStickerAnimation(j, z);
    }

    public int pauseSync() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativePauseSync(j);
    }

    public int prepareEngine(int i) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativePrepareEngine(j, i);
    }

    public int processLongPressEvent(float f2, float f3) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessLongPressEvent(j, f2, f3);
    }

    public int processPanEvent(float f2, float f3, float f4, float f5, float f6) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessPanEvent(j, f2, f3, f4, f5, f6);
    }

    public int processRotationEvent(float f2, float f3) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessRotationEvent(j, f2, f3);
    }

    public int processScaleEvent(float f2, float f3) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessScaleEvent(j, f2, f3);
    }

    public int processTouchDownEvent(float f2, float f3, VEGestureType vEGestureType) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessTouchDownEvent(j, f2, f3, vEGestureType.ordinal());
    }

    public int processTouchMoveEvent(float f2, float f3) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessTouchMoveEvent(j, f2, f3);
    }

    public int processTouchUpEvent(float f2, float f3, VEGestureType vEGestureType) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeProcessTouchUpEvent(j, f2, f3, vEGestureType.ordinal());
    }

    public void releasePreviewSurface() {
        long j = this.mNative;
        if (j != 0) {
            nativeReleasePreviewSurface(j);
        }
    }

    public int removeFilter(int[] iArr) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeRemoveFilter(j, iArr);
    }

    public int removeInfoSticker(int i) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : this.mHostTrackIndex < 0 ? VEResult.TER_INVALID_STAT : nativeRemoveInfoSticker(j, i);
    }

    public int restore(String str) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeRestore(j, str);
    }

    @Nullable
    public String save() {
        long j = this.mNative;
        if (j == 0) {
            return null;
        }
        String nativeSave = nativeSave(j);
        if (TextUtils.isEmpty(nativeSave)) {
            return null;
        }
        return nativeSave;
    }

    public int seek(int i, int i2, int i3, int i4) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSeek(j, i, i2, i3, i4);
    }

    public int set2DBrushCanvasAlpha(float f2) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSet2DBrushCanvasColor(j, f2);
    }

    public int set2DBrushColor(float f2, float f3, float f4, float f5) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSet2DBrushColor(j, f2, f3, f4, f5);
    }

    public int set2DBrushSize(float f2) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSet2DBrushSize(j, f2);
    }

    public int setAudioOffset(int i, int i2) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetAudioOffset(j, i, i2);
    }

    public void setBackGroundColor(int i) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetBackGroundColor(j, i);
        }
    }

    public int setClipAttr(int i, int i2, int i3, String str, String str2) {
        return nativeSetClipAttr(this.mNative, i, i2, i3, str, str2);
    }

    public void setCompileCommonEncodeOptions(int i, int i2) {
        setOption(0, "CompileBitrateMode", (long) i);
        setOption(0, "CompileEncodeProfile", (long) i2);
    }

    public void setCompileFps(int i) {
        setOption(0, "CompileFps", (long) i);
    }

    public void setCompileHardwareEncodeOptions(int i) {
        setOption(0, "CompileHardwareBitrate", (long) i);
    }

    public void setCompileSoftwareEncodeOptions(int i, long j, int i2, int i3) {
        setOption(0, "CompileSoftwareCrf", (long) i);
        setOption(0, "CompileSoftwareMaxrate", j);
        setOption(0, "CompileSoftwarePreset", (long) i2);
        setOption(0, "CompileSoftwareQp", (long) i3);
    }

    public void setCompileType(int i) {
        setOption(0, "CompileType", (long) i);
    }

    public void setCompileWatermark(VEWatermarkParam vEWatermarkParam) {
        setOption(0, "CompilePathWatermark", vEWatermarkParam.extFile);
        setOption(0, "CompilePathWavWatermark", "");
    }

    public void setCrop(int i, int i2, int i3, int i4) {
        setOption(1, new String[]{"engine crop x", "engine crop y", "engine crop width", "engine crop height"}, new long[]{(long) i, (long) i2, (long) i3, (long) i4});
    }

    public int setDestroyVersion(boolean z) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetDestroyVersion(j, z);
    }

    public void setDisplayState(float f2, float f3, float f4, float f5, int i, int i2, boolean z) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetDisplayState(j, f2, f3, f4, f5, i, i2, z ? 1 : 0);
        }
    }

    public int setDldThrVal(int i) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetDldThrVal(j, i);
    }

    public int setDleEnabled(boolean z) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetDleEnabled(j, z);
    }

    public void setEnableInterLeave(boolean z) {
        long j = 0;
        if (this.mNative != 0) {
            if (z) {
                j = 1;
            }
            setOption(0, "CompileInterleave", j);
        }
    }

    public void setEnableMultipleAudioFilter(boolean z) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetEnableMultipleAudioFilter(j, z);
        }
    }

    public void setEnableRemuxVideo(boolean z) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetEnableRemuxVideo(j, z);
        }
    }

    public void setEncGopSize(int i) {
        setOption(0, "video gop size", (long) i);
    }

    public void setEncoderParallel(boolean z) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetEncoderParallel(j, z);
        }
    }

    public void setEngineCompilePath(String str, String str2) {
        setOption(0, "CompilePath", str);
        setOption(0, "CompilePathWav", str2);
    }

    public void setExpandLastFrame(boolean z) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetExpandLastFrame(j, z);
        }
    }

    public int setFilterParam(int i, String str, VEMusicSRTEffectParam vEMusicSRTEffectParam) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetFilterParam(j, i, str, vEMusicSRTEffectParam);
    }

    public int setFilterParam(int i, String str, VEStickerAnimator vEStickerAnimator) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetFilterParam(j, i, str, vEStickerAnimator);
    }

    public int setFilterParam(int i, String str, String str2) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetFilterParam(j, i, str, str2);
    }

    public void setLooping(boolean z) {
        setOption(1, "engine loop play", z ? 1 : 0);
    }

    public void setMaxWidthHeight(int i, int i2) {
        if (i > 0) {
            setOption(0, "engine max video width", (long) i);
        }
        if (i2 > 0) {
            setOption(0, "engine max video height", (long) i2);
        }
    }

    public void setOption(int i, String str, float f2) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetOption(j, i, str, f2);
        }
    }

    public void setOption(int i, String str, long j) {
        long j2 = this.mNative;
        if (j2 != 0) {
            nativeSetOption(j2, i, str, j);
        }
    }

    public void setOption(int i, String str, String str2) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetOption(j, i, str, str2);
        }
    }

    public void setOption(int i, String[] strArr, long[] jArr) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetOptionArray(j, i, strArr, jArr);
        }
    }

    public void setPageMode(int i) {
        setOption(0, "engine page mode", (long) i);
    }

    public int setPreviewFps(int i) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        nativeSetPreviewFps(j, i);
        return 0;
    }

    public int setPreviewScaleMode(int i) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetPreviewScaleMode(j, i);
    }

    public void setPreviewSurface(Surface surface) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetPreviewSurface(j, surface);
        }
    }

    public void setResizer(int i, float f2, float f3) {
        setOption(0, "filter mode", (long) i);
        setOption(0, "resizer offset x percent", f2);
        setOption(0, "resizer offset y percent", f3);
    }

    public void setScaleMode(int i) {
        setOption(0, "filter mode", (long) i);
    }

    public void setSpeedRatio(float f2) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetSpeedRatio(j, f2);
        }
    }

    public void setSurfaceSize(int i, int i2) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetSurfaceSize(j, i, i2);
        }
    }

    public int setTimeRange(int i, int i2, int i3) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeSetTimeRange(j, i, i2, i3);
    }

    public boolean setTrackVolume(int i, int i2, float f2) {
        long j = this.mNative;
        if (j == 0) {
            return false;
        }
        return nativeSetTrackVolume(j, i, i2, f2);
    }

    public void setUseHwEnc(boolean z) {
        setOption(0, "HardwareVideo", z ? 1 : 0);
    }

    public void setUseLargeMattingModel(boolean z) {
        setOption(0, "UseLargeMattingModel", z ? 1 : 0);
    }

    public void setUsrRotate(int i) {
        if (i == 0) {
            setOption(0, "usr rotate", 0L);
        } else if (i == 90) {
            setOption(0, "usr rotate", 1L);
        } else if (i == 180) {
            setOption(0, "usr rotate", 2L);
        } else if (i != 270) {
            setOption(0, "usr rotate", 0L);
        } else {
            setOption(0, "usr rotate", 3L);
        }
    }

    public void setVideoCompileBitrate(int i, int i2) {
        setOption(0, "CompileBitrateMode", (long) i);
        setOption(0, "CompileBitrateValue", (long) i2);
    }

    public void setViewPort(int i, int i2, int i3, int i4) {
        long j = this.mNative;
        if (j != 0) {
            nativeSetViewPort(j, i, i2, i3, i4);
        }
    }

    public void setWaterMark(ArrayList<String[]> arrayList, int i, int i2, int i3, int i4, int i5, long j, VEWaterMarkPosition vEWaterMarkPosition, VEWatermarkParam.VEWatermarkMask vEWatermarkMask) {
        long j2 = this.mNative;
        if (j2 != 0) {
            nativeSetWaterMark(j2, arrayList, i, i2, i3, i4, i5, j, vEWaterMarkPosition.ordinal(), vEWatermarkMask);
        }
    }

    public void setWidthHeight(int i, int i2) {
        if (i > 0) {
            setOption(0, "engine video width", (long) i);
        }
        if (i2 > 0) {
            setOption(0, "engine video height", (long) i2);
        }
    }

    public int start() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeStart(j);
    }

    public int stop() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeStop(j);
    }

    public native String stringFromJNI();

    public boolean testSerialization() {
        return nativeTestSerialization(this.mNative);
    }

    public int undo2DBrush() {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeUndo2DBrush(j);
    }

    public int updateAudioTrack(int i, int i2, int i3, int i4, int i5, boolean z) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        if (i < 0) {
            return -100;
        }
        return nativeUpdateAudioTrack(j, i, i2, i3, i4, i5, z);
    }

    public int updateAudioTrack(int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) {
        long j = this.mNative;
        if (j == 0) {
            return -1;
        }
        if (i < 0) {
            return -100;
        }
        return nativeUpdateAudioTrack2(j, i, i2, i3, i4, i5, z, i6, i7);
    }

    public void updateResolution(int i, int i2, int i3, int i4) {
        setOption(1, new String[]{"engine preivew width", "engine preivew height", "engine preivew width percent", "engine preivew height percent"}, new long[]{(long) i, (long) i2, (long) i3, (long) i4});
    }

    public int updateScene(String[] strArr, int[] iArr, int[] iArr2) {
        long j = this.mNative;
        if (j == 0) {
            return VEResult.TER_INVALID_HANDLER;
        }
        int nativeUpdateScene = nativeUpdateScene(j, strArr, iArr, iArr2);
        if (nativeUpdateScene < 0) {
            return nativeUpdateScene;
        }
        this.mHostTrackIndex = nativeUpdateScene;
        return 0;
    }

    public int updateSceneFileOrder(VETimelineParams vETimelineParams) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeUpdateSceneFileOrder(j, vETimelineParams.videoFileIndex);
    }

    public int updateSenceTime(VETimelineParams vETimelineParams) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeUpdateSceneTime(j, vETimelineParams.enable, vETimelineParams.vTrimIn, vETimelineParams.vTrimOut, ROTATE_DEGREE.toIntArray(vETimelineParams.rotate), vETimelineParams.speed);
    }

    public int updateTrackClips(int i, int i2, String[] strArr) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeUpdateTrackClip(j, i, i2, strArr);
    }

    public int updateTrackFilter(int i, int i2, boolean z) {
        long j = this.mNative;
        return j == 0 ? VEResult.TER_INVALID_HANDLER : nativeUpdateTrackFilter(j, i, i2, z);
    }
}
