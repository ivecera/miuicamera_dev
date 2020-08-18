package com.google.android.libraries.lens.lenslite;

import android.graphics.drawable.Drawable;
import android.util.Log;

public abstract class ExternalChipContent {
    private static final String TAG = "ExternalChipContent";

    public interface ExternalChipContentCallback {
        void onClick();
    }

    public static ExternalChipContent create(String str, Drawable drawable, ExternalChipContentCallback externalChipContentCallback) {
        Log.d(TAG, "onFocusChange: text = " + str + ", icon = " + drawable + ", callback = " + externalChipContentCallback);
        return null;
    }
}
