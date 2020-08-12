package com.android.gallery3d.ui;

import android.opengl.GLES20;
import com.android.camera.Util;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.renders.BasicRender;
import com.android.camera.effect.renders.EffectRenderGroup;
import com.android.camera.effect.renders.Render;
import com.android.camera.effect.renders.RenderGroup;
import com.android.camera.log.Log;

public class GLCanvasImpl extends BaseGLCanvas {
    private static final String TAG = "GLCanvasImpl";

    public GLCanvasImpl() {
        ((BaseGLCanvas) this).mRenderCaches = new RenderGroup(this);
        ((BaseGLCanvas) this).mRenderGroup = new RenderGroup(this);
        EffectRenderGroup effectRenderGroup = new EffectRenderGroup(this);
        EffectController.getInstance().addChangeListener(effectRenderGroup);
        ((BaseGLCanvas) this).mRenderGroup.addRender(effectRenderGroup);
        ((BaseGLCanvas) this).mRenderGroup.addRender(new BasicRender(this));
        initialize();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void deleteProgram() {
        super.deleteProgram();
        ((BaseGLCanvas) this).mRenderCaches.destroy();
        ((BaseGLCanvas) this).mRenderGroup.destroy();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void draw(DrawAttribute drawAttribute) {
        ((BaseGLCanvas) this).mRenderGroup.draw(drawAttribute);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.BaseGLCanvas
    public void initialize() {
        super.initialize();
        GLES20.glEnable(3024);
        GLES20.glLineWidth(1.0f);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void prepareBlurRenders() {
        if (((BaseGLCanvas) this).mRenderGroup.getRender(FilterInfo.FILTER_ID_BLUR) == null) {
            if (((BaseGLCanvas) this).mRenderCaches.getRender(FilterInfo.FILTER_ID_BLUR) == null) {
                prepareEffectRenders(false, FilterInfo.FILTER_ID_BLUR);
            }
            ((BaseGLCanvas) this).mRenderGroup.addRender(((BaseGLCanvas) this).mRenderCaches.getRender(FilterInfo.FILTER_ID_BLUR));
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void prepareEffectRenders(boolean z, int i) {
        if (((BaseGLCanvas) this).mRenderCaches.isNeedInit(i)) {
            EffectController.getInstance().getEffectGroup(this, ((BaseGLCanvas) this).mRenderCaches, z, false, i);
        }
    }

    public void setFrameBufferCallback(Render.FrameBufferCallback frameBufferCallback, int i) {
        ((BaseGLCanvas) this).mRenderGroup.setFrameBufferCallback(frameBufferCallback, i);
    }

    public void setKaleidoscope(String str) {
        ((BaseGLCanvas) this).mRenderGroup.setKaleidoscope(str);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void setSize(int i, int i2) {
        super.setSize(i, i2);
        Log.c(TAG, "setSize: size=" + i + "x" + i2 + " modelMatrix=" + Util.dumpMatrix(((BaseGLCanvas) this).mState.getModelMatrix()));
    }

    public void setSticker(String str) {
        ((BaseGLCanvas) this).mRenderGroup.setSticker(str);
    }
}
