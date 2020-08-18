package com.android.camera2.vendortag;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.util.Log;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;

public class VendorTagHelper {
    private static final int RETHROW = 51966;
    private static final int SWALLOW = 57005;
    private static final String TAG = "VendorTagHelper";
    private static final int WARNING = 47806;
    private static int sPreferredAction = -1;

    private static int getPreferredAction() {
        if (sPreferredAction == -1) {
            sPreferredAction = (Log.isLoggable("VENDOR_TAG_NFE_RETHROW", 3) || !CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.rethrow_vendor_tag_not_found_exception)) ? WARNING : RETHROW;
        }
        return sPreferredAction;
    }

    public static <T> T getValue(CameraCharacteristics cameraCharacteristics, VendorTag<CameraCharacteristics.Key<T>> vendorTag) {
        return tryGetValue(cameraCharacteristics, vendorTag, getPreferredAction());
    }

    public static <T> T getValue(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag) {
        return tryGetValue(builder, vendorTag, getPreferredAction());
    }

    public static <T> T getValue(CaptureRequest captureRequest, VendorTag<CaptureRequest.Key<T>> vendorTag) {
        return tryGetValue(captureRequest, vendorTag, getPreferredAction());
    }

    public static <T> T getValue(CaptureResult captureResult, VendorTag<CaptureResult.Key<T>> vendorTag) {
        return tryGetValue(captureResult, vendorTag, getPreferredAction());
    }

    public static <T> T getValueQuietly(CameraCharacteristics cameraCharacteristics, VendorTag<CameraCharacteristics.Key<T>> vendorTag) {
        return tryGetValue(cameraCharacteristics, vendorTag, (int) SWALLOW);
    }

    public static <T> T getValueQuietly(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag, T t) {
        return tryGetValue(builder, vendorTag, (int) SWALLOW);
    }

    public static <T> T getValueQuietly(CaptureResult captureResult, VendorTag<CaptureResult.Key<T>> vendorTag) {
        return tryGetValue(captureResult, vendorTag, (int) SWALLOW);
    }

    public static <T> T getValueSafely(CameraCharacteristics cameraCharacteristics, VendorTag<CameraCharacteristics.Key<T>> vendorTag) {
        return tryGetValue(cameraCharacteristics, vendorTag, (int) WARNING);
    }

    public static <T> T getValueSafely(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag) {
        return tryGetValue(builder, vendorTag, (int) WARNING);
    }

    public static <T> T getValueSafely(CaptureRequest captureRequest, VendorTag<CaptureRequest.Key<T>> vendorTag) {
        return tryGetValue(captureRequest, vendorTag, (int) WARNING);
    }

    public static <T> T getValueSafely(CaptureResult captureResult, VendorTag<CaptureResult.Key<T>> vendorTag) {
        return tryGetValue(captureResult, vendorTag, (int) WARNING);
    }

    public static <T> void setValue(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag, T t) {
        trySetValue(builder, vendorTag, t, getPreferredAction());
    }

    public static <T> void setValueQuietly(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag, T t) {
        trySetValue(builder, vendorTag, t, SWALLOW);
    }

    public static <T> void setValueSafely(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag, T t) {
        trySetValue(builder, vendorTag, t, WARNING);
    }

    private static <T> T tryGetValue(CameraCharacteristics cameraCharacteristics, VendorTag<CameraCharacteristics.Key<T>> vendorTag, int i) {
        if (cameraCharacteristics == null || vendorTag == null || vendorTag.getKey() == null) {
            Log.w(TAG, "caution: failed to try get value from camera characteristics: <NULL>");
            return null;
        }
        try {
            return cameraCharacteristics.get(vendorTag.getKey());
        } catch (Exception e2) {
            if (i != RETHROW) {
                if (i == WARNING) {
                    Log.w(TAG, "caution: failed to try get value from camera characteristics: <VTNF>, " + vendorTag.getName());
                }
                return null;
            }
            throw new VendorTagNotFoundException(e2);
        }
    }

    private static <T> T tryGetValue(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag, int i) {
        if (builder == null || vendorTag == null || vendorTag.getKey() == null) {
            Log.w(TAG, "caution: failed to try get value from capture request: <NULL>");
            return null;
        }
        try {
            return builder.get(vendorTag.getKey());
        } catch (Exception e2) {
            if (i != RETHROW) {
                if (i == WARNING) {
                    Log.w(TAG, "caution: failed to try get value from capture request: <VTNF>, " + vendorTag.getName());
                }
                return null;
            }
            throw new VendorTagNotFoundException(e2);
        }
    }

    private static <T> T tryGetValue(CaptureRequest captureRequest, VendorTag<CaptureRequest.Key<T>> vendorTag, int i) {
        if (captureRequest == null || vendorTag == null || vendorTag.getKey() == null) {
            Log.w(TAG, "caution: failed to try get value from capture request: <NULL>");
            return null;
        }
        try {
            return captureRequest.get(vendorTag.getKey());
        } catch (Exception e2) {
            if (i != RETHROW) {
                if (i == WARNING) {
                    Log.w(TAG, "caution: failed to try get value from capture request: <VTNF>, " + vendorTag.getName());
                }
                return null;
            }
            throw new VendorTagNotFoundException(e2);
        }
    }

    private static <T> T tryGetValue(CaptureResult captureResult, VendorTag<CaptureResult.Key<T>> vendorTag, int i) {
        if (captureResult == null || vendorTag == null || vendorTag.getKey() == null) {
            Log.w(TAG, "caution: failed to try get value from capture result: <NULL>");
            return null;
        }
        try {
            return captureResult.get(vendorTag.getKey());
        } catch (Exception e2) {
            if (i != RETHROW) {
                if (i == WARNING) {
                    Log.w(TAG, "caution: failed to try get value from capture result: <VTNF>, " + vendorTag.getName());
                }
                return null;
            }
            throw new VendorTagNotFoundException(e2);
        }
    }

    private static <T> void trySetValue(CaptureRequest.Builder builder, VendorTag<CaptureRequest.Key<T>> vendorTag, T t, int i) {
        if (builder == null || vendorTag == null || vendorTag.getKey() == null) {
            Log.w(TAG, "caution: failed to update capture request: <NULL>");
            return;
        }
        try {
            builder.set(vendorTag.getKey(), t);
        } catch (Exception e2) {
            if (i == RETHROW) {
                throw new VendorTagNotFoundException(e2);
            } else if (i == WARNING) {
                Log.w(TAG, "caution: failed to update capture request: <VTNF>, " + vendorTag.getName());
            }
        }
    }
}
