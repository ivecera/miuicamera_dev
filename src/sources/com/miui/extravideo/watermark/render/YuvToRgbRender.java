package com.miui.extravideo.watermark.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.miui.extravideo.watermark.gles.OpenGlUtils;
import com.miui.extravideo.watermark.gles.ShaderProgram;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class YuvToRgbRender {
    private static final String FRAGMENT_SHADER = "precision highp float; \nvarying vec2 vTexCoord; \nuniform sampler2D uYTexture;\nuniform sampler2D uUvTexture;\nvoid main (void){ \n   float r, g, b, y, u, v; \n   y = texture2D(uYTexture, vTexCoord).r; \n   v = texture2D(uUvTexture, vTexCoord).a - 0.5; \n   u = texture2D(uUvTexture, vTexCoord).r - 0.5; \n   r = y + 1.402 * v;\n   g = y - 0.34414 * u - 0.71414 * v;\n   b = y + 1.772 * u;\n   gl_FragColor = vec4(r , g , b ,1.0);\n} \n";
    private static final String VERTEXT_SHADER = "uniform mat4 uOrientationM;\nuniform mat4 uTransformM;\nattribute vec2 aPosition;\nvarying vec2 vTexCoord;\nvoid main() {\ngl_Position = vec4(aPosition, 0.0, 1.0);\nvTexCoord = (uTransformM * ((uOrientationM * gl_Position + 1.0) * 0.5)).xy;}";
    private final byte[] FULL_QUAD_COORDINATES = {-1, 1, -1, -1, 1, 1, 1, -1};
    private ByteBuffer fullQuadVertices;
    private int mTextureUV;
    private int mTextureY;
    private final float[] orientationMatrix = new float[16];
    private ShaderProgram shader;
    private final float[] transformMatrix = new float[16];

    public YuvToRgbRender() {
        if (this.shader != null) {
            this.shader = null;
        }
        this.shader = new ShaderProgram();
        this.shader.create(VERTEXT_SHADER, FRAGMENT_SHADER);
        this.fullQuadVertices = ByteBuffer.allocateDirect(8);
        this.fullQuadVertices.put(this.FULL_QUAD_COORDINATES).position(0);
        Matrix.setRotateM(this.orientationMatrix, 0, 0.0f, 0.0f, 0.0f, 1.0f);
        Matrix.setIdentityM(this.transformMatrix, 0);
        this.mTextureY = OpenGlUtils.genTexture();
        this.mTextureUV = OpenGlUtils.genTexture();
    }

    private void renderQuad(int i) {
        GLES20.glVertexAttribPointer(i, 2, 5120, false, 0, (Buffer) this.fullQuadVertices);
        GLES20.glEnableVertexAttribArray(i);
        GLES20.glDrawArrays(5, 0, 4);
    }

    public void draw(byte[] bArr, int i, int i2, int[] iArr) {
        this.shader.use();
        int attributeLocation = this.shader.getAttributeLocation("uYTexture");
        int attributeLocation2 = this.shader.getAttributeLocation("uUvTexture");
        int attributeLocation3 = this.shader.getAttributeLocation("uOrientationM");
        int attributeLocation4 = this.shader.getAttributeLocation("uTransformM");
        GLES20.glUniformMatrix4fv(attributeLocation3, 1, false, this.orientationMatrix, 0);
        GLES20.glUniformMatrix4fv(attributeLocation4, 1, false, this.transformMatrix, 0);
        YuvTextureJNI.setup(i, i2, 1, 2, i, i);
        YuvTextureJNI.texture(bArr, (iArr[1] * i) + iArr[0], ((iArr[1] >> 1) * i) + iArr[0], iArr[2], iArr[3], this.mTextureY, this.mTextureUV, attributeLocation, attributeLocation2);
        renderQuad(this.shader.getAttributeLocation("aPosition"));
        this.shader.unUse();
    }

    public void release() {
        this.shader = null;
        this.fullQuadVertices = null;
    }
}
