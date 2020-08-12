package com.android.gallery3d.ui;

import android.opengl.GLES20;
import com.android.camera.log.Log;

public class ExtTexture extends BasicTexture {
    private static int[] sTextureId = new int[1];
    private int mTarget = 36197;

    public ExtTexture() {
        GLES20.glGenTextures(1, sTextureId, 0);
        ((BasicTexture) this).mId = sTextureId[0];
        Log.d("ExtTexture", "texId=" + ((BasicTexture) this).mId);
    }

    private void uploadToCanvas(GLCanvas gLCanvas) {
        GLES20.glBindTexture(this.mTarget, ((BasicTexture) this).mId);
        GLES20.glTexParameteri(this.mTarget, 10242, 33071);
        GLES20.glTexParameteri(this.mTarget, 10243, 33071);
        GLES20.glTexParameterf(this.mTarget, 10241, 9729.0f);
        GLES20.glTexParameterf(this.mTarget, 10240, 9729.0f);
        setAssociatedCanvas(gLCanvas);
        ((BasicTexture) this).mState = 1;
    }

    @Override // com.android.gallery3d.ui.Texture, com.android.gallery3d.ui.BasicTexture
    public void draw(GLCanvas gLCanvas, int i, int i2) {
    }

    @Override // com.android.gallery3d.ui.Texture, com.android.gallery3d.ui.BasicTexture
    public void draw(GLCanvas gLCanvas, int i, int i2, int i3, int i4) {
    }

    @Override // com.android.gallery3d.ui.BasicTexture
    public int getTarget() {
        return this.mTarget;
    }

    @Override // com.android.gallery3d.ui.BasicTexture
    public boolean hasBorder() {
        return false;
    }

    @Override // com.android.gallery3d.ui.Texture
    public boolean isOpaque() {
        return true;
    }

    @Override // com.android.gallery3d.ui.BasicTexture
    public boolean onBind(GLCanvas gLCanvas) {
        if (isLoaded()) {
            return true;
        }
        uploadToCanvas(gLCanvas);
        return true;
    }

    @Override // com.android.gallery3d.ui.BasicTexture
    public void yield() {
    }
}
