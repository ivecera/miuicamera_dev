package com.android.camera.effect.renders;

import android.graphics.Color;
import android.graphics.RectF;
import android.opengl.GLES20;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawLineAttribute;
import com.android.camera.effect.draw_mode.DrawMeshAttribute;
import com.android.camera.effect.draw_mode.DrawMixedAttribute;
import com.android.camera.effect.draw_mode.DrawRectAttribute;
import com.android.camera.effect.draw_mode.DrawRectFTexAttribute;
import com.android.camera.effect.draw_mode.FillRectAttribute;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.GLPaint;
import com.android.gallery3d.ui.UploadedTexture;
import java.nio.Buffer;

public class BasicRender extends ShaderRender {
    private static final float BYTE_COLOR_TO_FLOAT = 0.003921569f;
    private static final int OFFSET_DRAW_LINE = 4;
    private static final int OFFSET_DRAW_RECT = 6;
    private static final int OFFSET_FILL_RECT = 0;
    private static final float[] TEXTURES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f};
    private static final float[] VERTICES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f};
    private int mUniformBlendFactorH;
    private int mUniformPaintColorH;

    public BasicRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    private void convertCoordinate(RectF rectF, RectF rectF2, BasicTexture basicTexture) {
        int width = basicTexture.getWidth();
        int height = basicTexture.getHeight();
        int textureWidth = basicTexture.getTextureWidth();
        int textureHeight = basicTexture.getTextureHeight();
        float f2 = (float) textureWidth;
        rectF.left /= f2;
        rectF.right /= f2;
        float f3 = (float) textureHeight;
        rectF.top /= f3;
        rectF.bottom /= f3;
        float f4 = ((float) width) / f2;
        if (rectF.right > f4) {
            rectF2.right = rectF2.left + ((rectF2.width() * (f4 - rectF.left)) / rectF.width());
            rectF.right = f4;
        }
        float f5 = ((float) height) / f3;
        if (rectF.bottom > f5) {
            rectF2.bottom = rectF2.top + ((rectF2.height() * (f5 - rectF.top)) / rectF.height());
            rectF.bottom = f5;
        }
    }

    private void drawLine(float f2, float f3, float f4, float f5, GLPaint gLPaint) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        initAttribPointer();
        updateViewport();
        initGLPaint(gLPaint);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4 - f2, f5 - f3, 1.0f);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
        GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, ((Render) this).mGLCanvas.getState().getAlpha());
        GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, ((Render) this).mGLCanvas.getState().getBlendAlpha());
        GLES20.glUniform4f(this.mUniformBlendFactorH, 0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(3, 4, 2);
        ((Render) this).mGLCanvas.getState().popState();
    }

    private void drawMesh(BasicTexture basicTexture, float f2, float f3, int i, int i2, int i3, int i4) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        if (bindTexture(basicTexture, 33984)) {
            setBlendEnabled(((ShaderRender) this).mBlendEnabled && ((Render) this).mGLCanvas.getState().getAlpha() < 0.95f);
            GLES20.glBindBuffer(34962, i);
            GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributePositionH, 2, 5126, false, 0, 0);
            GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributePositionH);
            GLES20.glBindBuffer(34962, i2);
            GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributeTexCoorH, 2, 5126, false, 0, 0);
            GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributeTexCoorH);
            ((Render) this).mGLCanvas.getState().pushState();
            ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
            GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
            GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
            GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, ((Render) this).mGLCanvas.getState().getAlpha());
            GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, ((Render) this).mGLCanvas.getState().getBlendAlpha());
            GLES20.glUniform4f(this.mUniformBlendFactorH, 1.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glUniform1i(((ShaderRender) this).mUniformTextureH, 0);
            GLES20.glBindBuffer(34963, i3);
            GLES20.glDrawElements(5, i4, 5121, 0);
            GLES20.glBindBuffer(34963, 0);
            GLES20.glBindBuffer(34962, 0);
            ((Render) this).mGLCanvas.getState().popState();
        }
    }

    private void drawMixed(BasicTexture basicTexture, int i, float f2, float f3, float f4, float f5, float f6) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        if (bindTexture(basicTexture, 33984)) {
            initAttribPointer();
            initGLPaint(i);
            updateViewport();
            setBlendEnabled(((ShaderRender) this).mBlendEnabled && (!basicTexture.isOpaque() || ((Render) this).mGLCanvas.getState().getAlpha() < 0.95f));
            ((Render) this).mGLCanvas.getState().pushState();
            ((Render) this).mGLCanvas.getState().translate(f3, f4, 0.0f);
            ((Render) this).mGLCanvas.getState().scale(f5, f6, 1.0f);
            GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
            GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
            GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, ((Render) this).mGLCanvas.getState().getAlpha());
            GLES20.glUniform4f(this.mUniformBlendFactorH, 1.0f - f2, 0.0f, 0.0f, f2);
            GLES20.glUniform1i(((ShaderRender) this).mUniformTextureH, 0);
            GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, ((Render) this).mGLCanvas.getState().getBlendAlpha());
            GLES20.glDrawArrays(5, 0, 4);
            ((Render) this).mGLCanvas.getState().popState();
        }
    }

    private void drawRect(float f2, float f3, float f4, float f5, GLPaint gLPaint) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        initAttribPointer();
        updateViewport();
        initGLPaint(gLPaint);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
        GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, ((Render) this).mGLCanvas.getState().getAlpha());
        GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, ((Render) this).mGLCanvas.getState().getBlendAlpha());
        GLES20.glUniform4f(this.mUniformBlendFactorH, 0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(2, 6, 4);
        ((Render) this).mGLCanvas.getState().popState();
    }

    private void drawTexture(BasicTexture basicTexture, float f2, float f3, float f4, float f5) {
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().identityTexM();
        drawTextureInternal(basicTexture, f2, f3, f4, f5);
        ((Render) this).mGLCanvas.getState().popState();
    }

    private void drawTexture(BasicTexture basicTexture, RectF rectF, RectF rectF2) {
        if (rectF2.width() > 0.0f && rectF2.height() > 0.0f && basicTexture.onBind(((Render) this).mGLCanvas)) {
            convertCoordinate(rectF, rectF2, basicTexture);
            ((Render) this).mGLCanvas.getState().pushState();
            ((Render) this).mGLCanvas.getState().setTexMatrix(rectF.left, rectF.top, rectF.right, rectF.bottom);
            drawTextureInternal(basicTexture, rectF2.left, rectF2.top, rectF2.width(), rectF2.height());
            ((Render) this).mGLCanvas.getState().popState();
        }
    }

    private void drawTextureInternal(BasicTexture basicTexture, float f2, float f3, float f4, float f5) {
        if (f4 > 0.0f && f5 > 0.0f) {
            GLES20.glUseProgram(((ShaderRender) this).mProgram);
            if (bindTexture(basicTexture, 33984)) {
                GLES20.glUniform4f(this.mUniformBlendFactorH, 1.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glUniform1i(((ShaderRender) this).mUniformTextureH, 0);
                initAttribPointer();
                updateViewport();
                float alpha = ((Render) this).mGLCanvas.getState().getAlpha();
                float blendAlpha = ((Render) this).mGLCanvas.getState().getBlendAlpha();
                setBlendEnabled(((ShaderRender) this).mBlendEnabled && (!basicTexture.isOpaque() || alpha < 0.95f || blendAlpha >= 0.0f), basicTexture instanceof UploadedTexture ? ((UploadedTexture) basicTexture).isPremultiplied() : false);
                ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
                ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
                GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
                GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
                GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, alpha);
                GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, blendAlpha);
                GLES20.glDrawArrays(5, 0, 4);
            }
        }
    }

    private void fillRect(float f2, float f3, float f4, float f5, int i) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        initAttribPointer();
        updateViewport();
        initGLPaint(i);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
        GLES20.glUniform1f(((ShaderRender) this).mUniformAlphaH, ((Render) this).mGLCanvas.getState().getAlpha());
        GLES20.glUniform1f(((ShaderRender) this).mUniformBlendAlphaH, ((Render) this).mGLCanvas.getState().getBlendAlpha());
        GLES20.glUniform4f(this.mUniformBlendFactorH, 0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(5, 0, 4);
        ((Render) this).mGLCanvas.getState().popState();
    }

    private void initAttribPointer() {
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributePositionH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mVertexBuffer);
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributeTexCoorH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributePositionH);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributeTexCoorH);
    }

    private void initGLPaint(int i) {
        float alpha = ((float) Color.alpha(i)) * BYTE_COLOR_TO_FLOAT;
        setBlendEnabled(((ShaderRender) this).mBlendEnabled && (alpha < 0.95f || ((Render) this).mGLCanvas.getState().getAlpha() < 0.95f));
        GLES20.glUniform4f(this.mUniformPaintColorH, ((float) Color.red(i)) * BYTE_COLOR_TO_FLOAT, ((float) Color.green(i)) * BYTE_COLOR_TO_FLOAT, ((float) Color.blue(i)) * BYTE_COLOR_TO_FLOAT, alpha);
    }

    private void initGLPaint(GLPaint gLPaint) {
        initGLPaint(gLPaint.getColor());
        GLES20.glLineWidth(gLPaint.getLineWidth());
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            return false;
        }
        int target = drawAttribute.getTarget();
        if (target == 0) {
            DrawLineAttribute drawLineAttribute = (DrawLineAttribute) drawAttribute;
            drawLine(drawLineAttribute.mX1, drawLineAttribute.mY1, drawLineAttribute.mX2, drawLineAttribute.mY2, drawLineAttribute.mGLPaint);
        } else if (target == 1) {
            DrawRectAttribute drawRectAttribute = (DrawRectAttribute) drawAttribute;
            drawRect(drawRectAttribute.mX, drawRectAttribute.mY, drawRectAttribute.mWidth, drawRectAttribute.mHeight, drawRectAttribute.mGLPaint);
        } else if (target == 2) {
            DrawMeshAttribute drawMeshAttribute = (DrawMeshAttribute) drawAttribute;
            drawMesh(drawMeshAttribute.mBasicTexture, drawMeshAttribute.mX, drawMeshAttribute.mY, drawMeshAttribute.mXYBuffer, drawMeshAttribute.mUVBuffer, drawMeshAttribute.mIndexBuffer, drawMeshAttribute.mIndexCount);
        } else if (target == 3) {
            DrawMixedAttribute drawMixedAttribute = (DrawMixedAttribute) drawAttribute;
            drawMixed(drawMixedAttribute.mBasicTexture, drawMixedAttribute.mToColor, drawMixedAttribute.mRatio, drawMixedAttribute.mX, drawMixedAttribute.mY, drawMixedAttribute.mWidth, drawMixedAttribute.mHeight);
        } else if (target == 4) {
            FillRectAttribute fillRectAttribute = (FillRectAttribute) drawAttribute;
            fillRect(fillRectAttribute.mX, fillRectAttribute.mY, fillRectAttribute.mWidth, fillRectAttribute.mHeight, fillRectAttribute.mColor);
        } else if (target == 5) {
            DrawBasicTexAttribute drawBasicTexAttribute = (DrawBasicTexAttribute) drawAttribute;
            drawTexture(drawBasicTexAttribute.mBasicTexture, (float) drawBasicTexAttribute.mX, (float) drawBasicTexAttribute.mY, (float) drawBasicTexAttribute.mWidth, (float) drawBasicTexAttribute.mHeight);
        } else if (target == 7) {
            DrawRectFTexAttribute drawRectFTexAttribute = (DrawRectFTexAttribute) drawAttribute;
            drawTexture(drawRectFTexAttribute.mBasicTexture, drawRectFTexAttribute.mSourceRectF, drawRectFTexAttribute.mTargetRectF);
        }
        return true;
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return ShaderUtil.loadFromAssetsFile("frag_normal.txt");
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
            this.mUniformBlendFactorH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uBlendFactor");
            this.mUniformPaintColorH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uPaintColor");
            ((ShaderRender) this).mAttributePositionH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aPosition");
            ((ShaderRender) this).mAttributeTexCoorH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aTexCoord");
            return;
        }
        throw new IllegalArgumentException(BasicRender.class + ": mProgram = 0");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initSupportAttriList() {
        ((ShaderRender) this).mAttriSupportedList.add(0);
        ((ShaderRender) this).mAttriSupportedList.add(1);
        ((ShaderRender) this).mAttriSupportedList.add(2);
        ((ShaderRender) this).mAttriSupportedList.add(3);
        ((ShaderRender) this).mAttriSupportedList.add(4);
        ((ShaderRender) this).mAttriSupportedList.add(5);
        ((ShaderRender) this).mAttriSupportedList.add(7);
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
