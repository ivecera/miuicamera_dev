package com.android.camera.aiwatermark.data;

import java.util.ArrayList;

public class GeneralWatermark extends AbstractWatermarkData {
    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForAI() {
        return getWatermarkByType(10);
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForManual() {
        return getWatermarkByType(0);
    }
}
