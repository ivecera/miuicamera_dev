package org.webrtc.videoengine;

import android.content.Context;
import android.view.SurfaceView;

public class ViERenderer {
    public static SurfaceView CreateRenderer(Context context) {
        return CreateRenderer(context, false);
    }

    public static SurfaceView CreateRenderer(Context context, boolean z) {
        return (!z || !ViEAndroidGLES20.IsSupported(context)) ? new SurfaceView(context) : new ViEAndroidGLES20(context);
    }
}
