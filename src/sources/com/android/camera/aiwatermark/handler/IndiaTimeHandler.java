package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.TimeWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;

public class IndiaTimeHandler extends TimeHandler {
    private TimeWatermark mTimeWatermark = new TimeWatermark();
    private ArrayList<WatermarkItem> mWatermarkItems = new ArrayList<>();

    public IndiaTimeHandler(boolean z) {
        super(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler, com.android.camera.aiwatermark.handler.TimeHandler
    public WatermarkItem findWatermark() {
        if (this.mWatermarkItems.isEmpty()) {
            this.mWatermarkItems = this.mTimeWatermark.getForAI();
        }
        return this.mWatermarkItems.get(0);
    }
}
