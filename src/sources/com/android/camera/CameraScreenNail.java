package com.android.camera;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.support.v4.view.ViewCompat;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawBlurTexAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.draw_mode.FillRectAttribute;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.gallery3d.ui.BitmapTexture;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.RawTexture;
import com.mi.config.b;
import com.xiaomi.stat.d;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraScreenNail extends SurfaceTextureScreenNail {
    private static final int ANIM_CAPTURE_RUNNING = 12;
    private static final int ANIM_CAPTURE_START = 11;
    private static final int ANIM_MODULE_COPY_TEXTURE = 31;
    private static final int ANIM_MODULE_COPY_TEXTURE_WITH_ALPHA = 37;
    private static final int ANIM_MODULE_DRAW_PREVIEW = 32;
    private static final int ANIM_MODULE_RESUME = 35;
    private static final int ANIM_MODULE_RUNNING = 33;
    private static final int ANIM_MODULE_WAITING_FIRST_FRAME = 34;
    private static final int ANIM_NONE = 0;
    private static final int ANIM_READ_LAST_FRAME_GAUSSIAN = 36;
    private static final int ANIM_SWITCH_COPY_TEXTURE = 21;
    private static final int ANIM_SWITCH_DRAW_PREVIEW = 22;
    private static final int ANIM_SWITCH_RESUME = 25;
    private static final int ANIM_SWITCH_RUNNING = 23;
    private static final int ANIM_SWITCH_WAITING_FIRST_FRAME = 24;
    public static final int FRAME_AVAILABLE_AFTER_CATCH = 4;
    public static final int FRAME_AVAILABLE_ON_CREATE = 1;
    private static final int GAUSSIAN_PREVIEWING = 39;
    private static final int STATE_FULL_READ_PIXELS = 15;
    private static final int STATE_HIBERNATE = 14;
    private static final int STATE_PREVIEW_GAUSSIAN_FOREVER = 38;
    private static final int STATE_READ_PIXELS = 13;
    private static final String TAG = "CameraScreenNail";
    private int mAnimState = 0;
    private CaptureAnimManager mCaptureAnimManager = new CaptureAnimManager();
    private boolean mDisableSwitchAnimationOnce;
    private FrameBuffer mExtProcessorBlurFrameBuffer;
    private RawTexture mExtProcessorBlurTexture;
    private FrameBuffer mExtProcessorFrameBuffer;
    private RawTexture mExtProcessorTexture;
    private SurfaceTextureScreenNail.ExternalFrameProcessor mExternalFrameProcessorCopy;
    private boolean mFirstFrameArrived;
    private AtomicBoolean mFrameAvailableNotified = new AtomicBoolean(false);
    private int mFrameNumber = 0;
    private SwitchAnimManager mGaussianPreviewManager = new SwitchAnimManager(2000.0f);
    private volatile boolean mIsDrawBlackFrame;
    private Bitmap mLastFrameGaussianBitmap;
    private SwitchAnimManager mModuleAnimManager = new SwitchAnimManager();
    private NailListener mNailListener;
    private int mReadPixelsNum = 0;
    private List<RequestRenderListener> mRequestRenderListeners;
    private SwitchAnimManager mSwitchAnimManager = new SwitchAnimManager();
    private final float[] mTextureTransformMatrix = new float[16];
    private boolean mVisible;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrivedType {
    }

    public interface NailListener extends SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback {
        int getOrientation();

        boolean isKeptBitmapTexture();

        void onFrameAvailable(int i);

        void onPreviewPixelsRead(byte[] bArr, int i, int i2);

        void onPreviewTextureCopied();
    }

    public interface RequestRenderListener {
        void requestRender();
    }

    public CameraScreenNail(NailListener nailListener, RequestRenderListener requestRenderListener) {
        super(nailListener);
        this.mNailListener = nailListener;
        this.mRequestRenderListeners = new ArrayList();
        addRequestListener(requestRenderListener);
    }

    private void copyExternalPreviewTexture(GLCanvas gLCanvas, RawTexture rawTexture, FrameBuffer frameBuffer) {
        int width = rawTexture.getWidth();
        int height = rawTexture.getHeight();
        gLCanvas.beginBindFrameBuffer(frameBuffer);
        gLCanvas.getState().pushState();
        gLCanvas.getState().translate(((float) width) / 2.0f, ((float) height) / 2.0f);
        gLCanvas.getState().scale(1.0f, -1.0f, 1.0f);
        gLCanvas.getState().translate(((float) (-width)) / 2.0f, ((float) (-height)) / 2.0f);
        gLCanvas.draw(new DrawBasicTexAttribute(rawTexture, 0, 0, width, height));
        gLCanvas.getState().popState();
        gLCanvas.endBindFrameBuffer();
    }

    private void copyPreviewTexture(GLCanvas gLCanvas, RawTexture rawTexture, FrameBuffer frameBuffer) {
        int width = rawTexture.getWidth();
        int height = rawTexture.getHeight();
        getSurfaceTexture().getTransformMatrix(this.mTextureTransformMatrix);
        updateTransformMatrix(this.mTextureTransformMatrix);
        if (frameBuffer == null) {
            frameBuffer = new FrameBuffer(gLCanvas, rawTexture, 0);
        }
        SurfaceTextureScreenNail.ExternalFrameProcessor externalFrameProcessor = this.mExternalFrameProcessorCopy;
        if (externalFrameProcessor == null) {
            externalFrameProcessor = ((SurfaceTextureScreenNail) this).mExternalFrameProcessor;
        }
        if (rawTexture == ((SurfaceTextureScreenNail) this).mCaptureAnimTexture && externalFrameProcessor != null) {
            if (this.mExtProcessorTexture == null) {
                this.mExtProcessorTexture = new RawTexture(width, height, true);
            }
            if (this.mExtProcessorFrameBuffer == null) {
                this.mExtProcessorFrameBuffer = new FrameBuffer(gLCanvas, this.mExtProcessorTexture, 0);
            }
            gLCanvas.beginBindFrameBuffer(this.mExtProcessorFrameBuffer);
            externalFrameProcessor.onDrawFrame(getDisplayRect(), width, height, true);
            gLCanvas.endBindFrameBuffer();
            copyExternalPreviewTexture(gLCanvas, this.mExtProcessorTexture, frameBuffer);
        } else if (ModuleManager.getActiveModuleIndex() != 177 && ModuleManager.getActiveModuleIndex() != 184 && !ModuleManager.isMiLiveModule()) {
            gLCanvas.beginBindFrameBuffer(frameBuffer);
            gLCanvas.draw(new DrawExtTexAttribute(((SurfaceTextureScreenNail) this).mExtTexture, this.mTextureTransformMatrix, 0, 0, width, height));
            gLCanvas.endBindFrameBuffer();
        } else if (externalFrameProcessor != null) {
            if (this.mExtProcessorBlurTexture == null) {
                this.mExtProcessorBlurTexture = new RawTexture(width, height, true);
            }
            if (this.mExtProcessorBlurFrameBuffer == null) {
                this.mExtProcessorBlurFrameBuffer = new FrameBuffer(gLCanvas, this.mExtProcessorBlurTexture, 0);
            }
            gLCanvas.beginBindFrameBuffer(this.mExtProcessorBlurFrameBuffer);
            externalFrameProcessor.onDrawFrame(getDisplayRect(), width, height, true);
            gLCanvas.endBindFrameBuffer();
            copyExternalPreviewTexture(gLCanvas, this.mExtProcessorBlurTexture, frameBuffer);
        } else {
            gLCanvas.beginBindFrameBuffer(frameBuffer);
            gLCanvas.draw(new DrawExtTexAttribute(((SurfaceTextureScreenNail) this).mExtTexture, this.mTextureTransformMatrix, 0, 0, width, height));
            gLCanvas.endBindFrameBuffer();
            gLCanvas.beginBindFrameBuffer(frameBuffer);
            gLCanvas.draw(new DrawExtTexAttribute(((SurfaceTextureScreenNail) this).mExtTexture, this.mTextureTransformMatrix, 0, 0, width, height));
            gLCanvas.endBindFrameBuffer();
        }
        this.mExternalFrameProcessorCopy = null;
    }

    private void postRequestListener() {
        for (RequestRenderListener requestRenderListener : this.mRequestRenderListeners) {
            requestRenderListener.requestRender();
        }
    }

    private byte[] readPreviewPixels(GLCanvas gLCanvas, int i, int i2, boolean z) {
        ByteBuffer allocate = ByteBuffer.allocate(i * i2 * 4);
        getSurfaceTexture().getTransformMatrix(this.mTextureTransformMatrix);
        updateTransformMatrix(this.mTextureTransformMatrix);
        if (z) {
            RawTexture rawTexture = ((SurfaceTextureScreenNail) this).mFullCaptureAnimTexture;
            if (!(rawTexture != null && rawTexture.getTextureWidth() == i && ((SurfaceTextureScreenNail) this).mFullCaptureAnimTexture.getTextureHeight() == i2)) {
                RawTexture rawTexture2 = ((SurfaceTextureScreenNail) this).mFullCaptureAnimTexture;
                if (rawTexture2 != null) {
                    GLES20.glDeleteTextures(1, new int[]{rawTexture2.getId()}, 0);
                }
                FrameBuffer frameBuffer = ((SurfaceTextureScreenNail) this).mFullCaptureAnimFrameBuffer;
                if (frameBuffer != null) {
                    frameBuffer.delete();
                }
                ((SurfaceTextureScreenNail) this).mFullCaptureAnimTexture = new RawTexture(i, i2, true);
                ((SurfaceTextureScreenNail) this).mFullCaptureAnimFrameBuffer = new FrameBuffer((GLCanvas) null, ((SurfaceTextureScreenNail) this).mFullCaptureAnimTexture, 0);
            }
            gLCanvas.beginBindFrameBuffer(((SurfaceTextureScreenNail) this).mFullCaptureAnimFrameBuffer);
        } else {
            if (((SurfaceTextureScreenNail) this).mCaptureAnimFrameBuffer == null) {
                ((SurfaceTextureScreenNail) this).mCaptureAnimFrameBuffer = new FrameBuffer(gLCanvas, ((SurfaceTextureScreenNail) this).mCaptureAnimTexture, 0);
            }
            gLCanvas.beginBindFrameBuffer(((SurfaceTextureScreenNail) this).mCaptureAnimFrameBuffer);
        }
        gLCanvas.draw(new DrawExtTexAttribute(((SurfaceTextureScreenNail) this).mExtTexture, this.mTextureTransformMatrix, 0, 0, i, i2));
        GLES20.glReadPixels(0, 0, i, i2, 6408, 5121, allocate);
        gLCanvas.endBindFrameBuffer();
        return allocate.array();
    }

    private void renderBlurTexture(GLCanvas gLCanvas, RawTexture rawTexture) {
        int width = rawTexture.getWidth();
        int height = rawTexture.getHeight();
        if (((SurfaceTextureScreenNail) this).mFrameBuffer == null) {
            ((SurfaceTextureScreenNail) this).mFrameBuffer = new FrameBuffer(gLCanvas, rawTexture, 0);
        }
        gLCanvas.prepareBlurRenders();
        gLCanvas.beginBindFrameBuffer(((SurfaceTextureScreenNail) this).mFrameBuffer);
        gLCanvas.draw(new DrawBlurTexAttribute(rawTexture, 0, 0, width, height));
        gLCanvas.endBindFrameBuffer();
    }

    @Override // com.android.camera.SurfaceTextureScreenNail
    public void acquireSurfaceTexture() {
        Log.v(TAG, "acquireSurfaceTexture");
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            this.mFirstFrameArrived = false;
            this.mFrameNumber = 0;
            this.mReadPixelsNum = 0;
            this.mDisableSwitchAnimationOnce = false;
            super.acquireSurfaceTexture();
        }
    }

    public void addRequestListener(RequestRenderListener requestRenderListener) {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            this.mRequestRenderListeners.add(requestRenderListener);
        }
    }

    public void animateCapture(int i) {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            String str = TAG;
            Log.v(str, "animateCapture: state=" + this.mAnimState);
            if (this.mAnimState == 0) {
                this.mCaptureAnimManager.animateHoldAndSlide();
                postRequestListener();
                this.mAnimState = 11;
            }
        }
    }

    public void animateHold(int i) {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            String str = TAG;
            Log.v(str, "animateHold: state=" + this.mAnimState);
            if (this.mAnimState == 0) {
                this.mCaptureAnimManager.animateHold();
                postRequestListener();
                this.mAnimState = 11;
            }
        }
    }

    public void animateModuleCopyTexture(boolean z) {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (((SurfaceTextureScreenNail) this).mAnimTexture == null || this.mFrameAvailableNotified.get()) {
                if (z) {
                    this.mAnimState = 37;
                    Log.v(TAG, "state=MODULE_COPY_TEXTURE_WITH_ALPHA");
                } else {
                    this.mAnimState = 31;
                    Log.v(TAG, "state=MODULE_COPY_TEXTURE");
                }
                this.mExternalFrameProcessorCopy = ((SurfaceTextureScreenNail) this).mExternalFrameProcessor;
                postRequestListener();
            }
        }
    }

    public void animateSlide() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (this.mAnimState != 12) {
                String str = TAG;
                Log.w(str, "Cannot animateSlide outside of animateCapture! Animation state = " + this.mAnimState);
            }
            this.mCaptureAnimManager.animateSlide();
            postRequestListener();
        }
    }

    public void animateSwitchCameraBefore() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            String str = TAG;
            Log.v(str, "switchBefore: state=" + this.mAnimState);
            if (this.mAnimState == 22) {
                this.mAnimState = 23;
                this.mSwitchAnimManager.startAnimation(false);
                postRequestListener();
            }
        }
    }

    public void animateSwitchCopyTexture() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            postRequestListener();
            this.mAnimState = 21;
            Log.v(TAG, "state=SWITCH_COPY_TEXTURE");
        }
    }

    public void clearAnimation() {
        if (this.mAnimState != 0) {
            this.mAnimState = 0;
            this.mSwitchAnimManager.clearAnimation();
            this.mCaptureAnimManager.clearAnimation();
            this.mModuleAnimManager.clearAnimation();
            this.mGaussianPreviewManager.clearAnimation();
        }
    }

    public void directDraw(GLCanvas gLCanvas, int i, int i2, int i3, int i4) {
        super.draw(gLCanvas, i, i2, i3, i4);
    }

    public void disableSwitchAnimationOnce() {
        this.mDisableSwitchAnimationOnce = true;
    }

    public void doPreviewGaussianForever() {
        Log.d(TAG, "doPreviewGaussianForever: start");
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (this.mFirstFrameArrived) {
                if (getSurfaceTexture() != null) {
                    if (((SurfaceTextureScreenNail) this).mAnimTexture == null || this.mFrameAvailableNotified.get()) {
                        this.mAnimState = 38;
                        postRequestListener();
                        Log.d(TAG, "doPreviewGaussianForever: end");
                        return;
                    }
                    Log.w(TAG, "doPreviewGaussianForever: not start preview return!!!");
                    return;
                }
            }
            Log.w(TAG, "doPreviewGaussianForever: not start preview return!!!");
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x029b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02c2, code lost:
        return;
     */
    @Override // com.android.gallery3d.ui.ScreenNail, com.android.camera.SurfaceTextureScreenNail
    public void draw(GLCanvas gLCanvas, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z;
        int i7;
        int i8;
        int i9;
        int i10;
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            boolean z2 = true;
            if (!this.mVisible) {
                this.mVisible = true;
            }
            if (((SurfaceTextureScreenNail) this).mBitmapTexture != null) {
                if (ModuleManager.isSquareModule()) {
                    if (i3 > i4) {
                        i10 = ((i3 - i4) / 2) + i;
                        i9 = i2;
                        i8 = i4;
                    } else {
                        i9 = ((i4 - i3) / 2) + i2;
                        i10 = i;
                        i8 = i3;
                    }
                    i7 = i8;
                } else {
                    i10 = i;
                    i9 = i2;
                    i8 = i3;
                    i7 = i4;
                }
                ((SurfaceTextureScreenNail) this).mBitmapTexture.draw(gLCanvas, i10, i9, i8, i7);
            } else if (this.mIsDrawBlackFrame) {
                Log.d(TAG, "draw: skip frame...");
                gLCanvas.draw(new FillRectAttribute((float) i, (float) i2, (float) i3, (float) i4, ViewCompat.MEASURED_STATE_MASK));
                postRequestListener();
            } else {
                SurfaceTexture surfaceTexture = getSurfaceTexture();
                if (surfaceTexture != null) {
                    if (this.mFirstFrameArrived || this.mAnimState != 0) {
                        int i11 = this.mAnimState;
                        if (i11 == 0) {
                            i5 = 33;
                            super.draw(gLCanvas, i, i2, i3, i4);
                        } else if (i11 != 11) {
                            if (i11 != 31) {
                                if (i11 != 21) {
                                    if (i11 != 22) {
                                        switch (i11) {
                                            case 13:
                                                super.draw(gLCanvas, i, i2, i3, i4);
                                                int width = ((SurfaceTextureScreenNail) this).mCaptureAnimTexture.getWidth();
                                                int height = ((SurfaceTextureScreenNail) this).mCaptureAnimTexture.getHeight();
                                                int i12 = i3 * height;
                                                int i13 = i4 * width;
                                                if (i12 > i13) {
                                                    height = i13 / i3;
                                                } else {
                                                    width = i12 / i4;
                                                }
                                                byte[] readPreviewPixels = readPreviewPixels(gLCanvas, width, height, false);
                                                this.mReadPixelsNum--;
                                                Log.d(TAG, "draw: state=STATE_READ_PIXELS mReadPixelsNum=" + this.mReadPixelsNum);
                                                if (this.mReadPixelsNum < 1) {
                                                    this.mAnimState = 0;
                                                }
                                                this.mNailListener.onPreviewPixelsRead(readPreviewPixels, width, height);
                                                i5 = 33;
                                                break;
                                            case 14:
                                                surfaceTexture.updateTexImage();
                                                gLCanvas.clearBuffer();
                                                i5 = 33;
                                                break;
                                            case 15:
                                                super.draw(gLCanvas, i, i2, i3, i4);
                                                byte[] readPreviewPixels2 = readPreviewPixels(gLCanvas, ((SurfaceTextureScreenNail) this).mCameraWidth, ((SurfaceTextureScreenNail) this).mCameraHeight, true);
                                                this.mAnimState = 0;
                                                if (((SurfaceTextureScreenNail) this).mPreviewSaveListener != null) {
                                                    ((SurfaceTextureScreenNail) this).mPreviewSaveListener.save(readPreviewPixels2, ((SurfaceTextureScreenNail) this).mCameraWidth, ((SurfaceTextureScreenNail) this).mCameraHeight, this.mNailListener.getOrientation());
                                                }
                                                i5 = 33;
                                                break;
                                            default:
                                                switch (i11) {
                                                    case 36:
                                                        super.draw(gLCanvas, i, i2, i3, i4);
                                                        Log.v(TAG, "draw: state=ANIM_READ_LAST_FRAME_GAUSSIAN");
                                                        copyPreviewTexture(gLCanvas, ((SurfaceTextureScreenNail) this).mAnimTexture, ((SurfaceTextureScreenNail) this).mFrameBuffer);
                                                        drawGaussianBitmap(gLCanvas, i, i2, i3, i4);
                                                        this.mAnimState = 0;
                                                        i5 = 33;
                                                        break;
                                                    case 37:
                                                        break;
                                                    case 38:
                                                        copyPreviewTexture(gLCanvas, ((SurfaceTextureScreenNail) this).mAnimTexture, ((SurfaceTextureScreenNail) this).mFrameBuffer);
                                                        drawGaussianBitmap(gLCanvas, i, i2, i3, i4);
                                                        this.mGaussianPreviewManager.setReviewDrawingSize(i, i2, i3, i4);
                                                        Log.v(TAG, "draw: state=STATE_PREVIEW_GUASSIAN_FOREVER");
                                                        this.mAnimState = 39;
                                                        this.mGaussianPreviewManager.startAnimation(false);
                                                        postRequestListener();
                                                        i5 = 33;
                                                        break;
                                                    default:
                                                        i5 = 33;
                                                        break;
                                                }
                                        }
                                    }
                                } else {
                                    copyPreviewTexture(gLCanvas, ((SurfaceTextureScreenNail) this).mAnimTexture, ((SurfaceTextureScreenNail) this).mFrameBuffer);
                                    this.mSwitchAnimManager.setReviewDrawingSize(i, i2, i3, i4);
                                    this.mNailListener.onPreviewTextureCopied();
                                    this.mAnimState = 22;
                                    Log.v(TAG, "draw: state=SWITCH_DRAW_PREVIEW");
                                }
                                surfaceTexture.updateTexImage();
                                i5 = 33;
                                this.mSwitchAnimManager.drawPreview(gLCanvas, i, i2, i3, i4, ((SurfaceTextureScreenNail) this).mAnimTexture);
                            }
                            i5 = 33;
                            copyPreviewTexture(gLCanvas, ((SurfaceTextureScreenNail) this).mAnimTexture, ((SurfaceTextureScreenNail) this).mFrameBuffer);
                            this.mModuleAnimManager.setReviewDrawingSize(i, i2, i3, i4);
                            Log.v(TAG, "draw: state=MODULE_DRAW_PREVIEW");
                            if (this.mAnimState != 37) {
                                z2 = false;
                            }
                            this.mAnimState = 33;
                            this.mModuleAnimManager.startAnimation(z2);
                            postRequestListener();
                        } else {
                            i5 = 33;
                            super.draw(gLCanvas, i, i2, i3, i4);
                            copyPreviewTexture(gLCanvas, ((SurfaceTextureScreenNail) this).mCaptureAnimTexture, ((SurfaceTextureScreenNail) this).mCaptureAnimFrameBuffer);
                            this.mCaptureAnimManager.startAnimation(i, i2, i3, i4);
                            this.mAnimState = 12;
                            Log.v(TAG, "draw: state=CAPTURE_RUNNING");
                        }
                        if (!(this.mAnimState == 23 || this.mAnimState == 24)) {
                            if (this.mAnimState != 25) {
                                if (this.mAnimState != 12) {
                                    if (!(this.mAnimState == i5 || this.mAnimState == 34)) {
                                        if (this.mAnimState != 35) {
                                            if (this.mAnimState == 39) {
                                                surfaceTexture.updateTexImage();
                                                if (this.mGaussianPreviewManager.drawAnimation(gLCanvas, i, i2, i3, i4, this, ((SurfaceTextureScreenNail) this).mAnimTexture)) {
                                                    postRequestListener();
                                                } else {
                                                    this.mAnimState = 0;
                                                }
                                            }
                                        }
                                    }
                                    surfaceTexture.updateTexImage();
                                    if (!this.mModuleAnimManager.drawAnimation(gLCanvas, i, i2, i3, i4, this, ((SurfaceTextureScreenNail) this).mAnimTexture)) {
                                        if (this.mAnimState == 35) {
                                            this.mAnimState = 0;
                                            super.draw(gLCanvas, i, i2, i3, i4);
                                        }
                                    }
                                    postRequestListener();
                                } else if (this.mCaptureAnimManager.drawAnimation(gLCanvas, ((SurfaceTextureScreenNail) this).mCaptureAnimTexture)) {
                                    postRequestListener();
                                } else {
                                    this.mAnimState = 0;
                                    this.mCaptureAnimManager.drawPreview(gLCanvas, ((SurfaceTextureScreenNail) this).mCaptureAnimTexture);
                                    postRequestListener();
                                }
                            }
                        }
                        surfaceTexture.updateTexImage();
                        if (this.mDisableSwitchAnimationOnce) {
                            i6 = 25;
                            this.mSwitchAnimManager.drawPreview(gLCanvas, i, i2, i3, i4, ((SurfaceTextureScreenNail) this).mAnimTexture);
                            z = false;
                        } else {
                            i6 = 25;
                            z = this.mSwitchAnimManager.drawAnimation(gLCanvas, i, i2, i3, i4, this, ((SurfaceTextureScreenNail) this).mAnimTexture);
                        }
                        if (!z) {
                            if (this.mAnimState == i6) {
                                this.mAnimState = 0;
                                this.mDisableSwitchAnimationOnce = false;
                                super.draw(gLCanvas, i, i2, i3, i4);
                            }
                        }
                        postRequestListener();
                    }
                }
                Log.w(TAG, "draw: firstFrame=" + this.mFirstFrameArrived + " surface=" + surfaceTexture);
                if (surfaceTexture != null) {
                    surfaceTexture.updateTexImage();
                }
            }
        }
    }

    public void drawBlackFrame(boolean z) {
        this.mIsDrawBlackFrame = z;
    }

    public void drawBlurTexture(GLCanvas gLCanvas, int i, int i2, int i3, int i4) {
        gLCanvas.draw(new DrawBasicTexAttribute(((SurfaceTextureScreenNail) this).mAnimTexture, i, i2, i3, i4));
    }

    public void drawGaussianBitmap(GLCanvas gLCanvas, int i, int i2, int i3, int i4) {
        long currentTimeMillis = System.currentTimeMillis();
        int width = ((SurfaceTextureScreenNail) this).mAnimTexture.getWidth();
        int height = ((SurfaceTextureScreenNail) this).mAnimTexture.getHeight();
        for (int i5 = 0; i5 < 8; i5++) {
            renderBlurTexture(gLCanvas);
        }
        GLES20.glFlush();
        ByteBuffer allocate = ByteBuffer.allocate(width * height * 4);
        if (((SurfaceTextureScreenNail) this).mFrameBuffer == null) {
            ((SurfaceTextureScreenNail) this).mFrameBuffer = new FrameBuffer(gLCanvas, ((SurfaceTextureScreenNail) this).mAnimTexture, 0);
        }
        gLCanvas.beginBindFrameBuffer(((SurfaceTextureScreenNail) this).mFrameBuffer);
        gLCanvas.draw(new DrawBasicTexAttribute(((SurfaceTextureScreenNail) this).mAnimTexture, 0, 0, width, height));
        GLES20.glReadPixels(0, 0, width, height, 6408, 5121, allocate);
        gLCanvas.endBindFrameBuffer();
        byte[] array = allocate.array();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(array));
        this.mLastFrameGaussianBitmap = createBitmap;
        Log.d(TAG, "readLastFrameGaussian end... bitmap = " + this.mLastFrameGaussianBitmap + ", cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    public Rect getDisplayRect() {
        return ((SurfaceTextureScreenNail) this).mDisplayRect;
    }

    public SurfaceTextureScreenNail.ExternalFrameProcessor getExternalFrameProcessor() {
        return ((SurfaceTextureScreenNail) this).mExternalFrameProcessor;
    }

    public boolean getFrameAvailableFlag() {
        return this.mFrameAvailableNotified.get();
    }

    public Bitmap getLastFrameGaussianBitmap() {
        return this.mLastFrameGaussianBitmap;
    }

    public SurfaceTextureScreenNail.PreviewSaveListener getPreviewSaveListener() {
        return ((SurfaceTextureScreenNail) this).mPreviewSaveListener;
    }

    public Rect getRenderRect() {
        return ((SurfaceTextureScreenNail) this).mRenderLayoutRect;
    }

    @Override // com.android.camera.SurfaceTextureScreenNail
    public boolean isAnimationGaussian() {
        return this.mAnimState == 36;
    }

    @Override // com.android.camera.SurfaceTextureScreenNail
    public boolean isAnimationRunning() {
        return this.mAnimState != 0;
    }

    @Override // com.android.gallery3d.ui.ScreenNail, com.android.camera.SurfaceTextureScreenNail
    public void noDraw() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            this.mVisible = false;
        }
    }

    public void notifyFrameAvailable(int i) {
        if (!this.mFrameAvailableNotified.get()) {
            this.mFrameAvailableNotified.set(true);
            this.mNailListener.onFrameAvailable(i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0075, code lost:
        return;
     */
    @Override // com.android.camera.SurfaceTextureScreenNail
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (getSurfaceTexture() != surfaceTexture) {
            Log.e(TAG, "onFrameAvailable: surface changed");
            return;
        }
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (!this.mFirstFrameArrived) {
                if (this.mFrameNumber < CameraSettings.getSkipFrameNumber()) {
                    this.mFrameNumber++;
                    postRequestListener();
                    return;
                }
                Log.d(TAG, "onFrameAvailable first frame arrived.");
                ScenarioTrackUtil.trackSwitchCameraEnd();
                ScenarioTrackUtil.trackSwitchModeEnd();
                notifyFrameAvailable(1);
                this.mVisible = true;
                this.mFirstFrameArrived = true;
            }
            if (this.mVisible) {
                if (this.mAnimState == 24) {
                    this.mAnimState = 25;
                    Log.v(TAG, "SWITCH_WAITING_FIRST_FRAME->SWITCH_RESUME");
                    this.mSwitchAnimManager.startResume();
                } else if (this.mAnimState == 34) {
                    this.mAnimState = 35;
                    Log.v(TAG, "MODULE_WAITING_FIRST_FRAME->MODULE_RESUME");
                    this.mModuleAnimManager.startResume();
                }
                postRequestListener();
                notifyFrameAvailable(4);
            }
        }
    }

    public void readLastFrameGaussian() {
        Log.d(TAG, "readLastFrameGaussian: state=ANIM_READ_LAST_FRAME_GAUSSIAN start");
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (this.mFirstFrameArrived) {
                if (getSurfaceTexture() != null) {
                    if (((SurfaceTextureScreenNail) this).mAnimTexture == null || this.mFrameAvailableNotified.get()) {
                        this.mAnimState = 36;
                        postRequestListener();
                        Log.d(TAG, "readLastFrameGaussian: state=ANIM_READ_LAST_FRAME_GAUSSIAN end");
                        return;
                    }
                    Log.w(TAG, "readLastFrameGaussian: not start preview return!!!");
                    return;
                }
            }
            Log.w(TAG, "readLastFrameGaussian: not start preview return!!!");
        }
    }

    @Override // com.android.gallery3d.ui.ScreenNail, com.android.camera.SurfaceTextureScreenNail
    public void recycle() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            this.mVisible = false;
        }
    }

    @Override // com.android.camera.SurfaceTextureScreenNail
    public void releaseBitmapIfNeeded() {
        if (((SurfaceTextureScreenNail) this).mBitmapTexture != null && !this.mNailListener.isKeptBitmapTexture()) {
            ((SurfaceTextureScreenNail) this).mBitmapTexture = null;
            postRequestListener();
        }
    }

    @Override // com.android.camera.SurfaceTextureScreenNail
    public void releaseSurfaceTexture() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            super.releaseSurfaceTexture();
            this.mAnimState = 0;
            Log.v(TAG, "release: state=NONE");
            this.mFirstFrameArrived = false;
            this.mFrameNumber = 0;
            this.mReadPixelsNum = 0;
            ((SurfaceTextureScreenNail) this).mModuleSwitching = false;
            if (this.mExtProcessorTexture != null) {
                this.mExtProcessorTexture.recycle();
                this.mExtProcessorTexture = null;
            }
            if (this.mExtProcessorFrameBuffer != null) {
                this.mExtProcessorFrameBuffer.delete();
                this.mExtProcessorFrameBuffer = null;
            }
            if (this.mExtProcessorBlurTexture != null) {
                this.mExtProcessorBlurTexture.recycle();
                this.mExtProcessorBlurTexture = null;
            }
            if (this.mExtProcessorBlurFrameBuffer != null) {
                this.mExtProcessorBlurFrameBuffer.delete();
                this.mExtProcessorBlurFrameBuffer = null;
            }
        }
    }

    public void removeRequestListener(RequestRenderListener requestRenderListener) {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            this.mRequestRenderListeners.remove(requestRenderListener);
        }
    }

    public void renderBitmapToCanvas(Bitmap bitmap) {
        this.mVisible = false;
        ((SurfaceTextureScreenNail) this).mBitmapTexture = new BitmapTexture(bitmap);
        postRequestListener();
    }

    public void renderBlurTexture(GLCanvas gLCanvas) {
        renderBlurTexture(gLCanvas, ((SurfaceTextureScreenNail) this).mAnimTexture);
    }

    public void requestAwaken() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (this.mAnimState == 14) {
                this.mAnimState = 0;
                this.mFirstFrameArrived = false;
                this.mFrameNumber = 0;
                this.mReadPixelsNum = 0;
            }
        }
    }

    public void requestFullReadPixels() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            String str = TAG;
            Log.d(str, "requestFullReadPixels state=" + this.mAnimState);
            if (this.mAnimState == 0 || this.mAnimState == 15 || 12 == this.mAnimState || 11 == this.mAnimState) {
                this.mAnimState = 15;
                postRequestListener();
            }
        }
    }

    public void requestHibernate() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            if (this.mAnimState == 0) {
                this.mAnimState = 14;
                postRequestListener();
            }
        }
    }

    public void requestReadPixels() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            Log.d(TAG, "requestReadPixels state=" + this.mAnimState);
            if (this.mAnimState == 0 || this.mAnimState == 13 || 12 == this.mAnimState || 11 == this.mAnimState) {
                this.mAnimState = 13;
                this.mReadPixelsNum++;
                postRequestListener();
            }
        }
    }

    public void resetFrameAvailableFlag() {
        this.mFrameAvailableNotified.set(false);
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            this.mFirstFrameArrived = false;
            this.mFrameNumber = 0;
            this.mReadPixelsNum = 0;
        }
    }

    public void setExternalFrameProcessor(SurfaceTextureScreenNail.ExternalFrameProcessor externalFrameProcessor) {
        ((SurfaceTextureScreenNail) this).mExternalFrameProcessor = externalFrameProcessor;
    }

    public void setPreviewFrameLayoutSize(int i, int i2) {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            Log.d(TAG, String.format(Locale.ENGLISH, "setPreviewFrameLayoutSize: %dx%d", Integer.valueOf(i), Integer.valueOf(i2)));
            ((SurfaceTextureScreenNail) this).mSurfaceWidth = !b.om() ? i : Util.LIMIT_SURFACE_WIDTH;
            if (b.om()) {
                i2 = (i2 * Util.LIMIT_SURFACE_WIDTH) / i;
            }
            ((SurfaceTextureScreenNail) this).mSurfaceHeight = i2;
            this.mSwitchAnimManager.setPreviewFrameLayoutSize(((SurfaceTextureScreenNail) this).mSurfaceWidth, ((SurfaceTextureScreenNail) this).mSurfaceHeight);
            updateRenderRect();
        }
    }

    public void setPreviewSaveListener(SurfaceTextureScreenNail.PreviewSaveListener previewSaveListener) {
        ((SurfaceTextureScreenNail) this).mPreviewSaveListener = previewSaveListener;
    }

    public void switchCameraDone() {
        synchronized (((SurfaceTextureScreenNail) this).mLock) {
            String str = TAG;
            Log.v(str, "switchDone: state=" + this.mAnimState);
            if (this.mAnimState == 23) {
                this.mAnimState = 24;
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.SurfaceTextureScreenNail
    public void updateExtraTransformMatrix(float[] fArr) {
        float f2;
        float f3;
        int i = this.mAnimState;
        if (i == 23 || i == 24 || i == 25) {
            f3 = this.mSwitchAnimManager.getExtScaleX();
            f2 = this.mSwitchAnimManager.getExtScaleY();
        } else {
            f2 = 1.0f;
            f3 = 1.0f;
        }
        if (f3 != 1.0f || f2 != 1.0f) {
            Matrix.translateM(fArr, 0, 0.5f, 0.5f, 0.0f);
            Matrix.scaleM(fArr, 0, f3, f2, 1.0f);
            Matrix.translateM(fArr, 0, -0.5f, -0.5f, 0.0f);
        }
    }
}
