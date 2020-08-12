package com.android.camera2;

import android.hardware.camera2.CaptureRequest;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;
import java.util.HashMap;
import java.util.Map;

public class CaptureSessionConfigurations {
    private final CameraCapabilities mCapabilities;
    private final Map<Object, Object> mSessionParameters = new HashMap();

    public CaptureSessionConfigurations(CameraCapabilities cameraCapabilities) {
        this.mCapabilities = cameraCapabilities;
    }

    static /* synthetic */ void a(CaptureRequest.Builder builder, Object obj, Object obj2) {
        if (obj instanceof VendorTag) {
            VendorTagHelper.setValueSafely(builder, (VendorTag) obj, obj2);
        } else if (obj instanceof CaptureRequest.Key) {
            builder.set((CaptureRequest.Key) obj, obj2);
        }
    }

    public synchronized void apply(CaptureRequest.Builder builder) {
        this.mSessionParameters.forEach(new b(builder));
    }

    public synchronized <T> T get(CaptureRequest.Key<T> key) {
        return this.mSessionParameters.get(key);
    }

    public synchronized <T> T get(VendorTag<CaptureRequest.Key<T>> vendorTag) {
        return this.mSessionParameters.get(vendorTag);
    }

    public synchronized void reset() {
        this.mSessionParameters.clear();
    }

    public synchronized <T> void set(CaptureRequest.Key<T> key, T t) {
        if (key == null || t == null) {
            throw new IllegalArgumentException("Both key and value are must not be null");
        } else if (this.mCapabilities != null && this.mCapabilities.isTagDefined(key.getName())) {
            this.mSessionParameters.put(key, t);
        }
    }

    public synchronized <T> void set(VendorTag<CaptureRequest.Key<T>> vendorTag, T t) {
        if (vendorTag == null || t == null) {
            throw new IllegalArgumentException("Both key and value are must not be null");
        } else if (this.mCapabilities != null && this.mCapabilities.isTagDefined(vendorTag.getName())) {
            this.mSessionParameters.put(vendorTag, t);
        }
    }
}
