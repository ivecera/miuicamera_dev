package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.ScenicSpotsWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentSpotsWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 65537;

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        return new ScenicSpotsWatermark().getForManual();
    }
}
