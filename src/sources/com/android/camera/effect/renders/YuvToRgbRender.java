package com.android.camera.effect.renders;

import android.media.Image;
import android.opengl.GLES20;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.effect.ShaderUtil;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import com.android.gallery3d.ui.GLCanvas;
import com.mi.config.b;
import com.xiaomi.camera.base.ImageUtil;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Locale;

public class YuvToRgbRender extends ShaderRender {
    private static final String FRAG = "precision highp float; \nvarying vec2 vTexCoord; \nuniform sampler2D uYTexture; \nuniform sampler2D uUVTexture; \nuniform float uMtkPlatform; \nvoid main (void){ \n   float r, g, b, y, u, v; \n   y = texture2D(uYTexture, vTexCoord).r; \n   if(uMtkPlatform > 0.5){\n       u = texture2D(uUVTexture, vTexCoord).a - 0.5; \n       v = texture2D(uUVTexture, vTexCoord).r - 0.5;    }else {\n       v = texture2D(uUVTexture, vTexCoord).a - 0.5; \n       u = texture2D(uUVTexture, vTexCoord).r - 0.5; \n   }\n   r = y + 1.402 * v;\n   g = y - 0.34414 * u - 0.71414 * v;\n   b = y + 1.772 * u;\n   gl_FragColor = vec4(r, g, b, 1); \n} \n";
    private static final String TAG = "YuvToRgbRender";
    private static final float[] TEXTURES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] VERTICES = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private int mUniformMTKPlatform;
    private int mUniformUVTexture;
    private int mUniformYTexture;
    private int[] mYuvTextureIds;

    public YuvToRgbRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    private void drawTexture(int[] iArr, float f2, float f3, float f4, float f5) {
        GLES20.glUseProgram(((ShaderRender) this).mProgram);
        updateViewport();
        setBlendEnabled(false);
        ((Render) this).mGLCanvas.getState().pushState();
        ((Render) this).mGLCanvas.getState().translate(f2, f3, 0.0f);
        ((Render) this).mGLCanvas.getState().scale(f4, f5, 1.0f);
        if (iArr[0] != -1) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, iArr[0]);
            GLES20.glUniform1i(this.mUniformYTexture, 0);
        }
        if (iArr[1] != -1) {
            GLES20.glActiveTexture(33985);
            GLES20.glBindTexture(3553, iArr[1]);
            GLES20.glUniform1i(this.mUniformUVTexture, 1);
        }
        initShaderValue();
        GLES20.glDrawArrays(5, 0, 4);
        ((Render) this).mGLCanvas.getState().popState();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        if (!isAttriSupported(drawAttribute.getTarget())) {
            String str = TAG;
            Log.w(str, "unsupported target " + drawAttribute.getTarget());
            return false;
        }
        if (drawAttribute.getTarget() != 11) {
            String str2 = TAG;
            Log.w(str2, "unsupported target " + drawAttribute.getTarget());
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            DrawYuvAttribute drawYuvAttribute = (DrawYuvAttribute) drawAttribute;
            if (drawYuvAttribute.mYuvImage != null) {
                genMiYuvTextures(drawYuvAttribute);
                Log.e(TAG, "yuv image not available !!!");
            } else if (drawYuvAttribute.mBlockWidth == 0 && drawYuvAttribute.mBlockHeight == 0) {
                genYUVTextures(drawYuvAttribute);
            } else {
                genBlockYUVTextures(drawYuvAttribute);
            }
            if (drawYuvAttribute.mBlockWidth == 0 && drawYuvAttribute.mBlockHeight == 0) {
                drawTexture(this.mYuvTextureIds, 0.0f, 0.0f, (float) drawYuvAttribute.mPictureSize.getWidth(), (float) drawYuvAttribute.mPictureSize.getHeight());
            } else {
                drawTexture(this.mYuvTextureIds, 0.0f, 0.0f, (float) drawYuvAttribute.mBlockWidth, (float) drawYuvAttribute.mBlockHeight);
            }
            Log.d(TAG, String.format(Locale.ENGLISH, "draw: size=%s time=%d", drawYuvAttribute.mPictureSize, Long.valueOf(System.currentTimeMillis() - currentTimeMillis)));
        }
        return true;
    }

    public void genBlockYUVTextures(DrawYuvAttribute drawYuvAttribute) {
        drawYuvAttribute.mImage.getWidth();
        drawYuvAttribute.mImage.getHeight();
        ShaderUtil.loadYuvImageTextures(drawYuvAttribute.mBlockWidth, drawYuvAttribute.mBlockHeight, drawYuvAttribute.mOffsetY, drawYuvAttribute.mOffsetUV, this.mYuvTextureIds);
    }

    public void genMiYuvTextures(DrawYuvAttribute drawYuvAttribute) {
        MiYuvImage miYuvImage = drawYuvAttribute.mYuvImage;
        int i = miYuvImage.mWidth;
        int i2 = miYuvImage.mHeight;
        ByteBuffer yBuffer = miYuvImage.getYBuffer();
        ByteBuffer uVBuffer = drawYuvAttribute.mYuvImage.getUVBuffer();
        if (yBuffer != null && uVBuffer != null) {
            ShaderUtil.loadYuvToTextures(yBuffer, uVBuffer, i, i2, this.mYuvTextureIds);
        }
    }

    public void genYUVTextures(DrawYuvAttribute drawYuvAttribute) {
        int width = drawYuvAttribute.mImage.getWidth();
        int height = drawYuvAttribute.mImage.getHeight();
        Image.Plane[] planes = drawYuvAttribute.mImage.getPlanes();
        Image.Plane plane = planes[0];
        Image.Plane plane2 = planes[2];
        ShaderUtil.loadYuvToTextures(plane.getRowStride() == width ? plane.getBuffer() : ImageUtil.removePadding(plane, width, height), plane2.getRowStride() == width ? plane2.getBuffer() : ImageUtil.removePadding(plane2, width / 2, height / 2), width, height, this.mYuvTextureIds);
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
            ((ShaderRender) this).mAttributePositionH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aPosition");
            ((ShaderRender) this).mAttributeTexCoorH = GLES20.glGetAttribLocation(((ShaderRender) this).mProgram, "aTexCoord");
            this.mUniformYTexture = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uYTexture");
            this.mUniformUVTexture = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uUVTexture");
            this.mUniformMTKPlatform = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uMtkPlatform");
            this.mYuvTextureIds = new int[2];
            GLES20.glGenTextures(2, this.mYuvTextureIds, 0);
            Log.d(TAG, String.format(Locale.ENGLISH, "genTexture: %d %d", Integer.valueOf(this.mYuvTextureIds[0]), Integer.valueOf(this.mYuvTextureIds[1])));
            return;
        }
        throw new IllegalArgumentException(YuvToRgbRender.class + ": mProgram = 0");
    }

    /* access modifiers changed from: protected */
    public void initShaderValue() {
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributePositionH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mVertexBuffer);
        GLES20.glVertexAttribPointer(((ShaderRender) this).mAttributeTexCoorH, 2, 5126, false, 8, (Buffer) ((ShaderRender) this).mTexCoorBuffer);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributePositionH);
        GLES20.glEnableVertexAttribArray(((ShaderRender) this).mAttributeTexCoorH);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformMVPMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getFinalMatrix(), 0);
        GLES20.glUniformMatrix4fv(((ShaderRender) this).mUniformSTMatrixH, 1, false, ((Render) this).mGLCanvas.getState().getTexMatrix(), 0);
        CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
        int xiaomiYuvFormat = currentCameraCapabilities != null ? currentCameraCapabilities.getXiaomiYuvFormat() : -1;
        if (b.isMTKPlatform() || 2 == xiaomiYuvFormat) {
            GLES20.glUniform1f(this.mUniformMTKPlatform, 1.0f);
        } else {
            GLES20.glUniform1f(this.mUniformMTKPlatform, 0.0f);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.ShaderRender
    public void initSupportAttriList() {
        ((ShaderRender) this).mAttriSupportedList.add(11);
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
