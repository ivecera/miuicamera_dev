package com.miui.filtersdk.filter.base;

import android.opengl.GLES20;
import com.miui.filtersdk.BeautificationSDK;
import com.miui.filtersdk.utils.OpenGlUtils;

public class ColorLookupFilter extends BaseOriginalFilter {
    private static final String LOOKUP_FRAGMENT_SHADER_512X512 = " precision mediump float;\n varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture\n uniform lowp float strength;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 63.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1 = texture2D(inputImageTexture2, texPos1);\n     lowp vec4 newColor2 = texture2D(inputImageTexture2, texPos2);\n     \n     lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n     gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), strength);\n }";
    private static final String LOOKUP_FRAGMENT_SHADER_64X64 = " precision mediump float;\n varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2; // lookup texture\n uniform lowp float strength;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump float blueColor = textureColor.b * 15.0;\n     \n     mediump vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 4.0);\n     quad1.x = floor(blueColor) - (quad1.y * 4.0);\n     \n     mediump vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 4.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 4.0);\n     \n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 1.0/4.0) + 0.5/64.0 + ((1.0/4.0 - 1.0/64.0) * textureColor.r);\n     texPos1.y = (quad1.y * 1.0/4.0) + 0.5/64.0 + ((1.0/4.0 - 1.0/64.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 1.0/4.0) + 0.5/64.0 + ((1.0/4.0 - 1.0/64.0) * textureColor.r);\n     texPos2.y = (quad2.y * 1.0/4.0) + 0.5/64.0 + ((1.0/4.0 - 1.0/64.0) * textureColor.g);\n     \n     lowp vec4 newColor1 = texture2D(inputImageTexture2, texPos1);\n     lowp vec4 newColor2 = texture2D(inputImageTexture2, texPos2);\n     \n     lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n     gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), strength);\n }";
    /* access modifiers changed from: private */
    public final String mColorLookupTableName;
    private final int mColorLookupTableSize;
    private int mGLStrengthLocation;
    /* access modifiers changed from: private */
    public int mLookupSourceTexture;
    private int mLookupTextureUniform;
    private float mStrength;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ColorLookupFilter(String str, int i) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, i == 64 ? LOOKUP_FRAGMENT_SHADER_64X64 : LOOKUP_FRAGMENT_SHADER_512X512);
        this.mStrength = 1.0f;
        this.mLookupSourceTexture = -1;
        this.mColorLookupTableSize = i;
        this.mColorLookupTableName = str;
    }

    public String getColorLookupTableName() {
        return this.mColorLookupTableName;
    }

    @Override // com.miui.filtersdk.filter.base.FilterDegreeAdjustController, com.miui.filtersdk.filter.base.BaseOriginalFilter
    public boolean isDegreeAdjustSupported() {
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(1, new int[]{this.mLookupSourceTexture}, 0);
        this.mLookupSourceTexture = -1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onDrawArraysAfter() {
        if (this.mLookupSourceTexture != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, 0);
            GLES20.glActiveTexture(33984);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onDrawArraysPre() {
        if (this.mLookupSourceTexture != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.mLookupSourceTexture);
            GLES20.glUniform1i(this.mLookupTextureUniform, 3);
        }
        GLES20.glUniform1f(this.mGLStrengthLocation, this.mStrength);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mLookupTextureUniform = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture2");
        this.mGLStrengthLocation = GLES20.glGetUniformLocation(getProgram(), "strength");
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        runOnDraw(new Runnable() {
            /* class com.miui.filtersdk.filter.base.ColorLookupFilter.AnonymousClass1 */

            public void run() {
                ColorLookupFilter colorLookupFilter = ColorLookupFilter.this;
                int unused = colorLookupFilter.mLookupSourceTexture = OpenGlUtils.loadTexture(BeautificationSDK.sContext, colorLookupFilter.mColorLookupTableName);
            }
        });
    }

    @Override // com.miui.filtersdk.filter.base.FilterDegreeAdjustController, com.miui.filtersdk.filter.base.BaseOriginalFilter
    public void setDegree(int i) {
        this.mStrength = ((float) i) / 100.0f;
    }

    public String toString() {
        return "ColorLookupFilter(" + this.mColorLookupTableSize + "x" + this.mColorLookupTableSize + ", " + this.mColorLookupTableName + ")";
    }
}
