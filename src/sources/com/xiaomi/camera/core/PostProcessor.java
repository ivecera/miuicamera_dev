package com.xiaomi.camera.core;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.storage.ImageSaver;
import com.mi.config.b;
import com.xiaomi.camera.base.CommonUtil;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.ImageProcessor;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.ReprocessorFactory;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.MiCameraAlgo;
import com.xiaomi.engine.ResultData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import com.xiaomi.protocol.IImageReaderParameterSets;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import d.d.a;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PostProcessor {
    private static final int MSG_RESULT_FLAW = 0;
    /* access modifiers changed from: private */
    public static final String TAG = "PostProcessor";
    /* access modifiers changed from: private */
    public CaptureDataListener mCaptureDataListener = new CaptureDataListener() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass3 */

        @Override // com.xiaomi.camera.core.CaptureDataListener
        public void onCaptureDataAvailable(@NonNull CaptureData captureData) {
            long captureTimestamp = captureData.getCaptureTimestamp();
            int algoType = captureData.getAlgoType();
            if (2 == algoType || ((3 == algoType && PostProcessor.this.isSRRequireReprocess()) || 5 == algoType)) {
                CaptureData.CaptureDataBean multiFrameProcessResult = captureData.getMultiFrameProcessResult();
                if (multiFrameProcessResult != null) {
                    ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureTimestamp));
                    long timeStamp = multiFrameProcessResult.getResult().getTimeStamp();
                    Log.d(PostProcessor.TAG, "[1] onCaptureDataAvailable: timestamp: " + captureTimestamp + " | " + timeStamp);
                    if (timeStamp != captureTimestamp) {
                        parallelTaskData.setTimestamp(timeStamp);
                        PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureTimestamp));
                        PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timeStamp), parallelTaskData);
                    }
                    if (5 == algoType && parallelTaskData.getDataParameter().isSaveGroupshotPrimitive()) {
                        for (int i = 0; i < captureData.getCaptureDataBeanList().size(); i++) {
                            ParallelTaskData cloneTaskData = parallelTaskData.cloneTaskData(i);
                            long timeStamp2 = captureData.getCaptureDataBeanList().get(i).getResult().getTimeStamp();
                            if (timeStamp2 == timeStamp) {
                                timeStamp2++;
                            }
                            cloneTaskData.setTimestamp(timeStamp2);
                            PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timeStamp2), cloneTaskData);
                            Log.d(PostProcessor.TAG, "[1] onCaptureDataAvailable: add " + timeStamp2);
                        }
                    }
                    captureData.getCaptureDataBeanList().add(multiFrameProcessResult);
                } else {
                    throw new RuntimeException("No multi-frame process result!");
                }
            }
            if (4 == algoType) {
                if (a.IS_DEBUGGABLE) {
                    Log.d(PostProcessor.TAG, "onCaptureDataAvailable: start process multi-shot image...");
                }
                CaptureData.CaptureDataBean captureDataBean = captureData.getCaptureDataBeanList().get(0);
                ICustomCaptureResult result = captureDataBean.getResult();
                Image mainImage = captureDataBean.getMainImage();
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureTimestamp));
                if (parallelTaskData2 != null) {
                    parallelTaskData2.setCaptureResult(result);
                    if (parallelTaskData2.getDataParameter().shouldReprocessBurstShotPicture()) {
                        captureData.getImageProcessor().dispatchTask(captureData.getCaptureDataBeanList());
                        return;
                    }
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(mainImage.getTimestamp());
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessed(mainImage, 0, false);
                    mainImage.close();
                    onOriginalImageClosed(mainImage);
                    return;
                }
                Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: no captureResult " + captureTimestamp);
                mainImage.close();
                onOriginalImageClosed(mainImage);
                return;
            }
            List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
            if (captureDataBeanList != null && !captureDataBeanList.isEmpty()) {
                for (CaptureData.CaptureDataBean captureDataBean2 : captureDataBeanList) {
                    if (captureDataBean2.isFirstResult()) {
                        long timeStamp3 = captureDataBean2.getResult().getTimeStamp();
                        ParallelTaskData parallelTaskData3 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(timeStamp3));
                        if (parallelTaskData3 == null) {
                            Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: no task data with timestamp " + timeStamp3, new RuntimeException());
                            Image mainImage2 = captureDataBean2.getMainImage();
                            mainImage2.close();
                            onOriginalImageClosed(mainImage2);
                            ImagePool.getInstance().releaseImage(mainImage2);
                            Image subImage = captureDataBean2.getSubImage();
                            if (subImage != null) {
                                subImage.close();
                                onOriginalImageClosed(subImage);
                                ImagePool.getInstance().releaseImage(subImage);
                            }
                        } else if (DataRepository.dataItemFeature().ze()) {
                            int burstNum = captureData.getBurstNum() - 1;
                            parallelTaskData3.setCaptureResult(captureData.getCaptureDataBeanList().get(burstNum).getResult());
                            Log.i(PostProcessor.TAG, "[ALGOUP|MMCAMERA]: Add last metadata, index = " + burstNum);
                        } else {
                            parallelTaskData3.setCaptureResult(captureDataBean2.getResult());
                        }
                    }
                }
                ImageProcessor imageProcessor = captureData.getImageProcessor();
                if (imageProcessor != PostProcessor.this.mImageProcessor) {
                    Log.w(PostProcessor.TAG, "[1] onCaptureDataAvailable: image processor switched");
                }
                imageProcessor.dispatchTask(captureDataBeanList);
            } else if (!a.IS_DEBUGGABLE) {
                Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: There are no result to process!");
            } else {
                throw new RuntimeException("There are no result to process!");
            }
        }

        @Override // com.xiaomi.camera.core.CaptureDataListener
        public void onOriginalImageClosed(Image image) {
            if (PostProcessor.this.mImageMemoryManager != null && image != null) {
                String access$100 = PostProcessor.TAG;
                Log.d(access$100, "onOriginalImageClosed: " + image);
                PostProcessor.this.mImageMemoryManager.releaseAnImage(image);
            }
        }
    };
    private CaptureStatusListener mCaptureStatusListener = new CaptureStatusListener();
    /* access modifiers changed from: private */
    public ImageMemoryManager mImageMemoryManager;
    /* access modifiers changed from: private */
    public ImageProcessor mImageProcessor;
    private List<ImageProcessor> mImageProcessorList = new ArrayList();
    /* access modifiers changed from: private */
    public ImageProcessor.ImageProcessorStatusCallback mImageProcessorStatusCb = new ImageProcessor.ImageProcessorStatusCallback() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass4 */

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public ParallelTaskData getParallelTaskData(long j) {
            return (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j));
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public boolean isAnyFrontProcessing(ImageProcessor imageProcessor) {
            return ParallelDataZipper.getInstance().isAnyFrontProcessingByProcessor(imageProcessor);
        }

        /* JADX DEBUG: Additional 2 move instruction added to help type inference */
        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onImageProcessFailed(Image image, String str) {
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onImageProcessFailed: image=");
            Object obj = image;
            if (image == null) {
                obj = "null";
            }
            sb.append(obj);
            sb.append(" reason=");
            sb.append(str);
            Log.d(access$100, sb.toString());
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onImageProcessStart(long j) {
            if (PostProcessor.this.mPostProcessStatusCallback != null) {
                String access$100 = PostProcessor.TAG;
                Log.d(access$100, "onImageProcessStart: get parallelTaskData: " + j);
                PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessStart((ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j)));
            }
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onImageProcessed(Image image, int i, boolean z) {
            long timestamp = image.getTimestamp();
            String str = timestamp + File.separator + i;
            Log.d(PostProcessor.TAG, "[2] onImageProcessed: " + image + " | " + str);
            if (i == 2) {
                PerformanceTracker.trackJpegReprocess(i, 0);
                PostProcessor.this.mJpegEncoderListener.onJpegAvailable(ImageUtil.getFirstPlane(image), null, str);
                return;
            }
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(timestamp));
            if (parallelTaskData != null) {
                ICustomCaptureResult captureResult = parallelTaskData.getCaptureResult();
                Log.d(PostProcessor.TAG, "[2] onImageProcessed: captureResult = " + captureResult.getResults());
                ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
                ReprocessData reprocessData = new ReprocessData(image, str, captureResult, dataParameter.isFrontCamera(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.getOutputFormat(), PostProcessor.this.mJpegEncoderListener);
                reprocessData.setFrontMirror(dataParameter.isMirror());
                reprocessData.setJpegQuality(dataParameter.getJpegQuality());
                reprocessData.setImageFromPool(z);
                PerformanceTracker.trackJpegReprocess(i, 0);
                try {
                    ReprocessorFactory.getDefaultReprocessor().submit(reprocessData);
                } catch (Exception unused) {
                    PostProcessor.this.mJpegEncoderListener.onError("nativeDetachImage failed for image!!!", str);
                }
            } else {
                throw new RuntimeException("no parallelTaskData with timestamp " + timestamp);
            }
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onOriginalImageClosed(Image image) {
            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(image);
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "onOriginalImageClosed: " + image);
        }
    };
    private List<ImageReader> mImageReaderList = new ArrayList();
    /* access modifiers changed from: private */
    public ImageSaver mImageSaver;
    /* access modifiers changed from: private */
    public ReprocessData.OnDataAvailableListener mJpegEncoderListener = new ReprocessData.OnDataAvailableListener() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass5 */

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onError(String str, String str2) {
            String[] split = str2.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String access$100 = PostProcessor.TAG;
            Log.e(access$100, "[3] onError: " + parseLong + " | " + parseInt + " | " + str);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.releaseImageData();
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                String access$1002 = PostProcessor.TAG;
                Log.e(access$1002, "[3] onError: remove task " + parseLong + " | " + parseInt);
            }
            PostProcessor.this.tryToCloseSession();
        }

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onJpegAvailable(byte[] bArr, byte[] bArr2, String str) {
            String[] split = str.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[3] onJpegAvailable: " + parseLong + " | " + parseInt);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.fillJpegData(bArr, parseInt);
                if (bArr2 != null) {
                    parallelTaskData.setDataOfTheRegionUnderWatermarks(bArr2);
                    ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
                    int[] watermarkRange = Util.getWatermarkRange(dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), (dataParameter.getJpegRotation() + 270) % 360, true, true, false);
                    String access$1002 = PostProcessor.TAG;
                    Log.d(access$1002, "watermarkRange:" + watermarkRange[0] + "_" + watermarkRange[1] + "_" + watermarkRange[2] + "_" + watermarkRange[3]);
                    parallelTaskData.setCoordinatesOfTheRegionUnderWatermarks(watermarkRange);
                }
                if (parallelTaskData.isJpegDataReady()) {
                    TotalCaptureResult totalCaptureResult = CommonUtil.toTotalCaptureResult(parallelTaskData.getCaptureResult(), -1);
                    String access$1003 = PostProcessor.TAG;
                    Log.d(access$1003, "[3] onJpegAvailable: save image start. dataLen=" + bArr.length);
                    if (PostProcessor.this.mImageSaver.onParallelProcessFinish(parallelTaskData, totalCaptureResult, null)) {
                        parallelTaskData.releaseImageData();
                    }
                    if (PostProcessor.this.isNeedCallBackFinished(parallelTaskData) && PostProcessor.this.mPostProcessStatusCallback != null) {
                        PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessEnd(parallelTaskData);
                    }
                    PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                    String access$1004 = PostProcessor.TAG;
                    Log.d(access$1004, "[3] onJpegAvailable: parallelTaskHashMap remove " + parseLong);
                } else {
                    Log.d(PostProcessor.TAG, "[3] onJpegAvailable: jpeg data isn't ready, save action has been ignored.");
                }
            } else {
                String access$1005 = PostProcessor.TAG;
                Log.w(access$1005, "[3] onJpegAvailable: null task data. timestamp=" + parseLong);
            }
            PostProcessor.this.tryToStopBoost();
            PostProcessor.this.tryToCloseSession();
        }

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onYuvAvailable(Image image, String str) {
        }
    };
    private int mMaxParallelRequestNumber = 10;
    private MtkBoost mMtkBoost;
    private List<ImageReader> mObsoleteImageReaderList = new ArrayList();
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Long, ParallelTaskData> mParallelTaskHashMap = new ConcurrentHashMap<>();
    private List<IImageReaderParameterSets> mParams;
    /* access modifiers changed from: private */
    public PostProcessStatusCallback mPostProcessStatusCallback;
    private boolean mSRRequireReprocess;
    private TaskSession.SessionStatusCallback mSessionStatusCb = new TaskSession.SessionStatusCallback() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass1 */

        @Override // com.xiaomi.engine.TaskSession.SessionStatusCallback
        public void onSessionCallback(int i, String str, Object obj) {
            ISessionStatusCallBackListener iSessionStatusCallBackListener;
            if (i != 0) {
                String access$100 = PostProcessor.TAG;
                Log.e(access$100, "Unknown result type " + i);
                return;
            }
            ResultData resultData = (ResultData) obj;
            if (!(PostProcessor.this.mSessionStatusCbListener == null || resultData == null || (iSessionStatusCallBackListener = (ISessionStatusCallBackListener) PostProcessor.this.mSessionStatusCbListener.get()) == null)) {
                try {
                    iSessionStatusCallBackListener.onSessionStatusFlawResultData(resultData.getResultId(), resultData.getFlawResult());
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            String access$1002 = PostProcessor.TAG;
            Log.d(access$1002, "onSessionCallback FLAW getResultId: " + resultData.getResultId() + ", getFlawResult: " + resultData.getFlawResult());
        }
    };
    /* access modifiers changed from: private */
    public WeakReference<ISessionStatusCallBackListener> mSessionStatusCbListener;
    private boolean mShouldDestroyWhenTasksFinished = false;
    private List<Surface> mSurfaceList = new ArrayList();
    private int mToken = -1;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread = new HandlerThread("CallbackHandleThread");
    /* access modifiers changed from: private */
    public ParallelDataZipper.DataListener mZipperResultListener = new ParallelDataZipper.DataListener() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass2 */

        @Override // com.xiaomi.camera.core.ParallelDataZipper.DataListener
        public void onParallelDataAbandoned(CaptureData captureData) {
            if (captureData != null) {
                String access$100 = PostProcessor.TAG;
                Log.d(access$100, "onParallelDataAbandoned: " + captureData);
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureData.getCaptureTimestamp()));
                for (CaptureData.CaptureDataBean captureDataBean : captureData.getCaptureDataBeanList()) {
                    if (captureDataBean != null) {
                        Image mainImage = captureDataBean.getMainImage();
                        String access$1002 = PostProcessor.TAG;
                        Log.d(access$1002, "onParallelDataAbandoned: mainImage = " + mainImage);
                        if (mainImage != null) {
                            mainImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(mainImage);
                        }
                        Image subImage = captureDataBean.getSubImage();
                        String access$1003 = PostProcessor.TAG;
                        Log.d(access$1003, "onParallelDataAbandoned: subImage = " + subImage);
                        if (subImage != null) {
                            subImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(subImage);
                        }
                    }
                }
            }
        }

        @Override // com.xiaomi.camera.core.ParallelDataZipper.DataListener
        public void onParallelDataAvailable(@NonNull CaptureData captureData) {
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[z] onParallelDataAvailable: " + captureData.getCaptureTimestamp());
            if (a.IS_DEBUGGABLE) {
                Iterator<CaptureData.CaptureDataBean> it = captureData.getCaptureDataBeanList().iterator();
                while (it.hasNext()) {
                    Log.d(PostProcessor.TAG, "[z] onParallelDataAvailable: ------------------------");
                    String access$1002 = PostProcessor.TAG;
                    Log.d(access$1002, "[z] Result timestamp: " + it.next().getResult().getTimeStamp());
                }
            }
            if (DataRepository.dataItemFeature().Be() && 2 == captureData.getStreamNum()) {
                if (captureData.getCaptureDataBeanList().size() > 1) {
                    CaptureData.CaptureDataBean captureDataBean = captureData.getCaptureDataBeanList().get(0);
                    if (((Integer) ICustomCaptureResult.toTotalCaptureResult(captureDataBean.getResult(), captureDataBean.getResult().getSessionId()).get(TotalCaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION)).intValue() < 0) {
                        Image mainImage = captureData.getCaptureDataBeanList().get(0).getMainImage();
                        ImageUtil.dumpYuvImageAppendWH(mainImage, "evMinusMainImage" + captureData.getCaptureTimestamp());
                        Image mainImage2 = captureData.getCaptureDataBeanList().get(1).getMainImage();
                        ImageUtil.dumpYuvImageAppendWH(mainImage2, "evZeroMainImage" + captureData.getCaptureTimestamp());
                        Image subImage = captureData.getCaptureDataBeanList().get(1).getSubImage();
                        ImageUtil.dumpYuvImageAppendWH(subImage, "evZeroSubImage" + captureData.getCaptureTimestamp());
                    } else {
                        Image mainImage3 = captureData.getCaptureDataBeanList().get(0).getMainImage();
                        ImageUtil.dumpYuvImageAppendWH(mainImage3, "evZeroMainImage" + captureData.getCaptureTimestamp());
                        Image subImage2 = captureData.getCaptureDataBeanList().get(0).getSubImage();
                        ImageUtil.dumpYuvImageAppendWH(subImage2, "evZeroSubImage" + captureData.getCaptureTimestamp());
                        Image mainImage4 = captureData.getCaptureDataBeanList().get(1).getMainImage();
                        ImageUtil.dumpYuvImageAppendWH(mainImage4, "evMinusMainImage" + captureData.getCaptureTimestamp());
                    }
                } else if (captureData.getCaptureDataBeanList().size() == 1) {
                    Image mainImage5 = captureData.getCaptureDataBeanList().get(0).getMainImage();
                    ImageUtil.dumpYuvImageAppendWH(mainImage5, "evZeroMainImage" + captureData.getCaptureTimestamp());
                    Image subImage3 = captureData.getCaptureDataBeanList().get(0).getSubImage();
                    ImageUtil.dumpYuvImageAppendWH(subImage3, "evZeroSubImage" + captureData.getCaptureTimestamp());
                }
            }
            int algoType = captureData.getAlgoType();
            String access$1003 = PostProcessor.TAG;
            Log.d(access$1003, "[z] onParallelDataAvailable: algoType = " + algoType);
            if (algoType != 4) {
                ImagePool.ImageFormat imageQueueKey = ImagePool.getInstance().toImageQueueKey(captureData.getCaptureDataBeanList().get(0).getMainImage());
                if (ImagePool.getInstance().isImageQueueFull(imageQueueKey, 4)) {
                    Log.w(PostProcessor.TAG, "[z] wait image pool>>");
                    ImagePool.getInstance().waitIfImageQueueFull(imageQueueKey, 4, 0);
                    Log.w(PostProcessor.TAG, "[z] wait image pool<<");
                }
                ImageProcessor imageProcessor = captureData.getImageProcessor();
                if (imageProcessor != null) {
                    imageProcessor.mNeedProcessNormalImageSize.getAndIncrement();
                    if (imageProcessor.mIsBokehMode) {
                        imageProcessor.mNeedProcessRawImageSize.getAndIncrement();
                        imageProcessor.mNeedProcessDepthImageSize.getAndIncrement();
                    }
                }
                PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(captureData.getCaptureTimestamp());
            }
            if (2 == algoType || (3 == algoType && PostProcessor.this.isSRRequireReprocess())) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData != null) {
                    captureData.setMoonMode(parallelTaskData.getDataParameter().isMoonMode());
                    captureData.setCapturedByFrontCamera(parallelTaskData.getDataParameter().isFrontCamera());
                }
                MultiFrameProcessor.getInstance().processData(captureData, PostProcessor.this.mImageProcessor.getTaskSession());
            } else if (5 == algoType) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData2 != null && parallelTaskData2.getDataParameter().isSaveGroupshotPrimitive()) {
                    captureData.setSaveInputImage(true);
                }
                MultiFrameProcessor.getInstance().processData(captureData, null);
            } else {
                PostProcessor.this.mCaptureDataListener.onCaptureDataAvailable(captureData);
            }
            ParallelTaskData parallelTaskData3 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
            if (parallelTaskData3 != null) {
                parallelTaskData3.mIsFrontProcessing = false;
            }
            PostProcessor.this.tryToCloseSession();
        }
    };

    public class CaptureStatusListener {
        public CaptureStatusListener() {
        }

        public void onCaptureCompleted(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[0] onCaptureCompleted: timestamp = " + iCustomCaptureResult.getTimeStamp() + " frameNo = " + iCustomCaptureResult.getFrameNumber());
            ParallelDataZipper.getInstance().join(iCustomCaptureResult, z);
        }

        public void onCaptureFailed(long j, int i) {
            String access$100 = PostProcessor.TAG;
            Log.w(access$100, "[0] onCaptureFailed: reason = " + i + " timestamp = " + j);
            PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(j));
        }

        public void onCaptureStarted(@NonNull ParallelTaskData parallelTaskData) {
            PostProcessor.this.startMtkBoost();
            long timestamp = parallelTaskData.getTimestamp();
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[0] onCaptureStarted: timestamp = " + timestamp);
            if (!parallelTaskData.isAbandoned()) {
                PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timestamp), parallelTaskData);
                parallelTaskData.mIsFrontProcessing = true;
            }
            CaptureData captureData = new CaptureData(parallelTaskData.getAlgoType(), PostProcessor.this.mImageProcessor instanceof DualCameraProcessor ? 2 : 1, parallelTaskData.getBurstNum(), timestamp, parallelTaskData.isAbandoned(), PostProcessor.this.mImageProcessor);
            captureData.setDataListener(PostProcessor.this.mZipperResultListener);
            ParallelDataZipper.getInstance().startTask(captureData);
        }
    }

    private static class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
        private int mImageFlag;
        private ImageMemoryManager mMemoryManager;

        ImageAvailableListener(int i, ImageMemoryManager imageMemoryManager) {
            this.mImageFlag = i;
            this.mMemoryManager = imageMemoryManager;
        }

        public void onImageAvailable(ImageReader imageReader) {
            if (imageReader == null) {
                Log.e(PostProcessor.TAG, "[0] onImageAvailable: null imageReader!");
                return;
            }
            this.mMemoryManager.waitImageCloseIfNeeded();
            Image acquireNextImage = imageReader.acquireNextImage();
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[0] onImageAvailable: timestamp = " + acquireNextImage.getTimestamp());
            this.mMemoryManager.holdAnImage(imageReader.hashCode(), acquireNextImage);
            if (Util.isDumpImageEnabled()) {
                ImageUtil.dumpYuvImage(acquireNextImage, "dump_0_" + acquireNextImage.getTimestamp());
            }
            ParallelDataZipper.getInstance().join(acquireNextImage, this.mImageFlag);
        }
    }

    public interface PostProcessStatusCallback {
        void onImagePostProcessEnd(ParallelTaskData parallelTaskData);

        void onImagePostProcessStart(ParallelTaskData parallelTaskData);

        void onPostProcessorClosed(PostProcessor postProcessor);
    }

    public PostProcessor(Context context, PostProcessStatusCallback postProcessStatusCallback) {
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper());
        this.mPostProcessStatusCallback = postProcessStatusCallback;
        init();
    }

    private void configParallelCaptureSession() {
        if (this.mSurfaceList != null) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mParams.size(); i++) {
                if (this.mParams.get(i).isParallel) {
                    arrayList.add(this.mSurfaceList.get(i));
                }
            }
            if (arrayList.size() > 0) {
                ParallelSnapshotManager.getInstance().createCaptureSession(arrayList);
            }
        }
    }

    private int getMinHoldImageNum(List<IImageReaderParameterSets> list) {
        int i = 0;
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "getMinHoldImageNum: empty param");
            return 0;
        }
        for (IImageReaderParameterSets iImageReaderParameterSets : list) {
            if (i == 0 || iImageReaderParameterSets.maxImages < i) {
                i = iImageReaderParameterSets.maxImages;
            }
        }
        return i;
    }

    private boolean isAnyFrontProcessing(int i) {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.mIsFrontProcessing) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isNeedCallBackFinished(ParallelTaskData parallelTaskData) {
        if (parallelTaskData == null) {
            return false;
        }
        return parallelTaskData.getParallelType() == -7 || parallelTaskData.getParallelType() == -5 || parallelTaskData.getParallelType() == -6;
    }

    /* access modifiers changed from: private */
    public void startMtkBoost() {
        if (b.isMTKPlatform()) {
            if (this.mMtkBoost == null) {
                this.mMtkBoost = new MtkBoost();
            }
            this.mMtkBoost.startBoost();
        }
    }

    private void stopMtkBoost() {
        MtkBoost mtkBoost;
        if (b.isMTKPlatform() && (mtkBoost = this.mMtkBoost) != null) {
            mtkBoost.stopBoost();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void tryToCloseSession() {
        if (this.mShouldDestroyWhenTasksFinished && !isAnyFrontProcessing(0)) {
            Iterator<IImageReaderParameterSets> it = this.mParams.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().isParallel) {
                        ParallelSnapshotManager.getInstance().closeCaptureSession(this.mSurfaceList);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (!this.mParallelTaskHashMap.isEmpty() || !this.mShouldDestroyWhenTasksFinished) {
            Log.d(TAG, "tryToCloseSession: ignore");
        } else {
            Log.d(TAG, "tryToCloseSession: E");
            finish();
            deInit();
            if (this.mPostProcessStatusCallback != null) {
                this.mPostProcessStatusCallback.onPostProcessorClosed(this);
            }
            stopMtkBoost();
            Log.d(TAG, "tryToCloseSession: X");
        }
    }

    /* access modifiers changed from: private */
    public void tryToStopBoost() {
        Log.d(TAG, "tryToStopBoost");
        if (isIdle()) {
            Log.d(TAG, "stopBoost");
            stopMtkBoost();
        }
    }

    public synchronized void configCaptureSession(BufferFormat bufferFormat) {
        String str = TAG;
        Log.d(str, "configCaptureSession: " + bufferFormat);
        boolean z = bufferFormat.getGraphDescriptor().getOperationModeID() == 32770;
        if (this.mImageProcessor != null) {
            this.mImageProcessor.stopWorkWhenIdle();
        }
        if (bufferFormat.getGraphDescriptor().getStreamNumber() == 2) {
            this.mImageProcessor = new DualCameraProcessor(this.mImageProcessorStatusCb, z, bufferFormat);
        } else {
            this.mImageProcessor = new SingleCameraProcessor(this.mImageProcessorStatusCb, z, bufferFormat);
        }
        this.mImageProcessor.setMaxParallelRequestNumber(this.mMaxParallelRequestNumber);
        this.mImageProcessor.setImageBufferQueueSize(this.mMaxParallelRequestNumber);
        this.mImageProcessorList.add(this.mImageProcessor);
        this.mImageProcessor.startWork();
        this.mImageProcessor.setTaskSession(MiCameraAlgo.createSessionByOutputConfigurations(bufferFormat, this.mImageProcessor.configOutputConfigurations(bufferFormat), this.mSessionStatusCb));
    }

    public List<Surface> configHALOutputSurface(@NonNull List<IImageReaderParameterSets> list) {
        String str = TAG;
        Log.d(str, "configHALOutputSurface: paramsNum=" + list.size());
        if (!this.mImageReaderList.isEmpty()) {
            Log.d(TAG, "save obsolete image readers");
            this.mObsoleteImageReaderList.addAll(this.mImageReaderList);
            this.mImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
        int minHoldImageNum = getMinHoldImageNum(list);
        this.mImageMemoryManager = new ImageMemoryManager(minHoldImageNum);
        String str2 = TAG;
        Log.d(str2, "configHALOutputSurface: holdNum=" + minHoldImageNum);
        for (IImageReaderParameterSets iImageReaderParameterSets : list) {
            ImageReader newInstance = ImageReader.newInstance(iImageReaderParameterSets.width, iImageReaderParameterSets.height, iImageReaderParameterSets.format, iImageReaderParameterSets.maxImages);
            newInstance.setOnImageAvailableListener(new ImageAvailableListener(iImageReaderParameterSets.targetCamera, this.mImageMemoryManager), this.mWorkerHandler);
            this.mSurfaceList.add(newInstance.getSurface());
            this.mImageReaderList.add(newInstance);
        }
        configParallelCaptureSession();
        return this.mSurfaceList;
    }

    public void deInit() {
        Log.d(TAG, "deInit");
        if (!this.mImageReaderList.isEmpty()) {
            for (ImageReader imageReader : this.mImageReaderList) {
                imageReader.close();
            }
            this.mImageReaderList.clear();
        }
        if (!this.mObsoleteImageReaderList.isEmpty()) {
            for (ImageReader imageReader2 : this.mObsoleteImageReaderList) {
                imageReader2.close();
            }
            this.mObsoleteImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
    }

    public synchronized void destroyWhenTasksFinished(int i) {
        int token = getToken();
        if (token == -1 || i == token) {
            this.mShouldDestroyWhenTasksFinished = true;
            tryToCloseSession();
        }
    }

    public synchronized void destroyWhenTasksFinished(boolean z) {
        if (!z) {
            if (this.mShouldDestroyWhenTasksFinished && !isAnyFrontProcessing(0)) {
                configParallelCaptureSession();
            }
        }
        this.mShouldDestroyWhenTasksFinished = z;
        if (this.mShouldDestroyWhenTasksFinished) {
            tryToCloseSession();
        }
    }

    public void finish() {
        ImageProcessor imageProcessor = this.mImageProcessor;
        if (imageProcessor != null) {
            imageProcessor.stopWork();
            this.mImageProcessor = null;
        }
        if (!this.mImageProcessorList.isEmpty() && isIdle()) {
            for (ImageProcessor imageProcessor2 : this.mImageProcessorList) {
                imageProcessor2.stopWork();
            }
            this.mImageProcessorList.clear();
        }
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mWorkerThread.join();
                this.mWorkerThread = null;
                this.mWorkerHandler = null;
            } catch (InterruptedException e2) {
                Log.w(TAG, "finish: failed!", e2);
            }
        }
    }

    public CaptureStatusListener getCaptureStatusListener() {
        return this.mCaptureStatusListener;
    }

    public int getFrontProcessingCount() {
        int i = 0;
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.mIsFrontProcessing) {
                i++;
            }
        }
        return i;
    }

    public List<IImageReaderParameterSets> getParams() {
        return this.mParams;
    }

    public List<Surface> getSurfaceList() {
        return this.mSurfaceList;
    }

    public synchronized int getToken() {
        return this.mToken;
    }

    public void init() {
    }

    public boolean isAnyRequestBlocked() {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if ((parallelTaskData.getAlgoType() == 1 || parallelTaskData.getAlgoType() == 8) && parallelTaskData.mIsFrontProcessing) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnyRequestIsHWMFNRProcessing() {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.isHWMFNRProcessing()) {
                return true;
            }
        }
        return false;
    }

    public boolean isIdle() {
        String str = TAG;
        Log.d(str, "isIdle " + this.mParallelTaskHashMap.size());
        return this.mParallelTaskHashMap.isEmpty();
    }

    public boolean isSRRequireReprocess() {
        return this.mSRRequireReprocess;
    }

    public synchronized boolean isStopping() {
        return this.mShouldDestroyWhenTasksFinished;
    }

    public boolean needWaitAlgorithmEngine() {
        ImageProcessor imageProcessor = this.mImageProcessor;
        boolean z = (imageProcessor == null || !imageProcessor.isAlgorithmEngineBusy()) ? false : true;
        if (z) {
            Log.d(TAG, "needWaitAlgorithmEngine: return true");
        } else {
            Log.c(TAG, "needWaitAlgorithmEngine: return false");
        }
        return z;
    }

    public boolean needWaitImageClose() {
        ImageMemoryManager imageMemoryManager = this.mImageMemoryManager;
        boolean z = (imageMemoryManager == null || !imageMemoryManager.needWaitImageClose()) ? false : true;
        if (z) {
            Log.d(TAG, "needWaitImageClose: return true");
        } else {
            Log.c(TAG, "needWaitImageClose: return false");
        }
        return z;
    }

    public void setImageSaver(ImageSaver imageSaver) {
        this.mImageSaver = imageSaver;
    }

    public void setMaxParallelRequestNumber(int i) {
        if (i > 0) {
            this.mMaxParallelRequestNumber = i;
        }
    }

    public void setOnSessionStatusCallBackListener(ISessionStatusCallBackListener iSessionStatusCallBackListener) {
        this.mSessionStatusCbListener = new WeakReference<>(iSessionStatusCallBackListener);
    }

    public void setParams(List<IImageReaderParameterSets> list) {
        this.mParams = list;
    }

    public void setSRRequireReprocess(boolean z) {
        this.mSRRequireReprocess = z;
    }

    public synchronized void setToken(int i) {
        this.mToken = i;
    }
}
