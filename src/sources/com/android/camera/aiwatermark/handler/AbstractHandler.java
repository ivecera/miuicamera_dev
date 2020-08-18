package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.module.BaseModule;

public abstract class AbstractHandler {
    private boolean mIsConsume;
    protected BaseModule mModule = null;
    private AbstractHandler mNextHandler;

    public AbstractHandler(boolean z) {
        this.mIsConsume = z;
    }

    private WatermarkItem nextHandlerRequest() {
        AbstractHandler abstractHandler = this.mNextHandler;
        if (abstractHandler != null) {
            return abstractHandler.handleRequest();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract WatermarkItem findWatermark();

    public WatermarkItem handleRequest() {
        if (!this.mIsConsume) {
            return nextHandlerRequest();
        }
        WatermarkItem findWatermark = findWatermark();
        return findWatermark != null ? findWatermark : nextHandlerRequest();
    }

    public void setNextHandler(AbstractHandler abstractHandler) {
        this.mNextHandler = abstractHandler;
    }
}
