package com.ss.android.ttve.nativePort;

import android.support.annotation.Keep;
import com.ss.android.vesdk.VEFrameAvailableListener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Keep
public class TEVideoUtilsCallback {
    private VEFrameAvailableListener listener;

    public static ByteBuffer allocateFrame(int i, int i2) {
        return ByteBuffer.allocateDirect(i * i2 * 4).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static boolean onFrameAvailable(Object obj, ByteBuffer byteBuffer, int i, int i2, int i3) {
        TEVideoUtilsCallback tEVideoUtilsCallback;
        VEFrameAvailableListener vEFrameAvailableListener;
        return (obj instanceof TEVideoUtilsCallback) && (tEVideoUtilsCallback = (TEVideoUtilsCallback) obj) != null && (vEFrameAvailableListener = tEVideoUtilsCallback.listener) != null && vEFrameAvailableListener.processFrame(byteBuffer, i, i2, i3);
    }

    public boolean onFrameAvailable(ByteBuffer byteBuffer, int i, int i2, int i3) {
        VEFrameAvailableListener vEFrameAvailableListener = this.listener;
        return vEFrameAvailableListener != null && vEFrameAvailableListener.processFrame(byteBuffer, i, i2, i3);
    }

    public void setListener(Object obj) {
        this.listener = (VEFrameAvailableListener) obj;
    }
}
