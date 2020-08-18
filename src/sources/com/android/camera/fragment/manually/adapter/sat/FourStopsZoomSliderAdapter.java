package com.android.camera.fragment.manually.adapter.sat;

import android.content.Context;
import android.util.Spline;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver;
import com.android.camera.fragment.manually.adapter.MultiStopsZoomSliderAdapter;

public class FourStopsZoomSliderAdapter extends MultiStopsZoomSliderAdapter {
    private static final String TAG = "FourStopsZoomSliderAdapter";

    private static class _6_10_20_100 extends MultiStopsZoomRatioResolver {
        private static final int ENTRY_INDEX_1X = 4;
        private static final int ENTRY_INDEX_1X_WITHOUT_MIN = 0;
        private static final int ENTRY_INDEX_2X = 20;
        private static final int ENTRY_INDEX_2X_WITHOUT_MIN = 16;
        private static final int ENTRY_INDEX_MAX = 36;
        private static final int ENTRY_INDEX_MAX_WITHOUT_MIN = 32;
        private static final int ENTRY_INDEX_MIN = 0;
        private static final float[] RANGE_1X_TO_2X_ENTRY_INDEX_X = {4.0f, 5.0f, 20.0f};
        private static final float[] RANGE_1X_TO_2X_WITHOUT_MIN_ENTRY_INDEX_X = {0.0f, 1.0f, 16.0f};
        private static final float[] RANGE_1X_TO_2X_WITHOUT_MIN_ZOOM_RATIO_Y = {1.0f, 1.2f, 2.0f};
        private static final float[] RANGE_1X_TO_2X_ZOOM_RATIO_Y = {1.0f, 1.2f, 2.0f};
        private static final float[] RANGE_2X_TO_MAX_ENTRY_INDEX_X = {20.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 32.0f, 34.0f, 36.0f};
        private static final float[] RANGE_2X_TO_MAX_WITHOUT_MIN_ENTRY_INDEX_X = {16.0f, 18.0f, 20.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 32.0f};
        private static final float[] RANGE_2X_TO_MAX_WITHOUT_MIN_ZOOM_RATIO_Y = {2.0f, 2.2f, 3.7f, 5.1f, 5.8f, 6.6f, 7.0f, 8.0f, 10.0f};
        private static final float[] RANGE_2X_TO_MAX_ZOOM_RATIO_Y = {2.0f, 2.2f, 3.7f, 5.1f, 5.8f, 6.6f, 7.0f, 8.0f, 10.0f};
        private static final float[] RANGE_MIN_TO_1X_ENTRY_INDEX_X = {0.0f, 1.0f, 2.0f, 3.0f, 4.0f};
        private static final float[] RANGE_MIN_TO_1X_ZOOM_RATIO_Y = {0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        private Spline mRange1XTo2XEntryToZoomRatioSpline;
        private Spline mRange1XTo2XZoomRatioToEntrySpline;
        private Spline mRange2XToMaxEntryToZoomRatioSpline;
        private Spline mRange2XToMaxZoomRatioToEntrySpline;
        private Spline mRangeMinTo1XEntryToZoomRatioSpline;
        private Spline mRangeMinTo1XZoomRatioToEntrySpline;

        _6_10_20_100(MultiStopsZoomSliderAdapter multiStopsZoomSliderAdapter) {
            super(multiStopsZoomSliderAdapter);
            if (isFullRangeZoomSupported()) {
                this.mRangeMinTo1XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ENTRY_INDEX_X, RANGE_MIN_TO_1X_ZOOM_RATIO_Y);
                this.mRangeMinTo1XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ZOOM_RATIO_Y, RANGE_MIN_TO_1X_ENTRY_INDEX_X);
                this.mRange1XTo2XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_1X_TO_2X_ENTRY_INDEX_X, RANGE_1X_TO_2X_ZOOM_RATIO_Y);
                this.mRange1XTo2XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_1X_TO_2X_ZOOM_RATIO_Y, RANGE_1X_TO_2X_ENTRY_INDEX_X);
                this.mRange2XToMaxEntryToZoomRatioSpline = Spline.createMonotoneCubicSpline(RANGE_2X_TO_MAX_ENTRY_INDEX_X, RANGE_2X_TO_MAX_ZOOM_RATIO_Y);
                this.mRange2XToMaxZoomRatioToEntrySpline = Spline.createMonotoneCubicSpline(RANGE_2X_TO_MAX_ZOOM_RATIO_Y, RANGE_2X_TO_MAX_ENTRY_INDEX_X);
                return;
            }
            this.mRange1XTo2XEntryToZoomRatioSpline = Spline.createSpline(RANGE_1X_TO_2X_WITHOUT_MIN_ENTRY_INDEX_X, RANGE_1X_TO_2X_WITHOUT_MIN_ZOOM_RATIO_Y);
            this.mRange1XTo2XZoomRatioToEntrySpline = Spline.createSpline(RANGE_1X_TO_2X_WITHOUT_MIN_ZOOM_RATIO_Y, RANGE_1X_TO_2X_WITHOUT_MIN_ENTRY_INDEX_X);
            this.mRange2XToMaxEntryToZoomRatioSpline = Spline.createSpline(RANGE_2X_TO_MAX_WITHOUT_MIN_ENTRY_INDEX_X, RANGE_2X_TO_MAX_WITHOUT_MIN_ZOOM_RATIO_Y);
            this.mRange2XToMaxZoomRatioToEntrySpline = Spline.createSpline(RANGE_2X_TO_MAX_WITHOUT_MIN_ZOOM_RATIO_Y, RANGE_2X_TO_MAX_WITHOUT_MIN_ENTRY_INDEX_X);
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0073  */
        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean checkAndNotifyIfValueChanged(int i, float f2) {
            String str;
            boolean equals;
            boolean z = true;
            if (i == -1) {
                str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                equals = str.equals(((MultiStopsZoomRatioResolver) this).mAdapter.getCurrentValue());
            } else if (!isFullRangeZoomSupported()) {
                str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                equals = str.equals(getCurrentValue());
            } else if (i <= 4) {
                str = String.valueOf(HybridZoomingSystem.add(0.6f, ((float) i) * 0.1f));
                if (getCurrentValueIndex() == i) {
                    z = false;
                }
                if (z) {
                    notifyDataChanged(i, str);
                }
                return z;
            } else {
                str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                equals = str.equals(getCurrentValue());
            }
            z = true ^ equals;
            if (z) {
            }
            return z;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public int getCount() {
            return isFullRangeZoomSupported() ? 37 : 33;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isFirstStopPoint(int i) {
            return isFullRangeZoomSupported() ? i == 0 : i == 0;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isLastStopPoint(int i) {
            return isFullRangeZoomSupported() ? i == 36 : i == 32;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isStopPoint(int i) {
            return isFullRangeZoomSupported() ? i == 0 || i == 4 || i == 20 || i == 36 : i == 0 || i == 16 || i == 32;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public float mapPositionToZoomRatio(float f2) {
            if (isFullRangeZoomSupported()) {
                return (0.0f > f2 || f2 >= 4.0f) ? (4.0f > f2 || f2 >= 20.0f) ? this.mRange2XToMaxEntryToZoomRatioSpline.interpolate(f2) : this.mRange1XTo2XEntryToZoomRatioSpline.interpolate(f2) : this.mRangeMinTo1XEntryToZoomRatioSpline.interpolate(f2);
            }
            if (0.0f <= f2 && f2 < 16.0f) {
                return this.mRange1XTo2XEntryToZoomRatioSpline.interpolate(f2);
            }
            if (16.0f > f2 || f2 > 32.0f) {
                return 0.0f;
            }
            return this.mRange2XToMaxEntryToZoomRatioSpline.interpolate(f2);
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public float mapZoomRatioToPosition(float f2) {
            if (isFullRangeZoomSupported()) {
                return (0.6f > f2 || f2 >= 1.0f) ? (1.0f > f2 || f2 >= HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE) ? this.mRange2XToMaxZoomRatioToEntrySpline.interpolate(f2) : this.mRange1XTo2XZoomRatioToEntrySpline.interpolate(f2) : this.mRangeMinTo1XZoomRatioToEntrySpline.interpolate(f2);
            }
            if (1.0f <= f2 && f2 < HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE) {
                return this.mRange1XTo2XZoomRatioToEntrySpline.interpolate(f2);
            }
            if (HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE > f2 || f2 > 10.0f) {
                return 0.0f;
            }
            return this.mRange2XToMaxZoomRatioToEntrySpline.interpolate(f2);
        }
    }

    public FourStopsZoomSliderAdapter(Context context, int i, ZoomValueListener zoomValueListener) {
        super(context, i, zoomValueListener);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return ((MultiStopsZoomSliderAdapter) this).mZoomRatioResolver.getCount();
    }

    @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomSliderAdapter
    public MultiStopsZoomRatioResolver getZoomRatioResolver() {
        return new _6_10_20_100(this);
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public float mapPositionToZoomRatio(float f2) {
        return ((MultiStopsZoomSliderAdapter) this).mZoomRatioResolver.mapPositionToZoomRatio(f2);
    }

    @Override // com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter
    public float mapZoomRatioToPosition(float f2) {
        return ((MultiStopsZoomSliderAdapter) this).mZoomRatioResolver.mapZoomRatioToPosition(f2);
    }
}
