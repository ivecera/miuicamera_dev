package com.android.camera.module.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class BaseModuleInterceptor {
    public static final int PRIORITY_BLOCK = 1;
    public static final int PRIORITY_COMMON = 0;
    public static final int PRIORITY_FIRST = 2;
    public static final int SCOPE_CAPTURE_FINISH = 3;
    public static final int SCOPE_CAPTURE_PREPARE = 1;
    public static final int SCOPE_CAPTURE_START = 2;
    public static final int SCOPE_CONFIG_CHANGE = 5;
    public static final int SCOPE_SAVE_FINISH = 4;

    @Retention(RetentionPolicy.SOURCE)
    @interface InterceptorPriority {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface InterceptorScope {
    }

    public boolean asBlocker() {
        return getPriority() == 1;
    }

    public abstract int getPriority();

    public abstract int getScope();
}
