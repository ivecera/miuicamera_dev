package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.GeneralWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentGenWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 65536;
    private static final String TAG = "FragmentGenWatermark";

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        return new GeneralWatermark().getForManual();
    }
}
