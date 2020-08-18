package com.android.camera.module.interceptor;

public class CaptureInterceptor extends BaseModuleInterceptor {
    @Override // com.android.camera.module.interceptor.BaseModuleInterceptor
    public int getPriority() {
        return 0;
    }

    @Override // com.android.camera.module.interceptor.BaseModuleInterceptor
    public int getScope() {
        return 0;
    }
}
