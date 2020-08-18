package com.miui.filtersdk.filter;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Environment;
import android.util.Log;
import com.android.camera.storage.Storage;
import com.miui.filtersdk.filter.base.BaseBeautyFilter;
import com.miui.filtersdk.filter.base.GPUImageFilter;
import com.miui.filtersdk.utils.OpenGlUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class NewBeautificationFilter extends BaseBeautyFilter {
    private static final String TAG = "BeautificationFilter";
    public static final String fragmentShader = "precision highp float;                             \nvarying vec2 v_texCoord;                           \nuniform sampler2D texture;                         \nvoid main (void){                                  \n   vec3 rgb = texture2D(texture, v_texCoord).rgb;  \n   gl_FragColor = vec4(rgb, 1.0);                  \n}                                                  \n";
    public static final String vertexShader = "attribute vec4 a_position;                         \nattribute vec2 a_texCoord;                         \nvarying vec2 v_texCoord;                           \nvoid main(){                                       \n   gl_Position = a_position;                       \n   v_texCoord = a_texCoord;                        \n}                                                  \n";
    private int mWindowHeight;
    private int mWindowWidth;
    /* access modifiers changed from: private */
    public boolean mbGetValidTexureID = false;

    public static String dumpMatrix(float[] fArr) {
        int length = fArr.length;
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%f", Float.valueOf(fArr[i])));
            if (i != length - 1) {
                sb.append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0075 A[SYNTHETIC, Splitter:B:21:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    private static void dumpToBitmap(int i, int i2, ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(byteBuffer);
            String str = Environment.getExternalStorageDirectory().getPath() + "/hehe/" + System.currentTimeMillis() + Storage.JPEG_SUFFIX;
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                    Log.d(TAG, "dump to " + str);
                    try {
                        fileOutputStream2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (FileNotFoundException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    try {
                        e.printStackTrace();
                        if (fileOutputStream == null) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream2 != null) {
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                if (fileOutputStream == null) {
                    fileOutputStream.close();
                }
            }
        }
    }

    private void initBeauty() {
        ((BaseBeautyFilter) this).mBeautyProcessor.init(this.mWindowWidth, this.mWindowHeight);
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void init() {
        onInit();
        initBeauty();
    }

    @Override // com.miui.filtersdk.filter.base.BaseBeautyFilter
    public void initBeautyProcessor(int i, int i2) {
        if (((BaseBeautyFilter) this).mBeautyProcessor != null) {
            this.mWindowWidth = i;
            this.mWindowHeight = i2;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onDestroy() {
        super.onDestroy();
        ((GPUImageFilter) this).mTextureId = -1;
        this.mbGetValidTexureID = false;
        ((BaseBeautyFilter) this).mBeautyProcessor.release();
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onDisplaySizeChanged(int i, int i2) {
        super.onDisplaySizeChanged(i, i2);
        ((BaseBeautyFilter) this).mBeautyProcessor.onDisplaySizeChanged(i, i2);
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public int onDrawFrame(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        runPendingOnDrawTasks();
        if (!((GPUImageFilter) this).mIsInitialized) {
            return -1;
        }
        GLES20.glUseProgram(((GPUImageFilter) this).mGLProgId);
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(((GPUImageFilter) this).mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
        GLES20.glEnableVertexAttribArray(((GPUImageFilter) this).mGLAttribPosition);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(((GPUImageFilter) this).mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
        GLES20.glEnableVertexAttribArray(((GPUImageFilter) this).mGLAttribTextureCoordinate);
        if (((GPUImageFilter) this).mTextureId != -1 && this.mbGetValidTexureID) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, ((GPUImageFilter) this).mTextureId);
            GLES20.glUniform1i(((GPUImageFilter) this).mGLUniformTexture, 0);
        }
        onDrawArraysPre();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(((GPUImageFilter) this).mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(((GPUImageFilter) this).mGLAttribTextureCoordinate);
        onDrawArraysAfter();
        GLES20.glBindTexture(3553, 0);
        return 1;
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public int onDrawToTexture(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (((GPUImageFilter) this).mFrameBuffers == null) {
            return -1;
        }
        runPendingOnDrawTasks();
        if (!isInitialized() || ((GPUImageFilter) this).mTextureId == -1 || !this.mbGetValidTexureID) {
            return -1;
        }
        GLES20.glViewport(0, 0, ((GPUImageFilter) this).mFrameWidth, ((GPUImageFilter) this).mFrameHeight);
        GLES20.glBindFramebuffer(36160, ((GPUImageFilter) this).mFrameBuffers[0]);
        GLES20.glUseProgram(((GPUImageFilter) this).mGLProgId);
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(((GPUImageFilter) this).mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
        GLES20.glEnableVertexAttribArray(((GPUImageFilter) this).mGLAttribPosition);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(((GPUImageFilter) this).mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
        GLES20.glEnableVertexAttribArray(((GPUImageFilter) this).mGLAttribTextureCoordinate);
        if (((GPUImageFilter) this).mTextureId != -1 && this.mbGetValidTexureID) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, ((GPUImageFilter) this).mTextureId);
            GLES20.glUniform1i(((GPUImageFilter) this).mGLUniformTexture, 0);
        }
        onDrawArraysPre();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(((GPUImageFilter) this).mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(((GPUImageFilter) this).mGLAttribTextureCoordinate);
        onDrawArraysAfter();
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glViewport(0, 0, ((GPUImageFilter) this).mOutputWidth, ((GPUImageFilter) this).mOutputHeight);
        return ((GPUImageFilter) this).mFrameBufferTextures[0];
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onInit() {
        ((GPUImageFilter) this).mGLProgId = OpenGlUtils.loadProgram("attribute vec4 a_position;                         \nattribute vec2 a_texCoord;                         \nvarying vec2 v_texCoord;                           \nvoid main(){                                       \n   gl_Position = a_position;                       \n   v_texCoord = a_texCoord;                        \n}                                                  \n", fragmentShader);
        ((GPUImageFilter) this).mGLAttribPosition = GLES20.glGetAttribLocation(((GPUImageFilter) this).mGLProgId, "a_position");
        ((GPUImageFilter) this).mGLAttribTextureCoordinate = GLES20.glGetAttribLocation(((GPUImageFilter) this).mGLProgId, "a_texCoord");
        ((GPUImageFilter) this).mGLUniformTexture = GLES20.glGetUniformLocation(((GPUImageFilter) this).mGLProgId, "texture");
        initFrameBuffers(this.mWindowWidth, this.mWindowHeight);
        ((GPUImageFilter) this).mIsInitialized = true;
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void onInputSizeChanged(int i, int i2) {
        super.onInputSizeChanged(i, i2);
        ((BaseBeautyFilter) this).mBeautyProcessor.onDisplaySizeChanged(i, i2);
    }

    @Override // com.miui.filtersdk.filter.base.GPUImageFilter
    public void passPreviewFrameToTexture(final byte[] bArr, final int i, final int i2) {
        if (!hasPendingDrawTasks()) {
            runOnDraw(new Runnable() {
                /* class com.miui.filtersdk.filter.NewBeautificationFilter.AnonymousClass1 */

                public void run() {
                    NewBeautificationFilter newBeautificationFilter = NewBeautificationFilter.this;
                    int unused = ((GPUImageFilter) newBeautificationFilter).mTextureId = ((BaseBeautyFilter) newBeautificationFilter).mBeautyProcessor.beautify(bArr, i, i2);
                    boolean unused2 = NewBeautificationFilter.this.mbGetValidTexureID = true;
                }
            });
        }
    }
}
