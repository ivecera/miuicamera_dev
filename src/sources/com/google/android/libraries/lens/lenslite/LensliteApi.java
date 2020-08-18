package com.google.android.libraries.lens.lenslite;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.android.libraries.lens.lenslite.api.LinkImage;

public class LensliteApi {
    private static final String TAG = "LensliteApi";

    public static LensliteApi create(Context context, int i) {
        Log.d(TAG, "create: context = " + context + ", imagePoolSize = " + i);
        return null;
    }

    public LensliteUiController getUiController() {
        return null;
    }

    public void onNewImage(LinkImage linkImage) {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStart(LensliteUiContainer lensliteUiContainer, Activity activity, StatusUpdateCallback statusUpdateCallback) {
    }

    public void onStop() {
    }
}
