package com.android.camera.effect.renders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Size;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.effect.ShaderNativeUtil;
import com.android.camera.effect.SnapshotCanvas;
import com.android.camera.effect.draw_mode.DrawBasicTexAttribute;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.effect.framework.gles.EglCore;
import com.android.camera.effect.framework.gles.PbufferSurface;
import com.android.camera.effect.framework.graphics.Block;
import com.android.camera.effect.framework.graphics.Splitter;
import com.android.camera.effect.framework.image.MemYuvImage;
import com.android.camera.effect.framework.utils.CounterUtil;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.watermark.WaterMarkBitmap;
import com.android.camera.watermark.WaterMarkData;
import com.arcsoft.supernight.SuperNightProcess;
import com.mi.config.b;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.core.FilterProcessor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Locale;

public class SnapshotRender {
    private static final boolean DUMP_TEXTURE = false;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private static final int QUEUE_LIMIT = 7;
    /* access modifiers changed from: private */
    public static final String TAG = "SnapshotRender";
    /* access modifiers changed from: private */
    public int mAdjHeight;
    /* access modifiers changed from: private */
    public int mAdjWidth;
    /* access modifiers changed from: private */
    public int mBlockHeight;
    /* access modifiers changed from: private */
    public int mBlockWidth;
    /* access modifiers changed from: private */
    public String mCurrentCustomWaterMarkText;
    /* access modifiers changed from: private */
    public DeviceWatermarkParam mDeviceWaterMarkParam;
    /* access modifiers changed from: private */
    public Bitmap mDualCameraWaterMarkBitmap;
    /* access modifiers changed from: private */
    public EglCore mEglCore;
    private EGLHandler mEglHandler;
    private HandlerThread mEglThread;
    /* access modifiers changed from: private */
    public CounterUtil mFrameCounter;
    /* access modifiers changed from: private */
    public Bitmap mFrontCameraWaterMarkBitmap;
    private volatile int mImageQueueSize;
    /* access modifiers changed from: private */
    public final Object mLock;
    /* access modifiers changed from: private */
    public MemYuvImage mMemImage;
    /* access modifiers changed from: private */
    public int mQuality;
    private boolean mRelease;
    /* access modifiers changed from: private */
    public boolean mReleasePending;
    /* access modifiers changed from: private */
    public PbufferSurface mRenderSurface;
    /* access modifiers changed from: private */
    public Splitter mSplitter;
    private int mTextureId;
    /* access modifiers changed from: private */
    public CounterUtil mTotalCounter;
    /* access modifiers changed from: private */
    public Bitmap mUltraPixelCameraWaterMarkBitmap;

    private class EGLHandler extends Handler {
        public static final int MSG_DRAW_MAIN_ASYNC = 1;
        public static final int MSG_DRAW_MAIN_SYNC = 2;
        public static final int MSG_INIT_EGL_SYNC = 0;
        public static final int MSG_PREPARE_EFFECT_RENDER = 6;
        public static final int MSG_RELEASE = 5;
        private FrameBuffer mFrameBuffer;
        private SnapshotCanvas mGLCanvas;
        private FrameBuffer mWatermarkFrameBuffer;

        public EGLHandler(Looper looper) {
            super(looper);
        }

        /* JADX DEBUG: Additional 1 move instruction added to help type inference */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r47v0, types: [com.android.camera.effect.renders.SnapshotRender$EGLHandler] */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARNING: Unknown variable types count: 1 */
        private byte[] applyEffect(DrawYuvAttribute drawYuvAttribute) {
            byte[] bArr;
            int[] iArr;
            DrawYuvAttribute drawYuvAttribute2;
            boolean z;
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            boolean z2;
            DrawYuvAttribute drawYuvAttribute3;
            byte[] bArr2;
            int[] iArr2;
            int i7;
            int i8;
            byte[] bArr3;
            int i9;
            int i10;
            boolean z3;
            int i11;
            int i12;
            boolean z4;
            boolean z5;
            Size size;
            PipeRender effectRender = getEffectRender(drawYuvAttribute);
            if (effectRender == null) {
                Log.w(SnapshotRender.TAG, "init render failed");
                return null;
            }
            int width = drawYuvAttribute.mPictureSize.getWidth();
            int height = drawYuvAttribute.mPictureSize.getHeight();
            long currentTimeMillis = System.currentTimeMillis();
            Image.Plane plane = drawYuvAttribute.mImage.getPlanes()[0];
            Image.Plane plane2 = drawYuvAttribute.mImage.getPlanes()[1];
            int rowStride = plane.getRowStride();
            int rowStride2 = plane2.getRowStride();
            Log.d(SnapshotRender.TAG, "plane0 stride =  " + plane.getRowStride() + ", plane1 stride = " + plane2.getRowStride());
            boolean isOutputSquare = drawYuvAttribute.isOutputSquare();
            if (drawYuvAttribute.mEffectIndex != FilterInfo.FILTER_ID_NONE || CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() || isOutputSquare || CameraSettings.isTiltShiftOn() || (!drawYuvAttribute.mApplyWaterMark && drawYuvAttribute.mTimeWatermark == null)) {
                iArr = null;
                bArr = null;
                drawYuvAttribute2 = drawYuvAttribute;
                z = false;
            } else {
                iArr = Util.getWatermarkRange(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight(), (drawYuvAttribute.mJpegRotation + 270) % 360, drawYuvAttribute.mApplyWaterMark, drawYuvAttribute.mTimeWatermark != null, SnapshotRender.this.mDeviceWaterMarkParam.isCinematicAspectRatio());
                byte[] yuvData = ImageUtil.getYuvData(drawYuvAttribute.mImage);
                MiYuvImage subYuvImage = Util.getSubYuvImage(yuvData, width, height, rowStride, rowStride2, iArr);
                Log.d(SnapshotRender.TAG, "get sub range data spend total tome =" + (System.currentTimeMillis() - currentTimeMillis));
                DrawYuvAttribute drawYuvAttribute4 = new DrawYuvAttribute(drawYuvAttribute.mImage, drawYuvAttribute.mPreviewSize, new Size(iArr[2], iArr[3]), drawYuvAttribute.mEffectIndex, drawYuvAttribute.mOrientation, drawYuvAttribute.mJpegRotation, drawYuvAttribute.mShootRotation, drawYuvAttribute.mDate, drawYuvAttribute.mMirror, drawYuvAttribute.mApplyWaterMark, drawYuvAttribute.mTiltShiftMode, drawYuvAttribute.mTimeWatermark, drawYuvAttribute.mAttribute, drawYuvAttribute.mWaterInfos, drawYuvAttribute.mAIWatermarkItem);
                drawYuvAttribute4.mYuvImage = subYuvImage;
                drawYuvAttribute2 = drawYuvAttribute4;
                bArr = yuvData;
                z = true;
            }
            updateRenderParameters(effectRender, drawYuvAttribute2, false);
            effectRender.setFrameBufferSize(drawYuvAttribute2.mPictureSize.getWidth(), drawYuvAttribute2.mPictureSize.getHeight());
            int i13 = z ? iArr[2] : width;
            int i14 = z ? iArr[3] : height;
            checkFrameBuffer(i13, i14);
            ((EGLHandler) this).mGLCanvas.beginBindFrameBuffer(((EGLHandler) this).mFrameBuffer);
            long currentTimeMillis2 = System.currentTimeMillis();
            GLES20.glFlush();
            effectRender.setParentFrameBufferId(((EGLHandler) this).mFrameBuffer.getId());
            effectRender.draw(drawYuvAttribute2);
            Log.d(SnapshotRender.TAG, "drawTime=" + (System.currentTimeMillis() - currentTimeMillis2));
            effectRender.deleteBuffer();
            drawYuvAttribute2.mOriginalSize = new Size(width, height);
            if (!isOutputSquare) {
                i4 = width;
                i3 = height;
                i2 = 0;
                i = 0;
            } else if (width > height) {
                i2 = (width - height) / 2;
                i4 = height;
                i3 = i4;
                i = 0;
            } else {
                i = (height - width) / 2;
                i4 = width;
                i3 = i4;
                i2 = 0;
            }
            if (drawYuvAttribute2.mApplyWaterMark) {
                if (z) {
                    z3 = true;
                    i10 = -iArr[0];
                    i9 = -iArr[1];
                } else {
                    z3 = true;
                    i10 = 0;
                    i9 = 0;
                }
                long currentTimeMillis3 = System.currentTimeMillis();
                if (!z) {
                    z4 = z;
                    ? r9 = z3;
                    iArr = Util.getWatermarkRange(i4, i3, (drawYuvAttribute2.mJpegRotation + 270) % 360, drawYuvAttribute2.mApplyWaterMark, drawYuvAttribute2.mTimeWatermark != null ? z3 : false, SnapshotRender.this.mDeviceWaterMarkParam.isCinematicAspectRatio());
                    i12 = iArr[0];
                    i11 = iArr[r9];
                    z5 = r9;
                } else {
                    z4 = z;
                    z5 = z3;
                    i12 = 0;
                    i11 = 0;
                }
                int i15 = drawYuvAttribute2.mJpegQuality;
                int i16 = (i15 <= 0 || i15 > 97) ? 97 : i15;
                i5 = height;
                z2 = z4;
                i6 = width;
                drawYuvAttribute3 = drawYuvAttribute2;
                drawAgeGenderAndMagicMirrorWater(drawYuvAttribute2.mWaterInfos, i10, i9, width, height, drawYuvAttribute2.mPreviewSize.getWidth(), drawYuvAttribute2.mPreviewSize.getHeight(), drawYuvAttribute2.mJpegRotation, false, false);
                bArr2 = ShaderNativeUtil.getPicture(i12 + i2, i11 + i, iArr[2], iArr[3], i16);
                Log.d(SnapshotRender.TAG, "for remove watermark spend more time = " + (System.currentTimeMillis() - currentTimeMillis3));
                drawWaterMark(i10 + i2, i9 + i, i4, i3, drawYuvAttribute3.mJpegRotation, drawYuvAttribute3.mTimeWatermark, false);
                Log.d(SnapshotRender.TAG, "watermarkTime=" + (System.currentTimeMillis() - currentTimeMillis3));
                ((EGLHandler) this).mGLCanvas.endBindFrameBuffer();
                if (z2) {
                    i8 = i14;
                    i7 = i13;
                    size = new Size(i7, i8);
                } else {
                    i8 = i14;
                    i7 = i13;
                    size = drawYuvAttribute3.mOriginalSize;
                }
                checkWatermarkFrameBuffer(size);
                ((EGLHandler) this).mGLCanvas.beginBindFrameBuffer(((EGLHandler) this).mWatermarkFrameBuffer);
                long currentTimeMillis4 = System.currentTimeMillis();
                RgbToYuvRender rgbToYuvRender = (RgbToYuvRender) fetchRender(FilterInfo.FILTER_ID_RGB2YUV);
                updateRenderParameters(rgbToYuvRender, drawYuvAttribute3, false);
                rgbToYuvRender.setParentFrameBufferId(((EGLHandler) this).mFrameBuffer.getId());
                rgbToYuvRender.drawTexture(((EGLHandler) this).mFrameBuffer.getTexture().getId(), 0.0f, 0.0f, (float) size.getWidth(), (float) size.getHeight(), true);
                Log.d(SnapshotRender.TAG, "rgb2YuvTime=" + (System.currentTimeMillis() - currentTimeMillis4));
                iArr2 = iArr;
            } else {
                i8 = i14;
                i7 = i13;
                i6 = width;
                i5 = height;
                z2 = z;
                drawYuvAttribute3 = drawYuvAttribute2;
                iArr2 = iArr;
                bArr2 = null;
            }
            GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
            long currentTimeMillis5 = System.currentTimeMillis();
            int width2 = z2 ? i7 : drawYuvAttribute3.mOriginalSize.getWidth();
            if (!z2) {
                i8 = drawYuvAttribute3.mOriginalSize.getHeight();
            }
            int ceil = (int) Math.ceil(((double) width2) / 2.0d);
            int ceil2 = (int) Math.ceil((((double) i8) * 3.0d) / 4.0d);
            ByteBuffer allocate = ByteBuffer.allocate(ceil * ceil2 * 4);
            GLES20.glReadPixels(0, 0, ceil, ceil2, 6408, 5121, allocate);
            allocate.rewind();
            Log.d(SnapshotRender.TAG, String.format(Locale.ENGLISH, "readSize=%dx%d imageSize=%dx%d", Integer.valueOf(ceil), Integer.valueOf(ceil2), Integer.valueOf(width2), Integer.valueOf(i8)));
            Log.d(SnapshotRender.TAG, "readTime=" + (System.currentTimeMillis() - currentTimeMillis5));
            byte[] array = allocate.array();
            if (z2) {
                long currentTimeMillis6 = System.currentTimeMillis();
                Util.coverSubYuvImage(bArr, i4, i3, rowStride, rowStride2, allocate.array(), iArr2);
                Log.d(SnapshotRender.TAG, "cover sub range data spend total time =" + (System.currentTimeMillis() - currentTimeMillis6));
                bArr3 = bArr;
            } else {
                bArr3 = array;
            }
            long currentTimeMillis7 = System.currentTimeMillis();
            ImageUtil.updateYuvImage(drawYuvAttribute3.mImage, bArr3, z2);
            Log.d(SnapshotRender.TAG, "updateImageTime=" + (System.currentTimeMillis() - currentTimeMillis7));
            ((EGLHandler) this).mGLCanvas.endBindFrameBuffer();
            ((EGLHandler) this).mGLCanvas.recycledResources();
            if (drawYuvAttribute.mApplyWaterMark) {
                int[] reverseCalculateRange = reverseCalculateRange(i6, i5, drawYuvAttribute.mOutputSize.getWidth(), drawYuvAttribute.mOutputSize.getHeight(), iArr2);
                drawYuvAttribute.mDataOfTheRegionUnderWatermarks = bArr2;
                drawYuvAttribute.mCoordinatesOfTheRegionUnderWatermarks = reverseCalculateRange;
            }
            return bArr3;
        }

        private byte[] applyEffectForAIWatermark(DrawYuvAttribute drawYuvAttribute) {
            PipeRender effectRenderForAI = getEffectRenderForAI(drawYuvAttribute);
            if (effectRenderForAI == null) {
                Log.w(SnapshotRender.TAG, "init render failed");
                return null;
            }
            int width = drawYuvAttribute.mPictureSize.getWidth();
            int height = drawYuvAttribute.mPictureSize.getHeight();
            long currentTimeMillis = System.currentTimeMillis();
            Image.Plane plane = drawYuvAttribute.mImage.getPlanes()[0];
            Image.Plane plane2 = drawYuvAttribute.mImage.getPlanes()[1];
            int rowStride = plane.getRowStride();
            int rowStride2 = plane2.getRowStride();
            int width2 = drawYuvAttribute.mPictureSize.getWidth();
            int height2 = drawYuvAttribute.mPictureSize.getHeight();
            Rect displayRect = Util.getDisplayRect();
            WatermarkItem watermarkItem = drawYuvAttribute.mAIWatermarkItem;
            if (watermarkItem == null) {
                String access$400 = SnapshotRender.TAG;
                Log.w(access$400, "watermark item is " + watermarkItem);
                return null;
            }
            CameraStatUtils.trackAIWatermarkCapture(String.valueOf(watermarkItem.getType()), watermarkItem.getKey(), String.valueOf(watermarkItem.hasMove()), String.valueOf(drawYuvAttribute.mOrientation));
            int[] location = getLocation(displayRect, watermarkItem);
            float scale = getScale(width2, height2, displayRect);
            String access$4002 = SnapshotRender.TAG;
            Log.d(access$4002, "plane0 stride =  " + plane.getRowStride() + ", plane1 stride = " + plane2.getRowStride());
            int[] aIWatermarkRange = Util.getAIWatermarkRange(drawYuvAttribute.mOrientation, drawYuvAttribute.mJpegRotation, location, scale, displayRect);
            byte[] yuvData = ImageUtil.getYuvData(drawYuvAttribute.mImage);
            MiYuvImage subYuvImage = Util.getSubYuvImage(yuvData, width, height, rowStride, rowStride2, aIWatermarkRange);
            String access$4003 = SnapshotRender.TAG;
            Log.d(access$4003, "get sub range data spend total tome =" + (System.currentTimeMillis() - currentTimeMillis));
            DrawYuvAttribute drawYuvAttribute2 = new DrawYuvAttribute(drawYuvAttribute.mImage, drawYuvAttribute.mPreviewSize, new Size(aIWatermarkRange[2], aIWatermarkRange[3]), drawYuvAttribute.mEffectIndex, drawYuvAttribute.mOrientation, drawYuvAttribute.mJpegRotation, drawYuvAttribute.mShootRotation, drawYuvAttribute.mDate, drawYuvAttribute.mMirror, drawYuvAttribute.mApplyWaterMark, drawYuvAttribute.mTiltShiftMode, drawYuvAttribute.mTimeWatermark, drawYuvAttribute.mAttribute, drawYuvAttribute.mWaterInfos, drawYuvAttribute.mAIWatermarkItem);
            drawYuvAttribute2.mYuvImage = subYuvImage;
            updateRenderParameters(effectRenderForAI, drawYuvAttribute2, false);
            effectRenderForAI.setFrameBufferSize(drawYuvAttribute2.mPictureSize.getWidth(), drawYuvAttribute2.mPictureSize.getHeight());
            int i = aIWatermarkRange[2];
            int i2 = aIWatermarkRange[3];
            checkFrameBuffer(i, i2);
            this.mGLCanvas.beginBindFrameBuffer(this.mFrameBuffer);
            long currentTimeMillis2 = System.currentTimeMillis();
            GLES20.glFlush();
            effectRenderForAI.setParentFrameBufferId(this.mFrameBuffer.getId());
            effectRenderForAI.draw(drawYuvAttribute2);
            String access$4004 = SnapshotRender.TAG;
            Log.d(access$4004, "drawTime=" + (System.currentTimeMillis() - currentTimeMillis2));
            effectRenderForAI.deleteBuffer();
            drawYuvAttribute2.mOriginalSize = new Size(width, height);
            drawAIWaterMark((-aIWatermarkRange[0]) + 0, (-aIWatermarkRange[1]) + 0, width, height, drawYuvAttribute2.mJpegRotation, Util.convertResToBitmap(watermarkItem.getResId()), aIWatermarkRange, scale, false);
            this.mGLCanvas.endBindFrameBuffer();
            Size size = new Size(i, i2);
            checkWatermarkFrameBuffer(size);
            this.mGLCanvas.beginBindFrameBuffer(this.mWatermarkFrameBuffer);
            long currentTimeMillis3 = System.currentTimeMillis();
            RgbToYuvRender rgbToYuvRender = (RgbToYuvRender) fetchRender(FilterInfo.FILTER_ID_RGB2YUV);
            updateRenderParameters(rgbToYuvRender, drawYuvAttribute2, false);
            rgbToYuvRender.setParentFrameBufferId(this.mFrameBuffer.getId());
            rgbToYuvRender.drawTexture(this.mFrameBuffer.getTexture().getId(), 0.0f, 0.0f, (float) size.getWidth(), (float) size.getHeight(), true);
            String access$4005 = SnapshotRender.TAG;
            Log.d(access$4005, "rgb2YuvTime=" + (System.currentTimeMillis() - currentTimeMillis3));
            GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
            long currentTimeMillis4 = System.currentTimeMillis();
            int ceil = (int) Math.ceil(((double) i) / 2.0d);
            int ceil2 = (int) Math.ceil((((double) i2) * 3.0d) / 4.0d);
            ByteBuffer allocate = ByteBuffer.allocate(ceil * ceil2 * 4);
            GLES20.glReadPixels(0, 0, ceil, ceil2, 6408, 5121, allocate);
            allocate.rewind();
            Log.d(SnapshotRender.TAG, String.format(Locale.ENGLISH, "readSize=%dx%d imageSize=%dx%d", Integer.valueOf(ceil), Integer.valueOf(ceil2), Integer.valueOf(i), Integer.valueOf(i2)));
            String access$4006 = SnapshotRender.TAG;
            Log.d(access$4006, "readTime=" + (System.currentTimeMillis() - currentTimeMillis4));
            allocate.array();
            long currentTimeMillis5 = System.currentTimeMillis();
            Util.coverSubYuvImage(yuvData, width, height, rowStride, rowStride2, allocate.array(), aIWatermarkRange);
            String access$4007 = SnapshotRender.TAG;
            Log.d(access$4007, "cover sub range data spend total time =" + (System.currentTimeMillis() - currentTimeMillis5));
            long currentTimeMillis6 = System.currentTimeMillis();
            ImageUtil.updateYuvImage(drawYuvAttribute2.mImage, yuvData, true);
            String access$4008 = SnapshotRender.TAG;
            Log.d(access$4008, "updateImageTime=" + (System.currentTimeMillis() - currentTimeMillis6));
            this.mGLCanvas.endBindFrameBuffer();
            this.mGLCanvas.recycledResources();
            return yuvData;
        }

        private byte[] blockSplitApplyEffect(DrawYuvAttribute drawYuvAttribute) {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            int i6;
            int[] iArr;
            RectF rectF;
            int i7;
            int i8;
            int i9;
            int i10;
            int[] iArr2;
            int i11;
            int i12;
            int i13;
            int i14;
            PipeRender pipeRender;
            List<Block> list;
            int i15;
            RectF rectF2;
            RectF rectF3;
            RectF rectF4;
            RectF rectF5;
            Block block;
            int i16;
            int i17;
            int i18;
            int i19;
            int i20;
            int i21;
            int i22;
            DrawYuvAttribute drawYuvAttribute2 = drawYuvAttribute;
            GLES20.glGetIntegerv(3379, IntBuffer.allocate(2));
            String str = "TOTAL";
            SnapshotRender.this.mTotalCounter.reset(str);
            PipeRender effectRender = getEffectRender(drawYuvAttribute);
            if (effectRender == null) {
                Log.w(SnapshotRender.TAG, "init render failed");
                return null;
            }
            int width = drawYuvAttribute2.mPictureSize.getWidth();
            int height = drawYuvAttribute2.mPictureSize.getHeight();
            Image.Plane plane = drawYuvAttribute2.mImage.getPlanes()[0];
            Image.Plane plane2 = drawYuvAttribute2.mImage.getPlanes()[1];
            int rowStride = plane.getRowStride();
            int rowStride2 = plane2.getRowStride();
            SnapshotRender.this.mMemImage.mWidth = width;
            SnapshotRender.this.mMemImage.mHeight = height;
            SnapshotRender.this.mMemImage.parseImage(drawYuvAttribute2.mImage);
            Log.d(SnapshotRender.TAG, "plane0 stride =  " + plane.getRowStride() + ", plane1 stride = " + plane2.getRowStride());
            updateRenderParameters(effectRender, drawYuvAttribute2, true);
            int i23 = rowStride2;
            List<Block> split = SnapshotRender.this.mSplitter.split(width, height, SnapshotRender.this.mBlockWidth, SnapshotRender.this.mBlockHeight, SnapshotRender.this.mAdjWidth, SnapshotRender.this.mAdjHeight);
            boolean isOutputSquare = drawYuvAttribute.isOutputSquare();
            if (isOutputSquare) {
                if (width > height) {
                    i2 = (width - height) / 2;
                    i = 0;
                    i4 = height;
                } else {
                    i = (height - width) / 2;
                    i2 = 0;
                    i4 = width;
                }
                i3 = i4;
            } else {
                i2 = 0;
                i = 0;
                i4 = height;
                i3 = width;
            }
            boolean z = drawYuvAttribute2.mApplyWaterMark;
            if (z) {
                int[] watermarkRange = Util.getWatermarkRange(i3, i4, (drawYuvAttribute2.mJpegRotation + 270) % 360, z, drawYuvAttribute2.mTimeWatermark != null, SnapshotRender.this.mDeviceWaterMarkParam.isCinematicAspectRatio());
                i5 = rowStride;
                RectF rectF6 = new RectF((float) (watermarkRange[0] + i2), (float) (watermarkRange[1] + i), (float) (watermarkRange[0] + watermarkRange[2] + i2), (float) (watermarkRange[1] + watermarkRange[3] + i));
                i6 = i4;
                ShaderNativeUtil.genWaterMarkRange(watermarkRange[0] + i2, watermarkRange[1] + i, watermarkRange[2], watermarkRange[3], 4);
                iArr = watermarkRange;
                rectF = rectF6;
            } else {
                i5 = rowStride;
                i6 = i4;
                rectF = null;
                iArr = null;
            }
            RectF rectF7 = new RectF();
            boolean z2 = drawYuvAttribute2.mEffectIndex == FilterInfo.FILTER_ID_NONE && !CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() && !isOutputSquare && !CameraSettings.isTiltShiftOn() && (drawYuvAttribute2.mApplyWaterMark || drawYuvAttribute2.mTimeWatermark != null);
            int i24 = drawYuvAttribute2.mBlockWidth;
            int i25 = drawYuvAttribute2.mBlockHeight;
            int i26 = 0;
            while (i26 < split.size()) {
                SnapshotRender.this.mFrameCounter.reset(String.format("[loop%d/%d]begin", Integer.valueOf(i26), Integer.valueOf(split.size())));
                Block block2 = split.get(i26);
                int i27 = block2.mWidth;
                int i28 = block2.mHeight;
                int[] offset = block2.getOffset(width, height);
                rectF7.left = (float) offset[0];
                rectF7.top = (float) offset[1];
                rectF7.right = (float) (offset[0] + i27);
                rectF7.bottom = (float) (offset[1] + i28);
                if (z2) {
                    block = block2;
                    i18 = i27;
                    iArr2 = iArr;
                    i17 = i5;
                    i15 = i26;
                    i10 = i25;
                    i9 = i24;
                    rectF5 = rectF7;
                    i8 = i6;
                    i16 = height;
                    i7 = i3;
                    list = split;
                    rectF4 = rectF;
                    i19 = 0;
                    if (!rectangle_collision(rectF7.left, rectF7.top, rectF7.width(), rectF7.height(), rectF.left, rectF.top, rectF.width(), rectF.height())) {
                        drawYuvAttribute2 = drawYuvAttribute;
                        i12 = width;
                        i11 = i17;
                        pipeRender = effectRender;
                        i13 = i23;
                        i14 = i16;
                        rectF3 = rectF5;
                        rectF2 = rectF4;
                        i26 = i15 + 1;
                        rectF7 = rectF3;
                        rectF = rectF2;
                        str = str;
                        split = list;
                        effectRender = pipeRender;
                        height = i14;
                        i23 = i13;
                        width = i12;
                        i5 = i11;
                        iArr = iArr2;
                        i25 = i10;
                        i24 = i9;
                        i6 = i8;
                        i3 = i7;
                    }
                } else {
                    i18 = i27;
                    i9 = i24;
                    rectF5 = rectF7;
                    block = block2;
                    i7 = i3;
                    list = split;
                    rectF4 = rectF;
                    iArr2 = iArr;
                    i8 = i6;
                    i17 = i5;
                    i15 = i26;
                    i10 = i25;
                    i16 = height;
                    i19 = 0;
                }
                if (effectRender instanceof PipeRender) {
                    effectRender.setFrameBufferSize(i18, i28);
                }
                checkFrameBuffer(i18, i28);
                effectRender.setParentFrameBufferId(this.mFrameBuffer.getId());
                this.mGLCanvas.beginBindFrameBuffer(this.mFrameBuffer.getId(), i18, i28);
                GLES20.glViewport(i19, i19, i18, i28);
                int[] yUVOffset = block.getYUVOffset(i17, i23, width, i16);
                drawYuvAttribute2 = drawYuvAttribute;
                drawYuvAttribute2.mOffsetY = yUVOffset[i19];
                drawYuvAttribute2.mOffsetUV = yUVOffset[1];
                drawYuvAttribute2.mBlockWidth = i18;
                drawYuvAttribute2.mBlockHeight = i28;
                effectRender.draw(drawYuvAttribute2);
                Object[] objArr = new Object[2];
                objArr[i19] = Integer.valueOf(i15);
                objArr[1] = Integer.valueOf(list.size());
                SnapshotRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl drawFrame", objArr));
                drawYuvAttribute2.mOriginalSize = new Size(i7, i8);
                if (drawYuvAttribute2.mApplyWaterMark) {
                    CounterUtil counterUtil = new CounterUtil();
                    counterUtil.reset("drawWaterMark");
                    int i29 = i2 - offset[i19];
                    int i30 = i - offset[1];
                    i11 = i17;
                    pipeRender = effectRender;
                    i8 = i8;
                    i7 = i7;
                    i13 = i23;
                    i14 = i16;
                    i20 = 0;
                    i21 = i18;
                    i12 = width;
                    drawAgeGenderAndMagicMirrorWater(drawYuvAttribute2.mWaterInfos, -offset[i19], -offset[1], width, i16, drawYuvAttribute2.mPreviewSize.getWidth(), drawYuvAttribute2.mPreviewSize.getHeight(), drawYuvAttribute2.mJpegRotation, false, false);
                    rectF3 = rectF5;
                    rectF2 = rectF4;
                    if (rectangle_collision(rectF3.left, rectF3.top, rectF3.width(), rectF3.height(), rectF2.left, rectF2.top, rectF2.width(), rectF2.height())) {
                        float[] intersectRect = getIntersectRect(rectF3.left, rectF3.top, rectF3.right, rectF3.bottom, rectF2.left, rectF2.top, rectF2.right, rectF2.bottom);
                        ShaderNativeUtil.mergeWaterMarkRange((int) intersectRect[0], (int) intersectRect[1], (int) (intersectRect[2] - intersectRect[0]), (int) (intersectRect[3] - intersectRect[1]), offset[0], offset[1], 4);
                        drawWaterMark(i29, i30, i7, i8, drawYuvAttribute2.mJpegRotation, drawYuvAttribute2.mTimeWatermark, false);
                    }
                    counterUtil.tick("drawWaterMark");
                    this.mGLCanvas.endBindFrameBuffer();
                    i22 = i28;
                    Size size = new Size(i21, i22);
                    checkWatermarkFrameBuffer(size);
                    this.mGLCanvas.beginBindFrameBuffer(this.mWatermarkFrameBuffer);
                    RgbToYuvRender rgbToYuvRender = (RgbToYuvRender) fetchRender(FilterInfo.FILTER_ID_RGB2YUV);
                    updateRenderParameters(rgbToYuvRender, drawYuvAttribute2, true);
                    rgbToYuvRender.setParentFrameBufferId(this.mFrameBuffer.getId());
                    rgbToYuvRender.drawTexture(this.mFrameBuffer.getTexture().getId(), 0.0f, 0.0f, (float) size.getWidth(), (float) size.getHeight(), true);
                    counterUtil.tick("drawWaterMark rgb2yuv");
                } else {
                    i8 = i8;
                    i7 = i7;
                    i13 = i23;
                    i14 = i16;
                    i20 = i19;
                    i12 = width;
                    i11 = i17;
                    i22 = i28;
                    pipeRender = effectRender;
                    rectF3 = rectF5;
                    rectF2 = rectF4;
                    i21 = i18;
                }
                GLES20.glPixelStorei(SuperNightProcess.ASVL_PAF_RAW12_RGGB_12B, 1);
                ShaderNativeUtil.mergeYUV(i21, i22, yUVOffset[i20], yUVOffset[1]);
                Object[] objArr2 = new Object[2];
                objArr2[i20] = Integer.valueOf(i15);
                objArr2[1] = Integer.valueOf(list.size());
                SnapshotRender.this.mFrameCounter.tick(String.format("[loop%d/%d]gl mergeYUV", objArr2));
                i26 = i15 + 1;
                rectF7 = rectF3;
                rectF = rectF2;
                str = str;
                split = list;
                effectRender = pipeRender;
                height = i14;
                i23 = i13;
                width = i12;
                i5 = i11;
                iArr = iArr2;
                i25 = i10;
                i24 = i9;
                i6 = i8;
                i3 = i7;
            }
            drawYuvAttribute2.mBlockWidth = i24;
            drawYuvAttribute2.mBlockHeight = i25;
            effectRender.deleteBuffer();
            this.mGLCanvas.endBindFrameBuffer();
            this.mGLCanvas.recycledResources();
            if (drawYuvAttribute2.mApplyWaterMark) {
                byte[] waterMarkRange = ShaderNativeUtil.getWaterMarkRange(SnapshotRender.this.mQuality, 4);
                int[] reverseCalculateRange = reverseCalculateRange(drawYuvAttribute2.mPictureSize.getWidth(), drawYuvAttribute2.mPictureSize.getHeight(), drawYuvAttribute2.mOutputSize.getWidth(), drawYuvAttribute2.mOutputSize.getHeight(), iArr);
                drawYuvAttribute2.mDataOfTheRegionUnderWatermarks = waterMarkRange;
                drawYuvAttribute2.mCoordinatesOfTheRegionUnderWatermarks = reverseCalculateRange;
            }
            SnapshotRender.this.mTotalCounter.tick(str);
            return null;
        }

        private void checkFrameBuffer(int i, int i2) {
            FrameBuffer frameBuffer = this.mFrameBuffer;
            if (frameBuffer == null || frameBuffer.getWidth() != i || this.mFrameBuffer.getHeight() != i2) {
                this.mFrameBuffer = new FrameBuffer(this.mGLCanvas, i, i2, 0);
            }
        }

        private void checkWatermarkFrameBuffer(Size size) {
            FrameBuffer frameBuffer = this.mWatermarkFrameBuffer;
            if (frameBuffer == null || frameBuffer.getWidth() < size.getWidth() || this.mWatermarkFrameBuffer.getHeight() < size.getHeight()) {
                this.mWatermarkFrameBuffer = new FrameBuffer(this.mGLCanvas, size.getWidth(), size.getHeight(), 0);
            }
        }

        private byte[] compress(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return byteArray;
        }

        private void drawAgeGenderAndMagicMirrorWater(List<WaterMarkData> list, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, boolean z2) {
            if (b.al() && !z && CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()) {
                WaterMarkBitmap waterMarkBitmap = new WaterMarkBitmap(list);
                WaterMarkData waterMarkData = waterMarkBitmap.getWaterMarkData();
                if (waterMarkData != null) {
                    drawWaterMark(new AgeGenderAndMagicMirrorWaterMark(waterMarkData.getImage(), i3, i4, i7, i5, i6, 0.0f, 0.0f), i, i2, i7 - waterMarkData.getOrientation(), z2);
                }
                waterMarkBitmap.releaseBitmap();
                Log.d(WaterMarkBitmap.class.getSimpleName(), "Draw age_gender_and_magic_mirror water mark");
            }
        }

        private boolean drawImage(DrawYuvAttribute drawYuvAttribute, boolean z) {
            if (drawYuvAttribute.mAIWatermarkItem != null) {
                drawYuvAttribute.mApplyWaterMark = false;
                drawYuvAttribute.mTimeWatermark = null;
            }
            byte[] applyEffect = (CameraSettings.isTiltShiftOn() || (drawYuvAttribute.mEffectIndex == FilterInfo.FILTER_ID_NONE && !CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() && !CameraSettings.isTiltShiftOn() && !z && (drawYuvAttribute.mApplyWaterMark || drawYuvAttribute.mTimeWatermark != null))) ? applyEffect(drawYuvAttribute) : blockSplitApplyEffect(drawYuvAttribute);
            if (drawYuvAttribute.mAIWatermarkItem != null) {
                applyEffect = applyEffectForAIWatermark(drawYuvAttribute);
            }
            String access$400 = SnapshotRender.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("mainLen=");
            sb.append(applyEffect == null ? "null" : Integer.valueOf(applyEffect.length));
            Log.d(access$400, sb.toString());
            if (!SnapshotRender.this.mDeviceWaterMarkParam.isCinematicAspectRatio()) {
                return true;
            }
            Util.drawMiMovieBlackBridge(drawYuvAttribute.mImage);
            return true;
        }

        private void drawWaterMark(WaterMark waterMark, int i, int i2, int i3, boolean z) {
            this.mGLCanvas.getState().pushState();
            if (z) {
                int i4 = (i3 + 360) % 360;
                int i5 = waterMark.mPictureWidth;
                if (i4 == 90 || i4 == 270) {
                    int i6 = waterMark.mPictureHeight;
                    this.mGLCanvas.getState().translate(0.0f, (float) ((i6 / 2) + i2));
                    this.mGLCanvas.getState().rotate(180.0f, 1.0f, 0.0f, 0.0f);
                    this.mGLCanvas.getState().translate(0.0f, (float) (((-i6) / 2) - i2));
                } else {
                    this.mGLCanvas.getState().translate((float) (i5 / 2), 0.0f);
                    this.mGLCanvas.getState().rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    this.mGLCanvas.getState().translate((float) ((-i5) / 2), 0.0f);
                }
            }
            if (i3 != 0) {
                this.mGLCanvas.getState().translate((float) (waterMark.getCenterX() + i), (float) (waterMark.getCenterY() + i2));
                this.mGLCanvas.getState().rotate((float) (-i3), 0.0f, 0.0f, 1.0f);
                this.mGLCanvas.getState().translate((float) ((-i) - waterMark.getCenterX()), (float) ((-i2) - waterMark.getCenterY()));
            }
            this.mGLCanvas.getBasicRender().draw(new DrawBasicTexAttribute(waterMark.getTexture(), i + waterMark.getLeft(), i2 + waterMark.getTop(), waterMark.getWidth(), waterMark.getHeight()));
            this.mGLCanvas.getState().popState();
        }

        private Render fetchRender(int i) {
            RenderGroup effectRenderGroup = this.mGLCanvas.getEffectRenderGroup();
            Render render = effectRenderGroup.getRender(i);
            if (render != null) {
                return render;
            }
            this.mGLCanvas.prepareEffectRenders(false, i);
            return effectRenderGroup.getRender(i);
        }

        private PipeRender getEffectRender(DrawYuvAttribute drawYuvAttribute) {
            Render fetchRender;
            PipeRender pipeRender = new PipeRender(this.mGLCanvas);
            pipeRender.addRender(fetchRender(FilterInfo.FILTER_ID_YUV2RGB));
            int i = drawYuvAttribute.mEffectIndex;
            if (!(i == FilterInfo.FILTER_ID_NONE || (fetchRender = fetchRender(i)) == null)) {
                pipeRender.addRender(fetchRender);
            }
            String str = drawYuvAttribute.mTiltShiftMode;
            if (str != null) {
                Render render = null;
                if (ComponentRunningTiltValue.TILT_CIRCLE.equals(str)) {
                    render = fetchRender(FilterInfo.FILTER_ID_GAUSSIAN);
                } else if (ComponentRunningTiltValue.TILT_PARALLEL.equals(drawYuvAttribute.mTiltShiftMode)) {
                    render = fetchRender(FilterInfo.FILTER_ID_TILTSHIFT);
                }
                if (render != null) {
                    pipeRender.addRender(render);
                }
            }
            if (!drawYuvAttribute.mApplyWaterMark) {
                pipeRender.addRender(fetchRender(FilterInfo.FILTER_ID_RGB2YUV));
            }
            return pipeRender;
        }

        private PipeRender getEffectRenderForAI(DrawYuvAttribute drawYuvAttribute) {
            PipeRender pipeRender = new PipeRender(this.mGLCanvas);
            pipeRender.addRender(fetchRender(FilterInfo.FILTER_ID_YUV2RGB));
            return pipeRender;
        }

        private Bitmap getGPURBGA(int i, int i2, int i3, int i4) {
            int i5 = i3 * i4;
            int[] iArr = new int[i5];
            int[] iArr2 = new int[i5];
            IntBuffer wrap = IntBuffer.wrap(iArr);
            wrap.position(0);
            GLES20.glReadPixels(i, i2, i3, i4, 6408, 5121, wrap);
            int i6 = 0;
            int i7 = 0;
            while (i6 < i4) {
                int i8 = i7;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = iArr[i8];
                    iArr2[i8] = (i10 & -16711936) | ((i10 << 16) & 16711680) | ((i10 >> 16) & 255);
                    i8++;
                }
                i6++;
                i7 = i8;
            }
            return Bitmap.createBitmap(iArr2, i3, i4, Bitmap.Config.ARGB_8888);
        }

        private Bitmap getGPUYYY(int i, int i2, int i3, int i4) {
            int i5 = i3 >> 1;
            byte[] bArr = new byte[(i5 * i5 * 4)];
            int i6 = i3 * i4;
            int[] iArr = new int[i6];
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.position(0);
            GLES20.glReadPixels(i, i2, i5, i4 >> 1, 6408, 5121, wrap);
            int i7 = 0;
            for (int i8 = 0; i8 < i6; i8++) {
                byte b2 = bArr[i8];
                iArr[i7] = (b2 & 255) | ((b2 << 8) & 65280) | -16777216 | ((b2 << 16) & 16711680);
                i7++;
            }
            return Bitmap.createBitmap(iArr, i3, i4, Bitmap.Config.ARGB_8888);
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

        private int[] getLocation(Rect rect, WatermarkItem watermarkItem) {
            int[] coordinate = watermarkItem.getCoordinate();
            int i = coordinate[0];
            int i2 = rect.left;
            int i3 = coordinate[1];
            int i4 = rect.top;
            return new int[]{i - i2, i3 - i4, coordinate[2] - i2, coordinate[3] - i4};
        }

        private float getScale(int i, int i2, Rect rect) {
            int abs = Math.abs(rect.bottom - rect.top);
            int abs2 = Math.abs(rect.right - rect.left);
            int i3 = abs > abs2 ? abs : abs2;
            if (abs > abs2) {
                abs = abs2;
            }
            int i4 = i > i2 ? i : i2;
            if (i > i2) {
                i = i2;
            }
            return Math.min(((float) i4) / ((float) i3), ((float) i) / ((float) abs));
        }

        private void initEGL(EGLContext eGLContext, boolean z) {
            if (SnapshotRender.this.mEglCore == null) {
                EglCore unused = SnapshotRender.this.mEglCore = new EglCore(eGLContext, 2);
            }
            if (z && SnapshotRender.this.mRenderSurface != null) {
                SnapshotRender.this.mRenderSurface.release();
                PbufferSurface unused2 = SnapshotRender.this.mRenderSurface = null;
            }
            SnapshotRender snapshotRender = SnapshotRender.this;
            PbufferSurface unused3 = snapshotRender.mRenderSurface = new PbufferSurface(snapshotRender.mEglCore, 1, 1);
            SnapshotRender.this.mRenderSurface.makeCurrent();
        }

        private boolean rectangle_collision(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            return f2 <= f8 + f6 && f2 + f4 >= f6 && f3 <= f9 + f7 && f3 + f5 >= f7;
        }

        private void release() {
            FrameBuffer frameBuffer = this.mFrameBuffer;
            if (frameBuffer != null) {
                frameBuffer.release();
                this.mFrameBuffer = null;
            }
            FrameBuffer frameBuffer2 = this.mWatermarkFrameBuffer;
            if (frameBuffer2 != null) {
                frameBuffer2.release();
                this.mWatermarkFrameBuffer = null;
            }
            if (SnapshotRender.this.mRenderSurface != null) {
                SnapshotRender.this.mRenderSurface.makeCurrent();
                this.mGLCanvas.recycledResources();
                SnapshotRender.this.mRenderSurface.makeNothingCurrent();
            }
            this.mGLCanvas = null;
            SnapshotRender.this.destroy();
        }

        private int[] reverseCalculateRange(int i, int i2, int i3, int i4, int[] iArr) {
            float f2 = ((float) i4) / ((float) i2);
            if (((float) i3) / ((float) i) == f2 || i3 == i4) {
                return new int[]{(int) (((float) iArr[0]) * f2), (int) (((float) iArr[1]) * f2), (int) (((float) iArr[2]) * f2), (int) (((float) iArr[3]) * f2)};
            }
            String access$400 = SnapshotRender.TAG;
            Log.e(access$400, "orgin w:" + i3 + " origin h:" + i4 + " image w:" + i + " image h:" + i2 + " in different ratio");
            return null;
        }

        private void updateRenderParameters(Render render, DrawYuvAttribute drawYuvAttribute, boolean z) {
            if (z) {
                render.setViewportSize(SnapshotRender.this.mBlockWidth, SnapshotRender.this.mBlockHeight);
            } else {
                render.setViewportSize(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
            }
            render.setPreviewSize(drawYuvAttribute.mPreviewSize.getWidth(), drawYuvAttribute.mPreviewSize.getHeight());
            render.setEffectRangeAttribute(drawYuvAttribute.mAttribute);
            render.setMirror(drawYuvAttribute.mMirror);
            render.setSnapshotSize(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
            render.setOrientation(drawYuvAttribute.mOrientation);
            render.setShootRotation(drawYuvAttribute.mShootRotation);
            render.setJpegOrientation(drawYuvAttribute.mJpegRotation);
        }

        public void drawAIWaterMark(int i, int i2, int i3, int i4, int i5, Bitmap bitmap, int[] iArr, float f2, boolean z) {
            if (bitmap != null) {
                drawWaterMark(new AIImageWaterMark(bitmap, i3, i4, i5, iArr, f2), i, i2, i5, z);
            }
        }

        public void drawWaterMark(int i, int i2, int i3, int i4, int i5, String str, boolean z) {
            if (str != null) {
                drawWaterMark(new NewStyleTextWaterMark(str, i3, i4, i5, SnapshotRender.this.mDeviceWaterMarkParam.isCinematicAspectRatio()), i, i2, i5, z);
            }
            Bitmap bitmap = null;
            if (SnapshotRender.this.mFrontCameraWaterMarkBitmap != null && SnapshotRender.this.mDeviceWaterMarkParam.isFrontWatermarkEnable()) {
                bitmap = SnapshotRender.this.mFrontCameraWaterMarkBitmap;
            } else if (SnapshotRender.this.mDualCameraWaterMarkBitmap != null && SnapshotRender.this.mDeviceWaterMarkParam.isDualWatermarkEnable()) {
                if (SnapshotRender.this.mCurrentCustomWaterMarkText != null && !SnapshotRender.this.mCurrentCustomWaterMarkText.equals(CameraSettings.getCustomWatermark())) {
                    String unused = SnapshotRender.this.mCurrentCustomWaterMarkText = CameraSettings.getCustomWatermark();
                    SnapshotRender snapshotRender = SnapshotRender.this;
                    Bitmap unused2 = snapshotRender.mDualCameraWaterMarkBitmap = snapshotRender.loadCameraWatermark(CameraAppImpl.getAndroidContext());
                }
                bitmap = SnapshotRender.this.mDualCameraWaterMarkBitmap;
                boolean equals = CameraSettings.getCustomWatermark().equals(CameraSettings.getDefaultWatermarkStr());
                if (SnapshotRender.this.mDeviceWaterMarkParam.isUltraWatermarkEnable() && equals) {
                    if (SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap == null) {
                        SnapshotRender snapshotRender2 = SnapshotRender.this;
                        Bitmap unused3 = snapshotRender2.mUltraPixelCameraWaterMarkBitmap = snapshotRender2.loadUltraWatermark(CameraAppImpl.getAndroidContext());
                    }
                    if (SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap != null) {
                        bitmap = SnapshotRender.this.mUltraPixelCameraWaterMarkBitmap;
                    }
                }
            }
            if (bitmap != null) {
                drawWaterMark(new ImageWaterMark(bitmap, i3, i4, i5, SnapshotRender.this.mDeviceWaterMarkParam.getSize(), SnapshotRender.this.mDeviceWaterMarkParam.getPaddingX(), SnapshotRender.this.mDeviceWaterMarkParam.getPaddingY(), SnapshotRender.this.mDeviceWaterMarkParam.isCinematicAspectRatio()), i, i2, i5, z);
            }
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                initEGL(null, false);
                this.mGLCanvas = new SnapshotCanvas();
            } else if (i == 1) {
                drawImage((DrawYuvAttribute) message.obj, false);
                this.mGLCanvas.recycledResources();
                if (SnapshotRender.this.mReleasePending && !hasMessages(1)) {
                    release();
                }
                synchronized (SnapshotRender.this.mLock) {
                    SnapshotRender.access$310(SnapshotRender.this);
                }
            } else if (i == 2) {
                FilterProcessor.YuvAttributeWrapper yuvAttributeWrapper = (FilterProcessor.YuvAttributeWrapper) message.obj;
                DrawYuvAttribute drawYuvAttribute = yuvAttributeWrapper.mAttribute;
                ConditionVariable conditionVariable = yuvAttributeWrapper.mBlocker;
                if (drawYuvAttribute.mPictureSize.getWidth() == 0 || drawYuvAttribute.mPictureSize.getHeight() == 0) {
                    Log.e(SnapshotRender.TAG, String.format("yuv image is broken width %d height %d", Integer.valueOf(drawYuvAttribute.mPictureSize.getWidth()), Integer.valueOf(drawYuvAttribute.mPictureSize.getHeight())));
                    if (conditionVariable != null) {
                        conditionVariable.open();
                        return;
                    }
                    return;
                }
                this.mGLCanvas.setSize(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
                int access$500 = SnapshotRender.this.calEachBlockHeight(drawYuvAttribute.mPictureSize.getWidth(), drawYuvAttribute.mPictureSize.getHeight());
                int unused = SnapshotRender.this.mBlockWidth = drawYuvAttribute.mPictureSize.getWidth();
                IntBuffer allocate = IntBuffer.allocate(2);
                GLES20.glGetIntegerv(3379, allocate);
                boolean z = false;
                while (SnapshotRender.this.mBlockWidth > allocate.get(0)) {
                    SnapshotRender snapshotRender = SnapshotRender.this;
                    int unused2 = snapshotRender.mBlockWidth = snapshotRender.mBlockWidth / 2;
                    access$500 /= 2;
                    if ((drawYuvAttribute.mJpegRotation + 360) % 180 == 0) {
                        z = true;
                    }
                }
                int unused3 = SnapshotRender.this.mBlockHeight = drawYuvAttribute.mPictureSize.getHeight() / access$500;
                SnapshotRender snapshotRender2 = SnapshotRender.this;
                int unused4 = snapshotRender2.mAdjWidth = snapshotRender2.mBlockWidth;
                SnapshotRender snapshotRender3 = SnapshotRender.this;
                int unused5 = snapshotRender3.mAdjHeight = snapshotRender3.mBlockHeight;
                if (SnapshotRender.this.mBlockHeight % 4 != 0) {
                    SnapshotRender snapshotRender4 = SnapshotRender.this;
                    SnapshotRender.access$712(snapshotRender4, 4 - (snapshotRender4.mBlockHeight % 4));
                }
                drawImage(drawYuvAttribute, z);
                this.mGLCanvas.recycledResources();
                if (conditionVariable != null) {
                    conditionVariable.open();
                }
            } else if (i == 5) {
                release();
            } else if (i == 6) {
                this.mGLCanvas.prepareEffectRenders(false, message.arg1);
            }
        }
    }

    private static class RenderHolder {
        /* access modifiers changed from: private */
        public static SnapshotRender render = new SnapshotRender();

        private RenderHolder() {
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

    private SnapshotRender() {
        this.mQuality = 97;
        this.mImageQueueSize = 0;
        this.mLock = new Object();
        String str = TAG;
        Log.d(str, "SnapshotRender created " + this);
        this.mEglThread = new HandlerThread(TAG);
        this.mEglThread.start();
        this.mEglHandler = new EGLHandler(this.mEglThread.getLooper());
        if (this.mMemImage == null) {
            this.mMemImage = new MemYuvImage();
        }
        this.mFrameCounter = new CounterUtil();
        this.mTotalCounter = new CounterUtil();
        this.mSplitter = new Splitter();
        this.mEglHandler.sendEmptyMessage(0);
        this.mRelease = false;
    }

    static /* synthetic */ int access$310(SnapshotRender snapshotRender) {
        int i = snapshotRender.mImageQueueSize;
        snapshotRender.mImageQueueSize = i - 1;
        return i;
    }

    static /* synthetic */ int access$712(SnapshotRender snapshotRender, int i) {
        int i2 = snapshotRender.mBlockHeight + i;
        snapshotRender.mBlockHeight = i2;
        return i2;
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
        String str = TAG;
        Log.d(str, "SnapshotRender released " + this);
    }

    public static SnapshotRender getRender() {
        return RenderHolder.render;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0051, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0052, code lost:
        $closeResource(r2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        throw r0;
     */
    public Bitmap loadCameraWatermark(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = false;
        if (!DataRepository.dataItemFeature().s_o_a_w() && !DataRepository.dataItemFeature().c_0x4a()) {
            return BitmapFactory.decodeFile(CameraSettings.getDualCameraWaterMarkFilePathVendor(), options);
        }
        File file = new File(context.getFilesDir(), Util.getDefaultWatermarkFileName());
        if (!file.exists()) {
            Util.generateWatermark2File();
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
    public Bitmap loadUltraWatermark(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPurgeable = true;
        options.inPremultiplied = false;
        if (DataRepository.dataItemFeature().s_o_a_w() || DataRepository.dataItemFeature().c_0x4a()) {
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

    public boolean isRelease() {
        return this.mReleasePending || this.mRelease;
    }

    public void prepareEffectRender(DeviceWatermarkParam deviceWatermarkParam, int i) {
        this.mDeviceWaterMarkParam = deviceWatermarkParam;
        if (deviceWatermarkParam.isDualWatermarkEnable() && this.mDualCameraWaterMarkBitmap == null) {
            this.mDualCameraWaterMarkBitmap = loadCameraWatermark(CameraAppImpl.getAndroidContext());
            this.mCurrentCustomWaterMarkText = CameraSettings.getCustomWatermark();
        } else if (deviceWatermarkParam.isFrontWatermarkEnable() && this.mFrontCameraWaterMarkBitmap == null) {
            this.mFrontCameraWaterMarkBitmap = Util.loadFrontCameraWatermark(CameraAppImpl.getAndroidContext());
        }
        if (i != FilterInfo.FILTER_ID_NONE) {
            this.mEglHandler.obtainMessage(6, i, 0).sendToTarget();
        }
    }

    public boolean processImageAsync(DrawYuvAttribute drawYuvAttribute) {
        Log.d(TAG, "queueSize=" + this.mImageQueueSize);
        if (this.mImageQueueSize >= 7) {
            Log.d(TAG, "queueSize is full");
            return false;
        }
        synchronized (this.mLock) {
            this.mImageQueueSize++;
        }
        this.mEglHandler.obtainMessage(1, drawYuvAttribute).sendToTarget();
        return true;
    }

    public void processImageSync(FilterProcessor.YuvAttributeWrapper yuvAttributeWrapper) {
        this.mEglHandler.obtainMessage(2, yuvAttributeWrapper).sendToTarget();
    }

    public void release() {
        if (this.mEglHandler.hasMessages(1)) {
            Log.d(TAG, "release: try to release but message is not null, so pending it");
            this.mReleasePending = true;
            return;
        }
        this.mEglHandler.sendEmptyMessage(5);
    }
}
