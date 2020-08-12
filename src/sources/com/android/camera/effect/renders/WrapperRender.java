package com.android.camera.effect.renders;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawIntTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.miui.filtersdk.filter.base.GPUImageFilter;

public class WrapperRender extends ShaderRender {
    private static final String TAG = "WrapperRender";
    private static final float[] VERTICES = {0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private float[] mConvertedVertex = new float[16];
    protected GPUImageFilter mFilter;
    private float mRenderHeight;
    private float mRenderWidth;
    private float mRenderX;
    private float mRenderY;

    public WrapperRender(GLCanvas gLCanvas, int i, GPUImageFilter gPUImageFilter) {
        super(gLCanvas, i);
        this.mFilter = gPUImageFilter;
        initFilter();
    }

    private void checkRenderRect(float f2, float f3, float f4, float f5) {
        if (this.mRenderX != f2 || this.mRenderY != f3 || this.mRenderWidth != f4 || this.mRenderHeight != f5) {
            this.mRenderX = f2;
            this.mRenderY = f3;
            this.mRenderWidth = f4;
            this.mRenderHeight = f5;
            updateVertexData(f2, f3, f4, f5);
        }
    }

    private void updateVertexData(float f2, float f3, float f4, float f5) {
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
        Matrix.multiplyMM(this.mConvertedVertex, 0, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0, VERTICES, 0);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 2; i2++) {
                ((ShaderRender) this).mVertexBuffer.put(this.mConvertedVertex[(i * 4) + i2]);
            }
        }
        ((ShaderRender) this).mVertexBuffer.position(0);
        Matrix.multiplyMM(this.mConvertedVertex, 0, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0, VERTICES, 0);
        for (int i3 = 0; i3 < 4; i3++) {
            for (int i4 = 0; i4 < 2; i4++) {
                ((ShaderRender) this).mTexCoorBuffer.put(this.mConvertedVertex[(i3 * 4) + i4]);
            }
        }
        ((ShaderRender) this).mTexCoorBuffer.position(0);
        ((Render) this).mGLCanvas.getState().popState();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public void destroy() {
        super.destroy();
        releaseFilter();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            String str = TAG;
            Log.w(str, "unsupported target " + drawAttribute.getTarget());
            return false;
        } else if (this.mFilter == null) {
            Log.e(TAG, "null filter!");
            return false;
        } else {
            int target = drawAttribute.getTarget();
            if (target == 5) {
                DrawBasicTexAttribute drawBasicTexAttribute = (DrawBasicTexAttribute) drawAttribute;
                drawTexture(drawBasicTexAttribute.mBasicTexture, (float) drawBasicTexAttribute.mX, (float) drawBasicTexAttribute.mY, (float) drawBasicTexAttribute.mWidth, (float) drawBasicTexAttribute.mHeight);
                return true;
            } else if (target != 6) {
                return true;
            } else {
                DrawIntTexAttribute drawIntTexAttribute = (DrawIntTexAttribute) drawAttribute;
                drawTexture(drawIntTexAttribute.mTexId, (float) drawIntTexAttribute.mX, (float) drawIntTexAttribute.mY, (float) drawIntTexAttribute.mWidth, (float) drawIntTexAttribute.mHeight);
                return true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawTexture(int i, float f2, float f3, float f4, float f5) {
        int i2 = ((ShaderRender) this).mProgram;
        if (i2 != 0) {
            GLES20.glUseProgram(i2);
        }
        bindTexture(i, 33984);
        setBlendEnabled(false);
        updateViewport();
        checkRenderRect(f2, f3, f4, f5);
        this.mFilter.onDrawFrame(i, ((ShaderRender) this).mVertexBuffer, ((ShaderRender) this).mTexCoorBuffer);
    }

    /* access modifiers changed from: protected */
    public void drawTexture(BasicTexture basicTexture, float f2, float f3, float f4, float f5) {
        int i = ((ShaderRender) this).mProgram;
        if (i != 0) {
            GLES20.glUseProgram(i);
        }
        if (basicTexture.onBind(((Render) this).mGLCanvas) && bindTexture(basicTexture, 33984)) {
            setBlendEnabled(false);
            updateViewport();
            checkRenderRect(f2, f3, f4, f5);
            this.mFilter.onDrawFrame(basicTexture.getId(), ((ShaderRender) this).mVertexBuffer, ((ShaderRender) this).mTexCoorBuffer);
        }
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return null;
    }

    public void initFilter() {
        GPUImageFilter gPUImageFilter = this.mFilter;
        if (gPUImageFilter != null && !gPUImageFilter.isInitialized()) {
            this.mFilter.init();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initShader() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initSupportAttriList() {
        ((ShaderRender) this).mAttriSupportedList.add(5);
        ((ShaderRender) this).mAttriSupportedList.add(6);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initVertexData() {
        int length = (VERTICES.length * 32) / 8;
        ((ShaderRender) this).mVertexBuffer = ShaderRender.allocateByteBuffer(length).asFloatBuffer();
        ((ShaderRender) this).mTexCoorBuffer = ShaderRender.allocateByteBuffer(length).asFloatBuffer();
    }

    public void releaseFilter() {
        GPUImageFilter gPUImageFilter = this.mFilter;
        if (gPUImageFilter != null && gPUImageFilter.isInitialized()) {
            this.mFilter.destroy();
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setViewportSize(int i, int i2) {
        super.setViewportSize(i, i2);
        this.mRenderWidth = 0.0f;
        this.mRenderHeight = 0.0f;
        GPUImageFilter gPUImageFilter = this.mFilter;
        if (gPUImageFilter != null) {
            gPUImageFilter.onDisplaySizeChanged(i, i2);
        }
    }
}
