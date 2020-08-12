package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.effect.EffectController;
import com.android.camera.log.Log;

public class GradienterDrawer extends RelativeLayout {
    public static final int COLOR_NORMAL = -1711276033;
    public static final int COLOR_SELECTED = -13840129;
    public static final String TAG = "GradienterDrawer";
    private boolean isReferenceLineEnabled;
    private boolean isSquareModule;
    private Direct mCurrentDirect = Direct.NONE;
    private float mDeviceRotation = 0.0f;
    private View mLineLeftView;
    private int mLineLongColor = 1795162111;
    private int mLineLongWidth = 1;
    private View mLineRightView;
    private int mLineShortColor = COLOR_SELECTED;
    private View mLineShortView;
    private int mLineShortWidth = 6;
    private int mParentHeighth = 0;
    private int mParentWidth = 0;
    private LinearLayout mRootView;

    /* renamed from: com.android.camera.ui.GradienterDrawer$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$camera$ui$GradienterDrawer$Direct = new int[Direct.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$com$android$camera$ui$GradienterDrawer$Direct[Direct.TOP.ordinal()] = 1;
            $SwitchMap$com$android$camera$ui$GradienterDrawer$Direct[Direct.BOTTOM.ordinal()] = 2;
            $SwitchMap$com$android$camera$ui$GradienterDrawer$Direct[Direct.RIGHT.ordinal()] = 3;
            try {
                $SwitchMap$com$android$camera$ui$GradienterDrawer$Direct[Direct.LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    enum Direct {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public GradienterDrawer(Context context) {
        super(context);
        init(context);
    }

    public GradienterDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public GradienterDrawer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        this.mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.v6_preview_gradienter, (ViewGroup) this, false);
        addView(this.mRootView, new RelativeLayout.LayoutParams(-1, -1));
        this.mLineShortView = this.mRootView.findViewById(R.id.view_line_short);
        this.mLineLeftView = this.mRootView.findViewById(R.id.view_line_left);
        this.mLineRightView = this.mRootView.findViewById(R.id.view_line_right);
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private void resetMargin() {
        int i;
        int i2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mLineShortView.getLayoutParams();
        if (this.isReferenceLineEnabled) {
            resetParams(layoutParams);
            setViewVisible(this.mLineRightView, 8);
            setViewVisible(this.mLineLeftView, 8);
            int i3 = AnonymousClass1.$SwitchMap$com$android$camera$ui$GradienterDrawer$Direct[this.mCurrentDirect.ordinal()];
            if (i3 == 1) {
                if (!this.isSquareModule) {
                    i = this.mParentHeighth / 3;
                } else {
                    int i4 = this.mParentWidth;
                    i = (i4 / 3) + ((this.mParentHeighth - i4) / 2);
                }
                int i5 = this.mParentWidth / 3;
                this.mRootView.setOrientation(0);
                this.mRootView.setGravity(80);
                int i6 = this.mLineShortWidth;
                layoutParams.height = i6;
                layoutParams.width = -1;
                layoutParams.bottomMargin = i - (i6 / 2);
                layoutParams.leftMargin = i5;
                layoutParams.rightMargin = i5;
            } else if (i3 == 2) {
                if (!this.isSquareModule) {
                    i2 = this.mParentHeighth / 3;
                } else {
                    int i7 = this.mParentWidth;
                    i2 = (i7 / 3) + ((this.mParentHeighth - i7) / 2);
                }
                int i8 = this.mParentWidth / 3;
                this.mRootView.setOrientation(0);
                this.mRootView.setGravity(48);
                int i9 = this.mLineShortWidth;
                layoutParams.height = i9;
                layoutParams.width = -1;
                layoutParams.topMargin = i2 - (i9 / 2);
                layoutParams.leftMargin = i8;
                layoutParams.rightMargin = i8;
            } else if (i3 == 3) {
                int i10 = this.mParentWidth;
                int i11 = i10 / 3;
                int i12 = !this.isSquareModule ? this.mParentHeighth / 3 : (i10 / 3) + ((this.mParentHeighth - i10) / 2);
                this.mRootView.setOrientation(1);
                this.mRootView.setGravity(3);
                int i13 = this.mLineShortWidth;
                layoutParams.width = i13;
                layoutParams.height = -1;
                layoutParams.leftMargin = i11 - (i13 / 2);
                layoutParams.topMargin = i12;
                layoutParams.bottomMargin = i12;
            } else if (i3 == 4) {
                int i14 = this.mParentWidth;
                int i15 = i14 / 3;
                int i16 = !this.isSquareModule ? this.mParentHeighth / 3 : (i14 / 3) + ((this.mParentHeighth - i14) / 2);
                this.mRootView.setOrientation(1);
                this.mRootView.setGravity(5);
                int i17 = this.mLineShortWidth;
                layoutParams.width = i17;
                layoutParams.height = -1;
                layoutParams.rightMargin = i15 - (i17 / 2);
                layoutParams.topMargin = i16;
                layoutParams.bottomMargin = i16;
            }
            this.mLineShortView.setLayoutParams(layoutParams);
            return;
        }
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLineLeftView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mLineRightView.getLayoutParams();
        resetParams(layoutParams, layoutParams2, layoutParams3);
        setViewVisible(this.mLineRightView, 0);
        setViewVisible(this.mLineLeftView, 0);
        int i18 = AnonymousClass1.$SwitchMap$com$android$camera$ui$GradienterDrawer$Direct[this.mCurrentDirect.ordinal()];
        if (i18 == 1 || i18 == 2) {
            int i19 = this.mParentHeighth / 2;
            this.mRootView.setOrientation(0);
            this.mRootView.setGravity(48);
            int i20 = this.mLineShortWidth;
            layoutParams.height = i20;
            int i21 = this.mLineLongWidth;
            layoutParams2.height = i21;
            layoutParams3.height = i21;
            layoutParams.width = -1;
            layoutParams2.width = -1;
            layoutParams3.width = -1;
            layoutParams.topMargin = i19 - (i20 / 2);
            layoutParams2.topMargin = i19 - (i21 / 2);
            layoutParams3.topMargin = i19 - (i21 / 2);
        } else if (i18 == 3 || i18 == 4) {
            int i22 = this.mParentWidth / 2;
            this.mRootView.setOrientation(1);
            this.mRootView.setGravity(5);
            layoutParams.width = this.mLineShortWidth;
            int i23 = this.mLineLongWidth;
            layoutParams2.width = i23;
            layoutParams3.width = i23;
            if (!this.isSquareModule) {
                layoutParams.height = -1;
                layoutParams2.height = -1;
                layoutParams3.height = -1;
            } else {
                int i24 = this.mParentWidth;
                layoutParams.height = i24 / 3;
                int i25 = this.mParentHeighth;
                layoutParams2.height = (i25 - (i24 / 3)) / 2;
                layoutParams3.height = (i25 - (i24 / 3)) / 2;
            }
            layoutParams.rightMargin = i22 - (this.mLineShortWidth / 2);
            int i26 = this.mLineLongWidth;
            layoutParams2.rightMargin = i22 - (i26 / 2);
            layoutParams3.rightMargin = i22 - (i26 / 2);
        }
        this.mLineShortView.setLayoutParams(layoutParams);
        this.mLineLeftView.setLayoutParams(layoutParams2);
        this.mLineRightView.setLayoutParams(layoutParams3);
    }

    private void resetParams(LinearLayout.LayoutParams... layoutParamsArr) {
        for (LinearLayout.LayoutParams layoutParams : layoutParamsArr) {
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        }
    }

    private void setViewVisible(View view, int i) {
        if (view.getVisibility() != i) {
            view.setVisibility(i);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0086  */
    private void updateView() {
        Direct direct;
        float f2;
        this.mDeviceRotation = EffectController.getInstance().getDeviceRotation();
        float f3 = this.mDeviceRotation;
        if (f3 <= 45.0f || f3 >= 135.0f) {
            f3 = this.mDeviceRotation;
            if (f3 < 135.0f || f3 >= 225.0f) {
                f3 = this.mDeviceRotation;
                if (f3 <= 225.0f || f3 >= 315.0f) {
                    direct = Direct.BOTTOM;
                    if (this.mDeviceRotation == -1.0f) {
                        this.mDeviceRotation = -5.0f;
                    }
                    f3 = this.mDeviceRotation;
                    if (f3 > 300.0f) {
                        f2 = 360.0f;
                    }
                    if (Math.abs(f3) <= 3.0f) {
                        f3 = 0.0f;
                    }
                    setViewVisible(this.mLineShortView, 0);
                    if (direct != this.mCurrentDirect) {
                        setViewVisible(this.mLineShortView, 4);
                        this.mCurrentDirect = direct;
                        resetMargin();
                    }
                    if (f3 != 0.0f) {
                        setLineShortColor(COLOR_SELECTED);
                    } else {
                        setLineShortColor(-1711276033);
                    }
                    this.mLineShortView.setRotation(-f3);
                    Log.i(TAG, "updateView  rotationOffset : " + f3 + ";  mDeviceRotation :" + this.mDeviceRotation);
                    invalidateView();
                }
                direct = Direct.LEFT;
                f2 = 270.0f;
            } else {
                direct = Direct.TOP;
                f2 = 180.0f;
            }
        } else {
            direct = Direct.RIGHT;
            f2 = 90.0f;
        }
        f3 -= f2;
        if (Math.abs(f3) <= 3.0f) {
        }
        setViewVisible(this.mLineShortView, 0);
        if (direct != this.mCurrentDirect) {
        }
        if (f3 != 0.0f) {
        }
        this.mLineShortView.setRotation(-f3);
        Log.i(TAG, "updateView  rotationOffset : " + f3 + ";  mDeviceRotation :" + this.mDeviceRotation);
        invalidateView();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        updateView();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            this.mCurrentDirect = Direct.NONE;
        }
    }

    public void setConfigInfo(int i, int i2, boolean z) {
        this.mParentWidth = i;
        this.mParentHeighth = i2;
        this.isSquareModule = z;
        this.mCurrentDirect = Direct.NONE;
        updateView();
    }

    public void setLineShortColor(int i) {
        if (this.mLineShortColor != i) {
            this.mLineShortColor = i;
            this.mLineShortView.setBackgroundColor(i);
        }
    }

    public void setReferenceLineEnabled(boolean z) {
        this.mCurrentDirect = Direct.NONE;
        this.isReferenceLineEnabled = z;
    }

    public void setlineWidth(int i, int i2) {
        this.mLineLongWidth = i;
        this.mLineShortWidth = i2;
        this.mCurrentDirect = Direct.NONE;
    }
}
