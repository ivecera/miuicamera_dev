package com.ss.android.vesdk;

import android.support.annotation.Keep;
import com.ss.android.vesdk.VEListener;

@Keep
public class TEEffectCallback {
    private VEListener.VEEditorEffectListener listener;

    public void onDone(int i, boolean z) {
        this.listener.onDone(i, z);
    }

    public void setListener(Object obj) {
        this.listener = (VEListener.VEEditorEffectListener) obj;
    }
}
