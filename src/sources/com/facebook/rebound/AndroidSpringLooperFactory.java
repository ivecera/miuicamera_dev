package com.facebook.rebound;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Choreographer;

abstract class AndroidSpringLooperFactory {

    @TargetApi(16)
    private static class ChoreographerAndroidSpringLooper extends SpringLooper {
        /* access modifiers changed from: private */
        public final Choreographer mChoreographer;
        /* access modifiers changed from: private */
        public final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() {
            /* class com.facebook.rebound.AndroidSpringLooperFactory.ChoreographerAndroidSpringLooper.AnonymousClass1 */

            public void doFrame(long j) {
                if (ChoreographerAndroidSpringLooper.this.mStarted && ((SpringLooper) ChoreographerAndroidSpringLooper.this).mSpringSystem != null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    ChoreographerAndroidSpringLooper choreographerAndroidSpringLooper = ChoreographerAndroidSpringLooper.this;
                    ((SpringLooper) choreographerAndroidSpringLooper).mSpringSystem.loop((double) (uptimeMillis - choreographerAndroidSpringLooper.mLastTime));
                    long unused = ChoreographerAndroidSpringLooper.this.mLastTime = uptimeMillis;
                    ChoreographerAndroidSpringLooper.this.mChoreographer.postFrameCallback(ChoreographerAndroidSpringLooper.this.mFrameCallback);
                }
            }
        };
        /* access modifiers changed from: private */
        public long mLastTime;
        /* access modifiers changed from: private */
        public boolean mStarted;

        public ChoreographerAndroidSpringLooper(Choreographer choreographer) {
            this.mChoreographer = choreographer;
        }

        public static ChoreographerAndroidSpringLooper create() {
            return new ChoreographerAndroidSpringLooper(Choreographer.getInstance());
        }

        @Override // com.facebook.rebound.SpringLooper
        public void start() {
            if (!this.mStarted) {
                this.mStarted = true;
                this.mLastTime = SystemClock.uptimeMillis();
                this.mChoreographer.removeFrameCallback(this.mFrameCallback);
                this.mChoreographer.postFrameCallback(this.mFrameCallback);
            }
        }

        @Override // com.facebook.rebound.SpringLooper
        public void stop() {
            this.mStarted = false;
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
        }
    }

    private static class LegacyAndroidSpringLooper extends SpringLooper {
        /* access modifiers changed from: private */
        public final Handler mHandler;
        /* access modifiers changed from: private */
        public long mLastTime;
        /* access modifiers changed from: private */
        public final Runnable mLooperRunnable = new Runnable() {
            /* class com.facebook.rebound.AndroidSpringLooperFactory.LegacyAndroidSpringLooper.AnonymousClass1 */

            public void run() {
                if (LegacyAndroidSpringLooper.this.mStarted && ((SpringLooper) LegacyAndroidSpringLooper.this).mSpringSystem != null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    LegacyAndroidSpringLooper legacyAndroidSpringLooper = LegacyAndroidSpringLooper.this;
                    ((SpringLooper) legacyAndroidSpringLooper).mSpringSystem.loop((double) (uptimeMillis - legacyAndroidSpringLooper.mLastTime));
                    long unused = LegacyAndroidSpringLooper.this.mLastTime = uptimeMillis;
                    LegacyAndroidSpringLooper.this.mHandler.post(LegacyAndroidSpringLooper.this.mLooperRunnable);
                }
            }
        };
        /* access modifiers changed from: private */
        public boolean mStarted;

        public LegacyAndroidSpringLooper(Handler handler) {
            this.mHandler = handler;
        }

        public static SpringLooper create() {
            return new LegacyAndroidSpringLooper(new Handler());
        }

        @Override // com.facebook.rebound.SpringLooper
        public void start() {
            if (!this.mStarted) {
                this.mStarted = true;
                this.mLastTime = SystemClock.uptimeMillis();
                this.mHandler.removeCallbacks(this.mLooperRunnable);
                this.mHandler.post(this.mLooperRunnable);
            }
        }

        @Override // com.facebook.rebound.SpringLooper
        public void stop() {
            this.mStarted = false;
            this.mHandler.removeCallbacks(this.mLooperRunnable);
        }
    }

    AndroidSpringLooperFactory() {
    }

    public static SpringLooper createSpringLooper() {
        return Build.VERSION.SDK_INT >= 16 ? ChoreographerAndroidSpringLooper.create() : LegacyAndroidSpringLooper.create();
    }
}
