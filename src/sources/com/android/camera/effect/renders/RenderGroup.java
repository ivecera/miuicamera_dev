package com.android.camera.effect.renders;

import android.opengl.GLES20;
import android.util.SparseArray;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.renders.Render;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import java.util.ArrayList;
import java.util.Iterator;

public class RenderGroup extends Render {
    private static final String TAG = "RenderGroup";
    protected int mParentFrameBufferIdOld;
    private ArrayList<Render> mPartRenders = new ArrayList<>();
    private ArrayList<Render> mRenders = new ArrayList<>();
    private SparseArray<Render> mRendersMap = new SparseArray<>();

    public RenderGroup(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public RenderGroup(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    private void initOrientation(Render render) {
        if (render != null) {
            render.setOrientation(((Render) this).mOrientation);
            render.setJpegOrientation(((Render) this).mJpegOrientation);
            render.setShootRotation(((Render) this).mShootRotation);
        }
    }

    private boolean recordRender(Render render) {
        int id = render == null ? FilterInfo.FILTER_ID_NONE : render.getId();
        if (this.mRendersMap.indexOfKey(id) < 0) {
            this.mRendersMap.put(id, render);
            return true;
        }
        String str = TAG;
        Log.e(str, "already added render with id " + Integer.toHexString(id), new RuntimeException());
        return false;
    }

    private void setSize(Render render) {
        if (render != null) {
            if (!(((Render) this).mPreviewWidth == 0 && ((Render) this).mPreviewHeight == 0)) {
                render.setPreviewSize(((Render) this).mPreviewWidth, ((Render) this).mPreviewHeight);
            }
            if (((Render) this).mViewportWidth != 0 || ((Render) this).mViewportHeight != 0) {
                render.setViewportSize(((Render) this).mViewportWidth, ((Render) this).mViewportHeight);
            }
        }
    }

    public void addPartRender(Render render) {
        this.mPartRenders.add(render);
    }

    public void addRender(Render render) {
        if (recordRender(render)) {
            this.mRenders.add(render);
            setSize(render);
            initOrientation(render);
        }
    }

    public void beginBindFrameBuffer(int i, int i2, int i3) {
        GLES20.glBindFramebuffer(36160, i);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().identityAllM();
        ((Render) this).mOldViewportWidth = ((Render) this).mViewportWidth;
        ((Render) this).mOldViewportHeight = ((Render) this).mViewportHeight;
        this.mParentFrameBufferIdOld = ((Render) this).mParentFrameBufferId;
        setParentFrameBufferId(i);
        setViewportSize(i2, i3);
    }

    public void beginBindFrameBuffer(FrameBuffer frameBuffer) {
        GLES20.glBindFramebuffer(36160, frameBuffer.getId());
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, frameBuffer.getTexture().getId(), 0);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().identityAllM();
        ((Render) this).mOldViewportWidth = ((Render) this).mViewportWidth;
        ((Render) this).mOldViewportHeight = ((Render) this).mViewportHeight;
        this.mParentFrameBufferIdOld = ((Render) this).mParentFrameBufferId;
        setParentFrameBufferId(frameBuffer.getId());
        setViewportSize(frameBuffer.getWidth(), frameBuffer.getHeight());
    }

    public void clearPartRenders() {
        this.mPartRenders.clear();
    }

    /* access modifiers changed from: protected */
    public void clearRenders() {
        this.mRenders.clear();
        this.mRendersMap.clear();
    }

    @Override // com.android.camera.effect.renders.Render
    public void deleteBuffer() {
        super.deleteBuffer();
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.deleteBuffer();
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void destroy() {
        Iterator<Render> it = this.mRenders.iterator();
        while (it.hasNext()) {
            Render next = it.next();
            if (next != null) {
                next.destroy();
            }
        }
        clearRenders();
    }

    @Override // com.android.camera.effect.renders.Render
    public boolean draw(DrawAttribute drawAttribute) {
        Iterator<Render> it = this.mRenders.iterator();
        while (it.hasNext()) {
            Render next = it.next();
            if (next != null && next.draw(drawAttribute)) {
                return true;
            }
        }
        return false;
    }

    public void endBindFrameBuffer() {
        ((Render) this).mGLCanvas.getState().popState();
        GLES20.glBindFramebuffer(36160, this.mParentFrameBufferIdOld);
        setViewportSize(((Render) this).mOldViewportWidth, ((Render) this).mOldViewportHeight);
        setParentFrameBufferId(this.mParentFrameBufferIdOld);
    }

    public Render getPartRender(int i) {
        if (i < 0 || i >= this.mPartRenders.size()) {
            return null;
        }
        return this.mPartRenders.get(i);
    }

    public Render getRender(int i) {
        if (i < 0) {
            String str = TAG;
            Log.e(str, "invalid render id " + Integer.toHexString(i), new RuntimeException());
        }
        return this.mRendersMap.get(i);
    }

    public Render getRenderByIndex(int i) {
        if (i >= 0 && i <= this.mRenders.size() - 1) {
            return this.mRenders.get(i);
        }
        String str = TAG;
        Log.e(str, "invalid render index: " + i + " size: " + this.mRenders.size());
        return null;
    }

    /* access modifiers changed from: protected */
    public int getRenderSize() {
        return this.mRenders.size();
    }

    public ArrayList<Render> getRenders() {
        return this.mRenders;
    }

    public boolean isNeedInit(int i) {
        return i <= -1 || this.mRendersMap.indexOfKey(i) < 0;
    }

    public boolean isPartComplete(int i) {
        return this.mPartRenders.size() == i;
    }

    public void removeRender(int i) {
        if (this.mRendersMap.indexOfKey(i) >= 0) {
            Render render = getRender(i);
            this.mRenders.remove(render);
            this.mRendersMap.delete(i);
            if (render != null) {
                render.destroy();
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setEffectRangeAttribute(EffectController.EffectRectAttribute effectRectAttribute) {
        super.setEffectRangeAttribute(effectRectAttribute);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setEffectRangeAttribute(effectRectAttribute);
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setFrameBufferCallback(Render.FrameBufferCallback frameBufferCallback, int i) {
        Iterator<Render> it = this.mRenders.iterator();
        while (it.hasNext()) {
            Render next = it.next();
            if (next != null) {
                next.setFrameBufferCallback(frameBufferCallback, i);
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setJpegOrientation(int i) {
        if (((Render) this).mJpegOrientation != i) {
            super.setJpegOrientation(i);
            if (!this.mRenders.isEmpty()) {
                Iterator<Render> it = this.mRenders.iterator();
                while (it.hasNext()) {
                    Render next = it.next();
                    if (next != null) {
                        next.setJpegOrientation(i);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setKaleidoscope(String str) {
        Iterator<Render> it = this.mRenders.iterator();
        while (it.hasNext()) {
            Render next = it.next();
            if (next != null) {
                next.setKaleidoscope(str);
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setMirror(boolean z) {
        super.setMirror(z);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setMirror(z);
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setOrientation(int i) {
        if (((Render) this).mOrientation != i) {
            super.setOrientation(i);
            if (!this.mRenders.isEmpty()) {
                Iterator<Render> it = this.mRenders.iterator();
                while (it.hasNext()) {
                    Render next = it.next();
                    if (next != null) {
                        next.setOrientation(i);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.Render
    public void setParentFrameBufferId(int i) {
        super.setParentFrameBufferId(i);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setParentFrameBufferId(i);
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setPreviewSize(int i, int i2) {
        super.setPreviewSize(i, i2);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setPreviewSize(i, i2);
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setPreviousFrameBufferInfo(int i, int i2, int i3) {
        Iterator<Render> it = this.mRenders.iterator();
        while (it.hasNext()) {
            Render next = it.next();
            if (next != null) {
                next.setPreviousFrameBufferInfo(i, i2, i3);
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setShootRotation(float f2) {
        super.setShootRotation(f2);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setShootRotation(f2);
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setSnapshotSize(int i, int i2) {
        super.setSnapshotSize(i, i2);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setSnapshotSize(i, i2);
                }
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setSticker(String str) {
        Iterator<Render> it = this.mRenders.iterator();
        while (it.hasNext()) {
            Render next = it.next();
            if (next != null) {
                next.setSticker(str);
            }
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setViewportSize(int i, int i2) {
        super.setViewportSize(i, i2);
        if (!this.mRenders.isEmpty()) {
            Iterator<Render> it = this.mRenders.iterator();
            while (it.hasNext()) {
                Render next = it.next();
                if (next != null) {
                    next.setViewportSize(i, i2);
                }
            }
        }
    }
}
