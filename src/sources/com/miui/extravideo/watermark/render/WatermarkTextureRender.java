package com.miui.extravideo.watermark.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.arcsoft.camera.wideselfie.ArcWideSelfieDef;
import com.miui.extravideo.watermark.gles.ShaderProgram;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class WatermarkTextureRender {
    private static final String FRAGMENT_SHADER = "precision highp float;\nuniform sampler2D sTexture;\nvarying vec2 vTextureCoord;\nvoid main() {\ngl_FragColor = texture2D(sTexture, vTextureCoord);\n}";
    private static final String VERTEXT_SHADER = "uniform mat4 uOrientationM;\nuniform mat4 uTransformM;\nattribute vec2 aPosition;\nvarying vec2 vTextureCoord;\nvoid main() {\ngl_Position = vec4(aPosition, 0.0, 1.0);\nvTextureCoord = (uTransformM * ((uOrientationM * gl_Position + 1.0) * 0.5)).xy;}";
    private final byte[] FULL_QUAD_COORDINATES = {-1, 1, -1, -1, 1, 1, 1, -1};
    private ByteBuffer fullQuadVertices;
    private final float[] orientationMatrix = new float[16];
    private ShaderProgram shader;
    private final float[] transformMatrix = new float[16];

    public WatermarkTextureRender() {
        if (this.shader != null) {
            this.shader = null;
        }
        this.shader = new ShaderProgram();
        this.shader.create(VERTEXT_SHADER, FRAGMENT_SHADER);
        this.fullQuadVertices = ByteBuffer.allocateDirect(8);
        this.fullQuadVertices.put(this.FULL_QUAD_COORDINATES).position(0);
        Matrix.setRotateM(this.orientationMatrix, 0, 0.0f, 0.0f, 0.0f, 1.0f);
        Matrix.setIdentityM(this.transformMatrix, 0);
    }

    private void renderQuad(int i) {
        GLES20.glVertexAttribPointer(i, 2, 5120, false, 0, (Buffer) this.fullQuadVertices);
        GLES20.glEnableVertexAttribArray(i);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(768, ArcWideSelfieDef.MAsvlOffScreen.ASVL_PAF_RGB32_A8R8G8B8);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisable(3042);
    }

    public void draw(int i, int i2) {
        this.shader.use();
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, i);
        int attributeLocation = this.shader.getAttributeLocation("uOrientationM");
        int attributeLocation2 = this.shader.getAttributeLocation("uTransformM");
        Matrix.setRotateM(this.orientationMatrix, 0, (float) i2, 0.0f, 0.0f, 1.0f);
        GLES20.glUniformMatrix4fv(attributeLocation, 1, false, this.orientationMatrix, 0);
        GLES20.glUniformMatrix4fv(attributeLocation2, 1, false, this.transformMatrix, 0);
        renderQuad(this.shader.getAttributeLocation("aPosition"));
        this.shader.unUse();
    }

    public void release() {
        this.shader = null;
        this.fullQuadVertices = null;
    }
}
