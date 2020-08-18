package com.android.camera.fragment.manually.adapter;

import com.android.camera.data.DataRepository;

public abstract class MultiStopsZoomRatioResolver {
    protected final MultiStopsZoomSliderAdapter mAdapter;
    protected final float mZoomRatioMax = this.mAdapter.getZoomRatioMax();
    protected final float mZoomRatioMin = this.mAdapter.getZoomRatioMin();

    public MultiStopsZoomRatioResolver(MultiStopsZoomSliderAdapter multiStopsZoomSliderAdapter) {
        this.mAdapter = multiStopsZoomSliderAdapter;
    }

    public abstract boolean checkAndNotifyIfValueChanged(int i, float f2);

    public abstract int getCount();

    /* access modifiers changed from: protected */
    public String getCurrentValue() {
        return this.mAdapter.getCurrentValue();
    }

    /* access modifiers changed from: protected */
    public int getCurrentValueIndex() {
        return this.mAdapter.getCurrentValueIndex();
    }

    public abstract boolean isFirstStopPoint(int i);

    public boolean isFullRangeZoomSupported() {
        int currentCapturingMode = this.mAdapter.getCurrentCapturingMode();
        return (currentCapturingMode == 163 || currentCapturingMode == 165) && DataRepository.dataItemGlobal().isNormalIntent();
    }

    public abstract boolean isLastStopPoint(int i);

    public abstract boolean isStopPoint(int i);

    public abstract float mapPositionToZoomRatio(float f2);

    public abstract float mapZoomRatioToPosition(float f2);

    /* access modifiers changed from: protected */
    public void notifyDataChanged(int i, String str) {
        this.mAdapter.notifyDataChanged(i, str);
    }
}
