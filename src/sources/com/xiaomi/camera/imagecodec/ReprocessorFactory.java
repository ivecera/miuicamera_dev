package com.xiaomi.camera.imagecodec;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor;
import com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor;
import com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor;

public class ReprocessorFactory {
    private static final String TAG = "ReprocessorFactory";
    private static ReprocessorType sDefaultReprocessorType;

    /* renamed from: com.xiaomi.camera.imagecodec.ReprocessorFactory$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType = new int[ReprocessorType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[ReprocessorType.HARDWARE_CODEC.ordinal()] = 1;
            $SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[ReprocessorType.SOFTWARE_CODEC.ordinal()] = 2;
        }
    }

    public enum ReprocessorType {
        VIRTUAL_CAMERA,
        HARDWARE_CODEC,
        SOFTWARE_CODEC
    }

    private ReprocessorFactory() {
    }

    public static synchronized Reprocessor getDefaultReprocessor() {
        Reprocessor reprocessor;
        synchronized (ReprocessorFactory.class) {
            if (sDefaultReprocessorType != null) {
                reprocessor = getReprocessor(sDefaultReprocessorType);
            } else {
                throw new IllegalStateException("The default reprocessingType is not defined yet, make sure #init(Context) has been called");
            }
        }
        return reprocessor;
    }

    public static synchronized Reprocessor getReprocessor(ReprocessorType reprocessorType) {
        synchronized (ReprocessorFactory.class) {
            if (reprocessorType != null) {
                int i = AnonymousClass1.$SwitchMap$com$xiaomi$camera$imagecodec$ReprocessorFactory$ReprocessorType[reprocessorType.ordinal()];
                if (i == 1) {
                    Reprocessor reprocessor = HardwareCodecReprocessor.sInstance.get();
                    return reprocessor;
                } else if (i != 2) {
                    Reprocessor reprocessor2 = VirtualCameraReprocessor.sInstance.get();
                    return reprocessor2;
                } else {
                    Reprocessor reprocessor3 = SoftwareCodecReprocessor.sInstance.get();
                    return reprocessor3;
                }
            } else {
                throw new IllegalArgumentException("The given reprocessingType must not be null");
            }
        }
    }

    public static synchronized void init(Context context) {
        ReprocessorType reprocessorType;
        synchronized (ReprocessorFactory.class) {
            ReprocessorType reprocessorType2 = ReprocessorType.VIRTUAL_CAMERA;
            try {
                reprocessorType = ReprocessorType.values()[context.getResources().getInteger(R.integer.preferred_image_reprocessor_type)];
            } catch (Resources.NotFoundException | ArrayIndexOutOfBoundsException e2) {
                Log.d(TAG, "Failed to find the preferred reprocessor, defaults to use VIRTUAL_CAMERA instead", e2);
            }
            Log.d(TAG, "reprocessorType = " + reprocessorType);
            if (sDefaultReprocessorType == null) {
                sDefaultReprocessorType = reprocessorType;
            } else if (sDefaultReprocessorType != reprocessorType) {
                throw new IllegalStateException("The type of preferred reprocessor is not allowed to be changed.");
            }
        }
    }
}
