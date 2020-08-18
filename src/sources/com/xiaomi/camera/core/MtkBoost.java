package com.xiaomi.camera.core;

import com.android.camera.log.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MtkBoost implements BaseBoostFramework {
    private static final String BOOST_CLASS_NAME = "android.util.MtkBoostFramework";
    private static final String BOOST_METHOD_NAME = "perfLockAcquire";
    private static final String BOOST_STOP_NAME = "perfLockRelease";
    private static final String TAG = "MtkBoost";
    private static boolean isInited;
    private static Class<?> mBoostClass;
    private static final int[] mFBoostParamVal = {4194304, 2000000, 4194560, 2000000, 16777216, 0, 12582912, 0};
    private static Method mStartBoost;
    private static Method mStopBoost;
    private Object mPerf = null;

    public MtkBoost() {
        synchronized (MtkBoost.class) {
            if (!isInited) {
                try {
                    mBoostClass = Class.forName(BOOST_CLASS_NAME);
                    if (mBoostClass != null) {
                        mStartBoost = mBoostClass.getMethod(BOOST_METHOD_NAME, Integer.TYPE, int[].class);
                        mStopBoost = mBoostClass.getMethod(BOOST_STOP_NAME, new Class[0]);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                isInited = true;
            }
        }
        try {
            if (mBoostClass != null) {
                Constructor<?> constructor = mBoostClass.getConstructor(new Class[0]);
                constructor.setAccessible(true);
                this.mPerf = constructor.newInstance(new Object[0]);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private boolean startBoostInternal(int i) {
        Log.d(TAG, "startBoostInternal " + i);
        if (this.mPerf == null) {
            Log.d(TAG, "not init boost pref");
            return false;
        }
        try {
            if (mStartBoost != null) {
                Log.d(TAG, "ready to boost");
                mStartBoost.setAccessible(true);
                mStartBoost.invoke(this.mPerf, Integer.valueOf(i), mFBoostParamVal);
                return true;
            }
        } catch (Exception e2) {
            Log.e(TAG, "start boost exception " + e2);
            e2.printStackTrace();
        }
        return false;
    }

    private void stopBoostInternal() {
        Log.d(TAG, "stopBoostInternal:");
        if (this.mPerf == null) {
            Log.d(TAG, "not init boost pref, not need to stop");
            return;
        }
        try {
            if (mStopBoost != null) {
                Log.d(TAG, "ready to stop boost");
                mStopBoost.setAccessible(true);
                mStopBoost.invoke(this.mPerf, new Object[0]);
            }
        } catch (Exception e2) {
            Log.e(TAG, "stop boost exception " + e2);
            e2.printStackTrace();
        }
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public boolean startBoost() {
        return startBoost(0);
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public boolean startBoost(int i) {
        boolean startBoostInternal = startBoostInternal(i);
        Log.d(TAG, "startBoost: isBoostStarted " + startBoostInternal);
        return startBoostInternal;
    }

    @Override // com.xiaomi.camera.core.BaseBoostFramework
    public void stopBoost() {
        stopBoostInternal();
    }
}
