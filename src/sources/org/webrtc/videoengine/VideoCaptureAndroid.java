package org.webrtc.videoengine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.android.camera.constant.AutoFocus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class VideoCaptureAndroid implements Camera.PreviewCallback, SurfaceHolder.Callback {
    private static final String TAG = "WEBRTC-JC-VideoCaptureAndroid";
    private static SurfaceHolder localPreview;
    private boolean bLandScapeMode = false;
    private Camera camera;
    /* access modifiers changed from: private */
    public CameraThread cameraThread;
    private Handler cameraThreadHandler;
    /* access modifiers changed from: private */
    public Integer deviceRotation = 0;
    private OrientationEventListener deviceRotationNotifier;
    private SurfaceTexture dummySurfaceTexture;
    private boolean firstFrame = true;
    private int frameRotation = 0;
    private final int id;
    private final Camera.CameraInfo info;
    private final long native_capturer;
    private final int numCaptureBuffers = 3;
    private int rotation = 0;

    /* access modifiers changed from: private */
    public class CameraThread extends Thread {
        private Exchanger<Handler> handlerExchanger;

        public CameraThread(Exchanger<Handler> exchanger) {
            this.handlerExchanger = exchanger;
        }

        public void quitLoop() {
            Looper.myLooper().quit();
        }

        public void run() {
            Looper.prepare();
            Object unused = VideoCaptureAndroid.exchange(this.handlerExchanger, new Handler());
            Looper.loop();
        }
    }

    public VideoCaptureAndroid(Context context, int i, long j) {
        this.id = i;
        this.native_capturer = j;
        this.info = new Camera.CameraInfo();
        this.firstFrame = true;
        Camera.getCameraInfo(i, this.info);
        int rotation2 = ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation2 == 0) {
            this.deviceRotation = 0;
        } else if (rotation2 == 1) {
            this.deviceRotation = 270;
        } else if (rotation2 == 2) {
            this.deviceRotation = 180;
        } else if (rotation2 == 3) {
            this.deviceRotation = 90;
        }
        this.deviceRotationNotifier = new OrientationEventListener(context, 3) {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass1 */

            public void onOrientationChanged(int i) {
                if (i == -1) {
                    Log.d(VideoCaptureAndroid.TAG, "The device rotation angle is unknown.");
                    return;
                }
                synchronized (VideoCaptureAndroid.this.deviceRotation) {
                    if (VideoCaptureAndroid.this.deviceRotation.intValue() != i) {
                        Integer unused = VideoCaptureAndroid.this.deviceRotation = Integer.valueOf(i);
                    }
                }
            }
        };
        Exchanger exchanger = new Exchanger();
        this.cameraThread = new CameraThread(exchanger);
        this.cameraThread.start();
        this.cameraThreadHandler = (Handler) exchange(exchanger, null);
    }

    private native void ProvideCameraFrame(byte[] bArr, int i, long j, int i2);

    private int clamp(int i, int i2) {
        int i3 = i2 / 2;
        return Math.abs(i) + i3 > 1000 ? i > 0 ? 1000 - i2 : NotificationManagerCompat.IMPORTANCE_UNSPECIFIED : i - i3;
    }

    private void destroy() {
        Log.d(TAG, "destroy, stop the thread and looper.");
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass2 */

            public void run() {
                VideoCaptureAndroid.this.cameraThread.quitLoop();
            }
        });
    }

    /* access modifiers changed from: private */
    public static <T> T exchange(Exchanger<T> exchanger, T t) {
        try {
            return exchanger.exchange(t);
        } catch (InterruptedException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* access modifiers changed from: private */
    public void getCurrentVideoZoomFactorOnCameraThread(Exchanger<Integer> exchanger) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            exchange(exchanger, 0);
            return;
        }
        Camera.Parameters parameters = camera2.getParameters();
        if (!parameters.isZoomSupported()) {
            Log.d(TAG, "setVideoZoomFactor is not suppored.");
            exchange(exchanger, 0);
            return;
        }
        int zoom = parameters.getZoom();
        Log.d(TAG, "The current zoom value is: " + zoom);
        exchange(exchanger, Integer.valueOf(zoom));
    }

    /* access modifiers changed from: private */
    public void getSupportedVideoZoomMaxFactorOnCameraThread(Exchanger<Integer> exchanger) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            exchange(exchanger, 0);
            return;
        }
        Camera.Parameters parameters = camera2.getParameters();
        if (!parameters.isZoomSupported()) {
            Log.d(TAG, "setVideoZoomFactor is not supported.");
            exchange(exchanger, 0);
            return;
        }
        int maxZoom = parameters.getMaxZoom();
        Log.d(TAG, "The max zoom value is: " + maxZoom);
        exchange(exchanger, Integer.valueOf(maxZoom));
    }

    /* access modifiers changed from: private */
    public void isVideoZoomSupportedOnCameraThread(Exchanger<Boolean> exchanger) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            exchange(exchanger, false);
        } else if (camera2.getParameters().isZoomSupported()) {
            exchange(exchanger, true);
        } else {
            exchange(exchanger, false);
        }
    }

    private synchronized void setExposurePointOfInterest(final float f2, final float f3) {
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass9 */

            public void run() {
                VideoCaptureAndroid.this.setExposurePointOfInterestOnCameraThread(f2, f3);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setExposurePointOfInterestOnCameraThread(float f2, float f3) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            return;
        }
        Camera.Parameters parameters = camera2.getParameters();
        if (parameters.getMaxNumMeteringAreas() == 0) {
            Log.d(TAG, "SettingMeteringArea is not supported.");
            return;
        }
        int clamp = clamp(Float.valueOf((f2 * 2000.0f) - 1000.0f).intValue(), 300);
        int clamp2 = clamp(Float.valueOf((2000.0f * f3) - 1000.0f).intValue(), 300);
        Log.d(TAG, "setExposurePointOfInterest: left: " + clamp + " top: " + clamp2 + " before convert X:" + f2 + " Y:" + f3);
        Rect rect = new Rect(clamp, clamp2, clamp + 300, clamp2 + 300);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Camera.Area(rect, 1000));
        try {
            parameters.setMeteringAreas(arrayList);
            this.camera.setParameters(parameters);
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.d(TAG, "Unable to setExposurePointOfInterest.");
        }
    }

    private synchronized void setFlashState(final boolean z) {
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass10 */

            public void run() {
                VideoCaptureAndroid.this.setFlashStateOnCameraThread(z);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setFlashStateOnCameraThread(boolean z) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            return;
        }
        Camera.Parameters parameters = camera2.getParameters();
        if (parameters.getFlashMode() == null) {
            Log.d(TAG, "Flash mode setting is not supported.");
            return;
        }
        try {
            parameters.setFlashMode(z ? "torch" : "off");
            this.camera.setParameters(parameters);
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.d(TAG, "Failed to turn the flash on!");
        }
    }

    private synchronized void setFocusPointOfInterest(final float f2, final float f3) {
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass4 */

            public void run() {
                VideoCaptureAndroid.this.setFocusPointOfInterestOnCameraThread(f2, f3);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setFocusPointOfInterestOnCameraThread(float f2, float f3) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            return;
        }
        Camera.Parameters parameters = camera2.getParameters();
        this.camera.cancelAutoFocus();
        parameters.setFocusMode("auto");
        if (parameters.getMaxNumFocusAreas() == 0) {
            Log.d(TAG, "SetFocusArea is not supported");
            return;
        }
        int clamp = clamp(Float.valueOf((f2 * 2000.0f) - 1000.0f).intValue(), 200);
        int clamp2 = clamp(Float.valueOf((2000.0f * f3) - 1000.0f).intValue(), 200);
        Log.d(TAG, "SetFocusArea: left: " + clamp + " top: " + clamp2 + " before convert X:" + f2 + " Y:" + f3);
        Rect rect = new Rect(clamp, clamp2, clamp + 200, clamp2 + 200);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Camera.Area(rect, 1000));
        try {
            parameters.setFocusAreas(arrayList);
            this.camera.setParameters(parameters);
            this.camera.cancelAutoFocus();
            if (parameters.getFocusMode() != AutoFocus.LEGACY_CONTINUOUS_VIDEO) {
                parameters.setFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO);
            }
            if (parameters.getMaxNumFocusAreas() > 0) {
                parameters.setFocusAreas(null);
            }
            this.camera.setParameters(parameters);
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.d(TAG, "Unable to setFocusPointOfInterest.");
        }
    }

    public static void setLocalPreview(SurfaceHolder surfaceHolder) {
        localPreview = surfaceHolder;
    }

    /* access modifiers changed from: private */
    public void setPreviewDisplayOnCameraThread(SurfaceHolder surfaceHolder, Exchanger<IOException> exchanger) {
        try {
            this.camera.setPreviewDisplay(surfaceHolder);
            exchange(exchanger, null);
        } catch (IOException e2) {
            exchange(exchanger, e2);
        }
    }

    private synchronized void setPreviewRotation(final int i) {
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass12 */

            public void run() {
                VideoCaptureAndroid.this.setPreviewRotationOnCameraThread(i);
            }
        });
    }

    private synchronized void setPreviewRotation(final int i, boolean z) {
        this.bLandScapeMode = z;
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass13 */

            public void run() {
                VideoCaptureAndroid.this.setPreviewRotationOnCameraThread(i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setPreviewRotationOnCameraThread(int i) {
        Log.v(TAG, "setPreviewRotation:" + i);
        if (this.camera != null) {
            Camera.CameraInfo cameraInfo = this.info;
            int i2 = cameraInfo.facing == 1 ? (360 - cameraInfo.orientation) % 360 : cameraInfo.orientation;
            if (!this.bLandScapeMode) {
                this.camera.setDisplayOrientation(i2);
            }
        }
    }

    private synchronized void setVideoZoomFactor(final int i) {
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass5 */

            public void run() {
                VideoCaptureAndroid.this.setVideoZoomFactorOnCameraThread(i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setVideoZoomFactorOnCameraThread(int i) {
        Camera camera2 = this.camera;
        if (camera2 == null) {
            Log.d(TAG, "Camera is null.");
            return;
        }
        Camera.Parameters parameters = camera2.getParameters();
        if (!parameters.isZoomSupported()) {
            Log.d(TAG, "setVideoZoomFactor is not supported.");
            return;
        }
        int maxZoom = parameters.getMaxZoom();
        int max = Math.max(0, Math.min(i, maxZoom));
        Log.d(TAG, "The max zoom value is: " + maxZoom + ", and the set zoom value is: " + max);
        try {
            parameters.setZoom(max);
            this.camera.setParameters(parameters);
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.d(TAG, "setVideoZoomFacor failed.");
        }
    }

    private boolean startCapture(final int i, final int i2, final int i3, final int i4) {
        final Exchanger exchanger = new Exchanger();
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass3 */

            public void run() {
                VideoCaptureAndroid.this.startCaptureOnCameraThread(i, i2, i3, i4, exchanger);
            }
        });
        return ((Boolean) exchange(exchanger, false)).booleanValue();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00e3, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00e9, code lost:
        throw new java.lang.RuntimeException(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ea, code lost:
        r6 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00eb, code lost:
        r5.deviceRotationNotifier.disable();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0100, code lost:
        r6 = new java.util.concurrent.Exchanger<>();
        stopCaptureOnCameraThread(r6);
        exchange(r6, false);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ea A[ExcHandler: RuntimeException (e java.lang.RuntimeException), Splitter:B:1:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0100  */
    @SuppressLint({"NewApi"})
    public void startCaptureOnCameraThread(int i, int i2, int i3, int i4, Exchanger<Boolean> exchanger) {
        Log.d(TAG, "startCapture: " + i + "x" + i2 + "@" + i3 + ":" + i4);
        try {
            this.camera = Camera.open(this.id);
            if (localPreview != null) {
                localPreview.addCallback(this);
                if (localPreview.getSurface() != null && localPreview.getSurface().isValid()) {
                    this.camera.setPreviewDisplay(localPreview);
                }
            } else {
                this.dummySurfaceTexture = new SurfaceTexture(42);
                this.camera.setPreviewTexture(this.dummySurfaceTexture);
            }
            Camera.Parameters parameters = this.camera.getParameters();
            Log.d(TAG, "isVideoStabilizationSupported: " + parameters.isVideoStabilizationSupported());
            if (parameters.isVideoStabilizationSupported()) {
                parameters.setVideoStabilization(true);
            }
            parameters.setPreviewSize(i, i2);
            parameters.setPreviewFpsRange(i3, i4);
            int i5 = 17;
            if (Build.MODEL.equalsIgnoreCase("MiTV2")) {
                i5 = 842094169;
            }
            parameters.setPreviewFormat(i5);
            this.camera.setParameters(parameters);
            int bitsPerPixel = ((i * i2) * ImageFormat.getBitsPerPixel(i5)) / 8;
            for (int i6 = 0; i6 < 3; i6++) {
                this.camera.addCallbackBuffer(new byte[bitsPerPixel]);
            }
            this.camera.setPreviewCallbackWithBuffer(this);
            this.camera.startPreview();
            this.deviceRotationNotifier.enable();
            setFocusPointOfInterest(0.5f, 0.5f);
            exchange(exchanger, true);
        } catch (IOException e2) {
            e = e2;
            this.deviceRotationNotifier.disable();
            Log.e(TAG, "startCapture failed", e);
            if (this.camera != null) {
            }
            exchange(exchanger, false);
        } catch (RuntimeException e3) {
        }
    }

    /* access modifiers changed from: private */
    public void stopCaptureOnCameraThread(Exchanger<Boolean> exchanger) {
        Log.d(TAG, "stopCapture");
        Camera camera2 = this.camera;
        if (camera2 != null) {
            try {
                camera2.stopPreview();
                this.camera.setPreviewCallbackWithBuffer(null);
                if (localPreview != null) {
                    localPreview.removeCallback(this);
                    this.camera.setPreviewDisplay(null);
                } else {
                    this.camera.setPreviewTexture(null);
                }
                this.camera.release();
                this.camera = null;
                this.deviceRotationNotifier.disable();
                exchange(exchanger, true);
            } catch (IOException e2) {
                e = e2;
                this.deviceRotationNotifier.disable();
                Log.e(TAG, "Failed to stop camera", e);
                exchange(exchanger, false);
            } catch (RuntimeException e3) {
                e = e3;
                this.deviceRotationNotifier.disable();
                Log.e(TAG, "Failed to stop camera", e);
                exchange(exchanger, false);
            }
        } else {
            throw new RuntimeException("Camera is already stopped!");
        }
    }

    public int getCurrentVideoZoomFactor() {
        final Exchanger exchanger = new Exchanger();
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass8 */

            public void run() {
                VideoCaptureAndroid.this.getCurrentVideoZoomFactorOnCameraThread(exchanger);
            }
        });
        return ((Integer) exchange(exchanger, 0)).intValue();
    }

    public int getSupportedVideoZoomMaxFactor() {
        final Exchanger exchanger = new Exchanger();
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass6 */

            public void run() {
                VideoCaptureAndroid.this.getSupportedVideoZoomMaxFactorOnCameraThread(exchanger);
            }
        });
        return ((Integer) exchange(exchanger, 0)).intValue();
    }

    public boolean isVideoZoomSupported() {
        final Exchanger exchanger = new Exchanger();
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass7 */

            public void run() {
                VideoCaptureAndroid.this.isVideoZoomSupportedOnCameraThread(exchanger);
            }
        });
        return ((Boolean) exchange(exchanger, false)).booleanValue();
    }

    public synchronized void onPreviewFrame(byte[] bArr, Camera camera2) {
        if (this.camera == null) {
            Log.e(TAG, "camera is null (onPreviewFrame)");
        } else if (this.camera == camera2) {
            if (this.firstFrame) {
                if (this.deviceRotation.intValue() >= 315 || this.deviceRotation.intValue() <= 45) {
                    this.rotation = 0;
                } else if (this.deviceRotation.intValue() > 45 && this.deviceRotation.intValue() < 135) {
                    this.rotation = 90;
                } else if (this.deviceRotation.intValue() >= 135 && this.deviceRotation.intValue() <= 225) {
                    this.rotation = 180;
                } else if (this.deviceRotation.intValue() > 225 && this.deviceRotation.intValue() < 315) {
                    this.rotation = 270;
                }
                this.firstFrame = false;
            } else if (this.deviceRotation.intValue() > 325 || this.deviceRotation.intValue() < 35) {
                this.rotation = 0;
            } else if (this.deviceRotation.intValue() > 55 && this.deviceRotation.intValue() < 125) {
                this.rotation = 90;
            } else if (this.deviceRotation.intValue() > 145 && this.deviceRotation.intValue() < 215) {
                this.rotation = 180;
            } else if (this.deviceRotation.intValue() > 235 && this.deviceRotation.intValue() < 305) {
                this.rotation = 270;
            }
            if (this.info.facing == 1) {
                this.frameRotation = ((this.info.orientation - this.rotation) + 360) % 360;
            } else if (this.info.facing == 0) {
                this.frameRotation = (this.info.orientation + this.rotation) % 360;
            }
            ProvideCameraFrame(bArr, bArr.length, this.native_capturer, this.frameRotation);
            this.camera.addCallbackBuffer(bArr);
        } else {
            throw new RuntimeException("Unexpected camera in callback!");
        }
    }

    public boolean stopCapture() {
        final Exchanger exchanger = new Exchanger();
        this.cameraThreadHandler.post(new Runnable() {
            /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass11 */

            public void run() {
                VideoCaptureAndroid.this.stopCaptureOnCameraThread(exchanger);
            }
        });
        return ((Boolean) exchange(exchanger, true)).booleanValue();
    }

    public synchronized void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Log.d(TAG, "VideoCaptureAndroid::surfaceChanged ignored: " + i + ": " + i2 + "x" + i3);
    }

    public synchronized void surfaceCreated(final SurfaceHolder surfaceHolder) {
        Log.d(TAG, "VideoCaptureAndroid::surfaceCreated");
        if (this.camera != null) {
            final Exchanger exchanger = new Exchanger();
            this.cameraThreadHandler.post(new Runnable() {
                /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass14 */

                public void run() {
                    VideoCaptureAndroid.this.setPreviewDisplayOnCameraThread(surfaceHolder, exchanger);
                }
            });
            IOException iOException = (IOException) exchange(exchanger, null);
            if (iOException != null) {
                throw new RuntimeException(iOException);
            }
        }
    }

    public synchronized void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "VideoCaptureAndroid::surfaceDestroyed");
        if (this.camera != null) {
            final Exchanger exchanger = new Exchanger();
            this.cameraThreadHandler.post(new Runnable() {
                /* class org.webrtc.videoengine.VideoCaptureAndroid.AnonymousClass15 */

                public void run() {
                    VideoCaptureAndroid.this.setPreviewDisplayOnCameraThread(null, exchanger);
                }
            });
            IOException iOException = (IOException) exchange(exchanger, null);
            if (iOException != null) {
                throw new RuntimeException(iOException);
            }
        }
    }
}
