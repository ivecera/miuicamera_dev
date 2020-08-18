package com.android.camera.effect.renders;

import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import java.util.Locale;

public class VideoRecorderRender extends RenderGroup {
    private static final String TAG = "VideoRecorderRender";
    private int mEffectId = FilterInfo.FILTER_ID_NONE;
    private boolean mFirstFrameDrawn;
    private boolean mKaleidoscopeEnabled;
    private PipeRenderPair mRenderPipe;
    private PipeRender mSecondRender;
    private boolean mStickerEnabled;
    private PipeRenderPair mStickerPipeRender;

    public VideoRecorderRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mStickerPipeRender = new PipeRenderPair(gLCanvas);
        this.mRenderPipe = new PipeRenderPair(gLCanvas);
        this.mRenderPipe.setFirstRender(new SurfaceTextureRender(gLCanvas));
        this.mSecondRender = new PipeRender(gLCanvas);
        updateSecondRender();
        addRender(this.mRenderPipe);
    }

    private boolean drawPreview(DrawAttribute drawAttribute) {
        if (!this.mFirstFrameDrawn) {
            this.mFirstFrameDrawn = true;
            setViewportSize(((Render) this).mViewportWidth, ((Render) this).mViewportHeight);
            setPreviewSize(((Render) this).mPreviewWidth, ((Render) this).mPreviewHeight);
            this.mSecondRender.setFrameBufferSize(((Render) this).mPreviewWidth, ((Render) this).mPreviewHeight);
        }
        synchronized (this) {
            super.draw(drawAttribute);
        }
        return true;
    }

    private Render getSecondRender(int i, boolean z, boolean z2) {
        Render render;
        Render render2;
        Render render3 = null;
        if (i != FilterInfo.FILTER_ID_NONE) {
            Render render4 = ((Render) this).mGLCanvas.getEffectRenderGroup().getRender(i);
            if (render4 == null) {
                ((Render) this).mGLCanvas.prepareEffectRenders(false, i);
                render = ((Render) this).mGLCanvas.getEffectRenderGroup().getRender(i);
            } else {
                render = render4;
            }
        } else {
            render = null;
        }
        if (z) {
            render2 = ((Render) this).mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_STICKER);
            if (render2 == null) {
                ((Render) this).mGLCanvas.prepareEffectRenders(false, FilterInfo.FILTER_ID_STICKER);
                render2 = ((Render) this).mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_STICKER);
            }
        } else {
            render2 = null;
        }
        if (z2) {
            Render render5 = ((Render) this).mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_KALEIDOSCOPE);
            if (render5 == null) {
                ((Render) this).mGLCanvas.prepareEffectRenders(false, FilterInfo.FILTER_ID_KALEIDOSCOPE);
                render5 = ((Render) this).mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_KALEIDOSCOPE);
            }
            render3 = render5;
            render3.setKaleidoscope(EffectController.getInstance().getCurrentKaleidoscope());
        }
        if (render != null) {
            this.mSecondRender.addRender(render);
        }
        if (render2 != null) {
            this.mSecondRender.addRender(render2);
        }
        if (render3 != null) {
            this.mSecondRender.addRender(render3);
        }
        return this.mSecondRender;
    }

    private void updateSecondRender() {
        int i = this.mEffectId;
        this.mEffectId = EffectController.getInstance().getEffectForPreview(false);
        boolean z = this.mStickerEnabled;
        this.mStickerEnabled = EffectController.getInstance().isStickerEnable();
        boolean z2 = this.mKaleidoscopeEnabled;
        this.mKaleidoscopeEnabled = EffectController.getInstance().isKaleidoscopeEnable();
        Log.d(TAG, String.format(Locale.ENGLISH, "effectId: 0x%x->0x%x stickerEnabled: %b->%b KaleidoscopeEnabled: %b->%b", Integer.valueOf(i), Integer.valueOf(this.mEffectId), Boolean.valueOf(z), Boolean.valueOf(this.mStickerEnabled), Boolean.valueOf(z2), Boolean.valueOf(this.mKaleidoscopeEnabled)));
        if (this.mEffectId != i || this.mStickerEnabled != z || this.mKaleidoscopeEnabled != z2) {
            this.mFirstFrameDrawn = false;
            this.mRenderPipe.setSecondRender(getSecondRender(this.mEffectId, this.mStickerEnabled, this.mKaleidoscopeEnabled));
        }
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public boolean draw(DrawAttribute drawAttribute) {
        if (drawAttribute.getTarget() == 8) {
            return drawPreview(drawAttribute);
        }
        String str = TAG;
        Log.e(str, "unsupported target " + drawAttribute.getTarget());
        return false;
    }

    public void setFilterId(int i) {
        if (i != this.mEffectId) {
            synchronized (this) {
                updateSecondRender();
            }
        }
    }
}
