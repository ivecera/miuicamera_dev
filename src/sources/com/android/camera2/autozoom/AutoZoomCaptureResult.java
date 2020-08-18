package com.android.camera2.autozoom;

import android.hardware.camera2.CaptureResult;
import android.util.Log;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;

public class AutoZoomCaptureResult {
    private static final String TAG = "AutoZoomCaptureResult";
    private int[] mAutoZoomActiveObjects;
    private boolean mAutoZoomAvailable = true;
    private float[] mAutoZoomBounds;
    private float[] mAutoZoomObjectBoundsStabilized;
    private float[] mAutoZoomObjectBoundsZoomed;
    private int[] mAutoZoomPausedObjects;
    private int[] mAutoZoomSelectedObjects;
    private int mAutoZoomStatus;
    private float[] mAutoZoomTargetBoundsStabilized;
    private float[] mAutoZoomTargetBoundsZoomed;

    public AutoZoomCaptureResult(CaptureResult captureResult) {
        if (this.mAutoZoomAvailable) {
            try {
                Integer num = (Integer) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_STATUS, captureResult);
                this.mAutoZoomStatus = num == null ? 0 : num.intValue();
                String str = TAG;
                Log.i(str, "autozoom status is " + this.mAutoZoomStatus);
                this.mAutoZoomBounds = (float[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_BOUNDS, captureResult);
                String str2 = TAG;
                Log.i(str2, "autoZoomBound is " + this.mAutoZoomBounds);
                if (this.mAutoZoomBounds != null) {
                    String str3 = TAG;
                    Log.i(str3, "bounds left " + this.mAutoZoomBounds[0] + " top " + this.mAutoZoomBounds[1] + " right " + this.mAutoZoomBounds[2] + " bottom " + this.mAutoZoomBounds[3]);
                }
                this.mAutoZoomTargetBoundsStabilized = (float[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_TARGET_BOUNDS_STABILIZED, captureResult);
                this.mAutoZoomTargetBoundsZoomed = (float[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_TARGET_BOUNDS_ZOOMED, captureResult);
                this.mAutoZoomActiveObjects = (int[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_ACTIVE_OBJECTS, captureResult);
                this.mAutoZoomSelectedObjects = (int[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_SELECTED_OBJECTS, captureResult);
                this.mAutoZoomPausedObjects = (int[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_PAUSED_OBJECTS, captureResult);
                this.mAutoZoomObjectBoundsStabilized = (float[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_OBJECT_BOUNDS_STABILIZED, captureResult);
                this.mAutoZoomObjectBoundsZoomed = (float[]) getAutoZoomResult(CaptureResultVendorTags.AUTOZOOM_OBJECT_BOUNDS_ZOOMED, captureResult);
            } catch (Exception e2) {
                this.mAutoZoomAvailable = false;
                Log.e(TAG, "Could not read AutoZoom tags from CaptureResult. This instance of Device will not try to read AutoZoom tags from subsequent results", e2);
            }
        }
    }

    private <T> T getAutoZoomResult(VendorTag<CaptureResult.Key<T>> vendorTag, CaptureResult captureResult) {
        return VendorTagHelper.getValueSafely(captureResult, vendorTag);
    }

    public int[] getAutoZoomActiveObjects() {
        return this.mAutoZoomActiveObjects;
    }

    public float[] getAutoZoomBounds() {
        return this.mAutoZoomBounds;
    }

    public float[] getAutoZoomObjectBoundsStabilized() {
        return this.mAutoZoomObjectBoundsStabilized;
    }

    public float[] getAutoZoomObjectBoundsZoomed() {
        return this.mAutoZoomObjectBoundsZoomed;
    }

    public int[] getAutoZoomPausedObjects() {
        return this.mAutoZoomPausedObjects;
    }

    public int[] getAutoZoomSelectedObjects() {
        return this.mAutoZoomSelectedObjects;
    }

    public int getAutoZoomStatus() {
        return this.mAutoZoomStatus;
    }

    public float[] getAutoZoomTargetBoundsStabilized() {
        return this.mAutoZoomTargetBoundsStabilized;
    }

    public float[] getAutoZoomTargetBoundsZoomed() {
        return this.mAutoZoomTargetBoundsZoomed;
    }
}
