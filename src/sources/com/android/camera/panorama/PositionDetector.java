package com.android.camera.panorama;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.ViewGroup;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.panorama.MorphoPanoramaGP3;

public class PositionDetector {
    public static final int COMPLETED = 1;
    public static final int ERROR_IDLE = -1;
    public static final int ERROR_REVERSE = -2;
    private static final int IDLE_THRES_RATIO = 2;
    private static final long IDLE_TIME = 3000000000L;
    public static final int OK = 0;
    private static final float PREVIEW_LONG_SIDE_CROP_RATIO = 0.8f;
    private static final int REVERSE_THRES_RATIO = 7;
    private static final int SPEED_CHECK_CONTINUOUSLY_TIMES = 5;
    private static final int SPEED_CHECK_IGNORE_TIMES = 15;
    private static final int SPEED_CHECK_MODE = 1;
    private static final int SPEED_CHECK_MODE_AVERAGE = 1;
    private static final String TAG = "PositionDetector";
    private static final double TOO_FAST_THRES_RATIO = 0.8d;
    private static final double TOO_SLOW_THRES_RATIO = 0.05d;
    public static final int WARNING_TOO_FAST = 2;
    public static final int WARNING_TOO_SLOW = 3;
    private long count;
    private volatile double cur_x;
    private volatile double cur_y;
    private final int direction;
    private final RectF frame_rect = new RectF();
    private RectF idle_rect = null;
    private long idle_start_time;
    private double idle_thres;
    private int mCameraOrientation;
    private final DiffManager mDiffManager = new DiffManager();
    private MorphoPanoramaGP3.InitParam mInitParam;
    private boolean mIsFrontCamera;
    private ViewGroup mPreviewFrame;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private final int output_height;
    private final int output_width;
    private double peak;
    private double prev_x;
    private double prev_y;
    private boolean reset_idle_timer;
    private double reverse_thres;
    private double reverse_thres2;
    private int too_fast_count;
    private double too_fast_thres;
    private int too_slow_count;
    private double too_slow_thres;

    private static class DiffManager {
        private static final int NUM = 5;
        private int add_num;
        private double ave;
        private int index;
        private final double[] pos = new double[5];

        public DiffManager() {
            clear();
        }

        private void calc() {
            double d2 = 0.0d;
            int i = 0;
            while (true) {
                int i2 = this.add_num;
                if (i < i2) {
                    d2 += this.pos[i];
                    i++;
                } else {
                    this.ave = d2 / ((double) i2);
                    return;
                }
            }
        }

        public void add(double d2) {
            double[] dArr = this.pos;
            int i = this.index;
            dArr[i] = d2;
            this.index = i + 1;
            if (this.index >= 5) {
                this.index = 0;
            }
            int i2 = this.add_num;
            if (i2 < 5) {
                this.add_num = i2 + 1;
            }
            calc();
        }

        public void clear() {
            for (int i = 0; i < 5; i++) {
                this.pos[i] = 0.0d;
            }
            this.index = 0;
            this.add_num = 0;
        }

        public double getDiff() {
            return this.ave;
        }
    }

    public PositionDetector(MorphoPanoramaGP3.InitParam initParam, ViewGroup viewGroup, boolean z, int i, int i2, int i3, int i4, int i5, int i6) {
        this.mInitParam = initParam;
        this.mPreviewFrame = viewGroup;
        this.mIsFrontCamera = z;
        this.mCameraOrientation = i;
        this.mPreviewWidth = i2;
        this.mPreviewHeight = i3;
        this.count = 0;
        this.direction = i4;
        this.output_width = i5;
        this.output_height = i6;
        this.reset_idle_timer = true;
        this.too_fast_count = 0;
        this.too_slow_count = 0;
        this.prev_y = 0.0d;
        this.prev_x = 0.0d;
        this.cur_y = 0.0d;
        this.cur_x = 0.0d;
        int i7 = this.direction;
        if (i7 == 0) {
            int i8 = this.mInitParam.output_rotation;
            int i9 = this.mCameraOrientation;
            if ((i8 + i9) % 360 == 90 || (i8 + i9) % 360 == 180) {
                this.peak = (double) i5;
            } else {
                this.peak = 0.0d;
            }
            float f2 = (float) i5;
            this.reverse_thres = (double) (0.07f * f2);
            this.reverse_thres2 = (double) (PREVIEW_LONG_SIDE_CROP_RATIO * f2);
            this.idle_thres = (double) (f2 * 0.02f);
            double d2 = (double) i5;
            this.too_slow_thres = 5.0E-4d * d2;
            this.too_fast_thres = d2 * 0.008d;
        } else if (i7 == 1) {
            int i10 = this.mInitParam.output_rotation;
            int i11 = this.mCameraOrientation;
            if ((i10 + i11) % 360 == 90 || (i10 + i11) % 360 == 180) {
                this.peak = 0.0d;
            } else {
                this.peak = (double) i5;
            }
            float f3 = (float) i5;
            this.reverse_thres = (double) (0.07f * f3);
            this.reverse_thres2 = (double) (PREVIEW_LONG_SIDE_CROP_RATIO * f3);
            this.idle_thres = (double) (f3 * 0.02f);
            double d3 = (double) i5;
            this.too_slow_thres = 5.0E-4d * d3;
            this.too_fast_thres = d3 * 0.008d;
        } else if (i7 == 2) {
            int i12 = this.mInitParam.output_rotation;
            int i13 = this.mCameraOrientation;
            if ((i12 + i13) % 360 == 90 || (i12 + i13) % 360 == 180) {
                this.peak = (double) i6;
            } else {
                this.peak = 0.0d;
            }
            float f4 = (float) i6;
            this.reverse_thres = (double) (0.07f * f4);
            this.reverse_thres2 = (double) (PREVIEW_LONG_SIDE_CROP_RATIO * f4);
            this.idle_thres = (double) (f4 * 0.02f);
            double d4 = (double) i6;
            this.too_slow_thres = 5.0E-4d * d4;
            this.too_fast_thres = d4 * 0.008d;
        } else if (i7 == 3) {
            int i14 = this.mInitParam.output_rotation;
            int i15 = this.mCameraOrientation;
            if ((i14 + i15) % 360 == 90 || (i14 + i15) % 360 == 180) {
                this.peak = 0.0d;
            } else {
                this.peak = (double) i6;
            }
            float f5 = (float) i6;
            this.reverse_thres = (double) (0.07f * f5);
            this.reverse_thres2 = (double) (PREVIEW_LONG_SIDE_CROP_RATIO * f5);
            this.idle_thres = (double) (f5 * 0.02f);
            double d5 = (double) i6;
            this.too_slow_thres = 5.0E-4d * d5;
            this.too_fast_thres = d5 * 0.008d;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004a  */
    private int checkSpeed() {
        double d2;
        double d3;
        int i = this.direction;
        int i2 = 3;
        if (i == 2 || i == 3) {
            d3 = this.cur_y;
            d2 = this.prev_y;
        } else {
            d3 = this.cur_x;
            d2 = this.prev_x;
        }
        this.mDiffManager.add(Math.abs(d3 - d2));
        if (15 < this.count) {
            if (this.mDiffManager.getDiff() >= this.too_slow_thres) {
                if (this.mDiffManager.getDiff() > this.too_fast_thres) {
                    i2 = 2;
                }
            }
            if (this.too_slow_count > 0) {
                this.too_slow_count = 0;
            }
            if (this.too_fast_count > 0) {
                this.too_fast_count = 0;
            }
            return i2;
        }
        i2 = 0;
        if (this.too_slow_count > 0) {
        }
        if (this.too_fast_count > 0) {
        }
        return i2;
    }

    private boolean isComplete() {
        int i;
        double d2;
        int i2;
        int i3 = this.direction;
        if (i3 == 2 || i3 == 3) {
            d2 = this.cur_y;
            i2 = this.output_height;
            i = this.mPreviewHeight / 2;
        } else {
            d2 = this.cur_x;
            i2 = this.output_width;
            i = this.mPreviewWidth / 2;
        }
        int i4 = this.direction;
        if (i4 == 1 || i4 == 3) {
            int i5 = this.mInitParam.output_rotation;
            return (i5 == 90 || i5 == 0) ? d2 > ((double) (i2 - (i / 2))) : d2 < ((double) (i / 2));
        }
        int i6 = this.mInitParam.output_rotation;
        return (i6 == 90 || i6 == 0) ? d2 < ((double) (i / 2)) : d2 > ((double) (i2 - (i / 2)));
    }

    private boolean isIdle() {
        long nanoTime = System.nanoTime();
        if (this.reset_idle_timer) {
            this.reset_idle_timer = false;
            this.idle_start_time = nanoTime;
        }
        if (this.idle_rect == null) {
            double d2 = this.idle_thres / 2.0d;
            this.idle_rect = new RectF((float) (this.cur_x - d2), (float) (this.cur_y - d2), (float) (this.cur_x + d2), (float) (this.cur_y + d2));
        }
        if (IDLE_TIME < nanoTime - this.idle_start_time) {
            return true;
        }
        if (!this.idle_rect.contains((float) this.cur_x, (float) this.cur_y)) {
            this.reset_idle_timer = true;
            this.idle_rect = null;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0054, code lost:
        if (((r7 + r12) % 360) != 270) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0067, code lost:
        if (((r7 + r12) % 360) != 180) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006a, code lost:
        r7 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0090, code lost:
        if (r5 != 3) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00af, code lost:
        if (r5 != 3) goto L_0x009b;
     */
    private boolean isReverse() {
        double d2;
        double d3;
        int i;
        boolean z;
        Log.v(TAG, "cur_x = " + this.cur_x + ", cur_y = " + this.cur_y);
        int i2 = this.direction;
        if (i2 == 2 || i2 == 3) {
            d3 = this.cur_y;
            d2 = this.prev_y;
            i = this.output_height;
        } else {
            d3 = this.cur_x;
            d2 = this.prev_x;
            i = this.output_width;
        }
        int i3 = this.direction;
        boolean z2 = false;
        if (i3 == 1 || i3 == 3) {
            int i4 = this.mInitParam.output_rotation;
            int i5 = this.mCameraOrientation;
            if ((i4 + i5) % 360 != 90) {
            }
        } else {
            int i6 = this.mInitParam.output_rotation;
            int i7 = this.mCameraOrientation;
            if ((i6 + i7) % 360 != 0) {
            }
        }
        boolean z3 = false;
        if (z3) {
            if (d2 - d3 > this.reverse_thres2) {
                return true;
            }
            int i8 = this.mInitParam.output_rotation;
            int i9 = this.mCameraOrientation;
            if ((i8 + i9) % 360 == 90 || (i8 + i9) % 360 == 180) {
                int i10 = this.direction;
                if (i10 != 0) {
                    if (i10 != 1) {
                        if (i10 != 2) {
                        }
                    }
                    this.peak = Math.max(this.peak, d3);
                    z = true;
                    if (!z ? !(d3 <= ((double) i) && d3 - this.peak <= this.reverse_thres) : !(d3 <= ((double) i) && this.peak - d3 <= this.reverse_thres)) {
                        z2 = true;
                    }
                }
                this.peak = Math.min(this.peak, d3);
            } else {
                int i11 = this.direction;
                if (i11 != 0) {
                    if (i11 != 1) {
                        if (i11 != 2) {
                        }
                    }
                    this.peak = Math.min(this.peak, d3);
                }
                this.peak = Math.max(this.peak, d3);
                z = true;
            }
            z = false;
        } else if (d3 - d2 > this.reverse_thres2) {
            return true;
        } else {
            if (d3 > this.peak) {
                this.peak = d3;
            }
            if (d3 < 0.0d || this.peak - d3 > this.reverse_thres) {
                return true;
            }
        }
        return z2;
    }

    private boolean isYOutOfRange() {
        if (this.cur_x < 0.0d || this.cur_y < 0.0d) {
            return true;
        }
        int min = Math.min(this.output_width, this.output_height);
        return this.mInitParam.output_rotation % 180 == 90 ? this.cur_y > ((double) min) : this.cur_x > ((double) min);
    }

    private void updateFrame() {
        float width;
        float height;
        float f2;
        float f3;
        int height2;
        Rect rect = new Rect();
        this.mPreviewFrame.getGlobalVisibleRect(rect);
        if (rect.width() > 0) {
            int i = this.mInitParam.output_rotation;
            if (i == 90) {
                width = ((float) this.mPreviewFrame.getWidth()) / ((float) this.output_width);
                f2 = (float) this.cur_x;
                height = (float) ((this.cur_y / ((double) this.output_height)) * ((double) rect.height()));
                f3 = (((float) this.mInitParam.input_height) / 2.0f) * width;
                height2 = rect.height();
            } else if (i == 180) {
                width = ((float) this.mPreviewFrame.getWidth()) / ((float) this.output_height);
                height = (float) ((this.cur_x / ((double) this.output_width)) * ((double) rect.height()));
                f2 = (float) this.cur_y;
                f3 = (((float) this.mInitParam.input_height) / 2.0f) * width;
                height2 = rect.height();
            } else if (i == 270) {
                int i2 = this.output_width;
                width = ((float) this.mPreviewFrame.getWidth()) / ((float) i2);
                f2 = (float) (((double) i2) - this.cur_x);
                height = (float) ((this.cur_y / ((double) this.output_height)) * ((double) rect.height()));
                f3 = (((float) this.mInitParam.input_height) / 2.0f) * width;
                height2 = rect.height();
            } else {
                width = ((float) this.mPreviewFrame.getWidth()) / ((float) this.output_height);
                height = (float) ((this.cur_x / ((double) this.output_width)) * ((double) rect.height()));
                f2 = (float) (((double) this.output_height) - this.cur_y);
                f3 = (((float) this.mInitParam.input_height) / 2.0f) * width;
                height2 = rect.height();
            }
            float f4 = ((float) height2) / 2.0f;
            float f5 = f2 * width;
            this.frame_rect.set(f5 - f3, height - f4, f5 + f3, height + f4);
        }
    }

    public int detect(double d2, double d3) {
        this.count++;
        if (!Util.isEqualsZero(this.cur_x) || !Util.isEqualsZero(this.prev_x)) {
            this.prev_x = this.cur_x;
            this.cur_x = d2;
        } else {
            this.prev_x = d2;
            this.cur_x = d2;
        }
        if (!Util.isEqualsZero(this.cur_y) || !Util.isEqualsZero(this.prev_y)) {
            this.prev_y = this.cur_y;
            this.cur_y = d3;
        } else {
            this.prev_y = d3;
            this.cur_y = d3;
        }
        if (isYOutOfRange()) {
            Log.d(TAG, "isYOutOfRange");
            return 1;
        } else if (isReverse()) {
            Log.d(TAG, "isReverse");
            return -2;
        } else if (isComplete()) {
            Log.d(TAG, "isComplete");
            return 1;
        } else {
            int checkSpeed = checkSpeed();
            updateFrame();
            return checkSpeed;
        }
    }

    public RectF getFrameRect() {
        return this.frame_rect;
    }
}
