package com.android.camera.effect.renders;

import android.graphics.Color;
import android.opengl.GLES20;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.gallery3d.ui.GLCanvas;

public class ZebraRender extends ConvolutionEffectRender {
    private static final String FRAG = "precision highp float; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nuniform vec2 uSize; \nuniform vec2 uStep; \nuniform float uWidth; \nuniform float uGap; \nuniform float uOffset; \nuniform float uAlpha; \nuniform vec3 uOverColor; \nuniform vec3 uUnderColor; \nuniform float uOverExposure; \nuniform float uUnderExposure; \nvoid main() { \n    vec3 color = texture2D(sTexture, vTexCoord).rgb; \n    vec3 factor = vec3(0.2125, 0.7154, 0.0721); \n    float Y = dot(color, factor); \n    vec2 coord = vTexCoord * uSize; // convert to world coordinate \n    float x = coord.x + uOffset; \n    float y = coord.y; \n    float diff; \n    if (y > x) { \n        diff = y - x; \n    } else { \n        diff = x - y + uWidth; \n    } \n    if (mod(diff, uGap + uWidth) < uWidth) { \n        if (Y >= uOverExposure) { \n            gl_FragColor = vec4(uOverColor, Y)*uAlpha; \n        } else if (Y < uUnderExposure) { \n            gl_FragColor = vec4(uUnderColor, Y)*uAlpha; \n        } else { \n            gl_FragColor = vec4(color, 1)*uAlpha; \n        } \n    } else { \n        gl_FragColor = vec4(color, 1)*uAlpha; \n    } \n}";
    protected float mGap = 12.0f;
    protected float mOffset = 2.0f;
    protected int mOverColor = 13458528;
    protected float mOverExposure = 0.99f;
    protected int mUnderColor = 7189236;
    protected float mUnderExposure = 0.01f;
    protected int mUniformGapH;
    protected int mUniformOffsetH;
    protected int mUniformOverColorH;
    protected int mUniformOverExposureH;
    protected int mUniformSizeH;
    protected int mUniformUnderColorH;
    protected int mUniformUnderExposureH;
    protected int mUniformWidthH;
    protected float mWidth = 4.0f;

    public ZebraRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public ZebraRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ShaderRender
    public boolean draw(DrawAttribute drawAttribute) {
        this.mOffset += 1.0f;
        return super.draw(drawAttribute);
    }

    @Override // com.android.camera.effect.renders.ShaderRender
    public String getFragShaderString() {
        return FRAG;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender, com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        super.initShader();
        this.mUniformSizeH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uSize");
        this.mUniformOverExposureH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uOverExposure");
        this.mUniformUnderExposureH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uUnderExposure");
        this.mUniformOverColorH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uOverColor");
        this.mUniformUnderColorH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uUnderColor");
        this.mUniformWidthH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uWidth");
        this.mUniformGapH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uGap");
        this.mUniformOffsetH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uOffset");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        GLES20.glUniform2f(this.mUniformSizeH, (float) ((Render) this).mPreviewWidth, (float) ((Render) this).mPreviewHeight);
        GLES20.glUniform1f(this.mUniformOverExposureH, this.mOverExposure);
        GLES20.glUniform1f(this.mUniformUnderExposureH, this.mUnderExposure);
        GLES20.glUniform3f(this.mUniformOverColorH, ((float) Color.red(this.mOverColor)) / 255.0f, ((float) Color.green(this.mOverColor)) / 255.0f, ((float) Color.blue(this.mOverColor)) / 255.0f);
        GLES20.glUniform3f(this.mUniformUnderColorH, ((float) Color.red(this.mUnderColor)) / 255.0f, ((float) Color.green(this.mUnderColor)) / 255.0f, ((float) Color.blue(this.mUnderColor)) / 255.0f);
        GLES20.glUniform1f(this.mUniformWidthH, this.mWidth);
        GLES20.glUniform1f(this.mUniformGapH, this.mGap);
        GLES20.glUniform1f(this.mUniformOffsetH, this.mOffset);
    }

    public void setExposureThreshold(float f2, float f3) {
        this.mOverExposure = f2;
        this.mUnderExposure = f3;
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.ConvolutionEffectRender
    public void setPreviewSize(int i, int i2) {
        super.setPreviewSize(i, i2);
    }
}
