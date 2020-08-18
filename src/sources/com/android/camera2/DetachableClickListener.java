package com.android.camera2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ViewTreeObserver;
import com.android.camera.log.Log;

public final class DetachableClickListener implements DialogInterface.OnClickListener {
    /* access modifiers changed from: private */
    public static final String TAG = "DetachableClickListener";
    /* access modifiers changed from: private */
    public DialogInterface.OnClickListener delegateOrNull;

    private DetachableClickListener(DialogInterface.OnClickListener onClickListener) {
        this.delegateOrNull = onClickListener;
    }

    public static DetachableClickListener wrap(DialogInterface.OnClickListener onClickListener) {
        return new DetachableClickListener(onClickListener);
    }

    public void clearOnDetach(Dialog dialog) {
        dialog.getWindow().getDecorView().getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            /* class com.android.camera2.DetachableClickListener.AnonymousClass1 */

            public void onWindowAttached() {
                Log.v(DetachableClickListener.TAG, "dialog attach to window");
            }

            public void onWindowDetached() {
                DialogInterface.OnClickListener unused = DetachableClickListener.this.delegateOrNull = null;
            }
        });
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        DialogInterface.OnClickListener onClickListener = this.delegateOrNull;
        if (onClickListener != null) {
            onClickListener.onClick(dialogInterface, i);
        }
    }
}
