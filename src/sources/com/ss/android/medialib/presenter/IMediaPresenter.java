package com.ss.android.medialib.presenter;

import android.graphics.SurfaceTexture;
import com.ss.android.medialib.camera.ImageFrame;
import com.ss.android.medialib.common.Common;

public interface IMediaPresenter {
    int initImageDrawer(int i);

    int onDrawFrame(int i, float[] fArr);

    int onDrawFrame(ImageFrame imageFrame);

    void setOnOpenGLCallback(Common.IOnOpenGLCallback iOnOpenGLCallback);

    void setSurfaceTexture(SurfaceTexture surfaceTexture);

    void updateRotation(int i, boolean z);
}
