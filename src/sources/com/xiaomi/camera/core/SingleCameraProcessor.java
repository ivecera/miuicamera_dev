package com.xiaomi.camera.core;

import android.hardware.camera2.params.OutputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.ImageProcessor;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.FrameData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.List;

public class SingleCameraProcessor extends ImageProcessor {
    /* access modifiers changed from: private */
    public static final String TAG = "SingleCameraProcessor";

    SingleCameraProcessor(ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback, boolean z, BufferFormat bufferFormat) {
        super(imageProcessorStatusCallback, z, bufferFormat);
    }

    private void processCaptureResult(ICustomCaptureResult iCustomCaptureResult, Image image) {
        String str = TAG;
        Log.d(str, "processCaptureResult: image = " + image);
        String str2 = TAG;
        Log.d(str2, "processCaptureResult: image = " + image.getTimestamp());
        FrameData frameData = new FrameData(0, iCustomCaptureResult.getSequenceId(), iCustomCaptureResult.getFrameNumber(), iCustomCaptureResult.getResults(), iCustomCaptureResult.getParcelRequest(), image);
        frameData.setFrameCallback(new FrameData.FrameStatusCallback() {
            /* class com.xiaomi.camera.core.SingleCameraProcessor.AnonymousClass4 */

            @Override // com.xiaomi.engine.FrameData.FrameStatusCallback
            public void onFrameImageClosed(Image image) {
                String access$000 = SingleCameraProcessor.TAG;
                Log.d(access$000, "onFrameImageClosed: " + image);
                ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = ((ImageProcessor) SingleCameraProcessor.this).mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback != null) {
                    imageProcessorStatusCallback.onOriginalImageClosed(image);
                }
                ImagePool.getInstance().releaseImage(image);
            }
        });
        ((ImageProcessor) this).mTaskSession.processFrame(frameData, new TaskSession.FrameCallback() {
            /* class com.xiaomi.camera.core.SingleCameraProcessor.AnonymousClass5 */

            @Override // com.xiaomi.engine.TaskSession.FrameCallback
            public void onFrameProcessed(int i, String str, Object obj) {
                String access$000 = SingleCameraProcessor.TAG;
                Log.d(access$000, "onFrameProcessed: [" + i + "]:{" + str + "}");
            }
        });
    }

    @Override // com.xiaomi.camera.core.ImageProcessor
    public List<OutputConfiguration> configOutputConfigurations(BufferFormat bufferFormat) {
        ArrayList arrayList = new ArrayList();
        ((ImageProcessor) this).mEffectImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight(), bufferFormat.getBufferFormat(), getImageBufferQueueSize());
        ((ImageProcessor) this).mEffectImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            /* class com.xiaomi.camera.core.SingleCameraProcessor.AnonymousClass1 */

            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                PerformanceTracker.trackAlgorithmProcess("[  EFFECT]", 1);
                long timestamp = acquireNextImage.getTimestamp();
                String access$000 = SingleCameraProcessor.TAG;
                Log.d(access$000, "onImageAvailable: effectImage received: " + timestamp);
                Image queueImageToPool = SingleCameraProcessor.this.queueImageToPool(ImagePool.getInstance(), acquireNextImage);
                acquireNextImage.close();
                SingleCameraProcessor.this.dispatchFilterTask(new ImageProcessor.FilterTaskData(queueImageToPool, 0, true));
                SingleCameraProcessor.this.onProcessImageDone();
            }
        }, getImageReaderHandler());
        arrayList.add(new OutputConfiguration(0, ((ImageProcessor) this).mEffectImageReaderHolder.getSurface()));
        if (((ImageProcessor) this).mIsBokehMode) {
            ((ImageProcessor) this).mRawImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight(), bufferFormat.getBufferFormat(), getImageBufferQueueSize());
            ((ImageProcessor) this).mRawImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                /* class com.xiaomi.camera.core.SingleCameraProcessor.AnonymousClass2 */

                public void onImageAvailable(ImageReader imageReader) {
                    Image acquireNextImage = imageReader.acquireNextImage();
                    long timestamp = acquireNextImage.getTimestamp();
                    PerformanceTracker.trackAlgorithmProcess("[     RAW]", 1);
                    String access$000 = SingleCameraProcessor.TAG;
                    Log.d(access$000, "onImageAvailable: rawImage received: " + timestamp);
                    Image queueImageToPool = SingleCameraProcessor.this.queueImageToPool(ImagePool.getInstance(), acquireNextImage);
                    acquireNextImage.close();
                    SingleCameraProcessor.this.dispatchFilterTask(new ImageProcessor.FilterTaskData(queueImageToPool, 1, true));
                }
            }, getImageReaderHandler());
            arrayList.add(new OutputConfiguration(1, ((ImageProcessor) this).mRawImageReaderHolder.getSurface()));
            ((ImageProcessor) this).mDepthImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth() / 2, bufferFormat.getBufferHeight() / 2, 540422489, getImageBufferQueueSize());
            ((ImageProcessor) this).mDepthImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                /* class com.xiaomi.camera.core.SingleCameraProcessor.AnonymousClass3 */

                public void onImageAvailable(ImageReader imageReader) {
                    Image acquireNextImage = imageReader.acquireNextImage();
                    PerformanceTracker.trackAlgorithmProcess("[   DEPTH]", 1);
                    String access$000 = SingleCameraProcessor.TAG;
                    Log.d(access$000, "onImageAvailable: depthImage received: " + acquireNextImage.getTimestamp());
                    ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = ((ImageProcessor) SingleCameraProcessor.this).mImageProcessorStatusCallback;
                    if (imageProcessorStatusCallback != null) {
                        imageProcessorStatusCallback.onImageProcessed(acquireNextImage, 2, false);
                    }
                    acquireNextImage.close();
                    ((ImageProcessor) SingleCameraProcessor.this).mNeedProcessDepthImageSize.getAndDecrement();
                    SingleCameraProcessor.this.tryToStopWork();
                }
            }, getImageReaderHandler());
            arrayList.add(new OutputConfiguration(2, ((ImageProcessor) this).mDepthImageReaderHolder.getSurface()));
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    @Override // com.xiaomi.camera.core.ImageProcessor
    public boolean isIdle() {
        String str = TAG;
        Log.d(str, "isIdle: " + ((ImageProcessor) this).mNeedProcessNormalImageSize.get() + " processing: " + ((ImageProcessor) this).mImageProcessorStatusCallback.isAnyFrontProcessing(this));
        return ((ImageProcessor) this).mIsBokehMode ? ((ImageProcessor) this).mNeedProcessNormalImageSize.get() == 0 && ((ImageProcessor) this).mNeedProcessRawImageSize.get() == 0 && ((ImageProcessor) this).mNeedProcessDepthImageSize.get() == 0 : ((ImageProcessor) this).mNeedProcessNormalImageSize.get() == 0 && !((ImageProcessor) this).mImageProcessorStatusCallback.isAnyFrontProcessing(this);
    }

    /* access modifiers changed from: package-private */
    @Override // com.xiaomi.camera.core.ImageProcessor
    public void processImage(List<CaptureData.CaptureDataBean> list) {
        if (list == null || list.size() == 0) {
            Log.w(TAG, "processImage: dataBeans is empty!");
            return;
        }
        onProcessImageStart();
        for (CaptureData.CaptureDataBean captureDataBean : list) {
            ICustomCaptureResult result = captureDataBean.getResult();
            Image mainImage = captureDataBean.getMainImage();
            try {
                mainImage.getHardwareBuffer();
                PerformanceTracker.trackAlgorithmProcess("[ORIGINAL]", 0);
                processCaptureResult(result, mainImage);
                Image subImage = captureDataBean.getSubImage();
                if (subImage != null) {
                    subImage.close();
                    ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = ((ImageProcessor) this).mImageProcessorStatusCallback;
                    if (imageProcessorStatusCallback != null) {
                        imageProcessorStatusCallback.onOriginalImageClosed(subImage);
                    }
                    ImagePool.getInstance().releaseImage(subImage);
                }
            } catch (IllegalArgumentException | IllegalStateException e2) {
                Log.e(TAG, e2.getMessage(), e2);
            }
        }
    }
}
