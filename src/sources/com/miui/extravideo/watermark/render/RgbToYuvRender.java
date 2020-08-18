package com.miui.extravideo.watermark.render;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.miui.extravideo.watermark.gles.ShaderProgram;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class RgbToYuvRender {
    private static final String FRAGMENT_SHADER = "precision highp float; \nuniform sampler2D sTexture; \nuniform vec2 uSize; \nvarying vec2 vTexCoord; \nfloat getY(float x, float y) { \n    vec3 pix = texture2D(sTexture, vec2(x, y)).rgb; \n    return 0.299 * pix.r + 0.587 * pix.g + 0.114 * pix.b; \n} \nfloat getU(float x, float y) { \n    vec3 pix = texture2D(sTexture, vec2(x, y)).rgb; \n    return clamp(-0.16874 * pix.r - 0.33126 * pix.g + 0.5 * pix.b + 0.5, 0.0, 1.0); \n} \nfloat getV(float x, float y) { \n    vec3 pix = texture2D(sTexture, vec2(x, y)).rgb; \n    return clamp(0.5 * pix.r - 0.41869 * pix.g - 0.08131 * pix.b + 0.5, 0.0, 1.0); \n} \nvoid main() { \n    float x, y; \n    if (vTexCoord.y < 0.5) { \n        if (vTexCoord.x < 0.25) { \n            x = (vTexCoord.x - 0.5 / uSize.x) * 4.0 + 0.5 / uSize.x; \n            y = (vTexCoord.y -  0.5 / uSize.y) * 2.0 + 0.5 / uSize.y; \n        } else if (vTexCoord.x < 0.5) { \n            x = (vTexCoord.x -  0.5 / uSize.x ) * 4.0 + 0.5 / uSize.x - 1.0; \n            y = (vTexCoord.y -  0.5 / uSize.y )* 2.0 + 0.5 / uSize.y + 1.0 / uSize.y; \n        } else {gl_FragColor = vec4(1.0,1.0,0.0,1.0);}\n        vec4 yyyy; \n        yyyy.x = getY(x + 0.0 / uSize.x, y); \n        yyyy.y = getY(x + 1.0 / uSize.x, y); \n        yyyy.z = getY(x + 2.0 / uSize.x, y); \n        yyyy.w = getY(x + 3.0 / uSize.x, y); \n        gl_FragColor = yyyy; \n    } else if (vTexCoord.y < 0.75 + 0.6 / uSize.y) { \n        if (vTexCoord.x < 0.25) { \n            x = (vTexCoord.x -  0.5 / uSize.x) * 4.0 + 0.5 / uSize.x ; \n            y = (vTexCoord.y -  0.5 / uSize.y) * 4.0 + 0.5 / uSize.y - 2.0 ; \n        } else if (vTexCoord.x < 0.5) { \n            x = (vTexCoord.x - 0.5 / uSize.x )* 4.0 + 0.5 / uSize.x - 1.0 ; \n            y = (vTexCoord.y - 0.5 / uSize.y) * 4.0 + 0.5 /uSize.y - 2.0  + 2.0 / uSize.y; \n        } else {gl_FragColor = vec4(1.0,0.0,1.0,1.0);}\n        vec4 uvuv; \n        uvuv.x = getU(x + 0.0 / uSize.x, y); \n        uvuv.y = getV(x + 0.0 / uSize.x, y); \n        uvuv.z = getU(x + 2.0 / uSize.x, y); \n        uvuv.w = getV(x + 2.0 / uSize.x, y); \n        gl_FragColor = uvuv; \n    }    else{gl_FragColor = vec4(1.0,1.0,1.0,1.0);\n}\n}\n";
    private static final String VERTEXT_SHADER = "uniform mat4 uOrientationM;\nuniform mat4 uTransformM;\nattribute vec2 aPosition;\nvarying vec2 vTexCoord;\nvoid main() {\ngl_Position = vec4(aPosition, 0.0, 1.0);\nvTexCoord = (uTransformM * ((uOrientationM * gl_Position + 1.0) * 0.5)).xy;}";
    private final byte[] FULL_QUAD_COORDINATES = {-1, 1, -1, -1, 1, 1, 1, -1};
    private ByteBuffer fullQuadVertices;
    private final float[] orientationMatrix = new float[16];
    private ShaderProgram shader;
    private final float[] transformMatrix = new float[16];

    public RgbToYuvRender() {
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
        GLES20.glDrawArrays(5, 0, 4);
    }

    public void draw(int i, int i2, int i3) {
        this.shader.use();
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, i);
        int attributeLocation = this.shader.getAttributeLocation("uOrientationM");
        int attributeLocation2 = this.shader.getAttributeLocation("uTransformM");
        GLES20.glUniform2f(this.shader.getAttributeLocation("uSize"), (float) i2, (float) i3);
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
