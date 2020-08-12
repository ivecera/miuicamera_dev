package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawIntTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.GLCanvas;
import java.nio.Buffer;

public abstract class PixelEffectRender extends ShaderRender {
    private static final String TAG = "PixelEffectRender";
    private static final float[] TEXTURES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] VERTICES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public PixelEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public PixelEffectRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    /* access modifiers changed from: protected */
    public void bindExtraTexture() {
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            return false;
        }
        int target = drawAttribute.getTarget();
        if (target == 5) {
            DrawBasicTexAttribute drawBasicTexAttribute = (DrawBasicTexAttribute) drawAttribute;
            drawTexture(drawBasicTexAttribute.mBasicTexture, (float) drawBasicTexAttribute.mX, (float) drawBasicTexAttribute.mY, (float) drawBasicTexAttribute.mWidth, (float) drawBasicTexAttribute.mHeight, drawBasicTexAttribute.mIsSnapshot);
            return true;
        } else if (target != 6) {
            return true;
        } else {
            DrawIntTexAttribute drawIntTexAttribute = (DrawIntTexAttribute) drawAttribute;
            drawTexture(drawIntTexAttribute.mTexId, (float) drawIntTexAttribute.mX, (float) drawIntTexAttribute.mY, (float) drawIntTexAttribute.mWidth, (float) drawIntTexAttribute.mHeight, drawIntTexAttribute.mIsSnapshot);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void drawTexture(int i, float f2, float f3, float f4, float f5, boolean z) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        bindTexture(i, 33984);
        bindExtraTexture();
        updateViewport();
        setBlendEnabled(false);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
        initShaderValue(z);
        GLES20.glDrawArrays(5, 0, 4);
        ((Render) this).mGLCanvas.getState().popState();
    }

    /* access modifiers changed from: protected */
    public void drawTexture(BasicTexture basicTexture, float f2, float f3, float f4, float f5, boolean z) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        if (!basicTexture.onBind(((Render) this).mGLCanvas)) {
            Log.e(TAG, "drawTexture: fail bind texture " + basicTexture.getId());
        } else if (bindTexture(basicTexture, 33984)) {
            bindExtraTexture();
            updateViewport();
            setBlendEnabled(false);
            ((Render) this).mGLCanvas.getState().pushState();
            ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
            ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
            initShaderValue(z);
            GLES20.glDrawArrays(5, 0, 4);
            ((Render) this).mGLCanvas.getState().popState();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        ((ShaderRender) this).mProgram = ShaderUtil.createProgram(getVertexShaderString(), getFragShaderString());
        int i = ((ShaderRender) this).mProgram;
        if (i != 0) {
            GLES20.glUseProgram(i);
            ((ShaderRender) this).mUniformMVPMatrixH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uMVPMatrix");
            ((ShaderRender) this).mUniformSTMatrixH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uSTMatrix");
            ((ShaderRender) this).mUniformTextureH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "sTexture");
            ((ShaderRender) this).mAttributePositionH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aPosition");
            ((ShaderRender) this).mAttributeTexCoorH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aTexCoord");
            ((ShaderRender) this).mUniformAlphaH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uAlpha");
            return;
        }
        throw new IllegalArgumentException(getClass() + ": mProgram = 0");
    }

    /* access modifiers changed from: protected */
    public void initShaderValue(boolean z) {
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributePositionH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mVertexBuffer);
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributeTexCoorH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributePositionH);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributeTexCoorH);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
        GLES20.glUniform1i(((ShaderRender) this).mUniformTextureH, 0);
        GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, z ? 1.0f : ((Render) this).mGLCanvas.getState().getAlpha());
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
        ((ShaderRender) this).mVertexBuffer = ShaderRender.allocateByteBuffer((VERTICES.length * 32) / 8).asFloatBuffer();
        ((ShaderRender) this).mVertexBuffer.put(VERTICES);
        ((ShaderRender) this).mVertexBuffer.position(0);
        ((ShaderRender) this).mTexCoorBuffer = ShaderRender.allocateByteBuffer((TEXTURES.length * 32) / 8).asFloatBuffer();
        ((ShaderRender) this).mTexCoorBuffer.put(TEXTURES);
        ((ShaderRender) this).mTexCoorBuffer.position(0);
    }
}
