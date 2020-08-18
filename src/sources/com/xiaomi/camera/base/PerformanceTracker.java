package com.xiaomi.camera.base;

import com.android.camera.log.Log;

public final class PerformanceTracker {
    public static final int END = 1;
    private static final String END_TAG = "[  END]";
    private static final String EVENT_ALGORITHM_PROCESS = "[AlgorithmProcess]";
    private static final String EVENT_CLEAR_SHOT = "[       ClearShot]";
    private static final String EVENT_IMAGE_SAVER = "[      ImageSaver]";
    private static final String EVENT_JPEG_REPROCESS = "[   JpegReprocess]";
    private static final String EVENT_PICTURE_CAPTURE = "[    PictureTaken]";
    public static final int START = 0;
    private static final String START_TAG = "[START]";
    private static final String TAG = "[PERFORMANCE]";
    private static final boolean isEnable = true;

    public static synchronized void trackAlgorithmProcess(String str, int i) {
        synchronized (PerformanceTracker.class) {
            if (i == 0) {
                try {
                    Log.i(TAG, "[AlgorithmProcess][START]" + str + "[" + System.currentTimeMillis() + "]");
                } catch (Throwable th) {
                    throw th;
                }
            } else if (i == 1) {
                Log.i(TAG, "[AlgorithmProcess][  END]" + str + "[" + System.currentTimeMillis() + "]");
            }
        }
    }

    public static synchronized void trackClearShotProcess(String str, int i) {
        synchronized (PerformanceTracker.class) {
            if (i == 0) {
                try {
                    Log.i(TAG, "[       ClearShot][START]" + str + "[" + System.currentTimeMillis() + "]");
                } catch (Throwable th) {
                    throw th;
                }
            } else if (i == 1) {
                Log.i(TAG, "[       ClearShot][  END]" + str + "[" + System.currentTimeMillis() + "]");
            }
        }
    }

    public static synchronized void trackImageSaver(Object obj, int i) {
        synchronized (PerformanceTracker.class) {
            if (i == 0) {
                try {
                    Log.i(TAG, "[      ImageSaver][START][" + obj + "][" + System.currentTimeMillis() + "]");
                } catch (Throwable th) {
                    throw th;
                }
            } else if (i == 1) {
                Log.i(TAG, "[      ImageSaver][  END][" + obj + "][" + System.currentTimeMillis() + "]");
            }
        }
    }

    public static synchronized void trackJpegReprocess(int i, int i2) {
        synchronized (PerformanceTracker.class) {
            String str = null;
            if (i == 0) {
                str = "[EFFECT]";
            } else if (i == 1) {
                str = "[   RAW]";
            } else if (i == 2) {
                str = "[ DEPTH]";
            }
            if (i2 == 0) {
                Log.i(TAG, "[   JpegReprocess][START]" + str + "[" + System.currentTimeMillis() + "]");
            } else if (i2 == 1) {
                Log.i(TAG, "[   JpegReprocess][  END]" + str + "[" + System.currentTimeMillis() + "]");
            }
        }
    }

    public static synchronized void trackPictureCapture(int i) {
        synchronized (PerformanceTracker.class) {
            if (i == 0) {
                try {
                    Log.i(TAG, "[    PictureTaken][START][" + System.currentTimeMillis() + "]");
                } catch (Throwable th) {
                    throw th;
                }
            } else if (i == 1) {
                Log.i(TAG, "[    PictureTaken][  END][" + System.currentTimeMillis() + "]");
            }
        }
    }
}
