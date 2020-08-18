package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.ASDWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentASDWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 65532;

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        return new ASDWatermark().getForManual();
    }
}
