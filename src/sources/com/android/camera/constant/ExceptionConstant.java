package com.android.camera.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ExceptionConstant {
    public static final int EXCEPTION_CAMERA_ACTIVITY_FINISHING = 235;
    public static final int EXCEPTION_CAMERA_ACTIVITY_NULL = 234;
    public static final int EXCEPTION_CAMERA_DEVICE_NOT_RESPONDING = 238;
    public static final int EXCEPTION_CAMERA_DISABLED = 227;
    public static final int EXCEPTION_CAMERA_GET_DEVICE_NULL = 230;
    public static final int EXCEPTION_CAMERA_GET_PARAMETERS_NULL = 231;
    public static final int EXCEPTION_CAMERA_HANDLER_THREAD_STUCK = 236;
    public static final int EXCEPTION_CAMERA_HARDWARE = 226;
    public static final int EXCEPTION_CAMERA_MODULE_CREATE = 237;
    public static final int EXCEPTION_CAMERA_OPEN_CANCEL = 225;
    public static final int EXCEPTION_CAMERA_PERMISSION_BLOCK = 229;
    public static final int EXCEPTION_CAMERA_RUNTIME = 228;
    public static final int EXCEPTION_CAMERA_UNKNOWN_CAMERA_ID = 233;
    public static final int EXCEPTION_NO_ERROR = 224;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Exceptions {
    }

    public static final int transFromCamera2Error(int i) {
        if (i == 1 || i == 2) {
            return 230;
        }
        if (i == 3) {
            return 227;
        }
        if (i == 4 || i != 5) {
        }
        return 230;
    }
}
