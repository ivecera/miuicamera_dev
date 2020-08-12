package com.android.camera2.vendortag.struct;

import android.hardware.camera2.marshal.MarshalRegistry;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.SlowMotionVideoConfiguration;

public class MarshalQueryableRegister {
    public static void preload() {
        MarshalRegistry.registerMarshalQueryable(new MarshalQueryableSuperNightExif());
        MarshalRegistry.registerMarshalQueryable(new AWBFrameControl.MarshalQueryableAWBFrameControl());
        MarshalRegistry.registerMarshalQueryable(new SlowMotionVideoConfiguration.MarshalQueryableSlowMotionVideoConfiguration());
        MarshalRegistry.registerMarshalQueryable(new AECFrameControl.MarshalQueryableAECFrameControl());
        MarshalRegistry.registerMarshalQueryable(new AFFrameControl.MarshalQueryableAFFrameControl());
        MarshalRegistry.registerMarshalQueryable(new MarshalQueryableASDScene());
    }
}
