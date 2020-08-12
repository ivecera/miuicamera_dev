package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.CityWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentCityWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 65533;

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        return new CityWatermark().getForManual();
    }
}
