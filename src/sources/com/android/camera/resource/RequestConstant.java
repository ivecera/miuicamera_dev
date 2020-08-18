package com.android.camera.resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RequestConstant {
    protected static final int ERROR_IO = 3;
    protected static final int ERROR_NET = 0;
    protected static final int ERROR_PARSE = 2;
    protected static final int ERROR_SERVER = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
    }
}
