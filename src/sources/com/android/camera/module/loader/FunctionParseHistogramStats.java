package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CaptureResultParser;
import com.mi.config.b;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseHistogramStats implements Function<CaptureResult, int[]> {
    private int[] mStats = new int[0];
    private WeakReference<ModeProtocol.MainContentProtocol> mainContentProtocolWeakReference;

    public FunctionParseHistogramStats(ModeProtocol.MainContentProtocol mainContentProtocol) {
        this.mainContentProtocolWeakReference = new WeakReference<>(mainContentProtocol);
    }

    public int[] apply(CaptureResult captureResult) throws Exception {
        WeakReference<ModeProtocol.MainContentProtocol> weakReference = this.mainContentProtocolWeakReference;
        if (weakReference == null) {
            return this.mStats;
        }
        ModeProtocol.MainContentProtocol mainContentProtocol = weakReference.get();
        if (mainContentProtocol == null) {
            return this.mStats;
        }
        this.mStats = CaptureResultParser.getHistogramStats(captureResult);
        if (this.mStats != null) {
            int[] iArr = new int[256];
            for (int i = 0; i < 256; i++) {
                iArr[i] = this.mStats[b.isMTKPlatform() ? i : i * 3];
            }
            mainContentProtocol.updateHistogramStatsData(iArr);
        }
        return this.mStats;
    }
}
