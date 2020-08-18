package com.android.camera.fragment.aiwatermark;

import android.view.View;
import com.android.camera.aiwatermark.data.WatermarkItem;

public interface OnItemClickListener {
    void onItemClick(WatermarkItem watermarkItem, int i, View view);
}
