package com.android.camera.animation;

import android.os.SystemClock;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

public class AnimationMonitor {
    private static final long MIN_CHECK_INTERVAL = 100;
    private static final String TAG = "AnimationMonitor";
    private long mLastCheckTime;
    private WeakHashMap<Object, Long> mRunningAnimations = new WeakHashMap<>();

    private static class AnimationMonitorContainer {
        /* access modifiers changed from: private */
        public static AnimationMonitor sInstance = new AnimationMonitor();

        private AnimationMonitorContainer() {
        }
    }

    private void checkRunningAnimations() {
        Log.d(TAG, "checkRunningAnimations");
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ArrayList<Object> arrayList = new ArrayList();
        for (Map.Entry<Object, Long> entry : this.mRunningAnimations.entrySet()) {
            if (elapsedRealtime > entry.getValue().longValue()) {
                arrayList.add(entry.getKey());
            }
        }
        for (Object obj : arrayList) {
            this.mRunningAnimations.remove(obj);
        }
    }

    public static AnimationMonitor get() {
        return AnimationMonitorContainer.sInstance;
    }

    public void animationStart(Object obj, int i) {
        Log.d(TAG, "animationStart animationObject:" + obj + " duration:" + i);
        this.mRunningAnimations.put(obj, Long.valueOf(((long) i) + SystemClock.elapsedRealtime()));
    }

    public void animationStop(Object obj) {
        Log.d(TAG, "animationStop");
        this.mRunningAnimations.remove(obj);
    }

    public boolean hasAnimationRunning() {
        if (this.mRunningAnimations.isEmpty()) {
            return false;
        }
        if (SystemClock.elapsedRealtime() - this.mLastCheckTime > MIN_CHECK_INTERVAL) {
            checkRunningAnimations();
            this.mLastCheckTime = SystemClock.elapsedRealtime();
        }
        return !this.mRunningAnimations.isEmpty();
    }
}
