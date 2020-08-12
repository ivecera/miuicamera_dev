package com.android.camera.effect.renders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.location.Location;
import android.net.Uri;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.EncodingQuality;
import com.android.camera.R;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.ShaderNativeUtil;
import com.android.camera.effect.SnapshotCanvas;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawIntTexAttribute;
import com.android.camera.effect.draw_mode.DrawJPEGAttribute;
import com.android.camera.effect.draw_mode.FillRectAttribute;
import com.android.camera.effect.framework.gles.EglCore;
import com.android.camera.effect.framework.gles.OpenGlUtils;
import com.android.camera.effect.framework.gles.PbufferSurface;
import com.android.camera.effect.framework.graphics.Block;
import com.android.camera.effect.framework.graphics.GraphicBuffer;
import com.android.camera.effect.framework.graphics.Splitter;
import com.android.camera.effect.framework.image.MemImage;
import com.android.camera.effect.framework.utils.CounterUtil;
import com.android.camera.log.Log;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.watermark.WaterMarkBitmap;
import com.android.camera.watermark.WaterMarkData;
import com.android.gallery3d.exif.ExifInterface;
import com.android.gallery3d.ui.BaseGLCanvas;
import com.arcsoft.supernight.SuperNightProcess;
import com.mi.config.b;
import com.xiaomi.camera.core.PictureInfo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SnapshotEffectRender {
    private static final int DEFAULT_BLOCK_HEIGHT = 1500;
    private static final int DEFAULT_BLOCK_WIDTH = 4000;
    private static final int QUEUE_LIMIT = 7;
    /* access modifiers changed from: private */
    public static final String TAG = "SnapshotEffectRender";
    /* access modifiers changed from: private */
    public int mBlockHeight;
    /* access modifiers changed from: private */
    public int mBlockWidth;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public String mCurrentCustomWaterMarkText;
    /* access modifiers changed from: private */
    public Bitmap mDualCameraWaterMarkBitmap;
    /* access modifiers changed from: private */
    public float mDualCameraWaterMarkPaddingXRatio;
    /* access modifiers changed from: private */
    public float mDualCameraWaterMarkPaddingYRatio;
    /* access modifiers changed from: private */
    public float mDualCameraWaterMarkSizeRatio;
    /* access modifiers changed from: private */
    public EglCore mEglCore;
    private EGLHandler mEglHandler;
    private HandlerThread mEglThread;
    /* access modifiers changed from: private */
    public ConditionVariable mEglThreadBlockVar = new ConditionVariable();
    private boolean mExifNeeded = true;
    /* access modifiers changed from: private */
    public FrameBuffer mFrameBuffer;
    /* access modifiers changed from: private */
    public CounterUtil mFrameCounter;
    /* access modifiers changed from: private */
    public Bitmap mFrontCameraWaterMarkBitmap;
    /* access modifiers changed from: private */
    public float mFrontCameraWaterMarkPaddingXRatio;
    /* access modifiers changed from: private */
    public float mFrontCameraWaterMarkPaddingYRatio;
    /* access modifiers changed from: private */
    public float mFrontCameraWaterMarkSizeRatio;
    /* access modifiers changed from: private */
    public SnapshotCanvas mGLCanvas;
    /* access modifiers changed from: private */
    public GraphicBuffer mGraphicBuffer;
    /* access modifiers changed from: private */
    public ImageSaver mImageSaver;
    /* access modifiers changed from: private */
    public boolean mInitGraphicBuffer;
    private boolean mIsImageCaptureIntent;
    private volatile int mJpegQueueSize = 0;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public MemImage mMemImage;
    /* access modifiers changed from: private */
    public int mQuality = 97;
    private boolean mRelease;
    /* access modifiers changed from: private */
    public boolean mReleasePending;
    /* access modifiers changed from: private */
    public CounterUtil mRenderCounter;
    /* access modifiers changed from: private */
    public PbufferSurface mRenderSurface;
    /* access modifiers changed from: private */
    public WeakReference<ImageSaver.ImageSaverCallback> mSaverCallback;
    /* access modifiers changed from: private */
    public Splitter mSplitter;
    /* access modifiers changed from: private */
    public int mSquareModeExtraMargin;
    /* access modifiers changed from: private */
    public int mTextureId;
    /* access modifiers changed from: private */
    public Map<String, String> mTitleMap = new HashMap(7);
    /* access modifiers changed from: private */
    public CounterUtil mTotalCounter;
    /* access modifiers changed from: private */
    public Bitmap mUltraPixelCameraWaterMarkBitmap;

    private class EGLHandler extends Handler {
        public static final int MSG_DRAW_MAIN_ASYNC = 1;
        public static final int MSG_DRAW_MAIN_SYNC = 2;
        public static final int MSG_DRAW_THUMB = 4;
        public static final int MSG_GET_DRAW_THUMB = 3;
        public static final int MSG_INIT_EGL_SYNC = 0;
        public static final int MSG_PREPARE_EFFECT_RENDER = 6;
        public static final int MSG_RELEASE = 5;

        public EGLHandler(Looper looper) {
            super(looper);
        }

        /* JADX DEBUG: Additional 1 move instruction added to help type inference */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: com.android.camera.EncodingQuality} */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r8v3 */
        /* JADX WARN: Type inference failed for: r8v7 */
        private byte[] applyEffect(DrawJPEGAttribute drawJPEGAttribute, int i, boolean z, Size size, Size size2) {
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            EGLHandler eGLHandler;
            int i9;
            ? r8;
            int[] iArr;
            byte[] bArr;
            byte[] bArr2;
            int[] iArr2;
            boolean z2;
            int i10;
            PictureInfo pictureInfo;
            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
            Log.d(SnapshotEffectRender.TAG, "applyEffect: applyToThumb = " + z);
            byte[] thumbnailBytes = z ? drawJPEGAttribute.mExif.getThumbnailBytes() : drawJPEGAttribute.mData;
            if (thumbnailBytes == null) {
                String access$1000 = SnapshotEffectRender.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Null ");
                sb.append(z ? "thumb!" : "jpeg!");
                Log.w(access$1000, sb.toString());
                return null;
            } else if (!z && drawJPEGAttribute.mEffectIndex == FilterInfo.FILTER_ID_NONE && !CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() && !CameraSettings.isTiltShiftOn()) {
                return applyEffectOnlyWatermarkRange(drawJPEGAttribute, size, size2);
            } else {
                CounterUtil counterUtil = new CounterUtil();
                counterUtil.reset("init Texture");
                int[] iArr3 = new int[1];
                GLES20.glGenTextures(1, iArr3, 0);
                int[] initTexture = ShaderNativeUtil.initTexture(thumbnailBytes, iArr3[0], i);
                GLES20.glFlush();
                counterUtil.tick("init Texture");
                int i11 = initTexture[0];
                int i12 = initTexture[1];
                int i13 = z ? initTexture[0] : drawJPEGAttribute.mPreviewWidth;
                int i14 = z ? initTexture[1] : drawJPEGAttribute.mPreviewHeight;
                Render effectRender = getEffectRender(drawJPEGAttribute.mEffectIndex);
                if (effectRender == null) {
                    Log.w(SnapshotEffectRender.TAG, "init render failed");
                    return thumbnailBytes;
                }
                if (effectRender instanceof PipeRender) {
                    ((PipeRender) effectRender).setFrameBufferSize(i11, i12);
                }
                effectRender.setPreviewSize(i13, i14);
                effectRender.setEffectRangeAttribute(drawJPEGAttribute.mAttribute);
                effectRender.setMirror(drawJPEGAttribute.mMirror);
                if (z) {
                    effectRender.setSnapshotSize(i11, i12);
                } else {
                    effectRender.setSnapshotSize(size2.width, size2.height);
                }
                effectRender.setOrientation(drawJPEGAttribute.mOrientation);
                effectRender.setShootRotation(drawJPEGAttribute.mShootRotation);
                effectRender.setJpegOrientation(drawJPEGAttribute.mJpegOrientation);
                checkFrameBuffer(i11, i12);
                SnapshotEffectRender.this.mGLCanvas.beginBindFrameBuffer(SnapshotEffectRender.this.mFrameBuffer);
                effectRender.setParentFrameBufferId(SnapshotEffectRender.this.mFrameBuffer.getId());
                effectRender.draw(new DrawIntTexAttribute(iArr3[0], 0, 0, i11, i12, true));
                effectRender.deleteBuffer();
                if (drawJPEGAttribute.mRequestModuleIdx != 165) {
                    i5 = i11;
                    i2 = i12;
                    i4 = 0;
                    i3 = 0;
                } else if (i11 > i12) {
                    i3 = ((i11 - i12) / 2) - ((SnapshotEffectRender.this.mSquareModeExtraMargin * i12) / Util.sWindowWidth);
                    i5 = i12;
                    i2 = i5;
                    i4 = 0;
                } else {
                    i4 = ((i12 - i11) / 2) - ((SnapshotEffectRender.this.mSquareModeExtraMargin * i11) / Util.sWindowWidth);
                    i5 = i11;
                    i2 = i5;
                    i3 = 0;
                }
                drawAgeGenderAndMagicMirrorWater(drawJPEGAttribute.mWaterInfos, i3, i4, i11, i12, i13, i14, drawJPEGAttribute.mJpegOrientation, drawJPEGAttribute.mIsPortraitRawData);
                if (!z) {
                    drawJPEGAttribute.mWidth = i5;
                    drawJPEGAttribute.mHeight = i2;
                } else if (size != null) {
                    size.width = i5;
                    size.height = i2;
                    Log.d(SnapshotEffectRender.TAG, "thumbSize=" + size.width + "*" + size.height);
                }
                if (drawJPEGAttribute.mApplyWaterMark) {
                    if (!z) {
                        int[] watermarkRange = Util.getWatermarkRange(i5, i2, (drawJPEGAttribute.mJpegOrientation + 270) % 360, drawJPEGAttribute.mDeviceWaterMarkEnabled, drawJPEGAttribute.mTimeWaterMarkText != null, false);
                        z2 = false;
                        i9 = i3;
                        i8 = 1;
                        i7 = i4;
                        i10 = i5;
                        eGLHandler = this;
                        iArr2 = watermarkRange;
                        bArr2 = ShaderNativeUtil.getPicture(watermarkRange[0] + i9, watermarkRange[1] + i7, watermarkRange[2], watermarkRange[3], SnapshotEffectRender.this.mQuality);
                    } else {
                        z2 = false;
                        i8 = 1;
                        i7 = i4;
                        i10 = i5;
                        i9 = i3;
                        eGLHandler = this;
                        iArr2 = null;
                        bArr2 = null;
                    }
                    i6 = i10;
                    drawTimeWaterMark(drawJPEGAttribute, i9, i7, i10, i2, drawJPEGAttribute.mJpegOrientation, null);
                    if (!DataRepository.dataItemFeature().c_0x46() || (pictureInfo = drawJPEGAttribute.mInfo) == null || !pictureInfo.isFrontCamera()) {
                        drawDoubleShotWaterMark(drawJPEGAttribute, i9, i7, i6, i2, drawJPEGAttribute.mJpegOrientation, null);
                    } else {
                        drawFrontCameraWaterMark(drawJPEGAttribute, i9, i7, i6, i2, drawJPEGAttribute.mJpegOrientation, null);
                    }
                    iArr = iArr2;
                    bArr = bArr2;
                    r8 = z2;
                } else {
                    r8 = 0;
                    i8 = 1;
                    i7 = i4;
                    i6 = i5;
                    i9 = i3;
                    eGLHandler = this;
                    bArr = null;
                    iArr = null;
                }
                counterUtil.tick("draw");
                GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, i8);
                int access$2900 = SnapshotEffectRender.this.mQuality;
                if (z) {
                    access$2900 = Math.min(SnapshotEffectRender.this.mQuality, EncodingQuality.NORMAL.toInteger(r8));
                }
                byte[] picture = ShaderNativeUtil.getPicture(i9, i7, i6, i2, access$2900);
                counterUtil.tick("readpixels");
                if (GLES20.glIsTexture(iArr3[r8])) {
                    GLES20.glDeleteTextures(i8, iArr3, r8);
                }
                SnapshotEffectRender.this.mGLCanvas.endBindFrameBuffer();
                SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
                if (drawJPEGAttribute.mApplyWaterMark) {
                    drawJPEGAttribute.mDataOfTheRegionUnderWatermarks = bArr;
                    drawJPEGAttribute.mCoordinatesOfTheRegionUnderWatermarks = iArr;
                }
                return picture;
            }
        }

        private byte[] applyEffectOnlyWatermarkRange(DrawJPEGAttribute drawJPEGAttribute, Size size, Size size2) {
            int i;
            int i2;
            int i3;
            int i4;
            PictureInfo pictureInfo;
            if (!drawJPEGAttribute.mApplyWaterMark && drawJPEGAttribute.mRequestModuleIdx != 165) {
                return drawJPEGAttribute.mData;
            }
            long currentTimeMillis = System.currentTimeMillis();
            int[] decompressPicture = ShaderNativeUtil.decompressPicture(drawJPEGAttribute.mData, 1);
            Log.d(SnapshotEffectRender.TAG, "jpeg decompress total time =" + (System.currentTimeMillis() - currentTimeMillis));
            int i5 = drawJPEGAttribute.mPreviewWidth;
            int i6 = drawJPEGAttribute.mPreviewHeight;
            int i7 = decompressPicture[0];
            int i8 = decompressPicture[1];
            if (drawJPEGAttribute.mRequestModuleIdx != 165) {
                i2 = i8;
                i3 = i7;
                i4 = 0;
                i = 0;
            } else if (i7 > i8) {
                i3 = i8;
                i2 = i3;
                i = ((i7 - i8) / 2) - ((SnapshotEffectRender.this.mSquareModeExtraMargin * i8) / Util.sWindowWidth);
                i4 = 0;
            } else {
                i4 = ((i8 - i7) / 2) - ((SnapshotEffectRender.this.mSquareModeExtraMargin * i7) / Util.sWindowWidth);
                i3 = i7;
                i2 = i3;
                i = 0;
            }
            drawJPEGAttribute.mWidth = i3;
            drawJPEGAttribute.mHeight = i2;
            if (drawJPEGAttribute.mRequestModuleIdx == 165 && !drawJPEGAttribute.mDeviceWaterMarkEnabled && drawJPEGAttribute.mTimeWaterMarkText == null) {
                ShaderNativeUtil.getCenterSquareImage(i, i4);
                return ShaderNativeUtil.compressPicture(i3, i2, SnapshotEffectRender.this.mQuality);
            }
            int[] watermarkRange = Util.getWatermarkRange(drawJPEGAttribute.mWidth, drawJPEGAttribute.mHeight, (drawJPEGAttribute.mJpegOrientation + 270) % 360, drawJPEGAttribute.mDeviceWaterMarkEnabled, drawJPEGAttribute.mTimeWaterMarkText != null, false);
            int i9 = watermarkRange[2];
            int i10 = watermarkRange[3];
            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
            SnapshotEffectRender.this.mGraphicBuffer.reszieBuffer(i9, i10);
            int unused = SnapshotEffectRender.this.mBlockWidth = i9;
            int unused2 = SnapshotEffectRender.this.mBlockHeight = i10;
            long currentTimeMillis2 = System.currentTimeMillis();
            int[] iArr = {OpenGlUtils.genTexture()};
            byte[] jpegFromMemImage = ShaderNativeUtil.getJpegFromMemImage(watermarkRange[0] + i, watermarkRange[1] + i4, watermarkRange[2], watermarkRange[3], SnapshotEffectRender.this.mQuality);
            int i11 = (((watermarkRange[1] + i4) * i7) + watermarkRange[0] + i) * 3;
            ShaderNativeUtil.updateTextureWidthStride(iArr[0], i9, i10, i7, i11);
            Log.d(SnapshotEffectRender.TAG, "get pixel and upload total time =" + (System.currentTimeMillis() - currentTimeMillis2));
            Render effectRender = getEffectRender(drawJPEGAttribute.mEffectIndex);
            if (effectRender == null) {
                Log.w(SnapshotEffectRender.TAG, "init render failed");
                return drawJPEGAttribute.mData;
            }
            effectRender.setPreviewSize(i5, i6);
            effectRender.setEffectRangeAttribute(drawJPEGAttribute.mAttribute);
            effectRender.setMirror(drawJPEGAttribute.mMirror);
            effectRender.setSnapshotSize(size2.width, size2.height);
            effectRender.setOrientation(drawJPEGAttribute.mOrientation);
            effectRender.setShootRotation(drawJPEGAttribute.mShootRotation);
            effectRender.setJpegOrientation(drawJPEGAttribute.mJpegOrientation);
            SnapshotEffectRender.this.mGLCanvas.beginBindFrameBuffer(SnapshotEffectRender.this.mGraphicBuffer.getFrameBufferId(), i9, i10);
            long currentTimeMillis3 = System.currentTimeMillis();
            ((PipeRender) effectRender).drawOnExtraFrameBufferOnce(new DrawIntTexAttribute(iArr[0], 0, 0, i9, i10, true));
            effectRender.deleteBuffer();
            int i12 = -watermarkRange[0];
            int i13 = -watermarkRange[1];
            drawTimeWaterMark(drawJPEGAttribute, i12, i13, i3, i2, drawJPEGAttribute.mJpegOrientation, null);
            if (!DataRepository.dataItemFeature().c_0x46() || (pictureInfo = drawJPEGAttribute.mInfo) == null || !pictureInfo.isFrontCamera()) {
                drawDoubleShotWaterMark(drawJPEGAttribute, i12, i13, i3, i2, drawJPEGAttribute.mJpegOrientation, null);
            } else {
                drawFrontCameraWaterMark(drawJPEGAttribute, i12, i13, i3, i2, drawJPEGAttribute.mJpegOrientation, null);
            }
            Log.d(SnapshotEffectRender.TAG, "drawTime=" + (System.currentTimeMillis() - currentTimeMillis3));
            effectRender.deleteBuffer();
            GLES20.glFinish();
            if (drawJPEGAttribute.mRequestModuleIdx == 165) {
                ShaderNativeUtil.getCenterSquareImage(i, i4);
                SnapshotEffectRender.this.mGraphicBuffer.readBuffer(i9, i10, ((watermarkRange[1] * i3) + watermarkRange[0]) * 3);
            } else {
                SnapshotEffectRender.this.mGraphicBuffer.readBuffer(i9, i10, i11);
            }
            long currentTimeMillis4 = System.currentTimeMillis();
            byte[] compressPicture = ShaderNativeUtil.compressPicture(i3, i2, SnapshotEffectRender.this.mQuality);
            Log.d(SnapshotEffectRender.TAG, "compress=" + (System.currentTimeMillis() - currentTimeMillis4));
            if (GLES20.glIsTexture(iArr[0])) {
                GLES20.glDeleteTextures(1, iArr, 0);
            }
            SnapshotEffectRender.this.mGLCanvas.endBindFrameBuffer();
            SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
            drawJPEGAttribute.mDataOfTheRegionUnderWatermarks = jpegFromMemImage;
            drawJPEGAttribute.mCoordinatesOfTheRegionUnderWatermarks = watermarkRange;
            return compressPicture;
        }

        private byte[] applyMiMovieBlackBridge(DrawJPEGAttribute drawJPEGAttribute, Size size) {
            int[] decompressPicture = ShaderNativeUtil.decompressPicture(drawJPEGAttribute.mData, 1);
            int i = drawJPEGAttribute.mPreviewWidth;
            int i2 = drawJPEGAttribute.mPreviewHeight;
            int i3 = decompressPicture[0];
            int i4 = decompressPicture[1];
            drawJPEGAttribute.mWidth = i3;
            drawJPEGAttribute.mHeight = i4;
            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
            SnapshotEffectRender.this.mGraphicBuffer.reszieBuffer(i3, i4);
            int[] iArr = {OpenGlUtils.genTexture()};
            ShaderNativeUtil.updateTextureWidthStride(iArr[0], i3, i4, i3, 0);
            Render effectRender = getEffectRender(drawJPEGAttribute.mEffectIndex);
            if (effectRender == null) {
                Log.w(SnapshotEffectRender.TAG, "init render failed");
                return null;
            }
            effectRender.setPreviewSize(i, i2);
            effectRender.setEffectRangeAttribute(drawJPEGAttribute.mAttribute);
            effectRender.setMirror(drawJPEGAttribute.mMirror);
            effectRender.setSnapshotSize(size.width, size.height);
            effectRender.setOrientation(drawJPEGAttribute.mOrientation);
            effectRender.setShootRotation(drawJPEGAttribute.mShootRotation);
            effectRender.setJpegOrientation(drawJPEGAttribute.mJpegOrientation);
            SnapshotEffectRender.this.mGLCanvas.beginBindFrameBuffer(SnapshotEffectRender.this.mGraphicBuffer.getFrameBufferId(), i3, i4);
            ((PipeRender) effectRender).drawOnExtraFrameBufferOnce(new DrawIntTexAttribute(iArr[0], 0, 0, i3, i4, true));
            drawBlackBridge(size.width, size.height);
            effectRender.deleteBuffer();
            GLES20.glFinish();
            SnapshotEffectRender.this.mGraphicBuffer.readBuffer(i3, i4, 0);
            byte[] compressPicture = ShaderNativeUtil.compressPicture(i3, i4, SnapshotEffectRender.this.mQuality);
            if (GLES20.glIsTexture(iArr[0])) {
                GLES20.glDeleteTextures(1, iArr, 0);
            }
            SnapshotEffectRender.this.mGLCanvas.endBindFrameBuffer();
            SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
            return compressPicture;
        }

        /* JADX WARNING: Removed duplicated region for block: B:28:0x017e  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x01e2  */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x01f9  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x0487  */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x049c  */
        /* JADX WARNING: Removed duplicated region for block: B:58:0x04bd  */
        /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
        private void blockSplitApplyEffect(DrawJPEGAttribute drawJPEGAttribute, int i, boolean z, Size size, Size size2) {
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            RectF rectF;
            int[] iArr;
            int i8;
            PictureInfo pictureInfo;
            DrawJPEGAttribute drawJPEGAttribute2 = drawJPEGAttribute;
            Log.d(SnapshotEffectRender.TAG, "applyEffect: applyToThumb = " + z);
            byte[] thumbnailBytes = z ? drawJPEGAttribute2.mExif.getThumbnailBytes() : drawJPEGAttribute2.mData;
            if (thumbnailBytes == null) {
                String access$1000 = SnapshotEffectRender.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Null ");
                sb.append(z ? "thumb!" : "jpeg!");
                Log.w(access$1000, sb.toString());
                return;
            }
            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
            int unused = SnapshotEffectRender.this.mTextureId = OpenGlUtils.genTexture();
            SnapshotEffectRender.this.mMemImage.getPixelsFromJpg(thumbnailBytes);
            int i9 = SnapshotEffectRender.this.mMemImage.mWidth;
            int i10 = SnapshotEffectRender.this.mMemImage.mHeight;
            int i11 = drawJPEGAttribute2.mPreviewWidth;
            int i12 = drawJPEGAttribute2.mPreviewHeight;
            SnapshotEffectRender.this.mRenderCounter.reset("[NewEffectFramework]start");
            CounterUtil counterUtil = new CounterUtil();
            counterUtil.reset("local start");
            List<Block> split = SnapshotEffectRender.this.mSplitter.split(SnapshotEffectRender.this.mMemImage.mWidth, SnapshotEffectRender.this.mMemImage.mHeight, SnapshotEffectRender.this.mBlockWidth, SnapshotEffectRender.this.mBlockHeight, SnapshotEffectRender.this.mBlockWidth, SnapshotEffectRender.this.mBlockHeight);
            counterUtil.tick("local start setImageSettings");
            Render effectRender = getEffectRender(drawJPEGAttribute2.mEffectIndex);
            if (effectRender == null) {
                Log.w(SnapshotEffectRender.TAG, "init render failed");
                return;
            }
            effectRender.setPreviewSize(i11, i12);
            effectRender.setEffectRangeAttribute(drawJPEGAttribute2.mAttribute);
            effectRender.setMirror(drawJPEGAttribute2.mMirror);
            if (z) {
                effectRender.setSnapshotSize(i9, i10);
            } else {
                effectRender.setSnapshotSize(size2.width, size2.height);
            }
            effectRender.setOrientation(drawJPEGAttribute2.mOrientation);
            effectRender.setShootRotation(drawJPEGAttribute2.mShootRotation);
            effectRender.setJpegOrientation(drawJPEGAttribute2.mJpegOrientation);
            counterUtil.tick("local start render");
            SnapshotEffectRender.this.mGLCanvas.beginBindFrameBuffer(SnapshotEffectRender.this.mGraphicBuffer.getFrameBufferId(), SnapshotEffectRender.this.mBlockWidth, SnapshotEffectRender.this.mBlockHeight);
            counterUtil.tick("local beginBindFrameBuffer");
            SnapshotEffectRender.this.mGLCanvas.getState().pushState();
            counterUtil.tick("local beginBindFrameBuffer");
            if (drawJPEGAttribute2.mRequestModuleIdx != 165) {
                i2 = i9;
                i3 = i10;
                i5 = 0;
            } else if (i9 > i10) {
                i4 = ((i9 - i10) / 2) - ((SnapshotEffectRender.this.mSquareModeExtraMargin * i10) / Util.sWindowWidth);
                i3 = i10;
                i2 = i3;
                i5 = 0;
                drawJPEGAttribute2.mWidth = i2;
                drawJPEGAttribute2.mHeight = i3;
                WaterMark waterMark = null;
                if (!drawJPEGAttribute2.mApplyWaterMark) {
                    i6 = i3;
                    int[] watermarkRange = Util.getWatermarkRange(drawJPEGAttribute2.mWidth, drawJPEGAttribute2.mHeight, (drawJPEGAttribute2.mJpegOrientation + 270) % 360, drawJPEGAttribute2.mDeviceWaterMarkEnabled, drawJPEGAttribute2.mTimeWaterMarkText != null, false);
                    i7 = i2;
                    RectF rectF2 = new RectF((float) (watermarkRange[0] + i4), (float) (watermarkRange[1] + i5), (float) (watermarkRange[0] + i4 + watermarkRange[2]), (float) (watermarkRange[1] + i5 + watermarkRange[3]));
                    ShaderNativeUtil.genWaterMarkRange(watermarkRange[0] + i4, watermarkRange[1] + i5, watermarkRange[2], watermarkRange[3], 3);
                    iArr = watermarkRange;
                    rectF = rectF2;
                } else {
                    i6 = i3;
                    i7 = i2;
                    iArr = null;
                    rectF = null;
                }
                RectF rectF3 = new RectF();
                WaterMark waterMark2 = null;
                WaterMark waterMark3 = null;
                i8 = 0;
                while (i8 < split.size()) {
                    SnapshotEffectRender.this.mFrameCounter.reset(String.format("[loop%d/%d]begin", Integer.valueOf(i8), Integer.valueOf(split.size())));
                    Block block = split.get(i8);
                    int i13 = block.mWidth;
                    int i14 = block.mHeight;
                    int i15 = block.mRowStride;
                    int i16 = block.mOffset;
                    int i17 = SnapshotEffectRender.this.mMemImage.mChannels;
                    GLES20.glViewport(0, 0, i13, i14);
                    GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
                    GLES20.glClear(16640);
                    SnapshotEffectRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl predraw", Integer.valueOf(i8), Integer.valueOf(split.size())));
                    int i18 = i16 * i17;
                    SnapshotEffectRender.this.mMemImage.textureWithStride(SnapshotEffectRender.this.mTextureId, i13, i14, i15, i18);
                    SnapshotEffectRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl uploadtexture textureId %d", Integer.valueOf(i8), Integer.valueOf(split.size()), Integer.valueOf(SnapshotEffectRender.this.mTextureId)));
                    ((PipeRender) effectRender).drawOnExtraFrameBufferOnce(new DrawIntTexAttribute(SnapshotEffectRender.this.mTextureId, 0, 0, i13, i14, true));
                    int[] offset = block.getOffset(i9, i10);
                    rectF3.left = (float) offset[0];
                    rectF3.top = (float) offset[1];
                    rectF3.right = (float) (offset[0] + i13);
                    rectF3.bottom = (float) (offset[1] + i14);
                    int i19 = i4 - offset[0];
                    int i20 = i5 - offset[1];
                    SnapshotEffectRender.this.mGLCanvas.getState().pushState();
                    drawJPEGAttribute2 = drawJPEGAttribute;
                    drawAgeGenderAndMagicMirrorWater(drawJPEGAttribute.mWaterInfos, -offset[0], -offset[1], i9, i10, i11, i12, drawJPEGAttribute.mJpegOrientation, drawJPEGAttribute.mIsPortraitRawData);
                    if (drawJPEGAttribute2.mApplyWaterMark && rectangle_collision(rectF3.left, rectF3.top, rectF3.width(), rectF3.height(), rectF.left, rectF.top, rectF.width(), rectF.height())) {
                        float[] intersectRect = getIntersectRect(rectF3.left, rectF3.top, rectF3.right, rectF3.bottom, rectF.left, rectF.top, rectF.right, rectF.bottom);
                        ShaderNativeUtil.mergeWaterMarkRange((int) intersectRect[0], (int) intersectRect[1], (int) (intersectRect[2] - intersectRect[0]), (int) (intersectRect[3] - intersectRect[1]), offset[0], offset[1], 3);
                        waterMark = drawTimeWaterMark(drawJPEGAttribute, i19, i20, i7, i6, drawJPEGAttribute2.mJpegOrientation, waterMark);
                        if (!DataRepository.dataItemFeature().c_0x46() || (pictureInfo = drawJPEGAttribute2.mInfo) == null || !pictureInfo.isFrontCamera()) {
                            waterMark2 = drawDoubleShotWaterMark(drawJPEGAttribute, i19, i20, i7, i6, drawJPEGAttribute2.mJpegOrientation, waterMark2);
                        } else {
                            waterMark3 = drawFrontCameraWaterMark(drawJPEGAttribute, i19, i20, i7, i6, drawJPEGAttribute2.mJpegOrientation, waterMark3);
                        }
                    }
                    SnapshotEffectRender.this.mGLCanvas.getState().popState();
                    SnapshotEffectRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl gldraw", Integer.valueOf(i8), Integer.valueOf(split.size())));
                    GLES20.glFinish();
                    SnapshotEffectRender.this.mGraphicBuffer.readBuffer(i13, i14, i18);
                    SnapshotEffectRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl readPixelAndMerge", Integer.valueOf(i8), Integer.valueOf(split.size())));
                    i8++;
                    rectF3 = rectF3;
                    rectF = rectF;
                    i6 = i6;
                    i10 = i10;
                    i12 = i12;
                    iArr = iArr;
                    effectRender = effectRender;
                    split = split;
                    i9 = i9;
                    i11 = i11;
                    i5 = i5;
                    i4 = i4;
                }
                if (GLES20.glIsTexture(SnapshotEffectRender.this.mTextureId)) {
                    GLES20.glDeleteTextures(1, new int[]{SnapshotEffectRender.this.mTextureId}, 0);
                }
                if (drawJPEGAttribute2.mRequestModuleIdx == 165) {
                    ShaderNativeUtil.getCenterSquareImage(i4, i5);
                }
                SnapshotEffectRender.this.mGLCanvas.getState().popState();
                SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
                if (!drawJPEGAttribute2.mApplyWaterMark) {
                    drawJPEGAttribute2.mDataOfTheRegionUnderWatermarks = ShaderNativeUtil.getWaterMarkRange(SnapshotEffectRender.this.mQuality, 3);
                    drawJPEGAttribute2.mCoordinatesOfTheRegionUnderWatermarks = iArr;
                    return;
                }
                return;
            } else {
                i5 = ((i10 - i9) / 2) - ((SnapshotEffectRender.this.mSquareModeExtraMargin * i9) / Util.sWindowWidth);
                i3 = i9;
                i2 = i3;
            }
            i4 = 0;
            drawJPEGAttribute2.mWidth = i2;
            drawJPEGAttribute2.mHeight = i3;
            WaterMark waterMark4 = null;
            if (!drawJPEGAttribute2.mApplyWaterMark) {
            }
            RectF rectF32 = new RectF();
            WaterMark waterMark22 = null;
            WaterMark waterMark32 = null;
            i8 = 0;
            while (i8 < split.size()) {
            }
            if (GLES20.glIsTexture(SnapshotEffectRender.this.mTextureId)) {
            }
            if (drawJPEGAttribute2.mRequestModuleIdx == 165) {
            }
            SnapshotEffectRender.this.mGLCanvas.getState().popState();
            SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
            if (!drawJPEGAttribute2.mApplyWaterMark) {
            }
        }

        private void checkFrameBuffer(int i, int i2) {
            if (SnapshotEffectRender.this.mFrameBuffer == null || SnapshotEffectRender.this.mFrameBuffer.getWidth() < i || SnapshotEffectRender.this.mFrameBuffer.getHeight() < i2) {
                FrameBuffer unused = SnapshotEffectRender.this.mFrameBuffer = null;
                SnapshotEffectRender snapshotEffectRender = SnapshotEffectRender.this;
                FrameBuffer unused2 = snapshotEffectRender.mFrameBuffer = new FrameBuffer(snapshotEffectRender.mGLCanvas, i, i2, 0);
            }
        }

        private void drawAgeGenderAndMagicMirrorWater(List<WaterMarkData> list, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
            if (b.al() && !z && CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()) {
                WaterMarkBitmap waterMarkBitmap = new WaterMarkBitmap(list);
                WaterMarkData waterMarkData = waterMarkBitmap.getWaterMarkData();
                if (waterMarkData != null) {
                    drawWaterMark(new AgeGenderAndMagicMirrorWaterMark(waterMarkData.getImage(), i3, i4, i7, i5, i6, 0.0f, 0.0f), i, i2, i7 - waterMarkData.getOrientation());
                }
                waterMarkBitmap.releaseBitmap();
                Log.d(WaterMarkBitmap.class.getSimpleName(), "Draw age_gender_and_magic_mirror water mark");
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:31:0x00ab A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x00ac  */
        private WaterMark drawDoubleShotWaterMark(DrawJPEGAttribute drawJPEGAttribute, int i, int i2, int i3, int i4, int i5, WaterMark waterMark) {
            Bitmap bitmap;
            Bitmap bitmap2;
            if (!b.al()) {
                return null;
            }
            if (waterMark != null) {
                drawWaterMark(waterMark, i, i2, i5);
                return waterMark;
            } else if (!drawJPEGAttribute.mDeviceWaterMarkEnabled) {
                return null;
            } else {
                boolean equals = CameraSettings.getCustomWatermark().equals(CameraSettings.getDefaultWatermarkStr());
                if (!drawJPEGAttribute.mIsUltraPixelWatermarkEnabled || DataRepository.dataItemFeature().Vb() || !equals) {
                    if (!SnapshotEffectRender.this.mCurrentCustomWaterMarkText.equals(CameraSettings.getCustomWatermark())) {
                        String unused = SnapshotEffectRender.this.mCurrentCustomWaterMarkText = CameraSettings.getCustomWatermark();
                        SnapshotEffectRender snapshotEffectRender = SnapshotEffectRender.this;
                        Bitmap unused2 = snapshotEffectRender.mDualCameraWaterMarkBitmap = snapshotEffectRender.loadCameraWatermark(snapshotEffectRender.mContext);
                    } else if (SnapshotEffectRender.this.mDualCameraWaterMarkBitmap == null) {
                        SnapshotEffectRender snapshotEffectRender2 = SnapshotEffectRender.this;
                        Bitmap unused3 = snapshotEffectRender2.mDualCameraWaterMarkBitmap = snapshotEffectRender2.loadCameraWatermark(snapshotEffectRender2.mContext);
                    }
                    if (SnapshotEffectRender.this.mDualCameraWaterMarkBitmap != null) {
                        bitmap2 = SnapshotEffectRender.this.mDualCameraWaterMarkBitmap;
                    }
                    bitmap = null;
                    if (bitmap == null) {
                        return null;
                    }
                    ImageWaterMark imageWaterMark = new ImageWaterMark(bitmap, i3, i4, i5, SnapshotEffectRender.this.mDualCameraWaterMarkSizeRatio, SnapshotEffectRender.this.mDualCameraWaterMarkPaddingXRatio, SnapshotEffectRender.this.mDualCameraWaterMarkPaddingYRatio, false);
                    drawWaterMark(imageWaterMark, i, i2, i5);
                    return imageWaterMark;
                }
                if (SnapshotEffectRender.this.mUltraPixelCameraWaterMarkBitmap == null) {
                    SnapshotEffectRender snapshotEffectRender3 = SnapshotEffectRender.this;
                    Bitmap unused4 = snapshotEffectRender3.mUltraPixelCameraWaterMarkBitmap = snapshotEffectRender3.loadUltraPixelWatermark(snapshotEffectRender3.mContext);
                }
                if (SnapshotEffectRender.this.mUltraPixelCameraWaterMarkBitmap != null) {
                    bitmap2 = SnapshotEffectRender.this.mUltraPixelCameraWaterMarkBitmap;
                }
                bitmap = null;
                if (bitmap == null) {
                }
                bitmap = bitmap2;
                if (bitmap == null) {
                }
            }
        }

        private WaterMark drawFrontCameraWaterMark(DrawJPEGAttribute drawJPEGAttribute, int i, int i2, int i3, int i4, int i5, WaterMark waterMark) {
            if (!b.al()) {
                return null;
            }
            if (waterMark != null) {
                drawWaterMark(waterMark, i, i2, i5);
                return waterMark;
            } else if (!drawJPEGAttribute.mDeviceWaterMarkEnabled || SnapshotEffectRender.this.mFrontCameraWaterMarkBitmap == null) {
                return null;
            } else {
                MimojiImageWaterMark mimojiImageWaterMark = new MimojiImageWaterMark(SnapshotEffectRender.this.mFrontCameraWaterMarkBitmap, i3, i4, i5, SnapshotEffectRender.this.mFrontCameraWaterMarkSizeRatio, SnapshotEffectRender.this.mFrontCameraWaterMarkPaddingXRatio, SnapshotEffectRender.this.mFrontCameraWaterMarkPaddingYRatio);
                drawWaterMark(mimojiImageWaterMark, i, i2, i5);
                return mimojiImageWaterMark;
            }
        }

        private boolean drawMainJpeg(DrawJPEGAttribute drawJPEGAttribute, boolean z, boolean z2) {
            Size size = new Size(drawJPEGAttribute.mWidth, drawJPEGAttribute.mHeight);
            int i = 1;
            while (true) {
                int i2 = drawJPEGAttribute.mWidth;
                int i3 = BaseGLCanvas.sMaxTextureSize;
                if (i2 <= i3 && drawJPEGAttribute.mHeight <= i3) {
                    break;
                }
                drawJPEGAttribute.mWidth /= 2;
                drawJPEGAttribute.mHeight /= 2;
                i *= 2;
            }
            Log.d(SnapshotEffectRender.TAG, String.format(Locale.US, "downScale: %d width: %d %d parallel: %b", Integer.valueOf(i), Integer.valueOf(drawJPEGAttribute.mWidth), Integer.valueOf(drawJPEGAttribute.mPreviewWidth), Boolean.valueOf(z2)));
            if (drawJPEGAttribute.isMiMovieOpen && !z2) {
                return saveMainJpeg(drawJPEGAttribute, z, z2, applyMiMovieBlackBridge(drawJPEGAttribute, size));
            }
            if (drawJPEGAttribute.mEffectIndex == FilterInfo.FILTER_ID_NONE && !CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() && !CameraSettings.isTiltShiftOn()) {
                return saveMainJpeg(drawJPEGAttribute, z, z2, applyEffectOnlyWatermarkRange(drawJPEGAttribute, null, size));
            }
            if (CameraSettings.isTiltShiftOn()) {
                saveMainJpeg(drawJPEGAttribute, false, z2, applyEffect(drawJPEGAttribute, i, false, null, size));
            } else {
                blockSplitApplyEffect(drawJPEGAttribute, i, false, null, size);
                SnapshotEffectRender.this.mRenderCounter.tick("[NewEffectFramework] done");
                saveMainJpeg(drawJPEGAttribute, false, z2, SnapshotEffectRender.this.mMemImage.encodeJpeg(SnapshotEffectRender.this.mQuality, drawJPEGAttribute.mWidth, drawJPEGAttribute.mHeight));
            }
            SnapshotEffectRender.this.mTotalCounter.tick("TOTAL finish");
            return true;
        }

        private boolean drawThumbJpeg(DrawJPEGAttribute drawJPEGAttribute, boolean z) {
            if (drawJPEGAttribute.mExif == null) {
                drawJPEGAttribute.mExif = SnapshotEffectRender.this.getExif(drawJPEGAttribute);
                if (!TextUtils.isEmpty(drawJPEGAttribute.mAlgorithmName)) {
                    drawJPEGAttribute.mExif.addAlgorithmComment(drawJPEGAttribute.mAlgorithmName);
                }
            }
            Size size = new Size();
            byte[] applyEffect = applyEffect(drawJPEGAttribute, 1, true, size, null);
            String access$1000 = SnapshotEffectRender.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("drawThumbJpeg: thumbLen=");
            sb.append(applyEffect == null ? "null" : Integer.valueOf(applyEffect.length));
            Log.d(access$1000, sb.toString());
            if (applyEffect != null) {
                drawJPEGAttribute.mExif.setCompressedThumbnail(applyEffect);
            }
            boolean z2 = drawJPEGAttribute.mJpegOrientation != 0;
            if (z && drawJPEGAttribute.mExif.getThumbnailBytes() != null) {
                drawJPEGAttribute.mUri = Storage.addImage(SnapshotEffectRender.this.mContext, drawJPEGAttribute.mTitle, drawJPEGAttribute.mDate, drawJPEGAttribute.mLoc, drawJPEGAttribute.mJpegOrientation, drawJPEGAttribute.mExif.getThumbnailBytes(), false, size.width, size.height, false, false, false, z2, false, drawJPEGAttribute.mAlgorithmName, null);
                ImageSaver.ImageSaverCallback imageSaverCallback = (ImageSaver.ImageSaverCallback) SnapshotEffectRender.this.mSaverCallback.get();
                Uri uri = drawJPEGAttribute.mUri;
                if (!(uri == null || imageSaverCallback == null)) {
                    imageSaverCallback.onNewUriArrived(uri, drawJPEGAttribute.mTitle);
                }
            }
            return true;
        }

        private WaterMark drawTimeWaterMark(DrawJPEGAttribute drawJPEGAttribute, int i, int i2, int i3, int i4, int i5, WaterMark waterMark) {
            if (!b.al()) {
                return null;
            }
            if (waterMark != null) {
                drawWaterMark(waterMark, i, i2, i5);
                return waterMark;
            }
            String str = drawJPEGAttribute.mTimeWaterMarkText;
            if (str == null || waterMark != null) {
                return null;
            }
            WaterMark newStyleTextWaterMark = b.bm() ? new NewStyleTextWaterMark(str, i3, i4, i5, false) : new TextWaterMark(str, i3, i4, i5);
            drawWaterMark(newStyleTextWaterMark, i, i2, i5);
            return newStyleTextWaterMark;
        }

        private void drawWaterMark(WaterMark waterMark, int i, int i2, int i3) {
            SnapshotEffectRender.this.mGLCanvas.getState().pushState();
            if (i3 != 0) {
                SnapshotEffectRender.this.mGLCanvas.getState().translate((float) (waterMark.getCenterX() + i), (float) (waterMark.getCenterY() + i2));
                SnapshotEffectRender.this.mGLCanvas.getState().rotate((float) (-i3), 0.0f, 0.0f, 1.0f);
                SnapshotEffectRender.this.mGLCanvas.getState().translate((float) ((-i) - waterMark.getCenterX()), (float) ((-i2) - waterMark.getCenterY()));
            }
            SnapshotEffectRender.this.mGLCanvas.getBasicRender().draw(new DrawBasicTexAttribute(waterMark.getTexture(), i + waterMark.getLeft(), i2 + waterMark.getTop(), waterMark.getWidth(), waterMark.getHeight()));
            SnapshotEffectRender.this.mGLCanvas.getState().popState();
        }

        private Render fetchRender(int i) {
            RenderGroup effectRenderGroup = SnapshotEffectRender.this.mGLCanvas.getEffectRenderGroup();
            Render render = effectRenderGroup.getRender(i);
            if (render != null) {
                return render;
            }
            SnapshotEffectRender.this.mGLCanvas.prepareEffectRenders(false, i);
            return effectRenderGroup.getRender(i);
        }

        private Render getEffectRender(int i) {
            Render fetchRender;
            PipeRender pipeRender = new PipeRender(SnapshotEffectRender.this.mGLCanvas);
            if (!(i == FilterInfo.FILTER_ID_NONE || (fetchRender = fetchRender(i)) == null)) {
                pipeRender.addRender(fetchRender);
            }
            if (CameraSettings.isTiltShiftOn()) {
                Render render = null;
                String componentValue = DataRepository.dataItemRunning().getComponentRunningTiltValue().getComponentValue(160);
                if (ComponentRunningTiltValue.TILT_CIRCLE.equals(componentValue)) {
                    render = fetchRender(FilterInfo.FILTER_ID_GAUSSIAN);
                } else if (ComponentRunningTiltValue.TILT_PARALLEL.equals(componentValue)) {
                    render = fetchRender(FilterInfo.FILTER_ID_TILTSHIFT);
                }
                if (render != null) {
                    pipeRender.addRender(render);
                }
            }
            if (pipeRender.getRenderSize() == 0) {
                pipeRender.addRender(fetchRender(i));
            }
            return pipeRender;
        }

        private float[] getIntersectRect(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            float[] fArr = new float[4];
            if (f2 <= f6) {
                f2 = f6;
            }
            fArr[0] = f2;
            if (f3 <= f7) {
                f3 = f7;
            }
            fArr[1] = f3;
            int i = (f4 > f8 ? 1 : (f4 == f8 ? 0 : -1));
            fArr[2] = f8;
            if (f5 >= f9) {
                f5 = f9;
            }
            fArr[3] = f5;
            return fArr;
        }

        private void initEGL(EGLContext eGLContext, boolean z) {
            if (SnapshotEffectRender.this.mEglCore == null) {
                EglCore unused = SnapshotEffectRender.this.mEglCore = new EglCore(eGLContext, 2);
            }
            if (z && SnapshotEffectRender.this.mRenderSurface != null) {
                SnapshotEffectRender.this.mRenderSurface.release();
                PbufferSurface unused2 = SnapshotEffectRender.this.mRenderSurface = null;
            }
            if (SnapshotEffectRender.this.mRenderSurface == null) {
                SnapshotEffectRender snapshotEffectRender = SnapshotEffectRender.this;
                PbufferSurface unused3 = snapshotEffectRender.mRenderSurface = new PbufferSurface(snapshotEffectRender.mEglCore, 1, 1);
            }
            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
        }

        private boolean rectangle_collision(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            return f2 <= f8 + f6 && f2 + f4 >= f6 && f3 <= f9 + f7 && f3 + f5 >= f7;
        }

        private void release() {
            if (SnapshotEffectRender.this.mFrameBuffer != null) {
                SnapshotEffectRender.this.mFrameBuffer.release();
            }
            FrameBuffer unused = SnapshotEffectRender.this.mFrameBuffer = null;
            SnapshotEffectRender.this.mGLCanvas.recycledResources();
            SnapshotCanvas unused2 = SnapshotEffectRender.this.mGLCanvas = null;
            SnapshotEffectRender.this.destroy();
        }

        private boolean saveMainJpeg(DrawJPEGAttribute drawJPEGAttribute, boolean z, boolean z2, byte[] bArr) {
            String str;
            String access$1000 = SnapshotEffectRender.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("mainLen=");
            sb.append(bArr == null ? "null" : Integer.valueOf(bArr.length));
            Log.d(access$1000, sb.toString());
            if (bArr != null) {
                drawJPEGAttribute.mData = bArr;
            }
            if (z) {
                synchronized (SnapshotEffectRender.this) {
                    str = (String) SnapshotEffectRender.this.mTitleMap.get(drawJPEGAttribute.mTitle);
                    SnapshotEffectRender.this.mTitleMap.remove(drawJPEGAttribute.mTitle);
                }
                String str2 = null;
                if (SnapshotEffectRender.this.mImageSaver != null) {
                    ImageSaver access$3700 = SnapshotEffectRender.this.mImageSaver;
                    byte[] bArr2 = drawJPEGAttribute.mData;
                    boolean z3 = drawJPEGAttribute.mNeedThumbnail;
                    String str3 = str == null ? drawJPEGAttribute.mTitle : str;
                    if (str != null) {
                        str2 = drawJPEGAttribute.mTitle;
                    }
                    access$3700.addImage(bArr2, z3, str3, str2, drawJPEGAttribute.mDate, drawJPEGAttribute.mUri, drawJPEGAttribute.mLoc, drawJPEGAttribute.mWidth, drawJPEGAttribute.mHeight, drawJPEGAttribute.mExif, drawJPEGAttribute.mJpegOrientation, false, false, str == null ? drawJPEGAttribute.mFinalImage : false, false, z2, drawJPEGAttribute.mAlgorithmName, drawJPEGAttribute.mInfo, drawJPEGAttribute.mPreviewThumbnailHash, null);
                    return true;
                } else if (drawJPEGAttribute.mUri == null) {
                    Log.d(SnapshotEffectRender.TAG, "addImageForEffect");
                    Context access$1600 = SnapshotEffectRender.this.mContext;
                    if (str == null) {
                        str = drawJPEGAttribute.mTitle;
                    }
                    Storage.addImageForEffect(access$1600, str, drawJPEGAttribute.mDate, drawJPEGAttribute.mLoc, drawJPEGAttribute.mJpegOrientation, drawJPEGAttribute.mData, drawJPEGAttribute.mWidth, drawJPEGAttribute.mHeight, false, z2, drawJPEGAttribute.mAlgorithmName, drawJPEGAttribute.mInfo);
                    return true;
                } else {
                    String access$10002 = SnapshotEffectRender.TAG;
                    Log.d(access$10002, "updateImage: uri=" + drawJPEGAttribute.mUri);
                    Context access$16002 = SnapshotEffectRender.this.mContext;
                    byte[] bArr3 = drawJPEGAttribute.mData;
                    ExifInterface exifInterface = drawJPEGAttribute.mExif;
                    Uri uri = drawJPEGAttribute.mUri;
                    String str4 = str == null ? drawJPEGAttribute.mTitle : str;
                    Location location = drawJPEGAttribute.mLoc;
                    int i = drawJPEGAttribute.mJpegOrientation;
                    int i2 = drawJPEGAttribute.mWidth;
                    int i3 = drawJPEGAttribute.mHeight;
                    if (str != null) {
                        str2 = drawJPEGAttribute.mTitle;
                    }
                    Storage.updateImage(access$16002, bArr3, false, exifInterface, uri, str4, location, i, i2, i3, str2);
                    return true;
                }
            } else if (drawJPEGAttribute.mExif == null) {
                return true;
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    drawJPEGAttribute.mExif.writeExif(drawJPEGAttribute.mData, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (byteArray != null) {
                        drawJPEGAttribute.mData = byteArray;
                    }
                    byteArrayOutputStream.close();
                    return true;
                } catch (IOException e2) {
                    Log.e(SnapshotEffectRender.TAG, e2.getMessage(), e2);
                    return true;
                }
            }
        }

        public void drawBlackBridge(int i, int i2) {
            SnapshotEffectRender.this.mGLCanvas.getState().pushState();
            if (i < i2) {
                float cinematicAspectRatio = (float) ((((double) i) - (((double) i2) / Util.getCinematicAspectRatio())) / 2.0d);
                float f2 = (float) i2;
                SnapshotEffectRender.this.mGLCanvas.getBasicRender().draw(new FillRectAttribute(0.0f, 0.0f, cinematicAspectRatio, f2, ViewCompat.MEASURED_STATE_MASK));
                SnapshotEffectRender.this.mGLCanvas.getBasicRender().draw(new FillRectAttribute(((float) i) - cinematicAspectRatio, 0.0f, cinematicAspectRatio, f2, ViewCompat.MEASURED_STATE_MASK));
            } else {
                float cinematicAspectRatio2 = (float) ((((double) i2) - (((double) i) / Util.getCinematicAspectRatio())) / 2.0d);
                float f3 = (float) i;
                SnapshotEffectRender.this.mGLCanvas.getBasicRender().draw(new FillRectAttribute(0.0f, 0.0f, f3, cinematicAspectRatio2, ViewCompat.MEASURED_STATE_MASK));
                SnapshotEffectRender.this.mGLCanvas.getBasicRender().draw(new FillRectAttribute(0.0f, ((float) i2) - cinematicAspectRatio2, f3, cinematicAspectRatio2, ViewCompat.MEASURED_STATE_MASK));
            }
            SnapshotEffectRender.this.mGLCanvas.getState().popState();
        }

        public void handleMessage(Message message) {
            if (SnapshotEffectRender.this.mSaverCallback.get() != null) {
                boolean z = false;
                int i = 0;
                switch (message.what) {
                    case 0:
                        initEGL(null, false);
                        SnapshotCanvas unused = SnapshotEffectRender.this.mGLCanvas = new SnapshotCanvas();
                        GraphicBuffer unused2 = SnapshotEffectRender.this.mGraphicBuffer = new GraphicBuffer();
                        ImageSaver.ImageSaverCallback imageSaverCallback = (ImageSaver.ImageSaverCallback) SnapshotEffectRender.this.mSaverCallback.get();
                        if (imageSaverCallback != null) {
                            SnapshotEffectRender.this.mGLCanvas.setSize(imageSaverCallback.getCameraScreenNail().getWidth(), imageSaverCallback.getCameraScreenNail().getHeight());
                        }
                        SnapshotEffectRender.this.mEglThreadBlockVar.open();
                        return;
                    case 1:
                        DrawJPEGAttribute drawJPEGAttribute = (DrawJPEGAttribute) message.obj;
                        if (message.arg1 > 0) {
                            z = true;
                        }
                        drawMainJpeg(drawJPEGAttribute, true, z);
                        SnapshotEffectRender.this.mGLCanvas.recycledResources();
                        if (SnapshotEffectRender.this.mReleasePending && !hasMessages(1)) {
                            release();
                        }
                        synchronized (SnapshotEffectRender.this.mLock) {
                            SnapshotEffectRender.access$610(SnapshotEffectRender.this);
                        }
                        return;
                    case 2:
                        DrawJPEGAttribute drawJPEGAttribute2 = (DrawJPEGAttribute) message.obj;
                        Object[] objArr = message.arg1 > 0 ? 1 : null;
                        boolean z2 = message.arg2 > 0;
                        int access$700 = SnapshotEffectRender.this.mBlockWidth;
                        int access$800 = SnapshotEffectRender.this.mBlockHeight;
                        int access$900 = SnapshotEffectRender.this.calEachBlockHeight(drawJPEGAttribute2.mWidth, drawJPEGAttribute2.mHeight);
                        int i2 = drawJPEGAttribute2.mWidth;
                        if (i2 == 0 || drawJPEGAttribute2.mHeight == 0) {
                            Object[] objArr2 = new Object[3];
                            objArr2[0] = Integer.valueOf(drawJPEGAttribute2.mWidth);
                            objArr2[1] = Integer.valueOf(drawJPEGAttribute2.mHeight);
                            byte[] bArr = drawJPEGAttribute2.mData;
                            if (bArr != null) {
                                i = bArr.length;
                            }
                            objArr2[2] = Integer.valueOf(i);
                            Log.e(SnapshotEffectRender.TAG, String.format("jpeg data is broken width %d height %d length %d", objArr2));
                            SnapshotEffectRender.this.mEglThreadBlockVar.open();
                            return;
                        }
                        int unused3 = SnapshotEffectRender.this.mBlockWidth = i2;
                        int unused4 = SnapshotEffectRender.this.mBlockHeight = drawJPEGAttribute2.mHeight / access$900;
                        if (!(access$700 == SnapshotEffectRender.this.mBlockWidth && access$800 == SnapshotEffectRender.this.mBlockHeight) && SnapshotEffectRender.this.mInitGraphicBuffer) {
                            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
                            SnapshotEffectRender.this.mGraphicBuffer.reszieBuffer(SnapshotEffectRender.this.mBlockWidth, SnapshotEffectRender.this.mBlockHeight);
                            SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
                        } else {
                            SnapshotEffectRender.this.mRenderSurface.makeCurrent();
                            SnapshotEffectRender.this.mGraphicBuffer.initBuffer(SnapshotEffectRender.this.mBlockWidth, SnapshotEffectRender.this.mBlockHeight);
                            SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
                            boolean unused5 = SnapshotEffectRender.this.mInitGraphicBuffer = true;
                        }
                        if (objArr != null && !z2) {
                            drawThumbJpeg(drawJPEGAttribute2, false);
                        }
                        SnapshotEffectRender.this.mTotalCounter.reset("TOTAL");
                        drawMainJpeg(drawJPEGAttribute2, false, z2);
                        SnapshotEffectRender.this.mRenderSurface.makeCurrent();
                        SnapshotEffectRender.this.mGLCanvas.recycledResources();
                        SnapshotEffectRender.this.mGraphicBuffer.release();
                        SnapshotEffectRender.this.mRenderSurface.makeNothingCurrent();
                        SnapshotEffectRender.this.mTotalCounter.tick("TOTAL");
                        SnapshotEffectRender.this.mEglThreadBlockVar.open();
                        return;
                    case 3:
                        drawThumbJpeg((DrawJPEGAttribute) message.obj, true);
                        return;
                    case 4:
                        drawThumbJpeg((DrawJPEGAttribute) message.obj, true);
                        SnapshotEffectRender.this.mEglThreadBlockVar.open();
                        return;
                    case 5:
                        release();
                        return;
                    case 6:
                        SnapshotEffectRender.this.mGLCanvas.prepareEffectRenders(false, message.arg1);
                        return;
                    default:
                        return;
                }
            }
        }

        public void sendMessageSync(int i) {
            SnapshotEffectRender.this.mEglThreadBlockVar.close();
            sendEmptyMessage(i);
            SnapshotEffectRender.this.mEglThreadBlockVar.block();
        }
    }

    private class Size {
        public int height;
        public int width;

        public Size() {
        }

        Size(int i, int i2) {
            this.width = i;
            this.height = i2;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public SnapshotEffectRender(ImageSaver.ImageSaverCallback imageSaverCallback, boolean z) {
        Log.d(TAG, "SnapshotEffectRender: has been created!!!");
        this.mSaverCallback = new WeakReference<>(imageSaverCallback);
        this.mContext = CameraAppImpl.getAndroidContext();
        this.mIsImageCaptureIntent = z;
        if (this.mMemImage == null) {
            this.mMemImage = new MemImage();
        }
        this.mFrameCounter = new CounterUtil();
        this.mTotalCounter = new CounterUtil();
        this.mRenderCounter = new CounterUtil();
        this.mEglThread = new HandlerThread("SnapshotEffectProcessor");
        this.mEglThread.start();
        this.mSplitter = new Splitter();
        this.mBlockWidth = DEFAULT_BLOCK_WIDTH;
        this.mBlockHeight = 1500;
        this.mEglHandler = new EGLHandler(this.mEglThread.getLooper());
        this.mEglHandler.sendMessageSync(0);
        this.mRelease = false;
        this.mInitGraphicBuffer = false;
        if (CameraSettings.isSupportedDualCameraWaterMark()) {
            this.mDualCameraWaterMarkBitmap = loadCameraWatermark(this.mContext);
            this.mCurrentCustomWaterMarkText = CameraSettings.getCustomWatermark();
            this.mDualCameraWaterMarkSizeRatio = getResourceFloat(R.dimen.dualcamera_watermark_size_ratio, 0.0f);
            this.mDualCameraWaterMarkPaddingXRatio = getResourceFloat(R.dimen.dualcamera_watermark_padding_x_ratio, 0.0f);
            this.mDualCameraWaterMarkPaddingYRatio = getResourceFloat(R.dimen.dualcamera_watermark_padding_y_ratio, 0.0f);
        }
        if (DataRepository.dataItemFeature().c_0x46()) {
            this.mFrontCameraWaterMarkBitmap = loadFrontCameraWatermark(this.mContext);
            this.mFrontCameraWaterMarkSizeRatio = getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            this.mFrontCameraWaterMarkPaddingXRatio = getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            this.mFrontCameraWaterMarkPaddingYRatio = getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        }
        this.mSquareModeExtraMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.square_mode_bottom_cover_extra_margin);
    }

    static /* synthetic */ int access$610(SnapshotEffectRender snapshotEffectRender) {
        int i = snapshotEffectRender.mJpegQueueSize;
        snapshotEffectRender.mJpegQueueSize = i - 1;
        return i;
    }

    /* access modifiers changed from: private */
    public int calEachBlockHeight(int i, int i2) {
        int i3 = 1;
        while (i * i2 > 6000000) {
            i2 >>= 1;
            i3 <<= 1;
        }
        return i3;
    }

    /* access modifiers changed from: private */
    public void destroy() {
        this.mImageSaver = null;
        this.mRelease = true;
        this.mReleasePending = false;
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
        this.mEglThread.quit();
        Bitmap bitmap = this.mDualCameraWaterMarkBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mDualCameraWaterMarkBitmap.recycle();
            this.mDualCameraWaterMarkBitmap = null;
        }
        Bitmap bitmap2 = this.mFrontCameraWaterMarkBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mFrontCameraWaterMarkBitmap.recycle();
            this.mFrontCameraWaterMarkBitmap = null;
        }
        Bitmap bitmap3 = this.mUltraPixelCameraWaterMarkBitmap;
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            this.mUltraPixelCameraWaterMarkBitmap.recycle();
            this.mUltraPixelCameraWaterMarkBitmap = null;
        }
        System.gc();
        Log.d(TAG, "SnapshotEffectRender: has been released!!!");
    }

    /* access modifiers changed from: private */
    public ExifInterface getExif(DrawJPEGAttribute drawJPEGAttribute) {
        ExifInterface exifInterface = new ExifInterface();
        try {
            exifInterface.readExif(drawJPEGAttribute.mData);
            if (drawJPEGAttribute.mInfo != null) {
                exifInterface.addXiaomiComment(drawJPEGAttribute.mInfo.toString());
            }
        } catch (IOException e2) {
            Log.d(TAG, e2.getMessage());
        }
        return exifInterface;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        $closeResource(r2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
        throw r0;
     */
    public Bitmap loadCameraWatermark(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = false;
        if (!DataRepository.dataItemFeature().s_o_a_w() && !DataRepository.dataItemFeature().qf()) {
            return BitmapFactory.decodeFile(CameraSettings.getDualCameraWaterMarkFilePathVendor(), options);
        }
        File file = new File(context.getFilesDir(), Util.getDefaultWatermarkFileName());
        if (!file.exists()) {
            return Util.generateWatermark2File();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream, null, options);
            $closeResource(null, fileInputStream);
            return decodeStream;
        } catch (Exception e2) {
            Log.d(TAG, "Failed to load app camera watermark ", e2);
            return null;
        }
    }

    private Bitmap loadFrontCameraWatermark(Context context) {
        return Util.loadFrontCameraWatermark(context);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0047, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0048, code lost:
        $closeResource(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004b, code lost:
        throw r0;
     */
    public Bitmap loadUltraPixelWatermark(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = false;
        if (DataRepository.dataItemFeature().s_o_a_w() || DataRepository.dataItemFeature().qf()) {
            File file = new File(context.getFilesDir(), Util.WATERMARK_ULTRA_PIXEL_FILE_NAME);
            if (!file.exists()) {
                return Util.generateUltraPixelWatermark2File();
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream, null, options);
                $closeResource(null, fileInputStream);
                return decodeStream;
            } catch (Exception e2) {
                Log.d(TAG, "Failed to load app camera watermark ", e2);
            }
        }
        return null;
    }

    private void processorThumAsync(DrawJPEGAttribute drawJPEGAttribute) {
        if (this.mExifNeeded) {
            this.mEglHandler.obtainMessage(3, drawJPEGAttribute).sendToTarget();
        } else {
            drawJPEGAttribute.mUri = Storage.newImage(this.mContext, drawJPEGAttribute.mTitle, drawJPEGAttribute.mDate, drawJPEGAttribute.mJpegOrientation, drawJPEGAttribute.mPreviewWidth, drawJPEGAttribute.mPreviewHeight, false);
        }
    }

    private boolean processorThumSync(DrawJPEGAttribute drawJPEGAttribute) {
        if (this.mExifNeeded) {
            drawJPEGAttribute.mExif = getExif(drawJPEGAttribute);
            if (!TextUtils.isEmpty(drawJPEGAttribute.mAlgorithmName)) {
                drawJPEGAttribute.mExif.addAlgorithmComment(drawJPEGAttribute.mAlgorithmName);
            }
            if (drawJPEGAttribute.mExif.getThumbnailBytes() != null) {
                this.mEglThreadBlockVar.close();
                this.mEglHandler.obtainMessage(4, drawJPEGAttribute).sendToTarget();
                this.mEglThreadBlockVar.block();
                return true;
            }
        }
        drawJPEGAttribute.mUri = Storage.newImage(this.mContext, drawJPEGAttribute.mTitle, drawJPEGAttribute.mDate, drawJPEGAttribute.mJpegOrientation, drawJPEGAttribute.mPreviewWidth, drawJPEGAttribute.mPreviewHeight, false);
        return false;
    }

    public void changeJpegTitle(String str, String str2) {
        if (str2 != null && str2.length() != 0) {
            synchronized (this) {
                this.mTitleMap.put(str2, str);
            }
        }
    }

    public float getResourceFloat(int i, float f2) {
        TypedValue typedValue = new TypedValue();
        try {
            this.mContext.getResources().getValue(i, typedValue, true);
            return typedValue.getFloat();
        } catch (Exception unused) {
            String str = TAG;
            Log.e(str, "Missing resource " + Integer.toHexString(i));
            return f2;
        }
    }

    public boolean isRelease() {
        return this.mReleasePending || this.mRelease;
    }

    public void prepareEffectRender(int i) {
        this.mEglHandler.obtainMessage(6, i, 0).sendToTarget();
    }

    public boolean processorJpegAsync(DrawJPEGAttribute drawJPEGAttribute, boolean z) {
        boolean z2;
        Uri uri;
        Log.d(TAG, "queueSize=" + this.mJpegQueueSize);
        if (z || this.mJpegQueueSize < 7) {
            if (!z) {
                Object[] objArr = this.mJpegQueueSize == 0 ? 1 : null;
                if (objArr != null) {
                    z2 = processorThumSync(drawJPEGAttribute);
                } else {
                    processorThumAsync(drawJPEGAttribute);
                    z2 = false;
                }
                if (!this.mIsImageCaptureIntent && objArr != null && this.mExifNeeded && z2) {
                    if (!drawJPEGAttribute.mNeedThumbnail) {
                        ImageSaver.ImageSaverCallback imageSaverCallback = this.mSaverCallback.get();
                        if (imageSaverCallback != null) {
                            imageSaverCallback.getThumbnailUpdater().updatePreviewThumbnailUri(drawJPEGAttribute.mUri);
                        }
                    } else {
                        Bitmap thumbnailBitmap = drawJPEGAttribute.mExif.getThumbnailBitmap();
                        if (!(thumbnailBitmap == null || (uri = drawJPEGAttribute.mUri) == null)) {
                            drawJPEGAttribute.mFinalImage = false;
                            Thumbnail createThumbnail = Thumbnail.createThumbnail(uri, thumbnailBitmap, drawJPEGAttribute.mJpegOrientation, false);
                            ImageSaver.ImageSaverCallback imageSaverCallback2 = this.mSaverCallback.get();
                            if (imageSaverCallback2 != null) {
                                imageSaverCallback2.getThumbnailUpdater().setThumbnail(createThumbnail, true, true);
                            }
                        }
                    }
                }
            }
            synchronized (this.mLock) {
                this.mJpegQueueSize++;
            }
            this.mEglHandler.obtainMessage(1, z ? 1 : 0, 0, drawJPEGAttribute).sendToTarget();
            return true;
        }
        Log.d(TAG, "queueSize is full, drop it " + drawJPEGAttribute.mTitle);
        return false;
    }

    public void processorJpegSync(DrawJPEGAttribute drawJPEGAttribute, boolean z) {
        this.mEglThreadBlockVar.close();
        EGLHandler eGLHandler = this.mEglHandler;
        boolean z2 = this.mExifNeeded;
        eGLHandler.obtainMessage(2, z2 ? 1 : 0, z ? 1 : 0, drawJPEGAttribute).sendToTarget();
        this.mEglThreadBlockVar.block();
    }

    public void releaseIfNeeded() {
        if (this.mEglHandler.hasMessages(1)) {
            this.mReleasePending = true;
        } else {
            this.mEglHandler.sendEmptyMessage(5);
        }
    }

    public void setExifNeed(boolean z) {
        this.mExifNeeded = z;
    }

    public void setImageSaver(ImageSaver imageSaver) {
        this.mImageSaver = imageSaver;
    }

    public void setQuality(int i) {
        if (i >= 0 && i <= 97) {
            this.mQuality = i;
        }
    }
}
