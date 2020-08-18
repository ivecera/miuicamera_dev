package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.ExtTexture;
import com.android.gallery3d.ui.GLCanvas;
import java.nio.Buffer;

public class SurfaceTextureRender extends ShaderRender {
    private static final String FRAG = "#extension GL_OES_EGL_image_external : require  \nprecision mediump float; \nuniform float uAlpha; \nuniform float uMixAlpha; \nuniform samplerExternalOES sTexture; \nvarying vec2 vTexCoord; \nvoid main() \n{ \n    gl_FragColor = texture2D(sTexture, vTexCoord)*uAlpha; \n    if (uMixAlpha >= 0.0) { \n       gl_FragColor.a = uMixAlpha; \n    } \n}";
    private static final String TAG = "SurfaceTextureRender";
    private static final float[] TEXTURES = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] VERTICES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public SurfaceTextureRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    private void drawTexture(ExtTexture extTexture, float[] fArr, float f2, float f3, float f4, float f5) {
        GLES20.glUseProgram(0);
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        if (!bindTexture(extTexture, 33984)) {
            Log.e(TAG, "fail bind texture " + extTexture.getId());
            return;
        }
        initAttributePointer();
        updateViewport();
        float alpha = ((Render) this).mGLCanvas.getState().getAlpha();
        float blendAlpha = ((Render) this).mGLCanvas.getState().getBlendAlpha();
        setBlendEnabled(((ShaderRender) this).mBlendEnabled && (!extTexture.isOpaque() || alpha < 0.95f || blendAlpha >= 0.0f));
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, fArr, 0);
        GLES20.glUniform1i(((ShaderRender) this).mUniformTextureH, 0);
        GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, ((Render) this).mGLCanvas.getState().getAlpha());
        GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, blendAlpha);
        GLES20.glDrawArrays(5, 0, 4);
        ((Render) this).mGLCanvas.getState().popState();
    }

    private void initAttributePointer() {
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributePositionH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mVertexBuffer);
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributeTexCoorH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributePositionH);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributeTexCoorH);
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            return false;
        }
        DrawExtTexAttribute drawExtTexAttribute = (DrawExtTexAttribute) drawAttribute;
        drawTexture(drawExtTexAttribute.mExtTexture, drawExtTexAttribute.mTextureTransform, (float) drawExtTexAttribute.mX, (float) drawExtTexAttribute.mY, (float) drawExtTexAttribute.mWidth, (float) drawExtTexAttribute.mHeight);
        return true;
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return FRAG;
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
            ((ShaderRender) this).mUniformAlphaH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uAlpha");
            ((ShaderRender) this).mUniformBlendAlphaH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uMixAlpha");
            ((ShaderRender) this).mAttributePositionH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aPosition");
            ((ShaderRender) this).mAttributeTexCoorH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aTexCoord");
            return;
        }
        throw new IllegalArgumentException(SurfaceTextureRender.class + ": mProgram = 0");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initSupportAttriList() {
        ((ShaderRender) this).mAttriSupportedList.add(8);
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
