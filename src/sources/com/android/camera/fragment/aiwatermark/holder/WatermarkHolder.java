package com.android.camera.fragment.aiwatermark.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.aiwatermark.data.WatermarkItem;

public class WatermarkHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "WatermarkHolder";
    private ImageView mImageView = null;
    private int mIndex = -1;
    private ImageView mSelectedIndicator = null;

    public WatermarkHolder(@NonNull View view) {
        super(view);
        this.mImageView = (ImageView) view.findViewById(R.id.watermark_item_image);
        this.mSelectedIndicator = (ImageView) view.findViewById(R.id.watermark_item_selected_indicator);
    }

    public void bindHolder(int i, WatermarkItem watermarkItem) {
        this.mIndex = i;
        this.mImageView.setImageResource(watermarkItem.getResRvItem());
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void updateSelectItem(int i) {
        this.mSelectedIndicator.setVisibility(i);
    }
}
