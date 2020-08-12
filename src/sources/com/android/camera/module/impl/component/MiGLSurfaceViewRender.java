package com.android.camera.module.impl.component;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import com.xiaomi.mediaprocess.OpenGlRender;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MiGLSurfaceViewRender {
    private static final String TAG = "MiGLSurfaceViewRender";
    private static final String cameraFragmentShaderString = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;uniform samplerExternalOES tex_rgb;varying vec2 textureOut;void main() {gl_FragColor = texture2D(tex_rgb, textureOut);}";
    private static final String dispalyFragmentShaderString = "precision mediump float;uniform sampler2D tex_rgb;varying vec2 textureOut;void main() {vec4 res = texture2D(tex_rgb, textureOut);float r = clamp(1.1643 * (res.r - 0.0625) + 1.5958  * (res.b - 0.5), 0.0, 1.0);float g = clamp(1.1643 * (res.r - 0.0625) - 0.39173 * (res.g - 0.5) - 0.81290 * (res.b - 0.5), 0.0, 1.0);float b = clamp(1.1643 * (res.r - 0.0625) + 2.017   * (res.g - 0.5), 0.0, 1.0);gl_FragColor = vec4(r, g, b, 1.0);}";
    private static final String previewShaderString = "precision mediump float;\nuniform sampler2D tex_rgb, filter_rgb;\nuniform bool extraVideoFilter;\nvarying vec2 textureOut;\nvoid main() {\n    vec2 uv = vec2(textureOut.x, 1.0 - textureOut.y);\n    vec4 res = texture2D(tex_rgb, uv);\n    if (extraVideoFilter) {\n        float quadx, quady, x, y;\n        float bi = floor(res.b * 63.0);\n        float mixratio = res.b * 63.0 - floor(res.b * 63.0);\n\n        quady = floor(bi / 8.0);\n        quadx = bi - quady * 8.0;\n        x = quadx * 64.0 + clamp(res.r * 63.0, 1.0, 63.0);\n        y = quady * 64.0 + clamp(res.g * 63.0, 1.0, 63.0);\n        vec2 poss1 = vec2(x / 512.0, y / 512.0);\n\n        bi = bi + 1.0;\n        quady = floor(bi / 8.0);\n        quadx = bi - quady * 8.0;\n        x = quadx * 64.0 + clamp(res.r * 63.0, 1.0, 63.0);\n        y = quady * 64.0 + clamp(res.g * 63.0, 1.0, 63.0);\n        vec2 poss2 = vec2(x / 512.0, y / 512.0);\n\n        vec4 color1 = texture2D(filter_rgb, poss1);\n        vec4 color2 = texture2D(filter_rgb, poss2);\n        res = mix(color1, color2, mixratio);\n\n}\n    gl_FragColor = res;\n}";
    private static float[] textureVertices = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final String vertexShaderString = "attribute vec4 vertexIn;attribute vec2 textureIn;varying vec2 textureOut;uniform mat4 modelViewProjectionMatrix;void main() {gl_Position = modelViewProjectionMatrix*vertexIn ;textureOut = (vec4(textureIn, 0.0, 1.0)).xy;}";
    private static float[] vertexVertices = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public int ATTRIB_TEXTURE = 0;
    public int ATTRIB_TEXTURE2 = 0;
    public int ATTRIB_VERTEX = 0;
    public int ATTRIB_VERTEX2 = 0;
    private ByteBuffer RGBColor = null;
    private final int TABLESIZE = 512;
    private int[] camera_texture_id = new int[1];
    private int extraVideoFilter;
    private int filter_rgb;
    private int mFbo;
    private int[] mFboBuffer;
    private int[] mFboTexture;
    private int mFragshaderRgb;
    private int mFramebufferTexture;
    private SurfaceTexture mInputSurfaceTexture;
    private OpenGlRender mOpenGlRender;
    private int mProgramID;
    private int mProgramID2;
    private int[] mRgbTexture = new int[1];
    /* access modifiers changed from: private */
    public GLSurfaceView mTargetSurface;
    private final SurfaceTexture.OnFrameAvailableListener mUpdateListener = new SurfaceTexture.OnFrameAvailableListener() {
        /* class com.android.camera.module.impl.component.MiGLSurfaceViewRender.AnonymousClass1 */

        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            Log.d(MiGLSurfaceViewRender.TAG, "camera surceface texture available: " + surfaceTexture);
            MiGLSurfaceViewRender.this.mTargetSurface.requestRender();
        }
    };
    private int mcamera_fragshader_texture;
    private int mcamera_texture;
    private int mmodelMatrix;
    private int mmodelMatrixPreviewFilter;
    private int mpreviewFilterProgramID;
    private float[] mtransformMatrix = new float[16];
    ByteBuffer textureVertices_buffer = null;
    ByteBuffer vertexVertices_buffer = null;

    public MiGLSurfaceViewRender(OpenGlRender openGlRender) {
        this.mOpenGlRender = openGlRender;
    }

    private void InitShaders() {
        this.vertexVertices_buffer = ByteBuffer.allocateDirect(vertexVertices.length * 4);
        this.vertexVertices_buffer.order(ByteOrder.nativeOrder());
        this.vertexVertices_buffer.asFloatBuffer().put(vertexVertices);
        int i = 0;
        this.vertexVertices_buffer.position(0);
        this.textureVertices_buffer = ByteBuffer.allocateDirect(textureVertices.length * 4);
        this.textureVertices_buffer.order(ByteOrder.nativeOrder());
        this.textureVertices_buffer.asFloatBuffer().put(textureVertices);
        this.textureVertices_buffer.position(0);
        this.mProgramID = createProgram(vertexShaderString, cameraFragmentShaderString);
        this.ATTRIB_VERTEX = GLES20.glGetAttribLocation(this.mProgramID, "vertexIn");
        if (this.ATTRIB_VERTEX == -1) {
            Log.d(TAG, "glGetAttribLocation error ");
        }
        this.ATTRIB_TEXTURE = GLES20.glGetAttribLocation(this.mProgramID, "textureIn");
        if (this.ATTRIB_TEXTURE == -1) {
            Log.d(TAG, "glGetAttribLocation error ");
        }
        GLES20.glUseProgram(this.mProgramID);
        this.mcamera_fragshader_texture = GLES20.glGetUniformLocation(this.mProgramID, "tex_rgb");
        Log.d(TAG, "glGetAttribLocation mcamera_fragshader_texture: " + this.mcamera_fragshader_texture);
        this.mmodelMatrix = GLES20.glGetUniformLocation(this.mProgramID, "modelViewProjectionMatrix");
        this.camera_texture_id = new int[1];
        GLES20.glGenTextures(1, this.camera_texture_id, 0);
        this.mcamera_texture = this.camera_texture_id[0];
        GLES20.glBindTexture(36197, this.mcamera_texture);
        GLES20.glTexParameteri(36197, 10240, 9729);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkNoGLES2Error();
        Log.d(TAG, "glGetAttribLocation mcamera_texture: " + this.mcamera_texture);
        this.mProgramID2 = createProgram(vertexShaderString, dispalyFragmentShaderString);
        this.ATTRIB_VERTEX2 = GLES20.glGetAttribLocation(this.mProgramID2, "vertexIn");
        if (this.ATTRIB_VERTEX2 < 0) {
            Log.d(TAG, "programID_2 glGet vertex Location error ");
        }
        this.ATTRIB_TEXTURE2 = GLES20.glGetAttribLocation(this.mProgramID2, "textureIn");
        if (this.ATTRIB_TEXTURE2 < 0) {
            Log.d(TAG, "programID_2 glGet texture bLocation error ");
        }
        GLES20.glUseProgram(this.mProgramID2);
        checkNoGLES2Error();
        this.mFragshaderRgb = GLES20.glGetUniformLocation(this.mProgramID2, "tex_rgb");
        Log.d(TAG, "programID_2 param ATTRIB_VERTEX2: " + this.ATTRIB_VERTEX2 + " ATTRIB_TEXTURE2:" + this.ATTRIB_TEXTURE2 + " textuer2d samp:" + this.mFragshaderRgb);
        this.mFboBuffer = new int[1];
        GLES20.glGenFramebuffers(1, this.mFboBuffer, 0);
        checkNoGLES2Error();
        this.mFbo = this.mFboBuffer[0];
        GLES20.glBindFramebuffer(36160, this.mFbo);
        checkNoGLES2Error();
        this.mFboTexture = new int[1];
        GLES20.glGenTextures(1, this.mFboTexture, 0);
        checkNoGLES2Error();
        this.mFramebufferTexture = this.mFboTexture[0];
        GLES20.glBindTexture(3553, this.mFramebufferTexture);
        checkNoGLES2Error();
        GLES20.glTexImage2D(3553, 0, 6408, 3840, 2160, 0, 6408, 5121, null);
        checkNoGLES2Error();
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        checkNoGLES2Error();
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        checkNoGLES2Error();
        GLES20.glTexParameterf(3553, 10242, 33071.0f);
        checkNoGLES2Error();
        GLES20.glTexParameterf(3553, 10243, 33071.0f);
        checkNoGLES2Error();
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mFramebufferTexture, 0);
        checkNoGLES2Error();
        GLES20.glClearColor(0.0f, 0.5f, 0.5f, 1.0f);
        checkNoGLES2Error();
        GLES20.glBindTexture(3553, 0);
        checkNoGLES2Error();
        GLES20.glBindFramebuffer(36160, 0);
        checkNoGLES2Error();
        Log.d(TAG, "fbo id:" + this.mFbo + " mFramebufferTexture:" + this.mFramebufferTexture);
        this.mpreviewFilterProgramID = createProgram(vertexShaderString, previewShaderString);
        GLES20.glUseProgram(this.mpreviewFilterProgramID);
        this.mmodelMatrixPreviewFilter = GLES20.glGetUniformLocation(this.mpreviewFilterProgramID, "modelViewProjectionMatrix");
        if (this.mmodelMatrixPreviewFilter < 0) {
            Log.d(TAG, "mpreviewFilterProgramID glGet modelViewProjectionMatrix Location error ");
        }
        this.filter_rgb = GLES20.glGetUniformLocation(this.mpreviewFilterProgramID, "filter_rgb");
        this.extraVideoFilter = GLES20.glGetUniformLocation(this.mpreviewFilterProgramID, "extraVideoFilter");
        Log.d(TAG, "glGetAttribLocation filter rgb id: " + this.filter_rgb + " extraVideoFilter id:" + this.extraVideoFilter);
        GLES20.glGenTextures(1, this.mRgbTexture, 0);
        StringBuilder sb = new StringBuilder();
        sb.append("generate texture rgb id: ");
        sb.append(this.mRgbTexture[0]);
        Log.d(TAG, sb.toString());
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, this.mRgbTexture[0]);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glUniform1i(this.filter_rgb, 1);
        GLES20.glTexImage2D(3553, 0, 6408, 512, 512, 0, 6408, 5121, this.RGBColor);
        int i2 = this.extraVideoFilter;
        if (this.RGBColor != null) {
            i = 1;
        }
        GLES20.glUniform1i(i2, i);
        checkNoGLES2Error();
    }

    private void TransferExternalImagetoFbo() {
        GLES20.glViewport(0, 0, 3840, 2160);
        GLES20.glBindFramebuffer(36160, this.mFbo);
        GLES20.glUseProgram(this.mProgramID);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, this.mcamera_texture);
        checkNoGLES2Error();
        GLES20.glUniform1i(this.mcamera_fragshader_texture, 0);
        checkNoGLES2Error();
        GLES20.glUniformMatrix4fv(this.mmodelMatrix, 1, false, this.mtransformMatrix, 0);
        checkNoGLES2Error();
        GLES20.glEnableVertexAttribArray(this.ATTRIB_VERTEX);
        checkNoGLES2Error();
        GLES20.glVertexAttribPointer(this.ATTRIB_VERTEX, 2, 5126, false, 0, (Buffer) this.vertexVertices_buffer);
        checkNoGLES2Error();
        GLES20.glEnableVertexAttribArray(this.ATTRIB_TEXTURE);
        checkNoGLES2Error();
        GLES20.glVertexAttribPointer(this.ATTRIB_TEXTURE, 2, 5126, false, 0, (Buffer) this.textureVertices_buffer);
        checkNoGLES2Error();
        GLES20.glDrawArrays(5, 0, 4);
        checkNoGLES2Error();
        GLES20.glFlush();
        checkNoGLES2Error();
        GLES20.glDisableVertexAttribArray(this.ATTRIB_VERTEX);
        GLES20.glDisableVertexAttribArray(this.ATTRIB_TEXTURE);
        checkNoGLES2Error();
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glFlush();
    }

    private static void abortUnless(boolean z, String str) {
        if (!z) {
            throw new RuntimeException(str);
        }
    }

    private static void checkNoGLES2Error() {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.e(TAG, "GLES20 error:" + glGetError);
        }
        boolean z = glGetError == 0;
        abortUnless(z, "GLES20 error: " + glGetError);
    }

    private int createProgram(String str, String str2) {
        int loadShader = loadShader(35633, str);
        int loadShader2 = loadShader(35632, str2);
        Log.d(TAG, "vertex shader: " + str + " -- " + loadShader);
        Log.d(TAG, "fragment shader: " + str2 + " -- " + loadShader2);
        int glCreateProgram = GLES20.glCreateProgram();
        abortUnless(glCreateProgram > 0, "Create OpenGL program failed.");
        Log.d(TAG, "program: " + glCreateProgram);
        if (glCreateProgram != 0) {
            GLES20.glAttachShader(glCreateProgram, loadShader);
            GLES20.glAttachShader(glCreateProgram, loadShader2);
            GLES20.glLinkProgram(glCreateProgram);
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                GLES20.glDeleteProgram(glCreateProgram);
                glCreateProgram = 0;
            }
        }
        Log.d(TAG, " end if program: " + glCreateProgram);
        return glCreateProgram;
    }

    private int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        Log.d(TAG, "shader: " + glCreateShader);
        if (glCreateShader != 0) {
            GLES20.glShaderSource(glCreateShader, str);
            GLES20.glCompileShader(glCreateShader);
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            if (iArr[0] == 0) {
                GLES20.glDeleteShader(glCreateShader);
                glCreateShader = 0;
            }
        }
        Log.d(TAG, "end shader: " + glCreateShader);
        return glCreateShader;
    }

    public void DrawCameraPreview(int i, int i2, int i3, int i4) {
        TransferExternalImagetoFbo();
        GLES20.glViewport(i, i2, i3, i4);
        GLES20.glUseProgram(this.mpreviewFilterProgramID);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.mFramebufferTexture);
        checkNoGLES2Error();
        GLES20.glUniform1i(GLES20.glGetUniformLocation(this.mpreviewFilterProgramID, "tex_rgb"), 0);
        checkNoGLES2Error();
        Matrix.setIdentityM(this.mtransformMatrix, 0);
        GLES20.glUniformMatrix4fv(this.mmodelMatrixPreviewFilter, 1, false, this.mtransformMatrix, 0);
        checkNoGLES2Error();
        int glGetAttribLocation = GLES20.glGetAttribLocation(this.mpreviewFilterProgramID, "vertexIn");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation);
        checkNoGLES2Error();
        GLES20.glVertexAttribPointer(glGetAttribLocation, 2, 5126, false, 0, (Buffer) this.vertexVertices_buffer);
        checkNoGLES2Error();
        int glGetAttribLocation2 = GLES20.glGetAttribLocation(this.mpreviewFilterProgramID, "textureIn");
        GLES20.glEnableVertexAttribArray(glGetAttribLocation2);
        checkNoGLES2Error();
        GLES20.glVertexAttribPointer(glGetAttribLocation2, 2, 5126, false, 0, (Buffer) this.textureVertices_buffer);
        checkNoGLES2Error();
        GLES20.glDrawArrays(5, 0, 4);
        checkNoGLES2Error();
        GLES20.glFlush();
        checkNoGLES2Error();
        GLES20.glDisableVertexAttribArray(glGetAttribLocation);
        GLES20.glDisableVertexAttribArray(glGetAttribLocation2);
        checkNoGLES2Error();
    }

    public void bind(Rect rect, int i, int i2) {
        TransferExternalImagetoFbo();
    }

    public void init(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "init :");
        InitShaders();
        byte[] bArr = new byte[this.vertexVertices_buffer.remaining()];
        this.vertexVertices_buffer.get(bArr);
        this.vertexVertices_buffer.position(0);
        byte[] bArr2 = new byte[this.textureVertices_buffer.remaining()];
        this.textureVertices_buffer.get(bArr2);
        this.textureVertices_buffer.position(0);
        this.mOpenGlRender.SetOpengGlRenderParams(this.mProgramID2, this.mFramebufferTexture, this.mFragshaderRgb, this.ATTRIB_VERTEX2, this.ATTRIB_TEXTURE2, bArr, bArr2);
        this.mOpenGlRender.SetCurrentGLContext(this.mFramebufferTexture);
        this.mInputSurfaceTexture = surfaceTexture;
        this.mInputSurfaceTexture.attachToGLContext(this.mcamera_texture);
    }

    public void release() {
        GLES20.glDeleteTextures(1, this.camera_texture_id, 0);
        GLES20.glDeleteTextures(1, this.mRgbTexture, 0);
        GLES20.glDeleteTextures(1, this.mRgbTexture, 0);
        GLES20.glDeleteTextures(1, this.mFboTexture, 0);
        GLES20.glDeleteFramebuffers(1, this.mFboBuffer, 0);
        GLES20.glDeleteProgram(this.mProgramID);
        GLES20.glDeleteProgram(this.mProgramID2);
        GLES20.glDeleteProgram(this.mpreviewFilterProgramID);
    }

    public void setFilterRGBColor(ByteBuffer byteBuffer) {
        this.RGBColor = byteBuffer;
    }

    public void updateTexImage() {
        this.mInputSurfaceTexture.updateTexImage();
        this.mInputSurfaceTexture.getTransformMatrix(this.mtransformMatrix);
        Matrix.setIdentityM(this.mtransformMatrix, 0);
        Matrix.rotateM(this.mtransformMatrix, 0, -90.0f, 0.0f, 0.0f, 1.0f);
    }
}
