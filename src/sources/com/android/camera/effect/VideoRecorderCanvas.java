package com.android.camera.effect;

import com.android.camera.Util;
import com.android.camera.effect.renders.RenderGroup;
import com.android.camera.effect.renders.VideoRecorderRender;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BaseGLCanvas;

public class VideoRecorderCanvas extends BaseGLCanvas {
    public VideoRecorderCanvas() {
        ((BaseGLCanvas) this).mRenderCaches = new RenderGroup(this);
        ((BaseGLCanvas) this).mRenderGroup = new VideoRecorderRender(this);
        initialize();
    }

    public void applyFilterId(int i) {
        ((VideoRecorderRender) ((BaseGLCanvas) this).mRenderGroup).setFilterId(i);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void deleteProgram() {
        super.deleteProgram();
        ((BaseGLCanvas) this).mRenderCaches.destroy();
        ((BaseGLCanvas) this).mRenderGroup.destroy();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void setSize(int i, int i2) {
        super.setSize(i, i2);
        Log.d("VideoRecorderCanvas", "setSize: size=" + i + "x" + i2 + " modelMatrix=" + Util.dumpMatrix(((BaseGLCanvas) this).mState.getModelMatrix()));
    }
}
