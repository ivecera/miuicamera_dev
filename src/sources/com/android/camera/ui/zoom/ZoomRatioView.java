package com.android.camera.ui.zoom;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;

public class ZoomRatioView extends FrameLayout {
    private static final String TAG = "ZoomRatioView";
    private static final boolean UI_DEBUG_ENABLED = Log.isLoggable(TAG, 3);
    private ImageView mZoomRatioIcon;
    private int mZoomRatioIndex;
    private ZoomTextImageView mZoomRatioText;

    public ZoomRatioView(Context context) {
        this(context, null);
    }

    public ZoomRatioView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomRatioView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ZoomRatioView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private static void debugUi(String str, String str2) {
        if (UI_DEBUG_ENABLED) {
            com.android.camera.log.Log.d(str, str2);
        }
    }

    public float getAlpha() {
        return this.mZoomRatioIcon.getAlpha();
    }

    public ImageView getIconView() {
        return this.mZoomRatioIcon;
    }

    public ZoomTextImageView getTextView() {
        return this.mZoomRatioText;
    }

    public int getZoomRatioIndex() {
        return this.mZoomRatioIndex;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mZoomRatioIcon = (ImageView) findViewById(R.id.zoom_ratio_item_image);
        this.mZoomRatioText = (ZoomTextImageView) findViewById(R.id.zoom_ratio_item_text);
    }

    public void setAlpha(float f2) {
        debugUi(TAG, "setAlpha(): index = " + this.mZoomRatioIndex + ", alpha = " + f2);
        this.mZoomRatioIcon.setAlpha(f2);
        this.mZoomRatioText.setAlpha(1.0f - f2);
    }

    public void setCaptureCount(int i) {
        this.mZoomRatioText.setText(1, String.format("%02d", Integer.valueOf(i)));
    }

    public void setIconify(boolean z) {
        setAlpha(z ? 1.0f : 0.0f);
    }

    public void setVisibility(int i) {
        debugUi(TAG, "setVisibility(): index = " + this.mZoomRatioIndex + ", visibility = " + Util.viewVisibilityToString(i));
        super.setVisibility(i);
    }

    public void setZoomRatio(float f2) {
        StringBuilder sb = new StringBuilder();
        if (f2 != 0.0f) {
            float decimal = HybridZoomingSystem.toDecimal(f2);
            int i = (int) decimal;
            if (((int) ((10.0f * decimal) - ((float) (i * 10)))) == 0) {
                sb.append(i);
            } else {
                sb.append(decimal);
            }
            debugUi(TAG, "setZoomRatio(): " + f2);
            this.mZoomRatioText.setText(0, sb.toString());
            return;
        }
        this.mZoomRatioText.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.optical_zoom_ratio_dot_macro_on));
    }

    public void setZoomRatioIcon(float f2) {
        if (f2 != 0.0f) {
            this.mZoomRatioIcon.setImageResource(R.drawable.optical_zoom_ratio_dot_indicator);
        } else {
            this.mZoomRatioIcon.setImageResource(R.drawable.optical_zoom_ratio_dot_macro_off);
        }
    }

    public void setZoomRatioIndex(int i) {
        this.mZoomRatioIndex = i;
    }
}
