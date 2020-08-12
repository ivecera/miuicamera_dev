package com.android.camera.module.interceptor;

public class ConfigChangeInterceptor extends BaseModuleInterceptor {
    public boolean consumeConfigChanged(int i) {
        return false;
    }

    @Override // com.android.camera.module.interceptor.BaseModuleInterceptor
    public int getPriority() {
        return 0;
    }

    @Override // com.android.camera.module.interceptor.BaseModuleInterceptor
    public int getScope() {
        return 0;
    }
}
