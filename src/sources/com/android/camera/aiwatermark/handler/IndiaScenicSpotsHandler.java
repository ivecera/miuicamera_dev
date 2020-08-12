package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.Region;
import com.android.camera.aiwatermark.data.ScenicSpotsWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;
import java.util.HashMap;

public class IndiaScenicSpotsHandler extends ScenicSpotsHandler {
    private ScenicSpotsWatermark scenicSpotsWatermark = new ScenicSpotsWatermark();

    public IndiaScenicSpotsHandler(boolean z) {
        super(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ScenicSpotsHandler
    public HashMap<String, Region> getRegionMap() {
        return this.scenicSpotsWatermark.getRegionMap(2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ScenicSpotsHandler
    public ArrayList<WatermarkItem> getWatermarkList() {
        return this.scenicSpotsWatermark.getForAI();
    }
}
