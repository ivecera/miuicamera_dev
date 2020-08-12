package com.android.camera.aiwatermark.data;

import com.android.camera.aiwatermark.util.WatermarkConstant;
import java.util.ArrayList;

public class FestivalWatermark extends AbstractWatermarkData {
    private ArrayList<WatermarkItem> getChinaTraditionWM() {
        ArrayList<WatermarkItem> arrayList = new ArrayList<>();
        arrayList.add(new WatermarkItem("0101", 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.DRAGON_BOAT_FESTIVAL, 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.MID_AUTUMN_FESTIVAL, 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.CHINESE_EVE, 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.CHINESE_VALENTINE_DAY, 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.LABA_FESTIVAL, 2, 1, 12));
        return arrayList;
    }

    private ArrayList<WatermarkItem> getInternationalFestivalWM() {
        ArrayList<WatermarkItem> arrayList = new ArrayList<>();
        arrayList.add(new WatermarkItem("0101", 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.VALENTINE_DAY, 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.CHILDREN_DAY, 2, 1, 12));
        arrayList.add(new WatermarkItem(WatermarkConstant.CHRISTMAS_DAY, 2, 1, 12));
        return arrayList;
    }

    public ArrayList<WatermarkItem> getFestivalWatermark(int i) {
        return i != 0 ? i != 1 ? new ArrayList<>() : getChinaTraditionWM() : getInternationalFestivalWM();
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForAI() {
        return getWatermarkByType(6);
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForManual() {
        return getWatermarkByType(2);
    }
}
