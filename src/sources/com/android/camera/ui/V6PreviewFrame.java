package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.module.ModuleManager;

public class V6PreviewFrame extends V6RelativeLayout {
    public GradienterDrawer mGradienter;
    public ReferenceLineDrawer mReferenceLine;

    public V6PreviewFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void hidePreviewGradienter() {
        GradienterDrawer gradienterDrawer = this.mGradienter;
        if (gradienterDrawer != null && gradienterDrawer.getVisibility() == 0) {
            this.mReferenceLine.setGradienterEnabled(false);
            this.mGradienter.setVisibility(8);
        }
    }

    public void hidePreviewReferenceLine() {
        if (this.mReferenceLine.getVisibility() == 0) {
            this.mGradienter.setReferenceLineEnabled(false);
            this.mReferenceLine.setVisibility(8);
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI, com.android.camera.ui.V6RelativeLayout
    public void onCameraOpen() {
        super.onCameraOpen();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mReferenceLine = (ReferenceLineDrawer) findViewById(R.id.v6_reference_grid);
        this.mReferenceLine.initialize(3, 3);
        this.mReferenceLine.setBorderVisible(false, false);
        this.mReferenceLine.setLineColor(-2130706433);
        this.mGradienter = (GradienterDrawer) findViewById(R.id.v6_reference_gradienter);
    }

    @Override // com.android.camera.ui.V6FunctionUI, com.android.camera.ui.V6RelativeLayout
    public void onResume() {
    }

    public void updateReferenceGradienterSwitched(boolean z, boolean z2, boolean z3) {
        ReferenceLineDrawer referenceLineDrawer;
        if (this.mGradienter != null && (referenceLineDrawer = this.mReferenceLine) != null) {
            referenceLineDrawer.setGradienterEnabled(z2);
            this.mGradienter.setReferenceLineEnabled(z);
            int i = 0;
            this.mReferenceLine.setVisibility(z ? 0 : 8);
            if (z2) {
                this.mGradienter.setConfigInfo(getWidth(), getHeight(), z3);
            }
            GradienterDrawer gradienterDrawer = this.mGradienter;
            if (!z2) {
                i = 8;
            }
            gradienterDrawer.setVisibility(i);
        }
    }

    public void updateReferenceLineAccordSquare() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mReferenceLine.getLayoutParams();
        if (ModuleManager.isSquareModule()) {
            int i = Util.sWindowWidth / 6;
            layoutParams.topMargin = i;
            layoutParams.bottomMargin = i;
        } else {
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        }
        if (this.mReferenceLine.getVisibility() == 0) {
            this.mReferenceLine.requestLayout();
        }
    }
}
