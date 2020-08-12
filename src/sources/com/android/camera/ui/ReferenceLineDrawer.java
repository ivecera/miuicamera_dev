package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.android.camera.effect.EffectController;
import com.android.camera.ui.GradienterDrawer;

public class ReferenceLineDrawer extends View {
    private static final int BORDER = 1;
    public static final String TAG = "ReferenceLineDrawer";
    private boolean isGradienterEnabled;
    private boolean mBottomVisible = true;
    private int mColumnCount = 1;
    private GradienterDrawer.Direct mCurrentDirect = GradienterDrawer.Direct.NONE;
    private float mDeviceRotation = 0.0f;
    private int mFrameColor = 402653184;
    private Paint mFramePaint;
    private int mLineColor = 1795162111;
    private Paint mLinePaint;
    private int mRowCount = 1;
    private boolean mTopVisible = true;

    public ReferenceLineDrawer(Context context) {
        super(context);
    }

    public ReferenceLineDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ReferenceLineDrawer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void resetline(Canvas canvas) {
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        int i = 1;
        while (true) {
            int i2 = this.mColumnCount;
            if (i >= i2) {
                break;
            }
            if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.LEFT && i == 2) {
                int i3 = i * width;
                canvas.drawRect((float) (i3 / i2), 1.0f, (float) ((i3 / i2) + 3), (float) (height / this.mRowCount), this.mFramePaint);
                int i4 = this.mColumnCount;
                int i5 = this.mRowCount;
                canvas.drawRect((float) (i3 / i4), (float) (((height / i5) * (i5 - 1)) + 1), (float) ((i3 / i4) + 3), (float) (height - 1), this.mFramePaint);
            } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.RIGHT && i == 1) {
                int i6 = i * width;
                int i7 = this.mColumnCount;
                canvas.drawRect((float) (i6 / i7), 1.0f, (float) ((i6 / i7) + 3), (float) (height / this.mRowCount), this.mFramePaint);
                int i8 = this.mColumnCount;
                int i9 = this.mRowCount;
                canvas.drawRect((float) (i6 / i8), (float) (((height / i9) * (i9 - 1)) + 1), (float) ((i6 / i8) + 3), (float) (height - 1), this.mFramePaint);
            } else {
                int i10 = i * width;
                int i11 = this.mColumnCount;
                canvas.drawRect((float) (i10 / i11), 1.0f, (float) ((i10 / i11) + 3), (float) (height - 1), this.mFramePaint);
            }
            i++;
        }
        int i12 = !this.mBottomVisible;
        int i13 = 0;
        int i14 = 0;
        while (true) {
            int i15 = this.mRowCount;
            if (i14 > i15) {
                break;
            }
            if (!(i14 == 0 || i14 == i15) || ((i14 == 0 && this.mTopVisible) || (i14 == this.mRowCount && this.mBottomVisible))) {
                if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.BOTTOM && i14 == 1) {
                    int i16 = i14 * height;
                    int i17 = this.mRowCount;
                    canvas.drawRect((float) i12, (float) (i16 / i17), (float) (width / this.mColumnCount), (float) ((i16 / i17) + 3), this.mFramePaint);
                    int i18 = this.mColumnCount;
                    int i19 = this.mRowCount;
                    canvas.drawRect((float) (((width / i18) * (i18 - 1)) + i12), (float) (i16 / i19), (float) (width - i12), (float) ((i16 / i19) + 3), this.mFramePaint);
                } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.TOP && i14 == 2) {
                    int i20 = i14 * height;
                    int i21 = this.mRowCount;
                    canvas.drawRect((float) i12, (float) (i20 / i21), (float) (width / this.mColumnCount), (float) ((i20 / i21) + 3), this.mFramePaint);
                    int i22 = this.mColumnCount;
                    int i23 = this.mRowCount;
                    canvas.drawRect((float) (((width / i22) * (i22 - 1)) + i12), (float) (i20 / i23), (float) (width - i12), (float) ((i20 / i23) + 3), this.mFramePaint);
                } else {
                    int i24 = i14 * height;
                    int i25 = this.mRowCount;
                    canvas.drawRect((float) i12, (float) (i24 / i25), (float) (width - i12), (float) ((i24 / i25) + 3), this.mFramePaint);
                }
            }
            i14++;
        }
        int i26 = 1;
        while (true) {
            int i27 = this.mColumnCount;
            if (i26 >= i27) {
                break;
            }
            if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.RIGHT && i26 == 1) {
                int i28 = i26 * width;
                canvas.drawRect((float) (i28 / i27), 1.0f, (float) ((i28 / i27) + 2), (float) (height / this.mRowCount), this.mLinePaint);
                int i29 = this.mColumnCount;
                int i30 = this.mRowCount;
                canvas.drawRect((float) (i28 / i29), (float) (((height / i30) * (i30 - 1)) + 1), (float) ((i28 / i29) + 2), (float) (height - 1), this.mLinePaint);
            } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.LEFT && i26 == 2) {
                int i31 = i26 * width;
                int i32 = this.mColumnCount;
                canvas.drawRect((float) (i31 / i32), 1.0f, (float) ((i31 / i32) + 2), (float) (height / this.mRowCount), this.mLinePaint);
                int i33 = this.mColumnCount;
                int i34 = this.mRowCount;
                canvas.drawRect((float) (i31 / i33), (float) (((height / i34) * (i34 - 1)) + 1), (float) ((i31 / i33) + 2), (float) (height - 1), this.mLinePaint);
            } else {
                int i35 = i26 * width;
                int i36 = this.mColumnCount;
                canvas.drawRect((float) (i35 / i36), 1.0f, (float) ((i35 / i36) + 2), (float) (height - 1), this.mLinePaint);
            }
            i26++;
        }
        while (true) {
            int i37 = this.mRowCount;
            if (i13 <= i37) {
                if (!(i13 == 0 || i13 == i37) || ((i13 == 0 && this.mTopVisible) || (i13 == this.mRowCount && this.mBottomVisible))) {
                    if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.BOTTOM && i13 == 1) {
                        int i38 = i13 * height;
                        int i39 = this.mRowCount;
                        canvas.drawRect((float) i12, (float) (i38 / i39), (float) (width / this.mColumnCount), (float) ((i38 / i39) + 2), this.mLinePaint);
                        int i40 = this.mColumnCount;
                        int i41 = this.mRowCount;
                        canvas.drawRect((float) (((width / i40) * (i40 - 1)) + i12), (float) (i38 / i41), (float) (width - i12), (float) ((i38 / i41) + 2), this.mLinePaint);
                    } else if (this.isGradienterEnabled && this.mCurrentDirect == GradienterDrawer.Direct.TOP && i13 == 2) {
                        int i42 = i13 * height;
                        int i43 = this.mRowCount;
                        canvas.drawRect((float) i12, (float) (i42 / i43), (float) (width / this.mColumnCount), (float) ((i42 / i43) + 2), this.mLinePaint);
                        int i44 = this.mColumnCount;
                        int i45 = this.mRowCount;
                        canvas.drawRect((float) (((width / i44) * (i44 - 1)) + i12), (float) (i42 / i45), (float) (width - i12), (float) ((i42 / i45) + 2), this.mLinePaint);
                    } else {
                        int i46 = i13 * height;
                        int i47 = this.mRowCount;
                        canvas.drawRect((float) i12, (float) (i46 / i47), (float) (width - i12), (float) ((i46 / i47) + 2), this.mLinePaint);
                    }
                }
                i13++;
            } else {
                return;
            }
        }
    }

    private void updateView(Canvas canvas) {
        GradienterDrawer.Direct direct;
        this.mDeviceRotation = EffectController.getInstance().getDeviceRotation();
        float f2 = this.mDeviceRotation;
        if (f2 <= 45.0f || f2 >= 135.0f) {
            float f3 = this.mDeviceRotation;
            if (f3 < 135.0f || f3 >= 225.0f) {
                float f4 = this.mDeviceRotation;
                if (f4 <= 225.0f || f4 >= 315.0f) {
                    direct = GradienterDrawer.Direct.BOTTOM;
                    int i = (this.mDeviceRotation > 300.0f ? 1 : (this.mDeviceRotation == 300.0f ? 0 : -1));
                } else {
                    direct = GradienterDrawer.Direct.LEFT;
                }
            } else {
                direct = GradienterDrawer.Direct.TOP;
            }
        } else {
            direct = GradienterDrawer.Direct.RIGHT;
        }
        if (direct != this.mCurrentDirect) {
            this.mCurrentDirect = direct;
        }
        resetline(canvas);
        if (this.isGradienterEnabled) {
            invalidate();
        }
    }

    public void initialize(int i, int i2) {
        this.mColumnCount = Math.max(i2, 1);
        this.mRowCount = Math.max(i, 1);
        this.mLinePaint = new Paint();
        this.mFramePaint = new Paint();
        this.mLinePaint.setStrokeWidth(1.0f);
        this.mFramePaint.setStrokeWidth(1.0f);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mFramePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setColor(this.mLineColor);
        this.mFramePaint.setColor(this.mFrameColor);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        updateView(canvas);
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.mCurrentDirect = GradienterDrawer.Direct.NONE;
        }
    }

    public void setBorderVisible(boolean z, boolean z2) {
        if (this.mTopVisible != z || this.mBottomVisible != z2) {
            this.mTopVisible = z;
            this.mBottomVisible = z2;
            invalidate();
        }
    }

    public void setGradienterEnabled(boolean z) {
        this.isGradienterEnabled = z;
        if (getVisibility() == 0) {
            this.mCurrentDirect = GradienterDrawer.Direct.NONE;
            invalidate();
        }
    }

    public void setLineColor(int i) {
        this.mLineColor = i;
    }
}
