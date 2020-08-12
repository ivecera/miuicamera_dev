package com.android.camera.effect.renders;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES20;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.effect.EffectController;
import com.android.gallery3d.ui.GLCanvas;

public abstract class RegionEffectRender extends ConvolutionEffectRender {
    private EffectController.EffectRectAttribute mAttribute;
    private int mThresholdHeight;
    private int mThresholdWidth;
    protected int mUniformEffectParameterH;
    protected int mUniformEffectRectH;
    protected int mUniformInvertRectH;

    public RegionEffectRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        init();
    }

    public RegionEffectRender(GLCanvas gLCanvas, int i) {
        super(gLCanvas, i);
        init();
    }

    private Matrix getChangeMatrix() {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate((float) (((Render) this).mOrientation - ((Render) this).mJpegOrientation));
        matrix.preTranslate(-0.5f, -0.5f);
        matrix.postTranslate(0.5f, 0.5f);
        return matrix;
    }

    private float[] getEffectArray(boolean z) {
        if (z) {
            Matrix changeMatrix = getChangeMatrix();
            EffectController.EffectRectAttribute effectRectAttribute = this.mAttribute;
            PointF pointF = effectRectAttribute.mPoint1;
            PointF pointF2 = effectRectAttribute.mPoint2;
            float[] fArr = {pointF.x, pointF.y, pointF2.x, pointF2.y, effectRectAttribute.mRangeWidth};
            changeMatrix.mapPoints(fArr, 0, fArr, 0, 2);
            return fArr;
        }
        EffectController.EffectRectAttribute effectAttribute = EffectController.getInstance().getEffectAttribute();
        PointF pointF3 = effectAttribute.mPoint1;
        PointF pointF4 = effectAttribute.mPoint2;
        return new float[]{pointF3.x, pointF3.y, pointF4.x, pointF4.y, effectAttribute.mRangeWidth};
    }

    private void init() {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        this.mThresholdWidth = resources.getDimensionPixelSize(R.dimen.effect_item_width);
        this.mThresholdHeight = resources.getDimensionPixelSize(R.dimen.effect_item_height);
    }

    private void setEffectRectF(RectF rectF) {
        if (rectF != null) {
            float[] fArr = ((ShaderRender) this).mPreviewEffectRect;
            fArr[0] = rectF.left;
            fArr[1] = rectF.top;
            fArr[2] = rectF.right;
            fArr[3] = rectF.bottom;
            return;
        }
        float[] fArr2 = ((ShaderRender) this).mPreviewEffectRect;
        fArr2[0] = 0.0f;
        fArr2[1] = 0.0f;
        fArr2[2] = 1.0f;
        fArr2[3] = 1.0f;
    }

    /* access modifiers changed from: protected */
    public float[] getEffectRect(boolean z) {
        RectF rectF;
        if (z) {
            if (!((Render) this).mMirror) {
                float[] fArr = ((ShaderRender) this).mPreviewEffectRect;
                rectF = new RectF(fArr[0], fArr[1], fArr[2], fArr[3]);
            } else if (((Render) this).mOrientation % 180 == 0) {
                float[] fArr2 = ((ShaderRender) this).mPreviewEffectRect;
                rectF = new RectF(1.0f - fArr2[0], fArr2[1], 1.0f - fArr2[2], fArr2[3]);
            } else {
                float[] fArr3 = ((ShaderRender) this).mPreviewEffectRect;
                rectF = new RectF(fArr3[0], 1.0f - fArr3[1], fArr3[2], 1.0f - fArr3[3]);
            }
            getChangeMatrix().mapRect(rectF);
            float[] fArr4 = ((ShaderRender) this).mSnapshotEffectRect;
            fArr4[0] = rectF.left;
            fArr4[1] = rectF.top;
            fArr4[2] = rectF.right;
            fArr4[3] = rectF.bottom;
            return fArr4;
        }
        RectF effectRectF = EffectController.getInstance().getEffectRectF();
        if (((Render) this).mPreviewWidth <= this.mThresholdWidth || ((Render) this).mPreviewHeight <= this.mThresholdHeight) {
            setEffectRectF(null);
        } else {
            setEffectRectF(effectRectF);
        }
        return ((ShaderRender) this).mPreviewEffectRect;
    }

    /* access modifiers changed from: protected */
    public int getInvertFlag(boolean z) {
        return z ? this.mAttribute.mInvertFlag : EffectController.getInstance().getInvertFlag();
    }

    /* access modifiers changed from: protected */
    public void initEffectRect(boolean z) {
        GLES20.glUniform4fv(this.mUniformEffectRectH, 1, getEffectRect(z), 0);
        GLES20.glUniform1i(this.mUniformInvertRectH, getInvertFlag(z));
        GLES20.glUniform1fv(this.mUniformEffectParameterH, 5, getEffectArray(z), 0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender, com.android.camera.effect.renders.ShaderRender
    public void initShader() {
        super.initShader();
        this.mUniformEffectRectH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uEffectRect");
        this.mUniformInvertRectH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uInvertRect");
        this.mUniformEffectParameterH = GLES20.glGetUniformLocation(((ShaderRender) this).mProgram, "uEffectArray");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.effect.renders.PixelEffectRender, com.android.camera.effect.renders.ConvolutionEffectRender
    public void initShaderValue(boolean z) {
        super.initShaderValue(z);
        initEffectRect(z);
    }

    @Override // com.android.camera.effect.renders.Render
    public void setEffectRangeAttribute(EffectController.EffectRectAttribute effectRectAttribute) {
        this.mAttribute = effectRectAttribute;
        setEffectRectF(effectRectAttribute.mRectF);
    }
}
