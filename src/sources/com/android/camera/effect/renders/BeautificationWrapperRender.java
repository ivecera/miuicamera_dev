package com.android.camera.effect.renders;

import android.opengl.GLES20;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.miui.filtersdk.filter.NewBeautificationFilter;
import com.miui.filtersdk.filter.base.GPUImageFilter;
import com.miui.filtersdk.utils.TextureRotationUtil;
import java.util.Locale;

public class BeautificationWrapperRender extends WrapperRender {
    private static final String TAG = "BeautificationWrapperRender";
    private NoneEffectRender mSimpleRender;

    public BeautificationWrapperRender(GLCanvas gLCanvas, int i, GPUImageFilter gPUImageFilter, boolean z) {
        super(gLCanvas, i, gPUImageFilter);
        this.mSimpleRender = new NoneEffectRender(gLCanvas);
        adjustSize(z);
    }

    private void drawToFrameBuffer(int i, float f2, float f3, float f4, float f5) {
        int i2 = ((Render) this).mParentFrameBufferId;
        if (i2 != 0) {
            GLES20.glBindFramebuffer(36160, i2);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        }
        this.mSimpleRender.drawTexture(i, f2, f3, f4, f5, false);
    }

    private void dumpGLParams(String str) {
        boolean glIsEnabled = GLES20.glIsEnabled(2884);
        int[] iArr = new int[1];
        GLES20.glGetIntegerv(2885, iArr, 0);
        int i = iArr[0];
        GLES20.glGetIntegerv(2886, iArr, 0);
        int i2 = iArr[0];
        GLES20.glGetIntegerv(34016, iArr, 0);
        int i3 = iArr[0];
        GLES20.glGetIntegerv(32873, iArr, 0);
        int i4 = iArr[0];
        GLES20.glGetIntegerv(36006, iArr, 0);
        int i5 = iArr[0];
        int[] iArr2 = new int[4];
        GLES20.glGetIntegerv(2978, iArr2, 0);
        String str2 = TAG;
        Locale locale = Locale.ENGLISH;
        Log.d(str2, String.format(locale, str + "cullFace=%b faceMode=0x%x frontFace=0x%x activeTex=0x%x bindTex=%d bindBuf=%d viewport=[%d %d %d %d]", Boolean.valueOf(glIsEnabled), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(iArr2[0]), Integer.valueOf(iArr2[1]), Integer.valueOf(iArr2[2]), Integer.valueOf(iArr2[3])));
    }

    public void adjustSize(boolean z) {
        if (z) {
            TextureRotationUtil.adjustSize(90, false, false, ((ShaderRender) this).mVertexBuffer, ((ShaderRender) this).mTexCoorBuffer);
        } else {
            TextureRotationUtil.adjustSize(270, false, true, ((ShaderRender) this).mVertexBuffer, ((ShaderRender) this).mTexCoorBuffer);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.WrapperRender
    public void drawTexture(int i, float f2, float f3, float f4, float f5) {
        int onDrawToTexture = ((WrapperRender) this).mFilter.onDrawToTexture(i, ((ShaderRender) this).mVertexBuffer, ((ShaderRender) this).mTexCoorBuffer);
        int i2 = onDrawToTexture == -1 ? i : onDrawToTexture;
        GLES20.glDisable(2884);
        drawToFrameBuffer(i2, f2, f3, f4, f5);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.WrapperRender
    public void drawTexture(BasicTexture basicTexture, float f2, float f3, float f4, float f5) {
        int onDrawToTexture = ((WrapperRender) this).mFilter.onDrawToTexture(basicTexture.getId(), ((ShaderRender) this).mVertexBuffer, ((ShaderRender) this).mTexCoorBuffer);
        if (onDrawToTexture == -1) {
            onDrawToTexture = basicTexture.getId();
        }
        GLES20.glDisable(2884);
        drawToFrameBuffer(onDrawToTexture, f2, f3, f4, f5);
    }

    public void setBuffer(byte[] bArr, int i, int i2) {
        GPUImageFilter gPUImageFilter = ((WrapperRender) this).mFilter;
        if (gPUImageFilter instanceof NewBeautificationFilter) {
            ((NewBeautificationFilter) gPUImageFilter).passPreviewFrameToTexture(bArr, i, i2);
        }
    }

    @Override // com.android.camera.effect.renders.Render
    public void setPreviewSize(int i, int i2) {
        super.setPreviewSize(i, i2);
        this.mSimpleRender.setPreviewSize(i, i2);
        ((WrapperRender) this).mFilter.onDisplaySizeChanged(i, i2);
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.WrapperRender
    public void setViewportSize(int i, int i2) {
        super.setViewportSize(i, i2);
        this.mSimpleRender.setViewportSize(i, i2);
    }
}
