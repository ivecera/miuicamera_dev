package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class V6RelativeLayout extends RelativeLayout implements Rotatable, V6FunctionUI, V6Animation {
    public V6RelativeLayout(Context context) {
        super(context);
    }

    public V6RelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.camera.ui.V6Animation
    public void animateIn(Runnable runnable) {
    }

    @Override // com.android.camera.ui.V6Animation
    public void animateIn(Runnable runnable, int i) {
        animateIn(runnable);
    }

    @Override // com.android.camera.ui.V6Animation
    public void animateIn(Runnable runnable, int i, boolean z) {
    }

    @Override // com.android.camera.ui.V6Animation
    public void animateOut(Runnable runnable) {
    }

    @Override // com.android.camera.ui.V6Animation
    public void animateOut(Runnable runnable, int i) {
        animateOut(runnable);
    }

    @Override // com.android.camera.ui.V6Animation
    public void animateOut(Runnable runnable, int i, boolean z) {
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void enableControls(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).enableControls(z);
            }
        }
    }

    public View findChildrenById(int i) {
        return findViewById(i);
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onCameraOpen() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onCameraOpen();
            }
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onCreate() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onCreate();
            }
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onPause() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onPause();
            }
        }
    }

    @Override // com.android.camera.ui.V6FunctionUI
    public void onResume() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof V6FunctionUI) {
                ((V6FunctionUI) childAt).onResume();
            }
        }
    }

    @Override // com.android.camera.ui.Rotatable
    public void setOrientation(int i, boolean z) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof Rotatable) {
                ((Rotatable) childAt).setOrientation(i, z);
            }
        }
    }
}
