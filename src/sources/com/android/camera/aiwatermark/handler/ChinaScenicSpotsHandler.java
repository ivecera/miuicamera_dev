package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.Region;
import com.android.camera.aiwatermark.data.ScenicSpotsWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;
import java.util.HashMap;

public class ChinaScenicSpotsHandler extends ScenicSpotsHandler {
    private ScenicSpotsWatermark mWatermark;

    public ChinaScenicSpotsHandler(boolean z) {
        super(z);
        this.mWatermark = null;
        this.mWatermark = new ScenicSpotsWatermark();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ScenicSpotsHandler
    public HashMap<String, Region> getRegionMap() {
        return this.mWatermark.getRegionMap(1);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ScenicSpotsHandler
    public ArrayList<WatermarkItem> getWatermarkList() {
        return this.mWatermark.getForAI();
    }
}
