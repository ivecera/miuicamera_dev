package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.FestivalWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentFestivalWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 65531;

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        return new FestivalWatermark().getForManual();
    }
}
