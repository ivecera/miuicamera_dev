package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawIntTexAttribute;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import java.util.ArrayList;
import java.util.Locale;

public class PipeRender extends RenderGroup {
    private static final boolean DUMP_TEXTURE = false;
    private static final String TAG = "PipeRender";
    private int mBufferHeight;
    private int mBufferWidth;
    private int[] mFrameBufferTextures;
    private int[] mFrameBuffers;

    public PipeRender(GLCanvas gLCanvas) {
        super(gLCanvas);
    }

    public PipeRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
    }

    private synchronized void destroyFrameBuffers() {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("destroyFrameBuffers: count=");
        sb.append(this.mFrameBuffers == null ? 0 : this.mFrameBuffers.length);
        Log.v(str, sb.toString());
        if (this.mFrameBuffers != null) {
            for (int i = 0; i < this.mFrameBuffers.length; i++) {
                Log.d(TAG, String.format(Locale.ENGLISH, "delete fbo thread=%d id=%d", Long.valueOf(Thread.currentThread().getId()), Integer.valueOf(this.mFrameBuffers[i])));
            }
        }
        if (this.mFrameBufferTextures != null) {
            GLES20.glDeleteTextures(this.mFrameBufferTextures.length, this.mFrameBufferTextures, 0);
            this.mFrameBufferTextures = null;
        }
        if (this.mFrameBuffers != null) {
            GLES20.glDeleteFramebuffers(this.mFrameBuffers.length, this.mFrameBuffers, 0);
            this.mFrameBuffers = null;
        }
    }

    private synchronized void initFrameBuffers(int i, int i2, int i3) {
        if (i > 0) {
            Log.v(TAG, String.format(Locale.ENGLISH, "initFrameBuffers: count=%d size=%dx%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
            this.mFrameBuffers = new int[i];
            this.mFrameBufferTextures = new int[i];
            for (int i4 = 0; i4 < i; i4++) {
                GLES20.glGenFramebuffers(1, this.mFrameBuffers, i4);
                GLES20.glGenTextures(1, this.mFrameBufferTextures, i4);
                GLES20.glBindTexture(3553, this.mFrameBufferTextures[i4]);
                GLES20.glTexImage2D(3553, 0, 6408, i2, i3, 0, 6408, 5121, null);
                GLES20.glTexParameterf(3553, 10240, 9729.0f);
                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                GLES20.glTexParameterf(3553, 10243, 33071.0f);
                GLES20.glBindFramebuffer(36160, this.mFrameBuffers[i4]);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.mFrameBufferTextures[i4], 0);
                Log.v(TAG, String.format(Locale.ENGLISH, "fbo[%d]: %d %d %d*%d thread=%d", Integer.valueOf(i4), Integer.valueOf(this.mFrameBuffers[i4]), Integer.valueOf(this.mFrameBufferTextures[i4]), Integer.valueOf(i2), Integer.valueOf(i3), Long.valueOf(Thread.currentThread().getId())));
                GLES20.glBindTexture(3553, 0);
                GLES20.glBindFramebuffer(36160, 0);
            }
        }
    }

    @Override // com.android.camera.effect.renders.RenderGroup
    public void addRender(Render render) {
        int i;
        int i2;
        super.addRender(render);
        int renderSize = getRenderSize() - 1;
        int[] iArr = this.mFrameBuffers;
        if ((iArr == null || renderSize > iArr.length) && (i = this.mBufferWidth) > 0 && (i2 = this.mBufferHeight) > 0) {
            reInitFrameBuffers(i, i2);
        }
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public void deleteBuffer() {
        super.deleteBuffer();
        destroyFrameBuffers();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public boolean draw(DrawAttribute drawAttribute) {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ArrayList<Render> arrayList;
        char c2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        boolean z2;
        int i12;
        int i13;
        if (this.mFrameBuffers == null || this.mFrameBufferTextures == null) {
            Log.e(TAG, "framebuffer hasn't been initialized");
            return false;
        }
        int target = drawAttribute.getTarget();
        if (target == 5) {
            DrawBasicTexAttribute drawBasicTexAttribute = (DrawBasicTexAttribute) drawAttribute;
            i4 = drawBasicTexAttribute.mX;
            i3 = drawBasicTexAttribute.mY;
            i2 = drawBasicTexAttribute.mWidth;
            i = drawBasicTexAttribute.mHeight;
            i11 = drawBasicTexAttribute.mBasicTexture.getId();
            z2 = drawBasicTexAttribute.mIsSnapshot;
        } else if (target != 6) {
            if (target != 11) {
                Log.w(TAG, "unsupported target " + target);
                i5 = 0;
                i4 = 0;
                i3 = 0;
                i2 = 0;
                i = 0;
                z = false;
            } else {
                DrawYuvAttribute drawYuvAttribute = (DrawYuvAttribute) drawAttribute;
                if (drawYuvAttribute.mBlockWidth == 0 && drawYuvAttribute.mBlockHeight == 0) {
                    i12 = drawYuvAttribute.mPictureSize.getWidth();
                    i13 = drawYuvAttribute.mPictureSize.getHeight();
                } else {
                    i12 = drawYuvAttribute.mBlockWidth;
                    i13 = drawYuvAttribute.mBlockHeight;
                }
                i = i13;
                i2 = i12;
                i5 = 0;
                i4 = 0;
                i3 = 0;
                z = true;
            }
            if (i2 != 0 || i == 0) {
                Log.e(TAG, String.format(Locale.ENGLISH, "invalid size: %dx%d", Integer.valueOf(i2), Integer.valueOf(i)));
                return false;
            }
            int i14 = this.mBufferWidth;
            int i15 = this.mBufferHeight;
            ArrayList<Render> renders = getRenders();
            if (renders == null) {
                return true;
            }
            int size = renders.size();
            DrawIntTexAttribute drawIntTexAttribute = null;
            int i16 = 0;
            while (i16 < size) {
                Render render = renders.get(i16);
                boolean z3 = i16 < size + -1;
                if (z3) {
                    beginBindFrameBuffer(this.mFrameBuffers[i16], i14, i15);
                }
                if (i16 == 0) {
                    if (11 == target || !z3) {
                        i7 = i16;
                        c2 = 11;
                        arrayList = renders;
                        i10 = i15;
                        i6 = size;
                        render.draw(drawAttribute);
                        i14 = i14;
                    } else {
                        i7 = i16;
                        c2 = 11;
                        i6 = size;
                        arrayList = renders;
                        i10 = i15;
                        drawIntTexAttribute = new DrawIntTexAttribute(i5, 0, 0, i14, i10, z);
                        render.draw(drawIntTexAttribute);
                    }
                    i15 = i10;
                } else {
                    i7 = i16;
                    arrayList = renders;
                    i6 = size;
                    c2 = 11;
                    render.setPreviousFrameBufferInfo(this.mFrameBuffers[i7 - 1], i14, i15);
                    if (!z3) {
                        drawIntTexAttribute.mX = i4;
                        drawIntTexAttribute.mY = i3;
                        drawIntTexAttribute.mWidth = i2;
                        drawIntTexAttribute.mHeight = i;
                    }
                    render.draw(drawIntTexAttribute);
                }
                if (z3) {
                    i8 = i15;
                    i9 = i14;
                    drawIntTexAttribute = new DrawIntTexAttribute(this.mFrameBufferTextures[i7], 0, 0, i14, i8, z);
                    endBindFrameBuffer();
                } else {
                    i8 = i15;
                    i9 = i14;
                }
                i16 = i7 + 1;
                i14 = i9;
                i15 = i8;
                size = i6;
                renders = arrayList;
            }
            return true;
        } else {
            DrawIntTexAttribute drawIntTexAttribute2 = (DrawIntTexAttribute) drawAttribute;
            i4 = drawIntTexAttribute2.mX;
            i3 = drawIntTexAttribute2.mY;
            i2 = drawIntTexAttribute2.mWidth;
            i = drawIntTexAttribute2.mHeight;
            i11 = drawIntTexAttribute2.mTexId;
            z2 = drawIntTexAttribute2.mIsSnapshot;
        }
        z = z2;
        i5 = i11;
        if (i2 != 0) {
        }
        Log.e(TAG, String.format(Locale.ENGLISH, "invalid size: %dx%d", Integer.valueOf(i2), Integer.valueOf(i)));
        return false;
    }

    public boolean drawOnExtraFrameBufferOnce(DrawAttribute drawAttribute) {
        int i;
        int i2;
        int target = drawAttribute.getTarget();
        if (target == 5) {
            DrawBasicTexAttribute drawBasicTexAttribute = (DrawBasicTexAttribute) drawAttribute;
            i = drawBasicTexAttribute.mWidth;
            i2 = drawBasicTexAttribute.mHeight;
        } else if (target != 6) {
            String str = TAG;
            Log.w(str, "unsupported target " + target);
            i2 = 0;
            i = 0;
        } else {
            DrawIntTexAttribute drawIntTexAttribute = (DrawIntTexAttribute) drawAttribute;
            i = drawIntTexAttribute.mWidth;
            i2 = drawIntTexAttribute.mHeight;
        }
        if (i == 0 || i2 == 0) {
            Log.e(TAG, String.format(Locale.ENGLISH, "invalid size: %dx%d", Integer.valueOf(i), Integer.valueOf(i2)));
            return false;
        }
        ArrayList<Render> renders = getRenders();
        if (renders != null) {
            int size = renders.size();
            if (size == 1) {
                renders.get(0).draw(drawAttribute);
            } else {
                Log.e(TAG, String.format(Locale.ENGLISH, "renders more than 1: %d", Integer.valueOf(size)));
            }
        }
        return true;
    }

    public void reInitFrameBuffers(int i, int i2) {
        destroyFrameBuffers();
        initFrameBuffers(Math.max(2, getRenderSize() - 1), i, i2);
    }

    public void setFrameBufferSize(int i, int i2) {
        if (this.mBufferWidth != i || this.mBufferHeight != i2) {
            this.mBufferWidth = i;
            this.mBufferHeight = i2;
            reInitFrameBuffers(i, i2);
        }
    }
}
