package com.android.camera.effect.framework.gles;

import android.opengl.GLES20;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BasicTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.RawTexture;

public class RGBTexture extends RawTexture {
    private static final String TAG = "RGBTexture";
    private static final int[] sTextureId = new int[1];

    public RGBTexture(int i, int i2, boolean z) {
        super(i, i2, z);
    }

    @Override // com.android.gallery3d.ui.RawTexture, com.android.gallery3d.ui.BasicTexture
    public int getTarget() {
        return 3553;
    }

    @Override // com.android.gallery3d.ui.RawTexture, com.android.gallery3d.ui.BasicTexture
    public boolean onBind(GLCanvas gLCanvas) {
        if (isLoaded()) {
            return true;
        }
        Log.w(TAG, "lost the content due to context change");
        return false;
    }

    @Override // com.android.gallery3d.ui.RawTexture
    public void prepare(GLCanvas gLCanvas) {
        GLES20.glGenTextures(1, sTextureId, 0);
        GLES20.glBindTexture(3553, sTextureId[0]);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexImage2D(3553, 0, 6407, getTextureWidth(), getTextureHeight(), 0, 6407, 5121, null);
        Log.v(TAG, "prepare textureSize=" + getTextureWidth() + "x" + getTextureHeight() + " id=" + sTextureId[0]);
        ((BasicTexture) this).mId = sTextureId[0];
        ((BasicTexture) this).mState = 1;
        setAssociatedCanvas(gLCanvas);
    }

    @Override // com.android.gallery3d.ui.RawTexture, com.android.gallery3d.ui.BasicTexture
    public void yield() {
    }
}
