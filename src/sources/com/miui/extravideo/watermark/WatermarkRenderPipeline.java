package com.miui.extravideo.watermark;

import android.graphics.Bitmap;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import com.miui.extravideo.watermark.gles.EglCore;
import com.miui.extravideo.watermark.gles.GLFrameBuffer;
import com.miui.extravideo.watermark.gles.OpenGlUtils;
import com.miui.extravideo.watermark.gles.PbufferSurface;
import com.miui.extravideo.watermark.render.RgbToYuvRender;
import com.miui.extravideo.watermark.render.WatermarkTextureRenderExt;
import com.miui.extravideo.watermark.render.YuvTextureJNI;
import com.miui.extravideo.watermark.render.YuvToRgbRender;

public class WatermarkRenderPipeline {
    private EglCore mEglCore;
    private int mParentH;
    private int mParentW;
    private GLFrameBuffer mPostFrameBuffer;
    private GLFrameBuffer mPreFrameBuffer;
    private PbufferSurface mRenderSurface;
    private RgbToYuvRender mRgbToYuvRender;
    private int mWatermarkBitmapTexId;
    private int mWatermarkH;
    private int[] mWatermarkRange;
    private int mWatermarkRotation;
    private WatermarkTextureRenderExt mWatermarkTextureRenderExt;
    private int mWatermarkW;
    private YuvToRgbRender mYuvToRgbRender;

    public static int[] calcDualCameraWatermarkLocation(int i, int i2, int i3, int i4, float f2, float f3, float f4) {
        float min = ((float) Math.min(i, i2)) / 720.0f;
        int i5 = (int) (f2 * min * 1.0f);
        return new int[]{Math.round(f3 * min) & -2, Math.round(f4 * min) & -2, (int) ((((float) i5) * ((float) i3)) / ((float) i4)), i5};
    }

    private void checkPostFrameBuffer(int i, int i2) {
        GLFrameBuffer gLFrameBuffer = this.mPostFrameBuffer;
        if (gLFrameBuffer == null || gLFrameBuffer.getWidth() != i || this.mPostFrameBuffer.getHeight() != i2) {
            GLFrameBuffer gLFrameBuffer2 = this.mPostFrameBuffer;
            if (gLFrameBuffer2 != null) {
                gLFrameBuffer2.release();
            }
            this.mPostFrameBuffer = new GLFrameBuffer(i, i2);
        }
    }

    private void checkPreFrameBuffer(int i, int i2) {
        GLFrameBuffer gLFrameBuffer = this.mPreFrameBuffer;
        if (gLFrameBuffer == null || gLFrameBuffer.getWidth() != i || this.mPreFrameBuffer.getHeight() != i2) {
            GLFrameBuffer gLFrameBuffer2 = this.mPreFrameBuffer;
            if (gLFrameBuffer2 != null) {
                gLFrameBuffer2.release();
            }
            this.mPreFrameBuffer = new GLFrameBuffer(i, i2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0121  */
    private int[] getWatermarkRange(int i, int i2, int i3, boolean z, boolean z2, float f2) {
        boolean z3;
        boolean z4;
        int[] iArr = new int[6];
        if (i3 != 0) {
            if (i3 != 90) {
                if (i3 != 180) {
                    if (i3 == 270) {
                        if (z && z2) {
                            iArr[0] = 0;
                            int i4 = (int) (((float) i2) * f2);
                            iArr[1] = i2 - i4;
                            iArr[2] = i;
                            iArr[3] = i4;
                        } else if (z) {
                            iArr[0] = 0;
                            int i5 = (int) (((float) i2) * f2);
                            iArr[1] = i2 - i5;
                            iArr[2] = i / 2;
                            iArr[3] = i5;
                        } else {
                            int i6 = i / 2;
                            iArr[0] = i6;
                            int i7 = (int) (((float) i2) * f2);
                            iArr[1] = i2 - i7;
                            iArr[2] = i6;
                            iArr[3] = i7;
                            z4 = true;
                        }
                    }
                } else if (z && z2) {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    iArr[2] = (int) (((float) i) * f2);
                    iArr[3] = i2;
                } else if (z) {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    iArr[2] = (int) (((float) i) * f2);
                    iArr[3] = (int) (((float) i2) * 0.6f);
                } else {
                    iArr[0] = 0;
                    int i8 = i2 / 2;
                    iArr[1] = i8;
                    iArr[2] = (int) (((float) i) * f2);
                    iArr[3] = i8;
                }
                z3 = true;
                z4 = false;
                iArr[0] = (iArr[0] / 2) * 2;
                iArr[1] = (iArr[1] / 2) * 2;
                iArr[2] = (iArr[2] / 4) * 4;
                iArr[3] = (iArr[3] / 4) * 4;
                if (z4 || iArr[0] + iArr[2] == i) {
                    iArr[4] = 0;
                } else {
                    iArr[4] = Math.abs((i - iArr[0]) - iArr[2]);
                }
                if (z3 || iArr[1] + iArr[3] == i2) {
                    iArr[5] = 0;
                } else {
                    iArr[5] = Math.abs((i2 - iArr[1]) - iArr[3]);
                }
                return iArr;
            } else if (z && z2) {
                iArr[0] = 0;
                iArr[1] = 0;
                iArr[2] = i;
                iArr[3] = (int) (((float) i2) * f2);
            } else if (z) {
                int i9 = i / 2;
                iArr[0] = i9;
                iArr[1] = 0;
                iArr[2] = i9;
                iArr[3] = (int) (((float) i2) * f2);
                z4 = true;
                z3 = false;
                iArr[0] = (iArr[0] / 2) * 2;
                iArr[1] = (iArr[1] / 2) * 2;
                iArr[2] = (iArr[2] / 4) * 4;
                iArr[3] = (iArr[3] / 4) * 4;
                if (z4) {
                }
                iArr[4] = 0;
                if (z3) {
                }
                iArr[5] = 0;
                return iArr;
            } else {
                iArr[0] = 0;
                iArr[1] = 0;
                iArr[2] = i / 2;
                iArr[3] = (int) (((float) i2) * f2);
            }
            z4 = false;
        } else if (z && z2) {
            int i10 = (int) (((float) i) * f2);
            iArr[0] = i - i10;
            iArr[1] = 0;
            iArr[2] = i10;
            iArr[3] = i2;
            z4 = true;
            z3 = false;
            iArr[0] = (iArr[0] / 2) * 2;
            iArr[1] = (iArr[1] / 2) * 2;
            iArr[2] = (iArr[2] / 4) * 4;
            iArr[3] = (iArr[3] / 4) * 4;
            if (z4) {
            }
            iArr[4] = 0;
            if (z3) {
            }
            iArr[5] = 0;
            return iArr;
        } else if (z) {
            int i11 = (int) (((float) i) * f2);
            iArr[0] = i - i11;
            float f3 = (float) i2;
            iArr[1] = (int) (0.4f * f3);
            iArr[2] = i11;
            iArr[3] = (int) (f3 * 0.6f);
            z4 = true;
        } else {
            int i12 = (int) (((float) i) * f2);
            iArr[0] = i - i12;
            iArr[1] = 0;
            iArr[2] = i12;
            iArr[3] = i2 / 2;
            z4 = true;
            z3 = false;
            iArr[0] = (iArr[0] / 2) * 2;
            iArr[1] = (iArr[1] / 2) * 2;
            iArr[2] = (iArr[2] / 4) * 4;
            iArr[3] = (iArr[3] / 4) * 4;
            if (z4) {
            }
            iArr[4] = 0;
            if (z3) {
            }
            iArr[5] = 0;
            return iArr;
        }
        z3 = z4;
        iArr[0] = (iArr[0] / 2) * 2;
        iArr[1] = (iArr[1] / 2) * 2;
        iArr[2] = (iArr[2] / 4) * 4;
        iArr[3] = (iArr[3] / 4) * 4;
        if (z4) {
        }
        iArr[4] = 0;
        if (z3) {
        }
        iArr[5] = 0;
        return iArr;
    }

    private void initEGL(EGLContext eGLContext) {
        if (this.mEglCore == null) {
            this.mEglCore = new EglCore(eGLContext, 2);
        }
        if (this.mRenderSurface == null) {
            this.mRenderSurface = new PbufferSurface(this.mEglCore, 1, 1);
        }
    }

    public int[] getWatermarkRange(int i, int i2, int i3, boolean z, boolean z2) {
        return getWatermarkRange(i, i2, (i3 + 270) % 360, z, z2, 0.11f);
    }

    public void init(int i, int i2, Bitmap bitmap, float[] fArr, int i3) {
        int i4;
        int i5;
        this.mParentW = i;
        this.mParentH = i2;
        this.mWatermarkRotation = i3;
        initEGL(null);
        this.mWatermarkRange = getWatermarkRange(i, i2, i3, true, false);
        int[] iArr = this.mWatermarkRange;
        this.mWatermarkW = iArr[2];
        this.mWatermarkH = iArr[3];
        this.mRenderSurface.makeCurrent();
        this.mPreFrameBuffer = new GLFrameBuffer(this.mWatermarkW, this.mWatermarkH);
        this.mPostFrameBuffer = new GLFrameBuffer(this.mWatermarkW, this.mWatermarkH);
        this.mYuvToRgbRender = new YuvToRgbRender();
        this.mRgbToYuvRender = new RgbToYuvRender();
        int i6 = this.mWatermarkRotation;
        if ((i6 + 360) % 360 == 90 || (i6 + 360) % 360 == 270) {
            i4 = i;
            i5 = i2;
        } else {
            i5 = i;
            i4 = i2;
        }
        int[] calcDualCameraWatermarkLocation = calcDualCameraWatermarkLocation(i5, i4, bitmap.getWidth(), bitmap.getHeight(), fArr[0], fArr[1], fArr[2]);
        int i7 = calcDualCameraWatermarkLocation[0];
        int[] iArr2 = this.mWatermarkRange;
        calcDualCameraWatermarkLocation[0] = i7 - iArr2[4];
        calcDualCameraWatermarkLocation[1] = calcDualCameraWatermarkLocation[1] - iArr2[5];
        int i8 = this.mWatermarkW;
        int i9 = this.mWatermarkH;
        if (i8 <= i9) {
            i8 = i9;
        }
        int i10 = this.mWatermarkW;
        int i11 = this.mWatermarkH;
        if (i10 > i11) {
            i10 = i11;
        }
        this.mWatermarkTextureRenderExt = new WatermarkTextureRenderExt(i8, i10, calcDualCameraWatermarkLocation);
        this.mWatermarkBitmapTexId = OpenGlUtils.loadTexture(bitmap, -1, true);
        this.mRenderSurface.makeNothingCurrent();
    }

    public void process(byte[] bArr) {
        this.mRenderSurface.makeCurrent();
        checkPreFrameBuffer(this.mWatermarkW, this.mWatermarkH);
        GLES20.glBindFramebuffer(36160, this.mPreFrameBuffer.getFrameBufferId());
        GLES20.glViewport(0, 0, this.mWatermarkW, this.mWatermarkH);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        this.mYuvToRgbRender.draw(bArr, this.mParentW, this.mParentH, this.mWatermarkRange);
        this.mWatermarkTextureRenderExt.draw(this.mWatermarkBitmapTexId, this.mWatermarkRotation);
        GLES20.glBindFramebuffer(36160, 0);
        checkPostFrameBuffer(this.mWatermarkW, this.mWatermarkH);
        GLES20.glBindFramebuffer(36160, this.mPostFrameBuffer.getFrameBufferId());
        GLES20.glViewport(0, 0, this.mWatermarkW, this.mWatermarkH);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        this.mRgbToYuvRender.draw(this.mPreFrameBuffer.getFrameBufferTextureId(), this.mWatermarkW, this.mWatermarkH);
        int[] iArr = this.mWatermarkRange;
        int i = iArr[1];
        int i2 = this.mParentW;
        YuvTextureJNI.merge(this.mWatermarkW, this.mWatermarkH, (i * i2) + iArr[0] + 0, ((iArr[1] >> 1) * i2) + iArr[0] + 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.mRenderSurface.makeNothingCurrent();
    }

    public void release() {
        PbufferSurface pbufferSurface = this.mRenderSurface;
        if (pbufferSurface != null) {
            pbufferSurface.release();
            this.mRenderSurface = null;
        }
        EglCore eglCore = this.mEglCore;
        if (eglCore != null) {
            eglCore.release();
            this.mEglCore = null;
        }
    }
}
