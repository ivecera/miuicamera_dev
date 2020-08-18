package com.android.camera.fragment.manually.adapter.sat;

import android.content.Context;
import android.util.Spline;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver;
import com.android.camera.fragment.manually.adapter.MultiStopsZoomSliderAdapter;
import com.mi.config.b;

public class FiveStopsZoomSliderAdapter extends MultiStopsZoomSliderAdapter {
    private static final String TAG = "FiveStopsZoomSliderAdapter";

    private static class _6_10_30_100_300 extends MultiStopsZoomRatioResolver {
        private static final int ENTRY_COUNT_10X_TO_MAX = 11;
        private static int ENTRY_COUNT_MIN_TO_MAX = 35;
        private static int ENTRY_COUNT_TOTAL = ENTRY_COUNT_MIN_TO_MAX;
        private static final int ENTRY_INDEX_10X = 24;
        private static final int ENTRY_INDEX_10X_RANGE_10X = 30;
        private static final int ENTRY_INDEX_1X = 4;
        private static final int ENTRY_INDEX_1X_RANGE_10X = 0;
        private static final int ENTRY_INDEX_3X = 14;
        private static final int ENTRY_INDEX_3X_RANGE_10X = 10;
        private static int ENTRY_INDEX_LAST = 34;
        private static int ENTRY_INDEX_MAX = ENTRY_INDEX_LAST;
        private static final int ENTRY_INDEX_MIN = 0;
        private static final float[] RANGE_10X_TO_MAX_ENTRY_INDEX_X = {24.0f, 26.0f, 28.0f, 30.0f, 32.0f, 34.0f};
        private static final float[] RANGE_10X_TO_MAX_ZOOM_RATIO_Y = {10.0f, 14.0f, 18.0f, 22.0f, 26.0f, 30.0f};
        private static final float[] RANGE_1X_TO_3X_ENTRY_INDEX_X = {4.0f, 14.0f};
        private static final float[] RANGE_1X_TO_3X_FOR_10X_ENTRY_INDEX_X = {0.0f, 2.0f, 4.0f, 6.0f, 8.0f, 10.0f};
        private static final float[] RANGE_1X_TO_3X_FOR_10X_ZOOM_RATIO_Y = {1.0f, 1.4f, 1.8f, 2.2f, 2.6f, 3.0f};
        private static final float[] RANGE_1X_TO_3X_ZOOM_RATIO_Y = {1.0f, 3.0f};
        private static final float[] RANGE_3X_TO_10X_ENTRY_INDEX_X = {14.0f, 24.0f};
        private static final float[] RANGE_3X_TO_10X_FOR_10X_ENTRY_INDEX_X = {10.0f, 12.0f, 14.0f, 16.0f, 18.0f, 20.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f};
        private static final float[] RANGE_3X_TO_10X_FOR_10X_ZOOM_RATIO_Y = {3.0f, 3.7f, 4.4f, 5.1f, 5.8f, 6.5f, 7.2f, 7.9f, 8.6f, 9.3f, 10.0f};
        private static final float[] RANGE_3X_TO_10X_ZOOM_RATIO_Y = {3.0f, 10.0f};
        private static final float[] RANGE_MIN_TO_1X_ENTRY_INDEX_X = {0.0f, 1.0f, 2.0f, 3.0f, 4.0f};
        private static final float[] RANGE_MIN_TO_1X_ZOOM_RATIO_Y = {0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        private Spline mRange10XToMaxEntryToZoomRatioSpline;
        private Spline mRange10XToMaxZoomRatioToEntrySpline;
        private Spline mRange1XTo3XEntryToZoomRatioSpline;
        private Spline mRange1XTo3XFor10XEntryToZoomRatioSpline;
        private Spline mRange1XTo3XFor10XZoomRatioToEntrySpline;
        private Spline mRange1XTo3XZoomRatioToEntrySpline;
        private Spline mRange3XTo10XEntryToZoomRatioSpline;
        private Spline mRange3XTo10XFor10XEntryToZoomRatioSpline;
        private Spline mRange3XTo10XFor10XZoomRatioToEntrySpline;
        private Spline mRange3XTo10XZoomRatioToEntrySpline;
        private Spline mRangeMinTo1XEntryToZoomRatioSpline;
        private Spline mRangeMinTo1XZoomRatioToEntrySpline;

        _6_10_30_100_300(MultiStopsZoomSliderAdapter multiStopsZoomSliderAdapter) {
            super(multiStopsZoomSliderAdapter);
            if (isFullRangeZoomSupported()) {
                this.mRangeMinTo1XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ENTRY_INDEX_X, RANGE_MIN_TO_1X_ZOOM_RATIO_Y);
                this.mRangeMinTo1XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ZOOM_RATIO_Y, RANGE_MIN_TO_1X_ENTRY_INDEX_X);
                this.mRange1XTo3XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_1X_TO_3X_ENTRY_INDEX_X, RANGE_1X_TO_3X_ZOOM_RATIO_Y);
                this.mRange1XTo3XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_1X_TO_3X_ZOOM_RATIO_Y, RANGE_1X_TO_3X_ENTRY_INDEX_X);
                this.mRange3XTo10XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_3X_TO_10X_ENTRY_INDEX_X, RANGE_3X_TO_10X_ZOOM_RATIO_Y);
                this.mRange3XTo10XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_3X_TO_10X_ZOOM_RATIO_Y, RANGE_3X_TO_10X_ENTRY_INDEX_X);
                float[] entryX = getEntryX();
                float[] zoomRatioY = getZoomRatioY(((MultiStopsZoomRatioResolver) this).mZoomRatioMax);
                this.mRange10XToMaxEntryToZoomRatioSpline = Spline.createMonotoneCubicSpline(entryX, zoomRatioY);
                this.mRange10XToMaxZoomRatioToEntrySpline = Spline.createMonotoneCubicSpline(zoomRatioY, entryX);
                ENTRY_COUNT_TOTAL = ENTRY_COUNT_MIN_TO_MAX;
                ENTRY_INDEX_MAX = ENTRY_INDEX_LAST;
                return;
            }
            this.mRange1XTo3XFor10XEntryToZoomRatioSpline = Spline.createSpline(RANGE_1X_TO_3X_FOR_10X_ENTRY_INDEX_X, RANGE_1X_TO_3X_FOR_10X_ZOOM_RATIO_Y);
            this.mRange1XTo3XFor10XZoomRatioToEntrySpline = Spline.createSpline(RANGE_1X_TO_3X_FOR_10X_ZOOM_RATIO_Y, RANGE_1X_TO_3X_FOR_10X_ENTRY_INDEX_X);
            this.mRange3XTo10XFor10XEntryToZoomRatioSpline = Spline.createSpline(RANGE_3X_TO_10X_FOR_10X_ENTRY_INDEX_X, RANGE_3X_TO_10X_FOR_10X_ZOOM_RATIO_Y);
            this.mRange3XTo10XFor10XZoomRatioToEntrySpline = Spline.createSpline(RANGE_3X_TO_10X_FOR_10X_ZOOM_RATIO_Y, RANGE_3X_TO_10X_FOR_10X_ENTRY_INDEX_X);
            ENTRY_COUNT_TOTAL = 31;
            ENTRY_INDEX_MAX = 30;
        }

        private static float[] getEntryX() {
            float[] fArr = RANGE_10X_TO_MAX_ENTRY_INDEX_X;
            float f2 = (float) 24;
            int i = (int) ((fArr[fArr.length - 1] - f2) + 1.0f);
            float[] fArr2 = new float[fArr.length];
            for (int i2 = 0; i2 < fArr.length; i2++) {
                if (fArr[i2] <= f2) {
                    fArr2[i2] = fArr[i2];
                } else {
                    fArr2[i2] = (((fArr[i2] - f2) / ((float) (i - 1))) * 10.0f) + f2;
                }
            }
            return fArr2;
        }

        private static float[] getZoomRatioY(float f2) {
            float[] fArr = RANGE_10X_TO_MAX_ZOOM_RATIO_Y;
            int i = (int) fArr[fArr.length - 1];
            float[] fArr2 = new float[fArr.length];
            for (int i2 = 0; i2 < fArr.length; i2++) {
                if (fArr[i2] <= 10.0f) {
                    fArr2[i2] = fArr[i2];
                } else {
                    fArr2[i2] = (((fArr[i2] - 10.0f) / (((float) i) - 10.0f)) * (f2 - 10.0f)) + 10.0f;
                }
            }
            return fArr2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0060, code lost:
            if (getCurrentValueIndex() != r7) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x007a, code lost:
            if (getCurrentValueIndex() != r7) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a7, code lost:
            if (getCurrentValueIndex() != r7) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c1, code lost:
            if (getCurrentValueIndex() != r7) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0045, code lost:
            if (getCurrentValueIndex() != r7) goto L_0x00c5;
         */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00c7  */
        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean checkAndNotifyIfValueChanged(int i, float f2) {
            String str;
            boolean equals;
            boolean z = true;
            if (i == -1) {
                str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                equals = str.equals(((MultiStopsZoomRatioResolver) this).mAdapter.getCurrentValue());
            } else {
                if (isFullRangeZoomSupported()) {
                    if (i <= 4) {
                        str = String.valueOf(HybridZoomingSystem.add(0.6f, ((float) i) * 0.1f));
                    } else if (i <= 14) {
                        str = String.valueOf(HybridZoomingSystem.add(1.0f, ((float) (i - 4)) * 0.2f));
                    } else if (i <= 24) {
                        str = String.valueOf(HybridZoomingSystem.add(3.0f, ((float) (i - 14)) * 0.7f));
                    } else {
                        str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                        equals = str.equals(getCurrentValue());
                    }
                } else if (i <= 10) {
                    str = String.valueOf(HybridZoomingSystem.add(1.0f, ((float) i) * 0.2f));
                } else {
                    if (i <= 30) {
                        str = String.valueOf(HybridZoomingSystem.add(3.0f, ((float) (i - 10)) * 0.35f));
                    } else {
                        str = null;
                    }
                    if (z) {
                        notifyDataChanged(i, str);
                    }
                    return z;
                }
                z = false;
                if (z) {
                }
                return z;
            }
            z = true ^ equals;
            if (z) {
            }
            return z;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public int getCount() {
            return ENTRY_COUNT_TOTAL;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isFirstStopPoint(int i) {
            return i == 0;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isLastStopPoint(int i) {
            return i == ENTRY_INDEX_MAX;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isStopPoint(int i) {
            return isFullRangeZoomSupported() ? i == 0 || i == 4 || i == 14 || i == 24 || i == ENTRY_INDEX_MAX : i == 0 || i == 10 || i == 30;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public float mapPositionToZoomRatio(float f2) {
            if (isFullRangeZoomSupported()) {
                return (0.0f > f2 || f2 >= 4.0f) ? (4.0f > f2 || f2 >= 14.0f) ? (14.0f > f2 || f2 > 24.0f) ? this.mRange10XToMaxEntryToZoomRatioSpline.interpolate(f2) : this.mRange3XTo10XEntryToZoomRatioSpline.interpolate(f2) : this.mRange1XTo3XEntryToZoomRatioSpline.interpolate(f2) : this.mRangeMinTo1XEntryToZoomRatioSpline.interpolate(f2);
            }
            if (0.0f <= f2 && f2 < 10.0f) {
                return this.mRange1XTo3XFor10XEntryToZoomRatioSpline.interpolate(f2);
            }
            if (10.0f > f2 || f2 > 30.0f) {
                return 0.0f;
            }
            return this.mRange3XTo10XFor10XEntryToZoomRatioSpline.interpolate(f2);
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public float mapZoomRatioToPosition(float f2) {
            if (isFullRangeZoomSupported()) {
                return (0.6f > f2 || f2 >= 1.0f) ? (1.0f > f2 || f2 >= 3.0f) ? (3.0f > f2 || f2 > 10.0f) ? this.mRange10XToMaxZoomRatioToEntrySpline.interpolate(f2) : this.mRange3XTo10XZoomRatioToEntrySpline.interpolate(f2) : this.mRange1XTo3XZoomRatioToEntrySpline.interpolate(f2) : this.mRangeMinTo1XZoomRatioToEntrySpline.interpolate(f2);
            }
            if (1.0f <= f2 && f2 < 3.0f) {
                return this.mRange1XTo3XFor10XZoomRatioToEntrySpline.interpolate(f2);
            }
            if (3.0f > f2 || f2 > 10.0f) {
                return 0.0f;
            }
            return this.mRange3XTo10XFor10XZoomRatioToEntrySpline.interpolate(f2);
        }
    }

    private static class _6_10_50_100_500 extends MultiStopsZoomRatioResolver {
        private static final int ENTRY_COUNT_10X_TO_MAX = 11;
        private static final int ENTRY_COUNT_MIN_TO_10X_RANGE_10X = 31;
        private static final int ENTRY_COUNT_MIN_TO_MAX = 35;
        private static int ENTRY_COUNT_TOTAL = 35;
        private static final int ENTRY_INDEX_10X = 24;
        private static final int ENTRY_INDEX_10X_RANGE_10X = 30;
        private static final int ENTRY_INDEX_1X = 4;
        private static final int ENTRY_INDEX_1X_RANGE_10X = 0;
        private static final int ENTRY_INDEX_5X = 14;
        private static final int ENTRY_INDEX_5X_RANGE_10X = 10;
        private static final int ENTRY_INDEX_LAST = 34;
        private static int ENTRY_INDEX_MAX = 34;
        private static final int ENTRY_INDEX_MIN = 0;
        private static final float[] RANGE_10X_TO_MAX_ENTRY_INDEX_X = {24.0f, 26.0f, 30.0f, 32.0f, 34.0f};
        private static final float[] RANGE_10X_TO_MAX_ZOOM_RATIO_Y = {10.0f, 20.0f, 32.0f, 46.4f, 50.0f};
        private static final float[] RANGE_1X_TO_5X_ENTRY_INDEX_X = {4.0f, 14.0f};
        private static final float[] RANGE_1X_TO_5X_FOR_10X_ENTRY_INDEX_X = {0.0f, 2.0f, 4.0f, 6.0f, 8.0f, 10.0f};
        private static final float[] RANGE_1X_TO_5X_FOR_10X_ZOOM_RATIO_Y = {1.0f, 1.8f, 2.6f, 3.4f, 4.2f, 5.0f};
        private static final float[] RANGE_1X_TO_5X_ZOOM_RATIO_Y = {1.0f, 5.0f};
        private static final float[] RANGE_5X_TO_10X_ENTRY_INDEX_X = {14.0f, 24.0f};
        private static final float[] RANGE_5X_TO_10X_FOR_10X_ENTRY_INDEX_X = {10.0f, 12.0f, 14.0f, 20.0f, 22.0f, 26.0f, 30.0f};
        private static final float[] RANGE_5X_TO_10X_FOR_10X_ZOOM_RATIO_Y = {5.0f, 5.5f, 6.0f, 7.5f, 8.0f, 9.0f, 10.0f};
        private static final float[] RANGE_5X_TO_10X_ZOOM_RATIO_Y = {5.0f, 10.0f};
        private static final float[] RANGE_MIN_TO_1X_ENTRY_INDEX_X = {0.0f, 1.0f, 2.0f, 3.0f, 4.0f};
        private static final float[] RANGE_MIN_TO_1X_ZOOM_RATIO_Y = {0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
        private Spline mRange10XToMaxEntryToZoomRatioSpline;
        private Spline mRange10XToMaxZoomRatioToEntrySpline;
        private Spline mRange1XTo5XEntryToZoomRatioSpline;
        private Spline mRange1XTo5XFor10XEntryToZoomRatioSpline;
        private Spline mRange1XTo5XFor10XZoomRatioToEntrySpline;
        private Spline mRange1XTo5XZoomRatioToEntrySpline;
        private Spline mRange5XTo10XEntryToZoomRatioSpline;
        private Spline mRange5XTo10XFor10XEntryToZoomRatioSpline;
        private Spline mRange5XTo10XFor10XZoomRatioToEntrySpline;
        private Spline mRange5XTo10XZoomRatioToEntrySpline;
        private Spline mRangeMinTo1XEntryToZoomRatioSpline;
        private Spline mRangeMinTo1XZoomRatioToEntrySpline;

        _6_10_50_100_500(MultiStopsZoomSliderAdapter multiStopsZoomSliderAdapter) {
            super(multiStopsZoomSliderAdapter);
            if (((MultiStopsZoomRatioResolver) this).mZoomRatioMax > 10.0f) {
                this.mRangeMinTo1XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ENTRY_INDEX_X, RANGE_MIN_TO_1X_ZOOM_RATIO_Y);
                this.mRangeMinTo1XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_MIN_TO_1X_ZOOM_RATIO_Y, RANGE_MIN_TO_1X_ENTRY_INDEX_X);
                this.mRange1XTo5XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_1X_TO_5X_ENTRY_INDEX_X, RANGE_1X_TO_5X_ZOOM_RATIO_Y);
                this.mRange1XTo5XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_1X_TO_5X_ZOOM_RATIO_Y, RANGE_1X_TO_5X_ENTRY_INDEX_X);
                this.mRange5XTo10XEntryToZoomRatioSpline = Spline.createLinearSpline(RANGE_5X_TO_10X_ENTRY_INDEX_X, RANGE_5X_TO_10X_ZOOM_RATIO_Y);
                this.mRange5XTo10XZoomRatioToEntrySpline = Spline.createLinearSpline(RANGE_5X_TO_10X_ZOOM_RATIO_Y, RANGE_5X_TO_10X_ENTRY_INDEX_X);
                float[] entryX = getEntryX();
                float[] zoomRatioY = getZoomRatioY(((MultiStopsZoomRatioResolver) this).mZoomRatioMax);
                this.mRange10XToMaxEntryToZoomRatioSpline = Spline.createMonotoneCubicSpline(entryX, zoomRatioY);
                this.mRange10XToMaxZoomRatioToEntrySpline = Spline.createMonotoneCubicSpline(zoomRatioY, entryX);
                ENTRY_COUNT_TOTAL = 35;
                ENTRY_INDEX_MAX = 34;
                return;
            }
            this.mRange1XTo5XFor10XEntryToZoomRatioSpline = Spline.createSpline(RANGE_1X_TO_5X_FOR_10X_ENTRY_INDEX_X, RANGE_1X_TO_5X_FOR_10X_ZOOM_RATIO_Y);
            this.mRange1XTo5XFor10XZoomRatioToEntrySpline = Spline.createSpline(RANGE_1X_TO_5X_FOR_10X_ZOOM_RATIO_Y, RANGE_1X_TO_5X_FOR_10X_ENTRY_INDEX_X);
            this.mRange5XTo10XFor10XEntryToZoomRatioSpline = Spline.createSpline(RANGE_5X_TO_10X_FOR_10X_ENTRY_INDEX_X, RANGE_5X_TO_10X_FOR_10X_ZOOM_RATIO_Y);
            this.mRange5XTo10XFor10XZoomRatioToEntrySpline = Spline.createSpline(RANGE_5X_TO_10X_FOR_10X_ZOOM_RATIO_Y, RANGE_5X_TO_10X_FOR_10X_ENTRY_INDEX_X);
            ENTRY_COUNT_TOTAL = 31;
            ENTRY_INDEX_MAX = 30;
        }

        private static float[] getEntryX() {
            float[] fArr = RANGE_10X_TO_MAX_ENTRY_INDEX_X;
            float f2 = (float) 24;
            int i = (int) ((fArr[fArr.length - 1] - f2) + 1.0f);
            float[] fArr2 = new float[fArr.length];
            for (int i2 = 0; i2 < fArr.length; i2++) {
                if (fArr[i2] <= f2) {
                    fArr2[i2] = fArr[i2];
                } else {
                    fArr2[i2] = (((fArr[i2] - f2) / ((float) (i - 1))) * 10.0f) + f2;
                }
            }
            return fArr2;
        }

        private static float[] getZoomRatioY(float f2) {
            float[] fArr = RANGE_10X_TO_MAX_ZOOM_RATIO_Y;
            int i = (int) fArr[fArr.length - 1];
            float[] fArr2 = new float[fArr.length];
            for (int i2 = 0; i2 < fArr.length; i2++) {
                if (fArr[i2] <= 10.0f) {
                    fArr2[i2] = fArr[i2];
                } else {
                    fArr2[i2] = (((fArr[i2] - 10.0f) / (((float) i) - 10.0f)) * (f2 - 10.0f)) + 10.0f;
                }
            }
            return fArr2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0046, code lost:
            if (getCurrentValueIndex() != r9) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0060, code lost:
            if (getCurrentValueIndex() != r9) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x008f, code lost:
            if (getCurrentValueIndex() != r9) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a8, code lost:
            if (getCurrentValueIndex() != r9) goto L_0x00c5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x002b, code lost:
            if (getCurrentValueIndex() != r9) goto L_0x00c5;
         */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x00c7  */
        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean checkAndNotifyIfValueChanged(int i, float f2) {
            String str;
            boolean z;
            boolean z2 = true;
            if (i == -1 || ((MultiStopsZoomRatioResolver) this).mZoomRatioMax <= 10.0f) {
                if (i == -1) {
                    str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                    z = str.equals(getCurrentValue());
                    z2 = true ^ z;
                    if (z2) {
                    }
                    return z2;
                } else if (i <= 10) {
                    str = String.valueOf(HybridZoomingSystem.add(1.0f, ((float) i) * 0.4f));
                } else {
                    if (i <= 30) {
                        str = String.valueOf(HybridZoomingSystem.add(5.0f, ((float) (i - 10)) * 0.25f));
                    } else {
                        str = null;
                    }
                    if (z2) {
                        notifyDataChanged(i, str);
                    }
                    return z2;
                }
            } else if (i <= 4) {
                str = String.valueOf(HybridZoomingSystem.add(0.6f, ((float) i) * 0.1f));
            } else if (i <= 14) {
                str = String.valueOf(HybridZoomingSystem.add(1.0f, ((float) (i - 4)) * 0.4f));
            } else if (i <= 24) {
                str = String.valueOf(HybridZoomingSystem.add(5.0f, ((float) (i - 14)) * 0.5f));
            } else {
                str = String.valueOf(mapPositionToZoomRatio(f2 * ((float) (getCount() - 1))));
                z = str.equals(getCurrentValue());
                z2 = true ^ z;
                if (z2) {
                }
                return z2;
            }
            z2 = false;
            if (z2) {
            }
            return z2;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public int getCount() {
            return ENTRY_COUNT_TOTAL;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isFirstStopPoint(int i) {
            return i == 0;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isLastStopPoint(int i) {
            return i == ENTRY_INDEX_MAX;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public boolean isStopPoint(int i) {
            float f2 = ((MultiStopsZoomRatioResolver) this).mZoomRatioMax;
            if (f2 > 10.0f) {
                return i == 0 || i == 4 || i == 14 || i == 24 || i == ENTRY_INDEX_MAX;
            }
            if (f2 <= 10.0f) {
                return i == 0 || i == 10 || i == 30;
            }
            return false;
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public float mapPositionToZoomRatio(float f2) {
            if (((MultiStopsZoomRatioResolver) this).mZoomRatioMax > 10.0f) {
                return (0.0f > f2 || f2 >= 4.0f) ? (4.0f > f2 || f2 >= 14.0f) ? (14.0f > f2 || f2 > 24.0f) ? this.mRange10XToMaxEntryToZoomRatioSpline.interpolate(f2) : this.mRange5XTo10XEntryToZoomRatioSpline.interpolate(f2) : this.mRange1XTo5XEntryToZoomRatioSpline.interpolate(f2) : this.mRangeMinTo1XEntryToZoomRatioSpline.interpolate(f2);
            }
            if (0.0f <= f2 && f2 < 10.0f) {
                return this.mRange1XTo5XFor10XEntryToZoomRatioSpline.interpolate(f2);
            }
            if (10.0f > f2 || f2 > 30.0f) {
                return 0.0f;
            }
            return this.mRange5XTo10XFor10XEntryToZoomRatioSpline.interpolate(f2);
        }

        @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomRatioResolver
        public float mapZoomRatioToPosition(float f2) {
            if (((MultiStopsZoomRatioResolver) this).mZoomRatioMax > 10.0f) {
                return (0.6f > f2 || f2 >= 1.0f) ? (1.0f > f2 || f2 >= 5.0f) ? (5.0f > f2 || f2 > 10.0f) ? this.mRange10XToMaxZoomRatioToEntrySpline.interpolate(f2) : this.mRange5XTo10XZoomRatioToEntrySpline.interpolate(f2) : this.mRange1XTo5XZoomRatioToEntrySpline.interpolate(f2) : this.mRangeMinTo1XZoomRatioToEntrySpline.interpolate(f2);
            }
            if (1.0f <= f2 && f2 < 5.0f) {
                return this.mRange1XTo5XFor10XZoomRatioToEntrySpline.interpolate(f2);
            }
            if (5.0f > f2 || f2 > 10.0f) {
                return 0.0f;
            }
            return this.mRange5XTo10XFor10XZoomRatioToEntrySpline.interpolate(f2);
        }
    }

    public FiveStopsZoomSliderAdapter(Context context, int i, ZoomValueListener zoomValueListener) {
        super(context, i, zoomValueListener);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.HorizontalDrawAdapter
    public int getCount() {
        return ((MultiStopsZoomSliderAdapter) this).mZoomRatioResolver.getCount();
    }

    @Override // com.android.camera.fragment.manually.adapter.MultiStopsZoomSliderAdapter
    public MultiStopsZoomRatioResolver getZoomRatioResolver() {
        if (b.fv || b.jv) {
            return new _6_10_50_100_500(this);
        }
        if (b.lv) {
            return new _6_10_30_100_300(this);
        }
        throw new IllegalStateException("Device not supported");
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
