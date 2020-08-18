package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;

public class IndiaCityHandler extends CityHandler {
    public IndiaCityHandler(boolean z) {
        super(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.CityHandler, com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        return super.findWatermark();
    }

    @Override // com.android.camera.aiwatermark.handler.CityHandler
    public String getAddress(double d2, double d3) {
        return null;
    }

    @Override // com.android.camera.aiwatermark.handler.CityHandler
    public ArrayList<WatermarkItem> getCityWatermarkList() {
        return new ArrayList<>();
    }
}
